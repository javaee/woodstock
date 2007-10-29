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

import javax.faces.context.FacesContext;

/**
 * Component that represents a table column.
 * <p>
 * The Table2Column component provides a layout mechanism for displaying columns 
 * of data. UI guidelines describe specific behavior that can applied to the 
 * rows and columns of data such as sorting, filtering, pagination, selection, 
 * and custom user actions. In addition, UI guidelines also define sections of 
 * the table that can be used for titles, row group headers, and placement of 
 * pre-defined and user defined actions.
 * </p>
 */
@Component(type="com.sun.webui.jsf.Table2Column",
    family="com.sun.webui.jsf.Table2Column",
    tagRendererType="com.sun.webui.jsf.widget.Table2Column",
    displayName="Table2Column", tagName="table2Column", isTag=false)
public class Table2Column extends TableColumnBase {
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

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Child methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // TBD...
}
