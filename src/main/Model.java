package main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

public class Model extends AbstractUnit {
	
	public ArrayList<Upgrade> upgrades = new ArrayList<Upgrade>();
	protected TreeMap<String, Integer> gearMap = new TreeMap<String, Integer>();
	protected JButton plusButton, minusButton;
	public Unit parent;
	private JLabel numberLabel;
	protected ArrayList<Model> excludeList = new ArrayList<Model>();

	public Model(String name, String race, Unit parent){
		this.parent = parent;
		this.name = name;
		this.race = race;
	}
	
	public Model(Model m){
		name = m.name;
		race = m.race;
		cost = m.cost;
		currentSize = minSize;
		parent = m.parent;
		type = m.type;
		excludeList.addAll(m.excludeList);
		System.arraycopy(m.stats, 0, stats, 0, 10);
	}
	
	public void setCost(int i){
		cost = i;
	}
	
	public void setSize(int i){
		currentSize = i;
		if(numberLabel != null) numberLabel.setText(currentSize + "x " + name);
	}
	
	public String toString(){
		if(gearMap.isEmpty()) return getCost() + "pts   " + currentSize + "x " + name; 
		return getCost() + "pts   " + currentSize + "x " + name + "       " + gearMap.keySet();
	}

	public int getCost() {
		return cost * currentSize;
	}
	
	public boolean equals(Object o){
		if(!(o instanceof Model)) return false;
		return gearMap.equals(((Model) o).gearMap) && 
				name.equalsIgnoreCase(((Model) o).name) && 
				race.equalsIgnoreCase(((Model) o).race) &&
				currentSize == ((Model) o).currentSize;
	}

	public boolean isEmpty() {
		return currentSize == 0;
	}
	
	public void setButtons(JButton plus, JButton minus, JLabel label){
		plusButton = plus;
		minusButton = minus;
		numberLabel = label;
	}

	public int[] getStats() {
		return stats;
	}

	public Unit getParent() {
		return parent;
	}

	public int getType() {
		return type;
	}

	public ListModel<String> getRulesModel() {
		DefaultListModel<String> tempModel = new DefaultListModel<String>();
		for(String s: rules)
			tempModel.addElement(s);
		return tempModel;
	}

	public ListModel<String> getGearModel() {
		DefaultListModel<String> tempModel = new DefaultListModel<String>();
		for(String s: gear)
			tempModel.addElement(s);
		return tempModel;
	}

	public Model copyModel(Model m) {
		name = m.name;
		race = m.race;
		cost = m.cost;
		currentSize = minSize;
		parent = m.parent;
		type = m.type;
		System.arraycopy(m.stats, 0, stats, 0, 10);
		return this;
	}

	public void addExclude(ArrayList<Model> list) {
		excludeList.addAll(list);
	}
	
	public void loadUpgrades() {
		JScrollPane tempScrollPanel = new JScrollPane();
		tempScrollPanel.setViewportBorder(null);
		tempScrollPanel.setBorder(null);
		tempScrollPanel.getViewport().setOpaque(false);
		tempScrollPanel.setOpaque(false);
		upgradePanel = new JPanel();
		upgradePanel.setBorder(null);
		upgradePanel.setOpaque(false);
		upgradePanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		int x = 0;
		int y = 0;
		for(Upgrade u: upgrades){
			u.setFile(parent);
			u.makeEntry(null, x, y, false);
			y++;
		}
		tempScrollPanel.setViewportView(upgradePanel);
		cardName = race + ":" + parent.name + ":" + name;
		GUI.addUpgradePane(GUI.cardPanel, tempScrollPanel, cardName);
	}
}
