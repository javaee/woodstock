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
import com.sun.webui.jsf.component.Table;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * This class renders Table components and is a subclass of TableRenderer.
 * It is the same as the superclass in every respect except this class
 * implements <code>renderEnclosingTagStart</code> and <code>
 * enclosingTagEnd</code>. The difference is in the number of "div" elements
 * used and which HTML element is associated with the Table component's id.
 */
public class TableDesignTimeRenderer extends TableRenderer {
    /**
     * Render enclosing tag for Table components.
     * This implementation associates the component id with the table
     * element.
     *
     * @param context FacesContext for the current request.
     * @param component Table to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderEnclosingTagStart(FacesContext context,
            Table component, ResponseWriter writer) throws IOException {
        if (component == null) {
	    Class clazz = this.getClass();
            if (LogUtil.fineEnabled(clazz)) {
		// Log method name and message.
		LogUtil.fine(clazz, clazz.getName() + 
		    ".renderEnclosingTagStart: " + //NOI18N
		    "Cannot render enclosing tag, Table is null"); //NOI18N
	    }
            return;
        }

        Theme theme = ThemeUtilities.getTheme(context);
        
        // Render table.
        writer.writeText("\n", null); //NOI18N
        writer.startElement("table", component); //NOI18N
        writer.writeAttribute("id", //NOI18N
		component.getClientId(context), null);

        // Render style.
	String style = component.getStyle();
	StringBuffer buff = new StringBuffer(128);
        if (style != null) {
	    buff.append(style).append(";"); //NOI18N
	}

	String width = component.getWidth();
	if (width != null){ 
	    buff.append("width: "); //NOI18N

	    // If not a percentage, units are in pixels.
	    if (width.indexOf("%") == -1) { //NOI18N
		buff.append(width).append("px"); //NOI18N
            } else  {
		buff.append(width);
	    }
	    buff.append(";"); //NOI18N
	} else {
	    buff.append("width: 100%;"); //NOI18N
	}

	// There will always be at least "width: 100%" in buffer.
	writer.writeAttribute("style", buff.toString(), null); //NOI18N
            
        // Get default table style class.
        String styleClass = theme.getStyleClass(ThemeStyles.TABLE);

        if (component.isLite()) {
            styleClass += " " +  //NOI18N
		theme.getStyleClass(ThemeStyles.TABLE_LITE);
        }
        
	// This call renders the hidden  style class as appropriate.
	// Note that creator does not override the default style
	// in deference to the developer set styleClass but incudes both.
	// (This was the case even before using "renderStyleClass" for
	// this implementation)
	//
	RenderingUtilities.renderStyleClass(context, writer, component,
		styleClass);

	renderTableAttributes(context, component, writer);
    }

    /**
     * Render enclosing tag for Table components.
     * This implementation ends the table element rendered in 
     * renderEnclosingTagStart.
     *
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderEnclosingTagEnd(ResponseWriter writer)
            throws IOException {
        writer.endElement("table"); //NOI18N
    }
}
