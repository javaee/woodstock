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

import com.sun.webui.jsf.model.Option;
import com.sun.rave.propertyeditors.util.JavaInitializer;
import java.beans.PropertyEditorSupport;
import java.util.Collection;

/**
 * An editor for properties that take lists of options, such as the
 * <code>items</code> property on all list-like components.
 *
 * @author gjmurphy
 */
public class OptionsPropertyEditor extends PropertyEditorSupport {
    
    public void setAsText(String text) throws IllegalArgumentException {
        // Read-only
    }
    
    public String getAsText() {
        Object value = this.getValue();
        if (value == null)
            return "";
        StringBuffer buffer = new StringBuffer();
        if (value instanceof Option[] && ((Option[]) value).length > 0) {
            for (Option option : (Option[]) value) {
                buffer.append(option.getLabel());
                buffer.append(", ");
            }
            buffer.setLength(buffer.length() - 2);
        } else if (value instanceof Collection) {
            for (Object item : (Collection) value) {
                buffer.append(((Option) item).getLabel());
                buffer.append(", ");
            }
            buffer.setLength(buffer.length() - 2);
        } else {
            buffer.append(value.toString());
        }
        return buffer.toString();
    }
    
    public String getJavaInitializationString() {
        Object value = this.getValue();
        if (!(value instanceof Option[]))
            return null;
        Option[] options = (Option[]) value;
        StringBuffer buffer = new StringBuffer();
        buffer.append("new " + Option.class.getName() + "[] {");
        for (int i = 0; i < options.length; i++) {
            if (i > 0)
                buffer.append(", ");
            buffer.append("new " + Option.class.getName() + "(");
            buffer.append(JavaInitializer.toJavaInitializationString(options[i].getValue()));
            buffer.append(", ");
            buffer.append(JavaInitializer.toJavaInitializationString(options[i].getLabel()));
            buffer.append(")");
        }
        buffer.append("}");



        return buffer.toString();
    }
    
}
