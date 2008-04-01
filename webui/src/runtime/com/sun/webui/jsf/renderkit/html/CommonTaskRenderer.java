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

import com.sun.webui.html.HTMLAttributes;
import com.sun.webui.html.HTMLElements;
import com.sun.webui.jsf.component.CommonTask;
import com.sun.webui.jsf.component.CommonTasksGroup;
import com.sun.webui.jsf.component.CommonTasksSection;
import com.sun.webui.jsf.component.ImageHyperlink;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.theme.Theme;

import java.io.IOException;
import java.util.Iterator;
import java.beans.Beans;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>Renderer for a {@link com.sun.webui.jsf.component.CommonTask} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.CommonTask"))
public class CommonTaskRenderer extends AbstractRenderer {
 
    /**
     * <p>The set of integer pass-through attributes to be rendered.</p>
     */
    private static final String integerAttributes[] =
    { "tabIndex" }; //NOI18N

    /**
     *<p> Tooltip to be rendered for the "i" image.
     */
    private static final String INFO_IMAGE_TOOLTIP =
            "commonTasks.infoImageTooltip";   //NOI18N
    
    /**
     *<p> Tooltip to be rendered for the close image.
     */
    private static final String CLOSE_IMAGE_TOOLTIP =
            "commonTasks.closeImageTooltip";   //NOI18N
    
    /*
     *<p> Tooltip to be rendered for the common task overview image.
     */
    private static final String OVERVIEW_IMAGE_TOOLTIP =
            "commonTasks.overviewImageTooltip"; //NOI18N
    
    /**
     * <p>The set of String pass-through attributes to be rendered.</p>
     */
    private static final String stringAttributes[] =
    { "onBlur", "onFocus", "onDblClick", "onKeyDown", "onKeyPress", "onMouseUp", //NOI18N
      "onKeyUp", "onMouseDown", "onMouseMove", "onMouseOut", "onMouseOver"}; //NOI18N
        
    /**
     *Append this string for the  id for "i" image
     */
    private static final String TOGGLE_IMAGE = "_toggleImg";
    
    /**
     *Append this string for the id for info panel close link.
     */
    private static final String CLOSE_IMAGE = "_closeImg";
    
    /**
     *Append this string for the id for spacer image.
     */
    private static final String SPACER_IMAGE = "_spacerImg";
    
    /**
     *Append this string for the id of the toggle panel.
     */
    private static final String INFO_DIV = "_info";
    
    /**
     *Append this string for the id of title in info panel.
     */
    private static final String INFO_TITLE = "_infoTitle";
    
    /**
     *Append this string for the id of text in info panel.
     */
    private static final String INFO_TEXT = "_infoText";
    
    /**
     *Append this string for the id of the default hyperlink of commonTask
     */
    private static final String COMMONTASK_LINK = "_link";
    
    /**
     *Append this string for the id of div for the hyperlink in the info panel.
     */
    private static final String INFO_DIV_LINK = "_infoLinkDiv";
    /**
     * Append this string for the id of the ">>" image that comes in the bottom
     * part of the info panel.
     */
    private static final String HREF_LINK="_linkImage";    
    /**
     * Ids that are appended for the spans that are present inside the
     * hyperlink 
     */
    private static final String LEFT_BOTTOM = "_leftBottom_";
    private static final String LEFT_TOP = "_leftTop_";
    private static final String RIGHT_BOTTOM = "_rightBottom_";
    private static final String RIGHT_TOP = "_rightTop_";
    private static final String RIGHT_BORDER = "_rightBorder_";
    private static final String LINK_TEXT = "_linkText_";
    

    /**
     *Info icon image link for the task.
     */    
    private ImageHyperlink ihk;
    
    /**
     *Spacer  image used while rendering a toggle panel.
     */
    private Icon spacer;
      
    /** Creates a new instance of TaskRenderer */
    public CommonTaskRenderer() {
    }
    
