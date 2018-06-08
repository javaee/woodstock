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

package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * Component that represents a table.
 */
@Component(type = "com.sun.webui.jsf.Table2",
family = "com.sun.webui.jsf.Table2",
tagRendererType = "com.sun.webui.jsf.widget.Table2",
displayName = "Table2", tagName = "table2", isTag = false) // Remove isTag to run
public class Table2 extends Table implements NamingContainer, Widget {

    /** The facet name for the actions area. */
    public static final String ACTIONS_FACET = "actions"; //NOI18N 
    /** The facet name for the title area. */
    public static final String TITLE_FACET = "title"; //NOI18N

    // A List containing Table2RowGroup children. 
    private List table2RowGroupChildren = null;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Base methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public Table2() {
        super();
        setRendererType("com.sun.webui.jsf.widget.Table2");
    }

    public FacesContext getContext() {
        return getFacesContext();
    }

    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.Table2";
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @return The type of widget represented by this component.
     */
    public String getWidgetType() {
        return JavaScriptUtilities.getNamespace("table2");
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Child methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /** 
     * Get an Iterator over the Table2RowGroup children found for this
     * component. 
     * 
     * @return An Iterator over the Table2RowGroup children. 
     */
    public Iterator getTable2RowGroupChildren() {
        // Get TableRowGroup children. 
        if (table2RowGroupChildren == null) {
            table2RowGroupChildren = new ArrayList();
            Iterator kids = getChildren().iterator();
            while (kids.hasNext()) {
                UIComponent kid = (UIComponent) kids.next();
                if ((kid instanceof Table2RowGroup)) {
                    table2RowGroupChildren.add(kid);
                }
            }
        }
        return table2RowGroupChildren.iterator();
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Alternative HTML template to be used by this component.
     */
    @Property(name = "htmlTemplate", displayName = "HTML Template", category = "Appearance")
    private String htmlTemplate = null;

    /**
     * Get alternative HTML template to be used by this component.
     */
    public String getHtmlTemplate() {
        if (this.htmlTemplate != null) {
            return this.htmlTemplate;
        }
        ValueExpression _vb = getValueExpression("htmlTemplate");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Set alternative HTML template to be used by this component.
     */
    public void setHtmlTemplate(String htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }
}
