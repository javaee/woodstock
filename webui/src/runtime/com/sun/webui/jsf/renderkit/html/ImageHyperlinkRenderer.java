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
import com.sun.webui.jsf.component.ImageHyperlink;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import java.io.IOException;
import javax.faces.component.UIComponent;


import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;


/**
 * <p>This class is responsible for rendering the {@link ImageHyperlink} component for the
 * HTML Render Kit.</p> <p> The {@link ImageHyperlink} component can be used as an anchor, a
 * plain hyperlink or a hyperlink that submits the form depending on how the
 * properites are filled out for the component </p>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.ImageHyperlink"))
public class ImageHyperlinkRenderer extends HyperlinkRenderer {
    
    // -------------------------------------------------------- Static Variables
    
    // for positioning of the label.
    private static final String LABEL_LEFT="left"; //NOI8N
    private static final String LABEL_RIGHT="right"; //NOI8N
    
    // -------------------------------------------------------- Renderer Methods
    protected void finishRenderAttributes(FacesContext context,
            UIComponent component,
            ResponseWriter writer)
            throws IOException {
        //create an image component based on image attributes
        //write out image as escaped text
        //TODO: suppress the text field from the XML
        ImageHyperlink ilink = (ImageHyperlink) component;
              
        // ImageURL
	ImageComponent ic = ilink.getImageFacet();
                       
        // If there is no text property set, then label == null which prevents
        // rendering anything at all
	//
        Object text = ilink.getText();
        String label = (text == null) ? null : 
		ConversionUtilities.convertValueToString(component, text);
        
	String textPosition = ilink.getTextPosition();

	if (label != null && textPosition.equalsIgnoreCase(LABEL_LEFT)) {
	    writer.writeText(label, null);
            writer.write("&nbsp;");
	}
	if (ic != null) {
	    RenderingUtilities.renderComponent(ic, context);
	}

	if (label != null && textPosition.equalsIgnoreCase(LABEL_RIGHT)) {
            writer.write("&nbsp;");
	    writer.writeText(label, null);
	}
 
    }
    
    // --------------------------------------------------------- Private Methods
    
}
