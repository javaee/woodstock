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

import com.sun.data.provider.FieldKey;
import com.sun.data.provider.RowKey;
import com.sun.data.provider.TableDataProvider;
import com.sun.data.provider.impl.ObjectListDataProvider;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.OptionTitle;

import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import com.sun.webui.jsf.example.common.MessageUtil;

// This class provides functionality for table actions.
public class Actions {
    private Group group = null; // Group util.

    // Action menu items.
    protected static final Option[] moreActionsOptions = {
        new OptionTitle(MessageUtil.getMessage("table_ActionsMenuTitle")),
        new Option("ACTION1", MessageUtil.getMessage("table_Action1")),
        new Option("ACTION2", MessageUtil.getMessage("table_Action2")),
        new Option("ACTION3", MessageUtil.getMessage("table_Action3")),
        new Option("ACTION4", MessageUtil.getMessage("table_Action4")),
    };

    // Default constructor.
    public Actions(Group group) {
        this.group = group;
    }

    // Action button event.
    public void action() {
        String message = null;

        // Get hyperlink parameter used for embedded actions example.
        Map map = FacesContext.getCurrentInstance().getExternalContext()
            .getRequestParameterMap();
        String param = (String) map.get("param");
        if (param != null) {
            message = MessageUtil.getMessage("table_embeddedActionMsg") + " " + param;
        } else {
            message = MessageUtil.getMessage("table_tableActionMsg");
        }

        group.getMessages().setMessage(message);
    }

    // Action to remove rows from ObjectListDataProvider.
    public void delete() {
        // Since mutiple examples are using the same beans, the binding
        // simply tells us that checkbox state is maintained arcoss pages.
        if (group.getSelect().isKeepSelected()) {
            // If we got here, then we're maintaining state across pages.
            delete(group.getTableRowGroup().getSelectedRowKeys());
        } else {
            // If we got here, then we're using the phase listener and must
            // take filtering, sorting, and pagination into account.
            delete(group.getTableRowGroup().getRenderedSelectedRowKeys());
        }
    }

    // Set disabled value for table actions.
    public boolean getDisabled() {
        // If there is at least one row selection, actions are enabled.
        boolean result = true;
        if (group.getTableRowGroup() == null) {
            return result;
        }

        // Since mutiple examples are using the same beans, the binding
        // simply tells us that checkbox state is maintained arcoss pages.
        if (group.getSelect().isKeepSelected()) {
            // If we got here, then we're maintaining state across pages.
            result = group.getTableRowGroup().getSelectedRowsCount() < 1;
        } else {
            // If we got here, then we're using the phase listener and must
            // take filtering, sorting, and pagination into account.
            result = group.getTableRowGroup().getRenderedSelectedRowsCount() < 1;
        }
        return result;
    }

    // Get action.
    public String getMoreActions() {
        // Per the UI guidelines, always snap back to "More Actions...".
        return OptionTitle.NONESELECTED;
    }

    // Get action menu options.
    public Option[] getMoreActionsOptions() {
        return moreActionsOptions;
    }

    // Action menu event.
    public void moreActions() {
        group.getMessages().setMessage(MessageUtil.getMessage("table_moreActionsMsg"));
    }

    // Set action.
    public void setMoreActions(String action) {
        // Do nothing.
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Action to remove rows from ObjectListDataProvider.
    private void delete(RowKey[] rowKeys) {
        if (rowKeys == null) {
            return;
        }
        TableDataProvider provider = group.getNames();
        for (int i = 0; i < rowKeys.length; i++) {
            RowKey rowKey = rowKeys[i];
            if (provider.canRemoveRow(rowKey)) {
                provider.removeRow(rowKey);
            }
        }
        ((ObjectListDataProvider) provider).commitChanges(); // Commit.
        group.getSelect().clear(); // Clear phase listener.
    }
}
