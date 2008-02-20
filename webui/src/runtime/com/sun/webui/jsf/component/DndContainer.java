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
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponentBase;
/**
 * DndContainer represents drag, drop or drag-and-drop container.
 * This container makes any Woodstock components OR any other user elements nested in it draggable:
 * DndContainer itself becomes a drag source/target/source-and-target container, each element of which 
 * is activated for DnD ( drag and drop).
 * <ul>
 * <li>Elements within container can be selected for single and multiple selections
 * <li>Elements can be dropped within the same container ( rearranged) or into other dndContainers
 * provided that the drop containers accepts drops of this particular type.
 * <li>For the purpose of dragging all elements are identified with 
 *  <ol>
 * <li>Type - aimed to facilitate the acceptance check on the drop site. 
 *      All elements inherit the same type set  from the container ( in this case type set 
 *      is associated with the container, and propagated down to elements)
 * <li> Data - payload. The data is furnished to the drop container when new element is created there.
 * </ol>
 * <li>When dragged, an avatar ( moving image) is created.
 * <li> When dropped, the new element at the destination is created: user can use default  
 * logic on creating new nodes when element is dropped( deep node clone), or provide a user's function 
 * <code>onNodeCreateFunc</code>that takes care of creating DOM elements based on the data supplied 
 * by each element ( see payload above).
 * <li>Every time an item is dragged within, out, or dropped into the container, the container's state 
 * ( hidden field) is updated with ordered list of items' data. 
 * </ul>
 * 
 * DndContainer component represents a "render-only" component and the
 * JSF component tree will not reflect any changes performed on the client side.
 * 
 * 
 * 
 * todo rendered
 */
@Component(type="com.sun.webui.jsf.DndContainer", 
    family="com.sun.webui.jsf.DndContainer", displayName="Dnd Container", isTag=true,
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_dndContainer",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_dndContainer_props")
public class DndContainer extends UIComponentBase  {
    public static final String STATE_ID = "_state"; //NOI18N
    public static final String CONTAINER_ID = "_container"; //NOI18N
    
    private static final boolean DEBUG = false;
    
