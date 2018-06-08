/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
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
