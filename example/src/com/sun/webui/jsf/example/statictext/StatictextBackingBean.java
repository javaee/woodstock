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
import java.util.Date;
import java.io.Serializable;

import com.sun.webui.jsf.example.common.MessageUtil;
import javax.faces.convert.Converter;

/**
 * Backing bean for Static Text example.
 */

public class StatictextBackingBean implements Serializable {
    
    // Holds the Date.
    Date date = null;
    
    // Holds the Employee object.
    Employee emp = null;

    // Converter for the Employee object.
    EmployeeConverter empConverter = new EmployeeConverter(); 
                       
    /** Creates a new instance of StatictextBackingBean. */
    public StatictextBackingBean() {
        date = new Date();
        emp = new Employee(MessageUtil.getMessage("statictext_firstname"), 
                MessageUtil.getMessage("statictext_lastname"), 
                MessageUtil.getMessage("statictext_designation"));
    }
   
    /** Returns the date. */
    public Date getDate() {
        return date;
    }
    
    /** Sets the date. */
    public void setDate(Date date) {
        this.date = date;
    }
           
    /** Returns employee object. */
    public Employee getEmp() {
        return emp;
    }
    
    /** Sets employee object. */
    public void setEmp(Employee emp) {
        this.emp = emp;
    } 

    /**
     * Get the converter.
     *
     * @return converter for the Employee object.
     */	
    public Converter getEmpConverter() {
        return empConverter;
    }
}
