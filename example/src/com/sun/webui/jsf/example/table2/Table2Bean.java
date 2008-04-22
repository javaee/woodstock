/*
* {START_JAVA_COPYRIGHT_NOTICE
* Copyright 2006 Sun Microsystems, Inc. All rights reserved.
* Use is subject to license terms.
* END_COPYRIGHT_NOTICE}
*/

// Note: Do not use multiline comments below for TLD examples as renderer XML
// files shall be used to generate Javadoc. Embedding a "*/" in a Javadoc 
// comment cuases compile errors because it terminates the outer comment.

package com.sun.webui.jsf.example.table2;

import com.sun.webui.jsf.example.index.IndexBackingBean;
import java.util.ArrayList;
import table2.util.Group;
import table2.util.Name;

// Backing bean for table2 examples.
public class Table2Bean {
    
    // Navigation case outcome to go to table index
    public static final String SHOW_TABLE_INDEX = "showTable2Index";
    // Group util for table examples.
    private Group groupA = null; // List (rows 0-19).
    private Group groupB = null; // Array (rows 0-9).
    private Group groupC = null; // Array (rows 10-19).

    // Data for table examples.
    protected static final Name[] names = {
        new Name("William", "Dupont"),
        new Name("Anna", "Keeney"),
        new Name("Mariko", "Randor"),
        new Name("John", "Wilson"),
        new Name("Lynn", "Seckinger"),
        new Name("Richard", "Tattersall"),
        new Name("Gabriella", "Sarintia"),
        new Name("Lisa", "Hartwig"),
        new Name("Shirley", "Jones"),
        new Name("Bill", "Sprague"),
        new Name("Greg", "Doench"),
        new Name("Solange", "Nadeau"),
        new Name("Heather", "McGann"),
        new Name("Roy", "Martin"),
        new Name("Claude", "Loubier"),
        new Name("Dan", "Woodard"),
        new Name("Ron", "Dunlap"),
        new Name("Keith", "Frankart"),
        new Name("Andre", "Nadeau"),
        new Name("Horace", "Celestin"),
    };

    // Default constructor.
    public Table2Bean() {
    }

    // Get Group util created with a List containing all names.
    public Group getGroupA() {
        if (groupA != null) {
            return groupA;
        }
        // Create List with all names.
        ArrayList newNames = new ArrayList();
        for (int i = names.length - 1; i >= 0; i--) {
            newNames.add(names[i]);
        }
        return (groupA = new Group(newNames));
    }

    // Get Group util created with an array containing a subset of names.
    public Group getGroupB() {
        if (groupB != null) {
            return groupB;
        }
        // Create an array with subset of names (i.e., 0-9).
        Name[] newNames = new Name[10];
        System.arraycopy(names, 0, newNames, 0, 10);
        return (groupB = new Group(newNames));
    }

    // Get Group util created with an array containing a subset of names.
    public Group getGroupC() {
        if (groupC != null) {
            return groupC;
        }
        // Create an array with subset of names (i.e., 10-19).
        Name[] newNames = new Name[10];
        System.arraycopy(names, 10, newNames, 0, 10);
        return (groupC = new Group(newNames));
    }
    
    // Action handler when navigating to the table index.
    public String showTableIndex() {
        reset();
        return SHOW_TABLE_INDEX;
    }
    
    // Action handler when navigating to the main example index.
    public String showExampleIndex() {
        reset();
        return IndexBackingBean.INDEX_ACTION;
    }
    
    // Reset values so next visit starts fresh.
    private void reset() {
        groupA = null;
        groupB = null;
        groupC = null;	
    }
}
