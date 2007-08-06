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
 * The Page component is used to indicate the beginning of the part of the JSP
 * page that is used by the Sun Java Web UI Components.
 */
@Component(type="com.sun.webui.jsf.Page", family="com.sun.webui.jsf.Page", displayName="Page", tagName="page",
    helpKey="projrave_ui_elements_propsheets_page_props",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_page_props")
public class Page extends UIComponentBase {

    /**
     * <p>Construct a new <code>Page</code>.</p>
     */
    public Page() {
        super();
        setRendererType("com.sun.webui.jsf.Page");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Page";
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
     * <span style="color: rgb(0, 0, 0);">Use the frame attribute to
     * indicate whether the page should render frames. If this attribute is
     * true, the rendered HTML page includes a </span><code
     * style="color: rgb(0, 0, 0);">&lt;frameset&gt;</code><span
     * style="color: rgb(0, 0, 0);"> element. If false, the rendered page
     * uses a </span><code style="color: rgb(0, 0, 0);">&lt;body&gt;</code><span
     * style="color: rgb(0, 0, 0);"> tag.&nbsp; This attribute also
     * influences the rendering of the <code>&lt;!DOCTYPE&gt;</code>
     * attribute. If frameset is true, the <code>&lt;!DOCTYPE&gt;</code> will
     * be one of the following,
     * depending on the setting of xhtml attribute.<br></span>
     * <pre style="color: rgb(0, 0, 0);">&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN"<br> "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd"&gt;<br><br>&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN"<br> "http://www.w3.org/TR/html4/DTD/frameset.dtd"&gt;<br></pre>
     */
    @Property(name="frame", displayName="Needs Frame", category="Appearance")
    private boolean frame = false;
    private boolean frame_set = false;

    /**
     * <span style="color: rgb(0, 0, 0);">Use the frame attribute to
     * indicate whether the page should render frames. If this attribute is
     * true, the rendered HTML page includes a </span><code
     * style="color: rgb(0, 0, 0);">&lt;frameset&gt;</code><span
     * style="color: rgb(0, 0, 0);"> element. If false, the rendered page
     * uses a </span><code style="color: rgb(0, 0, 0);">&lt;body&gt;</code><span
     * style="color: rgb(0, 0, 0);"> tag.&nbsp; This attribute also
     * influences the rendering of the <code>&lt;!DOCTYPE&gt;</code>
     * attribute. If frameset is true, the <code>&lt;!DOCTYPE&gt;</code> will
     * be one of the following,
     * depending on the setting of xhtml attribute.<br></span>
     * <pre style="color: rgb(0, 0, 0);">&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN"<br> "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd"&gt;<br><br>&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN"<br> "http://www.w3.org/TR/html4/DTD/frameset.dtd"&gt;<br></pre>
     */
    public boolean isFrame() {
        if (this.frame_set) {
            return this.frame;
        }
        ValueExpression _vb = getValueExpression("frame");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return false;
    }

    /**
     * <span style="color: rgb(0, 0, 0);">Use the frame attribute to
     * indicate whether the page should render frames. If this attribute is
     * true, the rendered HTML page includes a </span><code
     * style="color: rgb(0, 0, 0);">&lt;frameset&gt;</code><span
     * style="color: rgb(0, 0, 0);"> element. If false, the rendered page
     * uses a </span><code style="color: rgb(0, 0, 0);">&lt;body&gt;</code><span
     * style="color: rgb(0, 0, 0);"> tag.&nbsp; This attribute also
     * influences the rendering of the <code>&lt;!DOCTYPE&gt;</code>
     * attribute. If frameset is true, the <code>&lt;!DOCTYPE&gt;</code> will
     * be one of the following,
     * depending on the setting of xhtml attribute.<br></span><pre style="color: rgb(0, 0, 0);">&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN"<br> "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd"&gt;<br><br>&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN"<br> "http://www.w3.org/TR/html4/DTD/frameset.dtd"&gt;<br></pre>
     * @see #isFrame()
     */
    public void setFrame(boolean frame) {
        this.frame = frame;
        this.frame_set = true;
    }

    /**
     * <span style="color: rgb(0, 0, 0);">XHTML transitional page or HTML
     * transitional page. This attribute influences
     * the rendering of the <code>&lt;!DOCTYPE&gt;</code> attribute. If xhtml
     * is true, the <code>&lt;!DOCTYPE&gt;</code> will be one of the
     * following,
     * depending on the setting of frameset attribute.<br></span><pre style="color: rgb(0, 0, 0);">&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN"<br> "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd"</pre><pre style="color: rgb(0, 0, 0);">&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"<br> "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"&gt;</pre>
     */
    @Property(name="xhtml", displayName="XHTML Transitional", category="Appearance")
    private boolean xhtml = false;
    private boolean xhtml_set = false;

    /**
     * <span style="color: rgb(0, 0, 0);">XHTML transitional page or HTML
     * transitional page. This attribute influences
     * the rendering of the <code>&lt;!DOCTYPE&gt;</code> attribute. If xhtml
     * is true, the <code>&lt;!DOCTYPE&gt;</code> will be one of the
     * following,
     * depending on the setting of frameset attribute.<br></span><pre style="color: rgb(0, 0, 0);">&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN"<br> "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd"</pre><pre style="color: rgb(0, 0, 0);">&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"<br> "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"&gt;</pre>
     */
    public boolean isXhtml() {
        if (this.xhtml_set) {
            return this.xhtml;
        }
        ValueExpression _vb = getValueExpression("xhtml");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }

    /**
     * <span style="color: rgb(0, 0, 0);">XHTML transitional page or HTML
     * transitional page. This attribute influences
     * the rendering of the <code>&lt;!DOCTYPE&gt;</code> attribute. If xhtml
     * is true, the <code>&lt;!DOCTYPE&gt;</code> will be one of the
     * following,
     * depending on the setting of frameset attribute.<br></span><pre style="color: rgb(0, 0, 0);">&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN"<br> "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd"</pre><pre style="color: rgb(0, 0, 0);">&lt;!DOCTYPE html <br> PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"<br> "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"&gt;</pre>
     * @see #isXhtml()
     */
    public void setXhtml(boolean xhtml) {
        this.xhtml = xhtml;
        this.xhtml_set = true;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.frame = ((Boolean) _values[1]).booleanValue();
        this.frame_set = ((Boolean) _values[2]).booleanValue();
        this.xhtml = ((Boolean) _values[3]).booleanValue();
        this.xhtml_set = ((Boolean) _values[4]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[5];
        _values[0] = super.saveState(_context);
        _values[1] = this.frame ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.frame_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.xhtml ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.xhtml_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }

}
