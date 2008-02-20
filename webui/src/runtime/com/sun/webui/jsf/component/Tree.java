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
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.jsf.util.RenderingUtilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.EditableValueHolder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.Validator;
import javax.faces.component.NamingContainer;
import javax.el.ValueExpression;
import javax.el.MethodExpression;

import javax.servlet.http.Cookie;

/**
 * The Tree component is used to display a tree structure in the rendered HTML
 * page.
 */
@Component(type="com.sun.webui.jsf.Tree", family="com.sun.webui.jsf.Tree", displayName="Tree", tagName="tree",
helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_tree",
        propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_tree_props")
        public class Tree extends TreeNode implements EditableValueHolder {
    
    /**
     *	Constructor.
     */
    public Tree() {
        super();
        setRendererType("com.sun.webui.jsf.Tree");
    }
    
    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Tree";
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
     * The resource at the specified URL is displayed in the frame that is
     * specified with the target attribute. Values such as "_blank" that are
     * valid for the target attribute of the  HTML element are also valid for
     * this attribute in the tree components. The target attribute is useful
     * only with the url attribute, and does not apply when a facet is used.
     */
    @Property(isHidden=true, isAttribute=true)
    public String getTarget() {
        return super.getTarget();
    }
    
    /**
     * Indicates that the text that is specified with the text attribute
     * should be rendered as a hyperlink that resolves to the specified URL.
     * If the imageURL attribute is used with the url attribute, the image is
     * hyperlinked. The url attribute does not apply to facets.
     */
    @Property(isHidden=true, isAttribute=true)
    public String getUrl() {
        return super.getUrl();
    }
    
    /**
     * Absolute or relative URL to the image to be rendered for the tree node.
     * Note that you cannot use the imageURL to display a theme image in the
     * tree. You should use an image facet that contains a ui:image or
     * ui:imageHyperlink tag to use a theme image. The imageURL attribute is
     * overridden by the <code>image</code> facet.
     * <p>When the imageURL attribute is used with the url attribute, the image
     * is hyperlinked.</p>
     */
    @Property(isHidden=true, isAttribute=true)
    public String getImageURL() {
        return super.getImageURL();
    }
    
    /**
     * The actionListener attribute is used to specify a method to handle an
     * action event that is triggered when a component is activated by the user.
     * The actionListener attribute value must be a JavaServer Faces EL
     * expression that resolves to a method in a backing bean. The method must
     * take a single parameter that is an ActionEvent, and its return type must
     * be <code>void</code>. The class that defines the method must implement
     * the <code>java.io.Serializable</code> interface or
     * <code>javax.faces.component.StateHolder</code> interface.
     * <p>In the TreeNode component, the method specified with the actionListener
     * atttribute is invoked when the node's handle icon is clicked.</p>
     */
    @Property(isHidden=true, isAttribute=true)
    public MethodExpression getActionListenerExpression() {
        return super.getActionListenerExpression();
    }
    
    /**
     * The action attribute is used to specify the action to take when this
     * component is activated by the user. The value of the action attribute
     * must be one of the following:
     * <ul>
     * <li>an outcome string, used to indicate which page to display next,
     * as defined by a navigation rule in the application configuration
     * resource file <code>(faces-config.xml)</code>.
     * </li>
     * <li>a JavaServer Faces EL expression that resolves to a backing bean
     * method. The method must take no parameters and return an outcome string.
     * The class that defines the method must implement the
     * <code>java.io.Serializable</code> interface or
     * <code>javax.faces.component.StateHolder</code> interface.
     * </li></ul>
     * <p>In the Tree and TreeNode components, the action applies only when
     * attributes are used to define the tree and tree nodes. When facets are
     * used, the action attribute does not apply to the facets.</p>
     */
    @Property(isHidden=true, isAttribute=true)
    public MethodExpression getActionExpression() {
        return super.getActionExpression();
    }
    
    // Hide expanded
    @Property(isHidden=true, isAttribute=false)
    public boolean isExpanded() {
        return super.isExpanded();
    }
    
    // clientSide
    /**
     * <p>
     *         Set the clientSide attribute to true to specify that the Tree component
     *         should run on the client. By default, this attribute is false, so the
     *         Tree component interacts
     *         with the server.  In a client-side tree, expanding and collapsing of the
     *         tree nodes happens only in the browser. In a server-side tree, a request
     *         is made to the server each time the tree nodes are expanded or collapsed.
     *         If you use the actionListener attribute to fire events, you must use a
     *         server side tree so that the event can be processed.
     *         </p>
     */
    @Property(name="clientSide", displayName="ClientSide", category="Behavior")
    private boolean clientSide = false;
    private boolean clientSide_set = false;
    
    /**
     * <p>
     *         Set the clientSide attribute to true to specify that the Tree component
     *         should run on the client. By default, this attribute is false, so the
     *         Tree component interacts
     *         with the server.  In a client-side tree, expanding and collapsing of the
     *         tree nodes happens only in the browser. In a server-side tree, a request
     *         is made to the server each time the tree nodes are expanded or collapsed.
     *         If you use the actionListener attribute to fire events, you must use a
     *         server side tree so that the event can be processed.
     *         </p>
     */
    public boolean isClientSide() {
        if (this.clientSide_set) {
            return this.clientSide;
        }
        ValueExpression _vb = getValueExpression("clientSide");
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
     * <p>
     *         Set the clientSide attribute to true to specify that the Tree component
     *         should run on the client. By default, this attribute is false, so the
     *         Tree component interacts
     *         with the server.  In a client-side tree, expanding and collapsing of the
     *         tree nodes happens only in the browser. In a server-side tree, a request
     *         is made to the server each time the tree nodes are expanded or collapsed.
     *         If you use the actionListener attribute to fire events, you must use a
     *         server side tree so that the event can be processed.
     *         </p>
     * @see #isClientSide()
     */
    public void setClientSide(boolean clientSide) {
        this.clientSide = clientSide;
        this.clientSide_set = true;
    }
    
    // expandOnSelect
    /**
     * <p>Flag indicating that folder / container nodes will automatically expand
     * 	when they are selected. This attribute is true by default. If you want a tree's container
     *   nodes to expand only when the handle icons are clicked, set expandOnSelect to false.</p>
     */
    @Property(name="expandOnSelect", displayName="Expand On Select", category="Behavior")
    private boolean expandOnSelect = false;
    private boolean expandOnSelect_set = false;
    
    /**
     * <p>Flag indicating that folder / container nodes will automatically expand
     * 	when they are selected. This attribute is true by default. If you want a tree's container
     *         nodes to expand only when the handle icons are clicked, set expandOnSelect to false.</p>
     */
    public boolean isExpandOnSelect() {
        if (this.expandOnSelect_set) {
            return this.expandOnSelect;
        }
        ValueExpression _vb = getValueExpression("expandOnSelect");
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
     * <p>Flag indicating that folder / container nodes will automatically expand
     * 	when they are selected. This attribute is true by default. If you want a tree's container
     *         nodes to expand only when the handle icons are clicked, set expandOnSelect to false.</p>
     * @see #isExpandOnSelect()
     */
    public void setExpandOnSelect(boolean expandOnSelect) {
        this.expandOnSelect = expandOnSelect;
        this.expandOnSelect_set = true;
    }
    
    // immediate
    /**
     * <p>Flag indicating that event handling for this component should be
     * 	handled immediately (in Apply Request Values phase) rather than
     * 	waiting until Invoke Application phase.
     * 	May be desired for this component when required is true (although most
     * 	likely not).</p>
     */
    @Property(name="immediate", displayName="Immediate", category="Advanced")
    private boolean immediate = false;
    private boolean immediate_set = false;
    
    /**
     * <p>Flag indicating that event handling for this component should be
     * 	handled immediately (in Apply Request Values phase) rather than
     * 	waiting until Invoke Application phase.
     * 	May be desired for this component when required is true (although most
     * 	likely not).</p>
     */
    public boolean isImmediate() {
        if (this.immediate_set) {
            return this.immediate;
        }
        ValueExpression _vb = getValueExpression("immediate");
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
     * <p>Flag indicating that event handling for this component should be
     * 	handled immediately (in Apply Request Values phase) rather than
     * 	waiting until Invoke Application phase.
     * 	May be desired for this component when required is true (although most
     * 	likely not).</p>
     * @see #isImmediate()
     */
    public void setImmediate(boolean immediate) {
        this.immediate = immediate;
        this.immediate_set = true;
    }
    
    // required
    /**
     * <p>Flag indicating that the user must select a value for this tree.
     *    Default value is false.</p>
     * @deprecated This attribute is deprecated from 4.1 and should not be used as it does not make much 
     * sense in the context of the tree.
     */
    @Property(isHidden=true ) 
    private boolean required = false;
    private boolean required_set = false;
    
    /**
     * <p>Flag indicating that the user must select a value for this tree.
     *         Default value is false. This attribute should be hidden from 
     * the application developer as it does not make sense in the context
     * of the tree. The isRequired()/setRequired() methods have to be 
     * maintained as Tree implements EditableValueHolder.</p>
     */
    public boolean isRequired() {
        if (this.required_set) {
            return this.required;
        }
        ValueExpression _vb = getValueExpression("required");
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
     * <p>Flag indicating that the user must select a value for this tree.
     *         Default value is false.</p>
     * @see #isRequired()
     */
    public void setRequired(boolean required) {
        this.required = required;
        this.required_set = true;
    }
    
    
    // style
    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this
     *    component is rendered.</p>
     */
    @Property(name="style", displayName="CSS Style(s)", category="Appearance", editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;
    
    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this
     *         component is rendered.</p>
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
     *         component is rendered.</p>
     * @see #getStyle()
     */
    public void setStyle(String style) {
        this.style = style;
    }
    
    // styleClass
    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this
     *    component is rendered.</p>
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;
    
    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this
     *    component is rendered.</p>
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
     *        component is rendered.</p>
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    
    // visible
    /**
     * <p>Use the visible attribute to indicate whether the component should be
     *     viewable by the user in the rendered HTML page. If set to false, the
     *     HTML code for the component is present in the page, but the component
     *     is hidden with style attributes. By default, visible is set to true, so
     *     HTML for the component HTML is included and visible to the user. If the
     *     component is not visible, it can still be processed on subsequent form
     *     submissions because the HTML is present.</p>
     */
    @Property(name="visible", displayName="Visible", category="Behavior")
    private boolean visible = false;
    private boolean visible_set = false;
    
    /**
     * <p>Use the visible attribute to indicate whether the component should be
     *     viewable by the user in the rendered HTML page. If set to false, the
     *     HTML code for the component is present in the page, but the component
     *     is hidden with style attributes. By default, visible is set to true, so
     *     HTML for the component HTML is included and visible to the user. If the
     *     component is not visible, it can still be processed on subsequent form
     *     submissions because the HTML is present.</p>
     */
    public boolean isVisible() {
        if (this.visible_set) {
            return this.visible;
        }
        ValueExpression _vb = getValueExpression("visible");
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
     * <p>Use the visible attribute to indicate whether the component should be
     *     viewable by the user in the rendered HTML page. If set to false, the
     *     HTML code for the component is present in the page, but the component
     *     is hidden with style attributes. By default, visible is set to true, so
     *     HTML for the component HTML is included and visible to the user. If the
     *     component is not visible, it can still be processed on subsequent form
     *     submissions because the HTML is present.</p>
     * @see #isVisible()
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }
    
    
    
    /**
     * This component renders its children
     */
    public boolean getRendersChildren() {
        return true;
    }
    
    
    /**
     * <p>Returns the id of the selected tree node. Should be cast to a String and
     * nothing else.</p>
     */
    @Property(name="selected", displayName="Selected", category="Data")
    public String getSelected() {
        return (String) getValue();
    }
    
    /**
     * <p>Specify the id of the selected tree node. Should specify a String object.
     * Also note that this should NOT be the client ID of the selected node.</p>
     * @see #getSelected()
     */
    public void setSelected(String selected) {
        
        if (selected == null || selected.length() == 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            String treeCID = getClientId(context);
            Cookie cookie = getCookie(context, treeCID + COOKIE_SUFFIX);
            if (cookie != null) {
                if (!RenderingUtilities.isPortlet(context)) {
                    ExternalContext extCtx = context.getExternalContext();
                    HttpServletResponse res =
                        (HttpServletResponse)extCtx.getResponse();
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    res.addCookie(cookie);
                    setValue(null);
                }
            }
        }

        setValue(selected);
    }
    
    /**
     * <p>Return the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property aliases.</p>
     *
     * @param name Name of value binding to retrieve
     */
    public ValueExpression getValueExpression(String name) {
        if (name.equals("selected")) {
            return super.getValueExpression("value");
        }
        return super.getValueExpression(name);
    }
    
    /**
     * <p>Set the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property
     * aliases.</p>
     *
     * @param name    Name of value binding to set
     * @param binding ValueBinding to set, or null to remove
     */
    public void setValueExpression(String name,ValueExpression binding) {
        if (name.equals("selected")) {
            super.setValueExpression("value", binding);
            return;
        }
        super.setValueExpression(name, binding);
    }
    
    //////////////////////////////////////////////////////////////////////
    //	ValueHolder Methods
    //////////////////////////////////////////////////////////////////////
    
    /**
     *	<p> Return the <code>Converter</code> (if any) that is registered for
     *	    this <code>UIComponent</code>.</p>
     *
     *	<p> Not implemented for this component.</p>
     */
    public Converter getConverter() {
        return converter;
    }
    
    /**
     *	<p> Set the <code>Converter</code> (if any) that is registered for
     *	    this <code>UIComponent</code>.</p>
     *
     *	<p> Not implemented for this component.</p>
     *
     *	@param conv New <code>Converter</code> (or <code>null</code>)
     */
    public void setConverter(Converter conv) {
        converter = conv;
        // Do nothing... throw exception?
    }
    
    /**
     *	<p> Return the local value of this <code>UIComponent</code> (if any),
     *	    without evaluating any associated <code>ValueBinding</code>.</p>
     */
    public Object getLocalValue() {
        return value;
    }
    
    /**
     *	<p> Gets the value of this {@link UIComponent}.  First, consult the
     *	    local value property of this component.  If non-<code>null</code>
     *	    return it.  If non-null, see if we have a <code>ValueBinding</code>
     *	    for the <code>value</code> property.  If so, return the result of
     *	    evaluating the property, otherwise return null.</p>
     */
    public Object getValue() {
        if (value != null) {
            return value;
        }
        ValueExpression _vb = getValueExpression("value");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            return _result;
        }
        return null;
    }
    
    /**
     *	<p> Set the value of this {@link UIComponent} (if any).</p>
     *
     *	@param	val The new local value
     */
    public void setValue(Object val) {
        
        value = val;
        
        // Mark the local value as set.
        setLocalValueSet(true);
    }
    
    
    //////////////////////////////////////////////////////////////////////
    //	EditableValueHolder Methods
    //////////////////////////////////////////////////////////////////////
    
    /**
     *	<p> Return the submittedValue value of this component.  This method
     *	    should only be used by the <code>encodeBegin()</code> and/or
     *	    <code>encodeEnd()</code> methods of this component, or its
     *	    corresponding <code>Renderer</code>.</p>
     */
    public Object getSubmittedValue() {
        return submittedValue;
    }
    
    /**
     *	<p> Set the submittedValue value of this component.  This method should
     *	    only be used by the <code>decode()</code> and
     *	    <code>validate()</code> method of this component, or its
     *	    corresponding <code>Renderer</code>.</p>
     *
     *	@param	value	The new submitted value.
     */
    public void setSubmittedValue(Object value) {
        submittedValue = value;
    }
    
    /**
     *	<p> Return the "local value set" state for this component.  Calls to
     *	    <code>setValue()</code> automatically reset this property to
     *	    <code>true</code>.
     */
    public boolean isLocalValueSet() {
        return localValueSet;
    }
    
    /**
     *	<p> Sets the "local value set" state for this component.</p>
     */
    public void setLocalValueSet(boolean value) {
        localValueSet = value;
    }
    
    /**
     *	<p> Return a flag indicating whether the local value of this component
     *	    is valid (no conversion error has occurred).</p>
     */
    public boolean isValid() {
        return valid;
    }
    
    /**
     *	<p> Set a flag indicating whether the local value of this component
     *	    is valid (no conversion error has occurred).</p>
     *
     *	@param	value	The new valid flag.
     */
    public void setValid(boolean value) {
        valid = value;
    }
    
    /**
     *	<p> Return a <code>MethodBinding</code> pointing at a method that will
     *	    be used to validate the current value of this component.   This
     *	    method will be called during the <em>Process Validations</em> or
     *	    <em>Apply Request Values</em> phases (depending on the value of
     *	    the <code>immediate</code> property). </p>
     *
     *	<p> Not implemented for this component.</p>
     */
    public MethodBinding getValidator() {
        return validatorBinding;
    }
    
    /**
     *	<p> Set a <code>MethodBinding</code> pointing at a method that will be
     *	    used to validate the current value of this component.  This method
     *	    will be called during the <em>Process Validations</em> or
     *	    <em>Apply Request Values</em> phases (depending on the value of
     *	    the <code>immediate</code> property). </p>
     *
     *	<p> Any method referenced by such an expression must be public, with a
     *	    return type of <code>void</code>, and accept parameters of type
     *	    <code>FacesContext</code>, <code>UIComponent</code>, and
     *	    <code>Object</code>.</p>
     *
     *	<p> Not implemented for this component.</p>
     *
     *	@param	valBinding  The new <code>MethodBinding</code> instance.
     */
    public void setValidator(MethodBinding valBinding) {
        validatorBinding = valBinding;
    }
    
    /**
     *	<p> Add a <code>Validator</code> instance to the set associated with
     *	    this component.</p>
     *
     *	<p> Not implemented for this component.</p>
     *
     *	@param	validator   The <code>Validator</code> to add.
     */
    public void addValidator(Validator validator) {
        if (validator == null) {
            throw new NullPointerException();
        }
        if (validators == null) {
            validators = new ArrayList();
        }
        validators.add(validator);
    }
    
    /**
     *	<p> Return the set of registered <code>Validator</code>s for this
     *	    component instance.  If there are no registered validators, a
     *	    zero-length array is returned.</p>
     *
     *	<p> Not implemented for this component.</p>
     */
    public Validator[] getValidators() {
        if (validators == null) {
            return (new Validator[0]);
        }
        return ((Validator[]) validators.toArray(
                new Validator[validators.size()]));
    }
    
    /**
     *	<p> Remove a <code>Validator</code> instance from the set associated
     *	    with this component, if it was previously associated.  Otherwise,
     *	    do nothing.</p>
     *
     *	<p> Not implemented for this component.</p>
     *
     *	@param	validator   The <code>Validator</code> to remove.
     */
    public void removeValidator(Validator validator) {
        if (validators != null) {
            validators.remove(validator);
        }
    }
    
    /**
     *	<p> Return a <code>MethodBinding</code> instance method that will be
     *	    called after any registered <code>ValueChangeListener</code>s have
     *	    been notified of a value change.  This method will be called during
     *	    the <em>Process Validations</em> or <em>Apply Request Values</em>
     *	    phases (depending on the value of the <code>immediate</code>
     *	    property). </p>
     */
    public MethodBinding getValueChangeListener() {
        return valueChangeMethod;
    }
    
    /**
     *	<p> Set a <code>MethodBinding</code> instance method that will be
     *	    called after any registered <code>ValueChangeListener</code>s have
     *	    been notified of a value change.  This method will be called
     *	    during the <em>Process Validations</em> or <em>Apply Request
     *	    Values</em> phases (depending on the value of the
     *	    <code>immediate</code> property).</p>
     *
     *	@param	method	The new MethodBinding instance.
     */
    public void setValueChangeListener(MethodBinding method) {
        valueChangeMethod = method;
    }
    
    /**
     *	<p> Add a new <code>ValueChangeListener</code> to the set of listeners
     *	    interested in being notified when <code>ValueChangeEvent</code>s
     *	    occur.</p>
     *
     *	@param	listener    The <code>ValueChangeListener</code> to be added.
     */
    public void addValueChangeListener(ValueChangeListener listener) {
        addFacesListener(listener);
    }
    
    /**
     *	<p> Return the set of registered <code>ValueChangeListener</code>s for
     *	    this component instance.  If there are no registered listeners, a
     *	    zero-length array is returned.</p>
     */
    public ValueChangeListener[] getValueChangeListeners() {
        return (ValueChangeListener [])
        getFacesListeners(ValueChangeListener.class);
    }
    
    /**
     *	<p> Remove an existing <code>ValueChangeListener</code> (if any) from
     *	    the set of listeners interested in being notified when
     *	    <code>ValueChangeEvent</code>s occur.</p>
     *
     *	@param	listener    The <code>ValueChangeListener</code> to be removed.
     */
    public void removeValueChangeListener(ValueChangeListener listener) {
        removeFacesListener(listener);
    }
    
    //////////////////////////////////////////////////////////////////////
    //	Other Methods
    //////////////////////////////////////////////////////////////////////
    
    /**
     *	<p> Decode any new state of this <code>UIComponent</code> from the
     *	    request contained in the specified <code>FacesContext</code>, and
     *	    store this state as needed.</p>
     *
     *	<p> During decoding, events may be enqueued for later processing (by
     *	    event listeners who have registered an interest),  by calling
     *	    <code>queueEvent()</code>.</p>
     *
     *	@param	context	{@link FacesContext} for the request we are processing.
     */
    public void decode(FacesContext context) {
        setValid(true);
        super.decode(context);
    }
    
    /**
     *	<p> In addition to to the default <code>UIComponent#broadcast</code>
     *	    processing, pass the <code>ValueChangeEvent</code> being broadcast
     *	    to the method referenced by <code>valueChangeListener</code>.</p>
     *
     *	@param	event	<code>FacesEvent</code> to be broadcast
     *
     *	@exception  AbortProcessingException	Signal the JSF implementation
     *	    that no further processing on the current event should be performed
     */
    public void broadcast(FacesEvent event) throws AbortProcessingException {
        
        // Perform standard superclass processing
        super.broadcast(event);
	if (event instanceof ValueChangeEvent) {
	    MethodBinding method = getValueChangeListener();
	    if (method != null) {
		FacesContext context = getFacesContext();
		method.invoke(context, new Object[] { event });
	    }
	}
    }
    
    /**
     *	<p> Perform the component tree processing required by the <em>Update
     *	    Model Values</em> phase of the request processing lifecycle for
     *	    all facets of this component, all children of this component,
     *	    and this component itself, as follows.</p>
     *
     *	    <ul><li>If the <code>rendered</code> property of this
     *		    <code>UIComponent</code> is <code>false</code>, skip
     *		    further processing.</li>
     *		<li>Call the <code>processUpdates()</code> method of all
     *		    facets and children of this {@link UIComponent}, in the
     *		    order determined by a call to
     *		    <code>getFacetsAndChildren()</code>.</li></ul>
     *
     *	@param	context	<code>FacesContext</code> for this request
     */
    public void processUpdates(FacesContext context) {
        // Skip processing if our rendered flag is false
        if (!isRendered()) {
            return;
        }
        
        // Do the super stuff...
        super.processUpdates(context);
        
        // Save model stuff...
        try {
            updateModel(context);
        } catch (RuntimeException ex) {
            context.renderResponse();
            throw new RuntimeException(ex);
        }
    }
    
    /**
     *	<p> Perform the following algorithm to update the model data
     *	    associated with this component, if any, as appropriate.</p>
     *
     *	    <ul><li>If the <code>valid</code> property of this component is
     *		    <code>false</code>, take no further action.</li>
     *		<li>If the <code>localValueSet</code> property of this
     *		    component is <code>false</code>, take no further action.</li>
     *		<li>If no <code>ValueBinding</code> for <code>value</code>
     *		    exists, take no further action.</li>
     *		<li>Call <code>setValue()</code> method of the
     *		    <code>ValueBinding</code> to update the value that the
     *		    <code>ValueBinding</code> points at.</li>
     *		<li>If the <code>setValue()</code> method returns successfully:
     *		    <ul><li>Clear the local value of this component.</li>
     *			<li>Set the <code>localValueSet</code> property of
     *			    this component to false.</li></ul></li>
     *		<li>If the <code>setValue()</code> method call fails:
     *		    <ul><li>Queue an error message by calling
     *			    <code>addMessage()</code> on the specified
     *			    <code>FacesContext</code> instance.</li>
     *			<li>Set the <code>valid</code> property of this
     *			    component to <code>false</code>.</li></ul></li>
     *	    </ul>
     *
     *	@param	context	<code>FacesContext</code> for the request we are
     *			processing.
     */
    public void updateModel(FacesContext context) {
        // Sanity Checks...
        
        if (context == null) {
            throw new NullPointerException();
        }
        if (!isValid() || !isLocalValueSet()) {
            return;
        }
        ValueBinding vb = getValueBinding("value");
        if (vb == null) {
            return;
        }
        
        try {
            vb.setValue(context, getLocalValue());
            setValue(null);
            setLocalValueSet(false);
            return;
        } catch (Exception ex) {
            String messageStr = ex.getMessage();
            if (messageStr != null) {
                FacesMessage message = null;
                message = new FacesMessage(messageStr);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                context.addMessage(getClientId(context), message);
            }
            setValid(false);
            if (LogUtil.configEnabled()) {
                LogUtil.config("Unable to update Model!", ex); // NOI18N
            }
        }
        
    }
    
    /**
     *	<p> Perform the component tree processing required by the <em>Apply
     *	    Request Values</em> phase of the request processing lifecycle for
     *	    all facets of this component, all children of this component, and
     *	    this component itself, as follows.</p>
     *
     *	    <ul><li>If the <code>rendered</code> property of this
     *		    <code>UIComponent</code> is <code>false</code>, skip
     *		    further processing.</li>
     *		<li>Call the <code>processDecodes()</code> method of all
     *		    facets and children of this <code>UIComponent</code>, in the
     *		    order determined by a call to
     *		    <code>getFacetsAndChildren()</code>.</li>
     *		<li>Call the <code>decode()</code> method of this
     *		    component.</li>
     *		<li>If a <code>RuntimeException</code> is thrown during decode
     *		    processing, call <code>FacesContext.renderResponse</code>
     *		    and re-throw the exception.</li></ul>
     *
     *	@param	context	<code>FacesContext</code> for the request.
     */
    public void processDecodes(FacesContext context) {
        // Skip processing if our rendered flag is false
        
        if (!isRendered()) {
            return;
        }
        super.processDecodes(context);
        if (isImmediate()) {
            executeValidate(context);
        }
    }
    
    /**
     *	<p> In addition to the standard <code>processValidators</code> behavior
     *	    inherited from <code>UIComponentBases</code>, calls
     *	    <code>validate()</code> if the <code>immediate</code> property is
     *	    false (which is the default);  if the component is invalid
     *	    afterwards, calls <code>FacesContext.renderResponse</code>.  If a
     *	    <code>RuntimeException</code> is thrown during validation
     *	    processing, calls <code>FacesContext.renderResponse</code> and
     *	    re-throws the exception.</p>
     */
    public void processValidators(FacesContext context) {
        if (context == null) {
            throw new NullPointerException();
        }
        
        // Skip processing if our rendered flag is false
        if (!isRendered()) {
            return;
        }
        
        super.processValidators(context);
        
        if (!isImmediate()) {
            executeValidate(context);
        }
    }
    
    /**
     *	Executes validation logic.
     */
    private void executeValidate(FacesContext context) {
        try {
            validate(context);
        } catch (RuntimeException e) {
            context.renderResponse();
            throw e;
        }
        
        if (!isValid()) {
            context.renderResponse();
        }
    }
    
    /**
     *	<p> Perform the following algorithm to validate the local value of
     *	    this <code>UIInput</code>.</p>
     *
     *	    <ul><li>Retrieve the submitted value with
     *		    <code>getSubmittedValue()</code>. If this returns null,
     *		    exit without further processing.  (This indicates that no
     *		    value was submitted for this component.)</li>
     *
     *		<li>Convert the submitted value into a "local value" of the
     *		    appropriate data type by calling
     *		    <code>getConvertedValue</code>.</li>
     *
     *		<li>Validate the property by calling
     *		    <code>validateValue</code>.</li>
     *
     *		<li>If the <code>valid</code> property of this component is
     *		    still <code>true</code>, retrieve the previous value of
     *		    the component (with <code>getValue()</code>), store the new
     *		    local value using <code>setValue()</code>, and reset the
     *		    submitted value to null.  If the local value is different
     *		    from the previous value of this component, fire a
     *		    <code>ValueChangeEvent</code> to be broadcast to all
     *		    interested listeners.</li></ul>
     *
     *	@param	context	<code>FacesContext</code> for the current request.
     */
    public void validate(FacesContext context) {
        if (context == null) {
            throw new NullPointerException();
        }
        
        // Submitted value == null means "the component was not submitted
        // at all";  validation should not continue
        Object submittedValue = getSubmittedValue();
        if (submittedValue == null) {
            return;
        }
        
        Object newValue = submittedValue;
/*
FIXME: Decide if we ever want to the Tree to support Converters
        try {
            newValue = getConvertedValue(context, submittedValue);
        }
        catch (ConverterException ce) {
            addConversionErrorMessage(context, ce, submittedValue);
            setValid(false);
        }
 */
        
        // Validate the value (check for required for now)
        validateValue(context, newValue);
        
        // If our value is valid, store the new value, erase the
        // "submitted" value, and emit a ValueChangeEvent if appropriate
        if (isValid()) {
            Object previous = getValue();
            setValue(newValue);
            setSubmittedValue(null);
            if (isDifferent(previous, newValue)) {
                queueEvent(new ValueChangeEvent(this, previous, newValue));
            }
        }
    }
    
    /**
     *	<p> Return <code>true</code> if the objects are not equal.</p>
     *
     *	@param	val1    Value 1
     *	@param	val1	Value 2
     *
     *	@return	true if the 2 values are not equal
     */
    protected boolean isDifferent(Object val1, Object val2) {
        if (val1 == val2) {
            // Same object, they're equal
            return false;
        }
        if (val1 == null) {
            // Not equal, and one is null
            return true;
        }
        return !val1.equals(val2);
    }
    
    protected void validateValue(FacesContext context, Object newValue) {
        if (!isValid()) {
            return;
        }
        if (isRequired() && ((newValue == null)
        || (newValue.toString().trim().equals("")))) {
// FIXME: Add a message
// FacesMessage message =
//	message.setSeverity(FacesMessage.SEVERITY_ERROR);
// context.addMessage(getClientId(context), message);
            setValid(false);
        }
        
// FIXME: Decide if we ever want to the Tree to support Validators (See UIInput)
    }
    
    
    /**
     *	<p> This method accepts the {@link TreeNode} which is to be selected.
     *	    The previous {@link TreeNode} that was selected will unselected.
     *	    No state is saved with this operation, the state is maintained on
     *	    the client.</p>
     *
     *	@deprecated Use #setValue(Object)
     *
     *	@param	treeNode    The {@link TreeNode} to be selected.
     */
    public void selectTreeNode(TreeNode treeNode) {
        setSelected(treeNode.getId());
//	selectTreeNode(treeNode.getClientId(FacesContext.getCurrentInstance()));
    }
    
    /**
     *	<p> This method accepts the clientId of a {@link TreeNode} which is to
     *	    be selected.  The previous {@link TreeNode} that was selected will
     *	    unselected.  No state is saved with this operation, the state is
     *	    maintained on the client-side.</p>
     *
     *	@deprecated Use #setValue(Object)
     *
     *	@param	id The id of the {@link TreeNode} to be selected.
     */
    public void selectTreeNode(String id) {
        setSelected(id);
    }
    
    /**
     *	<p> This method returns the {@link TreeNode} client ID that is
     *	    selected according the browser cookie.  This method is generally
     *	    only useful during the decode process.</p>
     *
     *	@return	The selected tree node (according to the cookie).
     */
    public String getCookieSelectedTreeNode() {
        FacesContext context = FacesContext.getCurrentInstance();
        String treeCID = getClientId(context);
        
        // If it's stull null, look at cookies...
        Cookie cookie = getCookie(context, treeCID + COOKIE_SUFFIX);
        
        if (cookie != null) {
            return cookie.getValue();
        }
        
        // Not found, return null
        return null;
    }
    
    /**
     *	<p> This method will return the {@link TreeNode} client ID that is
     *	    selected according the browser cookie.  This method is only
     *      useful during the decode process as the cookie will typically be
     *      reset to null immediately after the request is processed.</p>
     *
     *	@return	The selected tree node (according to the cookie).
     */
    public String getCookieExpandNode() {
        FacesContext context = FacesContext.getCurrentInstance();
        String treeCID = getClientId(context);
        Cookie cookie = getCookie(context, treeCID + COOKIE_SUFFIX_EXPAND);
        
        if (cookie != null) {
            return cookie.getValue();
        }
        
        // Not found, return null
        return null;
    }
    
    
    private Cookie getCookie(FacesContext context, String name) {
        ExternalContext extCtx = context.getExternalContext();
        
        return (Cookie) extCtx.getRequestCookieMap().get(name);
    }
    
    //////////////////////////////////////////////////////////////////////
    //	ValueHolder Methods
    //////////////////////////////////////////////////////////////////////
    
    /**
     *
     */
    public Object saveState(FacesContext context) {
        Object _values[] = new Object[20];
        _values[0] = super.saveState(context);
        _values[1] = this.clientSide ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.clientSide_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.expandOnSelect ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.expandOnSelect_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.immediate ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.immediate_set ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.required ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.required_set ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = this.style;
        _values[10] = this.styleClass;
        _values[11] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[12] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[13] = saveAttachedState(context, converter);
        _values[14] = this.value;
        _values[15] = localValueSet ? Boolean.TRUE : Boolean.FALSE;
        _values[16] = this.valid ? Boolean.TRUE : Boolean.FALSE;
        _values[17] = saveAttachedState(context, validators);
        _values[18] = saveAttachedState(context, validatorBinding);
        _values[19] = saveAttachedState(context, valueChangeMethod);
        
        return (_values);
    }
    
    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.clientSide = ((Boolean) _values[1]).booleanValue();
        this.clientSide_set = ((Boolean) _values[2]).booleanValue();
        this.expandOnSelect = ((Boolean) _values[3]).booleanValue();
        this.expandOnSelect_set = ((Boolean) _values[4]).booleanValue();
        this.immediate = ((Boolean) _values[5]).booleanValue();
        this.immediate_set = ((Boolean) _values[6]).booleanValue();
        this.required = ((Boolean) _values[7]).booleanValue();
        this.required_set = ((Boolean) _values[8]).booleanValue();
        this.style = (String) _values[9];
        this.styleClass = (String) _values[10];
        this.visible = ((Boolean) _values[11]).booleanValue();
        this.visible_set = ((Boolean) _values[12]).booleanValue();
        converter = (Converter) restoreAttachedState(_context, _values[13]);
        this.value = _values[14];
        localValueSet = ((Boolean) _values[15]).booleanValue();
        valid = ((Boolean) _values[16]).booleanValue();
        
        List restoredValidators = null;
        Iterator iter = null;
        if (null != (restoredValidators = (List)
        restoreAttachedState(_context, _values[17]))) {
            // if there were some validators registered prior to this
            // method being invoked, merge them with the list to be
            // restored.
            if (null != validators) {
                iter = restoredValidators.iterator();
                while (iter.hasNext()) {
                    validators.add(iter.next());
                }
            } else {
                validators = restoredValidators;
            }
        }
        
        validatorBinding = (MethodBinding) restoreAttachedState(_context,
                _values[18]);
        valueChangeMethod = (MethodBinding) restoreAttachedState(_context,
                _values[19]);
    }
    
    /**
     *	<p> Converter.</p>
     */
    private Converter converter = null;
    
    /**
     *	<p> The set of {@link Validator}s associated with this
     *	    <code>UIComponent</code>.</p>
     */
    private List validators = null;
    
    /**
     *
     */
    private MethodBinding validatorBinding = null;
    
    /**
     *	<p> The submittedValue value of this component.</p>
     */
    private Object submittedValue = null;
    
    /**
     *	<p> Toggle indicating validity of this component.</p>
     */
    private boolean valid = true;
    
    /**
     *	<p> The "localValueSet" state for this component.</p>
     */
    private boolean localValueSet;
    
    /**
     *	<p> The "valueChange" MethodBinding for this component.
     */
    private MethodBinding valueChangeMethod = null;
    
    /**
     *	<p> The value of the <code>Tree</code>.  This should be a String
     *	    representing the client id of the selected
     *	    <code>TreeNode</code>.</p>
     */
    private Object value = null;
    
    /**
     *	<p> This is the {@link com.sun.webui.theme.Theme} key used to retrieve
     *	    the JavaScript needed for this component.</p>
     *
     *	@see com.sun.webui.theme.Theme#getPathToJSFile(String)
     */
    public static final String	JAVA_SCRIPT_THEME_KEY  =    "tree";
    
    
    /**
     *	<p> This is the suffix appended to the client id when forming a request
     *	    attribute key.  The value associated with the generated key
     *	    indicates which node should be selected.  The renderer uses this
     *	    information to generate JavaScript to select this node, overriding
     *	    the previous selection.</p>
     */
    public static final String	SELECTED_SUFFIX	=	"_select";
    
    /**
     *	<p> This is the suffix appended to the client id to form the key to the
     *	    cookie Map needed to retrieve the tree selection.</p>
     */
    public static final String	COOKIE_SUFFIX	=	"-hi";
    
    /**
     *	<p> This is the suffix appended to the client id to form the key to the
     *	    cookie Map needed to retrieve the node that may need to be
     *      expanded (because it was just selected).</p>
     */
    public static final String COOKIE_SUFFIX_EXPAND = "-expand";
    
    /**
     * <p> String constant representing the content facet name. </p>
     */
    public static final String TREE_CONTENT_FACET_NAME = "content";
    
    /**
     * <p> String constant representing the image facet name. </p>
     */
    public static final String TREE_IMAGE_FACET_NAME = "image";
}
