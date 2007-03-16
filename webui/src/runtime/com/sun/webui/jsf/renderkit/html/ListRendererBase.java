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
 * $Id: ListRendererBase.java,v 1.3 2007-03-16 18:54:46 rratta Exp $
 */
package com.sun.webui.jsf.renderkit.html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectMany;
import javax.faces.component.ValueHolder;
import javax.faces.component.EditableValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.sun.webui.jsf.component.ComplexComponent;
import com.sun.webui.jsf.component.ListManager;
import com.sun.webui.jsf.component.ListSelector;
import com.sun.webui.jsf.component.Label;
import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.jsf.model.Separator;
import com.sun.webui.jsf.model.OptionTitle;
import com.sun.webui.jsf.model.list.ListItem;
import com.sun.webui.jsf.model.list.StartGroup;
import com.sun.webui.jsf.model.list.EndGroup;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.theme.Theme;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The ListRendererBase is the base class for the listbox renderers
 * (Drop-down Menu and Selectable List). These are both rendered using
 * the same HTML tag (select) so a lot of the renderering functionality
 * is shared.
 */
abstract public class ListRendererBase extends Renderer {
    
    /**
     *
     */
    private final static boolean DEBUG = false;
    
     /** <p>The list of attribute names in the HTML 4.01 Specification that
     * correspond to the entity type <em>%events;</em>.</p>
     */
    public static final String[] STRING_ATTRIBUTES =
    { "onBlur", "onClick", "onDblClick", "onFocus",  //NOI18N
      "onMouseDown", "onMouseUp", "onMouseOver",    //NOI18N
      "onMouseMove", "onMouseOut", "onKeyPress",    //NOI18N
      "onKeyDown", "onKeyUp", "onSelect"            //NOI18N
    };
    
    protected static final String SEPARATOR = "|";  //NOI18N
    
    /**
     * <p>This method determines whether the component should be
     * rendered as a standalone list, or laid out together with a
     * label that was defined as part of the component.</p>
     *
     * <p>A label will be rendered if either of the following is
     * true:</p>
     * <ul>
     * <li>The page author defined a label facet; or</li>
     * <li>The page author specified text in the label attribute.</li>
     * </ul>
     * <p>If there is a label, the component will be laid out using a
     * HTML table. If not, the component will be rendered as a
     * standalone HTML <tt>select</tt> element.</p>
     * @param component The component associated with the
     * renderer. Must be a subclass of ListSelector.
     * @param context The FacesContext of the request
     * @param styles A String array of styles used to render the
     * component. The first item of the array is the name of the
     * JavaScript method that handles change event. The second item is
     * the style used when the list is enabled. The third style is the
     * one to use when the list is disabled. The fourth item is the
     * style to use for an item that is enabled, the fifth to use for
     * an item that is disabled, and the sixth to use when the item is
     * selected.
     * @throws java.io.IOException if the renderer fails to write to
     * the response
     */
    void renderListComponent(ListSelector component, FacesContext context,
            String[] styles)
            throws IOException {
        
        if(DEBUG) log("renderListComponent()");
        
        UIComponent label = component.getLabelComponent();
        ResponseWriter writer = context.getResponseWriter();   
        String id = component.getClientId(context); 

        // Always render the span
        //
        renderOpenEncloser(component, context, "span", styles[8]); //NOI18N
            
        if( label != null) {
            writer.writeText("\n", null);
            if(!component.isLabelOnTop() && component.getRows() > 1) {
                Map attributes = label.getAttributes();
                Object styleClass = attributes.get("styleClass");
                if(styleClass == null) {
                    attributes.put("styleClass", styles[9]);
                } else if(styleClass.toString().indexOf(styles[9]) == -1) {
                    attributes.put("styleClass", styleClass + " " + styles[9]);
                }
            }
            
            RenderingUtilities.renderComponent(label, context);
            writer.writeText("\n", null);
            if(component.isLabelOnTop()) {
              
                writer.startElement("br", component); //NOI18N
                writer.endElement("br");         //NOI18N
                writer.writeText("\n", null);
            }
       
            writer.writeText("\n", null);    
        }
        
        boolean readonly = component.isReadOnly();     
        if(readonly) {
            UIComponent value = component.getReadOnlyValueComponent();
            if(label == null) { 
                value.getAttributes().put("style", component.getStyle()); 
                value.getAttributes().put("styleClass", component.getStyleClass()); 
            } 
            RenderingUtilities.renderComponent(value,context);            
        } 
        else {
            // If its a complex componenet call getLabeledElementId
            // this must be the id of the element that is submitted
            // upon request, else leave it as getClientId().
            //
            if (component instanceof ComplexComponent) {
                id = ((ComplexComponent)component).getLabeledElementId(context);
            }

            //renderHiddenValue(component, context, writer, styles[8]);
            //writer.writeText("\n", null);

            // Because renderHiddenValue is commented out this needs
            // to be called for supporting DB NULL values.
            // If it becomes uncommented remove this call.
            //
            recordRenderedValue(component);
            renderList(component, id, context, styles);
        }
        context.getResponseWriter().endElement("span"); //NOI18N
    }
    
