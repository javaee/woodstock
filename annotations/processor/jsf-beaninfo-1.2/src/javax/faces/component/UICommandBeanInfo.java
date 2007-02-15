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

package javax.faces.component;

import com.sun.rave.designtime.Constants;
import com.sun.rave.designtime.markup.AttributeDescriptor;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.swing.Action;

/**
 * BeanInfo for {@link javax.faces.component.UICommand}.
 *
 * @author gjmurphy
 */
public class UICommandBeanInfo extends UIComponentBaseBeanInfo {
    
    public UICommandBeanInfo() {
        super(UICommand.class);
    }
    
    private PropertyDescriptor[] propertyDescriptors;
    
    public PropertyDescriptor[] getPropertyDescriptors() {
        
        if (propertyDescriptors == null) {
            try {
                List<PropertyDescriptor> propertyDescriptorList = new ArrayList<PropertyDescriptor>();
                propertyDescriptorList.addAll(Arrays.asList(super.getPropertyDescriptors()));
                AttributeDescriptor attrib = null;
                
                PropertyDescriptor prop_actionExpr = new PropertyDescriptor("actionExpression",UICommand.class,"getActionExpression","setActionExpression");
                prop_actionExpr.setDisplayName(resourceBundle.getString("UICommand_actionExpression_DisplayName"));
                prop_actionExpr.setShortDescription(resourceBundle.getString("UICommand_actionExpression_Description"));
                prop_actionExpr.setPropertyEditorClass(loadClass(PropertyEditorConstants.METHODBINDING_EDITOR));
                attrib = new AttributeDescriptor("actionExpression",false,null,true);
                prop_actionExpr.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR,attrib);
                propertyDescriptorList.add(prop_actionExpr);
                
                PropertyDescriptor prop_immediate = new PropertyDescriptor("immediate",UICommand.class,"isImmediate","setImmediate");
                prop_immediate.setDisplayName(resourceBundle.getString("UICommand_immediate_DisplayName"));
                prop_immediate.setShortDescription(resourceBundle.getString("UICommand_immediate_Description"));
                attrib = new AttributeDescriptor("immediate",false,null,true);
                prop_immediate.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR,attrib);
                prop_immediate.setValue(Constants.PropertyDescriptor.CATEGORY, CategoryDescriptorsConstants.ADVANCED);
                propertyDescriptorList.add(prop_immediate);
                
                PropertyDescriptor prop_value = new PropertyDescriptor("value",UICommand.class,"getValue","setValue");
                prop_value.setDisplayName(resourceBundle.getString("UICommand_value_DisplayName"));
                prop_value.setShortDescription(resourceBundle.getString("UICommand_value_Description"));
                prop_value.setPropertyEditorClass(loadClass(PropertyEditorConstants.VALUEBINDING_EDITOR));
                attrib = new AttributeDescriptor("value",false,null,true);
                prop_value.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR,attrib);
                propertyDescriptorList.add(prop_value);
                
                propertyDescriptors = (PropertyDescriptor[]) propertyDescriptorList.toArray(
                        new PropertyDescriptor[propertyDescriptorList.size()]);
                
            } catch (IntrospectionException e) {
                e.printStackTrace();
                return null;
            }
        }
        
        return propertyDescriptors;
        
    }
    
    EventSetDescriptor[] eventSetDescriptors;
    
    public EventSetDescriptor[] getEventSetDescriptors() {
        if (eventSetDescriptors == null) {
            try {
                EventSetDescriptor actionEventDescriptor =
                        new EventSetDescriptor("action", ActionListener.class,
                        new Method[] {ActionListener.class.getMethod("processAction", new Class[] {ActionEvent.class})},
                        UICommand.class.getMethod("addActionListener", new Class[] {ActionListener.class}),
                        UICommand.class.getMethod("removeActionListener", new Class[] {ActionListener.class}),
                        UICommand.class.getMethod("getActionListeners", new Class[] {}));
                eventSetDescriptors = new EventSetDescriptor[] {actionEventDescriptor};
            } catch (IntrospectionException e) {
                e.printStackTrace();
                return null;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }
        }
        return eventSetDescriptors;
    }
    
}
