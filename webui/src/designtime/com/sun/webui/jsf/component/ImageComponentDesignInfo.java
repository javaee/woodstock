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

import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.DisplayAction;
import com.sun.rave.designtime.Result;
import com.sun.webui.jsf.component.customizers.ImageCustomizerAction;
import com.sun.webui.jsf.design.AbstractDesignInfo;

/**
 * Design time behavior for an <code>Image</code> component.
 *
 * @author gjmurphy
 */
public class ImageComponentDesignInfo extends AbstractDesignInfo {
    
    public ImageComponentDesignInfo() {
        super(ImageComponent.class);
    }

    public Result beanCreatedSetup(DesignBean bean) {
        ImageComponent image = (ImageComponent)bean.getInstance();
        return Result.SUCCESS;
    }

    public DisplayAction[] getContextItems(DesignBean bean) {
        DisplayAction[] superActions = super.getContextItems(bean);
        if (superActions == null)
            return new DisplayAction[] {new ImageCustomizerAction(bean)};
        DisplayAction[] actions = new DisplayAction[superActions.length + 1];
        int i = 0;
        while (i < superActions.length) {
            actions[i] = superActions[i];
            i++;
        }
        actions[i] = new ImageCustomizerAction(bean);
        return actions;
    }

    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        return false;
    }

    protected DesignProperty getDefaultBindingProperty(DesignBean targetBean) {
        return targetBean.getProperty("url"); //NOI18N
    }
        
}
