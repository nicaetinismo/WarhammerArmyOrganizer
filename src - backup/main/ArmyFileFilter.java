package main;

import java.io.File;
import javax.swing.filechooser.FileFilter;
 
public class ArmyFileFilter extends FileFilter {
 
    public boolean accept(File f) {
    	String extension = getExtension(f);
        if (extension != null) {
        	return extension.equalsIgnoreCase("army");
        }
        return f.isDirectory();
    }
 
    public String getDescription() {
        return "Armies";
    }
    
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}