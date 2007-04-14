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

import javax.faces.component.ActionSource;
import javax.faces.component.EditableValueHolder;
import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignContext;
import com.sun.rave.designtime.DisplayAction;
import com.sun.webui.jsf.component.Form;
import java.util.List;
import java.util.ArrayList;

public class VirtualFormsHelper {
    
    public static DisplayAction getContextItem(DesignContext context) {
        DesignBean formBean = null;
        DesignBean rootBean = context.getRootContainer();
        if (rootBean.getInstance() instanceof Form) {   //just in case
            formBean = rootBean;
        }
        else {
            formBean = findFormBeanFromRoot(rootBean);
        }
        if (formBean != null) {
            return new VirtualFormsCustomizerAction(formBean);
        }
        return null;
    }
    
    public static DisplayAction getContextItem(DesignBean bean) {
        if (findFormBean(bean) == null) {
            return null;
        }
        if (bean.getInstance() instanceof EditableValueHolder ||
            bean.getInstance() instanceof ActionSource) {
            return new EditVirtualFormsCustomizerAction(bean);
        }
        return null;
    }
    
    public static DisplayAction getContextItem(DesignBean[] beans) {
        if (findFormBean(beans) == null) {
            return null;
        }
        for (int i = 0; beans != null && i < beans.length; i++) {
            if (beans[i].getInstance() instanceof EditableValueHolder ||
                beans[i].getInstance() instanceof ActionSource) {
                return new EditVirtualFormsCustomizerAction(beans);
            }
        }
        return null;
    }
    
    public static DesignBean findFormBean(DesignBean[] beans) {
        if (beans == null) {
            return null;
        }
        for (int i = 0; i < beans.length; i++) {
            DesignBean formBean = findFormBean(beans[i]);
            if (formBean != null) {
                return formBean;
            }
        }
        return null;
    }
    
    public static DesignBean findFormBean(DesignBean bean) {
        if (bean == null) {
            return null;
        }
        if (bean.getInstance() instanceof Form) {
            return bean;
        }
        return findFormBean(bean.getBeanParent());
    }
    
    private static DesignBean findFormBeanFromRoot(DesignBean parent) {
        if (parent == null) {
            return null;
        }
        DesignBean[] childBeans = parent.getChildBeans();
        for (int i = 0; childBeans != null && i < childBeans.length; i++) {
            DesignBean bean = childBeans[i];
            if (bean.getInstance() instanceof Form) {
                return bean;
            }
            DesignBean formBean = findFormBeanFromRoot(bean);
            if (formBean != null) {
                return formBean;
            }
        }
        return null;
    }
    
    public static String getNewVirtualFormName(List vformsList) {
        List nameList = new ArrayList();
        for (int i = 0; vformsList != null && i < vformsList.size(); i++) {
            nameList.add(((Form.VirtualFormDescriptor)vformsList.get(i)).getName());
        }
        
        String name = java.util.ResourceBundle.getBundle("com/sun/webui/jsf/component/vforms/Bundle").getString("newVirtualForm"); // NOI18N
        
        for (int i = 1; i < 999; i++) {
            if (!nameList.contains(name + i)) {
                name = name + i;
                break;
            }
        }
        
        return name;
    }
}
