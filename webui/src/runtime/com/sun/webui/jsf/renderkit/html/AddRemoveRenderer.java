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
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.html.HTMLAttributes;
import com.sun.webui.html.HTMLElements;
import com.sun.webui.jsf.component.ComplexComponent;
import com.sun.webui.jsf.component.AddRemove;
import com.sun.webui.jsf.component.ListSelector;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme;

import java.io.IOException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>Renderer for a {@link com.sun.webui.jsf.component.AddRemove} component.</p>
 */

@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.AddRemove"))
public class AddRemoveRenderer extends ListRendererBase {
    
    private final static boolean DEBUG             = false;
    private final static String ITEMS_ID           = "_item_list"; //NOI18N
 
    /**
     * <p>Render the list.
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * end should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    public void encodeEnd(FacesContext context, UIComponent component)
    throws IOException {
        
        if(DEBUG) log("encodeEnd()");

	if(component instanceof AddRemove) {
	    renderListComponent((AddRemove)component, context, getStyles(context)); 
        } 
        else {
            String message = "Component " + component.toString() +     //NOI18N
                    " has been associated with a ListboxRenderer. " +  //NOI18N
                    " This renderer can only be used by components " + //NOI18N
                    " that extend com.sun.webui.jsf.component.Selector."; //NOI18N
            throw new FacesException(message);
        }
    }

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
     void renderListComponent(AddRemove component, FacesContext context, 
            String[] styles) throws IOException {
        if(DEBUG) log("renderListComponent()");

	if(component.isReadOnly()) { 
	    UIComponent label = component.getReadOnlyLabelComponent(); 
            super.renderReadOnlyList(component, label, context, styles[23]); 
	    return; 
	} 

        Theme theme = ThemeUtilities.getTheme(context);
        ResponseWriter writer = context.getResponseWriter();

	renderOpenEncloser(component, context, HTMLElements.DIV, styles[23]);

	if(component.isVertical()) { 
	    renderVerticalAddRemove(component, context, writer, styles); 
	} else { 
	    renderHorizontalAddRemove(component, context, writer, styles); 
	} 

	UIComponent footerComponent = 
	    component.getFacet(AddRemove.FOOTER_FACET); 
	if(footerComponent != null) {
            writer.startElement(HTMLElements.DIV, component);
            writer.writeText("\n", null); //NOI18N
            RenderingUtilities.renderComponent(footerComponent, context);
            writer.writeText("\n", null); //NOI18N
            writer.endElement(HTMLElements.DIV);
            writer.writeText("\n", null); //NOI18N
        }

        String id = component.getClientId(context);
	RenderingUtilities.renderHiddenField(component, writer, 
            id.concat(ITEMS_ID), component.getAllValues());
        writer.writeText("\n", null); //NOI18N

	// Value field
	renderHiddenValue(component, context, writer, styles[19]);

        writer.writeText("\n", null); //NOI18N
	renderDivEnd(writer); 

        try {
            // Append properties.
            StringBuffer buff = new StringBuffer(256);
            JSONObject json = new JSONObject();
            json.put("id", id)
                .put("separator", component.getSeparator())
                .put("sort", component.isSorted())
                .put("duplicateSelections", component.isDuplicateSelections())
                .put("selectAll", component.isSelectAll())
                .put("moveButtons", component.isMoveButtons());
            String jsObject = JavaScriptUtilities.getDomNode(context, component);

            // Append JavaScript.
            buff.append(JavaScriptUtilities.getModule("_html.addRemove"))
                .append("\n") // NOI18N
                .append(JavaScriptUtilities.getModuleName("_html.addRemove._init")) // NOI18N
                .append("(") //NOI18N
                .append(JSONUtilities.getString(json))
                .append(");"); //NOI18N

            // Render JavaScript.
            JavaScriptUtilities.renderJavaScript(component, writer,
                buff.toString(), JavaScriptUtilities.isParseOnLoad());
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    private void renderHorizontalAddRemove(AddRemove component, 
					   FacesContext context,  
					   ResponseWriter writer, 
					   String[] styles) 
	throws IOException { 

        // Put the columns into a table so that they can be 
	// displayed horizontally
        // in a table cell as in portlet. See bug 6299233

	writer.startElement(HTMLElements.TABLE, component);
        writer.writeText("\n", null); //NOI18N
        writer.startElement(HTMLElements.TR, component);
        writer.writeText("\n", null); //NOI18N

	// If the component has a label, render it first... 
	UIComponent headerComponent = component.getHeaderComponent(); 
        if(headerComponent != null) {
	    writer.startElement(HTMLElements.TD, component);
	    if(!component.isLabelOnTop()) {
                writer.writeAttribute(HTMLAttributes.VALIGN, "top", null); //NOI18N
	    } else {
                writer.writeAttribute(HTMLAttributes.COLSPAN, "3", null); //NOI18N
            }
            writer.writeText("\n", null); //NOI18N	
            renderDivBegin(component, writer, styles[21]);             
            RenderingUtilities.renderComponent(headerComponent, context);
	    renderDivEnd(writer); 
            renderTableCellEnd(writer); 
                
            if(component.isLabelOnTop()) {
                writer.endElement(HTMLElements.TR);
                writer.writeText("\n", null); //NOI18N              
        	writer.startElement(HTMLElements.TR, component);
	        writer.writeText("\n", null); //NOI18N
	    }
        }

	// Available items column
	renderTableCellStart(component, writer);
	renderDivBegin(component, writer, styles[17]); 

	RenderingUtilities.renderComponent
	    (component.getAvailableLabelComponent(), context); 
	renderAvailableList(component, context, styles); 

	renderDivEnd(writer); 
	renderTableCellEnd(writer);

	// Buttons column
	renderTableCellStart(component, writer);
	renderDivBegin(component, writer, styles[17]); 

	renderButtons(component, context, writer, styles); 

	renderDivEnd(writer); 
	renderTableCellEnd(writer);

	// Selected list column 
	renderTableCellStart(component, writer);
        renderDivBegin(component, writer, styles[17]); 

	RenderingUtilities.renderComponent
	    (component.getSelectedLabelComponent(), context); 
	renderSelectedList(component, context, styles); 

	renderDivEnd(writer); 
	renderTableCellEnd(writer);
        
        // Close the table 
        writer.endElement(HTMLElements.TR);
        writer.writeText("\n", null); //NOI18N
        writer.endElement(HTMLElements.TABLE);
        writer.writeText("\n", null); //NOI18N

        writer.startElement(HTMLElements.DIV, component);
        writer.writeAttribute(HTMLAttributes.CLASS, styles[18], null);
        writer.endElement(HTMLElements.DIV);
    } 

    private void renderTableCellStart(AddRemove component,
	    ResponseWriter writer) throws IOException{
        writer.startElement(HTMLElements.TD, component);
        writer.writeAttribute(HTMLAttributes.VALIGN, "top", null);  //NOI18N
        writer.writeText("\n", null); //NOI18N
    }
    
    private void renderTableCellEnd(ResponseWriter writer) throws IOException{
        writer.endElement(HTMLElements.TD);
        writer.writeText("\n", null); //NOI18N
    }


    private void renderVerticalAddRemove(AddRemove component, 
					 FacesContext context,  
					 ResponseWriter writer, 
					 String[] styles) 
	throws IOException { 

	// Render the header and list for available items in a table
        writer.startElement(HTMLElements.TABLE, component);
        writer.writeText("\n", null); //NOI18N
        writer.startElement(HTMLElements.TR, component);
        writer.writeText("\n", null); //NOI18N
        // If the component has a label, render it first... 
	UIComponent headerComponent = component.getHeaderComponent(); 
        if(headerComponent != null) {
	    writer.startElement(HTMLElements.TD, component);
	    
            writer.writeText("\n", null); //NOI18N	
            renderDivBegin(component, writer, styles[21]);             
            RenderingUtilities.renderComponent(headerComponent, context);
	    renderDivEnd(writer); 
            renderTableCellEnd(writer); 
  
            writer.endElement(HTMLElements.TR);
            writer.writeText("\n", null); //NOI18N              
            writer.startElement(HTMLElements.TR, component);
            writer.writeText("\n", null); //NOI18N
	   
        }
        writer.startElement(HTMLElements.TD, component);
        writer.writeText("\n", null); //NOI18N
        renderDivBegin(component, writer, styles[21]);             
	RenderingUtilities.renderComponent
	    (component.getAvailableLabelComponent(), context); 
	renderAvailableList(component, context, styles); 
	renderDivEnd(writer); 
        renderTableCellEnd(writer); 
        writer.endElement(HTMLElements.TR);
        writer.writeText("\n", null); //NOI18N
        writer.endElement(HTMLElements.TABLE);
        writer.writeText("\n", null); //NOI18N

	// Render add/remove buttons
        renderDivBegin(component, writer, styles[21]);             
	renderAddButtonRow(component, context, writer, styles); 
	renderDivEnd(writer); 

	// open a div
        renderDivBegin(component, writer, styles[22]);             
	
	// render the header and list for selected items in a table
        writer.startElement(HTMLElements.TABLE, component);
        writer.writeText("\n", null); //NOI18N
        writer.startElement(HTMLElements.TR, component);
        writer.writeText("\n", null); //NOI18N
        writer.startElement(HTMLElements.TD, component);
        writer.writeText("\n", null); //NOI18N
        renderDivBegin(component, writer, styles[21]);             
	RenderingUtilities.renderComponent
	    (component.getSelectedLabelComponent(), context); 
	renderSelectedList(component, context, styles); 
	renderDivEnd(writer); 
        renderTableCellEnd(writer); 
        writer.endElement(HTMLElements.TR);
        writer.writeText("\n", null); //NOI18N
        writer.endElement(HTMLElements.TABLE);
        writer.writeText("\n", null); //NOI18N

	// render move up/down buttons
	if(component.isMoveButtons()) { 
            renderDivBegin(component, writer, styles[21]);             
	    renderMoveButtonRow(component, context, writer, styles); 
	    renderDivEnd(writer); 
	} 

	// Don't want elements below here affected by floated elements
	// in the component, so cancel the float property.
        renderDivBegin(component, writer, styles[22]);             
	renderDivEnd(writer); 
	
	// close the div
	writer.writeText("\n", null); //NOI18N
    } 

    private void renderDivBegin(AddRemove addRemove, ResponseWriter writer, String style) 
	throws IOException { 

	writer.startElement(HTMLElements.DIV, addRemove);
	writer.writeAttribute(HTMLAttributes.CLASS, style, null);
	writer.writeText("\n", null); //NOI18N
    } 

    private void renderDivEnd(ResponseWriter writer) 
	throws IOException { 
        writer.writeText("\n", null); //NOI18N
	writer.endElement(HTMLElements.DIV);
	writer.writeText("\n", null); //NOI18N
    } 

    private void renderButtons(AddRemove component, FacesContext context, 
			       ResponseWriter writer, String[] styles) 
    throws IOException { 

	writer.startElement(HTMLElements.TABLE, component);
	writer.writeAttribute(HTMLAttributes.CLASS, styles[10], null);
        writer.writeText("\n", null); //NOI18N 
	writer.startElement(HTMLElements.TR, component);
        writer.writeText("\n", null); //NOI18N
	writer.startElement(HTMLElements.TD, component);
	writer.writeAttribute(HTMLAttributes.ALIGN, "center", null);  //NOI18N
	writer.writeAttribute(HTMLAttributes.WIDTH, "125px", null);  //NOI18N
        writer.writeText("\n", null); //NOI18N
	
	// Render the add button
	renderButton(component, component.getAddButtonComponent(), 
			 styles[14], writer, context); 
        writer.writeText("\n", null); //NOI18N

	if(component.isSelectAll()) { 
	    renderButton(component, component.getAddAllButtonComponent(), 
			 styles[14], writer, context); 
	} 
        String buttonStyle = null; 
        if(component.isSelectAll()) { 
            buttonStyle = styles[15];
        } 
        else { 
            buttonStyle = styles[14]; 
        } 
	renderButton(component, component.getRemoveButtonComponent(), buttonStyle, 
		     writer, context); 
	if(component.isSelectAll()) { 
	    renderButton(component, component.getRemoveAllButtonComponent(), 
			 styles[14], writer, context); 
	} 

	if(component.isMoveButtons()) { 
	    renderButton(component, component.getMoveUpButtonComponent(), 
			 styles[15], writer, context); 
	    renderButton(component, component.getMoveDownButtonComponent(), 
			 styles[14], writer, context); 
	}

	writer.endElement(HTMLElements.TD);
        writer.writeText("\n", null); //NOI18N
	writer.endElement(HTMLElements.TR);
        writer.writeText("\n", null); //NOI18N
	writer.endElement(HTMLElements.TABLE);
    } 

    private void renderAddButtonRow(AddRemove component, 
				    FacesContext context, 
				    ResponseWriter writer, 
				    String[] styles) 
    throws IOException { 

	renderButtonVertical(component, component.getAddButtonComponent(context), 
			     styles[11], writer, context); 

	if(component.isSelectAll()) { 
	    renderButtonVertical(component, component.getAddAllButtonComponent(), 
				 styles[12], writer, context); 
	} 
	renderButtonVertical(component, component.getRemoveButtonComponent(), 
			     styles[13], writer, context); 

	if(component.isSelectAll()) { 
	    renderButtonVertical(component, component.getRemoveAllButtonComponent(), 
				 styles[12], writer, context); 
	} 
    } 

    private void renderMoveButtonRow(AddRemove component, 
				    FacesContext context, 
				    ResponseWriter writer, 
				    String[] styles) 
    throws IOException { 

	renderButtonVertical(component, component.getMoveUpButtonComponent(), 
		     styles[11], writer, context); 
	renderButtonVertical(component, component.getMoveDownButtonComponent(), 
		     styles[12], writer, context); 
    } 

    private void renderButton(AddRemove addRemove, 
                              UIComponent comp, 
                              String style, 
			      ResponseWriter writer, 
                              FacesContext context) 
        throws IOException { 
        
	if(comp == null) return; 

        renderDivBegin(addRemove, writer, style);             
	RenderingUtilities.renderComponent(comp, context);
	writer.writeText("\n", null); //NOI18N
	renderDivEnd(writer); 
    }

    private void renderButtonVertical(AddRemove addRemove, 
                                      UIComponent comp, 
                                      String style, 
				      ResponseWriter writer, 
				      FacesContext context) 
        throws IOException { 
        
	if(comp == null) return; 

        renderDivBegin(addRemove, writer, style);             
	RenderingUtilities.renderComponent(comp, context);
	writer.writeText("\n", null); //NOI18N
        renderDivEnd(writer); 
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
    protected void renderAvailableList(AddRemove component, FacesContext context,
            String[] styles)
            throws IOException {
        
        String id = null;
        if (component instanceof ComplexComponent) {
            id = component.getLabeledElementId(context);
        } else {
            id = component.getClientId(context);
        }
                
        // Set the style class
        String styleClass = styles[1];
        if(component.isDisabled()) styleClass = styles[2];
        
        ResponseWriter writer = context.getResponseWriter();
        
        // This stuff is from the RI...
        //Util.doAssert(writer != null);
        
        // This stuff is from the RI... Not sure what it is supposed to
        // accomplish?
        // String redisplay = "" + attributeMap.get("redisplay");
        
        // (redisplay == null || !redisplay.equals("true")) {
        //currentValue = "";
        //}
        
              
        writer.startElement(HTMLElements.SELECT, component);
        writer.writeAttribute(HTMLAttributes.CLASS, styleClass, null);
        writer.writeAttribute(HTMLAttributes.ID, id, null);
	writer.writeAttribute(HTMLAttributes.NAME, id, null);

        String jsObject = JavaScriptUtilities.getDomNode(context, component);

        StringBuffer jsBuffer = new StringBuffer(256);
        jsBuffer.append(jsObject)
                .append(AddRemove.ADD_FUNCTION)
                .append(AddRemove.RETURN);
        writer.writeAttribute(HTMLAttributes.ONDBLCLICK, jsBuffer.toString(),
		null);

	jsBuffer = new StringBuffer(200);
        jsBuffer.append(jsObject)
                .append(AddRemove.AVAILABLE_ONCHANGE_FUNCTION)
                .append(AddRemove.RETURN);
        writer.writeAttribute(HTMLAttributes.ONCHANGE, jsBuffer.toString(),
		null);

	int size = component.getRows(); 
	writer.writeAttribute(HTMLAttributes.SIZE, String.valueOf(size),
		null); //NOI18N
	writer.writeAttribute(HTMLAttributes.MULTIPLE, "multiple",
		null); //NOI18N

        if(component.isDisabled()) {
            writer.writeAttribute(HTMLAttributes.DISABLED,
				  "disabled",  //NOI18N
				  "disabled"); //NOI18N
        }

	String tooltip = component.getToolTip(); 
	if(tooltip != null) { 
	    writer.writeAttribute(HTMLAttributes.TITLE, tooltip, null);
	} 
        
        if(DEBUG) log("Setting onchange event handler"); 
        //writer.writeAttribute("onchange", script, null);    //NOI18N
        
        int tabindex = component.getTabIndex(); 
        if(tabindex > 0 && tabindex < 32767) { 
            writer.writeAttribute(HTMLAttributes.TABINDEX,
				  String.valueOf(tabindex), 
				  "tabindex");              //NOI18N
        }

	RenderingUtilities.writeStringAttributes(component, writer, 
                                                 STRING_ATTRIBUTES); 

        
        writer.writeText("\n", null); //NOI18N
        
        renderListOptions(component, context,
		component.getListItems(context, true), 
                          writer, styles);
        
        writer.endElement(HTMLElements.SELECT);  //NOI18N
        writer.writeText("\n", null); //NOI18N
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
    protected void renderSelectedList(AddRemove component, 
				      FacesContext context, 
				      String[] styles)
	throws IOException {

        String id = 
	    component.getClientId(context).concat(AddRemove.SELECTED_ID);
                
        // Set the style class
        String styleClass = styles[1];
        if(component.isDisabled()) {
	    styleClass = styles[2];
	}

        ResponseWriter writer = context.getResponseWriter();
        
        // This stuff is from the RI...
        //Util.doAssert(writer != null);
        
        // This stuff is from the RI... Not sure what it is supposed to
        // accomplish?
        // String redisplay = "" + attributeMap.get("redisplay");
        
        // (redisplay == null || !redisplay.equals("true")) {
        //currentValue = "";
        //}
        
        
        writer.startElement(HTMLElements.SELECT, component);
        writer.writeAttribute(HTMLAttributes.CLASS, styleClass, null);
        writer.writeAttribute(HTMLAttributes.ID, id, null);
	writer.writeAttribute(HTMLAttributes.SIZE,
			      String.valueOf(component.getRows()), null); 
	writer.writeAttribute(HTMLAttributes.MULTIPLE, "multiple", //NOI18N
		null);

        String jsObject = JavaScriptUtilities.getDomNode(context, component);

        StringBuffer jsBuffer = new StringBuffer(256);
        jsBuffer.append(jsObject);
        jsBuffer.append(AddRemove.REMOVE_FUNCTION);
        jsBuffer.append(AddRemove.RETURN);
        writer.writeAttribute(HTMLAttributes.ONDBLCLICK, jsBuffer.toString(),
		null);

	jsBuffer = new StringBuffer(200);
        jsBuffer.append(jsObject);
        jsBuffer.append(AddRemove.SELECTED_ONCHANGE_FUNCTION);
        jsBuffer.append(AddRemove.RETURN);
        writer.writeAttribute(HTMLAttributes.ONCHANGE, jsBuffer.toString(),
		null);

	if(component.isDisabled()) {
            writer.writeAttribute(HTMLAttributes.DISABLED,
				  "disabled",  //NOI18N
				  "disabled"); //NOI18N
        }

	// TODO
	/*
	String tooltip = component.getToolTip(); 
	if(tooltip != null) { 
	    writer.writeAttribute("title", tooltip, null); //NOI18N
	} 
        */ 

