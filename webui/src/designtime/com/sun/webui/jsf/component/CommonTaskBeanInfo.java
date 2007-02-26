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


import com.sun.webui.jsf.component.propertyeditors.ThemeIconsDomain;
import com.sun.webui.jsf.component.util.DesignUtil;
import java.beans.PropertyDescriptor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.validator.Validator;

/**
 * BeanInfo for the {@link com.sun.webui.jsf.component.CommonTasksGroup} component.
 */
public class CommonTaskBeanInfo extends CommonTaskBeanInfoBase{
    
    /** Creates a new instance of CommonTaskBeanInfo */
    public CommonTaskBeanInfo() {
        super();
        DesignUtil.applyPropertyDomain(this, "icon", ThemeIconsDomain.class);
        DesignUtil.applyPropertyDomain(this, "target", com.sun.rave.propertyeditors.domains.FrameTargetsDomain.class);
        PropertyDescriptor[] descriptors = this.getPropertyDescriptors();
        for (int i = 0; i < descriptors.length; i++) {
            if (descriptors[i].getName().equals("action")) //NOI18N
                descriptors[i].setHidden(true);
        }
    }
    
}
