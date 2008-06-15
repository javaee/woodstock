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

package com.sun.webui.jsf.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpServletResponse;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.sun.webui.html.HTMLAttributes;
import com.sun.webui.html.HTMLElements;
import com.sun.webui.jsf.component.ComplexComponent;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.theme.Theme;
import java.text.MessageFormat;

/**
 * This class provides common methods for renderers.
 */
public class RenderingUtilities {
    /** Creates a new instance of RenderingUtilities. */
    public RenderingUtilities() {
    }

    /**
     * Render a component.
     * @param component The component to render
     * @param context The FacesContext of the request
     *
     */
    public static void renderComponent(UIComponent component,
            FacesContext context) throws IOException {
        // This is to workaround a JSF bug with tables where client ids are
        // cached, especially for facets. By setting the id, we ensure ids are
        // recreated for each row of the table.
        String id = component.getId();
        if (id != null) {
            component.setId(id);
        }
        // Calling encodeBegin, encodeChildren, and encodeEnd directly is no
        // longer necessary here.
        component.encodeAll(context);
    }

    /**
     * This method goes through an array of possible attribute names,
     * evaluates if they have been set on the component, and writes
     * them out using the specified writer.
     * @param component The component being rendered
     * @param writer The writer to use to write the attributes
     * @param possibleAttributes String attributes that are treated as
     * passthrough for this component
     */
    public static void writeStringAttributes(UIComponent component,
            ResponseWriter writer, String[] possibleAttributes) throws IOException {
        // Get the rest of the component attributes and display them
        Map attributes = component.getAttributes();

        int numNames = possibleAttributes.length;
        String attributeName = null;
        Object attributeValue;

        for (int counter = 0; counter < numNames; counter++) {
            attributeName = possibleAttributes[counter];
            attributeValue = attributes.get(attributeName);
            if (attributeValue != null) {
                writer.writeAttribute(attributeName.toLowerCase(),
                        String.valueOf(attributeValue),
                        attributeName);
            }
        }
    }

    /**
     * Add any attributes on the specified list directly to the specified
     * ResponseWriter for which the specified UIComponent has a non-null String
     * value. This method may be used to "pass through" commonly used attribute
     * name/value pairs with a minimum of code. Attribute names are converted to
     * lower case in the rendered output. Any name/value pairs in the extraHtml
     * String shall take precedence over attribute values.
     *
     * @param context FacesContext for the current request.
     * @param component EditableValueHolder component whose submitted value is 
     * to be stored.
     * @param writer ResponseWriter to which the element start should be rendered.
     * @param names List of attribute names to be passed through.
     * @param extraHtml Extra name/value pairs to be rendered.
     *
     * @exception IOException if an input/output error occurs
     */
    public static void writeStringAttributes(UIComponent component,
        ResponseWriter writer, String names[], String extraHtml)
            throws IOException {
        if (component == null || names == null) {
            return;
        }
        Map attributes = component.getAttributes();
        Object value;
        for (int i = 0; i < names.length; i++) {
            // Special case for names matching "valign" instead of "align".
            if (extraHtml == null 
                    || extraHtml.indexOf(names[i] + "=") != 0
                    && extraHtml.indexOf(" " + names[i] + "=") == -1) {
                value = attributes.get(names[i]);
                if (value != null) {
                    if (value instanceof String) {
                        writer.writeAttribute(names[i].toLowerCase(),
                            (String) value, names[i]);
                    } else {
                        writer.writeAttribute(names[i].toLowerCase(),
                            value.toString(), names[i]);
                    }
                }
            }
        }
        // Render extra HTML attributes.
        renderExtraHtmlAttributes(writer, extraHtml);
    }

