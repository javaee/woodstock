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
package com.sun.webui.jsf.renderkit.ajax;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ExternalContext;
import javax.faces.application.FacesMessage;

import com.sun.faces.annotation.Renderer;
import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.component.Login;
import com.sun.webui.jsf.util.LogUtil;

import com.sun.webui.jsf.model.login.LoginCallback;
import com.sun.webui.jsf.model.login.LoginConstants;

import javax.servlet.http.*;
import javax.servlet.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * <p>Ajax Renderer for a {@link Login} component. This renderer responds 
 * to asynchronous requests from the login client. The request is parsed
 * and routed to the underlying CallbackHandler class which populates the
 * callback object that the LoginModule implementation uses to authenticate
 * the user. The result of the authentication is one of the following:
 * a) init - the authentication process is about to start. The system fires
 *    up the authentication mechanism. The first LoginModule implementation's
 *    callback object is converted to a JSON object representing client side
 *    widgets and sent across to the client. 
 * b) continue - authentication succeeded but the process is not complete. One
 *    or more LoginModule implementations need to execute. Prompts for the
 *    next loginModule are sent across to the client.     
 * c) success - the last LoginModule in the chain has successfully 
 *    executed and authentication process is complete. In this case 
 *    the "success" state is tranmitted to the client and the widget takes
 *    appropriate action based on the needs of the application. These actions
 *    could include forwarding to another URL, storing a cookie somewhere
 *    on the client side, etc.
 * d) failure - authentication failed. The error message and the failed state
 *    information would be transmitted back to the client. The user would
 *    then be expected to reenter authentication information and submit the 
 *    form.
 *
 * </p>
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.ajax.Login",
    componentFamily="com.sun.webui.jsf.Login"))
public class LoginRenderer extends com.sun.webui.jsf.renderkit.widget.LoginRenderer {
    
    /**
     * Decode the Login component
     * The request parms should have the ids of the components
     * that contain user and credential information. Extract these
     * data for later use. This renderer is assumes a servlet environment.
     * For the portlet a separate renderer has to be written. 
     * The renderer creates a Map object representing the submitted value of
     * the login component. The getConvertedValue() method takes this and converts
     * it to a form that can be sent back to the underlying authentication 
     * module.
     * @param context The FacesContext associated with this request
     * @param component The Login component to decode
     */
    public void decode(FacesContext context, UIComponent component) {
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!(component instanceof Login)) {
            throw new IllegalArgumentException("LoginRenderer can only decode Login components.");
        }
         
        Login login = (Login)component;    
                  
