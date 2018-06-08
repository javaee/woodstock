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
import com.sun.webui.jsf.component.ImageHyperlink;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import java.io.IOException;
import javax.faces.component.UIComponent;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * <p>This class is responsible for rendering the {@link ImageHyperlink} component for the
 * HTML Render Kit.</p> <p> The {@link ImageHyperlink} component can be used as an anchor, a
 * plain hyperlink or a hyperlink that submits the form depending on how the
 * properites are filled out for the component </p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.ImageHyperlink"))
public class ImageHyperlinkRenderer extends HyperlinkRenderer {

    // -------------------------------------------------------- Static Variables
    // for positioning of the label.
    private static final String LABEL_LEFT = "left"; //NOI8N
    private static final String LABEL_RIGHT = "right"; //NOI8N

    // -------------------------------------------------------- Renderer Methods
    @Override
    protected void finishRenderAttributes(FacesContext context,
            UIComponent component,
            ResponseWriter writer)
            throws IOException {
        //create an image component based on image attributes
        //write out image as escaped text
        //TODO: suppress the text field from the XML
        ImageHyperlink ilink = (ImageHyperlink) component;

        // If there is no text property set, then label == null which prevents
        // rendering anything at all
        //
        Object text = ilink.getText();
        String label = (text == null) ? null : ConversionUtilities.convertValueToString(component, text);

        String textPosition = ilink.getTextPosition();

        if (label != null && textPosition.equalsIgnoreCase(LABEL_LEFT)) {
            writer.writeText(label, null);
            writer.write("&nbsp;");
        }

        // ImageURL
        UIComponent ic = ilink.getImageFacet();
        if (ic != null) {
            // GF-required 508 change
            Map<String, Object> atts = ic.getAttributes();
            Object value = atts.get("alt");
            if ((value == null) || (value.equals(""))) {
                atts.put("alt", label);
            }
            value = atts.get("toolTip");
            if ((value == null) || (value.equals(""))) {
                atts.put("toolTip", label);
            }
            RenderingUtilities.renderComponent(ic, context);
        }

        if (label != null && textPosition.equalsIgnoreCase(LABEL_RIGHT)) {
            writer.write("&nbsp;");
            writer.writeText(label, null);
        }

    }
    // --------------------------------------------------------- Private Methods
}
