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

/**
 * Design time for Link component.
 * 
 * @author Edwin Goei
 */
public class LinkDesignInfo extends AbstractDesignInfo {

    public LinkDesignInfo() {
        super(Link.class);
    }

    /**
     * <p>
     * Called when a new <code>Link</code> is dropped.
     * </p>
     * 
     * @param bean
     *            <code>DesignBean</code> for the newly created instance
     */
    public Result beanCreatedSetup(DesignBean bean) {
        Link alert = (Link) bean.getInstance();
        DesignProperty prop = bean.getProperty("url"); //NOI18N
        // TODO what should this value be.
        // To prevent NPE set it to something that works for now
        prop.setValue("/resources/stylesheet.css");
        return Result.SUCCESS;
    }

}