    /**
     * This method will output a hidden field for use with Params and components
     * that need to submit a value through a hidden field.
     * Note: The name of the hidden field will be written as is.  For Params
     * no encoding inside the form is done.  This is intentional.
     * @param writer The writer to use to write the attributes
     * @param id The identifier of the hidden field.
     * passthrough for this component
     */
    public static void renderHiddenField(UIComponent component,
            ResponseWriter writer, String id, String value) throws IOException {
        if (id == null) {
            // TODO: when we figure out our exception string strategy, fix this
            throw new IllegalArgumentException(
                "An f:param tag had a null name attribute");
        }

        writer.startElement("input", component);  //NOI18N
        writer.writeAttribute("id", id, null); //NOI18N
        writer.writeAttribute("name", id, null); //NOI18N
        if (value != null) {
            writer.writeAttribute("value", value, null); //NOI18N
        }
        writer.writeAttribute("type", "hidden", null); //NOI18N
        writer.endElement("input"); //NOI18N
    }

    /**
     * Decode a hidden field.
     * @param context The FacesContext associated with this request
     * @param parameterMapId  Identifies the value in the parameters map
     */
    public static String decodeHiddenField(FacesContext context,
	    String parameterMapId) {
        
        Map params = context.getExternalContext().getRequestParameterMap();
        Object valueObject = params.get(parameterMapId);
        return (String)valueObject;
    }

    /**
     * <p>Return a space-separated list of CSS style classes to render for
     * this component, or <code>null</code> for none.</p>
     *
     * @param component <code>UIComponent</code> for which to calculate classes
     * @param styles Additional styles specified by the renderer
     */
    public static String getStyleClasses(FacesContext context,
            UIComponent component, String styles) {
        String styleClass = (String)
	    component.getAttributes().get("styleClass");

        boolean componentNotVisible = !isVisible(component);

        if (componentNotVisible) {
            String hiddenStyleClass = ThemeUtilities.getTheme(context)
            .getStyleClass(ThemeStyles.HIDDEN);
            if (styleClass != null) {
                styleClass += " "  + hiddenStyleClass;
            } else {
                styleClass = hiddenStyleClass;
            }
        }

        if (styleClass != null) {
            if (styles != null) {
                return styleClass + " " + styles;
            } else {
                return styleClass;
            }
        } else {
            if (styles != null) {
                return styles;
            } else {
                return null;
            }
        }
    }

    /**
     *
     */
    public static void renderStyleClass(FacesContext context,
        ResponseWriter writer, UIComponent component, String extraStyles) 
            throws IOException {
        String classes = getStyleClasses(context, component, extraStyles);
        if (classes != null) {
            writer.writeAttribute("class", classes, "styleClass");
        }
    }

    /**
     * Helper method to render style classes when name/value pairs are given
     * via an extraHtml String. This method will append the given style to the
     * class name/value pair found in the extraHtml String. The class name/value
     * is removed from the returned extraHtml String so that developers may
     * invoke the writeStringAttributes method without rendering the style
     * class, again.
     *
     * @param context FacesContext for the current request.
     * @param component The UIComponent component to be rendered.
     * @param writer ResponseWriter to which the element start should be rendered.
     * @param style The style to append to the component's styleClass property.
     * @param extraHtml Extra name/value pairs to be rendered.
     */
    public static String renderStyleClass(FacesContext context, 
        ResponseWriter writer, UIComponent component, String styleClass, 
            String extraHtml) throws IOException {
        if (styleClass != null) {
            int first = -1;
            if (extraHtml != null 
                    && (first = extraHtml.indexOf("class=")) != -1) {
                try {
                    // Concat given class value with styleClass attribute.
                    int quote = first + 6; // Quote char index.
                    char ch = extraHtml.charAt(quote); // Get quote char.
                    int last = extraHtml.indexOf(ch, quote + 1); // Last index.
                    String s = extraHtml.substring(first, last + 1); // Get name/value pair
                    extraHtml = extraHtml.replaceAll(s, ""); // Remove substring.
                    s = s.substring(7, s.length() - 1); // Remove quote chars.
                    styleClass =  s + " " + styleClass; // Append styleClass.
                } catch (IndexOutOfBoundsException e) {}
            }
            renderStyleClass(context, writer, component, styleClass);
        }
        return extraHtml;
    }

    /**
     *
     */
    public static boolean isPortlet(FacesContext context) {
        if (context.getExternalContext().getContext()
		instanceof ServletContext) {
            return false;
        }
        return true;
    }

