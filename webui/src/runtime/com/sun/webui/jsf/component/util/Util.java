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

import java.beans.Beans;
import java.util.Iterator;
import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility class that contains helper methods for components.
 *
 * @author  Ken Paulsen
 */
public class Util {

    /**
     *	<p> This constructor is here to prevent this class from being
     *	    instantiated.  It only contains static methods.</p>
     */
    private Util() {
    }

    /**
     *	<p> Return a child with the specified component id from the specified
     *	    component. If not found, return <code>null</code>.</p>
     *
     *	<p> This method will NOT create a new <code>UIComponent</code>.</p>
     *
     *	@param	parent	<code>UIComponent</code> to be searched
     *	@param	id	Component id (or facet name) to search for
     *
     *	@return	The child <code>UIComponent</code> if it exists, null otherwise.
     */
    public static UIComponent getChild(UIComponent parent, String id) {
        return findChild(parent, id, id);
    }

    /**
     *	<p> Return a child with the specified component id (or facetName) from
     *	    the specified component. If not found, return <code>null</code>.
     *	    <code>facetName</code> or <code>id</code> may be null to avoid
     *	    searching the facet Map or the <code>parent</code>'s children.</p>
     *
     *	<p> This method will NOT create a new <code>UIComponent</code>.</p>
     *
     *	@param	parent	    <code>UIComponent</code> to be searched
     *	@param	id	    id to search for
     *	@param	facetName   Facet name to search for
     *
     *	@return	The child <code>UIComponent</code> if it exists, null otherwise.
     */
    public static UIComponent findChild(UIComponent parent, String id, String facetName) {
        // Sanity Check
        if (parent == null) {
            return null;
        }

        // First search for facet
        UIComponent child = null;
        if (facetName != null) {
            child = (UIComponent) parent.getFacets().get(facetName);
            if (child != null) {
                return child;
            }
        }

        // Search for component by id
        if (id != null) {
            Iterator it = parent.getChildren().iterator();
            while (it.hasNext()) {
                child = (UIComponent) it.next();
                if (id.equals(child.getId())) {
                    return (child);
                }
            }
        }

        // Not found, return null
        return null;
    }

    /**
     *	<p>Helper method to obtain containing <code>UIForm</code>.</p>
     *
     *	@param	context	    <code>FacesContext</code> for the request we are
     *			    processing.
     *	@param	component   <code>UIComponent</code> to find form from.
     *
     *	@return	Returns the <code>UIForm</code> component that contains this element
     */
    public static UIComponent getForm(FacesContext context, UIComponent component) {
        //make sure component is not null
        if (component != null) {
            //make sure we don't already have a form
            if (component instanceof UIForm) {
                return component;
            }

            UIComponent form = component;
            do {
                form = form.getParent();
                if (form != null && form instanceof UIForm) {
                    return form;
                }
            } while (form != null);
        }
        return null;
    }

    /**
     *	<p>Gets the form id from the containing <code>UIForm</code>.</p>
     *
     *	@param	context	    FacesContext for the request we are processing.
     *	@param	component   UIComponent to find form from.
     *
     *	@return Returns the id of the <code>UIForm</code> that contains this
     *		element.
     */
    public static String getFormName(FacesContext context, UIComponent component) {
        UIComponent form = getForm(context, component);
        if (form != null) {
            return form.getClientId(context);
        }
        return null;
    }

    /**
     * <p>Return the base URI for the view identifier of the current view.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     */
    public static String getBase(FacesContext context) {

        return getContext(context) + context.getViewRoot().getViewId();

    }

    /**
     * <p>Return an absolute URL to our server and context path.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     */
    public static String getContext(FacesContext context) {

        // FIXME - design time FacesContext needs better instrumentaiton?
        if (Beans.isDesignTime()) {
            return "http://localhost:18080/myapp";
        }

        //FIXME - portlet environment variation?
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        StringBuffer sb = new StringBuffer(request.getScheme());
        sb.append("://"); //NOI18N
        sb.append(request.getServerName());
        if ("http".equals(request.getScheme()) && (80 == request.getServerPort())) {//NOI18N
            ;
        } else if ("https".equals(request.getScheme()) && (443 == request.getServerPort())) {//NOI18N
            ;
        } else {
            sb.append(":" + request.getServerPort()); //NOI18N
        }
        sb.append(request.getContextPath());
        return sb.toString();

    }

    public static String getActionURL(FacesContext context, String url) {
        return context.getApplication().getViewHandler().getActionURL(context, url);
    }

    /**
     * Add a PhaseListener.
     *
     * @param phaseListener PhaseListener instance.
     */
    public static void addPhaseListener(PhaseListener phaseListener) {

        LifecycleFactory factory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        Lifecycle lifecycle =
                factory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
        lifecycle.addPhaseListener(phaseListener);
    }

    /**
     * Remove a PhaseListener.
     *
     * @param phaseListener PhaseListener instance.
     */
    public static void removePhaseListener(PhaseListener phaseListener) {

        LifecycleFactory factory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        Lifecycle lifecycle =
                factory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
        lifecycle.removePhaseListener(phaseListener);
    }
}
