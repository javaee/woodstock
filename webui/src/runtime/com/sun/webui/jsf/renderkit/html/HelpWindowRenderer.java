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

import com.sun.webui.jsf.util.LogUtil;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.HelpWindow;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * <p>This class is responsible for rendering the {@link HelpWindow} component for
 * the HTML Render Kit.</p> <p> The {@link HelpWindow} component can be used as 
 * a link which when clicked opens up the a popup window that displays help data </p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.HelpWindow"))
public class HelpWindowRenderer extends HyperlinkRenderer {

    // -------------------------------------------------------- Static Variables
    // -------------------------------------------------------- Renderer Methods
    /**
     * This method returns the most appropriate URL under the
     * circumstances. Insome cases viewhandler.getActionURL() needs
     * to be invoked while in other cases viewhandler.getResourceURL()
     * needs to be invoked. The hyperlink renderer, by default, will
     * always use latter while generating the complete URL. Subclasses
     * of this renderer can do it their own way.
     */
    @Override
    protected String getCorrectURL(FacesContext context, UIComponent component,
            String url) {

        if (!(component instanceof HelpWindow)) {
            return null;
        }

        if (url == null) {
            return null;
        }

        StringBuffer urlBuffer = new StringBuffer();
        urlBuffer.append(context.getApplication().getViewHandler().getActionURL(context, url)).append("?");

        HelpWindow hw = (HelpWindow) component;
        try {
            if (hw.getWindowTitle() != null) {
                addParameter(urlBuffer, "windowTitle", hw.getWindowTitle());
            }

            if (hw.getHelpFile() != null) {
                addParameter(urlBuffer, "helpFile", hw.getHelpFile());
            }
        } catch (UnsupportedEncodingException e) {
            LogUtil.warning("Encoding error: ", e);
        }
        return urlBuffer.toString();
    }

    // --------------------------------------------------------- Private Methods

    // Helper method to append encoded request parameters to the URL in the
    // appropriate character encoding
    private void addParameter(StringBuffer buffer, String name, String value)
            throws UnsupportedEncodingException {
        if (buffer == null || name == null || value == null) {
            return;
        }

        buffer.append("&").append(name).append("=").append(URLEncoder.encode(value, "UTF-8"));
    }
}