    /**
     * Get the client ID of the last component to have focus.
     * @deprecated
     * @see com.sun.webui.jsf.component.Body.getRequestFocusElementId(FacesContext)
     */
    public static String getLastClientID(FacesContext context) {
	return FocusManager.getRequestFocusElementId(context);
    }

    /**
     * Set the client ID of the last component to have focus.
     * @deprecated
     * @see com.sun.webui.jsf.component.Body.setRequestFocusElementId(FacesContext,String)
     */
    public static void setLastClientID(FacesContext context,
            String clientId) {
	FocusManager.setRequestFocusElementId(context, clientId);
    }
    
    /**
     * Return the focus element id for <code>component</code>.
     * The returned id should be the id of an HTML element suitable to 
     * receive the focus.
     * If <code>component</code> is a <code>ComplexComponent</code>
     * call <code>component.getFocusElementId</code> else return
     * <code>component.getClientId</code>.
     */
    public static String getFocusElementId(FacesContext context,
	    UIComponent component) {
	if (component instanceof ComplexComponent) {
	    return ((ComplexComponent)component).getFocusElementId(context);
	} else {
	    return component.getClientId(context);
	}
    }

    /**
     * Helper function to render a transparent spacer image.
     *
     * @param writer The current ResponseWriter
     * @param component The uicomponent
     * @param height The value to use for the image height attribute
     * @param width The value to use for the image width attribute
     */
    public static void renderSpacer(ResponseWriter writer, UIComponent component, 
            String dotSrc, int height, int width) throws IOException {
        if (height == 0 && width == 0) {
            return;
        }
        writer.startElement("img", component);
        writer.writeAttribute("src", dotSrc, null); // NOI18N
        writer.writeAttribute("alt", "", null); // NOI18N
        writer.writeAttribute("border", "0", null); // NOI18N
        writer.writeAttribute("height", new Integer(height), null); // NOI18N
        writer.writeAttribute("width", new Integer(width), null); // NOI18N
        writer.endElement("img"); // NOI18N
    }    

    /**
     * Helper function to render a transparent spacer image.
     *
     *
     * @param writer The current ResponseWriter
     * @param component The uicomponent
     * @param height The value to use for the image height attribute
     * @param width The value to use for the image width attribute
     */
    public static void renderSpacer(FacesContext context, ResponseWriter writer,
            UIComponent component, int height, int width) throws IOException {
        if (height == 0 && width == 0) {
            return;
        }
        Theme theme = ThemeUtilities.getTheme(context);
        String dotSrc = theme.getImagePath(ThemeImages.DOT);
        renderSpacer(writer, component, dotSrc, height, width);
    }   

    /**
     * Helper function to render theme stylesheet link(s)
     *
     *
     * @param context containing theme
     * @param writer The current ResponseWriter
     * @param component The uicomponent
     */
    public static void renderStyleSheetLink(UIComponent component, Theme theme, 
            FacesContext context, ResponseWriter writer) throws IOException {
        //Master.
        //String master = theme.getPathToMasterStylesheet(); 
	String[] files = theme.getMasterStylesheets();
	if (files != null &&  files.length != 0) {
	    renderStylesheetLinks(files, component, writer);
	}
	// Global stylesheets
	//
        files = theme.getGlobalStylesheets();
        if (files != null &&  files.length != 0) {
	    renderStylesheetLinks(files, component, writer);
	}
        // browser specific stylesheets
	//
	ClientType clientType = ClientSniffer.getClientType(context);
        files = theme.getStylesheets(clientType.toString());
        if (files != null &&  files.length != 0) {
	    renderStylesheetLinks(files, component, writer);
        }
    }

    /**
     * Render <code>link</code> elments for <code>css</code> files.
     */
    public static void renderStylesheetLinks(String[] css,
	    UIComponent component, ResponseWriter writer) throws IOException {
	for (int i = 0; i < css.length; i++) {
            if (JavaScriptUtilities.isDebug()) {
                writer.write("\n");
            }
            writer.startElement(HTMLElements.LINK, component); //NOI18N
            writer.writeAttribute(HTMLAttributes.REL, "stylesheet", null); //NOI18N
            writer.writeAttribute(HTMLAttributes.TYPE, "text/css", null); //NOI18N
            writer.writeURIAttribute(HTMLAttributes.HREF, css[i], null);
            writer.endElement(HTMLElements.LINK); //NOI18N
	}
    }
        