    public boolean getRendersChildren() {
       return true;
    }
    /**
     * Render a common task.
     * 
     * @param context The current FacesContext
     * @param component The CommonTask object to render
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
       
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        CommonTask task = (CommonTask)component; 
  
        boolean info = false;
        if (!task.isRendered()) {
            return;
        }
        
        // The common task component should come inside either a common task group
        // component or inside a common tasks section component.
        if (!(task.getParent() instanceof CommonTasksGroup || 
                task.getParent() instanceof CommonTasksSection)) {
            return;
        }
        
        Theme theme = ThemeUtilities.getTheme(context);
        UIComponent comp = task.getTaskAction();  
        
        String compId = task.getClientId(context);
        writer.startElement(HTMLElements.DIV, task);
        writer.writeAttribute(HTMLAttributes.ID, task.
                getClientId(context), HTMLAttributes.ID); // NOI18N

        String styles = RenderingUtilities.getStyleClasses(context, task, 
                theme.getStyleClass(ThemeStyles.CTS_TASK));
        writer.writeAttribute(HTMLAttributes.CLASS,styles,
                HTMLAttributes.CLASS);          // NOI18N
        styles = task.getStyle();
        if (styles != null) {
            writer.writeAttribute(HTMLAttributes.STYLE, styles, 
                    HTMLAttributes.STYLE);
        }

        writer.startElement(HTMLElements.TABLE, task);       
        writer.writeAttribute(HTMLAttributes.BORDER,"0",
                HTMLAttributes.BORDER);          
        writer.writeAttribute(HTMLAttributes.CELLSPACING,"0",
                HTMLAttributes.CELLSPACING); 
        writer.writeAttribute(HTMLAttributes.CELLPADDING,"0",
                HTMLAttributes.CELLPADDING); 
        writer.writeAttribute(HTMLAttributes.CLASS, theme.getStyleClass
                (ThemeStyles.CTS_TASK_BACKGROUND), HTMLAttributes.CLASS);

        writer.startElement(HTMLElements.TR, component);


        // If no facet has been defined, render the default taskAction.
        if (comp == null) {
            comp = task;
        } 
        renderActionItem(comp, task, context, theme, writer);   
        String infoText = task.getInfoText();
        String infoTitle = task.getInfoTitle();
        UIComponent facet = task.getInfoPanel();          
        
        if (facet != null || infoText != null || 
                infoTitle != null ) {         
            renderInfoIcon(task, theme, context, writer);
            // Start a new table for rending the info panel.
            // Otherwise there appears to be a rendering problem
            // on IE.
             writer.endElement(HTMLElements.TR);
             writer.endElement(HTMLElements.TABLE);
             writer.startElement(HTMLElements.TABLE, task);
             writer.writeAttribute(HTMLAttributes.BORDER,"0",
                    HTMLAttributes.BORDER);          
             writer.writeAttribute(HTMLAttributes.CELLSPACING,"0",
                    HTMLAttributes.CELLSPACING); 
             writer.writeAttribute(HTMLAttributes.CELLPADDING,"0",
                    HTMLAttributes.CELLPADDING);             
            renderInfoText(task, theme, context, writer, infoText, infoTitle,
                    facet);                  
        } else {
            renderPlaceHolderImage(task, theme, context, writer);
         }
         writer.endElement(HTMLElements.TR);
         writer.endElement(HTMLElements.TABLE);
         writer.endElement(HTMLElements.DIV);           
    }
    
    /**
     * Renders the action item for a task. 
     * 
     * @param context The current FacesContext
     * @param component The action object to render
     * @param writer The current ResponseWriter
     * @param theme The theme used for the object 
     */
    
   
    protected void renderActionItem(UIComponent component, UIComponent task,
            FacesContext context, Theme theme, ResponseWriter writer) 
         throws IOException {
       
        writer.startElement(HTMLElements.TD, component); 
        writer.writeAttribute(HTMLAttributes.WIDTH,"2%",HTMLAttributes.WIDTH);          
        writer.writeAttribute(HTMLAttributes.VALIGN,"bottom",
              HTMLAttributes.VALIGN);      // NOI18N
        writer.writeAttribute(HTMLAttributes.CLASS, theme.getStyleClass
                            (ThemeStyles.CTS_TASK_LEFT), null);
            

        spacer = ThemeUtilities.getIcon(theme, ThemeImages.CTS_SPACER_IMAGE);
        spacer.setParent(task);
        spacer.setId(SPACER_IMAGE);
        RenderingUtilities.renderComponent(spacer, context);

        writer.endElement(HTMLElements.TD); 

        writer.startElement(HTMLElements.TD, component);
        writer.writeAttribute(HTMLAttributes.WIDTH,"100%",
                  HTMLAttributes.WIDTH);          

         writer.writeAttribute(HTMLAttributes.CLASS, theme.getStyleClass  
                (ThemeStyles.CTS_TASK_CENTER), HTMLAttributes.CLASS);    // NOI18N

          // If no facet is defined, render the default taskAction.
          // Else, render the component that has been specified in the fact.
          if (component instanceof CommonTask) {
              renderDefaultTaskAction((CommonTask)component, writer, context, theme);              
          }else {
              RenderingUtilities.renderComponent(component, context);
          }
          writer.endElement(HTMLElements.TD);   
    }
    
