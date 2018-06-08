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
 * The Meta component is used to create an HTML <meta> element in the rendered
 * HTML page.
 */
@Component(type = "com.sun.webui.jsf.Meta", family = "com.sun.webui.jsf.Meta",
displayName = "Meta", tagName = "meta",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_meta",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_meta_props")
public class Meta extends UIComponentBase {

    /**
     * <p>Construct a new <code>Meta</code>.</p>
     */
    public Meta() {
        super();
        setRendererType("com.sun.webui.jsf.Meta");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Meta";
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
     * <p>The content attribute is used to specify the data to  associate with a 
     * name attribute or httpEquiv attribute in the webuijsf:meta tag.</p>
     */
    @Property(name = "content", displayName = "Content", category = "Appearance", isDefault = true)
    private String content = null;

    /**
     * <p>The content attribute is used to specify the data to  associate with a 
     * name attribute or httpEquiv attribute in the webuijsf:meta tag.</p>
     */
    public String getContent() {
        if (this.content != null) {
            return this.content;
        }
        ValueExpression _vb = getValueExpression("content");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The content attribute is used to specify the data to  associate with a 
     * name attribute or httpEquiv attribute in the webuijsf:meta tag.</p>
     * @see #getContent()
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * <p>The httpEquiv attribute is used to specify a value for the http-equiv 
     * attribute of an HTML Meta element. The http-equiv attribute specifies 
     * HTTP properties that the web server can use in the HTTP response header.</p>
     */
    @Property(name = "httpEquiv", displayName = "HTTP header", category = "Appearance")
    private String httpEquiv = null;

    /**
     * <p>The httpEquiv attribute is used to specify a value for the http-equiv 
     * attribute of an HTML Meta element. The http-equiv attribute specifies 
     * HTTP properties that the web server can use in the HTTP response header.</p>
     */
    public String getHttpEquiv() {
        if (this.httpEquiv != null) {
            return this.httpEquiv;
        }
        ValueExpression _vb = getValueExpression("httpEquiv");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The httpEquiv attribute is used to specify a value for the http-equiv 
     * attribute of an HTML Meta element. The http-equiv attribute specifies 
     * HTTP properties that the web server can use in the HTTP response header.</p>
     * @see #getHttpEquiv()
     */
    public void setHttpEquiv(String httpEquiv) {
        this.httpEquiv = httpEquiv;
    }
    /**
     * <p>The identifier that is assigned to a property in the meta element.  
     * The content attribute provides the actual content of the property that 
     * is identified by the name attribute.</p>
     */
    @Property(name = "name", displayName = "Name", category = "Appearance")
    private String name = null;

    /**
     * <p>The identifier that is assigned to a property in the meta element.  
     * The content attribute provides the actual content of the property that 
     * is identified by the name attribute.</p>
     */
    public String getName() {
        if (this.name != null) {
            return this.name;
        }
        ValueExpression _vb = getValueExpression("name");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The identifier that is assigned to a property in the meta element.  
     * The content attribute provides the actual content of the property that 
     * is identified by the name attribute.</p>
     * @see #getName()
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * <p>Defines a format to be used to interpret the value of the content 
     * attribute.</p>
     */
    @Property(name = "scheme", displayName = "Scheme", category = "Advanced")
    private String scheme = null;

    /**
     * <p>Defines a format to be used to interpret the value of the content 
     * attribute.</p>
     */
    public String getScheme() {
        if (this.scheme != null) {
            return this.scheme;
        }
        ValueExpression _vb = getValueExpression("scheme");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Defines a format to be used to interpret the value of the content 
     * attribute.</p>
     * @see #getScheme()
     */
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.content = (String) _values[1];
        this.httpEquiv = (String) _values[2];
        this.name = (String) _values[3];
        this.scheme = (String) _values[4];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[5];
        _values[0] = super.saveState(_context);
        _values[1] = this.content;
        _values[2] = this.httpEquiv;
        _values[3] = this.name;
        _values[4] = this.scheme;
        return _values;
    }
}
