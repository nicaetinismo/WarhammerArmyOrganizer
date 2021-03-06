package main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpinnerNumberModel;

public class Unit extends AbstractUnit{


	public Unit(CodexFile w){
		parent = w;
		zeroStatModifiers();
	}

	public Unit(Unit u) {
		zeroStatModifiers();
		name = new String(u.getName());
		type = new Integer(u.getType());
		cost = new Integer(u.getCost());
		slot = new Integer(u.getSlot());
		originalCost = new Integer(u.originalCost);
		stats = u.getNewStatArray();
		rules = new ArrayList<String>(u.getRules());
		upgrades = copyUpgrades(u.getUpgrades());
		gear = new ArrayList<String>(u.getGear());
		currentUpgrades = copyUpgrades(u.getCurrentUpgrades());
		originalStats = u.getNewOriginalStatArray();
		maxSize = u.maxSize;
		minSize = u.minSize;
		maxInArmy = u.maxInArmy;
		currentSize = new Integer(u.currentSize);
		dedicatedTransport = u.dedicatedTransport;
		parent = u.parent;
		rulesModel = u.copyRulesModel();
		gearModel = u.copyGearModel();
		setPartOfArmy(true);
		numberModel = u.getNumberModel();
		editNumberModel = u.getEditNumberModel();
		statModifiers = u.statModifiers;
		capacity = u.capacity;
		setRace(u.race);
		models = new ArrayList<Model>(u.getModelList());
		unitsToAdd = u.unitsToAdd;
	}

	public ArrayList<Model> getModelList() {
		return models;
	}

	private void zeroStatModifiers(){
		for(String s: Main.statKey) statModifiers.put(s, 0);
		for(String s: Main.statKey) setStatModifiers.put(s, 0);
	}

	private int[] getNewOriginalStatArray() {
		int[] temp = new int[originalStats.length];
		System.arraycopy(temp, 0, originalStats, 0, originalStats.length);
		return temp;
	}

	private ArrayList<Upgrade> copyUpgrades(ArrayList<Upgrade> a) {
		ArrayList<Upgrade> temp = new ArrayList<Upgrade>();
		for(Upgrade u: a) temp.add(new Upgrade(u).setFile(this));
		return temp;
	}

	private DefaultListModel<String> copyRulesModel() {
		DefaultListModel<String> temp = new DefaultListModel<String>();
		String[] temp1 = new String[rulesModel.getSize()];
		rulesModel.copyInto(temp1);
		for(String r: temp1) temp.addElement(r);
		return temp;
	}

	private DefaultListModel<String> copyGearModel() {
		DefaultListModel<String> temp = new DefaultListModel<String>();
		String[] temp1 = new String[gearModel.getSize()];
		gearModel.copyInto(temp1);
		for(String r: temp1) temp.addElement(r);
		return temp;
	}

	public SpinnerNumberModel getNumberModel() {
		return numberModel;
	}

	public void setNumberModel(){
		currentSize = minSize;
		numberModel = new SpinnerNumberModel(minSize, minSize, maxSize, 1);
	}

	public void setNumberModel(int i){
		currentSize = i;
		cost = originalCost * i;
		
		numberModel = new SpinnerNumberModel(i, minSize, maxSize, 1);
	}

	public int getOldStat(String s) {
		for(int i=0;i<(Main.statKey.length-1);i++) if(s.equalsIgnoreCase(Main.statKey[i])) return originalStats[i];
		return 0;
	}

	public void setPartOfArmy(boolean b){
		isPartOfArmy = b;
	}

	private int[] getNewStatArray() {
		int[] temp = new int[10];
		int marker = 0;
		for(int i: stats){
			temp[marker++] = i;
		}
		return temp;
	}

	private ArrayList<Upgrade> getCurrentUpgrades() {
		return currentUpgrades;
	}

	private ArrayList<String> getGear() {
		return gear;
	}

	private ArrayList<Upgrade> getUpgrades() {
		return upgrades;
	}

	private ArrayList<String> getRules() {
		return rules;
	}

