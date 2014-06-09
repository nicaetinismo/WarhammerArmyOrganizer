package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class ArmyFileHandler {


	private static Army army;

	public static Army loadArmy(File f) throws IOException{
		try{
			BufferedReader input = new BufferedReader(new FileReader(f));
			try{
				String line = "";
				while ((line = input.readLine()) != null){
					String temp = line.trim();
					if(temp.equals("")) continue;
					if(temp.charAt(0) == '!') army = new Army(temp.substring(1));
					else if(temp.charAt(0) == '-') Main.pointsValue = new Integer(temp.substring(1));
					else if(temp.charAt(0) == '('){
						Unit tempUnit = null;
						for(int i=1;i<temp.length();i++){
							if(temp.charAt(i) == '|'){
								String s = temp.substring(1, i);
								int temp1 = 1;
								for(int i1=0;i1<s.length();i1++){
									if(s.charAt(i1) == '@'){
										temp1 = new Integer(s.substring(i1+1));
										s = new StringBuilder(s).delete(i1, s.length()).toString();
									}
								}
								tempUnit = Main.getUnit(s, army.race);
								tempUnit.currentUpgrades = new ArrayList<Upgrade>();
								tempUnit.setArmy(army);
								tempUnit.setNumberModel(temp1);
								temp = new StringBuilder(temp).delete(0, i+1).toString().trim();
								i = -1;
								if(!tempUnit.upgrades.isEmpty()) for(Upgrade u: tempUnit.upgrades) if(u.isActive) u.setActive(false);
							}
							else if(temp.charAt(i) == '>'){
								String s = temp.substring(0, i);
								int temp1 = 1;
								for(int i1=0;i1<s.length();i1++){
									if(s.charAt(i1) == '@'){
										temp1 = new Integer(s.substring(i1+1));
										s = new StringBuilder(s).delete(i1, s.length()).toString();
									}
								}
								for(Upgrade u: tempUnit.upgrades){
									u.setFile(tempUnit);
									if(u.getName().equalsIgnoreCase(s)){
										u.setActive(true);
										u.setNumberInSquad(temp1);
										temp = new StringBuilder(temp).delete(0, i+1).toString().trim();
										i = -1;
									}
								}
							}
							else if(temp.charAt(i) == ')'){
								if(temp.charAt(i - 1) != '#'){
									String s = temp.substring(0, i);
									int temp1 = 1;
									for(int i1=0;i1<s.length();i1++){
										if(s.charAt(i1) == '@'){
											temp1 = new Integer(s.substring(i1+1));
											s = new StringBuilder(s).delete(i1, s.length()).toString();
										}
									}
									for(Upgrade u: tempUnit.upgrades){
										u.setFile(tempUnit);
										if(u.getName().equalsIgnoreCase(s)){
											u.setActive(true);
											u.setNumberInSquad(temp1);
											break;
										}
									}
								}
								else {
									String s = temp.substring(1, i);
									int temp1 = 1;
									for(int i1=0;i1<s.length();i1++){
										if(s.charAt(i1) == '@'){
											temp1 = new Integer(s.substring(i1+1, s.indexOf('#')));
											s = new StringBuilder(s).delete(i1, s.length()).toString();
										}
									}
									if(s.contains("#")) s = new StringBuilder(s).delete(s.indexOf('#'), s.indexOf('#') + 1).toString();
									tempUnit = Main.getUnit(s, army.race);
									tempUnit.setArmy(army);
									tempUnit.update();
									tempUnit.setNumberModel(temp1);
								}
							}
						}
						for(Upgrade u: tempUnit.currentUpgrades){
							u.updateCostBasedOnSize();
						}
						tempUnit.update();
						army.addUnit(tempUnit);
					}
				}
			}
			finally{
				input.close();
			}
		}
		catch(FileNotFoundException e){
			throw e;
		}
		army.fileName = f.getPath();
		army.update();
		return army;
	}

	public Army getArmy() {
		return army;
	}

	public static void saveArmy(Army a, File f, boolean b) throws FileNotFoundException, IOException {
		String contents = "!" + a.race + Main.lb + "-" + Main.pointsValue + Main.lb;

		for(Unit u: a.unitList){
			if(!u.currentUpgrades.isEmpty()){
				contents = contents + "(" + u.getName();
				if(u.currentSize > 1) contents = contents + "@" + u.currentSize + "|";
				else contents = contents + "|";
				for(Upgrade u1: u.currentUpgrades){
					contents = contents + u1.getName();
					if(u1.numberInSquad > 1) contents = contents + "@" + u1.numberInSquad + ", ";
					else contents = contents + ", ";
				}
				contents = new StringBuilder(contents).delete(contents.length() - 2, contents.length()).append(")" + Main.lb).toString();
			}
			else {
				contents = contents + "(" + u.getName();
				if(u.currentSize > 1) contents = contents + "@" + u.currentSize + "#)" + Main.lb;
				else contents = contents + "#)" + Main.lb;
			}
		}
		
		String path = f.getPath();
		if(f.getPath().contains(".wasf")) path = new StringBuilder(f.getPath()).delete(f.getPath().lastIndexOf(".wasf"), f.getPath().length()).toString();

		Writer output = new BufferedWriter(new FileWriter(path + ".wasf", false));
		try {
			output.write(contents);
		}
		finally {
			output.close();
		}
	}
}
