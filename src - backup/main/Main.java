package main;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;

@SuppressWarnings("rawtypes")
public class Main implements Comparator{


	public void load() throws IOException {
		if(!debug){
			for(String s: codeciesToLoad){
				if(!new File(codexPath + s + ".codex").exists()){
					downloadCodex(s);
				}
			}
			
			File codexDir = new File(codexPath);
			File[] codecies = codexDir.listFiles();
			for(File f: codecies){
				if(f.getName().endsWith("codex")) loadArmy(f.getCanonicalPath());
			}
		}
		else {
			File codexDir = new File(codexPath);
			File[] codecies = codexDir.listFiles();
			for(File f: codecies){
				if(f.getName().endsWith("codex")) loadArmy(f.getCanonicalPath());
			}
		}
	}

	private static void downloadCodex(String s) {
		String s1 = "https://raw.github.com/nicaetinismo/Codex/master/" + s + ".codex";
		String temp = codexPath + s + ".codex";
		
		try {
			BufferedReader input =  new BufferedReader(new InputStreamReader(new URL(s1).openConnection().getInputStream()));
			String line = "";
			BufferedWriter output = new BufferedWriter(new FileWriter(temp, false));
			
			while ((line = input.readLine()) != null){
				output.write(line + lb);
			}
			input.close();
			output.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadArmy(File f){
		loadedArmy = true;
		currentArmy = null;
		GUI.saveOption.setEnabled(true);
		GUI.saveAsOption.setEnabled(true);
		try {
			currentArmy = ArmyFileHandler.loadArmy(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String s: armies.keySet()){
			if(s.equalsIgnoreCase(currentArmy.race)){
				GUI.raceCombo.setSelectedItem(s);
				break;
			}
		}
		GUI.showScreen("create");
	}

	@SuppressWarnings("unchecked")
	private void loadArmy(String s) {
		CodexFile w = new CodexFile(s);
		ArrayList<Unit> temp = w.getUnitList();
		Collections.sort(temp, this);
		armies.put(w.getRace(), w.getUnitList());
	}

	public static Unit getUnit(String s, String s1){
				for(Unit u: armies.get(s1)){
					if(u.getName().equalsIgnoreCase(s)){
						Unit u1 = new Unit(u);
						return u1;
					}
		}
		return null;
	}

	public void run(){
		gui = new GUI();
		loadProps();
		arrangeLoadedModels();
		SplashWindow.doneLoading = true;
		gui.setVisible(true);
	}

	private void loadProps(){
		for(ArrayList<Unit> a: armies.values()){
			for(Unit w: a){
				w.loadGear();
				w.loadUpgrades();
				w.loadRules();
			}
		}
	}

	private void arrangeLoadedModels() {
		for(Entry<String, ArrayList<Unit>> e: armies.entrySet()){
			DefaultListModel<Unit> temp = new DefaultListModel<Unit>();
			for(Unit f: e.getValue()){
				if(f.display) temp.addElement(f);
			}
			raceListModels.put(e.getKey(), temp);
		}
	}

	public int compare(Object o1, Object o2) {
		if(o1 instanceof Unit) return ((Unit) o1).toString().compareTo(((Unit) o2).toString());
		return ((String) o1).compareTo((String) o2);
	}

	private void setPaths(){
		if(debug){
			if(isMac()){
				savePath = "/Library/Application Support/Warhammer Army Organizer/Saved Armies/";
				codexPath = "/Users/nicaetinismo/Documents/workspace/Warhammer Program/bin/codecies/";
			}
			else{
				savePath = System.getProperty("user.home") + "\\AppData\\Roaming\\Warhammer Army Organizer\\Saved Armies\\";
				codexPath = System.getProperty("user.home") + "\\Dropbox\\Warhammer Army Organizer\\src\\Codecies\\";
			}
		}
		else{
			if(isMac()){
				savePath = "/Library/Application Support/Warhammer Army Organizer/Saved Armies/";
				codexPath = "/Library/Application Support/Warhammer Army Organizer/Codecies/";
			}
			else if(isWindows()){
				savePath = System.getProperty("user.home") + "\\AppData\\Local\\Warhammer Army Organizer\\Saved Armies\\";
				codexPath = System.getProperty("user.home") + "\\AppData\\Local\\Warhammer Army Organizer\\Codecies\\";
			}
		}
		new File(savePath).mkdirs();
		new File(codexPath).mkdirs();
	}

	public Main(int i, boolean b){
		
		OS = i;
		debug = b;
		fileChooser.setAcceptAllFileFilterUsed(false);
		setPaths();
		fileChooser.setCurrentDirectory(new File(savePath));
		fileChooser.setFileFilter(new ArmyFileFilter());

		try {
			load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		instance = this;
		run();
	}

	public static void saveArmy(File f, boolean b) {
		try {
			ArmyFileHandler.saveArmy(currentArmy, f, b);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Army getCurrentArmy() {
		return currentArmy;
	}

	public static void logln(Object o){
		System.out.println(o);
	}

	public static void log(Object o){
		System.out.print(o);
	}

	public static boolean isWindows() {
		return OS == 0;
	}

	public static boolean isMac() {
		return OS == 1;
	}

	public static boolean isUnix() {
		return OS ==2;
	}

	public static final String[] statKey = {"ws", "bs", "s", "t", "w", "i", "a", "ld", "sv", "isv"};
	public static final String[] codeciesToLoad = {"necrons"};
	public static HashMap<String, ArrayList<Unit>> armies = new HashMap<String, ArrayList<Unit>>();
	public static HashMap<String, DefaultListModel<Unit>> raceListModels = new HashMap<String, DefaultListModel<Unit>>();
	public static ArrayList<Unit> units = new ArrayList<Unit>();
	public static String savePath, codexPath;
	public static final Dimension BUTTON_DIMENSION = new Dimension(100, 50);
	public static Army currentArmy = null;
	public static GUI gui;
	public static JFileChooser fileChooser = new JFileChooser();
	public static boolean loadedArmy;
	public static int pointsValue;
	public static int OS;
	public static final String version = "ALPHA 1.0";
	public static boolean debug;
	public static final String lb = System.getProperty("line.separator");
	public static Main instance;
}
