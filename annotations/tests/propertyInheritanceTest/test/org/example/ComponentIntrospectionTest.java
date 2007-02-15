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
 * ComponentIntrospectionTest.java
 * JUnit based test
 *
 * Created on 23 juin 2006, 09:56
 */

package org.example;

import com.sun.rave.designtime.Constants;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test integrity of generated BeanInfo classes.
 *
 * @author gjmurphy
 */
public class ComponentIntrospectionTest extends TestCase {
    
    Set<String> displayNameSet = new HashSet();
    
    public ComponentIntrospectionTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
        this.displayNameSet.add("The First");
        this.displayNameSet.add("The Second");
        this.displayNameSet.add("The Third");
    }
    
    protected void tearDown() throws Exception {
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(ComponentIntrospectionTest.class);
        return suite;
    }
    
    public void testInheritance01() throws Exception {
        this.introspectBean(Component01.class);
    }
    
    public void testInheritance02() throws Exception {
        this.introspectBean(Component02.class);
    }
    
    public void testInheritance03() throws Exception {
        this.introspectBean(Component03.class);
    }
    
    public void testInheritance04() throws Exception {
        this.introspectBean(Component04.class);
    }
    
    public void testInheritance05() throws Exception {
        this.introspectBean(Component05.class);
    }
    
    public void testInheritance06() throws Exception {
        this.introspectBean(Component06.class);
    }
    
    public void testInheritance07() throws Exception {
        this.introspectBean(Component07.class);
        this.introspectHiddenProperties(Component07.class);
    }
    
    public void testInheritance08() throws Exception {
        this.introspectBean(Component08.class);
    }
    
    public void testInheritance09() throws Exception {
        this.introspectBean(Component09.class);
    }
    
    public void testInheritance10() throws Exception {
        this.introspectBean(Component10.class);
    }
    
    public void testInheritance11() throws Exception {
        this.introspectBean(Component11.class);
    }
    
    public void testInheritance12() throws Exception {
        this.introspectBean(Component12.class);
        this.introspectUnhiddenProperties(Component12.class);
    }
    
    public void testInheritance13() throws Exception {
        this.introspectBean(Component13.class);
    }
    
    public void testInheritance14() throws Exception {
        this.introspectBean(Component14.class);
        this.introspectUnhiddenProperties(Component14.class);
    }
    
    public void testInheritance16() throws Exception {
        this.introspectBean(Component16.class);
        this.introspectUnhiddenProperties(Component16.class);
    }
    
    public void introspectHiddenProperties(Class c) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(c);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor p : propertyDescriptors) {
            this.assertTrue("Property not hidden: " + p.getName(), p.isHidden());
            this.assertNull("Property has attribute: " + p.getName(),
                    p.getValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR));
        }
    }
    
    public void introspectUnhiddenProperties(Class c) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(c);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor p : propertyDescriptors) {
            this.assertFalse("Property is hidden: " + p.getName(), p.isHidden());
            this.assertTrue("Missing category descriptor", CategoryDescriptors.CATEGORY.equals(p.getValue(Constants.PropertyDescriptor.CATEGORY)));
            this.assertNotNull("Property missing attribute: " + p.getName(),
                    p.getValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR));
        }
    }
    
    public void introspectBean(Class c) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(c);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        this.assertEquals("Wrong number of property descriptors", 3, propertyDescriptors.length);
        for (PropertyDescriptor p : propertyDescriptors) {
            if (!p.isHidden())
                this.assertTrue("Incorrect property display name", this.displayNameSet.contains(p.getDisplayName()));
        }
    }
    
}
