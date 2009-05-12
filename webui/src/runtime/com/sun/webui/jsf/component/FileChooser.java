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
import com.sun.webui.jsf.model.FileChooserModel;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.ResourceItem;
import com.sun.webui.jsf.model.ResourceModel;
import com.sun.webui.jsf.model.ResourceModelException;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.ClientSniffer;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.validator.FileChooserLookInValidator;
import com.sun.webui.jsf.validator.FileChooserFilterValidator;
import com.sun.webui.jsf.validator.FileChooserSelectValidator;
import com.sun.webui.jsf.validator.FileChooserSortValidator;
import com.sun.webui.jsf.validator.MethodExprValidator;
import com.sun.webui.theme.Theme;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Vector;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase; /* For javadoc */
import javax.faces.component.NamingContainer;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.render.Renderer;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

// The file chooser poses several problems when trying to integrate
// with the JSF lifecycle. As an EditableValueHolder it must
// provide a value and allow it to be edited. This includes having a
// submittedValue, validation and model updating.
// 
// However, it also supports several actions to allow the user to
// navigate a filesystem directory tree, including an initial directory
// called the look in field, a filter field to filter the directory
// contents, a sort menu allowing the user to sort the contents of
// the directory, a moveup button (displaye parent directory contents)
// and an open folder button to navigate a filesystem tree.
//
// In addition it must conform to SWAED guidelines in the appearance
// and behavior of the basic component as well as in context
// with other components.
//
// Handling the submittedValue
//
// The submittedValue is the sole means by which the component 
// determines that it has selections. It is only at this time that
// the file chooser participates in the full JSF lifecycle.
//
// All other actions are intermediate and perform only the necessary
// validations associated with those actions.
//
// Actions
//
// OpenFolder
//
//    This serves as the action handler for the followin actions
//    - return key pressed in the look in field
//    - return key pressed in the filter field
//    - double clicking a folder in the list box
//    - return key pressed in the listbox with a folder selection
//    - the open folder button clicked
//
// MoveUp
//
//    - uses the current lookin field value. First ensures it is 
//      valid. Must also ensure the validity of the filter field as well.
//
// Sort
//
//    - uses the current lookin field value. First ensures it is 
//      valid. Must also ensure the validity of the filter field as well.
//
// LookInField
//
//    - Effectively validated on every request.
//
// Filter
//
//    - Effectively validated on every request.
//
// FIXME: The model is used as a persistant store and is being
// referenced in preference to the subcompoents which containt
// the current data. The model must be stateless and data
// must be obtained from the sub components and respect
// their validity by checking the submittedValue if the
// component is not valid and then the component's value
// if it is valid. The model should only be used to validate
// and obtain contents.
//

/**
 * The FileChooser component allows the user to select files and folders.
 */
@Component(type="com.sun.webui.jsf.FileChooser", family="com.sun.webui.jsf.FileChooser", displayName="File Chooser", tagName="fileChooser",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_file_chooser",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_file_chooser_props")
public class FileChooser extends WebuiInput implements NamingContainer {

    // These should be model constants.
    //
    /**
     * Alphabetic sort field type
     */
    public static final String ALPHABETIC      = "alphabetic";  
    public static final String ALPHABETIC_ASC  = "alphabetica";
    public static final String ALPHABETIC_DSC  = "alphabeticd";

    /**
     * Sort "by size" field type
     */
    public static final String SIZE      = "size";
    public static final String SIZE_ASC  = "sizea";
    public static final String SIZE_DSC  = "sized";

    /**
     * Sort "by last modified" field type
     */
    public static final String LASTMODIFIED      = "time";
    public static final String LASTMODIFIED_ASC  = "timea";
    public static final String LASTMODIFIED_DSC  = "timed";
    
    /** default component ids  */
    
    // server name facet
    public static String FILECHOOSER_SERVERNAME_STATICTEXT_FACET =
        "serverNameText";
    
    // server label facet
    public static String FILECHOOSER_SERVERNAME_LABEL_FACET =
        "serverLabel";
    
    // enter keypress inline help facet
    public static String FILECHOOSER_ENTERPRESS_HELP_FACET =
        "enterPressHelp";
    
    // multiselct inline help facet
    public static String FILECHOOSER_MULTISELECT_HELP_FACET =
        "multiSelectHelp";
             
    public static String FILECHOOSER_LOOKIN_TEXTFIELD_FACET = 
	"lookinField";  //NOI18N

    public static String FILECHOOSER_LOOKIN_LABEL_FACET = 
	"lookinLabel";   //NOI18N
    
    public static String FILECHOOSER_LABEL_FACET = 
	"fileChooserLabel";   //NOI18N

    public static String FILECHOOSER_FILTERON_TEXTFIELD_FACET = 
	"filterField";    //NOI18N

    public static String FILECHOOSER_FILTER_LABEL_FACET = 
	"filterLabel";    //NOI18N

    public static String FILECHOOSER_SELECTED_TEXTFIELD_FACET = 
	"selectedField";     //NOI18N

    public static String FILECHOOSER_SELECT_LABEL_FACET = 
	"selectedLabel";    //NOI18N

    public static String FILECHOOSER_UPLEVEL_BUTTON_FACET = 
	"upButton";       //NOI18N

    public static String FILECHOOSER_OPENFOLDER_BUTTON_FACET = 
	"openButton";     //NOI18N

    public static String FILECHOOSER_SORTMENU_FACET = 
	"sortMenu";    //NOI18N

    public static String FILECHOOSER_SORT_LABEL_FACET = 
	"sortLabel";    //NOI18N

    public static String FILECHOOSER_HIDDEN_BUTTON_FACET = 
	"hiddenButton";    //NOI18N
    public static String FILECHOOSER_LISTBOX_FACET = 
	"listEntries";     //NOI18N

    // Refereced in FileChooserRenderer
    public static String FILECHOOSER_HIDDENFIELD_ID = 
	"_hiddenField";    //NOI18N

    // Error string constant for Internal Exceptions
    //
    private static final String NULLMODEL = "Null model value."; //NOI18N

    // Flag for detecting a change in the look in field.
    // Can't use value change events because they happen
    // too late. We could implement queueEvent and watch
    // events being queued  from the sub components.
    //
    private boolean openFolderChanged;
    private boolean filterChanged;
    private String lastOpenFolder;
    
    // handling a special case where both files and folders can be chosen
    private boolean fileAndFolderChooser = false;

    /**
     * Default constructor.
     */
    public FileChooser() {
        super();
        setRendererType("com.sun.webui.jsf.FileChooser");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.FileChooser";
    }

    public String getEscapeChar() {
	return getModel().getEscapeChar();
    }
    public String getDelimiterChar() {
	return getModel().getDelimiterChar();
    }

    /**
     * Return the current folder.
     * The value of <code>getModel().getCurrentDir()</code> is returned.
     */
    public String getCurrentFolder() {
	return getModel().getCurrentDir();
    }

    /**
     * Return the path element separator.
     * The value of <code>getModel().getSeparatorString()</code> is returned.
     */
    public String getSeparatorString() {
	return getModel().getSeparatorString();
    }

    /**
     * Return the current folder's parent folder.
     * The value of <code>getModel().getParentFolder()</code> is returned.
     * If model is <code>FileChooserModel</code> and there is no
     * parent folder null is returned.
     */
    public String getParentFolder() {
	return getModel().getParentFolder();
    }
  
    // Set flag to true if you want the fileChooser to be able to select both 
    // files and folders. If this method is used neither the folderChooser 
    // attribute nor the model API methods to set the chooser type should be 
    // set. 
    public void setFileAndFolderChooser(boolean flag) {
        if (flag) {
            _setFolderChooser(false);
        }
        this.fileAndFolderChooser = flag;
    }
    
    // Return true if both files and folders can be selected.
    public boolean isFileAndFolderChooser() {
        return this.fileAndFolderChooser;
    }
    
    
    public boolean isFolderChooser() {
        if (isFileAndFolderChooser()) {
            return false;
        } else {
            return _isFolderChooser();
        } 
    }

    public void setFolderChooser (boolean chooser) {
        if (isFileAndFolderChooser()) {
            _setFolderChooser(false);
        } else {
            _setFolderChooser(chooser);
        }
    }

    // - FileChooserModel must be stateless. (unless absolutely impossible
    //   which I don't think is the case.). This will allow the FileChooser
    //   to operate in a request scope. It means that data must be obtained
    //   from the sub components and not the model for things like the
    //   current directory.
    //
    // - There must be some mechanism like value change events from the
    //   subcomponents to indicate that data has changed that might
    //   affect a subcomponent after or before it has been validated.
    //   This is the case if the lookin field has changed, selections
    //   must be thrown away. (This is what the "lastOpenFolder" hack
    //   is about, as well as the directory changing due to a file
    //   selection.)
    //   One way to accomplish this is to intercept the queueEvents
    //   for the subcomponents since they bubble up the component hierachy
    //   and either do something based on seeing that event or
    //   if the PhaseId isn't ANYPHASE, make it ANYPHASE and use a
    //   value change listener. This is necessary so the immediate
    //   behavior can be realized.
    // 
    // - Calls like getDirContent() should be parameterized with the
    //   the value of the look in field, since it is
    //   the real data store for that value. Likewise the file value
    //   and sort value.
    //
    // - If the model is null create the model.
    //   It will be created on every request unless it is bound a
    //   session scope backing bean. It appears that the Model object is
    //   actually serialized during restore/save state.
    //
    public ResourceModel getModel() {
	ResourceModel model = _getModel();
	if (model == null) {
	    log(NULLMODEL);
            model = new FileChooserModel();
            Object obj = getLookin();
	    String currentDir = null;
	    if (obj != null) {
		if (obj instanceof File) {
		    currentDir = ((File)obj).getAbsolutePath();
		} else
		if (obj instanceof String) {
		    currentDir = (String)obj;
		}
	    }
            model.setCurrentDir(currentDir);
            setModel(model);
	}
	return model;
    }

    public String[] getRoots() {
	return getModel().getRoots();
    }
        
    /**
     * <p>Override the default {@link UIComponentBase#processDecodes}
     * processing to perform the following steps.</p>
     * <ul>
     * FileChooser obtains instances of the subcomponents and 
     * calls their "processDecodes" methods before it calls its 
     * own "decode" method. After, if FacesContext.getRenderResponse()
     * returns true an error has occured and FileChooser should take its
     * "failure" course.
     * After calling the processDecodes of the subcomponents, in the decode 
     * method of the FileChooser obtain the submittedValues of the 
     * subcomponents and synthesize a submitted value for the FileChooser 
     * and set its submitted value which will cause the FileChooser to 
     * participate in the JSF lifecycle processing.
     *
     * <li>If the <code>rendered</code> property of this {@link UIComponent}
     * is <code>false</code>, skip further processing.</li>
     * <li>Call the <code>processDecodes()</code> method of all facets
     * of this {@link FileChooser}, in the order determined
     * by a call to <code>getFacets().keySet().iterator()</code>.</li>
     *
     * <li>Call the <code>processDecodes()</code> method of all children
     * of this {@link FileChooser}, in the order determined
     * by a call to <code>getChildren().keySet().iterator()</code>.</li>
     * 
     * </ul>
     * @param context {@link FacesContext} for the current request
     *
     * @exception NullPointerException if <code>context</code>
     * is <code>null</code>
     */
    public void processDecodes(FacesContext context) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (!isRendered()) {
            return;
        }

        Iterator it = getFacets().keySet().iterator();
        while (it.hasNext()) {
            UIComponent facet = (UIComponent)
                getFacets().get(it.next());
            facet.processDecodes(context);
        }
        
