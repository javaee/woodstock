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

import com.sun.data.provider.SortCriteria;
import com.sun.data.provider.impl.FieldIdSortCriteria;
import com.sun.faces.annotation.Component;
import com.sun.webui.jsf.faces.ValueBindingSortCriteria;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;

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
    displayName="Table2Column", tagName="table2Column")
public class Table2Column extends TableColumnBase {
    public Table2Column() {
        super();
        setRendererType("com.sun.webui.jsf.widget.Table2Column");
    }

    // The Table ancestor enclosing this component.
    private Table2 tableAncestor = null;
    // Sort level for this component.
    private int sortLevel = -1;
    // The TableColumn ancestor enclosing this component.
    private Table2Column tableColumnAncestor = null;

    // The TableRowGroup ancestor enclosing this component.
    private Table2RowGroup tableRowGroupAncestor = null;
    
    public FacesContext getContext() {
        return getFacesContext();
    }

    public String getFamily() {
        return "com.sun.webui.jsf.Table2Column";
    }
     /**
     * Get SortCriteria used for sorting the contents of a TableDataProvider.
     * <p>
     * Note: If the sortKey attribute resolves to a SortCriteria object, it is
     * returned as is. However, if there is a value binding, and it's not null,
     * a ValueBindingSortCriteria object is created. If there is no value 
     * binding, a FieldIdSortCriteria object is created.
     * </p>
     * @return The SortCriteria used for sorting.
     */
    public SortCriteria getSortCriteria() {
        // Return if value binding resolves to a SortCriteria object.
        Object key = getSort();
        if (key instanceof SortCriteria) {
            return (SortCriteria) key;
        }

        SortCriteria result = null;
        ValueExpression vb = getValueExpression("sort"); //NOI18N
        if (vb != null) {
            ValueBindingSortCriteria vbsc = new ValueBindingSortCriteria(vb, 
                !isDescending()); // Note: Constructor accepts ascending param.
            Table2RowGroup group = getTableRowGroupAncestor();
            if (group != null) {
                vbsc.setRequestMapKey(group.getSourceVar());
            }
            result = vbsc;
        } else if (key != null) {
            result = new FieldIdSortCriteria(key.toString(), !isDescending());
        }
        return result;
    }

    /**
     * Helper method to get sort level for this component.
     *
     * @return The sort level or 0 if sort does not apply.
     */
    public int getSortLevel() {
        if (sortLevel == -1) {
            Table2Column col = this;
            Table2RowGroup group = getTableRowGroupAncestor();
            if (col != null && group != null) {
                sortLevel = group.getSortLevel(col.getSortCriteria());
            }
        }
        return sortLevel;
    }
    /**
     * Get the closest TableRowGroup ancestor that encloses this component.
     *
     * @return The TableRowGroup ancestor.
     */
    public Table2RowGroup getTableRowGroupAncestor() {
        // Get TableRowGroup ancestor.
        if (tableRowGroupAncestor == null) {
            UIComponent component = this;
            while (component != null) {
                component = component.getParent();
                if (component instanceof Table2RowGroup) {
                    tableRowGroupAncestor = (Table2RowGroup) component;
                    break;
                }
            }
        }
        return tableRowGroupAncestor;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Child methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // TBD...
}
