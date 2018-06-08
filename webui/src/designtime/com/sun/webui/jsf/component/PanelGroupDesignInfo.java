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

import com.sun.rave.designtime.*;
import com.sun.rave.designtime.markup.*;
import com.sun.webui.jsf.design.AbstractDesignInfo;
import javax.faces.component.html.HtmlPanelGrid;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * DesignInfo for the {@link com.sun.webui.jsf.component.PanelGroup} component.
 *
 * @author gjmurphy
 */
public class PanelGroupDesignInfo extends AbstractDesignInfo implements MarkupDesignInfo {
    
    public PanelGroupDesignInfo() {
        super(PanelGroup.class);
    }

    public Result beanCreatedSetup(DesignBean bean) {
        return Result.SUCCESS;
    }
    
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        if(childClass.equals(PanelGroup.class))
            return true;
        if (childClass.getName().equals("com.sun.rave.xhtml.Jsp_Directive_Include"))
            return false;
        return super.acceptChild(parentBean, childBean, childClass);
    }

    public boolean acceptParent(DesignBean parentBean, DesignBean childBean, Class childClass) {
        if(Tab.class.isAssignableFrom(parentBean.getInstance().getClass()))
            return true;
        if(PanelGroup.class.isAssignableFrom(parentBean.getInstance().getClass()))
            return true;
        return super.acceptParent(parentBean, childBean, childClass);
    }
    
    public void customizeRender(MarkupDesignBean markupDesignBean, MarkupRenderContext renderContext) {
        DocumentFragment documentFragment = renderContext.getDocumentFragment();
        MarkupPosition begin = renderContext.getBeginPosition();
        MarkupPosition end = renderContext.getEndPosition();
        
        if (begin == end || markupDesignBean.getChildBeanCount() > 0) {
            return;
        }
        
        assert begin.getUnderParent() == end.getUnderParent();
        Node child = begin.getBeforeSibling();
        Node stop = end.getBeforeSibling();
        
        // Draw a rave design border around the panel
        for (child = begin.getBeforeSibling(); child != stop; child = child.getNextSibling()) {
            if (child instanceof Element) {
                Element e = (Element)child;
                String styleClass = e.getAttribute("class"); //NOI18N
                styleClass = styleClass == null ? "" : styleClass;
                e.setAttribute("class", styleClass + " rave-design-border"); // NOI18N
                break;
            }
        }
    }
    
}
