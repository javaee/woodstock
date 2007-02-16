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

import java.beans.BeanDescriptor;
import com.sun.rave.designtime.Constants;

//import javax.faces.context.FacesContext;
//import com.sun.webui.theme.Theme;
//import com.sun.webui.jsf.theme.ThemeStyles;
//import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * BeanInfo for the {@link com.sun.webui.jsf.component.PropertySheetSection} 
 * component.
 */
public class PropertySheetSectionBeanInfo extends PropertySheetSectionBeanInfoBase {
    
    /** Creates a new instance of PropertySheetSectionBeanInfo */
    public PropertySheetSectionBeanInfo() {
        BeanDescriptor beanDescriptor = super.getBeanDescriptor();         
        beanDescriptor.setValue(Constants.BeanDescriptor.PREFERRED_CHILD_TYPES,
                new String[] {Property.class.getName()});
        // Not yet working right
	//Theme theme =
	//    ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
	//String conFldLegDiv =
	//    theme.getStyleClass(ThemeStyles.CONTENT_FIELDSET_LEGEND_DIV);
        //beanDescriptor.setValue(
        //    Constants.BeanDescriptor.INLINE_EDITABLE_PROPERTIES,
        //    new String[] { "*label://div[@class='" + conFldLegDiv + //NOI18N
	//	  "']" }); // NOI18N
    }

}
