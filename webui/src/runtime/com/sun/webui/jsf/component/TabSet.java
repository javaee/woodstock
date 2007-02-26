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

import com.sun.faces.annotation.Property;
import com.sun.faces.annotation.Component;
import java.util.Stack;
import javax.faces.component.NamingContainer;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ValueChangeEvent;

/**
 * A set of one or more tabs. A TabSet is a naming container which should contain
 * only {@link Tab} components. The currently selected tab is specified via the
 * {@code selected} property, which may be bound to a model value. Action listeners
 * may be registered individually with a Tab; or, an action listener may be
 * registered with the containing tabSet, in which case it is notified of all tab
 * selection actions.
 *
 * <p>TabSet implements {@link javax.faces.component.EditableValueHolder}, but it
 * diverges significantly from the behavior of a typical editable value holder. Note
 * that the {@code selected} property is an alias for the {@code value} property. 
 * 
 * <ul>
 * <li><b>Converters and Validators</b>. Converters and validators are not used by
 * the tab set component. The value of a tab set is the ID of the currently selected
 * tab, set as a result of the tab selection action. The id is always a String,
 * and need not be converted. Also, since the tab always sets a correct id, the id
 * need not be validated.</li>
 * <li><b>Validation and tab navigation</b>. Tabs may contain input components. By
 * default, when a new tab is selected, the tab set checks whether all input components
 * inside the current tab are valid. If one or more is not valid, then the tab set
 * marks itself as invalid. As a result, the selected property will not be updated,
 * and the tab set will output the previously selected tab as selected. In other
 * words, by default, users cannot move from one tab to another until all they have
 * provided valid values for all the tab's input components.</li>
 * <li><b>Updating of bound value models</b>. If a tab set is valid, and the value of
 * the selected property is changed, a model bound to the value will be updated.</li>
 * <li><b>Immediate tab sets</b>. If a tab set is made immediate, tab nagivation will
 * be allowed to occur even if there are invalid input components inside the
 * current tab. However, in this case, the model value will <emph>not</emph> be
 * updated. This generally works best if the tabs themselves are also made immediate,
 * and if some other command component is used to actually submit the page for
 * value processing.</li>
 * </ul>
 */
