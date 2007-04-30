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

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.util.ThemeUtilities;
import javax.faces.context.FacesContext;

/**
 * The Icon component is used to display a clickable icon image from the current
 * theme in the rendered HTML page.
 */
@Component(type="com.sun.webui.jsf.IconHyperlink",
    family="com.sun.webui.jsf.ImageHyperlink", 
    displayName="ImageHyperlink", isTag=false,
    tagRendererType="com.sun.webui.jsf.widget.ImageHyperlink",    
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_icon_hyperlink",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_icon_hyperlink_props")
public class IconHyperlink extends ImageHyperlink {
 
    /**
     * <p>Construct a new <code>IconHyperlink</code>.</p>
     */
    public IconHyperlink() {
        super();
        setRendererType("com.sun.webui.jsf.widget.ImageHyperlink");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.ImageHyperlink";
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[1];
        _values[0] = super.saveState(_context);
        return _values;
    }
}
