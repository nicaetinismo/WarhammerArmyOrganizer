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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.ListCellRenderer;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JSpinner;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.awt.FlowLayout;
import javax.swing.JToggleButton;


@SuppressWarnings("serial")
public class GUI extends JFrame {

	private static JPanel basePane = new JPanel();
	private static JPanel armyCreationPane;
	private static JPanel mainMenuPane;
	public static JComboBox<String> raceCombo;
	public static JList<Unit> raceList;
	public static JLabel modelNameLabel;
	public static JLabel lblWs, lblBs, lblS, lblT, lblW, lblI, lblA, lblLd, lblSv, lblIS, lblWsV, lblBsV, 
	lblSV, lblTV, lblWV, lblIV, lblAV, lblLdV, lblSvV, lblISV, lblVBS, lblVFA, lblVSA, 
	lblVBA, lblVBSV, lblVFAV, lblVSAV, lblVBAV, lblWWS, lblWBS, lblWS, lblWFA, lblWSA, 
	lblWBA, lblWI, lblWA, lblWWSV, lblWBSV, lblWSV, lblWFAV, lblWSAV, lblWBAV, lblWIV, 
	lblWAV, lblVHP, lblVHPV, lblWHP, lblWHPV;
	public static ArrayList<JLabel> statLabelList = new ArrayList<JLabel>();
	public static ArrayList<JLabel> editStatLabelList = new ArrayList<JLabel>();
	public static ArrayList<JLabel> vehicleStatLabelList = new ArrayList<JLabel>();
	public static ArrayList<JLabel> editVehicleStatLabelList = new ArrayList<JLabel>();
	public static ArrayList<JLabel> walkerStatLabelList = new ArrayList<JLabel>();
	public static ArrayList<JLabel> editWalkerStatLabelList = new ArrayList<JLabel>();
	private JLabel lblRules;
	public static JLabel costLabel = new JLabel("test");
	public static int layerMarker = 1;
	private JPanel panel_5;
	private JLabel lblCost;
	public static JList<String> rulesList;
	public static JList<String> gearList;
	private static final Font statsFont = new Font("Serif", Font.BOLD, 14);
	private static final Dimension statsDimension = new Dimension(10, 12);
	public static JPanel statsCard = new JPanel();
	public static JPanel statsPanelInf = new JPanel();
	public static JPanel statsPanelVeh = new JPanel();
	public static JPanel statsPanelWalker = new JPanel();
	private static final DefaultListModel<String> blankModel = new DefaultListModel<String>();
	private JPanel panel_2;
	public static JPanel bottomPanel;
	private JPanel blankPanel;
	public static JPanel cardPanel;
	public static JPanel vehiclePanel;
	public static JPanel editVehiclePanel = new JPanel();
	public static JPanel walkerPanel;
	public static JPanel editWalkerPanel = new JPanel();
	public static JPanel editInfantryPanel = new JPanel();
	public static ArrayList<String> cardNames = new ArrayList<String>();
	private JLabel lblAmount;
	public static JSpinner numberSpinner;
	public static SpinnerNumberModel blankSpinnerModel = new SpinnerNumberModel(0, 0, 0, 1);
	public static ImageIcon background;
	public static JButton loadButton;
	public static JButton createButton;
	public static JPanel saveLoad = new JPanel();
	public static JMenu fileMenu = new JMenu("File");
	public static JMenuBar menuBar = new JMenuBar();
	public static JMenuItem saveOption = new JMenuItem("Save");
	public static JMenuItem saveAsOption = new JMenuItem("Save As...");
	public static JMenuItem newOption = new JMenuItem("New Army");
	public static JMenuItem openOption = new JMenuItem("Load Army");
	public static JMenuItem changeOption = new JMenuItem("Change point limit");
	public static JScrollPane scrollPane_8;
	public static final String[] armyListStrings = {"hq", "troops", "elites", "fast attack", "heavy support", "transports"};
	public final static Color fontColor = new Color(0xFF, 0xFF, 0x99);
	private JPanel editPanel;
	private JScrollPane scrollPane_9;
	public static JPanel editUpgradePane;
	private JLabel label;
	public static JPanel editStatsPane;
	private JLabel label_1;
	public static JSpinner editNumberSpinner;
	private JPanel panel_8;
	private JLabel label_2;
	public static JLabel editCostLbl;
	private JScrollPane scrollPane_10;
	public static JLabel editNameLbl;
	private JLabel label_5;
	private JButton btnRemoveFromArmy;
	public static JList<String> editRulesList;
	public static JList<String> editGearList;
	public static JList<AbstractUnit> armyList;
	public static JLabel lblCurrentArmy;
	private JScrollPane scrollPane_2;
	private JList<JToggleButton> unitModelsList;

