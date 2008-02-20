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
import com.sun.webui.jsf.model.list.ListItem;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.MessageUtil;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.validator.StringLengthValidator;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;

import java.lang.reflect.Array;
import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.ValueChangeListener;
import javax.faces.validator.Validator;


/**
 * The EditableList component allows users to create and modify a list of 
 * strings.
 * <p>
 * Use this component when web application users need to create and modify a 
 * list of strings. The application user can add new strings by typing them into
 * the textfield and clicking the "Add" button, and remove them by selecting one
 * or more items from the list and clicking the "Remove" button.</p>
 *
 * <h4>Configuring the listbox tag</h4>
 *
 * <p> Use the <code>list</code> attribute to bind the component
 * to a model. The value must be an EL expression that corresponds to
 * a managed bean or a property of a managed bean, and it must
 * evaluate to an array of  <code>java.lang.String</code>.
 * </p>
 *
 * <p>To set the label of the textfield, use the
 * <code>fieldLabel</code> attribute. To set the label of the
 * textfield, use the <code>listLabel</code> attribute. To validate
 * new items, use the <code>fieldValidator</code> attribute; to
 * validate the contents of the list once the user has finished
 * adding and removing items, specify a <code>labelValidator</code>.</p>
 *
 * <h4>Facets</h4>
 *
 * <ul>
 * <li><code>fieldLabel</code>: use this facet to specify a custom
 * component for the textfield label.</li>
 * <li><code>listLabel</code>: use this facet to specify a custom
 * component for the textfield label.</li>
 * <li><code>field</code>: use this facet to specify a custom
 * component for the textfield.</li>
 * <li><code>addButton</code>: use this facet to specify a custom
 * component for the add button.</li>
 * <li><code>removeButton</code>: use this facet to specify a custom
 * component for the remove button.</li>
 * <li><code>search</code>: use this facet to specify a custom
 * component for the search button. </li>
 * <li><code>readOnly</code>: use this facet to specify a custom
 * component for display a readonly version of the component.</li>
 * <li><code>header</code>: use this facet to specify a header,
 * rendered in a table row above the component.</li>
 * <li><code>footer</code>: use this facet to specify a header,
 * rendered in a table row below the component.</li>
 * </ul>
 *
 * <h4>Client-side JavaScript functions</h4>
 *
 * <ul>
 * <li>NONE yet</li>
 * </ul>
 */