@Component(type="com.sun.webui.jsf.TabSet", family="com.sun.webui.jsf.TabSet", displayName="Tab Set", tagName="tabSet",
helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_tab_set",
        propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_tab_set_props")
        public class TabSet extends WebuiInput implements NamingContainer {
    
    /**
     * Create a new TabSet.
     */
    public TabSet() {
        super();
        setRendererType("com.sun.webui.jsf.TabSet");
    }
    
    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.TabSet";
    }
    
    @Override
    public ValueExpression getValueExpression(String name) {
        if (name.equals("selected")) {
            return super.getValueExpression("value");
        }
        return super.getValueExpression(name);
    }
    
    @Override
    public void setValueExpression(String name,ValueExpression binding) {
        if (name.equals("selected")) {
            super.setValueExpression("value", binding);
            return;
        }
        super.setValueExpression(name, binding);
    }
    
    @Property(isHidden=true, isAttribute=false)
    @Override
    public Converter getConverter() {
        return super.getConverter();
    }
    
    @Property(isHidden=true, isAttribute=false)
    @Override
    public boolean isRequired() {
        return super.isRequired();
    }
    
    @Property(isHidden=true, isAttribute=false)
    @Override
    public MethodExpression getValidatorExpression() {
        return super.getValidatorExpression();
    }

    /**
     * The current value of this component.
     */
    @Property(isHidden=true, isAttribute=true)
    @Override
    public Object getValue() {
        return super.getValue();
    }
    
    /**
     * Set the method expression that identifies a method that handles
     * the action event fired when one of this tab set's tabs is used to submit
     * the page. The signature of the bound method must correspond to {@link
     * javax.faces.event.ActionListenerExpression#processAction}. The class that
     * defines the method must implement the <code>java.io.Serializable</code>
     * interface or <code>javax.faces.component.StateHolder</code> interface.
     */
    @Property(name="actionListenerExpression", displayName="Action Listener Expression", category="Advanced")
    @Property.Method(signature="void processAction(javax.faces.event.ActionEvent)")
    private MethodExpression actionListenerExpression;
    
    /**
     * Get the method expression that identifies a method that handles
     * the action event fired when one of this tab set's tabs is used to submit
     * the page.
     */
    public MethodExpression getActionListenerExpression() {
        return this.actionListenerExpression;
    }
    
    /**
     * Set the method expression that identifies a method that handles
     * the action event fired when one of this tab set's tabs is used to submit
     * the page. The signature of the bound method must correspond to {@link
     * javax.faces.event.MethodExpressionActionListener#processAction}. The class that
     * defines the method must implement the <code>java.io.Serializable</code>
     * interface or <code>javax.faces.component.StateHolder</code> interface.
     */
    public void setActionListenerExpression(MethodExpression actionListenerExpression) {
        this.actionListenerExpression = actionListenerExpression;
    }
    
    /**
     * Returns true if the tabs in this tab set should remember
     * which of their tab children was last selected. This enables the user to
     * choose other tabs in the set, and have the child tab selection in the
     * original tab be retained when the user returns to the original tab.
     */
    @Property(name="lastSelectedChildSaved", displayName="Last Selected Child Saved", isHidden=true)
    private boolean lastSelectedChildSaved = true;
    private boolean lastSelectedChildSaved_set = true;
    
    /**
     * Returns true if the tabs in this tab set should remember
     * which of their tab children was last selected. This enables the user to
     * choose other tabs in the set, and have the child tab selection in the
     * original tab be retained when the user returns to the original tab.
     */
    public boolean isLastSelectedChildSaved() {
        if (this.lastSelectedChildSaved_set) {
            return this.lastSelectedChildSaved;
        }
        ValueExpression _vb = getValueExpression("lastSelectedChildSaved");
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
     * Set to true if the tabs in this tab set should remember
     * which of their tab children was last selected. This enables the user to
     * choose other tabs in the set, and have the child tab selection in the
     * original tab be retained when the user returns to the original tab.
     */
    public void setLastSelectedChildSaved(boolean lastSelectedChildSaved) {
        this.lastSelectedChildSaved = lastSelectedChildSaved;
        this.lastSelectedChildSaved_set = true;
    }
    
    /**
     * Returns true if the tabs should render in a visually lighter style, with reduced
     * shading and bolding. This attribute can only be used with mini tabs, so
     * you must also set the mini attribute to true to render lightweight tabs.
     */
    @Property(name="lite", displayName="Lightweight Tab Set", category="Appearance")
    private boolean lite = false;
    private boolean lite_set = false;
    
    /**
     * Returns true if the tabs should render in a visually lighter style, with reduced
     * shading and bolding. This attribute can only be used with mini tabs, so
     * you must also set the mini attribute to true to render lightweight tabs.
     */
    public boolean isLite() {
        if (this.lite_set) {
            return this.lite;
        }
        ValueExpression _vb = getValueExpression("lite");
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
     * Set to true to render the tabs in a visually lighter style, with reduced
     * shading and bolding. This attribute can only be used with mini tabs, so
     * you must also set the mini attribute to true to render lightweight tabs.
     */
    public void setLite(boolean lite) {
        this.lite = lite;
        this.lite_set = true;
    }
    
    /**
     * Set this attribute to true in a first level tab set, to create tabs that
     * have the smaller "mini" tab style. Note that mini tab sets will not display
     * properly if more than one level of tabs are specified.
     */
    @Property(name="mini", displayName="Mini", category="Appearance")
    private boolean mini = false;
    private boolean mini_set = false;
    
    /**
     * Set this attribute to true in a first level tab set, to create tabs that
     * have the smaller "mini" tab style. Note that mini tab sets will not display
     * properly if more than one level of tabs are specified.
     */
    public boolean isMini() {
        if (this.mini_set) {
            return this.mini;
        }
        ValueExpression _vb = getValueExpression("mini");
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
     * Set this attribute to true in a first level tab set, to create tabs that
     * have the smaller "mini" tab style. Note that mini tab sets will not display
     * properly if more than one level of tabs are specified.
     */
    public void setMini(boolean mini) {
        this.mini = mini;
        this.mini_set = true;
    }
    
    /**
     * The id of the selected tab.
     */
    @Property(name="selected", displayName="Selected", category="Data")
    
    /**
     * The id of the selected tab.
     */
    public String getSelected() {
        return (String) getValue();
    }
    
    /**
     * The id of the selected tab.
     * @see #getSelected()
     */
    public void setSelected(String selected) {
        setValue((Object) selected);
    }
    
    /**
     * Returns this tab set's tab descendant with id equal to the value of
     * the selected property. If the value of the selected property is null,
     * returns the first tab child. If there are no tab children, returns null.
     */
    public Tab getSelectedTab() {
        if (this.getChildCount() == 0)
            return null;
        Stack<Tab> tabStack = new Stack<Tab>();
        for (UIComponent child : this.getChildren()) {
            if (Tab.class.isAssignableFrom(child.getClass())) {
                tabStack.push((Tab) child);
            }
        }
        String id = this.getSelected();
        if (id == null) {
            if (tabStack.size() == 0) {
                return null;
            } else {
                return tabStack.get(0);
            }
        }
        Tab selectedTab = null;
        while (selectedTab == null && !tabStack.isEmpty()) {
            Tab tab = tabStack.pop();
            if (id.equals(tab.getId())) {
                selectedTab = tab;
            } else if (tab.getTabChildCount() > 0) {
                tabStack.addAll(tab.getTabChildren());
            }
        }
        return selectedTab;
    }
    
    /**
     * CSS style(s) to be applied to the outermost HTML element when this
     * component is rendered.
     */
    @Property(name="style", displayName="CSS Style(s)", category="Appearance", editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;
    
    /**
     * CSS style(s) to be applied to the outermost HTML element when this
     * component is rendered.
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
     * CSS style(s) to be applied to the outermost HTML element when this
     * component is rendered.
     * @see #getStyle()
     */
    public void setStyle(String style) {
        this.style = style;
    }
    
    /**
     * CSS style class(es) to be applied to the outermost HTML element when this
     * component is rendered.
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", 
        editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;
    
    /**
     * CSS style class(es) to be applied to the outermost HTML element when this
     * component is rendered.
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
     * CSS style class(es) to be applied to the outermost HTML element when this
     * component is rendered.
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    
    /**
     * Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.
     */
    @Property(name="visible", displayName="Visible", category="Behavior")
    private boolean visible = false;
    private boolean visible_set = false;
    
    /**
     * Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.
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
     * Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.
     * @see #isVisible()
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }
    
    /**
     * Marks this tab set as valid only if all input component children of the
     * currently selected tab are valid.
     */
    public void validate(FacesContext context) {
        if (!this.isRendered())
            return;
        this.setValid(true);
        if (!this.isImmediate()) {
            Tab selectedTab = this.getSelectedTab();
            if (selectedTab != null && selectedTab.getChildCount() > 0) {
                Stack<UIComponent> componentStack = new Stack<UIComponent>();
                componentStack.addAll(selectedTab.getChildren());
                while (this.isValid() && !componentStack.isEmpty()) {
                    UIComponent component = componentStack.pop();
                    if (component instanceof EditableValueHolder && !((EditableValueHolder) component).isValid()) {
                        this.setValid(false);
                    }
                    if (component.getChildCount() > 0) {
                        componentStack.addAll(component.getChildren());
                    }
                }
            }
        }
        if (this.isValid()) {
            Object submittedValue = getSubmittedValue();
            // If a child tab was used to submit the page, then the tab will have set
            // a non-null submitted value. Otherwise, if the submitted value is null,
            // it means a command elsewhere on the page sumitted the tab set. In this
            // case, leave the tab set's current value unchanged.
            if (submittedValue != null) {
                Object previousValue = getValue();
                setValue(submittedValue);
                setSubmittedValue(null);
                if (compareValues(previousValue, submittedValue)) {
                    queueEvent(new ValueChangeEvent(this, previousValue, submittedValue));
                }
            }
        }
    }
    
    /**
     * Restore the state of this component.
     */
    @Override
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.actionListenerExpression = (javax.el.MethodExpression) _values[1];
        this.lastSelectedChildSaved = ((Boolean) _values[2]).booleanValue();
        this.lastSelectedChildSaved_set = ((Boolean) _values[3]).booleanValue();
        this.lite = ((Boolean) _values[4]).booleanValue();
        this.lite_set = ((Boolean) _values[5]).booleanValue();
        this.mini = ((Boolean) _values[6]).booleanValue();
        this.mini_set = ((Boolean) _values[7]).booleanValue();
        this.style = (String) _values[8];
        this.styleClass = (String) _values[9];
        this.visible = ((Boolean) _values[10]).booleanValue();
        this.visible_set = ((Boolean) _values[11]).booleanValue();
    }
    
    /**
     * Save the state of this component.
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[12];
        _values[0] = super.saveState(_context);
        _values[1] = this.actionListenerExpression;
        _values[2] = this.lastSelectedChildSaved ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.lastSelectedChildSaved_set ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.lite ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.lite_set ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.mini ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.mini_set ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.style;
        _values[9] = this.styleClass;
        _values[10] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
    
    /**
     * Returns the tab with the id specified that is a child of this tabSet. If
     * no such descendant tab exists, returns null. If this tabSet contains more
     * than one tab with the same id, the tab returned will be the first encountered
     * in document order.
     */
    public Tab findChildTab(String tabId) {
        if (tabId == null) {
            return null;
        }
        for (UIComponent child : this.getChildren()) {
            Tab tab = TabSet.findChildTab((Tab) child, tabId);
            if (tab != null) {
                return tab;
            }
        }
        return null;
    }
    
    /**
     * Returns the tab with the id specified that is a child of the tab specified. If
     * no such descendant tab exists, returns null. If the tab specified contains more
     * than one tab with the same id, the tab returned will be the first encountered
     * in document order.
     */
    public static Tab findChildTab(Tab tab, String tabId) {
        if (tab == null || tabId == null) {
            return null;
        }
        if (tabId.equals(tab.getId())) {
            return tab;
        }
        if (tab.getTabChildCount() == 0) {
            return null;
        }
        for (Tab child : tab.getTabChildren()) {
            Tab foundTab = TabSet.findChildTab(child, tabId);
            if (foundTab != null) {
                return foundTab;
            }
        }
        return null;
    }
}