        it = getChildren().iterator();
        while (it.hasNext()) {
            UIComponent child = (UIComponent)it.next();
            child.processDecodes(context);
        }

	// If the facet input components are immediate it can
	// be misleading if the chooser's submitted values, i.e.
	// the file selections, are full paths.
	// 
	// If the filechooser's submittedValue is one or more
	// fully qualified paths, then these fields are irrelevant
	// and invalidating the chooser because any of them are 
	// invalid is misleading.
	//

	// Policy decision. 
	// This may be contrary to SWAED desires.
	// In an effort to not create new request protocols in
	// order to conditionally decode or validate facets, the
	// look in component, the filter component and the sort
	// component will always be validated as if they were immediate.
	//
	// The policy difference is that it may be that SWAED only
	// wants these values validated if explicitly commited by
	// virtue of pressing the return key in the in the look in 
	// or filter text fields, or only when explicitly selecting a sort.
	// But that is very problematic when trying to integrate with the
	// JSF lifecycle.
	//
	// The chosen policy is also implemented in the javascript.
	//
	// If these components are immediate, they have already been validated.
	// All internally created facet fields are set as immediate.
	//
	// Typically JSF performs all validation even if one
	// validator fails.
	//
	boolean invalid = false;
	EditableValueHolder evh = null;

	// Need to do this first.
	// The policy is that if the look in field changes
	// then any selections have to be thrown away
	// since they may be relative to the last shown
	// directory.
	//
	// This is contrary to the feature of allowing the
	// selected file field to contain a full path to the 
	// desired file.
	//
	evh = (EditableValueHolder)getLookInTextField();
	if (evh != null && !evh.isImmediate()) {
	    ((UIComponent)evh).processValidators(context);
	}
	invalid = invalid || !evh.isValid();
	
	// Do this second for the a similar reason.
	// These policies reflect the client behavior of
	// clearing the selected field when the filter field
	// and the lookin field are changed by hitting the
	// return key.
	// 
	evh = (EditableValueHolder)getFilterTextField();
	if (evh != null && !evh.isImmediate()) {
	    ((UIComponent)evh).processValidators(context);
	}
	invalid = invalid || !evh.isValid();

	// Checks to see if the the look in field or the 
	// filter field have changed. If they have throw 
	// away the selections. 
	//
	// Can't use value change events because they
	// come too late.
	//
	// Need to also update the lookin field if a
	// selection implies a new folder location.
	//
	evh = (EditableValueHolder)getSelectedTextField();
	if (evh != null && !evh.isImmediate()) {
	    ((UIComponent)evh).processValidators(context);
	}
	invalid = invalid || !evh.isValid();

	evh = (EditableValueHolder)getSortComponent();
	if (evh != null && !evh.isImmediate()) {
	    ((UIComponent)evh).processValidators(context);
	}
	invalid = invalid || !evh.isValid();

	// Update the listbox state
	//
	evh = (EditableValueHolder)getListComponent();
	if (evh != null && !isImmediate()) {
	    ((UIComponent)evh).processValidators(context);
	}
	invalid = invalid || !evh.isValid();

	// As noted above, if the submitted values are
	// full paths, then the prior validation may be misleading.
	//
        decode(context);

