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

import com.sun.rave.designtime.*;
import com.sun.rave.designtime.markup.*;
import com.sun.webui.jsf.design.AbstractDesignInfo;
import javax.faces.component.html.HtmlPanelGrid;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * DesignInfo for the {@link com.sun.webui.jsf.component.PanelLayout} component.
 *
 * @author gjmurphy
 */
public class PanelLayoutDesignInfo extends AbstractDesignInfo implements MarkupDesignInfo {
    
    public PanelLayoutDesignInfo() {
        super(PanelLayoutDesignInfo.class);
    }
    
    public Result beanCreatedSetup(DesignBean bean) {
        Object parent = bean.getBeanParent().getInstance();
        bean.getProperty("panelLayout").setValue(PanelLayout.FLOW_LAYOUT);
        if (parent instanceof HtmlPanelGrid) {
            appendStyle(bean, "width: 100%; height: 100%;");
        } else if (parent instanceof PanelLayout && ((PanelLayout)parent).getPanelLayout().equals(PanelLayout.FLOW_LAYOUT)) {
            appendStyle(bean, "width: 100%; height: 100%;");
        } else if (parent instanceof Tab) {
            appendStyle(bean, "width: 100%; height: 128px;");
        } else {
            appendStyle(bean, "width: 128px; height: 128px;");
        }
        return Result.SUCCESS;
    }
    
    private static void appendStyle(DesignBean bean, String style) {
        DesignProperty styleProperty = bean.getProperty("style");
        String styleValue = (String) styleProperty.getValue();
        if (styleValue == null || styleValue.length() == 0) {
            styleValue = style;
        } else {
            styleValue = styleValue.trim();
            if (styleValue.charAt(styleValue.length() - 1) == ';')
                styleValue = styleValue + " " + style;
            else
                styleValue = styleValue + "; " + style;
        }
        styleProperty.setValue(styleValue);
    }
    
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        if(childClass.equals(PanelLayout.class))
            return true;
        if (childClass.getName().equals("com.sun.rave.xhtml.Jsp_Directive_Include"))
            return false;
        return super.acceptChild(parentBean, childBean, childClass);
    }
    
    public boolean acceptParent(DesignBean parentBean, DesignBean childBean, Class childClass) {
        if(Tab.class.isAssignableFrom(parentBean.getInstance().getClass()))
            return true;
        if(PanelLayout.class.isAssignableFrom(parentBean.getInstance().getClass()))
            return true;
        return super.acceptParent(parentBean, childBean, childClass);
    }
    
    public void customizeRender(MarkupDesignBean markupDesignBean, MarkupRenderContext renderContext) {
//        DocumentFragment documentFragment = renderContext.getDocumentFragment();
//        MarkupPosition begin = renderContext.getBeginPosition();
//        MarkupPosition end = renderContext.getEndPosition();
//        
//        if (begin == end || markupDesignBean.getChildBeanCount() > 0) {
//            return;
//        }
//        
//        assert begin.getUnderParent() == end.getUnderParent();
//        Node child = begin.getBeforeSibling();
//        Node stop = end.getBeforeSibling();
//        
//        // Draw a rave design border around the panel
//        for (child = begin.getBeforeSibling(); child != stop; child = child.getNextSibling()) {
//            if (child instanceof Element) {
//                Element e = (Element)child;
//                String styleClass = e.getAttribute("class"); //NOI18N
//                styleClass = styleClass == null ? "" : styleClass;
//                e.setAttribute("class", styleClass + " rave-design-border"); // NOI18N
//                break;
//            }
//        }
    }
    
}
