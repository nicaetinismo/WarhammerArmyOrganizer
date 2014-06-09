package main;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class Upgrade {

	public Upgrade(String s, AbstractUnit a) {
		name = s;
		if(a instanceof Model) model = (Model) a;
		else unit = (Unit) a;
	}

	public Upgrade(Upgrade u){
		name = new String(u.getName());
		cost = new Integer(u.cost);
		maxInSquad = new Integer(u.maxInSquad);
		enabled = new Boolean(u.enabled);
		isActive = new Boolean(u.isActive);
		numberInSquad = new Integer(u.numberInSquad);
		linkedUpgrades = u.linkedUpgrades;
		removeRuleList = u.removeRuleList;
		removeGearList = u.removeGearList;
		addGearList = u.addGearList;
		addRuleList = u.addRuleList;
		addUnitList = u.addUnitList;
		disableList = u.disableList;
		enableList = u.enableList;
		statList = u.statList;
		setStatList = u.setStatList;
		requiredNumber = u.requiredNumber;
		model = u.model;
	}
	
	public void setCost(int i){
		cost = i;
	}
	
	public void setMaxInSquad(int i){
		maxInSquad = i;
	}

	public void makeEntry(JPanel p, int x, int y, boolean edit){
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = x;
		c.gridy = y;
		if(!(maxInSquad == -1) && !(maxInSquad == 1)){
			if(!edit) numberInSquad = 0;
			int i = Integer.valueOf(maxInSquad);
			if(!edit) spinner = new JSpinner(new SpinnerNumberModel(0, 0, i, 1));
			else spinner = new JSpinner(new SpinnerNumberModel(numberInSquad, 0, i, 1));
			spinner.addChangeListener(new SpinnerListener(spinner, this));
			spinner.setEnabled(enabled);
			spinner.setPreferredSize(new Dimension(54, 28));
			JLabel label = new JLabel(name);
			label.setForeground(GUI.fontColor);
			c.weightx = 0.2;
			if(p == null) unit.upgradePanel.add(spinner, c);
			else p.add(spinner, c);
			c.gridx = x+1;
			c.weightx = 0.8;
			if(p == null) unit.upgradePanel.add(label, c);
			else p.add(label, c);
		}
		else {
			c.weightx = 1.0;
			checkBox = new JCheckBox();
			checkBox.setOpaque(false);
			checkBox.addActionListener(new Listener(this, checkBox));
			checkBox.setEnabled(enabled);
			c.weightx = 0.1;
			if(p == null) unit.upgradePanel.add(checkBox, c);
			else p.add(checkBox, c);
			JLabel temp = new JLabel("<html>" + name + "</html>");
			temp.setForeground(GUI.fontColor);
			temp.setOpaque(false);
			c.gridx = x+1;
			c.weightx = 0.8;
			if(p == null) unit.upgradePanel.add(temp, c);
			else p.add(temp, c);
		}
	}

	public void setNumberInSquad(int i){
		boolean isActivating = false;
		if(numberInSquad == 0 && i > 0) isActivating = true;
		numberInSquad = i;
		if(isActivating) setActive(true);
		if(numberInSquad == 0) setActive(false);
	}

	public Upgrade setFile(Unit w){
		unit = w;
		return this;
	}

	public void updateCostBasedOnSize(){
		if(maxInSquad == -1){
			numberInSquad = unit.currentSize;
		}
		else if(linkedUpgrades.isEmpty() && unit.currentSize < numberInSquad){
			if(spinner != null){
				((SpinnerNumberModel) spinner.getModel()).setValue(new Integer(unit.currentSize));
			}
		}
	}

	protected void setActive(boolean b){
		isActive = b;
		if(b) unit.addUpgrade(this);
		else unit.removeUpgrade(this);
		unit.update();
	}
	
	protected boolean isActive(){
		return isActive;
	}

	public String getName(){
		return name;
	}

	public int getCost(){
		return cost * numberInSquad;
	}

	public String toString(){
		return name + Main.lb + "cost: " + cost + Main.lb + "adds: " + addRuleList + ", " + addGearList + ", " + addUnitList;
	}

	public void update(){
		if(spinner != null) ((SpinnerNumberModel) spinner.getModel()).setMaximum(getMax());
	}

	public int getMax(){
		int temp = 0;
		if(maxInSquad == -1) return unit.currentSize;
		else if(linkedUpgrades.isEmpty()){
			temp = Integer.valueOf(maxInSquad);
			if(temp <= unit.currentSize){
				if(requiredNumber > 1){
					if(unit.currentSize/requiredNumber < temp) return (int) (unit.currentSize/requiredNumber);
					else return temp;
				}
				else return temp;
			}
			else return (int) ( unit.currentSize/requiredNumber);
		}
		else {
			if(Integer.valueOf(maxInSquad) <= unit.currentSize) temp += Integer.valueOf(maxInSquad);
			else temp += unit.currentSize;
		}
		for(String s: linkedUpgrades){
			for(Upgrade u: unit.currentUpgrades){
				if(s.equalsIgnoreCase(u.getName())) temp -= u.numberInSquad;
			}
		}
		if(unit.currentSize/requiredNumber < temp) return (int) (unit.currentSize/requiredNumber);
		else return temp;
	}
	
	public void setActive(String b, boolean c) {
		isActive = c;
	}

	protected ArrayList<String> linkedUpgrades = new ArrayList<String>();
	protected ArrayList<String> removeRuleList = new ArrayList<String>();
	protected ArrayList<String> removeGearList = new ArrayList<String>();
	protected ArrayList<String> addGearList = new ArrayList<String>();
	protected ArrayList<String> addRuleList = new ArrayList<String>();
	protected ArrayList<String> addUnitList = new ArrayList<String>();
	protected ArrayList<String> disableList = new ArrayList<String>();
	protected ArrayList<String> enableList = new ArrayList<String>();
	protected LinkedHashMap<String, Integer> statList = new LinkedHashMap<String, Integer>();
	protected LinkedHashMap<String, Integer> setStatList = new LinkedHashMap<String, Integer>();
	protected JSpinner spinner;
	protected int maxInSquad = 1;
	protected int numberInSquad = 1;
	protected double requiredNumber = 1;
	protected String chosenGear;
	private String name;
	private int cost = 0;
	protected Unit unit;
	protected Model model;
	protected JDialog dialog;
	protected JCheckBox checkBox;
	protected boolean isActive = false;
	protected boolean enabled = true;
}
