package main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Splash {
	protected static final boolean DEBUG = false;

	public static void main(String[] args) {
		try {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Army Organizer");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.getMessage());
		}
		catch(InstantiationException e) {
			System.out.println("InstantiationException: " + e.getMessage());
		}
		catch(IllegalAccessException e) {
			System.out.println("IllegalAccessException: " + e.getMessage());
		}
		catch(UnsupportedLookAndFeelException e) {
			System.out.println("UnsupportedLookAndFeelException: " + e.getMessage());
		}
		SplashWindow.splash(Splash.class.getClassLoader().getResource("splash.gif"));
		SplashWindow.invokeMain("main.Launcher", args);
		SplashWindow.disposeSplash();
	}

	public static boolean isMac() {
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("mac") >= 0);
	}
}