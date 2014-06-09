package main;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

public class Army {

	public ArrayList<Unit> unitList = new ArrayList<Unit>();
	public int currentCost = 0;
	public String race;
	public String fileName;


	public Army(String s){
		race = s;
	}
	
	public Army addUnit(Unit u){
		unitList.add(u);
		u.setArmy(this);
		u.loadSetUpgrades();
		GUI.armyList.setModel(updateArmyModel());
		return this;
	}
	
	public void updatePointsLabel(){

	}
	
	public void update(){
		updateCost();
		updatePointsLabel();
	}

	private void updateCost() {
		currentCost = 0;
		for(Unit u: unitList){
			currentCost += u.cost;
		}
	}

	public void removeUnit(Unit u) {
		u.removeAllUpgrades();
		removeFromList(u);
		update();
		GUI.armyList.setModel(updateArmyModel());
	}
	
	public void removeUnit(String s){
		for(Unit u: unitList){
			if(u.name.equalsIgnoreCase(s)) removeUnit(u);
		}
	}

	private void removeFromList(Unit u) {
		ArrayList<Unit> temp = new ArrayList<Unit>();
		for(Unit u1: unitList){
			temp.add(u1);
		}
		for(Unit u1: temp){
			if(u1.equals(u)){
				temp.remove(u1);
				break;
			}
		}
		unitList = temp;
	}

	public boolean isEmpty() {
		return unitList.isEmpty();
	}

	public ListModel<AbstractUnit> updateArmyModel(){
		DefaultListModel<AbstractUnit> temp = new DefaultListModel<AbstractUnit>();
		for(Unit u: unitList) temp.addElement(u);
		return temp;
	}
}
