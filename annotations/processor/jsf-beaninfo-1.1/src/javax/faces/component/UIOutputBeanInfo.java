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

package javax.faces.component;

import com.sun.rave.designtime.Constants;
import com.sun.rave.designtime.markup.AttributeDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * BeanInfo for {@link javax.faces.component.UICommand}.
 *
 * @author gjmurphy
 */
public class UIOutputBeanInfo extends UIComponentBaseBeanInfo {
    
    public UIOutputBeanInfo() {
        super(UIOutput.class);
    }
    
    public UIOutputBeanInfo(Class beanClass) {
        super(beanClass);
    }
    
    private PropertyDescriptor[] propertyDescriptors;
    
    public PropertyDescriptor[] getPropertyDescriptors() {
        
        if (propertyDescriptors == null) {
            try {
                List<PropertyDescriptor> propertyDescriptorList = new ArrayList<PropertyDescriptor>();
                propertyDescriptorList.addAll(Arrays.asList(super.getPropertyDescriptors()));
                AttributeDescriptor attrib = null;
                
                PropertyDescriptor prop_converter = new PropertyDescriptor("converter", UIOutput.class, "getConverter", "setConverter");
                prop_converter.setDisplayName(resourceBundle.getString("UIOutput_converter_DisplayName"));
                prop_converter.setShortDescription(resourceBundle.getString("UIOutput_converter_Description"));
                prop_converter.setPropertyEditorClass(loadClass(PropertyEditorConstants.CONVERTER_EDITOR));
                attrib = new AttributeDescriptor("converter", false, null, true);
                prop_converter.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR, attrib);
                propertyDescriptorList.add(prop_converter);
                
                PropertyDescriptor prop_value = new PropertyDescriptor("value", UIOutput.class, "getValue", "setValue");
                prop_value.setDisplayName(resourceBundle.getString("UICommand_value_DisplayName"));
                prop_value.setShortDescription(resourceBundle.getString("UICommand_value_Description"));
                prop_value.setPropertyEditorClass(loadClass(PropertyEditorConstants.VALUEBINDING_EDITOR));
                attrib = new AttributeDescriptor("value", false, null, true);
                prop_value.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR, attrib);
                propertyDescriptorList.add(prop_value);
                
                PropertyDescriptor prop_localValue = new PropertyDescriptor("localValue",UIOutput.class,"getLocalValue",null);
                prop_localValue.setHidden(true);
                propertyDescriptorList.add(prop_localValue);
                
                propertyDescriptors = (PropertyDescriptor[]) propertyDescriptorList.toArray(
                        new PropertyDescriptor[propertyDescriptorList.size()]);
                
            } catch (IntrospectionException e) {
                e.printStackTrace();
                return null;
            }
        }
        
        return propertyDescriptors;
        
    }
}
