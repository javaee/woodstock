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

package com.sun.webui.jsf.component;

import javax.el.ValueExpression;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

/**
 * The Html component is used to create the &lt;html&gt; element.
 */
@Component(type = "com.sun.webui.jsf.Html", family = "com.sun.webui.jsf.Html",
displayName = "Html", tagName = "html",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_html",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_html_props")
public class Html extends UIComponentBase {

    /**
     * <p>Construct a new <code>Html</code>.</p>
     */
    public Html() {
        super();
        setRendererType("com.sun.webui.jsf.Html");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Html";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * The component identifier for this component. This value must be unique 
     * within the closest parent component that is a naming container.
     */
    @Property(name = "id")
    @Override
    public void setId(String id) {
        super.setId(id);
    }

    /**
     * Use the rendered attribute to indicate whether the HTML code for the
     * component should be included in the rendered HTML page. If set to false,
     * the rendered HTML page does not include the HTML for the component. If
     * the component is not rendered, it is also not processed on any subsequent
     * form submission.
     */
    @Property(name = "rendered")
    @Override
    public void setRendered(boolean rendered) {
        super.setRendered(rendered);
    }
    /**
     * <p>Sets the language code for this document</p>
     */
    @Property(name = "lang", displayName = "Lang", category = "Advanced")
    private String lang = null;

    /**
     * <p>Sets the language code for this document</p>
     */
    public String getLang() {
        if (this.lang != null) {
            return this.lang;
        }
        ValueExpression _vb = getValueExpression("lang");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Sets the language code for this document</p>
     * @see #getLang()
     */
    public void setLang(String lang) {
        this.lang = lang;
    }
    /**
     * <p>Defines the XML namespace attribute.  Default value is: 
     * http://www.w3.org/1999/xhtml</p>
     */
    @Property(name = "xmlns", displayName = "XML Namespace", category = "Advanced", isDefault = true)
    private String xmlns = null;

    /**
     * <p>Defines the XML namespace attribute.  Default value is: 
     * http://www.w3.org/1999/xhtml</p>
     */
    public String getXmlns() {
        if (this.xmlns != null) {
            return this.xmlns;
        }
        ValueExpression _vb = getValueExpression("xmlns");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return "http://www.w3.org/1999/xhtml";
    }

    /**
     * <p>Defines the XML namespace attribute.  Default value is: 
     * http://www.w3.org/1999/xhtml</p>
     * @see #getXmlns()
     */
    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.lang = (String) _values[1];
        this.xmlns = (String) _values[2];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[3];
        _values[0] = super.saveState(_context);
        _values[1] = this.lang;
        _values[2] = this.xmlns;
        return _values;
    }
}
