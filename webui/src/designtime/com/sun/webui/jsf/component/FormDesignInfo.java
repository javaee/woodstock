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

/*
 * FormDesignInfo.java
 *
 * Created on February 8, 2005, 2:18 PM
 */

package com.sun.webui.jsf.component;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import javax.faces.component.ActionSource;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectManyCheckbox;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignContext;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.DisplayAction;
import com.sun.rave.designtime.faces.FacesDesignContext;
import com.sun.webui.jsf.design.AbstractDesignInfo;
import com.sun.webui.jsf.renderkit.html.FormRenderer;
import com.sun.webui.jsf.component.vforms.VirtualFormsCustomizerAction;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;

/**
 * DesignInfo for the {@link com.sun.webui.jsf.component.Form} component.
 *
 * @author Matt
 * @author gjmurphy
 */
public class FormDesignInfo extends AbstractDesignInfo {
    
    private static final String ID_SEP = String.valueOf(NamingContainer.SEPARATOR_CHAR);

    /** Creates a new instance of FormDesignInfo */
    public FormDesignInfo() {
        super(Form.class);
    }

    /**
     * Allow form anywhere, so long as parent is not a form and the parent has
     * no form ancestor.
     */
    public boolean acceptParent(DesignBean parentBean, DesignBean childBean, Class childClass) {        
        DesignBean thisBean = parentBean;
        while (thisBean.getBeanParent() != null) {
            if (thisBean.getInstance() instanceof Form)
                return false;
            thisBean = thisBean.getBeanParent();
        }
        //return super.isSunWebUIContext(parentBean);
        return true;
    }
    
    /** 
     * <p>Designtime version of 
     * <code>Form.getFullyQualifiedId(UIComponent)</code> for webui.
     */
    /*
     * Be sure to keep this method in sync with the versions in 
     * <code>com.sun.webui.jsf.component.Form</code> (in webui) and 
     * <code>javax.faces.component.html.HtmlFormDesignInfo</code> 
     * (in jsfcl).</p>
     */
    public static String getFullyQualifiedId(DesignBean bean) {
        if (bean == null) {
            return null;
        }
        Object beanInstance = bean.getInstance();
        if (! (beanInstance instanceof UIComponent)) {
            return null;
        }
        if (beanInstance instanceof Form) {
            return ID_SEP;
        }
        String compId = bean.getInstanceName();
        if (compId == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer(compId);
        DesignBean currentBean = bean.getBeanParent();
        boolean formEncountered = false;
        while (currentBean != null) {
            sb.insert(0, ID_SEP);
            if (currentBean.getInstance() instanceof Form) {
                formEncountered = true;
                break;
            }
            else {
                String currentCompId = currentBean.getInstanceName();
                if (currentCompId == null) {
                    return null;
                }
                sb.insert(0, currentCompId);
            }
            currentBean = currentBean.getBeanParent();
        }
        if (formEncountered) {
            return sb.toString();
        }
        else {
            return null;
        }
    }
}