@Component(type="com.sun.webui.jsf.EditableList", family="com.sun.webui.jsf.EditableList", displayName="Editable List", tagName="editableList",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_editable_list",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_editable_list_props")   
public class EditableList extends WebuiInput implements ListManager,
        NamingContainer {
    
    /**
     * The component id for the ADD button
     */
    public static final String ADD_BUTTON_ID = "_addButton"; //NOI18N
    /**
     * The add button facet name
     */
    public static final String ADD_BUTTON_FACET = "addButton"; //NOI18N
    /**
     * The add button text key.
     */
    public static final String ADD_BUTTON_TEXT_KEY = "EditableList.add"; //NOI18N
    /**
     * The component ID for the remove button
     */
    public static final String REMOVE_BUTTON_ID = "_removeButton"; //NOI18N
    /**
     * The remove button facet name
     */
    public static final String REMOVE_BUTTON_FACET = "removeButton"; //NOI18N
    /**
     * The remove button text key.
     */
    public static final String REMOVE_BUTTON_TEXT_KEY = "EditableList.remove"; //NOI18N
    /**
     * The component ID for the textfield
     */
    public static final String FIELD_ID = "_field"; //NOI18N
    /**
     * The input field facet name.
     */
    public static final String FIELD_FACET = "field"; //NOI18N
    /**
     * The text key for the StringLengthValidator "too long" message.
     */
    public static final String SLV_TOOLONG_KEY =
                    "EditableList.itemTooLong"; //NOI18N
    /**
     * The text key for the StringLengthValidator "too short" message.
     */
    public static final String SLV_TOOSHORT_KEY =
                "EditableList.fieldEmpty"; // NOI18N
    /**
     * The component ID for the textfield
     */
    public static final String LIST_LABEL_ID = "_listLabel"; //NOI18N
    /**
     * The list label facet name.
     */
    public static final String LIST_LABEL_FACET = "listLabel"; //NOI18N
    /**
     * The list default label text key.
     */
    public static final String LIST_LABEL_TEXT_KEY =
                "EditableList.defaultListLabel"; //NOI18N
    
    /**
     * The component ID for the textfield
     */
    public static final String FIELD_LABEL_ID = "_fieldLabel"; //NOI18N
    /**
     * The input field label facet name.
     */
    public static final String FIELD_LABEL_FACET = "fieldLabel"; //NOI18N
    /**
     * The default field label text key.
     */
    public static final String FIELD_LABEL_TEXT_KEY =
                            "EditableList.defaultFieldLabel"; //NOI18N
    
    /**
     * The component ID for the textfield
     */
    public static final String READ_ONLY_ID = "_readOnly"; //NOI18N
    /**
     * The read only facet name.
     */
    public static final String READ_ONLY_FACET = "readOnly"; //NOI18N
    
    /**
     * Facet name for the header facet
     */
    public static final String HEADER_FACET = "header"; //NOI18N
    
    /**
     * Facet name for the footer facet
     */
    public static final String FOOTER_FACET = "footer"; //NOI18N
    
    /**
     * Name of the JavaScript function which is responsible for adding elements from the availble list to the selected list
     */
    public static final String ADD_FUNCTION = ".add(); ";
    
    /**
     * Name of the JavaScript function which is responsible for
     * enabling/disabling the add button
     */
    public static final String ENABLE_ADD_FUNCTION = ".enableAdd(); ";
    
    /**
     * Name of the JavaScript function which is responsible for
     * enabling/disabling the add button
     */
    public static final String SET_ADD_DISABLED_FUNCTION = ".setAddDisabled(false);";
    
    /**
     * Name of the JavaScript function which is responsible for
     * enabling/disabling the remove button
     */
    public static final String ENABLE_REMOVE_FUNCTION = ".enableRemove(); ";
    
    /**
     * Name of the JavaScript function that updates the buttons
     */
    public static final String UPDATE_BUTTONS_FUNCTION = ".updateButtons(); ";

    // FIXME: This should be part of the theme.
    //
    /**
     * Read only separator string
     */
    private static final String READ_ONLY_SEPARATOR = ", "; //NOI18N
    
    /**
     * Facet name for the search facet
     */
    public static final String SEARCH_FACET = "search"; //NOI18N

    // FIXME: This should be part of the theme.
    //
    public static final String SPACER_STRING = "_"; //NOI18N

    private static final String KEY_STRING = "a"; //NOI18N
    private static final String DUP_STRING = "\t"; //NOI18N
    
    // FIXME: This should be part of the theme.
    //
    private static final int MIN_LENGTH = 20;
    
    private static final boolean DEBUG = false;
    
    private TreeMap listItems = null;
    private Collator collator = null;
    private transient Theme theme = null;
    private String selectedValue = null;
    private String[] valuesToRemove = null;

    /**
     * Default constructor.
     */
    public EditableList() {
        super();
        setRendererType("com.sun.webui.jsf.EditableList");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.EditableList";
    }

    /**
     * Get the maximum length of the strings on the list.
     * If the length is less than 1, the default value of 15 is returned.
     *
     * @return An integer value for the maximum number of characters on the list
     */
    public int getMaxLength() {
        int length = _getMaxLength();

        if(length < 1) {
            // FIXME: Should be part of Theme.
            length = 25;
            // Shouldn't reset the length, it clobbers a 
            // developers value. Just return the default.
            //
            //super.setMaxLength(length);
        }
        return length;
    }
    
    // Buttons
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
        
        if(DEBUG) log("getAddButtonComponent()"); //NOI18N

        return getButtonFacet(ADD_BUTTON_FACET, false,
            getTheme().getMessage(ADD_BUTTON_TEXT_KEY), new AddListener());
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
        
        if(DEBUG) log("getRemoveButtonComponent()"); //NOI18N

        return getButtonFacet(REMOVE_BUTTON_FACET, false,
                getTheme().getMessage(REMOVE_BUTTON_TEXT_KEY),
                new RemoveListener());
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
     * @param actionListener the button's actionListener
     *
     * @return a button facet component
     */
    private UIComponent getButtonFacet(String facetName, boolean primary,
        String text, ActionListener actionListener) { 

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
            if (DEBUG) log("create Button"); 
            button = new Button(); 
            button.setId(ComponentUtilities.createPrivateFacetId(this,
                facetName));
            button.addActionListener(actionListener);
            ComponentUtilities.putPrivateFacet(this, facetName, button);
        }
        initButtonFacet(button, primary, text);

        return button; 
    }
    
    /**
     * Initialize a <code>Button</code> facet.
     *
     * @param button the Button instance
     * @param text the button text
     */
    private void initButtonFacet(Button button, boolean primary, String text) {
        
        if(DEBUG) log("initButtonFacet()"); //NOI18N
        
        button.setText(text);
        int tindex = getTabIndex();
        if (tindex > 0) {
            button.setTabIndex(tindex);
        }
        button.setImmediate(true);
        button.setPrimary(primary);
        button.setDisabled(isDisabled());
    }
    
    // Labels
    //
    /**
     * Return a component that implements a label for the list.
     * If a facet named <code>listLabel</code> is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_listLabel"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a list label component
     */
    public UIComponent getListLabelComponent() {
        
        if(DEBUG) log("getListLabelComponent()"); //NOI18N

        String labelString = getListLabel();
        if(labelString == null || labelString.length() == 0) {
            labelString = getTheme().getMessage(LIST_LABEL_TEXT_KEY);
        }
	// We need to pass the "for" id here, because getLabeledElementId
	// returns the id of the field component.
	//
        return getLabelFacet(LIST_LABEL_FACET, labelString, false,
	    getClientId(FacesContext.getCurrentInstance()).
		    concat(ListSelector.LIST_ID), null, this);
    }
    
    /**
     * Return a component that implements a label for the input field.
     * If a facet named <code>fieldLabel</code> is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_fieldLabel"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a field label component
     */
    public UIComponent getFieldLabelComponent() {
        
        if(DEBUG) log("getFieldLabelComponent()"); //NOI18N

        String labelString = getFieldLabel();
        if(labelString == null || labelString.length() == 0) {
            labelString = getTheme().getMessage(FIELD_LABEL_TEXT_KEY);
        }
        // This will cause two initializations of the private field 
        // facet, in succession. The renderer is calling 
        // getFieldLabelComponent()
        // getFieldComponent()
        //
	UIComponent fc = getFieldComponent();
        return getLabelFacet(FIELD_LABEL_FACET, labelString, false, 
		null, fc, fc);
    }
    
    /**
     * Return a component that implements a label facet.
     * If a facet named <code>facetName</code> is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_<facetName>"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is re-intialized every time this method is called.
     * </p>
     * 
     *
     * @param facetName the name of the facet to return or create
     * @param text the label text
     * @param hideIndicators if true hide the label's indicators.
     * @param labeledComponent the component instance this label is for
     * @param indicatorComponent the component instance to determine
     * the label's indicator status
     *
     * @return a label facet component
     */
    private UIComponent getLabelFacet(String facetName, String text,
		boolean hideIndicators, String forId,
		UIComponent labeledComponent,
		UIComponent indicatorComponent) {

        if (DEBUG) log("getLabelFacet() " + facetName);  //NOI18N

        // Check if the page author has defined the facet
        //
        UIComponent labelComponent = getFacet(facetName); 
        if (labelComponent != null) {
            if (DEBUG) { 
                log("\tFound facet"); //NOI18N
            } 
            return labelComponent;
        }

        // Return the private facet or create one, but initialize
        // it every time
        //
        // We know it's a Label
        //
        Label label = (Label)ComponentUtilities.getPrivateFacet(this,
                facetName, true);
        if (label == null) {
            if (DEBUG) log("create Label"); 
            label = new Label(); 
            label.setId(ComponentUtilities.createPrivateFacetId(this,
                facetName));
            ComponentUtilities.putPrivateFacet(this, facetName, label);
        }
        if(text == null || text.length() < 1) {
            // TODO - maybe print a default?
            // A Theme default value.
            text = new String();
        }

        label.setText(text);
	label.setFor(forId);
        label.setLabelLevel(getLabelLevel());
	label.setHideIndicators(hideIndicators);
	label.setLabeledComponent(labeledComponent);
	label.setIndicatorComponent(indicatorComponent);

        return label; 
    }
    
    // Other
    /**
     * Return the actual facet or private facet without
     * reinitializing it, if it exists. We sometimes want the component
     * in the state that it was last rendered with and not
     * a newly initialized state, like during validation processing
     * or decoding. For example, you don't want to render with
     * a 30 character StringLengthValidator and then validate
     * against a 25 character validator on the odd chance that
     * getMaxLength() returns a different value during decode.
     */
    private UIComponent getRenderedFieldComponent() {

        // Check if the page author has defined the facet
        //
        UIComponent fieldComponent = getFacet(FIELD_FACET); 
        if (fieldComponent != null) {
            if (DEBUG) { 
                log("\tFound facet"); //NOI18N
            } 
            return fieldComponent;
        }

        // If the private facet does't exist
        // just call the normal "getFieldComponent", but
        // it should exist, since getRenderedFieldComponent is designed
        // to be used for lifecycle phases before rendereding, during
        // request processing.
        //
        fieldComponent = ComponentUtilities.getPrivateFacet(this,
            FIELD_FACET, false);

        return fieldComponent == null ? getFieldComponent() : fieldComponent;
    }

    /**
     * Return a component that implements an input field facet.
     * If a facet named <code>field</code> is found
     * that component is returned. Otherwise a <code>TextField</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_field"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>TextField</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return an input field facet component
     */
    public UIComponent getFieldComponent() {

        if (DEBUG) log("getFieldComponent()");  //NOI18N

        // Check if the page author has defined the facet
        //
        UIComponent fieldComponent = getFacet(FIELD_FACET); 
        if (fieldComponent != null) {
            if (DEBUG) { 
                log("\tFound facet"); //NOI18N
            } 
            return fieldComponent;
        }

        // Return the private facet or create one, but initialize
        // it every time
        //
        // We know it's a TextField
        //
        TextField field = (TextField)ComponentUtilities.getPrivateFacet(this,
            FIELD_FACET, true);
        if (field == null) {
            if (DEBUG) log("create Field"); //NOI18N
            field = new TextField(); 
            field.setId(ComponentUtilities.createPrivateFacetId(this,
                FIELD_FACET));
            field.setTrim(true);
            ComponentUtilities.putPrivateFacet(this, FIELD_FACET, field);

	    // Add the validator ONCE !
	    //
	    StringLengthValidator strl =
		    new StringLengthValidator(getMaxLength(), 1);

	    Theme theme = getTheme();
	    strl.setTooLongMessage(theme.getMessage(SLV_TOOLONG_KEY));
	    strl.setTooShortMessage(theme.getMessage(SLV_TOOSHORT_KEY));

	    field.addValidator(strl);

        }
        initFieldFacet(field);

        return field; 
    }

    private void initFieldFacet(TextField field) {
        if(DEBUG) log("initFieldFacet()"); //NOI18N

        // FIXME should use formElements.js function for return key
        //
        String jsObjectName = getJavaScriptObjectName();
        StringBuffer onkeypressBuffer = new StringBuffer(128);
        onkeypressBuffer.append("if(event && event.keyCode == 13) { ");  //NOI18N      
        onkeypressBuffer.append(jsObjectName);
        onkeypressBuffer.append(ADD_FUNCTION);
        onkeypressBuffer.append("return false; } "); //NOI18N

        field.setOnKeyPress(onkeypressBuffer.toString());
        
        StringBuffer onfocusBuffer = new StringBuffer(128);
        onfocusBuffer.append(jsObjectName);
        onfocusBuffer.append(SET_ADD_DISABLED_FUNCTION);
        onfocusBuffer.append("return false;"); //NOI18N
        
        field.setOnFocus(onfocusBuffer.toString());

        StringBuffer onfocuslostBuffer = new StringBuffer(128);
        onfocuslostBuffer.append(jsObjectName);
        onfocuslostBuffer.append(ENABLE_ADD_FUNCTION);
        onfocuslostBuffer.append("return false;"); //NOI18N
        
        field.setOnBlur(onfocuslostBuffer.toString());

        // FIXME: MIN_LENGTH should be part of Theme
        //
        int columns = getMaxLength();
        if(columns < MIN_LENGTH) {
            columns = MIN_LENGTH;
        }
        field.setColumns(columns);

        int tindex = getTabIndex();
        if (tindex > 0) {
            field.setTabIndex(tindex);
        }
        
        field.setDisabled(isDisabled());

	// Now add an application field validator expression
	// Do this every time in case it was changed on the
	// EditableList.
	//
	field.setValidatorExpression(getFieldValidatorExpression());
    }

    /**
     * Return a component that implements the read only value of this 
     * EditableList.
     * If a facet named <code>readOnly</code> is found
     * that component is returned. Otherwise a <code>StaticText</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_readOnly"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>StaticText</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a component that represents the read only value of this EditableList
     */
    public UIComponent getReadOnlyValueComponent() {
        
        if(DEBUG) log("getReadOnlyValueComponent()"); //NOI18N
        
        // Check if the page author has defined the facet
        //
        UIComponent textComponent = getFacet(READ_ONLY_FACET); 
        if (textComponent != null) {
            if (DEBUG) { 
                log("\tFound facet"); //NOI18N
            } 
            return textComponent;
        }

        // Just create it every time.
        //
        if (DEBUG) log("create StaticText"); //NOI18N
        StaticText text = new StaticText(); 
        text.setId(ComponentUtilities.createPrivateFacetId(this,
                READ_ONLY_FACET));
        text.setParent(this);
        
        FacesContext context = FacesContext.getCurrentInstance();
        String readOnlyString = getValueAsReadOnly(context);
        if (readOnlyString == null || readOnlyString.length() < 1) {
            // TODO - maybe print a default?
            readOnlyString = new String();
        }
        text.setText(readOnlyString);
        return text; 
    }
    
    // Readonly value
    /**
     * Return a string suitable for displaying the value in read only mode.
     * The default is to separate the list values with a comma.
     *
     * @param context The FacesContext
     * @throws javax.faces.FacesException If the list items cannot be processed
     */
    protected String getValueAsReadOnly(FacesContext context)
    throws FacesException {
        
        // The comma format READ_ONLY_SEPARATOR should be part of the theme
        // and/or configurable by the application
        //
        StringBuffer valueBuffer = new StringBuffer(200);
        
        Iterator iterator = getListItems(context, false);
        
        while(iterator.hasNext()) {
            String string = ((ListItem)(iterator.next())).getLabel();
            // Do this with a boolean on getListItems instead
            if(string.indexOf("nbsp") > -1) {  //NOI18N
                continue;
            }
            valueBuffer.append(string);
            if(iterator.hasNext()) {
                valueBuffer.append(READ_ONLY_SEPARATOR);
            }
        }
        return valueBuffer.toString();
    }
    
    // The following methods overrides default behaviour that does not
    // make sense for this component
    /**
     *
     * @param converter
     */
    public void setConverter(javax.faces.convert.Converter converter) {
        String msg = getTheme().getMessage("EditableList.noConversion"); //NOI18N
        throw new RuntimeException(msg);
    }
    
    public String getJavaScriptObjectName() {
        return JavaScriptUtilities.getDomNode(getFacesContext(), this);
    }

    private Theme getTheme() {
        return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }
    
    public String getOnChange() {
        StringBuffer onchangeBuffer = new StringBuffer(128);
        onchangeBuffer.append(getJavaScriptObjectName());
        onchangeBuffer.append(ENABLE_REMOVE_FUNCTION);
        return onchangeBuffer.toString();
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

        // If this component has a label return the field component's
	// id. Since that is where we want focus to go when clicking
	// an overall label.
        //
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
	    // Return the field component id because if an overall label
	    // is clicked, the focus should go to the text field.
	    //
	    //return getClientId(context).concat(ListSelector.LIST_ID);

	    UIComponent fc = getFieldComponent();
	    if (fc instanceof ComplexComponent) {
		return ((ComplexComponent)fc).getLabeledElementId(context);
	    } else {
		return fc != null ? fc.getClientId(context) : null;
	    }
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
     * This implementations returns the value of 
     * <code>getLabeledElementId</code>.
     * </p>
     *
     * @param context The FacesContext used for the request
     */
    public String getFocusElementId(FacesContext context) {
        // This is a little sketchy, because in this case we'd actually prefer
        // to return different values for focus and for the labelled component.
        // We should always label the list (that's the one that should have the
        // invalid icon if the list is empty, for example. But we should
        // probably also set the focus to the top input component which could
        // be either the field or the label. Ah well. I can get around this
        // if I implement some extra bits on the label.
        // TODO
	return getLabeledElementId(context);
    }

    /**
     * Return a component instance that can be referenced
     * by a <code>Label</code> in order to evaluate the <code>required</code>
     * and <code>valid</code> states of this component.
     *
     * @param context The current <code>FacesContext</code> instance
     * @param label The <code>Label</code> that labels this component.
     * @return a <code>UIComponent</code> in order to evaluate the
     * required and valid states.
     */
    public UIComponent getIndicatorComponent(FacesContext context,
            Label label) {
	return this;
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
	// In case callers can't handle null do not
	// return getLabeledElementId here.
	//
        return getClientId(context).concat(ListSelector.LIST_ID);
    }
    
    /**
     * Getter for property valuesToRemove.
     * @return Value of property valuesToRemove.
     */
    public String[] getValuesToRemove() {
        if(valuesToRemove == null) {
            return new String[0];
        }
        return this.valuesToRemove;
    }
    
    /**
     * Setter for property valuesToRemove.
     * @param valuesToRemove New value of property valuesToRemove.
     */
    public void setValuesToRemove(String[] valuesToRemove) {
        
        this.valuesToRemove = valuesToRemove;
    }

    /**
     * Retrieve an Iterator of ListSelector.ListItem, to be used by the
     * renderer.
     * @return an Iterator over {@link ListItem}.
     * @throws javax.faces.FacesException
     */
    public Iterator getListItems(FacesContext context, boolean rulerAtEnd) throws FacesException {
        
        if(DEBUG) log("getListItems()");
        
        Locale locale = context.getViewRoot().getLocale();
        if(DEBUG) log("\tLocale is " + locale.toString());
        collator =  Collator.getInstance(locale);
        listItems = new TreeMap(collator);

        // Are we sorting ?
        //
        boolean sorted = isSorted();

        // Keep a list of keys that have been seen
        // to support duplicates when sorting.
        //
        Map keysSeen = sorted ? new HashMap() : null;
        
        // We have to make sure that the long empty list item (whose
        // purpose is to guarantee that the size of the list stays
        // constant) is alwasy at the bottom of the list.  (=has the
        // highest key in the map). We do this by identifying the
        // longest key in the map, as long as the collator is
        // concerned and appending something at the end. (It's not
        // possible to use a constant for this, since an o with an
        // umlaut comes after z in Swedish, but before it in German,
        // for example).
        String lastKey = ""; //NOI18N
        String[] currentValues = getCurrentValueAsStringArray();
        if(DEBUG) {
            log("\tValues are:");
            for(int i=0; i<currentValues.length; ++i) {
                log(currentValues[i]);
            }
        }
        
        // The string currently being evaluated
        String currentString = null;
        
        // Two cases:
        // First case: the page author requested a sorted map (by
        // character), in which case we sort by the strings
        // themselves. The last key is set to the string that the
        // collator deems to be the last.
        // Second case: the list is sorted by the order they were
        // added to the map. We deal with that by generating a
        // successively longer key for each entry (maps do not
        // conserve the order the items were added). The last key
        // is set to the last key generated.
        //
        ListItem listItem = null;

        // Keep track of the last ListItem that matches the 
        // selectedValue. This is necessary so that the last
        // seen duplicate of the selectedValue is selected.
        // And so that only one match is selected.
        //
        ListItem selectedItem = null;

        // If the page author does not want the list items to be
        // sorted (alphabetically by locale), then they're
        // supposed to be sorted by the order they were added.
        // Maps are not guaranteed to return items in the order
        // they were added, so we have to create this order
        // artificially. We do that by creating a successively
        // longer key for each element. (a, aa, aaa...).
        //
        // Only need this when not sorting
        //
        StringBuffer unsortedKeyBuffer = sorted ? null :
                new StringBuffer(KEY_STRING);

        for (int counter=0; counter < currentValues.length; ++ counter) {
            currentString = currentValues[counter];

            if(DEBUG) {
                log("Current string is " + currentString); //NOI18N
                log("SelectedValue is " + 
                        String.valueOf(selectedValue)); //NOI18N
            }
            
            if (currentString == null) {
                String msg = MessageUtil.getMessage
                        ("com.sun.webui.jsf.resources.LogMessages",
                        "EditableList.badValue", 
                        new Object[]{ getClientId(context) });
                throw new FacesException(msg);
            }
            
            listItem = new ListItem(currentString);
            listItem.setValue(currentString);

            // The selectedValue will be the last entry in the 
            // submittedValues array (getCurrentValueAsStringArray)
            // if it is not null. It will also appear as the "last"
            // duplicate.
            //
            // Keep track of last ListItem that matches the selectedValue
            // and at the end mark the last one found as selected.
            //
            if(currentString.equals(selectedValue)) {
                if(DEBUG) log("Selected value seen");
                selectedItem = listItem;
            }
            if(sorted) {
                // Since duplicates are allowed, if a duplicate occurs
                // the key must be unique but must sort next
                // to the duplicate. Add an increasing string of
                // spaces to the duplicate key which should sort the keys
                // next to each other.
                //
                String key = currentString;
                if (keysSeen.containsKey(key)) {
                    String dup_string = (String)keysSeen.get(key);
                    dup_string = dup_string.concat(DUP_STRING);
                    key = key.concat(dup_string);
                    keysSeen.put(currentString, dup_string);
                } else {
                    keysSeen.put(key, DUP_STRING);
                }
                if(collator.compare(key, lastKey) > 0) {
                    lastKey = key;
                }
                listItems.put(key, listItem);
            } else {
                listItems.put(unsortedKeyBuffer.toString(), listItem);
                unsortedKeyBuffer.append(KEY_STRING);
            }
        }

        // selectedItem was the last seen entry that matched the
        // selectedValue
        //
        if (selectedItem != null) {
            selectedItem.setSelected(true);
        }

        if(!sorted) {
            lastKey = unsortedKeyBuffer.toString();
        }
        
        // rulerAtEnd will be true if the invoker needs a blank
        // disabled list option at the end. Typically this is
        // needed by the renderer, to guarantee that the widget
        // stays the same in size when items are added and removed.
        if(rulerAtEnd) {
            
            int length = getMaxLength();
            if(length < MIN_LENGTH) {
                length = MIN_LENGTH;
            }
            StringBuffer labelBuffer = new StringBuffer(length);
            
            for(int counter=0; counter < length; ++counter) {
                labelBuffer.append(SPACER_STRING);
            }
            ListItem item = new ListItem(labelBuffer.toString());
            item.setDisabled(true);
            listItems.put(lastKey.concat(KEY_STRING), item);
        }
        
        return listItems.values().iterator();
    }
    
    private String[] getCurrentValueAsStringArray() {
        
        
        if(DEBUG) log("getCurrentValueAsStringArray()");
        Object value = getSubmittedValue();
        if(value == null) {
            if(DEBUG) log("\tUsing regular value");
            value = getValue();
        } else if(DEBUG) log("\tUsing submitted value");
        
        if(value == null) {
            return new String[0];
        }
        if(value instanceof String[]) {
            return (String[])value;
        }
        
        String msg = MessageUtil.getMessage
                ("com.sun.webui.jsf.resources.LogMessages",
                "EditableList.badValue", 
                new Object[]{ getClientId(FacesContext.getCurrentInstance()) });
        throw new FacesException(msg);
    }
    
    private void log(String s) {
        System.out.println(this.getClass().getName() + "::" + s); //NOI18N
    }
    
    /**
     * Retrieve the value of this component (the "selected" property) as an
     * object. This method is invoked by the JSF engine during the validation
     * phase. The JSF default behaviour is for components to defer the
     * conversion and validation to the renderer, but for the Selector based
     * components, the renderers do not share as much functionality as the
     * components do, so it is more efficient to do it here.
     * @param context The FacesContext of the request
     * @param submittedValue The submitted value of the component
     */
    
    public Object getConvertedValue(FacesContext context,
            Object submittedValue)
            throws ConverterException {
        
        if(DEBUG) log("getConvertedValue()");
        
        if(!(submittedValue instanceof String[])) {
            throw new ConverterException(
                    "Submitted value must be a String array");
        }
        String[] rawValues = (String[])submittedValue;
        
        // If there are no elements in rawValues nothing was submitted.
        // If null was rendered, return null
        //
        if(rawValues.length == 0) {
            if (ConversionUtilities.renderedNull(this)) {
                return null;
            }
        }
        return submittedValue;
    }
    
    /**
     * @exception NullPointerException
     */
    public void processValidators(FacesContext context) {
        
        if (context == null) {
            throw new NullPointerException();
        }
        
        // Skip processing if our rendered flag is false
        if (!isRendered()) {
            return;
        }
        
        // This component may be a developer defined facet.
        // It is explicitly validated during an Add action.
        // It must not be validated during a submit. The assumption
        // is that processValidators is being called during
        // a submit and not in an immediate context.
        // Compare the id of this component with the children
        // and facets and if it matches don't call its
        // processValidators method.
        //
        // Get the last rendered field component instead of 
        // a newly intialized one.
        //
        UIComponent field = getRenderedFieldComponent();
        String fieldId = field.getId();
        
        // Process all the facets and children of this component
        Iterator kids = getFacetsAndChildren();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();
            // We probably should ensure that fieldId is not
            // null, during getFieldComponent() even if
            // it is a developer defined facet.
            //
            if (fieldId != null && fieldId.equals(kid.getId())) {
                continue;
            }
            kid.processValidators(context);
        }
        
        // Validate the EditableList
        //
        checkValid(context);
    }
    
    public void processAddAction() {
        
        if(DEBUG) log("processAddAction()");
        
        // If we are rendering prematurely don't do anything
        //
        if (FacesContext.getCurrentInstance().getRenderResponse()) {
            return;
        }
        
        selectedValue = null;
        
        String[] values = getCurrentValueAsStringArray();
        
        Object value = getAddedObject();
        if(value == null) {
            return;
        }
        //TODO - fix this when implementing conversion for this component
        selectedValue = value.toString();
        
        int numValues = values.length;
        
        String[] newValues = new String[numValues + 1];
        int counter;
        for(counter=0; counter < numValues; ++counter) {
            newValues[counter] = values[counter];
            if(DEBUG) log("\tAdding " + newValues[counter]);
        }
        newValues[counter] = selectedValue;
        if(DEBUG) log("\tAdding " + newValues[counter]);
        setSubmittedValue(newValues);
    }
    
    public void processRemoveAction() {
        
        if(DEBUG) log("processRemoveAction()");
        
        // If we are rendering prematurely don't do anything
        //
        if (FacesContext.getCurrentInstance().getRenderResponse()) {
            return;
        }
        
        // Reset the selected value
        selectedValue = null;
        
        ArrayList items = new ArrayList();
        int counter;
        
        if(getValue() != null) {
            if(DEBUG) log("\tList was not empty");
            String[] strings = getCurrentValueAsStringArray();
            int length = strings.length;
            for(counter=0; counter<length; ++counter) {
                items.add(strings[counter]);
                if(DEBUG) log("Added " + strings[counter]);
            }
        }
        
        String[] valuesToRemove = getValuesToRemove();
        for(counter=0; counter < valuesToRemove.length; ++counter) {
            items.remove(valuesToRemove[counter]);
            if(DEBUG) log("remove " + valuesToRemove[counter]);
        }
        
        String[] newValues = new String[items.size()];
        for(counter=0; counter < items.size(); ++counter) {
            newValues[counter] = (String)(items.get(counter));
            if(DEBUG) log("\tAdding back " + newValues[counter]);
        }
        
        setValuesToRemove(null);
        setSubmittedValue(newValues);
    }
    
    private void checkValid(FacesContext context) {
        
        
        if(DEBUG) log("checkValid()");
        
        try {
            validate(context);
        } catch (RuntimeException e) {
            if(DEBUG) log("Error during validation");
            context.renderResponse();
            throw e;
        }
        
        if (!isValid()) {
            if(DEBUG) log("Component is not valid");
            context.renderResponse();
        }
    }
    
    private Object getAddedObject() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        if(DEBUG) log("\tAdd a new item");
        
        // Need to get the field's value validated first
        // The field can't be immediate because we don't want
        // to validate it if the value is not going to be added.
        // For example in a real submit request.
        //
        // Get the last rendered Field component, not necessarily
        // a newly initialized one.
        //
        EditableValueHolder field = (EditableValueHolder)
                getRenderedFieldComponent();
        
        // This is ok to do here.
        // We are currently after the APPLY_REQUEST_VALUES phase
        // and before the PROCESS_VALIDATIONS phase.
        // If the field were marked immediate, then the validation
        // would have occured before we get here. If not done
        // here it will be done in the next phase. But it needs
        // to be done here, sort a simulation of immediate
        // henavior. But we don't want the side effect of immediate
        // behavior from external immediate components.
        //
        ((UIComponent)field).processValidators(context);
        
        if (!field.isValid()) {
            return null;
        }
        // Get the value from the field.
        //
        Object value = field.getValue();
        
        // This is a policy of the EditableList.
        // An emptyString or null value cannot be added to the list.
        //
        if (value == null ||
                (value instanceof String && value.toString().length() == 0)) {
            
            field.setValid(false);
            context.renderResponse();
            
            if(DEBUG) log("No value from the field");
            
            String message = ThemeUtilities.getTheme(context).
                    getMessage("EditableList.fieldEmpty");
            context.addMessage(getClientId(context), new FacesMessage(message));
            return null;
        }
        // The new value was added so clear the value.
        // This set is questionable, if the field is a developer
        // defined facet. This will cause an update to the model
        // before the value change event.
        //
        if(DEBUG) log("\tFound new value: " + value);
        field.setValue(null);
        
        return value;
    }
    
    /* Don't need this. Only needed for debugging.
    public void setValue(Object value) {
     
        if(DEBUG) log("setValue()...");
        super.setValue(value);
        if(DEBUG) log("\tLocal value set: " +
                String.valueOf(isLocalValueSet()));
    }
     */
    
    /** Always returns false for EditableList **/
    private boolean _isImmediate() {
        return false;
    }
    
    /**
     * <p>Return <code>true</code> if the new value is different from the
     * previous value.</p>
     *
     * This only implements a compareValues for value if it is an Array.
     * If value is not an Array, defer to super.compareValues.
     * The assumption is that the ordering of the elements
     * between the previous value and the new value is determined
     * in the same manner.
     *
     * Another assumption is that the two object arguments
     * are of the same type, both arrays of both not arrays.
     *
     * @param previous old value of this component (if any)
     * @param value new value of this component (if any)
     */
    protected boolean compareValues(Object previous, Object value) {
        
        // Let super take care of null cases
        //
        if (previous == null || value == null) {
            return super.compareValues(previous, value);
        }
        if (value instanceof Object[]) {
            // If the lengths aren't equal return true
            //
            int length = Array.getLength(value);
            if (Array.getLength(previous) != length) {
                return true;
            }
            // Each element at index "i" in previous must be equal to the
            // elementa at index "i" in value.
            //
            for (int i = 0; i < length; ++i) {
                
                Object newValue = Array.get(value, i);
                Object prevValue = Array.get(previous, i);
                
                // This is probably not necessary since
                // an Option's value cannot be null
                //
                if (newValue == null) {
                    if (prevValue == null) {
                        continue;
                    } else {
                        return true;
                    }
                }
                if (prevValue == null) {
                    return true;
                }
                
                if (!prevValue.equals(newValue)) {
                    return true;
                }
            }
            return false;
        }
        return super.compareValues(previous, value);
    }
    
    public String[] getValueAsStringArray(FacesContext context) {
        
        if(DEBUG) log("getValueAsStringArray)");
        
        Iterator iterator = getListItems(context, false);
        int numItems = listItems.size();
        String[] values = new String[numItems];
        
        int counter = 0;
        while(counter < numItems) {
            values[counter] = ((ListItem)(iterator.next())).getValue();
            ++counter;
        }
        return values;
    }
    
    public boolean mainListSubmits() {
        return true;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * <p>Return the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property aliases.</p>
     *
     * @param name Name of value binding expression to retrieve
     */
    public ValueExpression getValueExpression(String name) {
        if (name.equals("list")) {
            return super.getValueExpression("value");
        }
        return super.getValueExpression(name);
    }

    /**
     * <p>Set the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property
     * aliases.</p>
     *
     * @param name    Name of value binding to set
     * @param binding ValueExpression to set, or null to remove
     */
    public void setValueExpression(String name,ValueExpression binding) {
        if (name.equals("list")) {
            super.setValueExpression("value", binding);
            return;
        }
        super.setValueExpression(name, binding);
    }
    
    // Hide converter
    @Property(name="converter", isHidden=true, isAttribute=false)
    public Converter getConverter() {
        return super.getConverter();
    }
    
    // Hide immediate
    @Property(name="immediate", isHidden=true, isAttribute=false)
    public boolean isImmediate() {
        return _isImmediate();
    }
    
    // Hide value
    @Property(name="value", isHidden=true, isAttribute=false)
    public Object getValue() {
        return super.getValue();
    }

    /**
     * <p>Flag indicating that the user is not permitted to activate this
     * component, and that the component's value will not be submitted with the
     * form.</p>
     */
    @Property(name="disabled", displayName="Disabled", category="Behavior")
    private boolean disabled = false;
    private boolean disabled_set = false;

    /**
     * <p>Flag indicating that the user is not permitted to activate this
     * component, and that the component's value will not be submitted with the
     * form.</p>
     */
    public boolean isDisabled() {
        if (this.disabled_set) {
            return this.disabled;
        }
        ValueExpression _vb = getValueExpression("disabled");
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
     * <p>Flag indicating that the user is not permitted to activate this
     * component, and that the component's value will not be submitted with the
     * form.</p>
     * @see #isDisabled()
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
        this.disabled_set = true;
    }

    /**
     * <p>Text to be used as the label next to the input text field.</p>
     */
    @Property(name="fieldLabel", displayName="Textfield Label", category="Appearance")
    private String fieldLabel = null;

    /**
     * <p>Text to be used as the label next to the input text field.</p>
     */
    public String getFieldLabel() {
        if (this.fieldLabel != null) {
            return this.fieldLabel;
        }
        ValueExpression _vb = getValueExpression("fieldLabel");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Text to be used as the label next to the input text field.</p>
     * @see #getFieldLabel()
     */
    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    /**
     * <p>Sets the style level for the generated labels. Valid values
     * are 1 (largest), 2 and 3 (smallest). The default value is 2.</p>
     */
    @Property(name="labelLevel", displayName="Label Level", category="Appearance")
    private int labelLevel = Integer.MIN_VALUE;
    private boolean labelLevel_set = false;

    /**
     * <p>Sets the style level for the generated labels. Valid values
     * are 1 (largest), 2 and 3 (smallest). The default value is 2.</p>
     */
    public int getLabelLevel() {
        if (this.labelLevel_set) {
            return this.labelLevel;
        }
        ValueExpression _vb = getValueExpression("labelLevel");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return 2;
    }

    /**
     * <p>Sets the style level for the generated labels. Valid values
     * are 1 (largest), 2 and 3 (smallest). The default value is 2.</p>
     * @see #getLabelLevel()
     */
    public void setLabelLevel(int labelLevel) {
        this.labelLevel = labelLevel;
        this.labelLevel_set = true;
    }

    /**
     * <p>
     * The object that represents the list. The list attribute must be an EL
     * expression that evaluates to an object of type<code>java.lang.String[]</code>.</p>
     */
    @Property(name="list", displayName="List", category="Data", editorClassName="com.sun.rave.propertyeditors.binding.ValueBindingPropertyEditor")
    public Object getList() {
        return getValue();
    }

    /**
     * <p>
     * The object that represents the list. The list attribute must be an EL
     * expression that evaluates to an object of type<code>java.lang.String[]</code>.</p>
     * @see #getList()
     */
    public void setList(Object list) {
        setValue(list);
    }

    /**
     * <p>Text to be used as the label next to the list box.</p>
     */
    @Property(name="listLabel", displayName="List Label", category="Appearance")
    private String listLabel = null;

    /**
     * <p>Text to be used as the label next to the list box.</p>
     */
    public String getListLabel() {
        if (this.listLabel != null) {
            return this.listLabel;
        }
        ValueExpression _vb = getValueExpression("listLabel");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Text to be used as the label next to the list box.</p>
     * @see #getListLabel()
     */
    public void setListLabel(String listLabel) {
        this.listLabel = listLabel;
    }

    /**
     * <p>Specifies the display order of the parts of this component. When set to 
     * true, the listOnTop attribute causes the list box to be displayed above 
     * the text input field. By default, the list box is displayed below the input field.</p>
     */
    @Property(name="listOnTop", displayName="Show List On Top", category="Advanced")
    private boolean listOnTop = false;
    private boolean listOnTop_set = false;

    /**
     * <p>Specifies the display order of the parts of this component. When set to 
     * true, the listOnTop attribute causes the list box to be displayed above 
     * the text input field. By default, the list box is displayed below the input field.</p>
     */
    public boolean isListOnTop() {
        if (this.listOnTop_set) {
            return this.listOnTop;
        }
        ValueExpression _vb = getValueExpression("listOnTop");
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
     * <p>Specifies the display order of the parts of this component. When set to 
     * true, the listOnTop attribute causes the list box to be displayed above 
     * the text input field. By default, the list box is displayed below the input field.</p>
     * @see #isListOnTop()
     */
    public void setListOnTop(boolean listOnTop) {
        this.listOnTop = listOnTop;
        this.listOnTop_set = true;
    }

    /**
     * <p>The maximum number of characters allowed for each string in the list.</p>
     */
    @Property(name="maxLength", displayName="Maximum String Length", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int maxLength = Integer.MIN_VALUE;
    private boolean maxLength_set = false;

    /**
     * <p>The maximum number of characters allowed for each string in the list.</p>
     */
    private int _getMaxLength() {
        if (this.maxLength_set) {
            return this.maxLength;
        }
        ValueExpression _vb = getValueExpression("maxLength");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return 25;
    }

    /**
     * <p>The maximum number of characters allowed for each string in the list.</p>
     * @see #getMaxLength()
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        this.maxLength_set = true;
    }

    /**
     * <p>Flag indicating that the application user can select
     * more than one option at a time in the listbox.</p>
     */
    @Property(name="multiple", displayName="Multiple", category="Data")
    private boolean multiple = false;
    private boolean multiple_set = false;

    /**
     * <p>Flag indicating that the application user can select
     * more than one option at a time in the listbox.</p>
     */
    public boolean isMultiple() {
        if (this.multiple_set) {
            return this.multiple;
        }
        ValueExpression _vb = getValueExpression("multiple");
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
     * <p>Flag indicating that the application user can select
     * more than one option at a time in the listbox.</p>
     * @see #isMultiple()
     */
    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
        this.multiple_set = true;
    }

    /**
     * <p>If this attribute is set to true, the value of the component is
     * rendered as text, preceded by the label if one was defined.</p>
     */
    @Property(name="readOnly", displayName="Read-only", category="Behavior")
    private boolean readOnly = false;
    private boolean readOnly_set = false;

    /**
     * <p>If this attribute is set to true, the value of the component is
     * rendered as text, preceded by the label if one was defined.</p>
     */
    public boolean isReadOnly() {
        if (this.readOnly_set) {
            return this.readOnly;
        }
        ValueExpression _vb = getValueExpression("readOnly");
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
     * <p>If this attribute is set to true, the value of the component is
     * rendered as text, preceded by the label if one was defined.</p>
     * @see #isReadOnly()
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        this.readOnly_set = true;
    }

    /**
     * <p>The number of items to display, which determines the length of the 
     * rendered listbox. The default value is 6.</p>
     */
    @Property(name="rows", displayName="Number of Items to Display", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int rows = Integer.MIN_VALUE;
    private boolean rows_set = false;

    /**
     * The number of items to display, which determines the length of the 
     * rendered listbox. The default value is 6.
     * @return The number options to display.
     */
    public int getRows() {
	if (this.rows_set) {
	    return this.rows;
	}
        ValueExpression _vb = getValueExpression("rows");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result != null) {
		try {
		    return ((Integer) _result).intValue();
		} catch (Exception e) {
		    // fall through for default;
		}
	    }
        }
	return getTheme().getMessageInt("editableList.rows", 6);
    }

    /*
     * <p>The number of items to display, which determines the length of the 
     * rendered listbox. The default value is 6.</p>
     * @see #getRows()
     */
    public void setRows(int rows) {
        this.rows = rows;
        this.rows_set = true;
    }


    /**
     * <p>Set sorted to true if the list items should be
     * sorted in locale-specific alphabetical order. The sorting is 
     * performed using a Collator configured
     * with the locale from the FacesContext.</p>
     */
    @Property(name="sorted", displayName="Sorted", category="Advanced")
    private boolean sorted = false;
    private boolean sorted_set = false;

    /**
     * <p>Set sorted to true if the list items should be
     * sorted in locale-specific alphabetical order. The sorting is 
     * performed using a Collator configured
     * with the locale from the FacesContext.</p>
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
     * <p>Set this attribute to true if the list items should be
     * sorted in locale-specific alphabetical order. The sorting is 
     * performed using a Collator configured
     * with the locale from the FacesContext.</p>
     * @see #isSorted()
     */
    public void setSorted(boolean sorted) {
        this.sorted = sorted;
        this.sorted_set = true;
    }

    /**
     * <p>CSS style(s) see Accordion example to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name="style", displayName="CSS Style(s)", category="Appearance", editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    public String getStyle() {
        if (this.style != null) {
            return this.style;
        }
        ValueExpression _vb = getValueExpression("style");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     * @see #getStyle()
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * <p>CSS style class(es) no parents, see Accordion example to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;

    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    public String getStyleClass() {
        if (this.styleClass != null) {
            return this.styleClass;
        }
        ValueExpression _vb = getValueExpression("styleClass");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     */
    @Property(name="tabIndex", displayName="Tab Index", category="Accessibility", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int tabIndex = Integer.MIN_VALUE;
    private boolean tabIndex_set = false;

    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     */
    public int getTabIndex() {
        if (this.tabIndex_set) {
            return this.tabIndex;
        }
        ValueExpression _vb = getValueExpression("tabIndex");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     * @see #getTabIndex()
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
        this.tabIndex_set = true;
    }

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     */
    @Property(name="toolTip", displayName="Tool Tip", category="Behavior")
    private String toolTip = null;

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     */
    public String getToolTip() {
        if (this.toolTip != null) {
            return this.toolTip;
        }
        ValueExpression _vb = getValueExpression("toolTip");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     * @see #getToolTip()
     */
    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    /**
     * <p>Indicates whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for this component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    @Property(name="visible", displayName="Visible", category="Behavior")
    private boolean visible = false;
    private boolean visible_set = false;

    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    public boolean isVisible() {
        if (this.visible_set) {
            return this.visible;
        }
        ValueExpression _vb = getValueExpression("visible");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }

    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     * @see #isVisible()
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }
    
    /**
     * The <code>width</code> property is a value for the CSS <code>width</code>
     * property suitable for the <code>select</code> HTML element.
     */
    @Property(name="width", displayName="Width", category="Appearance")
    private String width;

    /**
     * Return a value suitable for the CSS width property to be applied to 
     * an HTML select element or null.
     * <p>
     * If no value has been set, a default value is determined from
     * the theme property <code>editableList.width</code> defined in the
     * <code>messages</code> theme category. If this theme
     * property is not defined, the width is determined by the
     * longest option element of the rendered select element.
     * </p>
     * @return The value used to determine the width of a select HTML element.
     */
    public String getWidth() {
	if (this.width != null) {
	    return this.width;
	}
        ValueExpression _vb = getValueExpression("style");
        if (_vb != null) {
            String _width = (String)_vb.getValue(
		getFacesContext().getELContext());
	    if (_width != null && _width.trim().length() != 0) {
		return _width;
	    }
        }
	String _width = getTheme().getMessage("editableList.width");
	if (_width != null && _width.trim().length() != 0) {
	    return _width.trim();
	}
	return null;
    }

    /**
     * <code>width</code> is a value for the CSS <code>width</code>
     * property suitable for the <code>select</code> HTML element.
     * As a CSS string property value, <code>width</code>
     * is assumed to contain the units. For example:
     * <p>
     * <ul>
     * <li>20em</li>
     * <li>250px</li>
     * <li>5%</li>
     * </ul>
     * The value is not parsed by <code>EditableList</code>
     * and is intended to be applied directly
     * to the style attribute of the select element.
     * If <code>width</code> is null, <code>EditableList</code> behavior
     * will assume the size of the select element will be based on the
     * length of the longest option in the select element.
     * </p>
     * @see #getWidth()
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * <p>
     * A validator that will be applied to entries made into the
     * textfield. Specify this to be the <code>validate()</code>
     * method of a <code>javax.faces.validator.Validator</code>, or
     * to another method with the same argument structure and
     * exceptions.  </p>
     */
    @Property(name="fieldValidatorExpression", displayName="Field Validator Expression", editorClassName="com.sun.rave.propertyeditors.ValidatorPropertyEditor")
    @Property.Method(signature="void validate(javax.faces.context.FacesContext,javax.faces.component.UIComponent,java.lang.Object)")
    private MethodExpression fieldValidatorExpression;
    
     /**
     * <p>
     * A validator that will be applied to entries made into the
     * textfield. Specify this to be the <code>validate()</code>
     * method of a <code>javax.faces.validator.Validator</code>, or
     * to another method with the same argument structure and
     * exceptions.  </p>
     */
    public MethodExpression getFieldValidatorExpression() {
       return this.fieldValidatorExpression;
    }
    
    /**
     * <p>
     * A validator that will be applied to entries made into the
     * textfield. Specify this to be the <code>validate()</code>
     * method of a <code>javax.faces.validator.Validator</code>, or
     * to another method with the same argument structure and
     * exceptions.  </p>
     * @see #getFieldValidatorExpression()
     */
    public void setFieldValidatorExpression(MethodExpression me) {
	this.fieldValidatorExpression = me;
    }
    
    /**
     * <p>
     * A validator which will be applied to the contents of the list
     * (e.g. to verify that the list has a minimum number of
     * entries). Specify this to be the <code>validate()</code>
     * method of a <code>javax.faces.validator.Validator</code>, or
     * to another method with the same argument structure and
     * exceptions.  </p>
     */
    @Property(name="listValidatorExpression", isHidden=false, displayName="List Validator Expression", editorClassName="com.sun.rave.propertyeditors.ValidatorPropertyEditor")
    @Property.Method(signature="void validate(javax.faces.context.FacesContext,javax.faces.component.UIComponent,java.lang.Object)")
    public MethodExpression getListValidatorExpression() {
        return getValidatorExpression();
    }

    /**
     * <p>
     * A validator which will be applied to the contents of the list
     * (e.g. to verify that the list has a minimum number of
     * entries). Specify this to be the <code>validate()</code>
     * method of a <code>javax.faces.validator.Validator</code>, or
     * to another method with the same argument structure and
     * exceptions.  </p>
     * @see #getListValidatorExpression()
     */
    public void setListValidatorExpression(MethodExpression listValidator) {
        setValidatorExpression(listValidator);
    }

    @Property(name="validatorExpression", isHidden=true, isAttribute=false)
    public MethodExpression getValidatorExpression() {
        return super.getValidatorExpression();
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.disabled = ((Boolean) _values[1]).booleanValue();
        this.disabled_set = ((Boolean) _values[2]).booleanValue();
        this.fieldLabel = (String) _values[3];
        this.labelLevel = ((Integer) _values[4]).intValue();
        this.labelLevel_set = ((Boolean) _values[5]).booleanValue();
        this.listLabel = (String) _values[6];
        this.listOnTop = ((Boolean) _values[7]).booleanValue();
        this.listOnTop_set = ((Boolean) _values[8]).booleanValue();
        this.maxLength = ((Integer) _values[9]).intValue();
        this.maxLength_set = ((Boolean) _values[10]).booleanValue();
        this.multiple = ((Boolean) _values[11]).booleanValue();
        this.multiple_set = ((Boolean) _values[12]).booleanValue();
        this.readOnly = ((Boolean) _values[13]).booleanValue();
        this.readOnly_set = ((Boolean) _values[14]).booleanValue();
        this.rows = ((Integer) _values[15]).intValue();
        this.rows_set = ((Boolean) _values[16]).booleanValue();
        this.sorted = ((Boolean) _values[17]).booleanValue();
        this.sorted_set = ((Boolean) _values[18]).booleanValue();
        this.style = (String) _values[19];
        this.styleClass = (String) _values[20];
        this.tabIndex = ((Integer) _values[21]).intValue();
        this.tabIndex_set = ((Boolean) _values[22]).booleanValue();
        this.toolTip = (String) _values[23];
        this.visible = ((Boolean) _values[24]).booleanValue();
        this.visible_set = ((Boolean) _values[25]).booleanValue();
        this.fieldValidatorExpression = (MethodExpression)restoreAttachedState(_context, _values[26]);
        this.width = (String) _values[27];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[28];
        _values[0] = super.saveState(_context);
        _values[1] = this.disabled ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.disabled_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.fieldLabel;
        _values[4] = new Integer(this.labelLevel);
        _values[5] = this.labelLevel_set ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.listLabel;
        _values[7] = this.listOnTop ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.listOnTop_set ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = new Integer(this.maxLength);
        _values[10] = this.maxLength_set ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.multiple ? Boolean.TRUE : Boolean.FALSE;
        _values[12] = this.multiple_set ? Boolean.TRUE : Boolean.FALSE;
        _values[13] = this.readOnly ? Boolean.TRUE : Boolean.FALSE;
        _values[14] = this.readOnly_set ? Boolean.TRUE : Boolean.FALSE;
        _values[15] = new Integer(this.rows);
        _values[16] = this.rows_set ? Boolean.TRUE : Boolean.FALSE;
        _values[17] = this.sorted ? Boolean.TRUE : Boolean.FALSE;
        _values[18] = this.sorted_set ? Boolean.TRUE : Boolean.FALSE;
        _values[19] = this.style;
        _values[20] = this.styleClass;
        _values[21] = new Integer(this.tabIndex);
        _values[22] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        _values[23] = this.toolTip;
        _values[24] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[25] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[26] = saveAttachedState(_context, fieldValidatorExpression);
        _values[27] = this.width;
        return _values;
    }
}

class AddListener implements ActionListener, Serializable {
    
    public void processAction(ActionEvent event) {
        
        UIComponent comp = event.getComponent();
        comp = comp.getParent();
        if(comp instanceof EditableList) {
            ((EditableList)comp).processAddAction();
        }
    }
}

class RemoveListener implements ActionListener, Serializable {
    
    public void processAction(ActionEvent event) {
        
        UIComponent comp = event.getComponent();
        comp = comp.getParent();
        if(comp instanceof EditableList) {
            ((EditableList)comp).processRemoveAction();
        }
    }   
}
