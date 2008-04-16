/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
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

@JS_NS@._dojo.provide("@JS_NS@._html.fileChooser");

@JS_NS@._dojo.require("@JS_NS@._base.common");
@JS_NS@._dojo.require("@JS_NS@._base.proto");

/** 
 * @class This class contains functions for fileChooser components.
 * <p>
 * The filechooser has several intermediate actions in addition to the selection
 * of one or more files of folders. For example, there are actions that are 
 * initiated by buttons and actions initiated by mouse clicks and key presses.
 * Some events generated from key presses and mouse clicks behave like 
 * accelerators for the button actions.
 * </p><p>
 * Some actions are client side only actions, such as placing a
 * selected file or folder into the selected file or folder field.
 * </p><p>
 * The server side events and how they are generated.
 * </p><p><pre>
 * 1. moveup - the moveup button is clicked
 * 2. openfolder - select a folder from the list and the openfolder
 *    button is clicked or press the return key with the selection in
 *    focus or double click the selection
 * 3. sort - a sort selection is made from the sort dropdown menu
 * 4. refresh the list - set focus in the look in field and press the return key
 * 5. change the directory listing - type a new value in the look in field
 *    and press the return key
 *    The directory listing is also changed when a folder is typed into
 *    the selected file field, and submitted.
 * 6. filter the list - the focus is placed in the filter field and the
 *    return key is pressed
 * 7. change filter value - a new value is entered into the filter field 
 *    and the return key is pressed
 * 8. commit the changes - the button assigned as the chooseButton is
 *    is clicked, or programmtically clicked when the return key is 
 *    pressed in the select file field or a file selection is double clicked.
 * </pre></p><p>
 * Mouse clicks and return key activations.
 * </p><p><pre>
 * - The mouse clicks and key presses that correspond to actions are
 *   exectuted by "clicking" the corresponding button action programmatically.
 *   For example, action #2 when activated by double clicking or the
 *   return key, programmatically clicks the open folder button.
 *
 * - Action #4 and #6 explcitly submit the form
 *
 * - Action #8 programmatically clicks the assigned chooserButton.
 * </pre></p><p>
 * Selections are made and submitted in the following ways
 * </p><p><pre>
 *   File chooser or folder chooser
 *
 *   - One or more absolute or relative paths are typed or placed
 *   into the select file or folder field the return key is pressed.
 *
 *   - An external submit button submits the form and there are selections
 *   in the selected file or folder field.
 *
 *   File chooser 
 *
 *   - A file selection is double clicked.
 * </pre></p><p>
 * Client side selections
 * </p><p><pre>
 * - A file or folder is single clicked and it is entered into the
 *   selected file or folders field, depending on whether the chooser
 *   is a file or folder chooser.
 *
 * - When a directory is selected and the open folder button is clicked
 *   the entry is set as the look in field value and the form is submitted.
 * 
 * - When the move up button is clicked the parent directory is placed
 *   into the look in field and the form is submitted.
 * </pre></p>
 * @static
 * @private
 */
