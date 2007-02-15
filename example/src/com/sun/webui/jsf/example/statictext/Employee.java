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

package com.sun.webui.jsf.example.statictext;

import java.beans.*;
import java.io.Serializable; 

/**
 * Employee Class
 */
public class Employee implements Serializable {
    
    // Holds employee details
    private String firstName;
    private String lastName;
    private String designation;
    
    /** Creates an instance of Employee. */
    public Employee(String firstName, String lastName, String designation) {
        this.firstName = firstName; 
        this.lastName = lastName; 
        this.designation = designation; 
    }
    
    /** Getter for property firstName. */    
    public String getFirstName() {
        return this.firstName;
    }
    
    /** Setter for property firstName. */ 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /** Getter for property lastName. */    
    public String getLastName() {
        return this.lastName;
    }    
    
    /** Setter for property lastName. */ 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    /** Getter for property designation. */    
    public String getDesignation() {
        return this.designation;
    }
    
    /** Setter for property designation. */ 
    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
