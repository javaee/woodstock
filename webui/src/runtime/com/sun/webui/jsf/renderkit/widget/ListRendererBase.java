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

package com.sun.webui.jsf.renderkit.widget;

import com.sun.webui.jsf.component.ComplexComponent;
import com.sun.webui.jsf.component.ListManager;
import com.sun.webui.jsf.component.ListSelector;
import com.sun.webui.jsf.model.OptionTitle;
import com.sun.webui.jsf.model.Separator;
import com.sun.webui.jsf.model.list.EndGroup;
import com.sun.webui.jsf.model.list.ListItem;
import com.sun.webui.jsf.model.list.StartGroup;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.WidgetUtilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The ListRendererBase is the base class for the listbox renderers
 * (Drop-down Menu and Selectable List). These are both rendered using
 * the same HTML tag (select) so a lot of the renderering functionality
 * is shared.
 *
 * @author dongmeic
 */
abstract public class ListRendererBase extends RendererBase {
    /**
     * The set of pass-through attributes to be rendered.
     */
    protected static final String[] attributes = {
        "onBlur", 
        "onChange",
        "onClick",
        "onDblClick", 
        "onFocus", 
        "onMouseDown",
        "onMouseUp",
        "onMouseOver",
        "onMouseMove",
        "onMouseOut",
        "onKeyPress",
        "onKeyDown",
        "onKeyUp",
        "onSelect", 
        "style", 
        "dir",
        "lang", 
        "accessKey"
    };

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Retrieve user input from the UI.
     * @param context The FacesContext of this request
     * @param component The component associated with the renderer
     */
    public void decode(FacesContext context, UIComponent component) {

        if (((ListManager)component).isReadOnly()) {
            return;
        }
        
        String id = component.getClientId(context); 
        if (component instanceof ComplexComponent) {
            id = ((ComplexComponent)component).getLabeledElementId(context);
        }
        
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
    protected void decode(FacesContext context, UIComponent component, String id) {
        
        ListManager lmComponent = (ListManager)component;
        
        if (lmComponent.isReadOnly()) {
            return;
        }
        
        Map params = context.getExternalContext().getRequestParameterValuesMap();
        
        String[] values = null; 
        Object p = params.get(id); 
        if(p == null) { 
            values = new String[0];
        }
        else {
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

        //CR 6455533/6447372
        //if component is disabled, don't set empty array as submitted value
        //so only set if values.length > 0, or if it's == 0 and the component
        //is not disabled
        if (values.length > 0 || !lmComponent.isDisabled()) {
            lmComponent.setSubmittedValue(values);
        }
      
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Obtain the properties of the List component
     *
     * @param json The JSONOjbect the properties will be added to
     * @param component The List component
     * @param context The faces context
     */
    protected JSONObject getProperties(FacesContext context, 
        ListSelector component)throws JSONException, IOException {
        
     
        JSONObject json = new JSONObject();
        
        // Append label properties. null label will be checked in the WidgetUtilities.renderComponent()
        WidgetUtilities.addProperties( json, "label",
            WidgetUtilities.renderComponent(context, ((ListSelector)component).getLabelComponent()) );
        
        // StyleClass, labelOnTop
        json.put( "className", component.getStyleClass() );
        json.put( "labelOnTop", component.isLabelOnTop() );
        
        // This needs to be called for supporting DB NULL values.
        recordRenderedValue(component);
        
        // Get properties for the select HTML element
        getListProperties( json, component, context );
        
        return json;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Property methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Obtains the properties for the select element
     *
     * @param json The JSONObject for adding the properties to
     * @param listManager The List component
     * @param context The faces context
     */
    private void getListProperties( JSONObject json, ListManager listManager, 
            FacesContext context)  throws JSONException {

        json.put( "disabled", listManager.isDisabled() );
        json.put( "size", listManager.getRows() );
        json.put( "multiple", listManager.isMultiple() );
        json.put( "tabIndex", listManager.getTabIndex() );
        json.put( "title", listManager.getToolTip() );
       
        // Get the properties for all the option elements
        getListOptionsProperties(json,(UIComponent)listManager, listManager.getListItems(context, true));
    }
    
    /**
     * Obtains the properties for the option elements
     *
     * @param json The JSONObject for adding the properties
     * @param component The List component
     * @param optionsIterator The iterator for looping through the options
     */
    private void getListOptionsProperties( JSONObject json, UIComponent component, 
            Iterator optionsIterator)  throws JSONException {
        
        // Store the options in a JSONArray. The elements in the array are the JSONObject for 
        // each option. Each option object contains the following properties;
        // - separator: true/false. To indicator if this is a separator
        // - diabled: to indicate whether the option is disabled or not. 
        // - selected: to indicate whether this opton is selected or not
        // - label: the label of this option
        // - isTitle: to indicate whether this is a title option, such as "Please select one of the following:"
        // - value: the value of this option
        // - group: to indicate this is an optgroup
        // - options: the options contained in the optgroup
        
        // Store the options Array for this List component
        JSONArray optionsJsonArray = new JSONArray();

        // Specific for the optgroup
        JSONObject groupOptionJson = null;
        // Options for the current optgroup
        JSONArray groupOptionsJsonArray = null;
        
        // Lets build up the option array
        Object option = null;
        boolean noSeparator = true;
        while(optionsIterator.hasNext() ) {
            
            option = optionsIterator.next();
            
            if (option instanceof Separator) {
                
                // Create a JSONObject for the separator
                JSONObject separatorJson = new JSONObject();
                getSeparatorProperties( separatorJson, component );
                
                if( groupOptionsJsonArray != null )
                    // That means this is for the optgroup we're working on
                    groupOptionsJsonArray.put( separatorJson );
                else
                    // This is just an option for the list component
                    optionsJsonArray.put( separatorJson );
            }
            else if (option instanceof StartGroup ) {
                
                StartGroup group = (StartGroup)option;
                
                if(!noSeparator) {
                    JSONObject separatorJson = new JSONObject();
                    getSeparatorProperties(separatorJson, component);
                    optionsJsonArray.put( separatorJson );
                }
                
                groupOptionJson = new JSONObject();
                groupOptionJson.put( "group", true );
                groupOptionJson.put( "label", group.getLabel() );
                
                // Add this option group to the option array for the list component
                optionsJsonArray.put( groupOptionJson );
                
                // And allocate space for the options for this group
                groupOptionsJsonArray = new JSONArray();
                
                noSeparator = true;
            } 
            else if(option instanceof EndGroup ) {
                
                // Done with this group.
                groupOptionJson.put( "options", groupOptionsJsonArray );
                groupOptionsJsonArray = null;
                
                if(optionsIterator.hasNext()) {
                    JSONObject separatorJson = new JSONObject();
                    getSeparatorProperties(separatorJson, component);
                    optionsJsonArray.put( separatorJson );
                }
                
                noSeparator = true;
            } else { 
                
                // A regular option element
                JSONObject optionJson = new JSONObject();
                getListOptionProperties(optionJson, (ListItem)option);
                
                if( groupOptionsJsonArray != null )
                    // This means this option belongs to the optgroup we're working on
                    groupOptionsJsonArray.put( optionJson );
                else
                    optionsJsonArray.put( optionJson );
                
                noSeparator = false;
            }
            
        }
        
        // Add all the options to the json object
        json.put( "options", optionsJsonArray );
        
        // DONE!
    }
    
    /**
     * Adds the properties for the separator to the passed-in json object
     *
     * @param json The JSONObject for adding the properties
     * @param component The List component
     */
    private void getSeparatorProperties(JSONObject json, UIComponent component) throws JSONException {
        
        if(!(component instanceof ListSelector)) {
            return;
        }
        
        ListSelector selector = (ListSelector)component;
        if(!selector.isSeparators()) {
            return;
        }
        
        // Indicates this is a separator
        json.put( "separator", true );
        json.put( "group", false );
        json.put( "disabled", true ); // Always disabled for separator
        
        // The label for the separator. It is a series of dashes (-----------)
        int numEms = selector.getSeparatorLength();
        StringBuffer labelBuffer = new StringBuffer();
        for(int em = 0; em < numEms; ++em) {
            labelBuffer.append("-");                        
        }
        json.put( "label", labelBuffer.toString() );
    }
    
    /**
     * Adds the properties for the option to the passed-in json object
     *
     * @param json The JSONObject for adding the properties
     * @param listItem The list item for the option
     */
    private void getListOptionProperties(JSONObject json, ListItem listItem) throws JSONException {
        
        json.put( "group", false ); // Not a group
        json.put( "separator", false );  // Not a separator
        json.put( "disabled", listItem.isDisabled() );
        json.put( "selected", listItem.isSelected() );
        json.put( "value", listItem.getValue() );
        json.put( "isTitle", listItem.isTitle() );  // A title option
        json.put( "label", listItem.getLabel() );
        
        return;
    }
    
    /**
     * This must be called where the value is about to be rendered
     * for DB Null value support
     */
    private void recordRenderedValue(UIComponent component){
	
	if (component instanceof EditableValueHolder &&
		((EditableValueHolder)component).getSubmittedValue() == null) {
	    ConversionUtilities.setRenderedValue(component, 
		    ((EditableValueHolder)component).getValue());
	}
    }
}
