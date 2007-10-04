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
    
    public Result beanPastedSetup(DesignBean bean) {
        FacesDesignContext context = (FacesDesignContext) bean.getDesignContext();
        // If selector cut or copied and then pasted, and it looks like the
        // component was previously bound to a default options bean, create a
        // new bean for the pasted component. If the paste operation follows a
        // copy operation, then copy the previous default option's properties
        DesignProperty itemsProperty = bean.getProperty(ITEMS);
        if (itemsProperty != null && itemsProperty.getValueSource() != null && itemsProperty.getValueSource().indexOf("DefaultOptions") >= 0) {
            DesignBean options = context.createBean(getOptionsListClass().getName(), null, null);
            options.setInstanceName(getOptionsListName(bean), true);
            itemsProperty.setValueSource(context.getBindingExpr(options, ".options")); // NOI18N
            
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
