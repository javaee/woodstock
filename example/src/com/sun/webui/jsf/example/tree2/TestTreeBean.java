/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sun.webui.jsf.example.tree2;

import com.sun.webui.jsf.component.Tree2;
import com.sun.webui.jsf.component.TreeNode2;
import com.sun.webui.jsf.component.StaticText;
import com.sun.webui.jsf.event.TreeNode2Event;
import com.sun.webui.jsf.example.util.ExampleUtilities;
import com.sun.webui.jsf.example.index.IndexBackingBean;

public class TestTreeBean {

    public final static String SHOW_TREE2 = "showTree2Index";
 
    // a new tree
    private Tree2 newTree = null;
    
    
    private StaticText statictext = null;
    private String selectedNode = "No nodes selected";
    
    /** Action handler when navigating to the tree2 example */
    public String showTree2() {
	return SHOW_TREE2;
    }
         
    /** Action handler when navigating to the main example index. */
    public String showExampleIndex() {
	_reset();
        return IndexBackingBean.INDEX_ACTION;
    }
    
    public StaticText getStatictext() {
        statictext = new StaticText();
        statictext.setText(selectedNode);
        return statictext;
    }
    
    public void setStatictext(StaticText tf) {
        this.statictext = tf;
        if (selectedNode != null) {
            this.statictext.setText(selectedNode);
        } else {
            this.statictext.setText("");
        }
    }
    
    public void setNewTree(Tree2 tree) {
        System.out.println("set called...");
	this.newTree = tree;
    }

    // public Tree getNewTree() {
    public Tree2 getNewTree() {
        
        if (newTree != null) {
            return newTree;
        }
        
        System.out.println("get called...");
        newTree = new Tree2();
	newTree.setLabel("Yet Another Test Tree");
	
	TreeNode2 state1 = createNewTreeNode("NewYork");
	newTree.getChildren().add(state1);

	TreeNode2 ithaca = createNewTreeNode("Ithaca");
	TreeNode2 rochester = createNewTreeNode("Rochester");
	TreeNode2 troy = createNewTreeNode("Troy");
	state1.getChildren().add(ithaca);
	state1.getChildren().add(rochester);
	state1.getChildren().add(troy);

	TreeNode2 state2 = createNewTreeNode("Massachussets");
	newTree.getChildren().add(state2);

	TreeNode2 camb = createNewTreeNode("Cambridge");
	TreeNode2 water = createNewTreeNode("WaterTown");
        TreeNode2 well = createNewTreeNode("Wellesly");
        TreeNode2 bed = createNewTreeNode("Bedford");
        TreeNode2 woburn = createNewTreeNode("Woburn");
        TreeNode2 reading = createNewTreeNode("Reading");
        reading.setImageURL("/images/yahoo.jpg");
	state2.getChildren().add(camb);
	state2.getChildren().add(water);
        state2.getChildren().add(well);
        state2.getChildren().add(bed);
        state2.getChildren().add(woburn);
        state2.getChildren().add(reading);

       
	newTree.setExpanded(true);
        
        ExampleUtilities.setMethodExpression(newTree, "nodeSelectedActionListenerExpression", 
            "#{TestTreeBean.nodeClicked}", null, new Class[]{TreeNode2Event.class});
        
        ExampleUtilities.setMethodExpression(state1, "nodeSelectedActionListenerExpression", 
            "#{TestTreeBean.nodeClicked}", null, new Class[]{TreeNode2Event.class});
        ExampleUtilities.setMethodExpression(state2, "nodeSelectedActionListenerExpression", 
            "#{TestTreeBean.nodeClicked}", null, new Class[]{TreeNode2Event.class});
        ExampleUtilities.setMethodExpression(camb, "nodeSelectedActionListenerExpression", 
            "#{TestTreeBean.nodeClicked}", null, new Class[]{TreeNode2Event.class});
        ExampleUtilities.setMethodExpression(reading, "nodeSelectedActionListenerExpression", 
            "#{TestTreeBean.nodeClicked}", null, new Class[]{TreeNode2Event.class});
        
	return newTree;
    }