	// If this component is immediate, then we would 
	// validate it now. However if the other components aren't
	// immediate, it wouldn't make much sense.
	// But since we are treating the facet input components as
	// immediate, we're ok.
	//
        if (isImmediate()) {
	    // If the other fields are invalid don't perform the
	    // validation. It's probably not useful since the 
	    // values may be derived from the other fields most of the time.
	    // i.e. relative path values requiring the look in field value.
	    //
	    if (!invalid) {
		validate(context);
	    } else {
		setValid(false);
	    }
        } else {
	    // If any of the components were invalid set the
	    // chooser invalid.
	    //
	    if (invalid) {
		setValid(false);
	    }
	}
	if (!isValid()) {
	    context.renderResponse();
	}
    }

    /**
     * <p>Override the default {@link UIComponentBase#processValidators}
     * processing to perform the following steps.</p>
     * <ul>
     * <li>If the <code>rendered</code> property of this {@link UIComponent}
     * is <code>false</code>, skip further processing.</li>
     * <li>Call the <code>processValidators()</code> method of all facets
     * and children of the fileChooser component except the
     * listbox. Then validate the listbox followed by the 
     * filechooser component itself. The listbox needs to be
     * validated after the other components because its
     * value depends on user input to the other components.
     * </li>
     * </ul>
     *
     * @param context {@link FacesContext} for the current request
     *
     * @exception NullPointerException if <code>context</code>
     * is <code>null</code>
     */
    public void processValidators(FacesContext context) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (!isRendered()) {
            return;
        }

	// This has the potential of validating
	// developer defined facets twice. Once, above
	// because we treat them as immediate even if they
	// are not immediate.
	//
        Iterator it = getFacetsAndChildren();
        Listbox lb = null;
        while (it.hasNext()) {
            UIComponent child = (UIComponent)it.next();
            if (child instanceof Listbox) {
                lb = (Listbox)child;
                continue;
            } else {
                child.processValidators(context);
                if (child instanceof EditableValueHolder) {
                    if (!((EditableValueHolder)child).isValid()) {
                        setValid(false);
                    }                    
                }
            }
        }
	// This only needs to happen if a developer defined
	// facet is specified. But this is not a supported
	// facet, but do it anyway.
	//
        if (lb != null) {
            lb.processValidators(context);
            if (!lb.isValid()) {
                setValid(false);
            }
        }

        try {
	    if (!isImmediate()) {
		validate(context);
	    } 
        } catch (RuntimeException rte) {
            context.renderResponse();
        }
        
        if (!isValid()) {
            context.renderResponse();
        }
    }

    /**
     * <p>Retrieve the submitted value with getSubmittedValue(). If this 
     * returns null, exit without further processing. (This indicates that 
     * no value was submitted for fileChooser.) Convert the submitted value
     * into a "local value" of the appropriate data type by calling 
     * getConvertedValue(javax.faces.context.FacesContext, java.lang.Object).
     * Validate the property by calling 
     * validateValue(javax.faces.context.FacesContext, java.lang.Object).
     * If the valid property of this component is still true, retrieve the
     * previous value of the component (with getValue()), store the new 
     * local value using setValue(), and reset the submitted value to null. 
     * If the local value is different from the previous value of this 
     * component, fire a ValueChangeEvent to be broadcast to all interested 
     * listeners.
     * processing to perform the following steps.</p>
     * <ul>
     * <li>If the <code>rendered</code> property of this {@link UIComponent}
     * is <code>false</code>, skip further processing.</li>
     * <li>Call the <code>processUpdates()</code> method of all facets
     * of this {@link FileChooser}, in the order determined
     * by a call to <code>getFacets().keySet().iterator()</code>.</li>
     * </ul>
     *
     * @param context {@link FacesContext} for the current request
     *
     * @exception NullPointerException if <code>context</code>
     * is <code>null</code>
     */
    public void validate(javax.faces.context.FacesContext context) {
        
        if (context == null) {
            throw new NullPointerException();
        }
                 
	// If submittedValue is null, then no selections were
	// And this validate will never be called unless the
	// file chooser is immediate or we are processing a true
	// submit and not an intermediate action like openFolder.
	//
        Object submittedValue = getSubmittedValue();
        if (submittedValue == null) {
            return;
        }

	// enforce isMultiple here
	//
	if (!isMultiple() && ((String[])submittedValue).length > 1) {
	    FacesMessage fmsg = null;
            if (isFolderChooser()) {
		fmsg = createFacesMessage(
			"filechooser.tooManyFileErrSum", //NOI18N
			"filechooser.tooManyFileErrDet", //NOI18N
			null, null);
            } else if (isFileAndFolderChooser()) {
                fmsg = createFacesMessage(
			"filechooser.tooManyFileFolderErrSum", //NOI18N
			"filechooser.tooManyFileFolderErrDet", //NOI18N
			null, null);
                fmsg = createFacesMessage(
			"filechooser.tooManyFolderErrSum", //NOI18N
			"filechooser.tooManyFolderErrDet", //NOI18N
			null, null);
            }
	    context.addMessage(getClientId(context), fmsg);
	    setValid(false);
	    return;
	}

	// Get the current dir to see if it has changed
	// from the previous value.
	//
	ResourceModel model = getModel();
	lastOpenFolder = model.getCurrentDir();

        Object newValue = null;
        try {
            newValue = getConvertedValue(context, submittedValue);
        }
        catch (ConverterException ce) {
            // display error message here
            setValid(false);
        }
	// Here's where it gets ugly.
	// If the openFolder has changed, call processValidators
	// on the look in field after setting its submittedValue
	// to the new value. If it turns out to be invalid.
	// null the submittedValue, and set the file chooser as invalid.
	// It should validate since it already is the current directory.
	//
	if (!lastOpenFolder.equals(model.getCurrentDir())) {
	    EditableValueHolder evh = (EditableValueHolder)getLookInTextField();
	    evh.setSubmittedValue(model.getCurrentDir());
	    try {
		((UIComponent)evh).processValidators(context);
	    } catch (ValidatorException ve) {
		setValid(false);
		// Get the last known good value
		//
		evh.setSubmittedValue(lastOpenFolder);
		model.setCurrentDir(lastOpenFolder);
		return;
	    }
	}

	// We have to handle a special case where the chooser does
	// not record a value event though a selection was submitted.
	// If the chooser is a file chooser and there is only one
	// selection and it is a folder,then this is not set as the
	// value and is the same as changing the look in field.
        
        // If our value is valid, store the new value, erase the
        // "submitted" value, and emit a ValueChangeEvent if appropriate
	//
	super.validateValue(context, newValue);
        
        if (isValid()) {
            Object previous = getValue();
            setValue(newValue);
            setSubmittedValue(null);
            if (compareValues(previous, newValue)) {
                queueEvent(new ValueChangeEvent(this, previous, newValue));
            }
        }
    }

    // Currently previous and newValue are ResourceItem arrays.
    // Return true if values are different
    //
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

    /**
     * This validation method is in addition to any that might
     * be part of the component when specified as a facet.
     * Throw a ValidatorException with a FacesMessage explaining what happened.
     *
     * Called from ChooserLookInValidator.
     */
    public void validateLookInComponent(FacesContext context,
	    UIComponent component, Object value) 
	    throws ValidatorException {
	
	// No need to check for null, getModel throws FacesException if null.
	//
	ResourceModel model = getModel();

	// Assuming object value type is String.
	//
	String lookInValue = (String)value;
		
	// For now assume this is called from 
	// ChooserLookInValidator. It is a registered validator.
	// setCurrentDir throws exception, return its FacesMessage.
	// LookInValidator will throw ValidatorException 
	// with that FacesMessage.
	// When it throws JSF will add that message
	// to the context, but with the component
	// id of the lookInField. Therefore, add it to the
	// context under the filechooser id since that is what
	// "displayAlert" and the renderer expects.
	//
	// The error strategy to be more integrated and well defined
	// so the renderer can do the right thing.
	//
	try {
	    model.setCurrentDir(lookInValue);
	} catch (ResourceModelException cme) {

	    // First clear the submitted value so the last known
	    // valid value is rendered.
	    // This is part of the policy of the FileChooser.
	    //
	    ((EditableValueHolder)component).setSubmittedValue(null);
	    FacesMessage fmsg = cme.getFacesMessage();
	    context.addMessage(getClientId(context), fmsg);
	    throw new ValidatorException(fmsg);
	}
    }

    /**
     * This validation method is in addition to any that might
     * be part of the component when specified as a facet.
     * Throw a ValidatorException with a FacesMessage explaining what happened.
     *
     * Called from ChooserFilterValidator.
     */
    public void validateFilterComponent(FacesContext context,
	    UIComponent component, Object value) 
	    throws ValidatorException {

	// No need to check for null, getModel throws FacesException if null.
	//
	ResourceModel model = getModel();

	// For now assume this is called from 
	// ChooserFilterValidator. It is a registered validator.
	// setFilterValue throws exception, return its FacesMessage.
	// ChooserFilterValidator will throw ValidatorException 
	// with that FacesMessage.
	// When it throws JSF will add that message
	// to the context, but with the component
	// id of the filterField. Therefore, add it to the
	// context under the filechooser id since that is what
	// "displayAlert" and the renderer expects.
	//
	// The error strategy needs to be more integrated and well defined
	// so the renderer can do the right thing.
	//
        String filterValue = (String)value;
                
	// Get the current filter to see if it has changed
	// from the previous value.
	//
	String lastFilter = model.getFilterValue();
	try {
	    model.setFilterValue(filterValue);
	} catch (ResourceModelException cme) {
	    ((EditableValueHolder)component).setSubmittedValue(null);
	    FacesMessage fmsg = cme.getFacesMessage();
	    context.addMessage(getClientId(context), fmsg);
	    throw new ValidatorException(fmsg);
	}
	filterChanged = !lastFilter.equals(model.getFilterValue());
    }

    /**
     * This validation method is in addition to any that might
     * be part of the component if specified as a facet.
     * Throw a ValidatorException with a FacesMessage explaining what happened.
     *
     * Called from ChooserSortValidator.
     */
    public void validateSortComponent(FacesContext context,
	    UIComponent component, Object value) 
	    throws ValidatorException {

	// No need to check for null, getModel throws FacesException if null.
	//
	ResourceModel model = getModel();

	// For now assume this is called from 
	// ChooserSortValidator. It is a registered validator.
	// setSortValue throws exception, return its FacesMessage.
	// ChooserSortValidator will throw ValidatorException 
	// with that FacesMessage.
	// When it throws JSF will add that message
	// to the context, but with the component
	// id of the sortField. Therefore, add it to the
	// context under the filechooser id since that is what
	// "displayAlert" and the renderer expects.
	//
	// The error strategy needs to be more integrated and well defined
	// so the renderer can do the right thing.
	//
        String sortValue = (String)value;
                
	// Not sure is this is the right thing to do for a drop down.
	//
	try {
	    model.setSortValue(sortValue);
	} catch (ResourceModelException cme) {
	    ((EditableValueHolder)component).setSubmittedValue(null);
	    FacesMessage fmsg = cme.getFacesMessage();
	    context.addMessage(getClientId(context), fmsg);
	    throw new ValidatorException(fmsg);
	}
    }

    /**
     * This validation method is in addition to any that might
     * be part of the component if specified as a facet.
     * Throw a ValidatorException with a FacesMessage explaining what happened.
     *
     * Called from ChooserSelectValidator.
     */
    public void validateSelectComponent(FacesContext context,
	    UIComponent component, Object value) 
	    throws ValidatorException {

	// This validator is a strange validator.
	// It going to parse out the selection and set the
	// result as the submittedValue of the filechooser.
	//
	// This is due to the fact that the selections can be
	// submitted in two ways. 
	//
	// If this selectFieldComponent doesn't exist, then
	// the selections are submitted differently and decoded
	// by the file chooser renderer as an array of selections
	// as a request parameter.
	//
	// If this select field component does exist it is 
	// submitted as well. Since it can have user entered
	// data and not just selections from the list it must
	// take precedence in the request.
	//
	// Parse the comma separated selections.
	//
	// To enforce similar policies on the client, if the
	// look in field has changed or the filter has changed
	// Throw a ValidatorException with a null message
	// to simulate what happens on the client.
	// We may want a real message at some point.
	//
	if (openFolderChanged || filterChanged) {
	    ((EditableValueHolder)component).setSubmittedValue(null);
	    ((EditableValueHolder)component).setValid(false);
	    return;
	}

	if ((value != null) && (value.toString().length() > 0)) {
	    String[] selections = decodeSelections((String)value,
		getEscapeChar(), getDelimiterChar());

	    this.setSubmittedValue(selections);
	}
    }
    /**
     * Perform the following algorithm to update the model data 
     * associated with this UIInput, if any, as appropriate.
     *
     * If the valid property of filechooser is false, take no 
     * further action. 
     * If the localValueSet property of this component is false, 
     * take no further action.
     * If no ValueBinding for value exists, take no further action.
     * Call setValue() method of the ValueBinding to update the value
     * that the ValueBinding points at.
     * If the setValue() method returns successfully:
     * o Clear the local value of this UIInput.
     * o Set the localValueSet property of this UIInput to false.
     * If the setValue() method call fails:
     * o Enqueue an error message by calling addMessage() on 
     * the specified FacesContext instance.
     * o Set the valid property of this UIInput to false.
     *
     */

    public void updateModel(javax.faces.context.FacesContext context) {
        
        
        // Update the individual component values from the model
        // and then update the fileChooser component.
        
        if (context == null) {
            throw new NullPointerException();
        }
                                                                                      
        if (!isValid() || !isLocalValueSet()) {
            return;
        }

        ValueExpression vb = getValueExpression("selected"); //NOI18N
        if (vb != null) {
            try {
                vb.setValue(context.getELContext(), getLocalValue());
                setValue(null);
                setLocalValueSet(false);
                return;
            } catch (FacesException e) {
                FacesMessage message =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        CONVERSION_MESSAGE_ID, e.getMessage());
                context.addMessage(getClientId(context), message);
                setValid(false);
            } catch (IllegalArgumentException e) {
                FacesMessage message =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        CONVERSION_MESSAGE_ID, e.getMessage());
                context.addMessage(getClientId(context), message);
                setValid(false);
            } catch (Exception e) {
                FacesMessage message =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        CONVERSION_MESSAGE_ID, e.getMessage());
                context.addMessage(getClientId(context), message);
                setValid(false);
            }
        }

    }

    /**
     * @exception NullPointerException {@inheritDoc}   
     */ 
    /*
    public void encodeEnd(FacesContext context) throws IOException {

        if (context == null) {
            throw new NullPointerException();
        }
        if (!isRendered()) {
            return;
        }
        String rendererType = getRendererType();
        if (rendererType != null) {
            getRenderer(context).encodeEnd(context, this);
        }

    }
    */

    // EditableValueMethods
    // this is the overriden method of UIInput.

    /**
     * Create a value for the fileChooser component based on the 
     * submitted value, which are the user selections.
     * The selections may be absolute or relative paths.
     * The result is an array of objects.
     *
     * @return - an object that reflects the value of the fileChooser
     * component.
     * 
     */
    public java.lang.Object getConvertedValue(FacesContext context,
        UIComponent component, Object submittedValue)
            throws ConverterException {

	// First defer to the renderer.
	//
	Renderer renderer = getRenderer(context);
	if (renderer != null) {
	    return renderer.getConvertedValue(context, this, submittedValue);
	}
	return getConvertedValue(context, (FileChooser)component,
		submittedValue);
    }

    /**
     * Overloaded getConvertedValue called by our renderer.
     *
     * We have this method because the we want the implementation
     * of getConvertedValue to exist in the component and not solely in 
     * the renderer.
     * However JSF by convention defers to the renderer first
     * in getConvertedValue and if there isn't a renderer will 
     * getConvetedValue(FacesContext, UIComponent, Object) calls this method.
     * Typically our renderer is registered as the renferer for this
     * component. Therefore it calls this method to obtain the 
     * the value when the other getConvertedValue is called and tries
     * exectute its getConvertedValue.
     */
    public Object getConvertedValue(FacesContext context,
	    FileChooser chooser, Object submittedValue)
			throws ConverterException {

	// No need to check for null, getModel throws FacesException if null.
	//
        ResourceModel model = chooser.getModel();
	
        if (submittedValue == null) {
            return null;
        }

	if(!(submittedValue instanceof String[])) { 
	    String msg = "FileChooser getConvertedValue requires " + //NOI18N
		"String[] for its submittedValue."; //NOI18N
	    log(msg);
	    throw new ConverterException(msg);
	} 
	
	// FIXME: We should strive to make this Object[] or ResourceItem[]
	// and try to convert anonymously, as needed.
	//
	Object[] chooserValues = null;
	try {
	    // Need to record any change to the current directory
	    // from a selection. Unfortunately I haven't figured
	    // out a way to do this in an elegant way.
	    //
	    lastOpenFolder = model.getCurrentDir();

	    // It really returns Object but for now assume File[]
	    //
            if (isFolderChooser()) {
	        chooserValues = (Object[])
		    model.getSelectedContent((String[])submittedValue,
		    true);
            } else if (isFileAndFolderChooser()) {
                Object[] fVals = (Object[])
                    model.getSelectedContent((String[])submittedValue, false);
                Object[] dVals = (Object[])
                    model.getSelectedContent((String[])submittedValue, true);
                int dsize = 0;
                if (dVals != null) {
                    dsize = dVals.length;
                }
                int fsize = 0;
                if (fVals != null) {
                    fsize = fVals.length;
                }
                if (fsize + dsize > 0) {
                    if (fVals instanceof File[]) {
                        chooserValues = new File[dsize + fsize];
                    } else {
                        chooserValues = new Object[dsize + fsize];
                    }
                    int k = 0;
                    for (int i=0; i < dsize; i++) {
                        chooserValues[k++] = dVals[i];
                    }
                    for (int j = 0; j < fsize; j++) {
                        chooserValues[k++] = fVals[j];
                    }
                } 
            } else {
                chooserValues = (Object[])
                    model.getSelectedContent((String[])submittedValue, false);
            }
	    // Set a flag if the directory has changed.
	    //
	    openFolderChanged = !lastOpenFolder.equals(model.getCurrentDir());

	} catch (ResourceModelException cme) {
	    FacesMessage fmsg = cme.getFacesMessage();
	    context.addMessage(getClientId(context), fmsg);
	    throw new ConverterException(fmsg.getDetail() == null ?
		fmsg.getSummary(): fmsg.getDetail());
	}

	// Need to coordinate this with "DB null values" strategy.
	//
        if (chooserValues == null || chooserValues.length == 0) {
	    return null;
	}
        
        
        // For now, if the developer chooses to use a custom model then 
        // no converter will be supplied. The backing bean has to be
        // aware of what the model was and read the value of the component
        // accordingly.
        

        if (!(chooserValues instanceof File[])) {
           return chooserValues;
        }
        
        File[] realChooserValues = (File[])chooserValues;
        
	// In case its a value binding
	//
	boolean isMultiple = isMultiple();

	// Try and get a converter
	//
	Object value = getValue();
	Converter converter = ((ValueHolder)chooser).getConverter();
	if (converter == null) {
	    converter = getConverterFromValue(value);
	}

	// If there's no value binding or existing value,
	// convert if necessary or return File[] or File
	//
	ValueExpression valueExpr = chooser.getValueExpression("value"); //NOI18N
	Class vclazz = null;
	if (valueExpr != null) {
	    vclazz = valueExpr.getType(context.getELContext());
	} else 
	if (value != null) {
	    vclazz = value.getClass();
	}

	// Default to File as native type.
	// 
	if (vclazz == null) {
	    if (isMultiple) {
		if (converter == null) {
		    return realChooserValues;
		} else {
		    return convertFileArrayToObjectArray(context, converter,
			realChooserValues);
		}
	    } else {
		if (converter == null) {
		    return realChooserValues[0];
		} else {
		    return convertFileToObject(context, converter,
			realChooserValues[0]);
		}
	    }
	}

	if (isMultiple) {
	    if (vclazz.isArray()) {
		// File[] and String[] special case
		//
		if (converter == null) {
		    if (vclazz.getComponentType().isAssignableFrom(
			    java.io.File.class)) {
			return realChooserValues;
		    } else
		    if (vclazz.getComponentType().isAssignableFrom(
			    java.lang.String.class)) {
			return convertFileArrayToStringArray(realChooserValues);
		    }
		} else {
		    // Convert to object with a converter.
		    //
		    return convertFileArrayToObjectArray(context, converter,
			realChooserValues);
		}
	    } else {
		List list = null;
		if (vclazz.isAssignableFrom(java.util.ArrayList.class)) {
		    list = new ArrayList();
		} else 
		if (vclazz.isAssignableFrom(java.util.Vector.class)) {
		    list = new Vector();
		} else
		if (vclazz.isAssignableFrom(java.util.LinkedList.class)) {
		    list = new LinkedList();
		} else {
		    try {
			list = (java.util.List)vclazz.newInstance();
		    } catch (Throwable t) {
			throw new ConverterException(
			   "FileChooser is configured for multiple selection " +
			   "but the value is not bound to an assignable type.");
		    }
		}
		// Create the list of converted or File types.
		//
		return convertFileArrayToList(context, converter,
			realChooserValues, list);
	    }
	} else {

	    if (converter != null) {
		return converter.getAsObject(context, chooser,
			convertFileToString(realChooserValues[0]));
	    }

	    if (vclazz.isAssignableFrom(java.io.File.class)) {
		return realChooserValues[0];
	    } else 
	    if (vclazz.isAssignableFrom(java.lang.String.class)) {
		return convertFileToString(realChooserValues[0]);
	    } else {
		return (Object)realChooserValues[0];
	    }
	}
	// We shouldn't get here but if we do return null.
	//
	return null;
    }

    // Converters
    //
    // FIXME: Some of these should be in the model. Especially if native type
    // of the model is more Opaque like ChooserItem
    // Then the ChooseItem would provide the converters.
    //

    private Converter getConverterFromValue(Object value) {
		
	if (value == null) {
	    return null;
	}
	Converter converter = null;
	try {
	    Class clazz = value.getClass();
	    if (isMultiple()) {

		if (clazz.isArray()) {
		    clazz = clazz.getComponentType();
		} else
		if (value instanceof List && ((List)value).size() != 0) {
		    Object listItem = ((List)value).get(0);
		    clazz = listItem != null ? listItem.getClass() : null;
		} else {
		    // Can't figure out for multiple return null
		    //
		    clazz = null;
		    log("Failed to obtain a class for the " + //NOI18N
			"FileChooser multiple value."); //NOI18N
		    return null;
		}
	
	    } 
	    if (clazz != null) {
		converter = ConversionUtilities.getConverterForClass(clazz);
	    }

	} catch (Exception e) {
	    // Proceed but log the error
	    //
	    String msg =
		"Failed to obtain a class for FileChooser value."; //NOI18N
	    log(msg + "\nException: " + e.getStackTrace()); //NOI18N
	}
	return converter;
    }

    /**
     * If converter is not null return an Object[] where each entry
     * is converted by applying the converted to each entry in 
     * fileArray.
     * If converter is null return fileArray.
     * If fileArray is null return null;
     */
    protected Object convertFileArrayToObjectArray(FacesContext context,
	    Converter converter, File[] fileArray) 
	    throws ConverterException {

	// What does it mean if fileArray.length == 0 ?
	//
	if (fileArray == null) {
	    return null;
	}
	Object[] objArray = new Object[fileArray.length];
	if (converter == null) {
	    for (int i = 0; i < fileArray.length; ++i) {
		objArray[i] = fileArray[i];
	    }
	} else {
	    for (int i = 0; i < fileArray.length; ++i) {
		objArray[i] = converter.getAsObject(context, this,
		    convertFileToString(fileArray[i]));
	    }
	}
	return objArray;
    }

    protected List convertFileArrayToList(FacesContext context,
	    Converter converter, File[] fileArray, List list) 
	    throws ConverterException {
	
	if (list == null) {
	    return null;
	}
	if (converter != null) {
	    for (int i = 0; i < fileArray.length; ++i) {
		list.add(converter.getAsObject(context, this,
		    convertFileToString(fileArray[i])));
	    }
	} else {
	    for (int i = 0; i < fileArray.length; ++i) {
		list.add(fileArray[i]);
	    }
	}
	return list;
    }

    /**
     * If converter is not null return an Object that was converted
     * by converter.
     * If converter is null, return file.
     */
    protected Object convertFileToObject(FacesContext context,
	    Converter converter, File file) 
	    throws ConverterException {

	    if (converter == null) {
		return file;
	    }
	    String fileString = convertFileToString(file);
	    if (fileString == null) {
		return null;
	    }
	    return converter.getAsObject(context, this, fileString);
    }

    protected String convertFileToString(File file) {

	// using getAbsolutePath path for this is a policy
	// issue and need to make sure this is consistent
	// and expected.
	//
	return file != null ? file.getAbsolutePath() : null;
    }

    protected String[] convertFileArrayToStringArray(File[] fileArray) {

	if (fileArray == null) {
	    return null;
	}
	String[] strArray = new String[fileArray.length];
	for (int i = 0; i < fileArray.length; ++i) {
	    strArray[i] = convertFileToString(fileArray[i]);
	}
	return strArray;
    }

    /**
     * 
     */
    protected String[] convertValueToStringArray(FacesContext context,
	    Converter converter, Object value) throws ConverterException {

	// If there's no value just return null.
	//
	if (value == null) {
	    return null;
	}

	Class vclazz = value.getClass();
	if (!isMultiple()) {
	    return new String[] {
		convertValueToString(context, converter, value)};
	} else {
	    if (vclazz.isArray()) {
		return convertObjectArrayToStringArray(context, converter,
			(Object[])value);
	    } else
	    if (value instanceof List) {
		return convertObjectListToStringArray(context, converter,
			(List)value);
	    }
	    String msg =
	       "FileChooser is configured for multiple selection " +
	       "but the value is not an assignable type.";
	    log(msg);
	    throw new ConverterException(msg);
	}
    }

    protected String convertValueToString(FacesContext context, 
		Converter converter, Object value)
		throws ConverterException {

	if (value == null) {
	    return null;
	}
	if (converter != null) {
	    return converter.getAsString(context, this, value);
	} else
	if (value instanceof File) {
	    return convertFileToString((File)value);
	} else 
	if (value instanceof String) {
	    return (String)value;
	}  else {
	    // Instead of bailing just return "toString".
	    //
	    log("Resorting to object.toString() to convert single " +
		    "value to String.");
	    return value.toString();
	}
    }

    protected String[] convertObjectArrayToStringArray(FacesContext context,
	    Converter converter, Object[] value) 
	    throws ConverterException {
	
	if (value == null) {
	    return null;
	}

	if (converter != null) {
	    String[] strArray = new String[value.length];
	    for (int i = 0; i < value.length; ++i) {
		strArray[i] = converter.getAsString(context, this, value[i]);
	    }
	    return strArray;
	} else {
	    if (value instanceof File[]) {
		return convertFileArrayToStringArray((File[])value);
	    } else
	    if (value instanceof String[]) {
		return (String[])value;
	    } else {
		// Instead of bailing just return "toString".
		//
		log("Resorting to object.toString() to convert multiple " +
			"array value to String[].");
		String[] strArray = new String[value.length];
		for (int i = 0; i < value.length; ++i) {
		    strArray[i] = value[i].toString();
		}
		return strArray;
	    }
	}
    }

    protected String[] convertObjectListToStringArray(FacesContext context,
	    Converter converter, List list) throws ConverterException {
	
	if (list == null) {
	    return null;
	}

	if (converter != null) {
	    String[] strArray = new String[list.size()];
	    for (int i = 0; i < list.size(); ++i) {
		strArray[i] = converter.getAsString(context, this,
			list.get(i));
	    }
	    return strArray;
	} else {
	    // Try and find out what type we have by looking at the
	    // first List value.
	    //
	    // To be consistent
	    //
	    String[] strArray = new String[list.size()];
	    if (list.size() == 0) {
		return strArray;
	    }
	    Object listItem = list.get(0);
	    if (listItem instanceof File) {
		File[] fileArray = new File[list.size()];
		fileArray = (File[])list.toArray(fileArray);
		return convertFileArrayToStringArray(fileArray);
	    } else
	    if (listItem instanceof String) {
		return (String[])list.toArray(strArray);
	    } else {
		// Instead of bailing just return "toString".
		//
		log("Resorting to object.toString() to convert multiple " +
			"list value to String[].");
		for (int i = 0; i < list.size(); ++i) {
		    strArray[i] = list.get(i).toString();
		}
		return strArray;
	    }
	}
    }

    public static final String HYPHEN = "-";     //NOI18N
    /**
     * This method returns a Listbox containing the list of
     * resources selected by the user.
     */
    private Listbox populateListComponent(Listbox fileList) {

	// No need to check for null, getModel throws FacesException if null.
	//
	ResourceModel model = getModel();
	ResourceItem[] items = null;
	// If a folder chooser always disable files
	//
        
        items = model.getFolderContent(model.getCurrentDir(),
		isFolderChooser(), false);
        
	// FIXME: Need more well defined data here for the
	// list options. We probably want files read only
	// in a folder chooser, folders read only or 
	// disabled whichever allows a javascript event
	// to open a folder but not to select it for
	// submission.
	//
	if (items != null && items.length != 0) {
	    Option[] optList = new Option[items.length];
	    for (int i=0; i< items.length; i++) {
		optList[i] = new Option(items[i].getItemKey(),
		    items[i].getItemLabel());
		optList[i].setDisabled(items[i].isItemDisabled());
                optList[i].setEscape(false);
	    }
	    fileList.setItems(optList);
        } else {
	    populateEmptyList(fileList);
        }
        return fileList;
    }

    private void populateEmptyList(Listbox fileList) {

	Theme theme = getTheme();

	// FIXME: Consider having a format string in the Theme
	// 
        int fileNameLen = Integer.parseInt(theme
                .getMessage("filechooser.fileNameLen"));
        int fileSizeLen = Integer.parseInt(theme
                .getMessage("filechooser.fileSizeLen"));
        int fileDateLen = Integer.parseInt(theme
                .getMessage("filechooser.fileDateLen"));
        
	// no files or directories exist
	// A line with "--------" has to be added to make the list box
	// have a width
	String label = "";   //NOI18N
	String value = "0";  //NOI18N
	// int len = fileNameLen + fileSizeLen + fileDateLen + 6;
	int len = fileNameLen + fileSizeLen + fileDateLen + 10;
	for (int i = 0; i < len; i++) {
	    label += HYPHEN;
	}
	Option [] fileEntries = new Option[1];
	fileEntries[0] = new Option(value, label);
	fileEntries[0].setDisabled(true);
        fileEntries[0].setEscape(false);
	fileList.setItems(fileEntries);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Child Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Since the server text label does not use the for
    // attribute, neither the StaticText component or
    // label need to be placed in the facet map.
    //
    /**
     * Return a component that implements the server name field.
     * If a facet named <code>serverNameText</code> is found
     * that component is returned. Otherwise a <code>StaticText</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_serverNameText"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>StaticText</code>
     * component is recreated every time this method is called.
     * </p>
     * @return - the server name field component
     */
    public UIComponent getServerNameText() {

	UIComponent facet = getFacet(FILECHOOSER_SERVERNAME_STATICTEXT_FACET);
   	if (facet != null) {
	    return facet;
   	}

	StaticText child = new StaticText();
	child.setId(ComponentUtilities.createPrivateFacetId(this,
		FILECHOOSER_SERVERNAME_STATICTEXT_FACET));
	child.setParent(this);

	Theme theme = getTheme();
        child.setText(theme.getMessage("filechooser.lookinColumn"));
        child.setStyleClass(theme
                    .getStyleClass(ThemeStyles.FILECHOOSER_NAME_TXT));
        
	// No need to check for null, getModel throws FacesException if null.
	//
        ResourceModel model = getModel();
	// Defaults to "localhost"
	//
        String serverName = model.getServerName(); 
        child.setText(serverName);

        return child;
    }
            

    /**
     * Return a component that implements the inline help for the 
     * filter text field.
     * If a facet named <code>enterPressHelp</code> is found
     * that component is returned. Otherwise a <code>HelpInline</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_enterPressHelp"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>StaticText</code>
     * component is created every time this method is called.
     * </p>
     *
     * @return the inline help  component
     */
    public UIComponent getEnterInlineHelp() {

	UIComponent facet = getFacet(FILECHOOSER_ENTERPRESS_HELP_FACET);
   	if (facet != null) {
	    return facet;
   	}

	HelpInline child = new HelpInline();
	child.setId(ComponentUtilities.createPrivateFacetId(this,
	    FILECHOOSER_ENTERPRESS_HELP_FACET));
	child.setParent(this);

	Theme theme = getTheme();
	child.setText(theme.getMessage("filechooser.enterKeyHelp")); // NOI18N
       child.setType("field");

        return child;
    }
    
    /**
     * Return a component that implements the inline help for
     * selecting multiple rows from the listbox.
     * If the <code>isMultiple</code> returns false, null is returned.</br>
     * If a facet named <code>multiSelectHelp</code> is found
     * that component is returned. Otherwise a <code>HelpInline</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_multiSelectHelp"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>HelpInline</code>
     * component is created every time this method is called.
     * </p>
     *
     * @return the inline help  component
     */
    public UIComponent getMultiSelectHelp() {

        if (!isMultiple()) {
	    return null;
	}

	UIComponent facet = getFacet(FILECHOOSER_MULTISELECT_HELP_FACET);
   	if (facet != null) {
	    return facet;
   	}
        
	HelpInline child = new HelpInline();
	child.setId(ComponentUtilities.createPrivateFacetId(this,
	    FILECHOOSER_MULTISELECT_HELP_FACET));
	child.setParent(this);

	Theme theme = getTheme();
	child.setText(theme
	    .getMessage("filechooser.multiSelectHelp")); // NOI18N
	child.setType("field");

	return child;
    }
    
    /**
     * Return a component that implements the server name field label.
     * If a facet named <code>serverLabel</code> is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_serverLabel"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is created every time this method is called.
     * </p>
     *
     * @return the server name field label component
     */
    public UIComponent getServerNameLabel() {

	UIComponent facet = getFacet(FILECHOOSER_SERVERNAME_LABEL_FACET);
   	if (facet != null) {
	    return facet;
   	}

	Label child = new Label();
	child.setId(ComponentUtilities.createPrivateFacetId(this,
	    FILECHOOSER_SERVERNAME_LABEL_FACET));
	child.setParent(this);

	// Should be in theme
	//
	child.setLabelLevel(2);

	Theme theme = getTheme();
	child.setText(theme.getMessage("filechooser.serverPrompt")); //NOI18N
        child.setStyleClass(theme
            .getStyleClass(ThemeStyles.LABEL_LEVEL_TWO_TEXT));

        return child;
    }
    
    /**
     * Return a component that implements the title text.
     * If a facet named <code>fileChooserLabel</code> is found
     * that component is returned. Otherwise a <code>StaticText</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_fileChooserLabel"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>StaticText</code>
     * component is created every time this method is called.
     * </p>
     *
     * @return the FileChooser title component
     */
    public UIComponent getFileChooserTitle() {

	UIComponent facet = getFacet(FILECHOOSER_LABEL_FACET);
   	if (facet != null) {
	    return facet;
   	}

	StaticText child = new StaticText();
	child.setId(ComponentUtilities.createPrivateFacetId(this,
	    FILECHOOSER_LABEL_FACET));
	child.setParent(this);

        Theme theme = getTheme();
        child.setText(theme.getMessage("filechooser.title")); //NOI18N

        /*child.setStyleClass(theme
            .getStyleClass(ThemeStyles.FILECHOOSER_LABEL_TXT));*/

        return child;

    }
    
    /**
     * Return a component that implements the look in input field.
     * If a facet named <code>lookinField</code> is found
     * that component is returned. Otherwise a <code>TextField</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_lookinField"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>TextField</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return the look in input field component
     */
    public UIComponent getLookInTextField() {

	UIComponent facet = getFacet(FILECHOOSER_LOOKIN_TEXTFIELD_FACET);
   	if (facet != null) {
	    return facet;
   	}

	TextField child = (TextField)
	    ComponentUtilities.getPrivateFacet(this,
		FILECHOOSER_LOOKIN_TEXTFIELD_FACET, true);
	if (child == null) {
	    child = new TextField();
	    child.setId(ComponentUtilities.createPrivateFacetId(this,
		FILECHOOSER_LOOKIN_TEXTFIELD_FACET));
                        
	    child.addValidator(new FileChooserLookInValidator());
            child.setSubmittedValue(getModel().getCurrentDir());
	    ComponentUtilities.putPrivateFacet(this,
		FILECHOOSER_LOOKIN_TEXTFIELD_FACET, child);
	}       
                
	Theme theme = getTheme();
	child.setColumns(Integer.parseInt(theme
	    .getMessage("filechooser.lookinColumn"))); //NOI18N
        child.setStyleClass(
	    theme.getStyleClass(
		ThemeStyles.FILECHOOSER_NAME_TXT).concat(" ")
	    .concat(theme.getStyleClass(ThemeStyles.FILECHOOSER_WIDTH_TXT)));
        
        int tindex = getTabIndex();
        if (tindex > 0 && tindex < 32767) {
            child.setTabIndex(tindex);
        }

        return child;
    }

    /**
     * Return a component that implements the look in input field label.
     * If a facet named <code>lookinLabel</code> is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_lookinLabel"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return the look in input field label component
     */
    public UIComponent getLookInLabel() {

	UIComponent facet = getFacet(FILECHOOSER_LOOKIN_LABEL_FACET);
   	if (facet != null) {
	    return facet;
   	}

	Label child = (Label)
	    ComponentUtilities.getPrivateFacet(this,
		FILECHOOSER_LOOKIN_LABEL_FACET, true);
	if (child == null) {
	    child = new Label();
	    child.setId(ComponentUtilities.createPrivateFacetId(this,
		FILECHOOSER_LOOKIN_LABEL_FACET));

	    // Should be in theme
	    //
	    child.setLabelLevel(2);
	    ComponentUtilities.putPrivateFacet(this,
		FILECHOOSER_LOOKIN_LABEL_FACET, child);
	}

	Theme theme = getTheme();
	child.setText(theme.getMessage("filechooser.lookin")); //NOI18N

	// We could have issues here if the returned component
	// is a developer defined facet and it has its own label
	//
	UIComponent licomp = getLookInTextField();
        child.setLabeledComponent(licomp);
        child.setIndicatorComponent(licomp);

        return child;
    }

    /**
     * Return a component that implements the filter input field.
     * If a facet named <code>filterField</code> is found
     * that component is returned. Otherwise a <code>TextField</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_filterField"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>TextField</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return the filter input field component
     */
    public UIComponent getFilterTextField() {

   	UIComponent facet = (TextField)
		getFacet(FILECHOOSER_FILTERON_TEXTFIELD_FACET);
   	if (facet != null) {
	    return facet;
   	}

	TextField child = (TextField)
	    ComponentUtilities.getPrivateFacet(this,
		FILECHOOSER_FILTERON_TEXTFIELD_FACET, true);
	if (child == null) {
	    child = new TextField();
	    child.setId(ComponentUtilities.createPrivateFacetId(this,
		FILECHOOSER_FILTERON_TEXTFIELD_FACET));

	    child.addValidator(new FileChooserFilterValidator());
	    child.setSubmittedValue(getModel().getFilterValue());
	    ComponentUtilities.putPrivateFacet(this,
		FILECHOOSER_FILTERON_TEXTFIELD_FACET, child);
	}

        FacesContext context = FacesContext.getCurrentInstance();
        ClientSniffer sniffer = ClientSniffer.getInstance(context);

	// Needs to be in Theme.
	//
	int size = sniffer.isNav6up() ? 32 : 18;
        child.setColumns(size);
        
        int tindex = getTabIndex();
        if (tindex > 0 && tindex < 32767) {
            child.setTabIndex(tindex);
        }

        return child;
    }

    /**
     * Return a component that implements the filter input field label.
     * If a facet named <code>filterLabel</code> is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_filterLabel"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return the filter input field label component
     */
    public UIComponent getFilterLabel() {

	UIComponent facet = getFacet(FILECHOOSER_FILTER_LABEL_FACET);
   	if (facet != null) {
	    return facet;
   	}

	Label child = (Label)
	    ComponentUtilities.getPrivateFacet(this,
		FILECHOOSER_FILTER_LABEL_FACET, true);
	if (child == null) {
	    child = new Label();
	    child.setId(ComponentUtilities.createPrivateFacetId(this,
		FILECHOOSER_FILTER_LABEL_FACET));

	    // Should be in theme
	    //
	    child.setLabelLevel(2);
	    ComponentUtilities.putPrivateFacet(this,
		FILECHOOSER_FILTER_LABEL_FACET, child);
	}

	Theme theme = getTheme();
        if (isFolderChooser()) {
            child.setText(
		theme.getMessage("filechooser.folderFilter")); //NOI18N
        } else {
            child.setText(
		theme.getMessage("filechooser.fileFilter")); //NOI18N
        }
	
	// We could have issues here if the returned component
	// is a developer defined facet and it has its own label
	//
	UIComponent fcomp = getFilterTextField();
	child.setLabeledComponent(fcomp);
	child.setIndicatorComponent(fcomp);

        return child;
    }

    /**
     * Return a component that implements the selected file(s) or 
     * folder(s) input field.
     * If a facet named <code>selectedField</code> is found
     * that component is returned. Otherwise a <code>TextField</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_selectedField"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>TextField</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return the select text field component. This text
     * field displays the list of selected items.
     */
    public UIComponent getSelectedTextField() {

	UIComponent facet = null;
   	facet = getFacet(FILECHOOSER_SELECTED_TEXTFIELD_FACET);
   	if (facet != null) {
	    return facet;
   	}

	TextField child = (TextField)
	    ComponentUtilities.getPrivateFacet(this,
		FILECHOOSER_SELECTED_TEXTFIELD_FACET, true);
	if (child == null) {
	    child = new TextField();
	    child.setId(ComponentUtilities.createPrivateFacetId(this,
		FILECHOOSER_SELECTED_TEXTFIELD_FACET));

	    child.addValidator(new FileChooserSelectValidator());
	    ComponentUtilities.putPrivateFacet(this,
		FILECHOOSER_SELECTED_TEXTFIELD_FACET, child);
	}

	Theme theme = getTheme();
        if (isMultiple()) {
	    child.setColumns(Integer.parseInt(theme
	        .getMessage("filechooser.multipleColumn"))); //NOI18N
        } else {
	    child.setColumns(Integer.parseInt(theme
	        .getMessage("filechooser.singleColumn"))); //NOI18N
        }
        
        if (isFolderChooser()) {
             child.setStyleClass(theme
                    .getStyleClass(ThemeStyles.FILECHOOSER_FOLD_STYLE));
        } else {
            child.setStyleClass(theme
                    .getStyleClass(ThemeStyles.FILECHOOSER_FILE_STYLE));
        }
        
        int tindex = getTabIndex();
        if (tindex > 0 && tindex < 32767) {
            child.setTabIndex(tindex);
        }        
                
	// FIXME: Need to add onblur handler.
	// This will be the only way to control the
	// chooser button correctly when a user enters a
	// value manually.
	//

        return child;
    }

    // This label does not use the for attribute.
    // Therefore it does not necessarily have to be placed in the
    // facet map.
    /**
     * Return a component that implements the selected file(s) or
     * folder(s) input field label.
     * If a facet named <code>selectedLabel</code> is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_selectedLabel"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return - returns the selected text field label component
     */
    public UIComponent getSelectLabel() {

	UIComponent facet = getFacet(FILECHOOSER_SELECT_LABEL_FACET);
   	if (facet != null) {
	    return facet;
   	}

	Label child = (Label)
	    ComponentUtilities.getPrivateFacet(this,
		FILECHOOSER_SELECT_LABEL_FACET, true);
	if (child == null) {
	    child = new Label();
	    child.setId(ComponentUtilities.createPrivateFacetId(this,
		FILECHOOSER_SELECT_LABEL_FACET));

	    // Should be in theme
	    //
	    child.setLabelLevel(2);
	    ComponentUtilities.putPrivateFacet(this,
		FILECHOOSER_SELECT_LABEL_FACET, child);
	}

	Theme theme = getTheme();

	// We could have issues here if the returned component
	// is a developer defined facet and it has its own label
	//
	UIComponent selcomp = getSelectedTextField();
	child.setLabeledComponent(selcomp);
	child.setIndicatorComponent(selcomp);

	boolean ismultiple = isMultiple();
        String labelKey = null;
        if (isFolderChooser()) {
            labelKey = ismultiple ? "filechooser.selectedFolders" : //NOI18N
		"filechooser.selectedFolder"; //NOI18N
        } else if (isFileAndFolderChooser()) {
            labelKey = ismultiple ? 
		"filechooser.selectedFileAndFolders" : //NOI18N
		"filechooser.selectedFileAndFolder";  //NOI18N
        } else {
            labelKey = ismultiple ? "filechooser.selectedFiles" :  //NOI18N
		"filechooser.selectedFile";  //NOI18N
        }

        child.setText(theme.getMessage(labelKey));

        return child;
    }

    /**
     * Return a component that implements the sort criteria menu.
     * If a facet named <code>sortMenu</code> is found
     * that component is returned. Otherwise a <code>DropDown</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_sortMenu"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>DropDown</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return the dropdown sort menu component
     */
    public UIComponent getSortComponent() {

   	UIComponent facet = getFacet(FILECHOOSER_SORTMENU_FACET);
   	if (facet != null) {
	    return facet;

   	}

	DropDown jdd = (DropDown)
	    ComponentUtilities.getPrivateFacet(this,
		FILECHOOSER_SORTMENU_FACET, true);
	if (jdd == null) {
	    jdd = new DropDown();
	    jdd.setId(ComponentUtilities.createPrivateFacetId(this,
		FILECHOOSER_SORTMENU_FACET));

	    jdd.setSubmitForm(true);

	    // Should be part of theme
	    //
	    jdd.setLabelLevel(2);
	    jdd.addValidator(new FileChooserSortValidator());
	    jdd.setImmediate(true);

	    ComponentUtilities.putPrivateFacet(this,
		FILECHOOSER_SORTMENU_FACET, jdd);
	}

	// FIXME: These sort constants are a function of the
	// chooser model and should be obtained from
	// model. Sort should be some sort of sort class
	// from which one could get the "display" names
	// of the sort options.
	//
	// Possibly even have the model return
	// and Option array. This is a known model type
	// for our components.
	//
	Theme theme = getTheme();
	Option[] sortFields = new Option[6];
	sortFields[0] = new Option(ALPHABETIC_ASC, 
	    theme.getMessage("filechooser.sortOption1")); //NOI18N
	sortFields[1] = new Option(ALPHABETIC_DSC,
	    theme.getMessage("filechooser.sortOption4")); //NOI18N
	sortFields[2] = new Option(LASTMODIFIED_ASC, 
	    theme.getMessage("filechooser.sortOption2")); //NOI18N
	sortFields[3] = new Option(LASTMODIFIED_DSC, 
	    theme.getMessage("filechooser.sortOption5")); //NOI18N
	sortFields[4] = new Option(SIZE_ASC, 
	    theme.getMessage("filechooser.sortOption3")); //NOI18N
	sortFields[5] = new Option(SIZE_DSC, 
	    theme.getMessage("filechooser.sortOption6")); //NOI18N

	jdd.setItems(sortFields);
	// jdd.setLabel(theme.getMessage("filechooser.sortBy")); //NOI18N
        // jdd.setLabelLevel(2);
	jdd.setStyleClass(theme.getStyleClass(ThemeStyles.MENU_JUMP));
        
        int tindex = getTabIndex();
        if (tindex > 0 && tindex < 32767) {
            jdd.setTabIndex(tindex);
        }
	
	// No need to check for null, getModel throws FacesException if null.
	//
	ResourceModel model = getModel();
	String sortVal = model.getSortValue();

	// first check if model's sortfield is set
	//
	if (sortVal == null) {
	    String sField = getSortField();
	    if (isDescending()) {
		sField = sField.concat("d");    //NOI18N
            } else {
                sField = sField.concat("a");    //NOI18N
            }
            jdd.setSelected(sField);
            model.setSortValue(sField);	    
	} else {
	    jdd.setSelected(sortVal);
	}

	return jdd;
    }
    /**
     * Return a component that implements the sort criteria menu.
     * If a facet named <code>sortMenu</code> is found
     * that component is returned. Otherwise a <code>DropDown</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_sortMenu"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>DropDown</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return the dropdown sort menu component
     */
    public UIComponent getSortComponentLabel() {

   	UIComponent facet = getFacet(FILECHOOSER_SORT_LABEL_FACET);
   	if (facet != null) {
	    return facet;
   	}

	Label child = (Label)
	    ComponentUtilities.getPrivateFacet(this,
		FILECHOOSER_SORT_LABEL_FACET, true);
	if (child == null) {
	    child = new Label();
	    child.setId(ComponentUtilities.createPrivateFacetId(this,
		FILECHOOSER_SORT_LABEL_FACET));

	    // Should be in theme
	    //
	    child.setLabelLevel(2);
	    ComponentUtilities.putPrivateFacet(this,
		FILECHOOSER_SORT_LABEL_FACET, child);
	}

	Theme theme = getTheme();
        child.setText(
	    theme.getMessage("filechooser.sortBy")); //NOI18N

	// We could have issues here if the returned component
	// is a developer defined facet and it has its own label
	//
	UIComponent sortcomp = getSortComponent();
	child.setLabeledComponent(sortcomp);
	child.setIndicatorComponent(sortcomp);

        return child;
    }
        
    /**
     * Return a component that implements the list of files and folders.
     * It is assigned the id</br>
     * <code>getId() + "_listEntries"</code></br>
     * <p>
     * The returned <code>Listbox</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return the dropdown sort menu component
     */
    public UIComponent getListComponent() {

        Listbox fileList = (Listbox)
	    ComponentUtilities.getPrivateFacet(this,
		FILECHOOSER_LISTBOX_FACET, true);
        if (fileList == null) {
	    fileList = new Listbox();
	    fileList.setId(ComponentUtilities.createPrivateFacetId(this,
		FILECHOOSER_LISTBOX_FACET));

	    // FIXME: This should be in Theme
	    //
	    fileList.setMonospace(true);
	    ComponentUtilities.putPrivateFacet(this,
		FILECHOOSER_LISTBOX_FACET, fileList);
	}	
        
	fileList.setRows(getRows());
	
	Theme theme = getTheme();

	if (isFolderChooser()) {
	    fileList.setToolTip(theme
		.getMessage("filechooser.listTitleFolder")); //NOI18N
	} else if (isFileAndFolderChooser()) {
	    fileList.setToolTip(theme
		.getMessage("filechooser.listTitleFileAndFolder")); //NOI18N)
	} else {
	    fileList.setToolTip(theme
		.getMessage("filechooser.listTitleFile")); //NOI18N
	}
     
	fileList.setMultiple(isMultiple());
	fileList.setValue(null);
        
        int tindex = getTabIndex();
        if (tindex > 0 && tindex < 32767) {
            fileList.setTabIndex(tindex);
        }

	return populateListComponent(fileList);
    }

    /**
     * Return a component that implements the move up button.
     * If a facet named <code>upButton</code> is found
     * that component is returned. Otherwise a <code>Button</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_upButton"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Button</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @param disabled Flag indicating button is disabled
     * @return the button component for moving up the folder hierarchy
     */
    public UIComponent getUpLevelButton(boolean disabled) {

	UIComponent facet = getFacet(FILECHOOSER_UPLEVEL_BUTTON_FACET);
   	if (facet != null) {
	    return facet;
   	}
        Theme theme = getTheme();
        
	Button child = (Button)
	    ComponentUtilities.getPrivateFacet(this,
		FILECHOOSER_UPLEVEL_BUTTON_FACET, true);
	if (child == null) {
	    child = new Button();
            child.setStyleClass(theme.getStyleClass(ThemeStyles.FILECHOOSER_IMG_BTN));
	    child.setId(ComponentUtilities.createPrivateFacetId(this,
		FILECHOOSER_UPLEVEL_BUTTON_FACET));
            child.setIcon(ThemeImages.FC_UP_1LEVEL);
	    child.setImmediate(true);
	    ComponentUtilities.putPrivateFacet(this,
		FILECHOOSER_UPLEVEL_BUTTON_FACET, child);
	}

	child.setText(theme.getMessage("filechooser.upOneLevel")); //NOI18N
        child.setToolTip(
		theme.getMessage("filechooser.upOneLevelTitle")); //NOI18N

	// Disabled should not be passed in.
	// This should either be determined solely on the client
	// or from the model, if there is a parent folder of the
	// current folder. The problem is ensuring that the
	// model has been updated to have the latest data
	// when this method is called.
	//
	child.setDisabled(disabled);
        
        int tindex = getTabIndex();
        if (tindex > 0 && tindex < 32767) {
            child.setTabIndex(tindex);
        }        

        return child;
    }

    /**
     * Return a component that implements the open folder button.
     * If a facet named <code>openButton</code> is found
     * that component is returned. Otherwise a <code>Button</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_openButton"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Button</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return the OpenFolder button component.
     */
    public UIComponent getOpenFolderButton() {

	UIComponent facet = getFacet(FILECHOOSER_OPENFOLDER_BUTTON_FACET);
   	if (facet != null) {
	    return facet;
   	}
        Theme theme = getTheme();
	Button child = (Button)
	    ComponentUtilities.getPrivateFacet(this,
		FILECHOOSER_OPENFOLDER_BUTTON_FACET, true);
	if (child == null) {
	    child = new Button();
	    child.setId(ComponentUtilities.createPrivateFacetId(this,
		FILECHOOSER_OPENFOLDER_BUTTON_FACET));

	    child.setDisabled(false);
            child.setImmediate(true);
            child.setIcon(ThemeImages.FC_OPEN_FOLDER);
	    ComponentUtilities.putPrivateFacet(this,
		FILECHOOSER_OPENFOLDER_BUTTON_FACET, child);
	}

	
        child.setText(theme.getMessage("filechooser.openFolder")); //NOI18N
        child.setToolTip(
	    theme.getMessage("filechooser.openFolderTitle")); //NOI18N
            
        int tindex = getTabIndex();
        if (tindex > 0 && tindex < 32767) {
            child.setTabIndex(tindex);
        }
        
        return child;
    }

    // I don't think this is ever used on the client any more.
    // This method is referenced in the renderer but I don't think
    // the client javascript "clicks" it anymore.
    /**
     * Get a hidden button. In order to associate all user actions
     * with an ActionEvent and have a single ActionListener to listen
     * for these events a hidden button is being created to monitor
     * changes in texh filed values. When a user enters data in a
     * text field and hits enter a click of this hidden button will
     * be initiated using Javascript.
     *
     * @return the hidden button component.
     */
    public UIComponent getHiddenFCButton() {

	Button child = (Button)
	    ComponentUtilities.getPrivateFacet(this,
		FILECHOOSER_HIDDEN_BUTTON_FACET, true);
	if (child == null) {
            child = new Button();
            child.setId(ComponentUtilities.createPrivateFacetId(this,
		FILECHOOSER_HIDDEN_BUTTON_FACET));

	    ComponentUtilities.putPrivateFacet(this,
		FILECHOOSER_HIDDEN_BUTTON_FACET, child);
	}
	child.setVisible(false);
	return child;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        
        if (_state == null) {
            return;
        }
        Object _values[] = (Object[]) _state;
        _restoreState(_context, _values[0]);
        this.fileAndFolderChooser = ((Boolean) _values[1]).booleanValue();
        //this.valueChangeListenerExpression =(MethodExpression) restoreAttachedState(_context, _values[2]);
        //this.validatorExpression = (MethodExpression)restoreAttachedState(_context, _values[3]);
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[2];
        _values[0] = _saveState(_context);
        _values[1] = this.fileAndFolderChooser ? Boolean.TRUE : Boolean.FALSE;
        //_values[2] = saveAttachedState(_context, valueChangeListenerExpression);
        //_values[3] = saveAttachedState(_context, validatorExpression);
        return _values;
    }

    /**
     * This method handles the display of error messages.
     * @param summary The error message summary
     * @param detail The error message detail
     */
    public void displayAlert(String summary, String detail, 
            String [] summaryArgs, String [] detailArgs) {

	FacesContext context = FacesContext.getCurrentInstance();
	FacesMessage fmsg = createFacesMessage(summary, detail,
	    summaryArgs, detailArgs);
	context.addMessage(getClientId(context), fmsg);
    }
    
    /*
     * Implement this method so that it returns the DOM ID of the
     * HTML element which should receive focus when the filechooser
     * receives focus, and to which a component label should apply.
     * Usually, this is the first element that accepts input. For
     * the fileChooser this happens to be the lookIn text field.
     *
     * @param context The FacesContext for the request
     * @return The client id, also the JavaScript element id
     */
     // Looks like implementing the ComplexComponent interface comes in the way 
     // of individual elements setting focus. Commenting it out for now. 
     // There is a prolem with the textField component in that it does not
     // maintain focus.

     /*
     public String getPrimaryElementID(FacesContext context) {
         return getLookInTextField().getClientId(context);
     }
     */

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Protected Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // private convenience methods. Some may be useful for
    // general utilities.
    // 

    /**
     * Log an error - only used during development time.
     */
    void log(String s) {
	if (LogUtil.fineEnabled(FileChooser.class)) {
            LogUtil.fine(FileChooser.class, s);
        }
    }

    /** <p>Convience function to get the current Theme.</p> */

    private String getEncodedSelections() {

	Object value = getValue();
	Converter converter = getConverter();
	if (converter == null) {
	    converter = getConverterFromValue(value);
	}
	String[] selections = null;
	try {
	    selections = convertValueToStringArray(
		FacesContext.getCurrentInstance(), converter, value);
	} catch (ConverterException ce) {
	    log("Failed to convert and encode initial selections.");
	}
	return encodeSelections(selections, getEscapeChar(),
	    getDelimiterChar());
    }

    private Theme getTheme() {
        return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }

    /**
     * This method creates a FacesMessage.
     * @param summary The error message summary
     * @param detail The error message detail
     */
    private FacesMessage createFacesMessage(String summary,
	    String detail, String [] summaryArgs, String [] detailArgs) {

	FacesContext context = FacesContext.getCurrentInstance();
	Theme theme = ThemeUtilities.getTheme(context);
	String summaryMsg = theme.getMessage(summary, summaryArgs);
	String detailMsg = theme.getMessage(detail, detailArgs);
	FacesMessage fmsg = new FacesMessage(
	    FacesMessage.SEVERITY_ERROR,
	    summaryMsg, detailMsg);
	return fmsg;
    }

    /**
     * Encode the selectedFileField value.
     * This is an escaped, comma separated list of selected
     * entries. This method is used to format an initial
     * value for the selectedFileField. It is only used
     * on initial display (?), which is true if processDecodes
     * has not been called. If processDecodes has not been called format and 
     * set the value of the selectedFileField. Do this
     * by setting the submittedValue not by the setValue method.
     *
     * This can get complicated since converters may be necessary
     * to convert the values to Strings.
     */
    private String encodeSelections(String[] selections,
	    String escapeChar, String delimiter) {

	if (selections == null || selections.length == 0) {
	    return null;
	}
	StringBuffer sb = new StringBuffer(
		escapeString(selections[0], escapeChar, delimiter));

	for (int i = 1; i < selections.length; ++i) {
	    sb.append(delimiter);
	    sb.append(escapeString(selections[i], escapeChar, delimiter));
	}

	return sb.toString();
    }

    private String[] decodeSelections(String selections,
	    String escapeChar, String delimiter) {

	if (selections == null) {
	    return null;
	}
        
	// This has to be done character by character
	//
	char del = delimiter.toCharArray()[0];
	char esc = escapeChar.toCharArray()[0];
	char[] charArray = selections.toCharArray();
	int escseen = 0;
	int ind = 0;
	int j = 0;
	ArrayList strArray = new ArrayList();
	for (int i = 0; i < selections.length(); ++i) {
	    if (charArray[i] == del) {
		if (escseen % 2 == 0) {
		    strArray.add(ind++,
			unescapeString(selections.substring(j, i),
				escapeChar, delimiter));
		    j = i + 1;
		}
	    }
	    if (charArray[i] == esc) {
		++escseen;
		continue;
	    } else {
		escseen = 0;
	    }
	}
	// Capture the last substring
	//
	strArray.add(ind, unescapeString(selections.substring(j),
		escapeChar, delimiter));

	return (String[])strArray.toArray(new String[strArray.size()]);
    }

    // These should be made utility methods but I'm not sure
    // where they should go.
    //
    private String escapeString(String s, String escapeChar, String delimiter) {

	// Replace all escapeChar's with two escapeChar's
	// But if the escaape char is "\" need to escape it 
	// in the regex since it is a special character.
	//
	String escaped_escapeChar = escapeChar;
	if (escapeChar.equals("\\")) {
	    escaped_escapeChar = escapeChar + escapeChar;
	}
	String regEx = escaped_escapeChar;
	String s0 = s.replaceAll(regEx, escaped_escapeChar +
		escaped_escapeChar);

	// Replace all delimiter characters with the
	// escapeChar and a delimiter.
	//
	regEx = delimiter;
	s0 = s0.replaceAll(regEx, escaped_escapeChar + delimiter);

	return s0;
    }

    private String unescapeString(String s, String escapeChar,
	String delimiter) {

	// Replace every escaped delimiter with just the
	// delimter.
	// But if the escaape char is "\" need to escape it 
	// in the regex since it is a special character.
	//
	String escaped_escapeChar = escapeChar;
	if (escapeChar.equals("\\")) {
	    escaped_escapeChar = escapeChar + escapeChar;
	}
	String regEx = escaped_escapeChar + delimiter;
	String s0 = s.replaceAll(regEx, delimiter);


	// Replace every two occurrences of the escape char  with one.
	// 
	regEx = escaped_escapeChar + escaped_escapeChar;
	s0 = s0.replaceAll(regEx, escaped_escapeChar);

	return s0;
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
        if (name.equals("selected")) {
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
        if (name.equals("selected")) {
            super.setValueExpression("value", binding);
            return;
        }
        super.setValueExpression(name, binding);
    }
    
    // Hide required 
    @Property(name="required", isHidden=true, isAttribute=false)
    public boolean isRequired() {
        return super.isRequired();
    }
    
    /**
     * <p>Use this attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If this attribute
     * is set to false, the HTML code for the component is present in the 
     * page, but the component is hidden with style attributes. By default,
     * this attribute is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    @Property(name="visible", displayName="Visible", category="Behavior")
    private boolean visible = true;
    private boolean visible_set = false;

    /**
     * <p>Use this attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If this attribute
     * is set to false, the HTML code for the component is present in the 
     * page, but the component is hidden with style attributes. By default,
     * this attribute is set to true, so
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
        return this.visible;
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
     * <p>Describes the position of this element in the tabbing order
     * of the current document.
     * Tabbing order determines the sequence in which elements receive
     * focus when the tab key is pressed. The value must be an integer
     * between 0 and 32767.</p>
     */
    @Property(name="tabIndex", displayName="Tab Index", category="Accessibility", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int tabIndex = Integer.MIN_VALUE;
    private boolean tabIndex_set = false;

    /**
     * <p>Describes the position of this element in the tabbing order
     * of the current document.
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
    
    // Hide value
    @Property(name="value", isHidden=true, isAttribute=false)
    public Object getValue() {
        return super.getValue();
    }

    /**
     * <p>Set this attribute to true to sort from the highest value to lowest value,
     * such as Z-A for alphabetic, or largest file to smallest for sorting
     * on file size. The default of this attribute to sort in ascending order.
     * </p>
     */
    @Property(name="descending", displayName="Descending", category="Advanced")
    private boolean descending = false;
    private boolean descending_set = false;

    public boolean isDescending() {
        if (this.descending_set) {
            return this.descending;
        }
        ValueExpression _vb = getValueExpression("descending");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result != null) {
                return ((Boolean) _result).booleanValue();
            }
        }	
        // Return the default value.
        boolean defaultValue = descending;
        try {
            defaultValue = Boolean.parseBoolean(getTheme().getMessage(
                    "filechooser.descending")); //NOI18N	       	
        } catch (Exception e) {
            log("Failed to obtain the default value from the theme." +
                    "Using the default value " + defaultValue + ".");            
        }
        return defaultValue;
    }

    /**
     * <p>Set descending to true to sort from the highest value to lowest value,
     * such as Z-A for alphabetic, or largest file to smallest for sorting
     * on file size. The default of this attribute to sort in ascending order.</p>
     * @see #isDescending()
     */
    public void setDescending(boolean descending) {
        this.descending = descending;
        this.descending_set = true;
    }

    /**
     * <p>Indicates  that activation of this component by the user is not 
     * currently permitted.</p>
     */
    @Property(name="disabled", displayName="Disabled", category="Behavior")
    private boolean disabled = false;
    private boolean disabled_set = false;

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
     * <p>Indicates  that activation of this component by the user is not 
     * currently permitted.</p>
     * @see #isDisabled()
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
        this.disabled_set = true;
    }

    /**
     * <p>Use this attribute to configure the file chooser as a folder chooser.
     * Set the value to true for a folder chooser or false for a file
     * chooser. The default value is false.</p>
     */
    @Property(name="folderChooser", displayName="Folder Chooser", category="Appearance")
    private boolean folderChooser = false;
    private boolean folderChooser_set = false;

    private boolean _isFolderChooser() {
        if (this.folderChooser_set) {
            return this.folderChooser;
        }
        ValueExpression _vb = getValueExpression("folderChooser");
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
     * <p>Use this attribute to configure the file chooser as a folder chooser.
     * Set the value to true for a folder chooser or false for a file
     * chooser. The default value is false.</p>
     * @see #isFolderChooser()
     */
    private void _setFolderChooser(boolean folderChooser) {
        this.folderChooser = folderChooser;
        this.folderChooser_set = true;
    }

    /**
     * <p>Use this attribute to specify the initial folder to display in the
     * Look In text field and display the contents of the lookin folder.
     * Only <code>java.io.File</code> or <code>java.lang.String</code> objects 
     * can be bound to this attribute.</p>
     */
    @Property(name="lookin", displayName="Lookin", category="Data", editorClassName="com.sun.rave.propertyeditors.StringPropertyEditor")
    private Object lookin = null;

    public Object getLookin() {
        if (this.lookin != null) {
            return this.lookin;
        }
        ValueExpression _vb = getValueExpression("lookin");
        if (_vb != null) {
            return (Object) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Use this attribute to specify the initial folder to display in the
     * Look In text field and display the contents of the lookin folder.
     * Only <code>java.io.File</code> or <code>java.lang.String</code> objects 
     * can be bound to this attribute.</p>
     * @see #getLookin()
     */
    public void setLookin(Object lookin) {        
        this.lookin = lookin;                
    }

    // model
    /**
     * <p>Specifies the model associated with the FileChooser. The model
     * provides the file chooser with content displayed in the file
     * chooser's list. It provides other services as defined in<code>com.sun.webui.jsf.model.ResourceModel</code>.
     * If the model attribute is not assigned a value, a FileChooserModel is 
     * used as the ResourceModel instance. A value binding assigned to this
     * attribute must return an instance of ResourceModel.</p>
     */
    @Property(name="model", displayName="Model", shortDescription="The model associated with the filechooser", isHidden=true, isAttribute=false)
    private com.sun.webui.jsf.model.ResourceModel model = null;

    private com.sun.webui.jsf.model.ResourceModel _getModel() {
        if (this.model != null) {
            return this.model;
        }
        ValueExpression _vb = getValueExpression("model");
        if (_vb != null) {
            return (com.sun.webui.jsf.model.ResourceModel) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Specifies the model associated with the FileChooser. The model
     * provides the file chooser with content displayed in the file
     * chooser's list. It provides other services as defined in<code>com.sun.webui.jsf.model.ResourceModel</code>.
     * If the model attribute is not assigned a value, a FileChooserModel is 
     * used as the ResourceModel instance. A value binding assigned to this
     * attribute must return an instance of ResourceModel.</p>
     * @see #getModel()
     */
    public void setModel(com.sun.webui.jsf.model.ResourceModel model) {
        this.model = model;
    }

    /**
     * <p>Set this attribute to true to allow multiple files or folders
     * to be selected from the list. The default for this attribute is
     * false, which allows only one item to be selected.</p>
     */
    @Property(name="multiple", displayName="Multiple", category="Appearance")
    private boolean multiple = false;
    private boolean multiple_set = false;

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
     * <p>Set this attribute to true to allow multiple files or folders 
     * to be selected from the list. The default for this attribute
     * false, which allows only one item to be selected.</p>
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
     * <p>The number of items to display in the listbox. The value must be
     * greater than or equal to one. The default value is 12. Invalid values
     * are ignored and the value is set to 12.</p>
     */
    @Property(name="rows", displayName="Rows", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int rows = 12;
    private boolean rows_set = false;

    public int getRows() {                
        if (this.rows_set) {
            return this.rows;
        }               
        ValueExpression _vb = getValueExpression("rows");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result != null && ((Integer) _result).intValue() > 0) {
                return ((Integer) _result).intValue();
            }
        }
        
        // Return the default.
        int defaultRows = rows;                                
        try {
	    defaultRows = Integer.parseInt(getTheme().getMessage(
                    "filechooser.rows")); //NOI18N
            if (defaultRows < 1) {
                defaultRows = rows;
            }
        } catch (Exception e) {
            log("Failed to obtain the default value from the theme." +
                    "Using the default value " + defaultRows + ".");           
        }
        return defaultRows;       
    }

    /**
     * <p>The number of items to display in the listbox. The value must be
     * greater than or equal to one. The default value is 12. Invalid values
     * are ignored and the value is set to 12.</p>
     * @see #getRows()
     */
    public void setRows(int rows) {
        if (rows < 1) {
            throw new IllegalArgumentException(getTheme().getMessage(
                    "filechooser.invalidRows"));                    
        }
        
        this.rows = rows;
        this.rows_set = true;
    }

    /**
     * <p>This attribute represents the value of the fileChooser. Depending on 
     * the value of the <code>folderChooser</code>
     * attribute, the value of the <code>selected</code>
     * attribute can consist of 
     * selected files or folders from the listbox and/or paths to files 
     * or folders entered into the Selected File field.</p><p>If the <code>multiple</code> attribute is true, the <code>selected</code> attribute must be bound to
     * one of the following:<ul><li><code>java.io.File[]</code></li><li><code>java.lang.String[]</code></li><li>a <code>java.util.List[]</code> such as <code>java.util.ArrayList</code>, or  <code>java.util.LinkedList</code>, or <code>java.util.Vector</code> containing instances of <code>java.io.File</code> or <code>java.lang.String</code>.</li></ul><p>
     * If the <code>multiple</code> attribute is false, 
     * the <code>selected</code>
     * attribute must
     * be bound to one of the following:</p><ul><li><code>java.io.File</code></li><li><code>java.lang.String</code></li></ul></p><p>
     * If a type other than these is contained in a list type or bound 
     * directly to the <code>selected</code>
     * attribute, then you must specify a converter with the <code>converter</code>
     * attribute.</p></p>
     */
    @Property(name="selected", displayName="Selected", shortDescription="The selected file(s) or folder(s) name.", category="Data", editorClassName="com.sun.rave.propertyeditors.binding.ValueBindingPropertyEditor")
    public Object getSelected() {
        return getValue();
    }

    /**
     * <p>This attribute represents the value of the fileChooser. Depending on 
     * the value of the <code>folderChooser</code>
     * attribute, the value of the <code>selected</code>
     * attribute can consist of 
     * selected files or folders from the listbox and/or paths to files 
     * or folders entered into the Selected File field.</p><p>If the <code>multiple</code> attribute is true, the <code>selected</code> attribute must be bound to
     * one of the following:<ul><li><code>java.io.File[]</code></li><li><code>java.lang.String[]</code></li><li>a <code>java.util.List[]</code> such as <code>java.util.ArrayList</code>, or  <code>java.util.LinkedList</code>, or <code>java.util.Vector</code> containing instances of <code>java.io.File</code> or <code>java.lang.String</code>.</li></ul><p>
     * If the <code>multiple</code> attribute is false, 
     * the <code>selected</code>
     * attribute must
     * be bound to one of the following:</p><ul><li><code>java.io.File</code></li><li><code>java.lang.String</code></li></ul></p><p>
     * If a type other than these is contained in a list type or bound 
     * directly to the <code>selected</code>
     * attribute, then you must specify a converter with the <code>converter</code>
     * attribute.</p></p>
     * @see #getSelected()
     */
    public void setSelected(Object selected) {
        setValue(selected);
    }

    /**
     * <p>Field used to sort the list of files. Valid values are:
     * <ul><li>alphabetic - sort alphabetically</li>
     * <li>size - sort by file size</li>
     * <li>time - sort by last modified date</li></ul>
     * <p>Note that these values are case sensitive. By default, the list is sorted alphabetically.</p></p>
     */
    @Property(name="sortField", displayName="Sort Field", category="Advanced")
    private String sortField = "alphabetic";
    private boolean sortField_set = false;

    public String getSortField() {
        if (this.sortField_set) {
            return this.sortField;
        }
        ValueExpression _vb = getValueExpression("sortField");
        if (_vb != null) {
            String _result = (String)_vb.getValue(getFacesContext().getELContext());
            if (_result != null || _result.trim().length() > 0) {
                _result = _result.trim();
                if (_result.equals(ALPHABETIC) || _result.equals(SIZE) ||
                        _result.equals(LASTMODIFIED)) {
                    return _result;    
                }
            }
        }  
        
        // Return the default value.
        String defaultValue = getTheme().getMessage("filechooser.sortField"); //NOI18N
        if (defaultValue == null || defaultValue.length() < 1) {
            defaultValue = sortField;
	    log("Failed to obtain the default value from the theme." +
                    "Using the default value " + defaultValue + ".");
        }
        return defaultValue;
    }

    /**
     * <p>Field used to sort the list of files. Valid values are:
     * <ul><li>alphabetic - sort alphabetically</li>
     * <li>size - sort by file size</li>
     * <li>time - sort by last modified date</li></ul>
     * <p>Note that these values are case sensitive. By default, the list is sorted alphabetically.</p></p>
     * @see #getSortField()
     */
    public void setSortField(String sortField) {
        if (sortField == null) {
            throw new IllegalArgumentException(getTheme().getMessage(
                        "filechooser.nullSortField"));  
        }
        sortField = sortField.trim();
        if (sortField.length() < 1) {
            throw new IllegalArgumentException(getTheme().getMessage(
                        "filechooser.whitespaceSortField"));            
        }        
        if (!(sortField.equals(ALPHABETIC) || sortField.equals(SIZE) ||
                sortField.equals(LASTMODIFIED))) {
            throw new IllegalArgumentException(getTheme().getMessage(
                        "filechooser.invalidSortField"));            
        }                    
        this.sortField = sortField;
        this.sortField_set = true;
    }

    /**
     * <p>CSS style properties to be applied to the outermost HTML 
     * element when this component is rendered.</p>
     */
    @Property(name="style", displayName="CSS Style(s)", category="Appearance", editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;

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
     * <p>CSS style properties to be applied to the outermost HTML 
     * element when this component is rendered.</p>
     * @see #getStyle()
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * <p>CSS style class or classes to be applied to the outermost HTML element when this
     * component is rendered.</p>
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;

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
     * <p>Restore the state of this component.</p>
     */
    private void _restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.descending = ((Boolean) _values[1]).booleanValue();
        this.descending_set = ((Boolean) _values[2]).booleanValue();
        this.disabled = ((Boolean) _values[3]).booleanValue();
        this.disabled_set = ((Boolean) _values[4]).booleanValue();
        this.folderChooser = ((Boolean) _values[5]).booleanValue();
        this.folderChooser_set = ((Boolean) _values[6]).booleanValue();
        this.lookin = (Object) _values[7];
        this.model = (com.sun.webui.jsf.model.ResourceModel) _values[8];
        this.multiple = ((Boolean) _values[9]).booleanValue();
        this.multiple_set = ((Boolean) _values[10]).booleanValue();
        this.readOnly = ((Boolean) _values[11]).booleanValue();
        this.readOnly_set = ((Boolean) _values[12]).booleanValue();
        this.rows = ((Integer) _values[13]).intValue();
        this.rows_set = ((Boolean) _values[14]).booleanValue();
        this.sortField = (String) _values[15];
        this.style = (String) _values[16];
        this.styleClass = (String) _values[17];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    private Object _saveState(FacesContext _context) {
        Object _values[] = new Object[18];
        _values[0] = super.saveState(_context);
        _values[1] = this.descending ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.descending_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.disabled ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.disabled_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.folderChooser ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.folderChooser_set ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.lookin;
        _values[8] = this.model;
        _values[9] = this.multiple ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = this.multiple_set ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.readOnly ? Boolean.TRUE : Boolean.FALSE;
        _values[12] = this.readOnly_set ? Boolean.TRUE : Boolean.FALSE;
        _values[13] = new Integer(this.rows);
        _values[14] = this.rows_set ? Boolean.TRUE : Boolean.FALSE;
        _values[15] = this.sortField;
        _values[16] = this.style;
        _values[17] = this.styleClass;
        return _values;
    }
}
