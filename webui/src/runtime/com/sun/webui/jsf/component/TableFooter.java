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

import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.ThemeUtilities;

import java.io.IOException;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.NamingContainer;

/**
 * Component that represents a table footer.
 * <p>
 * Note: Column headers and footers are rendered by TableRowGroupRenderer. Table
 * column footers are rendered by TableRenderer.
 * </p><p>
 * Note: To see the messages logged by this class, set the following global
 * defaults in your JDK's "jre/lib/logging.properties" file.
 * </p><p><pre>
 * java.util.logging.ConsoleHandler.level = FINE
 * com.sun.webui.jsf.component.TableFooter.level = FINE
 * </pre></p>
 */
@Component(type="com.sun.webui.jsf.TableFooter",
    family="com.sun.webui.jsf.TableFooter", displayName="Footer", isTag=false)
public class TableFooter extends UIComponentBase implements NamingContainer {
    // The Table ancestor enclosing this component.
    private Table table = null;

    // The TableColumn ancestor enclosing this component.
    private TableColumn tableColumn = null;

    // The TableRowGroup ancestor enclosing this component.
    private TableRowGroup tableRowGroup = null;

    // Sort level for this component.
    private int sortLevel = -1;

    /** Default constructor */
    public TableFooter() {
        super();
        setRendererType("com.sun.webui.jsf.TableFooter");
    }

