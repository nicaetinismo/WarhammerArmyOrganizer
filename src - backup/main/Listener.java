package main;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeListener;

public class Listener implements ActionListener {


	@Override
	public void actionPerformed(ActionEvent e) {
		if(upgrade != null && checkBox != null){
			if(upgrade.removeGearList.isEmpty()){
				if(upgrade.maxInSquad == -1){
					upgrade.updateCostBasedOnSize();
					upgrade.setActive(checkBox.isSelected());
				}
				else upgrade.setActive(checkBox.isSelected());
			}
			else {
				if(!upgrade.isActive()){
					for(String s: upgrade.removeGearList){
						if(s.contains("/")){
							new GUI.UpgradeChoiceDialog(upgrade);
							return;
						}
					}
				}
				else if(upgrade.maxInSquad == -1){
					upgrade.updateCostBasedOnSize();
					upgrade.setActive(checkBox.isSelected());
				}
				else upgrade.setActive(checkBox.isSelected());
			}
		}
		if(e.getSource() == GUI.loadButton){
			int returnVal = Main.fileChooser.showOpenDialog(GUI.saveLoad);

			if(returnVal == JFileChooser.APPROVE_OPTION){
				File file = Main.fileChooser.getSelectedFile();
				Main.loadArmy(file);
				GUI.changeOption.setEnabled(true);
			}
		}
		if(e.getSource() == GUI.createButton || e.getSource() == GUI.changeOption){
			new GUI.PointsDialog();
		}
		if(e.getSource() == GUI.saveAsOption){
			int returnVal = Main.fileChooser.showSaveDialog(GUI.saveLoad);

			if(returnVal == JFileChooser.APPROVE_OPTION){
				File f = Main.fileChooser.getSelectedFile();
				Main.saveArmy(f, true);
			}
		}
		if(e.getSource() == GUI.saveOption){
			int returnVal;
			if(!Main.loadedArmy) returnVal = Main.fileChooser.showSaveDialog(GUI.saveLoad);
			else returnVal = -1;
			if(returnVal == -1){
				File f = new File(Main.currentArmy.fileName);
				Main.saveArmy(f, false);
			}
			else if(returnVal == JFileChooser.APPROVE_OPTION){
				File f = Main.fileChooser.getSelectedFile();
				Main.saveArmy(f, true);
			}
		}
		if(e.getSource() == GUI.newOption){
			Main.currentArmy = null;
			Main.loadedArmy = false;
		}
		if(e.getSource() == GUI.openOption){
			if(Main.currentArmy != null){
				String[] options = {"Continue", "Save army", "Cancel"};
				int n = JOptionPane.showOptionDialog(new JPanel(), "Any unsaved changes will be lost!" + Main.lb + "Are you sure you want to load an army?", "Warning!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[2]);


				if(n == JOptionPane.YES_OPTION){
					int returnVal = Main.fileChooser.showOpenDialog(GUI.saveLoad);

					if(returnVal == JFileChooser.APPROVE_OPTION){
						File file = Main.fileChooser.getSelectedFile();
						Main.loadArmy(file);
					}
				}
				else if(n == JOptionPane.NO_OPTION){
					int returnVal;
					if(!Main.loadedArmy) returnVal = Main.fileChooser.showSaveDialog(GUI.saveLoad);
					else returnVal = -1;
					if(returnVal == -1){
						File f = new File(Main.currentArmy.fileName);
						Main.saveArmy(f, false);
					}
					else if(returnVal == JFileChooser.APPROVE_OPTION){
						File f = Main.fileChooser.getSelectedFile();
						Main.saveArmy(f, true);
					}

					int returnVal1 = Main.fileChooser.showOpenDialog(GUI.saveLoad);

					if(returnVal1 == JFileChooser.APPROVE_OPTION){
						File file = Main.fileChooser.getSelectedFile();
						Main.loadArmy(file);
					}
				}
			}
			else {
				int returnVal = Main.fileChooser.showOpenDialog(GUI.saveLoad);

				if(returnVal == JFileChooser.APPROVE_OPTION){
					File file = Main.fileChooser.getSelectedFile();
					Main.loadArmy(file);
				}
			}
		}
		if(affected.equalsIgnoreCase("racecombo")){
			if(!((String) GUI.raceCombo.getSelectedItem()).equalsIgnoreCase("Pick a race!")){
				GUI.raceList.setModel(Main.raceListModels.get((String) GUI.raceCombo.getSelectedItem()));
				CardLayout cl1 = (CardLayout)(GUI.cardPanel.getLayout());
				cl1.show(GUI.cardPanel, "blank");
				GUI.raceCombo.removeItem("Pick a race!");
			}
		}
		else if(affected.equalsIgnoreCase("addUnit")){
			Unit u1;
			if(GUI.raceList.getSelectedValue() != null){
				u1 = new Unit(GUI.raceList.getSelectedValue());
				if(Main.currentArmy != null){
					Main.currentArmy.addUnit(u1);
					if(u1.addsUnit){
						for(Unit u2: u1.unitsToAdd){
							Main.currentArmy.addUnit(new Unit(u2));
						}
					}
				}
				else {
					GUI.saveOption.setEnabled(true);
					GUI.saveAsOption.setEnabled(true);
					Army newArmy = new Army(u1.getRace()).addUnit(u1);
					Main.currentArmy = newArmy;
					if(u1.addsUnit){
						for(Unit u2: u1.unitsToAdd){
							newArmy.addUnit(new Unit(u2));
						}
					}
					GUI.lblCurrentArmy.setVisible(true);
				}
			}
			else return;
			Main.currentArmy.update();
		}
		else if(affected.equalsIgnoreCase("remove")){
			Unit u = (Unit) GUI.armyList.getSelectedValue();
			if(u.addsUnit) for(Unit u1: u.unitsToAdd) Main.getCurrentArmy().removeUnit(u1);
			Main.getCurrentArmy().removeUnit(u);
			if(Main.getCurrentArmy().isEmpty()){
				CardLayout cl1 = (CardLayout)(GUI.bottomPanel.getLayout());
				cl1.show(GUI.bottomPanel, "blank");
			}
		}
		else if(affected.equalsIgnoreCase("upgrade1")){
			button.setSelected(false);
			upgrade.dialog.setVisible(false);
		}
		else if(affected.equalsIgnoreCase("edit")){
			Unit w = (Unit) GUI.armyList.getSelectedValue();
			if(w != null){
				CardLayout cl1 = (CardLayout)(GUI.bottomPanel.getLayout());
				cl1.show(GUI.bottomPanel, "add");
				CardLayout cl2 = (CardLayout)(GUI.statsCard.getLayout());
				cl2.show(GUI.statsCard, String.valueOf(w.getType()));
				GUI.modelNameLabel.setText(w.getName());
				int[] stats = w.getStats();
				int i = 0;
				for(JLabel l: GUI.statLabelList){
					if(i<8) l.setText(String.valueOf(stats[i++]));
					else l.setText(String.valueOf(stats[i++]) + "+");
				}
				CardLayout cl = (CardLayout)(GUI.cardPanel.getLayout());
				cl.show(GUI.cardPanel, w.getCardName());
				GUI.rulesList.setModel(w.getRulesModel());
				GUI.gearList.setModel(w.getGearModel());
				GUI.costLabel.setText(w.getCost());
				GUI.numberSpinner.setModel(w.getNumberModel());
				w.update();
				for(ChangeListener s: GUI.numberSpinner.getChangeListeners()) if(s instanceof SpinnerListener) ((SpinnerListener) s).setUnit(w);
			}
		}
	}


	public Listener(String s){
		affected = s;
	}

	public Listener(Upgrade u, JCheckBox c){
		upgrade = u;
		checkBox = c;
	}

	public Listener(Upgrade u, String s, JToggleButton b){
		upgrade = u;
		affected = s;
		button = b;
	}

	public Listener(Upgrade u, String s, JCheckBox c){
		upgrade = u;
		affected = s;
		checkBox = c;
	}

	public Listener(Upgrade u, String s){
		upgrade = u;
		affected = s;
	}

	public Listener(){
	}

	private String affected = "";
	private Upgrade upgrade;
	private JToggleButton button;
	private JCheckBox checkBox;
}