            try {
                ExternalContext ec = context.getExternalContext();
                Map map = ec.getRequestHeaderMap();
                JSONObject xjson = new JSONObject((String)
                    map.get(AsyncResponse.XJSON_HEADER));
                 
                String id = (String) xjson.get("id");
                if (login.getClientId(context).equals(id)) {
                    String loginState = (String) xjson.get("loginState");
                    if (loginState != null) {
                        if (loginState.equalsIgnoreCase("INIT")) {
                            login.setLoginState(LoginConstants.LOGINSTATE.INIT);
                        } else if (loginState.equalsIgnoreCase("CONTINUE")) {
                            login.setLoginState(LoginConstants.LOGINSTATE.CONTINUE);
                        } else if (loginState.equalsIgnoreCase("SUCCESS")) {
                            login.setLoginState(LoginConstants.LOGINSTATE.SUCCESS);
                        } else if (loginState.equalsIgnoreCase("FAILURE")) {
                            login.setLoginState(LoginConstants.LOGINSTATE.FAILURE);
                        }
                    } else {
                        login.setLoginState(LoginConstants.LOGINSTATE.INIT);
                    }
                    
                    // Get the client id's for which data was requested
                    // by the callback object. Extract the keys associated
                    // with these client IDs. The submitted value of the
                    // login component is a map consiting of these keys 
                    // and their associated values extracted form request map.
                    if (!login.getLoginState().equals(LoginConstants.LOGINSTATE.INIT)) {
                        JSONArray keys = (JSONArray) xjson.get("keys");
                        if (keys != null) {
                           
                            Map parmMap = ec.getRequestParameterMap();
                            HashMap hMap = new HashMap();
                            Map keyMap = login.getAuthenticationKeys();
                            
                            int length = keys.length();
                            String[] strKeys = new String[length];
                            for (int i=0; i<length; i++) {
                                JSONArray keyArray = (JSONArray) keys.get(i);
                                String key = (String)keyArray.get(0);
                                Object value = keyArray.get(1);
                                hMap.put(key, value);
                                
                            }
                            login.setSubmittedValue(hMap);
                        }
                    }
                }   
            } catch(JSONException e) {
                login.setLoginState(LoginConstants.LOGINSTATE.INIT);
            } catch(NullPointerException e) {
                login.setLoginState(LoginConstants.LOGINSTATE.INIT);
            }
     }
            
    /**
     * Render the beginning of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    public void encodeBegin(FacesContext context, UIComponent component)
    throws IOException {
        // Do nothing...
    }

    /**
     * Render the children of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * This method parses the LoginCallback object to generate JSON
     * properties representing the components that should be displayed
     * on the client side for authentication data entry, displaying alert
     * messages and also a map of the clientIDs of the relevant components and 
     * their corresponding IDs as specified in the callback object.
     * The final JSON object consists of the following data:
     * a) login id
     * b) login state
     * c) An array of arrays. Each array is of length atmost 2
     *    possibly representing a name and a value or just a value.
     * d) An alert component's markup
     * e) A set of keys that will be used as keys while sending user data
     *    back to the server for authentication.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    public void encodeChildren(FacesContext context, UIComponent component)
        throws IOException {
        
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) {
            return;
        }
        
        // if component is not valid display the alert message and return.
        Login login = (Login) component;
        
        try {
            
            LoginCallback lcb = login.getCallbackObject();
            JSONObject json = new JSONObject();
            
            if (lcb != null) {
                
                json.put("loginState", lcb.getLoginState());
                json.put("id", login.getClientId(context));
                
                if (login.getLoginState().equals(LoginConstants.LOGINSTATE.FAILURE)) {
                    String msgSummary = lcb.getMessageSummary();
               
                    // Display FacesMessage if available.
                    if (!login.isValid()) {
                        Iterator it = context.getMessages();
                        while (it.hasNext()) {
                            FacesMessage fm = (FacesMessage)it.next();
                            json.put("alert", getAlertData(context, 
                                fm.getSeverity().toString(), fm.getSummary(),
                                fm.getDetail()));
                        }
                    } else if (msgSummary != null && msgSummary.length() > 0) {
                        // Update the alert property and send it across
                        // to the client. All other data should remain the same
                        // on the client side. Also, set the old keyMap as the user
                        // will be submitting the same partial form.
                        json.put("alert", getAlertData(context, 
                            lcb.getMessageType(),
                            msgSummary, lcb.getMessageDetail()));
                    }
                    Map oldKeyMap = login.getAuthenticationKeys();
                    login.setAuthenticationKeys(oldKeyMap);
                
                } else {
                
                    JSONArray keys = new JSONArray();
                    JSONArray userData = new JSONArray();
                    String[] dataKeys = lcb.getDataKeys();
                    HashMap keyMap = new HashMap();
                    for (int i=0; i < dataKeys.length; i++) {
                        String key = dataKeys[i];
                        String type = lcb.getCallbackDataType(key);
                        JSONArray content = getRenderedWidget(login, context, 
                                lcb, key, keys, keyMap);
                        if (content != null) {
                            userData.put(i, content);
                        }
                    }
                    String msgSummary = lcb.getMessageSummary();
                    if (msgSummary != null && msgSummary.length() > 0) {
                        json.put("alert", getAlertData(context, lcb.getMessageType(),
                            msgSummary, lcb.getMessageDetail()));
                    }
                    login.setAuthenticationKeys(keyMap);

                    json.put("keys", keys);
                    json.put("userData", userData);
                    json.put("redirectURL", login.getRedirectURL());
                    
                }
                
            } else {
                if (!login.isValid()) {
                    json.put("loginState", LoginConstants.LOGINSTATE.FAILURE);
                    json.put("id", login.getClientId(context));
                    Iterator it = context.getMessages();
                    while (it.hasNext()) {
                        FacesMessage fm = (FacesMessage)it.next();
                        json.put("alert", getAlertData(context, 
                            fm.getSeverity().toString(), 
                            fm.getSummary(), fm.getDetail()));
                    }
                }
            }
            json.write(context.getResponseWriter());
            
        } catch (JSONException je) {
                LogUtil.severe("JSON Exception thrown during rendering");
        }
        
    }
    
    /**
     * Render the ending of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    public void encodeEnd(FacesContext context, UIComponent component)
    throws IOException {
        // Do nothing...
    }
        
    /**
     * Create a JSON array for the component and its label (if any).
     */
    private JSONArray getRenderedWidget(Login login, FacesContext context, 
            LoginCallback lcb, String key, JSONArray keys, Map keyMap) throws
            IOException, JSONException {
        
        JSONArray contentArray = new JSONArray();
        JSONArray keyArray = new JSONArray();
        
        String widgetType = lcb.getCallbackDataType(key);
        String labelData = (String)lcb.getCallbackDataLabel(key);
        setLabelData(labelData, contentArray, login, key, context);   
        
        JSONObject props = new JSONObject();
        props.put("widgetType", widgetType);
        props.put("id", key);
        
        if (widgetType.equalsIgnoreCase("text")) {
            props.put("value", (String)lcb.getCallbackDataValue(key));
            contentArray.put(props);
                        
        } else if (widgetType.equalsIgnoreCase("textField")) {
            
            props.put("value", (String)lcb.getCallbackDataValue(key));
            contentArray.put(props);
            
            keyArray.put(key);
            keyArray.put((String)lcb.getCallbackDataValue(key));
            keys.put(keyArray);
                        
        } else if (widgetType.equalsIgnoreCase("passwordfield")) {
            
            contentArray.put(props);
            keyArray.put(key);
            keys.put(keyArray);
                    
        } else if (widgetType.equalsIgnoreCase("dropDown") ||
                widgetType.equalsIgnoreCase("listbox")) {
            String[] list = lcb.getCallbackDataList(key);
            
            JSONArray optArray = new JSONArray();
            if (list != null && list.length > 0) {
                for (int i=0; i < list.length; i++) {
                    JSONObject opt = new JSONObject();
                    opt.put("group", false); // Not a group
                    opt.put("separator", false);  // Not a separator
                    opt.put("value", list[i]);
                    opt.put("label", list[i]);
                    optArray.put(opt);
                }
            }
            
            props.put("options", optArray);
            contentArray.put(props);
            
            keyArray.put(key);
            keys.put(keyArray);
            
        } else if (widgetType.equalsIgnoreCase("image")) {
            
            props.put("src", (String)lcb.getCallbackDataValue(key));
            contentArray.put(props);
        }
        if (contentArray.length() > 0) {
            return contentArray;
        } else {
            return null;
        }
    }
    
    /*
     * Set the labels associated with authentication data and input
     * fields.
     */
    private void setLabelData(String labelData, JSONArray contentArray, 
            Login login, String key, FacesContext context) 
            throws IOException, JSONException{
        
        if (labelData != null && labelData.length() > 0) {
                JSONObject stObject = new JSONObject();
                stObject.put("widgetType", "label");
                stObject.put("id", login.getClientId(context) + "_" + key + "_label");
                stObject.put("value", labelData);
                contentArray.put(stObject);
        }
    }
    
    private JSONObject getAlertData(FacesContext context, String type, 
        String summaryKey, String detailKey) {
        
        JSONObject json = new JSONObject();
        Theme theme = ThemeUtilities.getTheme(context);
        
        try {
            // Add alert type.
            if (type != null && type.length() > 0) {
                json.put("type", type);
            }
            if (summaryKey != null && summaryKey.length() > 0) {
                String summary = theme.getMessage(summaryKey);
                if (summary != null) {
                    json.put("summary", summary); 
                } else {
                    json.put("summary", summaryKey);
                }
            }
            if (detailKey != null && detailKey.length() > 0) {
                String detail = theme.getMessage(detailKey);
                if (detail != null) {
                    json.put("detail", detail);
                } else {
                    json.put("detail", detailKey);
                }
            }
            return json;
        } catch (JSONException je) {
            return null;
        }
    }
}
