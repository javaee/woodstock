/*
 The contents of this file are subject to the terms
 of the Common Development and Distribution License
 (the License).  You may not use this file except in
 compliance with the License.

 You can obtain a copy of the license at
 https://woodstock.dev.java.net/public/CDDLv1.0.html.
 See the License for the specific language governing
 permissions and limitations under the License.

 When distributing Covered Code, include this CDDL
 Header Notice in each file and include the License file
 at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 If applicable, add the following below the CDDL Header,
 with the fields enclosed by brackets [] replaced by
 you own identifying information:
 "Portions Copyrighted [year] [name of copyright owner]"

 Copyright 2007 Sun Microsystems, Inc. All rights reserved.

 */


package org.renderer;

import java.io.IOException;

import javax.faces.render.Renderer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.sun.webui.html.HTMLAttributes;
import com.sun.webui.html.HTMLElements;

import com.sun.webui.theme.Theme;
import com.sun.webui.theme.ThemeImage;

import com.sun.webui.jsf.util.ThemeUtilities;

import org.component.BoxComponent;

/**
 */
public class BoxComponentRenderer extends Renderer {
    
    enum ClassMapperKeys { BOX };
    enum Images { BoxImage };
    enum Messages { BoxTitle };

      
    /**
     */
    public void encodeChildren(FacesContext context, UIComponent component)
	    throws IOException {

        // Enforce NPE requirements in the Javadocs
        if (context == null || component == null) {
            throw new NullPointerException();
        }
    }
      
    /**
     */
    public void encodeEnd(FacesContext context, UIComponent component)
	    throws IOException {

        // Enforce NPE requirements in the Javadocs
        if (context == null || component == null) {
            throw new NullPointerException();
        }

        // Render the element closing for this component
        if (!component.isRendered()) {
	    return;
        }

	ResponseWriter writer = context.getResponseWriter();

	BoxComponent box = (BoxComponent)component;
	Theme theme = ThemeUtilities.getTheme(context);

	writer.startElement(HTMLElements.DIV, component);
	String attrValue = box.getStyleClass();
	writer.writeAttribute(HTMLAttributes.CLASS, 
	    attrValue != null ? attrValue :
		theme.getStyleClass(ClassMapperKeys.BOX.toString()), null);

	StringBuffer sb = new StringBuffer(64);
	// Expect that values have units specified i.e. "500px"
	//
	int intValue = box.getWidth();
	if (intValue != Integer.MIN_VALUE) {
	   sb.append("width:").
		append(Integer.toString(intValue)).append("px;");
	}
	intValue = box.getHeight();
	if (intValue != Integer.MIN_VALUE) {
	   sb.append("height:").
		append(Integer.toString(intValue)).append("px;");
	}
	if (sb.length() > 0) {
	    writer.writeAttribute(HTMLAttributes.STYLE, sb.toString(), null);
	}

	ThemeImage boxImage = theme.getImage(Images.BoxImage.toString());
	writer.startElement(HTMLElements.IMG, component);
	writer.writeAttribute(HTMLAttributes.ALT, boxImage.getAlt(), null);
	writer.writeAttribute(HTMLAttributes.TITLE, boxImage.getTitle(), null);
	writer.writeAttribute(HTMLAttributes.WIDTH, 
	    Integer.valueOf(boxImage.getWidth()).toString() +
		boxImage.getUnits().toString(), null);
	writer.writeAttribute(HTMLAttributes.HEIGHT, 
	    Integer.valueOf(boxImage.getHeight()).toString() +
		boxImage.getUnits().toString(), null);
	writer.writeAttribute(HTMLAttributes.SRC,
		boxImage.getPath(), null);
	writer.endElement(HTMLElements.IMG);

	writer.endElement(HTMLElements.DIV);
    }
}
