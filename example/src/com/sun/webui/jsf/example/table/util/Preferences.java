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

// This class provides functionality for table preferences.
public class Preferences {
    private String preference = null; // Rows preference.
    private int rows = 5; // Rows per page.

    // Default constructor.
    public Preferences() {
    }

    // Table preferences event.
    public void applyPreferences() {
        try {
            int rows = Integer.parseInt(preference);
            if (rows > 0) {
                this.rows = rows;
            }
        } catch (NumberFormatException e) {}
    }

    // Get rows per page.
    public int getRows() {
        return rows;
    }

    // Get preference.
    public String getPreference() {
        return Integer.toString(rows);
    }

    // Set preference.
    public void setPreference(String value) {
        preference = value;
    }
}
