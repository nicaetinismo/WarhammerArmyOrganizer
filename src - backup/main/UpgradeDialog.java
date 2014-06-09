package main;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import java.awt.Font;

@SuppressWarnings("serial")
public class UpgradeDialog extends JDialog {

	private JPanel contentPane;
	protected JCheckBox box;

	
	public UpgradeDialog(JSpinner s, Upgrade u, JToggleButton b) {
		setBounds(200, 200, 305, 143);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		box = new JCheckBox("Add to all squad members");
		box.setBounds(39, 34, 217, 23);
		contentPane.add(box);
		
		JButton btnAddUpgrade = new JButton("Add Upgrade!");
		btnAddUpgrade.setBounds(29, 86, 117, 29);
		btnAddUpgrade.addActionListener(new Listener(u, "upgrade", box));
		contentPane.add(btnAddUpgrade);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(158, 86, 117, 29);
		btnCancel.addActionListener(new Listener(u, "upgrade1", b));
		contentPane.add(btnCancel);
		
		s.setBounds(221, 6, 56, 28);
		contentPane.add(s);
		
		JLabel lblAmountToAdd = new JLabel("Amount to add to Squad");
		lblAmountToAdd.setBounds(29, 6, 199, 28);
		contentPane.add(lblAmountToAdd);
		
		JLabel lblNoteThisWill = new JLabel("this will automatically add the upgrade to new squad members");
		lblNoteThisWill.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		lblNoteThisWill.setBounds(6, 58, 293, 16);
		contentPane.add(lblNoteThisWill);
	}
}
