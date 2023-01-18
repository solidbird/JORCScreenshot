package main;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Screenshots {
	
	
	public Screenshots(String filename,Rectangle screenRect) throws IOException, AWTException {
		BufferedImage capture = new Robot().createScreenCapture(screenRect);
		ImageIO.write(capture, "png", new File(filename));
	}
	
}
