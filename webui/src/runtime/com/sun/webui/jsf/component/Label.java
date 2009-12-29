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
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme;
import java.beans.Beans;
import java.util.Iterator;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * The Label component displays a label for a component.
 */
@Component(type = "com.sun.webui.jsf.Label", family = "com.sun.webui.jsf.Label", displayName = "Label", tagName = "label",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_label",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_label_props")
public class Label extends UIOutput implements NamingContainer {

    public static final String REQUIRED_ID = "_required";
    public static final String REQUIRED_FACET = "required";
    public static final String ERROR_ID = "_error";
    public static final String ERROR_FACET = "error";
    private EditableValueHolder labeledComponent = null;
    private String element = "span"; //NOI18N
    private static final boolean DEBUG = false;

    /**
     * Default constructor.
     */
    public Label() {
        super();
        setRendererType("com.sun.webui.jsf.Label");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.Label";
    }

    /**
     * Set the labeled component to <code>comp</code>.
     * If <code>comp</code> is null, the labeled component is set to null.</br>
     * If <code>comp is an instance of <code>EditableValueHolder</code>
     * the labeled component is set to <code>comp</code> and
     * <code>setFor</code> is called with the value of
     * <code>comp.getClientId</code>. Subsequent calls to 
     * <code>getElement</code> will return "label".</br>
     * If <code>comp</code> is not an <code>EditableValueHolder</code>
     * the labeled component is set to null, and subsequent calls to 
     * <code>getElement</code> will return "label".</br>
     *
     * @deprecated 
     * @see #setFor
     */
    public void setLabeledComponent(UIComponent comp) {

        if (DEBUG) {
            log("setLabeledComponent");
        }
        if (comp == null) {
            if (DEBUG) {
                log("component is null");
            }
            this.labeledComponent = null;
        } else if (comp instanceof EditableValueHolder) {
            if (DEBUG) {
                log("Component is EditableValueHolder");
            }
            this.labeledComponent = (EditableValueHolder) comp;
            if (!Beans.isDesignTime()) {
                this.setFor(comp.getClientId(
                        FacesContext.getCurrentInstance()));
            }
            element = "label";
        } else {
            if (DEBUG) {
                log("Component is not an EditableValueHolder");
            }
            if (LogUtil.infoEnabled(Label.class)) {
                FacesContext context = FacesContext.getCurrentInstance();

                LogUtil.info(Label.class, "Label.invalidFor",
                        new Object[]{getId(),
                            context.getViewRoot().getViewId(),
                            comp.getId()});
            }

            this.labeledComponent = null;
            element = "label";
        }
    }

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

        if (DEBUG) {
            log("getLabeledComponent for label " + String.valueOf(getText()));
        }
        if (labeledComponent != null) {
            if (DEBUG) {
                log("Found component ");
            }
            if (DEBUG) {
                log(((UIComponent) labeledComponent).getId());
            }
            return labeledComponent;
        }
        if (DEBUG) {
            log("labelled component is null, try something else");
        }
        String id = getFor();

        if (DEBUG && id != null) {
            log("\tfor attribute set to " + id);
        }

