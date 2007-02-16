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

import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.CustomizerResult;
import com.sun.rave.designtime.Result;
import com.sun.rave.designtime.impl.BasicDisplayAction;
import com.sun.webui.jsf.component.util.DesignMessageUtil;

/**
 * Bind to Data Action for the <code>Table</code> or <code>TableGroup</code> component.
 * @author Winston Prakash
 */

public class TableBindToDataAction extends BasicDisplayAction {

    protected DesignBean designBean;

    public TableBindToDataAction(DesignBean bean) {
        super(DesignMessageUtil.getMessage(TableCustomizerAction.class, "tableBindToDataAction.displayName")); //NOI18N
        setHelpKey("projrave_ui_elements_dialogs_data_table_bind_to_data_db"); //NOI18N
        designBean = bean;
    }

    public Result invoke() {
        return new CustomizerResult(designBean, new TableBindToDataCustomizer());
    }
}
