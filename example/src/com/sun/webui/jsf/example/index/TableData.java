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
                    "button/Button.jsp",
                    "button/ButtonResults.jsp",
                    "button/ButtonBackingBean.java"}
        ),
        new AppData("index_cbrbName","index_cbrbConcepts", "showCbRbIndex",
                new String[] {
                    "cbrb/index.jsp",
                    "cbrb/clientsideCheckbox.jsp",
                    "cbrb/checkboxRadiobutton.jsp",
                    "cbrb/clientsideRadioButton.jsp",
                    "cbrb/clientsideCbRbGroup.jsp",
                    "cbrb/checkboxRadiobuttonResults.jsp",
                    "cbrb/CheckboxRadiobuttonBackingBean.java"}
        ),
        new AppData("index_labelName","index_labelConcepts", "showLabel",
                new String[] {
                    "label/Label.jsp",
                    "label/LabelResults.jsp",
                    "label/Help.jsp",
                    "label/LabelBackingBean.java"}
        ),
        new AppData("index_alertName","index_alertConcepts", "showAlertIndex",
                new String[] {
                    "alert/Alert.jsp",
                    "alert/InlineAlert.jsp",
                    "alert/PageAlertExample.jsp",
                    "alert/PageAlert.jsp",
                    "alert/HelpAlert.jsp",
                    "alert/InlineAlertBackingBean.java",        
                    "alert/PageAlertBackingBean.java"}
        ),
        new AppData("index_textInputName", "index_textInputConcepts", "showTextInputIndex", 
                new String[] {
                    "field/index.jsp",
                    "field/TextInput.jsp",
                    "field/Payment.jsp",
                    "field/PaymentResults.jsp",
                    "field/TextInputResults.jsp",
                    "field/TextInputBackingBean.java",
                    "field/PaymentBackingBean.java"}
        ),
        new AppData("index_tableName", "index_tableConcepts", "showTableIndex", 
                new String[] {
                    "table/actions.jsp",
                    "table/actionsBottom.jsp",
                    "table/actionsTop.jsp",
                    "table/alarms.jsp",
                    "table/basicTable.jsp",
                    "table/customTitle.jsp",
                    "table/dynamicGroupTable.jsp",
                    "table/dynamicTable.jsp",
                    "table/embeddedActions.jsp",
                    "table/emptyCells.jsp",
                    "table/filter.jsp",
                    "table/filterPanel.jsp",
                    "table/groupTable.jsp",
                    "table/hiddenRowsActionsBottom.jsp",        
                    "table/hiddenRowsActionsTop.jsp",        
                    "table/hiddenSelectedRows.jsp",                    
                    "table/index.jsp",
                    "table/multipleHeadersFooters.jsp",
                    "table/liteTable.jsp",
                    "table/paginatedTable.jsp",
                    "table/preferences.jsp",
                    "table/preferencesPanel.jsp",
                    "table/selectMultipleRows.jsp",
                    "table/selectSingleRow.jsp",
                    "table/spacerColumn.jsp",
                    "table/sortableTable.jsp",
                    "table/table.jsp",
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
        new AppData("index_addRemoveName", "index_addRemoveConcepts", "showAddRemove",
                new String[] {
                    "addremove/AddRemove.jsp",
                    "addremove/AddRemoveResults.jsp",
                    "addremove/AddRemoveBackingBean.java",
                    "common/UserData.java"}
        ),
        new AppData("index_orderableListName", "index_orderableListConcepts", "showOrderableList", 
                new String[] {
                    "orderablelist/OrderableList.jsp",
                    "orderablelist/OrderableListResults.jsp",
                    "orderablelist/OrderableListBackingBean.java",
                    "orderablelist/Flavor.java",
                    "common/UserData.java"}        
        ),
        new AppData("index_chooserUploader", "index_chooserUploaderConcepts", "showChooserUploader",
                new String[] {
                    "chooseruploader/fileUploader.jsp",
                    "chooseruploader/fileChooser.jsp", 
                    "chooseruploader/folderChooserPopup.jsp",
                    "chooseruploader/fileUploaderPopup.jsp",
                    "chooseruploader/folderChooser.jsp", 
                    "chooseruploader/fileChooserPopup.jsp",        
                    "chooseruploader/FileChooserBackingBean.java",  
                    "chooseruploader/FolderChooserBackingBean.java",
                    "chooseruploader/FileUploaderBackingBean.java",        
                    "chooseruploader/ChooserUploaderBackingBean.java",
                    "chooseruploader/ChooserUploaderValidator.java"}
        ),
        new AppData("index_menuListName", "index_menuListConcepts", "showMenuList", 
                new String[] {
                    "menu/MenuList.jsp",
                    "menu/MenuListResults.jsp",
                    "menu/MenuListBackingBean.java"}
        ),
        new AppData("index_mastheadName", "index_mastheadConcepts", "showMasthead",
                new String[] {
                    "masthead/Index.jsp",
                    "masthead/Masthead.jsp",
                    "masthead/MastheadFacets.jsp",
                    "masthead/Version.jsp",
                    "masthead/Popup.jsp",
                    "masthead/Images.jsp",
                    "masthead/ResultMasthead.jsp",
                    "masthead/ResultMastheadFacets.jsp",
                    "masthead/MastheadBackingBean.java"}
        ),
        new AppData("index_propertysheet", "index_propertySheetConcepts", "showPropertySheet",
                new String[] {
                    "propertysheet/PropertySheet.jsp",
                    "propertysheet/PropertySheetResult.jsp",         
                    "propertysheet/PropertySheetBackingBean.java"}
        ),
        new AppData("index_editablelist", "index_editableListConcepts", "showEditableList",
                new String[] {
                    "editablelist/editableList.jsp",
                    "editablelist/editableListResult.jsp",        
                    "editablelist/EditableListBackingBean.java"}
        ),
        new AppData("index_pagetitleName", "index_pagetitleConcepts", "showPagetitle",
                new String[] {
                    "pagetitle/Pagetitle.jsp",
                    "pagetitle/PagetitleBackingBean.java"}
        ),
        new AppData("index_hyperlink", "index_hyperlinkConcepts", "showHyperlink",
                new String[] {
                    "hyperlink/hyperLink.jsp",
                    "hyperlink/hyperLinkResult.jsp",        
                    "hyperlink/HyperlinkBackingBean.java"}
        ),
        new AppData("index_statictextName", "index_statictextConcepts", "showStaticText",
                new String[] {
                    "statictext/Statictext.jsp",
                    "statictext/StatictextBackingBean.java",
                    "statictext/Employee.java",
                    "statictext/EmployeeConverter.java"}
        ),         
        new AppData("index_commonTaskName", "index_commonTaskConcepts", "showCommonTask",
                new String[] {
                    "commontask/commonTasks.jsp",
                    "commontask/sample.jsp",
                    "commontask/commonTaskBean.java"}
        ),
        new AppData("index_treeName", "index_treeConcepts", "showTreeIndex",
                new String[] {
                    "tree/content.jsp",
                    "tree/dynamicTree.jsp",
                    "tree/header.jsp",
                    "tree/index.jsp",
                    "tree/navTree.jsp",
                    "tree/treeFrame.jsp",
		    "common/ClientSniffer.java",
		    "util/ExampleUtilities.java",
		    "tree/DynamicTreeBackingBean.java",
		    "tree/NavTreeBackingBean.java"}
        ),
        new AppData("index_progressBar", "index_progressBarConcepts", "showProgressBar",
                new String[] {
                    "progressbar/index.jsp",
                    "progressbar/determinate.jsp",
                    "progressbar/indeterminate.jsp",
                    "progressbar/busy.jsp",
                    "progressbar/ProgressBarBackingBean.java"}
        ),
        new AppData("index_wizardName","index_wizardConcepts", "showWizardIndex",
                new String[] {
                    "wizard/index.jsp",
                    "wizard/simpleWizard.jsp",
                    "wizard/SimpleWizardBackingBean.java"}
        ),
        new AppData("index_tabsetName","index_tabsetConcepts", "showTabsetIndex",
                new String[] {
                    "tabset/index.jsp",
                    "tabset/message.jsp",
                    "tabset/navtab.jsp",
                    "tabset/state.jsp",
                    "tabset/stateResults.jsp",
                    "tabset/NavtabBackingBean.java",
                    "tabset/StateBackingBean.java",
                    "tabset/StringValue.java",
		    "util/ExampleUtilities.java",
                    "common/UserData.java"}
        ),         
        new AppData("index_calendarName", "index_calendarConcepts", "showCalendar",
                new String[] {
                    "calendar/calendar.jsp",
                    "calendar/calendarResults.jsp",
                    "calendar/CalendarBackingBean.java"}
	),
        new AppData("index_accordionName","index_accordionConcepts", "showAccordionIndex",
                new String[] {
                    "accordion/index.jsp",
                    "accordion/example1.jsp",
                    "accordion/example2.jsp",
                    "accordion/example2top.jsp",
                    "accordion/example2left.jsp",
                    "accordion/example2right.jsp",
                    "accordion/example3.jsp",
                    "accordion/AccordionBackingBean.java"}
        ),
        new AppData("index_bubbleName","index_bubbleConcepts", "showBubble",
                new String[] {
                    "bubble/bubble.jsp"}
        ),
        new AppData("index_popupMenu", "index_popupMenuConcepts", "showPopupMenu",
                new String[] {
                    "popupMenu/menu.jsp",
                    "popupMenu/menu.js",
                    "popupMenu/PopupMenuBackingBean.java"}
	),
        new AppData("index_login", "index_loginConcepts", "showLoginIndex",
                new String[] {
                    "login/login1.jsp",
                    "login/login2.jsp",
                    "login/login3.jsp",
                    "login/secondary.jsp",
                    "login/LoginBean.java",
                    "login/TestLoginModule.java",
                    "login/TestLoginModule2.java",
                    "login/TestLoginModule3.java"}
        ),
        new AppData("index_rating", "index_ratingConcepts", "showRating",
                new String[] {
                    "rating/rating.js",
                    "rating/rating.jsp",
                    "rating/results.jsp",
                    "rating/RatingBackingBean.java"}
	),
        new AppData("index_table2", "index_table2Concepts", "showTable2Index",
                new String[] {
                    "table2/index.jsp",
                    "table2/filterTable.jsp",
                    "table2/sortTable.jsp",
                    "table2/spanHeaderTable.jsp",
                    "table2/table.jsp",                    
                    "table2/Table2Bean.java",
                    "table2/util/Group.java",
                    "table2/util/Name.java",
                    "table2/util/Filter.java",}
	),
        new AppData("index_tree2", "index_tree2Concepts", "showTree2Index",
                new String[] {
                    "tree2/index.jsp",
                    "tree2/example1.jsp",
                    "tree2/example2.jsp",
                    "tree2/example3.jsp",
                    "tree2/example4.jsp",
                    "tree2/example5.jsp",
                    "tree2/example6.jsp",
                    "tree2/example7.jsp",
                    "tree2/TestTreeBean.java"}
	),
    };    
    
    /** Default constructor */
    public TableData() { 		
    }
}
	
