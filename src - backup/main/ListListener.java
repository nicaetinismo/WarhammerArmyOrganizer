package main;

import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListListener implements ListSelectionListener {

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(index == 0){
			Unit w = (Unit) list.getSelectedValue();
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
			else GUI.clear();
		}
		else if(index == 3){
			Unit w = (Unit) list.getSelectedValue();
			if(w != null){
				CardLayout cl = (CardLayout)(GUI.bottomPanel.getLayout());
				cl.show(GUI.bottomPanel, "edit");
				CardLayout cl1 = (CardLayout)(GUI.editUpgradePane.getLayout());
				cl1.show(GUI.editUpgradePane, w.getCardName1());
				CardLayout cl2 = (CardLayout)(GUI.editStatsPane.getLayout());
				cl2.show(GUI.editStatsPane, String.valueOf(w.getType()));
				w.update();
				GUI.editRulesList.setModel(w.getRulesModel());
				GUI.editGearList.setModel(w.getGearModel());
				GUI.editNameLbl.setText(w.getName());
				GUI.editNumberSpinner.setModel(w.getEditNumberModel());
				GUI.editCostLbl.setText(w.getCost());
				for(ChangeListener s: GUI.editNumberSpinner.getChangeListeners()){
					if(s instanceof SpinnerListener) ((SpinnerListener) s).setUnit(w);
				}
			}
			else if(dialog != null){
				dialog.getUpgrade().chosenGear = (String) list.getSelectedValue();
				dialog.getUpgrade().setActive(true);
				dialog.dispose();
			}
			else GUI.clear();
		}
	}

	public ListListener(JList<?> j, int i){
		list = j;
		index = i;
	}

	public ListListener(JList<?> l, GUI.UpgradeChoiceDialog d) {
		list = l;
		dialog = d;
	}

	private JList<?> list;
	private int index;
	private GUI.UpgradeChoiceDialog dialog;
}
