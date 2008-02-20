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
package com.sun.webui.jsf.component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import javax.security.auth.Subject;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.LogUtil;

import com.sun.webui.jsf.model.login.JaasController;
import com.sun.webui.jsf.model.login.LoginCallbackHandler;
import com.sun.webui.jsf.model.login.LoginController;
import com.sun.webui.jsf.model.login.JaasLoginController;
import com.sun.webui.jsf.model.login.LoginCallback;
import com.sun.webui.jsf.model.login.LoginConstants;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.ResponseWriter;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;


/**
 * <p>
 * 
 * The Login component provides an authentication 
 * presentation layer that can plug in different authentication 
 * technologies at the backend. The component currently supports
 * JAAS based authentication. This component is an EditableValueHolder.
 * There are essentially two threads in play here - the presentation 
 * layer's thread and a controller thread that performs the actual 
 * authentication. Interaction between the two threads happens during the
 * validation phase of the JSF lifecycle. The interaction happens via
 * a callback object that originates from the underlying authentication implementation
 * class. This callback object contains a list of input fields that need
 * to be displayed on the client side for user data entry. During validation
 * the component can be in one of three states based on the state of the client
 * and the underlying authentication mechanism: </p>
 * <ul>
 * <li>
 * INIT : An XHR call has been made by the client to initiate the 
 * authentication process. The JAAS controller thread has not yet started. 
 * The validation phase fires up the controller thread, starts the
 * authentication process and waits for a response form controller. The
 * response comes in the form of a LoginCallback object getting placed in the
 * session map. The controller thread is also stored in the session map
 * for later use. The component directly goes to the render response phase
 * to render input and other elements needed for user authentication. At this 
 * time the controller thread is waiting for client input.
 * </li>
 * <li>
 * CONTINUE:  User data entry and request for authentication triggers the Login
 * component to go through its lifecycle yet again. The validation phase extracts the
 * user entered data from the request parameter list based on the clientID
 * of the child components and sets it in the callback object. This callback
 * object is placed in the session map and the controller
 * thread is notified that client input is available. The prsenetation 
 * thread waits for a response from the controller thread about
 * the status of the authentication process. The status could be CONTINUE
 * again which means there are more authentication modules that need to go through
 * the same cycle. In this case the validator saves the next 
 * LoginCallback object for use by the renderer and triggers the RenderResponse
 * phase. The status could also be SUCCESS or FAILURE which are described next.
 * </li>
 * <li>
 * FAILURE: This means that the authentication process has failed. The validation
 * process ends with an error. 
 * </li>
 * <li>
 * SUCCESS: This means that the authentication process has completed successfully. 
 * This also indicates that the autenticated Subject object is available in
 * the session map. This value is extracted and set as the value of the component.
 * Only after a successful authentication process does the component go through
 * processUpdates phase. 
 * </li>
 * </ul>
 * An application can specify a URL to redirect to after successful 
 * authentication.
 *
 */
@Component(type="com.sun.webui.jsf.Login", family="com.sun.webui.jsf.Login",
    displayName="Login", tagName="login",
    tagRendererType="com.sun.webui.jsf.widget.Login")
public class Login extends WebuiInput implements NamingContainer {
     
    private static final String CONTROLLER_MAP_KEY = 
        "com_sun_webui_jsf_login_controller";
    
    /**
     * <p>Construct a new <code>Login</code>.</p>
     */
    
    public Login() {
        super();
        setRendererType("com.sun.webui.jsf.widget.Login");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Login";
    }
       
