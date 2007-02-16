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
/*
 * ClockTime.java
 *
 * Created on July 8, 2005, 1:32 PM
 */

package com.sun.webui.jsf.model;
import java.io.Serializable;


public class ClockTime implements Serializable {
    
    
    /** Creates a new instance of ClockTime */
    public ClockTime() {
    }
    
    /**
     * Holds value of property hour.
     */
    private Integer hour;
    
    /**
     * Getter for property hour.
     * @return Value of property hour.
     */
    public Integer getHour() {
        
        return this.hour;
    }
    
    /**
     * Setter for property hour.
     * @param hour New value of property hour.
     */
    public void setHour(Integer hour) {
        if(hour.intValue() > -1 && hour.intValue() < 24) {
            this.hour = hour;
        } else {
            throw new RuntimeException();
        }
    }
    
    /**
     * Holds value of property minute.
     */
    private Integer minute;
    
    /**
     * Getter for property minute.
     * @return Value of property minute.
     */
    public Integer getMinute() {
        
        return this.minute;
    }
    
    /**
     * Setter for property minute.
     * @param minute New value of property minute.
     */
    public void setMinute(Integer minute) {
        if(minute.intValue() > -1 && minute.intValue() < 60) {
            this.minute = minute;
        } else {
            throw new RuntimeException();
        }
    }
    
    public boolean equals(Object obj) {
        if(obj instanceof ClockTime) {
            return (((ClockTime)obj).getHour().equals(hour) &&
                    ((ClockTime)obj).getMinute().equals(minute));
        }
        return false;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer(128);
        buffer.append(this.getClass().getName());
        buffer.append(": ");
        buffer.append(String.valueOf(hour));
        buffer.append(":");
        buffer.append(String.valueOf(minute));
        return buffer.toString();
    }
}
