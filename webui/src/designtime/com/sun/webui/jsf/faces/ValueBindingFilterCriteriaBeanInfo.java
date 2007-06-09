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
package com.sun.webui.jsf.faces;

import java.awt.Image;
import java.beans.*;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.sun.rave.designtime.CategoryDescriptor;
import com.sun.rave.designtime.Constants;
import com.sun.rave.designtime.faces.FacetDescriptor;
import com.sun.rave.designtime.markup.AttributeDescriptor;


abstract class ValueBindingFilterCriteriaBeanInfo extends SimpleBeanInfo {
    
    /**
     * The class of the component (bean) to which this BeanInfo corresponds.
     */
    protected Class beanClass = com.sun.webui.jsf.faces.ValueBindingFilterCriteria.class;

    protected static ResourceBundle resourceBundle =
        ResourceBundle.getBundle("com.sun.webui.jsf.faces.BeanInfoBundle", Locale.getDefault(),ValueBindingFilterCriteriaBeanInfo.class.getClassLoader());
    
    private BeanDescriptor beanDescriptor;
    

    /**
     * Return the <code>BeanDescriptor</code> for this bean.
     */
    public BeanDescriptor getBeanDescriptor() {

        if (beanDescriptor == null) {
            beanDescriptor = new BeanDescriptor(this.beanClass);
            beanDescriptor.setDisplayName(resourceBundle.getString("ValueBindingFilterCriteria_displayName"));
            beanDescriptor.setShortDescription(resourceBundle.getString("ValueBindingFilterCriteria_shortDescription"));
            beanDescriptor.setValue(Constants.BeanDescriptor.FACET_DESCRIPTORS,
                    this.getFacetDescriptors());
            beanDescriptor.setValue(Constants.BeanDescriptor.HELP_KEY, "");
            beanDescriptor.setValue(Constants.BeanDescriptor.INSTANCE_NAME, "valueBindingFilterCriteria");
            beanDescriptor.setValue(Constants.BeanDescriptor.PROPERTIES_HELP_KEY,"");
            beanDescriptor.setValue(Constants.BeanDescriptor.PROPERTY_CATEGORIES,
                    this.getCategoryDescriptors());
        }
        return beanDescriptor;
        
    }
    
    
    private int defaultPropertyIndex = -2;
    
    /**
     * Return the index of the default property, or -1 if there is no default property.
     */
    public int getDefaultPropertyIndex() {
        if (defaultPropertyIndex == -2) {
            defaultPropertyIndex = -1;
        }
        return defaultPropertyIndex;
    }
    
    private int defaultEventIndex = -2;
    
    /**
     * Return the index of the default event, or -1 if there is no default event.
     */
    public int getDefaultEventIndex() {
        if (defaultEventIndex == -2) {
            defaultEventIndex = -1;
        }
        return defaultEventIndex;
    }

    private CategoryDescriptor[] categoryDescriptors;
    
    /**
     * Returns an array of <code>CategoryDescriptor</code>s, representing all
     * property categories referenced by properties of this component.
     */
    protected CategoryDescriptor[] getCategoryDescriptors() {
        if (categoryDescriptors == null) {
            categoryDescriptors = new CategoryDescriptor[]{
            };
        }
        return categoryDescriptors;
    }
    
    private FacetDescriptor[] facetDescriptors;
    
    /**
     * Returns an array of <code>FacetDescriptor</code>s for the component.
     */
    public FacetDescriptor[] getFacetDescriptors() {
        if (facetDescriptors == null)
            facetDescriptors = new FacetDescriptor[] {};
        return facetDescriptors;
    }
    
    
    // The 16x16 color icon.
    protected String iconFileName_C16 = "ValueBindingFilterCriteria_C16";
    
    // The 32x32 color icon.
    private String iconFileName_C32 = "ValueBindingFilterCriteria_C32";
    
    // The 16x16 monochrome icon.
    private String iconFileName_M16 = "ValueBindingFilterCriteria_M16";
    
    // The 32x32 monochrome icon.
    private String iconFileName_M32 = "ValueBindingFilterCriteria_C32";
    
    /**
     * Returns an appropriate image icon (if any) for the component.
     */
    public Image getIcon(int kind) {
        String name;
        switch (kind) {
            case ICON_COLOR_16x16:
                name = iconFileName_C16;
                break;
            case ICON_COLOR_32x32:
                name = iconFileName_C32;
                break;
            case ICON_MONO_16x16:
                name = iconFileName_M16;
                break;
            case ICON_MONO_32x32:
                name = iconFileName_M32;
                break;
            default:
                name = null;
                break;
        }
        if (name == null)
            return null;
        Image image = loadImage(name + ".png");
        if (image == null)
            image = loadImage(name + ".gif");
        return image;
        
    }
    
    
    private PropertyDescriptor[] propertyDescriptors;
    
