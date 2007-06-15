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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import com.sun.rave.propertyeditors.util.JavaInitializer;


/**
 * An editor for properties that specify the options that are "selected" among
 * a list of options. An option is "selected" if the value of its
 * <code>value</code> property is equal to one of the objects in the selected
 * property.
 *
 * @author gjmurphy
 */
public class SelectedValuesPropertyEditor extends PropertyEditorSupport {
    
    
    public void setAsText(String text) throws IllegalArgumentException {
        // Read-only
    }
    
    public String getJavaInitializationString() {
        StringBuffer buffer = new StringBuffer();
        Object value = this.getValue();
        if (value instanceof Object[]) {
            buffer.append("new Object[] {");
            Object[] values = (Object[]) value;
            for (int i = 0; i < values.length; i++) {
                if (i > 0)
                    buffer.append(", ");
                buffer.append(JavaInitializer.toJavaInitializationString(values[i]));
            }
            buffer.append("}");
        } else {
            buffer.append(JavaInitializer.toJavaInitializationString(value));
        }
        return buffer.toString();
    }

    public String getAsText() {
        Object value = this.getValue();
        if (value == null)
            return "";
        StringBuffer buffer = new StringBuffer();
        Collection collection;
        if (value instanceof Object[]) {
            collection = Arrays.asList((Object[]) value);
        } else if (value instanceof Collection) {
            collection = (Collection) value;
        } else {
            collection = Collections.singletonList(value);
        }
        for (Object item : collection) {
            buffer.append(item.toString());
            buffer.append(", ");
        }
        if (buffer.length() > 1) {
            buffer.setLength(buffer.length() - 2);
        }
        return buffer.toString();
    }
    
}
