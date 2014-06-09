package main;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SpinnerListener implements ChangeListener{

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == GUI.PointsDialog.pointsSpinner) return;
		if(upgrade != null){
			upgrade.setNumberInSquad((Integer) spinner.getValue());
			upgrade.unit.update();
		}
		else {
			int i = ((Integer) spinner.getValue()) - unit.currentSize;
			unit.setCurrentSize((Integer) spinner.getValue());
			unit.updateChangingUpgrades(i);
			unit.update();
		}
	}

	public void setUnit(Unit u){
		unit = u;
	}

	public SpinnerListener(JSpinner j, Upgrade u){
		spinner = j;
		upgrade = u;
	}

	private Upgrade upgrade;
	private Unit unit;
	private JSpinner spinner;
}
