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

import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignContext;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.Result;
import com.sun.rave.designtime.faces.FacesDesignContext;
import com.sun.webui.jsf.design.AbstractDesignInfo;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;

/**
 * DesignInfo for the {@link com.sun.webui.jsf.component.Breadcrumbs} component.
 * The following behaviors are implemented:
 * <ul>
 * <li>Upon creation, populate breadcrumbs with two hyperlink components, one
 * for the web application, and one for the current page
 * ({@link com.sun.webui.jsf.component.Hyperlink} is used, since that is
 * the only hyperlink used by Creator).</li>
 * </ul>
 *
 * @author gjmurphy
 */
public class BreadcrumbsDesignInfo extends AbstractDesignInfo {
    
    public BreadcrumbsDesignInfo() {
        super(Breadcrumbs.class);
    }
 
    /**
     * Create the content for the Breadcrumbs component.
     * Loop through the design contexts in the project for the
     * Page contexts.  For each page, add a hyperlink component
     * to the breadcrumb.  Make sure the last hyperlink is for
     * the current page.
     *
     * @param bean The design bean for the breadcrumbs component
     * @return The results
     */
    public Result beanCreatedSetup(DesignBean bean) {

	// Save the display name for this page's design context.
        DesignContext context = bean.getDesignContext();
	String currentPageDisplayName = ((FacesDesignContext) context).getDisplayName();
        if (context.canCreateBean(Hyperlink.class.getName(), bean, null)) {
            // Add an initial hyperlink for every page in the project.
	    // Add the current page hyperlink last.
            try {
                DesignContext[] contexts = bean.getDesignContext().getProject().getDesignContexts();
                URI rootURI = context.getProject().getResourceFile(new URI("./web")).toURI(); //NOI18N
		DesignBean rootBean = null;
                for (int i = 0; i < contexts.length; i++) {
                    rootBean = contexts[i].getRootContainer();
                    Object instance = rootBean.getInstance();
                    // Test to determine whether this rootBean corresponds to a page
                    if (instance != null &&
			UIViewRoot.class.isAssignableFrom(instance.getClass()) &&
                        rootBean.getChildBeanCount() > 0 &&
			rootBean.getChildBean(0).getInstance() instanceof Page) {

			// Check if this is our current page.  If so, continue.
			// We will add that link after the loop.
			String displayName =
			    ((FacesDesignContext) contexts[i]).getDisplayName();
			if (! displayName.equals(currentPageDisplayName)) {
			    createLink(bean, rootBean, rootURI, contexts[i]);
			}
		    }
                }					// End of for
		// Now add the hyperlink for the current page context
		// as the last link in the breadcrumb.
		rootBean = context.getRootContainer();
		createLink(bean, rootBean, rootURI, context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Result.SUCCESS;
    }

    /**
     * Check if child component can be a child of the Breadcrumbs component.
     * Any instance of Hyperlink or its subclasses is acceptible.
     *
     * @param parentBean Bean info for parent component
     * @param childBean  Bean info for child component
     * @param childClass Class instance for child component
     *
     * @return true if the child component can be a child of this component
     */
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean,
	Class childClass) {

        if ((Hyperlink.class).isAssignableFrom(childClass)) {
            return true;
	}
        return false;

    } // acceptChild
    
    protected DesignProperty getDefaultBindingProperty(DesignBean targetBean) {
        return targetBean.getProperty("pages"); //NOI18N
    }
 
    // Method to create new Hyperlink in the Breadcrumb.
    private void createLink(DesignBean parentBean, DesignBean rootBean,
	URI rootURI, DesignContext context) throws Exception {

	DesignContext parentContext = parentBean.getDesignContext();
        DesignBean hyperlinkBean =
            parentContext.createBean(Hyperlink.class.getName(), parentBean, null);                        
        URI pageURI = new URI(parentContext.resolveResource(rootBean.getInstanceName()
            + ".jsp").toString()); //NOI18N
        URI relativeURI = rootURI.relativize(pageURI);
        String contextRelativePath = "/faces/" + relativeURI.toString();
        hyperlinkBean.getProperty("url").setValue(contextRelativePath); //NOI18N
        hyperlinkBean.getProperty("text").setValue(
	    ((FacesDesignContext) context).getDisplayName()); //NOI18N

    } // createLink

}
