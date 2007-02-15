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

package com.sun.webui.jsf.example.orderablelist;

/**
 * This class represents a flavor.
 */
public class Flavor {
    
    /** Name of a flavor. */
    private String name;
    
    /** Creates a new instance of Flavor. */
    public Flavor() {
    }
 
    /** Creates a new instance of Flavor. */
    public Flavor(String name) {
        this.name = name;
    }
    
    /** Get the name of the flavor. */
    public String getName() {
        return this.name;
    }
    
    /** Set the name of the flavor. */
    public void setName(String name) {
        this.name = name;
    }
 
    /** Returns a string representation of the object. */
    public String toString() { 
        return name;
    }
}
