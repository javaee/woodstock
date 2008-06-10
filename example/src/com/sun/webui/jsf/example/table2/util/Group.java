/*
* {START_JAVA_COPYRIGHT_NOTICE
* Copyright 2005 Sun Microsystems, Inc. All rights reserved.
* Use is subject to license terms.
* END_COPYRIGHT_NOTICE}
*/

// Note: Do not use multiline comments below for TLD examples as renderer XML
// files shall be used to generate Javadoc. Embedding a "*/" in a Javadoc 
// comment cuases compile errors because it terminates the outer comment.

package table2.util;

import com.sun.data.provider.TableDataProvider;
import com.sun.data.provider.impl.ObjectArrayDataProvider;
import com.sun.data.provider.impl.ObjectListDataProvider;
import com.sun.webui.jsf.component.Table2RowGroup;

import java.util.List;

// This class contains data provider and util classes. Note that not all util
// classes are used for each example.
public class Group {
    private Table2RowGroup tableRowGroup = null; // Table2RowGroup component.
    private TableDataProvider provider = null; // Data provider.
    private Filter filter = null; // Filter util.

    // Default constructor.
    public Group() {
        filter = new Filter(this);
    }

    // Construct an instance using given Object array.
    public Group(Object[] array) {
        this();
        provider = new ObjectArrayDataProvider(array);
    }

    // Construct an instance using given List.
    public Group(List list) {
        this();
        provider = new ObjectListDataProvider(list);
    }

    // Get data provider.
    public TableDataProvider getNames() {
        return provider;
    }    

    // Get Table2RowGroup component.
    public Table2RowGroup getTableRowGroup() {
        return tableRowGroup;
    }

    // Set Table2RowGroup component.
    public void setTableRowGroup(Table2RowGroup tableRowGroup) {
        this.tableRowGroup = tableRowGroup;
    }
    
    // Get Filter util.
    public Filter getFilter() {
        return filter;
    }
}
