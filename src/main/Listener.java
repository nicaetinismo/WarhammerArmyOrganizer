package main;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class Listener implements ActionListener, TreeSelectionListener, ListSelectionListener {


	@Override
	public void actionPerformed(ActionEvent e) {
		if(model != null){
			if(adding){
				if(model.currentSize == model.minSize) model.minusButton.setEnabled(true);
				model.setSize(model.currentSize + 1);
				if(!GUI.currentUnit.models.contains(model)) GUI.currentUnit.addModel(model);
				if(model.currentSize == model.maxSize) model.plusButton.setEnabled(false);
				if(!model.excludeList.isEmpty())
					for(Model m: model.excludeList)
						if(m.plusButton.isEnabled())
							m.plusButton.setEnabled(false);
			}
			else{
				if(!model.plusButton.isEnabled()) model.plusButton.setEnabled(true);
				if(model.currentSize > model.minSize) model.setSize(model.currentSize - 1);
				if(model.currentSize == model.minSize){
					if(!model.excludeList.isEmpty())
						for(Model m: model.excludeList)
							if(!m.plusButton.isEnabled())
								m.plusButton.setEnabled(true);
					model.minusButton.setEnabled(false);
				}
			}
			GUI.currentUnit.update();
			GUI.showModelList(GUI.currentUnit);
		}
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
//							new GUI.UpgradeChoiceDialog(upgrade);
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
			if((u1 = new Unit(GUI.raceList.getSelectedValue())) != null){
				if(Main.currentArmy != null){
					Main.currentArmy.addUnit(u1);
					GUI.armyTree.setVisible(true);
					GUI.showUnit(u1);
				}
				else {
					GUI.saveOption.setEnabled(true);
					GUI.saveAsOption.setEnabled(true);
					Main.currentArmy = new Army(u1.getRace());
					Main.currentArmy.addUnit(u1);
					GUI.lblCurrentArmy.setVisible(true);
					GUI.armyTree.setVisible(true);
					GUI.showUnit(u1);
				}
			}

			Main.currentArmy.update();
		}
		else if(affected.equalsIgnoreCase("remove")){
			Unit u = (Unit) ((DefaultMutableTreeNode) GUI.armyTree.getSelectionPath().getLastPathComponent()).getUserObject();
			Main.getCurrentArmy().removeUnit(u);
			if(Main.getCurrentArmy().isEmpty()){
				CardLayout cl1 = (CardLayout)(GUI.bottomPanel.getLayout());
				cl1.show(GUI.bottomPanel, "blank");
				GUI.lblCurrentArmy.setVisible(false);
			}
		}
		else if(affected.equalsIgnoreCase("upgrade1")){
			button.setSelected(false);
			upgrade.dialog.setVisible(false);
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(changesUnit) GUI.showUnit((AbstractUnit) list.getSelectedValue());
	}
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		Object o = ((DefaultMutableTreeNode) e.getPath().getLastPathComponent()).getUserObject();
		if(o instanceof Model || o instanceof Unit)
			GUI.showUnit((AbstractUnit) o);
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

	public Listener(Model m, boolean b) {
		model = m;
		adding = b;
	}

	public Listener(JList<?> j, boolean b){
		list = j;
		changesUnit = b;
	}

	private JList<?> list;
	private boolean changesUnit;
	private boolean adding;
	private Model model;
	private String affected = "";
	private Upgrade upgrade;
	private JToggleButton button;
	private JCheckBox checkBox;
}
