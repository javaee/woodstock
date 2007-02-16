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
package com.sun.webui.jsf.renderkit.html;

import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.renderkit.html.LabelRenderer;

/**
 * A delegating renderer for {@link com.sun.webui.jsf.component.Label} that
 * sets a default text property when the property is null.
 *
 * @author gjmurphy
 */
public class LabelDesignTimeRenderer extends ValueHolderDesignTimeRenderer {
    
    boolean isTextDefaulted;
    
    public LabelDesignTimeRenderer() {
        super(new LabelRenderer());
    }

    protected String getShadowText() {
        return DesignMessageUtil.getMessage(LabelDesignTimeRenderer.class, "label.label");
    }
    
}