    /**
     *Renders an  placeholder image if no toggled information panel
     *comes up.
     * @param context The current FacesContext
     * @param component The commonTask object
     * @param writer The current ResponseWriter
     * @param theme The theme used for the object 
     */
    protected void renderPlaceHolderImage(UIComponent component, Theme theme, 
                FacesContext context, ResponseWriter writer) 
                throws IOException {
            
        writer.startElement(HTMLElements.TD, component); 
        writer.writeAttribute(HTMLAttributes.WIDTH,"3%",
                HTMLAttributes.WIDTH);
        writer.writeAttribute(HTMLAttributes.ALIGN,"right",
                HTMLAttributes.ALIGN);         // NOI18N
        writer.writeAttribute(HTMLAttributes.VALIGN,"top",
                HTMLAttributes.VALIGN);         // NOI18N 
        writer.writeAttribute(HTMLAttributes.CLASS,theme.getStyleClass
                (ThemeStyles.CTS_TASK_RIGHT),HTMLAttributes.CLASS);  // NOI18N

        String themeIcon = ThemeImages.CTS_RIGHT_TOGGLE_EMPTY;
        Icon icon = ThemeUtilities.getIcon(theme, themeIcon);
        icon.setParent(component);
        icon.setId(TOGGLE_IMAGE);                         // NOI18N
        RenderingUtilities.renderComponent(icon, context);
        writer.endElement(HTMLElements.TD);

    }
          
