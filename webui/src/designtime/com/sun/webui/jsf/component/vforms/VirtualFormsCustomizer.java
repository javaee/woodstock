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
package com.sun.webui.jsf.component.vforms;

import java.awt.Component;
import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.Result;
import com.sun.rave.designtime.impl.BasicCustomizer2;

public class VirtualFormsCustomizer extends BasicCustomizer2 {

    public VirtualFormsCustomizer() {
        super(VirtualFormsCustomizerPanel.class, java.util.ResourceBundle.getBundle("com/sun/webui/jsf/component/vforms/Bundle").getString("vfHeader"));   //NOI18N
        setHelpKey("projrave_ui_elements_dialogs_virtual_forms_db"); //NOI18N
    }

    protected VirtualFormsCustomizerPanel customizerPanel;
    public Component getCustomizerPanel(DesignBean designBean) {
        this.designBean = designBean;
        this.customizerPanel = new VirtualFormsCustomizerPanel(this);
        return customizerPanel;
    }

    public Result applyChanges() {
        return customizerPanel.applyChanges();
    }
}