	@SuppressWarnings("unchecked")
	public GUI() {
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

		if(Main.debug){
			if(Main.isMac()){
				loadButton.setIcon(new ImageIcon("/Users/nicaetinismo/Documents/workspace/Warhammer Program/src/load button background.png"));
				createButton.setIcon(new ImageIcon("/Users/nicaetinismo/Documents/workspace/Warhammer Program/src/button background.png"));
				background = new ImageIcon("/Users/nicaetinismo/Documents/workspace/Warhammer Program/bin/background.png");
			}
			else{
				loadButton.setIcon(new ImageIcon(System.getProperty("user.home") + "\\Dropbox\\Warhammer Army Organizer\\src\\load button background.png"));
				createButton.setIcon(new ImageIcon(System.getProperty("user.home") + "\\Dropbox\\Warhammer Army Organizer\\src\\button background.png"));
				background = new ImageIcon(System.getProperty("user.home") + "\\Dropbox\\Warhammer Army Organizer\\src\\background.png");
			}
		}
		else {
			try {

				loadButton.setIcon(new ImageIcon(ImageIO.read(GUI.class.getClassLoader().getResourceAsStream("load button background.png"))));
				createButton.setIcon(new ImageIcon(ImageIO.read(GUI.class.getClassLoader().getResourceAsStream("button background.png"))));
				background = new ImageIcon(ImageIO.read(GUI.class.getClassLoader().getResourceAsStream("background.png")));
				Main.logln("loaded images");
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

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
		gearList.addListSelectionListener(new ListListener(gearList, 2));
		cardPanel.setLayout(new CardLayout(0, 0));

		rulesList = new JList<String>();
		setClearCell(rulesList);
		rulesList.setOpaque(false);
		rulesList.setBorder(null);
		scrollPane_4.setViewportView(rulesList);
		rulesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rulesList.setSelectionModel(new DeselectModel("rules"));
		rulesList.addListSelectionListener(new ListListener(rulesList, 1));
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

		armyList = new JList<AbstractUnit>();
		armyList.setFont(new Font("Tahoma", Font.PLAIN, 14));
		armyList.setOpaque(false);
		armyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		armyPane.setViewportView(armyList);
		armyList.addMouseListener(new PopClickListener(armyList, 0));
		setClearCell(armyList);
		armyList.setSelectionModel(new SpoilerModel(armyList));

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

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(427, 0, 252, 71);
		panel_2.add(scrollPane);

		JPanel modelListPane = new JPanel();
		scrollPane.setViewportView(modelListPane);
		modelListPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(6, 190, 409, 118);
		panel_2.add(scrollPane_2);

		unitModelsList = new JList<JToggleButton>();
		scrollPane_2.setViewportView(unitModelsList);

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
		raceList.addListSelectionListener(new ListListener(raceList, 0));
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
			if(isSelectedIndex(index0) && !gestureStarted) {
				super.removeSelectionInterval(index0, index1);
			}
			else {
				if(list.equalsIgnoreCase("race")){ }
				super.setSelectionInterval(index0, index1);
			}
			gestureStarted = true;
		}

		public void setValueIsAdjusting(boolean isAdjusting) {
			if(!isAdjusting){
				gestureStarted = false;
			}
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

	class SpoilerModel extends DefaultListSelectionModel
	{
		boolean gestureStarted = false;
		private JList<AbstractUnit> list;
		private TreeMap<Integer, ArrayList<Byte>> expandedIndexes = new TreeMap<Integer, ArrayList<Byte>>();

		public SpoilerModel(JList<AbstractUnit> j){
			list = j;
		}

		public void setSelectionInterval(int index0, int index1) {
			if(isSelectedIndex(index0) && !gestureStarted){
				for(Integer i: expandedIndexes.keySet()){
					if(index0 == i){
						DefaultListModel<AbstractUnit> temp = (DefaultListModel<AbstractUnit>) list.getModel();
						for(int j=0;j<expandedIndexes.get(i).size();j++) temp.remove(index0 + 1);
						super.setSelectionInterval(index0, index1);
						gestureStarted = true;
						updateExpandedIndexes(index0, false);
						return;
					}
				}
				DefaultListModel<AbstractUnit> temp = (DefaultListModel<AbstractUnit>) list.getModel();
				try{ 
					expandedIndexes.put(index0, ((Unit) temp.getElementAt(index0)).showModels(temp, index0));
				} catch(ClassCastException e) {}
				updateExpandedIndexes(index0, true);
				list.setModel(temp);
				super.setSelectionInterval(index0, index1);
			}
			else super.setSelectionInterval(index0, index1);
			gestureStarted = true;
		}

		private void updateExpandedIndexes(int index0, boolean b) {
			TreeMap<Integer, ArrayList<Byte>> temp1 = new TreeMap<Integer, ArrayList<Byte>>();
			ArrayList<Integer> temp2 = new ArrayList<Integer>();
			for(Integer j: expandedIndexes.keySet()){
				if(j > index0){
					if(b) temp1.put(j + expandedIndexes.get(index0).size(), expandedIndexes.get(j));
					else temp1.put(j - expandedIndexes.get(index0).size(), expandedIndexes.get(j));
					temp2.add(j);
				}
			}
			for(int i: temp2) expandedIndexes.remove(i);
			if(!b) expandedIndexes.remove(index0);
			expandedIndexes.putAll(temp1);
		}

		public void setValueIsAdjusting(boolean isAdjusting) {
			if(!isAdjusting) gestureStarted = false;
		}
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

				if(value == JOptionPane.UNINITIALIZED_VALUE) {
					return;
				}
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
			if(!isExpandedIndex(list, index)){
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
			}
			else{
				String s = value.toString();
				setText("       " + s);
				if (isSelected) {
					setBackground(Color.BLACK);
					setForeground(Color.WHITE);
				} else {
					setBackground(Color.BLACK);
					setForeground(fontColor);
				}
				setEnabled(list.isEnabled());
				setFont(list.getFont().deriveFont(12F));
				setOpaque(false);
				return this;
			}
		}

		private boolean isExpandedIndex(JList<?> list, int i){
			if(list.getSelectionModel() instanceof SpoilerModel){
				for(Integer j: ((SpoilerModel) list.getSelectionModel()).expandedIndexes.keySet())
					for(int k=0;k<((SpoilerModel) list.getSelectionModel()).expandedIndexes.get(j).size();k++)
						if(j + k + 1 == i) return true;
			}
			return false;
		}
	}


	class PopClickListener extends MouseAdapter {

		private JList<? extends AbstractUnit> jList;
		private PopUp jPopupMenu;

		public PopClickListener(JList<? extends AbstractUnit> list, int i){
			jList = list;
			jPopupMenu = new PopUp(i);
		}

		public void mousePressed(MouseEvent e)  {
			check(e);
		}

		public void mouseReleased(MouseEvent e) {
			check(e);
		}

		public void check(MouseEvent e) {
			if (e.isPopupTrigger()) {
				if(jList.getCellBounds(jList.locationToIndex(e.getPoint()), jList.locationToIndex(e.getPoint()) + 1) != null){
					if(jList.getCellBounds(jList.locationToIndex(e.getPoint()), jList.locationToIndex(e.getPoint()) + 1).contains(e.getPoint())){
						jList.setSelectedIndex(jList.locationToIndex(e.getPoint())); 
						jPopupMenu.show(jList, e.getX(), e.getY()); 
					}
				}
			}
		}
	}


	public static class PopUp extends JPopupMenu {

		public PopUp(int i){
			if(i==0){
				JMenuItem remove = new JMenuItem("Remove from army");
				remove.addActionListener(new Listener("Remove"));
				add(remove);

				JMenuItem edit = new JMenuItem("Edit Unit");
				edit.addActionListener(new Listener("edit"));
				add(edit);
			}
			else{
				JMenuItem add = new JMenuItem("Quick-add to Army");
				add.addActionListener(new Listener("addUnit"));
				add(add);
			}
		}
	}

	static class UpgradeChoiceDialog extends JDialog {

		private JList<Object> list = new JList<Object>();
		private JPanel pane;
		private Upgrade upgrade;
		private DefaultListModel<Object> listModel;

		public UpgradeChoiceDialog(Upgrade u) {
			super(Main.gui, true);
			upgrade = u;
			makeList();
			setTitle("Remove Gear");

			setSize(325, 150);
			setLocation(400, 200);

			setContentPane(pane);
			list.addListSelectionListener(new ListListener(list, this));
			list.setModel(listModel);
			pane.add(new JLabel("choose a piece of wargear to remove"));
			pane.add(list);
			setVisible(true);
		}

		public Upgrade getUpgrade(){
			return upgrade;
		}

		private void makeList(){
			DefaultListModel<Object> d = new DefaultListModel<Object>();
			for(String s: upgrade.removeGearList){
				if(s.contains("/")){
					for(int i=0;i<s.length();i++){
						if(s.charAt(i) == '/'){
							String s1 = s.substring(0, i).trim();
							String[] sA = new String[upgrade.file.getGearModel().getSize()];
							upgrade.file.getGearModel().copyInto(sA);
							for(String s2: sA) if(s1.equalsIgnoreCase(s2)) d.addElement(s1);
							s = new StringBuilder(s).delete(0, i+1).toString().trim();
							i = -1;
						}
						else if(i+1 == s.length()){
							String s1 = s.substring(0, i).trim();
							String[] sA = new String[upgrade.file.getGearModel().getSize()];
							upgrade.file.getGearModel().copyInto(sA);
							for(String s2: sA) if(s1.equalsIgnoreCase(s2)) d.addElement(s1);
							listModel = d;
						}
					}
				}
			}
		}
	}
}
