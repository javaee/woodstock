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
    
    public Result beanCreatedSetup(DesignBean bean) {
        DesignContext context = bean.getDesignContext();
        if (context.canCreateBean(Hyperlink.class.getName(), bean, null)) {
            // Add an initial hyperlink for every page in the project
            try {
                DesignContext[] contexts = bean.getDesignContext().getProject().getDesignContexts();
                URI rootURI = context.getProject().getResourceFile(new URI("./web")).toURI(); //NOI18N
                for (int i = 0; i < contexts.length; i++) {
                    DesignBean rootBean = contexts[i].getRootContainer();
                    Object instance = rootBean.getInstance();
                    // Test to determine whether this rootBean corresponds to a page
                    if (instance != null && UIViewRoot.class.isAssignableFrom(instance.getClass()) &&
                            rootBean.getChildBeanCount() > 0 && rootBean.getChildBean(0).getInstance() instanceof Page) {
                        DesignBean hyperlinkBean =
                                context.createBean(Hyperlink.class.getName(), bean, null);                        
                        URI pageURI = new URI(contexts[i].resolveResource(rootBean.getInstanceName() + ".jsp").toString()); //NOI18N
                        URI relativeURI = rootURI.relativize(pageURI);
                        String contextRelativePath = "/faces/" + relativeURI.toString();
                        hyperlinkBean.getProperty("url").setValue(contextRelativePath); //NOI18N
                        hyperlinkBean.getProperty("text").setValue(((FacesDesignContext) contexts[i]).getDisplayName()); //NOI18N
                    }
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return Result.SUCCESS;
    }
    
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        Class parentClass = parentBean.getInstance().getClass();
        if(Hyperlink.class.equals(childClass) || ImageHyperlink.class.equals(childClass))
            return true;
        return super.acceptChild(parentBean, childBean, childClass);
    }
    
    protected DesignProperty getDefaultBindingProperty(DesignBean targetBean) {
        return targetBean.getProperty("pages"); //NOI18N
    }
    
}
