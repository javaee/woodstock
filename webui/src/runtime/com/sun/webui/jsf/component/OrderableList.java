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
import com.sun.webui.jsf.event.MethodExprValueChangeListener;
import com.sun.webui.jsf.model.list.ListItem;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.ValueType;
import com.sun.webui.jsf.util.ValueTypeEvaluator; 
import com.sun.webui.jsf.validator.MethodExprValidator;

import java.lang.reflect.Array;
import java.util.ArrayList; 
import java.util.Iterator;

import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.event.ValueChangeListener;
import javax.faces.FacesException;
import javax.faces.validator.Validator;

/**
 * The OrderableList component creates a list with buttons allowing the user to 
 * change the order of the list items.
 *
 * <p>This tag renders an OrderableList component. Use this component
 * when web application users need to create and modify a list of
 * strings. The application user can add new strings by typing them
 * into the textfield and clicking the "moveUp" button, and remove them
 * by selecting one or more items from the list and clicking the
 * "Remove" button.</p>
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
 * <li><code>moveUpButton</code>: use this facet to specify a custom
 * component for the moveUp button.</li>
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
@Component(type="com.sun.webui.jsf.OrderableList", family="com.sun.webui.jsf.OrderableList", displayName="Orderable List", tagName="orderableList",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_orderable_list",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_orderable_list_props")
public class OrderableList extends WebuiInput implements ListManager,
	NamingContainer {
    /**
     * The component id for the moveUp button.
     */
    public static final String MOVEUP_BUTTON_ID = "_moveUpButton"; //NOI18N
    /**
     * The facet name for the moveUp button.
     */
    public static final String MOVEUP_BUTTON_FACET = "moveUpButton"; //NOI18N
    /**
     * The move up button text key.
     */
    public static final String MOVEUP_TEXT_KEY = "OrderableList.moveUp"; //NOI8N
    
    /**
     * The component id for the moveDown button.
     */
    public static final String MOVEDOWN_BUTTON_ID = "_moveDownButton"; //NOI18N
    /**
     * The facet name for the moveDown button.
     */
    public static final String MOVEDOWN_BUTTON_FACET = "moveDownButton"; //NOI18N
    /**
     * The move down button text key.
     */
    public static final String MOVEDOWN_TEXT_KEY = "OrderableList.moveDown"; //NOI8N
    
    /**
     * The component id for the moveTop button.
     */
    public static final String MOVETOP_BUTTON_ID = "_moveTopButton"; //NOI18N
    /**
     * The facet name for the moveTop button.
     */
    public static final String MOVETOP_BUTTON_FACET = "moveTopButton"; //NOI18N
    /**
     * The move top button text key.
     */
    public static final String MOVETOP_TEXT_KEY = "OrderableList.moveTop"; //NOI8N
    
    /**
     * The component id for the moveBottom button.
     */
    public static final String MOVEBOTTOM_BUTTON_ID = "_moveBottomButton"; //NOI18N
    /**
     * The facet name for the moveBottom button.
     */
    public static final String MOVEBOTTOM_BUTTON_FACET = "moveBottomButton"; //NOI18N
    /**
     * The move bottom button text key.
     */
    public static final String MOVEBOTTOM_TEXT_KEY = "OrderableList.moveBottom"; //NOI8N

    /**
     * The component ID for the label.
     */
    public static final String LABEL_ID = "_label"; //NOI18N
    /**
     * The facet name for the label.
     */
    public static final String LABEL_FACET = "label"; //NOI18N
    /**
     * The default label text message key.
     */
    public static final String LABEL_TEXT_KEY =
		"OrderableList.defaultListLabel"; //NOI18N
    
    /**
     * The component ID for the read only text field.
     */
    public static final String READ_ONLY_ID = "_readOnly"; //NOI18N
    /**
     * The facet name for the readOnly text field.
     */
    public static final String READ_ONLY_FACET = "readOnly"; //NOI18N

    /** 
     * The name for the footer facet.
     */ 
    public static final String FOOTER_FACET = "footer"; //NOI18N

    /**
     * String representing "return false" printed at the end of the
     * javascript event handlers.
     */
    public static final String RETURN = "return false;";
    
    /**
     * Name of the JavaScript function which moves elements up.
     */
    public static final String MOVEUP_FUNCTION = ".moveUp(); ";
    /**
     * Name of the JavaScript function which moves elements down.
     */
    public static final String MOVEDOWN_FUNCTION = ".moveDown();";      
    /**
     * Name of the JavaScript function which moves elements to the top.
     */
    public static final String MOVETOP_FUNCTION = ".moveTop(); ";
    /**
     * Name of the JavaScript function which moves elements to the bottom.
     */
    public static final String MOVEBOTTOM_FUNCTION = ".moveBottom();";          
    /**
     * Name of the JavaScript function that updates the buttons.
     */
    public static final String UPDATEBUTTONS_FUNCTION = ".updateButtons(); ";
    /**
     * Name of the JavaScript function that handles changes on the
     * available list.
     */
    public static final String ONCHANGE_FUNCTION = ".onChange(); ";

    // FIXME: Should be in Theme
    //
    /**
     * Read only separator string
     */
    private static final String READ_ONLY_SEPARATOR = ", "; //NOI18N

    // Holds the ValueType of this component
    private ValueTypeEvaluator valueTypeEvaluator = null; 
    private ArrayList listItems = null; 
    private transient Theme theme = null; 
  
    private static final boolean DEBUG = false; 

    /**Default constructor.<p>Construct a new <code>OrderableListBase</code>.</p>
     */
    public OrderableList() {
        super();
        valueTypeEvaluator = new ValueTypeEvaluator(this);
        setRendererType("com.sun.webui.jsf.OrderableList");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.OrderableList";
    }

    // Buttons
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
    public UIComponent getMoveUpButtonComponent(FacesContext context) { 
	if(DEBUG) log("getMoveUpButtonComponent()"); //NOI18N

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
    public UIComponent getMoveDownButtonComponent(FacesContext context) { 
	if(DEBUG) log("getMoveDownButtonComponent()"); //NOI18N

	return getButtonFacet(MOVEDOWN_BUTTON_FACET, false,
            getTheme().getMessage(MOVEDOWN_TEXT_KEY), MOVEDOWN_FUNCTION);
    } 

    /**
     * Return a component that implements the move to top button.
     * If a facet named <code>moveToTop</code> is found
     * that component is returned. Otherwise a <code>Button</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_moveToTop"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Button</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a move to top button component
     */
    public UIComponent getMoveTopButtonComponent(FacesContext context) {
	if(DEBUG) log("getMoveTopButtonComponent()");  //NOI18N

	return getButtonFacet(MOVETOP_BUTTON_FACET, false,
            getTheme().getMessage(MOVETOP_TEXT_KEY), MOVETOP_FUNCTION);
    } 

    /**
     * Return a component that implements the move to bottom button.
     * If a facet named <code>moveToBottom</code> is found
     * that component is returned. Otherwise a <code>Button</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_moveToBottom"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Button</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a move to bottom button component
     */
    public UIComponent getMoveBottomButtonComponent(FacesContext context) { 
	if(DEBUG) log("getMoveBottomButtonComponent()"); //NOI18N

	return getButtonFacet(MOVEBOTTOM_BUTTON_FACET, false,
            getTheme().getMessage(MOVEBOTTOM_TEXT_KEY), MOVEBOTTOM_FUNCTION);
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
	    if (DEBUG) log("create Button"); 
	    button = new Button(); 
	    button.setId(ComponentUtilities.createPrivateFacetId(this,
		facetName));
	}

	initButtonFacet(button, primary, text, onclickFunction);
	ComponentUtilities.putPrivateFacet(this, facetName, button);

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
        buff.append("document.getElementById('")
            .append(getClientId(getFacesContext()))
            .append("')")
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
     * Return a component that implements the header label.
     * If a facet named <code>label</code> is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_label"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a header label component
     */
    public UIComponent getHeaderComponent() { 
	if(DEBUG) log("getHeaderComponent()"); //NOI18N

	String labelString = getLabel(); 
	if(labelString == null || labelString.length() == 0) { 
	    labelString = getTheme().getMessage(LABEL_TEXT_KEY);
	}
	return getLabelFacet(LABEL_FACET, labelString, this);
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
     * @param facetName the name of the facet to return or create
     * @param text the label text
     * @param forComponent the component instance this label is for
     *
     * @return a label facet component
     */
    private UIComponent getLabelFacet(String facetName, String text,
		UIComponent forComponent) {

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
	    if (DEBUG) log("create Label"); //NOI18N
	    label = new Label(); 
	    label.setId(ComponentUtilities.createPrivateFacetId(this,
		facetName));
	}
        if(text == null || text.length() < 1) {
            // TODO - maybe print a default?
	    // A Theme default value.
            text = new String();
        }

        label.setText(text);
        label.setLabelLevel(getLabelLevel());
	label.setLabeledComponent(this);
	label.setIndicatorComponent(this);

	ComponentUtilities.putPrivateFacet(this, facetName, label);

	return label; 
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
        return getLabeledElementId(context);
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

        // If this component has a label either as a facet or
        // an attribute, return the id of the input element
        // that will have the "LIST_ID" suffix. IF there is no
        // label, then the input element id will be the component's
        // client id.
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
        }
        return getClientId(context).concat(ListSelector.LIST_ID);
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
     *
     * @param context The FacesContext used for the request
     */
    public String getFocusElementId(FacesContext context) {
	// This should be the id of the selected Option in the
	// select list.
	// For now return the same as for the label.
	//
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
     * Retrieve an Iterator of ListSelector.ListItem, to be used by the
     * renderer. 
     * @return an Iterator over {@link ListItem}.
     * @throws javax.faces.FacesException 
     */
    public Iterator getListItems(FacesContext context, boolean ruler) throws FacesException {
        
        if(DEBUG) log("getListItems()");
        
	listItems = new ArrayList(); 

        Object submittedValue = getSubmittedValue(); 
        if(submittedValue != null && submittedValue instanceof String[]) { 
            ListItem listItem = null; 
            String[] values = (String[])submittedValue; 
            for(int counter=0; counter < values.length; ++counter) {
                if(DEBUG) log("Adding listItem " + values[counter]); 
                listItem = new ListItem(values[counter], values[counter]);
                listItem.setValue(values[counter]);               
                listItems.add(listItem);
            }
            return listItems.iterator();           
        } 
             
	Object listItemsObject = getList(); 
	if(listItemsObject == null) { 
            if(DEBUG) log("\tNo list items!");
	    // do nothing...
	} 
        else if(valueTypeEvaluator.getValueType() == ValueType.LIST) {
	    Iterator items = ((java.util.List)listItemsObject).iterator(); 
	    Object item;
            ListItem listItem; 
	    while(items.hasNext()) { 
		item = items.next(); 
                listItems.add(createListItem(this, item)); 
	    } 
	} 
	
	 else if(valueTypeEvaluator.getValueType() == ValueType.ARRAY) {
            
            if(DEBUG) log("\tFound array value"); 

	    // The strings variable represents the strings entered by
	    // the user, as an array. 
            
            
 	    Object[] listObjects = (Object[])listItemsObject; 
            
            for(int counter=0; counter<listObjects.length; ++counter) {
                listItems.add(createListItem(this, listObjects[counter])); 
            } 
        }

	else { 
	    String msg = getTheme().getMessage("OrderableList.invalidListType"); //NOI18N 
	    throw new FacesException(msg); 
	} 
            
	return listItems.iterator(); 
    } 
    
    /**
     * Enforce non null values.
     * This is ok, since Converter returns null on null input.
     * And secondly this is equivalent to SelectItem and therefore
     * Option which do not allow null values.
     *
     * However we have to be wary of values that are "".
     * But if the null case is out of the way the this should
     * work ok.
     */
    protected ListItem createListItem(UIComponent comp, Object value) { 
	// Do not allow null values
	//
	if (value == null) {
	    throw new NullPointerException(
	    	"OrderableList ListItems cannot have null values");
	}
        if(DEBUG) log("createListItem()"); 
        String label = ConversionUtilities.convertValueToString(comp, value);
        if(DEBUG) log("\tLabel is " + label); 
        ListItem listItem = new ListItem(value, label); 
        if(DEBUG) log("\tCreated ListItem"); 
        listItem.setValue(label);  
        return listItem; 
    }
     
    public String[] getValueAsStringArray(FacesContext context) {
        
        if(DEBUG) log("getValueAsStringArray)");
        
        Iterator iterator = getListItems(context, false);
        int numItems = listItems.size();
        String[] values = new String[numItems];
        
        int counter = 0;
        while(counter < numItems) {
            values[counter] = ((ListItem)(iterator.next())).getValue();
            if(DEBUG) log("List item value " + String.valueOf(values[counter]));
            ++counter;
        }
        return values;
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

        Object cValue = null; 
        try { 
            
	    if(valueTypeEvaluator.getValueType() == ValueType.ARRAY) { 
		if(DEBUG) log("\tComponent value is an array"); 
		cValue = ConversionUtilities.convertValueToArray
		    (this, rawValues, context); 
	    } 
	    // This case is not supported yet!
	    else if(valueTypeEvaluator.getValueType() == ValueType.LIST) { 
		if(DEBUG) log("\tComponent value is a list"); 
		/* Until this is fixed throw exception saying it is 
		   unsupported
		cValue = ConversionUtilities.convertValueToList
		    (this, rawValues, context); 
		*/
		throw new FacesException("List is not a supported value.");
	    } 
        } 
        catch(Exception ex) {
            if(DEBUG) ex.printStackTrace(); 
        }
        return cValue;
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

    /**
     * Return a component that implements the read only value of this 
     * OrderableList.
     * If a facet named <code>readOnly</code> is found
     * that component is returned. Otherwise a <code>StaticText</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_readOnly"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>StaticText</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a component that represents the read only value of this OrderableList
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
    
    private void log(String s) {
        System.out.println(this.getClass().getName() + "::" + s); //NOI18N
    }
    
    private Theme getTheme() {
	return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }


    public String getOnChange() {
        return null;
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

    public boolean mainListSubmits() {
        return false;
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
    
    // Hide value
    @Property(name="value", isHidden=true, isAttribute=false)
    public Object getValue() {
        return super.getValue();
    }

    /**
     * <p>Flag indicating that activation of this component by the user is not currently permitted.</p>
     */
    @Property(name="disabled", displayName="Disabled", category="Behavior")
    private boolean disabled = false;
    private boolean disabled_set = false;

    /**
     * <p>Flag indicating that activation of this component by the user is not currently permitted.</p>
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
     * <p>Flag indicating that activation of this component by the user is not currently permitted.</p>
     * @see #isDisabled()
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
        this.disabled_set = true;
    }

    /**
     * <p>If set, a label is rendered adjacent to the component with the
     * value of this attribute as the label text.</p>
     */
    @Property(name="label", displayName="List Label", category="Appearance")
    private String label = null;

    /**
     * <p>If set, a label is rendered adjacent to the component with the
     * value of this attribute as the label text.</p>
     */
    public String getLabel() {
        if (this.label != null) {
            return this.label;
        }
        ValueExpression _vb = getValueExpression("label");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>If set, a label is rendered adjacent to the component with the
     * value of this attribute as the label text.</p>
     * @see #getLabel()
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * <p>Sets the style level for the generated labels. Valid values
     * 	are 1 (largest), 2 and 3 (smallest). The default value is 2.</p>
     */
    @Property(name="labelLevel", displayName="Label Level", category="Appearance")
    private int labelLevel = Integer.MIN_VALUE;
    private boolean labelLevel_set = false;

    /**
     * <p>Sets the style level for the generated labels. Valid values
     * 	are 1 (largest), 2 and 3 (smallest). The default value is 2.</p>
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
     * 	are 1 (largest), 2 and 3 (smallest). The default value is 2.</p>
     * @see #getLabelLevel()
     */
    public void setLabelLevel(int labelLevel) {
        this.labelLevel = labelLevel;
        this.labelLevel_set = true;
    }

    /**
     * <p>If this attribute is true, the label is rendered above the
     * component. If it is false, the label is rendered next to the
     * component. The default is false.</p>
     */
    @Property(name="labelOnTop", displayName="Label on Top", category="Appearance")
    private boolean labelOnTop = false;
    private boolean labelOnTop_set = false;

    /**
     * <p>If this attribute is true, the label is rendered above the
     * component. If it is false, the label is rendered next to the
     * component. The default is false.</p>
     */
    public boolean isLabelOnTop() {
        if (this.labelOnTop_set) {
            return this.labelOnTop;
        }
        ValueExpression _vb = getValueExpression("labelOnTop");
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
     * <p>If this attribute is true, the label is rendered above the
     * component. If it is false, the label is rendered next to the
     * component. The default is false.</p>
     * @see #isLabelOnTop()
     */
    public void setLabelOnTop(boolean labelOnTop) {
        this.labelOnTop = labelOnTop;
        this.labelOnTop_set = true;
    }

    /**
     * <p>
     * The object that represents the list of items. The attribute's value must 
     * be a JavaServer Faces EL expression that evaluates to an array of 
     * Objects or to a <code>java.util.List</code>.</p>
     */
    @Property(name="list", displayName="List", category="Data", editorClassName="com.sun.rave.propertyeditors.binding.ValueBindingPropertyEditor")
    public Object getList() {
        return getValue();
    }

    /**
     * <p>
     * The object that represents the list of items. The attribute's value must 
     * be a JavaServer Faces EL expression that evaluates to an array of 
     * Objects or to a <code>java.util.List</code>.</p>
     * @see #getList()
     */
    public void setList(Object list) {
        setValue(list);
    }

    /**
     * <p>If this attribute is true, the Move to Top and Move to Bottom
     * buttons are shown. The default is false.</p>
     */
    @Property(name="moveTopBottom", displayName="Move Top and Bottom", category="Appearance")
    private boolean moveTopBottom = false;
    private boolean moveTopBottom_set = false;

    /**
     * <p>If this attribute is true, the Move to Top and Move to Bottom
     * buttons are shown. The default is false.</p>
     */
    public boolean isMoveTopBottom() {
        if (this.moveTopBottom_set) {
            return this.moveTopBottom;
        }
        ValueExpression _vb = getValueExpression("moveTopBottom");
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
     * <p>If this attribute is true, the Move to Top and Move to Bottom
     * buttons are shown. The default is false.</p>
     * @see #isMoveTopBottom()
     */
    public void setMoveTopBottom(boolean moveTopBottom) {
        this.moveTopBottom = moveTopBottom;
        this.moveTopBottom_set = true;
    }

    /**
     * <p>Flag indicating that the application user can make select
     * 	more than one option from the listbox.</p>
     */
    @Property(name="multiple", displayName="Multiple", category="Data")
    private boolean multiple = false;
    private boolean multiple_set = false;

    /**
     * <p>Flag indicating that the application user can make select
     * 	more than one option from the listbox.</p>
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
     * <p>Flag indicating that the application user can make select
     * 	more than one option from the listbox.</p>
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
     * <p>The number of rows to display, which determines the length of the 
     * rendered listbox. The default value is 6.</p>
     */
    @Property(name="rows", displayName="Number of Items to Display", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int rows = Integer.MIN_VALUE;
    private boolean rows_set = false;

    /**
     * <p>The number of rows to display, which determines the length of the 
     * rendered listbox. The default value is 12.</p>
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
		    // fall through for default
		}
            }
        }
	return getTheme().getMessageInt("orderableList.rows", 12);
    }

    /**
     * <p>The number of rows to display, which determines the length of the 
     * rendered listbox. The default value is 12.</p>
     * @see #getRows()
     */
    public void setRows(int rows) {
        this.rows = rows;
        this.rows_set = true;
    }

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
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
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
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
     * <p>Position of this element in the tabbing order for the current
     * document. The tabbing order determines the sequence in which
     * elements receive focus when the tab key is pressed. The tabIndex
     * value must be an integer between 0 and 32767.</p>
     */
    @Property(name="tabIndex", displayName="Tab Index", category="Accessibility", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int tabIndex = Integer.MIN_VALUE;
    private boolean tabIndex_set = false;

    /**
     * <p>Position of this element in the tabbing order for the current
     * document. The tabbing order determines the sequence in which
     * elements receive focus when the tab key is pressed. The tabIndex
     * value must be an integer between 0 and 32767.</p>
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
     * <p>Position of this element in the tabbing order for the current
     * document. The tabbing order determines the sequence in which
     * elements receive focus when the tab key is pressed. The tabIndex
     * value must be an integer between 0 and 32767.</p>
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
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
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
     * the theme property <code>orderableList.width</code> defined in the
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
        ValueExpression _vb = getValueExpression("width");
        if (_vb != null) {
	    String _width = (String)_vb.getValue(
		getFacesContext().getELContext());
	    if (_width != null && _width.trim().length() != 0) {
		return _width.trim();
	    }
        }
	String _width = getTheme().getMessage("orderableList.width");
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
     * The value is not parsed by <code>OrderableList</code>
     * and is intended to be applied directly
     * to the style attribute of the select element.
     * If <code>width</code> is null, <code>OrderableList</code> behavior
     * will assume the size of the select element will be based on the
     * length of the longest option in the select element.
     * </p>
     * @see #getWidth()
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.disabled = ((Boolean) _values[1]).booleanValue();
        this.disabled_set = ((Boolean) _values[2]).booleanValue();
        this.label = (String) _values[3];
        this.labelLevel = ((Integer) _values[4]).intValue();
        this.labelLevel_set = ((Boolean) _values[5]).booleanValue();
        this.labelOnTop = ((Boolean) _values[6]).booleanValue();
        this.labelOnTop_set = ((Boolean) _values[7]).booleanValue();
        this.moveTopBottom = ((Boolean) _values[8]).booleanValue();
        this.moveTopBottom_set = ((Boolean) _values[9]).booleanValue();
        this.multiple = ((Boolean) _values[10]).booleanValue();
        this.multiple_set = ((Boolean) _values[11]).booleanValue();
        this.readOnly = ((Boolean) _values[12]).booleanValue();
        this.readOnly_set = ((Boolean) _values[13]).booleanValue();
        this.rows = ((Integer) _values[14]).intValue();
        this.rows_set = ((Boolean) _values[15]).booleanValue();
        this.style = (String) _values[16];
        this.styleClass = (String) _values[17];
        this.tabIndex = ((Integer) _values[18]).intValue();
        this.tabIndex_set = ((Boolean) _values[19]).booleanValue();
        this.toolTip = (String) _values[20];
        this.visible = ((Boolean) _values[21]).booleanValue();
        this.visible_set = ((Boolean) _values[22]).booleanValue();
        this.width = (String) _values[23];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[24];
        _values[0] = super.saveState(_context);
        _values[1] = this.disabled ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.disabled_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.label;
        _values[4] = new Integer(this.labelLevel);
        _values[5] = this.labelLevel_set ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.labelOnTop ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.labelOnTop_set ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.moveTopBottom ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = this.moveTopBottom_set ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = this.multiple ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.multiple_set ? Boolean.TRUE : Boolean.FALSE;
        _values[12] = this.readOnly ? Boolean.TRUE : Boolean.FALSE;
        _values[13] = this.readOnly_set ? Boolean.TRUE : Boolean.FALSE;
        _values[14] = new Integer(this.rows);
        _values[15] = this.rows_set ? Boolean.TRUE : Boolean.FALSE;
        _values[16] = this.style;
        _values[17] = this.styleClass;
        _values[18] = new Integer(this.tabIndex);
        _values[19] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        _values[20] = this.toolTip;
        _values[21] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[22] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[23] = this.width;
        return _values;
    }
}