        if (id == null) {
            if (DEBUG) {
                log("\tID is not set, find children ");
            }
            setLabeledComponent(findLabeledChild());
        } else {
            if (DEBUG) {
                log("\tID found");
            }
            // If the id is an absolute path, prefix it with ":"
            // to tell findComponent to do a search from the root

            if (id.indexOf(":") > -1 && !id.startsWith(":")) {
                id = ":" + id;
            }
            // Since Label is now a NamingContainer, findComponent
            // will treat it as the closest NamingContainer. Therefore
            // obtain the parent and call findComponent on it.
            // This makes the logic similar to getLabelComponentId by
            // treating a relative path id as a sibling.
            //
            // This use of parent is different from getLabelComponentId
            // since we are not requiring that the parent be a
            // NamingContainer.
            //
            try {
                UIComponent parent = this.getParent();
                setLabeledComponent(parent.findComponent(id));
            } catch (Exception e) {
                if (DEBUG) {
                    log("\t ID is not found");
                }
            }
            element = "label";
        }
        return labeledComponent;
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
     * @deprecated
     * @see com.sun.webui.jsf.util.RenderingUtilities#getLabeledElementId
     */
    public String getLabeledComponentId(FacesContext context) {

        String id = null;

        if (labeledComponent != null) {
            if (labeledComponent instanceof ComplexComponent) {
                ComplexComponent compComp = (ComplexComponent) labeledComponent;
                id = compComp.getPrimaryElementID(context);
            } else {
                UIComponent comp = ((UIComponent) labeledComponent);
                id = comp.getClientId(context);
            }
        } else {
            id = getFor();
            // If the id is not null and is an absolute path
            // return the id, otherwise
            // assume it is a sibling if the Label has a parent.
            // and return the absolute path.
            //
            // This assumption should be stated in the doc for the
            // "for" attribute. I'm not sure what happens if the
            // relative path id is returned. Its possible that this
            // will not match any HTML element id, since most if
            // not all element id's will be absolute paths starting
            // with the form id. Wouldn't it be better to keep searching
            // for a Naming container ? For exmaple until recently
            // many of our own components could be parents but
            // were not NamingContainers.
            //
            if (id != null && id.indexOf(":") == -1) {
                UIComponent comp = this.getParent();
                if (comp instanceof NamingContainer) {
                    id = comp.getClientId(context) + ":" + id;
                }
            }
        }
        return id;
    }

