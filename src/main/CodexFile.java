package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JScrollPane;


public class CodexFile {

	private String path, name, race;
	public int modelType, pointsCost;
	private int layer;
	private JScrollPane upgradePane;
	public static StringBuilder sb = new StringBuilder();
	private ArrayList<Unit> unitList = new ArrayList<Unit>();
	public ArrayList<String> gearList = new ArrayList<String>();
	public ArrayList<String> rulesList = new ArrayList<String>();
	private static final String[] slots = {"hq", "troops", "elites", "fast attack", "heavy support"};
	private static final String[] types = {"infantry", "vehicle", "walker"};


	public CodexFile(String s){
		path = s;
		try {
			load();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Unit> getUnitList(){
		return unitList;
	}

	public String getName(){
		return name;
	}

	public String toString(){
		return name;
	}

	public void load() throws IOException, FileNotFoundException{
		BufferedReader input;
		try{
			input = new BufferedReader(new FileReader(new File(path)));
			try{
				String line = null;
				Unit tempUnit = null;
				Model currentModel = null;
				boolean isUnit = false;
				boolean isModel = false;
				boolean isFirst = true;
				while ((line = input.readLine()) != null){
					String temp = line.trim();
					if(temp.equalsIgnoreCase("")) continue;
					if(temp.charAt(0) == '!'){
						race = temp.substring(1, temp.length());
						continue;
					}
					if(temp.charAt(0) == '-'){
						tempUnit = new Unit(this);
						tempUnit.setName(temp.substring(1).trim());
						unitList.add(tempUnit);
						isUnit = true;
						isModel = false;
						continue;
					}
					if(temp.charAt(0) =='/'){
						isUnit = false;
						isModel = false;
						continue;
					}
					if(temp.charAt(0) =='@'){
						if(temp.charAt(1) == '@'){
							Model previousModel = getPreviousModel(temp.substring(2).trim());
							if(previousModel != null){
								currentModel = tempUnit.getModel(temp.substring(2).trim(), false).copyModel(previousModel);
								continue;
							}
						}
						else currentModel = tempUnit.getModel(temp.substring(1).trim(), false);
						isModel = true;
						continue;
					}
					if(isUnit){
						for(int i=0;i<temp.length();i++){
							if(temp.charAt(i) == ':'){
								int lineType = getLineType(temp.substring(0, i).trim());
								String restOfLine = temp.substring(i+1, temp.length()).trim();
								if(!isModel) tempUnit = (Unit) readLine(restOfLine, lineType, tempUnit);
								else currentModel = (Model) readLine(restOfLine, lineType, currentModel);
							}
						}
					}
					else {
						if(isFirst){
							for(int i=0;i<temp.length();i++){
								if(temp.length() == 0) break;
								if(temp.charAt(i) == ','){
									String gearName = temp.substring(0, i).trim();
									gearList.add(gearName);
									if(indexOf(',', temp)+1 < temp.length()) temp = new StringBuilder(temp).delete(0, indexOf(',', temp)+1).toString();
									else break;
									i = -1;
								}
							}
							isFirst = false;
						}
						else {
							for(int i=0;i<temp.length();i++){
								if(temp.length() == 0) break;
								if(temp.charAt(i) == ','){
									String ruleName = temp.substring(0, i).trim();
									rulesList.add(ruleName);
									if(indexOf(',', temp)+1 < temp.length()) temp = new StringBuilder(temp).delete(0, indexOf(',', temp)+1).toString();
									else break;
									i = -1;
								}
							}
						}
					}
				}
			}
			finally{
				if(!Main.debug) Main.logln("loaded Codex: " + race);
				input.close();
			}
		}
		catch(FileNotFoundException e){
			throw e;
		}
		for(Unit u: unitList){
			u.setRace(race);
			u.setNumberModel();
			u.updateStartingCost();
		}
	}

	private Model getPreviousModel(String name) {
		for(Unit u: unitList)
			for(Model m: u.possibleModels)
				if(m.name.equalsIgnoreCase(name))
					return new Model(m);
		return null;
	}

	private int indexOf(char c, String s) {
		int i = 0;
		for (int j = 0; j < s.length(); j++) {
			if (i != 0) i = 0;
			else if (s.charAt(j) == '#') i = 1;
			else if (s.charAt(j) == c) return j;
		}
		return -1;
	}

	private AbstractUnit readLine(String s, int i, AbstractUnit unit) {
		//		Main.logln(s + "  " + unit);
		if(i == 0) unit.setName(s);
		if(i == 1) unit.setType(getTypeValue(s));
		if(i == 2) unit.setStats(readStats(s, unit));
		if(i == 3) unit.setGear(readGear(s));
		if(i == 4) unit.setRules(readRules(s));
		if(i == 5) unit.setUpgrades(readUpgrades(s, unit));
		if(i == 6) unit.setRace(s);
		if(i == 7) unit.setCapacity(s);
		if(i == 8) unit.setDisplay(Boolean.valueOf(s));
		if(i == 9) unit.setSlot(getSlotValue(s));
		if(i == 10) unit.defineModels(readModels(s, (Unit) unit));
		if(i == 11) ((Model) unit).addExclude(readExclude(s, (Model) unit));
		return unit;
	}

	private ArrayList<Model> readExclude(String s, Model m) {
		ArrayList<Model> tempList = new ArrayList<Model>();
		for(int i=0;i<s.length();i++){
			if(s.charAt(i) == ','){
				tempList.add(m.parent.getModel(s.substring(0, i).trim(), false));
				s = sb.delete(0, sb.length()).append(s).delete(0, i+1).toString().trim();
				i = 0;
			}
		}
		tempList.add(m.parent.getModel(s.substring(0).trim(), false));
		Main.logln(tempList);
		return tempList;
	}

	private int getSlotValue(String s) {
		for(int i=0;i<slots.length;i++)
			if(slots[i].equalsIgnoreCase(s.trim()))
				return i;
		return -1;
	}

	private int getTypeValue(String s) {
		for(int i=0;i<types.length;i++)
			if(types[i].equalsIgnoreCase(s.trim()))
				return i;
		return -1;
	}

	private ArrayList<Model> readModels(String s, Unit unit) {
		ArrayList<Model> models = new ArrayList<Model>();
		ArrayList<String> temp = new ArrayList<String>();
		for(int i=0;i<s.length();i++){
			if(s.charAt(i) == '('){
				temp.add(s.substring(i + 1, indexOf(')', s)).trim());
				s = sb.delete(0, sb.length()).append(s).delete(0, indexOf(')', s)+1).toString().trim();
				i = -1;
			}
		}
		for(String s1: temp){
			String s2 = s1.substring(0, indexOf('|', s1)).trim();
			s1 = sb.delete(0, sb.length()).append(s1).delete(0, indexOf('|', s1)+1).toString().trim();
			Model tempModel = new Model(s2, race, unit);
			for(int i=0;i<s1.length();i++){
				if(s1.charAt(i) == ':'){
					String action = s1.substring(0, i).trim();
					String toDo = "";
					try{ toDo = s1.substring(i+1, indexOf('|', s1)).trim(); }
					catch(StringIndexOutOfBoundsException e){ toDo = s1.substring(i+1).trim(); }
					if(action.equalsIgnoreCase("points")) tempModel.setCost(Integer.valueOf(toDo));
					else if(action.equalsIgnoreCase("max")) tempModel.maxSize = Integer.valueOf(toDo);
					else if(action.equalsIgnoreCase("min")){
						tempModel.minSize = Integer.valueOf(toDo);
						tempModel.setSize(Integer.valueOf(toDo));
					}
					else System.out.println("Invalid model action, please check the " + unit.name + " entry in Codex: " + race);
					if(indexOf('|', s1) != -1){
						s1 = sb.delete(0, sb.length()).append(s1).delete(0, indexOf('|', s1)+1).toString().trim();
						i = 0;
					}
					else break;
				}
			}
			models.add(tempModel);
		}
		return models;
	}

	private ArrayList<Upgrade> readUpgrades(String s, AbstractUnit unit) {
		ArrayList<Upgrade> upgrades = new ArrayList<Upgrade>();
		for(int i=0;i<s.length();i++){
			if(s.charAt(i) == '('){
				Upgrade newUpgrade;
				i++;
				String temp = s.substring(i, indexOf(')', s));
				int marker = indexOf('|', temp);
				String upgradeName = temp.substring(0, marker);
				newUpgrade = new Upgrade(upgradeName, unit);
				temp = sb.delete(0, sb.length()).append(temp).delete(0, marker+1).toString().trim();
				marker = 0;
				for(int i1=0;i1<temp.length();i1++){
					if(temp.length() == 0) break;
					if(temp.charAt(i1) == ':'){
						String action = temp.substring(0, i1).trim();
						String allAdd = "";
						try{ allAdd = temp.substring(i1+1, indexOf('|', temp)).trim(); }
						catch(StringIndexOutOfBoundsException e){ allAdd = temp.substring(i1+1).trim(); }
						if(action.equalsIgnoreCase("points")) newUpgrade.setCost(Integer.valueOf(allAdd));
						else if(action.equalsIgnoreCase("enabled")) newUpgrade.enabled = Boolean.valueOf(allAdd);
						else if(action.equalsIgnoreCase("max")){
							if(allAdd.contains("<")){
								newUpgrade.setMaxInSquad(Integer.valueOf(allAdd.substring(0, indexOf('<', allAdd)).trim()));
								newUpgrade.requiredNumber = Double.valueOf(allAdd.substring(indexOf('<', allAdd)+1, indexOf('>', allAdd)).trim());
							}
							else newUpgrade.setMaxInSquad(Integer.valueOf(allAdd));
						}
						else if(action.equalsIgnoreCase("add") || action.equalsIgnoreCase("remove")){
							boolean add = action.equalsIgnoreCase("add");
							for(int i2=0;i2<allAdd.length();i2++){
								if(i2+1 == allAdd.length()){
									String toAdd = allAdd.substring(marker, i2+1).trim();
									addToUpgrade(toAdd, newUpgrade, add);
									marker = 0;
									break;
								}
								else if(allAdd.charAt(i2) == ','){
									String toAdd = allAdd.substring(marker, i2).trim();
									addToUpgrade(toAdd, newUpgrade, add);
									marker = i2+1;
								}
							}
						}
						else if(action.equalsIgnoreCase("stats")){
							for(int i2=0;i2<allAdd.length();i2++){
								if(i2+1 == allAdd.length()){
									String toAdd = allAdd.substring(marker, i2+1).trim();
									for(int i3=0;i3<toAdd.length();i3++){
										char c = toAdd.charAt(i3);
										if(Character.isLetter(c) || Character.isSpaceChar(c)) continue;
										if(c == '<'){
											newUpgrade.setStatList.put(toAdd.substring(0, i3).trim(), Integer.valueOf(toAdd.substring(i3+1, indexOf('>', toAdd)).trim()));
											break;
										}
										newUpgrade.statList.put(toAdd.substring(0, i3).trim(), Integer.valueOf(toAdd.substring(i3).trim()));
										break;
									}
									marker = 0;
									break;
								}
								else if(allAdd.charAt(i2) == ','){
									String toAdd = allAdd.substring(marker, i2).trim();
									for(int i3=0;i3<toAdd.length();i3++){
										char c = toAdd.charAt(i3);
										if(Character.isLetter(c) || Character.isSpaceChar(c)) continue;
										if(c == '<'){
											newUpgrade.setStatList.put(toAdd.substring(0, i3).trim(), Integer.valueOf(toAdd.substring(i3+1, indexOf('>', toAdd)).trim()));
											break;
										}
										newUpgrade.statList.put(toAdd.substring(0, i3).trim(), Integer.valueOf(toAdd.substring(i3).trim()));
										break;
									}
									marker = i2+1;
								}
							}
						}
						else if(action.equalsIgnoreCase("enable") || action.equalsIgnoreCase("disable")){
							for(int i2=0;i2<allAdd.length();i2++){
								if(i2+1 == allAdd.length()){
									if(action.equalsIgnoreCase("enable")) newUpgrade.enableList.add(allAdd.substring(marker, i2+1).trim());
									else newUpgrade.disableList.add(allAdd.substring(marker, i2+1).trim());
									marker = 0;
									break;
								}
								else if(allAdd.charAt(i2) == ','){
									if(action.equalsIgnoreCase("enable")) newUpgrade.enableList.add(allAdd.substring(marker, i2).trim());
									else newUpgrade.disableList.add(allAdd.substring(marker, i2).trim());
									marker = i2+1;
								}
							}
						}
						else if(action.equalsIgnoreCase("link")){
							for(int i2=0;i2<allAdd.length();i2++){
								if(i2+1 == allAdd.length()){
									newUpgrade.linkedUpgrades.add(allAdd.substring(marker, i2+1).trim());
									marker = 0;
									break;
								}
								else if(allAdd.charAt(i2) == ','){
									newUpgrade.linkedUpgrades.add(allAdd.substring(marker, i2).trim());
									marker = i2+1;
								}
							}
						}
						else Main.logln("unrecognized upgrade action, please check the " + upgradeName + 
								" upgrade for " + unit.name + " in Codex: " + race + " for typos.");
						if(indexOf('|', temp) > -1) temp = sb.delete(0, indexOf('|', temp)+1).toString().trim();
						else temp = sb.delete(0, sb.length()).toString().trim();
						i1 = -1;
					}
				}
				upgrades.add(newUpgrade);
			}
			s = sb.delete(0, sb.length()).append(s).delete(0, indexOf(')', s)+1).toString().trim();
			i = -1;
		}
		return upgrades;
	}

	private void addToUpgrade(String s, Upgrade u, boolean b) {
		if(b){
			for(String s1: gearList){
				if(s.equalsIgnoreCase(s1)){
					u.addGearList.add(s1);
					return;
				}
			}
			for(String s1: rulesList){
				if(s.equalsIgnoreCase(s1)){
					u.addRuleList.add(s1);
					return;
				}
			}
			u.addUnitList.add(s);
		}
		else{
			for(String s1: gearList){
				if(s.equalsIgnoreCase(s1)){
					u.removeGearList.add(s1);
					return;
				}
			}
			for(String s1: rulesList){
				if(s.equalsIgnoreCase(s1)){
					u.removeRuleList.add(s1);
					return;
				}
			}
		}
	}

	private ArrayList<String> readRules(String s) {
		ArrayList<String> rules = new ArrayList<String>();
		s = s.trim();
		int marker = 0;
		for(int i = 0;i < s.length();i++){
			if(s.length() == 0) break;
			if(i+1 == s.length()) rules.add(s.substring(marker, i+1).trim());
			else if(s.charAt(i) == ','){
				rules.add(s.substring(marker, i).trim());
				marker = i+1;
			}
		}
		return rules;
	}

	private ArrayList<String> readGear(String s) {
		ArrayList<String> wargear = new ArrayList<String>();
		s = s.trim();
		int marker = 0;
		for(int i = 0;i < s.length();i++){
			if(s.length() == 0) break;
			if(i+1 == s.length()) wargear.add(s.substring(marker, i+1).trim());
			else if(s.charAt(i) == ','){
				wargear.add(s.substring(marker, i).trim());
				marker = i+1;
			}
		}
		return wargear;
	}

	private int[] readStats(String s, AbstractUnit unit) {
		int[] stats = new int[10];
		int marker = 0;
		for(int i = 0;i<s.length();i++){
			if(Character.isDigit(s.charAt(i))){
				if(marker < 10){
					for(int i1 = i;i1<s.length();i1++){
						if(i1+1 == s.length()){
							stats[marker] = Integer.valueOf(s.substring(i, i1+1));
							return stats;
						}
						if(!Character.isDigit(s.charAt(i1))){
							stats[marker] = Integer.valueOf(s.substring(i, i1));
							marker++;
							if(i1-i > 0) i = i1;
							break;
						}
					}
				}
			}
		}
		return stats;
	}

	private int getLineType(String s) {
		if(s.equalsIgnoreCase("name")) return 0;
		if(s.equalsIgnoreCase("type")) return 1;
		if(s.equalsIgnoreCase("stats")) return 2;
		if(s.equalsIgnoreCase("gear")) return 3;
		if(s.equalsIgnoreCase("rules")) return 4;
		if(s.equalsIgnoreCase("upgrades")) return 5;
		if(s.equalsIgnoreCase("race")) return 6;
		if(s.equalsIgnoreCase("capacity")) return 7;
		if(s.equalsIgnoreCase("display")) return 8;
		if(s.equalsIgnoreCase("slot")) return 9;
		if(s.equalsIgnoreCase("models")) return 10;
		if(s.equalsIgnoreCase("exclude")) return 11;
		return -1;
	}

	public String getRace() {
		return race;
	}

	public JScrollPane getUpgradePane(){
		return upgradePane;
	}

	public int getLayer() {
		return layer;
	}

	public Unit getUnit(String s) {
		for(Unit u: unitList)
			if(u.toString().equalsIgnoreCase(s)) 
				return new Unit(u);
		return null;
	}
}