    /**
     * Helper function to render theme stylesheet definitions inline
     *
     * @param context containing theme
     * @param writer The current ResponseWriter
     * @param component The uicomponent
     * @deprecated Not well supported by browsers.
     */
    public static void renderStyleSheetInline(UIComponent component, 
            Theme theme, FacesContext context, ResponseWriter writer) 
            throws IOException {
        if (JavaScriptUtilities.isDebug()) {
            writer.write("\n");
        }
        writer.startElement(HTMLElements.STYLE, component);
        writer.writeAttribute(HTMLAttributes.TYPE, "text/css", null); //NOI18N

        String[] files = theme.getMasterStylesheets();
        if (files != null && files.length != 0) {
	    renderImports(files, writer);
        }
        
        // browser specific stylesheets
	//
	ClientType clientType = ClientSniffer.getClientType(context);
        files = theme.getStylesheets(clientType.toString());
        if (files != null && files.length != 0) {
	    renderImports(files, writer);
        }

        files = theme.getGlobalStylesheets();
        if (files != null && files.length != 0) {
	    renderImports(files, writer);
        }

	writer.endElement(HTMLElements.STYLE);
    }
    
    /**
     * Render <code>import</code> directives for <code>imports</code>.
     * 
     * @deprecated Not well supported by browsers.
     */
    public static void renderImports(String[] imports, ResponseWriter writer)
            throws IOException {
        for (int i = 0; i < imports.length; i++) {
            if (JavaScriptUtilities.isDebug()) {
                writer.write("\n");
            }
            writer.write("@import(\"");
            writer.write(imports[i]);
            writer.write("\");");
        }
    }

    /**
     * Perform a <code>RequestDispatcher.include</code> of the specified URI
     * <code>jspURI</code>.
     * <p>
     * The path identifed by <code>jspURI</code> must begin with
     * a <code>&lt;f:subview&gt;</code> tag. The URI must not have 
     * as part of its path the FacesServlet mapping. For example if the
     * FacesServlet mapping maps to <code>/faces/*</code> then 
     * <code>jspURI</code> must not have <code>/faces/</code> as part of 
     * its path.
     * </p>
     * <p>
     * If <code>jspUIR</code> is a relative path then the  
     * request context path is prepended to it.
     * </p>
     * @param context the <code>FacesContext</code> for this request
     * @param writer the <code>ResponseWrite</code> destination for the
     * rendered output
     * @param jspURI the URI identifying a JSP page to be included.
     * @throws IOException if response can't be written or <code>jspURI</code>
     * cannot be included. Real cause is chained.
     */
    public static void includeJsp(FacesContext context, ResponseWriter writer,
            String jspURI) throws IOException {
	class ResponseWrapper extends HttpServletResponseWrapper {
	    private PrintWriter printWriter;
	    public ResponseWrapper(HttpServletResponse response,
		    Writer writer) {
		super((HttpServletResponse)response);
		this.printWriter = new PrintWriter(writer);
	    }
	    public PrintWriter getWriter() {
		return printWriter;
	    }
	    public ServletOutputStream getOutputStream() throws IOException {
		throw new IllegalStateException();
	    }
	    public void resetBuffer() {
	    }
	}

	if (jspURI == null) {
	    return;
	}

	// prepend the request path if there is one in this path is not
	// a relative path. It appears that the servlet context algorithm
	// differs from the JspRuntime algorithm that allowed a relative
	// path in the lockhart wizard.
	//
	try {
	    if (!jspURI.startsWith("/")) { //NOI18N
		String contextPath = 
		    context.getExternalContext().getRequestContextPath();
		jspURI = contextPath.concat("/").concat(jspURI); //NOI18N
	    }
	       
	    ServletRequest request = 
		    (ServletRequest)context.getExternalContext().getRequest();
	    ServletResponse response =
		    (ServletResponse)context.getExternalContext().getResponse();

	    RequestDispatcher rd = request.getRequestDispatcher(jspURI);

	    // JSF is already buffering and suppressing output.
	    // 
	    rd.include(request,
		new ResponseWrapper((HttpServletResponse)response, writer));

	} catch (Exception e) {
	    throw (IOException)new IOException().initCause(e);
	}
    }

