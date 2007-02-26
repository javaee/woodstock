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

import com.sun.rave.faces.event.Action;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.component.util.DesignUtil;
import java.beans.BeanDescriptor;
import java.beans.EventSetDescriptor;
import java.beans.PropertyDescriptor;
import com.sun.rave.designtime.Constants;
import com.sun.webui.jsf.component.propertyeditors.AlertTypesDomain;
import java.lang.reflect.Method;
import javax.faces.context.FacesContext;

import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;


/**
 * BeanInfo for the {@link com.sun.webui.jsf.component.Alert} component.
 *
 * @author gjmurphy
 */
public class AlertBeanInfo extends AlertBeanInfoBase {
    
    public AlertBeanInfo() {
        DesignUtil.applyPropertyDomain(this, "linkTarget", com.sun.rave.propertyeditors.domains.FrameTargetsDomain.class);
        DesignUtil.applyPropertyDomain(this, "type", AlertTypesDomain.class);
    }
    
    public BeanDescriptor getBeanDescriptor() {
        Theme theme =
                ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        String alertLabelStyle = theme.getStyleClass(ThemeStyles.ALERT_TEXT);
        BeanDescriptor beanDescriptor = super.getBeanDescriptor();
        beanDescriptor.setValue(
                Constants.BeanDescriptor.INLINE_EDITABLE_PROPERTIES,
                new String[] {
            "*summary://span[@class='" + alertLabelStyle + "']",//NOI18N
            "detail://span[@class='" + alertLabelStyle + "']" }); // NOI18N
        PropertyDescriptor[] descriptors = this.getPropertyDescriptors();
        for (int i = 0; i < descriptors.length; i++) {
            if (descriptors[i].getName().equals("linkAction")) //NOI18N
                descriptors[i].setHidden(true);
        }
        return beanDescriptor;
    }
    
    EventSetDescriptor[] eventSetDescriptors;
    
    public EventSetDescriptor[] getEventSetDescriptors() {
        if (eventSetDescriptors == null) {
            try {
                PropertyDescriptor actionDescriptor = null;
                PropertyDescriptor[] propertyDescriptors = this.getPropertyDescriptors();
                for (int i = 0; i < propertyDescriptors.length && actionDescriptor == null; i++) {
                    if (propertyDescriptors[i].getName().equals("linkActionExpression")) //NOI18N
                        actionDescriptor = propertyDescriptors[i];
                }
                EventSetDescriptor actionEventDescriptor =
                        new EventSetDescriptor("linkAction", Action.class,  //NOI18N
                        new Method[] {Action.class.getMethod("action", new Class[] {})},  //NOI18N
                        null, null);
                actionEventDescriptor.setDisplayName(actionDescriptor.getDisplayName());
                actionEventDescriptor.setShortDescription(actionDescriptor.getShortDescription());
                actionEventDescriptor.setValue(Constants.EventSetDescriptor.BINDING_PROPERTY,
                        actionDescriptor);
                actionEventDescriptor.setValue(Constants.EventDescriptor.DEFAULT_EVENT_BODY,
                        DesignMessageUtil.getMessage(this.getClass(), "actionHandler")); //NOI18N
                eventSetDescriptors = new EventSetDescriptor[] {actionEventDescriptor};
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return eventSetDescriptors;
    }
    
    
    
}
