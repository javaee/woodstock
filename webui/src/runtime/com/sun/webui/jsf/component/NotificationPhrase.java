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
package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.theme.Theme;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 * The NotificationPhrase component is used to display a message in the 
 * masthead.
 * 
 * @deprecated See tld docs for more information on how to create a 
 * notificationPhrase without using the notificationPhrase component.
 */
@Component(type="com.sun.webui.jsf.NotificationPhrase", 
    family="com.sun.webui.jsf.NotificationPhrase", 
    displayName="Notification Phrase", tagName="notificationPhrase",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_notification_phrase",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_notification_phrase_props")
public class NotificationPhrase extends ImageHyperlink {
    /**
     * Default constructor.
     */
    public NotificationPhrase() {
        super();
        setRendererType("com.sun.webui.jsf.NotificationPhrase");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.NotificationPhrase";
    }

    public String getIcon() {
        String icon = super.getIcon();
        
        if (icon == null) {
            icon = ThemeImages.ALERT_INFO_MEDIUM;
        }
        
        return icon;
    }
    
    public String getStyleClass() {
        String styleClass = super.getStyleClass();
        
        if (styleClass == null) {
            styleClass = ThemeStyles.MASTHEAD_PROGRESS_LINK;
        }
        
        Theme theme = ThemeUtilities.getTheme(
			      FacesContext.getCurrentInstance());        
        return theme.getStyleClass(styleClass);
    }
    
    public int getBorder() {
        return 0;
    }
    
    public String getAlign() {
        return "middle";        
    }
    
    public String getAlt() {
        String alt = super.getAlt();
        
        if (alt == null) {
	    // Don't convert if null, since convertValueToString
	    // returns "" for a null value.
	    //
	    Object textObj = getText();
	    if (textObj != null) {
		String text = ConversionUtilities.convertValueToString(this,
		    textObj);
		if (text != null && text.length() != 0) {
		    alt = text;
		}
	    }
        }
        
        return alt;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Scripting code executed when a mouse double click occurs over this component.
     */
    @Property(name="onDblClick", isHidden=false, isAttribute=true)
    public String getOnDblClick() {
        return super.getOnDblClick();
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[1];
        _values[0] = super.saveState(_context);
        return _values;
    }
}