    private UIComponent findLabeledChild() {

        if (DEBUG) {
            log("findLabeledChild");
        }
        List kids = getChildren();
        if (DEBUG && kids.size() == 0) {
            log("No children!");
        }
        for (int i = 0; i < kids.size(); i++) {
            Object kid = kids.get(i);
            if (kid instanceof EditableValueHolder) {
                if (DEBUG) {
                    log("Found good child " + kid.toString());
                }
                return (UIComponent) kid;
            }
        }
        if (DEBUG) {
            log("\tReturning null...");
        }
        return null;
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
        if (comp != null) {
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
     * @return - error facet or an Icon instance
     */
    public UIComponent getErrorIcon(Theme theme, FacesContext context,
            boolean valid) {

        UIComponent comp = getFacet(ERROR_FACET);
        if (comp != null) {
            return comp;
        }

        Icon icon = ThemeUtilities.getIcon(theme,
                ThemeImages.LABEL_INVALID_ICON);
        icon.setId(
                ComponentUtilities.createPrivateFacetId(this, ERROR_FACET));
        icon.setParent(this);
        icon.setBorder(0);
        //icon.setLongDesc("TODO: Invalid");

        if (valid) {
            icon.setIcon(ThemeImages.DOT);
            icon.setAlt("");
        } else if (labeledComponent != null) {
            String labeledCompID =
                    ((UIComponent) labeledComponent).getClientId(context);
            Iterator messages = context.getMessages(labeledCompID);
            FacesMessage fm = null;
            StringBuffer msgBuffer = new StringBuffer(200);
            while (messages.hasNext()) {
                fm = (FacesMessage) (messages.next());
                msgBuffer.append(fm.getDetail());
                msgBuffer.append(" "); //NOI18N
            }
            icon.setToolTip(msgBuffer.toString());
        }

        return icon;
    }

    /**
     * Return <code>span</code> if the label is not labeling another
     * component, else return <code>label</code>.
     */
    public String getElement() {
        return element;
    }

    private void log(String s) {
        System.out.println(getClass().getName() + "::" + s);
    }

    /**
     * Return the label level.
     * If the label level is less than 1 or greater than 3, 2 is returned.
     */
    // These values need to be Theme based.
    //
    public int getLabelLevel() {
        int level = _getLabelLevel();
        if (level < 1 || level > 3) {
            level = 2;
            setLabelLevel(level);
        }
        return level;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * The converter attribute is used to specify a method to translate native
     * property values to String and back for this component. The converter 
     * attribute value must be one of the following:
     * <ul>
     * <li>A JavaServer Faces EL expression that resolves to a backing bean or
     * bean property that implements the 
     * <code>javax.faces.converter.Converter</code> interface; or
     * </li><li>the ID of a registered converter (a String).</li>
     * </ul>
     */
    @Property(name = "converter")
    @Override
    public void setConverter(Converter converter) {
        super.setConverter(converter);
    }

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
     * <p>Return the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property aliases.</p>
     *
     * @param name Name of value binding expression to retrieve
     */
    @Override
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
    @Override
    public void setValueExpression(String name, ValueExpression binding) {
        if (name.equals("text")) {
            super.setValueExpression("value", binding);
            return;
        }
        super.setValueExpression(name, binding);
    }

    // Hide value
    @Property(name = "value", isHidden = true, isAttribute = false)
    @Override
    public Object getValue() {
        return super.getValue();
    }
    /**
     * <p>Use this attribute to specify the labeled component. 
     * The value of the attribute is the absolute client id of the component or
     * the id of the component to be labeled. Relative ids are no longer supported.
     * If this attribute is not specified, the <code>label</code> component tries
     *  to search its children to see whether any of them can be used for evaluating
     * the value of this "for" attribute.</p>
     */
    @Property(name = "for", displayName = "Input Component", category = "Appearance",
    editorClassName = "com.sun.webui.jsf.component.propertyeditors.InputComponentIdsEditor")
    private String _for = null;

    /**
     * <p>Use this attribute to specify the labeled component. 
     * The value of the attribute is the absolute client id of the component or
     * the id of the component to be labeled. Relative ids are no longer supported.
     * If this attribute is not specified, the <code>label</code> component tries
     *  to search its children to see whether any of them can be used for evaluating
     * the value of this "for" attribute.</p>
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
     * The value of the attribute is the absolute client id of the component or
     * the id of the component to be labeled. Relative ids are no longer supported.
     * If this attribute is not specified, the <code>label</code> component tries
     *  to search its children to see whether any of them can be used for evaluating
     * the value of this "for" attribute.</p>
     * @see #getFor()
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
    @Property(name = "hideIndicators", displayName = "Hide the Required and Invalid icons", category = "Advanced")
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
     * <p>Style level for this label, where lower values typically specify
     * progressively larger font sizes, and/or bolder font weights.
     * Valid values are 1, 2, and 3. The default label level is 2.  Any label
     * level outside this range will result in no label level being added.</p>
     */
    @Property(name = "labelLevel", displayName = "Style Level", category = "Appearance",
    editorClassName = "com.sun.webui.jsf.component.propertyeditors.LabelLevelsEditor")
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
    @Property(name = "onClick", displayName = "Click Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
     * <p>Scripting code executed when the user presses a mouse button while the
     * mouse pointer is on the component.</p>
     */
    @Property(name = "onMouseDown", displayName = "Mouse Down Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseDown = null;

    /**
     * <p>Scripting code executed when the user presses a mouse button while the
     * mouse pointer is on the component.</p>
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
     * <p>Scripting code executed when the user presses a mouse button while the
     * mouse pointer is on the component.</p>
     * @see #getOnMouseDown()
     */
    public void setOnMouseDown(String onMouseDown) {
        this.onMouseDown = onMouseDown;
    }
    /**
     * <p>Scripting code executed when the user moves the mouse pointer while
     * over the component.</p>
     */
    @Property(name = "onMouseMove", displayName = "Mouse Move Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name = "onMouseOut", displayName = "Mouse Out Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name = "onMouseOver", displayName = "Mouse In Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name = "onMouseUp", displayName = "Mouse Up Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name = "requiredIndicator", displayName = "Required Field Indicator", category = "Appearance")
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
     * <p>The label text to be displayed for this label. This attribute
     * can be set to a literal string, to a value binding expression
     * that corresponds to a property of a managed bean, or to a value
     * binding expression that corresponds to a message from a resource
     * bundle declared using <code>f:loadBundle</code>.</p>
     */
    @Property(name = "text", displayName = "Label Text", category = "Appearance", isDefault = true,
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
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
    @Property(name = "toolTip", displayName = "Tool Tip", category = "Behavior",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
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
    @Property(name = "visible", displayName = "Visible", category = "Behavior")
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
     * <p>Restore the state of this component.</p>
     */
    @Override
    public void restoreState(FacesContext _context, Object _state) {
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
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[19];
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
        return _values;
    }
}
