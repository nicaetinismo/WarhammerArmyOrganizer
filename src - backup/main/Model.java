package main;

import java.util.ArrayList;
import java.util.TreeMap;

public class Model extends AbstractUnit {
	
	public ArrayList<Upgrade> upgrades = new ArrayList<Upgrade>();
	protected TreeMap<String, Integer> gearMap = new TreeMap<String, Integer>();

	public Model(String name, String race){
		this.name = name;
		this.race = race;
//		cost = 13;
//		currentSize = 5;
//		gearMap.put("test", 3);
//		gearMap.put("another test", 3);
//		gearMap.put("power test", 3);
//		gearMap.put("plasma test", 3);
//		gearMap.put("one last test", 3);
	}
	
	public void setCost(int i){
		cost = i;
	}
	
	public void setSize(int i){
		currentSize = i;
	}
	
	public String toString(){
		if(gearMap.isEmpty()) return getCost() + "pts   " + currentSize + "x " + name; 
		return getCost() + "pts   " + currentSize + "x " + name + "       " + gearMap.keySet();
	}

	public int getCost() {
		return cost * currentSize;
	}
	
	public boolean equals(Model m){
		return gearMap.equals(m.gearMap) && name.equalsIgnoreCase(m.name) && race.equalsIgnoreCase(m.race);
	}

	public boolean isEmpty() {
		return currentSize == 0;
	}
}
