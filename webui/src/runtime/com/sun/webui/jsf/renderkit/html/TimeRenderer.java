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

/*
 * TimeRenderer.java
 *
 * Created on July 6, 2005, 4:04 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.io.IOException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import javax.faces.context.FacesContext;
import com.sun.webui.jsf.component.DropDown;
import com.sun.webui.jsf.component.Time;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.MessageUtil;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 *
 * @author avk
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.Time"))
public class TimeRenderer extends javax.faces.render.Renderer {

    private static final boolean DEBUG = false;

    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {

        if (DEBUG) {
            log("encodeEnd() START");
        }

        if (!(component instanceof Time)) {
            Object[] params = {component.toString(),
                this.getClass().getName(),
                Time.class.getName()};
            String message = MessageUtil.getMessage("com.sun.webui.jsf.resources.LogMessages", //NOI18N
                    "Renderer.component", params);              //NOI18N
            throw new FacesException(message);
        }
        Theme theme = ThemeUtilities.getTheme(context);
        Time time = (Time) component;
        DropDown hourMenu = time.getHourMenu();
        DropDown minuteMenu = time.getMinutesMenu();

        ResponseWriter writer = context.getResponseWriter();

        writer.startElement("table", time); //NOI18N
        writer.writeAttribute("cellspacing", "0", null); // NOI18N
        writer.writeAttribute("cellpadding", "0", null); // NOI18N
        writer.writeText("\n", null); //NOI18N
        writer.startElement("tr", time); //NOI18N
        writer.writeText("\n", null); //NOI18N

        // hour menu
        writer.startElement("td", time);    //NOI18N
        writer.writeText("\n", null); //NOI18N
        RenderingUtilities.renderComponent(hourMenu, context);
        writer.writeText("\n", null); //NOI18N
        writer.endElement("td");   //NOI18N
        writer.writeText("\n", null); //NOI18N

        // colon
        writer.startElement("td", time);
        writer.write(":");
        writer.endElement("td");
        writer.writeText("\n", null); //NOI18N

        // minutes menu
        writer.startElement("td", time); //NOI18N
        writer.writeText("\n", null); //NOI18N
        RenderingUtilities.renderComponent(minuteMenu, context);
        writer.writeText("\n", null); //NOI18N

        // Should use another cell for this, and a width attribute instad
        //writer.write("&nbsp;");

        writer.startElement("span", time); //NOI18N

        String string =
                theme.getStyleClass(ThemeStyles.DATE_TIME_ZONE_TEXT);
        writer.writeAttribute("class", string, null); // NOI18N    
        writer.writeText(theme.getMessage("Time.gmt"), null);
        writer.writeText(time.getOffset(), null);
        writer.endElement("span"); //NOI18N
        writer.writeText("\n", null); //NOI18N

        writer.endElement("td");   //NOI18N
        writer.writeText("\n", null); //NOI18N
        writer.endElement("tr");   //NOI18N
        writer.writeText("\n", null); //NOI18N
        writer.endElement("table"); //NOI18N
        writer.writeText("\n", null); //NOI18N

        if (DEBUG) {
            log("encodeEnd() END");
        }
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) {
        return;
    }

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) {
        return;
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    private void log(String s) {
        System.out.println(this.getClass().getName() + "::" + s);
    }
}
