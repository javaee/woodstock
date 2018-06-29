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

/*
 * EventInheritanceTest.java
 * JUnit based test
 *
 * Created on 23 juin 2006, 09:56
 */

package org.example;

import com.sun.rave.designtime.Constants;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
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
public class EventInheritanceTest extends TestCase {
    
    public EventInheritanceTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
    }
    
    protected void tearDown() throws Exception {
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(EventInheritanceTest.class);
        return suite;
    }
    
    public void testInheritanceComponent01() throws Exception {
        this.introspectBeanTest01(ComponentBean01.class);
    }
    
    public void testInheritanceComponent02() throws Exception {
        this.introspectBeanTest01(ComponentBean02.class);
    }
    
    public void testInheritanceComponent03() throws Exception {
        this.introspectBeanTest01(ComponentBean03.class);
    }
    
    public void testInheritanceComponent04() throws Exception {
        this.introspectBeanTest01(ComponentBean04.class);
    }
    
    public void testInheritanceComponent05() throws Exception {
        this.introspectBeanTest02(ComponentBean05.class);
    }
    
    public void testInheritanceComponent06() throws Exception {
        this.introspectBeanTest02(ComponentBean06.class);
    }
    
    
    public void introspectBeanTest01(Class c) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(c);
        EventSetDescriptor[] eventSetDescriptors = beanInfo.getEventSetDescriptors();
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        this.assertEquals("Wrong number of event descriptors", 2, eventSetDescriptors.length);
        this.assertEquals("Wrong number of property descriptors", 1, propertyDescriptors.length);
        EventSetDescriptor introspectedAction01Event = findEventSetDescriptor(eventSetDescriptors, "introspectedAction01");
        this.assertNotNull("No such event: introspectedAction01", introspectedAction01Event);
        EventSetDescriptor introspectedAction02Event = findEventSetDescriptor(eventSetDescriptors, "introspectedAction02");
        this.assertNotNull("No such event: introspectedAction02", introspectedAction02Event);
        PropertyDescriptor introspectedAction02EventListener = propertyDescriptors[0];
        this.assertTrue(introspectedAction02EventListener.equals(
                introspectedAction02Event.getValue(Constants.EventSetDescriptor.BINDING_PROPERTY)));
    }
    
    
    public void introspectBeanTest02(Class c) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(c);
        EventSetDescriptor[] eventSetDescriptors = beanInfo.getEventSetDescriptors();
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        this.assertEquals("Wrong number of event descriptors", 2, eventSetDescriptors.length);
        this.assertEquals("Wrong number of property descriptors", 2, propertyDescriptors.length);
        EventSetDescriptor introspectedAction01Event = findEventSetDescriptor(eventSetDescriptors, "introspectedAction01");
        this.assertNotNull("No such event: introspectedAction01", introspectedAction01Event);
        EventSetDescriptor introspectedAction02Event = findEventSetDescriptor(eventSetDescriptors, "introspectedAction02");
        this.assertNotNull("No such event: introspectedAction02", introspectedAction02Event);
        PropertyDescriptor introspectedAction01EventListener = 
                findPropertyDescriptor(propertyDescriptors, "introspectedListener1Expression");
        this.assertTrue(introspectedAction01EventListener.equals(
                introspectedAction01Event.getValue(Constants.EventSetDescriptor.BINDING_PROPERTY)));
        PropertyDescriptor introspectedAction02EventListener = 
                findPropertyDescriptor(propertyDescriptors, "introspectedListener2Expression");
        this.assertTrue(introspectedAction02EventListener.equals(
                introspectedAction02Event.getValue(Constants.EventSetDescriptor.BINDING_PROPERTY)));
    }
    
    EventSetDescriptor findEventSetDescriptor(EventSetDescriptor[] eventSetDescriptors, String name) {
        for (EventSetDescriptor d : eventSetDescriptors) {
            if (d.getName().equals(name))
                return d;
        }
        return null;
    }
    
    PropertyDescriptor findPropertyDescriptor(PropertyDescriptor[] propertyDescriptors, String name) {
        for (PropertyDescriptor d : propertyDescriptors) {
            if (d.getName().equals(name))
                return d;
        }
        return null;
    }
    
}
