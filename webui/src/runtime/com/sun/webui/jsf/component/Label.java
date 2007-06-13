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
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.FacesMessageUtils;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme; 

import java.beans.Beans;
import java.util.Iterator;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.NamingContainer; 
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * The Label component displays a label for a component and
 * implements methods to aid a <code>Label</code> renderer to 
 * render <code>error</code> and <code>required</code> indicators
 * based on the state of the labeled component.
 * <p>
 * A distinction is made between the component that is identified as the
 * target of the <code>for</code> attribute and the component that is
 * used to determine the status of the indicators. This is because 
 * the labeled component's id, as idententified by the value of the
 * <code>for</code> property, may not be the literal id used for the
 * rendered HTML label element's "for" attribute. This is commonly the
 * case for complex components that depend on subcomponents for 
 * parts of their behavior.
 * </p>
 * <p>
 * The <code>Label</code>'s <code>for</code> property is used to 
 * specify the id of the HTML label element's "for" attribute, and it
 * may not be a component's client id. However in many cases when the
 * JSP tag for a <code>Label</code> is used, the only id available is the
 * client id or simple id of the labeled component. The <code>Label</code> must
 * then find the component instance to determine if this is the
 * id to be rendered as the value of the HTML label element's "for"
 * attribute. A <code>Label</code> renderer uses <code>Label</code>
 * methods to obtain an appropriate id. The <code>Label</code>
 * methods that facilitate finding the appropriate id do so in the 
 * following way:
 * </p>
 * <p>
 * <ul>
 * <li>If the <code>labeledComponent</code> property is not <code>null</code>,
 * it is used directly to determine the appropriate id. This property
 * is usually set for performance reasons by components that have 
 * labels as subcomponents, i.e. when the <code>label</code>property
 * is used on some components, like <code>TextField</code>.</li>
 * <li>If the <code>labeledComponent</code> property is <code>null</code>
 * the, <code>for</code>property is used to obtain a component instance.
 * <code>findComponent</code> is called with the value of the
 * <code>for</code> property to locate a component instance.
 * If the value of the "for" property contains a leading 
 * <code>NamingContainer.SEPARATOR</code> character,
 * an explicit search from the view root is performed to find
 * the component.<br/>If there is no leading 
 * <code>NamingContainer.SEPARATOR</code> then the value is treated as
 * an id of a sibling to the <code>Label</code> and if the component
 * is still not found, it is treated as an id of a <code>Label</code>
 * child and <code>findComponent</code> is used again to find the
 * component instance.<br/>
 * If a component instance is still not found and if there are
 * embedded <code>NamingContainer.SEPARATOR</code> characters within the id, it 
 * is treated as a fully qualifed id and a search from the view root is
 * performed.<br/>If all these attempts are unsuccessful then the value
 * of the "for" property should be used literally for the HTML label 
 * element's "for" attribute value.</li>
 * <li>If both <code>labeledComponent</code> and the <code>for</code>
 * property are <code>null</code> a <code>Label</code> child, if
 * one exists, is chosen as the target of the <code>Label</code>.<br/>
 * If there are label children, then the first <code>EditableValueHolder</code>
 * child is chosen.<br/>If there are no <code>EditableValueHolder</code>
 * children then the first <code>ComplexComponent</code> child is chosen.<br/>
 * If there are no <code>ComplexComponent</code> children then the first
 * child is chosen.</li>
 * <li>If an instance is found (either from the <code>for</code> property
 * or the <code>labeledComponent</code> property), or as a child,
 * and it is an instance of <code>ComplexComponent</code>, 
 * the <code>getLabeledElementId</code> method is 
 * called on the instance and the returned value is used as the 
 * rendered HTML label element's "for" attribute value, else the 
 * component instance's client id is used.</li>
 * </ul>
 * <p>
 * The <code>Label</code> uses a similar algorithm to obtain a
 * component instance to reference in order to determine the status
 * of the <code>error</code> and <code>required</code> indicators.
 * If the <code>indicatorComponent</code> property is not null, it 
 * should be used to determine the status of the error and required
 * indicators. If it is an instanceof <code>ComplexComponent</code>
 * the <code>getIndicatorComponent</code> method should be called
 * to obtain the appropriate instance reference.
 * </p>
 * <p>
 * If the <code>indicatorComponent</code> property is <code>null</code>
 * then fallback to the strategy for finding an instance for the
 * HTML label element's "for" attribute. The instance obtained from this
 * strategy, if an instance of <code>ComplexComponent</code> has its
 * <code>getIndicatorComponent</code> method called and the instance
 * returned is used, otherwise use the instance to determine the state
 * of the error and required indicators. This may or may not be
 * possible depending on the interfaces needed by the renderer
 * to render error and required indicators. The default renderer
 * expects the <code>required</code> and <code>valid</code> properties
 * to exist on the component instance used to determine the status
 * of the error and required indicators.
 * </p>
 */
