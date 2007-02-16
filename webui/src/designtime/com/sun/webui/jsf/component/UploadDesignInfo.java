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

import com.sun.webui.jsf.design.AbstractDesignInfo;
import com.sun.rave.designtime.DesignBean;
import javax.faces.convert.Converter;

/**
 * Design time behavior for a <code>Upload</code> AKA <code>File Upload</code>
 * component.
 * 
 * @author Edwin Goei
 */
public class UploadDesignInfo extends EditableValueHolderDesignInfo {

    public UploadDesignInfo() {
        super(Upload.class);
    }

    public boolean acceptLink(DesignBean targetBean, DesignBean sourceBean, Class sourceClass) {
        if (Converter.class.isAssignableFrom(sourceClass))
            return false;
        return super.acceptLink(targetBean, sourceBean, sourceClass);
    }

    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        if (Converter.class.isAssignableFrom(childClass))
            return false;
        return super.acceptChild(parentBean, childBean, childClass);
    }
}
