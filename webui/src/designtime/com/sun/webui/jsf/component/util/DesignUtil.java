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

package com.sun.webui.jsf.component.util;

import com.sun.rave.designtime.Constants;
import com.sun.rave.faces.event.Action;
import com.sun.rave.designtime.CategoryDescriptor;
import com.sun.rave.propertyeditors.domains.Domain;
import com.sun.rave.propertyeditors.DomainPropertyEditor;
import com.sun.rave.propertyeditors.SelectOneDomainEditor;

import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.validator.Validator;

/**
 * Miscellaneous design-time utility methods
 *
 * @author Edwin Goei
 * @author gjmurphy
 * @author mbohm
 */
public class DesignUtil {
    
    private static Pattern numericalSuffixPattern = Pattern.compile("\\d*$");
    
    /**
     * A utility method that locates the numerical suffix of a typical bean
     * instance name, and returns it. If no numerical suffix is found the
     * empty string is returned.
     */
    public static String getNumericalSuffix(String name) {
        Matcher matcher = numericalSuffixPattern.matcher(name);
        matcher.find();
        return matcher.group();
    }
    
    /**
     * Add default body and parameter names to the event descriptors for the
     * standard input component event, valueChange, and the pseudo-event validate.
     */
    public static void updateInputEventSetDescriptors(BeanInfo beanInfo) {
        EventSetDescriptor[] eventSetDescriptors = beanInfo.getEventSetDescriptors();
        if (eventSetDescriptors != null) {
            for (EventSetDescriptor descriptor : eventSetDescriptors) {
                if (descriptor.getName().equals("validate")) {
                    descriptor.setValue(Constants.EventDescriptor.DEFAULT_EVENT_BODY,
                            DesignMessageUtil.getMessage(beanInfo.getClass(), "validateHandler"));
                    descriptor.setValue(Constants.EventDescriptor.PARAMETER_NAMES,
                            new String[] { "context", "component", "value" });
                }
                if (descriptor.getName().equals("valueChange")) {
                    descriptor.setValue(Constants.EventDescriptor.PARAMETER_NAMES,
                            new String[] { "event" });
                }
            }
        }
    }
    
    /**
     * Generate an "action" event descriptor for command components. This event
     * descriptor is generated especially because the event does not conform to the
     * Java Beans patterns for events: there is no listener class, and no add
     * or remove listener methods.
     */
    public static EventSetDescriptor[] generateCommandEventSetDescriptors(BeanInfo beanInfo) {
        try {
            PropertyDescriptor actionDescriptor = null;
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length && actionDescriptor == null; i++) {
                if (propertyDescriptors[i].getName().equals("actionExpression")) //NOI18N
                    actionDescriptor = propertyDescriptors[i];
            }
            EventSetDescriptor actionEventDescriptor =
                    new EventSetDescriptor("action", Action.class,  //NOI18N
                    new Method[] {Action.class.getMethod("action", new Class[] {})},  //NOI18N
                    null, null);
            actionEventDescriptor.setDisplayName(
                    DesignMessageUtil.getMessage(DesignUtil.class, "DesignUtil.event.action"));
            actionEventDescriptor.setValue(Constants.EventSetDescriptor.BINDING_PROPERTY,
                    actionDescriptor);
            actionEventDescriptor.setShortDescription(actionDescriptor.getShortDescription());
            actionEventDescriptor.setValue(
                    Constants.EventDescriptor.DEFAULT_EVENT_BODY,
                    DesignMessageUtil.getMessage(beanInfo.getClass(), "actionHandler")); //NOI18N
            return new EventSetDescriptor[] {actionEventDescriptor};
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }    
    
    public static void hideProperties(BeanInfo beanInfo, String[] hide) {
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < descriptors.length; i++) {
            for (int h = 0; h < hide.length; h++) {
                if (descriptors[i].getName().equals(hide[h]))  {//NOI18N
                    descriptors[i].setHidden(true);
                    break;
                }
            }
        }
    }
    
    public static PropertyDescriptor getPropertyDescriptor(BeanInfo beanInfo, String name) {
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < descriptors.length; i++) {
            if (name.equals(descriptors[i].getName())) { //let NullPointerException be thrown
                return descriptors[i];
            }
        }
        return null;
    }
    
    public static void applyPropertyCategoryAndEditor(BeanInfo beanInfo, String name, CategoryDescriptor category, String editor) {
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(beanInfo, name);
        if (propertyDescriptor == null) {
            System.err.println("com.sun.webui.jsf.component.util.DesignUtil.applyPropertyCategoryAndEditor: Warning: could not find property " + name + " for beanInfo " + beanInfo.getClass().getName()); //NOI18N
            return;
        }
        if (category != null) {
            propertyDescriptor.setValue(Constants.PropertyDescriptor.CATEGORY, category);
        }
        if (editor != null) {
            propertyDescriptor.setPropertyEditorClass(loadClass(editor));   
        }
    }
    
    public static void applyPropertyCategory(BeanInfo beanInfo, String name, CategoryDescriptor category) {
        applyPropertyCategoryAndEditor(beanInfo, name, category, null);
    }
    
    public static void applyPropertyDomain(BeanInfo beaninfo, String name, Class domainClass) {
        PropertyDescriptor p = getPropertyDescriptor(beaninfo, name);
        if (p != null) {
            p.setPropertyEditorClass(SelectOneDomainEditor.class);
            p.setValue(DomainPropertyEditor.DOMAIN_CLASS, domainClass);
        }
    }
    
    /**
     * Utility method that returns a class loaded by name via the class loader that 
     * loaded this class.
     */
    private static Class loadClass(java.lang.String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Utility method that returns a updated style value.
     * This method is only useful for properties that may be the last hypen separated 
     * substring of some other property, for example height and width.    
     * This method is used to append default attribute to style if already not
     * present in style.
     * This method always at least returns styleValue even if there are no replacements.
     * This also doesn't work if "attribute" is literally in the styleValue more than once.
     *
     */
    public static String parseStyle(String styleValue, String attribute, String defaultValue) {
        if (styleValue == null || attribute == null || defaultValue == null) {
            return null;
        }
        String temp = styleValue.trim().toLowerCase();
  
        temp = temp.replaceAll("-"+attribute, "!!!!!!!"); 
              
        if (temp.indexOf(attribute) == -1) {
            StringBuilder sb = new StringBuilder(); 
            if (styleValue.charAt(styleValue.length() - 1) == ';') 
                temp = (sb.append(styleValue).append(" ").append(defaultValue)).toString();
            else
                temp = (sb.append(styleValue).append("; ").append(defaultValue)).toString();
            return temp;
        }    
          
        return styleValue;
    }
}
