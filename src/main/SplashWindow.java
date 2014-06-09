package main;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.Window;
import java.net.URL;

import javax.swing.JFrame;

public class SplashWindow extends Window{
	
	private static final long serialVersionUID = 1L;
	private Image image;
	protected static SplashWindow instance;
	private boolean paintCalled = false;
	public static boolean doneLoading;
	
	
	private SplashWindow(JFrame parent, Image image) {
	        super(parent);
	        this.image = image;
	        doneLoading = false;
	        
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			int w = 800;
			int h = 373;
			int x = (dim.width-w)/2;
			int y = (dim.height-h)/2;
			
			setBounds(x, y, w, h);

	        MediaTracker mt = new MediaTracker(this);
	        mt.addImage(image,0);
	        try {
	            mt.waitForID(0);
	        } catch(InterruptedException ie){}
	    }
	
	public static void invokeMain(String className, String[] args) {
	    try {
	        Class.forName(className).getMethod("main", new Class[] {String[].class}).invoke(null, new Object[] {args});
	    } catch (Exception e) {
	        InternalError error = new InternalError("Failed to invoke main method");
	        error.initCause(e);
	        e.printStackTrace();
	        throw error;
	    }
	}
	
	public static void splash(URL imageURL) {
	    if (imageURL != null) {
	        splash(Toolkit.getDefaultToolkit().createImage(imageURL));
	    }
	    else System.out.println("image not found.");
	}


	public static void splash(Image image) {
	    if (instance == null && image != null) {
	        JFrame f = new JFrame();
	        f.setPreferredSize(new Dimension(800, 373));

	        instance = new SplashWindow(f, image);
	        instance.setVisible(true);
	        if (!EventQueue.isDispatchThread() && Runtime.getRuntime().availableProcessors() == 1) {
	            synchronized (instance) {
	                while (!instance.paintCalled && !doneLoading) {
	                    try {
	                    	instance.wait();
	                    } catch (InterruptedException e) {}
	                }
	            }
	        }
	    }
	}

	public void update(Graphics g) {
	    paint(g);
	}
	
	public void paint(Graphics g) {
	    g.drawImage(image, 0, 0, this);

	    if (!paintCalled) {
	        paintCalled = true;
	        synchronized (this) { notifyAll(); }
	    }
	}

	public static void disposeSplash() {
		instance.dispose();
	}
}
