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

import com.sun.webui.jsf.component.AddRemove;
import com.sun.webui.jsf.component.Button;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;

/**
 * A delegating renderer for {@link com.sun.webui.jsf.component.AddRemove}.
 *
 * @author gjmurphy
 */
public class AddRemoveDesignTimeRenderer extends SelectorDesignTimeRenderer {
    
    /** Creates a new instance of ListboxDesignTimeRenderer */
    public AddRemoveDesignTimeRenderer() {
        super(new AddRemoveRenderer());
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if(component instanceof AddRemove) {
            AddRemove addRemove = (AddRemove) component;
            // Clear facets that are used to cache properties, so that any changes to
            // property will render
            if (addRemove.getFacet(AddRemove.AVAILABLE_LABEL_FACET) != null)
                addRemove.getFacets().remove(AddRemove.AVAILABLE_LABEL_FACET);
            if (addRemove.getFacet(AddRemove.SELECTED_LABEL_FACET) != null)
                addRemove.getFacets().remove(AddRemove.SELECTED_LABEL_FACET);
            
            // Set the escape to true at designtime. Otherwise, designtime complains not 
            // well-formed character data or markup.: < Remove
            // See bug http://www.netbeans.org/issues/show_bug.cgi?id=116338
            ((Button)addRemove.getRemoveButtonComponent()).setEscape(true);
            ((Button)addRemove.getRemoveAllButtonComponent()).setEscape(true);
        }
        super.encodeBegin(context, component);
    }
    
}
