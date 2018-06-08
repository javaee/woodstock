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
import java.util.Date;
import java.util.Locale;
import java.io.IOException;
import java.text.DateFormat;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;
import com.sun.webui.jsf.component.TimeStamp;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;

/**
 * <p>Renders an instance of the TimeStamp component.</p>
 *
 * @author Sean Comerford
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.TimeStamp"))
public class TimeStampRenderer extends AbstractRenderer {

    /** Creates a new instance of TimeStampRenderer */
    public TimeStampRenderer() {
    }
    // Core attributes that are simple pass throughs
    private static final String coreAttributes[] = {"style", "title"}; // NOI18N

    /**
     * <p>Render the end element for the TimeStamp.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>EditableValueHolder</code> component whose
     *  submitted value is to be stored
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        TimeStamp timeStamp = (TimeStamp) component;
        Theme theme = ThemeUtilities.getTheme(context);

        if (!timeStamp.isRendered()) {
            return;
        }

        String textStyle = theme.getStyleClass(ThemeStyles.TIMESTAMP_TEXT);

        StringBuffer sb = new StringBuffer(timeStamp.getClientId(context));
        int idlen = sb.length();

        writer.startElement("span", timeStamp); // NOI18N
        writer.writeAttribute("id", sb.toString(), "id"); // NOI18N
        writer.startElement("span", timeStamp); // NOI18N

        writer.writeAttribute("id", sb.append("_span1"), "id"); // NOI18N
        //Reset the length
        sb.setLength(idlen);

        RenderingUtilities.renderStyleClass(context, writer, component, textStyle);
        addStringAttributes(context, component, writer, coreAttributes);

        String message = timeStamp.getText();
        if (message == null) {
            // use the default "Last updated:" message
            message = theme.getMessage("TimeStamp.lastUpdate"); // NOI18N
        }

        writer.write(message);
        writer.endElement("span"); // NOI18N
        writer.write("&nbsp;"); // NOI18N
        writer.startElement("span", timeStamp); // NOI18N

        writer.writeAttribute("id", sb.append("_span2"), "id"); // NOI18N

        RenderingUtilities.renderStyleClass(context, writer, component, textStyle);
        addStringAttributes(context, component, writer, coreAttributes);

        Locale locale =
                FacesContext.getCurrentInstance().getViewRoot().getLocale();

        DateFormat dateFormat = DateFormat.getDateTimeInstance(
                Integer.parseInt(theme.getMessage("TimeStamp.dateStyle")), // NOI18N
                Integer.parseInt(theme.getMessage("TimeStamp.timeStyle")), locale); // NOI18N

        writer.write(dateFormat.format(new Date())); // NOI18N

        writer.endElement("span"); // NOI18N
        writer.endElement("span"); // NOI18N
    }
}
