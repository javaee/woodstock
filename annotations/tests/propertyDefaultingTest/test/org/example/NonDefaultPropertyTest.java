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
/*
 * NonDefaultPropertyTest.java
 * JUnit based test
 *
 * Created on 24 juin 2006, 20:18
 */

package org.example;

import com.sun.rave.designtime.CategoryDescriptor;
import com.sun.rave.designtime.Constants;
import com.sun.rave.designtime.markup.AttributeDescriptor;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import junit.framework.*;

/**
 *
 * @author gjmurphy
 */
public class NonDefaultPropertyTest extends TestCase {
    
    public NonDefaultPropertyTest(String testName) {
        super(testName);
    }
    
    BeanInfo beanInfo;
    Map<String,PropertyDescriptor> propertyMap = new HashMap<String,PropertyDescriptor>();
    
    protected void setUp() throws Exception {
        beanInfo = Introspector.getBeanInfo(NonDefaultPropertyComponent.class);
        for (PropertyDescriptor p : beanInfo.getPropertyDescriptors()) {
            propertyMap.put(p.getName(), p);
        }
    }
    
    public void testNonAttributeProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("nonAttribute");
        this.assertNull(p.getValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR));
    }
    
    public void testNamedAttributeProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("namedAttribute");
        this.assertEquals(((AttributeDescriptor) p.getValue(
                (Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR))).getName(), "specialNamedAttribute");
    }
    
    public void testRequiredAttributeProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("requiredAttribute");
        this.assertTrue(((AttributeDescriptor) p.getValue(
                (Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR))).isRequired());
    }
    
    public void testReadOnlyProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("readOnlyProperty");
        this.assertNull(p.getWriteMethod());
    }
    
    public void testWriteOnlyProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("writeOnlyProperty");
        this.assertNull(p.getReadMethod());
    }
    
    public void testCategorizedProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("categorizedProperty");
        CategoryDescriptor c = (CategoryDescriptor) p.getValue(Constants.PropertyDescriptor.CATEGORY);
        this.assertNotNull(c);
        this.assertEquals("category", c.getName());
    }
    
    public void testHiddenProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("hiddenProperty");
        this.assertTrue(p.isHidden());
    }
    
    public void testDefaultProperty() throws Exception {
        int index = this.beanInfo.getDefaultPropertyIndex();
        this.assertTrue("No default property index", index >= 0);
        this.assertEquals("Wrong default property name", "defaultProperty", this.beanInfo.getPropertyDescriptors()[index].getName());
    }
    
    public void testNamedProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("namedProperty");
        this.assertEquals("Wrong read method name", "getNamedProperty", p.getReadMethod().getName());
        this.assertEquals("Wrong write method name", "setNamedProperty", p.getWriteMethod().getName());
    }
    
    public void testDifferentReadNamedProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("readNamedProperty");
        this.assertEquals("Wrong read method name", "getDifferentReadNamedProperty", p.getReadMethod().getName());
        this.assertEquals("Wrong write method name", "setReadNamedProperty", p.getWriteMethod().getName());
    }
    
    public void testDifferentWriteNamedProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("writeNamedProperty");
        this.assertEquals("Wrong read method name", "getWriteNamedProperty", p.getReadMethod().getName());
        this.assertEquals("Wrong write method name", "setDifferentWriteNamedProperty", p.getWriteMethod().getName());
    }
    
    public void testDifferentBothNamedProperty() throws Exception {
        PropertyDescriptor p = getPropertyDescriptor("bothNamedProperty");
        this.assertEquals("Wrong read method name", "getDifferentBothNamedProperty", p.getReadMethod().getName());
        this.assertEquals("Wrong write method name", "setDifferentBothNamedProperty", p.getWriteMethod().getName());
    }
    
    protected void tearDown() throws Exception {
    }
    
    private PropertyDescriptor getPropertyDescriptor(String name) throws Exception {
        PropertyDescriptor p = propertyMap.get(name);
        this.assertNotNull("No such property: " + name, p);
        return p;
    }
    
}
