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

import com.sun.webui.jsf.component.util.DesignUtil;
import java.beans.EventSetDescriptor;
import com.sun.rave.designtime.Constants;
import com.sun.webui.jsf.component.propertyeditors.ThemeIconsDomain;

/**
 * BeanInfo for the {@link com.sun.webui.jsf.component.ImageHyperlink} component.
 */
public class ImageHyperlinkBeanInfo extends ImageHyperlinkBeanInfoBase {

    public ImageHyperlinkBeanInfo() {
        DesignUtil.applyPropertyDomain(this, "target", com.sun.rave.propertyeditors.domains.FrameTargetsDomain.class);
        DesignUtil.applyPropertyDomain(this, "type", com.sun.rave.propertyeditors.domains.MimeTypesDomain.class);
        DesignUtil.applyPropertyDomain(this, "charset", com.sun.rave.propertyeditors.domains.CharacterSetsDomain.class);
        DesignUtil.applyPropertyDomain(this, "rel", com.sun.rave.propertyeditors.domains.HtmlLinkTypesDomain.class);
        DesignUtil.applyPropertyDomain(this, "urlLang", com.sun.rave.propertyeditors.domains.LanguagesDomain.class);
        DesignUtil.applyPropertyDomain(this, "align", com.sun.rave.propertyeditors.domains.HtmlAlignDomain.class);
        DesignUtil.applyPropertyDomain(this, "icon", ThemeIconsDomain.class);
        DesignUtil.applyPropertyDomain(this, "textPosition", com.sun.rave.propertyeditors.domains.HtmlHorizontalAlignDomain.class);
        DesignUtil.hideProperties(this, new String[]{"action", "value"});
        this.getBeanDescriptor().setValue(
            Constants.BeanDescriptor.INLINE_EDITABLE_PROPERTIES,
            new String[] { "*text://a" }); // NOI18N

        // This is set here rather than using <resize-constraints> metadata
        // in conf-image-renderer.xml because we need to do a bitwise
        // or of two constants, which the DTD (or code generator) doesn't
        // allow
        this.getBeanDescriptor().setValue(Constants.BeanDescriptor.RESIZE_CONSTRAINTS,
            new Integer(Constants.ResizeConstraints.MAINTAIN_ASPECT_RATIO|Constants.ResizeConstraints.ANY));
    }

    private EventSetDescriptor[] eventSetDescriptors;

    public EventSetDescriptor[] getEventSetDescriptors() {
        if (eventSetDescriptors == null)
            eventSetDescriptors = DesignUtil.generateCommandEventSetDescriptors(this);
        return eventSetDescriptors;
    }
    
}
