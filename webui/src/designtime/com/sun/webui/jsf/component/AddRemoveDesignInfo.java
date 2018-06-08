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
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.component.util.DesignUtil;
import com.sun.webui.jsf.design.AbstractDesignInfo;
import com.sun.webui.jsf.model.MultipleSelectOptionsList;
import com.sun.webui.jsf.model.Option;

/** DesignInfo class for components that extend the {@link
 * com.sun.webui.jsf.component.AddRemove} component.
 *
 * @author gjmurphy
 */
public class AddRemoveDesignInfo extends SelectorDesignInfo {
    
    /** Name of the Add Button facet */
    private static final String ADD_BUTTON_FACET = "addButton"; //NOI18N
    
    /** Name of the Remove Button facet */
    private static final String REMOVE_BUTTON_FACET = "removeButton"; //NOI18N
    
    public AddRemoveDesignInfo() {
        super(AddRemove.class);
    }
        
    
    /** When a new AddRemove-based component is dropped, create a default
     * list of options and bind if to this component's <code>items</code> and
     * <code>selected</code> properties.
     *
     * @param bean <code>DesignBean</code> for the newly created instance
     */
    public Result beanCreatedSetup(DesignBean bean) {
        super.beanCreatedSetup(bean);
        
        FacesDesignContext context = (FacesDesignContext) bean.getDesignContext();
        
        DesignProperty availableItemsLabel = bean.getProperty("availableItemsLabel"); // NOI18N
        availableItemsLabel.setValue(DesignMessageUtil.getMessage(AddRemoveDesignInfo.class,"AddRemove.available")); // NOI18N
        
        DesignProperty selectedItemsLabel = bean.getProperty("selectedItemsLabel"); // NOI18N
        selectedItemsLabel.setValue(DesignMessageUtil.getMessage(AddRemoveDesignInfo.class,"AddRemove.selected"));  // NOI18N    
        
        
        DesignBean options = context.getBeanByName(getOptionsListName(bean));
        if(options != null) {
            bean.getProperty("selected").setValueSource(context.getBindingExpr(options, ".selectedValue")); //NOI18N
        }
        return Result.SUCCESS;
    }
    /**
     * AddRemove component doesn't need to support "autosubmit" behavior so disabling this
     * default behavior.
     * @param bean <code>DesignBean</code> for the newly created instance 
     */
    public boolean supportsAutoSubmit(DesignBean bean) {
           return false;
    }
    
    protected Class getOptionsListClass() {
        return MultipleSelectOptionsList.class;
    }
    
}
