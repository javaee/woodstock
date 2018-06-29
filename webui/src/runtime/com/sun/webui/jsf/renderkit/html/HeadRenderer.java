/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.sun.webui.jsf.component.Head;
import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Renderer for a {@link Head} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.Head"))
public class HeadRenderer extends AbstractRenderer {

    /**
     * <p>The set of String pass-through attributes to be rendered.</p>
     */
    private static final String stringAttributes[] = {"profile"}; //NOI18N

    private static final String DATE_ONE =
            (new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US)).format(new Date(1));

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
    @Override
    protected void renderAttributes(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        Head head = (Head) component;
        if (!RenderingUtilities.isPortlet(context)) {
            // Profile
            addStringAttributes(context, component, writer, stringAttributes);

            // Meta tags
            if (head.isMeta()) {
                writer.write("\n"); //NOI18N
                
                HttpServletResponse servletResponse = (HttpServletResponse) context.getCurrentInstance().getExternalContext().getResponse();
                servletResponse.setHeader("Pragma", "no-cache");
                servletResponse.setHeader("Cache-Control", "no-store");
                servletResponse.setHeader("Cache-Control", "no-cache");
                servletResponse.setHeader("Expires", DATE_ONE);
                servletResponse.setHeader("X-Frame-Options", "SAMEORIGIN");
                
                renderMetaTag("no-cache", "Pragma", writer, head);
                renderMetaTag("no-cache", "Cache-Control", writer, head);
                renderMetaTag("no-store", "Cache-Control", writer, head);
                renderMetaTag("max-age=0", "Cache-Control", writer, head);
                renderMetaTag("1", "Expires", writer, head);
            }

            // Title
            String title = head.getTitle();
            if (title == null) {
                title = "";
            }

            writer.startElement("title", head);
            writer.write(title);
            writer.endElement("title");
            writer.write("\n"); //NOI18N

            // Base
            if (head.isDefaultBase()) {
                writer.startElement("base", head); //NOI18N
                // TODO - verify the requirements w.r.t. printing this href
                writer.writeURIAttribute("href", Util.getBase(context), null); //NOI18N
                writer.endElement("base"); //NOI18N
                writer.write("\n"); //NOI18N
            }

            // Master link to always write out.
            Theme theme = ThemeUtilities.getTheme(context);
            RenderingUtilities.renderStyleSheetLink(head, theme, context, writer);

            // Do not render any JavaScript.
            if (!head.isJavaScript()) {
                return;
            }

            // Render Dojo config.
            JavaScriptUtilities.renderJavaScript(component, writer,
                    JavaScriptUtilities.getDojoConfig(head.isDebug(),
                    head.isParseWidgets()));

            // Render Dojo include.
            JavaScriptUtilities.renderDojoInclude(component, writer);

            // Render JSON include.
            JavaScriptUtilities.renderJsonInclude(component, writer);

            // Render Prototype include before JSF Extensions.
            JavaScriptUtilities.renderPrototypeInclude(component, writer);

            // Render JSF Extensions include.
            JavaScriptUtilities.renderJsfxInclude(component, writer);

            // Render module config after including dojo.
            JavaScriptUtilities.renderJavaScript(component, writer,
                    JavaScriptUtilities.getModuleConfig(head.isDebug()));

            // Render global include.
            JavaScriptUtilities.renderGlobalInclude(component, writer);
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
    @Override
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        // Start the appropriate element.
        if (!RenderingUtilities.isPortlet(context)) {
            writer.endElement("head"); //NOI18N
            writer.write("\n"); //NOI18N
        }
    }

    private void renderMetaTag(String content, String httpEquivalent,
            ResponseWriter writer, Head head) throws IOException {
        writer.startElement("meta", head);
        writer.writeAttribute("content", content, null);
        writer.writeAttribute("http-equiv", httpEquivalent, null);
        writer.endElement("meta");
        writer.writeText("\n", null);
    }
}
