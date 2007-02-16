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

import com.sun.rave.designtime.CustomizerResult;
import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.Result;
import com.sun.rave.designtime.impl.BasicDisplayAction;

public class EditVirtualFormsCustomizerAction extends BasicDisplayAction {
    protected DesignBean[] beans;
    public EditVirtualFormsCustomizerAction(DesignBean bean) {
        super(java.util.ResourceBundle.getBundle("com/sun/webui/jsf/component/vforms/Bundle").getString("editVfAction"));    //NOI18N
        this.beans = new DesignBean[] { bean };
    }
    public EditVirtualFormsCustomizerAction(DesignBean[] beans) {
        super(java.util.ResourceBundle.getBundle("com/sun/webui/jsf/component/vforms/Bundle").getString("editVfAction"));    //NOI18N
        this.beans = beans;
    }
    public Result invoke() {
        return new CustomizerResult(null, new EditVirtualFormsCustomizer(beans));
    }
}
