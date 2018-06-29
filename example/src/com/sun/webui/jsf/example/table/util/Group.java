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

package com.sun.webui.jsf.example.table.util;

import com.sun.data.provider.TableDataProvider;
import com.sun.data.provider.impl.ObjectArrayDataProvider;
import com.sun.data.provider.impl.ObjectListDataProvider;
import com.sun.webui.jsf.component.Checkbox;
import com.sun.webui.jsf.component.TableRowGroup;

import java.util.List;

// This class contains data provider and util classes. Note that not all util
// classes are used for each example.
public class Group {
    private TableRowGroup tableRowGroup = null; // TableRowGroup component.
    private TableDataProvider provider = null; // Data provider.
    private Checkbox checkbox = null; // Checkbox component.
    private Preferences prefs = null; // Preferences util.
    private Messages messages = null; // Messages util.
    private Actions actions = null; // Actions util.
    private Filter filter = null; // Filter util.
    private Select select = null; // Select util.

    // Default constructor.
    public Group() {
        actions = new Actions(this);
        filter = new Filter(this);
        select = new Select(this);
        prefs = new Preferences();
        messages = new Messages();
    }

    // Construct an instance using given Object array.
    public Group(Object[] array) {
        this();
        provider = new ObjectArrayDataProvider(array);
    }

    // Construct an instance using given List.
    public Group(List list) {
        this();
        provider = new ObjectListDataProvider(list);
    }

    // Construct an instance using given List and a
    // flag indicating that selected objects should
    // not be cleared after the render response phase.        
    public Group(List list, boolean keepSelected) {
        actions = new Actions(this);        
        select = new Select(this, keepSelected);
        messages = new Messages();      
        provider = new ObjectListDataProvider(list);
    }
    
    // Get data provider.
    public TableDataProvider getNames() {
        return provider;
    }

    // Get Actions util.
    public Actions getActions() {
        return actions;
    }

    // Get Filter util.
    public Filter getFilter() {
        return filter;
    }

    // Get Messages util.
    public Messages getMessages() {
        return messages;
    }

    // Get Preferences util.
    public Preferences getPreferences() {
        return prefs;
    }

    // Get Select util.
    public Select getSelect() {
        return select;
    }

    // Get tableRowGroup component.
    public TableRowGroup getTableRowGroup() {
        return tableRowGroup;
    }

    // Set tableRowGroup component.
    public void setTableRowGroup(TableRowGroup tableRowGroup) {
        this.tableRowGroup = tableRowGroup;
    }

    // Get checkbox component.
    public Checkbox getCheckbox() {
        return checkbox;
    }

    // Set checkbox component.
    public void setCheckbox(Checkbox checkbox) {
        this.checkbox = checkbox;
    }
}