    /** Creates a new instance of DndContainer */
    public DndContainer() {
        super();
        setRendererType("com.sun.webui.jsf.DndContainer");
    }
    
    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.DndContainer";
    }

    /**
     * Log an error - only used during development time.
     */
    protected void log(String s) {
        System.out.println(this.getClass().getName() + "::" + s); //NOI18N
    }
    
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
  
        
      /**
     * <p>Flag indicating that the container items will be aligned horizontally,
     * and thereby insertion point should be displayed differently<br/>
     * Default is false, i.e. vertical allocation will be expected
     * </p>
     */
    @Property(name="horizontalIndicator", displayName="Horizontal Indicator", category="Behavior")
    private boolean horizontalIndicator = false;
    private boolean horizontalIndicator_set = false;
    
    /**
     * <p>Sets horizontalIndicator flag</p>
     */
    public boolean isHorizontalIndicator() {
        if (this.horizontalIndicator_set) {
            return this.horizontalIndicator;
        }
        ValueExpression _vb = getValueExpression("horizontalIndicator");
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
     * <p>Returns horizontalIndicator flag</p>
     * @see #isHorizontalIndicator()
     */
    public void setHorizontalIndicator(boolean horizontalIndicator) {
        this.horizontalIndicator = horizontalIndicator;
        this.horizontalIndicator_set = true;
    }
  
    
    /**
     * <p>All items within a container will advertise their types. 
     * These types are used to check whether a drop container is accepting a drop of that type(s).
     * 
     * The <i>dragTypes</i> attribute contains a comma-separated list of types that will be 
     * automatically associated with all direct children of the container. </p>
     * The default value for the attribute is null.
     */
    @Property(name="dragTypes", displayName="Drag Types", category="Appearance")
    private String dragTypes = null;
    
    /**
     * <p>Returns list of drag item types for any/all children within 
     *  this container.
     * </p>
     */
    public String getDragTypes() {
        if (this.dragTypes != null) {
            return this.dragTypes;
        }
        ValueExpression _vb = getValueExpression("dragTypes");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }
    
    /**
     * <p>Sets list of drag item types.
     * @see #getDragTypes()
     */
    public void setDragTypes(String dragTypes) {
        this.dragTypes = dragTypes;
    }
    

    
    
    /**
     * <p>The <i>dropTypes</i> attribute contains a comma-separated list of types that will be 
     * accepted for drop at this container.
     *  The list is empty by default.
     * </p>
     */
    @Property(name="dropTypes", displayName="Drop Types", category="Appearance")
    private String dropTypes = null;
    
    /**
     * <p>Returns list of drag item types for any/all children within 
     *  this container.
     * </p>
     */
    public String getDropTypes() {
        if (this.dropTypes != null) {
            return this.dropTypes;
        }
        ValueExpression _vb = getValueExpression("dropTypes");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }
    
    /**
     * <p>Sets list of drag item types.
     * @see #getDropTypes()
     */
    public void setDropTypes(String dropTypes) {
        this.dropTypes = dropTypes;
    }
    

    
    
    /**
     * <p>When item is inserted or dropped into a container, 
     * its data is used for creation of the new element within this container.
     * Default implementation creates /span/ with innerHTML={supplied data}.
     * <p>
     * onNodeCreateFunc attribute specifies optional Javascript function name that 
     * provides logic to create new drag item based on the data supplied. 
     * It must create a DOM-node that will visually represent supplied data and
     * must have signature <code>function(data, hint)</code> and return the following map:
     * <code>return {
     *      node: createdNode, 
     *      data: /data that will be associated with the drag node /,
     *      typeArray: /array of types represented by this drag node /};
     * <code>
     * <p>
     * Note that onNodeCreateFunc function is used in at least 2 cases:
     * <ol>
     * <li>creation of the new node when data is dropped into THIS container
     * <li>creation of the node within avatar ( moving draggable image) that will 
     * eventually be removed upon completion of the drag process. In this case 
     * hint=="avatar"
     * </ol>
     * 
     */
    @Property(name="onNodeCreateFunc", displayName="New Item Creator Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onNodeCreateFunc = null;
    
    /**
     *  <p>Function name to be executed when new element is created from the data.
     *  Specified function must have signature <code>function(data, hint)</code>
     *  For details, see attribute description.
     */
    public String getOnNodeCreateFunc() {
        if (this.onNodeCreateFunc != null) {
            return this.onNodeCreateFunc;
        }
        ValueExpression _vb = getValueExpression("onNodeCreateFunc");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }
    
    /**
     * <p>Scripting code executed when new element is created from the data.</p>
     * @see #getOnNodeCreateFunc()
     */
    public void setOnNodeCreateFunc(String onNodeCreateFunc) {
        this.onNodeCreateFunc = onNodeCreateFunc;
    }
    
    
 
    
    /**
     * <p>Function name to be executed after element is dropped into the container.
     *  Specified function must have signature <code>function(source, nodes, copy)</code>
     * and its return is ignored.
     * .</p>
     */
    @Property(name="onDropFunc", displayName="OnDrop Function Name", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onDropFunc = null;
    
    /**
     * <p>Function name to be executed after element is dropped into the container.
     *  Specified function must have signature <code>function(source, nodes, copy)</code>
     * .</p>
     */
    public String getOnDropFunc() {
        if (this.onDropFunc != null) {
            return this.onDropFunc;
        }
        ValueExpression _vb = getValueExpression("onDropFunc");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }
    
    /**
     * <p>Function name to be executed after element is dropped into the container.</p>
     * @see #getOnDropFunc()
     */
    public void setOnDropFunc(String onDropFunc) {
        this.onDropFunc = onDropFunc;
    }
    
       
  
    
    /**
     * <p>Flag indicating that only copies of the items will be made when item is dragged outside 
     * of this container.The default value of false will allow user to decide whether items are 
     * moved ( regular drag) or copied ( Ctrl-drag) The value of true will always copy items 
     * ( regular drag or Ctrl-drag).</p>
     */
    @Property(name="copyOnly", displayName="Copy Only Allowed", category="Behavior")
    private boolean copyOnly = false;
    private boolean copyOnly_set = false;
    
    /**
     * <p>Flag indicating that modification of this component by the
     * user is not currently permitted, but that it will be
     * included when the form is submitted.</p>
     */
    public boolean isCopyOnly() {
        if (this.copyOnly_set) {
            return this.copyOnly;
        }
        ValueExpression _vb = getValueExpression("copyOnly");
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
     * <p>Flag indicating that modification of this component by the
     * user is not currently permitted, but that it will be
     * included when the form is submitted.</p>
     * @see #isCopyOnly()
     */
    public void setCopyOnly(boolean copyOnly) {
        this.copyOnly = copyOnly;
        this.copyOnly_set = true;
    }
    
    
    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this
     * component is rendered.</p>
     */
    @Property(name="style", displayName="CSS Style(s)", category="Appearance", editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
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
     * <p>CSS style class(es) to be applied to the outermost HTML element when this
     * component is rendered.</p>
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
 
       
    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page.</p>
     */
    @Property(name="visible", displayName="Visible", category="Behavior")
    private boolean visible = false;
    private boolean visible_set = false;
    
    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page.</p>
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
     * viewable by the user in the rendered HTML page.</p>
     * @see #isVisible()
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }
    
    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.dragTypes = (String) _values[1];
        this.dropTypes = (String) _values[2];       
        this.onNodeCreateFunc = (String) _values[3];
        this.copyOnly = ((Boolean) _values[4]).booleanValue();
        this.copyOnly_set = ((Boolean) _values[5]).booleanValue();
        this.style = (String) _values[6];
        this.styleClass = (String) _values[7];
        this.visible = ((Boolean) _values[8]).booleanValue();
        this.visible_set = ((Boolean) _values[9]).booleanValue();
        this.horizontalIndicator = ((Boolean) _values[10]).booleanValue();
        this.horizontalIndicator_set = ((Boolean) _values[11]).booleanValue();
        this.onDropFunc = (String) _values[12];
    }
    
    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[13];
        _values[0] = super.saveState(_context);
        _values[1] = this.dragTypes;
        _values[2] = this.dropTypes;
        _values[3] = this.onNodeCreateFunc;
        _values[4] = this.copyOnly ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.copyOnly_set ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.style;
        _values[7] = this.styleClass;
        _values[8] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = this.horizontalIndicator ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.horizontalIndicator_set ? Boolean.TRUE : Boolean.FALSE;       
        _values[12] = this.onDropFunc;
        return _values;
    }
}