    /**
     * <p>Return the renderer type for this component.</p>
     */
    public String getRendererType() {
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.Login";
        } else {
            return super.getRendererType();
        }
    }
    
    /**
     * Alternative HTML template to be used by this component.
     */
    @Property(name="htmlTemplate", displayName="HTML Template", category="Appearance")
    private String htmlTemplate = null;

    /**
     * Get alternative HTML template to be used by this component.
     */
    public String getHtmlTemplate() {
        if (this.htmlTemplate != null) {
            return this.htmlTemplate;
        }
        ValueExpression _vb = getValueExpression("htmlTemplate");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Set alternative HTML template to be used by this component.
     */
    public void setHtmlTemplate(String htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }
    
    @Property(name="callback", displayName="State of the Authentication process", 
        isHidden=true, isAttribute=false)
    private LoginConstants.LOGINSTATE loginState = null;
    /**
    * <p>Defines the state of the authentication process. The states should
    * be one of the following: 
    * INIT - initialization of authentication process
    * CONTINUE - authentication process currently in progress, and
    * SUCCESS - authentication process completed successfully.
    * FAILURE - authentication process has ended in failure.
    * </p>
    */
    public LoginConstants.LOGINSTATE getLoginState() {
        return this.loginState;
    }

    /**
     * Sets the current state of the authentication process.
     * @see #getLoginState()
     */
    public void setLoginState(LoginConstants.LOGINSTATE loginState) {
        this.loginState = loginState;
    }
    
    /**
     * <p> A Map containing a mapping between the IDs 
     * of the callback data objects as supplied by the 
     * underlying authentication infrastructure and 
     * their corresponding clientIDs for the corresponding JSF components.
     * This information is required to extract the
     * data entered by the user from the request header map.</p>
     */
    @Property(name="authenticationKeys", category="Behavior", isHidden=true, isAttribute=false)
    private Map authenticationKeys = null;
    
    /**
     * <p> Sets a map containing a mapping between the IDs 
     * of the callback data objects as supplied by the 
     * underlying authentication infrastructure and 
     * their corresponding clientIDs for the corresponding JSF components.
     * This information is required to extract the
     * data entered by the user from the request header map.</p>
     */
    public void setAuthenticationKeys(Map keys) {
        this.authenticationKeys = keys;
    }
    
    /**
     * <p> Returns a map containing a mapping between the IDs 
     * of the callback data objects as supplied by the 
     * underlying authentication infrastructure and 
     * their corresponding clientIDs for the corresponding JSF components.
     * This information is required to extract the
     * data entered by the user from the request header map.</p>
     */
    public Map getAuthenticationKeys() {
        return this.authenticationKeys;
    }

    /**
     * <p> A boolean attribute indicating if the login component should
     * initiate the authentication process on its own without any client side
     * intervention or wait for some other client side entity to start
     * the authentication process. By default this attribute is set to true.
     * In situations where the authentication process needs to start based on
     * other events happening on the client side this attribute should be set
     * to false and the appropriate client side API used to fire up the 
     * process.</p>
     */
    @Property(name="autoStart", displayName="Auto Start", category="Behavior")
    private boolean autoStart = true;
    private boolean autoStart_set = false;

    /**
     * <p> A boolean attribute indicating if the login component should
     * initiate the authentication process on its own without any client side
     * intervention or wait for some other client side entity to start
     * the authentication process. By default this attribute is set to true.
     * In situations where the authentication process needs to start based on
     * other events happening on the client side this attribute should be set
     * to false and the appropriate client side API used to fire up the 
     * process.</p>
     */
    public boolean isAutoStart() {
        if (this.autoStart_set) {
            return this.autoStart;
        }
        ValueExpression ve = getValueExpression("autoStart");
        if (ve != null) {
            Object result = ve.getValue(getFacesContext().getELContext());
            if (result == null) {
                return false;
            } else {
                return ((Boolean) result).booleanValue();
            }
        }
        return true;
    }

    /**
     * <p>Set autoStart to false to control the time when authentication
     * should start.</p>
     * @see #isAutoStart()
     */
    public void setAutoStart(boolean autoStart) {
        this.autoStart = autoStart;
        this.autoStart_set = true;
    }
    
     // loginClass
    private String loginClass = null;

    /**
     * <p>Return the name of the login module implementation class 
     * where the request came from. This information is
     * needed by the callback handler to prompt users for
     * data or figure out error conditions.</p>
     */
    public String getLoginClass() {
        if (this.loginClass != null) {
            return this.loginClass;
        }
        return null;
    }

    /**
     * <p>Set the name of the login module implementation class 
     * where the request came from. This information is
     * needed by the callback handler to prompt users for
     * data or figure out error conditions.</p>
     */
    public void setLoginClass(String loginClass) {
        this.loginClass = loginClass;
    }

    /**
     * <p>Defines the URL to which the user should be redirected
     *         upon successful authentication.</p>
     */
    @Property(name="redirectURL", displayName="Redirect URL", category="Behavior")
    private String redirectURL = null;

/**
 * <p>Get the URL to which the user should be redirected
 *         upon successful authentication.</p>
 */
    public String getRedirectURL() {
        if (this.redirectURL != null) {
            return this.redirectURL;
        }
        ValueExpression ve = getValueExpression("redirectURL");
        if (ve != null) {
            return (String) ve.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Set the URL to which the user should be redirected
     * upon successful authentication.</p>
     * @see #getRedirectURL()
     */
    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    /**
     * <p>The name of an entry in the JAAS login configuration file.
     *    This is the name for the LoginContext to use to look up an 
     *    entry for this application in the JAAS login configuration 
     *    file, described here. Such an entry specifies the class(es) 
     *    that implement the desired underlying authentication technology(ies). 
     *    </p>
     */
    @Property(name="serviceName", displayName="JAAS service defn", category="Behavior")
    private String serviceName = null;

    /**
     * <p>Get the name of the JAAS login service from configuration file. 
     * The name of an entry in the JAAS login configuration file.
     * This is the name for the LoginContext to use to look up an 
     * entry for this application in the JAAS login configuration 
     * file, described here. Such an entry specifies the class(es) 
     * that implement the desired underlying authentication technology(ies). 
     * </p>
     */
    public String getServiceName() {
        if (this.serviceName != null) {
            return this.serviceName;
        }
        ValueExpression ve = getValueExpression("serviceName");
        if (ve != null) {
            return (String) ve.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Set the name of the JAAS login service from configuration file. </p>
     * 
     * @see #getServiceName()
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * <p>The login controller implementation class name. The controller must
     * implement the <code>LoginController</code> interface. If this attribute is
     * not supplied the default JAAS controller will be used. This attribute
     * provides the application developer to use a non JAAS based authentication
     * implementation with the same presentation layer. </p>
     */
    @Property(name="loginController", displayName="Login controller impelentation class name", 
        category="Behavior")
    private String loginController = null;

    /**
     * <p>Get the login controller implementation class name. If this attribute is
     * not supplied the default JAAS controller will be used. This attribute
     * provides the application developer to use a non JAAS based authentication
     * implementation with the same presentation layer. </p>
     */
    public String getLoginController() {
        if (this.loginController != null) {
            return this.loginController;
        }
        ValueExpression ve = getValueExpression("loginController");
        if (ve != null) {
            return (String) ve.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Set the Login controller implementation class.</p>
     * * @see #getLoginController()
     */
    public void setLoginController(String loginController) {
        this.loginController = loginController;
    }
    
    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     *         component is rendered.</p>
     */
    @Property(name="style", displayName="Outermost DIV Style", category="Appearance")
    private String style = null;

    /**
     * <p>Get the CSS style(s) to be applied to the outermost HTML element when 
     * the component is rendered.</p>
     */
    public String getStyle() {
        if (this.style != null) {
            return this.style;
        }
        ValueExpression ve = getValueExpression("style");
        if (ve != null) {
            return (String) ve.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Set the CSS style(s) to be applied to the outermost HTML element when 
     * the component is rendered.</p>
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     *        component is rendered.</p>
     */
    @Property(name="styleClass", displayName="Outermost DIV style selector", category="Appearance")
    private String styleClass = null;

    /**
     * <p> Get the CSS style class(es) to be applied to the outermost HTML element 
     *     when this component is rendered.</p>
     */
    public String getStyleClass() {
        if (this.styleClass != null) {
            return this.styleClass;
        }
        ValueExpression ve = getValueExpression("styleClass");
        if (ve != null) {
            return (String) ve.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p> Set the CSS style class(es) to be applied to the outermost HTML element 
     *     when this component is rendered.</p>
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    
    /**
     * <p>Flag indicating that event handling for this component should be
     * 	handled immediately (in Apply Request Values phase) rather than
     * 	waiting until Invoke Application phase.
     * 	May be desired for this component when required is true (although most
     * 	likely not).</p>
     */
    @Property(name="immediate", displayName="Immediate", category="Advanced")
    private boolean immediate = false;
    private boolean immediate_set = false;
    
    /**
     * <p>Flag indicating that event handling for this component should be
     * 	handled immediately (in Apply Request Values phase) rather than
     * 	waiting until Invoke Application phase.
     * 	May be desired for this component when required is true (although most
     * 	likely not).</p>
     */
    public boolean isImmediate() {
        if (this.immediate_set) {
            return this.immediate;
        }
        ValueExpression _vb = getValueExpression("immediate");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return false;
    }
    
    /**
     * <p>Flag indicating that event handling for this component should be
     * 	handled immediately (in Apply Request Values phase) rather than
     * 	waiting until Invoke Application phase.
     * 	May be desired for this component when required is true (although most
     * 	likely not).</p>
     * @see #isImmediate()
     */
    public void setImmediate(boolean immediate) {
        this.immediate = immediate;
        this.immediate_set = true;
    }
    
    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     */
    @Property(name="tabIndex", displayName="Tab Index", category="Accessibility", 
        editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int tabIndex = Integer.MIN_VALUE;
    private boolean tabIndex_set = false;

    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     */
    public int getTabIndex() {
        if (this.tabIndex_set) {
            return this.tabIndex;
        }
        ValueExpression _vb = getValueExpression("tabIndex");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     * @see #getTabIndex()
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
        this.tabIndex_set = true;
    }
    
    /**
     * <p> The callback object that contains information sent by the underlying
     * authentication layer to be used for generating presentation data that is
     * transmitted to the client side. The callback object is made available
     * during the validation phase of the component and contains entries that
     * correspond to input elements that need to be displayed at the client for 
     * users to enter authentication data. 
     * </p>
     */
    @Property(name="callbackObject", displayName="Callback Object", 
        isHidden=true, isAttribute=false)
    private LoginCallback callbackObject = null;
    
    /**
     * <p> Get the LoginCallback object.
     * The callback object that contains information sent by the underlying
     * authentication layer to be used for generating presentation data that is
     * transmitted to the client side. The callback object is made available
     * during the validation phase of the component and contains entries that
     * correspond to input elements that need to be displayed at the client for 
     * users to enter authentication data. 
     * </p>
     */
    public LoginCallback getCallbackObject() {
        
        return callbackObject;
    }

    /**
     * Sets the LoginCallback object for use during the Render Response phase.
     * @see #getCallbackObject()
     */
    public void setCallbackObject(LoginCallback callbackObject) {
        this.callbackObject = callbackObject;
    }
    
    
    /**
     * <p>Override the default {@link UIComponent#processDecodes}
     * processing to perform the following steps.</p>
     * <ul>
     * The Login component does not need all its children to be
     * decoded. All it needs is the value of the input elements that are
     * meaningful for the purposes of authentication. This data can be 
     * extracted from the request header. The behavior is the same 
     * irrespective of whether the component has been set to immediate
     * or not.
     * 
     * <li>If the <code>rendered</code> property of this {@link UIComponent}
     * is <code>false</code>, skip further processing.</li>
     * <li>Decode the Login component only</li>
     *
     * 
     * </ul>
     * @param context {@link FacesContext} for the current request
     *
     * @exception NullPointerException if <code>context</code>
     * is <code>null</code>
     */
    public void processDecodes(FacesContext context) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (!isRendered()) {
            return;
        }

       	decode(context);
        if (isImmediate()) {
            this.processValidators(context);
        }
    }

    /**
     * <p>Validation of user
     * entered data is done by posting them as key value pairs to callback object.
     * The callback object is read by the underlying authentication implementation
     * classes in order to authenticate the user.</p>
     * <ul>
     * <li>If the <code>rendered</code> property of this {@link UIComponent}
     * is <code>false</code>, skip further processing.</li>
     * <li>Validate the Login Component itself.
     * </li>
     * </ul>
     *
     * @param context {@link FacesContext} for the current request
     *
     * @exception NullPointerException if <code>context</code>
     * is <code>null</code>
     */
    public void processValidators(FacesContext context) {
        
        if (context == null) {
            throw new NullPointerException();
        }
        if (!isRendered()) {
            return;
        }
        
        Theme theme = getTheme(context);
        
        try {
            validate(context);
	} catch (RuntimeException rte) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                theme.getMessage("login.errorSummary"), rte.getLocalizedMessage());
            context.addMessage(getClientId(context), fm);
            context.renderResponse();
        }
        
        if (!isValid()) {
             FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                theme.getMessage("login.errorSummary"), 
                theme.getMessage("login.validationErr"));
            context.addMessage(getClientId(context), fm);
            context.renderResponse();
        }
    }

    /**
     * <p>Interaction with the underlying authentication infrastructure
     *  takes place in this method. By default the JAAS implementation
     *  of the controller is instantiated and used for interacting with
     *  the underlying the JAAS infrastructure. If the application 
     *  has supplied a different implementation that would be used.
     *  Once the controller is instantiated the authentication process is
     *  initiated and its result rendered. The object is the stored in
     *  the session. Subsequent invocations of this method would cause the
     *  object to be retrieved and based on the status of the callback 
     *  object act accordingly. If an exception is encountered the 
     *  controller invokes abort thereby stopping all activities in 
     *  the backend. Failure sets the component state to invalid and
     *  success sets the valid state to true, sets the local value of
     *  the component.</p>
     * 
     * @param context {@link FacesContext} for the current request
     *
     * @exception NullPointerException if <code>context</code>
     * is <code>null</code>
     */
    public void validate(javax.faces.context.FacesContext context) {
        
        if (context == null) {
            throw new NullPointerException();
        }
        
        Theme theme = getTheme(context);
        
        ExternalContext ec = context.getExternalContext();
        Object requestObj = ec.getRequest();
        
        LoginConstants.LOGINSTATE state = getLoginState();
        
        LoginController controllerClass = null;
                 
        if (state.equals(LoginConstants.LOGINSTATE.INIT)) {
            String loginClass = null;
            String jaasServiceName = getServiceName();
                    
            if (jaasServiceName == null) {
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    theme.getMessage("login.errorSummary"), 
                    theme.getMessage("login.svcNameError"));
                context.addMessage(getClientId(context), fm);
                setValid(false);
                
            } else {

                // check if an altrenate LoginController implementation 
                // has been supplied. If not, use the default one.
                
                String controllerClassName = getLoginController();
                if ((controllerClassName != null) && (controllerClassName.length() > 0)) {
                    try {
                        controllerClass = 
                            (LoginController)Class.forName(controllerClassName).newInstance();
                    } catch (Exception ex) {
                        LogUtil.severe("Could not instantiate controller class", ex);
                        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            theme.getMessage("login.errorSummary"), 
                            theme.getMessage("login.controllerError"));
                        context.addMessage(getClientId(context), fm);
                        setValid(false);
                    }
                } else {
                    controllerClass = new JaasLoginController();
                }
                
                try {
                    controllerClass.initialize(requestObj, jaasServiceName);
                    LoginCallback lcb = controllerClass.getCallbackObject();
                    if (lcb != null) {
                        setCallbackObject(lcb);
                        setLoginState(lcb.getLoginState());
                    }
                } catch (Exception e) {
                    LogUtil.severe("Validation exception", e);
                    controllerClass.abort(requestObj);
                    setValid(false);
                     FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        theme.getMessage("login.errorSummary"), 
                        theme.getMessage("login.genAuthFailure"));
                    context.addMessage(getClientId(context), fm);
                }
                ec.getSessionMap().put(CONTROLLER_MAP_KEY, controllerClass);
                context.renderResponse();
                                             
            }
        } else if (state.equals(LoginConstants.LOGINSTATE.CONTINUE)) {
		
            // Make the user entered data available to the
            // callback handler so that it can pass it on
	    // to the login modules.
            // Need to pass a map to the callback object
            // so that it can figure out what data it needs to 
            // pull out of the reqest.

            controllerClass = (LoginController)ec.getSessionMap().get(CONTROLLER_MAP_KEY);
            LoginCallback lcb = controllerClass.getCallbackObject();
            Map convertedValue = (Map) getConvertedValue(context, getSubmittedValue());
            Iterator it = convertedValue.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                lcb.setCallbackDataValue(key, (String)convertedValue.get(key));
            }
            try {
                              
                controllerClass.login(requestObj, lcb);
                lcb = controllerClass.getCallbackObject();
                LoginConstants.LOGINSTATE loginState = lcb.getLoginState();
                
                if (loginState.equals(LoginConstants.LOGINSTATE.SUCCESS)) {
                    setValue(controllerClass.getAuthenticatedEntity());
                    setValid(true);
                    
                } else if (loginState.equals(LoginConstants.LOGINSTATE.FAILURE)) {
                    setValid(false);
                    setLoginState(LoginConstants.LOGINSTATE.FAILURE);
                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        theme.getMessage("login.errorSummary"), 
                        theme.getMessage("login.ErrorDetail"));
                    context.addMessage(getClientId(context), fm);
                
                } else {
                    // set the callback object so that it can be used by the renderer 
                    lcb = controllerClass.getCallbackObject();
                    setLoginState(LoginConstants.LOGINSTATE.CONTINUE);
                    context.renderResponse();
                }
                setCallbackObject(lcb);
            } catch (Exception e) {
                LogUtil.severe("Validation exception", e);
                controllerClass.abort(requestObj);
                setValid(false);
            }
            
                        
        } else  {
            throw new FacesException("Login component has reached an invalid state: " + 
                    state);
        }
    }
    
    /**
     * Perform the following algorithm to update the model data 
     * associated with this UIInput, if any, as appropriate. By
     * default the Subject object generated after a successful
     * authentication would be used to update the model data.
     *
     * If the valid property of the Login component is false, take no 
     * further action. 
     * If the localValueSet property of this component is false, 
     * take no further action.
     * If no ValueBinding for value exists, take no further action.
     * Call setValue() method of the ValueBinding to update the value
     * that the ValueBinding points at.
     * If the setValue() method returns successfully:
     * o Clear the local value of this UIInput.
     * o Set the localValueSet property of this UIInput to false.
     * If the setValue() method call fails:
     * o Enqueue an error message by calling addMessage() on 
     * the specified FacesContext instance.
     * o Set the valid property of this UIInput to false.
     *
     */

    public void updateModel(javax.faces.context.FacesContext context) {
        
        if (context == null) {
            throw new NullPointerException();
        }
                                                                                      
        if (!isValid() || !isLocalValueSet()) {
            return;
        }
        
        ValueExpression ve = getValueExpression("value");
        if (ve == null) {
            return;
        }
        
        try {
            ve.setValue(context.getELContext(), getLocalValue());
            setValue(null);
            setLocalValueSet(false);
            return;
        } catch (Exception e) {
                FacesMessage message =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        CONVERSION_MESSAGE_ID, e.getMessage());
                context.addMessage(getClientId(context), message);
                if (LogUtil.configEnabled()) {
                    LogUtil.config("Unable to update Model!", e); // NOI18N
                }
                setValid(false);
        }        
    }
    
    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.loginClass = (String) _values[1];
        this.htmlTemplate = (String) _values[2];
        this.loginState = (LoginConstants.LOGINSTATE) _values[3];
        this.redirectURL = (String) _values[4];
        this.serviceName = (String) _values[5];
        this.style = (String) _values[6];
        this.styleClass = (String) _values[7];
        this.callbackObject = ((LoginCallback) _values[8]);
        this.autoStart = ((Boolean) _values[9]).booleanValue();
        this.autoStart_set = ((Boolean) _values[10]).booleanValue();
        this.authenticationKeys = (Map) _values[11];
        this.loginController = (String) _values[12];
        this.immediate = ((Boolean) _values[13]).booleanValue();
        this.immediate_set = ((Boolean) _values[14]).booleanValue();
        this.tabIndex = ((Integer) _values[15]).intValue();
        this.tabIndex_set = ((Boolean) _values[16]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[17];
        _values[0] = super.saveState(_context);
        _values[1] = this.loginClass;
        _values[2] = this.htmlTemplate;
        _values[3] = this.loginState;
        _values[4] = this.redirectURL;
        _values[5] = this.serviceName;
        _values[6] = this.style;
        _values[7] = this.styleClass;
        _values[8] = this.callbackObject;
        _values[9] = this.autoStart ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = this.autoStart_set ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.authenticationKeys;
        _values[12] = this.loginController;
        _values[13] = this.immediate ? Boolean.TRUE : Boolean.FALSE;
        _values[14] = this.immediate_set ? Boolean.TRUE : Boolean.FALSE;
        _values[15] = new Integer(this.tabIndex);
        _values[16] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }

    private Theme getTheme(FacesContext context) {
        return ThemeUtilities.getTheme(context);
    }
    
}
