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

import com.sun.rave.designtime.PropertyEditor2;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.scheduler.RepeatInterval;
import java.beans.PropertyEditorSupport;

/**
 * Property editor for the <code>RepeatIntervalItems</code> property of Scheduler component.
 */
public class RepeatIntervalEditor extends PropertyEditorSupport {
    
    /** Creates a new instance of RepeatIntervalEditor */
    public RepeatIntervalEditor() {
    }
    
    /**
     * Sets the property value by parsing a given string. If the
     * passed-in string is of type value binding, the property
     * value will be set, otherwise, it will be ignored.
     *
     * @param text The string to be parsed.
     */
    public void setAsText(String text) throws IllegalArgumentException {        
        if (text == null) {
            this.setValue(null);
        } else {
            text = text.trim();
            if (text.length() <= 0) {
                this.setValue(null);
            } else if (text.startsWith("#{") && text.endsWith("}")) {
                this.setValue(text);
            }
        }
    }
    
    /**
     * Gets the property's default value as a string suitable for
     * presentation to user in the property sheets. This will be 
     * a comma-separated list of pre-defined repeat interval object
     * representations.
     *
     * @return  The poperty's default value as a string, or null if
     *          the property value can't be expressed as a string.
     */
    public String getAsText() {
        Object value = (Object) this.getValue();
        if (value == null) {
            return null;
        } else if (value instanceof Option[]) {
            Option[] options = (Option[]) value;
            RepeatInterval repeatInterval;
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < options.length; i++) {
                repeatInterval = (RepeatInterval)options[i].getValue();
                if (repeatInterval != null) {
                    if (i > 0) {
                        buffer.append(", ");
                    }
                    buffer.append(repeatInterval.getRepresentation());
                }
            }
            return buffer.toString();
        } 
        return null;
    }
}
