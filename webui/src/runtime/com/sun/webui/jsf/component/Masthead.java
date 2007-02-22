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

import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIOutput;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.jsf.util.ComponentUtilities;

/**
 * The Masthead component displays a masthead or page banner at the top of the
 * page.
 */
@Component(type="com.sun.webui.jsf.Masthead", family="com.sun.webui.jsf.Masthead", displayName="Masthead", tagName="masthead",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_masthead",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_masthead_props")
public class Masthead extends UIOutput implements NamingContainer {
    /**
     * Default constructor.
     */
    public Masthead() {
        super();
        setRendererType("com.sun.webui.jsf.Masthead");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Masthead";
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

    // Hide converter
    @Property(name="converter", isHidden=true, isAttribute=false)
    public Converter getConverter() {
        return super.getConverter();
    }
    
    // Hide value
    @Property(name="value", isHidden=true, isAttribute=false)
    public Object getValue() {
        return super.getValue();
    }

    /**
     * <p>A JavaServer Faces EL expression that resolves to a backing bean or a 
     * backing bean property that is an array of integers that specify the 
     * down, critical, major, and minor alarm counts.</p>
     */
    @Property(name="alarmCounts", displayName="Alarm Counts", category="Behavior", editorClassName="com.sun.rave.propertyeditors.binding.ValueBindingPropertyEditor")
    private int[] alarmCounts = null;

    /**
     * <p>A JavaServer Faces EL expression that resolves to a backing bean or a 
     * backing bean property that is an array of integers that specify the 
     * down, critical, major, and minor alarm counts.</p>
     */
    public int[] getAlarmCounts() {
        if (this.alarmCounts != null) {
            return this.alarmCounts;
        }
        ValueExpression _vb = getValueExpression("alarmCounts");
        if (_vb != null) {
            return (int[]) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>A JavaServer Faces EL expression that resolves to a backing bean or a 
     * backing bean property that is an array of integers that specify the 
     * down, critical, major, and minor alarm counts.</p>
     * @see #getAlarmCounts()
     */
    public void setAlarmCounts(int[] alarmCounts) {
        this.alarmCounts = alarmCounts;
    }

    /**
     * <p>The description to use for the Brand Image, used as alt text for the image.</p>
     */
    @Property(name="brandImageDescription", displayName="Brand Image Description", category="Appearance")
    private String brandImageDescription = null;

    /**
     * <p>The description to use for the Brand Image, used as alt text for the image.</p>
     */
    public String getBrandImageDescription() {
        if (this.brandImageDescription != null) {
            return this.brandImageDescription;
        }
        ValueExpression _vb = getValueExpression("brandImageDescription");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The description to use for the Brand Image, used as alt text for the image.</p>
     * @see #getBrandImageDescription()
     */
    public void setBrandImageDescription(String brandImageDescription) {
        this.brandImageDescription = brandImageDescription;
    }

    /**
     * <p>The height to use for the Brand Image, in pixels. Use this attribute 
     * along with the brandImageWidth attribute to specify dimensions of PNG images for use in Internet Explorer.</p>
     */
    @Property(name="brandImageHeight", displayName="Brand Image Height", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int brandImageHeight = Integer.MIN_VALUE;
    private boolean brandImageHeight_set = false;

    /**
     * <p>The height to use for the Brand Image, in pixels. Use this attribute 
     * along with the brandImageWidth attribute to specify dimensions of PNG images for use in Internet Explorer.</p>
     */
    public int getBrandImageHeight() {
        if (this.brandImageHeight_set) {
            return this.brandImageHeight;
        }
        ValueExpression _vb = getValueExpression("brandImageHeight");
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
     * <p>The height to use for the Brand Image, in pixels. Use this attribute 
     * along with the brandImageWidth attribute to specify dimensions of PNG images for use in Internet Explorer.</p>
     * @see #getBrandImageHeight()
     */
    public void setBrandImageHeight(int brandImageHeight) {
        this.brandImageHeight = brandImageHeight;
        this.brandImageHeight_set = true;
    }

    /**
     * <p>The url to the image file to use as the Brand Image. Use this attribute to override the  brand image that is set in the theme.</p>
     */
    @Property(name="brandImageURL", displayName="Brand Image URL", category="Navigation", editorClassName="com.sun.rave.propertyeditors.UrlPropertyEditor")
    private String brandImageURL = null;

    /**
     * <p>The url to the image file to use as the Brand Image. Use this attribute to override the  brand image that is set in the theme.</p>
     */
    public String getBrandImageURL() {
        if (this.brandImageURL != null) {
            return this.brandImageURL;
        }
        ValueExpression _vb = getValueExpression("brandImageURL");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The url to the image file to use as the Brand Image. Use this attribute to override the  brand image that is set in the theme.</p>
     * @see #getBrandImageURL()
     */
    public void setBrandImageURL(String brandImageURL) {
        this.brandImageURL = brandImageURL;
    }

    /**
     * <p>The width to use for the Brand Image, in pixels. 
     * Use this attribute when specifying the brandImageURL, along with the 
     * brandImageHeight attribute, to specify dimensions of  PNG images for 
     * use in Internet Explorer.</p>
     */
    @Property(name="brandImageWidth", displayName="Brand Image Width", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int brandImageWidth = Integer.MIN_VALUE;
    private boolean brandImageWidth_set = false;

    /**
     * <p>The width to use for the Brand Image, in pixels. 
     * Use this attribute when specifying the brandImageURL, along with the 
     * brandImageHeight attribute, to specify dimensions of  PNG images for 
     * use in Internet Explorer.</p>
     */
    public int getBrandImageWidth() {
        if (this.brandImageWidth_set) {
            return this.brandImageWidth;
        }
        ValueExpression _vb = getValueExpression("brandImageWidth");
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
     * <p>The width to use for the Brand Image, in pixels. 
     * Use this attribute when specifying the brandImageURL, along with the 
     * brandImageHeight attribute, to specify dimensions of  PNG images for 
     * use in Internet Explorer.</p>
     * @see #getBrandImageWidth()
     */
    public void setBrandImageWidth(int brandImageWidth) {
        this.brandImageWidth = brandImageWidth;
        this.brandImageWidth_set = true;
    }



    /**
     * <p>Set to true to display a date and time stamp in the status area.</p>
     */
    @Property(name="dateTime", displayName="Date Time", category="Advanced")
    private boolean dateTime = false;
    private boolean dateTime_set = false;

    /**
     * <p>Set to true to display a date and time stamp in the status area.</p>
     */
    public boolean isDateTime() {
        if (this.dateTime_set) {
            return this.dateTime;
        }
        ValueExpression _vb = getValueExpression("dateTime");
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
     * <p>Set to true to display a date and time stamp in the status area.</p>
     * @see #isDateTime()
     */
    public void setDateTime(boolean dateTime) {
        this.dateTime = dateTime;
        this.dateTime_set = true;
    }

    /**
     * <p>The number of currently executing jobs or tasks. A JavaServer Faces EL 
     * expression that resolves to a backing bean or a backing bean property 
     * that is an integer.</p>
     */
    @Property(name="jobCount", displayName="Job Count", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int jobCount = Integer.MIN_VALUE;
    private boolean jobCount_set = false;

    /**
     * <p>The number of currently executing jobs or tasks. A JavaServer Faces EL 
     * expression that resolves to a backing bean or a backing bean property 
     * that is an integer.</p>
     */
    public int getJobCount() {
        if (this.jobCount_set) {
            return this.jobCount;
        }
        ValueExpression _vb = getValueExpression("jobCount");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return -1;
    }

    /**
     * <p>The number of currently executing jobs or tasks. A JavaServer Faces EL 
     * expression that resolves to a backing bean or a backing bean property 
     * that is an integer.</p>
     * @see #getJobCount()
     */
    public void setJobCount(int jobCount) {
        this.jobCount = jobCount;
        this.jobCount_set = true;
    }

    /**
     * <p>Text to display for the notification info in the status area</p>
     */
    @Property(name="notificationMsg", displayName="Notification Message", category="Appearance")
    private String notificationMsg = null;

    /**
     * <p>Text to display for the notification info in the status area</p>
     */
    public String getNotificationMsg() {
        if (this.notificationMsg != null) {
            return this.notificationMsg;
        }
        ValueExpression _vb = getValueExpression("notificationMsg");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Text to display for the notification info in the status area</p>
     * @see #getNotificationMsg()
     */
    public void setNotificationMsg(String notificationMsg) {
        this.notificationMsg = notificationMsg;
    }

    /**
     * <p>The description for the product name image, used as alt text for the image.</p>
     */
    @Property(name="productImageDescription", displayName="Product Image Description", category="Appearance")
    private String productImageDescription = null;

    /**
     * <p>The description for the product name image, used as alt text for the image.</p>
     */
    public String getProductImageDescription() {
        if (this.productImageDescription != null) {
            return this.productImageDescription;
        }
        ValueExpression _vb = getValueExpression("productImageDescription");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The description for the product name Image, used as alt text for the image.</p>
     * @see #getProductImageDescription()
     */
    public void setProductImageDescription(String productImageDescription) {
        this.productImageDescription = productImageDescription;
    }

    /**
     * <p>The height to use for the Product Name Image, in pixels.  For mastheads that are used in secondary windows, 
     * you might need to specify the productImageHeight and productImageWidth 
     * for correct display on Internet Explorer.</p>
     */
    @Property(name="productImageHeight", displayName="Product Image Height", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int productImageHeight = Integer.MIN_VALUE;
    private boolean productImageHeight_set = false;

    /**
     * <p>The height to use for the Product Name Image, in pixels.  For mastheads that are used in secondary windows, 
     * you might need to specify the productImageHeight and productImageWidth 
     * for correct display on Internet Explorer.</p>
     */
    public int getProductImageHeight() {
        if (this.productImageHeight_set) {
            return this.productImageHeight;
        }
        ValueExpression _vb = getValueExpression("productImageHeight");
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
     * <p>The height to use for the Product Name Image, in pixels.  For mastheads that are used in secondary windows, 
     * you might need to specify the productImageHeight and productImageWidth 
     * for correct display on Internet Explorer.</p>
     * @see #getProductImageHeight()
     */
    public void setProductImageHeight(int productImageHeight) {
        this.productImageHeight = productImageHeight;
        this.productImageHeight_set = true;
    }

    /**
     * <p>The url to the image file to use for the Product Name Image.</p>
     */
    @Property(name="productImageURL", displayName="Product Image URL", category="Appearance", editorClassName="com.sun.rave.propertyeditors.UrlPropertyEditor")
    private String productImageURL = null;

    /**
     * <p>The url to the image file to use for the Product Name Image.</p>
     */
    public String getProductImageURL() {
        if (this.productImageURL != null) {
            return this.productImageURL;
        }
        ValueExpression _vb = getValueExpression("productImageURL");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The url to the image file to use for the Product Name Image.</p>
     * @see #getProductImageURL()
     */
    public void setProductImageURL(String productImageURL) {
        this.productImageURL = productImageURL;
    }

    /**
     * <p>The width to use for the Product Name Image, in pixels. For mastheads 
     * that are used in secondary windows, you might need to specify the 
     * productImageHeight and productImageWidth for correct display on 
     * Internet Explorer.</p>
     */
    @Property(name="productImageWidth", displayName="Product Image Width", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int productImageWidth = Integer.MIN_VALUE;
    private boolean productImageWidth_set = false;

    /**
     * <p>The width to use for the Product Name Image, in pixels. For mastheads 
     * that are used in secondary windows, you might need to specify the 
     * productImageHeight and productImageWidth for correct display on 
     * Internet Explorer.</p>
     */
    public int getProductImageWidth() {
        if (this.productImageWidth_set) {
            return this.productImageWidth;
        }
        ValueExpression _vb = getValueExpression("productImageWidth");
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
     * <p>The width to use for the Product Name Image, in pixels. For mastheads 
     * that are used in secondary windows, you might need to specify the 
     * productImageHeight and productImageWidth for correct display on 
     * Internet Explorer.</p>
     * @see #getProductImageWidth()
     */
    public void setProductImageWidth(int productImageWidth) {
        this.productImageWidth = productImageWidth;
        this.productImageWidth_set = true;
    }

    /**
     * <p>Set to true to indicate that the masthead is to be used in a secondary/popup window.</p>
     */
    @Property(name="secondary", displayName="Is Secondary", category="Advanced")
    private boolean secondary = false;
    private boolean secondary_set = false;

    /**
     * <p>Set to true to indicate that the masthead is to be used in a secondary/popup window.</p>
     */
    public boolean isSecondary() {
        if (this.secondary_set) {
            return this.secondary;
        }
        ValueExpression _vb = getValueExpression("secondary");
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
     * <p>Set to true to indicate that the masthead is to be used in a secondary/popup window.</p>
     * @see #isSecondary()
     */
    public void setSecondary(boolean secondary) {
        this.secondary = secondary;
        this.secondary_set = true;
    }

    /**
     * <p>Text to display for the current Server information, such as the name 
     * of the server whose data is being displayed.</p>
     */
    @Property(name="serverInfo", displayName="Current Server Info", category="Appearance")
    private String serverInfo = null;

    /**
     * <p>Text to display for the current Server information, such as the name 
     * of the server whose data is being displayed.</p>
     */
    public String getServerInfo() {
        if (this.serverInfo != null) {
            return this.serverInfo;
        }
        ValueExpression _vb = getValueExpression("serverInfo");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Text to display for the current Server information, such as the name 
     * of the server whose data is being displayed.</p>
     * @see #getServerInfo()
     */
    public void setServerInfo(String serverInfo) {
        this.serverInfo = serverInfo;
    }

    /**
     * <p>The label text to display for the current Server information.</p>
     */
    @Property(name="serverInfoLabel", displayName="Current Server Info Label", category="Appearance")
    private String serverInfoLabel = null;

    /**
     * <p>The label text to display for the current Server information.</p>
     */
    public String getServerInfoLabel() {
        if (this.serverInfoLabel != null) {
            return this.serverInfoLabel;
        }
        ValueExpression _vb = getValueExpression("serverInfoLabel");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The label text to display for the current Server information.</p>
     * @see #getServerInfoLabel()
     */
    public void setServerInfoLabel(String serverInfoLabel) {
        this.serverInfoLabel = serverInfoLabel;
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
     * <p>Text to display for the current User information, such as the name of 
     * the user who is running the application.</p>
     */
    @Property(name="userInfo", displayName="Current User Info", category="Appearance")
    private String userInfo = null;

    /**
     * <p>Text to display for the current User information, such as the name of 
     * the user who is running the application.</p>
     */
    public String getUserInfo() {
        if (this.userInfo != null) {
            return this.userInfo;
        }
        ValueExpression _vb = getValueExpression("userInfo");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Text to display for the current User information, such as the name of 
     * the user who is running the application.</p>
     * @see #getUserInfo()
     */
    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * <p>The label text to display for the current User information.</p>
     */
    @Property(name="userInfoLabel", displayName="Current User Info Label", category="Appearance")
    private String userInfoLabel = null;

    /**
     * <p>The label text to display for the current User information.</p>
     */
    public String getUserInfoLabel() {
        if (this.userInfoLabel != null) {
            return this.userInfoLabel;
        }
        ValueExpression _vb = getValueExpression("userInfoLabel");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The label text to display for the current User information.</p>
     * @see #getUserInfoLabel()
     */
    public void setUserInfoLabel(String userInfoLabel) {
        this.userInfoLabel = userInfoLabel;
    }
    
    /**
     * <p>Text to display for the current Role information, such as the name of 
     * the user who is running the application.</p>
     */
    @Property(name="roleInfo", displayName="Current Role Info", category="Appearance")
    private String roleInfo = null;

    /**
     * <p>Getter method to get Role information, such as the name of 
     * the role who is running the application.</p>
     */
    public String getRoleInfo() {
        if (this.roleInfo != null) {
            return this.roleInfo;
        }
        ValueExpression _vb = getValueExpression("roleInfo");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Set the current Role information, such as the name of 
     * the role who is running the application.</p>
     * @see #getRoleInfo()
     */
    public void setRoleInfo(String roleInfo) {
        this.roleInfo = roleInfo;
    }

    /**
     * <p>The label text to display for the current User information.</p>
     */
    @Property(name="roleInfoLabel", displayName="Current Role Info Label", category="Appearance")
    private String roleInfoLabel = null;

    /**
     * <p>The label text to display for the current Role information.</p>
     */
    public String getRoleInfoLabel() {
        if (this.roleInfoLabel != null) {
            return this.roleInfoLabel;
        }
        ValueExpression _vb = getValueExpression("roleInfoLabel");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The label text to display for the current Role information.</p>
     * @see #getRoleInfoLabel()
     */
    public void setRoleInfoLabel(String roleInfoLabel) {
        this.roleInfoLabel = roleInfoLabel;
    }
    
    /**
     * <p>A JavaServer Faces EL expression that resolves to a backing bean or 
     * a backing bean property that is an array of one or more custom 
     * Hyperlink components to display in the utility bar. The Hyperlink 
     * components must be given ids.</p>
     */
    @Property(name="utilities", displayName="Utility Bar Links", category="Behavior", editorClassName="com.sun.rave.propertyeditors.binding.ValueBindingPropertyEditor")
    private com.sun.webui.jsf.component.Hyperlink[] utilities = null;

    /**
     * <p>A JavaServer Faces EL expression that resolves to a backing bean or 
     * a backing bean property that is an array of one or more custom 
     * Hyperlink components to display in the utility bar. The Hyperlink 
     * components must be given ids.</p>
     */
    public com.sun.webui.jsf.component.Hyperlink[] getUtilities() {
        if (this.utilities != null) {
            return this.utilities;
        }
        ValueExpression _vb = getValueExpression("utilities");
        if (_vb != null) {
            return (com.sun.webui.jsf.component.Hyperlink[]) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>A JavaServer Faces EL expression that resolves to a backing bean or 
     * a backing bean property that is an array of one or more custom 
     * Hyperlink components to display in the utility bar. The Hyperlink 
     * components must be given ids.</p>
     * @see #getUtilities()
     */
    public void setUtilities(com.sun.webui.jsf.component.Hyperlink[] utilities) {
        this.utilities = utilities;
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
    @Property(name="visible", displayName="Visible")
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
 * <p> Get the masthead jobcount hyperlink</p>
 */
    public UIComponent getJobCountLink() {
        FacesContext context = FacesContext.getCurrentInstance();
        Theme theme = ThemeUtilities.getTheme(context);
        Hyperlink jcLink = (Hyperlink) ComponentUtilities.getPrivateFacet(this,
		"jobCountHyperlink", true);
        // (Hyperlink) Util.getChild(this, "jobCountHyperlink");
        if (jcLink == null) {
            jcLink = new Hyperlink();
            jcLink.setId(ComponentUtilities.createPrivateFacetId(this,
		"jobCountHyperlink"));
            ComponentUtilities.putPrivateFacet(this,
		"jobCountHyperlink", jcLink);
        }   // setId("jobCountHyperlink");
            jcLink.setStyleClass(theme.getStyleClass(ThemeStyles.MASTHEAD_PROGRESS_LINK));
            jcLink.setText(theme.getMessage("masthead.tasksRunning") + 
                " " + getJobCount());
            // this.getChildren().add(jcLink);
        // }
        return jcLink;
    }

    @Override
    public void processDecodes(FacesContext context) {
        if (this.isRendered()) {
            if (this.getUtilities() != null) {
                for (Hyperlink hyperlink : this.getUtilities()) {
                    UIComponent parent = hyperlink.getParent();
                    hyperlink.setParent(this);
                    hyperlink.processDecodes(context);
                    hyperlink.setParent(parent);
                }
            }
            super.processDecodes(context);
        }
    }
    
    @Override
    public void processValidators(FacesContext context) {
        if (this.isRendered()) {
            if (this.getUtilities() != null) {
                for (Hyperlink hyperlink : this.getUtilities()) {
                    UIComponent parent = hyperlink.getParent();
                    hyperlink.setParent(this);
                    hyperlink.processValidators(context);
                    hyperlink.setParent(parent);
                }
            }
            super.processValidators(context);
        }
    }

    @Override
    public void processUpdates(FacesContext context) {
        if (this.isRendered()) {
            if (this.getUtilities() != null) {
                for (Hyperlink hyperlink : this.getUtilities()) {
                    UIComponent parent = hyperlink.getParent();
                    hyperlink.setParent(this);
                    hyperlink.processUpdates(context);
                    hyperlink.setParent(parent);
                }
            }
            super.processUpdates(context);
        }
    }

   /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.alarmCounts = (int[]) _values[1];
        this.brandImageDescription = (String) _values[2];
        this.brandImageHeight = ((Integer) _values[3]).intValue();
        this.brandImageHeight_set = ((Boolean) _values[4]).booleanValue();
        this.brandImageURL = (String) _values[5];
        this.brandImageWidth = ((Integer) _values[6]).intValue();
        this.brandImageWidth_set = ((Boolean) _values[7]).booleanValue();
        this.dateTime = ((Boolean) _values[8]).booleanValue();
        this.dateTime_set = ((Boolean) _values[9]).booleanValue();
        this.jobCount = ((Integer) _values[10]).intValue();
        this.jobCount_set = ((Boolean) _values[11]).booleanValue();
        this.notificationMsg = (String) _values[12];
        this.productImageDescription = (String) _values[13];
        this.productImageHeight = ((Integer) _values[14]).intValue();
        this.productImageHeight_set = ((Boolean) _values[15]).booleanValue();
        this.productImageURL = (String) _values[16];
        this.productImageWidth = ((Integer) _values[17]).intValue();
        this.productImageWidth_set = ((Boolean) _values[18]).booleanValue();
        this.secondary = ((Boolean) _values[19]).booleanValue();
        this.secondary_set = ((Boolean) _values[20]).booleanValue();
        this.serverInfo = (String) _values[21];
        this.serverInfoLabel = (String) _values[22];
        this.style = (String) _values[23];
        this.styleClass = (String) _values[24];
        this.userInfo = (String) _values[25];
        this.userInfoLabel = (String) _values[26];
        // this.utilities = (com.sun.webui.jsf.component.Hyperlink[]) _values[27];
        if (_values[27] != null) {
            Object[] _linkValues = (Object[]) _values[27];
            this.utilities = new Hyperlink[_linkValues.length];
            for (int i = 0; i < _linkValues.length; i++) {
                this.utilities[i] = new Hyperlink();
                this.utilities[i].restoreState(_context, _linkValues[i]);
            }
        }

        this.visible = ((Boolean) _values[28]).booleanValue();
        this.visible_set = ((Boolean) _values[29]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[36];
        _values[0] = super.saveState(_context);
        _values[1] = this.alarmCounts;
        _values[2] = this.brandImageDescription;
        _values[3] = new Integer(this.brandImageHeight);
        _values[4] = this.brandImageHeight_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.brandImageURL;
        _values[6] = new Integer(this.brandImageWidth);
        _values[7] = this.brandImageWidth_set ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.dateTime ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = this.dateTime_set ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = new Integer(this.jobCount);
        _values[11] = this.jobCount_set ? Boolean.TRUE : Boolean.FALSE;
        _values[12] = this.notificationMsg;
        _values[13] = this.productImageDescription;
        _values[14] = new Integer(this.productImageHeight);
        _values[15] = this.productImageHeight_set ? Boolean.TRUE : Boolean.FALSE;
        _values[16] = this.productImageURL;
        _values[17] = new Integer(this.productImageWidth);
        _values[18] = this.productImageWidth_set ? Boolean.TRUE : Boolean.FALSE;
        _values[19] = this.secondary ? Boolean.TRUE : Boolean.FALSE;
        _values[20] = this.secondary_set ? Boolean.TRUE : Boolean.FALSE;
        _values[21] = this.serverInfo;
        _values[22] = this.serverInfoLabel;
        _values[23] = this.style;
        _values[34] = this.styleClass;
        _values[25] = this.userInfo;
        _values[26] = this.userInfoLabel;
        // _values[27] = this.utilities;
        if (this.utilities != null) {
            Object[] _linkValues = new Object[this.utilities.length];
            for (int i = 0; i < this.utilities.length; i++) {
                _linkValues[i] = this.utilities[i].saveState(_context);
            }
            _values[27] = _linkValues;
        }

        _values[28] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[29] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
