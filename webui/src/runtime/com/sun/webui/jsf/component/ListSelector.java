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
package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.OptionGroup;
import com.sun.webui.jsf.model.OptionTitle;
import com.sun.webui.jsf.model.Separator;
import com.sun.webui.jsf.model.list.EndGroup; 
import com.sun.webui.jsf.model.list.ListItem;
import com.sun.webui.jsf.model.list.StartGroup; 
import com.sun.webui.jsf.util.ComponentUtilities; 
import com.sun.webui.jsf.util.ConversionUtilities; 
import com.sun.webui.jsf.util.MessageUtil;
import com.sun.webui.jsf.util.ValueType; 
import com.sun.webui.jsf.util.ThemeUtilities;

import java.beans.Beans; 
import java.lang.reflect.Array;
import java.util.ArrayList; 
import java.util.Collection;
import java.util.Iterator; 
import java.util.Map;
import java.util.Properties;

import javax.el.ValueExpression;
import javax.faces.FacesException; 
import javax.faces.application.FacesMessage;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

/** 
 * Base component for UI components that allow the user to make a
 * selection from a list of options using an HTML select element.
 */
public class ListSelector extends Selector implements ListManager,
        NamingContainer {

    /**
     * The name of the label facet.
     */
    public static final String LABEL_FACET = "label"; //NOI18N

    // If true, debugging statements are printed to stdout
    private static final boolean DEBUG = false;

    // Holds the options for this component
    protected ArrayList listItems = null; 
    private int separatorLength = 0; 
      
    private static final String READONLY_ID = "_readOnly"; //NOI18N
    private static final String LABEL_ID = "_label"; //NOI18N
    private static final String READONLY_FACET = "readOnly"; //NOI18N


    public static final String VALUE_ID = "_list_value";        //NOI18N
    public static final String VALUE_LABEL_ID = "_hiddenlabel"; //NOI18N
    public static final String LIST_ID = "_list";               //NOI18N

    /** Creates a new instance of ListSelector */
    public ListSelector() {
        setRendererType("com.sun.webui.jsf.ListSelectorRenderer");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.ListSelector";
    }
  
    /**
     * Check that this component has a valuebinding that matches the
     * value of the "multiple" attribute. 
     * @param context The FacesContext of the request
     */
    public void checkSelectionModel(FacesContext context) {
        
        if(DEBUG) { 
            log("checkSelectionModel()"); //NOI18N
            log("\tComponent multiple = " + String.valueOf(isMultiple())); //NOI18N
            log("\tValueType " + valueTypeEvaluator.getValueType().toString()); //NOI18N
        }
             
        if(isMultiple() && 
           valueTypeEvaluator.getValueType() != ValueType.ARRAY) {
         
            if(DEBUG) {
                log("\tMultiple selection enabled for non-array value");//NOI18N
            }
            Object[] params = {  toString() }; 
            String msg = MessageUtil.getMessage
                    ("com.sun.webui.jsf.resources.LogMessages", //NOI18N
                     "Selector.multipleError",           //NOI18N
                     params); 
            throw new RuntimeException(msg);
        }
        return;
    } 

    /**
     * Retrieve an Iterator of ListSelector.ListItem, to be used by the
     * renderer. 
     * @return an Iterator over {@link ListItem}. 
     */
    public Iterator getListItems(FacesContext context, boolean rulerAtEnd) 
        throws FacesException {
        
        if(DEBUG) log("getListItems()"); //NOI18N


        listItems = new ArrayList();
        separatorLength = 0; 

        // Retrieve the current selections. If there are selected
        // objects, mark the corresponding items as selected. 
        processOptions(getOptions());
        
        processSelections(); 
        
        return listItems.iterator(); 
    } 
    
    /**
     * Retrieve an Iterator of ListSelector.ListItem, to be used when 
     * evaluting the list items. If the list items are needed by the 
     * renderer, use getListItems(context, rulerAtEnd) instead.
     * @return an Iterator over {@link ListItem}. 
     */
    public Iterator getListItems() throws FacesException {
        
        if(DEBUG) log("getListItems()"); //NOI18N
        if(listItems != null) { 
            return listItems.iterator();
        } 
       
        listItems = new ArrayList();
        processOptions(getOptions());
        return listItems.iterator();
    } 

    /**
     * This method resets the options. Use this only if you need to
     * add or remove options after the component has been rendered once.
    public void resetOptions() { 
        listItems = null; 
    } 
     */

    public int getSeparatorLength() {
        return separatorLength;
    }

    // All separator length functionality should be the responsibility
    // of the renderer.
    /**
     * Set the separator length only if length is longer than
     * the current separatorLength. This implementation uses
     * "1.5" as an offset for some reason. It may be because of the
     * original "-" character used for the separator length. Now that
     * an "en" dash is used, this added offset may not be necessary.
     */
    protected void setSeparatorLength(int length) {
	// "1.5" is an arbitrary offset estimate to compensate for the
	// character used for the separator.
	//
	int newvalue = (int)(length * 1.5);
	if (newvalue > separatorLength) {
	    separatorLength = newvalue;
	}
    }

    /** 
     * Processes the component's SelectItems. Constructs an ArrayList
     * of Selector.Options.
     *
     * <ul>
     * <li>General algorithm copied from the RI, except that I modified
     * the class casts for readability. I don't think the algorithm is 
     * correct though, need to verify. </li> 
     * <li>The list of allowed data types must match the spec. </li>
     * <li>This code will have to be replaced when switching
     * to Selection.</li>
     * </ul> 
     */
    protected Option[] getOptions() { 

        Option[] options = null; 
        Object optionsObject = getItems(); 

        // TODO - add some error reporting... 

        if(optionsObject instanceof Option[]) { 
            options = (Option[])optionsObject;
        } 
        else if(optionsObject instanceof Collection) { 
            Object[] objects = ((Collection)optionsObject).toArray(); 
            if(objects == null || objects.length == 0) {
                options = new Option[0];
            }
            
            int numObjects = objects.length;
            options = new Option[numObjects]; 
            for(int counter = 0; counter < numObjects; ++counter) { 
                options[counter] = (Option)objects[counter];
            }
        } 
        else if(optionsObject instanceof Map) { 
            Collection itemsCollection = ((Map)optionsObject).values(); 
            options = (Option[])itemsCollection.toArray(new Option[itemsCollection.size()]); 
        } 
        // The items attribute has not been specified
        else { 
            // do nothing
            options =  new Option[0]; 
        } 
        return options;
    } 

    protected void processOptions(Option[] options) { 

        if(DEBUG) log("processOptions()");  //NOI18N
        int length = options.length; 
        
        for (int counter = 0; counter < length; ++counter) {

            if(options[counter] instanceof OptionGroup) {

                OptionGroup selectionGroup = 
                    (OptionGroup)options[counter]; 
                String groupLabel = selectionGroup.getLabel(); 
                if (groupLabel == null) {
                    groupLabel = ""; //NOI18N
                }
		// Perform the heuristic in the setSeparatorLength
		// method to hide the 1.5. Hopefully we can get
		// rid of it.
		//
		setSeparatorLength(groupLabel.length());

                listItems.add(new StartGroup(groupLabel)); 
                processOptions(selectionGroup.getOptions());
                listItems.add(new EndGroup()); 
            } 
            else if(options[counter] instanceof Separator) {
                listItems.add(options[counter]); 
            }
            else {
                listItems.add(createListItem(options[counter]));
            }
        }
    }

    /**
     * Retrieve the current selections and compare them with the list
     * items. 
     */
    protected void processSelections() { 
        
        if(DEBUG) log("processSelections()"); //NOI18N

        // For the "immediate" case: 
        Object value = getSubmittedValue();

        if(value != null) { 

            if(DEBUG) log("Found submitted value");  //NOI18N

            if(value instanceof String[]) {

                if(DEBUG) log("found submitted value (string array)"); //NOI18N

                String[] obj = (String[])value;
                ArrayList list = new ArrayList(obj.length);
                for(int counter =0; counter < obj.length; ++counter) { 
                    list.add(obj[counter]); 
                    if(DEBUG) log("\tAdded " + obj[counter]); //NOI18N
                } 
                markSelectedListItems(list, false); 
                return;
            }

            throw new IllegalArgumentException
                ("Illegal submitted value"); //NOI18N
        }
        
        // For the first time and "non-immediate" case: 
        if(DEBUG) log("No submitted values, use actual value");//NOI18N

        // Covers List cases
        if(valueTypeEvaluator.getValueType() == ValueType.NONE || 
           valueTypeEvaluator.getValueType() == ValueType.INVALID) { 
            if(DEBUG) log("\tNo value");
            markSelectedListItems(new ArrayList(), true); 
            return; 
        }

        value = getValue();
        
        if(DEBUG) {
            if(value == null) log("\t actual value is null"); //NOI18N
            else log("\t actual value is of type " +          //NOI18N
                    value.getClass().getName());
        }
        if(value == null) { 
            if(DEBUG) log("\tNo value");//NOI18N
            markSelectedListItems(new ArrayList(), true); 
            return; 
        } 

        // Covers List cases
        /*
        if(valueTypeEvaluator.getValueType() == ValueType.LIST) { 
            if(DEBUG) log("found actual value (list)");

            Object[] params = {  toString() }; 
            String msg =
                ThemeUtilities.getTheme(FacesContext.getCurrentInstance()).
                    getMessage("ListSelector.multipleError", params); //NOI18N
            throw new IllegalArgumentException(msg);

            //markSelectedOptions((java.util.List)value, true); 
            //return; 
        }
         */

        ArrayList list = new ArrayList(); 

        // Covers Object array
        if(valueTypeEvaluator.getValueType() == ValueType.ARRAY) { 

            int length = Array.getLength(value); 
            for(int counter = 0; counter < length; ++counter) { 
                list.add(Array.get(value, counter)); 
                if(DEBUG) log(String.valueOf(Array.get(value, counter))); 
            } 
            markSelectedListItems(list, true); 
            return; 
        }

        // Covers Object array
        list.add(value); 
        if(DEBUG) log("\tAdded object " + String.valueOf(value));  //NOI18N
        markSelectedListItems(list, true); 
        return; 
    }

    /** 
     * Marks options corresponding to objects listed as values of this
     * components as selected.
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

        if(DEBUG) log("markSelectedListItems()");//NOI18N
        
        ListItem option = null; 
        Object nextItem = null;
        Iterator items = listItems.iterator(); 
        Iterator selected = null; 
       
        while(items.hasNext()) { 
            nextItem = items.next(); 
            // If the next item is a selection group, we continue. 
            // Need to check this with the guidelines, perhaps
            // you can select options too... 
            if(!(nextItem instanceof ListItem)) { 
                  continue;
            }
            
            option = (ListItem)nextItem; 

            // By default, the option will not be marked as selected
            option.setSelected(false); 
            
            if(DEBUG) { 
                log("\tItem value: " + option.getValue()); //NOI18N
                log("\tItem type: " +                      //NOI18N
                    option.getValueObject().getClass().getName());
            }

            // There are no more selected items, continue with the
            // next option
            if(list.isEmpty()) { 
                if(DEBUG) log("No more selected items"); //NOI18N
                continue; 
            } 

            // There are still selected items to account for
            selected = list.iterator();
            while(selected.hasNext()) { 
                if(processed) { 
                    Object o = selected.next(); 
                    if(DEBUG) {
                        log("\tSelected object value: " +  //NOI18N
                            String.valueOf(o));
                        log("\tSelected object type: " +   //NOI18N 
                            o.getClass().getName());
                    }
                    Object optionValueObject = option.getValueObject();
                    if (o.equals(optionValueObject) || 
                            ("".equals(o) && optionValueObject == null)) {  //NOI18N 
                        if(DEBUG) { 
                            log("\tFound a match: " +  //NOI18N
                                String.valueOf(o)); 
                        } 
                        option.setSelected(true); 
                        list.remove(o); 
                        break;
                    }
                }
                else { 
                    String s = (String)selected.next(); 
                    if(s.equals(option.getValue())) { 
                        if(DEBUG) { 
                            log("\tFound a match: " + s);   //NOI18N
                        } 
                        option.setSelected(true); 
                        list.remove(s); 
                        break;
                    }
                }
            }
        }

        // At this point the selected list should be empty.
        if(!list.isEmpty() && !Beans.isDesignTime()) { 
            String msg = MessageUtil.getMessage(
                    "com.sun.webui.jsf.resources.LogMessages", //NOI18N
                    "List.badValue", 
                new Object[]{ getClientId(FacesContext.getCurrentInstance()) });
            //throw new FacesException(msg); 
            log(msg); 
        }
    }
    
    /* Add an option to the list */ 
    protected ListItem createListItem(Option si) {
        
        if(DEBUG) log("createListItem()");//NOI18N
        
        String label = si.getLabel(); 
        
         String valueString = 
            ConversionUtilities.convertValueToString(this, si.getValue());
         
         if(label == null)
             label = valueString;
         
        if(DEBUG) log("Label is " + label); 

	// Perform the heuristic in the setSeparatorLength
	// method to hide the 1.5. Hopefully we can get 
	// rid of it.
	//
	setSeparatorLength(label.length());

        ListItem listItem = new ListItem(si.getValue(), label,
	    si.getDescription(), si.isDisabled(), si.isEscape());

        listItem.setValue(valueString); 
        if(si instanceof OptionTitle) { 
            listItem.setTitle(true);
        }
        return listItem;
        
    }
    
    //Labels
    /**
     * Return a component that implements the label for this ListSelector.
     * If a facet named <code>label</code> is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_label"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a label component for this ListSelector
     */
    public UIComponent getLabelComponent() { 
        
        if(DEBUG) log("getLabelComponent()"); //NOI18N

        // Check if the page author has defined the facet
        //
        UIComponent labelComponent = getFacet(LABEL_FACET); 
        if (labelComponent != null) {
            if (DEBUG) { 
                log("\tFound facet"); //NOI18N
            } 
            return labelComponent;
        }
	// We need to allow an empty string label since this  
	// could mean that there is value binding and a   
	// message bundle hasn't loaded yet, but there  
	// is a value binding since the javax.el never returns  
	// null for a String binding.  
	//   
	String labelString = getLabel(); 
	if (labelString == null /*|| labelString.length() == 0*/) {  
            return null;
        } 

        // Return the private facet or create one, but initialize
        // it every time
        //
        // We know it's a Label
        //
        Label label = (Label)ComponentUtilities.getPrivateFacet(this,
                LABEL_FACET, true);
        if (label == null) {
            if (DEBUG) log("create Label"); //NOI18N
            label = new Label(); 
            label.setId(ComponentUtilities.createPrivateFacetId(this,
                LABEL_FACET));
        }

        if(labelString == null || labelString.length() < 1) {
            // TODO - maybe print a default?
            // A Theme default value.
            labelString = new String();
        }

        label.setText(labelString);
        label.setLabelLevel(getLabelLevel());
	label.setLabeledComponent(this);
	label.setIndicatorComponent(this);

        ComponentUtilities.putPrivateFacet(this, LABEL_FACET, label);

        return label; 
    }
    
    /**
     * Return a component that implements the read only value of this 
     * ListSelector.
     * If a facet named <code>readOnly</code> is found
     * that component is returned. Otherwise a <code>StaticText</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_readOnly"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>StaticText</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a component that represents the read only value of this ListSelector
     */
    public UIComponent getReadOnlyValueComponent() {
        
        if(DEBUG) log("getReadOnlyValueComponent()"); //NOI18N
        
        // Check if the page author has defined the facet
        //
        UIComponent textComponent = getFacet(READONLY_FACET); 
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
                READONLY_FACET));
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

    /**
     * Get the value (the object representing the selection(s)) of this 
     * component as a String array.
     *
     * @param context The FacesContext of the request
     */
    public String[] getValueAsStringArray(FacesContext context) {

        String[] values = null; 
        
        Object value = getSubmittedValue();    
        if(value != null) {
            if(value instanceof String[]) {
               return (String[])value;
            } else if(value instanceof String) {
                values = new String[1];
                values[0] = (String)value;
                return values;
            }
        }
        
        value = getValue();
        if(value == null) { 
            return new String[0];
        }
        
        // No submitted value found - look for 

        if(valueTypeEvaluator.getValueType() == ValueType.NONE) { 
            return new String[0]; 
        } 
        
        if(valueTypeEvaluator.getValueType() == ValueType.INVALID) { 
            return new String[0]; 
        } 
        
        int counter = 0; 
        
        if(valueTypeEvaluator.getValueType() == ValueType.LIST) { 

            java.util.List list = (java.util.List)value; 
            counter = list.size(); 
            values = new String[counter]; 
            
            Iterator valueIterator = ((java.util.List)value).iterator();
            String valueString = null; 

            counter = 0;
            while(valueIterator.hasNext()) {
                valueString = ConversionUtilities.convertValueToString
                        (this, valueIterator.next());
                values[counter++] = valueString; 
            }
        }
        else if(valueTypeEvaluator.getValueType() == ValueType.ARRAY) {

            counter = Array.getLength(value); 
            values = new String[counter];
            Object valueObject = null;
            String valueString = null; 
            
            for(int i = 0; i < counter; ++i) { 
                valueObject = Array.get(value,i); 
                valueString = 
                    ConversionUtilities.convertValueToString
                    (this, valueObject); 
                values[i] = valueString; 
            } 
        } 
        else if(valueTypeEvaluator.getValueType() == ValueType.OBJECT) {
            
            values = new String[1]; 
            values[0] = ConversionUtilities.convertValueToString(this, value); 
        } 
        
        return values; 
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
        // an attribute, return the id of the select list
        // that will have the "LIST_ID" suffix. IF there is not
        // label, then the select list id will be the component's
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

        // To ensure we get the right answer call getLabelComponent.
        // This checks for a developer facet or the private label facet.
        // It also checks the label attribute. This is better than
        // relying on "getLabeledComponent" having been called
        // like this method used to do.
        //
        String clntId = this.getClientId(context);
        return clntId.concat(LIST_ID);
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
        // For now just return the same id that is used for label.
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
    public String getPrimaryElementID(FacesContext context)  {
        return getLabeledElementId(context);
    }

    // remove me when the interface method goes.
    /**
     * Return a string suitable for displaying the value in read only mode.
     * The default is to separate the list values with a comma.
     *
     * @param context The FacesContext
     * @throws javax.faces.FacesException If the list items cannot be processed
     */
    public String getValueAsReadOnly(FacesContext context, String separator) {
        return "FIX ME!";//NOI18N
    }

    public boolean mainListSubmits() {
        return true;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * <p>If true, the label is rendered above the
     * component. If false, the label is rendered next to the
     * component. The default is false.</p>
     */
    @Property(name="labelOnTop", displayName="Label on Top", category="Appearance")
    private boolean labelOnTop = false;
    private boolean labelOnTop_set = false;

    /**
     * <p>If true, the label is rendered above the
     * component. If false, the label is rendered next to the
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
     * The number of items to display, <code>Integer.MIN_VALUE</code> 
     * is returned if no value has been set, or there is no value binding.
     * Subclasses should provide a reasonable default.
     */
    @Property(name="rows", displayName="Number of Items to Display", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int rows = Integer.MIN_VALUE;
    private boolean rows_set = false;

    /**
     * The number of items to display, <code>Integer.MIN_VALUE</code> 
     * is returned if no value has been set, or there is no value binding.
     * Subclasses should provide a reasonable default.
     * @return The number items to display.
     */
    public int getRows() {
        if (this.rows_set) {
            return this.rows;
        }
        ValueExpression _vb = getValueExpression("rows");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return rows;
    }

    /**
     * The number of items to display.
     * @see #getRows()
     */
    public void setRows(int rows) {
        this.rows = rows;
        this.rows_set = true;
    }

    /**
     * <p>Flag indicating that items corresponding to 
     * <code>com.sun.webui.jsf.model.Option</code> that are defined 
     * inside a <code>com.sun.webui.jsf.model.OptionGroup</code> should be
     * surrounded by separators inside the list. The default value is
     * true. If false, no separators are shown. To manually specify the
     * location of separators, set this flag to false and place
     * instances of <code>com.sun.webui.jsf.model.Separator</code> between
     * the relevant <code>com.sun.webui.jsf.model.Option</code> instances
     * when specifying the <code>items</code> attribute.</p>
     */
    @Property(name="separators", displayName="Separators", category="Appearance")
    private boolean separators = false;
    private boolean separators_set = false;

    /**
     * <p>Flag indicating that items corresponding to 
     * <code>com.sun.webui.jsf.model.Option</code> that are defined 
     * inside a <code>com.sun.webui.jsf.model.OptionGroup</code> should be
     * surrounded by separators inside the list. The default value is
     * true. If false, no separators are shown. To manually specify the
     * location of separators, set this flag to false and place
     * instances of <code>com.sun.webui.jsf.model.Separator</code> between
     * the relevant <code>com.sun.webui.jsf.model.Option</code> instances
     * when specifying the <code>items</code> attribute.</p>
     */
    public boolean isSeparators() {
        if (this.separators_set) {
            return this.separators;
        }
        ValueExpression _vb = getValueExpression("separators");
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
     * <p>Flag indicating that items corresponding to 
     * <code>com.sun.webui.jsf.model.Option</code> that are defined 
     * inside a <code>com.sun.webui.jsf.model.OptionGroup</code> should be
     * surrounded by separators inside the list. The default value is
     * true. If false, no separators are shown. To manually specify the
     * location of separators, set this flag to false and place
     * instances of <code>com.sun.webui.jsf.model.Separator</code> between
     * the relevant <code>com.sun.webui.jsf.model.Option</code> instances
     * when specifying the <code>items</code> attribute.</p>
     * @see #isSeparators()
     */
    public void setSeparators(boolean separators) {
        this.separators = separators;
        this.separators_set = true;
    }

   /**
     * <p>Indicates whether the component should be viewable by 
     * the user in the rendered HTML page. If set to false, the
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
     * an HTML select element.
     * <p>
     * This ListSelector base class interprets a null value to imply 
     * that the select element should size itself based on the
     * length of the longest option.
     * </p>
     * @return The value used to determine the width of a select HTML element.
     */
    public String getWidth() {
        if (this.width != null) {
            return this.width;
        }
        ValueExpression _vb = getValueExpression("width");
        if (_vb != null) {
	    String _result = 
		(String)_vb.getValue(getFacesContext().getELContext());
	    _result = _result == null ? null : _result.trim();
	    if (_result != null && _result.length() != 0) {
		return _result;
            }
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
     * The value is not parsed by <code>ListSelector</code> and may
     * not be by its subclasses. It is intended to be applied directly
     * to the style attribute of the select element.
     * If <code>width</code> is null, <code>ListSelector</code> behavior
     * will assume the size of the select element will be based on the
     * length of the longest <code>Option</code> item's <code>text</code>
     * property, and if that is not set, its <code>label</code> property.
     * </p>
     * @param width The width of the listbox.
     * @see #getWidth()
     * @see com.sun.webui.jsf.model.Option#label The Option.label property
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
        this.labelOnTop = ((Boolean) _values[1]).booleanValue();
        this.labelOnTop_set = ((Boolean) _values[2]).booleanValue();
        this.rows = ((Integer) _values[3]).intValue();
        this.rows_set = ((Boolean) _values[4]).booleanValue();
        this.separators = ((Boolean) _values[5]).booleanValue();
        this.separators_set = ((Boolean) _values[6]).booleanValue();
        this.visible = ((Boolean) _values[7]).booleanValue();
        this.visible_set = ((Boolean) _values[8]).booleanValue();
	this.width = (String)_values[9];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[10];
        _values[0] = super.saveState(_context);
        _values[1] = this.labelOnTop ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.labelOnTop_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = new Integer(this.rows);
        _values[4] = this.rows_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.separators ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.separators_set ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
	_values[9] = this.width;
        return _values;
    }
}