    /**
     * Return the family for this component.
     */
    public String getFamily() {
        return "com.sun.webui.jsf.TableFooter";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Child methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to get sort level for this component.
     *
     * @return The sort level or 0 if sort does not apply.
     */
    public int getSortLevel() {
        if (sortLevel == -1) {
            TableColumn col = getTableColumnAncestor();
            TableRowGroup group = getTableRowGroupAncestor();
            if (col != null && group != null) {
                sortLevel = group.getSortLevel(col.getSortCriteria());
            } else {
                log("getSortLevel", //NOI18N
                    "Cannot obtain sort level, TableColumn or TableRowGroup is null"); //NOI18N
            }
        }
        return sortLevel;
    }

    /**
     * Get the closest Table ancestor that encloses this component.
     *
     * @return The Table ancestor.
     */
    public Table getTableAncestor() {
        if (table == null) {
            UIComponent component = this;
            while (component != null) {
                component = component.getParent();
                if (component instanceof Table) {
                    table = (Table) component;
                    break;
                }
            }
        }
        return table;
    }

    /**
     * Get the closest TableColumn ancestor that encloses this component.
     *
     * @return The TableColumn ancestor.
     */
    public TableColumn getTableColumnAncestor() {
        if (tableColumn == null) {
            UIComponent component = this;
            while (component != null) {
                component = component.getParent();
                if (component instanceof TableColumn) {
                    tableColumn = (TableColumn) component;
                    break;
                }
            }
        }
        return tableColumn;
    }

    /**
     * Get the closest TableRowGroup ancestor that encloses this component.
     *
     * @return The TableRowGroup ancestor.
     */
    public TableRowGroup getTableRowGroupAncestor() {
        if (tableRowGroup == null) {
            UIComponent component = this;
            while (component != null) {
                component = component.getParent();
                if (component instanceof TableRowGroup) {
                    tableRowGroup = (TableRowGroup) component;
                    break;
                }
            }
        }
        return tableRowGroup;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // UIComponent methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * If the rendered property is true, render the begining of the current
     * state of this UIComponent to the response contained in the specified
     * FacesContext.
     *
     * If a Renderer is associated with this UIComponent, the actual encoding 
     * will be delegated to Renderer.encodeBegin(FacesContext, UIComponent).
     *
     * @param context FacesContext for the current request.
     *
     * @exception IOException if an input/output error occurs while rendering.
     * @exception NullPointerException if FacesContext is null.
     */
    public void encodeBegin(FacesContext context) throws IOException {
        // Clear cached variables -- bugtraq #6300020.
        table = null;
        tableColumn = null;
        tableRowGroup = null;
        sortLevel = -1;
        super.encodeBegin(context);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * ABBR gives an abbreviated version of the cell's content. This allows
     * visual browsers to use the short form if space is limited, and
     * non-visual browsers can give a cell's header information in an
     * abbreviated form before rendering each cell.
     */
    @Property(name="abbr", displayName="Abbreviation for Header Cell")
    private String abbr = null;

    /**
     * ABBR gives an abbreviated version of the cell's content. This allows
     * visual browsers to use the short form if space is limited, and
     * non-visual browsers can give a cell's header information in an
     * abbreviated form before rendering each cell.
     */
    public String getAbbr() {
        if (this.abbr != null) {
            return this.abbr;
        }
        ValueExpression _vb = getValueExpression("abbr");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * ABBR gives an abbreviated version of the cell's content. This allows
     * visual browsers to use the short form if space is limited, and
     * non-visual browsers can give a cell's header information in an
     * abbreviated form before rendering each cell.
     */
    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    /**
     * Use the <code>align</code> attribute to specify the horizontal alignment for 
     * the content of each cell in the column. Valid values are <code>left</code>, 
     * <code>center</code>, <code>right</code>, <code>justify</code>, and 
     * <code>char</code>. The default alignment is <code>left</code>. Setting the 
     * <code>align</code> attribute to <code>char</code> causes the cell's contents 
     * to be aligned on the character that you specify with the <code>char</code> 
     * attribute. For example, to align cell contents on colons, set 
     * <code>align="char"</code> and <code>char=":" </code>Some browsers do not 
     * support aligning on the character.
     */
    @Property(name="align", displayName="Horizontal Alignment")
    private String align = null;

    /**
     * Use the <code>align</code> attribute to specify the horizontal alignment for 
     * the content of each cell in the column. Valid values are <code>left</code>, 
     * <code>center</code>, <code>right</code>, <code>justify</code>, and 
     * <code>char</code>. The default alignment is <code>left</code>. Setting the 
     * <code>align</code> attribute to <code>char</code> causes the cell's contents 
     * to be aligned on the character that you specify with the <code>char</code> 
     * attribute. For example, to align cell contents on colons, set 
     * <code>align="char"</code> and <code>char=":" </code>Some browsers do not 
     * support aligning on the character.
     */
    public String getAlign() {
        if (this.align != null) {
            return this.align;
        }
        ValueExpression _vb = getValueExpression("align");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Use the <code>align</code> attribute to specify the horizontal alignment for 
     * the content of each cell in the column. Valid values are <code>left</code>, 
     * <code>center</code>, <code>right</code>, <code>justify</code>, and 
     * <code>char</code>. The default alignment is <code>left</code>. Setting the 
     * <code>align</code> attribute to <code>char</code> causes the cell's contents 
     * to be aligned on the character that you specify with the <code>char</code> 
     * attribute. For example, to align cell contents on colons, set 
     * <code>align="char"</code> and <code>char=":" </code>Some browsers do not 
     * support aligning on the character.
     */
    public void setAlign(String align) {
        this.align = align;
    }

    /**
     * The AXIS attribute provides a method of categorizing cells. The
     * attribute's value is a comma-separated list of category names. See the
     * HTML 4.0 Recommendation's section on categorizing cells for an
     * application of AXIS.
     */
    @Property(name="axis", displayName="Category of Header Cell")
    private String axis = null;

    /**
     * The AXIS attribute provides a method of categorizing cells. The
     * attribute's value is a comma-separated list of category names. See the
     * HTML 4.0 Recommendation's section on categorizing cells for an
     * application of AXIS.
     */
    public String getAxis() {
        if (this.axis != null) {
            return this.axis;
        }
        ValueExpression _vb = getValueExpression("axis");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The AXIS attribute provides a method of categorizing cells. The
     * attribute's value is a comma-separated list of category names. See the
     * HTML 4.0 Recommendation's section on categorizing cells for an
     * application of AXIS.
     */
    public void setAxis(String axis) {
        this.axis = axis;
    }

    /**
     * The BGCOLOR attribute suggests a background color for the cell. The
     * combination of this attribute with <FONT COLOR=...> can leave
     * invisible or unreadable text on Netscape Navigator 2.x, which does not
     * support BGCOLOR on table elements. BGCOLOR is dangerous even on
     * supporting browsers, since most fail to override it when overriding
     * other author-specified colors. Style sheets provide a safer, more
     * flexible method of specifying a table's background color. This
     * attribute is deprecated (in HTML 4.0) in favor of style sheets.
     */
    @Property(name="bgColor", displayName="Cell Background Color")
    private String bgColor = null;

    /**
     * The BGCOLOR attribute suggests a background color for the cell. The
     * combination of this attribute with <FONT COLOR=...> can leave
     * invisible or unreadable text on Netscape Navigator 2.x, which does not
     * support BGCOLOR on table elements. BGCOLOR is dangerous even on
     * supporting browsers, since most fail to override it when overriding
     * other author-specified colors. Style sheets provide a safer, more
     * flexible method of specifying a table's background color. This
     * attribute is deprecated (in HTML 4.0) in favor of style sheets.
     */
    public String getBgColor() {
        if (this.bgColor != null) {
            return this.bgColor;
        }
        ValueExpression _vb = getValueExpression("bgColor");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The BGCOLOR attribute suggests a background color for the cell. The
     * combination of this attribute with <FONT COLOR=...> can leave
     * invisible or unreadable text on Netscape Navigator 2.x, which does not
     * support BGCOLOR on table elements. BGCOLOR is dangerous even on
     * supporting browsers, since most fail to override it when overriding
     * other author-specified colors. Style sheets provide a safer, more
     * flexible method of specifying a table's background color. This
     * attribute is deprecated (in HTML 4.0) in favor of style sheets.
     */
    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    /**
     * Use the <code>char </code>attribute to specify a character to use for 
     * horizontal alignment in each cell in the row. You must also set the 
     * <code>align</code> attribute to <code>char</code> to enable character alignment 
     * to be used. The default value for the <code>char</code> attribute is the 
     * decimal point of the current language, such as a period in English. The 
     * <code>char</code> HTML property is not supported by all browsers.
     */
    @Property(name="char", displayName="Alignment Character")
    private String _char = null;

    /**
     * Use the <code>char </code>attribute to specify a character to use for 
     * horizontal alignment in each cell in the row. You must also set the 
     * <code>align</code> attribute to <code>char</code> to enable character alignment 
     * to be used. The default value for the <code>char</code> attribute is the 
     * decimal point of the current language, such as a period in English. The 
     * <code>char</code> HTML property is not supported by all browsers.
     */
    public String getChar() {
        if (this._char != null) {
            return this._char;
        }
        ValueExpression _vb = getValueExpression("char");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Use the <code>char </code>attribute to specify a character to use for 
     * horizontal alignment in each cell in the row. You must also set the 
     * <code>align</code> attribute to <code>char</code> to enable character alignment 
     * to be used. The default value for the <code>char</code> attribute is the 
     * decimal point of the current language, such as a period in English. The 
     * <code>char</code> HTML property is not supported by all browsers.
     */
    public void setChar(String _char) {
        this._char = _char;
    }

    /**
     * Use the <code>charOff </code>attribute to specify the offset of the first 
     * occurrence of the alignment character that is specified with the 
     * <code>char</code> attribute. The offset is the distance from the left cell 
     * border, in locales that read from left to right. The <code>charOff</code> 
     * attribute's value can be a number of pixels or a percentage of the cell's 
     * width. For example, <code>charOff="50%"</code> centers the alignment character 
     * horizontally in a cell. If <code>charOff="25%"</code>, the first instance 
     * of the alignment character is placed at one fourth of the width of the cell.
     */
    @Property(name="charOff", displayName="Alignment Character Offset")
    private String charOff = null;

    /**
     * Use the <code>charOff </code>attribute to specify the offset of the first 
     * occurrence of the alignment character that is specified with the 
     * <code>char</code> attribute. The offset is the distance from the left cell 
     * border, in locales that read from left to right. The <code>charOff</code> 
     * attribute's value can be a number of pixels or a percentage of the cell's 
     * width. For example, <code>charOff="50%"</code> centers the alignment character 
     * horizontally in a cell. If <code>charOff="25%"</code>, the first instance 
     * of the alignment character is placed at one fourth of the width of the cell.
     */
    public String getCharOff() {
        if (this.charOff != null) {
            return this.charOff;
        }
        ValueExpression _vb = getValueExpression("charOff");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Use the <code>charOff </code>attribute to specify the offset of the first 
     * occurrence of the alignment character that is specified with the 
     * <code>char</code> attribute. The offset is the distance from the left cell 
     * border, in locales that read from left to right. The <code>charOff</code> 
     * attribute's value can be a number of pixels or a percentage of the cell's 
     * width. For example, <code>charOff="50%"</code> centers the alignment character 
     * horizontally in a cell. If <code>charOff="25%"</code>, the first instance 
     * of the alignment character is placed at one fourth of the width of the cell.
     */
    public void setCharOff(String charOff) {
        this.charOff = charOff;
    }

    /**
     * The COLSPAN attribute of TD specifies the number of columns that are
     * spanned by the cell. The default value is 1. The special value 0
     * indicates that the cell spans all columns to the end of the table. The
     * value 0 is ignored by most browsers, so authors may wish to calculate
     * the exact number of rows or columns spanned and use that value.
     */
    @Property(name="colSpan", displayName="Columns Spanned By the Cell")
    private int colSpan = Integer.MIN_VALUE;
    private boolean colSpan_set = false;

    /**
     * The COLSPAN attribute of TD specifies the number of columns that are
     * spanned by the cell. The default value is 1. The special value 0
     * indicates that the cell spans all columns to the end of the table. The
     * value 0 is ignored by most browsers, so authors may wish to calculate
     * the exact number of rows or columns spanned and use that value.
     */
    public int getColSpan() {
        if (this.colSpan_set) {
            return this.colSpan;
        }
        ValueExpression _vb = getValueExpression("colSpan");
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
     * The COLSPAN attribute of TD specifies the number of columns that are
     * spanned by the cell. The default value is 1. The special value 0
     * indicates that the cell spans all columns to the end of the table. The
     * value 0 is ignored by most browsers, so authors may wish to calculate
     * the exact number of rows or columns spanned and use that value.
     */
    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
        this.colSpan_set = true;
    }

    /**
     * Extra HTML to be appended to the tag output by this renderer.
     */
    @Property(name="extraHtml", displayName="Extra HTML")
    private String extraHtml = null;

    /**
     * Extra HTML to be appended to the tag output by this renderer.
     */
    public String getExtraHtml() {
        if (this.extraHtml != null) {
            return this.extraHtml;
        }
        ValueExpression _vb = getValueExpression("extraHtml");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Extra HTML to be appended to the tag output by this renderer.
     */
    public void setExtraHtml(String extraHtml) {
        this.extraHtml = extraHtml;
    }

    /**
     * Flag indicating this component should render a group footer. The default renders
     * a column footer. This should not be used if tableColumnFooter or tableFooter are
     * used.
     */
    @Property(name="groupFooter", displayName="Is Group Footer", isAttribute=false)
    private boolean groupFooter = false;
    private boolean groupFooter_set = false;

    /**
     * Flag indicating this component should render a group footer. The default renders
     * a column footer. This should not be used if tableColumnFooter or tableFooter are
     * used.
     */
    public boolean isGroupFooter() {
        if (this.groupFooter_set) {
            return this.groupFooter;
        }
        ValueExpression _vb = getValueExpression("groupFooter");
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
     * Flag indicating this component should render a group footer. The default renders
     * a column footer. This should not be used if tableColumnFooter or tableFooter are
     * used.
     */
    public void setGroupFooter(boolean groupFooter) {
        this.groupFooter = groupFooter;
        this.groupFooter_set = true;
    }

    /**
     * The HEADERS attribute specifies the header cells that apply to the
     * TD. The value is a space-separated list of the header cells' ID
     * attribute values. The HEADERS attribute allows non-visual browsers to
     * render the header information for a given cell.
     */
    @Property(name="headers", displayName="List of Header Cells for Current Cell")
    private String headers = null;

    /**
     * The HEADERS attribute specifies the header cells that apply to the
     * TD. The value is a space-separated list of the header cells' ID
     * attribute values. The HEADERS attribute allows non-visual browsers to
     * render the header information for a given cell.
     */
    public String getHeaders() {
        if (this.headers != null) {
            return this.headers;
        }
        ValueExpression _vb = getValueExpression("headers");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The HEADERS attribute specifies the header cells that apply to the
     * TD. The value is a space-separated list of the header cells' ID
     * attribute values. The HEADERS attribute allows non-visual browsers to
     * render the header information for a given cell.
     */
    public void setHeaders(String headers) {
        this.headers = headers;
    }

    /**
     * The number of pixels for the cell's height. Styles should be used to specify 
     * cell height when possible because the height attribute is deprecated in HTML 4.0.
     */
    @Property(name="height", displayName="Height")
    private String height = null;

    /**
     * The number of pixels for the cell's height. Styles should be used to specify 
     * cell height when possible because the height attribute is deprecated in HTML 4.0.
     */
    public String getHeight() {
        if (this.height != null) {
            return this.height;
        }
        ValueExpression _vb = getValueExpression("height");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The number of pixels for the cell's height. Styles should be used to specify 
     * cell height when possible because the height attribute is deprecated in HTML 4.0.
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * Use the <code>noWrap</code> attribute to disable word wrapping of this column's 
     * cells in visual browsers. Word wrap can cause unnecessary horizontal scrolling 
     * when the browser window is small in relation to the font size. Styles 
     * should be used to disable word wrap when possible because the nowrap attribute 
     * is deprecated in HTML 4.0.
     */
    @Property(name="noWrap", displayName="Suppress Word Wrap")
    private boolean noWrap = false;
    private boolean noWrap_set = false;

    /**
     * Use the <code>noWrap</code> attribute to disable word wrapping of this column's 
     * cells in visual browsers. Word wrap can cause unnecessary horizontal scrolling 
     * when the browser window is small in relation to the font size. Styles 
     * should be used to disable word wrap when possible because the nowrap attribute 
     * is deprecated in HTML 4.0.
     */
    public boolean isNoWrap() {
        if (this.noWrap_set) {
            return this.noWrap;
        }
        ValueExpression _vb = getValueExpression("noWrap");
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
     * Use the <code>noWrap</code> attribute to disable word wrapping of this column's 
     * cells in visual browsers. Word wrap can cause unnecessary horizontal scrolling 
     * when the browser window is small in relation to the font size. Styles 
     * should be used to disable word wrap when possible because the nowrap attribute 
     * is deprecated in HTML 4.0.
     */
    public void setNoWrap(boolean noWrap) {
        this.noWrap = noWrap;
        this.noWrap_set = true;
    }

    /**
     * Scripting code executed when a mouse click
     * occurs over this component.
     */
    @Property(name="onClick", displayName="Click Script")
    private String onClick = null;

    /**
     * Scripting code executed when a mouse click
     * occurs over this component.
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
     * Scripting code executed when a mouse click
     * occurs over this component.
     */
    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    /**
     * Scripting code executed when a mouse double click
     * occurs over this component.
     */
    @Property(name="onDblClick", displayName="Double Click Script")
    private String onDblClick = null;

    /**
     * Scripting code executed when a mouse double click
     * occurs over this component.
     */
    public String getOnDblClick() {
        if (this.onDblClick != null) {
            return this.onDblClick;
        }
        ValueExpression _vb = getValueExpression("onDblClick");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when a mouse double click
     * occurs over this component.
     */
    public void setOnDblClick(String onDblClick) {
        this.onDblClick = onDblClick;
    }
 
    /**
     * Scripting code executed when the user presses down on a key while the
     * component has focus.
     */
    @Property(name="onKeyDown", displayName="Key Down Script")
    private String onKeyDown = null;

    /**
     * Scripting code executed when the user presses down on a key while the
     * component has focus.
     */
    public String getOnKeyDown() {
        if (this.onKeyDown != null) {
            return this.onKeyDown;
        }
        ValueExpression _vb = getValueExpression("onKeyDown");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when the user presses down on a key while the
     * component has focus.
     */
    public void setOnKeyDown(String onKeyDown) {
        this.onKeyDown = onKeyDown;
    }

    /**
     * Scripting code executed when the user presses and releases a key while
     * the component has focus.
     */
    @Property(name="onKeyPress", displayName="Key Press Script")
    private String onKeyPress = null;

    /**
     * Scripting code executed when the user presses and releases a key while
     * the component has focus.
     */
    public String getOnKeyPress() {
        if (this.onKeyPress != null) {
            return this.onKeyPress;
        }
        ValueExpression _vb = getValueExpression("onKeyPress");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when the user presses and releases a key while
     * the component has focus.
     */
    public void setOnKeyPress(String onKeyPress) {
        this.onKeyPress = onKeyPress;
    }

    /**
     * Scripting code executed when the user releases a key while the
     * component has focus.
     */
    @Property(name="onKeyUp", displayName="Key Up Script")
    private String onKeyUp = null;

    /**
     * Scripting code executed when the user releases a key while the
     * component has focus.
     */
    public String getOnKeyUp() {
        if (this.onKeyUp != null) {
            return this.onKeyUp;
        }
        ValueExpression _vb = getValueExpression("onKeyUp");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when the user releases a key while the
     * component has focus.
     */
    public void setOnKeyUp(String onKeyUp) {
        this.onKeyUp = onKeyUp;
    }

    /**
     * Scripting code executed when the user presses a mouse button while the
     * mouse pointer is on the component.
     */
    @Property(name="onMouseDown", displayName="Mouse Down Script")
    private String onMouseDown = null;

    /**
     * Scripting code executed when the user presses a mouse button while the
     * mouse pointer is on the component.
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
     * Scripting code executed when the user presses a mouse button while the
     * mouse pointer is on the component.
     */
    public void setOnMouseDown(String onMouseDown) {
        this.onMouseDown = onMouseDown;
    }

    /**
     * Scripting code executed when the user moves the mouse pointer while
     * over the component.
     */
    @Property(name="onMouseMove", displayName="Mouse Move Script")
    private String onMouseMove = null;

    /**
     * Scripting code executed when the user moves the mouse pointer while
     * over the component.
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
     * Scripting code executed when the user moves the mouse pointer while
     * over the component.
     */
    public void setOnMouseMove(String onMouseMove) {
        this.onMouseMove = onMouseMove;
    }

    /**
     * Scripting code executed when a mouse out movement
     * occurs over this component.
     */
    @Property(name="onMouseOut", displayName="Mouse Out Script")
    private String onMouseOut = null;

    /**
     * Scripting code executed when a mouse out movement
     * occurs over this component.
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
     * Scripting code executed when a mouse out movement
     * occurs over this component.
     */
    public void setOnMouseOut(String onMouseOut) {
        this.onMouseOut = onMouseOut;
    }

    /**
     * Scripting code executed when the user moves the  mouse pointer into
     * the boundary of this component.
     */
    @Property(name="onMouseOver", displayName="Mouse In Script")
    private String onMouseOver = null;

    /**
     * Scripting code executed when the user moves the  mouse pointer into
     * the boundary of this component.
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
     * Scripting code executed when the user moves the  mouse pointer into
     * the boundary of this component.
     */
    public void setOnMouseOver(String onMouseOver) {
        this.onMouseOver = onMouseOver;
    }

    /**
     * Scripting code executed when the user releases a mouse button while
     * the mouse pointer is on the component.
     */
    @Property(name="onMouseUp", displayName="Mouse Up Script")
    private String onMouseUp = null;

    /**
     * Scripting code executed when the user releases a mouse button while
     * the mouse pointer is on the component.
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
     * Scripting code executed when the user releases a mouse button while
     * the mouse pointer is on the component.
     */
    public void setOnMouseUp(String onMouseUp) {
        this.onMouseUp = onMouseUp;
    }

    /**
     * The ROWSPAN attribute of TD specifies the number of rows that are
     * spanned by the cell. The default value is 1. The special value 0
     * indicates that the cell spans all rows to the end of the table. The
     * value 0 is ignored by most browsers, so authors may wish to calculate
     * the exact number of rows or columns spanned and use that value.
     */
    @Property(name="rowSpan", displayName="Rows Spanned By the Cell")
    private int rowSpan = Integer.MIN_VALUE;
    private boolean rowSpan_set = false;

    /**
     * The ROWSPAN attribute of TD specifies the number of rows that are
     * spanned by the cell. The default value is 1. The special value 0
     * indicates that the cell spans all rows to the end of the table. The
     * value 0 is ignored by most browsers, so authors may wish to calculate
     * the exact number of rows or columns spanned and use that value.
     */
    public int getRowSpan() {
        if (this.rowSpan_set) {
            return this.rowSpan;
        }
        ValueExpression _vb = getValueExpression("rowSpan");
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
     * The ROWSPAN attribute of TD specifies the number of rows that are
     * spanned by the cell. The default value is 1. The special value 0
     * indicates that the cell spans all rows to the end of the table. The
     * value 0 is ignored by most browsers, so authors may wish to calculate
     * the exact number of rows or columns spanned and use that value.
     */
    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
        this.rowSpan_set = true;
    }
    /**
     * Use the <code>scope</code> attribute to specify that the data cells of the 
     * column are also acting as headers for rows or other columns of the table. 
     * This attribute supports assistive technologies by enabling them to determine 
     * the order in which to read the cells. Valid values include: 
     * <ul>
     * <li><code>row</code>, when the cells provide header information for the row</li>
     * <li><code>col</code>, when the cells provide header information for the column</li>
     * <li><code>rowgroup</code>, when the cells provide header information for the row group</li>
     * <li><code>colgroup</code>, when the cells provide header information for the column group</li>
     * </ul>
     */
    @Property(name="scope", displayName="Cells Covered By Header Cell")
    private String scope = null;

    /**
     * Use the <code>scope</code> attribute to specify that the data cells of the 
     * column are also acting as headers for rows or other columns of the table. 
     * This attribute supports assistive technologies by enabling them to determine 
     * the order in which to read the cells. Valid values include: 
     * <ul>
     * <li><code>row</code>, when the cells provide header information for the row</li>
     * <li><code>col</code>, when the cells provide header information for the column</li>
     * <li><code>rowgroup</code>, when the cells provide header information for the row group</li>
     * <li><code>colgroup</code>, when the cells provide header information for the column group</li>
     * </ul>
     */
    public String getScope() {
        if (this.scope != null) {
            return this.scope;
        }
        ValueExpression _vb = getValueExpression("scope");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Use the <code>scope</code> attribute to specify that the data cells of the 
     * column are also acting as headers for rows or other columns of the table. 
     * This attribute supports assistive technologies by enabling them to determine 
     * the order in which to read the cells. Valid values include: 
     * <ul>
     * <li><code>row</code>, when the cells provide header information for the row</li>
     * <li><code>col</code>, when the cells provide header information for the column</li>
     * <li><code>rowgroup</code>, when the cells provide header information for the row group</li>
     * <li><code>colgroup</code>, when the cells provide header information for the column group</li>
     * </ul>
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.
     */
    @Property(name="style", displayName="CSS Style(s)")
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
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)")
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
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * Flag indicating this component should render a table column footer. The default
     * renders a column footer. This should not be used if groupFooter or tableFooter
     * are used.
     */
    @Property(name="tableColumnFooter", displayName="Is Table Column Footer", isAttribute=false)
    private boolean tableColumnFooter = false;
    private boolean tableColumnFooter_set = false;

    /**
     * Flag indicating this component should render a table column footer. The default
     * renders a column footer. This should not be used if groupFooter or tableFooter
     * are used.
     */
    public boolean isTableColumnFooter() {
        if (this.tableColumnFooter_set) {
            return this.tableColumnFooter;
        }
        ValueExpression _vb = getValueExpression("tableColumnFooter");
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
     * Flag indicating this component should render a table column footer. The default
     * renders a column footer. This should not be used if groupFooter or tableFooter
     * are used.
     */
    public void setTableColumnFooter(boolean tableColumnFooter) {
        this.tableColumnFooter = tableColumnFooter;
        this.tableColumnFooter_set = true;
    }
    /**
     * Flag indicating this component should render a table footer. The default renders
     * a column footer. This should not be used if groupFooter or tableColumnFooter are
     * used.
     */
    @Property(name="tableFooter", displayName="Is Table Footer", isAttribute=false)
    private boolean tableFooter = false;
    private boolean tableFooter_set = false;

    /**
     * Flag indicating this component should render a table footer. The default renders
     * a column footer. This should not be used if groupFooter or tableColumnFooter are
     * used.
     */
    public boolean isTableFooter() {
        if (this.tableFooter_set) {
            return this.tableFooter;
        }
        ValueExpression _vb = getValueExpression("tableFooter");
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
     * Flag indicating this component should render a table footer. The default renders
     * a column footer. This should not be used if groupFooter or tableColumnFooter are
     * used.
     */
    public void setTableFooter(boolean tableFooter) {
        this.tableFooter = tableFooter;
        this.tableFooter_set = true;
    }

    /**
     * Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.
     */
    @Property(name="toolTip", displayName="Tool Tip", category="Behavior")
    private String toolTip = null;

    /**
     * Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.
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
     * Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.
     */
    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    /**
     * Use the <code>valign</code> attribute to specify the vertical alignment for the 
     * content of each cell in the column. Valid values are <code>top</code>, 
     * <code>middle</code>, <code>bottom</code>, and <code>baseline</code>. The 
     * default vertical alignment is <code>middle</code>. Setting the 
     * <code>valign</code> attribute to <code>baseline </code>causes the first line of 
     * each cell's content to be aligned on the text baseline, the invisible line on 
     * which text characters rest.
     */
    @Property(name="valign", displayName="Vertical Position")
    private String valign = null;

    /**
     * Use the <code>valign</code> attribute to specify the vertical alignment for the 
     * content of each cell in the column. Valid values are <code>top</code>, 
     * <code>middle</code>, <code>bottom</code>, and <code>baseline</code>. The 
     * default vertical alignment is <code>middle</code>. Setting the 
     * <code>valign</code> attribute to <code>baseline </code>causes the first line of 
     * each cell's content to be aligned on the text baseline, the invisible line on 
     * which text characters rest.
     */
    public String getValign() {
        if (this.valign != null) {
            return this.valign;
        }
        ValueExpression _vb = getValueExpression("valign");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Use the <code>valign</code> attribute to specify the vertical alignment for the 
     * content of each cell in the column. Valid values are <code>top</code>, 
     * <code>middle</code>, <code>bottom</code>, and <code>baseline</code>. The 
     * default vertical alignment is <code>middle</code>. Setting the 
     * <code>valign</code> attribute to <code>baseline </code>causes the first line of 
     * each cell's content to be aligned on the text baseline, the invisible line on 
     * which text characters rest.
     */
    public void setValign(String valign) {
        this.valign = valign;
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
    @Property(name="visible", displayName="Visible")
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
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }

    /**
     * Use the <code>width</code> attribute to specify the width of the cells of the 
     * column. The width can be specified as the number of pixels or the percentage of 
     * the table width, and is especially useful for spacer columns. This attribute is 
     * deprecated in HTML 4.0 in favor of style sheets.
     */
    @Property(name="width", displayName="Width")
    private String width = null;

    /**
     * Use the <code>width</code> attribute to specify the width of the cells of the 
     * column. The width can be specified as the number of pixels or the percentage of 
     * the table width, and is especially useful for spacer columns. This attribute is 
     * deprecated in HTML 4.0 in favor of style sheets.
     */
    public String getWidth() {
        if (this.width != null) {
            return this.width;
        }
        ValueExpression _vb = getValueExpression("width");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Use the <code>width</code> attribute to specify the width of the cells of the 
     * column. The width can be specified as the number of pixels or the percentage of 
     * the table width, and is especially useful for spacer columns. This attribute is 
     * deprecated in HTML 4.0 in favor of style sheets.
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * Restore the state of this component.
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.abbr = (String) _values[1];
        this.align = (String) _values[2];
        this.axis = (String) _values[3];
        this.bgColor = (String) _values[4];
        this._char = (String) _values[5];
        this.charOff = (String) _values[6];
        this.colSpan = ((Integer) _values[7]).intValue();
        this.colSpan_set = ((Boolean) _values[8]).booleanValue();
        this.extraHtml = (String) _values[9];
        this.groupFooter = ((Boolean) _values[10]).booleanValue();
        this.groupFooter_set = ((Boolean) _values[11]).booleanValue();
        this.headers = (String) _values[12];
        this.height = (String) _values[13];
        this.noWrap = ((Boolean) _values[14]).booleanValue();
        this.noWrap_set = ((Boolean) _values[15]).booleanValue();
        this.onClick = (String) _values[16];
        this.onDblClick = (String) _values[17];
        this.onKeyDown = (String) _values[18];
        this.onKeyPress = (String) _values[19];
        this.onKeyUp = (String) _values[20];
        this.onMouseDown = (String) _values[21];
        this.onMouseMove = (String) _values[22];
        this.onMouseOut = (String) _values[23];
        this.onMouseOver = (String) _values[24];
        this.onMouseUp = (String) _values[25];
        this.rowSpan = ((Integer) _values[26]).intValue();
        this.rowSpan_set = ((Boolean) _values[27]).booleanValue();
        this.scope = (String) _values[28];
        this.style = (String) _values[29];
        this.styleClass = (String) _values[30];
        this.tableColumnFooter = ((Boolean) _values[31]).booleanValue();
        this.tableColumnFooter_set = ((Boolean) _values[32]).booleanValue();
        this.tableFooter = ((Boolean) _values[33]).booleanValue();
        this.tableFooter_set = ((Boolean) _values[34]).booleanValue();
        this.toolTip = (String) _values[35];
        this.valign = (String) _values[36];
        this.visible = ((Boolean) _values[37]).booleanValue();
        this.visible_set = ((Boolean) _values[38]).booleanValue();
        this.width = (String) _values[39];
    }

    /**
     * Save the state of this component.
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[40];
        _values[0] = super.saveState(_context);
        _values[1] = this.abbr;
        _values[2] = this.align;
        _values[3] = this.axis;
        _values[4] = this.bgColor;
        _values[5] = this._char;
        _values[6] = this.charOff;
        _values[7] = new Integer(this.colSpan);
        _values[8] = this.colSpan_set ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = this.extraHtml;
        _values[10] = this.groupFooter ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.groupFooter_set ? Boolean.TRUE : Boolean.FALSE;
        _values[12] = this.headers;
        _values[13] = this.height;
        _values[14] = this.noWrap ? Boolean.TRUE : Boolean.FALSE;
        _values[15] = this.noWrap_set ? Boolean.TRUE : Boolean.FALSE;
        _values[16] = this.onClick;
        _values[17] = this.onDblClick;
        _values[18] = this.onKeyDown;
        _values[19] = this.onKeyPress;
        _values[20] = this.onKeyUp;
        _values[21] = this.onMouseDown;
        _values[22] = this.onMouseMove;
        _values[23] = this.onMouseOut;
        _values[24] = this.onMouseOver;
        _values[25] = this.onMouseUp;
        _values[26] = new Integer(this.rowSpan);
        _values[27] = this.rowSpan_set ? Boolean.TRUE : Boolean.FALSE;
        _values[28] = this.scope;
        _values[29] = this.style;
        _values[30] = this.styleClass;
        _values[31] = this.tableColumnFooter ? Boolean.TRUE : Boolean.FALSE;
        _values[32] = this.tableColumnFooter_set ? Boolean.TRUE : Boolean.FALSE;
        _values[33] = this.tableFooter ? Boolean.TRUE : Boolean.FALSE;
        _values[34] = this.tableFooter_set ? Boolean.TRUE : Boolean.FALSE;
        _values[35] = this.toolTip;
        _values[36] = this.valign;
        _values[37] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[38] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[39] = this.width;
        return _values;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to get Theme objects.
     *
     * @return The current theme.
     */
    private Theme getTheme() {
	return ThemeUtilities.getTheme(getFacesContext());
    }

    /**
     * Log fine messages.
     */
    private void log(String method, String message) {
        // Get class.
        Class clazz = this.getClass();
	if (LogUtil.fineEnabled(clazz)) {
            // Log method name and message.
            LogUtil.fine(clazz, clazz.getName() + "." + method + ": " + message); //NOI18N
        }
    }
}