    /**
     * Overrides encodeChildren of Renderer to do nothing. This
     * renderer renders its own children, but not through this
     * method.
     * @param context The FacesContext of the request
     * @param component The component associated with the
     * renderer. Must be a subclass of ListSelector.
     */
    public void encodeChildren(javax.faces.context.FacesContext context,
            javax.faces.component.UIComponent component)
            throws java.io.IOException {
        return;
    }
    
    
    /**
     * <p>Renders the opening div tag.</p>
     * @param component The component associated with the
     * renderer. Must implement ListManager
     * @param context The FacesContext of the request
     * @param element One of "span" or "div"
     * @throws java.io.IOException if the renderer fails to write to
     * the response
     */
    protected void renderOpenEncloser(ListManager component, 
                                      FacesContext context,
                                      String element, 
                                      String hiddenStyle)
        throws IOException {
        
        String id = component.getClientId(context);
        
        ResponseWriter writer = context.getResponseWriter();
        writer.writeText("\n", null);
        writer.startElement(element, (UIComponent)component);
        writer.writeAttribute("id", id, "id"); //NOI18N
        
        String style = component.getStyle();
        if(style != null && style.length() > 0) {
            writer.writeAttribute("style", style, "style");      //NOI18N
        }
        style = component.getStyleClass();
        if(component.isVisible()) {
            if(style != null && style.length() > 0) {
                writer.writeAttribute("class", style, "class"); //NOI18N
            }
        } else {
            if(style == null) {
                style = hiddenStyle;
            } else {
                style = style + " " + hiddenStyle; //NOI18N
            }
            writer.writeAttribute("class", style, "class"); //NOI18N
        }
        
        writer.writeText("\n", null);                         //NOI18N
    } 

    protected void renderHiddenValue(UIComponent component, FacesContext context, 
                                     ResponseWriter writer, String hiddenStyle) 
        throws IOException { 
        
        ListManager listManager = (ListManager)component;
        
        recordRenderedValue(component);

        String hiddenID = component.getClientId(context).concat(ListSelector.VALUE_ID);
        String hiddenLabelID = component.getClientId(context).concat(ListSelector.VALUE_LABEL_ID);

        String[] values = listManager.getValueAsStringArray(context); 
        if(DEBUG) { 
            log("Values are: "); 
            for(int i=0; i<values.length; ++i) { 
                log(String.valueOf(values[i])); 
            }   
        }

        // Write a hidden label to pacify a11y checkers.
        writer.startElement("label", component); //NOI18N
        writer.writeAttribute("id", hiddenLabelID, null); //NOI18N
        writer.writeAttribute("for", hiddenID, "for"); //NOI19N
        writer.writeAttribute("class", hiddenStyle, null); //NOI18N     
        writer.endElement("label"); //NOI18N
        // Write the hidden <select> 

        writer.startElement("select", component); //NOI18N
        writer.writeAttribute("id", hiddenID, null); //NOI18N
        writer.writeAttribute("name", hiddenID, null); //NOI18N
        writer.writeAttribute("multiple", "true", null); //NOI18N
        writer.writeAttribute("class", hiddenStyle, null); //NOI18N
        writer.writeText("\n", null); //NOI18N
        for(int counter=0; counter<values.length; ++counter) {
            writer.startElement("option", component); //NOI18N
            writer.writeAttribute("selected", "selected", null); //NOI18N
            writer.writeAttribute("value", values[counter], null); //NOI18N
            writer.writeText(values[counter], null); //NOI18N
            writer.endElement("option"); //NOI18N
            writer.writeText("\n", null); //NOI18N          
        }
        writer.endElement("select"); //NOI18N       
    }
    
