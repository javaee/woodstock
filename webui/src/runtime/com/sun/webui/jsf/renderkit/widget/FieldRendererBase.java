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

package com.sun.webui.jsf.renderkit.widget;

import com.sun.webui.jsf.component.ComplexComponent;
import com.sun.webui.jsf.component.Field;

import java.util.Map;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/** 
 * FieldRendererBase provides common functionality for field renderers,
 * such as TextFieldRenderer or TextAreaRenderer.
 */
public abstract class FieldRendererBase extends RendererBase {
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Decode the TextField component
     *
     * @param context The FacesContext associated with this request
     * @param component The TextField2 component to decode
     */
    public void decode(FacesContext context, UIComponent component) {
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!(component instanceof Field)) {
            throw new IllegalArgumentException("TextFieldRenderer can only decode Field components.");
        }
        if (!(component instanceof EditableValueHolder)) {
            throw new IllegalArgumentException("TextFieldRenderer can only decode EditableValueHolder components.");
        }
        Field field = (Field)component;    
        if (field.isDisabled() || field.isReadOnly()) {
            return;
        }
                 
        String id = field.getClientId(context); 
        if (field instanceof ComplexComponent) {
            // This must be the id of the submitted element.
            // For now it is the same as the labeled element
            //
            id = field.getLabeledElementId(context);
        }
        if (id == null) {
            return;
        }
       
        String value = null;
        Map params = context.getExternalContext().getRequestParameterMap();
        Object valueObject = params.get(id);
        if (valueObject != null) { 
            value = (String) valueObject;
            if (field.isTrim()) {
                value = value.toString().trim();
            }
        }
        field.setSubmittedValue(value);
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
