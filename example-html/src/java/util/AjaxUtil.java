/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
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
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */

package util;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Methods for general component manipulation.
 */
public class AjaxUtil {
    public static final String XJSON_HEADER= "X-JSON";
    public static final String XJSON_HEADER_EVENT= "event";
    public static final String XREQUEST_HEADER= "X-Requested-With";
    public static final String XVERSION_HEADER= "X-Woodstock-Version";

    /** Creates a new instance of AjaxUtil. */
    public AjaxUtil() {
    }

    /**
     * Get properties from X-JSON header.
     *
     * @param request HttpServletRequest request.
     * @param key The key of a X-JSON name-value pair.
     * @return 
     */
    public static int getAjaxInt(HttpServletRequest request, String key) {
        int value = -1;
        try {
            if (key != null) {
                JSONObject xjson = new JSONObject(request.getHeader(XJSON_HEADER));
                value = xjson.getInt(key);
            }
        } catch(JSONException e) {
        } catch(NullPointerException e) {} // JSON property may be null.

        return value;
    }

    /**
     * Get properties from X-JSON header.
     *
     * @param request HttpServletRequest request.
     * @param key The key of a X-JSON name-value pair.
     * @return 
     */
    public static String getAjaxString(HttpServletRequest request, String key) {
        String value = null;
        try {
            if (key != null) {
                JSONObject xjson = new JSONObject(request.getHeader(XJSON_HEADER));
                value = xjson.getString(key);
            }
        } catch(JSONException e) {
        } catch(NullPointerException e) {} // JSON property may be null.

        return value;
    }

    /**
     * Return true if the X-Requested-With header is present.
     *
     * @param request HttpServletRequest request.
     * @param event The event name for this Ajax request.
     * @return true if the X-JSON header matches the given event name.
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return (request.getHeader(XREQUEST_HEADER) != null);
    }

    /**
     * Return true if the X-JSON header matches the given event name.
     *
     * @param request HttpServletRequest request.
     * @param event The event name for this Ajax request.
     * @return true if the X-JSON header matches the given event name.
     */
    public static boolean isAjaxEvent(HttpServletRequest request, String event) {
        return (event.equals(getAjaxString(request, XJSON_HEADER_EVENT)));
    }
}
