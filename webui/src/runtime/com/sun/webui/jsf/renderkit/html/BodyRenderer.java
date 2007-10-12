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

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.html.HTMLAttributes;
import com.sun.webui.html.HTMLElements;
import com.sun.webui.jsf.component.Body;
import com.sun.webui.jsf.component.ComplexComponent;
import com.sun.webui.jsf.util.FocusManager;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.MessageUtil;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme;

import java.beans.Beans;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * <p>Renderer for a {@link Body} component.</p>
 */

@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Body"))
public class BodyRenderer extends AbstractRenderer {
    private static final String SCRIPT_KEY = "com_sun_webui_jsf_renderkit_html_body_script";

    private static final String SCRIPT_START =
        "<script type=\"text/javascript\">dojo.addOnLoad(function() {";

    private static final String SCRIPT_END = 
        "});</script>";

    private static final boolean DEBUG = false;
    
    /**
     * <p>The set of String pass-through attributes to be rendered.</p>
     */
    private static final String stringAttributes[] =
    { "onClick", "onDblClick", "onMouseDown", "onMouseUp",
      "onMouseOver", "onMouseMove", "onMouseOut", "onKeyPress",
      "onKeyDown", "onKeyUp", "onFocus", "onBlur", "onLoad", "onUnload"};
      
    /**
     * <p>The set of integer pass-through attributes to be rendered.</p>
     */
    private static final String integerAttributes[] =
    { "tabIndex" };


    /**
     * Decode the request parameter that contains the element id 
     * that last had the focus.
     */
    public void decode(FacesContext context, UIComponent component) {
	// Enforce NPE requirements in the Javadocs
	if ((context == null) || (component == null)) {
	    throw new NullPointerException();
	}
	if (!component.isRendered()) {
	    return;
	}

	// If we are not preserving the focus do not update the
	// FocusManager.
	//
	if (component instanceof Body && 
		!((Body)component).isPreserveFocus()) {
	    return;
	}

	String id = RenderingUtilities.decodeHiddenField(context, 
		FocusManager.FOCUS_FIELD_ID);
	if (id != null && (id = id.trim()).length() != 0) {
	    FocusManager.setRequestFocusElementId(context, id);
	}
    }

    /**
     * <p>Render the appropriate element start, depending on whether the
     * <code>for</code> property is set or not.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component component to render.
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderStart(FacesContext context, UIComponent component,
                               ResponseWriter writer) throws IOException {

        // Start the appropriate element
        if (RenderingUtilities.isPortlet(context)) {
            return;
        } 
                      
        if(!(component instanceof Body)) {
            Object[] params = { component.toString(),
                    this.getClass().getName(),
                    Body.class.getName() };
                    String message = MessageUtil.getMessage
                            ("com.sun.webui.jsf.resources.LogMessages",
                            "Renderer.component", params);
                    throw new FacesException(message);
        }
        
        writer.startElement(HTMLElements.BODY, component);
    }
    

    /**
     * <p>Render the appropriate element attributes, 
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component component to be rendered
     *  submitted value is to be stored
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderAttributes(FacesContext context, UIComponent component,
                                    ResponseWriter writer) throws IOException {
                                        
        if (RenderingUtilities.isPortlet(context)) {
            return; 
        } 
        
        Body body = (Body) component;
        
        addCoreAttributes(context, component, writer, null);
        addStringAttributes(context, component, writer, stringAttributes);
        

	// Apply a background image
 	String imageUrl = body.getImageURL();
	if (imageUrl != null && imageUrl.length() > 0) {
	    String resourceUrl = context.getApplication().
		getViewHandler().getResourceURL(context, imageUrl);
	    writer.writeAttribute(HTMLAttributes.BACKGROUND, resourceUrl, null);
	}


        addIntegerAttributes(context, component, writer, integerAttributes);
    }

    /**
     * Return a flag indicating whether this Renderer is responsible
     * for rendering the children the component it is asked to render.
     * The default implementation returns false.
     */
    public boolean getRendersChildren() {
        // Hack for VWP because they are still using a JSF 1.1 renderer. VWP is
        // also listening for write calls to the ResponseWriter, so we cannot
        // invoke the initStringWriter method of RendereringUtilities in order 
        // to capture JavaScript.
        if (Beans.isDesignTime()) {
            return false;
        }
        return true;
    }

    /**
     * <p>Render the children of the specified <code>UIComponent</code>
     * to the output stream or writer associated with the response we are
     * creating.</p>
     *
     * <p>The default implementation iterates through the children of
     * this component and renders them.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be decoded
     *
     * @exception NullPointerException if <code>context</code> or
     *  <code>component</code> is <code>null</code>
     *
     * @exception IOException if an input/output error occurs
     */   
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        
        // Enforce NPE requirements in the Javadocs
        if (context == null || component == null) {
            throw new NullPointerException();
        }

        // Initialize Writer to buffer rendered output.
        ResponseWriter oldWriter = context.getResponseWriter();
        Writer strWriter = RenderingUtilities.initStringWriter(context);

        if (component.isRendered()) {
            Iterator kids = component.getChildren().iterator();
            while (kids.hasNext()) {
                UIComponent kid = (UIComponent) kids.next();
                RenderingUtilities.renderComponent(kid, context);
            }
        }

        // Restore writer.
        context.setResponseWriter(oldWriter);

        // Extract JavaScript from buffered output.
        StringBuffer buff = new StringBuffer(1024);
        String s = strWriter.toString();