    /**
     * Returns the <code>PropertyDescriptor</code>s for this component.
     */
    public PropertyDescriptor[] getPropertyDescriptors() {

        if (propertyDescriptors == null) {
            try {
                Map<String,PropertyDescriptor> propertyDescriptorMap = new HashMap<String,PropertyDescriptor>();
                PropertyDescriptor propertyDescriptor;
                AttributeDescriptor attributeDescriptor;
                

                propertyDescriptor =
                    new PropertyDescriptor("valueExpression", this.beanClass, "getValueExpression", "setValueExpression");
                propertyDescriptor.setDisplayName(resourceBundle.getString("ValueBindingFilterCriteria_valueExpression_displayName"));
                propertyDescriptor.setShortDescription(resourceBundle.getString("ValueBindingFilterCriteria_valueExpression_shortDescription"));
                propertyDescriptorMap.put(propertyDescriptor.getName(), propertyDescriptor);

                propertyDescriptor =
                    new PropertyDescriptor("requestMapKey", this.beanClass, "getRequestMapKey", "setRequestMapKey");
                propertyDescriptor.setDisplayName(resourceBundle.getString("ValueBindingFilterCriteria_requestMapKey_displayName"));
                propertyDescriptor.setShortDescription(resourceBundle.getString("ValueBindingFilterCriteria_requestMapKey_shortDescription"));
                 attributeDescriptor = new AttributeDescriptor("requestMapKey",false,null,true);
                propertyDescriptor.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR, attributeDescriptor);
                propertyDescriptorMap.put(propertyDescriptor.getName(), propertyDescriptor);

                propertyDescriptor =
                    new PropertyDescriptor("compareValue", this.beanClass, "getCompareValue", "setCompareValue");
                propertyDescriptor.setDisplayName(resourceBundle.getString("ValueBindingFilterCriteria_compareValue_displayName"));
                propertyDescriptor.setShortDescription(resourceBundle.getString("ValueBindingFilterCriteria_compareValue_shortDescription"));
                 attributeDescriptor = new AttributeDescriptor("compareValue",false,null,true);
                propertyDescriptor.setValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR, attributeDescriptor);
                propertyDescriptorMap.put(propertyDescriptor.getName(), propertyDescriptor);

                // Property declaration inherited from com.sun.data.provider.FilterCriteria
                propertyDescriptor =
                    new PropertyDescriptor("class", this.beanClass, "getClass", null);
                propertyDescriptor.setDisplayName(resourceBundle.getString("FilterCriteria_class_displayName"));
                propertyDescriptor.setShortDescription(resourceBundle.getString("FilterCriteria_class_shortDescription"));
                propertyDescriptorMap.put(propertyDescriptor.getName(), propertyDescriptor);

                // Property declaration inherited from com.sun.data.provider.FilterCriteria
                propertyDescriptor =
                    new PropertyDescriptor("include", this.beanClass, "isInclude", "setInclude");
                propertyDescriptor.setDisplayName(resourceBundle.getString("FilterCriteria_include_displayName"));
                propertyDescriptor.setShortDescription(resourceBundle.getString("FilterCriteria_include_shortDescription"));
                propertyDescriptorMap.put(propertyDescriptor.getName(), propertyDescriptor);

                // Property declaration inherited from com.sun.data.provider.FilterCriteria
                propertyDescriptor =
                    new PropertyDescriptor("displayName", this.beanClass, "getDisplayName", "setDisplayName");
                propertyDescriptor.setDisplayName(resourceBundle.getString("FilterCriteria_displayName_displayName"));
                propertyDescriptor.setShortDescription(resourceBundle.getString("FilterCriteria_displayName_shortDescription"));
                propertyDescriptorMap.put(propertyDescriptor.getName(), propertyDescriptor);

   
                Collection<PropertyDescriptor> propertyDescriptorCollection = 
                    propertyDescriptorMap.values();
                propertyDescriptors =
                    propertyDescriptorCollection.toArray(new PropertyDescriptor[propertyDescriptorCollection.size()]);

            } catch (IntrospectionException e) {
                e.printStackTrace();
                return null;
            }
        }
         return propertyDescriptors;
    }

    private EventSetDescriptor[] eventSetDescriptors;

    public EventSetDescriptor[] getEventSetDescriptors() {
        return eventSetDescriptors;
    }
    
    /**
     * Utility method that returns a class loaded by name via the class loader that 
     * loaded this class.
     */
    private Class loadClass(java.lang.String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
}
