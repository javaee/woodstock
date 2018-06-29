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
 * $Id: StaticTextRenderer.java,v 1.1.20.1 2009-12-29 04:52:44 jyeary Exp $
 */
package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.StaticText;
import com.sun.webui.jsf.util.ConversionUtilities;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * <p>Renderer for a {@link StaticText} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.StaticText"))
public class StaticTextRenderer extends AbstractRenderer {


    // ======================================================== Static Variables
    /**
     * <p>The set of String pass-through attributes to be rendered.</p>
     */
    private static final String stringAttributes[] = {"onClick", "onDblClick", "onMouseUp", //NOI18N
        "onMouseDown", "onMouseMove", "onMouseOut", "onMouseOver"}; //NOI18N


    // -------------------------------------------------------- Renderer Methods
    /**
     * <p>Render the appropriate element start, depending on whether the
     * <code>for</code> property is set or not.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component StaticText component
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        writer.writeText("\n", null); //NOI18N
        writer.startElement("span", component);
    }

    /**
     * <p>Render the appropriate element attributes, followed by the
     * label content, depending on whether the <code>for</code> property
     * is set or not.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component StaticText component
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderAttributes(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        StaticText st = (StaticText) component;
        addCoreAttributes(context, component, writer, null);
        addStringAttributes(context, component, writer, stringAttributes);
        if (st.getToolTip() != null) {
            writer.writeAttribute("title", st.getToolTip(), null);
        }
    }

    /**
     * <p>Render the appropriate element end, depending on whether the
     * <code>for</code> property is set or not.</p>
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
        StaticText staticText = (StaticText) component;

        String currentValue =
                ConversionUtilities.convertValueToString(component,
                staticText.getText());

        String style = staticText.getStyle();
        String styleClass = staticText.getStyleClass();

        if (currentValue != null) {
            java.util.ArrayList parameterList = new ArrayList();

            // get UIParameter children...

            java.util.Iterator kids = component.getChildren().iterator();
            while (kids.hasNext()) {
                UIComponent kid = (UIComponent) kids.next();

                //PENDING(rogerk) ignore if child is not UIParameter?

                if (!(kid instanceof UIParameter)) {
                    continue;
                }

                parameterList.add(((UIParameter) kid).getValue());
            }

            // If at least one substitution parameter was specified,
            // use the string as a MessageFormat instance.
            String message = null;
            if (parameterList.size() > 0) {
                message = MessageFormat.format(currentValue, parameterList.toArray(new Object[parameterList.size()]));
            } else {
                message = currentValue;
            }

            if (message != null) {
                if (staticText.isEscape()) {
                    writer.writeText(message, "value");
                } else {
                    writer.write(message);
                }
            }
        }
        writer.endElement("span");
    }
    // --------------------------------------------------------- Private Methods
}