        int first = 0;
        while (first < s.length()) {
            int last = s.indexOf(SCRIPT_START, first);

            if (last != -1) {
                // Write HTML to ResponseWriter.
                oldWriter.write(s.substring(first, last));

                // Append JavaScript to buffer.
                first = last + SCRIPT_START.length();
                last = s.indexOf(SCRIPT_END, first);
                if (last != -1) {
                    buff.append(s.substring(first, last));
                }
                first = last + SCRIPT_END.length();
            } else {
                oldWriter.write(s.substring(first, s.length()));
                break;
            } 
        } 

        // Store buffer for encodeEnd.
        getRequestMap().put(SCRIPT_KEY, buff);
    }

    /**
     * <p>Render the appropriate element end, depending on whether the
     * <code>for</code> property is set or not.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component component to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        
        if (RenderingUtilities.isPortlet(context)) {
            return;
        }
        
        Body body = (Body)component;
        String id = body.getClientId(context);
        String viewId = context.getViewRoot().getViewId(); // Scroll cookie name.
        String urlString = context.getApplication().getViewHandler().
            getActionURL(context, viewId); // Cookie string.

	// The common.body function accepts the scroll cookie 
	// information. We should reconsider using cookies and
	// use hidden fields instead. This will provide better
	// interfaces for portals and other frameworks, where
	// cookie processing is not convenient. Also a browser
	// can easily exceed the maximum of 300 cookies.
	//

        // Instead of creating a global variable...
        Theme theme = ThemeUtilities.getTheme(context);
        StringBuffer buff = (StringBuffer) getRequestMap().get(SCRIPT_KEY);
        if (buff == null) {
            buff = new StringBuffer(128);
        }
        buff.append(JavaScriptUtilities.getModuleName("bootstrap.body"))
            .append(" = new ")
            .append(JavaScriptUtilities.getModuleName("body"))
            .append("('")
            .append(viewId)
            .append("', '")
            .append(urlString)
            .append("'");

	// Pass the developer specified focus id. This will be the
	// default focus id, if the "dynamic" focus element cannot
	// receive the focus. This is the "defaultFocusElementId"
	// javascript argument.
	//
	String fid = getFocusElementId(context, body.getFocus());
	buff.append(",");
	if (fid != null && fid.length() != 0 ) {
	    buff.append("'")
		.append(fid)
		.append("'");
	} else  {
	    // Note that javascript null must be rendered if fid 
	    // is java null. We don't want to render the string 'null'.
	    //
	    buff.append("null");
	}

        // Pass the id of the element that should receive the initial focus.
	// for this response.
	// This has been set during decode by the body, or by a 
	// component during the lifecycle processing. It is assumed to be 
	// a client id. If its null pass javascript null and not 'null'.
	// This is the "focusElementId" javascript argument.
	//
	String rid = FocusManager.getRequestFocusElementId(context);
	buff.append(",");
	if (rid != null && rid.length() != 0 ) {
	    buff.append("'")
		.append(rid)
		.append("'");
	} else  {
	    // Note that javascript null must be rendered if fid 
	    // is java null. We don't want to render the string 'null'.
	    //
	    buff.append("null");
	}

	// pass the id of the hidden field that holds the
	// focus element id
	// This is the "focusElementFieldId" argument.
	//
	buff.append(",'")
	    .append(FocusManager.FOCUS_FIELD_ID)
	    .append("'");

	// Pass the value of "isPreserveScroll"
	//
	buff.append(body.isPreserveScroll() ? ",true" : ",false")
            .append(");");

        // Render JavaScript.
        JavaScriptUtilities.renderJavaScript(component, writer,
            buff.toString(), true);

        writer.endElement(HTMLElements.BODY);
    }
    
    /**
     * Helper method to obtain the id of a ComplexComponent sub
     * component. If a developer specified the focus property
     * they may not have been able to obtain the sub component that
     * should receive the focus, since they can only specify the id
     * of the complex component and not the sub component.
     * The returned id must be the id of an HTML element in the page
     * that can receive the focus.
     */
    protected String getFocusElementId(FacesContext context, String id) {

	// Note that this code is duplicated in 
	// Body because we don't want to 
	// reference the Body.getFocusID, which is deprecated.
	//
        if (id == null || id.length() == 0) {
	    return null;
	}

	// Need absolute id.
	// Make sure it doesn't already have a leading 
	// NamingContainer.SEPARATOR_CHAR
	//
	String absid = id;
	if (id.charAt(0) != NamingContainer.SEPARATOR_CHAR) {
	    absid = String.valueOf(NamingContainer.SEPARATOR_CHAR).concat(id);
	}
	try {
	    // Since a developer using Body.setFocus may not be able to
	    // identify a sub component of a ComplexComponent, that
	    // must be done here.
	    // There is an assumption here that the ComplexComponent
	    // will recurse to find the appropriate sub-component id.
	    // to return.
	    //
	    UIComponent comp = context.getViewRoot().findComponent(absid);
	    if (comp != null && comp instanceof ComplexComponent) {
		id = ((ComplexComponent)comp).getFocusElementId(context);
	    }
	} catch (Exception e) {
	    if (LogUtil.finestEnabled()) {
		LogUtil.finest("BodyRenderer.getFocusElementId: " +
		    "couldn't find component with id " + absid +
		    " rendering focus id as " + id);
	    }
	}
	return id;
    }

    // Helper method to get request map.
    private static Map getRequestMap() {
        return FacesContext.getCurrentInstance().getExternalContext().
            getRequestMap();
    }
}
