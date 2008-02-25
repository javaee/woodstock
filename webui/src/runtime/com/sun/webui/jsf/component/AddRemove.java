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
package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.OptionGroup;
import com.sun.webui.jsf.model.Separator;
import com.sun.webui.jsf.model.list.ListItem;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.MessageUtil;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.theme.Theme;

import java.text.Collator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * The AddRemove component is used to construct a list of selected items.
 * <p>
 * Use the AddRemove component when the web application user makes selections 
 * from a list and they need to see the currently selected items displayed
 * together, and/or they need to reorder the selected items.
 * </p>
 */
@Component(
        type="com.sun.webui.jsf.AddRemove",
        family="com.sun.webui.jsf.AddRemove",
        displayName="Add Remove List",
        instanceName="addRemoveList",
        tagName="addRemove",
        helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_add_remove_list",
        propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_addremovelist_props")
public class AddRemove extends ListSelector implements ListManager {
    /**
     * The component id for the ADD button
     */
    public static final String ADD_BUTTON_ID = "_addButton"; //NOI18N
    /**
     * The facet name of the add button
     */
    public static final String ADD_BUTTON_FACET = "addButton"; //NOI18N

    /**
     * The component id for the ADD ALL button
     */
    public static final String ADDALL_BUTTON_ID = "_addAllButton"; //NOI18N
    /**
     * The facet name of the Add All button
     */
    public static final String ADDALL_BUTTON_FACET = "addAllButton"; //NOI18N

    /**
     * The component ID for the remove button
     */
    public static final String REMOVE_BUTTON_ID = "_removeButton"; //NOI18N
    /**
     * The facet name of the remove button
     */
    public static final String REMOVE_BUTTON_FACET = "removeButton"; //NOI18N

    /**
     * The component ID for the remove all button
     */
    public static final String REMOVEALL_BUTTON_ID = "_removeAllButton"; //NOI18N
    /**
     * The facet name of the "Remove All" button
     */
    public static final String REMOVEALL_BUTTON_FACET = "removeAllButton"; //NOI18N

    /**
     * The component ID for the move up button
     */
    public static final String MOVEUP_BUTTON_ID = "_moveUpButton"; //NOI18N
    /**
     * The facet name of the "Move Up" button
     */
    public static final String MOVEUP_BUTTON_FACET = "moveUpButton"; //NOI18N

    /**
     * The component ID for the move down button
     */
    public static final String MOVEDOWN_BUTTON_ID = "_moveDownButton"; //NOI18N
    /**
     * The facet name of the "Move Down" button
     */
    public static final String MOVEDOWN_BUTTON_FACET = "moveDownButton"; //NOI18N

    /**
     * The component ID for the items list
     */
    public static final String AVAILABLE_LABEL_ID = "_availableLabel"; //NOI18N
    /**
     * The facet name of the label over the "Available" list
     */
    public static final String AVAILABLE_LABEL_FACET = "availableLabel"; //NOI18N
    
    /**
     * The facet name of the label readonly case
     */
    public static final String READ_ONLY_LABEL_FACET = "readonlyLabel"; //NOI18N

    /**
     * The component ID for the selected list
     */
    public static final String SELECTED_LABEL_ID = "_selectedLabel"; //NOI18N
    /**
     * The facet name of the label over the "Selected" list
     */
    public static final String SELECTED_LABEL_FACET = "selectedLabel"; //NOI18N

    /** 
     * Facet name for the header facet
     */ 
    public static final String HEADER_FACET = "header"; //NOI18N
    /**
     * The facet name of the header (component label)
     */
    public static final String HEADER_ID = "_header"; //NOI18N

    /** 
     * Facet name for the footer facet
     */ 
    public static final String FOOTER_FACET = "footer"; //NOI18N

    /**
     * The id of the label component that functions as the label above the available list
     */
    public static final String AVAILABLE_ID = "_available"; //NOI18N
    /**
     * The available label text key.
     */
    public static final String AVAILABLE_TEXT_KEY = "AddRemove.available"; //NOI18N

    /**
     * The ID of the component that functions as the label above the "Selected" list
     */
    public static final String SELECTED_ID = "_selected"; //NOI18N
    /**
     * The selected label text key.
     */
    public static final String SELECTED_TEXT_KEY = "AddRemove.selected"; //NOI18N
    
    /**
     * The ID of the component readonly case
     */
    public static final String READONLY_ID = "_readonly"; //NOI18N

    /**
     * String representing "return false" printed at the end of the javascript event handlers
     */
    public static final String RETURN = "return false;";  //NOI18N
    /**
     * Name of the JavaScript function which is responsible for adding elements from the availble list to the selected list
     */
    public static final String ADD_FUNCTION = ".add(); "; //NOI18N
    /**
     * Add button text key.
     */
    public static final String ADD_TEXT_KEY = "AddRemove.add"; //NOI8N
    /**
     * Add button text key, vertical layout.
     */
    public static final String ADDVERTICAL_TEXT_KEY =
	"AddRemove.addVertical"; //NOI8N
    /**
     * Name of the JavaScript function which is responsible for selecting
     * all the available items
     */
    public static final String ADDALL_FUNCTION = ".addAll();"; //NOI18N
    /**
     * Add all button text key.
     */
    public static final String ADDALL_TEXT_KEY = "AddRemove.addAll"; //NOI8N
    /**
     * Add all button text key, vertical layout.
     */
    public static final String ADDALLVERTICAL_TEXT_KEY =
	"AddRemove.addAllVertical"; //NOI8N
    /**
     * Name of the JavaScript function which removes items from the seleted list
     */
    public static final String REMOVE_FUNCTION = ".remove(); "; //NOI18N
    /**
     * Remove button text key.
     */
    public static final String REMOVE_TEXT_KEY = "AddRemove.remove"; //NOI8N
    /**
     * Remove button text key, vertical layout
     */
    public static final String REMOVEVERTICAL_TEXT_KEY =
	"AddRemove.removeVertical"; //NOI8N
    /**
     * Name of the JavaScript function which removes all the items from the seleted list
     */
    public static final String REMOVEALL_FUNCTION = ".removeAll(); "; //NOI18N
    /**
     * Remove all button text key.
     */
    public static final String REMOVEALL_TEXT_KEY = "AddRemove.removeAll"; //NOI8N
    /**
     * Remove all button text key, vertical layout.
     */
    public static final String REMOVEALLVERTICAL_TEXT_KEY =
	"AddRemove.removeAllVertical"; //NOI8N
    /**
     * Name of the JavaScript function which moves elements up
     */
    public static final String MOVEUP_FUNCTION = ".moveUp(); "; //NOI18N
    /**
     * Move up button text key.
     */
    public static final String MOVEUP_TEXT_KEY = "AddRemove.moveUp"; //NOI8N
    /**
     * Name of the JavaScript function which moves elements down
     */
    public static final String MOVEDOWN_FUNCTION = ".moveDown();"; //NOI18N
    /**
     * Move down button text key.
     */
    public static final String MOVEDOWN_TEXT_KEY = "AddRemove.moveDown"; //NOI8N
    /**
     * Name of the JavaScript function that updates the buttons
     */
    public static final String UPDATEBUTTONS_FUNCTION = ".updateButtons(); "; //NOI8N
    /**
     * Name of the JavaScript function that handles changes on the available list
     */
    public static final String AVAILABLE_ONCHANGE_FUNCTION = 
	".availableOnChange(); "; //NOI8N
    /**
     * Name of the JavaScript function which handles changes to the selected list
     */
    public static final String SELECTED_ONCHANGE_FUNCTION = 
	".selectedOnChange(); "; //NOI8N
    /**
     * The name of the JavaScript function used to hook up the correct
     * add and remove functions when the component allows items to be
     * added to the selected items list more than once
     */
    public static final String MULTIPLEADDITIONS_FUNCTION = 
	".allowMultipleAdditions()";  //NOI8N
    
    public static final String SPACER_STRING = "_"; //NOI18N

    private static final String KEY_STRING = "a"; //NOI18N
    private static final String DUP_STRING = "1"; //NOI18N

    /**
     * The string used as a separator between the selected values
     */
    public static final String SEPARATOR_VALUE = "com.sun.webui.jsf.separator"; //NOI8N

    /**
     * The label level key. It is used to overwrite the Label component's default value.
     */	
    public static final String ADDREMOVE_LABEL_LEVEL =
	"AddRemove.labelLevel"; //NOI18N
    /**
     * The rows. It is used to determine the default number of rows for
     * the available and selected lists.
     */	
    // Note that the new way to define keys is to use the widget name
    // and if possible use the attribute name of the HTML element
    // attribute it is assigned to.
    //
    public static final String ADDREMOVE_ROWS =
	"addRemove.size"; //NOI18N

    private TreeMap availableItems = null; 
    private TreeMap selectedItems = null; 
    private Collator collator = null;

    private String allValues = ""; //NOI18N
    private String selectedValues = ""; //NOI18N

    private static final boolean DEBUG = false; 

    /**
     * Constructor for the AddRemove component
     */
    public AddRemove() { 
	setMultiple(true); 
        setRendererType("com.sun.webui.jsf.AddRemove");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.AddRemove";
    }

    /**
     * Return the number of items to display in the available and
     * selected lists.
     * If no value has been set or is less than 1 or there is no value
     * binding or the value binding evaluates to null, then the value of
     * the <code>addRemove.size</code> theme key defined in the
     * <code>messages</code> theme category, is returned. If the theme
     * key is not defined, <code>12</code> is returned.
     * @return The number of items to disaplay
     */
    public int getRows() {

	int sise = super.getRows();
	if (sise < 1) { 
	    sise = getTheme().getMessageInt(ADDREMOVE_ROWS, 12);
	}
	return sise;
    }

    /**
     * Get the separator string that is used to separate the selected 
     * values on the client.
     * The default value is "|". When the AddRemove component is decoded, the 
     * value is taken from a hidden variable whose value is a list of the 
     * values of all the options in the list representing the selected items. 
     * Consider a case where the AddRemove has a list of options including 
     * <option value="1">One</option> 
     * <option value="2">Two</option> 
     * Assume that these two options are disabled. If the separator 
     * string is set to "|", then the value of the hidden
     * variable will be |1|2|.
     * 
     * You will only need to set this variable if the string 
     * representation of one of the option values contain the 
     * character "|". If you do need to change from the default,
     * bear in mind that the value of the hidden component 
     * is sent as part of the body of the HTTP request body. 
     * Make sure to select a character that does not change 
     * the syntax of the request.
     * @return The separator string. 
     */
    public String getSeparator() { 

	// FIXME: Either should be in Theme or configurable.
	//
	return "|"; //NOI18N
    } 

    /**
     * Returns an iterator over the selected items
     * This function will return one separator element <code>com.sun.web.ui.separator</code>
     * in addition to the selected items even if the selected list is empty.
     * @return an iterator over the selected items
     */
    public Iterator getSelectedItems() {
        FacesContext context = FacesContext.getCurrentInstance();
        // Initialize selectedItems and selectedValues.
        Iterator itr = getListItems(context, true);
	return selectedItems.values().iterator();
    }

    /**
     * This function returns a String consisting of the String representation of the
     * values of all the available Options, separated by the separator
     * String (see getSeparator())
     * @return eturns a String consisting of the String representation of the
     * values of all the available Options, separated by the separator
     * String
     */
    public String getAllValues() { 
	return allValues; 
    } 

    /**
     * This function returns a String consisting of the String representation of the
     * values of the selected Options, separated by the separator
     * String
     * @return a String consisting of the String representation of the
     * values of the selected Options, separated by the separator
     * String 
     */
    public String getSelectedValues() {
        FacesContext context = FacesContext.getCurrentInstance();
        // Initialize selectedItems and selectedValues.
        Iterator itr = getListItems(context, true);
	return selectedValues; 
    } 

    // Buttons
    /**
     * Get or create the ADD button. Retrieves the component specified by the 
     * addButton facet (if there is one) or creates a new Button component.
     * @return A UI Component for the Add button
     * @param context The FacesContext for the request
     * @deprecated  See getAddButtonComponent();
     */
    public UIComponent getAddButtonComponent(FacesContext context) { 
	return getAddButtonComponent();
    }

    /**
     * Return a component that implements the add button.
     * If a facet named <code>addButton</code> is found
     * that component is returned. Otherwise a <code>Button</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_addButton"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Button</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return an add button component
     */
    public UIComponent getAddButtonComponent() { 
	if(DEBUG) log("getAddButtonComponent()");  //NOI18N

	return getButtonFacet(ADD_BUTTON_FACET, false, getTheme().getMessage(
            isVertical() ? ADDVERTICAL_TEXT_KEY : ADD_TEXT_KEY), ADD_FUNCTION);
    }

    /**
     * Return a component that implements the add all button.
     * If a facet named <code>addAllButton</code> is found
     * that component is returned. Otherwise a <code>Button</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_addAllButton"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Button</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return an add all button component
     */
    public UIComponent getAddAllButtonComponent() { 
	if(DEBUG) log("getAddAllButtonComponent()");  //NOI18N

	return getButtonFacet(ADDALL_BUTTON_FACET, false, getTheme().getMessage(
            isVertical() ? ADDALLVERTICAL_TEXT_KEY : ADDALL_TEXT_KEY), ADDALL_FUNCTION);
    }

    /**
     * Return a component that implements the remove button.
     * If a facet named <code>removeButton</code> is found
     * that component is returned. Otherwise a <code>Button</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_removeButton"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Button</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a remove button component
     */
    public UIComponent getRemoveButtonComponent() {
	if(DEBUG) log("getRemoveButtonComponent()");  //NOI18N

	return getButtonFacet(REMOVE_BUTTON_FACET, false, getTheme().getMessage(
            isVertical() ? REMOVEVERTICAL_TEXT_KEY : REMOVE_TEXT_KEY), REMOVE_FUNCTION);
    }

    /**
     * Return a component that implements the remove all button.
     * If a facet named <code>removeAllButton</code> is found
     * that component is returned. Otherwise a <code>Button</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_removeAllButton"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Button</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a remove all button component
     */
    public UIComponent getRemoveAllButtonComponent() { 
	if(DEBUG) log("getRemoveAllButtonComponent()");  //NOI18N
	return getButtonFacet(REMOVEALL_BUTTON_FACET, false, getTheme().getMessage(
            isVertical() ? REMOVEALLVERTICAL_TEXT_KEY : REMOVEALL_TEXT_KEY), REMOVEALL_FUNCTION);
    }

    /**
     * Return a component that implements the move up button.
     * If a facet named <code>moveUpButton</code> is found
     * that component is returned. Otherwise a <code>Button</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_moveUpButton"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Button</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a move up button component
     */
    public UIComponent getMoveUpButtonComponent() { 
	if(DEBUG) log("getMoveUpButtonComponent()");  //NOI18N

	return getButtonFacet(MOVEUP_BUTTON_FACET, false, 
            getTheme().getMessage(MOVEUP_TEXT_KEY), MOVEUP_FUNCTION);
    } 

    /**
     * Return a component that implements the move down button.
     * If a facet named <code>moveDownButton</code> is found
     * that component is returned. Otherwise a <code>Button</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_moveDownButton"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Button</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a move down button component
     */
    public UIComponent getMoveDownButtonComponent() { 
	if(DEBUG) log("getMoveDownButtonComponent()");  //NOI18N

	return getButtonFacet(MOVEDOWN_BUTTON_FACET, false,
            getTheme().getMessage(MOVEDOWN_TEXT_KEY), MOVEDOWN_FUNCTION);
    }
   
    /**
     * Return a component that implements a button facet.
     * If a facet named <code>facetName</code> is found
     * that component is returned. Otherwise a <code>Button</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_<facetName>"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Button</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @param facetName the name of the facet to return or create
     * @param primary if false the button is not a primary button
     * @param text the button text
     * @param onclickFunction the javascript function name for the onclick event
     *
     * @return a button facet component
     */
    private UIComponent getButtonFacet(String facetName, boolean primary,
	String text, String onclickFunction) { 

	if (DEBUG) log("getButtonFacet() " + facetName);  //NOI18N

	// Check if the page author has defined the facet
	//
	UIComponent buttonComponent = getFacet(facetName); 
	if (buttonComponent != null) {
	    if (DEBUG) { 
		log("\tFound facet"); //NOI18N
	    } 
	    return buttonComponent;
	}

	// Return the private facet or create one, but initialize
	// it every time
	//
	// We know it's a Button
	//
	Button button = (Button)ComponentUtilities.getPrivateFacet(this,
		facetName, true);
	if (button == null) {
	    if (DEBUG) log("create Button");  //NOI18N
	    button = new Button(); 
	    button.setEscape(false);
	    button.setId(ComponentUtilities.createPrivateFacetId(this,
		facetName));
	    ComponentUtilities.putPrivateFacet(this, facetName, button);
	}
	initButtonFacet(button, primary, text, onclickFunction);

	return button; 
    }

    /**
     * Initialize a <code>Button</code> facet.
     *
     * @param button the Button instance
     * @param primary if false the button is not a primary button
     * @param text the button text
     * @param onclickFunction the javascript function name for the onclick event
     */
    private void initButtonFacet(Button button, boolean primary, String text,
            String onclickFunction) {
        button.setPrimary(primary);
	button.setText(text);
        
	int tindex = getTabIndex();
	if (tindex > 0) { 
	    button.setTabIndex(tindex);
	} 

        StringBuffer buff = new StringBuffer(256); 
        buff.append(JavaScriptUtilities.getDomNode(getFacesContext(), this))
            .append(onclickFunction)
            .append(RETURN);
        button.setOnClick(buff.toString());

	// NOTE: the original behavior would have set this
	// on the developer defined facet. It was determined that
	// a developer defined facet should not be modified.
	//
	button.setDisabled(isDisabled());
    }

    // Labels
    /**
     * Return a component that implements a label for the available list.
     * If a facet named <code>availableLabel</code> is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_availableLabel"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return an available list label facet component
     */
    public UIComponent getAvailableLabelComponent() { 

	if(DEBUG) log("getAvailableLabelComponent()"); //NOI18N

	String labelString = getAvailableItemsLabel(); 
	if(labelString == null || labelString.length() == 0) { 
	    labelString = getTheme().getMessage(AVAILABLE_TEXT_KEY);
	}

	String styleClass =
	        getTheme().getStyleClass(ThemeStyles.ADDREMOVE_LABEL2); 

	// pass this for labeledComponent and true for hideIndicators.
	// This will defeat the fallback to determine the status of
	// indicators using the labeled component, which is "this"
	// for optimization reasons.
	//
	return getLabelFacet(AVAILABLE_LABEL_FACET, labelString,
	    styleClass, null, true, this, null);
    } 

    /**
     * Return a component that implements a label for the selected list.
     * If a facet named <code>selectedLabel</code> is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_selectedLabel"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a selected list label facet component
     */
    public UIComponent getSelectedLabelComponent() { 


	String labelString = getSelectedItemsLabel(); 
	if(labelString == null || labelString.length() == 0) { 
	    labelString = getTheme().getMessage(SELECTED_TEXT_KEY);
	}

	String styleClass = 
		getTheme().getStyleClass(ThemeStyles.ADDREMOVE_LABEL2); 

	// "forId" MUST be the id that the renderer will use for the
	// select element that represents the selected list.
	// pass null for labeledComponent, since there isn't one,
	// and this for the indicator component.
	//
	return getLabelFacet(SELECTED_LABEL_FACET, labelString,
	    styleClass, 
	    getClientId(FacesContext.getCurrentInstance()).concat(SELECTED_ID),
	    false, null, this);
    } 

    /**
     * Return a component that implements a label for the readOnly selected list.
     * If a facet named <code>selectedLabel</code> is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_selectedLabel"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a selected list label facet component
     */
    public UIComponent getReadOnlyLabelComponent() { 

	if(DEBUG) log("getReadOnlyLabelComponent()"); //NOI18N

	// Prepare to call getLabelFacet so that it can be
	// re-initialized if it exists or created if it doesn't.
	// Preparing this information before we know if there
	// is a developer defined facet, is a hit, but there 
	// is less likelyhood of a developer defined facet, so
	// optimize for the majority case.

	// This extensibility should be accomplished by the
	// developer providing
	// an AVAILABLE_LABEL_FACET. So that "getAvailableItemsLabel"
	// would not have to be expressed as an AddRemove attribute.
	// But in general it is more work for the developer.
	//
	// Alternatively, there could have been a way to 
	// allow the developer to override the default 
	// message key "AddRemove.selected" with the text
	// that they desired, like an "messageBundle" attribute.
	// We could have used "param" tags for this, for example.
	//
	String labelString = getSelectedItemsLabel(); 
	if(labelString == null || labelString.length() == 0) { 
	    labelString = 
		getTheme().getMessage(SELECTED_TEXT_KEY);
	}

	String styleClass = 
	    getTheme().getStyleClass(ThemeStyles.ADDREMOVE_LABEL2_READONLY); 

	return getLabelFacet(READ_ONLY_LABEL_FACET, labelString,
	    styleClass, null, true, getReadOnlyValueComponent(), null);
    } 

    /**
     * Return a component that implements a label for the AddRemove component.
     * If a facet named <code>header</code> is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_header"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a header list label facet component
     */
    public UIComponent getHeaderComponent() { 

	if(DEBUG) log("getHeaderComponent()"); //NOI18N

	String labelString = getLabel(); 
	String styleClass = 
		getTheme().getStyleClass(ThemeStyles.ADDREMOVE_LABEL); 

	// Pass null for "forId" and "this" for labeledComponent.
	// This will result in calling getLabeledElementId which
	// will be AVAILABLE_ID. Pass this for the indicatorComponent.
	//
	return getLabelFacet(HEADER_FACET, labelString,
	    styleClass, null, true, this, this);
    } 
    
    /**
     * Return a component that implements a label for the facetName role.
     * If a facet named facetName is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "facetName"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is re-intialized every time this method is called.
     * </p>
     * <p>
     * If both <code>forId</code> and <code>labeledComponent</code> are
     * specified, <code>forId</code> is ignored.
     * </p>
     *
     * @param facetName the name of the facet to return or create
     * @param labelText the text for the label
     * @param styleClass the label styleClass
     * @param forId the value of the HTML Label element's "for" attribute.
     * @param labeledComponent a component instance that the Label will use
     * as the value of the HTML Label element's "for" attribute.
     * @param indicatorComponent a component instance that the Label will use
     * to ascertain the state of the Label indicators.
     *
     * @return a label facet component
     */
    private UIComponent getLabelFacet(String facetName,
	    String labelText, String styleClass, String forId,
	    boolean hideIndicators,
	    UIComponent labeledComponent,
	    UIComponent indicatorComponent) { 


	// Check if the page author has defined a label facet
	//
	UIComponent labelComponent = getFacet(facetName); 
	if (labelComponent != null) {
	    return labelComponent;
	}

	// There was an implicit policy for the HEADER_FACET
	// that if getLabel() returned null then no label facet
	// was returned or created. Follow that here if 
	// labelText is null, return null. For callers of this
	// method in this file, labelText will only be null if getLabel
	// returns null or empty string, when called by getHeaderComponent.
	//
	if (labelText == null) {
	    return null;
	}

	// Return the private facet or create one, but initialize
	// it every time
	//
	// We know it's a Label
	//
	Label label = (Label)ComponentUtilities.getPrivateFacet(this,
		facetName, true);
	if (label == null) {
	    label = new Label();
	    label.setId(ComponentUtilities.createPrivateFacetId(this,
		facetName));
	    ComponentUtilities.putPrivateFacet(this, facetName, label);
	}
	initLabelFacet(label, facetName, labelText,  styleClass, forId,
		hideIndicators, labeledComponent, indicatorComponent);

	return label; 
    } 

    /**
     * Initialize a <code>Label</code> component for the role of
     * <code>facetName</code>.
     * If forId is null, <code>setLabeledComponent</code> is called
     * with <code>this</code> as the parameter.</br>
     *
     * @deprecated
     *
     * @param label the Button instance
     * @param facetName the name of the facet to return or create
     * @param labelText the text for the label
     * @param styleClass the label styleClass
     * @param forId the component id that this facet labels
     */
    protected void initLabelFacet(Label label, String facetName,
		String labelText, String styleClass, 
	        String forId) {

	initLabelFacet(label, facetName, labelText, styleClass, forId,
	    false, null, null);

    }

    /**
     * Initialize a <code>Label</code> component for the role of
     * <code>facetName</code>.
     * <p>
     * If <code>forId</code> and <code>labeledComponent</code> are
     * specified, <code>forId</code> is ignored.
     *
     * @param label the Button instance
     * @param facetName the name of the facet to return or create
     * @param labelText the text for the label
     * @param styleClass the label styleClass
     * @param forId the component id that this facet labels
     * @param labeledComponent a component instance that the Label will use
     * as the value of the HTML Label element's "for" attribute.
     * @param indicatorComponent a component instance that the Label will use
     * to ascertain the state of the Label indicators.
     */
    private void initLabelFacet(Label label, String facetName,
	    String labelText, String styleClass, String forId,
	    boolean hideIndicators,
	    UIComponent labeledComponent, UIComponent indicatorComponent) {

	if (label == null) {
	    return;
	}

	// Not sure why this is done.
	//
	if (labelText == null || labelText.length() < 1) { 
	    // TODO - maybe print a default? 
	    labelText = new String(); 
	} 

	// By default, the Label component sets the label level to a default
	// value. Since we don't want any level to be set, we need to set the
	// label level to a value outside of the valid range.
	//
	// We should move away from LabelLevel and move to a design that
	// defines a label style for each specific label that can be
	// used specifically for the HTML "class" attribute.
	//
        label.setLabelLevel(getTheme().getMessageInt(ADDREMOVE_LABEL_LEVEL, 0));
	label.setText(labelText); 
	label.setStyleClass(styleClass); 
	label.setHideIndicators(hideIndicators);

	// We need to always set these values even if they are null
	// since null could mean to "clear" the previous value.`
	// And we don't want "for" set if labeledComponent is set.
	//
	label.setLabeledComponent(labeledComponent);
	if (labeledComponent == null) {
	    label.setFor(forId);
	} else {
	    label.setFor(null);
	}

	label.setIndicatorComponent(indicatorComponent);

	return;
    }

    /**
     * Implement this method so that it returns the DOM ID of the 
     * HTML element which should receive focus when the component 
     * receives focus, and to which a component label should apply. 
     * Usually, this is the first element that accepts input. 
     * 
     * @param context The FacesContext for the request
     * @return The client id, also the JavaScript element id
     *
     * @deprecated
     * @see #getLabeledElementId
     * @see #getFocusElementId
     */
    public String getPrimaryElementID(FacesContext context) {
	// Don't return getLabeledElementId here in case
	// callers can't handle null when isReadOnly is true.
	//
        return this.getClientId(context).concat(AVAILABLE_ID); 
    }

    /**
     * Returns the absolute ID of an HTML element suitable for use as
     * the value of an HTML LABEL element's <code>for</code> attribute.
     * If the <code>ComplexComponent</code> has sub-compoents, and one of 
     * the sub-components is the target of a label, if that sub-component
     * is a <code>ComplexComponent</code>, then
     * <code>getLabeledElementId</code> must called on the sub-component and
     * the value returned. The value returned by this 
     * method call may or may not resolve to a component instance.
     * <p>
     * If <code>isReadOnly</code> returns true, then the 
     * <code>getReadOnlyValueComponent</code> method is called. If the
     * component instance returned is a <code>ComplexComponent</code>
     * then <code>getLabeledElementId</code> is called on it and the
     * value returned, else its client id is returned.
     * </p>
     *
     * @param context The FacesContext used for the request
     * @return An abolute id suitable for the value of an HTML LABEL element's
     * <code>for</code> attribute.
     */
    public String getLabeledElementId(FacesContext context) {
	if (isReadOnly()) {
	    UIComponent readOnlyComponent = getReadOnlyValueComponent();
	    if (readOnlyComponent instanceof ComplexComponent) {
	        return ((ComplexComponent)readOnlyComponent).
			getLabeledElementId(context);
	    } else {
		return readOnlyComponent != null ?
			readOnlyComponent.getClientId(context) : null;
	    }
	} else {
	    return this.getClientId(context).concat(AVAILABLE_ID); 
	}
    }

    /**
     * Returns the id of an HTML element suitable to
     * receive the focus.
     * If the <code>ComplexComponent</code> has sub-compoents, and one of 
     * the sub-components is to reveive the focus, if that sub-component
     * is a <code>ComplexComponent</code>, then
     * <code>getFocusElementId</code> must called on the sub-component and
     * the value returned. The value returned by this 
     * method call may or may not resolve to a component instance.
     * <p>
     * This implementation returns the value of
     * <code>getLabeledElementId</code>.
     * </p>
     *
     * @param context The FacesContext used for the request
     */
    public String getFocusElementId(FacesContext context) {
	return getLabeledElementId(context);
    }

    private Theme getTheme() {
	return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }

    /**
     * Retrieve an Iterator of ListSelector.ListItem representing the available selections only. 
     * This method is used by the renderer, to create the options of 
     * the list of available items.
     * @return an Iterator over {@link ListItem}.
     * @param context The FacesContext used for the request
     * @param rulerAtEnd If true, a disabled  list item with a blank label is appended at 
     * the end of the options. The role of the blank 
     * item is to guarantee that the width of the lists
     * do not change when items are moved from one to the 
     * other.
     * @throws javax.faces.FacesException If something goes wrong when the options are processed
     */
    public Iterator getListItems(FacesContext context, boolean rulerAtEnd) 
	throws FacesException { 

	if(DEBUG) log("getListItems()");//NOI18N

        Locale locale = context.getViewRoot().getLocale(); 
	if(DEBUG) log("\tLocale is " + locale.toString()); //NOI18N
	collator =  Collator.getInstance(locale);
	collator.setStrength(Collator.IDENTICAL);
        
	availableItems = new TreeMap(collator);
        selectedItems = new TreeMap(collator); 

	// Retrieve the current selections. If there are selected
	// objects, mark the corresponding items as selected. 
	processOptions(context, collator, locale, rulerAtEnd);

	// We construct a string representation of all values (whether
	// they are selected or not) before we remove the selected
	// items in the processSelections step
	allValues = constructValueString(availableItems); 

	processSelections(); 

	// We construct a string representation of the selected values
	// only 
	selectedValues = 
	    constructValueString(selectedItems, SEPARATOR_VALUE); 

	return availableItems.values().iterator(); 
    } 

    /**
     * Evaluates the list of available Options, creating a ListItem for each
     * one.
     * @param context The FacesContext
     * @param rulerAtEnd the end of the options. The role of the blank 
     * item is to guarantee that the width of the lists
     * do not change when items are moved from one to the 
     * other.
     */
    protected void processOptions(FacesContext context, Collator collator,
                                  Locale locale, boolean rulerAtEnd) { 

	if(DEBUG) log("processOptions()"); //NOI18N
        
        Option[] options = getOptions();
	int length = options.length; 
        
	ListItem listItem = null; 
	String label = null; 
	String lastKey = ""; //NOI18N
	String longestString = ""; //NOI18N
	StringBuffer unsortedKeyBuffer = new StringBuffer(100); 
	boolean sorted = isSorted();

	for (int counter = 0; counter < length; ++counter) {

	    if(options[counter] instanceof OptionGroup) {
		String msg = MessageUtil.getMessage
                    ("com.sun.webui.jsf.resources.LogMessages", //NOI18N
		     "AddRemove.noGrouping");                //NOI18N
		log(msg); 
		continue; 
	    } 
	    if(options[counter] instanceof Separator) {
		String msg = MessageUtil.getMessage
                    ("com.sun.webui.jsf.resources.LogMessages", //NOI18N
		     "AddRemove.noGrouping");                //NOI18N
		log(msg); 
		continue; 
	    }
	    // Convert the option to a list item 
	    listItem = createListItem(options[counter]); 
            
	    label = listItem.getLabel(); 
	    if(label.length() > longestString.length()) { 
		longestString = label; 
	    } 

	    if(sorted) { 

		availableItems.put(label, listItem); 
		if(collator.compare(label, lastKey) > 0) { 
		    lastKey = label;
		} 
	    } 
	    else { 

		// If the page author does not want the list items to be
		// sorted (alphabetically by locale), then they're
		// supposed to be sorted by the order they were added. 
		// Maps are not guaranteed to return items in the order
		// they were added, so we have to create this order
		// artificially. We do that by creating a successively
		// longer key for each element. (a, aa, aaa...). 
		unsortedKeyBuffer.append(KEY_STRING);
		availableItems.put(unsortedKeyBuffer.toString(), listItem); 
		lastKey = unsortedKeyBuffer.toString(); 
	    } 
	}

	if(rulerAtEnd) { 

	    // It looks the like "5" is extra padding or a margin
	    // between the last letter of an item and the list 
	    // box border. This should be a Theme property.
	    // SPACER_STRING should also be a Theme property
	    //
	    int seplength = longestString.length() + 5;
	    StringBuffer labelBuffer = new StringBuffer(seplength);

	    for(int counter=0; counter < seplength; ++counter) {
		labelBuffer.append(SPACER_STRING); 
	    }
	    ListItem item = new ListItem(labelBuffer.toString()); 
            item.setDisabled(true); 
            item.setValue(SEPARATOR_VALUE); 
	    if(sorted) { 
                lastKey = lastKey.concat(KEY_STRING);
		availableItems.put(lastKey, item); //NOI18N
                lastKey = lastKey.concat(KEY_STRING);
                selectedItems.put(lastKey, item);  //NOI18N
	    } 
	    else { 
		unsortedKeyBuffer.append(KEY_STRING);
		availableItems.put(unsortedKeyBuffer.toString(), item);
                unsortedKeyBuffer.append(KEY_STRING);
                selectedItems.put(unsortedKeyBuffer.toString(), item);
	    }
	} 
        
        if(DEBUG) { 
            log("AvailableItems keys"); //NOI18N
            Iterator iterator = availableItems.keySet().iterator();
            while(iterator.hasNext()) { 
              log("next key " + iterator.next().toString());  //NOI18N
            } 
        }
    }
    private String constructValueString(TreeMap map) { 
	return constructValueString(map, null); 
    } 

    private String constructValueString(TreeMap map, String filter) { 

	// Set up the "All values" string. This is rendered as a
	// hidden input on the client side, and is used to 
	StringBuffer valuesBuffer = new StringBuffer(392); 
	Iterator values = map.values().iterator(); 
	ListItem listItem = null; 
	String separator = getSeparator(); 
	valuesBuffer.append(separator); 
	while(values.hasNext()) { 
	    listItem = (ListItem)(values.next()); 
	    if(filter != null && listItem.getValue().equals(filter)) { 
		continue; 
	    } 
	    valuesBuffer.append(listItem.getValue()); 
	    valuesBuffer.append(separator); 
	} 
	return valuesBuffer.toString(); 
    } 

    /**
     * Retrieve an Iterator of ListSelector.ListItem representing the selected selections only. 
     * This method is used by the renderer, to create the options of 
     * the list of selected items. It is also used when calculating a string
     * representation of the value of the component.
     * @return An Iterator over the selected ListItem
     */
    public Iterator getSelectedListItems() {
	return selectedItems.values().iterator();
    }


    /** 
     * Marks options corresponding to objects listed as values of this
     * component as selected.
     * @param list A list representation of the selected values
     * @param processed If true, compare the values object by
     * object (this is done if we compare the value of the object with
     * with the list items). If false, perform a string comparison of
     * the string representation of the submitted value of the
     * component with the string representation of the value from the
     * list items (this is done if we compare the submitted values
     * with the list items). */
    protected void markSelectedListItems(java.util.List list, 
					 boolean processed) { 
        
	if (DEBUG) log("markSelectedListItems()"); //NOI18N

	// The "selected" variable is an iteration over the selected
	// items 

	// CR 6359071
	// Drive the comparisons from the selected list vs. the
	// available list. This results in the resulting mapped
	// selected list reflecting the order of the original
	// selected list.
	// 
	Iterator selected = list.iterator();
	
	boolean allowDups = isDuplicateSelections();

	// The selected items are sorted if "isSorted" is true and
	// "isMoveButtons" is false. If "isMoveButtons" is true then
	// the selected items are not sorted even if "isSorted" is true.
	// They appear as they were inserted.
	// If "isSorted" is false and "isMoveButtons" is false, the selected
	// items will appear as they were inserted.
	// 
	boolean sorted = isSorted() && !isMoveButtons();

	// Use the HashMap "removeItems" to record the selected
	// items that must be removed from the available items.
	// This allows us to not use the available item keys in the
	// selectedItems list, enabling the 
	// selectedItems to be sorted as inserted.
	//
	Map removeItems = new HashMap();

	// Devise a key to use for the selectedItems. Use the same
	// strategy as used for available items. Create an increasing
	// String of the letter KEY_STRING as selected items are recorded.
	// If sorting, use the available item key.
	//
	String selectedKey = ""; //NOI18N
        
	while (selected.hasNext()) { 

	    Object selectedValue = selected.next();

	    // The "keys" are the keys of the options on the available map
	    // Need to "rewind" for every selected item.
	    //
	    Iterator keys = availableItems.keySet().iterator(); 

	    // Does the current listItem match the selected value?
	    boolean match = false; 
            
	    while (keys.hasNext()) {

		Object key = keys.next();
		// The next object from the available map
		//
		Object nextItem = availableItems.get(key);
		ListItem listItem = null;

		// If we get an exception just log it and continue.
		// It's cheaper this way than testing with "instanceof".
		//
		try {
		    listItem = (ListItem)nextItem;
		} catch (Exception e) {
		    log("An available item was not a ListItem."); //NOI18N
		    continue;
		}

		if (DEBUG) { 
		    log("Now processing ListItem " +  //NOI18N
			listItem.getValue());
		    log("\tSelected object value: " +  //NOI18N
			String.valueOf(selectedValue));
		    log("\tSelected object type: " +   //NOI18N 
			selectedValue.getClass().getName());
		    if (processed) { 
			log("\tMatching the values by " + //NOI18N
			    "object.equals()"); //NOI18N
		    } else {
			log("\tMatching the values by string" + //NOI18N
			    "comparison on converted values."); //NOI18N
		    } 
		}

		if (processed) { 
		    match = listItem.getValueObject().equals(selectedValue); 
		} 
		else { 
                    // Recall that "processed" means that we compare using the 
                    // actual value of this component, and this case means that 
                    // we compare from the submitted values. In other words, in
                    // this scenario, the selectedValue is an already converted 
                    // String. 
		    match = 
			selectedValue.toString().equals(listItem.getValue()); 
		} 

		// Note that elements in the selected list that do 
		// not match will not appear in the "selectedItems"
		// TreeMap.
		//
		if (!match) { 
		    continue; 
		} 

		if (DEBUG) log("\tListItem and selected item match"); //NOI18N

		// Ensure that the selectedItems are sorted appropriately.
		// Use the sort order of the available items if sorted
		// and the insertion order if not.
		//
		if (sorted) {
		    selectedKey = key.toString();
                } else {
		    selectedKey = selectedKey.concat(KEY_STRING);
                }

		// See if we have a dup. If dups are allowed 
		// create a new unique key for the dup and add it
		// to the selectedItems.
		// If not a dup, add it to the removeItems map
		// and add it to the selectedItems.
		//
		if (removeItems.containsKey(key)) {
		    if (allowDups) {
			// In case users are allowed to add the same
			// item more than once, use this complicated
			// procedure.
			// The assumption is that "1" comes before "a".
			// 
			if (DEBUG) { 
			    log("\tAdding duplicate " +  //NOI18N
				"and creating unique key."); //NOI18N
			} 
			String key2 = selectedKey.toString().concat(DUP_STRING);
                        while (selectedItems.containsKey(key2)) {
                             key2 = key2.concat(DUP_STRING);
                        } 
			selectedItems.put(key2, listItem); 
		    } else {
			if (DEBUG) { 
			    log("\tDuplicates not allowed " +  //NOI18N
			    "ignoring this duplicate selected item."); //NOI18N
			} 
		    }
		} else {
		    // Add the found key to the removeItems map
		    // and add to the selectedItems.
		    //
		    removeItems.put(key, null);
		    selectedItems.put(selectedKey, listItem); 
		}

		// We have a match break the loop
		//
		break;
	    }
	    if (DEBUG) {
		if (!match) {
		    log("\tSelected value " + //NOI18N
			String.valueOf(selectedValue) +
			" not present on the list of options."); //NOI18N 
		}
	    }
	}

	if (!allowDups) { 
	    if (DEBUG) {
		log("\tRemove the selected items from " +
		    "the available items"); //NOI18N
	    }
	    Iterator keys = removeItems.keySet().iterator();
	    Object key = null;
	    while(keys.hasNext()) {
		key = keys.next();
		availableItems.remove(key);
	    }
	} 
    }

    public boolean mainListSubmits() {
        return false;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Hide value 
    @Property(name="value", isHidden=true, isAttribute=false)
    public Object getValue() {
        return super.getValue();
    }
    
    // Hide labelLevel
    @Property(name="labelLevel", isHidden=true, isAttribute=false)
    public int getLabelLevel() {
        return super.getLabelLevel();
    }
    
    // Hide separators
    @Property(name="separators", isHidden=true, isAttribute=false)
    public boolean isSeparators() {
        return super.isSeparators();
    }
    
    // Hide onBlur
    @Property(name="onBlur", isHidden=true, isAttribute=false)
    public String getOnBlur() {
        return super.getOnBlur();
    }
    
    // Hide onChange
    @Property(name="onChange", isHidden=true, isAttribute=false)
    public String getOnChange() {
        return super.getOnChange();
    }
    
    // Hide onClick
    @Property(name="onClick", isHidden=true, isAttribute=false)
    public String getOnClick() {
        return super.getOnClick();
    }
    
    // Hide onDblClick
    @Property(name="onDblClick", isHidden=true, isAttribute=false)
    public String getOnDblClick() {
        return super.getOnDblClick();
    }
    
    // Hide onFocus
    @Property(name="onFocus", isHidden=true, isAttribute=false)
    public String getOnFocus() {
        return super.getOnFocus();
    }
    
    // Hide onKeyDown
    @Property(name="onKeyDown", isHidden=true, isAttribute=false)
    public String getOnKeyDown() {
        return super.getOnKeyDown();
    }
    
    // Hide onKeyPress
    @Property(name="onKeyPress", isHidden=true, isAttribute=false)
    public String getOnKeyPress() {
        return super.getOnKeyPress();
    }
    
    // Hide onKeyUp
    @Property(name="onKeyUp", isHidden=true, isAttribute=false)
    public String getOnKeyUp() {
        return super.getOnKeyUp();
    }
    
    // Hide onMouseDown
    @Property(name="onMouseDown", isHidden=true, isAttribute=false)
    public String getOnMouseDown() {
        return super.getOnMouseDown();
    }
    
    // Hide onMouseMove
    @Property(name="onMouseMove", isHidden=true, isAttribute=false)
    public String getOnMouseMove() {
        return super.getOnMouseMove();
    }
    
    // Hide onMouseOut
    @Property(name="onMouseOut", isHidden=true, isAttribute=false)
    public String getOnMouseOut() {
        return super.getOnMouseOut();
    }
    
    // Hide onMouseOver
    @Property(name="onMouseOver", isHidden=true, isAttribute=false)
    public String getOnMouseOver() {
        return super.getOnMouseOver();
    }
    
    // Hide onMouseUp
    @Property(name="onMouseUp", isHidden=true, isAttribute=false)
    public String getOnMouseUp() {
        return super.getOnMouseUp();
    }
    
    // Hide onSelect
    @Property(name="onSelect", isHidden=true, isAttribute=false)
    public String getOnSelect() {
        return super.getOnSelect();
    }

    /**
     * <p>The label for the list of available items.</p>
     */
    @Property(name="availableItemsLabel", displayName="Available Items label", category="Appearance")
    private String availableItemsLabel = null;

    /**
     * <p>The label for the list of available items.</p>
     */
    public String getAvailableItemsLabel() {
        if (this.availableItemsLabel != null) {
            return this.availableItemsLabel;
        }
        ValueExpression _vb = getValueExpression("availableItemsLabel");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The label for the list of available items.</p>
     * @see #getAvailableItemsLabel()
     */
    public void setAvailableItemsLabel(String availableItemsLabel) {
        this.availableItemsLabel = availableItemsLabel;
    }

    /**
     * <p>If true, items in the available
     * list are not removed when they are added to the selected list.
     * The user is permitted to add an available item more than once 
     * to the list of selected items. The list of selected items would 
     * then contain duplicate entries.</p>
     */
    @Property(name="duplicateSelections", displayName="Allow Duplicate Selections", category="Data")
    private boolean duplicateSelections = false;
    private boolean duplicateSelections_set = false;

    /**
     * <p>If true, items in the available
     * list are not removed when they are added to the selected list.
     * The user is permitted to add an available item more than once 
     * to the list of selected items. The list of selected items would 
     * then contain duplicate entries.</p>
     */
    public boolean isDuplicateSelections() {
        if (this.duplicateSelections_set) {
            return this.duplicateSelections;
        }
        ValueExpression _vb = getValueExpression("duplicateSelections");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return false;
    }

    /**
     * <p>If true, items in the available
     * list are not removed when they are added to the selected list.
     * The user is permitted to add an available item more than once 
     * to the list of selected items. The list of selected items would 
     * then contain duplicate entries.</p>
     * @see #isDuplicateSelections()
     */
    public void setDuplicateSelections(boolean duplicateSelections) {
        this.duplicateSelections = duplicateSelections;
        this.duplicateSelections_set = true;
    }

    /**
     * <p>Shows the Move Up and Move Down buttons.</p>
     */
    @Property(name="moveButtons", displayName="Move Buttons", category="Appearance")
    private boolean moveButtons = false;
    private boolean moveButtons_set = false;

    /**
     * <p>Shows the Move Up and Move Down buttons.</p>
     */
    public boolean isMoveButtons() {
        if (this.moveButtons_set) {
            return this.moveButtons;
        }
        ValueExpression _vb = getValueExpression("moveButtons");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return false;
    }

    /**
     * <p>Shows the Move Up and Move Down buttons.</p>
     * @see #isMoveButtons()
     */
    public void setMoveButtons(boolean moveButtons) {
        this.moveButtons = moveButtons;
        this.moveButtons_set = true;
    }

    /**
     * <p>Show the Add All and Remove All buttons.</p>
     */
    @Property(name="selectAll", displayName="Select All Buttons", category="Appearance")
    private boolean selectAll = false;
    private boolean selectAll_set = false;

    /**
     * <p>Show the Add All and Remove All buttons.</p>
     */
    public boolean isSelectAll() {
        if (this.selectAll_set) {
            return this.selectAll;
        }
        ValueExpression _vb = getValueExpression("selectAll");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return false;
    }

    /**
     * <p>Show the Add All and Remove All buttons.</p>
     * @see #isSelectAll()
     */
    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
        this.selectAll_set = true;
    }

    /**
     * <p>The label for the list of selected items.</p>
     */
    @Property(name="selectedItemsLabel", displayName="Selected Items label", category="Appearance")
    private String selectedItemsLabel = null;

    /**
     * <p>The label for the list of selected items.</p>
     */
    public String getSelectedItemsLabel() {
        if (this.selectedItemsLabel != null) {
            return this.selectedItemsLabel;
        }
        ValueExpression _vb = getValueExpression("selectedItemsLabel");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The label for the list of selected items.</p>
     * @see #getSelectedItemsLabel()
     */
    public void setSelectedItemsLabel(String selectedItemsLabel) {
        this.selectedItemsLabel = selectedItemsLabel;
    }

    /**
     * <p>If true, the items on the available list are shown in
     * alphabetical order. The items on the selected options list are
     * also shown in alphabetical order, unless the moveButtons
     * attribute is true, in which case the user is expected to order
     * the elements.</p>
     */
    @Property(name="sorted", displayName="Sorted", category="Data")
    private boolean sorted = false;
    private boolean sorted_set = false;

    /**
     * <p>If true, the items on the available list are shown in
     * alphabetical order. The items on the selected options list are
     * also shown in alphabetical order, unless the moveButtons
     * attribute is true, in which case the user is expected to order
     * the elements.</p>
     */
    public boolean isSorted() {
        if (this.sorted_set) {
            return this.sorted;
        }
        ValueExpression _vb = getValueExpression("sorted");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return false;
    }

    /**
     * <p>If true, the items on the available list are shown in
     * alphabetical order. The items on the selected options list are
     * also shown in alphabetical order, unless the moveButtons
     * attribute is true, in which case the user is expected to order
     * the elements.</p>
     * @see #isSorted()
     */
    public void setSorted(boolean sorted) {
        this.sorted = sorted;
        this.sorted_set = true;
    }

    /**
     * <p>Uses the vertical layout instead of the default horizontal layout. The
     * vertical layout displays the available items list above the selected
     * items list.</p>
     */
    @Property(name="vertical", displayName="Vertical layout", category="Appearance")
    private boolean vertical = false;
    private boolean vertical_set = false;

    /**
     * <p>Uses the vertical layout instead of the default horizontal layout. The
     * vertical layout displays the available items list above the selected
     * items list.</p>
     */
    public boolean isVertical() {
        if (this.vertical_set) {
            return this.vertical;
        }
        ValueExpression _vb = getValueExpression("vertical");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return false;
    }

    /**
     * <p>Uses the vertical layout instead of the default horizontal layout. The
     * vertical layout displays the available items list above the selected
     * items list.</p>
     * @see #isVertical()
     */
    public void setVertical(boolean vertical) {
        this.vertical = vertical;
        this.vertical_set = true;
    }

    @Property(name="width", isHidden=true, isAttribute=false)
    public String getWidth() {
        return super.getWidth();
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.availableItemsLabel = (String) _values[1];
        this.duplicateSelections = ((Boolean) _values[2]).booleanValue();
        this.duplicateSelections_set = ((Boolean) _values[3]).booleanValue();
        this.moveButtons = ((Boolean) _values[4]).booleanValue();
        this.moveButtons_set = ((Boolean) _values[5]).booleanValue();
        this.selectAll = ((Boolean) _values[6]).booleanValue();
        this.selectAll_set = ((Boolean) _values[7]).booleanValue();
        this.selectedItemsLabel = (String) _values[8];
        this.sorted = ((Boolean) _values[9]).booleanValue();
        this.sorted_set = ((Boolean) _values[10]).booleanValue();
        this.vertical = ((Boolean) _values[11]).booleanValue();
        this.vertical_set = ((Boolean) _values[12]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[13];
        _values[0] = super.saveState(_context);
        _values[1] = this.availableItemsLabel;
        _values[2] = this.duplicateSelections ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.duplicateSelections_set ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.moveButtons ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.moveButtons_set ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.selectAll ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.selectAll_set ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.selectedItemsLabel;
        _values[9] = this.sorted ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = this.sorted_set ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.vertical ? Boolean.TRUE : Boolean.FALSE;
        _values[12] = this.vertical_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
