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
 * RequiredFieldsPropertyEditor.java
 *
 * Created on October 20, 2005, 2:19 PM
 */

package com.sun.webui.jsf.component.propertyeditors;


import java.beans.PropertyEditorSupport;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.PropertyEditor2;


/**
 *A property editor for the <code>requiredFields</code> property of Property Sheet component
 * 
 * @author Gowri
 */
public class RequiredFieldsPropertyEditor extends PropertyEditorSupport implements PropertyEditor2 {
    
    static final int UNSET = 0;
    static final int TRUE = 1;
    static final int FALSE = 2;
    
    DesignProperty designProperty;
    String tags[] = new String[3];
    
    /**
     * Creates a new instance of RequiredFieldsPropertyEditor
     */
    public RequiredFieldsPropertyEditor() {
        tags[UNSET] = "";
        tags[TRUE] = Boolean.TRUE.toString();
        tags[FALSE] = Boolean.FALSE.toString();
    }
    
    /**
     * Set the design property for this editor.
     */
    public void setDesignProperty(DesignProperty designProperty) {
        this.designProperty = designProperty;
    }
    
    public void setAsText(String text) throws IllegalArgumentException {        
        if (text.equals(tags[UNSET])) {            
            this.setValue(null);
        } else if (text.equals(tags[FALSE])) {            
            this.setValue(tags[FALSE]);            
        } else {            
            this.setValue(tags[TRUE]);
        }
    }
    
    public String getAsText() {
        Object value = this.getValue();        
        if (value == null)
            return tags[UNSET];
        if (value.equals(tags[TRUE])) {
            return tags[TRUE];
        } else {
            return tags[FALSE];
        }
    }
    
    public String[] getTags() {
        return tags;
    }
}