@Component(type="com.sun.webui.jsf.Label", family="com.sun.webui.jsf.Label", 
    displayName="Label", tagName="label",
    tagRendererType="com.sun.webui.jsf.widget.Label",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_label",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_label_props")
public class Label extends WebuiOutput implements NamingContainer {
    
    /**
     * The id suffix used for the required image.
     * @see #getRequiredIcon
     */
    public static final String REQUIRED_ID = "_required";
    /**
     * The name of a developer defined facet for the required indicator.
     * @see #getRequiredIcon
     */
    public static final String REQUIRED_FACET = "required";
    /**
     * The id suffix used for the error image.
     * @see #getErrorIcon
     */
    public static final String ERROR_ID = "_error";
    /**
     * The name of a developer defined facet for the error indicator.
     * @see #getErrorIcon
     */
    public static final String ERROR_FACET = "error";
    
    // This used to be typed as an EditableValueHolder
    // but since it is private make it a UIComponent so that
    // it can be used effectively.
    // Methods that referenced it as an EditableValueHolder
    // are deprectated, and rewritten to use non deprecated
    // methods.
    //
    /**
     * Used as an alternative and optimization over the 
     * for property, eliminating the need for a renderer to 
     * call <code>findComponent</code> with the value of the
     * <code>for</code> property.
     */
    transient private UIComponent labeledComponent;

    private static final boolean DEBUG = false;

    /**
     * Default constructor.
     * This implementation sets the renderer type to 
     * <code>com.sun.webui.jsf.widget.Label</code>.
     */
    public Label() {
        super();
        setRendererType("com.sun.webui.jsf.widget.Label");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Label";
    }     

