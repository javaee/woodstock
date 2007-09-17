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
import com.sun.rave.designtime.faces.ResolveResult;
import com.sun.webui.jsf.model.DefaultOptionsList;
import com.sun.webui.jsf.model.OptionsList;
import com.sun.rave.designtime.Result;
import com.sun.rave.designtime.faces.FacesDesignContext;
import com.sun.rave.designtime.faces.FacesDesignProperty;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.component.util.DesignUtil;
import com.sun.webui.jsf.design.AbstractDesignInfo;
import java.beans.PropertyDescriptor;
import javax.faces.el.ValueBinding;

/**
 * DesignInfo for the {@link com.sun.webui.jsf.component.Menu} component.
 *
 */
public class MenuDesignInfo extends AbstractDesignInfo{

    static final String ITEMS = "items"; //NOI18N
    static final String ID = "id"; //NOI18N    
    public MenuDesignInfo() {
        super (MenuDesignInfo.class);
    }
    
    /** When a new Menu component is dropped, create a default
     * list of options and bind if to this component's <code>items</code>
     *  property.
     *
     * @param bean <code>DesignBean</code> for the newly created instance
     */
    public Result beanCreatedSetup(DesignBean bean) {
        FacesDesignContext context = (FacesDesignContext)bean.getDesignContext();
        Class optionsListClass = getOptionsListClass();
        if(context.canCreateBean(optionsListClass.getName(), null, null)) {
            DesignBean options =
                    context.createBean(optionsListClass.getName(), null, null);
            options.setInstanceName(getOptionsListName(bean), true);
            bean.getProperty(ITEMS).setValueSource(context.getBindingExpr(options, ".options")); //NOI18N
        }        
        return Result.SUCCESS;
    }    
    
    /**
     * When a Menu component is deleted, check for the existence of a
     * default list of options, and delete it if present.
     */
    public Result beanDeletedCleanup(DesignBean bean) {
        super.beanDeletedCleanup(bean);
        // If bound to a default options bean, and no other menu components
        // are bound to it, delete it
        DesignBean options = getOptionsListBean(bean);
        if (options != null) {
            FacesDesignProperty itemsProperty = (FacesDesignProperty) bean.getProperty(ITEMS);
            String oldExpression = itemsProperty.getValueBinding().getExpressionString();
            itemsProperty.unset();
            DesignBean[] beans = bean.getDesignContext().getBeansOfType(Selector.class);
            int referenceCount = 0;
            for (int i = 0; i < beans.length; i++) {
                DesignProperty p = beans[i].getProperty(ITEMS);
                if (p != null && p instanceof FacesDesignProperty) {
                    ValueBinding valueBinding = ((FacesDesignProperty) p).getValueBinding();
                    if (valueBinding != null) {
                        String expression = valueBinding.getExpressionString();
                        if (oldExpression.equals(expression))
                            referenceCount++;
                    }
                }
            }
            if (referenceCount == 0)
                bean.getDesignContext().deleteBean(options);
        }
        deleteConverter(bean);
        return Result.SUCCESS;
    }    


    public boolean acceptChild(DesignBean parentBean, DesignBean childBean,
            Class childClass) {
        return false;
    }    
    
    /**
     * Returns a class to instantiate for the default options bean. Must be
     * <code>OptionsList</code> or a subclass thereof.
     */
    protected Class getOptionsListClass() {
        return DefaultOptionsList.class;
    }    
     
    /**
     * Returns the name of the default options bean.
     */
    protected static String getOptionsListName(DesignBean menuBean) {
        return menuBean.getInstanceName() + "DefaultOptions"; //NOI18N
    }    
    
