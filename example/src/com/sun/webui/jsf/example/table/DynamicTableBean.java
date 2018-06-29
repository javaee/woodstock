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

package com.sun.webui.jsf.example.table;

import java.io.Serializable;

import com.sun.webui.jsf.component.Table;
import com.sun.webui.jsf.component.TableRowGroup;

import com.sun.webui.jsf.example.table.util.Dynamic;
import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.index.IndexBackingBean;

/** Backing bean for dynamic table examples.
 *
 * Note: To simplify the example, this bean is used only to create the table 
 * layout. The resulting table will use methods already defined in TableBean.
 *
 * Note that we must implement java.io.Serializable or
 * javax.faces.component.StateHolder in case client-side
 * state saving is used, or if server-side state saving is
 * used with a distributed system.
 */
public class DynamicTableBean implements Serializable {
    private Dynamic dynamic = null; // Dynamic util.
    private Table table = null; // Table component.

    // Default constructor.
    public DynamicTableBean() {
        dynamic = new Dynamic();
    }

    // Get Table component.
    public Table getTable() {
        if (table == null) {
            // Get table row group.
            TableRowGroup rowGroup1 = dynamic.getTableRowGroup("rowGroup1",
                "#{TableBean.groupB.names}",
                "#{TableBean.groupB.select.selectedState}", null);

            // Set table row group properties.
            dynamic.setTableRowGroupChildren(rowGroup1,
                "#{TableBean.groupB.select.selectedState}",
                "#{TableBean.groupB.select.selected}",
                "#{TableBean.groupB.select.selectedValue}",
                "#{TableBean.groupB.actions.action}", true);

            // Get table.
            table = dynamic.getTable("table1", MessageUtil.getMessage("table_dynamicTitle"));
            table.getChildren().add(rowGroup1);
        }
        return table;
    }

    // Set Table component.
    //
    // @param table The Table component.
    public void setTable(Table table) {
        this.table = table;
    }
    
    // Action handler when navigating to the table index.
    public String showTableIndex() {
        reset();
        return TableBean.SHOW_TABLE_INDEX;
    }
    
    // Action handler when navigating to the main example index.
    public String showExampleIndex() {
        reset();
        return IndexBackingBean.INDEX_ACTION;
    }
    
    // Reset values so next visit starts fresh.
    private void reset() {
        table = null;
        dynamic = new Dynamic();
    }
}
