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

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

/**
 * The SkipLink component is used to create a single-pixel transparent image
 * (not visible within the browser page) which is hyperlinked to an anchor 
 * beyond the section to skip.
 */
@Component(type = "com.sun.webui.jsf.SkipHyperlink", family = "com.sun.webui.jsf.SkipHyperlink",
displayName = "Skip Hyperlink", tagName = "skipHyperlink",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_skip_hyperlink",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_skip_hyperlink_props")
public class SkipHyperlink extends WebuiCommand {

    /**
     * <p>Construct a new <code>SkipHyperlink</code>.</p>
     */
    public SkipHyperlink() {
        super();
        setRendererType("com.sun.webui.jsf.SkipHyperlink");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.SkipHyperlink";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Hide actionExpression
    @Property(name = "actionExpression", isHidden = true, isAttribute = false)
    @Override
    public MethodExpression getActionExpression() {
        return super.getActionExpression();
    }

    // Hide actionListenerExpression
    @Property(name = "actionListenerExpression", isHidden = true, isAttribute = false)
    @Override
    public MethodExpression getActionListenerExpression() {
        return super.getActionListenerExpression();
    }

    // Hide immediate
    @Property(name = "immediate", isHidden = true, isAttribute = false)
    @Override
    public boolean isImmediate() {
        return super.isImmediate();
    }

    // Hide Value
    @Property(name = "value", isHidden = true, isAttribute = false)
    @Override
    public Object getValue() {
        return super.getValue();
    }
    /**
     * <p>Use the description attribute to provide text that describes the purpose 
     * of the skip hyperlink. The description should indicate which section is 
     * skipped when the link is clicked. The text is rendered as the alt text for 
     * the image.</p>
     */
    @Property(name = "description", displayName = "Description", category = "Appearance")
    private String description = null;

    /**
     * <p>Use the description attribute to provide text that describes the purpose 
     * of the skip hyperlink. The description should indicate which section is 
     * skipped when the link is clicked. The text is rendered as the alt text for 
     * the image.</p>
     */
    public String getDescription() {
        if (this.description != null) {
            return this.description;
        }
        ValueExpression _vb = getValueExpression("description");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Use the description attribute to provide text that describes the purpose 
     * of the skip hyperlink. The description should indicate which section is 
     * skipped when the link is clicked. The text is rendered as the alt text for 
     * the image.</p>
     * @see #getDescription()
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name = "style", displayName = "CSS Style(s)", category = "Appearance",
    editorClassName = "com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    public String getStyle() {
        if (this.style != null) {
            return this.style;
        }
        ValueExpression _vb = getValueExpression("style");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     * @see #getStyle()
     */
    public void setStyle(String style) {
        this.style = style;
    }
    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name = "styleClass", displayName = "CSS Style Class(es)", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;

    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    public String getStyleClass() {
        if (this.styleClass != null) {
            return this.styleClass;
        }
        ValueExpression _vb = getValueExpression("styleClass");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     */
    @Property(name = "tabIndex", displayName = "Tab Index", category = "Accessibility",
    editorClassName = "com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int tabIndex = Integer.MIN_VALUE;
    private boolean tabIndex_set = false;

    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     */
    public int getTabIndex() {
        if (this.tabIndex_set) {
            return this.tabIndex;
        }
        ValueExpression _vb = getValueExpression("tabIndex");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     * @see #getTabIndex()
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
        this.tabIndex_set = true;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.description = (String) _values[1];
        this.style = (String) _values[2];
        this.styleClass = (String) _values[3];
        this.tabIndex = ((Integer) _values[4]).intValue();
        this.tabIndex_set = ((Boolean) _values[5]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[6];
        _values[0] = super.saveState(_context);
        _values[1] = this.description;
        _values[2] = this.style;
        _values[3] = this.styleClass;
        _values[4] = new Integer(this.tabIndex);
        _values[5] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
