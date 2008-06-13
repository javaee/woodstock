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
package com.sun.webui.jsf.model.login;

import java.io.Serializable;
import java.util.Locale;
import java.util.Enumeration;
import java.util.Vector;
import java.util.Iterator;
import java.util.HashMap;

import javax.security.auth.callback.Callback;

/**
 *
 * The class used by all LoginModule implementations that want to operate
 * in this space. This callback class should be used to interact with the 
 * Ajax based presentation layer for exchanging data between the 
 * backend and the presentation layer. The callback class contains a list
 * of keys and values representing input elements and other static data that
 * needs to be presented on the client side for the purposes of getting
 * user and credential information from the user. It also contains the state
 * of the authentication process as seen by the JAAS sequence or the presentation
 * layer. It also contains any error messages that are the outcome of the 
 * authentication process. The callback class support a set of component types 
 * that we think would be needed to display information or prompt for user data 
 * on the client side. 
 */

public class LoginCallback implements Callback, Serializable {
    
    
    public static final String MODULECLASS = "moduleClass";
    public static final String STATE = "state";
    public static final String MSG_TYPE = "msgType";
    public static final String MSG_SUMMARY = "summary";
    public static final String MSG_DETAIL = "detail";
    public static final String MSG_KEY = "messageKey";
    
    public static final String CALLBACK_DATA = "cbData";
    public static final String CALLBACK_CLASS = "cbClass";
    public static final String CONTINUE = "continue";
    
    // Constants representing the component types
    // the Login component currently supports.
    
    public static final String LABEL = "label";
    public static final String TEXT = "textField";
    public static final String PASSWORD = "passwordField";
    public static final String STATICTEXT = "text";
    public static final String LIST = "listbox";
    public static final String DROPDOWN = "dropDown";
    public static final String IMAGE = "image";
    
    public static final boolean FOCUS = true;
    
    private Vector dataMap;
    private Locale locale = null;
    private String msgType = null;
    private String msgSummary = null;
    private String msgDetail = null;
    private String moduleClass = null;
    private LoginConstants.LOGINSTATE loginState = null;
    
     
    /**
     * Set the state of the authentication process. The callback class is 
     * also used in other places besides LoginModule implementations. 
     * See #getLoginState
     *
     * @param loginState The state of the authentication process.
     */
    public void setLoginState(LoginConstants.LOGINSTATE loginState) {
        this.loginState = loginState;
    }
    
    /**
     * Get the state of the authentication process. The valid states are
     * LoginConstant.INIT, LoginConstant.CONTINUE, LoginConstant.SUCCESS,
     * LoginConstant.FAILURE.
     * @return Returns the state of the authentication process.
     */
    public LoginConstants.LOGINSTATE getLoginState() {
        return this.loginState;
    }
    
    /**
     * This method should be used by the LoginModule impentation to add callback 
     * information it wants to be displayed on the client side. Normally the 
     * value parameter would be empty as the LoginModule would be expecting
     * a value from the callback process. However, in some cases this data
     * may be available. For example, in the case of Role Based Access Control
     * the user for which a role is being chosen is already known and, hence,
     * would be displayed on the browser window. 
     * @param key The key used to access this data. 
     * @param type The type of user interface reqauired to display this data.
     *    Right now the types defined as constants above by this interface are 
     *    supported.
     * @param label The label for the data whose value is being sought from the user.
     * @param value the value of the data (userid for example).
     */
    public void addCallbackData(String key, String type, String label, 
            String value) {
        UserData data = new UserData(key, label, value, null, type);
        if (dataMap == null) {
            dataMap = new Vector();
        }
        dataMap.add(data);
    }
    
    /**
     * This method should be used by the LoginModule impentation to add list based
     * callback information it wants to be displayed on the client side. Normally  
     * the value parameter would be empty as the LoginModule would be expecting
     * a value from the callback process. For example, in the case of Role Based 
     * Access Control the list of roles associated with an given user would be
     * set using this method.
     * @param key The key used to access this data. 
     * @param type The type of user interface reqauired to display this data.
     *    Right now the types defined as constants above by this interface are 
     *    supported.
     * @param label The label for the data whose value is being sought from the user.
     * @param value the value of the data (userid for example).
     */
    public void addCallbackListData(String key, String type, String label, 
            String[] list) {
        
        UserData data = new UserData(key, label, null, list, type);
        if (dataMap == null) {
            dataMap = new Vector();
        }
        dataMap.add(data);    
    }
    
    /**
     * This method sets callback data objects to be displayed on the client side. 
     * @param key The key used to access this data. 
     * @param type The type of user interface reqauired to display this data.
     *    Right now the types defined as constants above by this interface are 
     *    supported.
     * @param label The label for the data whose value is being sought from the user.
     * @param value the value of the data (userid for example).
     */
    public void setCallbackData(String key, String type, String label, 
            String value) {
        int i=0;
        for (; i< dataMap.size(); i++) {
            UserData ud = (UserData)dataMap.elementAt(i);
            if (ud.getUserDatakey().equals(key)) {
                break;
            }
        }
        UserData ud = new UserData(key, label, value, null, type);
        if (i < dataMap.size()) {
            dataMap.set(i, ud);
        } else {
            dataMap.add(ud);
        }
    }
    
    /**
     * This method returns an array of keys of the callback fields
     * added by the LoginModule implementation to the callback object.
     *
     * @return An array of Strings representing callback field keys.
     */
    public String[] getDataKeys() {
        if (dataMap == null) {
            return new String[0];
        }
        
        int size = dataMap.size();
        if (size == 0) {
            return new String[0];
        } 
        String keys[] = new String[size];
        for (int i=0; i < size; i++) {
            keys[i] = ((UserData)dataMap.elementAt(i)).getUserDatakey();
        }
        return keys;
    }
    
