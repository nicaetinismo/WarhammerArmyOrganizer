package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.ListCellRenderer;
import javax.swing.LookAndFeel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.UIManager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.awt.FlowLayout;


@SuppressWarnings("serial")
public class GUI extends JFrame {

	public static JPanel armyCreationPane, mainMenuPane, panel_5, panel_2, bottomPanel, blankPanel, cardPanel,
	vehiclePanel, walkerPanel, editPanel, editUpgradePane, editStatsPane, panel_8, modelListPane, 
	basePane = new JPanel(), statsCard = new JPanel(), statsPanelInf = new JPanel(), statsPanelVeh = new JPanel(), 
	statsPanelWalker = new JPanel(), editVehiclePanel = new JPanel(), editWalkerPanel = new JPanel(), 
	editInfantryPanel = new JPanel(), saveLoad = new JPanel();
	public static JLabel lblWs, lblBs, lblS, lblT, lblW, lblI, lblA, lblLd, lblSv, lblIS, lblWsV, lblBsV, 
	lblSV, lblTV, lblWV, lblIV, lblAV, lblLdV, lblSvV, lblISV, lblVBS, lblVFA, lblVSA, 
	lblVBA, lblVBSV, lblVFAV, lblVSAV, lblVBAV, lblWWS, lblWBS, lblWS, lblWFA, lblWSA, 
	lblWBA, lblWI, lblWA, lblWWSV, lblWBSV, lblWSV, lblWFAV, lblWSAV, lblWBAV, lblWIV, 
	lblWAV, lblVHP, lblVHPV, lblWHP, lblWHPV, lblRules, modelNameLabel, lblCost, lblAmount, 
	label, label_1, label_2, editCostLbl, editNameLbl, label_5, lblCurrentArmy, 
	costLabel = new JLabel("test");
	public static ArrayList<JLabel> statLabelList = new ArrayList<JLabel>(), editStatLabelList = new ArrayList<JLabel>(), 
			vehicleStatLabelList = new ArrayList<JLabel>(), editVehicleStatLabelList = new ArrayList<JLabel>(),
			walkerStatLabelList = new ArrayList<JLabel>(), editWalkerStatLabelList = new ArrayList<JLabel>();
	public static JMenuItem saveOption = new JMenuItem("Save"), saveAsOption = new JMenuItem("Save As..."),
			newOption = new JMenuItem("New Army"), openOption = new JMenuItem("Load Army"),
			changeOption = new JMenuItem("Change point limit");
	public static JScrollPane scrollPane_2, scrollPane_8, scrollPane_9, scrollPane_10, modelListScrollPane;
	public static JButton loadButton, createButton, btnRemoveFromArmy;
	public static JSpinner numberSpinner, editNumberSpinner;
	public static int layerMarker = 1;
	public static JList<String> rulesList, gearList, editRulesList, editGearList;
	public static ArrayList<String> cardNames = new ArrayList<String>();
	public static SpinnerNumberModel blankSpinnerModel = new SpinnerNumberModel(0, 0, 0, 1);
	public static ImageIcon background;
	public static JMenu fileMenu = new JMenu("File");
	public static JMenuBar menuBar = new JMenuBar();
	public final static Color fontColor = new Color(0xFF, 0xFF, 0x99);
	public static JTree armyTree, unitModelsTree;
	public static HashMap<String, ImageIcon> picMap = new HashMap<String, ImageIcon>();
	public static Unit currentUnit;
	public static JComboBox<String> raceCombo;
	public static JList<Unit> raceList;
	private static final Font statsFont = new Font("Serif", Font.BOLD, 14);
	private static final Dimension statsDimension = new Dimension(10, 12);
	private static final DefaultListModel<String> blankModel = new DefaultListModel<String>();
	public static final String[] armyTreeStrings = {"hq", "troops", "elites", "fast attack", "heavy support", "transports"};

