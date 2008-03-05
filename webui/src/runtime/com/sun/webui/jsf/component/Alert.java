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
import com.sun.webui.jsf.model.Indicator;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.ComponentUtilities;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * The Alert component is used to render an inline alert message.
 */
@Component(type="com.sun.webui.jsf.Alert", family="com.sun.webui.jsf.Alert",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_alert",
    tagRendererType="com.sun.webui.jsf.widget.Alert",    
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_alert_props")
public class Alert extends WebuiOutput implements NamingContainer, Comparator {
    /**
     * Facet name for alert image
     */
    public static final String ALERT_IMAGE_FACET = "alertImage"; //NOI18N
    /**
     * Facet name for the alert link
     */
    public static final String ALERT_LINK_FACET = "alertLink"; //NOI18N
    
    /**
     * The default "error" <code>Indicator</code>.
     */
    private static Indicator errorIndicator =
	new Indicator(ThemeImages.ERROR_ALERT_INDICATOR, "error", 100);
    /**
     * The default "warning" <code>Indicator</code>.
     */
    private static Indicator warnIndicator =
	new Indicator(ThemeImages.WARNING_ALERT_INDICATOR, "warning", 200);
    /**
     * The default "success" <code>Indicator</code>.
     */
    private static Indicator successIndicator =
	new Indicator(ThemeImages.SUCCESS_ALERT_INDICATOR, "success", 300);
    /**
     * The default "information" <code>Indicator</code>.
     */
    private static Indicator infoIndicator =
	new Indicator(ThemeImages.INFORMATION_ALERT_INDICATOR, "information", 400);
    
    /**
     * The list of default <code>Indicator</code>'s.
     */
    protected static List<Indicator> indicatorList =
	new ArrayList<Indicator>();

    static {
	indicatorList.add(errorIndicator);
	indicatorList.add(warnIndicator);
	indicatorList.add(successIndicator);
	indicatorList.add(infoIndicator);
    }

    /**
     * Default Constructor.
     */
    public Alert() {
        super();
        setRendererType("com.sun.webui.jsf.widget.Alert");
        
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Alert";
    }
      
    public String getRendererType() {
        // Ensure we have a valid Ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.Alert";
        }
        return super.getRendererType();
    }
    
    /**
     * Returns a cloned list of the default indicators that can
     * be modified without affecting the default list.
     * <p>
     * Typically this method is called by an application that
     * wants to add a an application defined <code>Indicator</code>
     * or replace a default <code>Indicator</code>. An application
     * first call<br/>
     * <code>List<Indicator> list = Alert.getDefaultIndicators();</code><br/>
     * and them add and/or replace an <code>Indicator</code>.<br/>
     * <code>list.add(appMostSevere); // Add an application indicator</code><br/>
     * To replace an indicator it must removed first. An indicator
     * is equal to another indicator if their "type" attributes are the
     * equal. If <code>appOkIndicator</code> has type = "ok" this call will
     * remove the default "ok" indicator.
     * <code>list.remove(appOkIndicator);// remove default</code><br/>
     * After the default indicator is removed add the replacement.
     * <code>list.add(appOkIndicator); // add the replacement</code><br/>
     * In order for the Alarm component to utilize the modified list
     * the application can have a value expression for the 
     * <code>indicators</code> property in which the application returns the
     * modified list, or calls the <code>setIndicators(list)</code>
     * method to assign the modified list.
     */
    public static List<Indicator> getDefaultIndicators() {
	List<Indicator> list = new ArrayList<Indicator>();
	list.addAll(indicatorList);
	return list;
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
            return (String) _vb.getValue(getFacesContext().getELContext());
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
     * Return a component that implements an alert image.
     * If a facet named <code>alertImage</code> is found
     * that component is returned.</br>
     * If a facet is not found an <code>Icon</code>
     * component instance is returned with the id</br>
     * <code>alertImage</code>. The <code>Icon</code> instance is
     * intialized with the values from
     * <p>
     * <ul>
     * <li><code>getType()</code> if a valid type, else 
     * <code>ThemeImages.ALERT_ERROR_LARGE</code></li>
     * <li><code>getAlt()</code> if not null</li>
     * </ul>
     * </p>
     * <p>
     * If a facet is not defined then the returned <code>Icon</code>
     * component is created every time this method is called.
     * </p>
     * @return - alertImage facet or an Icon instance
     */
    public UIComponent getAlertIcon() {

	UIComponent imageFacet =  getFacet(ALERT_IMAGE_FACET);
	if (imageFacet != null) {
	    return imageFacet;
	}

	// No need to save it as a facet.
	// Make sure to set parent, for clientId.
	//
	Theme theme =
	    ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
	String iconidentifier = getIconIdentifier();
	Icon icon = ThemeUtilities.getIcon(theme, iconidentifier);

	icon.setId(ALERT_IMAGE_FACET);
	icon.setParent(this);

	String iconalt = getAlt();
	if (iconalt != null) {
	    icon.setAlt(iconalt);
	}
	    
	return icon;
    }
    
    /**
     * Return a component that implements an alert link.
     * If a facet named <code>alertLink</code> is found
     * that component is returned.</br>
     * If a facet is not defined and <code>getLinkText()</code>
     * returns non null, an <code>IconHyperlink</code>
     * component instance is created with the id</br>
     * <code>alertLink</code> and initialized with the values from:
     * <ul>
     * <li><code>getLinkTarget()</code></li>
     * <li><code>getLinkText()</code></li>
     * <li><code>getLinkToolTip()</code></li>
     * <li><code>getLinkURL()</code></li>
     * <li><code>getLinkAction()</code></li>
     * <li><code>ThemeImages.HREF_LINK</code></li>
     * </ul>
     * If <code>getLinkText()</code> returns null, null is returned.
     * <p>
     * If a facet is not defined and if a <code>IconHyperlink</code>
     * component is created, it is reinitialized every time this method
     * is called. 
     * </p>
     * @return - alertLink facet or a IconHyperlink instance or null
     */
    public UIComponent getAlertLink() {
 	UIComponent linkFacet =  getFacet(ALERT_LINK_FACET);
	if (linkFacet != null) {
	    return linkFacet;
	}

	// Get the private facet.
	IconHyperlink h = (IconHyperlink)ComponentUtilities.getPrivateFacet(
		this, ALERT_LINK_FACET, false);	

	// If getLinkText() returns null, null is returned. 
	//
	String linktext = getLinkText();
	if (linktext == null) {
	    // Remove the previously created private facet.
	    if (h != null) {
	        ComponentUtilities.removePrivateFacet(this, ALERT_LINK_FACET);
	    }
	    return null;
	}

	// Return the private facet or create one, but initialize
	// it every time, except for the id.
	//
	if (h == null) {
	    h = new IconHyperlink();
	    h.setId(ALERT_LINK_FACET); // NOI18N
	    ComponentUtilities.putPrivateFacet(this, ALERT_LINK_FACET, h);
	}

	h.setIcon(ThemeImages.HREF_LINK);
	h.setTarget(getLinkTarget());
	h.setText(linktext);
	h.setToolTip(getLinkToolTip());
	h.setUrl(getLinkURL());
	
	MethodExpression action = getLinkActionExpression();
	if (action != null) {
	      h.setActionExpression(action);
	}

        return h;
    }

    /**
     * Returns IconIdentifier based on type attribute.
     */ 
    private String getIconIdentifier() {
        
        String type = getType();
        if (type != null) {
            String lower = type.toLowerCase();

            if (lower.startsWith("warn")) { // NOI18N
                return ThemeImages.ALERT_WARNING_LARGE;
            } else if (lower.startsWith("ques")) { // NOI18N
                return ThemeImages.ALERT_HELP_LARGE;
            } else if (lower.startsWith("info")) { // NOI18N
                return ThemeImages.ALERT_INFO_LARGE;
            } else if (lower.startsWith("succ")) { // NOI18N
		return ThemeImages.ALERT_SUCCESS_LARGE;
	    }
        }
        return ThemeImages.ALERT_ERROR_LARGE;
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

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
     * <p>Alternative textual description of the image rendered by this component. The alt
     * text can be used by screen readers and in tool tips, and when image display is turned off in
     * the web browser.</p>
     */
    @Property(name="alt", displayName="Alt Text", category="Accessibility")
    private String alt = null;

    /**
     * <p>Alternative textual description of the image rendered by this component. The alt
     * text can be used by screen readers and in tool tips, and when image display is turned off in
     * the web browser.</p>
     */
    public String getAlt() {
        if (this.alt != null) {
            return this.alt;
        }
        ValueExpression _vb = getValueExpression("alt");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Alternative textual description of the image rendered by this component. The alt
     * text can be used by screen readers and in tool tips, and when image display is turned off in
     * the web browser.</p>
     * @see #getAlt()
     */
    public void setAlt(String alt) {
        this.alt = alt;
    }
   
    /**
     * <p>Optional detailed message text for the Alert that can include more information about the alert and instructions for what to do about the alert.</p>
     */ 
    @Property(name="detail", displayName="Detail Message", category="Appearance")
    private String detail = null;

    /**
     * <p>Optional detailed message text for the Alert that can include more information about the alert and instructions for what to do about the alert.</p>
     */
    public String getDetail() {
        if (this.detail != null) {
            return this.detail;
        }
        ValueExpression _vb = getValueExpression("detail");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Optional detailed message text for the Alert that can include more information about the alert and instructions for what to do about the alert.</p>
     * @see #getDetail()
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }  

    /**
     * <p>The linkActionExpression attribute is used to specify the action to take when the 
     * embedded hyperlink component is activated by the user. The value of the 
     * linkActionExpression attribute must be one of the following: <br>
     * <ul>
     * <li>
     * An outcome string, used to indicate which page to display next, as defined by a
     * navigation rule in the application configuration resource file<code>(faces-config.xml)</code>.
     * </li>
     * <li>A JavaServer Faces EL expression that resolves
     * to a backing bean method. The method must take no parameters and return
     * an outcome string. The class that defines the method must implement the<br>
     * <code>java.io.Serializable</code> interface or <code>javax.faces.component.StateHolder</code>
     * interface.</li>
     * </ul></p>
     */
    @Property(name="linkActionExpression", isHidden=true, displayName="Link Action Method Expression", category="Navigation")
    @Property.Method(signature="java.lang.String action()")
    private javax.el.MethodExpression linkActionExpression = null;

    /**
     * <p>The linkActionExpression attribute is used to specify the action to take when the 
     * embedded hyperlink component is activated by the user. The value of the 
     * linkActionExpression attribute must be one of the following: <br>
     * <ul>
     * <li>
     * An outcome string, used to indicate which page to display next, as defined by a
     * navigation rule in the application configuration resource file<code>(faces-config.xml)</code>.
     * </li>
     * <li>A JavaServer Faces EL expression that resolves
     * to a backing bean method. The method must take no parameters and return
     * an outcome string. The class that defines the method must implement the<br>
     * <code>java.io.Serializable</code> interface or <code>javax.faces.component.StateHolder</code>
     * interface.</li>
     * </ul></p>
     */
    public javax.el.MethodExpression getLinkActionExpression() {
        if (this.linkActionExpression != null) {
            return this.linkActionExpression;
        }
        ValueExpression _vb = getValueExpression("linkActionExpression");
        if (_vb != null) {
            return (javax.el.MethodExpression) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The linkActionExpression attribute is used to specify the action to take when the 
     * embedded hyperlink component is activated by the user. The value of the 
     * linkActionExpression attribute must be one of the following: <br>
     * <ul>
     * <li>
     * An outcome string, used to indicate which page to display next, as defined by a
     * navigation rule in the application configuration resource file<code>(faces-config.xml)</code>.
     * </li>
     * <li>A JavaServer Faces EL expression that resolves
     * to a backing bean method. The method must take no parameters and return
     * an outcome string. The class that defines the method must implement the<br>
     * <code>java.io.Serializable</code> interface or <code>javax.faces.component.StateHolder</code>
     * interface.</li>
     * </ul></p>
     * @see #getLinkActionExpression()
     */
    public void setLinkActionExpression(javax.el.MethodExpression linkActionExpression) {
        this.linkActionExpression = linkActionExpression;
    }

    /**
     * <p>The window (target) in which to load the link that is specified with linkText.</p>
     */
    @Property(name="linkTarget", displayName="Link Target", category="Navigation")
    private String linkTarget = null;

    /**
     * <p>The window (target) in which to load the link that is specified with linkText.</p>
     */
    public String getLinkTarget() {
        if (this.linkTarget != null) {
            return this.linkTarget;
        }
        ValueExpression _vb = getValueExpression("linkTarget");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The window (target) in which to load the link that is specified with linkText.</p>
     * @see #getLinkTarget()
     */
    public void setLinkTarget(String linkTarget) {
        this.linkTarget = linkTarget;
    }

    /**
     * <p>The text for an optional link that is appended to the detail message.</p>
     */
    @Property(name="linkText", displayName="Link Text", category="Appearance")
    private String linkText = null;

    /**
     * <p>The text for an optional link that is appended to the detail message.</p>
     */
    public String getLinkText() {
        if (this.linkText != null) {
            return this.linkText;
        }
        ValueExpression _vb = getValueExpression("linkText");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The text for an optional link that is appended to the detail message.</p>
     * @see #getLinkText()
     */
    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }

    /**
     * <p>Sets the value of the title attribute for the HTML element. The specified text will display as a tooltip if the mouse cursor hovers over the link that is specified with linkText.</p>
     */
    @Property(name="linkToolTip", displayName="Link Tooltip", category="Behavior")
    private String linkToolTip = null;

    /**
     * <p>Sets the value of the title attribute for the HTML element. The specified text will display as a tooltip if the mouse cursor hovers over the link that is specified with linkText.</p>
     */
    public String getLinkToolTip() {
        if (this.linkToolTip != null) {
            return this.linkToolTip;
        }
        ValueExpression _vb = getValueExpression("linkToolTip");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Sets the value of the title attribute for the HTML element. The specified text will display as a tooltip if the mouse cursor hovers over the link that is specified with linkText.</p>
     * @see #getLinkToolTip()
     */
    public void setLinkToolTip(String linkToolTip) {
        this.linkToolTip = linkToolTip;
    }

    /**
     * <p>Absolute, relative, or context relative (starting with "/") URL to the 
     * resource to navigate to when the link that is specified with linkText is selected.</p>
     */
    @Property(name="linkURL", displayName="Link URL", category="Navigation", editorClassName="com.sun.rave.propertyeditors.UrlPropertyEditor")
    private String linkURL = null;

    /**
     * <p>Absolute, relative, or context relative (starting with "/") URL to the 
     * resource to navigate to when the link that is specified with linkText is selected.</p>
     */
    public String getLinkURL() {
        if (this.linkURL != null) {
            return this.linkURL;
        }
        ValueExpression _vb = getValueExpression("linkURL");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Absolute, relative, or context relative (starting with "/") URL to the 
     * resource to navigate to when the link that is specified with linkText is selected.</p>
     * @see #getLinkURL()
     */
    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }

    /**
     * <p>CSS style or styles to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name="style", displayName="CSS Style(s)", category="Appearance", editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;

    /**
     * <p>CSS style or styles to be applied to the outermost HTML element when this 
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
     * <p>CSS style or styles to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     * @see #getStyle()
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * <p>CSS style class or classes to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;

    /**
     * <p>CSS style class or classes to be applied to the outermost HTML element when this 
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
     * <p>CSS style class or classes to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * <p>Summary message text for the alert. This brief message is prominently 
     * displayed next to the icon.</p>
     */
    @Property(name="summary", displayName="Summary Message", category="Appearance", isDefault=true)
    private String summary = null;

    /**
     * <p>Summary message text for the alert. This brief message is prominently 
     * displayed next to the icon.</p>
     */
    public String getSummary() {
        if (this.summary != null) {
            return this.summary;
        }
        ValueExpression _vb = getValueExpression("summary");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Summary message text for the alert. This brief message is prominently 
     * displayed next to the icon.</p>
     * @see #getSummary()
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     */
    @Property(name="tabIndex", displayName="Tab Index", category="Accessibility", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
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
     * <p>The type or category of alert. This attribute can be set to
     * "information", "success", "warning" or "error". The selection determines
     * which icon is rendered for the alert.</p>
     */
    @Property(name="type", displayName="Alert Type", category="Advanced")
    private String type = null;

    /**
     * <p>The type or category of alert. This attribute can be set to
     * "information", "success", "warning", "error" or any custom defined type specified for Indicator.
     * The selection determines
     * which icon is rendered for the alert.</p>
     */
    public String getType() {
        if (this.type != null) {
            return this.type;
        }
        ValueExpression _vb = getValueExpression("type");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return "error";
    }

    /**
     * <p>The type or category of alert. This attribute can be set to  
     * "information", "success", "warning", "error" or any custom defined type specified for Indicator.
     * The selection determines
     * which icon is rendered for the alert.</p>
     * @see #getType()
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * <p>Indicates whether the component should be
     * viewable by the user in the rendered HTML page. If this setting is false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, this setting is true, so
     * HTML for the component HTML is included and visible to the user. If the
     * Alert component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    @Property(name="visible", displayName="Visible", category="Behavior")
    private boolean visible = false;
    private boolean visible_set = false;

    /**
     * <p>Indicates whether the component should be
     * viewable by the user in the rendered HTML page. If this setting is false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, this setting is true, so
     * HTML for the component HTML is included and visible to the user. If the
     * Alert component is not visible, it can still be processed on subsequent form
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
     * <p>Indicates whether the component should be
     * viewable by the user in the rendered HTML page. If this setting is false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default,this setting is true, so
     * HTML for the component HTML is included and visible to the user. If the
     * Alert component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     * @see #isVisible()
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }

    /**
     * <p>A developer define types of Alert that can be set to an array Indicators
     *  where Indicator holds
     *  the custom defined type and associated image.</p>
     */
    @Property(name="indicators", isHidden=true, displayName="Indicators", category="Behavior", editorClassName="com.sun.rave.propertyeditors.binding.ValueBindingPropertyEditor")
    // jasper compiler does not recognize generics which causes error. So, generics is not used 
    // here for indicators. 
    private List indicators;

    /**
     * Return a <code>List</code> of <code>Indicators</code> supported
     * by this <code>Alert</code>. If <code>indicators</code> has not been
     * set explicitly by the application and if there is no value
     * expression, a list of default alert indicators obtained by
     * calling <code>Alert.getDefaultIndicators()</code> is
     * returned. If the application modifies this list, it must call
     * <code>setIndicators</code> or add a value expression that resolves
     * to the modified list in order to persist the change, otherwise
     * this method will continue to return a list of default
     * alert indicators.
     */
    public List<Indicator> getIndicators() {
        if (this.indicators != null) {
            return this.indicators;
        }
        ValueExpression _vb = getValueExpression("indicators");
        if (_vb != null) {
            return (List<Indicator>) _vb.getValue(getFacesContext().getELContext());
        }
	// If we get here we know that the developer has not
	// assigned a list of indicators. Return null, client-side widget creates the
        // default indicators.	
	return null;
    }
    /**
     * Set the list of indicators supported by this alert. If this
     * method is called, any value expression defined for this property
     * are ignored. Call this method to persist changes to a 
     * default list of alert indicators.
     */
    public void setIndicators(List<Indicator> indicators) {
	this.indicators = indicators;
    }
    
    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.alt = (String) _values[1];
        this.detail = (String) _values[2];
        this.linkActionExpression = (javax.el.MethodExpression) restoreAttachedState(_context, _values[3]);
        this.linkTarget = (String) _values[4];
        this.linkText = (String) _values[5];
        this.linkToolTip = (String) _values[6];
        this.linkURL = (String) _values[7];
        this.style = (String) _values[8];
        this.styleClass = (String) _values[9];
        this.summary = (String) _values[10];
        this.tabIndex = ((Integer) _values[11]).intValue();
        this.tabIndex_set = ((Boolean) _values[12]).booleanValue();
        this.type = (String) _values[13];
        this.visible = ((Boolean) _values[14]).booleanValue();
        this.visible_set = ((Boolean) _values[15]).booleanValue();
        this.htmlTemplate = (String) _values[16];
        this.indicators = (List) _values[17];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[18];
        _values[0] = super.saveState(_context);
        _values[1] = this.alt;
        _values[2] = this.detail;
        _values[3] = saveAttachedState(_context, linkActionExpression);
        _values[4] = this.linkTarget;
        _values[5] = this.linkText;
        _values[6] = this.linkToolTip;
        _values[7] = this.linkURL;
        _values[8] = this.style;
        _values[9] = this.styleClass;
        _values[10] = this.summary;
        _values[11] = new Integer(this.tabIndex);
        _values[12] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        _values[13] = this.type;
        _values[14] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[15] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[16] = this.htmlTemplate;
        _values[17] = this.indicators;    
        return _values;
    }
    
/**
     * Return zero if the severity of <code>o1</code> equals <code>o2</code>,
     * negative 1 if the severity <code>o1</code> is less than <code>o2</code>,
     * positive 1 if the severity <code>o1</code> is greater than 
     * <code>o2</code>.
     */
    public int compare(Object o1, Object o2) {
	// Optimization. By definition if the severities or type's 
	// are equal the Alerts are equal.
	//
	String type1 = ((Alert)o1).getType();
	String type2 = ((Alert)o2).getType();
	if (type1 != null && type1.equals(type2)) {
	    return 0;
	}

	// Here we need to get the indicator from each object
	// and based on the severity level, and then call the
	// compareTo method. This method should have been from the
	// Comparable interface and not the Comparator.
	//
	List<Indicator> indList = getIndicators();
	Indicator ind1 = getIndicator(indList, type1);
	
	Indicator ind2 = getIndicator(indList, type2);

	if (ind1 != null && ind2 != null) {
	    return ind1.compareTo(ind2);
	}

	if (ind1 == null && ind2 != null) {
	    return -1;
	}
        
        if (ind1 != null && ind2 == null) {
            return 1;
        }

	//ind1 and ind2 are null
	//
	return 0;
    }

    /**
     * Returns Indicator for the specified type.
     * If type is null return null.
     */
    private Indicator getIndicator(List<Indicator> indList, String type) {
	if (indList != null) {
            Iterator<Indicator> iter1 = indList.iterator();
            if (type != null) {
                while (iter1.hasNext()) {
                    Indicator ind = (Indicator)iter1.next();
                    if (type.equals(ind.getType())) {
                        return ind;
                    }
                }
            }
        }
	return null;
    }

    /**
     * Override equals()
     * returns false if object is not an instance of Alarm.
     */
    public boolean equals(Object o) {
	if (!(o instanceof Alert)) {
	    return false;
	}
	return compare(this, o) == 0 ? true : false;
    }
}
