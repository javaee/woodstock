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

package com.sun.webui.jsf.renderkit.widget;

import com.sun.webui.jsf.component.ComplexComponent;
import com.sun.webui.jsf.component.ListManager;
import com.sun.webui.jsf.component.ListSelector;
import com.sun.webui.jsf.model.OptionTitle;
import com.sun.webui.jsf.model.Separator;
import com.sun.webui.jsf.model.list.EndGroup;
import com.sun.webui.jsf.model.list.ListItem;
import com.sun.webui.jsf.model.list.StartGroup;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.theme.Theme;

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
    protected static final String[] stringAttributes = {
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
    protected void decode(FacesContext context, UIComponent component, 
	    String id) {
        
        ListManager lmComponent = (ListManager)component;
        if (lmComponent.isReadOnly()) {
            return;
        }
        
        Map params = context.getExternalContext().
	    getRequestParameterValuesMap();
        
        String[] values = null; 
        Object p = params.get(id); 
        if (p == null) { 
            values = new String[0];
        } else {
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
            ArrayList<String> newParams = new ArrayList<String>();
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
        
        // Append label properties.
	//
        UIComponent label = component.getFacet(ListSelector.LABEL_FACET);
	if (label != null) {
	    json.put("label", WidgetUtilities.renderComponent(context, label));
	} else {
	    String labelstring = component.getLabel();
	    // We allow "" labels.
	    //
	    if (labelstring != null) {
		JSONObject labelobj = new JSONObject();
		labelobj.put("value", labelstring);
		// We always get a default.
		//
		labelobj.put("level", component.getLabelLevel());
		json.put("label", labelobj);
	    }
	}
        
        // StyleClass, labelOnTop
        json.put("className", component.getStyleClass());

	// Must always render labelOnTop since it is a theme'd property.
	// 
        boolean labelOnTop = component.isLabelOnTop();
	json.put("labelOnTop", labelOnTop);
        
        // This needs to be called for supporting DB NULL values.
        recordRenderedValue(component);
        
        // Get properties for the select HTML element
        getListProperties(json, component, context);
        
        return json;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Obtains the properties for the select element
     *
     * @param json The JSONObject for adding the properties to
     * @param listManager The List component
     * @param context The faces context
     */
    private void getListProperties(JSONObject json, ListManager listManager, 
            FacesContext context)  throws JSONException {

	boolean boolprop = listManager.isDisabled();
	if (boolprop) {
	    json.put("disabled", boolprop);
	}

	boolprop = listManager.isRequired();
	json.put("required", boolprop);

	boolprop = listManager.isValid();
	json.put("valid", boolprop);

        json.put("size", listManager.getRows());

	String width = listManager.getWidth(); 
	if (width != null && width.trim().length() != 0) { 
	    json.put("width", width.trim()); 
	}

	json.put("multiple", listManager.isMultiple());
	
	// http://www.w3.org/TR/html4/interact/forms.html#adef-tabindex
	//
	int tabindex = listManager.getTabIndex();
	if (tabindex > 0 && tabindex < 32767) {
	    json.put("tabIndex", tabindex);
	}
        json.put("title", listManager.getToolTip());
       
        // Get the properties for all the option elements
	//
        getListOptionsProperties(json,(UIComponent)listManager, 
            listManager.getListItems(context, true));
    }
    
    /**
     * Obtains the properties for the option elements
     * This method assumes only one level of nested options.
     *
     * @param json The JSONObject for adding the properties
     * @param component The List component
     * @param optionsIterator The iterator for looping through the options
     */
    private void getListOptionsProperties(JSONObject json, 
	    UIComponent component, Iterator optionsIterator)
	    throws JSONException {
        
	// Unfortunately we have no alternative but to
	// perform instanceof checks. The iterator returns
	// ListItem, StartGroup and EndGroup instances
	// which do not have a separator property, and
	// instances of Separartor which is also an Option
	// and does have a separator property but it is of
	// no help since we don't know what instance we have.
	// Make no assumptions about what is in the iterator
	// and check for Separator too.
	//

        // Store the options in a JSONArray. The elements in the array are 
	// the JSONObject for each option.
	// Each option object contains the following properties;
        // - separator: true/false. To indicator if this is a separator
        // - diabled: to indicate whether the option is disabled or not. 
        // - selected: to indicate whether this opton is selected or not
        // - label: the label of this option
        // - isTitle: to indicate whether this is a title option, such as 
	//   "Please select one of the following:"
        // - value: the value of this option
        // - group: to indicate this is an optgroup
        // - options: the options contained in an optgroup
        

	// Determine separator status once
	// Get just one separator properties JSON object
	// Use separatorProps != null to indicate that separators
	// are required.
	//
	JSONObject separatorProps = getSeparatorProperties(component);

        // All the options for this List component
	//
        JSONArray allOptionsArray = new JSONArray();
        json.put("options", allOptionsArray);

	// This variable will change between the complete list
	// "allOptionsArray" and an array for a Group's options.
	//
	JSONArray optionsArray = allOptionsArray;

	// Flag, if false, says that EndGroup has added a separator
	//
	// This may not be necessary.
        boolean addStartSeparator = false;
        while (optionsIterator.hasNext()) {
            
            Object option = optionsIterator.next();
            
	    // First look for ListItems since this is the
	    // most common instance in the iterator
	    //
	    if (option instanceof ListItem) {

                JSONObject optionJson = new JSONObject();
                getListItemProperties(optionJson, (ListItem)option);
		optionsArray.put(optionJson);

		// In case we have initial listitems before the
		// start of group
		//
		addStartSeparator = true;

	    } else
            if (option instanceof StartGroup) {

		// If we are addinng separators, add the separator
		// before the start of a group.
		//
		if (separatorProps != null && addStartSeparator) {
		    optionsArray.put(separatorProps);
		}
                
                StartGroup group = (StartGroup)option;
                
                JSONObject groupOptionJson = new JSONObject();
                groupOptionJson.put("group", true);
                groupOptionJson.put("label", group.getLabel());
                
                // Add this option group to the option array for the 
		// list component
		//
                optionsArray.put(groupOptionJson);
                
                // Allocate space for the options for this group
		//
                JSONArray groupOptionsJsonArray = new JSONArray();
                groupOptionJson.put("options", groupOptionsJsonArray);

		// Make sure all future items until EndGroup into the 
		// Group's array
		//
		optionsArray = groupOptionsJsonArray;

            } else if (option instanceof EndGroup) {

		// switch arrays, from the group to all the options.
		//
		optionsArray = allOptionsArray;

		// If this is the last instancce in the iterator
		// don't add a separator after the group
		//
		if (separatorProps != null && optionsIterator.hasNext()) {
		    optionsArray.put(separatorProps);
		    addStartSeparator = false;
                }
	    } else
            if (option instanceof Separator && separatorProps != null) {
		optionsArray.put(separatorProps);
            }
        }
        
    }
    
    /**
     * Return the properties for a separator.
     * <code>component</code> must be a <code>ListSelector</code> 
     * and <code>isSeparators</code> must be true or else
     * null is returned.
     *
     * @param component The List component
     */
    private JSONObject getSeparatorProperties(UIComponent component) 
            throws JSONException {

        if(!(component instanceof ListSelector &&
	    ((ListSelector)component).isSeparators())) {
            return null;
        }
        ListSelector selector = (ListSelector)component;
        
	JSONObject json = new JSONObject();

        // Indicates this is a separator
	//
        json.put("separator", true);

	// Always disabled for separator
	// This could be assumed on the client.
	//
        json.put("disabled", true);
        
        // The label for the separator. It is a series of en dashes
	// in the suntheme, using \u2013 and therefore can be 
	// escaped, i.e. it is not HTML markup.
	//
	// This is an issue, because it is hard to know if "escape"
	// should be true or false. The theme has a corresponding
	// property, "ListSelector.optionSeparatorCharEscape".
	// 
	// If this property is "false" then the string should be
	// not be escaped and is assumed
	// to be HTML markup to be rendered literally.
	// If the key does not exist, for backward compatibility
	// for older themes, assume that it should be is "false",
	// since the old theme used entity references for the 
	// character.
	//
	// If these keys do not exist then \u2013 is used
	// and can be escaped.
	// 
	// However this should really be implemented using CSS
	// to represent a separator "option" or "li" as a horizontal rule
	// or whatever.
	//
	boolean escape = true;
	String separatorString = 
	    getMessage("ListSelector.optionSeparatorChar", null);
	if (separatorString != null) {
	    String escapeSeparator =
		getMessage("ListSelector.optionSeparatorCharEscape", null);
	    if (escapeSeparator != null) {
		escape = Boolean.valueOf(escapeSeparator);
	    }
	} else {
	    separatorString = "\u2014";
	}
	if (!escape) {
	    json.put("escape", false);
	}

	// If this were done using CSS all this would not be necessary.
	//
        int numEns = selector.getSeparatorLength();
        StringBuilder labelBuffer = new StringBuilder();
        for(int en = 0; en < numEns; ++en) {
            labelBuffer.append(separatorString);                        
        }
        json.put("label", labelBuffer.toString());

	return json;
    }
    
    /**
     * Add the properties of <code>listItem</code> to the <code>json</code>.
     * object. They are only added if they are different than
     * the widget's default value.
     *
     * @param json The JSONObject for adding the properties
     * @param listItem The ListItem
     */
    private void getListItemProperties(JSONObject json, ListItem listItem) 
            throws JSONException {

	if (listItem.isDisabled()) {
	    json.put("disabled", listItem.isDisabled());
	}
	if (listItem.isSelected()) {
	    json.put("selected", listItem.isSelected());
	}

	// The value was converted when ListItem was created.
	// Well, actually, only if ListItem was created by 
	// "processOptions" in ListSelector ;(
	//
        json.put("value", listItem.getValue());

	String titleLabel = listItem.getLabel();
	boolean escapeTitle = true;

	// If this option is a title get the theme decoration 
	// pass that to the client.
	//
	if (listItem.isTitle() && titleLabel != null) {

	    // The theme title decoration is leading and trailing
	    // em dash. The suntheme, uses \u2014 and therefore can be 
	    // escaped, i.e. it is not HTML markup.
	    //
	    // This is an issue, because it is hard to know if "escape"
	    // should be true or false. The theme has a corresponding
	    // property, "ListSelector.titleOptionLabelEscape".
	    // 
	    // If this property is "false" then the string should be
	    // not be escaped and is assumed
	    // to be HTML markup to be rendered literally.
	    // If the key does not exist, for backward compatibility
	    // for older themes, assume that it should be is "false",
	    // since the old theme used entity references for the 
	    // character. However if this ListItem has a contradictory
	    // escape value, that is always used.
	    //
	    // If these keys do not exist the label text is just
	    // rendered as is and uses the "escape" property of the
	    // ListItem.
	    //
	    String tmp = getMessage("ListSelector.titleOptionLabel", 
		new Object[] {titleLabel});
	    if (tmp != null) {
	        titleLabel = tmp;
	        // See if this text needs to be escaped.
	        //
	        escapeTitle = getTheme().getMessageBoolean(
		    "ListSelector.titleOptionLabelEscape", true);
	    }
	}
	// No need to render "escape == true" since this is the
	// widget assumed default.
	//
	if (!escapeTitle || !listItem.isEscape()) {
	    json.put("escape", false);
	}
	if (titleLabel != null) {
	    json.put("label", titleLabel);
	}
    }
    
    /**
     * This must be called where the value is about to be rendered
     * for DB Null value support
     */
    private void recordRenderedValue(UIComponent component) {
	if (component instanceof EditableValueHolder 
	    && ((EditableValueHolder)component).getSubmittedValue() == null) {
	    ConversionUtilities.setRenderedValue(component, 
		((EditableValueHolder)component).getValue());
	}
    }

    /**
     * Return a message key value or null and log a message if the 
     * property couldn't be obtained or the value is "".
     */
    private String getMessage(String key, Object[] params) {
	String msg = null;
	try {
	    if (params != null) {
		msg = getTheme().getMessage(key, params);
	    } else {
	        msg = getTheme().getMessage(key);
	    }
	} catch (Exception e) {
	    if (LogUtil.finestEnabled()) {
		LogUtil.finest(
		    "ListRendererBase.getMessage: " +
		    "Can't get message key" + key, e);
	    }
	}
	return msg != null && msg.length() != 0 ? msg : null;
    }
}