    private TreeNode2 createNewTreeNode(String name) {
	TreeNode2 tnode = new TreeNode2();
	tnode.setId(name);
	tnode.setLabel(name);
	return tnode;
    }
    
    public void nodeClicked(TreeNode2Event ne) {
        
        String nodeId= ne.getEventNode().getId();
        String eventString = null;
        int eventType = ne.getEventType();
        
        if (eventType == TreeNode2Event.LOADCHILDREN_EVENT) {
            eventString = "LOADCHILDREN";
        } else if (eventType == TreeNode2Event.NODESELECTED_EVENT) {
            eventString = "NODESELECTED";
        }
        
        selectedNode = "Selected Node: " + nodeId;
    }
    
    // backing bean for example5
    
    private Tree2 dynamicTree = null;
    
    public void setDynamicTree(Tree2 tree) {
        System.out.println("set dtree called...");
	this.dynamicTree = tree;
    }

    public Tree2 getDynamicTree() {
        
        System.out.println("get dtree called...");
        if (dynamicTree != null) {
            return dynamicTree;
        }
        
        dynamicTree = new Tree2();
	dynamicTree.setLabel("This Tree has dynamically loaded nodes");
	
	TreeNode2 state1 = createNewTreeNode("NewYork");
	dynamicTree.getChildren().add(state1);

	TreeNode2 ithaca = createNewTreeNode("Ithaca");
	TreeNode2 rochester = createNewTreeNode("Rochester");
	TreeNode2 troy = createNewTreeNode("Troy");
	state1.getChildren().add(ithaca);
	state1.getChildren().add(rochester);
	state1.getChildren().add(troy);
        state1.setExpanded(false);
	TreeNode2 state2 = createNewTreeNode("Massachussets");
	dynamicTree.getChildren().add(state2);

	TreeNode2 camb = createNewTreeNode("Cambridge");
	TreeNode2 water = createNewTreeNode("WaterTown");
        TreeNode2 well = createNewTreeNode("Wellesly");
        TreeNode2 bed = createNewTreeNode("Bedford");
        TreeNode2 woburn = createNewTreeNode("Woburn");
        TreeNode2 reading = createNewTreeNode("Reading");
        reading.setImageURL("/images/yahoo.jpg");
	state2.getChildren().add(camb);
	state2.getChildren().add(water);
        state2.getChildren().add(well);
        state2.getChildren().add(bed);
        state2.getChildren().add(woburn);
        state2.getChildren().add(reading);
        state2.setExpanded(false);
       
	dynamicTree.setExpanded(true);
        dynamicTree.setLoadOnExpand(true);
        
        ExampleUtilities.setMethodExpression(dynamicTree, "toggleActionListenerExpression", 
            "#{TestTreeBean.dNodeClicked}", null, new Class[]{TreeNode2Event.class});
        
        ExampleUtilities.setMethodExpression(state1, "toggleActionListenerExpression", 
            "#{TestTreeBean.dNodeClicked}", null, new Class[]{TreeNode2Event.class});
        ExampleUtilities.setMethodExpression(state2, "toggleActionListenerExpression", 
            "#{TestTreeBean.dNodeClicked}", null, new Class[]{TreeNode2Event.class});
        ExampleUtilities.setMethodExpression(camb, "toggleActionListenerExpression", 
            "#{TestTreeBean.dNodeClicked}", null, new Class[]{TreeNode2Event.class});
        ExampleUtilities.setMethodExpression(reading, "toggleActionListenerExpression", 
            "#{TestTreeBean.dNodeClicked}", null, new Class[]{TreeNode2Event.class});
        
	return dynamicTree;
    }
    
    public void dNodeClicked(TreeNode2Event ne) {
        
        TreeNode2 thisNode = ne.getEventNode();
        String nodeId= thisNode.getId();
        int eventType = ne.getEventType();
        
        if (eventType == TreeNode2Event.LOADCHILDREN_EVENT) {
            thisNode.setExpanded(true);
        } else if (eventType == TreeNode2Event.NODESELECTED_EVENT) {
             
        }
        
        selectedNode = nodeId;
    }
    
     /** Initial all bean values to their defaults */
    private void _reset() {
	// nothing to add here for now.
    }
}