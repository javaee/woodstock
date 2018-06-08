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

package com.sun.webui.jsf.component.util;

import com.sun.rave.designtime.Constants;
import com.sun.rave.faces.event.Action;

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
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.rave.designtime.CategoryDescriptor;

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
}
