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
 * ListItem.java
 *
 * Created on December 23, 2004, 3:01 PM
 */

package com.sun.webui.jsf.model.list;

/**
 *
 * @author avk
 */
public class ListItem {
    
    Object valueObject;
    String label;
    String value;
    String description = null;
    boolean selected = false;
    boolean disabled = false;
    boolean title = false; 
    boolean escape = true;
    
    public ListItem(String label) {
        this.label = label;
        this.valueObject = label;
    }
    
    public ListItem(Object realValue, String label) {
        this.label = label;
        this.valueObject = realValue;
    }
    
    public ListItem(Object realValue, String label, boolean disabled) {
        this.label = label;
        this.valueObject = realValue;
        this.disabled = disabled;
    }
    public ListItem(Object realValue, String label, String description,
            boolean disabled) {
        this.label = label;
        this.valueObject = realValue;
        this.description = description;
        this.disabled = disabled;
    }
    public ListItem(Object realValue, String label, String description,
            boolean disabled, boolean escape) {
        this.label = label;
        this.valueObject = realValue;
        this.description = description;
        this.disabled = disabled;
        this.escape = escape;
    }
    
    public String getLabel() {
        return label;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean isSelected() {
        return selected;
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public Object getValueObject() {
        return valueObject;
    }
    
    public boolean isDisabled() {
        return disabled;
    }
    
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
    
    public void setTitle(boolean title) {
        this.title = title;
    }
    
    public boolean isTitle() {
        return title;
    }
    
    /**
     * Get whether the label must be escaped. When true, 
     * all characters in the label are displayed literally. When false, 
     * markup in the label is stripped and escape sequences, such as 
     * <code>&amp;nbsp;</code> and <code>&amp;amp;</code>, are displayed as
     * evaluated.
     */
    public boolean isEscape() {
        return escape;
    }

    /**
     * Set whether the label must be escaped. When true, 
     * all characters in the label are displayed literally. When false, 
     * markup in the label is stripped and escape sequences, such as 
     * <code>&amp;nbsp;</code> and <code>&amp;amp;</code>, are displayed as
     * evaluated.
     */
    public void setEscape(boolean escape) {
        this.escape = escape;
    }
}
