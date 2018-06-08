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

package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.theme.Theme;
import javax.faces.context.FacesContext;

/**
 * The NotificationPhrase component is used to display a message in the 
 * masthead.
 */
@Component(type = "com.sun.webui.jsf.NotificationPhrase", family = "com.sun.webui.jsf.NotificationPhrase",
displayName = "Notification Phrase", tagName = "notificationPhrase",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_notification_phrase",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_notification_phrase_props")
public class NotificationPhrase extends ImageHyperlink {

    /**
     * Default constructor.
     */
    public NotificationPhrase() {
        super();
        setRendererType("com.sun.webui.jsf.NotificationPhrase");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.NotificationPhrase";
    }

    @Override
    public String getIcon() {
        String icon = super.getIcon();

        if (icon == null) {
            icon = ThemeImages.ALERT_INFO_MEDIUM;
        }

        return icon;
    }

    @Override
    public String getStyleClass() {
        String styleClass = super.getStyleClass();

        if (styleClass == null) {
            styleClass = ThemeStyles.MASTHEAD_PROGRESS_LINK;
        }

        Theme theme = ThemeUtilities.getTheme(
                FacesContext.getCurrentInstance());
        return theme.getStyleClass(styleClass);
    }

    @Override
    public int getBorder() {
        return 0;
    }

    @Override
    public String getAlign() {
        return "middle";
    }

    @Override
    public String getAlt() {
        String alt = super.getAlt();

        if (alt == null) {
            // Don't convert if null, since convertValueToString
            // returns "" for a null value.
            //
            Object textObj = getText();
            if (textObj != null) {
                String text = ConversionUtilities.convertValueToString(this,
                        textObj);
                if (text != null && text.length() != 0) {
                    alt = text;
                }
            }
        }

        return alt;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Scripting code executed when a mouse double click occurs over this component.
     */
    @Property(name = "onDblClick", isHidden = false, isAttribute = true)
    @Override
    public String getOnDblClick() {
        return super.getOnDblClick();
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[1];
        _values[0] = super.saveState(_context);
        return _values;
    }
}