    /**
     * Helper method to render extra attributes.
     *
     * @param writer <code>ResponseWriter</code> to which the element
     *  end should be rendered
     * @param extraHtml Extra HTML appended to the tag enclosing the header
     *
     * @exception IOException if an input/output error occurs
     */
    public static void renderExtraHtmlAttributes(ResponseWriter writer, 
            String extraHtml) throws IOException {
        if (extraHtml == null) {
            return;
        }

        int n = extraHtml.length();
        int i = 0;
        while (i < n) {
            StringBuffer name = new StringBuffer();
            StringBuffer value = new StringBuffer();

            // Skip extra space characters.
            while (i < n && Character.isWhitespace(extraHtml.charAt(i))) {
                i++;
            }

            // Find name.
            for (; i < n; i++) {
                char c = extraHtml.charAt(i);
                if (c == '\'' || c == '"') {
                    return; // Not well formed.
                } else if (c == '=') {
                    break;
                } else {
                    name.append(c);
                }
            }
            i++; // Skip =

            // Process quote character.
            char quote = (i < n) ? extraHtml.charAt(i) : '\0';
            if (!(quote == '\'' || quote == '"')) {
                return; // Not well formed.
            }
            i++; // Skip quote character.

            // Find value.
            for (; i < n; i++) {
                char c = extraHtml.charAt(i);
                if (c == quote) {
                    break;
                } else {
                    value.append(c);
                }
            }
            i++; // Skip quote character.

            writer.writeAttribute(name.toString(), value.toString(), null); //NOI18N
        }
    }

   /**
    * Helper function to render a typical URL.
    * <p>
    * Note: Path must be a valid absolute URL or full path URI.
    * </p>
    *
    * @param writer The current ResponseWriter
    * @param component The uicomponent
    * @param name The attribute name of the url to write out
    * @param url The value passed in by the developer for the url
    * @param compPropName The property name of the component's property that 
    * specifies this property.  Should be null if same as name.
    *
    */
    public static void renderURLAttribute(FacesContext context, 
        ResponseWriter writer, UIComponent component, String name, String url,
            String compPropName) throws IOException {
        if (url == null) {
            return;
        }
        
        Param paramList[] = getParamList(context, component);
        StringBuffer sb = new StringBuffer();
        int i = 0;
        int len = paramList.length;
        sb = new StringBuffer();
        
        // Don't append context path here as themed images already include it.
        sb.append(url);
        if (0 < len) {
            sb.append("?");
        }
        for (i = 0; i < len; i++) {
            if (0 != i) {
                sb.append("&");
            }
            sb.append(paramList[i].getName());
            sb.append("=");
            sb.append(paramList[i].getValue());
        }

        String newName = null;
        if (compPropName != null) {
            newName = (compPropName.equals(name)) 
                                    ? null
                                    : compPropName;          
        }

        // Note: Path must be a valid absolute URL or full path URI -- see
        // bugtraq #6306848 & #6322887.
        writer.writeURIAttribute(name, (url.trim().length() != 0)
            ? context.getExternalContext().encodeResourceURL(sb.toString())
            : "", newName);
    }        
        
    static protected Param[] getParamList(FacesContext context,
            UIComponent command) {
        ArrayList parameterList = new ArrayList();

        Iterator kids = command.getChildren().iterator();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();

            if (kid instanceof UIParameter) {
                UIParameter uiParam = (UIParameter) kid;
                Object value = uiParam.getValue();
                Param param = new Param(uiParam.getName(),
                                        (value == null ? null :
                                         value.toString()));
                parameterList.add(param);
            }
        }

