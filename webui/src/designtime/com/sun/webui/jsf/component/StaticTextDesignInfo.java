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
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.Result;
import com.sun.webui.jsf.design.AbstractDesignInfo;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;

/**
 * <p>Design time behavior for a <code>StaticText</code> component.  The
 * following behavior is provided:</p>
 * <ul>
 * <li>Default the <code>text</code> property to the instance
 *     name when we are created.</li>
 * <li>The <code>text</code> property can be edited directly in the 
 *     design-time rendering of the component.
 * <li>Only <code>UIParameter</code> components may be dropped onto
 *     this component.</li>
 * </ul>
 *
 * @author gjmurphy
 */
public class StaticTextDesignInfo extends AbstractDesignInfo {
    
    /**
     * <p>Construct a new <code>StaticTextDesignInfo</code> instance.</p>
     */
    public StaticTextDesignInfo() {
        super(StaticText.class);
    }
    
    public Result beanCreatedSetup(DesignBean bean) {
        super.beanCreatedSetup(bean);
        return Result.SUCCESS;        
    }
    
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        return UIParameter.class.isAssignableFrom(childClass) ? true : false;
    }
    
}
