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
import com.sun.webui.jsf.component.Body;
import com.sun.webui.jsf.component.ComplexComponent;
import com.sun.webui.jsf.util.CookieUtils;
import com.sun.webui.jsf.util.FocusManager;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.MessageUtil;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme;
import java.io.IOException;
import javax.faces.FacesException;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * <p>Renderer for a {@link Body} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.Body"))
public class BodyRenderer extends AbstractRenderer {

    private static final boolean DEBUG = false;
    /**
     * <p>The set of String pass-through attributes to be rendered.</p>
     */
    private static final String stringAttributes[] = {"onClick", "onDblClick", "onMouseDown", "onMouseUp", //NOI18N
        "onMouseOver", "onMouseMove", "onMouseOut", "onKeyPress", //NOI18N
        "onKeyDown", "onKeyUp", "onFocus", "onBlur"}; //NOI18N
    /**
     * <p>The set of integer pass-through attributes to be rendered.</p>
     */
    private static final String integerAttributes[] = {"tabIndex"}; //NOI18N

    /**
     * Decode the request parameter that contains the element id 
     * that last had the focus.
     */
    @Override
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
                !((Body) component).isPreserveFocus()) {
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
    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        // Start the appropriate element
        if (RenderingUtilities.isPortlet(context)) {
            return;
        }

        if (!(component instanceof Body)) {
            Object[] params = {component.toString(),
                this.getClass().getName(),
                Body.class.getName()};
            String message = MessageUtil.getMessage("com.sun.webui.jsf.resources.LogMessages", //NOI18N
                    "Renderer.component", params); //NOI18N
            throw new FacesException(message);
        }

        writer.startElement("body", component); //NOI18N  
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
    @Override
    protected void renderAttributes(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        if (RenderingUtilities.isPortlet(context)) {
            return;
        }

        Body body = (Body) component;

        addCoreAttributes(context, component, writer, null);
        addStringAttributes(context, component, writer, stringAttributes);

        // onload is a special case;
        String onload = body.getOnLoad();

        StringBuffer sb = new StringBuffer(256);
        if (onload != null) {
            sb.append(onload);
            sb.append("; "); //NOI18N
        }
        writer.writeAttribute("onload", sb.toString(), null); //NOI18N

        // Apply a background image
        String imageUrl = body.getImageURL();
        if (imageUrl != null && imageUrl.length() > 0) {
            String resourceUrl = context.getApplication().
                    getViewHandler().getResourceURL(context, imageUrl);
            writer.writeAttribute("background", resourceUrl, null); //NOI18N
        }

        // unload is a special case;
        String onUnload = body.getOnUnload();
        sb = new StringBuffer(256);
        if (onUnload != null) {
            sb.append(onUnload);
            sb.append("; "); //NOI18N
        }
        writer.writeAttribute("onunload", sb.toString(), null); //NOI18N

        addIntegerAttributes(context, component, writer, integerAttributes);
        writer.write("\n"); //NOI18N
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
    @Override
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        if (RenderingUtilities.isPortlet(context)) {
            return;
        }

        Body body = (Body) component;
        String id = body.getClientId(context);
        String viewId = context.getViewRoot().getViewId(); // Scroll cookie name.
        String urlString = context.getApplication().getViewHandler().
                getActionURL(context, viewId); // Cookie string.
	// Get this after we calculate the urlString...
	viewId = CookieUtils.getValidCookieName(viewId);

        // The common.body function accepts the scroll cookie
        // information. We should reconsider using cookies and
        // use hidden fields instead. This will provide better
        // interfaces for portals and other frameworks, where
        // cookie processing is not convenient. Also a browser
        // can easily exceed the maximum of 300 cookies.
        //

        // Instead of creating a global variable...
        Theme theme = ThemeUtilities.getTheme(context);
        StringBuffer buff = new StringBuffer(128);
        buff.append(JavaScriptUtilities.getModuleName("common.body")).append(" = new ") //NOI18N
                .append(JavaScriptUtilities.getModuleName("body")) //NOI18N
                .append("('") //NOI18N
                .append(viewId).append("', '") //NOI18N
                .append(urlString).append("'"); //NOI18N

        // Pass the developer specified focus id. This will be the
        // default focus id, if the "dynamic" focus element cannot
        // receive the focus. This is the "defaultFocusElementId"
        // javascript argument.
        //
        String fid = getFocusElementId(context, body.getFocus());
        buff.append(","); //NOI18N
        if (fid != null && fid.length() != 0) {
            buff.append("'") //NOI18N
                    .append(fid).append("'"); //NOI18N
        } else {
            // Note that javascript null must be rendered if fid
            // is java null. We don't want to render the string 'null'.
            //
            buff.append("null"); //NOI18N
        }

        // Pass the id of the element that should receive the initial focus.
        // for this response.
        // This has been set during decode by the body, or by a
        // component during the lifecycle processing. It is assumed to be
        // a client id. If its null pass javascript null and not 'null'.
        // This is the "focusElementId" javascript argument.
        //
        String rid = FocusManager.getRequestFocusElementId(context);
        buff.append(","); //NOI18N
        if (rid != null && rid.length() != 0) {
            buff.append("'") //NOI18N
                    .append(rid).append("'"); //NOI18N
        } else {
            // Note that javascript null must be rendered if fid
            // is java null. We don't want to render the string 'null'.
            //
            buff.append("null"); //NOI18N
        }

        // pass the id of the hidden field that holds the
        // focus element id
        // This is the "focusElementFieldId" argument.
        //
        buff.append(",'") // NOI18N
                .append(FocusManager.FOCUS_FIELD_ID).append("'") // NOI18N
                .append(");"); //NOI18N

        // Render JavaScript.
        JavaScriptUtilities.renderJavaScript(component, writer,
                buff.toString());

        writer.endElement("body"); //NOI18N
        writer.write("\n"); //NOI18N
    }

    /**
     * Log an error - only used during development time.
     */
    void log(String s) {
        System.out.println(this.getClass().getName() + "::" + s); //NOI18N
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
                id = ((ComplexComponent) comp).getFocusElementId(context);
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
}
