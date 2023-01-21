package main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;

public class DrawRectMouse extends JLabel{
	
    Point p1 = new Point(0, 0);
    Point p2 = new Point(0, 0);
    int px, py, pw, ph;
    Rectangle screenRect;
    boolean drawing;
    String filename;
    Tesseract t;
    String res = "";

   public DrawRectMouse(Rectangle screenRect, String filename){
	   super(new ImageIcon(filename));
	   this.filename = filename;
	   
	   this.screenRect = screenRect;
	   MouseHandler mouseHandler = new MouseHandler();
	   
       //this.setLayout(null);
	   this.setOpaque(false);
       this.addMouseListener(mouseHandler);
       this.addMouseMotionListener(mouseHandler);
       
       this.t = new Tesseract();
       File f = new File("tessdata");
       
       if( !(f.exists() && f.isDirectory()) ) {
    	   int option = JOptionPane.showConfirmDialog(this, "Tesseract requires training data. Please place the 'tessdata' folder in the same directory like this jar file.", "Error", JOptionPane.DEFAULT_OPTION);
    	   if(option == JOptionPane.OK_OPTION) {
    		   System.exit(0);
    	   }
       }
       
       this.t.setDatapath(f.getAbsolutePath());

       //this.t.setDatapath(DrawRectMouse.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "tessdata");
       //this.t.setPageSegMode(7);
       //this.t.setLanguage("deu");
       
   }
   
   public void setStartPoint(Point p1) {
       this.p1 = p1;
   }

   public void setEndPoint(Point p2) {
       this.p2 = p2;
   }

   public void drawPerfectRect(Graphics g, Point p1, Point p2) {
       px = Math.min(p1.x,p2.x);
       py = Math.min(p1.y,p2.y);
       pw = Math.abs(p1.x-p2.x);
       ph = Math.abs(p1.y-p2.y);
       g.drawRect(px, py, pw, ph);
   }

   @Override
   protected void paintComponent(Graphics g) {
       super.paintComponent(g);
       g.setColor(Color.RED);
       drawPerfectRect(g, p1, p2);
       
   }

   private class MouseHandler extends MouseAdapter {
	   public void mousePressed(MouseEvent e) {
           setStartPoint(e.getPoint());
       }

       public void mouseDragged(MouseEvent e) {
           setEndPoint(e.getPoint());
           repaint();
       }

       public void mouseReleased(MouseEvent e) {
           setEndPoint(e.getPoint());
           repaint();
           
           screenRect.setBounds(px + screenRect.x + 1, py + screenRect.y + 1, pw - 1, ph - 1);
           try {
        	   new Screenshots(filename, screenRect);
        	   Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        	   clipboard.setContents(new StringSelection(t.doOCR(new File(filename))), null); 
        	   
           } catch (IOException e2) {
			// TODO Auto-generated catch block
				e2.printStackTrace();
           } catch (AWTException e2) {
			// TODO Auto-generated catch block
				e2.printStackTrace();
           } catch (TesseractException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
           
       
       }
   }
}
