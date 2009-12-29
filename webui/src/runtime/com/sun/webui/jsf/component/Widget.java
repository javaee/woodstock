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
package com.sun.webui.jsf.component;

/**
 * Widget is an interface that must be implemented by any UIComponent that wants
 * to be a client-side widget. A Widget does not necessarily need to implement
 * all methods, but the interface still affects the behavior of the
 * {@link RendererBase#encodeBegin} and {@link RendererBase#encodEnd} methods; 
 * see those methods for more information.
 */
public interface Widget {

    /**
     * Get alternative HTML template to be used by this component.
     *
     * @return The alternative HTML template to be used by this component.
     */
    public String getHtmlTemplate();

    /**
     * Get the type of widget represented by this component.
     *
     * @return The type of widget represented by this component.
     */
    public String getWidgetType();
}
