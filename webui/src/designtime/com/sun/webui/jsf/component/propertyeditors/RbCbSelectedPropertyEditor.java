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

package com.sun.webui.jsf.component.propertyeditors;

import java.beans.PropertyEditorSupport;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.PropertyEditor2;

/**
 * A property editor for the <code>selected</code> property of radioButton and
 * checkbox components.
 *
 * @author gjmurphy
 */
public class RbCbSelectedPropertyEditor extends PropertyEditorSupport implements PropertyEditor2 {
    
    static final int UNSET = 0;
    static final int TRUE = 1;
    static final int FALSE = 2;
    
    DesignProperty designProperty;
    String tags[] = new String[3];
    
    /**
     * Creates a new instance of RbCbSelectedPropertyEditor 
     */
    public RbCbSelectedPropertyEditor() {
        tags[UNSET] = "";
        tags[TRUE] = Boolean.TRUE.toString();
        tags[FALSE] = Boolean.FALSE.toString();
    }
    
    /**
     * Set the design property for this editor.
     */
    public void setDesignProperty (DesignProperty designProperty) {
        this.designProperty = designProperty;
    }
    
    public void setAsText(String text) throws IllegalArgumentException {
        if (text.equals(tags[UNSET])) {
            this.setValue(null);
        } else if (text.equals(tags[FALSE])) {
            if (Boolean.TRUE.equals(getSelectedValue()))
                this.setValue(Boolean.FALSE);
            else
                this.setValue(null);
        } else {
            this.setValue(getSelectedValue());
        }
    }

    public String getAsText() {
        Object value = this.getValue();
        Object selectedValue = getSelectedValue();
        if (value == null)
            return tags[UNSET];
        if (value.equals(selectedValue)) {
            if (selectedValue instanceof String)
                return tags[TRUE] + " (" + getSelectedValue().toString() + ")";
            else
                return tags[TRUE];
        } else if (value instanceof String && Boolean.valueOf((String) value).equals(selectedValue)) {
            return tags[TRUE];
        }
        return tags[FALSE];
    }

    public String[] getTags() {
        return tags;
    }
    
    private Object getSelectedValue() {
        return designProperty.getDesignBean().getProperty("selectedValue").getValue(); //NOI18N
    }
    
}
