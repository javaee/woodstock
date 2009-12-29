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
package com.sun.webui.jsf.model.scheduler;

import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.convert.Converter;
import javax.faces.context.FacesContext;

// Delete the setters once you have reimplemented this not to 
// use the default Serializable mechanism, but the same as 
// in the converter.... 
public class RepeatIntervalConverter implements Converter, Serializable {

    private static final long serialVersionUID = -1508546467189111720L;

    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        if (value == null) {
            return null;
        }
        return ((RepeatInterval) value).getRepresentation();
    }

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        return RepeatInterval.getInstance(value);
    }
}

