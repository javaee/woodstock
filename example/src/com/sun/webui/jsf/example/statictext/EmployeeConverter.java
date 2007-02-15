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
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.application.FacesMessage;

import com.sun.webui.jsf.example.common.MessageUtil;

/**
 * Employee Converter Class
 */
public class EmployeeConverter implements Converter {
    
    /** Creates an instance of EmployeeConverter. */
    public EmployeeConverter() {    
    } 
    
    /** Converts an object into a string */
    public String getAsString(FacesContext context, 
                              UIComponent component, 
                              Object value) throws ConverterException {
        if (value instanceof Employee) {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(((Employee)value).getFirstName());
            strbuf.append(" "); 
            strbuf.append(((Employee)value).getLastName());
            strbuf.append("-"); 
            strbuf.append(((Employee)value).getDesignation());
            return strbuf.toString();
        }
        throw new ConverterException(MessageUtil.getMessage("statictext_errorMessage1") + value.toString());
    }            
    
    /** Converts a string into an object */
    public Object getAsObject(FacesContext context, 
                              UIComponent component, 
                              String value) throws ConverterException {
        try { 
            String[] names = value.split(" ");
            Employee emp = new Employee(names[0], names[1], names[2]); 
            return emp;
        } 
        catch (Exception ex) { 
            String message = MessageUtil.getMessage("statictext_errorMessage2");              
            throw new ConverterException(new FacesMessage(message));
        } 
        
    }
}