    /**
     * This is the base method for rendering a HTML select
     * element. This method is based on the functionality of the RI
     * version, so it invokes a method renderSelectItems which in term
     * invokes renderSelectItem. Currently, this renderer requires for
     * the options to be specified using the JSF SelectItem construct,
     * but this should be replaced with a Lockhart version, because
     * the JSF version lacks the ability to associate an id with the
     * list item. I'm not sure whether it should be possible to use
     * SelectItem as well yet.
     * @param component The UI Component associated with the
     * renderer.
     * @param context The FacesContext of the request
     * @param styles A String array of styles used to render the
     * component. The first item of the array is the name of the
     * JavaScript method that handles change event. The second item is
     * the style used when the list is enabled. The third style is the
     * one to use when the list is disabled. The fourth item is the
     * style to use for an item that is enabled, the fifth to use for
     * an item that is disabled, and the sixth to use when the item is
     * selected.
     * @throws java.io.IOException if the renderer fails to write to
     * the response
     */
    protected void renderList(ListManager listManager, String id, 
                              FacesContext context, String[] styles)
            throws IOException {
        
        // Set the style class
        String styleClass = styles[1];
        if(listManager.isDisabled()) { 
            styleClass = styles[2];
        } 

        ResponseWriter writer = context.getResponseWriter();
   
        writer.startElement("select", (UIComponent)listManager); //NOI18N

        writer.writeAttribute("class", styleClass, null);      //NOI18N
        writer.writeAttribute("id", id, null); //NOI18N
        if(listManager.mainListSubmits()) {
            writer.writeAttribute("name", id, null);           //NOI18N
        }
        int size = listManager.getRows();
        if(size < 1) {
            size = 12;
        }
        writer.writeAttribute("size", String.valueOf(size), null); //NOI18N
        
        if(listManager.isMultiple()) {
            writer.writeAttribute("multiple", "multiple", null); //NOI18N
        }
        
        if(listManager.isDisabled()) {
            writer.writeAttribute("disabled",  //NOI18N
                    "disabled",  //NOI18N
                    "disabled"); //NOI18N
        }
        
        String tooltip = listManager.getToolTip();
        if(tooltip != null) {
            writer.writeAttribute("title", tooltip, null); //NOI18N
        }
        
        if(DEBUG) log("Setting onchange event handler");
        writer.writeAttribute("onchange", styles[0], null);    //NOI18N
        
        int tabindex = listManager.getTabIndex();
        if(tabindex > 0 && tabindex < 32767) {
            writer.writeAttribute("tabindex",               //NOI18N
                    String.valueOf(tabindex),
                    "tabindex");              //NOI18N
        }
        
        RenderingUtilities.writeStringAttributes((UIComponent)listManager, 
                writer, STRING_ATTRIBUTES);
           
        writer.writeText("\n", null); //NOI18N
        
        renderListOptions((UIComponent)listManager, 
                          listManager.getListItems(context, true),
                          writer, styles);
        
        writer.endElement("select");  //NOI18N
        writer.writeText("\n", null); //NOI18N
    }
      
    /**
     * Append the webui component's own JavaScript function at the end
     * of any component-specific event handling code.
     * @param component The ListManager for which we create the function call
     * @param functionName The name of the function
     * @param context The FacesContext of this request
     */
    protected String getOnChangeJavaScript(ListManager component,
            String functionName,
            FacesContext context) {
        
        String script = component.getOnChange();
        String id = component.getClientId(context);
        StringBuffer onchangeBuffer = new StringBuffer(200);
        if(script != null) {
            onchangeBuffer.append(script).append(";");
        }
        onchangeBuffer.append(functionName);
        onchangeBuffer.append("(\'"); //NOI18N
        onchangeBuffer.append(id);
        onchangeBuffer.append("\'); "); //NOI18N
        onchangeBuffer.append(" return false;"); //NOI18N
        return onchangeBuffer.toString();
    }
    