	@SuppressWarnings("unchecked")
	public GUI() {
		try{ loadPics(); }
		catch(IOException e){ Main.logln("failed to load pics"); }
		setTitle("Warhammer Army Organizer " + Main.version);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = 0;
		int h = 0;
		//		if(Main.isWindows()){
		w = 925;
		h = 607;
		//		}
		//		else{
		//			w = 900;
		//			h = 600;
		//		}
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		setBounds(x, y, w, h);
		setJMenuBar(menuBar);
		setContentPane(basePane);
		basePane.setLayout(new CardLayout(0, 0));

		saveAsOption.addActionListener(new Listener());
		saveOption.addActionListener(new Listener());
		newOption.addActionListener(new Listener());
		openOption.addActionListener(new Listener());
		changeOption.addActionListener(new Listener());

		changeOption.setEnabled(false);
		saveOption.setEnabled(false);
		saveAsOption.setEnabled(false);

		fileMenu.add(newOption);
		fileMenu.add(openOption);
		fileMenu.addSeparator();
		fileMenu.add(saveOption);
		fileMenu.add(saveAsOption);
		fileMenu.addSeparator();
		fileMenu.add(changeOption);
		menuBar.add(fileMenu);

		createButton = new JButton("");
		createButton.setOpaque(false);
		createButton.setFocusPainted(false);
		createButton.setBorderPainted(false);
		createButton.setContentAreaFilled(false);
		createButton.setBorder(null);
		createButton.addActionListener(new Listener());

		loadButton = new JButton("");
		loadButton.setOpaque(false);
		loadButton.setFocusPainted(false);
		loadButton.setBorderPainted(false);
		loadButton.setContentAreaFilled(false);
		loadButton.setBorder(null);
		loadButton.addActionListener(new Listener());

		loadButton.setIcon(picMap.get("loadButton"));
		createButton.setIcon(picMap.get("createButton"));
		background = picMap.get("background");

		mainMenuPane = new JPanel(){
			protected void paintComponent(Graphics g)
			{
				g.drawImage(background.getImage(), 0, 0, this.getWidth(),this.getHeight(), null);
				super.paintComponent(g);
			}
		};

		armyCreationPane = new JPanel(){
			protected void paintComponent(Graphics g)
			{
				g.drawImage(background.getImage(), 0, 0, this.getWidth(),this.getHeight(), null);
				super.paintComponent(g);
			}
		};

		armyCreationPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setOpaque(false);

		bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		bottomPanel.setLayout(new CardLayout(0, 0));

		blankPanel = new JPanel();
		blankPanel.setOpaque(false);
		bottomPanel.add(blankPanel, "blank");

		panel_2 = new JPanel();
		panel_2.setOpaque(false);
		statsCard.setBounds(6, 34, 283, 37);

		editPanel = new JPanel();
		bottomPanel.add(editPanel, "edit");
		editPanel.setLayout(null);
		editPanel.setOpaque(false);

		editStatsPane = new JPanel();
		editStatsPane.setOpaque(false);
		editStatsPane.setBounds(6, 34, 283, 37);
		editPanel.add(editStatsPane);
		editStatsPane.setLayout(new CardLayout(0, 0));

		statsCard.setLayout(new CardLayout(0, 0));

		statsPanelInf.setLayout(new GridLayout(2, 10, 0, 0));
		statsPanelVeh.setLayout(new GridLayout(2, 10, 0, 0));
		statsPanelWalker.setLayout(new GridLayout(2, 10, 0, 0));
		editInfantryPanel.setLayout(new GridLayout(2, 10, 0, 0));
		editVehiclePanel.setLayout(new GridLayout(2, 10, 0, 0));
		editWalkerPanel.setLayout(new GridLayout(2, 10, 0, 0));

		statsCard.add(statsPanelInf, "0");
		statsCard.add(statsPanelVeh, "1");
		statsCard.add(statsPanelWalker, "2");

		editStatsPane.add(editInfantryPanel, "0");
		editStatsPane.add(editVehiclePanel, "1");
		editStatsPane.add(editWalkerPanel, "2");

		modelNameLabel = new JLabel("Pick a unit!");
		modelNameLabel.setForeground(fontColor);
		modelNameLabel.setBounds(6, 6, 283, 28);
		modelNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		modelNameLabel.setHorizontalAlignment(SwingConstants.CENTER);

		panel_5 = new JPanel();
		panel_5.setOpaque(false);
		panel_5.setBounds(295, 34, 120, 31);

		lblCost = new JLabel("Cost:");
		lblCost.setForeground(fontColor);
		lblCost.setFont(new Font("Lucida Grande", Font.PLAIN, 18));

		costLabel.setForeground(fontColor);
		costLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		costLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
				gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
						.addComponent(lblCost)
						.addGap(18)
						.addComponent(costLabel, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		gl_panel_5.setVerticalGroup(
				gl_panel_5.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel_5.createSequentialGroup()
						.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCost, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(costLabel))
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		panel_5.setLayout(gl_panel_5);

		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setOpaque(false);
		scrollPane_4.getViewport().setOpaque(false);
		scrollPane_4.setBorder(null);
		scrollPane_4.setViewportBorder(null);
		scrollPane_4.setBounds(6, 88, 196, 91);

		cardPanel = new JPanel();
		cardPanel.setOpaque(false);
		cardPanel.setBounds(427, 75, 252, 200);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setOpaque(false);
		scrollPane_1.getViewport().setOpaque(false);
		scrollPane_1.setBorder(null);
		scrollPane_1.setViewportBorder(null);
		scrollPane_1.setBounds(214, 88, 201, 91);

		lblRules = new JLabel("Rules:");
		lblRules.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblRules.setForeground(fontColor);
		lblRules.setBounds(0, 73, 54, 14);

		lblAmount = new JLabel("Amount:");
		lblAmount.setVisible(false);
		lblAmount.setForeground(fontColor);
		lblAmount.setBounds(295, 12, 62, 19);
		lblAmount.setFont(new Font("Lucida Grande", Font.PLAIN, 15));


		numberSpinner = new JSpinner();
		numberSpinner.setVisible(false);
		numberSpinner.setBounds(361, 6, 54, 28);
		numberSpinner.addChangeListener(new SpinnerListener(numberSpinner, null));

		gearList = new JList<String>();
		setClearCell(gearList);
		gearList.setOpaque(false);
		gearList.setBorder(null);
		scrollPane_1.setViewportView(gearList);
		gearList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gearList.setSelectionModel(new DeselectModel("gear"));
		gearList.addListSelectionListener(new Listener(gearList, false));
		cardPanel.setLayout(new CardLayout(0, 0));

		rulesList = new JList<String>();
		setClearCell(rulesList);
		rulesList.setOpaque(false);
		rulesList.setBorder(null);
		scrollPane_4.setViewportView(rulesList);
		rulesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rulesList.setSelectionModel(new DeselectModel("rules"));
		rulesList.addListSelectionListener(new Listener(rulesList, false));
		panel_2.setLayout(null);
		panel_2.add(scrollPane_1);
		panel_2.add(cardPanel);

		JPanel blankPanel = new JPanel();
		cardPanel.add(blankPanel, "blank");
		blankPanel.setOpaque(false);
		panel_2.add(lblRules);
		panel_2.add(statsCard);
		panel_2.add(lblAmount);
		panel_2.add(numberSpinner);
		panel_2.add(panel_5);
		panel_2.add(scrollPane_4);
		panel_2.add(modelNameLabel);
		statsCard.setOpaque(false);
		statsPanelInf.setOpaque(false);
		statsPanelVeh.setOpaque(false);
		statsPanelWalker.setOpaque(false);
		editInfantryPanel.setOpaque(false);
		editVehiclePanel.setOpaque(false);
		editWalkerPanel.setOpaque(false);

		JScrollPane armyPane = new JScrollPane();
		armyPane.setOpaque(false);
		armyPane.setBorder(null);
		armyPane.setViewportBorder(null);
		armyPane.getViewport().setOpaque(false);

		lblCurrentArmy = new JLabel("Current Army:");
		lblCurrentArmy.setFont(new Font("Dialog", Font.BOLD, 16));
		lblCurrentArmy.setForeground(fontColor);
		lblCurrentArmy.setVisible(false);

		GroupLayout gl_contentPane = new GroupLayout(armyCreationPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(bottomPanel, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
								.addComponent(armyPane, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
								.addComponent(lblCurrentArmy, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))
								.addContainerGap())
				);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
										.addComponent(bottomPanel, GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblCurrentArmy, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(armyPane, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE))
										.addComponent(panel, GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE))
										.addGap(0))
				);

		armyTree = new JTree();
		armyTree.setFocusable(false);
		armyTree.setFont(new Font("Tahoma", Font.PLAIN, 14));
		armyTree.setOpaque(false);
		armyTree.setVisible(false);
		armyTree.setShowsRootHandles(false);
		armyTree.setUI(new NoIconUI());
		armyTree.setCellRenderer(new ClearTreeNode());
		armyTree.addMouseListener(new PopClickListener(armyTree));
		armyPane.setViewportView(armyTree);

		JLabel lblWargear = new JLabel("Wargear:");
		lblWargear.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblWargear.setForeground(fontColor);
		lblWargear.setBounds(207, 72, 70, 15);
		panel_2.add(lblWargear);

		JButton btnAddToArmy_1 = new JButton("Add to army!");
		btnAddToArmy_1.setBounds(427, 279, 252, 29);
		panel_2.add(btnAddToArmy_1);
		btnAddToArmy_1.addActionListener(new Listener("addUnit"));

		bottomPanel.add(panel_2, "add");

		modelListScrollPane = new JScrollPane();
		modelListScrollPane.setViewportBorder(null);
		modelListScrollPane.setOpaque(false);
		modelListScrollPane.setBorder(null);
		modelListScrollPane.getViewport().setOpaque(false);
		modelListScrollPane.setBounds(427, 0, 252, 71);
		panel_2.add(modelListScrollPane);

		modelListPane = new JPanel();
		modelListScrollPane.setViewportView(modelListPane);
		modelListPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setViewportBorder(null);
		scrollPane_2.setOpaque(false);
		scrollPane_2.setBorder(null);
		scrollPane_2.getViewport().setOpaque(false);
		scrollPane_2.setBounds(6, 190, 409, 118);
		panel_2.add(scrollPane_2);

		unitModelsTree = new JTree();
		unitModelsTree.addTreeSelectionListener(new Listener());
		unitModelsTree.setFocusable(false);
		unitModelsTree.setFont(new Font("Tahoma", Font.PLAIN, 14));
		unitModelsTree.setOpaque(false);
		unitModelsTree.setVisible(false);
		unitModelsTree.setUI(new NoIconUI());
		unitModelsTree.setCellRenderer(new ClearTreeNode());
		scrollPane_2.setViewportView(unitModelsTree);

		lblWs = newStatLabel("WS", 0);
		lblBs = newStatLabel("BS", 0);
		lblS = newStatLabel("S", 0);
		lblT = newStatLabel("T", 0);
		lblW = newStatLabel("W", 0);
		lblI = newStatLabel("I", 0);
		lblA = newStatLabel("A", 0);
		lblLd = newStatLabel("Ld", 0);
		lblSv = newStatLabel("Sv", 0);
		lblIS = newStatLabel("IS", 0);
		lblWsV = newStatLabel("-", 0);
		lblBsV = newStatLabel("-", 0);
		lblSV = newStatLabel("-", 0);
		lblTV = newStatLabel("-", 0);
		lblWV = newStatLabel("-", 0);
		lblIV = newStatLabel("-", 0);
		lblAV = newStatLabel("-", 0);
		lblLdV = newStatLabel("-", 0);
		lblSvV = newStatLabel("-", 0);
		lblISV = newStatLabel("-", 0);

		addVehicleStatGap(3, 0);

		lblVBS = newStatLabel("BS", 1);
		lblVFA = newStatLabel("F", 1);
		lblVSA = newStatLabel("S", 1);
		lblVBA = newStatLabel("R", 1);
		lblVHP = newStatLabel("HP", 1);

		addVehicleStatGap(5, 0);

		lblVBSV = newStatLabel("-", 1);
		lblVFAV = newStatLabel("-", 1);
		lblVSAV = newStatLabel("-", 1);
		lblVBAV = newStatLabel("-", 1);
		lblVHPV = newStatLabel("-", 1);

		addVehicleStatGap(2, 0);
		addVehicleStatGap(1, 1);

		lblWWS = newStatLabel("WS", 2);
		lblWBS = newStatLabel("BS", 2);
		lblWS = newStatLabel("S", 2);
		lblWFA = newStatLabel("F", 2);
		lblWSA = newStatLabel("S", 2);
		lblWBA = newStatLabel("R", 2);
		lblWI = newStatLabel("I", 2);
		lblWA = newStatLabel("A", 2);
		lblWHP = newStatLabel("HP", 2);

		addVehicleStatGap(1, 1);

		lblWWSV = newStatLabel("-", 2);
		lblWBSV = newStatLabel("-", 2);
		lblWSV = newStatLabel("-", 2);
		lblWFAV = newStatLabel("-", 2);
		lblWSAV = newStatLabel("-", 2);
		lblWBAV = newStatLabel("-", 2);
		lblWIV = newStatLabel("-", 2);
		lblWAV = newStatLabel("-", 2);
		lblWHPV = newStatLabel("-", 2);

		String[] raceNames = Main.armies.keySet().toArray(new String[]{});
		Arrays.sort(raceNames, Main.instance);
		raceCombo = new JComboBox<String>();
		raceCombo.addItem("Pick a race!");
		for(String s: raceNames) raceCombo.addItem(s);
		raceCombo.setSelectedIndex(0);
		raceCombo.addActionListener(new Listener("racecombo"));

		raceList = new JList<Unit>();
		raceList.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		raceList.setBorder(null);
		setClearCell(raceList);
		raceList.setOpaque(false);
		scrollPane_8 = new JScrollPane(raceList);
		scrollPane_8.setViewportBorder(null);
		scrollPane_8.setOpaque(false);
		scrollPane_8.setBorder(null);
		scrollPane_8.getViewport().setOpaque(false);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
						.addComponent(scrollPane_8, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
						.addGap(6))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
								.addComponent(raceCombo, 0, 198, Short.MAX_VALUE)
								.addContainerGap())
				);
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addComponent(raceCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane_8, GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE))
				);

		raceList.setSelectionModel(new DeselectModel("race"));
		raceList.addListSelectionListener(new Listener(raceList, true));
		raceList.addMouseListener(new PopClickListener(raceList, 1));
		panel.setLayout(gl_panel);
		armyCreationPane.setLayout(gl_contentPane);
		mainMenuPane.setOpaque(false);
		armyCreationPane.setOpaque(false);
		basePane.add(mainMenuPane, "menu");

		JPanel panel_4 = new JPanel();
		panel_4.setOpaque(false);
		GroupLayout gl_mainMenuPane = new GroupLayout(mainMenuPane);
		gl_mainMenuPane.setHorizontalGroup(
				gl_mainMenuPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_mainMenuPane.createSequentialGroup()
						.addContainerGap(341, Short.MAX_VALUE)
						.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 570, GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
				);
		gl_mainMenuPane.setVerticalGroup(
				gl_mainMenuPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainMenuPane.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(521, Short.MAX_VALUE))
				);


		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
				gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
						.addComponent(createButton, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(loadButton, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		gl_panel_4.setVerticalGroup(
				gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
						.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(createButton, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
								.addComponent(loadButton, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		panel_4.setLayout(gl_panel_4);
		mainMenuPane.setLayout(gl_mainMenuPane);
		basePane.add(armyCreationPane, "create");

		scrollPane_9 = new JScrollPane();
		scrollPane_9.setViewportBorder(null);
		scrollPane_9.setOpaque(false);
		scrollPane_9.getViewport().setOpaque(false);
		scrollPane_9.setBorder(null);
		scrollPane_9.setBounds(214, 88, 201, 156);
		editPanel.add(scrollPane_9);

		editGearList = new JList<String>();
		setClearCell(editGearList);
		editGearList.setOpaque(false);
		scrollPane_9.setViewportView(editGearList);

		editUpgradePane = new JPanel();
		editUpgradePane.setOpaque(false);
		editUpgradePane.setBounds(427, 0, 252, 214);
		editPanel.add(editUpgradePane);
		editUpgradePane.setLayout(new CardLayout(0, 0));

		label = new JLabel("Rules:");
		label.setForeground(new Color(255, 255, 153));
		label.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		label.setBounds(0, 73, 54, 14);
		editPanel.add(label);

		label_1 = new JLabel("Amount:");
		label_1.setForeground(new Color(255, 255, 153));
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		label_1.setBounds(295, 12, 62, 19);
		editPanel.add(label_1);

		editNumberSpinner = new JSpinner();
		editNumberSpinner.setBounds(361, 6, 54, 28);
		editNumberSpinner.addChangeListener(new SpinnerListener(editNumberSpinner, null));
		editPanel.add(editNumberSpinner);

		panel_8 = new JPanel();
		panel_8.setOpaque(false);
		panel_8.setBounds(295, 34, 120, 31);
		editPanel.add(panel_8);

		label_2 = new JLabel("Cost:");
		label_2.setForeground(new Color(255, 255, 153));
		label_2.setFont(new Font("Lucida Grande", Font.PLAIN, 18));

		editCostLbl = new JLabel("test");
		editCostLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		editCostLbl.setForeground(new Color(255, 255, 153));
		editCostLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		GroupLayout gl_panel_8 = new GroupLayout(panel_8);
		gl_panel_8.setHorizontalGroup(
				gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGap(0, 120, Short.MAX_VALUE)
				.addGroup(gl_panel_8.createSequentialGroup()
						.addComponent(label_2)
						.addGap(18)
						.addComponent(editCostLbl, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		gl_panel_8.setVerticalGroup(
				gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGap(0, 31, Short.MAX_VALUE)
				.addGroup(gl_panel_8.createSequentialGroup()
						.addGroup(gl_panel_8.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(editCostLbl))
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		panel_8.setLayout(gl_panel_8);

		scrollPane_10 = new JScrollPane();
		scrollPane_10.setViewportBorder(null);
		scrollPane_10.setOpaque(false);
		scrollPane_10.getViewport().setOpaque(false);
		scrollPane_10.setBorder(null);
		scrollPane_10.setBounds(6, 88, 196, 150);
		editPanel.add(scrollPane_10);

		editRulesList = new JList<String>();
		setClearCell(editRulesList);
		editRulesList.setOpaque(false);
		scrollPane_10.setViewportView(editRulesList);

		editNameLbl = new JLabel("Pick a unit!");
		editNameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		editNameLbl.setForeground(new Color(255, 255, 153));
		editNameLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		editNameLbl.setBounds(6, 6, 283, 28);
		editPanel.add(editNameLbl);

		label_5 = new JLabel("Wargear:");
		label_5.setForeground(new Color(255, 255, 153));
		label_5.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		label_5.setBounds(207, 72, 70, 15);
		editPanel.add(label_5);

		btnRemoveFromArmy = new JButton("Remove from army");
		btnRemoveFromArmy.setBounds(427, 215, 252, 29);
		btnRemoveFromArmy.addActionListener(new Listener("remove"));
		editPanel.add(btnRemoveFromArmy);
		CardLayout cl = (CardLayout)(basePane.getLayout());
		cl.show(basePane, "menu");
	}

	private void loadPics() throws IOException{
		if(Main.debug){
			if(Main.isMac()){
				picMap.put("loadButton", new ImageIcon("/Users/nicaetinismo/Documents/workspace/Warhammer Program/src/load button background.png"));
				picMap.put("createButton", new ImageIcon("/Users/nicaetinismo/Documents/workspace/Warhammer Program/src/button background.png"));
				picMap.put("background", new ImageIcon("/Users/nicaetinismo/Documents/workspace/Warhammer Program/bin/background.png"));
				picMap.put("slotIcon", new ImageIcon("/Users/nicaetinismo/Documents/workspace/Warhammer Program/src/icon small.png"));
				picMap.put("plusButton", new ImageIcon("/Users/nicaetinismo/Documents/workspace/Warhammer Program/src/plusbutton.png"));
				picMap.put("minusButton", new ImageIcon("/Users/nicaetinismo/Documents/workspace/Warhammer Program/src/minusbutton.png"));
			}
			else{
				picMap.put("loadButton", new ImageIcon(System.getProperty("user.home") + "\\Dropbox\\Warhammer Army Organizer\\src\\load button background.png"));
				picMap.put("createButton", new ImageIcon(System.getProperty("user.home") + "\\Dropbox\\Warhammer Army Organizer\\src\\button background.png"));
				picMap.put("background", new ImageIcon(System.getProperty("user.home") + "\\Dropbox\\Warhammer Army Organizer\\src\\background.png"));
				picMap.put("slotIcon", new ImageIcon(System.getProperty("user.home") + "\\Dropbox\\Warhammer Army Organizer\\src\\icon small.png"));
				picMap.put("plusButton", new ImageIcon(System.getProperty("user.home") + "\\Dropbox\\Warhammer Army Organizer\\src\\plusbutton.png"));
				picMap.put("minusButton", new ImageIcon(System.getProperty("user.home") + "\\Dropbox\\Warhammer Army Organizer\\src\\minusbutton.png"));
			}
		}
		else {
			picMap.put("loadButton", new ImageIcon(ImageIO.read(GUI.class.getClassLoader().getResourceAsStream("load button background.png"))));
			picMap.put("createButton", new ImageIcon(ImageIO.read(GUI.class.getClassLoader().getResourceAsStream("button background.png"))));
			picMap.put("background", new ImageIcon(ImageIO.read(GUI.class.getClassLoader().getResourceAsStream("background.png"))));
			picMap.put("slotIcon", new ImageIcon(ImageIO.read(GUI.class.getClassLoader().getResourceAsStream("icon small.png"))));
			picMap.put("plusButton", new ImageIcon(ImageIO.read(GUI.class.getClassLoader().getResourceAsStream("plusbutton.png"))));
			picMap.put("minusButton", new ImageIcon(ImageIO.read(GUI.class.getClassLoader().getResourceAsStream("minusbutton.png"))));
			Main.logln("loaded pics");
		}
	}

	private void addVehicleStatGap(int i, int i1) {
		if(i1 == 0) for(int i2=0;i2<i;i2++){
			statsPanelVeh.add(new JLabel(""));
			editVehiclePanel.add(new JLabel(""));
		}
		if(i1 == 1) for(int i2=0;i2<i;i2++){
			statsPanelWalker.add(new JLabel(""));
			editWalkerPanel.add(new JLabel(""));
		}
	}

	private JLabel newStatLabel(String s, int i){
		JLabel j = new JLabel(s);
		JLabel j1 = new JLabel(s);
		j.setHorizontalAlignment(SwingConstants.CENTER);
		j.setFont(statsFont);
		j.setPreferredSize(statsDimension);
		j.setForeground(fontColor);
		j1.setHorizontalAlignment(SwingConstants.CENTER);
		j1.setFont(statsFont);
		j1.setPreferredSize(statsDimension);
		j1.setForeground(fontColor);
		if(i == 0){
			statsPanelInf.add(j);
			editInfantryPanel.add(j1);
			if(s.equalsIgnoreCase("-")){
				statLabelList.add(j);
				editStatLabelList.add(j1);
			}
			return j;
		} else if(i == 1){
			statsPanelVeh.add(j);
			editVehiclePanel.add(j1);
			if(s.equalsIgnoreCase("-")){
				vehicleStatLabelList.add(j);
				editVehicleStatLabelList.add(j1);
			}
			return j;
		} else if(i == 2){
			statsPanelWalker.add(j);
			editWalkerPanel.add(j1);
			if(s.equalsIgnoreCase("-")){
				walkerStatLabelList.add(j);
				editWalkerStatLabelList.add(j1);
			}
			return j;
		}
		return null;
	}

	public static void addUpgradePane(JPanel p, JScrollPane sp, String s) {
		p.add(sp, s);
		if(p.equals(editUpgradePane)) cardNames.add(s);
	}

	public static void clear() {
		modelNameLabel.setText("Pick a unit!");
		for(JLabel l: statLabelList) l.setText("-");
		CardLayout cl = (CardLayout) cardPanel.getLayout();
		cl.show(cardPanel, "blank");
		CardLayout cl1 = (CardLayout) bottomPanel.getLayout();
		cl1.show(bottomPanel, "blank");
		rulesList.setModel(blankModel);
		gearList.setModel(blankModel);
		costLabel.setText("");
		numberSpinner.setModel(blankSpinnerModel);
		currentUnit = null;
	}

	public static void clearText(JTextArea j){
		j.setText("");
	}

	public static JFrame getAboutDialog() {
		JFrame tempFrame = new JFrame();
		JPanel temp = new JPanel();
		tempFrame.getContentPane().add(temp);
		return tempFrame;
	}

	public static void showScreen(String s) {
		CardLayout cl = (CardLayout)(basePane.getLayout());
		cl.show(basePane, s);
	}

	public static void setClearCell(JList<?> j){
		j.setCellRenderer(new ClearCell());
	}

	public static void showUnit(AbstractUnit unit) {
		if(unit instanceof Unit){
			currentUnit = (Unit) unit;
			if(unit != null){
				((Unit) unit).loadModels();
				CardLayout cl1 = (CardLayout)(bottomPanel.getLayout());
				cl1.show(bottomPanel, "add");
				CardLayout cl2 = (CardLayout)(statsCard.getLayout());
				int type = ((Unit) unit).getType();
				cl2.show(statsCard, String.valueOf(type));
				modelNameLabel.setText(unit.getName());
				int[] stats = ((Unit) unit).getStats();
				int i = 0;
				if(type == 0){
					for(JLabel l: statLabelList){
						if(i<8) l.setText(String.valueOf(stats[i++]));
						else l.setText(String.valueOf(stats[i++]) + "+");
					}
				}
				else if(type == 1){
					i = 3;
					for(JLabel l: vehicleStatLabelList)
						l.setText(String.valueOf(stats[i++]));
				}
				else{
					i = 1;
					for(JLabel l: walkerStatLabelList)
						l.setText(String.valueOf(stats[i++]));
				}
				if(statLabelList.get(9).getText().equalsIgnoreCase("0+")) statLabelList.get(9).setText("-");
				CardLayout cl = (CardLayout)(cardPanel.getLayout());
				cl.show(cardPanel, ((Unit) unit).getCardName());
				
				rulesList.setModel(((Unit) unit).getRulesModel());
				gearList.setModel(((Unit) unit).getGearModel());
				
				costLabel.setText(((Unit) unit).getCost());
				numberSpinner.setModel(((Unit) unit).getNumberModel());
				((Unit) unit).update();
				for(ChangeListener s: numberSpinner.getChangeListeners())
					if(s instanceof SpinnerListener) 
						((SpinnerListener) s).setUnit((Unit) unit);
				showModelList((Unit) unit);
			}
		}
		else if(unit != null && unit instanceof Model){
			currentUnit = ((Model) unit).getParent();
			CardLayout cl1 = (CardLayout)(bottomPanel.getLayout());
			cl1.show(bottomPanel, "add");
			CardLayout cl2 = (CardLayout)(statsCard.getLayout());
			int type = ((Model) unit).getType();
			cl2.show(statsCard, String.valueOf(type));
			modelNameLabel.setText(unit.getName());
			int[] stats = ((Model) unit).getStats();
			int i = 0;
			if(type == 0){
				for(JLabel l: statLabelList){
					if(i<8) l.setText(String.valueOf(stats[i++]));
					else l.setText(String.valueOf(stats[i++]) + "+");
				}
			}
			else if(type == 1){
				i = 3;
				for(JLabel l: vehicleStatLabelList)
					l.setText(String.valueOf(stats[i++]));
			}
			else{
				i = 1;
				for(JLabel l: walkerStatLabelList)
					l.setText(String.valueOf(stats[i++]));
			}
			if(statLabelList.get(9).getText().equalsIgnoreCase("0+")) statLabelList.get(9).setText("-");
			CardLayout cl = (CardLayout)(cardPanel.getLayout());
			cl.show(cardPanel, ((Model) unit).getCardName());
			
			rulesList.setModel(((Model) unit).getRulesModel());
			gearList.setModel(((Model) unit).getGearModel());
			rulesList.setVisible(true);
			gearList.setVisible(true);
		}
		else clear();
	}

	public static void showModelList(Unit unit) {
		DefaultMutableTreeNode unitRoot = new DefaultMutableTreeNode(unit.name);
		DefaultTreeModel tempModel = new DefaultTreeModel(unitRoot);
		for(Model model: unit.possibleModels){
			if(model.currentSize > 1){
				DefaultMutableTreeNode modelRoot = new DefaultMutableTreeNode(model);
//				for(int i=0;i<model.currentSize;i++)
//					modelRoot.add(new DefaultMutableTreeNode(model.getName()));
				unitRoot.add(modelRoot);
			}
			else if(model.currentSize == 1) unitRoot.add(new DefaultMutableTreeNode(model));
		}
		unitModelsTree.setModel(tempModel);
		unitModelsTree.expandRow(0);
		unitModelsTree.setVisible(true);
	}

	static class PointsDialog extends JDialog implements PropertyChangeListener {

		public static JSpinner pointsSpinner;
		private JOptionPane optionPane;
		private String set = "Set";
		private String cancel = "Cancel";


		public PointsDialog() {
			super(Main.gui, true);
			setTitle("Set Points");
			pointsSpinner = new JSpinner(new SpinnerNumberModel(1000, 0, 100000, 10));

			setSize(325, 150);
			setLocation(400, 200);

			String s = "Enter the number of points" + Main.lb + "you want to use in your army";
			Object[] array = {s, pointsSpinner};
			Object[] options = {set, cancel};

			optionPane = new JOptionPane(array, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null, options, options[0]);
			setContentPane(optionPane);
			pointsSpinner.addChangeListener(new SpinnerListener(pointsSpinner, null));
			optionPane.addPropertyChangeListener(this);
			setVisible(true);
		}

		public void propertyChange(PropertyChangeEvent e) {
			String prop = e.getPropertyName();

			if(isVisible() && (e.getSource() == optionPane) && (JOptionPane.VALUE_PROPERTY.equals(prop) || JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
				Object value = optionPane.getValue();

				if(value == JOptionPane.UNINITIALIZED_VALUE) return;
				optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

				if(set.equals(value)) {
					Main.pointsValue = (Integer) pointsSpinner.getValue();
					showScreen("create");
					changeOption.setEnabled(true);
					if(Main.currentArmy != null) Main.currentArmy.updatePointsLabel();
				} 
				setVisible(false);
			}
		}
	}

	static class ClearCell extends JLabel implements ListCellRenderer<Object> {

		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
			//			if(!isExpandedIndex(list, index)){
			String s = value.toString();
			setText(s);
			if (isSelected) {
				setBackground(Color.BLACK);
				setForeground(Color.WHITE);
			} else {
				setBackground(Color.BLACK);
				setForeground(fontColor);
			}
			setEnabled(list.isEnabled());
			setFont(list.getFont().deriveFont(Font.BOLD));
			setOpaque(false);
			return this;
			//			}
			//			else{
			//				String s = value.toString();
			//				setText("       " + s);
			//				if (isSelected) {
			//					setBackground(Color.BLACK);
			//					setForeground(Color.WHITE);
			//				} else {
			//					setBackground(Color.BLACK);
			//					setForeground(fontColor);
			//				}
			//				setEnabled(list.isEnabled());
			//				setFont(list.getFont().deriveFont(12F));
			//				setOpaque(false);
			//				return this;
			//			}
		}

		//		private boolean isExpandedIndex(JList<?> list, int i){
		//			if(list.getSelectionModel() instanceof SpoilerModel){
		//				for(Integer j: ((SpoilerModel) list.getSelectionModel()).expandedIndexes.keySet())
		//					for(int k=0;k<((SpoilerModel) list.getSelectionModel()).expandedIndexes.get(j).size();k++)
		//						if(j + k + 1 == i) return true;
		//			}
		//			return false;
		//		}
	}

	class PopClickListener extends MouseAdapter {

		private JList<? extends AbstractUnit> jList;
		private JTree tree;
		private PopUp jPopupMenu;

		public PopClickListener(JList<? extends AbstractUnit> list, int i){
			jList = list;
			jPopupMenu = new PopUp(i);
		}

		public PopClickListener(JTree j){
			tree = j;
			jPopupMenu = new PopUp(0);
		}

		public void mousePressed(MouseEvent e)  {
			check(e);
		}

		public void mouseReleased(MouseEvent e) {
			check(e);
		}

		public void check(MouseEvent e) {
			if (e.isPopupTrigger()) {
				if(tree == null){
					if(jList.getCellBounds(jList.locationToIndex(e.getPoint()), jList.locationToIndex(e.getPoint()) + 1) != null){
						if(jList.getCellBounds(jList.locationToIndex(e.getPoint()), jList.locationToIndex(e.getPoint()) + 1).contains(e.getPoint())){
							jList.setSelectedIndex(jList.locationToIndex(e.getPoint())); 
							jPopupMenu.show(jList, e.getX(), e.getY()); 
						}
					}
				} else {
					int i = tree.getClosestRowForLocation(e.getX(), e.getY());
					if(tree.getRowBounds(i).contains(e.getPoint())){
						tree.setSelectionInterval(i, i);
						if(((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent()).getUserObject() instanceof Unit) jPopupMenu.show(tree, e.getX(), e.getY());
						else if(((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent()).getUserObject() instanceof Model) new PopUp(2).show(tree, e.getX(), e.getY());
					}
				}
			}
		}
	}

	class PopUp extends JPopupMenu {

		/*TODO work on these listeners
		 * make sure to remove selection from race list when editing 
		 * and set current unit to the editing unit
		 */
		public PopUp(int i){
			if(i==0){
				JMenuItem remove = new JMenuItem("Remove from army");
				remove.addActionListener(new Listener("Remove"));
				add(remove);

				JMenuItem edit = new JMenuItem("Edit Unit");
				edit.addActionListener(new Listener("edit"));
				add(edit);
			}
			else if(i==1){
				JMenuItem add = new JMenuItem("Quick-add to Army");
				add.addActionListener(new Listener("addUnit"));
				add(add);
			}
			else if(i==2){
				JMenuItem remove = new JMenuItem("Remove from Unit");
				remove.addActionListener(new Listener("removeModel"));
				add(remove);

				JMenuItem edit = new JMenuItem("Edit Model");
				edit.addActionListener(new Listener("editModel"));
				add(edit);
			}
		}
	}

	class ClearTreeNode extends DefaultTreeCellRenderer{

		private ImageIcon slotIcon;

		public ClearTreeNode(){
			super();

			slotIcon = picMap.get("slotIcon");

			setLeafIcon(null);
			setClosedIcon(null);
			setOpenIcon(null);

			setTextSelectionColor(Color.WHITE);
			setTextNonSelectionColor(fontColor);
			setBackgroundSelectionColor(Color.BLACK);
			setBackgroundNonSelectionColor(Color.BLACK);
			setOpaque(false);
		}

		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel,  expanded, leaf, row, hasFocus);
			setBorderSelectionColor(null);
			Object nodeObj = ((DefaultMutableTreeNode) value).getUserObject();

			if (!(nodeObj instanceof AbstractUnit) && (((DefaultMutableTreeNode) value).getChildCount() > 0 || ((DefaultMutableTreeNode) value).getParent() == null)) 
				setIcon(slotIcon);
			else {
				if(nodeObj instanceof Unit) setText(((Unit) nodeObj).name + "   " + ((Unit) nodeObj).cost + " pts");
				setIcon(null);
			} 
			return this;
		}
	}

	class NoIconUI extends BasicTreeUI{
		@SuppressWarnings("unused")
		private boolean paintLines = true;
		@SuppressWarnings("unused")
		private boolean lineTypeDashed;
		@SuppressWarnings("unused")
		private long timeFactor = 1000L;

		public NoIconUI(){
			super();
			expandedIcon = null;
			collapsedIcon = null;
		}

		protected void installDefaults() {
			if(tree.getBackground() == null || tree.getBackground() instanceof UIResource) {
				tree.setBackground(UIManager.getColor("Tree.background"));
			}
			if(getHashColor() == null || getHashColor() instanceof UIResource) {
				setHashColor(UIManager.getColor("Tree.hash"));
			}
			if (tree.getFont() == null || tree.getFont() instanceof UIResource)
				tree.setFont( UIManager.getFont("Tree.font") );

			setExpandedIcon(null);
			setCollapsedIcon(null);

			setLeftChildIndent(((Integer)UIManager.get("Tree.leftChildIndent")).
					intValue());
			setRightChildIndent(((Integer)UIManager.get("Tree.rightChildIndent")).
					intValue());

			LookAndFeel.installProperty(tree, "rowHeight",
					UIManager.get("Tree.rowHeight"));

			largeModel = (tree.isLargeModel() && tree.getRowHeight() > 0);

			Object scrollsOnExpand = UIManager.get("Tree.scrollsOnExpand");
			if (scrollsOnExpand != null) {
				LookAndFeel.installProperty(tree, "scrollsOnExpand", scrollsOnExpand);
			}

			paintLines = UIManager.getBoolean("Tree.paintLines");
			lineTypeDashed = UIManager.getBoolean("Tree.lineTypeDashed");

			Long l = (Long)UIManager.get("Tree.timeFactor");
			timeFactor = (l!=null) ? l.longValue() : 1000L;

			Object showsRootHandles = UIManager.get("Tree.showsRootHandles");
			if (showsRootHandles != null) {
				LookAndFeel.installProperty(tree,
						JTree.SHOWS_ROOT_HANDLES_PROPERTY, showsRootHandles);
			}
		}
	}

	class DeselectModel extends DefaultListSelectionModel
	{
		boolean gestureStarted = false;
		String list;
		int index;

		public DeselectModel(String s){
			list = s;
		}

		public boolean isNonSelect(){
			return list.equalsIgnoreCase("rules") || list.equalsIgnoreCase("gear");
		}

		public void setSelectionInterval(int index0, int index1) {
			if(isNonSelect()) return;
			if(isSelectedIndex(index0) && !gestureStarted) super.removeSelectionInterval(index0, index1);
			else {
				if(list.equalsIgnoreCase("race")){ }
				super.setSelectionInterval(index0, index1);
			}
			gestureStarted = true;
		}

		public void setValueIsAdjusting(boolean isAdjusting) {
			if(!isAdjusting) gestureStarted = false;
		}
	}

	class PaneCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean hasFocus) {
			JPanel tempPane = new JPanel();
			FlowLayout tempLayout = new FlowLayout();
			tempLayout.setAlignment(LEADING);
			tempPane.setLayout(tempLayout);
			tempPane.add(new JSpinner());
			JLabel label = new JLabel(((Model) value).getName());
			label.setForeground(fontColor);
			tempPane.add(label);
			tempPane.setOpaque(false);
			return tempPane;
		}
	}
}