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

import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.util.ComponentUtilities;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Base class for components which need to extend UIOutput.
 */
public abstract class WebuiOutput extends UIOutput {
    /**
     * The converter attribute is used to specify a method to translate native
     * property values to String and back for this component. The converter 
     * attribute value must be one of the following:
     * <ul>
     * <li>A JavaServer Faces EL expression that resolves to a backing bean or
     * bean property that implements the 
     * <code>javax.faces.converter.Converter</code> interface; or
     * </li><li>the ID of a registered converter (a String).</li>
     * </ul>
     */
    @Property(name="converter") 
    public void setConverter(Converter converter) {
        super.setConverter(converter);
    }

    /**
     * The component identifier for this component. This value must be unique 
     * within the closest parent component that is a naming container.
     */
    @Property(name="id") 
    public void setId(String id) {
        super.setId(id);
    }

    /**
     * Use the rendered attribute to indicate whether the HTML code for the
     * component should be included in the rendered HTML page. If set to false,
     * the rendered HTML page does not include the HTML for the component. If
     * the component is not rendered, it is also not processed on any subsequent
     * form submission.
     */
    @Property(name="rendered") 
    public void setRendered(boolean rendered) {
        super.setRendered(rendered);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Lifecycle methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * <p>Specialized decode behavior on top of that provided by the
     * superclass.
     *
     * <ul>
     *  <li>This method will skip decoding for Ajax requests of type "refresh".</li>
     * </ul>
     *
     * @param context <code>FacesContext</code> for this request.
     */
    public void processDecodes(FacesContext context) {
        if (context == null) {
            return;
        }
        // Skip processing in case of "refresh" ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this, "refresh")
                && !ComponentUtilities.isAjaxExecuteRequest(getFacesContext(), this)) {
            return;
        }
        super.processDecodes(context);
    }

    /**
     * <p>Specialized validation behavior on top of that provided by the
     * superclass.
     *
     * <ul>
     *  <li>This method will skip decoding for Ajax requests of type "refresh".</li>
     * </ul>
     *
     * @param context <code>FacesContext</code> for this request.
     */
    public void processValidators(FacesContext context) {
        if (context == null) {
            return;
        }
        // Skip procesing in case of "refresh" ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this, "refresh")
                && !ComponentUtilities.isAjaxExecuteRequest(getFacesContext(), this)) {
            return; // Skip processing for ajax based validation events.
        }
        super.processValidators(context);
    }
    
    /**
     * <p>Specialized model update behavior on top of that provided by the
     * superclass.
     *
     * <ul>
     *  <li>This method will skip decoding for Ajax requests of type "refresh".</li>
     * </ul>
     *
     * @param context <code>FacesContext</code> for this request.
     */
    public void processUpdates(FacesContext context) {
        if (context == null) {
            return;
        }
        // Skip processing in case of "refresh" ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this, "refresh")
                && !ComponentUtilities.isAjaxExecuteRequest(getFacesContext(), this)) {
            return;
        }
        super.processUpdates(context);
    }
}
