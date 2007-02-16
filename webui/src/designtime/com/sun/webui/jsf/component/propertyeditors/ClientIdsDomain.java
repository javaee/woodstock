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
/*
 * ClientIdsDomain.java
 *
 * Created on July 29, 2005, 3:17 PM
 */

package com.sun.webui.jsf.component.propertyeditors;

import com.sun.rave.designtime.DesignBean;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import javax.faces.component.UIComponent;
import com.sun.rave.designtime.DesignContext;
import com.sun.rave.designtime.faces.FacesDesignContext;
import javax.faces.context.FacesContext;
import com.sun.rave.propertyeditors.domains.AttachedDomain;
import com.sun.rave.propertyeditors.domains.Element;

/**
 * Domain of identifiers of components in scope that meet the criteria of the
 * abstract method <code>isDomainComponent(UIComponent)</code>.
 */
public abstract class ClientIdsDomain extends AttachedDomain {
    
    /**
     * Returns a list of all the non-<code>null</code> component client identifiers.
     */
    public Element[] getElements() {
        
        // If we have not been attached yet, there is nothing we can do
        // except return an empty list
        if (getDesignProperty() == null) {
            return Element.EMPTY_ARRAY;
        }

        // Scan all the beans on this page and accumulate identifiers of all
        // of them that implement ActionSource or EditableValueHolder
        DesignContext designContext = getDesignProperty().getDesignBean().getDesignContext();
        FacesContext facesContext = ((FacesDesignContext)designContext).getFacesContext();
        Set set = new TreeSet();
        DesignBean designBeans[] = designContext.getBeansOfType(UIComponent.class);
        if (designBeans == null) {
            return Element.EMPTY_ARRAY;
        }
        for (int i = 0; i < designBeans.length; i++) {
            Object instance = designBeans[i].getInstance();
            if (instance instanceof UIComponent && isDomainComponent((UIComponent) instance) && !hasTableParent(designBeans[i])){
                String id = ((UIComponent) instance).getClientId(facesContext);
                if (id != null)
                    set.add(id);
            }
        }

        // Construct a list of elements of the retained identifiers
        Element elements[] = new Element[set.size()];
        Iterator ids = set.iterator();
        int n = 0;
        while (ids.hasNext()) {
            elements[n++] = new Element((String) ids.next());
        }
        return elements;

    }
    
    protected abstract boolean isDomainComponent(UIComponent component);
    
    private boolean hasTableParent(DesignBean bean) {
        DesignBean parent = bean.getBeanParent();
        if (parent == null) {
            return false;
        }
        Object instance = parent.getInstance();
        if (instance instanceof javax.faces.component.UIData || instance instanceof com.sun.webui.jsf.component.TableRowGroup) {
            return true;
        }
        return hasTableParent(parent);
    }
    
}
