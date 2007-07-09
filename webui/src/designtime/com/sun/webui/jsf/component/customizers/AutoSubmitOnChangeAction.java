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
package com.sun.webui.jsf.component.customizers;

import com.sun.rave.designtime.CheckedDisplayAction;
import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.Result;
import com.sun.rave.designtime.impl.BasicDisplayAction;
import com.sun.webui.jsf.component.RbCbSelector;
import com.sun.webui.jsf.component.RadioButtonGroup;
import com.sun.webui.jsf.component.CheckboxGroup;
import com.sun.webui.jsf.component.Form;
import javax.faces.component.NamingContainer;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import java.util.regex.Pattern;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import com.sun.webui.jsf.component.FormDesignInfo;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * A basic implementation of auto-submit for editable value-holder
 * components.
 *
 * @author gjmurphy
 */

public class AutoSubmitOnChangeAction extends BasicDisplayAction implements
        CheckedDisplayAction {
    
    private static Pattern submitPattern, legacyPattern;
    
    private static Pattern getSubmitPattern() {
        if (submitPattern == null) {
            submitPattern = Pattern.compile(JavaScriptUtilities.getModuleName("common.timeoutSubmitForm") +
                    "\\s*\\(\\s*this\\s*\\.\\s*form\\s*,\\s*'\\S+'\\s*\\)\\s*;?"); //NOI18N
        }
        return submitPattern;
    }
    
    private static Pattern getLegacyPattern() {
        if (legacyPattern == null) {
            legacyPattern = Pattern.compile(
                    "webuijsf\\.\\w+\\.common\\.timeoutSubmitForm\\s*\\(\\s*this\\s*\\.\\s*form\\s*,\\s*'\\S+'\\s*\\)\\s*;?"); //NOI18N
        }
        return legacyPattern;
    }
    
    protected DesignBean bean;
    
    public AutoSubmitOnChangeAction(DesignBean bean) {
        super(DesignMessageUtil.getMessage(AutoSubmitOnChangeAction.class,
                "AutoSubmitOnChangeAction.label")); //NOI18N
        this.bean = bean;
    }
    
    public boolean isChecked() {
        return isAutoSubmit();
    }
    
    public Result invoke() {
        return toggleAutoSubmit();
    }
    
    public boolean isAutoSubmit() {
        DesignProperty property = getSubmitProperty();
        if (property == null) {
            return false;
        }
        String value = (String) property.getValue();
        if(value == null) {
            return false;
        }
        boolean valueMatchesSubmitPattern = getSubmitPattern().matcher(value).find();
        if (valueMatchesSubmitPattern) {
            return true;
        }
        else {
            // no match against submit pattern.
            // return true if matches legacy pattern, false otherwise.
            return getLegacyPattern().matcher(value).find();
        }
    }
    
    public Result toggleAutoSubmit() {
        DesignProperty property = getSubmitProperty();
        if (property == null)
            return Result.FAILURE;
        String value = (String) property.getValue();
        if (value == null || value.length() == 0) {
            // If no property value, set it
            property.setValue(getSubmitScript(null));
        } else {
            if (isAutoSubmit()) {
                // If property value contains the onSubmit script, remove it
                String newValue = getSubmitPattern().matcher(value).replaceFirst(""); //NOI18N
                // also remove the legacy pattern
                newValue = getLegacyPattern().matcher(newValue).replaceFirst(""); //NOI18N
                property.setValue(newValue);
            } else {
                // Otherwise, append the onSubmit script
                property.setValue(getSubmitScript(value));
            }
        }
        return Result.SUCCESS;
    }
    
    /**
     * Returns the <code>onChange</code> property for all components except
     * checkbox and radio button types, for which <code>onClick</code> is
     * returned. Special casing for these components needed by Internet
     * Explorer.
     */
    DesignProperty getSubmitProperty() {
        Object beanInstance = bean.getInstance();
        Class beanType = beanInstance.getClass();
        if (RbCbSelector.class.isAssignableFrom(beanType) ||
                beanInstance instanceof RadioButtonGroup ||
                beanInstance instanceof CheckboxGroup)
            return bean.getProperty("onClick"); //NOI18N
        else
            return bean.getProperty("onChange"); //NOI18N
    }
    
    String getSubmitScript(String previousScript) {
        StringBuffer buffer = new StringBuffer();
        if (previousScript != null) {
            buffer.append(previousScript);
            if (!Pattern.compile(";\\s*$").matcher(previousScript).find()) {
                buffer.append(';');
            }
            if (!Pattern.compile("\\s+$").matcher(buffer.toString()).find()) {
                buffer.append(' ');
            }
        }
        String id = FormDesignInfo.getFullyQualifiedId(bean);
        if (id == null) {
            id = bean.getInstanceName();
        } else if (id.startsWith(String.valueOf(NamingContainer.SEPARATOR_CHAR)) && id.length() > 1) {
            //fully qualified id (starting with ":") could look intimidating to users. so just chop off leading ":"
            id = id.substring(1, id.length());
        }
        buffer.append(JavaScriptUtilities.getModuleName(
                "common.timeoutSubmitForm"));
        buffer.append("(this.form, '");
        buffer.append(id);
        buffer.append("');");
        return buffer.toString();
    }
    
}
