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

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.html.HTMLAttributes;
import com.sun.webui.html.HTMLElements;
import com.sun.webui.jsf.component.CommonTasksGroup;
import com.sun.webui.jsf.component.CommonTasksSection;
import com.sun.webui.jsf.component.StaticText;
import java.util.Iterator;

/**
 * <p>Renderer for a {@link com.sun.webui.jsf.component.CommonTasksGroup} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.CommonTasksGroup"))
public class CommonTasksGroupRenderer extends AbstractRenderer {

    /**
     *Append this string for the id of span for the group header's title.
     */
    private static final String TITLE_SPAN = "_groupTitle";
    public static final String GROUP_TITLE = "commonTasks.groupTitle";
    public static final String SKIP_GROUP = "commonTasks.skipTagAltText";
    /**
     *Skip a complete common tasks group.
     */
    private static final String SKIP_TASKSGROUP = "skipGroup"; // NOI18N

    /** Creates a new instance of CommonTaskGroupRenderer */
    public CommonTasksGroupRenderer() {
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {

        // purposefully don't want to do anything here!
    }

    /**
     * Render a common tasks group.
     * 
     * @param context The current FacesContext
     * @param component The CommonTasksGroup object to render
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer)
            throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        CommonTasksGroup ctg = (CommonTasksGroup) component;

        if (!ctg.isRendered()) {
            return;
        }
        Theme theme = ThemeUtilities.getTheme(context);
        String title = ctg.getTitle();
        if (title == null) {
            title = theme.getMessage(GROUP_TITLE);
            ctg.setTitle(title);
        }

        writer.startElement(HTMLElements.DIV, ctg);
        writer.writeAttribute(HTMLAttributes.ID, ctg.getClientId(context),
                HTMLAttributes.ID); //NOI18N

        String styles = RenderingUtilities.getStyleClasses(context, ctg,
                theme.getStyleClass(ThemeStyles.CTS_GROUP));

        if (styles != null) {
            writer.writeAttribute(HTMLAttributes.CLASS, styles,
                    HTMLAttributes.CLASS);// NOI18N
        }

        if (ctg.getStyle() != null) {
            writer.writeAttribute(HTMLAttributes.STYLE, ctg.getStyle(),
                    HTMLAttributes.STYLE);  // NOI18N
        }

        StringBuffer jsBuffer = new StringBuffer();
        renderTaskGroupHeader(title, writer, context, ctg, theme);

        if (!(ctg.getParent() instanceof CommonTasksSection)) {
            renderJavascriptImages(theme, writer, ctg, context);
        }

        Iterator it = ctg.getChildren().iterator();
        UIComponent comp;
        while (it.hasNext()) {
            comp = (UIComponent) it.next();
            RenderingUtilities.renderComponent(comp, context);
        }

        writer.endElement(HTMLElements.DIV);
    }

    /**
     * Renders the javascript necessary to precache the "i" images
     *
     * @param theme The current theme
     * @param writer The ResponseWriter object
     * @param component The commonTasksSection component
     */
    protected void renderJavascriptImages(Theme theme, ResponseWriter writer,
            UIComponent component, FacesContext context) throws IOException {
        StringBuffer buff = new StringBuffer();


        /*
         * Create the JSON object.
         */

        try {
            JSONObject json = getJSONProperties(context, theme, component);

            buff.append("require(['").append(JavaScriptUtilities.getModuleName("commonTasksSection")).append("'], function (commonTasksSection) {").append("\n") // NOI18N
//                    .append(JavaScriptUtilities.getModuleName("commonTasksSection.init(")) // NOI18N
                    .append("commonTasksSection.init(")
                    .append(json.toString(JavaScriptUtilities.INDENT_FACTOR)).append(");\n"); //NOI18N
            buff.append("});");
            
            // Render JavaScript.
            JavaScriptUtilities.renderJavaScript(component, writer,
                    buff.toString());
        } catch (JSONException e) {
            if (LogUtil.fineEnabled()) {
                LogUtil.fine(e.getStackTrace().toString()); //NOI18N
            }
        }
        writer.write("\n");             // NOI18N
    }

    protected JSONObject getJSONProperties(FacesContext context, Theme theme,
            UIComponent component) throws IOException, JSONException {

        JSONObject json = new JSONObject();

        json.put("id", component.getClientId(context));
        String url =
                theme.getImagePath(ThemeImages.CTS_RIGHT_TOGGLE_SELECTED);
        json.put("pic1URL", url);
        url = theme.getImagePath(ThemeImages.CTS_RIGHT_TOGGLE_OVER);
        json.put("pic2URL", url);
        url = theme.getImagePath(ThemeImages.CTS_RIGHT_TOGGLE);
        json.put("pic3URL", url);

        return json;
    }

    /**
     * Render a common tasks group.
     * 
     * @param context The current FacesContext
     * @param component The CommonTasksGroup object to render
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer)
            throws IOException {
    }

    /**
     * Renders the task group header.
     * @param header The header text to be rendered
     * @param writer The current ResponseWriter object
     * @param component The CommonTasksGroup object
     * @param theme The current theme.
     * @param context The current FacesContext
     */
    protected void renderTaskGroupHeader(String header, ResponseWriter writer,
            FacesContext context, UIComponent component, Theme theme)
            throws IOException {

        StaticText st = new StaticText();
        st.setParent(component);
        st.setId(TITLE_SPAN);
        st.setStyleClass(theme.getStyleClass(ThemeStyles.CTS_HEADER));
        st.setText(header);
        RenderingUtilities.renderComponent(st, context);
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }
}
