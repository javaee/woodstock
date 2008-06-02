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
import com.sun.webui.jsf.component.Head;
import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

import java.beans.Beans;
import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>Renderer for a {@link Head} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Head"))
public class HeadRenderer extends AbstractRenderer {
    /**
     * <p>The set of String pass-through attributes to be rendered.</p>
     */
    private static final String stringAttributes[] = { "profile" }; //NOI18N

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
	if (!RenderingUtilities.isPortlet(context)) {
            writer.startElement("head", component); //NOI18N
	}
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
        Head head = (Head) component;
        if (!RenderingUtilities.isPortlet(context)) {
	    // Profile
            addStringAttributes(context, component, writer, stringAttributes);

	    // Deprecated meta tags.
            if (head.isMeta()) {
	        renderMetaTag("no-cache", "Pragma", writer, head); 
	        renderMetaTag("no-cache", "Cache-Control", writer, head); 
	        renderMetaTag("no-store", "Cache-Control", writer, head); 
	        renderMetaTag("max-age=0", "Cache-Control", writer, head); 
	        renderMetaTag("1", "Expires", writer, head); 
            }

            UIComponent titleFacet = head.getFacets().get(head.TITLE_FACET);
              
            // A page must always have a title tag.
            writer.write("\n");
            writer.startElement("title",  head);            
            if (titleFacet != null) {
                RenderingUtilities.renderComponent(titleFacet, context);
            } else {            
                String title = head.getTitle();            
                if (title != null) {
                    writer.write(title);
                }
            }
            writer.endElement("title");

	    // Base
            if(head.isDefaultBase()) {
                writer.write("\n");
                writer.startElement("base", head); //NOI18N
                // TODO - verify the requirements w.r.t. printing this href
                writer.writeURIAttribute("href", Util.getBase(context), null); //NOI18N
                writer.endElement("base"); //NOI18N
            }

            // Get global flags.
            JavaScriptUtilities.setDebug(head.isDebug());
            JavaScriptUtilities.setJsfx(head.isJsfx());
            JavaScriptUtilities.setParseOnLoad(head.isParseOnLoad());
            JavaScriptUtilities.setStyleSheet(head.isStyleSheet());
            JavaScriptUtilities.setWebuiAll(head.isWebuiAll());
            JavaScriptUtilities.setWebuiAjax(head.isWebuiAjax());

            // Master stylesheet link.
            Theme theme = ThemeUtilities.getTheme(context);
            if (head.isStyleSheet() && Beans.isDesignTime()) {
                RenderingUtilities.renderStyleSheetLink(head, theme, context, writer);
                JavaScriptUtilities.setStyleSheet(false);
            }

            // Render bootstrap.
            if (head.isJavaScript()) {
                try {
                    JavaScriptUtilities.renderBootstrap(component, writer);
                    JavaScriptUtilities.renderOnLoad(component, writer, head.getOnLoad());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
	}
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
        // Start the appropriate element.
        if (!RenderingUtilities.isPortlet(context)) {
            writer.endElement("head"); //NOI18N
        }
    }

    private void renderMetaTag(String content, String httpEquivalent, 
            ResponseWriter writer, Head head) throws IOException {
        writer.write("\n");
        writer.startElement("meta", head); 
	writer.writeAttribute("content", content, null); 
	writer.writeAttribute("http-equiv", httpEquivalent, null); 
	writer.endElement("meta");
    }
}
