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