        if(DEBUG) log("Setting onchange event handler"); 

        int tabindex = component.getTabIndex(); 
        if(tabindex > 0 && tabindex < 32767) { 
            writer.writeAttribute(HTMLAttributes.TABINDEX,
				  String.valueOf(tabindex), 
				  "tabindex");              //NOI18N
        }

	RenderingUtilities.writeStringAttributes(component, writer, 
						 STRING_ATTRIBUTES); 
        writer.writeText("\n", null); //NOI18N
        renderListOptions(component, context, component.getSelectedListItems(), 
			  writer, styles);
        writer.endElement(HTMLElements.SELECT);
        writer.writeText("\n", null); //NOI18N
    }

    /**
     * Overrides encodeChildren of Renderer to do nothing. This
     * renderer renders its own children, but not through this
     * method.
     * @param context The FacesContext of the request
     * @param component The component associated with the
     * renderer. Must be a subclass of ListSelector.
     * @throws java.io.IOException if something goes wrong while writing the children
     */
    public void encodeChildren(javax.faces.context.FacesContext context,
                           javax.faces.component.UIComponent component)
	throws java.io.IOException { 
	return;
    } 

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
    private String[] getStyles(FacesContext context) { 
        
        if(DEBUG) log("getStyles()"); 
        
        Theme theme = ThemeUtilities.getTheme(context); 
        
        String[] styles = new String[24]; 
        styles[0] = JavaScriptUtilities.getModuleName("_html.listbox.changed"); //NOI18N
	styles[1] = theme.getStyleClass(ThemeStyles.LIST);
	styles[2] = theme.getStyleClass(ThemeStyles.LIST_DISABLED);
        styles[3] = theme.getStyleClass(ThemeStyles.LIST_OPTION);
        styles[4] = theme.getStyleClass(ThemeStyles.LIST_OPTION_DISABLED);
        styles[5] = theme.getStyleClass(ThemeStyles.LIST_OPTION_SELECTED);
        styles[6] = theme.getStyleClass(ThemeStyles.LIST_OPTION_GROUP);
        styles[7] = theme.getStyleClass(ThemeStyles.LIST_OPTION_SEPARATOR);
        styles[8] = theme.getStyleClass(ThemeStyles.ADDREMOVE_LABEL);
	styles[9] = theme.getStyleClass(ThemeStyles.ADDREMOVE_LABEL2);
	styles[10] = theme.getStyleClass(ThemeStyles.ADDREMOVE_BUTTON_TABLE);
        styles[11] = theme.getStyleClass(ThemeStyles.ADDREMOVE_VERTICAL_FIRST);
        styles[12] = theme.getStyleClass(ThemeStyles.ADDREMOVE_VERTICAL_WITHIN);
        styles[13] = theme.getStyleClass(ThemeStyles.ADDREMOVE_VERTICAL_BETWEEN);
        styles[14] = theme.getStyleClass(ThemeStyles.ADDREMOVE_HORIZONTAL_WITHIN);
	styles[15] = theme.getStyleClass(ThemeStyles.ADDREMOVE_HORIZONTAL_BETWEEN);
	styles[16] = null;
	styles[17] = theme.getStyleClass(ThemeStyles.ADDREMOVE_HORIZONTAL_ALIGN);
        styles[18] = theme.getStyleClass(ThemeStyles.ADDREMOVE_HORIZONTAL_LAST);
        styles[19] = theme.getStyleClass(ThemeStyles.HIDDEN);
        styles[20] = theme.getStyleClass(ThemeStyles.ADDREMOVE_VERTICAL_BUTTON);
        styles[21] = theme.getStyleClass(ThemeStyles.ADDREMOVE_VERTICAL_DIV);
        styles[22] = theme.getStyleClass(ThemeStyles.ADDREMOVE_VERTICAL_CLEAR);
        styles[23] = theme.getStyleClass(ThemeStyles.ADDREMOVE);
        return styles; 
    } 

    /**
     * Decodes the value of the component
     * @param context The FacesContext of the request
     * @param component The component instance to be decoded
     */
    public void decode(FacesContext context, UIComponent component) {
        
        if(DEBUG) log("decode()");
        String id = component.getClientId(context).concat(ListSelector.VALUE_ID);
        super.decode(context, component, id);        
    }
}

