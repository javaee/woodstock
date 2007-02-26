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

package com.sun.webui.jsf.component.propertyeditors;

import com.sun.rave.propertyeditors.SelectOneDomainEditor;
import javax.faces.component.ActionSource;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;

/**
 * Domain of client ids of all components in scope that generate events of any
 * kind, i.e. all action and input components. An input component is one that
 * implements {@link javax.faces.component.EditableValueHolder}, and an action
 * component is one that implements {@link javax.faces.component.ActionSource}.
 */
public class EventClientIdsDomain extends ClientIdsDomain {
    
    protected boolean isDomainComponent(UIComponent component) {
        Class c = component.getClass();
        if (EditableValueHolder.class.isAssignableFrom(c) || ActionSource.class.isAssignableFrom(c))
            return true;
        return false;
    }
    
}
