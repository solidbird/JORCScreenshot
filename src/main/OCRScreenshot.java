package main;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OCRScreenshot extends JFrame{
	
	DrawRectMouse rectMouse = null;
	String path = OCRScreenshot.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "test.png";
	
	public OCRScreenshot() {
		File f = new File(this.path);
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
				
		this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = g.getScreenDevices();
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        
        for( GraphicsDevice device : devices ) {
        	
        	Rectangle deviceRect = device.getDefaultConfiguration().getBounds();
        	if(deviceRect.contains(mouseLocation)) {
        		try {
					new Screenshots(this.path, deviceRect);
				} catch (IOException | AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		rectMouse = new DrawRectMouse(deviceRect, this.path);
        		
        		device.setFullScreenWindow(this);
        	} 
        }
        
        
        
        this.add(rectMouse);


        
        this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	public static void main(String[] args) throws IOException, AWTException {

        OCRScreenshot ocrs = new OCRScreenshot();
        
        ocrs.setVisible(true);	
    }
}