    /**
     * If the menu component for the bean specified is bound to an options
     * list, returns the design bean for the options list. Otherwise returns
     * null.
     */
    protected static DesignBean getOptionsListBean(DesignBean menuBean) {
        FacesDesignContext context = (FacesDesignContext) menuBean.getDesignContext();
        FacesDesignProperty itemsProperty = (FacesDesignProperty) menuBean.getProperty(ITEMS);
        if (itemsProperty == null || !itemsProperty.isBound())
            return null;
        String expression = itemsProperty.getValueBinding().getExpressionString();
        return getOptionsListBean(context, expression);
    }

    protected static DesignBean getOptionsListBean(FacesDesignContext context, String expression) {
        ResolveResult resolveResult = context.resolveBindingExprToBean(expression);
        if (resolveResult == null || resolveResult.getDesignBean() == null)
            return null;
        DesignBean itemsBean = resolveResult.getDesignBean();
        if (OptionsList.class.isAssignableFrom(itemsBean.getInstance().getClass()))
            return itemsBean;
        return null;
    }    
    
    /**
     * When the <code>items</code> property is changed, if previous value was a
     * binding to the default <code>OptionsList</code> for this component, then
     * delete the options list if present.
     */
    public void propertyChanged(DesignProperty property, Object oldValue) {
        super.propertyChanged(property, oldValue);
        PropertyDescriptor descriptor = property.getPropertyDescriptor();
        DesignBean menuBean = property.getDesignBean();
        FacesDesignContext context = (FacesDesignContext) menuBean.getDesignContext();
        
        // If instance name is changed: if the component is bound to a default
        // options list, update the name of the options list to reflect the new
        // instance name.
        if (descriptor.getName().equals(ID)) {
            DesignBean optionsBean = getOptionsListBean(menuBean);
            if (optionsBean != null) {
                optionsBean.setInstanceName(getOptionsListName(menuBean));
                menuBean.getProperty(ITEMS).setValueSource(context.getBindingExpr(optionsBean, ".options")); //NOI18N
            }
        } else if (descriptor.getName().equals(ITEMS)) {
            
            // If previous value was a value binding to a default options list, and new
            // value is not a value binding to the same options list, then deleat the
            // default options list
            if (oldValue != null && ValueBinding.class.isAssignableFrom(oldValue.getClass())) {
                ValueBinding valueBinding = ((FacesDesignProperty) property).getValueBinding();
                String oldExpression = ((ValueBinding) oldValue).getExpressionString();
                if (((FacesDesignProperty) property).isBound() && !oldExpression.equals(valueBinding.getExpressionString())) {
                    DesignBean optionsBean = getOptionsListBean(context, oldExpression);
                    if (optionsBean != null) {
                        // Make sure no other components are using the default options
                        DesignBean[] beans = context.getBeansOfType(Selector.class);
                        int referenceCount = 0;
                        for (int i = 0; i < beans.length; i++) {
                            DesignProperty p = beans[i].getProperty(ITEMS);
                            if (p != null && p instanceof FacesDesignProperty) {
                                String expression = ((FacesDesignProperty) p).getValueBinding().getExpressionString();
                                if (oldExpression.equals(expression))
                                    referenceCount++;
                            }
                        }
                        if (referenceCount == 0)
                            context.deleteBean(optionsBean);
                    }
                }
            }
        }
    }
    
    public Result beanPastedSetup(DesignBean bean) {
        FacesDesignContext context = (FacesDesignContext)bean.getDesignContext();
        // If menu cut or copied and then pasted, and it looks like the
        // component was previously bound to a default options bean, create a
        // new bean for the pasted component. If the paste operation follows a
        // copy operation, then copy the previous default option's properties
        DesignProperty itemsProperty = bean.getProperty(ITEMS);
        if (itemsProperty != null && itemsProperty.getValueSource() != null &&
                itemsProperty.getValueSource().indexOf("DefaultOptions") >= 0) {
            DesignBean options = context.createBean(getOptionsListClass().getName(), null, null);
            options.setInstanceName(getOptionsListName(bean), true);
            itemsProperty.setValueSource(context.getBindingExpr(options, ".options")); // NOI18N
        }
        return Result.SUCCESS;
    }    
}

