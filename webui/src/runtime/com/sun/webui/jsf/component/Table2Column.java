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

import com.sun.faces.annotation.Component;

import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;

/**
 * Component that represents a table column.
 */
@Component(type="com.sun.webui.jsf.table2Column",
    family="com.sun.webui.jsf.table2Column",
    tagRendererType="com.sun.webui.jsf.widget.Table2Column",
    displayName="Table2Column", tagName="table2Column")
public class Table2Column extends TableColumn implements NamingContainer {
    public Table2Column() {
        super();
        setRendererType("com.sun.webui.jsf.widget.Table2Column");
    }

    public FacesContext getContext() {
        return getFacesContext();
    }

    public String getFamily() {
        return "com.sun.webui.jsf.Table2Column";
    }

    /**
     * Get alternative HTML template to be used by this component.
     */
    public String getHtmlTemplate() {
        return null; // Not implemented
    }
}