        return (Param[]) parameterList.toArray(new Param[parameterList.size()]);
    }

    //inner class to store parameter name and value pairs
    static protected class Param {
        public Param(String name, String value) {
            set(name, value);
        }

        private String name;
        private String value;

        public void set(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }
    
    // This method is written in such a way that you can use it without
    // using the component.
    
    /**
     * Render an href for a "skip link".
     * Note that "anchor" must be unique if this method is called more
     * than once to render on the same page.
     */
    public static void renderSkipLink(String anchorName, String styleClass,
        String style, String toolTip, Integer tabIndex, UIComponent component,
            FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        writer.startElement("div", component); //NOI18N                       
        if(styleClass != null) {
            writer.writeAttribute("class", styleClass, null);
        } else {
            writer.writeAttribute("class", ThemeUtilities.getTheme(context).
                getStyleClass(ThemeStyles.SKIP_WHITE), null);
        }
        if(style != null) {
            writer.writeAttribute("style", styleClass, null);
        }

        StringBuffer buffer = new StringBuffer(128); 
	// Use this for the href and for the icon id
	//
        buffer.append("#").
		append(component.getClientId(context)).
		append("_").
		append(anchorName);
        
        writer.startElement("a", component); //NOI18N
        writer.writeAttribute("href", buffer.toString(), null);

	// This is generating and XHTML invalid error.
	// It doesn't like "alt".
	//
        if(toolTip != null) {
            writer.writeAttribute("alt", toolTip, null);
        }
        if(tabIndex != null) {
            writer.writeAttribute("tabindex", tabIndex.toString(), null);
        }        
        
        Icon icon = ThemeUtilities.getIcon(ThemeUtilities.getTheme(context),
		ThemeImages.DOT);
        icon.setParent(component); 
        icon.setWidth(1);
        icon.setHeight(1);
        icon.setBorder(0);

	buffer.setLength(0);
	buffer.append(anchorName).append("_icon"); //NOI18N
        icon.setId(buffer.toString());

        RenderingUtilities.renderComponent(icon, context);        
        
        writer.endElement("a"); //NOI18N        
        writer.endElement("div"); //NOI18N
    }
    
    // This method is written in such a way that you can use it without
    // using the component.
    
    static public void renderAnchor(String anchorName, UIComponent component, 
            FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        
        StringBuffer buffer = new StringBuffer(128); 
        buffer.append(component.getClientId(context)); 
        buffer.append("_"); 
        buffer.append(anchorName);
        
        writer.startElement("div", component); //NOI18N
        writer.startElement("a", component); //NOI18N
        writer.writeAttribute("name", buffer.toString(), null);
        writer.endElement("a"); //NOI18N
        writer.endElement("div"); //NOI18N
    }
    
    /**
     *	<p> Return whether the given <code>UIComponent</code> is "visible".
     *	    If the property is null, it will return true.  Otherwise the value
     *	    of the property is returned.</p>
     *
     * @param	component   The <code>UIComponent</code> to check
     *
     * @return	True if the property is null or true, false otherwise.
     */
    public static boolean isVisible(UIComponent component) {
        Object visible = component.getAttributes().get("visible"); //NOI18N
        if (visible == null) {
            return true;
        } else {
            return ((Boolean) visible).booleanValue();
        }
    }
    
    /**
     * <p> Returns formatted message string. </p>
     * @param	context   FacesContext
     * @param	component   UIComponent
     * @param	msg  String
     * @return	formatted message string.
     */
    public static String formattedMessage(FacesContext context, UIComponent component, 
            String msg) {
        
        java.util.ArrayList parameterList = new ArrayList();

        // get UIParameter children...
        java.util.Iterator kids = component.getChildren().iterator();
        while (kids.hasNext()) {
             UIComponent kid = (UIComponent) kids.next();
             if (!(kid instanceof UIParameter)) {
                    continue;
                }
             parameterList.add(((UIParameter) kid).getValue());
        }

        // If at least one substitution parameter was specified,
        // use the string as a MessageFormat instance.
        String message = null;
        if (parameterList.size() > 0) {
            message = MessageFormat.format
                (msg, parameterList.toArray
                               (new Object[parameterList.size()]));
        } else {
            message = msg;
        }

        return message;
    }
}
