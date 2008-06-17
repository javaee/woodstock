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

package com.sun.webui.jsf.example.table.util;

import com.sun.webui.jsf.component.Alarm;
import com.sun.webui.jsf.theme.ThemeImages;

public class Name {
    private String last = null; // Last name.
    private String first = null; // First name.
    private Alarm alarm = null; // Alarm.
    private String statusA = null; // A status
    private String statusB = null; // B status
    private String statusC = null; // C status
    private String statusD = null; // D status
    
    // Default constructor.
    public Name(String first, String last) {
        this.last = last;
        this.first = first;
    }

    // Construct an instance with given alarm severity.
    public Name(String first, String last, Alarm alarm) {
        this(first, last);
        this.alarm = alarm;
    }

    // Construct an instance with given alarm severity and statuses.
    public Name(String first, String last, Alarm alarm, 
            String statusA, String statusB, String statusC, String statusD) {
        this(first, last, alarm);
        this.statusA = statusA;
        this.statusB = statusB;
        this.statusC = statusC;
        this.statusD = statusD;
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

    // Get alarm.
    public Alarm getAlarm() {
        return alarm;
    }

    // Get alarm.
    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }
    
    // Get status A
    public String getStatusA() {
        return statusA;
    }
    
    // Set status A
    public void setStatusA(String status) {
        this.statusA = status;
    }
    
    // Get status B
    public String getStatusB() {
        return statusB;
    }
    
    // Set status B
    public void setStatusB(String status) {
        this.statusB = status;
    }
    
    // Get status C
    public String getStatusC() {
        return statusC;
    }
    
    // Set status C
    public void setStatusC(String status) {
        this.statusC = status;
    }
    
    // Get status D
    public String getStatusD() {
        return statusD;
    }
    
    // Set status D
    public void setStatusD(String status) {
        this.statusD = status;
    }

    // Get alarm severity.
    public String getSeverity() {
        return alarm.getSeverity();
    }

    // Get alarm severity.
    public void setSeverity(String severity) {
        alarm.setSeverity(severity);
    }
}
