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

import com.sun.webui.jsf.component.util.DesignUtil;
import java.beans.BeanDescriptor;
import java.beans.EventSetDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import javax.faces.context.FacesContext;

import com.sun.rave.faces.event.Action;
import com.sun.rave.designtime.Constants;

import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * BeanInfo for the {@link com.sun.webui.jsf.component.TreeNode} component.
 *
 * @author gjmurphy
 */
public class TreeNodeBeanInfo extends TreeNodeBeanInfoBase {
    
    public TreeNodeBeanInfo() {
        DesignUtil.applyPropertyDomain(this, "target", com.sun.rave.propertyeditors.domains.FrameTargetsDomain.class);
        DesignUtil.hideProperties(this, new String[]{"actionListeners"});    //NOI18N
        BeanDescriptor beanDescriptor = super.getBeanDescriptor();
        beanDescriptor.setValue(Constants.BeanDescriptor.PREFERRED_CHILD_TYPES,
                new String[] { TreeNode.class.getName() });

	Theme theme =
	    ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
	String treeContent = theme.getStyleClass(ThemeStyles.TREE_CONTENT);
	String treeImgHeight = theme.getStyleClass(ThemeStyles.TREE_NODE_IMAGE_HEIGHT);
        beanDescriptor.setValue(
            Constants.BeanDescriptor.INLINE_EDITABLE_PROPERTIES,
            new String[] { "*text://div[@class='" + treeContent + //NOI18N
		" " + treeImgHeight + "']" }); // NOI18N
    }
    
    protected EventSetDescriptor[] eventSetDescriptors;
    
    public EventSetDescriptor[] getEventSetDescriptors() {
        if (eventSetDescriptors == null) {
            eventSetDescriptors = DesignUtil.generateCommandEventSetDescriptors(this);
        }
        return eventSetDescriptors;
    }
    
}
