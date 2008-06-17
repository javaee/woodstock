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

package com.sun.webui.jsf.example.index;        

import com.sun.webui.jsf.example.index.AppData;

/**
 * This class serves as a data wrapper for the Table component in the
 * Example Application Index page.
 */
public class TableData {  
   
    // Fill data for each example in an array object.            
    protected static final AppData[] data = {
        new AppData("index_buttonName","index_buttonConcepts", "showButton",
                new String[] {
                    "button/Button.xhtml",
                    "button/ButtonResults.xhtml",
                    "button/ButtonBackingBean.java"}
        ),
        new AppData("index_cbrbName","index_cbrbConcepts", "showCbRbIndex",
                new String[] {
                    "cbrb/index.xhtml",
                    "cbrb/clientsideCheckbox.xhtml",
                    "cbrb/checkboxRadiobutton.xhtml",
                    "cbrb/clientsideRadioButton.xhtml",
                    "cbrb/clientsideCbRbGroup.xhtml",
                    "cbrb/checkboxRadiobuttonResults.xhtml",
                    "cbrb/CheckboxRadiobuttonBackingBean.java"}
        ),
        new AppData("index_labelName","index_labelConcepts", "showLabel",
                new String[] {
                    "label/Label.xhtml",
                    "label/LabelResults.xhtml",
                    "label/Help.xhtml",
                    "label/LabelBackingBean.java"}
        ),
        new AppData("index_alertName","index_alertConcepts", "showAlertIndex",
                new String[] {
                    "alert/Alert.xhtml",
                    "alert/InlineAlert.xhtml",
                    "alert/PageAlertExample.xhtml",
                    "alert/PageAlert.xhtml",
                    "alert/HelpAlert.xhtml",
                    "alert/InlineAlertBackingBean.java",        
                    "alert/PageAlertBackingBean.java"}
        ),
        new AppData("index_textInputName", "index_textInputConcepts", "showTextInputIndex", 
                new String[] {
                    "field/index.xhtml",
                    "field/TextInput.xhtml",
                    "field/Payment.xhtml",
                    "field/PaymentResults.xhtml",
                    "field/TextInputResults.xhtml",
                    "field/TextInputBackingBean.java",
                    "field/PaymentBackingBean.java"}
        ),
        new AppData("index_tableName", "index_tableConcepts", "showTableIndex", 
                new String[] {
                    "table/actions.xhtml",
                    "table/actionsBottom.xhtml",
                    "table/actionsTop.xhtml",
                    "table/alarms.xhtml",
                    "table/basicTable.xhtml",
                    "table/customTitle.xhtml",
                    "table/dynamicGroupTable.xhtml",
                    "table/dynamicTable.xhtml",
                    "table/embeddedActions.xhtml",
                    "table/emptyCells.xhtml",
                    "table/filter.xhtml",
                    "table/filterPanel.xhtml",
                    "table/groupTable.xhtml",
                    "table/hiddenRowsActionsBottom.xhtml",        
                    "table/hiddenRowsActionsTop.xhtml",        
                    "table/hiddenSelectedRows.xhtml",                    
                    "table/index.xhtml",
                    "table/multipleHeadersFooters.xhtml",
                    "table/liteTable.xhtml",
                    "table/paginatedTable.xhtml",
                    "table/preferences.xhtml",
                    "table/preferencesPanel.xhtml",
                    "table/selectMultipleRows.xhtml",
                    "table/selectSingleRow.xhtml",
                    "table/spacerColumn.xhtml",
                    "table/sortableTable.xhtml",
                    "table/table.xhtml",
                    "table/TableBean.java",
                    "table/DynamicGroupTableBean.java",
                    "table/DynamicTableBean.java",
                    "table/util/Actions.java",
                    "table/util/Dynamic.java",
                    "table/util/Filter.java",
                    "table/util/Group.java",
                    "table/util/Messages.java",
                    "table/util/Name.java",
                    "table/util/Preferences.java",
                    "table/util/Select.java",
                    "table/js/actions.js",
                    "table/js/filter.js",
                    "table/js/preferences.js",
                    "table/js/select.js"}
        ),
/*
        new AppData("index_addRemoveName", "index_addRemoveConcepts", "showAddRemove",
                new String[] {
                    "addremove/AddRemove.xhtml",
                    "addremove/AddRemoveResults.xhtml",
                    "addremove/AddRemoveBackingBean.java",
                    "common/UserData.java"}
        ),
        new AppData("index_orderableListName", "index_orderableListConcepts", "showOrderableList", 
                new String[] {
                    "orderablelist/OrderableList.xhtml",
                    "orderablelist/OrderableListResults.xhtml",
                    "orderablelist/OrderableListBackingBean.java",
                    "orderablelist/Flavor.java",
                    "common/UserData.java"}        
        ),
        new AppData("index_chooserUploader", "index_chooserUploaderConcepts", "showChooserUploader",
                new String[] {
                    "chooseruploader/fileUploader.xhtml",
                    "chooseruploader/fileChooser.xhtml", 
                    "chooseruploader/folderChooserPopup.xhtml",
                    "chooseruploader/fileUploaderPopup.xhtml",
                    "chooseruploader/folderChooser.xhtml", 
                    "chooseruploader/fileChooserPopup.xhtml",        
                    "chooseruploader/FileChooserBackingBean.java",  
                    "chooseruploader/FolderChooserBackingBean.java",
                    "chooseruploader/FileUploaderBackingBean.java",        
                    "chooseruploader/ChooserUploaderBackingBean.java",
                    "chooseruploader/ChooserUploaderValidator.java"}
        ),
        new AppData("index_menuListName", "index_menuListConcepts", "showMenuList", 
                new String[] {
                    "menu/MenuList.xhtml",
                    "menu/MenuListResults.xhtml",
                    "menu/MenuListBackingBean.java"}
        ),
        new AppData("index_mastheadName", "index_mastheadConcepts", "showMasthead",
                new String[] {
                    "masthead/Index.xhtml",
                    "masthead/Masthead.xhtml",
                    "masthead/MastheadFacets.xhtml",
                    "masthead/Version.xhtml",
                    "masthead/Popup.xhtml",
                    "masthead/Images.xhtml",
                    "masthead/ResultMasthead.xhtml",
                    "masthead/ResultMastheadFacets.xhtml",
                    "masthead/MastheadBackingBean.java"}
        ),
        new AppData("index_propertysheet", "index_propertySheetConcepts", "showPropertySheet",
                new String[] {
                    "propertysheet/PropertySheet.xhtml",
                    "propertysheet/PropertySheetResult.xhtml",         
                    "propertysheet/PropertySheetBackingBean.java"}
        ),
        new AppData("index_editablelist", "index_editableListConcepts", "showEditableList",
                new String[] {
                    "editablelist/editableList.xhtml",
                    "editablelist/editableListResult.xhtml",        
                    "editablelist/EditableListBackingBean.java"}
        ),
        new AppData("index_pagetitleName", "index_pagetitleConcepts", "showPagetitle",
                new String[] {
                    "pagetitle/Pagetitle.xhtml",
                    "pagetitle/PagetitleBackingBean.java"}
        ),
        new AppData("index_hyperlink", "index_hyperlinkConcepts", "showHyperlink",
                new String[] {
                    "hyperlink/hyperLink.xhtml",
                    "hyperlink/hyperLinkResult.xhtml",        
                    "hyperlink/HyperlinkBackingBean.java"}
        ),
        new AppData("index_statictextName", "index_statictextConcepts", "showStaticText",
                new String[] {
                    "statictext/Statictext.xhtml",
                    "statictext/StatictextBackingBean.java",
                    "statictext/Employee.java",
                    "statictext/EmployeeConverter.java"}
        ),         
        new AppData("index_commonTaskName", "index_commonTaskConcepts", "showCommonTask",
                new String[] {
                    "commontask/commonTasks.xhtml",
                    "commontask/sample.xhtml",
                    "commontask/commonTaskBean.java"}
        ),
        new AppData("index_treeName", "index_treeConcepts", "showTreeIndex",
                new String[] {
                    "tree/content.xhtml",
                    "tree/dynamicTree.xhtml",
                    "tree/header.xhtml",
                    "tree/index.xhtml",
                    "tree/navTree.xhtml",
                    "tree/treeFrame.xhtml",
		    "common/ClientSniffer.java",
		    "util/ExampleUtilities.java",
		    "tree/DynamicTreeBackingBean.java",
		    "tree/NavTreeBackingBean.java"}
        ),
        new AppData("index_progressBar", "index_progressBarConcepts", "showProgressBar",
                new String[] {
                    "progressbar/index.xhtml",
                    "progressbar/determinate.xhtml",
                    "progressbar/indeterminate.xhtml",
                    "progressbar/busy.xhtml",
                    "progressbar/ProgressBarBackingBean.java"}
        ),
        new AppData("index_wizardName","index_wizardConcepts", "showWizardIndex",
                new String[] {
                    "wizard/index.xhtml",
                    "wizard/simpleWizard.xhtml",
                    "wizard/SimpleWizardBackingBean.java"}
        ),
        new AppData("index_tabsetName","index_tabsetConcepts", "showTabsetIndex",
                new String[] {
                    "tabset/index.xhtml",
                    "tabset/message.xhtml",
                    "tabset/navtab.xhtml",
                    "tabset/state.xhtml",
                    "tabset/stateResults.xhtml",
                    "tabset/NavtabBackingBean.java",
                    "tabset/StateBackingBean.java",
                    "tabset/StringValue.java",
		    "util/ExampleUtilities.java",
                    "common/UserData.java"}
        ),         
        new AppData("index_calendarName", "index_calendarConcepts", "showCalendar",
                new String[] {
                    "calendar/calendar.xhtml",
                    "calendar/calendarResults.xhtml",
                    "calendar/CalendarBackingBean.java"}
	),
        new AppData("index_accordionName","index_accordionConcepts", "showAccordionIndex",
                new String[] {
                    "accordion/index.xhtml",
                    "accordion/example1.xhtml",
                    "accordion/example2.xhtml",
                    "accordion/example2top.xhtml",
                    "accordion/example2left.xhtml",
                    "accordion/example2right.xhtml",
                    "accordion/example3.xhtml",
                    "accordion/AccordionBackingBean.java"}
        ),
        new AppData("index_bubbleName","index_bubbleConcepts", "showBubble",
                new String[] {
                    "bubble/bubble.xhtml"}
        ),
        new AppData("index_popupMenu", "index_popupMenuConcepts", "showPopupMenu",
                new String[] {
                    "popupMenu/menu.xhtml",
                    "popupMenu/menu.js",
                    "popupMenu/PopupMenuBackingBean.java"}
	),
        new AppData("index_login", "index_loginConcepts", "showLoginIndex",
                new String[] {
                    "login/login1.xhtml",
                    "login/login2.xhtml",
                    "login/login3.xhtml",
                    "login/secondary.xhtml",
                    "login/LoginBean.java",
                    "login/TestLoginModule.java",
                    "login/TestLoginModule2.java",
                    "login/TestLoginModule3.java"}
        ),
        new AppData("index_rating", "index_ratingConcepts", "showRating",
                new String[] {
                    "rating/rating.js",
                    "rating/rating.xhtml",
                    "rating/results.xhtml",
                    "rating/RatingBackingBean.java"}
	),
        new AppData("index_table2", "index_table2Concepts", "showTable2Index",
                new String[] {
                    "table2/index.xhtml",
                    "table2/filterTable.xhtml",
                    "table2/sortTable.xhtml",
                    "table2/table.xhtml",                    
                    "table2/Table2Bean.java",
                    "table2/util/Group.java",
                    "table2/util/Name.java",
                    "table2/util/Filter.java",}
	),
        new AppData("index_tree2", "index_tree2Concepts", "showTree2Index",
                new String[] {
                    "tree2/index.xhtml",
                    "tree2/example1.xhtml",
                    "tree2/example2.xhtml",
                    "tree2/example3.xhtml",
                    "tree2/example4.xhtml",
                    "tree2/example5.xhtml",
                    "tree2/example6.xhtml",
                    "tree2/example7.xhtml",
                    "tree2/TestTreeBean.java"}
	)
*/
    };    
    
    /** Default constructor */
    public TableData() { 		
    }
}
	
