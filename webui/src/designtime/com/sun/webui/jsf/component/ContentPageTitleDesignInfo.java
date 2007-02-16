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
import com.sun.rave.designtime.faces.FacesDesignContext;

/**
 * DesignInfo for the ContentPageTitle component.
 * 
 */
public class ContentPageTitleDesignInfo extends AbstractDesignInfo {
    
    /** Creates a new instance of ContentPageTitleDesignInfo */
    public ContentPageTitleDesignInfo() {
        super(ContentPageTitleDesignInfo.class);
    }
    
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        
        return true;
    }
    
    public boolean acceptParent(DesignBean parentBean, DesignBean childBean, Class childClass) {
        
        return true;
    }
    
    /* Adds "-rave-layout: grid" to div style attribute. */
    public void customizeRender(final MarkupDesignBean bean, MarkupRenderContext renderContext) {
        DocumentFragment documentFragment = renderContext.getDocumentFragment();
        MarkupPosition begin = renderContext.getBeginPosition();
        MarkupPosition end = renderContext.getEndPosition();
        
        if (begin == end) {
            return;
        }
                       
        Node n = begin.getBeforeSibling();
        Element div = null;
        
        while (n != null && div == null) {
            if (n.getNodeType() == Node.ELEMENT_NODE && n.getLocalName().equals("div"))
                div = (Element) n;
            n = n.getNextSibling();
        }
        String style = div.getAttribute("style"); //NOI18N
                style = style == null ? "" : style;
                div.setAttribute("style", style + " ; -rave-layout: grid");
                
    }
    
}

