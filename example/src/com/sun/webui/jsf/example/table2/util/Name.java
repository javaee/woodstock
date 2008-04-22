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

import com.sun.webui.jsf.component.Alarm;

public class Name {
    private String last = null; // Last name.
    private String first = null; // First name.
    
    
    // Default constructor.
    public Name(String first, String last) {
        this.last = last;
        this.first = first;
    }    

    // Get first name.
    public String getFirst() {
        return first;
    }

    // Set first name.
    public void setFirst(String first) {
        this.first = first;
    }

    // Get last name.
    public String getLast() {
        return last;
    }

    // Set last name.
    public void setLast(String last) {
        this.last = last;
    }

    
}
