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

import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme;
import com.sun.webui.theme.ThemeImage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;


/**
 * Use <code>Indicator<code> class to create Indicator objects for any component 
 * which needs Indicator.
 * Indicator object contains an image component a type and a sort value.
 * "type" identifies an Indicator. It is the same as the value given to the Alarm
 * "severity" property or to the Alert "type" property.
 * An Indicator object can be constructed by calling any Indicator constructors.
 * <code> Indicator(String imageKey, String type, int sortValue) </code> or 
 * <code> Indicator(UIComponent imageComponent, String type,  int sortValue) </code>
 *
 */
public class Indicator implements Comparable<Indicator>, StateHolder {

    /**
     * Construct an <code>Indicator</code> instance.
     * @param imageKey An image <code>Theme</code> key that can be used
     * to obtain a <code>ThemeImage</code> from a <code>Theme</code>
     * @param type The identifier for this type of <code>Indicator</code>. If
     * two <code>Indicator</code>s have the same <code>type</code> they are
     * equal.
     * @param sortValue The value used to sort this Indicator among other
     * <code>Indicator</code>s.
     * @throws IllegalArgumentException if <code>imageKey</code> or 
     * <code>type</code> is <code>null</code> or an empty string.
     */
    public Indicator(String imageKey, String type, int sortValue) {
	super();
	if (imageKey == null || imageKey.length() == 0 ||
		type == null || type.length() == 0) {
	    throw new IllegalArgumentException(
		"Neither imageKey nor type can be null or empty string.");
	}
	this.imageKey = imageKey;
	this.type = type;
	this.sortValue = sortValue;
    }
        
    /**
     * Construct an <code>Indicator</code> instance.
     * @param imageComponent Represents the image for this <code>Indicator</code>.
     * @param type The identifier for this type of <code>Indicator</code>. If
     * two <code>Indicator</code>s have the same <code>type</code> they are
     * equal.
     * @param sortValue The value used to sort this Indicator among other
     * <code>Indicator</code>s.
     * @throws IllegalArgumentException if <code>imageComponent</code> or 
     * <code>type</code> is <code>null</code> or if <code>type</code.
     * is an empty string.
     */
    public Indicator(UIComponent imageComponent, String type,  int sortValue) {
	super();
	if (imageComponent == null || type == null || type.length() == 0) {
	    throw new IllegalArgumentException(
		"Neither imageComponent nor type can be null or empty string.");
	}
	this.imageComponent = imageComponent;
	this.type = type;
	this.sortValue = sortValue;
    }
    
    /**
     * holds value for type property.
     */
    private String type = null;
    /**
     * holds value for sortValue property.
     */
    private int sortValue = 0;
    /**
     * holds value for imageKey property.
     **/
    private String imageKey = null;
    /**
     * holds value for imageComponent property.
     */
    private UIComponent imageComponent = null;
    
    /**
     * Return the <code>Theme</code> image key if set, else null.
     */
    public String getImageKey() {
	return imageKey;
    }
    
    /**
     * Return a <code>UIComponent</code> for this <code>Indicator</code>.
     * A null value will be return if a theme image is not available for
     * specified image key. 
     * If a <code>imageComponent</code> instance has been set, return it.
     * If no <code>imageComponent</code> instance has been set on this Indicator
     * then <code>imageKey</code> must have been set. Use <code>theme</code>
     * to obtain a <code>imageComponent</code> instance for this
     * <code>Indicator</code> based on <code>imageKey</code> and return it.
     */
    public UIComponent getImageComponent(Theme theme) {
	if (imageComponent != null) {
	    return imageComponent;
	}
	if (imageKey != null) {
               ThemeImage themeImage = theme.getImage(imageKey);
           if (themeImage != null) {
               return ThemeUtilities.getIcon(theme, imageKey);
           }
        } 
	return null;
    }

    /**
     * Return the identifier for this <code>Indicator</code>. If two
     * <code>Indicator</code>s have the same <code>type</code> they
     * are equal.
     */
    public String getType() {
	return type;
    }

    /**
     * Return the value used to sort this <code>Indicator</code>.
     */
    public int getSortValue() {
	return sortValue;
    }
    /**
     * Return zero if <code>this.sortValue</code> is equal to
     * <code>indicator.getSortValue()</code>; positive one if 
     * <code>this.sortValue</code> is less than
     * <code>indicator.getSortValue()</code> or <code>indicator</code>
     * is <code>null</code>; negative one if
     * <code>this.sortValue</code> is greater than
     * <code>indicator.getSortValue()</code>.
     *
     * <em>NOTE THAT HIGHER SORT VALUES IMPLIES LESSER SEVERITY
     * THEREFORE INDICATORS OF HIGHER SEVERITY HAVE LOWER SORT VALUES</em>
     */
    public int compareTo(Indicator indicator) {
	// Always greater than null
	//
	if (indicator == null) {
	    return 1;
	}
	if (this.sortValue < indicator.getSortValue()) {
	    return 1;
	} else 
	if (this.sortValue > indicator.getSortValue()) {
	    return -1;
	} else {
	    return 0;
	}
    }

    /**
     * Return true if <code>this.type.equals((Indicator)obj).getType())</code>
     * returns <code>true</code> else return <code>false</code>.
     */
    public boolean equals(Object obj) {
	// Note that this.type can never be null.
	try {
	    return this.type.equals(((Indicator)obj).getType());
	} catch (Exception e) {
	    return false;
	}
    }
    
    /**
     * Saves the state of the component into an object
     * @param context the FacesContext
     * @return the Object representing the state of the component
     */
    public Object saveState(FacesContext context) {

        Object values[] = new Object[3];
          values[0] = this.imageKey;
          values[1] = new Integer(this.sortValue);
          values[2] = this.imageComponent.saveState(context);
        return (values);
    }

    /**
     * Restore the state of the component.
     * @param context The FacesContext
     * @param state the Object representing the state of the component
     */
    public void restoreState(FacesContext context, Object state) {

        Object values[] = (Object[]) state;
          this.imageKey = (String) values[0];
          this.sortValue = ((Integer) values[1]).intValue();
          this.imageComponent = (UIComponent) values[2];
          this.imageComponent.restoreState(context, values[2]);
      
    }

    private boolean transientValue = false;

    
    /**
     * Returns false, this component needs to save state.
     * @return false
     */
    public boolean isTransient() {
        return false; 
    }

    /**
     * Does nothing
     */
    public void setTransient(boolean transientValue) {
        return;
    }
}

