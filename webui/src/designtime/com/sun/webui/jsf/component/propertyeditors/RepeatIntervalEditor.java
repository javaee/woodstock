/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.webui.jsf.component.propertyeditors;

import com.sun.rave.propertyeditors.PropertyEditorBase;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.scheduler.RepeatInterval;

/**
 * Property editor for the <code>RepeatIntervalItems</code> property of Scheduler component.
 */
public class RepeatIntervalEditor extends PropertyEditorBase {
    
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
