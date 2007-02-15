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
 * DefaultPropertyTest.java
 * JUnit based test
 *
 * Created on 23 juin 2006, 09:56
 */

package org.example;

import com.sun.rave.designtime.Constants;
import com.sun.rave.designtime.markup.AttributeDescriptor;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test integrity of generated BeanInfo classes.
 *
 * @author gjmurphy
 */
public class DefaultPropertyTest extends TestCase {
    
    public DefaultPropertyTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
    }
    
    protected void tearDown() throws Exception {
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(DefaultPropertyTest.class);
        return suite;
    }
    
    public void testDefaultPropertyComponent01() throws Exception {
        this.introspectBean(DefaultPropertyComponent01.class);
    }
    
    public void testDefaultPropertyComponent02() throws Exception {
        this.introspectBean(DefaultPropertyComponent02.class);
    }
    
    public void testDefaultPropertyComponent03() throws Exception {
        this.introspectBean(DefaultPropertyComponent03.class);
    }
    
    public void testDefaultPropertyComponent04() throws Exception {
        this.introspectBean(DefaultPropertyComponent04.class);
    }
    
    public void testDefaultPropertyComponent05() throws Exception {
        this.introspectBean(DefaultPropertyComponent05.class);
    }
    
    public void testDefaultPropertyComponent06() throws Exception {
        this.introspectBean(DefaultPropertyComponent06.class);
    }
    
    public void testDefaultPropertyComponent07() throws Exception {
        this.introspectBean(DefaultPropertyComponent07.class);
    }
    
    public void testDefaultPropertyComponent08() throws Exception {
        this.introspectBean(DefaultPropertyComponent08.class);
    }
    
    public void testDefaultPropertyComponent09() throws Exception {
        this.introspectBean(DefaultPropertyComponent09.class);
    }
    
    public void testDefaultPropertyComponent10() throws Exception {
        this.introspectBean(DefaultPropertyComponent10.class);
    }
    
    public void testDefaultPropertyComponent11() throws Exception {
        this.introspectBean(DefaultPropertyComponent11.class);
    }
    
    public void testDefaultPropertyComponent12() throws Exception {
        this.introspectBean(DefaultPropertyComponent12.class);
    }
    
    public void testDefaultPropertyComponent13() throws Exception {
        this.introspectBean(DefaultPropertyComponent13.class);
    }
    
    public void testDefaultPropertyComponent14() throws Exception {
        this.introspectBean(DefaultPropertyComponent14.class);
    }
    
    public void testDefaultPropertyComponent15() throws Exception {
        this.introspectBean(DefaultPropertyComponent15.class);
    }
    
    public void introspectBean(Class c) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(c);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        this.assertEquals("Wrong number of property descriptors", 1, propertyDescriptors.length);
        PropertyDescriptor propertyDescriptor = propertyDescriptors[0];
        this.assertEquals("Wrong property name: " + propertyDescriptor.getName(),
                "defaultProperty", propertyDescriptor.getName());
        this.assertEquals("Wrong property display name: " + propertyDescriptor.getDisplayName(),
                "defaultProperty", propertyDescriptor.getDisplayName());
        this.assertEquals("Wrong property short description name: " + propertyDescriptor.getShortDescription(),
                "defaultProperty", propertyDescriptor.getShortDescription());
        this.assertEquals("Wrong read method name: " + propertyDescriptor.getReadMethod().getName(),
                "getDefaultProperty", propertyDescriptor.getReadMethod().getName());
        this.assertEquals("Wrong write method name: " + propertyDescriptor.getWriteMethod().getName(),
                "setDefaultProperty", propertyDescriptor.getWriteMethod().getName());
        AttributeDescriptor attributeDescriptor =
                (AttributeDescriptor) propertyDescriptor.getValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR);
        this.assertNotNull("No attribute descriptor", attributeDescriptor);
        this.assertEquals("Wrong attribute name: " + attributeDescriptor.getName(), 
                "defaultProperty", attributeDescriptor.getName());
        this.assertTrue("Attribute is not bindable", attributeDescriptor.isBindable());
        this.assertFalse("Attribute is required", attributeDescriptor.isRequired());
    }
    
}