	public int getType() {
		return type;
	}

	public void setName(String s) {
		name = s;
	}

	public void setType(int i) {
		type = i;
	}

	public void setStats(int[] i) {
		System.arraycopy(i, 0, stats, 0, i.length);
		originalStats = new int[i.length];
		System.arraycopy(i, 0, originalStats, 0, i.length);
	}

	public void setRules(ArrayList<String> r) {
		rules = r;
	}

	public void setUpgrades(ArrayList<Upgrade> u) {
		upgrades = u;
	}

	public void setGear(ArrayList<String> w) {
		gear = w;
	}

	public void setRace(String s) {
		race = s;
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
			u.setFile(this);
			u.makeEntry(null, x, y, false);
			y++;
		}
		tempScrollPanel.setViewportView(upgradePanel);
		cardName = race + name;
		GUI.addUpgradePane(GUI.cardPanel, tempScrollPanel, cardName);
	}

	public void showUpgrade(Upgrade u){
		if(u.checkBox != null) u.checkBox.setEnabled(true);
		else u.spinner.setEnabled(true);
	}

	private void hideUpgrade(Upgrade u) {
		if(u != null){
			if(u.isActive){
				if(u.checkBox != null) u.checkBox.setSelected(false);
				else u.spinner.setValue(0);
				u.setActive(false);
			}
			if(u.checkBox != null) u.checkBox.setEnabled(false);
			else u.spinner.setEnabled(false);
		}
	}

	public void loadRules(){
		for(String r: rules) rulesModel.addElement(r);
	}

	public void loadGear(){
		for(String w: gear) gearModel.addElement(w);
	}

	public int[] getStats() {
		return stats;
	}

	public DefaultListModel<String> getRulesModel(){
		return rulesModel;
	}

	public String getCardName(){
		return cardName;
	}

	public DefaultListModel<String> getGearModel() {
		return gearModel;
	}

	public String getCost() {
		return String.valueOf(cost);
	}

	public void update() {
		updateUpgrades();
		updateStats();
		updateRules();
		updateGear();
		updateCost();
		updateModels();
	}

	private void updateModels() {
		ArrayList<Integer> toDelete = new ArrayList<Integer>();
		for(int i = 0;i < models.size() - 1;i++){
			if(models.get(i).isEmpty() && !toDelete.contains(i)){
				toDelete.add(i);
				continue;
			}
			for(int j = i+1;j < models.size(); j++){
				if(!toDelete.contains(j) && models.get(i).equals(models.get(j))){
					models.get(i).currentSize += models.get(j).currentSize;
					toDelete.add(j);
				}
			}
		}
		for(int i = toDelete.size() - 1;i >=0;i--){
			models.remove(i);
		}
	}

	private void updateUpgrades() {
		zeroStatModifiers();
		for(Upgrade u: currentUpgrades){
			u.update();
			for(String rule: u.addRuleList) addRule(rule);
			for(String gear: u.addGearList) addGear(gear);
			for(String unitName: u.addUnitList) army.addUnit(parent.getUnit(unitName));
			for(String rule: u.removeRuleList) removeRule(rule);
			for(String enable: u.enableList) showUpgrade(getUpgrade(enable));
			for(String disable: u.disableList) hideUpgrade(getUpgrade(disable));
			for(String gear: u.removeGearList){
				if(gear.contains("/")){
					removeGear(u.chosenGear);
					continue;
				}
				removeGear(gear);
			}
			if(!u.statList.isEmpty()){
				for(String s: u.statList.keySet()) addStatMod(s, u.statList.get(s), false);
			}
			if(!u.setStatList.isEmpty()){
				for(String s: u.setStatList.keySet()) addStatMod(s, u.setStatList.get(s), true);
			}
		}
	}

	private Upgrade getUpgrade(String name) {
		for(Upgrade u: upgrades){
			if(name.equalsIgnoreCase(u.getName())) return u;
		}
		return null;
	}

	private void updateCost() {
//		cost = originalCost * currentSize;
//		if(!currentUpgrades.isEmpty()){
//			for(Upgrade u: currentUpgrades){
//				cost += u.getCost();
//			}
//		}
		cost = 0;
		for(Model m: models) cost += m.getCost();
		if(!isPartOfArmy) GUI.costLabel.setText(getCost());
		else {
			GUI.editCostLbl.setText(getCost());
			army.update();
		}
	}

	private void updateGear() {
		if(!isPartOfArmy) GUI.gearList.setModel(getGearModel());
		else GUI.editGearList.setModel(getGearModel());
	}

	private void updateRules() {
		if(!isPartOfArmy) GUI.rulesList.setModel(getRulesModel());
		else GUI.editRulesList.setModel(getRulesModel());
	}

	private void updateStats() {
		if(!isPartOfArmy){
			int i = 0;
			if(type == 0){
				for(String s: statModifiers.keySet()){
					if(i<8) GUI.statLabelList.get(i).setText(getFinalStat(s, i, stats[i++]));
					else{
						if(getFinalStat(s, i, stats[i]).equalsIgnoreCase("0")) GUI.statLabelList.get(i++).setText("-");
						else GUI.statLabelList.get(i).setText(getFinalStat(s, i, stats[i++]) + "+");
					}
				}
			}
			else if(type == 1){
				for(String s: statModifiers.keySet()){
					if(i >= 3 && i <= 7){
						GUI.vehicleStatLabelList.get(i - 3).setText(getFinalStat(s, i, stats[i++]));
						continue;
					}
					i++;
				}
			}
			else if(type == 2){
				for(String s: statModifiers.keySet()){
					if(i >= 1 && i <= 9){
						GUI.walkerStatLabelList.get(i - 1).setText(getFinalStat(s, i, stats[i++]));
						continue;
					}
					i++;
				}
			}
		}
		else {
			int i = 0;
			if(type == 0){
				for(String s: statModifiers.keySet()){
					if(i<8) GUI.editStatLabelList.get(i).setText(getFinalStat(s, i, stats[i++]));
					else{
						if(getFinalStat(s, i, stats[i]).equalsIgnoreCase("0")) GUI.editStatLabelList.get(i++).setText("-");
						else GUI.editStatLabelList.get(i).setText(getFinalStat(s, i, stats[i++]) + "+");
					}
				}
			}
			else if(type == 1){
				for(String s: statModifiers.keySet()){
					if(i >= 3 && i <= 7){
						GUI.editVehicleStatLabelList.get(i - 3).setText(getFinalStat(s, i, stats[i++]));
						continue;
					}
					i++;
				}
			}
			else if(type == 2){
				for(String s: statModifiers.keySet()){
					if(i >= 1 && i <= 9){
						GUI.editWalkerStatLabelList.get(i - 1).setText(getFinalStat(s, i, stats[i++]));
						continue;
					}
					i++;
				}
			}
		}
	}
	private String getFinalStat(String s, int index, int i) {
		int temp = i;
		int temp2 = setStatModifiers.get(s);
		int temp3 = statModifiers.get(s);
		if(temp2 > 0) temp = temp2;
		if(temp3 > 0) temp += temp3;
		if(index > 7 && temp == 1) temp = 2;
		else if(temp > 10) temp = 10;
		return String.valueOf(temp);
	}

	public String toString(){
		return name;
	}

	public void addGear(String s) {
		if(gear.contains(s)) return;
		gearModel.addElement(s);
		gear.add(s);
	}

	public void addGear(ArrayList<String> s) {
		for(String s1: s) gearModel.addElement(s1);
	}

	public void addRule(ArrayList<String> s) {
		for(String s1: s) rulesModel.addElement(s1);
	}

	public void removeGear(String s) {
		if(gearModel.contains(s)){
			gearModel.removeElement(s);
			gear.remove(s);
		}
	}

	public void removeGear(ArrayList<String> s) {
		for(String s1: s) if(gearModel.contains(s1)) gearModel.removeElement(s1);
	}

	public void removeRule(ArrayList<String> s) {
		for(String s1: s) if(rulesModel.contains(s1)) rulesModel.removeElement(s1);
	}

	public void addRule(String s){
		if(rules.contains(s)) return;
		rulesModel.addElement(s);
		rules.add(s);
	}

	public void removeRule(String s){
		rulesModel.removeElement(s);
		rules.remove(s);
	}

	private void addStatMod(String s, int i, boolean set) {
		if(set){
			int temp = setStatModifiers.get(s);
			int marker = 0;
			for(String s1: setStatModifiers.keySet()){
				if(s1.equalsIgnoreCase(s)) break;
				marker++;
			}
			if(temp == 0) setStatModifiers.put(s, i);
			else if(marker > 7 && i < temp) setStatModifiers.put(s, i);
			else if(i > temp) setStatModifiers.put(s, i);
		}
		else{
			int temp = statModifiers.get(s);
			temp += i;
			statModifiers.put(s, temp);
		}
	}

	private void removeStatMod(String s, int i) {
		int temp = statModifiers.get(s);
		temp -= i;
		statModifiers.put(s, temp);
	}

	public String getRace() {
		return race;
	}

	public void loadSetUpgrades() {
		JScrollPane tempScrollPanel = new JScrollPane();
		tempScrollPanel.setBorder(null);
		tempScrollPanel.setOpaque(false);
		tempScrollPanel.getViewport().setOpaque(false);
		tempScrollPanel.getViewport().setBorder(null);
		JPanel tempPanel = new JPanel();
		tempPanel.setOpaque(false);
		tempPanel.setBorder(null);
		GridBagLayout layout = new GridBagLayout();
		layout.preferredLayoutSize(tempPanel);
		tempPanel.setLayout(new GridBagLayout());
		int x = 0;
		int y = 0;
		ArrayList<Upgrade> tempList = new ArrayList<Upgrade>();
		for(Upgrade u: upgrades){
			Upgrade newUpgrade = new Upgrade(u).setFile(this);
			tempList.add(newUpgrade);
		}
		for(Upgrade u: tempList){
			u.makeEntry(tempPanel, x, y, true);
			y++;
			if(u.isActive){
				if(u.checkBox != null) u.checkBox.setSelected(true);
				else if(u.spinner != null) ((SpinnerNumberModel) u.spinner.getModel()).setValue(u.numberInSquad);
				u.setActive("", true);
			}
			else {
				if(u.checkBox != null) u.checkBox.setSelected(false);
				else if(u.spinner != null) ((SpinnerNumberModel) u.spinner.getModel()).setValue(u.numberInSquad);
				u.setActive("", false);
			}
		}
		upgrades = tempList;
		tempScrollPanel.setViewportView(tempPanel);
		String tempName = race + name;
		if(!GUI.cardNames.contains(tempName)) cardName1 = tempName;
		else{
			for(String s: GUI.cardNames){
				if(s.equalsIgnoreCase(tempName)) tempName = tempName + 0;
			}
			cardName1 = tempName;
		}
		GUI.addUpgradePane(GUI.editUpgradePane, tempScrollPanel, cardName1);
		update();
	}

	public void addUpgrade(Upgrade u) {
		currentUpgrades.add(u);
	}

	@SuppressWarnings("unchecked")
	public void removeUpgrade(Upgrade u) {
		for(String rule: u.addRuleList) removeRule(rule);
		for(String gear: u.addGearList) removeGear(gear);
		for(String unitName: u.addUnitList) army.removeUnit(parent.getUnit(unitName));
		for(String rule: u.removeRuleList) addRule(rule);
		for(String enable: u.enableList) hideUpgrade(getUpgrade(enable));
		for(String disable: u.disableList) showUpgrade(getUpgrade(disable));
		for(String gear: u.removeGearList){
			if(gear.contains("/")){
				addGear(u.chosenGear);
				continue;
			}
			addGear(gear);
		}
		if(!u.statList.isEmpty()){
			for(String s: u.statList.keySet()) removeStatMod(s, u.statList.get(s));
		}
		for(Upgrade u1: (ArrayList<Upgrade>) currentUpgrades.clone()){
			if(u1.getName().equalsIgnoreCase(u.getName())) currentUpgrades.remove(u1);
		}
	}

	public String getCardName1() {
		return cardName1;
	}

	public boolean getIsPartOfArmy() {
		return isPartOfArmy;
	}

	public void setCurrentSize(int i) {
		currentSize = i;
		update();
	}

	public void updateStartingCost(){
		for(int i=1;i<currentSize;i++){
			cost += originalCost;
		}
	}

	public void setArmy(Army a){
		army = a;
	}

	public SpinnerNumberModel getEditNumberModel() {
		editNumberModel = new SpinnerNumberModel(currentSize, minSize, maxSize, 1);
		return editNumberModel;
	}

	public void setCapacity(String s) {
		capacity = s;
		String r = "Transport";
		rulesModel.addElement(r);
		rules.add(r);
	}

	public void setVehicleTypes(String s) {
		for(int i=0;i<s.length();i++){
			if(s.charAt(i) == ','){
				String r = s.substring(0, i);
				rulesModel.addElement(r);
				rules.add(r);
				s = new StringBuilder(s).delete(0, i+1).toString();
				i = -1;
			}
			if(s.charAt(i) == '|'){
				String r = s.substring(0, i);
				rulesModel.addElement(r);
				rules.add(r);
				return;
			}
		}
	}

	public void updateChangingUpgrades(int i) {
		for(Upgrade u: upgrades){
			if(u.isActive) u.updateCostBasedOnSize();
		}
	}

	public void removeAllUpgrades() {
		ArrayList<Upgrade> temp = new ArrayList<Upgrade>();
		for(Upgrade u: currentUpgrades){
			temp.add(u);
		}
		for(Upgrade u: temp){
			u.setActive(false);
		}
	}

	public void setDisplay(boolean b){
		display = b;
	}

	public int getSlot(){
		return slot;
	}

	public void setSlot(int i) {
		slot = i;
	}
	
	public ArrayList<Byte> showModels(DefaultListModel<AbstractUnit> model, int currentIndex) {
		ArrayList<Byte> temp = new ArrayList<Byte>();
		for(Model m: models){
			temp.add((byte) 0);
			model.add(currentIndex + 1, m);
		}
		return temp;
	}
	
	public void addModel(Model m){
		models.add(m);
		update();
	}
	
	public void addModels(ArrayList<Model> m) {
		models.addAll(m);
		updateModels();
	}

	protected ArrayList<Unit> unitsToAdd = new ArrayList<Unit>();
	protected boolean addsUnit = false;
	protected boolean isPartOfArmy = false;
	protected SpinnerNumberModel numberModel;
	protected SpinnerNumberModel editNumberModel;
	protected Army army;
	protected String capacity;
	protected int maxInArmy;
	protected int type;
	protected int slot;
	protected Unit dedicatedTransport;
	protected String cardName;
	protected String cardName1;
	public int originalCost;
	public int[] stats = new int[10];
	public int[] originalStats = new int[10];
	public int[] vehicleStats = new int[4];
	public int[] originalVehicleStats = new int[4];
	protected ArrayList<Upgrade> currentUpgrades = new ArrayList<Upgrade>();
	public ArrayList<Upgrade> upgrades = new ArrayList<Upgrade>();
	public ArrayList<String> rules = new ArrayList<String>();
	public LinkedHashMap<String, Integer> statModifiers = new LinkedHashMap<String, Integer>();
	public LinkedHashMap<String, Integer> setStatModifiers = new LinkedHashMap<String, Integer>();
	protected DefaultListModel<String> rulesModel = new DefaultListModel<String>();
	protected DefaultListModel<String> gearModel = new DefaultListModel<String>();
	protected ArrayList<Model> models = new ArrayList<Model>();
	protected ArrayList<String> gear = new ArrayList<String>();
	protected CodexFile parent;
	protected JPanel upgradePanel;
	protected boolean display = true;
}