    /**
     * This implementation returns <code>com.sun.webui.jsf.ajax.Label</code>
     * for the renderer type if <code>ComponentUtilities.isAjaxRequest()</code>
     * returns <code>true</code> else <code>super.getRendererType()</code>.
     */
    public String getRendererType() { 
	// Ensure we have a valid Ajax request. 
	if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
	    return "com.sun.webui.jsf.ajax.Label"; 
	} 
	return super.getRendererType(); 
    }  
     
    /**
     * Set <code>labeledComponent</code> to <code>comp</code>.
     * If <code>comp</code> is null, <code>labeledComponent</code>
     * is set to <code>null</code>.
     */
    public void setLabeledComponent(UIComponent comp) { 
	labeledComponent = comp;
    }

    // Note that if annotations are provided for labeledComponent
    // this definition returning EditableValueHolder generates
    // annotation warnings. I'm not sure what the right thing to 
    // do is. There has always been a disconnect between the
    // labeledComponent property and the "Bean" methods.
    // The setter always accepted a UIComponent for an argument
    // and the getter returned an EditableValueHolder.
    // 
    /**
     * Return the labeled component instance.
     * If the labeled component has not been set and <code>getFor</code>
     * returns null, return the first <code>EditableValueHolder</code>
     * child of this <code>Label</code> component.</br>
     * If <code>getFor</code> does not return null and the value
     * is a not an absolute id, search for the labeled component
     * using <code>UIComponentBase.findComponent</code>.
     * Otherwise search for the labeled component from the view root
     * using <code>UIComponentBase.findComponent</code> (In this
     * case the id is ensured to have ":" prepended to the id to 
     * cause <code>findComponent</code> to search from the view root).</br>
     * <code>setLabeledComponent</code> is called 
     * to set the labeled component to the component
     * that is found or null.
     *
     * @return the labeled component instance or null
     * @deprecated 
     * @see #getFor()
     */
    public EditableValueHolder getLabeledComponent() {
        
	if (labeledComponent != null) {
	    if (labeledComponent instanceof EditableValueHolder) {
		return (EditableValueHolder)labeledComponent;
	    } else {
		return null;
	    }
	}
	String forId = getFor();
	if (forId == null) {

	    Iterator children = getChildren().iterator();
	    while (children.hasNext()) {
		UIComponent child = (UIComponent)children.next();
		if (child instanceof EditableValueHolder) {
		    return (EditableValueHolder)child;
		}
	    }
	}
	UIComponent lc = getLabeledComponent(FacesContext.getCurrentInstance(),
		forId);
	if (lc != null && lc instanceof EditableValueHolder) {
	    return (EditableValueHolder)lc;
	} else {
	    return null;
	}
    }
    
    /**
     * Return the absolute client id of the labeled component.
     * If the labeled component is an instance of
     * <code>ComplexComponent</code>, call <code>getPrimaryElementID</code> on
     * the labeled component and return that id.</br>
     * If the labeled component is not an instance of
     * <code>ComplexComponent</code>, call <code>getClientId</code> on the
     * labeled component and return that id.</br>
     * If the labeled component has not been set, and the
     * <code>getFor</code> method returns a non null absolute 
     * id, return that id.</br>
     * If <code>getFor</code> returns a relative id and the label's parent
     * is a <code>NamingContainer</code>, return
     * an absolute client id constructed from the label's parent client id
     * and the id returned by the <code>getFor</code> method. If the label's 
     * parent is not a <code>NamingContainer</code> return the id
     * returned by <code>getFor</code>.</br>
     * If <code>getFor</code> returns null, return null.
     *
     * @return the client id of the labeled component or null.
     * @deprecated This method now just calls <code>getLabeledElementId</code>
     * @see #getLabeledElementId
     */
    public String getLabeledComponentId(FacesContext context) { 
	return getLabeledElementId(context);
    } 

    /**
     * Return a component that implements a required icon.
     * If a facet named <code>required</code> is found
     * that component is returned.</br>
     * If a facet is not found an <code>Icon</code>
     * component instance is returned with the id</br>
     * <code>getId() + "_required"</code>.
     * <p>
     * If a facet is not defined then the returned <code>Icon</code>
     * component is created every time this method is called.
     * </p>
     * @return - required facet or an Icon instance
     */
    public UIComponent getRequiredIcon(Theme theme, FacesContext context) { 
        
        UIComponent comp = getFacet(REQUIRED_FACET);
        if(comp != null) {
	    return comp;
	}

	Icon icon = ThemeUtilities.getIcon(theme,
	    ThemeImages.LABEL_REQUIRED_ICON);
	icon.setId(
	    ComponentUtilities.createPrivateFacetId(this, REQUIRED_FACET));
	icon.setParent(this);
	icon.setBorder(0);            

	//icon.setLongDesc("TODO: Required");

        return icon;    
    }
    
    /**
     * Return a component that implements an error icon.
     * If a facet named <code>error</code> is found
     * that component is returned.</br>
     * If a facet is not found an <code>Icon</code>
     * component instance is returned with the id</br>
     * <code>getId() + "_error"</code>.
     * <p>
     * If a facet is not defined then the returned <code>Icon</code>
     * component is created every time this method is called.
     * </p>
     * @deprecated
     *
     * @see #getErrorIcon(Theme, FacesContext, String)
     * 
     * @return - error facet or an Icon instance
     */
    public UIComponent getErrorIcon(Theme theme, FacesContext context, 
                                    boolean valid) { 
        
        UIComponent comp = getFacet(ERROR_FACET);
        if (comp != null) {
	    return comp;
	}
           
	// We just return a DOT image for a "valid" labeled
	// component. I'm not sure why.
	// The renderer should just use CSS to provide the
	// appropriate padding if that is why the DOT image was used.
	//
	if (valid) {

	    Icon icon = ThemeUtilities.getIcon(theme, ThemeImages.DOT);
	    icon.setIcon(ThemeImages.DOT);
	    icon.setId(ComponentUtilities.createPrivateFacetId(this, 
	    	ERROR_FACET));
	    icon.setParent(this);
	    icon.setBorder(0);
	    //icon.setLongDesc("TODO: Invalid");
	    icon.setAlt("");
	    return icon;
	}

	// Find any error messages for the indicator component
	// and get the error icon.
	//
	UIComponent ic = getIndicatorComponent(context, true);
	String msg = null;
	if (ic != null) {
	    msg = FacesMessageUtils.getDetailMessages(context, 
		ic.getClientId(context), true, " ");
	}
        return getErrorIcon(theme, context, msg);
    }

    /**
     * Return a component that implements an error icon.
     * If a facet named <code>error</code> is found
     * that component is returned.</br>
     * If a facet is not found an <code>Icon</code>
     * component instance is returned with the id</br>
     * <code>getId() + "_error"</code>.
     * <p>
     * If a facet is not defined then the returned <code>Icon</code>
     * component is created every time this method is called.
     * </p>
     * 
     * @return - error facet or an Icon instance
     */
    public UIComponent getErrorIcon(Theme theme, FacesContext context,
		String toolTipErrorMsg) {
        
        UIComponent comp = getFacet(ERROR_FACET);
        if (comp != null) {
	    return comp;
	}
           
	Icon icon = ThemeUtilities.getIcon(theme,
	    ThemeImages.LABEL_INVALID_ICON);
	icon.setId(ComponentUtilities.createPrivateFacetId(this, ERROR_FACET));
	icon.setParent(this);
	icon.setBorder(0);
	//icon.setLongDesc("TODO: Invalid");

	if (toolTipErrorMsg != null) {
	   icon.setToolTip(toolTipErrorMsg);
	}

        return icon;
    }
    /**
     * Return <code>span</code> if the label is not labeling another
     * component, else return <code>label</code>.
     *
     * <em>The deprecated behavior always returns <code>label</code>.</em>
     *
     * @deprecated
     */
    public String getElement() { 
	return "label"; 
    } 

    /**
     * Return an id suitable for the HTML label element's "for" attribute.
     * Call {@link #getLabeledComponent(FacesContext)} to obtain
     * the labeled component instance. If the returned instance is
     * a <code>ComplexComponent</code> call <code>getLabeledElementId</code>
     * on the instance and return that id, else return the instance's
     * client id. If <code>getLabeledComponent</code> returns <code>null</code>
     * return the value of <code>getFor()</code>.
     *
     * @param context The faces context
     *
     * @return An id suitable for the HTML label element's "for" attribute.
     */
    public String getLabeledElementId(FacesContext context) {

        UIComponent _labeledComponent = getLabeledComponent(context);
        if (_labeledComponent != null) {
            if (_labeledComponent instanceof ComplexComponent) {
                return ((ComplexComponent)_labeledComponent).
                        getLabeledElementId(context);
            } else {
                return _labeledComponent.getClientId(context);
            }
        }
        return getFor();
    }
    
    /**
     * If the <code>labeledComponent</code> member is not <code>null</code>
     * return it, else return the result of 
     * {@link #getLabeledComponent(FacesContext,String)}.
     *
     * @return the labeled component instance or null
     */
    public UIComponent getLabeledComponent(FacesContext context) {
        
	if (labeledComponent != null) {
	    return labeledComponent;
	} else {
	    return getLabeledComponent(context, getFor());
	}
    }

    /**
     * Return the labeled component with <code>id</code>.
     * <p>
     * If <code>id</code> is not <code>null</code>:
     * <ul>
     * <li>If <code>id</code> starts with 
     * <code>NamingContainer.SEPARATOR_CHAR</code> search from the view root
     * and return a component instance else <code>null</code> if no component
     * is found, otherwise</li>
     * <li>search for <code>id</code> from the <code>Label</code> parent as
     * a sibling of the <code>Label</code> and return a component instance
     * if one is found, else</li>
     * <li>search for <code>id</code> as a child of the <code>Label</code>
     * and return a component instance if found, else</li>
     * <li>if <code>id</code> contains a
     * <code>NamingContainer.SEPARATOR_CHAR</code> treat <code>id</code>
     * as a fully qualified id and search from the view root and return
     * a component instance if found, else return <code>null</code>.</li>
     * </ul>
     * </p>
     * <p>
     * If <code>id</code> is <code>null</code> and there are no children
     * return <code>null</code>. If there are children then
     * return the first <code>EditableValueHolder</code>
     * child (in order to maintain legacy behavior). If there
     * are no <code>EditableValueHoldre</code> children, return the first
     * <code>ComplexComponent</code> child. If there are no 
     * <code>ComplexComponent</code> children, return the first child.
     * </p>
     */
    protected UIComponent getLabeledComponent(FacesContext context, String id) {

        // If there is no id specified then
        // return the first label child.
        //
        if (id == null) {
	    return getLabeledChild();
        }

        UIComponent lc = null;
	// There are several cases based on the form of the id.
	// If id starts with a "NamingContainer.SEPARATOR_CHAR",
	// then it is an explicit request to search from the view root.
	//
	if (id.charAt(0) == NamingContainer.SEPARATOR_CHAR) {
	    try {
		lc = context.getViewRoot().findComponent(id);
	    } catch (Exception e) {
		if (LogUtil.fineEnabled(Label.class)) {
		    LogUtil.fine(Label.class,
		    "Can't find component " + id + " from the view root.");
		}
	    }
	    // Always just return from a request for an explicit search
	    // from the root.
	    //
	    return lc;
	}

	// If there are any separator characters in the id 
	// it could still be an absolute path from the view root or
	// it could be a relative path to a sibling of the label.
	// Or it could be deeply nested child of Label.
	//
	// First look for a sibling from the Label's parent. There is high 
	// likelyhood that the component is a sibling of the Label.
	// In this case, we need to call findComponent from the
	// parent component of the Label since the Label itself
	// is a NamingContainer.
	// 
	UIComponent parent = getParent();
	if (parent != null) {
	    try {
		lc = parent.findComponent(id);
	    } catch (Exception e) {
		// try some other assumption
		if (LogUtil.fineEnabled(Label.class)) {
		    LogUtil.fine(Label.class,
		    "Can't find component " + id + " as sibling of label ");
		}
	    }
	    // Not sure if findComponent always throws if the 
	    // component is not found.
	    //
	    if (lc != null) {
		return lc;
	    }
	}

	// See if it is a child of label
	//
	try {
	    lc = findComponent(id);
	} catch (Exception e) {
	    // try some other assumption
	    if (LogUtil.fineEnabled(Label.class)) {
		LogUtil.fine(Label.class,
		"Can't find component " + id + " as a child of label ");
	    }
	}
	if (lc != null) {
	    return lc;
	}

	// Lastly try from the view root if there is a 
	// separator char as part of the id. Chances are that
	// if there isn't then the component would have been found as
	// a sibling if there was only one naming container ancestor of label
	//
	if (id.indexOf(NamingContainer.SEPARATOR_CHAR) != -1) {
	    id = String.valueOf(NamingContainer.SEPARATOR_CHAR).concat(id);
            try {
                lc = context.getViewRoot().findComponent(id);
            } catch (Exception e) {
                if (LogUtil.fineEnabled(Label.class)) {
                    LogUtil.fine(Label.class,
                        "Can't find " + id + "from the view root");  //NOI18N
                }
            }
	}
        return lc;
    }

    /**
     * Return a child component instance to be labeled, else <code>null</code>
     * if there are no children.
     * The following heuristic is used to return a child.
     * <ul>
     * <li>Return the first <code>EditableValueHolder</code> if one exists.</li>
     * <li>If there is no <code>EditableValueHolder</code>, return the first 
     * <code>ComplexComponent</code> child.</li>
     * <li>If there is no <code>ComplexComponent</code> child, return
     * the first child.</code></li>
     * </ul>
     */
    private UIComponent getLabeledChild() {
	    UIComponent ccChild = null;
	    UIComponent anyChild = null;
            Iterator iterator = getChildren().iterator();
	    while (iterator.hasNext()) {
                UIComponent child = (UIComponent)iterator.next();
		if (child instanceof EditableValueHolder) {
		    return child;
		}
		if (ccChild == null && child instanceof ComplexComponent) {
		    ccChild = child;
		} else 
		if (anyChild == null) {
		    anyChild = child;
		}
	    }
	    // No EditableValueHolder found. Return the ccChild or
	    // or anyChild if found, else null.
	    //
	    return ccChild != null ? ccChild :
		(anyChild != null ? anyChild : null);
    }

    private void log(String s) { 
        System.out.println(getClass().getName() + "::" + s);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Return the label level.
     * If the label level is less than 1 or greater than 3, 2 is returned.
     */
    // These values need to be Theme based.
    //
    public int getLabelLevel() {
        int level = _getLabelLevel();
        if(level < 1 || level > 3) { 
            level = 2; 
            setLabelLevel(level); 
        }
        return level;
    }

    /**
     * <p>Return the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property aliases.</p>
     *
     * @param name Name of value binding expression to retrieve
     */
    public ValueExpression getValueExpression(String name) {
        if (name.equals("text")) {
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
     * @param binding ValueExpression to set, or null to remove
     */
    public void setValueExpression(String name,ValueExpression binding) {
        if (name.equals("text")) {
            super.setValueExpression("value", binding);
            return;
        }
        super.setValueExpression(name, binding);
    }
    
    // Hide value
    @Property(name="value", isHidden=true, isAttribute=false)
    public Object getValue() {
        return super.getValue();
    } 
    
    /**
     * <p>Use this attribute to specify the labeled component. 
     * The value of the attribute is the absolute client id of the component
     * or the id of the component to be labeled. A relative component
     * id may be used only if the labeled component is a sibling of the label.
     * </p>
     */
    @Property(name="for", displayName="Input Component", category="Appearance")
    private String _for = null;

    /**
     * <p>Use this attribute to specify the labeled component. 
     * The value of the attribute is the absolute client id of the component
     * or the id of the component to be labeled. A relative component
     * id may be used only if the labeled component is a sibling of the label.
     * </p>
     */
    public String getFor() {
        if (this._for != null) {
            return this._for;
        }
        ValueExpression _vb = getValueExpression("for");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }


    /**
     * <p>Use this attribute to specify the labeled component. 
     * The value of the attribute is the absolute client id of the component
     * or the id of the component to be labeled. A relative component
     * id may be used only if the labeled component is a sibling of the label.
     * </p>
     */
    public void setFor(String _for) {
        this._for = _for;
    }

    /**
     * <p>Use the hideIndicators attribute to prevent display of the
     * required and invalid icons with the label. When the required
     * attribute on the component to be labeled is set to true, the
     * required icon is displayed next to the label. If the user
     * submits the page with an invalid value for the component, the
     * invalid icon is displayed. This attribute is useful when the
     * component has more than one label, and only one label should
     * show the icons.</p>
     */
    @Property(name="hideIndicators", 
	displayName="Hide the Required and Invalid icons", category="Advanced")
    private boolean hideIndicators = false;
    private boolean hideIndicators_set = false;

    /**
     * <p>Use the hideIndicators attribute to prevent display of the
     * required and invalid icons with the label. When the required
     * attribute on the component to be labeled is set to true, the
     * required icon is displayed next to the label. If the user
     * submits the page with an invalid value for the component, the
     * invalid icon is displayed. This attribute is useful when the
     * component has more than one label, and only one label should
     * show the icons.</p>
     */
    public boolean isHideIndicators() {
        if (this.hideIndicators_set) {
            return this.hideIndicators;
        }
        ValueExpression _vb = getValueExpression("hideIndicators");
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
     * <p>Use the hideIndicators attribute to prevent display of the
     * required and invalid icons with the label. When the required
     * attribute on the component to be labeled is set to true, the
     * required icon is displayed next to the label. If the user
     * submits the page with an invalid value for the component, the
     * invalid icon is displayed. This attribute is useful when the
     * component has more than one label, and only one label should
     * show the icons.</p>
     * @see #isHideIndicators()
     */
    public void setHideIndicators(boolean hideIndicators) {
        this.hideIndicators = hideIndicators;
        this.hideIndicators_set = true;
    }

    /**
     * Alternative HTML template to be used by this component. 
     */ 
    @Property(name="htmlTemplate", isHidden=true, isAttribute=true, displayName="HTML Template", category="Appearance") 
    private String htmlTemplate = null; 
  
    /** 
     * Get alternative HTML template to be used by this component. 
     */ 
    public String getHtmlTemplate() { 
        if (this.htmlTemplate != null) { 
            return this.htmlTemplate; 
        } 
        ValueExpression _vb = getValueExpression("htmlTemplate"); 
        if (_vb != null) { 
            return (String)_vb.getValue(getFacesContext().getELContext()); 
        } 
        return null; 
    } 
  
    /** 
     * Set alternative HTML template to be used by this component. 
     */ 
    public void setHtmlTemplate(String htmlTemplate) { 
        this.htmlTemplate = htmlTemplate; 
    } 

    /**
     * <p>Style level for this label, where lower values typically specify
     * progressively larger font sizes, and/or bolder font weights.
     * Valid values are 1, 2, and 3. The default label level is 2.  Any label
     * level outside this range will result in no label level being added.</p>
     */
    @Property(name="labelLevel", displayName="Style Level", 
	category="Appearance")
    private int labelLevel = Integer.MIN_VALUE;
    private boolean labelLevel_set = false;

    /**
     * <p>Style level for this label, where lower values typically specify
     * progressively larger font sizes, and/or bolder font weights.
     * Valid values are 1, 2, and 3. The default label level is 2.  Any label
     * level outside this range will result in no label level being added.</p>
     */
    public int _getLabelLevel() {
        if (this.labelLevel_set) {
            return this.labelLevel;
        }
        ValueExpression _vb = getValueExpression("labelLevel");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return 2;
    }

    /**
     * <p>Style level for this label, where lower values typically specify
     * progressively larger font sizes, and/or bolder font weights.
     * Valid values are 1, 2, and 3. The default label level is 2.  Any label
     * level outside this range will result in no label level being added.</p>
     * @see #getLabelLevel()
     */
    public void setLabelLevel(int labelLevel) {
        this.labelLevel = labelLevel;
        this.labelLevel_set = true;
    }

    /**
     * <p>Scripting code executed when a mouse click
     * occurs over this component.</p>
     */
    @Property(name="onClick", displayName="Click Script", 
    category="Javascript", 
    editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onClick = null;

    /**
     * <p>Scripting code executed when a mouse click
     * occurs over this component.</p>
     */
    public String getOnClick() {
        if (this.onClick != null) {
            return this.onClick;
        }
        ValueExpression _vb = getValueExpression("onClick");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when a mouse click
     * occurs over this component.</p>
     * @see #getOnClick()
     */
    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    /**
     * <p>Scripting code executed when the user presses a mouse button 
     * while the mouse pointer is on the component.</p>
     */
    @Property(name="onMouseDown", displayName="Mouse Down Script", 
	category="Javascript", 
    editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseDown = null;

    /**
     * <p>Scripting code executed when the user presses a mouse button 
     * while the mouse pointer is on the component.</p>
     */
    public String getOnMouseDown() {
        if (this.onMouseDown != null) {
            return this.onMouseDown;
        }
        ValueExpression _vb = getValueExpression("onMouseDown");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when the user presses a mouse button 
     * while the mouse pointer is on the component.</p>
     * @see #getOnMouseDown()
     */
    public void setOnMouseDown(String onMouseDown) {
        this.onMouseDown = onMouseDown;
    }

    /**
     * <p>Scripting code executed when the user moves the mouse pointer while
     * over the component.</p>
     */
    @Property(name="onMouseMove", displayName="Mouse Move Script", 
    category="Javascript", 
    editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseMove = null;

    /**
     * <p>Scripting code executed when the user moves the mouse pointer while
     * over the component.</p>
     */
    public String getOnMouseMove() {
        if (this.onMouseMove != null) {
            return this.onMouseMove;
        }
        ValueExpression _vb = getValueExpression("onMouseMove");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when the user moves the mouse pointer while
     * over the component.</p>
     * @see #getOnMouseMove()
     */
    public void setOnMouseMove(String onMouseMove) {
        this.onMouseMove = onMouseMove;
    }

    /**
     * <p>Scripting code executed when a mouse out movement
     * occurs over this component.</p>
     */
    @Property(name="onMouseOut", displayName="Mouse Out Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseOut = null;

    /**
     * <p>Scripting code executed when a mouse out movement
     * occurs over this component.</p>
     */
    public String getOnMouseOut() {
        if (this.onMouseOut != null) {
            return this.onMouseOut;
        }
        ValueExpression _vb = getValueExpression("onMouseOut");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when a mouse out movement
     * occurs over this component.</p>
     * @see #getOnMouseOut()
     */
    public void setOnMouseOut(String onMouseOut) {
        this.onMouseOut = onMouseOut;
    }

    /**
     * <p>Scripting code executed when the user moves the  mouse pointer into
     * the boundary of this component.</p>
     */
    @Property(name="onMouseOver", displayName="Mouse In Script", 
	category="Javascript", 
    editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseOver = null;

    /**
     * <p>Scripting code executed when the user moves the  mouse pointer into
     * the boundary of this component.</p>
     */
    public String getOnMouseOver() {
        if (this.onMouseOver != null) {
            return this.onMouseOver;
        }
        ValueExpression _vb = getValueExpression("onMouseOver");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when the user moves the  mouse pointer into
     * the boundary of this component.</p>
     * @see #getOnMouseOver()
     */
    public void setOnMouseOver(String onMouseOver) {
        this.onMouseOver = onMouseOver;
    }

    /**
     * <p>Scripting code executed when the user releases a mouse button while
     * the mouse pointer is on the component.</p>
     */
    @Property(name="onMouseUp", displayName="Mouse Up Script", 
	category="Javascript", 
    editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseUp = null;

    /**
     * <p>Scripting code executed when the user releases a mouse button while
     * the mouse pointer is on the component.</p>
     */
    public String getOnMouseUp() {
        if (this.onMouseUp != null) {
            return this.onMouseUp;
        }
        ValueExpression _vb = getValueExpression("onMouseUp");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when the user releases a mouse button while
     * the mouse pointer is on the component.</p>
     * @see #getOnMouseUp()
     */
    public void setOnMouseUp(String onMouseUp) {
        this.onMouseUp = onMouseUp;
    }

    /**
     * <p>Flag indicating that the labeled component should be marked as
     * required. It is only relevant if the labeled component is not
     * a child of the label tag. Set this flag to ensure that the 
     * required icon shows up the first time the page is rendered.</p>
     */
    @Property(name="requiredIndicator", displayName="Required Field Indicator", category="Appearance")
    private boolean requiredIndicator = false;
    private boolean requiredIndicator_set = false;

    /**
     * <p>Flag indicating that the labeled component should be marked as
     * required. It is only relevant if the labeled component is not
     * a child of the label tag. Set this flag to ensure that the 
     * required icon shows up the first time the page is rendered.</p>
     */
    public boolean isRequiredIndicator() {
        if (this.requiredIndicator_set) {
            return this.requiredIndicator;
        }
        ValueExpression _vb = getValueExpression("requiredIndicator");
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
     * <p>Flag indicating that the labeled component should be marked as
     * required. It is only relevant if the labeled component is not
     * a child of the label tag. Set this flag to ensure that the 
     * required icon shows up the first time the page is rendered.</p>
     * @see #isRequiredIndicator()
     */
    public void setRequiredIndicator(boolean requiredIndicator) {
        this.requiredIndicator = requiredIndicator;
        this.requiredIndicator_set = true;
    }

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name="style", displayName="CSS Style(s)", category="Appearance",
	editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
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
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
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
     * <p>CSS style class(es) to be applied to the outermost HTML 
     * element when this component is rendered.</p>
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * <p>The label text to be displayed for this label. This attribute
     * can be set to a literal string, to a value binding expression
     * that corresponds to a property of a managed bean, or to a value
     * binding expression that corresponds to a message from a resource
     * bundle declared using <code>f:loadBundle</code>.</p>
     */
    @Property(name="text", displayName="Label Text", category="Appearance", 
	isDefault=true, 
	editorClassName="com.sun.rave.propertyeditors.StringPropertyEditor")
    public Object getText() {
        return getValue();
    }

    /**
     * <p>The label text to be displayed for this label. This attribute
     * can be set to a literal string, to a value binding expression
     * that corresponds to a property of a managed bean, or to a value
     * binding expression that corresponds to a message from a resource
     * bundle declared using <code>f:loadBundle</code>.</p>
     * @see #getText()
     */
    public void setText(Object text) {
        setValue(text);
    }

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     */
    @Property(name="toolTip", displayName="Tool Tip", category="Behavior")
    private String toolTip = null;

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     */
    public String getToolTip() {
        if (this.toolTip != null) {
            return this.toolTip;
        }
        ValueExpression _vb = getValueExpression("toolTip");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     * @see #getToolTip()
     */
    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    @Property(name="visible", displayName="Visible", category="Behavior")
    private boolean visible = false;
    private boolean visible_set = false;

    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
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
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     * @see #isVisible()
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }

    /**
     * The component instance to be used to determine the status of the
     * required and error indicators.
     */
    @Property(name="indicatorComponent", isHidden=true, isAttribute=false)
    transient private UIComponent indicatorComponent;

    /**
     * Return the <code>indicatorComponent</code> property.
     */
    public UIComponent getIndicatorComponent() {
	return indicatorComponent;
    }

    /**
     * Return a component instance to determine the status of the
     * error and required indicators.
     *
     * If <code>indicatorComponent</code> is not null, and it is 
     * a <code>ComplexComponent</code>, call 
     * <code>getIndicatorComponent</code> and return the value, else
     * return <code>indicatorComponent</code>.
     * If <code>indicatorComponent</code> is <code>null</code>
     * call <code>getLabeledComponent</code>. If the labeled component is a
     * <code>ComplexComponent</code> call <code>getIndicatorComponent</code>
     * on it and return the value, else return the labeled component.
     * This method may return <code>null</code>.
     */
    public UIComponent getIndicatorComponent(FacesContext context,
    		boolean fallback) {
	
	if (indicatorComponent != null) {
	    if (indicatorComponent instanceof ComplexComponent) {
		return ((ComplexComponent)indicatorComponent).
			getIndicatorComponent(context, this);
	    } else {
		return indicatorComponent;
	    }
	}
	if (!fallback) {
	    return null;
	}
	UIComponent lb = getLabeledComponent(context);
	if (lb instanceof ComplexComponent) {
	    return ((ComplexComponent)lb).getIndicatorComponent(context, this);
	} else {
	    return lb;
	}
    }

    /**
     * This method is used to set a component instance to determine
     * the status of the error and required indicators. It is mainly
     * used by components that have built in labels as sub components.
     * This method must be called whenever a component wants to return
     * its built in label instance, since the underlying member that stores
     * the instance reference is transient.
     * <p>
     * The motivation for this method and member is to ensure that the
     * label has an appropriate component instance to allow a renderer
     * to discern the state of the error and required indicators. This is
     * sometimes needed because the id value of the <code>for</code>
     * property is not always guaranteed to identify a component that
     * can provide the interfaces needed by a renderer to render
     * the error and required indicators.
     * </p>
     * <p>
     * It also provides an optimization so that <code>findComponent</code>
     * does not have to be called when rendering built in labels, similar
     * to the optimization benefit of the <code>labeledComponent</code>
     * property.
     * </p>
     */
    public void setIndicatorComponent(UIComponent component) {
	indicatorComponent = component;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // State methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this._for = (String) _values[1];
        this.hideIndicators = ((Boolean) _values[2]).booleanValue();
        this.hideIndicators_set = ((Boolean) _values[3]).booleanValue();
        this.labelLevel = ((Integer) _values[4]).intValue();
        this.labelLevel_set = ((Boolean) _values[5]).booleanValue();
        this.onClick = (String) _values[6];
        this.onMouseDown = (String) _values[7];
        this.onMouseMove = (String) _values[8];
        this.onMouseOut = (String) _values[9];
        this.onMouseOver = (String) _values[10];
        this.onMouseUp = (String) _values[11];
        this.requiredIndicator = ((Boolean) _values[12]).booleanValue();
        this.requiredIndicator_set = ((Boolean) _values[13]).booleanValue();
        this.style = (String) _values[14];
        this.styleClass = (String) _values[15];
        this.toolTip = (String) _values[16];
        this.visible = ((Boolean) _values[17]).booleanValue();
        this.visible_set = ((Boolean) _values[18]).booleanValue();
        this.htmlTemplate = (String) _values[19];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[20];
        _values[0] = super.saveState(_context);
        _values[1] = this._for;
        _values[2] = this.hideIndicators ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.hideIndicators_set ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = new Integer(this.labelLevel);
        _values[5] = this.labelLevel_set ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.onClick;
        _values[7] = this.onMouseDown;
        _values[8] = this.onMouseMove;
        _values[9] = this.onMouseOut;
        _values[10] = this.onMouseOver;
        _values[11] = this.onMouseUp;
        _values[12] = this.requiredIndicator ? Boolean.TRUE : Boolean.FALSE;
        _values[13] = this.requiredIndicator_set ? Boolean.TRUE : Boolean.FALSE;
        _values[14] = this.style;
        _values[15] = this.styleClass;
        _values[16] = this.toolTip;
        _values[17] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[18] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[19] = this.htmlTemplate; 
        return _values;
    }
}
