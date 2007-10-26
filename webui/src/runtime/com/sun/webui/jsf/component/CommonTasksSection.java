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

import java.util.List;
import java.util.Iterator;
import java.beans.Beans;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase; /* For javadoc */
import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.ComponentUtilities;

import com.sun.webui.jsf.component.CommonTasksGroup;
import com.sun.webui.jsf.component.CommonTask;
import javax.el.ValueExpression;

/**
 * The CommonTasksSection component is used to present a number
 * of tasks that might commonly be performed by the user.
 */
@Component(
        type="com.sun.webui.jsf.CommonTasksSection",
        family="com.sun.webui.jsf.CommonTasksSection",
        displayName="Common Tasks Section",
        instanceName="commonTasksSection",
        tagName="commonTasksSection",
        helpKey="projrave_ui_elements_palette_wdstk-jsf1_2_common_tasks_section",
        propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1_2_propsheets_common_tasks_section_props")
public class CommonTasksSection extends javax.faces.component.UIComponentBase implements 
        NamingContainer{
    
    /**
     *Inline help text facet
     */
    public static final String HELP_INLINE_FACET = "help";
    
    public static final String SECTION_HELP = "commonTasks.sectionHelp";

    private UIComponent component;
 
    /** Creates a new instance of CommonTasksSection */
    public CommonTasksSection() {
        super();
        setRendererType("com.sun.webui.jsf.CommonTasksSection");
       
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.CommonTasksSection";
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
     * <p>The help text to be displayed for the common tasks section.</p>
     */
    @Property(name="helpText", displayName="Inline help to be displayed", category="Appearance")
    private String helpText = null;

    /**
     * <p>The help text to be displayed for the common tasks section.</p>
     */
    public String getHelpText() {
        if (this.helpText != null) {
            return this.helpText;
        }
        ValueExpression _vb = getValueExpression("helpText");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The help text to be displayed for the common tasks section.</p>
     * @see #getHelpText()
     */
    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    /**
     * <p>CSS style or styles  that are applied to the outermost HTML element when the commontaskssection
     * component is rendered.</p>
     */
    @Property(name="style", displayName="CSS Style(s)", category="Appearance", editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;

    /**
     * <p>CSS style or styles  that are applied to the outermost HTML element when the commontaskssection
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
     * <p>CSS style or styles  that are applied to the outermost HTML element when the commontaskssection
     * component is rendered.</p>
     * @see #getStyle()
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * <p>CSS style class or classes that are applied to the outermost HTML element when the commontaskssection 
     * component is rendered.</p>
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;

    /**
     * <p>CSS style class or classes that are applied to the outermost HTML element when the commontaskssection 
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
     * <p>CSS style class or classes that are applied to the outermost HTML element when the commontaskssection 
     * component is rendered.</p>
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * <p>The title text to be displayed for the common tasks section.</p>
     */
    @Property(name="title", displayName="Common Tasks section Text", category="Appearance", isDefault=true)
    private String title = null;

    /**
     * <p>The title text to be displayed for the common tasks section.</p>
     */
    public String getTitle() {
        if (this.title != null) {
            return this.title;
        }
        ValueExpression _vb = getValueExpression("title");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The title text to be displayed for the common tasks section.</p>
     * @see #getTitle()
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * <p>Indicates whether the component should be
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
     * <p>Indicates whether the component should be
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
     * <p>Specifies the number of task columns to display in the common tasks section.
     * </p>
     */
    @Property(name="columns", displayName="columns" , category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int columns = Integer.MIN_VALUE;
    private boolean columns_set = false;

    /**
     * <p>Specifies the number of task columns to display in the common tasks section.
     * </p>
     */
    public int getColumns() {
        if (this.columns_set) {
            return this.columns;
        }
        ValueExpression _vb = getValueExpression("columns");
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
     * <p>Specifies the number of task columns to display in the common tasks section. </p>
     * @see #getColumns()
     */
    public void setColumns(int columns) {
        this.columns = columns;
        this.columns_set = true;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.helpText = (String) _values[1];
        this.style = (String) _values[2];
        this.styleClass = (String) _values[3];
        this.title = (String) _values[4];
        this.visible = ((Boolean) _values[5]).booleanValue();
        this.visible_set = ((Boolean) _values[6]).booleanValue();
        this.columns = ((Integer) _values[7]).intValue();
        this.columns_set = ((Boolean) _values[8]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[9];
        _values[0] = super.saveState(_context);
        _values[1] = this.helpText;
        _values[2] = this.style;
        _values[3] = this.styleClass;
        _values[4] = this.title;
        _values[5] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.columns;
        _values[8] = this.columns_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
    
    /**
     * Return the total number of {@link com.sun.webui.jsf.component.CommonTask}s that are present in a
     * {@link CommonTasksSection}</br>
     * This gives the number of visible {@link com.sun.webui.jsf.component.CommonTask} elements and does not take into </br>
     * account the ones that have rendered or visible attribute set to false.
     *
     *@return - number of visible commonTask elements on the page
     */
    public int getCommonTaskCount() {
         int totalCount = 0;
         int tmp = 0;
         CommonTasksGroup ctg;
         List children = this.getChildren();
        
         if (children.size() > 0) {
            for (int i=0; i<children.size(); i++) {
                if (children.get(i) instanceof CommonTasksGroup) {
                    ctg = (CommonTasksGroup)children.get(i);
                    
                    // Get the number of commonTask elements for each commonTasksGroup
                    // that are to be rendered and are visible.
                    tmp = getSingleGroupTaskCount(ctg);
                    if (ctg.isRendered() && ctg.isVisible()) {
                       totalCount +=tmp;
                    } 
                } else {
                    // If some other component is put as a child of the 
                    // commonTasksSection, we just take it as a single component
                    // and increment the total count by one.
                    totalCount++;
                }
            }
        }

        return totalCount;
    }

    /**
     * Get the help facet for the {@link CommonTasksSection}. If a</br>
     * developer specified facet exists, use it or otherwise</br>
     * use the default facet.
     *
     * @param context The faces context.
     * @return An help component to be displayed below the title.
     */
    public UIComponent getHelp(FacesContext context) {
         component = this.getFacet(HELP_INLINE_FACET);
         
         if (component != null) {
             return component;
         }
         
         Theme theme = ThemeUtilities.getTheme(context);
         HelpInline hil;
         UIComponent component = ComponentUtilities.getPrivateFacet(this, 
                 HELP_INLINE_FACET, true);
         
         if (component == null) {
             hil = new HelpInline();
             hil.setType("page");
             hil.setId(ComponentUtilities.createPrivateFacetId(this,
		HELP_INLINE_FACET));
             ComponentUtilities.putPrivateFacet(this, HELP_INLINE_FACET, hil);
             component = hil;
         }
         
         try {
             hil = (HelpInline)component;
             if (getHelpText() == null) {
                hil.setText(theme.getMessage(SECTION_HELP));
             } else { 
                 hil.setText(getHelpText());
             }
         } catch (ClassCastException e) {
             
         }        
         return component;
    }
    
    
    /**
     * Returns the number of {@link commonTask} components contained in a {@link commonTasksGroup}. </br>
     * Check whether atleast one of the {@link commonTask} for a particular {@link commonTasksGroup} </br>
     * is to be rendered. Otherwise set the rendered attribute of that particular </br>
     * {@link commonTasksGroup} to false.
     * 
     * @param - The commonTasksGroup for which the number of commonTasks should be calculated
     * @return - The number of visible commonTask components. 
     */
    
    private int getSingleGroupTaskCount(CommonTasksGroup group) {
       CommonTask task;
       int count = 0;
       boolean flag = false;
        Iterator it = group.getChildren().iterator();
        while (it.hasNext()) {
            component = (UIComponent)it.next();
            if (component.isRendered()) {
                count++;
                flag = true;
            }
            
            if (component instanceof CommonTask) {
                if(((CommonTask)component).isVisible()) {
                    flag = true;
                }
                
            }
            
        }
        
        if (!Beans.isDesignTime()) {
            if (!flag) {
                group.setRendered(false);
            }
        }
        return count;
    }
}
