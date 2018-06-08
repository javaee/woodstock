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

package com.sun.webui.jsf.component;

import javax.faces.context.FacesContext;

/**
 * <p>Most components whose renderers write more than one HTML 
 * element need to implement this interface, which exists to allow
 * for a distinction between the component ID and the ID of the 
 * primary element that can recieve user input or focus. The 
 * latter is needed to allow the application to maintain 
 * focus, and to set the <code>for</code>attribute on labels.</p>
 */
public interface ComplexComponent {

    /**
     * Implement this method so that it returns the DOM ID of the 
     * HTML element which should receive focus when the component 
     * receives focus, and to which a component label should apply. 
     * Usually, this is the first element that accepts input. 
     * 
     * @param context The FacesContext for the request
     * @return The client id, also the JavaScript element id
     * @deprecated
     * @see #getLabeledElementId
     * @see #getFocusElementId
     */
    public String getPrimaryElementID(FacesContext context);

    /**
     * Returns the absolute ID of an HTML element suitable for use as
     * the value of an HTML LABEL element's <code>for</code> attribute.
     * If the <code>ComplexComponent</code> has sub-compoents, and one of 
     * the sub-components is the target of a label, if that sub-component
     * is a <code>ComplexComponent</code>, then
     * <code>getLabeledElementId</code> must called on the sub-component and
     * the value returned. The value returned by this 
     * method call may or may not resolve to a component instance.
     *
     * @param context The FacesContext used for the request
     * @return An abolute id suitable for the value of an HTML LABEL element's
     * <code>for</code> attribute.
     */
    public String getLabeledElementId(FacesContext context);

    /**
     * Returns the id of an HTML element suitable to
     * receive the focus.
     * If the <code>ComplexComponent</code> has sub-compoents, and one of 
     * the sub-components is to reveive the focus, if that sub-component
     * is a <code>ComplexComponent</code>, then
     * <code>getFocusElementId</code> must called on the sub-component and
     * the value returned. The value returned by this 
     * method call may or may not resolve to a component instance.
     *
     * @param context The FacesContext used for the request
     */
    public String getFocusElementId(FacesContext context);
}
