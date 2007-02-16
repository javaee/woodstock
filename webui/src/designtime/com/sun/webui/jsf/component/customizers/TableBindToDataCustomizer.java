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

/*
 * TableDesignInfo.java
 * Created on April 29, 2005, 12:40 PM
 * Version 1.0
 */

package com.sun.webui.jsf.component.customizers;

import com.sun.webui.jsf.component.table.TableBindToDataPanel;
import java.awt.Component;
import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.Result;
import com.sun.rave.designtime.impl.BasicCustomizer2;
import com.sun.webui.jsf.component.table.TableBindToDataPanel;
import com.sun.webui.jsf.component.util.DesignMessageUtil;

/**
 * Customizer for the <code>Table</code> component.
 * @author Winston Prakash
 */

public class TableBindToDataCustomizer extends BasicCustomizer2 {
    public TableBindToDataCustomizer() {
        super(null, DesignMessageUtil.getMessage(TableCustomizerAction.class, "tableBindToDataCustomizer.title"), null, null); //NOI18N
        setApplyCapable(true);
        setHelpKey("projrave_ui_elements_dialogs_bindtodata_table_db"); //NOI18N
    }

    protected TableBindToDataPanel panel;

    public Component getCustomizerPanel(DesignBean bean) {
        this.setDisplayName(DesignMessageUtil.getMessage(TableCustomizerAction.class, "tableBindToDataCustomizer.title") + " - " + bean.getInstanceName());
        panel = new TableBindToDataPanel(bean);
        return panel;
    }

    public boolean isModified() {
        if (panel != null) {
            return panel.isModified();
        }
        return false;
    }

    public Result applyChanges() {
        if (panel != null) {
            return panel.applyChanges();
        }
        return Result.SUCCESS;
    }
}
