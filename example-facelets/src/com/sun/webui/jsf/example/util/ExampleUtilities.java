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
