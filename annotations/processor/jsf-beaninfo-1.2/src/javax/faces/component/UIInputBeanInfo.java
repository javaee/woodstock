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
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.validator.Validator;

/**
 * BeanInfo for {@link javax.faces.component.UIInput}.
 *
 * @author gjmurphy
 */
public class UIInputBeanInfo extends UIOutputBeanInfo {
    
    public UIInputBeanInfo() {
        super(UIInput.class);
    }
    
    private PropertyDescriptor[] propertyDescriptors;
    
    public PropertyDescriptor[] getPropertyDescriptors() {
        
        if (propertyDescriptors == null) {
            try {
                List<PropertyDescriptor> propertyDescriptorList = new ArrayList<PropertyDescriptor>();
                propertyDescriptorList.addAll(Arrays.asList(super.getPropertyDescriptors()));
                AttributeDescriptor attrib = null;
                
                PropertyDescriptor prop_validator = new PropertyDescriptor("validator", UIInput.class, "getValidator", "setValidator");
                prop_validator.setDisplayName(resourceBundle.getString("UIInput_validator_DisplayName"));
                prop_validator.setShortDescription(resourceBundle.getString("UIInput_validator_Description"));
                prop_validator.setPropertyEditorClass(loadClass(PropertyEditorConstants.VALIDATOR_EDITOR));
                attrib = new AttributeDescriptor("validator", false, null, true);
                prop_validator.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR, attrib);
                prop_validator.setValue(Constants.PropertyDescriptor.CATEGORY, CategoryDescriptorsConstants.DATA);
                propertyDescriptorList.add(prop_validator);
                
                PropertyDescriptor prop_immediate = new PropertyDescriptor("immediate",UIInput.class,"isImmediate","setImmediate");
                prop_immediate.setDisplayName(resourceBundle.getString("UIInput_immediate_DisplayName"));
                prop_immediate.setShortDescription(resourceBundle.getString("UIInput_immediate_Description"));
                attrib = new AttributeDescriptor("immediate",false,null,true);
                prop_immediate.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR,attrib);
                prop_immediate.setValue(Constants.PropertyDescriptor.CATEGORY, CategoryDescriptorsConstants.ADVANCED);
                propertyDescriptorList.add(prop_immediate);
                
                PropertyDescriptor prop_required = new PropertyDescriptor("required",UIInput.class,"isRequired","setRequired");
                prop_required.setDisplayName(resourceBundle.getString("UIInput_required_DisplayName"));
                prop_required.setShortDescription(resourceBundle.getString("UIInput_required_Description"));
                attrib = new AttributeDescriptor("required",false,null,true);
                prop_required.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR,attrib);
                prop_required.setValue(Constants.PropertyDescriptor.CATEGORY, CategoryDescriptorsConstants.DATA);
                propertyDescriptorList.add(prop_required);
                
                PropertyDescriptor prop_submittedValue = new PropertyDescriptor("submittedValue",UIInput.class,"getSubmittedValue","setSubmittedValue");
                prop_submittedValue.setHidden(true);
                propertyDescriptorList.add(prop_submittedValue);
                
                PropertyDescriptor prop_localValue = new PropertyDescriptor("localValue",UIInput.class,"getlocalValue",null);
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
    
    EventSetDescriptor[] eventSetDescriptors;
    
    public EventSetDescriptor[] getEventSetDescriptors() {
        if (eventSetDescriptors == null) {
            try {
                EventSetDescriptor valueChangeEventDescriptor =
                        new EventSetDescriptor("valueChange", ValueChangeListener.class,
                        new Method[] {ValueChangeListener.class.getMethod("processValueChange", new Class[] {ValueChangeEvent.class})},
                        UIInput.class.getMethod("addValueChangeListener", new Class[] {ValueChangeListener.class}),
                        UIInput.class.getMethod("removeValueChangeListener", new Class[] {ValueChangeListener.class}),
                        UIInput.class.getMethod("getValueChangeListeners", new Class[] {}));
                EventSetDescriptor validateEventDescriptor =
                        new EventSetDescriptor("validate", Validator.class,
                        new Method[] {Validator.class.getMethod("validate", new Class[] {FacesContext.class, UIComponent.class, Object.class})},
                        null, null);
                eventSetDescriptors = new EventSetDescriptor[] {valueChangeEventDescriptor,validateEventDescriptor};
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
