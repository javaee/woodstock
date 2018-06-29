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

package com.sun.webui.jsf.component;

import com.sun.rave.faces.event.Action;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.component.util.DesignUtil;
import java.beans.BeanDescriptor;
import java.beans.EventSetDescriptor;
import java.beans.PropertyDescriptor;
import com.sun.rave.designtime.Constants;
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
