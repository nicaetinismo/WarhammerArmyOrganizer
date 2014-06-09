package main;

import java.util.ArrayList;

import javax.swing.JPanel;

public abstract class AbstractUnit {
	
	public String getName(){
		return name;
	}

	public void setName(String s) {
		name = s;
	}
	
	public void setType(int i) {
		type = i;
	}

	public void setGear(ArrayList<String> readGear) {
		gear = readGear;
	}

	public void setRules(ArrayList<String> readRules) {
		rules = readRules;
	}

	public void setRace(String s) {
		race = s;
	}

	public void setCapacity(String s) {
		capacity = s;
	}

	public void setDisplay(boolean b) {
		display = b;
	}

	public void setSlot(int i) {
		slot = i;
	}
	
	public void defineModels(ArrayList<Model> readModels){
		models.addAll(readModels);
		possibleModels.addAll(readModels);
	}

	public void addModels(ArrayList<Model> readModels) {
		models.addAll(readModels);
	}

	public void setUpgrades(ArrayList<Upgrade> readUpgrades) {
		upgrades.addAll(readUpgrades);
	}

	public void setStats(int[] readStats) {
		System.arraycopy(readStats, 0, stats, 0, readStats.length);
	}
	
	public String getCardName(){
		return cardName;
	}
	
	public abstract void loadUpgrades();

	protected String name;
	protected String race;
	protected int currentSize;
	protected int maxSize;
	protected int minSize;
	public int cost;
	public int[] stats = new int[10];
	public ArrayList<Upgrade> upgrades = new ArrayList<Upgrade>();
	protected ArrayList<Model> models = new ArrayList<Model>();
	protected ArrayList<Model> possibleModels = new ArrayList<Model>();
	protected int type = 0;
	protected int slot;
	protected String capacity;
	protected boolean display = true;
	protected ArrayList<String> gear = new ArrayList<String>();
	public ArrayList<String> rules = new ArrayList<String>();
	public JPanel upgradePanel;
	protected String cardName;
}