    /**
     * This is the method responsible for rendering the options of a
     * HTML select element. This method is based on the corresponding
     * method from the JSF RI, so the options to be specified using
     * the JSF SelectItem construct. This will have to be replaced -
     * see the renderList method for details.
     * <p/><i>Note - option groups are not yet implemented w.r.t. any
     * styles  specified by the HCI guidelines.</i>
     * @param component The UI Component associated with the renderer
     * @param styles A String array of styles used to render the
     * component. The first item of the array is the name of the
     * JavaScript method that handles change event. The second item is
     * the style used when the list is enabled. The third style is the
     * one to use when the list is disabled. The fourth item is the
     * style to use for an item that is enabled, the fifth to use for
     * an item that is disabled, and the sixth to use when the item is
     * selected.
     * @throws java.io.IOException if the renderer fails to write to
     * the response
     */
    void renderListOptions(UIComponent component,
            Iterator optionsIterator,
            ResponseWriter writer,
            String[] styles)
            throws IOException {
        
        if(DEBUG) log("renderListOptions() START");
        
        Object option = null;
        boolean noSeparator = true;
        
        while (optionsIterator.hasNext()) {
            
            option = optionsIterator.next();
            
            if (option instanceof Separator) {
                if(DEBUG) log("\tFound separator"); //NOII18N
                renderSeparator(component, writer, styles[7]);
            }
            
            else if (option instanceof StartGroup ) {
                
                if(DEBUG) log("\tFound option group"); //NOII18N
                
                StartGroup group = (StartGroup)option;
                if(DEBUG) {
                    log("\tThis is the start of a group"); //NOI18N
                    log("\tLabel is" + group.getLabel());  //NOI18N
                }
                
                if(!noSeparator) {
                    renderSeparator(component, writer, styles[7]);
                }
                writer.startElement("optgroup", component);     //NOI18N
                writer.writeAttribute("label",             //NOI18N
                        group.getLabel(),
                        null);
                writer.writeAttribute("class",             //NOI18N
                        styles[6],
                        null);
                writer.write("\n");                        //NOI18N
                noSeparator = true;
            } 
            else if(option instanceof EndGroup ) {
                if(DEBUG) log("\tThis is the end of a group");
                writer.endElement("optgroup");         //NOI18N
                writer.write("\n");                    //NOI18N
                if(optionsIterator.hasNext()) {
                    renderSeparator(component, writer, styles[7]);
                }
                noSeparator = true;
            } else {
                renderListOption(component, (ListItem)option, writer, styles);
                noSeparator = false;
            }
        }
        if(DEBUG) log("renderListOptions() END");
    }

    
    /**
     *
     * This is the method responsible for rendering an individual
     * option for a HTML select element.
     *
     * @param option The ListItem to render
     * @param writer The ResponseWriter used to render the option
     * @param styles A String array of styles used to render the
     * component. The first item of the array is the name of the
     * JavaScript method that handles change event. The second item is
     * the style used when the list is enabled. The third style is the
     * one to use when the list is disabled. The fourth item is the
     * style to use for an item that is enabled, the fifth to use for
     * an item that is disabled, and the sixth to use when the item is
     * selected.
     * @throws java.io.IOException if the renderer fails to write to
     * the response
     */
    void renderListOption(UIComponent list,
            ListItem listItem,
            ResponseWriter writer,
            String[] styles)
            throws IOException {
    
        if(DEBUG) log("renderListOption() - START"); 
        
        // By default, we use the basic option style
        String styleClass = styles[3];
        
        // CR 6317842. Regardless if the option is currently selected or        
        // not, the disabled option style should be used when the option
        // is disabled. So, check for the disabled item first.        
        if(listItem.isDisabled()) {
            if(DEBUG) log("\tItem is disabled");
            // Use "disabled" option style
            styleClass = styles[4];                            
        } 
        else if(listItem.isSelected()) {
            if(DEBUG) log("\tItem is selected");
            // Use "selected" option style
            styleClass = styles[5];
            if(DEBUG) log("\tStyleclass is " + styleClass); 
        } else if(DEBUG) {
            if(DEBUG) log("\tNormal item");
        }
        
        writer.writeText("\t", null);                            //NOI18N
        writer.startElement("option", list);                     //NOI18N
        writer.writeAttribute("class", styleClass, null);        //NOI18N
        String itemValue = listItem.getValue();

        // Note that there is no distinction made between an
        // itemValue that is null or an empty string.
        // This is important since the results may be indistinguishable
        // on the client and therefore indistinguishable in the response.
        // 
        // However Option which inherits from SelectItem does not
        // allow null item values.
        //
        if(itemValue != null) {
            if(DEBUG) log("Item value is not null"); 
            writer.writeAttribute("value", itemValue, null);      //NOI18N
        }
        if(listItem.isDisabled()) {
            writer.writeAttribute("disabled", "disabled", null); //NOI18N
        }
        if(listItem.isSelected()) {
            if(DEBUG) log("\tWriting selected attribute"); 
            writer.writeAttribute("selected", "selected", null); //NOI18N
        }
        
        boolean title = listItem.isTitle();
        if(title) { 
            writer.write("&#8212; "); 
        } 
        writer.write(listItem.getLabel());
        if(title) { 
            writer.write(" &#8212;"); 
        } 
        writer.endElement("option");                             //NOI18N
        writer.writeText("\n", null);                            //NOI18N
        if(DEBUG) log("\trenderListOption() - END"); 
        return;
    }
    