@JS_NS@._html.fileChooser = {
    // FIXME: Note that the dependence on literal client id's is not sufficient
    // if these components are developer defined facets. The actual
    // literal id's cannot be guaranteed.

    /**
     * This function is used to initialize HTML element properties with Object
     * literals.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {String} id The element id.
     * @config {String} chooserType 
     * @config {String} parentFolder 
     * @config {String} separatorChar 
     * @config {String} escapeChar 
     * @config {String} delimiter 
     * @config {String} currentDir
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _init: function(props) {
        var message = "Cannot initialize fileChooser.";
        if (props == null || props.id == null) {
            console.debug(message); // See Firebug console.
            return false;
        }
        var domNode = document.getElementById(props.id);
        if (domNode == null) {
            console.debug(message); // See Firebug console.
            return false;
        }

        // This is not a user defined facet. It is created dynamically
        // if the enter key is pressed in the selected file field.
        // This is the expected form of the request paramter name
        // in the renderers decode method.
        //
        // Consider passing the name as a parameter, or some other
        // more well defined manner.
        var idPrefix = props.id;
        var index = props.id.lastIndexOf(':');
        if (index != -1) {
             idPrefix += props.id.substring(index);
        }

        // Get HTML elements.
        var widget = @JS_NS@.widget.common.getWidget(idPrefix + "_lookinField");
        if (widget) {
            domNode.lookinfield = widget.getInputElement();
        }
        widget = @JS_NS@.widget.common.getWidget(idPrefix + "_filterField");
        if (widget) {
            domNode.filterfield = widget.getInputElement();
        }
        widget = @JS_NS@.widget.common.getWidget(idPrefix + "_selectedField");
        if (widget) {
            domNode.selectedfield = widget.getInputElement();
        }
        widget = @JS_NS@.widget.common.getWidget(idPrefix + "_listEntries");
        if (widget) {
            domNode.listentries = widget.getSelectElement();
        }
        widget = @JS_NS@.widget.common.getWidget(idPrefix + "_sortMenu");
        if (widget) {
            domNode.sortmenu = widget.getSelectElement();
        }
        domNode.upButton = document.getElementById(idPrefix + "_upButton");
        domNode.openFolderButton = document.getElementById(idPrefix + "_openButton");

        // HTML elements may not have been created, yet.
        if (domNode.lookinfield == null 
                || domNode.filterfield == null 
                || domNode.selectedfield == null 
                || domNode.upButton == null
                || domNode.openFolderButton == null 
                || domNode.listentries == null
                || domNode.sortmenu == null) {
            return setTimeout(function() {
                @JS_NS@._html.fileChooser._init(props);
            }, 10);
        }

        // Set given properties on domNode.
        @JS_NS@._base.proto._extend(domNode, props, false);

        // boolean identifying the chooser mode.
        domNode.folderChooser = (props.chooserType == "folderChooser");
        domNode.fileAndFolderChooser = (props.chooserType == "fileAndFolderChooser");
        domNode.chooseButton = null;
        domNode.selectionsId = idPrefix + "_selections";
        domNode.listOptions = domNode.listentries.options;

        // FIXME: This encoding needs to be generalized if this code is to
        // become more generic chooser-like.
        // In fact encoding entries this way in not ideal.
        // A more explicit typing needs to be developed if it is 
        // necessary, possible a data structure that maps type to entry.
        if (domNode.folderChooser) {
            domNode.chooser = 'folder';
        } else {
            domNode.chooser = 'file';
        }

        // Set public functions.
        domNode.enterKeyPressed = @JS_NS@._html.fileChooser.enterKeyPressed;
        domNode.handleDblClick = @JS_NS@._html.fileChooser.handleDblClick;
        domNode.handleOnChange = @JS_NS@._html.fileChooser.handleOnChange;
        domNode.openFolderClicked = @JS_NS@._html.fileChooser.openFolderClicked;
        domNode.moveUpButtonClicked = @JS_NS@._html.fileChooser.moveUpButtonClicked;
        domNode.setChooseButton = @JS_NS@._html.fileChooser.setChooseButton;
        domNode.getCurrentDirectory = @JS_NS@._html.fileChooser.getCurrentDirectory;
        domNode.getOptionElements = @JS_NS@._html.fileChooser.getOptionElements;
        domNode.getSelectedOptions = @JS_NS@._html.fileChooser.getSelectedOptions;
        domNode.isFolderChooser = @JS_NS@._html.fileChooser.isFolderChooser;
        domNode.isFolderSelected = @JS_NS@._html.fileChooser.isFolderSelected;
        domNode.getSelectionValue = @JS_NS@._html.fileChooser.getSelectionValue;
        domNode.getSelectionValueByIndex = @JS_NS@._html.fileChooser.getSelectionValueByIndex;
        domNode.getSelectionType = @JS_NS@._html.fileChooser.getSelectionType;
        domNode.getSelectionTypeByIndex = @JS_NS@._html.fileChooser.getSelectionTypeByIndex;
        domNode.getValueType = @JS_NS@._html.fileChooser.getValueType;
        domNode.itemSelected = @JS_NS@._html.fileChooser.itemSelected;
        domNode.getSelectedFolders = @JS_NS@._html.fileChooser.getSelectedFolders;
        domNode.getSelectedFiles = @JS_NS@._html.fileChooser.getSelectedFiles;
        domNode.getSelectedValuesByType = @JS_NS@._html.fileChooser.getSelectedValuesByType;
        domNode.setSelectedFieldValue = @JS_NS@._html.fileChooser.setSelectedFieldValue;
        domNode.clearSelections = @JS_NS@._html.fileChooser.clearSelections;
        domNode.deselectFolders = @JS_NS@._html.fileChooser.deselectFolders;
        domNode.deselectSelectionsByType = @JS_NS@._html.fileChooser.deselectSelectionsByType;
        domNode.setSelected = @JS_NS@._html.fileChooser.setSelected;
        domNode.clearSelectedField = @JS_NS@._html.fileChooser.clearSelectedField;
        domNode.armChooseButton = @JS_NS@._html.fileChooser.armChooseButton;
        domNode.getFileNameOnly = @JS_NS@._html.fileChooser.getFileNameOnly;
        domNode.setChooseButtonDisabled = @JS_NS@._html.fileChooser.setChooseButtonDisabled;

        // For supporting valid entries in look in field and filter field.
        //
        // It is imperative that the look in field and filter field
        // are never submitted if the value does not imply a valid action.
        // Not currently used.
        domNode.onFocus = @JS_NS@._html.fileChooser.onFocus;
        domNode.onBlur = @JS_NS@._html.fileChooser.onBlur;

        // Save the initial lookin and filter values.
        domNode.lastLookInValue = domNode.lookinfield.value;
        domNode.lastFilterValue = domNode.filterfield.value;
        domNode.lookinCommitted = false;
        domNode.filterCommitted = false;
        domNode.openFolderButton.setDisabled(true);

	if (props.currentFolder != null) {
	  if (props.parentFolder == props.currentFolder) {
	    domNode.upButton.setDisabled(true);
   	  } else {
	    domNode.upButton.setDisabled(false);
	  }
	}
        return true;
    },

    /**
     * Handler for enter key presses.
     * <p><pre>
     * - Enter key in LookInField
     * - Enter key in FilterField
     * - Enter key in SelectedFileField
     * - Enter key in Listbox with folder selection.
     * Submit the chooser from the various mouse clicks
     * key presses.
     * </pre></p><p>
     * Handles doubleclick on a file selection in the list box.
     * This is equivalent to an enter key press in the selected file field.
     * </p>
     *
     * @param {Node} element
     * @return {boolean} false to cancel JavaScript event.
     */
    enterKeyPressed: function(element) {
	// Return pressed in the list
	if (element.id == this.listentries.id) {
	    // If the selected item is a folder call the click method
	    // on the openFolderButton
	    if (this.isFolderSelected()) {
		// Done in openFolderClicked
		//
		//this.lookinfield.value = this.getSelectionValue();
		this.openFolderButton.click();
	    }
	    return false;
	}

	// The FileChooser's value must only change when selections
	// have been made, not from just intermediate operations.
	//
	// Enter key pressed in the selectedFileField
	// or dbl click in the list.
	if (this.selectedfield && element.id == this.selectedfield.id) {
	    var escapedSelections = this.selectedfield.value;
	    var selections = @JS_NS@._base.common._unescapeStrings(escapedSelections,
		    this.delimiter, this.escapeChar);

	    // If a choose button has been defined call its click method
	    // otherwise do nothing. This behavior allows the filechooser
	    // to behave like any other component on a page.
	    if (this.chooseButton) {
		// Make sure its enabled.
		this.chooseButton.setDisabled(false);
		this.chooseButton.click();
	    }
	    return false;
	}

	// Enter key pressed in the filter field
	// Call the open folder button's click method.
	// Since there is no JSF action for mouse clicks or key presses
	// overload the open folder action to ensure that the 
	// sort value is updated.
	if (element.id == this.filterfield.id) {
	    this.filterCommitted = true;
	    // Don't let "" get submitted.
	    var fv = this.filterfield.value;
	    if (fv == null || fv == "") {
		this.filterfield.value = this.lastFilterValue;
		return false;
	    }
	    this.lastFilterValue = fv;
	    this.clearSelections();
	    element.form.submit();
	    return false;
	}

	// Enter key pressed in the LookIn field.
	// Call the open folder button's click method.
	// Since there is no JSF action for mouse clicks or key presses
	// overload the open folder action to ensure that the 
	// look in value is updated. This is needed anyway to display
	// the new folder's content.
	if (element.id == this.lookinfield.id) {
	    this.lookinCommitted = true;
	    // Don't let "" get submitted.
	    var lv = this.lookinfield.value;
	    if (lv == null || lv == "") {
		this.lookinfield.value = this.lastLookInValue;
		return false;
	    }
	    this.lastLookInValue = lv;
	    this.clearSelections();
	    element.form.submit();
	    return false;
	}
	return false;
    },

    /**
     * In file chooser mode
     * <p><pre>
     *    - a file selection, call enterKeyPressed with selected file field
     *    - a folder selection, call open folder click handler
     * In folder chooser mode
     *    - a folder selection, call open folder click handler
     * </pre></p>
     * @return {boolean} true if successful; otherwise, false.
     */
    handleDblClick: function() {
	// Nothing selected. Not sure if this can happen since
	// doubleclick implies selection.
	if (!this.itemSelected()) {
	    return false; 
	}

	var fldrSelected = this.isFolderSelected();

	// If the selected item is a folder call the click method
	// on the openFolderButton
	if (fldrSelected) {
	    // Set the look in field, since the selected folder will be
	    // the new look in field value. Done in openFolderClicked.
	    // This only works now because values are full paths.
	    //
	    this.openFolderButton.click();
	    return true;
	}

	// doubleclick is not valid for file selections in a
	// folder chooser.
	// If a file chooser, this is equivalent to a return key
	// in the selected file field.
	if (this.isFolderChooser()) {
	    if (!fldrSelected) {
		return false;
	    }
	} else {
	    // file chooser
	    // double click a file in file chooser mode
	    if (!fldrSelected) {
		if (this.selectedfield) {
		    return this.enterKeyPressed(this.selectedfield);
		}

		// If a choose button has been defined call its click method
		// otherwise do nothing. This behavior allows the filechooser
		// to behave like any other component on a page.
		if (this.chooseButton) {
		    // Make sure its enabled.
		    //
		    this.chooseButton.setDisabled(false);
		    return this.chooseButton.click();
		}
	    }
	}
	return true;
    },

    /**
     * Set choose button disabled.
     *
     * @param {boolean} disabled
     * @return {boolean} true if successful; otherwise, false.
     */
    setChooseButtonDisabled: function(disabled) {
	if (this.chooseButton) {
	    this.chooseButton.setDisabled(disabled);
	}
        return true;
    },

    // Replaces entries in selectedFileField
    // Get the selected entries from the list box and place
    // them in the selected file field, as comma separated entries.
    //
    // Note that the listbox values are full paths and encoded with
    // a "folder" or "file" designation. They probably should
    // be relative paths. The open folder action now depends on the
    // fact that the value is a full path.
    // This will have an effect on the open folder
    // action when the selected value is placed into the look in field.
    // If relative paths are used for the values then
    // the relative path would need to be appended to the look in
    // field value.
    //
    // However it may be the case that the full paths are edited to
    // take off the last element in the full path and keep the 
    // full path list box entries. Full paths are generally more
    // convenient.
    //
    // Note that this handler should call any required handlers
    // needed by the list box, vs. placing the required listbox
    // handlers in a javascript statement as the value of the
    // onChange attribute.
    //
    // Note also that the SWAED guidelines say to place relavtive
    // paths into the selected file field, this probably means
    // just using the display name vs. the value. However note the
    // dependencies on the full paths as described above.

    /**
     * Handler placed on the list box onchange enent.
     * <p>
     * Place all currently selected entries in to the
     * selected file field. If the chooser mode is file, only
     * files are placed into the selected file field.
     * </p><p>
     * If the chooser mode is folder, only folders are placed in the
     * selected folder field.
     * </p><p>
     * If multiple selections are allowed the entries are separated
     * by the specified delimiter. Enteries are escaped appropriately
     * with the specified escape character.
     * </p>
     * @return {boolean} false to cancel JavaScript event.
     */
    handleOnChange: function() {
        var widget = @JS_NS@.widget.common.getWidget(this.listentries.id);
        if (widget) {
            widget._changed();
        }

	// If nothing is selected disable buttons.
	if (!this.itemSelected()) {
	    this.openFolderButton.setDisabled(true); 
	    if (this.selectedfield &&
		(this.selectedfield.value == null ||
		    this.selectedfield.value == '')) {
		this.setChooseButtonDisabled(true);
	    }
	    return false;
	}

	// This may not be sufficient.
	// The issue is, should a file be selectable in a folder
	// chooser, period. Are they disabled or read only ?
	// And ditto for a multiple selection in a file chooser.
	// Should a folder be selectable as a multiple selection
	// in a file chooser ?
	//
	// This could be made more efficient
	// by return both arrays at once and making only
	// one pass

	var folders = this.getSelectedFolders();
	var files = this.getSelectedFiles();
	var selections = null;

	// If a file chooser, deselect folders when mixed
	// with file selections and disable the openFolder button);
	if (this.fileAndFolderChooser) {
	    selections = new Array(files.length + folders.length);
	    var i = 0;
	    for (; i< files.length; i++) {
	        selections[i] = files[i];
	    } 
	    for (j=0; j< folders.length; j++) {
	        selections[i+j] = folders[j];
	    } 
	    if (files.length > 0) {
		this.openFolderButton.setDisabled(true);
	    } else if (folders.length > 1) {
		this.openFolderButton.setDisabled(true);
	    } else if ((files.length == 0) || (folders.length == 1)) {
		this.openFolderButton.setDisabled(false);
	    }
	} else if (!this.isFolderChooser()) {
	    if (files.length > 0) {
		this.openFolderButton.setDisabled(true);
		this.deselectFolders();
	    } else if (folders.length > 1) {
		this.openFolderButton.setDisabled(false);
		var index = this.listentries.selectedIndex;
		this.deselectFolders();
		this.setSelected(index, true);
		this.clearSelectedField();
                var widget = @JS_NS@.widget.common.getWidget(this.listentries.id);
                if (widget) {
                    widget._changed();
                }
	    } else if (folders.length == 1) {
		// Only allow one folder to be selected
		this.openFolderButton.setDisabled(false);
		this.clearSelectedField();
	    } else {
		this.openFolderButton.setDisabled(true);
		this.clearSelectedField();
	    }
	    selections = files;
	} else {
	    // If a folder chooser allow more than one folder
	    // to be selected
	    selections = folders;
	    if (selections.length == 1) {
		this.openFolderButton.setDisabled(false);
	    } else {
		this.openFolderButton.setDisabled(true);
	    }
	}

	// Make sure the hidden select option array is up
	// to date in case there isn't a selectedFileField.
	if (!this.setSelectedFieldValue(selections)) {
	    @JS_NS@._base.common._createSubmittableArray(
                this.selectionsId, this.listentries.form, null, selections);
	}

	var flag = ((selections!= null) && (selections.length > 0));
	this.armChooseButton(flag);
	return false;
    },

    /**
     * Clear selected field.
     *
     * @return {boolean} true if successful; otherwise, false.
     */
    clearSelectedField: function() {
	if (this.selectedfield) {
	    this.selectedfield.value = '';
	}
        return true;
    },

    /**
     * This function is the event handler for the onclick event
     * of the openFolder button.
     *
     * @return {boolean} true if successful; otherwise, false.
     */
    openFolderClicked: function() {
	if (!this.isFolderSelected()) {
	    return false;
	}
	this.clearSelectedField();

	// Only works because the value is a full path.
	this.lookinfield.value = this.getSelectionValue();
	return true;
    },

    /**
     * Test if folder is selected.
     *
     * @return {boolean} true if folder is selected.
     */
    isFolderSelected: function() {
	return this.getSelectionType() == 'folder';
    },

    /**
     * This function is the event handler for the moveUp button.
     * Set the look in field to contain the parent or move up directory.
     * This is imperative. Be careful of the corner case when the 
     * look in field is already the root directory.
     * @return {boolean} true if successful; otherwise, false.
     */
    moveUpButtonClicked: function() {
	this.clearSelections();
	this.lookinfield.value = this.parentFolder;
        return true;
    },

    /**
     * The values of the list options are encoded as
     * <p>
     * <type>=<value>
     * </p><p>
     * Where type is one of "file" or "folder"
     * </p>
     * @return {String} The selection value.
     */
    getSelectionValue: function() {
	var index = this.listentries.selectedIndex;
	return this.getSelectionValueByIndex(index);
    },

    /**
     * Get selection value by index.
     *
     * @return {String}The selection value.
     */
    getSelectionValueByIndex: function(index) {
	var selection = this.listOptions[index].value;
	var i = selection.indexOf('=', 0);
	if (i < 0) {
	    return null;
	}
	if (i != 0) {
	    i = i + 1;
	}
	return selection.substring(i, selection.length); 
    },

    /**
     * Get selection type.
     *
     * @return {String} The selection type.
     */
    getSelectionType: function() {
	var index = this.listentries.selectedIndex;
	return this.getSelectionTypeByIndex(index);
    },

    /**
     * Get selection type by index.
     *
     * @return {String} The selection type.
     */
    getSelectionTypeByIndex: function(index) {
	var selection = this.listOptions[index].value;
	return this.getValueType(selection);
    },

    /**
     * Get value type.
     *
     * @return {String} The value type.
     */
    getValueType: function(value) {
	var i = value.indexOf('=', 0);
	if (i <= 0) {
	    return null;
	}
	var type = value.substring(0, i); 
	return type;
    },

    /**
     * Test if folder chooser.
     *
     * @return {boolean} true if folder chooser.
     */
    isFolderChooser: function() {
	return this.folderChooser;
    },

    /**
     * Get selected item.
     *
     * @return {String} The selected item.
     */
    itemSelected: function() {
	return (this.listentries.selectedIndex != -1);
    },

    /**
     * Get selected folders.
     *
     * @return {Array} An array of selected folders.
     */
    getSelectedFolders: function() {
	return this.getSelectedValuesByType('folder');
    },

    /**
     * Get selected files.
     *
     * @return {Array} An array of selected files.
     */
    getSelectedFiles: function() {
	return this.getSelectedValuesByType('file');
    },

    /**
     * Return all selected options by type, file or folder.
     *
     * @param {String} type
     * @return {Array} An array of selected values.
     */
    getSelectedValuesByType: function(type) {
	var selections = new Array();
	var i = 0;
	for (var j = 0; j < this.listOptions.length; j++) {
	    if (this.listOptions[j].selected){
		if (this.getSelectionTypeByIndex(j) == type) {
		    selections[i++] = this.getSelectionValueByIndex(j);
		} 
	    } 
	} 
	return selections;
    },

    /**
     * Format the selected file field as a comma separated list.
     *
     * @param {Array} selections
     * @return {boolean} true if successful; otherwise, false.
     */
    setSelectedFieldValue: function(selections) {
	var value;
	if (this.selectedfield == null) {
	    return false;
	}

	if (selections == null || selections.length == 0) {
	    return false;
	} else {
	    value = @JS_NS@._base.common._escapeString(
                this.getFileNameOnly(selections[0]), this.delimiter,
                this.escapeChar);
	}

	for (var j = 1; j < selections.length; j++) {
	    value = value + ',' + 
                @JS_NS@._base.common._escapeString(
                    this.getFileNameOnly(selections[j]), this.delimiter,
                    this.escapeChar);
	} 

	if (value != null && value != '') { 
	    this.selectedfield.value = value;
	} else { 
	    this.selectedfield.value = '';
	} 
	return true;
    },

    /**
     *
     * @param {Node} element
     * @return {boolean} true if successful; otherwise, false.
     */
    onFocus: function(element) {
	if (element.id == this.lookinfield.id) {
	    this.lookinCommitted = false;
	    this.lastLookInValue = this.lookinfield.value;
	} else if (element.id == this.filterfield.id) {
	    this.filterCommitted = false;
	    this.lastFilterValue = this.filterfield.value;
	}
	return true;
    },

    /**
     *
     * @param {Node} element
     * @return {boolean} true if successful; otherwise, false.
     */
    onBlur: function(element) {
	if (element.id == this.lookinfield.id) {
	    if (this.lookinCommitted == false) {
		this.lookinfield.value = this.lastLookInValue;
	    }
	} else if (element.id == this.filterfield.id) {
	    if (this.filterCommitted == false) {
		this.filterfield.value = this.lastFilterValue;
	    }
	}
	return true;
    },

    /**
     * Clear the selections whenever the selectedFileField is cleared.
     *
     * @return {boolean} true if successful; otherwise, false.
     */
    clearSelections: function() {
	var i = 0;
	for (var j = 0; j < this.listOptions.length; j++) {
	    if (this.listOptions[j].selected){
		this.listOptions[j].selected = false;
	    } 
	} 
	// call listbox.changed to update the
	// private state
        var widget = @JS_NS@.widget.common.getWidget(this.listentries.id);
        if (widget) {
            widget._changed();
        }
	if (this.selectedfield != null) {
	    this.selectedfield.value = "";
	}
        return true;
    },

    /**
     * Set selected.
     *
     * @return {boolean} true if successful; otherwise, false.
     */
    setSelected: function(index, torf) {
	this.listOptions[index].selected = torf;
        var widget = @JS_NS@.widget.common.getWidget(this.listentries.id);
        if (widget) {
            return widget._changed();
        }
        return false;
    },

    /**
     * Deselect folders.
     *
     * @return {boolean} true if successful; otherwise, false.
     */
    deselectFolders: function() {
	return this.deselectSelectionsByType('folder');
    },

    /**
     * Deselect by type.
     *
     * @param {String} type
     * @return {boolean} true if successful; otherwise, false.
     */
    deselectSelectionsByType: function(type) {
	for (var j = 0; j < this.listOptions.length; j++) {
	    if (this.listOptions[j].selected &&
		    this.getValueType(this.listOptions[j].value) == type) {
		this.listOptions[j].selected = false;
	    } 
	} 
        var widget = @JS_NS@.widget.common.getWidget(this.listentries.id);
        if (widget) {
            return widget._changed();
        }
        return false;
    },

    /**
     * Enable the choose button.
     *
     * @param {boolean} flag
     * @return {boolean} true if successful; otherwise, false.
     */
    armChooseButton: function(flag) {
	var disabled = true;
	if (this.selectedfield == null) {
	    disabled = flag;
	} else if (this.selectedfield.value != null 
	    && this.selectedfield.value != '') {
	        disabled = false;
	} 
	return this.setChooseButtonDisabled(disabled);
    },

    // Note that this is the only way that the file chooser can control
    // the submit of selected values. If this button is not set then
    // only an external submit button can submit the selections.
    // That means that if there is no chooser button assigned, double clicking
    // a file entry or hitting the return key in the selected file field
    // will NOT submit the values.
    //
    // This "feature" may become configurable.

    /**
     * convenience function to allow developers to disable their
     * chooser button when no entries from the filechooser are
     * selected. This function is not yet complete.
     *
     * @param {String} buttonId
     * @return {boolean} true if successful; otherwise, false.
     */
    setChooseButton: function(buttonId) {
	this.chooseButton = document.getElementById(buttonId);
	// See if there are selections and if so 
	// enable the button. Needs to be after the assignment
	var selections = document.getElementById(this.selectionsId);
	var disabled = true;
	if ((selections != null) && (selections.length > 0)) {
	    disabled = false;
	}
	return this.armChooseButton(disabled);
    },

    /**
     * Convenience function to get the current directory without 
     * going to the server.
     *
     * @return {String} The current directory.
     */
    getCurrentDirectory: function() {
	if (this.lookinfield) {
	    return this.lookinfield.value;
	}
        return null;
    },

    /**
     * Convenience function returning the list of option elements.
     *
     * @return {Array} An array of list options.
     */
    getOptionElements: function() {
	return this.listOptions;
    },

    /**
     * Convenience function to get the list of selected option elements
     * Return an array of selected values or a 0 length array if there
     * are no selections.
     *
     * @return {Array} An array of selected options.
     */
    getSelectedOptions: function() {
	var selections = new Array();
	var i = 0;
	for (var j = 0; j < this.listOptions.length; j++) {
	    if (this.listOptions[j].selected){
		selections[i++] = this.getSelectionValueByIndex(j);
	    } 
	} 
	return selections;
    },

    /**
     * Convenience function to get the file or folder name when 
     * the entire path name is supplied.
     *
     * @param {String} absoluteFileName
     * @return {String} The file name.
     */
    getFileNameOnly: function(absoluteFileName) {
        arrayOfPaths = absoluteFileName.split(this.separatorChar);
	justTheFileName = arrayOfPaths[arrayOfPaths.length -1];
        return justTheFileName;
    }
};
