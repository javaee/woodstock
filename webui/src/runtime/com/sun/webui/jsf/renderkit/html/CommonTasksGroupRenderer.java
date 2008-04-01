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
import com.sun.webui.jsf.component.CommonTasksGroup;
import com.sun.webui.jsf.component.CommonTasksSection;
import com.sun.webui.jsf.component.StaticText;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.theme.Theme;

import java.io.IOException;
import java.util.*;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>Renderer for a {@link com.sun.webui.jsf.component.CommonTasksGroup} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.CommonTasksGroup"))

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

            buff.append(JavaScriptUtilities.getModule("_html.commonTasksSection"))
                .append(JavaScriptUtilities.getModuleName("_html.commonTasksSection._init(")) // NOI18N
                .append(JSONUtilities.getString(json))
                .append(");\n"); //NOI18N

            // Render JavaScript.
            JavaScriptUtilities.renderJavaScript(component, writer,
                buff.toString(), JavaScriptUtilities.isParseOnLoad());
        } catch(JSONException e) {
            if (LogUtil.fineEnabled()) {
                LogUtil.fine(e.getStackTrace().toString()); //NOI18N
            }
        }
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


    public boolean getRendersChildren() {
        return true;
    }
}
