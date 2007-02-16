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

package com.sun.webui.jsf.model;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author deep
 */
public class FileChooserItem implements ResourceItem, Serializable {
    
    
    File item = null;
    String itemKey = null;
    String itemLabel  = null;
    boolean itemDisabled = true;
    
    /** Creates a new instance of FileChooserItem */
    public FileChooserItem(File file) {
        
        this.item = file;
        StringBuffer buffer = new StringBuffer();
        if (item.isDirectory()) {
            buffer.append("folder=");
        } else {
            buffer.append("file=");
        }
        buffer.append(item.getAbsolutePath());
        this.itemKey = buffer.toString();
    }
 
    /**
     * Returns an object representing the value of this resource item.
     * For the default case of the FileChooser this would be a File
     * object.
     *
     * @return an object which is the value of the ResourceItem.
     */
    public Object getItemValue() {
        return item;
    }
        
    /**
     * Returns a String representing the item key.
     * 
     *
     * @return returns an object representing the resource item
     */
    public String getItemKey() {
        return this.itemKey;
    }    
    
    /**
     * Set the item key.
     * 
     *
     * @param key - the resource item key
     */
    public void setItemKey(String key) {
        if (key != null) {
            this.itemKey = key;
        }
    }
        
    /**
     * Returns an object representing the resource item.
     * 
     *
     * @return returns an object representing the resource item
     */
    public String getItemLabel() {
        return this.itemLabel;
    }
        
    /**
     * Returns an object representing the resource item.
     * 
     *
     * @return returns an object representing the resource item
     */
    public void setItemLabel(String label) {
        
        if (label != null) {
            this.itemLabel = label;
        }
    }
    /**
     * Returns an boolean value indicating if the item should be selectable
     * in the filechooser's listbox.
     * 
     *
     * @return true if the item in the listbox should be disabled. 
     */
    public boolean isItemDisabled() {
        return this.itemDisabled;
    }
        
    /**
     * Sets the item disabled flag. If set to true the item should 
     * not be selectable.
     * 
     * 
     *
     * @enabled flag when set to true indicates item is not selectable.
     */
    public void setItemDisabled(boolean disabled) {
        this.itemDisabled = disabled;
    }        
        
    /**
     * Returns a flag indicating if the resource item is a container. If true 
     * the item is a container item.
     * 
     *
     * @return true if the item is a container, false otherwise.
     */
    public boolean isContainerItem() {
        return item.isDirectory();
    }

    public boolean equals(Object object) {

	if (!(object instanceof ResourceItem)) {
	    return false;
	}
	ResourceItem resourceItem = (ResourceItem)object;

	boolean flags = isContainerItem() == resourceItem.isContainerItem() &&
		isItemDisabled() == resourceItem.isItemDisabled();

	if (!flags) {
	    return false;
	}
	if (!objectEquals(getItemValue(), resourceItem.getItemValue())) {
	    return false;
	}
	if (!objectEquals(getItemKey(), resourceItem.getItemKey())) {
	    return false;
	}
	if (!objectEquals(getItemLabel(), resourceItem.getItemLabel())) {
	    return false;
	}
	return true;
    }

    private boolean objectEquals(Object obj0, Object obj1) {
	if (obj0 == null) {
	    if (obj1 == null) {
		return true;
	    } else {
		return false;
	    }
	} else {
	    // Should handle null
	    //
	    return !obj0.equals(obj1);
	}
    }
        
}
