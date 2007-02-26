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

import com.sun.rave.designtime.Constants;
import com.sun.webui.jsf.component.propertyeditors.ThemeIconsDomain;
import com.sun.webui.jsf.component.util.DesignUtil;
import java.beans.BeanDescriptor;

/**
 * BeanInfo for the {@link com.sun.webui.jsf.component.ImageComponent} component.
 */
public class ImageComponentBeanInfo extends ImageComponentBeanInfoBase {
    
    public ImageComponentBeanInfo() {
        super();
        DesignUtil.applyPropertyDomain(this, "align", com.sun.rave.propertyeditors.domains.HtmlAlignDomain.class);
        DesignUtil.applyPropertyDomain(this, "rel", com.sun.rave.propertyeditors.domains.HtmlLinkTypesDomain.class);
        DesignUtil.applyPropertyDomain(this, "icon", ThemeIconsDomain.class);
    }
    
    public BeanDescriptor getBeanDescriptor() {
        BeanDescriptor beanDescriptor = super.getBeanDescriptor();
        // This is set here rather than using <resize-constraints> metadata
        // in conf-image-renderer.xml because we need to do a bitwise
        // or of two constants, which the DTD (or code generator) doesn't
        // allow
        beanDescriptor.setValue(Constants.BeanDescriptor.RESIZE_CONSTRAINTS,
                new Integer(Constants.ResizeConstraints.MAINTAIN_ASPECT_RATIO|Constants.ResizeConstraints.ANY));
        
        return beanDescriptor;
    }
    
}
