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

import javax.el.ValueExpression;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

/**
 * The Script component is can be used to refer to a Javascript file, by using 
 * the url attribute. The tag can also be used embed Javascript code within the 
 * rendered HTML page.
 */
@Component(type="com.sun.webui.jsf.Script", family="com.sun.webui.jsf.Script",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_script",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_script_props")
public class Script extends UIComponentBase {
    /**
     * Default constructor.
     */
    public Script() {
        super();
        setRendererType("com.sun.webui.jsf.Script");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Script";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * The component identifier for this component. This value must be unique 
     * within the closest parent component that is a naming container.
     */
    @Property(name="id") 
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
    @Property(name="rendered") 
    public void setRendered(boolean rendered) {
        super.setRendered(rendered);
    }

    /**
     * <p>Defines the character (charset) encoding of the target URL.
     * See iana.org for a complete list of character encodings.</p>
     */
    @Property(name="charset", displayName="Charset", category="Advanced")
    private String charset = null;

    /**
     * <p>Defines the character (charset) encoding of the target URL.
     * See iana.org for a complete list of character encodings.</p>
     */
    public String getCharset() {
        if (this.charset != null) {
            return this.charset;
        }
        ValueExpression _vb = getValueExpression("charset");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Defines the character (charset) encoding of the target URL.
     * See iana.org for a complete list of character encodings.</p>
     * @see #getCharset()
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * <p>Indicates the MIME type of the script.  Default is "text/javascript"</p>
     */
    @Property(name="type", displayName="Type", category="Advanced")
    private String type = null;

    /**
     * <p>Indicates the MIME type of the script.  Default is "text/javascript"</p>
     */
    public String getType() {
        if (this.type != null) {
            return this.type;
        }
        ValueExpression _vb = getValueExpression("type");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return "text/javascript";
    }

    /**
     * <p>Indicates the MIME type of the script.  Default is "text/javascript"</p>
     * @see #getType()
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * <p>Defines the absolute or relative URL to a file that contains the 
     * script.  Use this attribute to refer to a file instead of inserting the 
     * script into your HTML document</p>
     */
    @Property(name="url", displayName="URL", category="Data", isDefault=true, editorClassName="com.sun.rave.propertyeditors.UrlPropertyEditor")
    private String url = null;

    /**
     * <p>Defines the absolute or relative URL to a file that contains the 
     * script.  Use this attribute to refer to a file instead of inserting the 
     * script into your HTML document</p>
     */
    public String getUrl() {
        if (this.url != null) {
            return this.url;
        }
        ValueExpression _vb = getValueExpression("url");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Defines the absolute or relative URL to a file that contains the 
     * script.  Use this attribute to refer to a file instead of inserting the 
     * script into your HTML document</p>
     * @see #getUrl()
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.charset = (String) _values[1];
        this.type = (String) _values[2];
        this.url = (String) _values[3];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[4];
        _values[0] = super.saveState(_context);
        _values[1] = this.charset;
        _values[2] = this.type;
        _values[3] = this.url;
        return _values;
    }

}
