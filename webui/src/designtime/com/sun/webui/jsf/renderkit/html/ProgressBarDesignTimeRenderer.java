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
/*
 * ProgressBarDesignTimeRenderer.java
 *
 * Created on September 5, 2006, 11:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.webui.jsf.renderkit.html;


import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.sun.webui.jsf.component.ProgressBar;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.renderkit.widget.ProgressBarRenderer;


/**
 *
 * ProgressBar DesignTime Renderer
 */
public class ProgressBarDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    /** Creates a new instance of ProgressBarDesignTimeRenderer */
    public ProgressBarDesignTimeRenderer() {
        super(new ProgressBarRenderer());
    }
    
    public void encodeBegin(FacesContext context, UIComponent component)
    throws IOException {
        
        ResponseWriter writer = context.getResponseWriter();
        Theme theme = ThemeUtilities.getTheme(context);
        String clientId = component.getClientId(context);
        ProgressBar progressBar = (ProgressBar) component;
        
        //default attributes for progressbar
        
        //component top level div class
        String prgBarDivClassName = theme.getStyleClass(ThemeStyles.PROGRESSBAR);
        //progressbar container div class
        String prgBarContainerClassName = theme.getStyleClass(ThemeStyles.PROGRESSBAR_CONTAINER);
        //progressbar determinate div class
        String prgBarDeterminateClassName = theme.getStyleClass(ThemeStyles.PROGRESSBAR_DETERMINATE);
        //progressbar indeterminate div class
        String prgBarIndeterminateClassName = theme.getStyleClass(ThemeStyles.PROGRESSBAR_INDETERMINATE_PAUSED);
        //progressbar operation text div class
        String prgBarOperationClassName = theme.getStyleClass(ThemeStyles.PROGRESSBAR_OPERATION);
        //progressbar busy indicator div class
        String prgBarBusyClassName = theme.getStyleClass(ThemeStyles.PROGRESSBAR_BUSY);
        //progressbar right control div class
        String prgBarRightButtonClassName = theme.getStyleClass(ThemeStyles.PROGRESSBAR_RIGHTBUTTON);
        //progressbar bottom control div class
        String prgBarBottomButtonClassName = theme.getStyleClass(ThemeStyles.PROGRESSBAR_BOTTOMBUTTON);
        //progressbar status text (bottom text) div class
        String prgBarStatusClassName = theme.getStyleClass(ThemeStyles.PROGRESSBAR_STATUS);
        //progressbar log div class
        String prgBarLogClassname = theme.getStyleClass(ThemeStyles.PROGRESSBAR_LOG);
        //progressbar overlayanimation div class
        String prgBarLabelClassName = theme.getStyleClass(ThemeStyles.PROGRESSBAR_BARLABEL);
        //clear class to put the status text at bottom of the probressbar.
        String prgBarClearClassName = theme.getStyleClass(ThemeStyles.CLEAR);
        // busy indicator default image
        String prgBarBusyImage =
	    theme.getImagePath(ThemeImages.PROGRESS_BUSY_STILL);
        String hiddenStyleClass = ThemeUtilities.getTheme(context)
            .getStyleClass(ThemeStyles.HIDDEN);
        hiddenStyleClass = " " + hiddenStyleClass;
        
        
        if (progressBar.getTaskState() == null) {
            
            progressBar.setTaskState(ProgressBar.TASK_NOT_STARTED);
        }
        if (progressBar.getType() == null) {
            
            progressBar.setType("DETERMINATE");
        }
        if (progressBar.getRefreshRate() <= 0) {
            
            progressBar.setRefreshRate(3000);
        }
        
        String type = progressBar.getType();
        String bottomText = progressBar.getStatus();
        String topText = progressBar.getDescription();
        int progress = progressBar.getProgress();
        String taskState = progressBar.getTaskState();
        boolean visible = progressBar.isVisible();
        int refreshRate = progressBar.getRefreshRate();
        boolean overlayAnimation = progressBar.isOverlayAnimation();
        String progressImageUrl = progressBar.getProgressImageUrl();
        String logMessage = progressBar.getLogMessage();
        StringBuffer sb = new StringBuffer();
        
        if (progress < 0) progress = 40;
        
        sb.append("width:");
        sb.append(progress);
        sb.append(theme.getMessage("ProgressBar.percentChar"));
        
        String styleDeterminate = sb.toString();
        sb.setLength(0);
        sb.append(";background-image:");
        sb.append("url("+progressImageUrl+")");
        sb.append(";");
        
        String styleImage = sb.toString();
        sb.setLength(0);
        String dtClass = " rave-uninitialized-text"; //NOI18N
        
        //top level div
        writer.startElement("div", component);  //NOI18N
        writer.writeAttribute("id", clientId, null);  //NOI18N
        
        if (progressBar.isVisible()) {
            sb.append(prgBarDivClassName);
            sb.append(dtClass);
           writer.writeAttribute("class", sb.toString(), "styleClass");  //NOI18N
           sb.setLength(0);
        } else {
            sb.append(prgBarDivClassName);
            sb.append(dtClass);
            sb.append(hiddenStyleClass);
           writer.writeAttribute("class", sb.toString()
                   , "styleClass");  //NOI18N 
           sb.setLength(0);
        }   
        
        String style = (String) component.getAttributes().get("style"); //NOI18N
        
        
        if ((style != null) && (style.length() > 0)) {
            sb.append(style);
            
        }
        if (style == null || style.indexOf("width:") == -1) {
            sb.append("; width: 290px; "); // NOI18N
            
        }
        if (style == null || style.indexOf("height:") == -1) {
            
            sb.append("height: 70px; "); // NOI18N
        }
        writer.writeAttribute("style", sb.toString(), null); //NOI18N
        sb.setLength(0);
        
        //operation text div (top text)
        writer.startElement("div", component);  //NOI18N
        writer.writeAttribute("class", prgBarOperationClassName, null);  //NOI18N
        
        if (topText != null)
            writer.write(topText);
        writer.endElement("div");
        
        //write progressbar container div
        writer.startElement("div", component);  //NOI18N
        
        if (!type.equals("BUSY"))
            writer.writeAttribute("class", prgBarContainerClassName, null);  //NOI18N
        
        //modifying the height, width attribute if user specify it for bar container.
        String barHeightWidth = "";
        String barHeight = "";
        String barWidth = "";
        
        if ( progressBar.getHeight() > 0) {
            sb.append(";height:"); //NOI18N
            sb.append(progressBar.getHeight());
            barHeight = sb.toString();
            sb.setLength(0);
        }    
        if ( progressBar.getWidth() > 0) {
            sb.append(";width:"); //NOI18N
            sb.append(progressBar.getWidth());
            barWidth = sb.toString();  
            sb.setLength(0);
        }
        
        barHeightWidth = barHeight + barWidth;
        
        if ( barHeightWidth.length() > 0 )
            writer.writeAttribute("style", barHeightWidth , "style");  //NOI18N
        
        //write progressbar inner div
        writer.startElement("div", component);  //NOI18N
        
        if ( type.equals("DETERMINATE") ) {
            writer.writeAttribute("class", prgBarDeterminateClassName, null);  //NOI18N
            if (barHeight.length() > 0) {
                sb.append(styleDeterminate);
                sb.append(barHeight);
                writer.writeAttribute("style",
                        sb.toString() , "style");  //NOI18N
                sb.setLength(0);
            } else {
                writer.writeAttribute("style",
                        styleDeterminate , "style");  //NOI18N
            }
            
            if (progressImageUrl != null){
                if (barHeight.length() > 0) {
                    sb.append(styleDeterminate);
                    sb.append(styleImage);
                    sb.append(barHeight);
                    writer.writeAttribute("style",
                            sb.toString() , "style");  //NOI18N
                    sb.setLength(0);
                } else {
                    sb.append(styleDeterminate);
                    sb.append(styleImage);
                    writer.writeAttribute("style",
                            sb.toString(), "style");  //NOI18N
                    sb.setLength(0);
                }
            }
            
        } else if (type.equals("INDETERMINATE")) {
            
            writer.writeAttribute("class", prgBarIndeterminateClassName, null);  //NOI18N
            
            if (progressImageUrl != null) {
                if (barHeight.length() > 0) {
                    sb.append(styleImage);
                    sb.append("width:100%");
                    sb.append(barHeight);
                    writer.writeAttribute("style",
                            sb.toString() , "style");  //NOI18N
                    sb.setLength(0);
                } else {
                    sb.append(styleImage);
                    sb.append("width:100%");
                    writer.writeAttribute("style",
                            sb.toString() , "style");  //NOI18N
                    sb.setLength(0);
                }
            } else {
                if (barHeight.length() > 0) {
                    sb.append("width:100%");
                    sb.append(barHeight);
                    writer.writeAttribute("style", sb.toString(), "style");  //NOI18N
                    sb.setLength(0);
                } else {
                    writer.writeAttribute("style", "width:100%" , "style"); //NOI18N
                }
            }
        }
        writer.endElement("div");
        
        //overlay animation div
        if (overlayAnimation == true && type.equals("DETERMINATE") ) {
            writer.startElement("div", component);  //NOI18N
            if ( barHeightWidth.length() > 0 ) {
                writer.writeAttribute("style", barHeightWidth , "style");  //NOI18N
            }
            writer.write(progress+"%");
            writer.writeAttribute("class", prgBarLabelClassName, null);  //NOI18N
            writer.endElement("div");
        }
        
        // busy indicator associated with outermost div
        if (type.equals("BUSY")) {
                        
            sb.append("<img src=\"");
            
            if (progressImageUrl == null || progressImageUrl.length() == 0) {
                sb.append(prgBarBusyImage);
            } else {
                sb.append(progressImageUrl);
            }
            sb.append("\"");
            if (progressBar.getHeight() > 0) {
                sb.append(" height=\"");
                sb.append(progressBar.getHeight());
                sb.append("\"");
            }
            if (progressBar.getWidth() > 0) {
                sb.append(" width=\"");
                sb.append(progressBar.getWidth());
                sb.append("\"");
            }
            sb.append("/>");
            
            writer.write(sb.toString());
            sb.setLength(0);
            
        }
        //end div barcontainer
        writer.endElement("div");
        
        writer.startElement("div", component);  //NOI18N
        writer.writeAttribute("class", prgBarClearClassName, null);  //NOI18N
        writer.endElement("div");
        
        //status text div (bottom text)
        writer.startElement("div", component);  //NOI18N
        writer.writeAttribute("class", prgBarStatusClassName, null);  //NOI18N
        
        if (bottomText != null)
            writer.write(bottomText);
        writer.endElement("div");
        
        //end div component
        writer.endElement("div");
                
    }
    
    public void encodeChildren(FacesContext context, UIComponent component) 
        throws IOException {
        // don't do anything
    }
    
    public void encodeEnd(FacesContext context, UIComponent component)
        throws IOException {
        //don't do anything.
    }
}