    /**
     *
     * This method is responsible for rendering a separator for an
     * option group.
     *
     * @param option The component for which we render the separator
     * @param writer The ResponseWriter used to render the separator
     * @param style The style to use when rendering the option.
     * @throws java.io.IOException if the renderer fails to write to
     * the response
     */
    void renderSeparator(UIComponent component, ResponseWriter writer,
            String style) throws IOException {
        
        if(!(component instanceof ListSelector)) {
            return;
        }
        
        ListSelector selector = (ListSelector)component;
        if(!selector.isSeparators()) {
            return;
        }
        
        writer.writeText("\t", null);                        //NOI18N
        writer.startElement("option", component);                 //NOI18N
        writer.writeAttribute("class", style, null);         //NOI18N
        writer.writeAttribute("disabled", "disabled", null); //NOI18N
        
        int numEms = selector.getSeparatorLength();
        StringBuffer labelBuffer = new StringBuffer();
        for(int em = 0; em < numEms; ++em) {
            labelBuffer.append("-");                         //NOI18N
        }
        
        writer.writeText(labelBuffer.toString(), null);
        writer.endElement("option");                         //NOI18N
        writer.writeText("\n", null);                        //NOI18N
    }
    
    /** This method is used by some of the renderers that extend
        ListRenderBase (not ListBox and DropDown). I should 
        probably refactor things so that we always know what
        the hidden style is instead - then renderListComponent
        would do. 
        TODO.
     */
    void renderReadOnlyList(ListManager component, UIComponent label,
                                      FacesContext context, String hiddenStyle)
        throws IOException {
        
        UIComponent value = component.getReadOnlyValueComponent();
        
        renderOpenEncloser(component, context, "span", hiddenStyle);
        if(label != null) {
            RenderingUtilities.renderComponent(label,context);
        }
        RenderingUtilities.renderComponent(value,context);
        context.getResponseWriter().endElement("span"); //NOI18N
    }

    // Prepend the hidden style to the user's added style if necessary
    private String getStyleClass(ListManager component, String hiddenStyle) {
        
        String style = component.getStyleClass();
        if(style != null && style.length() == 0) {
            style = null;
        }
        
        if(!component.isVisible()) {
            if(style == null) {
                style = hiddenStyle;
            } else {
                style = style + " " + hiddenStyle;
            }
        }
        return style;
    }

     
    /**
     * Retrieve user input from the UI.
     * @param context The FacesContext of this request
     * @param component The component associated with the renderer
     */
    public void decode(FacesContext context, UIComponent component) {
        
        if(DEBUG) log("decode()");

        // The other decode implementation does this too
        // but we don't want to get there because 
        // getLabeledElementId returns null if a component is readOnly.
        // We should probably return if disabled too.
        // It's too late to refactor this code.
        //
        if (((ListManager)component).isReadOnly()) {
            return;
        }
        
        // The submitted select element will always have the value
        // of getLabeledElementId. Leaving the following comment 
        // for historical reasons.

        // **** No Longer True ****
        // We used to depend on getLabelComponent() returning non-null
        // to calculate the ID to be used to retrieve parameters for the 
        // component. But after the changes made to the facet management, 
        // that mechanism doesn't work anymore. 
        // We can simply see if we have any input for any of the 
        // possible parameter names instead, similar to what field 
        // does. It works, though we can technically end up looking 
        // at the wrong parameter, if there was no input. 
        // *****
        String id = component.getClientId(context); 
        if (component instanceof ComplexComponent) {
            id = ((ComplexComponent)component).getLabeledElementId(context);
        }
        /*
        Map params = context.getExternalContext().getRequestParameterMap();
        Object valueObject = params.get(id);
        
        if(valueObject == null) { 
           id = id.concat(ListSelector.LIST_ID);
        }
        */
        decode(context, component, id);
    }
    
