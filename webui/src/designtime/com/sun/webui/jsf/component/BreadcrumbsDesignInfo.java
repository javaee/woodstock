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
