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
package com.sun.webui.jsf.util;

import javax.faces.component.UIComponent;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class provides common methods for JSON tasks.
 */
public class JSONUtilities {
    // The default number of spaces to add to each level of indentation.
    private static final int INDENT_FACTOR = 4;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Property methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * This method may be used to add attribute name/value pairs to the given
     * JSONObject.
     *
     * @param names Array of attribute names to be passed through.
     * @param component UIComponent to be rendered.
     * @param json JSONObject to add name/value pairs to.
     *
     * @exception IOException if an input/output error occurs
     * @exception JSONException if a key/value error occurs
     */
    public static void addAttributes(String names[], UIComponent component,
            JSONObject json) throws JSONException {
        if (names == null) {
            return;
        }
        Map attributes = component.getAttributes();
        for (int i = 0; i < names.length; i++) {
            Object value = attributes.get(names[i]);
            if (value != null && value instanceof Integer) {
                if (((Integer) value).intValue() == Integer.MIN_VALUE) {
                    continue;
                }
            }
            json.put(names[i], value);
        }
    }

    /**
     * Helper method to add component properties.
     *
     * @param json The JSONArray to append value to.
     * @param value A string containing JSON or HTML text.
     */
    public static void addProperty(JSONArray json, String value) 
            throws JSONException {
        if (value != null) {
            try {
                // If JSON text is given, append a new JSONObject.
                json.put(new JSONObject(value));
            } catch (JSONException e) {
                // Append HTML string.
                json.put(value);
            }
        }
    }

    /**
     * Helper method to add component properties.
     *
     * @param json The JSONObject to append value to.
     * @param key A key string.
     * @param value A string containing JSON or HTML text.
     */
    public static void addProperty(JSONObject json, String key,
            String value) throws JSONException {
        if (value != null) {
            try {
                // If JSON text is given, append a new JSONObject.
                json.put(key, new JSONObject(value));
            } catch (JSONException e) {
                // Append HTML string.
                json.put(key, value);
            }
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Writer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to get pretty printed JSON text.
     * 
     * Note: If debug mode is set, text will be indented for readability.
     *
     * @param json The JSONObject to print.
     */
    public static String getString(JSONObject json) throws JSONException {
        String result = null;
        if (json == null) {
            return result;
        }
        // Add indent factor only while dubugging.
        if (JavaScriptUtilities.isDebug()) {
            result = json.toString(INDENT_FACTOR);
        } else {
            result = json.toString();
        }
        return result;
    }
}
