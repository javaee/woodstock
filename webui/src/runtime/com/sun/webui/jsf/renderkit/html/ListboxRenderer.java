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
 * $Id: ListboxRenderer.java,v 1.1.18.1 2009-12-29 04:52:43 jyeary Exp $
 */
package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.beans.Beans;
import java.io.IOException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import com.sun.webui.jsf.component.Listbox;
import com.sun.webui.jsf.component.ListManager;
import com.sun.webui.jsf.component.ListSelector;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>Renderer for a {@link com.sun.webui.jsf.component.Listbox} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.Listbox"))
public class ListboxRenderer extends ListRendererBase {

    private final static boolean DEBUG = false;

    /**
     * <p>Render the list.
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * end should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {

        if (DEBUG) {
            log("encodeEnd()");
        }

        if (component instanceof ListSelector) {

            ListSelector selector = (ListSelector) component;

            if (!Beans.isDesignTime()) {
                selector.checkSelectionModel(context);
            }

            boolean useMonospace = false;
            if (selector instanceof Listbox) {
                useMonospace = ((Listbox) selector).isMonospace();
            }

            super.renderListComponent(selector, context, getStyles(context, component, useMonospace));
        } else {
            String message = "Component " + component.toString() + //NOI18N
                    " has been associated with a ListboxRenderer. " + //NOI18N
                    " This renderer can only be used by components " + //NOI18N
                    " that extend com.sun.webui.jsf.component.Selector."; //NOI18N
            throw new FacesException(message);
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * <p>Render the appropriate element end, depending on the value of the
     * <code>type</code> property.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param monospace <code>UIComponent</code> if true, use the monospace
     * styles to render the list.
     *
     * @exception IOException if an input/output error occurs
     */
    private String[] getStyles(FacesContext context,
            UIComponent component,
            boolean monospace) {

        if (DEBUG) {
            log("getStyles()");
        }

        Theme theme = ThemeUtilities.getTheme(context);

        String[] styles = new String[10];
        styles[0] = getOnChangeJavaScript((ListManager) component,
                JavaScriptUtilities.getModuleName("listbox.changed"), //NOI18N
                context);
        if (monospace) {
            styles[1] = theme.getStyleClass(ThemeStyles.LIST_MONOSPACE);
            styles[2] =
                    theme.getStyleClass(ThemeStyles.LIST_MONOSPACE_DISABLED);
        } else {
            styles[1] = theme.getStyleClass(ThemeStyles.LIST);
            styles[2] = theme.getStyleClass(ThemeStyles.LIST_DISABLED);
        }
        styles[3] = theme.getStyleClass(ThemeStyles.LIST_OPTION);
        styles[4] = theme.getStyleClass(ThemeStyles.LIST_OPTION_DISABLED);
        styles[5] = theme.getStyleClass(ThemeStyles.LIST_OPTION_SELECTED);
        styles[6] = theme.getStyleClass(ThemeStyles.LIST_OPTION_GROUP);
        styles[7] = theme.getStyleClass(ThemeStyles.LIST_OPTION_SEPARATOR);
        styles[8] = theme.getStyleClass(ThemeStyles.HIDDEN);
        styles[9] = theme.getStyleClass(ThemeStyles.LIST_ALIGN);
        return styles;
    }
}