    /**
     * This method returns the component type representing a single callback
     * element. For example, if the element that needs to be presented
     * to the client side is a text field, the value returned would be "TEXTFIELD".
     * 
     * @return The component type of the callback element.
     */
    public String getCallbackDataType(String key) {
        Enumeration en = dataMap.elements();
        while (en.hasMoreElements()) {
            UserData ud = (UserData)en.nextElement();
            if (ud.getUserDatakey().equals(key)) {
                return ud.getUserDataType();
            }
        }
        
        return null;
    }
    
    /**
     * This method returns value of a callback data object. 
     * 
     * @return The value of the callback data object.
     */
    public String getCallbackDataValue(String key) {
        Enumeration en = dataMap.elements();
        while (en.hasMoreElements()) {
            UserData ud = (UserData)en.nextElement();
            if (ud.getUserDatakey().equals(key)) {
                return ud.getUserDataValue();
            }
        }
        return null;
    }
    
    /**
     * This method sets the value of a callback data object. 
     * 
     * @param key - the key representing the callback data object.
     * @param value - the value of the callback data object.
     */
    public void setCallbackDataValue(String key, String value) {
        Enumeration en = dataMap.elements();
        while (en.hasMoreElements()) {
            UserData ud = (UserData)en.nextElement();
            if (ud.getUserDatakey().equals(key)) {
                ud.setUserDataValue(value);
            }
        }
    }
    
    /**
     * This method returns the label associated with a callback data object. 
     * 
     * @param key - the key representing the callback data object.
     * @return - the label associated with the callback data object.
     */
    public String getCallbackDataLabel(String key) {
        Enumeration en = dataMap.elements();
        while (en.hasMoreElements()) {
            UserData ud = (UserData)en.nextElement();
            if (ud.getUserDatakey().equals(key)) {
                return ud.getUserDataLabel();
            }
        }
        return null;
    }

    /**
     * This method returns callback data that is in the form of a String array.
     * 
     * @param key - the key representing the callback data object.
     * @return - the callback data array.
     */
    public String[] getCallbackDataList(String key) {
        Enumeration en = dataMap.elements();
        while (en.hasMoreElements()) {
            UserData ud = (UserData)en.nextElement();
            if (ud.getUserDatakey().equals(key)) {
                return ud.getUserDataList();
            }
        }
        return null;
    }
    
    
    /**
     * Set any error or informational messages that the LoginModule 
     * implementation class wants to display on the client side.
     * @param msgType - the message type : ERROR, INFO.
     * @param summary - the message summary.
     * @param detail - the message detail.
     */
    public void setMessage(String msgType, String summary, String detail) {
        if (msgType != null) {
            this.msgType = msgType;
        }
        if (summary != null) {
            this.msgSummary = summary;
        }
        if (detail != null) {
            this.msgDetail = detail;
        }
    }
    
    /**
     * Get the alert type associated with the message that is to be displayed.
     * @return - the alert message type (INFO, ERROR).
     */
    public String getMessageType() {
        return msgType;
    }
    
    /**
     * Get the alert summary associated with the message that is to be displayed.
     * @return - the alert message summary.
     */
    public String getMessageSummary() {
        return this.msgSummary;
    }
    /**
     * Get the alert detail associated with the message that is to be displayed.
     * @return - the alert message detail.
     */
    public String getMessageDetail() {
        return this.msgDetail;
    }
    
    /**
     * Get the locale in which data is to be interchanged between the
     * presentation layer and the LoginModule implementation.
     * @return the locale.
     */

    public Locale getLocale() {
        return this.locale;
    }

    /**
     * Set the locale in which data is to be interchanged between the
     * presentation layer and the LoginModule implementation.
     * @param locale the locale to be set.
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    
    // Inner class to represent each row of user data that that needs to be 
    // presented on the client side.
    private class UserData {
        
        private String label;
        private String[] list;
        private String value;
        private String type;
        private String key;
        
        public UserData(String key, String label, String value, String[] list, String type) {
            
            if (key == null) {
                throw new NullPointerException("data key cannot be null");
            } else {
                this.key = key;
            }

            if (label != null) {
                this.label = label;
            }
            
            if (value != null) {
                this.value = value;
            }
            
            if (list != null && list.length > 0) {
                this.list = list;
            }
            
            if (type != null) {
                this.type = type;
            }
                  
        }
        
        public void setUserDataKey (String key) {
            if (key == null) {
                throw new NullPointerException("data key cannot be null");
            } else {
                this.key = key;
            }
        }
        
        public String getUserDatakey() {
            return this.key;
        }
        
        public void setUserDataLabel (String label) {
            if (label != null) {
                this.label = label;
            }            
        }
        
        public String getUserDataLabel() {
            return this.label;
        }

        public void setUserDataValue (String value) {
            if (value != null) {
                this.value = value;
            }            
        }

        public String getUserDataValue() {
            return this.value;
        }
        
        public void setUserDataList (String[] list) {
            if (list != null && list.length > 0) {
                this.list = list;
            }            
        }
        
        public String[] getUserDataList() {
            return this.list;
        }
        
        public void setUserDataType(String type) {
            if (type != null) {
                this.type = type;
            }  
        }        
        
        public String getUserDataType() {
            return this.type;
        }
    }
}