      /**
       *Render an info icon alongside the task. Clicking on this would
       *open a pane which contains information about the task.
       * @param context The current FacesContext
       * @param component The commonTask object
       * @param writer The current ResponseWriter
       * @param theme The theme used for the object 
       */
    protected void renderInfoIcon(UIComponent component, Theme theme,
           FacesContext context, ResponseWriter writer) throws IOException {

        writer.startElement(HTMLElements.TD, component);
        writer.writeAttribute(HTMLAttributes.WIDTH,"3%",
                HTMLAttributes.WIDTH);          
        writer.writeAttribute(HTMLAttributes.ALIGN,"right",
                HTMLAttributes.ALIGN); // NOI18N
        writer.writeAttribute(HTMLAttributes.VALIGN,"top",
                HTMLAttributes.VALIGN); // NOI18N
        writer.writeAttribute(HTMLAttributes.CLASS,theme.getStyleClass
                (ThemeStyles.CTS_TASK_RIGHT),HTMLAttributes.CLASS);  // NOI18N

        ihk = new ImageHyperlink();

        String themeIcon = null;
        themeIcon = ThemeImages.CTS_RIGHT_TOGGLE;
        ihk.setId(TOGGLE_IMAGE);                          // NOI18N
        ihk.setTabIndex(((Integer)component.getAttributes().get("tabIndex")).intValue());
        ihk.setIcon(themeIcon);
        ihk.setParent(component); 
        ihk.setToolTip(theme.getMessage(INFO_IMAGE_TOOLTIP));
        RenderingUtilities.renderComponent(ihk, context);
        writer.endElement(HTMLElements.TD);                        
    }
        
        
    /**
     * Renders the info panel for the task
     * @param context The current FacesContext
     * @param task The commonTask object
     * @param writer The current ResponseWriter
     * @param theme The theme used for the object 
     */
    protected void renderInfoText(CommonTask task, Theme theme,
              FacesContext context, ResponseWriter writer, 
            String infoText, String infoTitle, UIComponent facet)
            throws IOException {
        writer.startElement(HTMLElements.TR, task);
        writer.startElement(HTMLElements.TD, task);

        StringBuffer sb;
        ImageHyperlink close = new ImageHyperlink();
        // Start rendering the info menu..
        writer.startElement(HTMLElements.DIV, task); 
        writer.writeAttribute(HTMLAttributes.ID, 
              task.getClientId(context)+INFO_DIV, HTMLAttributes.ID);// NOI18N
        sb = new StringBuffer();

        writer.writeAttribute(HTMLAttributes.CLASS,theme.getStyleClass
            (ThemeStyles.CTS_TASK_INFOPANEL)+" "+
        theme.getStyleClass(ThemeStyles.HIDDEN), 
                  HTMLAttributes.CLASS); // NOI18N
        writer.startElement(HTMLElements.DIV, task);             // NOI18N
        writer.writeAttribute(HTMLAttributes.CLASS,
                theme.getStyleClass(ThemeStyles.CTS_INFOPANEL_CLOSE),
                HTMLAttributes.CLASS);

        close.setParent(task);
        close.setId(CLOSE_IMAGE);                     // NOI18N
        close.setTabIndex(task.getTabIndex());
        close.setToolTip(theme.getMessage(CLOSE_IMAGE_TOOLTIP));
        if (close.getParent() == null) {
            close.setParent(task); 
        }

        close.setIcon(ThemeImages.CTS_PANEL_CLOSE);            
        RenderingUtilities.renderComponent(close, context);

        writer.endElement(HTMLElements.DIV);           
        writer.startElement(HTMLElements.P, task);             
        writer.writeAttribute(HTMLAttributes.CLASS, theme.getStyleClass
                (ThemeStyles.CTS_TASK_INFO), HTMLAttributes.CLASS);            
        // If the infoPanel facet exists then use it.
        // instead of the stuff that is given in the tag attribute.
        if (facet == null) {             
            writer.startElement(HTMLElements.SPAN, task);

            writer.writeAttribute(HTMLAttributes.CLASS,
                  theme.getStyleClass(ThemeStyles.CTS_HEADER),
                  HTMLAttributes.CLASS);

            if (infoTitle != null) {
                writer.writeText(infoTitle, null);
            }

            writer.endElement(HTMLElements.SPAN);

            writer.startElement(HTMLElements.SPAN, task);
            writer.writeAttribute(HTMLAttributes.CLASS,
                 theme.getStyleClass(ThemeStyles.CTS_TASK_CONTENT), 
                  HTMLAttributes.CLASS);
            if (infoText != null) {
                writer.writeText(infoText, null);
            }
            writer.endElement(HTMLElements.SPAN);

        } else {
            RenderingUtilities.renderComponent(facet, context);
        }
        writer.endElement(HTMLElements.P);            
        facet = task.getInfoLink();
        // Check for the existence of an infoLink facet.
        if (facet != null) {
            renderBottomInfoPanel(task, facet, writer, theme, context); 
        }
        writer.endElement(HTMLElements.DIV);
        sb = new StringBuffer();

        UIComponent section = task.getParent();
        if (section instanceof CommonTasksGroup && section.getParent() 
          instanceof CommonTasksSection) {
            section = section.getParent();    
        }

        try {
            JSONObject json = null;
            if (facet != null) {
                json = getJSONProperties(context, theme, task, close, 
                section, facet);
            } else {
                json = getJSONProperties(context, theme, task, close, 
                section);                
            }

            sb.append(JavaScriptUtilities.getDomNode(context, section))
              .append(".addCommonTask(")
              .append(JSONUtilities.getString(json))
              .append(");\n"); //NOI18N
            JavaScriptUtilities.renderJavaScript(task, writer, sb.toString(),
                JavaScriptUtilities.isParseOnLoad());
        }catch(JSONException e) {
            e.printStackTrace();
        }
        // Note: The TR will be closed in renderEnd().
        writer.endElement(HTMLElements.TD);
    }
        
     /**
      * Renders the bottom section of the infoPanel
      * @param task The common task component
      * @param facet The component to be rendered at the bottom of the info panel.
      * @param writer The response writer
      * @param context Faces context
      * @param theme Theme used for the object
      */
      protected void renderBottomInfoPanel(UIComponent task, UIComponent facet,
              ResponseWriter writer, Theme theme, FacesContext context) 
              throws IOException{
          writer.startElement(HTMLElements.P, task);
          writer.writeAttribute(HTMLAttributes.CLASS, theme.getStyleClass
                  (ThemeStyles.CTS_TASK_MORE), HTMLAttributes.CLASS);// NOI18N
          
          Icon ic = new Icon();
          ic = ThemeUtilities.getIcon(theme, ThemeImages.HREF_LINK);
          ic.setBorder(0);
          ic.setParent(task);
          ic.setId(task.getId()+HREF_LINK);
          RenderingUtilities.renderComponent(ic, context);
          RenderingUtilities.renderComponent(facet, context);                   
          writer.endElement(HTMLElements.P);

      }
      
    /**
     * Get the common task object's parameters as JSON properties. 
     */
    protected JSONObject getJSONProperties(FacesContext context, Theme theme, 
          UIComponent component, ImageHyperlink close, UIComponent section,
          UIComponent bottomInfoPanel) throws IOException, JSONException {
        JSONObject json = getJSONProperties(context, theme, component, close, section);
        json.put("bottomInfoLink",bottomInfoPanel.getClientId(context));
        return json;
    }
   
