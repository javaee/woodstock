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

package com.sun.webui.jsf.example.util;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;

public class ExampleUtilities {

    protected ExampleUtilities() {
        super();
    }

    /**
     * Helper method to set value expression property.
     *
     * @param component The UIComponent to set a value expression for.
     * @param name The name of the value expression property.
     * @param expression The expresion for the value expression.
     */
    public static void setValueExpression(UIComponent component, String name, 
            String expression) {
        if (expression == null) {
            return;
        }
        FacesContext context = FacesContext.getCurrentInstance();
        component.setValueExpression(name, createValueExpression(
		context, expression, Object.class));
    }
    
    /**
     * Helper method to set a method expression property.
     * Create a method expression that returns String and has no
     * input paramaters.
     *   
     * @param component The UIComponent to set a value binding for.     
     * @param name The name of the method expression property
     * @param expression The expression to create.
     */
    public static void setMethodExpression(UIComponent component,
	    String name, String expression) {
	setMethodExpression(component, name, expression,
		Object.class, new Class[0]);
    }
    /**
     * Helper method to set a method expression property.
     *   
     * @param component The UIComponent to set a value binding for.     
     * @param name The name of the method expression property
     * @param expression The expression to create.
     */
    public static void setMethodExpression(UIComponent component,
	    String name, String expression, Class out, Class[] in) {
        if (expression == null) {
            return;
        }
        FacesContext context = FacesContext.getCurrentInstance();
        component.getAttributes().put(name, 
	    createMethodExpression(context, expression, out, in));
    }

    public static MethodExpression createMethodExpression(
	    FacesContext context, String expr, Class out, Class[] in) {

	return context.getApplication().getExpressionFactory().
	    createMethodExpression(context.getELContext(), expr, out, in);
    }

    public static ValueExpression createValueExpression(
	    FacesContext context, String expr, Class value) {

	return context.getApplication().getExpressionFactory().
	    createValueExpression(context.getELContext(), expr, value);
    }
}
