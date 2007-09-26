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

import com.sun.rave.designtime.CategoryDescriptor;
import com.sun.webui.jsf.component.util.DesignUtil;
import java.beans.BeanDescriptor;
import java.beans.EventSetDescriptor;
import com.sun.rave.designtime.Constants;
import com.sun.webui.jsf.design.CategoryDescriptors;

import javax.faces.context.FacesContext;

import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * BeanInfo for the {@link com.sun.webui.jsf.component.AccordionTab} component.
 *
 * @author deep
 */
public class AccordionTabBeanInfo extends AccordionTabBeanInfoBase {

    public AccordionTabBeanInfo() {
       
        BeanDescriptor beanDescriptor = super.getBeanDescriptor();
              
    }
    
    public EventSetDescriptor[] getEventSetDescriptors() {
         return new EventSetDescriptor[0];
     }
    
}