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

package table2.util;

import com.sun.data.provider.FilterCriteria;
import com.sun.data.provider.impl.CompareFilterCriteria;
import com.sun.webui.jsf.component.DropDown;
import com.sun.webui.jsf.component.TextField;
import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.model.Option;

// This class provides functionality for table filters.
import javax.faces.component.UIComponent;
import javax.faces.event.ValueChangeEvent;
// This util class sets filters directly on the Table2RowGroup component using 
// FilterCriteria; however, there is also a FilteredTableDataProvider class that
// can used for filtering outside of the table. The table will pick up what ever 
// filter has been applied automatically, for example:
//
// // Some choice of TableDataProvider.
// TableDataProvider provider = new ...
//
// // This wraps and filters an existing TableDataProvider.
// FilteredTableDataProvider filteredProvider = new FilteredTableDataProvider();
// filteredProvider.setTableDataProvider(provider);
//
// // Set FilteredTableDataProvider in the Table2RowGroup component.
// Table2RowGroup.setSourceData(filteredProvider);
//
// The table component itself has no idea that there is any filtering going on, 
// but the filtering functionality has been encapsulated in the data provider. 
// The developer can then use different FilterCriteria types to apply filters,
// for example:
//
// CompareFilterCriteria cfc = new ...
// RegexFilterCriteria rxfc = new ...
// filteredProvider.setFilterCriteria(new FilterCriteria[] { cfc, fxfc });
public class Filter {
    private String customFilter = null; // Custom filter.
    private String basicFilter = null; // Basic filter menu option.
    private String filterText = null; // Filter text.
    private Group group = null; // Group util.
    private String type = null;
    
    // Filter menu items.
    protected static final Option[] filterOptions = {
        new Option("FILTER0", MessageUtil.getMessage("table_filterAllItems")),
        new Option("FILTER1", MessageUtil.getMessage("table_filter1")),
        new Option("FILTER2", MessageUtil.getMessage("table_filter2")),
        new Option("SEARCH_RESULT", MessageUtil.getMessage("table2_searchResults")),
    };

    // Default constructor.
    public Filter(Group group) {
        this.group = group;
    }

    // Get basic filter.
    public String getBasicFilter() {        
        return basicFilter;       
    }

    // Set basic filter.
    public void setBasicFilter(String value) {
        String text= null;
        basicFilter = value;
        
        if (type != null && type.equals("basic")) {
            
            if (basicFilter.equals("FILTER1")) {
                text = "Frankart";
                filterText = MessageUtil.getMessage("table2_filter1Text");
            } else if (basicFilter.equals("FILTER2")) {
                text = "Woodard";
                filterText = MessageUtil.getMessage("table2_filter2Text");
            } else if (basicFilter.equals("FILTER0")) {
                group.getTableRowGroup().setFilterCriteria(null);
                filterText = "";
            } else if (basicFilter.equals("SEARCH_RESULT")) {
                text = customFilter;
                filterText = MessageUtil.getMessage("table2_customFilterText");
            }
            
            if (text == null || text.trim().length() == 0) {
                group.getTableRowGroup().setFilterCriteria(null);
            } else {
                // Filter rows that do not match custom filter.
                CompareFilterCriteria criteria = new CompareFilterCriteria(
                    group.getNames().getFieldKey("last"), text);

                // Note: Table2RowGroup ensures pagination is reset per UI guidelines.
                group.getTableRowGroup().setFilterCriteria(
                    new FilterCriteria[] {criteria});
            }            
        }
    }

    // Get custom filter.
    public String getCustomFilter() {
        return customFilter;
    }

    // Set custom filter.
    public void setCustomFilter(String value) {
        customFilter = value;                
        if (type == null || type.equals("custom")) {             
            if ((customFilter == null || customFilter.length() == 0)) {
                group.getTableRowGroup().setFilterCriteria(null);
            } else {
                // Filter rows that do not match custom filter.
                CompareFilterCriteria criteria = new CompareFilterCriteria(
                    group.getNames().getFieldKey("last"), customFilter);

                // Note: Table2RowGroup ensures pagination is reset per UI guidelines.
                group.getTableRowGroup().setFilterCriteria(
                    new FilterCriteria[] {criteria});
                filterText = "Custom";            
                basicFilter = "SEARCH_RESULT";
            }            
        }    
        type = null;   
    }

    // Get filter menu options.
    public Option[] getFilterOptions() {                
        return filterOptions;
    }

    // Get filter text.
    public String getFilterText() {        
        return filterText;
    }
    // filter types
    public void filterType(ValueChangeEvent ve) {
       UIComponent comp = (UIComponent) ve.getComponent();
       if (comp instanceof TextField) {
           type = "custom";
       } else if (comp instanceof DropDown) {
           type = "basic";       
       }
    }   
}
