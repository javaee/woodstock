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

import java.util.regex.Pattern;
import javax.faces.component.UIPanel;
import javax.faces.component.UIComponent;

import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignContext;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.Result;
import com.sun.rave.designtime.markup.BasicMarkupMouseRegion;
import com.sun.rave.designtime.markup.MarkupDesignBean;
import com.sun.rave.designtime.markup.MarkupMouseRegion;
import com.sun.rave.designtime.markup.MarkupPosition;
import com.sun.rave.designtime.markup.MarkupRenderContext;
import com.sun.webui.jsf.component.util.DesignUtil;
import com.sun.webui.jsf.design.AbstractDesignInfo;


/**
 * DesignInfo for the <code>AccordionTab</code> component. The following behavior is
 * implemented:
 * <ul>
 * <li>Upon component creation, pre-set <code>text</code> property to the
 * component's display name, "AccordionTab".</li>
 * <li>Clicking on the tab names will switch the containing Accordion's selected
 * property to the clicked tab, unless the selected property contains a value
 * binding expression, in which case nothing happens.</li>
 * </ul>
 *
 * @author deep
 */
public class AccordionTabDesignInfo extends AbstractDesignInfo {
    
    public AccordionTabDesignInfo() {
        super(AccordionTab.class);
    }
    
    /*
     * Add a panel group to each accordionTab so that other elements can be
     * dropped onto it.
     */
    public Result beanCreatedSetup(DesignBean bean) {
        super.beanCreatedSetup(bean);
        DesignContext context = bean.getDesignContext();
        DesignProperty titleProperty = bean.getProperty("title"); //NOI18N
        String suffix = DesignUtil.getNumericalSuffix(bean.getInstanceName());
        titleProperty.setValue(
                bean.getBeanInfo().getBeanDescriptor().getDisplayName() + " " + suffix);
        selectTab(bean);
        
        if (context.canCreateBean(PanelLayout.class.getName(), bean, null)) {
            DesignBean panelBean = context.createBean(PanelLayout.class.getName(), bean, null);
            panelBean.getDesignInfo().beanCreatedSetup(panelBean);
            panelBean.getProperty("panelLayout").setValue(getParentLayout(bean));
        }
        DesignBean parentBean = bean.getBeanParent();
        if (parentBean.getInstance() instanceof AccordionTab) {
            DesignBean childBean = parentBean.getChildBean(0);
            if (childBean.getInstance() instanceof PanelLayout)
                context.deleteBean(childBean);
        }
                
        return Result.SUCCESS;
    }
    
    /*
     * Accept all components. Select the current accordionTab when a component
     * is being dropped onto it.
     */
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        if (parentBean.getInstance() instanceof AccordionTab) {
            parentBean.getProperty("selected").setValue(Boolean.TRUE);
        }
        return true;
    }

    /*
     * An accordionTab can only go inside an accordion.
     */
    public boolean acceptParent(DesignBean parentBean, DesignBean childBean, Class childClass) {
        Object parent = parentBean.getInstance();
        if (parent instanceof Accordion) {
            return true;
        }
        return false;
        
    }
    
    public Result beanPastedSetup(DesignBean bean) {
        DesignContext context = bean.getDesignContext();
        DesignBean parentBean = bean.getBeanParent();
        if (parentBean.getInstance() instanceof AccordionTab) {
            DesignBean childBean = parentBean.getChildBean(0);
            if (childBean.getInstance() instanceof PanelLayout)
                context.deleteBean(childBean);
        }
        return Result.SUCCESS;
    }
    
    public Result beanDeletedCleanup(DesignBean bean) {
        unselectTab(bean);
        return Result.SUCCESS;
    }

    /*
     * If instance name is changed, check to see whether this tab is the Accordion's
     * currently selected tab. If so, updated the Accordion's selected property.
     */
    public void instanceNameChanged(DesignBean bean, String oldInstanceName) {
        
        DesignBean accordionBean = bean.getBeanParent();
        while(accordionBean != null && !(accordionBean.getInstance() instanceof Accordion)) {
            accordionBean.getProperty("selected").setValue(Boolean.FALSE);
        }
        if( accordionBean != null) {
            DesignProperty selectedProperty = accordionBean.getProperty("selected"); //NOI18N
            if (oldInstanceName != null && oldInstanceName.equals(selectedProperty.getValue()))
                selectedProperty.setValue(bean.getInstanceName());
        }
    }
    
    public void customizeRender(final MarkupDesignBean bean, MarkupRenderContext renderContext) {
        DocumentFragment documentFragment = renderContext.getDocumentFragment();
        MarkupPosition begin = renderContext.getBeginPosition();
        MarkupPosition end = renderContext.getEndPosition();
        
        if (begin == end) {
            return;
        }
        
        assert begin.getUnderParent() == end.getUnderParent();
        
        Node n = begin.getBeforeSibling();
        Element tabElement = null;
        
        while (n != null && tabElement == null) {
            if (n.getNodeType() == Node.ELEMENT_NODE && n.getLocalName().equals("td"))
                tabElement = (Element) n;
            n = n.getParentNode();
        }
        
        if (tabElement != null) {
            
            String id = (String) bean.getProperty("id").getValue();
            if (id != null) {
                // If accordion tab is not selected, and if it has no tab children, register
                // mouse region which allows user to select accordion tab
                registerTab(bean, renderContext, tabElement);
            }
        }
        
    }
    
    private void registerTab(final DesignBean bean, MarkupRenderContext context, Element e) {
        String styleClass = e.getAttribute("class"); // NOI18N
        
        if (styleClass.indexOf("disabled") == -1) { // NOI18N
            MarkupMouseRegion region =
                    new BasicMarkupMouseRegion() {
                public boolean isClickable() {
                    return true;
                }
                
                public Result regionClicked(int clickCount) {
                    selectTab(bean);
                    return Result.SUCCESS;
                }
            };
            
            context.associateMouseRegion(e, region);
            
            return;
        }
    }
    
    /**
     * "Selects" the accordiontab by setting the selected property of the 
     * accordionTab to true.
     */
    private static void selectTab(DesignBean bean) {
        if (bean.getInstance() instanceof AccordionTab) {
            bean.getProperty("selected").setValue(Boolean.TRUE);
        }
    }
    
    /**
     * "De-selects" the accordiontab by setting the selected property of the 
     * accordionTab to false.
     */
    private static void unselectTab(DesignBean bean) {
        // Find a suitable sibling tab to select
        
        if (bean.getInstance() instanceof AccordionTab) {
            bean.getProperty("selected").setValue(Boolean.FALSE);
        }
    }
    
    private static Pattern gridPattern = Pattern.compile(".*-rave-layout\\s*:\\s*grid.*");
    
    /**
     * Walk up the component tree, starting with the accordiontab bean specified, until
     * the first enclosing body or div element with a "style" property is
     * encountered. If there is a rave grid positioning style set, return a
     * keyword indicating grid layout. Otherwise, returns a keyword indicating
     * flow layout.
     */
    private static String getParentLayout(DesignBean bean) {
        while (bean != null) {
            if (bean.getInstance() instanceof Body ||
                    (bean instanceof MarkupDesignBean && ((MarkupDesignBean) bean).getElement().getTagName().equals("div"))) {
                String style = (String) bean.getProperty("style").getValue(); //NOI18N
                if (style != null && gridPattern.matcher(style).matches())
                    return PanelLayout.GRID_LAYOUT;
            }
            bean = bean.getBeanParent();
        }
        return PanelLayout.FLOW_LAYOUT;
    }
    
}