package main;

import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class Army {

	public ArrayList<Unit> unitList = new ArrayList<Unit>();
	public int currentCost = 0;
	public String race;
	public String fileName;
	private final DefaultMutableTreeNode[] defaultNodes = new DefaultMutableTreeNode[5];
	private final String[] defaultNodeNames = {"HQ", "Troops", "Elites", "Fast Attack", "Heavy Support"};
	private DefaultTreeModel armyTreeModel;
	private final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");


	public Army(String s){
		race = s;
		armyTreeModel = new DefaultTreeModel(rootNode);
		int i = 0;
		for(String s1: defaultNodeNames){
			defaultNodes[i] = new DefaultMutableTreeNode(s1);
			rootNode.add(defaultNodes[i++]);
		}
	}
	
	public Army addUnit(Unit u){
		unitList.add(u);
		u.setArmy(this);
		u.loadSetUpgrades();
		GUI.armyTree.setModel(updateArmyModel());
		GUI.armyTree.setRootVisible(false);
		return this;
	}
	
	public void updatePointsLabel(){

	}
	
	public void update(){
		updateCost();
		updatePointsLabel();
	}

	private void updateCost() {
		currentCost = 0;
		for(Unit u: unitList)
			currentCost += u.cost;
	}

	public void removeUnit(Unit u) {
		u.removeAllUpgrades();
		removeFromList(u);
		update();
		GUI.armyTree.setModel(updateArmyModel());
//		GUI.armyTree.expandRow(0);
		GUI.armyTree.setRootVisible(false);
	}
	
	public void removeUnit(String s){
		for(Unit u: unitList)
			if(u.name.equalsIgnoreCase(s)) 
				removeUnit(u);
	}

	private void removeFromList(Unit u) {
		ArrayList<Unit> temp = new ArrayList<Unit>();
		for(Unit u1: unitList)
			temp.add(u1);
		for(Unit u1: temp){
			if(u1.equals(u)){
				temp.remove(u1);
				break;
			}
		}
		unitList = temp;
		armyTreeModel.removeNodeFromParent(u.getNode());
		armyTreeModel.nodeStructureChanged(defaultNodes[u.getSlot()]);
	}

	public boolean isEmpty() {
		return unitList.isEmpty();
	}

	public TreeModel updateArmyModel(){
		for(Unit u: unitList){
			u.update();
			if(armyTreeModel.getIndexOfChild(rootNode, defaultNodes[u.getSlot()]) == -1){
				rootNode.add(defaultNodes[u.getSlot()]);
				armyTreeModel.nodeStructureChanged(rootNode);
			}
			defaultNodes[u.getSlot()].add(u.getNode());
			GUI.armyTree.expandPath(new TreePath(((DefaultMutableTreeNode) u.getNode()).getPath()));
			armyTreeModel.nodeStructureChanged(defaultNodes[u.getSlot()]);
		}
		for(DefaultMutableTreeNode d: defaultNodes){
			int index = armyTreeModel.getIndexOfChild(rootNode, d);
			if(index != -1 && d.getChildCount() == 0) rootNode.remove(d);
			else if(index != -1){
				GUI.armyTree.expandPath(new TreePath(d.getPath()));
				armyTreeModel.nodeChanged(d);
			}
		}
		return armyTreeModel;
	}
}
