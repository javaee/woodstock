/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 */
/*
 * CbRbGroupBackingBean.java
 *
 * Created on May 22, 2007, 10:58 AM
 */
package com.sun.webui.jsf.example.cbrb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.component.CheckboxGroup;
import com.sun.webui.jsf.example.common.MessageUtil;

public class CbRbGroupBackingBean  {

    // RadioButton group initial selection
    //
    private Object rbvalue = new String(MessageUtil.getMessage("cbrb_radioButton3"));
    private static final String server = MessageUtil.getMessage("cbrb_radioButton1");
    private static final String volume = MessageUtil.getMessage("cbrb_radioButton2");
    private static final String pool = MessageUtil.getMessage("cbrb_radioButton3");

    // Checkbox group initial selection
    //
    private Object[] cbvalue = {
	server,
	pool
    };

    private Option[] array;
    private ArrayList collection;
    private HashMap map;
    private String label; 

    public CbRbGroupBackingBean() {
	array = new Option[3];
        label = MessageUtil.getMessage("cbrb_label");
	collection = new ArrayList(); 
	map = new HashMap();        
	String[][] selectionData = {
	    {server, "/images/tree_server.gif", server, "server image"},
	    {volume, "/images/volumegroup_tree.gif", volume, "volume image"},
	    {pool, "/images/pool_tree.gif", pool, "pool image"}
	};
	for (int i = 0; i < 3; ++i) {
	    array[i] = new Option();
	    array[i].setValue(selectionData[i][0]);
	    array[i].setImage(selectionData[i][1]);
	    array[i].setLabel(selectionData[i][2]);
	    array[i].setImageAlt(selectionData[i][3]);
	    ((ArrayList)collection).add(i, array[i]);
	    map.put(selectionData[i][0], array[i]);
	}

	// init rb/cb required test members
	rbSelection = rbvalue;
	rbSelections = array;
	cbSelection = cbvalue;
	cbSelections = array;
    }

    // binding attribute
    //
    private CheckboxGroup cbGrp;      
    
    public CheckboxGroup getCbGrp() {
	return cbGrp;
    }
    public void setCbGrp(CheckboxGroup cbGrp) {
	this.cbGrp = cbGrp;
    }

    public String getRbGrpLabel() {
	return MessageUtil.getMessage("cbrb_radioButtonGroupLabel");
    }
    public String getCbGrpLabel() {
	return MessageUtil.getMessage("cbrb_checkboxGroupLabel");
    }   
    
    public void setLabel(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;           
    }

    // For "items"
    public ArrayList getCollection() {
	return collection;
    }
    public Option[] getArray() {
	return array;
    }
    public HashMap getMap() {
	return map;
    }

    // For selected rbgrp1 and cbgrp1
    //
    private Object rb1selected = rbvalue;
    public Object getRb1selected() {
	return rb1selected;
    }

    public void setRb1selected(Object rb1selected) {
	this.rb1selected = rb1selected;
    }

    private Object[] cb1selected = cbvalue;
    public Object[] getCb1selected() {
	return cb1selected;
    }

    public void setCb1selected(Object[] cb1selected) {
	this.cb1selected = cb1selected;
    }

    // For selected rbgrp2 and cbgrp2
    //
    private Object rb2selected = rbvalue;
    public Object getRb2selected() {
	return rb2selected;
    }

    public void setRb2selected(Object rb2selected) {
	this.rb2selected = rb2selected;
    }

    private Object[] cb2selected = cbvalue;
    public Object[] getCb2selected() {
	return cb2selected;
    }

    public void setCb2selected(Object[] cb2selected) {
	this.cb2selected = cb2selected;
    }

    // For selected rbgrp3 and cbgrp3
    //
    private Object rb3selected = rbvalue;
    public Object getRb3selected() {
	return rb3selected;
    }

    public void setRb3selected(Object rb3selected) {
	this.rb3selected = rb3selected;
    }

    private Object[] cb3selected = cbvalue;
    public Object[] getCb3selected() {
	return cb3selected;
    }

    public void setCb3selected(Object[] cb3selected) {
	this.cb3selected = cb3selected;
    }

    // For required.jsp
    Object rbSelection;
    Object rbSelections;
    public Object getRbSelections() {
	return rbSelections;
    }
    public void setRbSelection(Object value) {
	rbSelection = value;
    }
    public Object getRbSelection() {
	return rbSelection;
    }
    Object cbSelection;
    Object cbSelections;
    public Object getCbSelections() {
	return cbSelections;
    }
    public void setCbSelection(Object value) {
	cbSelection = value;
    }
    public Object getCbSelection() {
	return cbSelection;
    }
}