    /**
     * Retrieve user input from the UI.
     * The expected format of the request parameter of interest is
     * <separator>value<separator>value<separator> ...
     * If a value is an empty string the format is
     * <separator><separator>
     * If there is no value there is a single separator.
     * @param context The FacesContext of this request
     * @param component The component associated with the renderer
     * @param id The DOM id of the select element which represents the 
     * value of the list
     */
    protected void decode(FacesContext context, UIComponent component,
            String id) {
        
        if(DEBUG) log("decode(context, component, id)");
        if(DEBUG) log("id is " + id);
        ListManager lmComponent = (ListManager)component;
        
        if(lmComponent.isReadOnly()) {
            if(DEBUG) log("component is readonly...");
            return;
        }
        
        Map params = context.getExternalContext().getRequestParameterValuesMap();
        
        String[] values = null; 
        Object p = params.get(id); 
        if(p == null) { 
            values = new String[0];
        }
        else {
            if(DEBUG) log("\tValue is string array");
            values = (String[])p;
        } 
            
        // If we find the OptionTitle.NONESELECTED
        // value among a multiple selection list, remove it.
        // If there is only one value and it matches
        // OptionTitle.NONESELECTED then act like the
        // submit did not happen at all and leave the submitted
        // value as is. It should be null, thereby effectively
        // taking this component out of further lifecycle processing.
        //
        if (values.length > 1) {
            // Need to remove any OptionTitle submitted values
            //
            ArrayList newParams = new ArrayList();
            for (int i = 0; i < values.length; ++i) {
                if (OptionTitle.NONESELECTED.equals(values[i])) {
                    continue;
                }
                newParams.add(values[i]);
            }
            values = (String[])newParams.toArray(new String[newParams.size()]);
        } else
        if (values.length == 1 &&
                OptionTitle.NONESELECTED.equals(values[0])) {
            return;
        }


        if(DEBUG) {
            log("\tNumber of Selected values " +    //NOI18N
                    String.valueOf(values.length));
            for(int counter = 0; counter < values.length; ++counter)
                log("\tvalue: " + values[counter]); //NOI18N
        }
        
        //CR 6455533/6447372
        //if component is disabled, don't set empty array as submitted value
        //so only set if values.length > 0, or if it's == 0 and the component
        //is not disabled
        if (values.length > 0 || !lmComponent.isDisabled()) {
            lmComponent.setSubmittedValue(values);
        }
      
    }
    
    
    /**
     * The list is not responsible for rendering any child components,
     * so this method returns false. (This is unintuitive, but it
     * causes the right behaviour). I need to understand this better.
     */
    public boolean getRendersChildren() {
        return true;
    }
    
    
    /**
     * Retrieves the selected values as Strings.
     * @param context The FacesContext of this request
     * @param component The component associated with the renderer
     */
    String[] getUserInput(FacesContext context,
            UIComponent component) {
        
        if(DEBUG) log("::getUserSelectedValues()");
        String id = component.getClientId(context);
        // Util.doAssert(id != null);
        Map params =
                context.getExternalContext().getRequestParameterValuesMap();
        
        String[] values = null;
        if(params.containsKey(id)) {
            values = (String[])params.get(id);
        }
        if(values == null) {
            values = new String[0];
        }
        if(DEBUG) {
            log("\tNumber of Selected values " + // NOI18N
                    String.valueOf(values.length));
            for(int counter = 0; counter < values.length; ++counter)
                log("\t" + values[counter]);     //NOI18N
        }
        return values;
    }

   
    /**
     * Log an error - only used during development time.
     */
    void log(String s) {
        System.out.println(this.getClass().getName() + "::" + s); //NOI18N
    }

    /**
     * This must be called where the value is about to be rendered
     * for DB Null value support
     */
    private void recordRenderedValue(UIComponent component) {
        
        if (component instanceof EditableValueHolder &&
                ((EditableValueHolder)component).getSubmittedValue() == null) {
            ConversionUtilities.setRenderedValue(component, 
                    ((EditableValueHolder)component).getValue());
        }
    }
}
