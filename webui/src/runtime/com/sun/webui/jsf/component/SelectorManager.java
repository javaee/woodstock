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

import javax.faces.context.FacesContext; 
import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

/**
 * For internal use only.
 */
public interface SelectorManager {
      /**
     * JSF standard method from UIComponent
     * @param context The FacesContext for the request
     * @return The client id, also the JavaScript element id
     */
    public String getClientId(FacesContext context);

    /**
     * JSF standard method from UIComponent
     * @return true if the component is disabled
     */
    public boolean isDisabled();

        /**
     * Get the JS onchange event handler
     * @return A string representing the JS event handler
     */
    public String getOnChange();

    /**
     * Get the tab index for the component
     * @return the tabindex
     */
    public int getTabIndex();
    
       /**
     * Returns true if the component allows multiple selections
     * @return true if the component allows multiple selections
     */
    public boolean isMultiple();
    
    /**
     * Returns true if the component is readonly
     * @return true if the component is readonly
     */
    public boolean isReadOnly(); 
    
    public String getStyle();
    
    public String getStyleClass();
}
