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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * Base class for table column components.
 */
public abstract class TableColumnBase extends WebuiComponent
        implements NamingContainer {
    // A List of TableColumn children found for this component.
    private List tableColumnChildren = null;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Child methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Clear cached properties.
     * <p>
     * Note: Properties may have been cached via the apply request values,
     * validate, and update phases and must be re-evaluated during the render
     * response phase (e.g., the underlying DataProvider may have changed). This
     * cannot always be done via encodeBegin because the component's parent may
     * need to obtain updated properties before this component is rendered.
     * </p>
     */
    public void clear() {
        tableColumnChildren = null;

        // Clear properties of nested TableColumn children.
        Iterator kids = getTableColumnChildren();
        while (kids.hasNext()) {
            TableColumnBase kid = (TableColumnBase) kids.next();
            kid.clear(); // Clear cached properties.
        }
    }

    /**
     * Get an Iterator over the TableColumn children found for
     * this component.
     *
     * @return An Iterator over the TableColumn children.
     */
    public Iterator getTableColumnChildren() {
        // Get TableColumn children.
        if (tableColumnChildren == null) {
            tableColumnChildren = new ArrayList();
            Iterator kids = getChildren().iterator();
            while (kids.hasNext()) {
                UIComponent kid = (UIComponent) kids.next();
                if ((kid instanceof TableColumn)) {
                    tableColumnChildren.add(kid);
                }
            }
        }
        return tableColumnChildren.iterator();
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
    @Property(name="abbr", displayName="Abbreviation for Header Cell", isHidden=true, isAttribute=false)
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
    @Property(name="align", displayName="Horizontal Alignment", category="Appearance")
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
     * Use the <code>alignKey</code> attribute to specify the FieldKey id or FieldKey 
     * to be used as an identifier for a specific data element on which to align the 
     * table cell data in the column. If <code>alignKey</code> specifies a 
     * FieldKey, the FieldKey is used as is; otherwise, a FieldKey is created using 
     * the <code>alignKey</code> value that you specify. Alignment is based on 
     * the object type of the data element. For example, Date and Number objects are 
     * aligned "right", Character and String objects are aligned "left", and Boolean 
     * objects are aligned "center". All columns, including select columns, are 
     * aligned "left" by default. Note that the align property overrides this value.
     */
    @Property(name="alignKey", displayName="Horizontal Alignment Key", category="Appearance",
        editorClassName="com.sun.rave.propertyeditors.StringPropertyEditor")
    private Object alignKey = null;

    /**
     * Use the <code>alignKey</code> attribute to specify the FieldKey id or FieldKey 
     * to be used as an identifier for a specific data element on which to align the 
     * table cell data in the column. If <code>alignKey</code> specifies a 
     * FieldKey, the FieldKey is used as is; otherwise, a FieldKey is created using 
     * the <code>alignKey</code> value that you specify. Alignment is based on 
     * the object type of the data element. For example, Date and Number objects are 
     * aligned "right", Character and String objects are aligned "left", and Boolean 
     * objects are aligned "center". All columns, including select columns, are 
     * aligned "left" by default. Note that the align property overrides this value.
     */
    public Object getAlignKey() {
        if (this.alignKey != null) {
            return this.alignKey;
        }
        ValueExpression _vb = getValueExpression("alignKey");
        if (_vb != null) {
            return (Object) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Use the <code>alignKey</code> attribute to specify the FieldKey id or FieldKey 
     * to be used as an identifier for a specific data element on which to align the 
     * table cell data in the column. If <code>alignKey</code> specifies a 
     * FieldKey, the FieldKey is used as is; otherwise, a FieldKey is created using 
     * the <code>alignKey</code> value that you specify. Alignment is based on 
     * the object type of the data element. For example, Date and Number objects are 
     * aligned "right", Character and String objects are aligned "left", and Boolean 
     * objects are aligned "center". All columns, including select columns, are 
     * aligned "left" by default. Note that the align property overrides this value.
     */
    public void setAlignKey(Object alignKey) {
        this.alignKey = alignKey;
    }

    /**
     * The AXIS attribute provides a method of categorizing cells. The
     * attribute's value is a comma-separated list of category names. See the
     * HTML 4.0 Recommendation's section on categorizing cells for an
     * application of AXIS.
     */
    @Property(name="axis", displayName="Category of Header Cell", category="Advanced", isHidden=true, isAttribute=false)
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
    @Property(name="bgColor", displayName="Cell Background Color", isHidden=true, isAttribute=false)
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
    @Property(name="char", displayName="Alignment Character", isHidden=true, isAttribute=false)
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
    @Property(name="charOff", displayName="Alignment Character Offset", isHidden=true, isAttribute=false)
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
    @Property(name="colSpan", displayName="Columns Spanned By the Cell", category="Layout", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor", isAttribute=false)
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
     * Use the <code>descending</code> attribute to specify that the first 
     * user-applied sort is descending. By default, the first time a user clicks a 
     * column's sort button or column header, the sort is ascending. Note that this 
     * not an initial sort. The data is initially displayed unsorted.
     */
    public boolean isDescending() {
        if (this.descending_set) {
            return this.descending;
        }
        ValueExpression _vb = getValueExpression("descending");
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
     * Use the <code>descending</code> attribute to specify that the first 
     * user-applied sort is descending. By default, the first time a user clicks a 
     * column's sort button or column header, the sort is ascending. Note that this 
     * not an initial sort. The data is initially displayed unsorted.
     */
    @Property(name="descending", displayName="Is Descending", category="Data")
    private boolean descending = false;
    private boolean descending_set = false;

    /**
     * Use the <code>descending</code> attribute to specify that the first 
     * user-applied sort is descending. By default, the first time a user clicks a 
     * column's sort button or column header, the sort is ascending. Note that this 
     * not an initial sort. The data is initially displayed unsorted.
     */
    public void setDescending(boolean descending) {
        this.descending = descending;
        this.descending_set = true;
    }

    /**
     * Set the <code>embeddedActions</code> attribute to true when the column includes 
     * more than one embedded action. This attribute causes a separator image to be 
     * displayed between the action links. This attribute is overridden by the 
     * <code>emptyCell</code> attribute.
     */
    @Property(name="embeddedActions", displayName="Is Embedded Actions", category="Advanced")
    private boolean embeddedActions = false;
    private boolean embeddedActions_set = false;

    /**
     * Set the <code>embeddedActions</code> attribute to true when the column includes 
     * more than one embedded action. This attribute causes a separator image to be 
     * displayed between the action links. This attribute is overridden by the 
     * <code>emptyCell</code> attribute.
     */
    public boolean isEmbeddedActions() {
        if (this.embeddedActions_set) {
            return this.embeddedActions;
        }
        ValueExpression _vb = getValueExpression("embeddedActions");
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
     * Set the <code>embeddedActions</code> attribute to true when the column includes 
     * more than one embedded action. This attribute causes a separator image to be 
     * displayed between the action links. This attribute is overridden by the 
     * <code>emptyCell</code> attribute.
     */
    public void setEmbeddedActions(boolean embeddedActions) {
        this.embeddedActions = embeddedActions;
        this.embeddedActions_set = true;
    }

    /**
     * Use the <code>emptyCell</code> attribute to cause a theme-specific image to be 
     * displayed when the content of a table cell is not applicable or is unexpectedly 
     * empty. You should not use this attribute for a value that is truly null, such 
     * as an empty alarm cell or a comment field that is blank. In addition, the image 
     * should not be used for cells that contain user interface elements such as 
     * checkboxes or drop-down lists when these elements are not applicable. Instead, 
     * the elements should simply not be displayed so the cell is left empty.
     */
    @Property(name="emptyCell", displayName="Empty Cell", category="Appearance")
    private boolean emptyCell = false;
    private boolean emptyCell_set = false;

    /**
     * Use the <code>emptyCell</code> attribute to cause a theme-specific image to be 
     * displayed when the content of a table cell is not applicable or is unexpectedly 
     * empty. You should not use this attribute for a value that is truly null, such 
     * as an empty alarm cell or a comment field that is blank. In addition, the image 
     * should not be used for cells that contain user interface elements such as 
     * checkboxes or drop-down lists when these elements are not applicable. Instead, 
     * the elements should simply not be displayed so the cell is left empty.
     */
    public boolean isEmptyCell() {
        if (this.emptyCell_set) {
            return this.emptyCell;
        }
        ValueExpression _vb = getValueExpression("emptyCell");
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
     * Use the <code>emptyCell</code> attribute to cause a theme-specific image to be 
     * displayed when the content of a table cell is not applicable or is unexpectedly 
     * empty. You should not use this attribute for a value that is truly null, such 
     * as an empty alarm cell or a comment field that is blank. In addition, the image 
     * should not be used for cells that contain user interface elements such as 
     * checkboxes or drop-down lists when these elements are not applicable. Instead, 
     * the elements should simply not be displayed so the cell is left empty.
     */
    public void setEmptyCell(boolean emptyCell) {
        this.emptyCell = emptyCell;
        this.emptyCell_set = true;
    }

    /**
     * The text to be displayed in the column footer.
     */
    @Property(name="footerText", displayName="Footer Text", category="Appearance")
    private String footerText = null;

    /**
     * The text to be displayed in the column footer.
     */
    public String getFooterText() {
        if (this.footerText != null) {
            return this.footerText;
        }
        ValueExpression _vb = getValueExpression("footerText");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The text to be displayed in the column footer.
     */
    public void setFooterText(String footerText) {
        this.footerText = footerText;
    }

    /**
     * The text to be displayed in the column header.
     */
    @Property(name="headerText", displayName="header Text", category="Appearance")
    private String headerText = null;

    /**
     * The text to be displayed in the column header.
     */
    public String getHeaderText() {
        if (this.headerText != null) {
            return this.headerText;
        }
        ValueExpression _vb = getValueExpression("headerText");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The text to be displayed in the column header.
     */
    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    /**
     * The HEADERS attribute specifies the header cells that apply to the
     * TD. The value is a space-separated list of the header cells' ID
     * attribute values. The HEADERS attribute allows non-visual browsers to
     * render the header information for a given cell.
     */
    @Property(name="headers", displayName="List of Header Cells for Current Cell", category="Advanced", isHidden=true, isAttribute=false)
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
    @Property(name="height", displayName="Height", category="Layout")
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
    @Property(name="noWrap", displayName="Suppress Word Wrap", category="Appearance")
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
    @Property(name="onClick", displayName="Click Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name="onDblClick", displayName="Double Click Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name="onKeyDown", displayName="Key Down Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name="onKeyPress", displayName="Key Press Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name="onKeyUp", displayName="Key Up Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name="onMouseDown", displayName="Mouse Down Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name="onMouseMove", displayName="Mouse Move Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name="onMouseOut", displayName="Mouse Out Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name="onMouseOver", displayName="Mouse In Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name="onMouseUp", displayName="Mouse Up Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
     * <p>Use the <code>rowHeader</code> attribute to specify that the cells of the 
     * column are acting as row headers. Row headers are cells that "label" the row. 
     * For example, consider a table where the first column contains checkboxes, and 
     * the second column contains user names. The third and subsequent columns contain 
     * attributes of those users. The content of the cells in the user name column are 
     * acting as row headers. The <code>webuijsf:tableColumn</code> tag for the user name 
     * column should set the <code>rowHeader</code> attribute to true. If a table 
     * contains, for example, a system log with time stamp and log entry columns, 
     * neither column is acting as a row header, so the <code>rowHeader</code> 
     * attribute should not be set. 
     * </p><p>
     * By default, most column cells are rendered by the table component with HTML 
     * <code>&lt;td scope="col"&gt;</code> elements. The exceptions are columns that 
     * contain checkboxes or radio buttons and spacer columns, all of which are 
     * rendered as <code>&lt;td&gt;</code> elements without a scope property. 
     * </p><p>
     * When you set the <code>rowHeader</code> attribute, the column cells are 
     * rendered as <code>&lt;th scope="row"&gt;</code> elements, which enables 
     * adaptive technologies such as screen readers to properly read the table to 
     * indicate that the contents of these cells are headers for the rows.</p>
     */
    @Property(name="rowHeader", displayName="Row Header", category="Advanced")
    private boolean rowHeader = false;
    private boolean rowHeader_set = false;

    /**
     * <p>Use the <code>rowHeader</code> attribute to specify that the cells of the 
     * column are acting as row headers. Row headers are cells that "label" the row. 
     * For example, consider a table where the first column contains checkboxes, and 
     * the second column contains user names. The third and subsequent columns contain 
     * attributes of those users. The content of the cells in the user name column are 
     * acting as row headers. The <code>webuijsf:tableColumn</code> tag for the user name 
     * column should set the <code>rowHeader</code> attribute to true. If a table 
     * contains, for example, a system log with time stamp and log entry columns, 
     * neither column is acting as a row header, so the <code>rowHeader</code> 
     * attribute should not be set. 
     * </p><p>
     * By default, most column cells are rendered by the table component with HTML 
     * <code>&lt;td scope="col"&gt;</code> elements. The exceptions are columns that 
     * contain checkboxes or radio buttons and spacer columns, all of which are 
     * rendered as <code>&lt;td&gt;</code> elements without a scope property. 
     * </p><p>
     * When you set the <code>rowHeader</code> attribute, the column cells are 
     * rendered as <code>&lt;th scope="row"&gt;</code> elements, which enables 
     * adaptive technologies such as screen readers to properly read the table to 
     * indicate that the contents of these cells are headers for the rows.</p>
     */
    public boolean isRowHeader() {
        if (this.rowHeader_set) {
            return this.rowHeader;
        }
        ValueExpression _vb = getValueExpression("rowHeader");
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
     * <p>Use the <code>rowHeader</code> attribute to specify that the cells of the 
     * column are acting as row headers. Row headers are cells that "label" the row. 
     * For example, consider a table where the first column contains checkboxes, and 
     * the second column contains user names. The third and subsequent columns contain 
     * attributes of those users. The content of the cells in the user name column are 
     * acting as row headers. The <code>webuijsf:tableColumn</code> tag for the user name 
     * column should set the <code>rowHeader</code> attribute to true. If a table 
     * contains, for example, a system log with time stamp and log entry columns, 
     * neither column is acting as a row header, so the <code>rowHeader</code> 
     * attribute should not be set. 
     * </p><p>
     * By default, most column cells are rendered by the table component with HTML 
     * <code>&lt;td scope="col"&gt;</code> elements. The exceptions are columns that 
     * contain checkboxes or radio buttons and spacer columns, all of which are 
     * rendered as <code>&lt;td&gt;</code> elements without a scope property. 
     * </p><p>
     * When you set the <code>rowHeader</code> attribute, the column cells are 
     * rendered as <code>&lt;th scope="row"&gt;</code> elements, which enables 
     * adaptive technologies such as screen readers to properly read the table to 
     * indicate that the contents of these cells are headers for the rows.</p>
     */
    public void setRowHeader(boolean rowHeader) {
        this.rowHeader = rowHeader;
        this.rowHeader_set = true;
    }

    /**
     * The ROWSPAN attribute of TD specifies the number of rows that are
     * spanned by the cell. The default value is 1. The special value 0
     * indicates that the cell spans all rows to the end of the table. The
     * value 0 is ignored by most browsers, so authors may wish to calculate
     * the exact number of rows or columns spanned and use that value.
     */
    @Property(name="rowSpan", displayName="Rows Spanned By the Cell", category="Layout", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor", isAttribute=false)
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
    @Property(name="scope", displayName="Cells Covered By Header Cell", category="Advanced")
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
     * Use the <code>selectId</code> attribute in select columns, which contain 
     * checkboxes or radio buttons for selecting table rows. The value of 
     * <code>selectId</code> must match the <code>id</code> attribute of the checkbox 
     * or radioButton component that is a child of the tableColumn component. A fully 
     * qualified ID based on the tableColumn component ID and the 
     * <code>selectId</code> for the current row will be dynamically created for the 
     * <code>&lt;input&gt;</code> element that is rendered for the checkbox or radio 
     * button. The <code>selectId</code> is required for functionality that supports 
     * the toggle buttons for selecting rows. The <code>selectId</code> also 
     * identifies the column as a select column, for which the table component 
     * uses different CSS styles.
     */
    @Property(name="selectId", displayName="Select Component Id", category="Data")
    private String selectId = null;

    /**
     * Use the <code>selectId</code> attribute in select columns, which contain 
     * checkboxes or radio buttons for selecting table rows. The value of 
     * <code>selectId</code> must match the <code>id</code> attribute of the checkbox 
     * or radioButton component that is a child of the tableColumn component. A fully 
     * qualified ID based on the tableColumn component ID and the 
     * <code>selectId</code> for the current row will be dynamically created for the 
     * <code>&lt;input&gt;</code> element that is rendered for the checkbox or radio 
     * button. The <code>selectId</code> is required for functionality that supports 
     * the toggle buttons for selecting rows. The <code>selectId</code> also 
     * identifies the column as a select column, for which the table component 
     * uses different CSS styles.
     */
    public String getSelectId() {
        if (this.selectId != null) {
            return this.selectId;
        }
        ValueExpression _vb = getValueExpression("selectId");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Use the <code>selectId</code> attribute in select columns, which contain 
     * checkboxes or radio buttons for selecting table rows. The value of 
     * <code>selectId</code> must match the <code>id</code> attribute of the checkbox 
     * or radioButton component that is a child of the tableColumn component. A fully 
     * qualified ID based on the tableColumn component ID and the 
     * <code>selectId</code> for the current row will be dynamically created for the 
     * <code>&lt;input&gt;</code> element that is rendered for the checkbox or radio 
     * button. The <code>selectId</code> is required for functionality that supports 
     * the toggle buttons for selecting rows. The <code>selectId</code> also 
     * identifies the column as a select column, for which the table component 
     * uses different CSS styles.
     */
    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }

    /**
     * Use the <code>severity</code> attribute when including the <code>webuijsf:alarm</code> 
     * component in a column, to match the severity of the alarm. Valid values are 
     * described in the <code>webuijsf:alarm</code> documentation. When the 
     * <code>severity</code> attribute is set in the tableColumn, the table 
     * component renders sort tool tips to indicate that the column will be sorted 
     * least/most severe first, and the table cell appears hightlighted according to 
     * the level of severity. This functionality is overridden by the 
     * <code>emptyCell</code> attribute.
     */
    @Property(name="severity", displayName="Severity", category="Appearance")
    private String severity = null;

    /**
     * Use the <code>severity</code> attribute when including the <code>webuijsf:alarm</code> 
     * component in a column, to match the severity of the alarm. Valid values are 
     * described in the <code>webuijsf:alarm</code> documentation. When the 
     * <code>severity</code> attribute is set in the tableColumn, the table 
     * component renders sort tool tips to indicate that the column will be sorted 
     * least/most severe first, and the table cell appears hightlighted according to 
     * the level of severity. This functionality is overridden by the 
     * <code>emptyCell</code> attribute.
     */
    public String getSeverity() {
        if (this.severity != null) {
            return this.severity;
        }
        ValueExpression _vb = getValueExpression("severity");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Use the <code>severity</code> attribute when including the <code>webuijsf:alarm</code> 
     * component in a column, to match the severity of the alarm. Valid values are 
     * described in the <code>webuijsf:alarm</code> documentation. When the 
     * <code>severity</code> attribute is set in the tableColumn, the table 
     * component renders sort tool tips to indicate that the column will be sorted 
     * least/most severe first, and the table cell appears hightlighted according to 
     * the level of severity. This functionality is overridden by the 
     * <code>emptyCell</code> attribute.
     */
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    /**
     * Use the <code>sort</code> attribute to specify a FieldKey id or SortCriteria 
     * that defines the criteria to use for sorting the contents of a 
     * TableDataProvider. If SortCriteria is provided, the object is used for sorting 
     * as is. If an id is provided, a FieldIdSortCriteria is created for sorting. In 
     * addition, a value binding can also be used to sort on an object that is 
     * external to TableDataProvider, such as the selected state of a checkbox or 
     * radiobutton. When a value binding is used, a ValueBindingSortCriteria object 
     * is created for sorting. All sorting is based on the object type associated with 
     * the data element (for example, Boolean, Character, Comparator, Date, Number, 
     * and String). If the object type cannot be determined, the object is compared as 
     * a String. The <code>sort</code> attribute is required for a column to be shown 
     * as sortable.
     */
    @Property(name="sort", displayName="Sort Key", category="Data", 
        editorClassName="com.sun.rave.propertyeditors.StringPropertyEditor")
    private Object sort = null;

    /**
     * Use the <code>sort</code> attribute to specify a FieldKey id or SortCriteria 
     * that defines the criteria to use for sorting the contents of a 
     * TableDataProvider. If SortCriteria is provided, the object is used for sorting 
     * as is. If an id is provided, a FieldIdSortCriteria is created for sorting. In 
     * addition, a value binding can also be used to sort on an object that is 
     * external to TableDataProvider, such as the selected state of a checkbox or 
     * radiobutton. When a value binding is used, a ValueBindingSortCriteria object 
     * is created for sorting. All sorting is based on the object type associated with 
     * the data element (for example, Boolean, Character, Comparator, Date, Number, 
     * and String). If the object type cannot be determined, the object is compared as 
     * a String. The <code>sort</code> attribute is required for a column to be shown 
     * as sortable.
     */
    public Object getSort() {
        if (this.sort != null) {
            return this.sort;
        }
        ValueExpression _vb = getValueExpression("sort");
        if (_vb != null) {
            return (Object) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Use the <code>sort</code> attribute to specify a FieldKey id or SortCriteria 
     * that defines the criteria to use for sorting the contents of a 
     * TableDataProvider. If SortCriteria is provided, the object is used for sorting 
     * as is. If an id is provided, a FieldIdSortCriteria is created for sorting. In 
     * addition, a value binding can also be used to sort on an object that is 
     * external to TableDataProvider, such as the selected state of a checkbox or 
     * radiobutton. When a value binding is used, a ValueBindingSortCriteria object 
     * is created for sorting. All sorting is based on the object type associated with 
     * the data element (for example, Boolean, Character, Comparator, Date, Number, 
     * and String). If the object type cannot be determined, the object is compared as 
     * a String. The <code>sort</code> attribute is required for a column to be shown 
     * as sortable.
     */
    public void setSort(Object sort) {
        this.sort = sort;
    }

    /**
     * The theme identifier to use for the sort button that is displayed in the column 
     * header. Use this attribute to override the default image.
     */
    @Property(name="sortIcon", displayName="Sort Icon", category="Appearance")
    private String sortIcon = null;

    /**
     * The theme identifier to use for the sort button that is displayed in the column 
     * header. Use this attribute to override the default image.
     */
    public String getSortIcon() {
        if (this.sortIcon != null) {
            return this.sortIcon;
        }
        ValueExpression _vb = getValueExpression("sortIcon");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The theme identifier to use for the sort button that is displayed in the column 
     * header. Use this attribute to override the default image.
     */
    public void setSortIcon(String sortIcon) {
        this.sortIcon = sortIcon;
    }

    /**
     * Absolute or relative URL to the image used for the sort button that is 
     * displayed in the column header.
     */
    @Property(name="sortImageURL", displayName="Sort Image URL", category="Appearance", editorClassName="com.sun.rave.propertyeditors.ImageUrlPropertyEditor")
    private String sortImageURL = null;

    /**
     * Absolute or relative URL to the image used for the sort button that is 
     * displayed in the column header.
     */
    public String getSortImageURL() {
        if (this.sortImageURL != null) {
            return this.sortImageURL;
        }
        ValueExpression _vb = getValueExpression("sortImageURL");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Absolute or relative URL to the image used for the sort button that is 
     * displayed in the column header.
     */
    public void setSortImageURL(String sortImageURL) {
        this.sortImageURL = sortImageURL;
    }

    /**
     * Use the <code>spacerColumn</code> attribute to use the column as a blank column 
     * to enhance spacing in two or three column tables. When the 
     * <code>spacerColumn</code> attribute is true, the CSS styles applied to the 
     * column make it appear as if the columns are justified. If a column header and 
     * footer are required, provide an empty string for the <code>headerText</code> 
     * and <code>footerText</code> attributes. Set the <code>width</code> attribute to 
     * justify columns accordingly.
     */
    @Property(name="spacerColumn", displayName="Spacer Column", category="Layout")
    private boolean spacerColumn = false;
    private boolean spacerColumn_set = false;

    /**
     * Use the <code>spacerColumn</code> attribute to use the column as a blank column 
     * to enhance spacing in two or three column tables. When the 
     * <code>spacerColumn</code> attribute is true, the CSS styles applied to the 
     * column make it appear as if the columns are justified. If a column header and 
     * footer are required, provide an empty string for the <code>headerText</code> 
     * and <code>footerText</code> attributes. Set the <code>width</code> attribute to 
     * justify columns accordingly.
     */
    public boolean isSpacerColumn() {
        if (this.spacerColumn_set) {
            return this.spacerColumn;
        }
        ValueExpression _vb = getValueExpression("spacerColumn");
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
     * Use the <code>spacerColumn</code> attribute to use the column as a blank column 
     * to enhance spacing in two or three column tables. When the 
     * <code>spacerColumn</code> attribute is true, the CSS styles applied to the 
     * column make it appear as if the columns are justified. If a column header and 
     * footer are required, provide an empty string for the <code>headerText</code> 
     * and <code>footerText</code> attributes. Set the <code>width</code> attribute to 
     * justify columns accordingly.
     */
    public void setSpacerColumn(boolean spacerColumn) {
        this.spacerColumn = spacerColumn;
        this.spacerColumn_set = true;
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
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
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
    @Property(name="valign", displayName="Vertical Position", category="Appearance")
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
    @Property(name="width", displayName="Width", category="Layout")
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

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // State methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Restore the state of this component.
     */
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.abbr = (String) _values[1];
        this.align = (String) _values[2];
        this.alignKey = (Object) _values[3];
        this.axis = (String) _values[4];
        this.bgColor = (String) _values[5];
        this._char = (String) _values[6];
        this.charOff = (String) _values[7];
        this.colSpan = ((Integer) _values[8]).intValue();
        this.colSpan_set = ((Boolean) _values[9]).booleanValue();
        this.descending = ((Boolean) _values[10]).booleanValue();
        this.descending_set = ((Boolean) _values[11]).booleanValue();
        this.embeddedActions = ((Boolean) _values[12]).booleanValue();
        this.embeddedActions_set = ((Boolean) _values[13]).booleanValue();
        this.emptyCell = ((Boolean) _values[14]).booleanValue();
        this.emptyCell_set = ((Boolean) _values[15]).booleanValue();
        this.footerText = (String) _values[16];
        this.headerText = (String) _values[17];
        this.headers = (String) _values[18];
        this.height = (String) _values[19];
        this.noWrap = ((Boolean) _values[20]).booleanValue();
        this.noWrap_set = ((Boolean) _values[21]).booleanValue();
        this.onClick = (String) _values[22];
        this.onDblClick = (String) _values[23];
        this.onKeyDown = (String) _values[24];
        this.onKeyPress = (String) _values[25];
        this.onKeyUp = (String) _values[26];
        this.onMouseDown = (String) _values[27];
        this.onMouseMove = (String) _values[28];
        this.onMouseOut = (String) _values[29];
        this.onMouseOver = (String) _values[30];
        this.onMouseUp = (String) _values[31];
        this.rowHeader = ((Boolean) _values[32]).booleanValue();
        this.rowHeader_set = ((Boolean) _values[33]).booleanValue();
        this.rowSpan = ((Integer) _values[34]).intValue();
        this.rowSpan_set = ((Boolean) _values[35]).booleanValue();
        this.scope = (String) _values[36];
        this.selectId = (String) _values[37];
        this.severity = (String) _values[38];
        this.sort = (Object) _values[39];
        this.sortIcon = (String) _values[40];
        this.sortImageURL = (String) _values[41];
        this.spacerColumn = ((Boolean) _values[42]).booleanValue();
        this.spacerColumn_set = ((Boolean) _values[43]).booleanValue();
        this.style = (String) _values[44];
        this.styleClass = (String) _values[45];
        this.toolTip = (String) _values[46];
        this.valign = (String) _values[47];
        this.visible = ((Boolean) _values[48]).booleanValue();
        this.visible_set = ((Boolean) _values[49]).booleanValue();
        this.width = (String) _values[50];
    }

    /**
     * Save the state of this component.
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[51];
        _values[0] = super.saveState(_context);
        _values[1] = this.abbr;
        _values[2] = this.align;
        _values[3] = this.alignKey;
        _values[4] = this.axis;
        _values[5] = this.bgColor;
        _values[6] = this._char;
        _values[7] = this.charOff;
        _values[8] = new Integer(this.colSpan);
        _values[9] = this.colSpan_set ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = this.descending ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.descending_set ? Boolean.TRUE : Boolean.FALSE;
        _values[12] = this.embeddedActions ? Boolean.TRUE : Boolean.FALSE;
        _values[13] = this.embeddedActions_set ? Boolean.TRUE : Boolean.FALSE;
        _values[14] = this.emptyCell ? Boolean.TRUE : Boolean.FALSE;
        _values[15] = this.emptyCell_set ? Boolean.TRUE : Boolean.FALSE;
        _values[16] = this.footerText;
        _values[17] = this.headerText;
        _values[18] = this.headers;
        _values[19] = this.height;
        _values[20] = this.noWrap ? Boolean.TRUE : Boolean.FALSE;
        _values[21] = this.noWrap_set ? Boolean.TRUE : Boolean.FALSE;
        _values[22] = this.onClick;
        _values[23] = this.onDblClick;
        _values[24] = this.onKeyDown;
        _values[25] = this.onKeyPress;
        _values[26] = this.onKeyUp;
        _values[27] = this.onMouseDown;
        _values[28] = this.onMouseMove;
        _values[29] = this.onMouseOut;
        _values[30] = this.onMouseOver;
        _values[31] = this.onMouseUp;
        _values[32] = this.rowHeader ? Boolean.TRUE : Boolean.FALSE;
        _values[33] = this.rowHeader_set ? Boolean.TRUE : Boolean.FALSE;
        _values[34] = new Integer(this.rowSpan);
        _values[35] = this.rowSpan_set ? Boolean.TRUE : Boolean.FALSE;
        _values[36] = this.scope;
        _values[37] = this.selectId;
        _values[38] = this.severity;
        _values[39] = this.sort;
        _values[40] = this.sortIcon;
        _values[41] = this.sortImageURL;
        _values[42] = this.spacerColumn ? Boolean.TRUE : Boolean.FALSE;
        _values[43] = this.spacerColumn_set ? Boolean.TRUE : Boolean.FALSE;
        _values[44] = this.style;
        _values[45] = this.styleClass;
        _values[46] = this.toolTip;
        _values[47] = this.valign;
        _values[48] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[49] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[50] = this.width;
        return _values;
    }
}