    /**
     * Get the common task object's parameters as JSON properties. 
     */    
    protected JSONObject getJSONProperties(FacesContext context, Theme theme, 
          UIComponent component, ImageHyperlink close, UIComponent section) 
          throws IOException, JSONException {

        StringBuffer tmp = new StringBuffer();
        tmp.append(ihk.getClientId(context))
           .append(":")
           .append(ihk.getId())
           .append("_image");
        JSONObject json = new JSONObject();
        // common task id
        json.put("commonTaskId", component.getClientId(context))
              // id of the close link that closes the info panel.
             .put("closeId", close.getClientId(context))
             // id of the spacer image used for determining the info panel position
             .put("spacerId", SPACER_IMAGE) 
             // id of the displayed "i" image
             .put("infoIconId", tmp.toString())
             // id of the info panel div's prefix.
             .put("infoPanelVar", INFO_DIV)
             //id of the "i" link image
             .put("imageLinkId", ihk.getClientId(context));

        return json;
    }
     
    
    /**
     * Render a common task element.
     * 
     * @param context The current FacesContext
     * @param component The CommonTask object to render
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
       
    
    protected void renderStart(FacesContext context, UIComponent component,
                               ResponseWriter writer)
            throws IOException {
                
    }
    
    protected void renderDefaultTaskAction(CommonTask task, ResponseWriter writer,
            FacesContext context, Theme theme) throws IOException {
        String onclick= task.getOnClick();
        String target = task.getTarget();
        String tooltip = task.getToolTip();        
           
        writer.startElement(HTMLElements.A, task);
        writer.writeAttribute(HTMLAttributes.ID, task.getClientId(context)
            +COMMONTASK_LINK, HTMLAttributes.ID);
        
        // Render the "tabIndex".
        addIntegerAttributes(context, task, writer, integerAttributes);
        UIComponent form = Util.getForm(context, task);
        if (form != null) {
            String formClientId = form.getClientId(context);

            StringBuffer buff = new StringBuffer(200);
            if (onclick != null) {
                buff.append(onclick);
                if (!onclick.endsWith(";")) { //NOI18N
                    buff.append(";"); //NOI18N
                }
            }

            buff.append("return ") //NOI18N
                .append(JavaScriptUtilities.getModuleName("_html.hyperlink.submit")) //NOI18N
                .append("(this, ") //NOI18N
                .append("'") //NOI18N
                .append(formClientId)
                .append("'") //NOI18N
                .append(", "); //NOI18N

            boolean didOnce = false;
            Iterator kids = task.getChildren().iterator();
            while (kids.hasNext()) {
                UIComponent kid = (UIComponent) kids.next();
                if (!(kid instanceof UIParameter)) {
                    continue;
                }

                if (!didOnce) {
                    buff.append("new Array(");
                }
                String name = (String) kid.getAttributes().get("name"); //NOI18N
                String value = (String) kid.getAttributes().get("value"); //NOI18N
                //add to map for later use.
               if (!didOnce) {
                    buff.append("'");
                } else {
                    buff.append(",'");
                }
                buff.append(name);
                buff.append("','");
                buff.append(value);
                buff.append("'"); //NOI18N
                didOnce = true;
            }

            if (!didOnce) {
                buff.append("null");
            } else {
                buff.append(")");
            }

            buff.append(");");
            writer.writeAttribute(HTMLAttributes.ONCLICK, buff.toString(), null);
            writer.writeAttribute(HTMLAttributes.HREF, "#", null); //NOI18N
            writer.writeAttribute(HTMLAttributes.CLASS,theme.
                    getStyleClass(ThemeStyles.CTS_TEXT_BGROUND),null);
        }
        if (null != target) {
            writer.writeAttribute(HTMLAttributes.TARGET, target, null); //NOI18N
        }

        if (null != tooltip) {
            writer.writeAttribute(HTMLAttributes.TITLE, tooltip, null); //NOI18N
        }
        
        addIntegerAttributes(context, task, writer, integerAttributes);
        addStringAttributes(context, task, writer, stringAttributes);

        // We need to display these styles only at runtime.
        // They do not lend themselves very well at design time.
        if (!Beans.isDesignTime()) {
           renderStyles(task, writer, theme, context);
        }
            
        // Used for rendering the image along with the text.
        ImageComponent img = null;
        // The icon attribute gets preference over the imageURL attribute.
        // So, first check if the icon attribute is set. If not, check
        // whether the imageUrl attribute is set and set the image component's
        String icon = task.getIcon();
        // properties accordingly.
        if (icon != null) {
            img = ThemeUtilities.getIcon(theme, icon);
        } else {
	    img = new ImageComponent();
            icon = task.getImageUrl();
            if (icon != null){
              img.setUrl(icon);
              int dim = task.getImageHeight();
               if (dim > 0) {
                   img.setHeight(dim);
               }
              dim = task.getImageWidth();
               if (dim > 0) {
                   img.setWidth(dim);
               }
            }
        }
// Used for rendering the image along with the text.
        img.setId(task.getId()+"_img");
        img.setToolTip(theme.getMessage(OVERVIEW_IMAGE_TOOLTIP));
        img.setStyleClass(theme.getStyleClass(ThemeStyles.CTS_TASK_BULLET));
        writer.startElement(HTMLElements.SPAN, task);
        writer.writeAttribute(HTMLAttributes.CLASS, theme.getStyleClass
                (ThemeStyles.CTS_PADDING), null);
        writer.writeAttribute(HTMLAttributes.ID, task.getClientId(context)
        +LINK_TEXT, null);
        if (img.getUrl() != null) {
            RenderingUtilities.renderComponent(img, context);
        }
        String text = ConversionUtilities.convertValueToString(task,
			task.getText());
        if (text != null) {
            writer.writeText(text, null);
        }
        writer.endElement(HTMLElements.SPAN);           
        writer.endElement(HTMLElements.A);
    }
    
    protected void renderStyles(CommonTask component, ResponseWriter writer,
            Theme theme, FacesContext context) throws IOException {
        
        String clientId = component.getClientId(context);
        writer.startElement(HTMLElements.SPAN, component);
        writer.writeAttribute(HTMLAttributes.CLASS, theme.getStyleClass
               (ThemeStyles.CTS_LEFT_BOTTOM), null);
        writer.writeAttribute(HTMLAttributes.ID, clientId+LEFT_BOTTOM, null);
        writer.endElement(HTMLElements.SPAN);

        writer.startElement(HTMLElements.SPAN, component);
        writer.writeAttribute(HTMLAttributes.CLASS, theme.getStyleClass
               (ThemeStyles.CTS_LEFT_TOP), null);
        writer.writeAttribute(HTMLAttributes.ID, clientId+LEFT_TOP, null);
        writer.endElement(HTMLElements.SPAN);

        writer.startElement(HTMLElements.SPAN, component);
        writer.writeAttribute(HTMLAttributes.CLASS, theme.getStyleClass
               (ThemeStyles.CTS_RIGHT_BOTTOM), null);
        writer.writeAttribute(HTMLAttributes.ID, clientId+RIGHT_BOTTOM, null);
        writer.endElement(HTMLElements.SPAN);

        writer.startElement(HTMLElements.SPAN, component);
        writer.writeAttribute(HTMLAttributes.CLASS, theme.getStyleClass
               (ThemeStyles.CTS_RIGHT_TOP), null);
        writer.writeAttribute(HTMLAttributes.ID,clientId+RIGHT_TOP, null);
        writer.endElement(HTMLElements.SPAN);

        writer.startElement(HTMLElements.SPAN, component);
        writer.writeAttribute(HTMLAttributes.CLASS, theme.getStyleClass
                (ThemeStyles.CTS_RIGHT_BORDER), null);
        writer.writeAttribute(HTMLAttributes.ID, clientId+RIGHT_BORDER, null);
        writer.endElement(HTMLElements.SPAN);       
    }
    
    /**
     * Decode the submitted component.
     * 
     */
    public void decode(FacesContext context, UIComponent component) {
          
        // Enforce NPE requirements in the Javadocs
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
        CommonTask link = (CommonTask) component;
        StringBuffer paramId = new StringBuffer();
        String clientId = component.getClientId(context);
        paramId.append(clientId)
               .append(COMMONTASK_LINK)
               .append("_submittedLink");

        String value = (String) 
            context.getExternalContext().getRequestParameterMap().
            get(paramId.toString());

        if ((value == null) || !value.equals(clientId+
                COMMONTASK_LINK)) {
            return;
        }

        //add the event to the queue so we know that a command happened.
        //this should automatically take care of actionlisteners and actions
        link.queueEvent(new ActionEvent(link));
    }
    
}
