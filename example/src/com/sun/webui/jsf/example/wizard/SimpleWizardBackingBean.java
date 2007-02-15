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

package com.sun.webui.jsf.example.wizard;

import java.io.Serializable;
import java.io.File;
import java.net.InetAddress;

import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.application.FacesMessage;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.sun.webui.jsf.component.Wizard;
import com.sun.webui.jsf.component.WizardStep;
import com.sun.webui.jsf.event.WizardEvent;
import com.sun.webui.jsf.event.WizardEventListener;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.example.index.IndexBackingBean;
import com.sun.webui.jsf.example.common.MessageUtil;

/**
 * Backing bean for Simple Wizard example.
 */
public class SimpleWizardBackingBean implements Serializable {

    // Define constants
    private static final String SPECIAL_PSWD_CHARS = "?()[]!";
    private static final String DEFAULT_HOME_PATH = "/export/home/";
    private static final String PASSWORD_SETTING_LOCKED = "Locked";
    private static final String PASSWORD_SETTING_FIRSTLOGIN = "First Login";
    private static final String PASSWORD_SETTING_CREATE = "Create now";

    // Bean properties.
    private String userName = "";
    private String userDesc = "";
    private String userUid = "";
    private boolean uidAutoGenerate = false;
    private boolean uidSet = true;
    private String userPswd = "";
    private String userPswdConfirm = "";
    private boolean pswdNow = true;
    private boolean pswdLocked = false;
    private boolean pswdFirstLogin = false;
    private String primaryGroupName = "other";
    private String[] secondaryGroupNames = new String[0];
    private String homeServer = "";
    private String homePath = null;
    private String resultMessage = "Invalid results!";

    // Private members
    private String checkPswd = null;

    // Data lists used by some wizard steps.
    private Option[] primaryGroupList;
    private Option[] secondaryGroupList;

    /**
     * Constructor initializes some list elements.
     */
    public SimpleWizardBackingBean() {

	primaryGroupList = initPrimaryGroupList();
        secondaryGroupList = initSecondaryGroupList();

    } // Constructor
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Value binding methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** Getter for user name */
    public String getUserName() {
	return (userName);
    }

    /** Setter for user name */
    public void setUserName(String name) {
	userName = name;
    }

    /** Getter for user description */
    public String getUserDescription() {
	return (userDesc);
    }

    /** Setter for user description */
    public void setUserDescription(String desc) {
	userDesc = desc;
    }

    /** Getter for user uid */
    public String getUserUid() {
	return (userUid );
    }

    /** Setter for user uid */
    public void setUserUid(String uid) {
	userUid = uid;
    }

    /** Getter for uid auto generate radio button */
    public boolean isUidAutoGenerate() {
	return(uidAutoGenerate);
    }

    /** Setter for uid auto generate radio button */
    public void setUidAutoGenerate(boolean value) {
	uidAutoGenerate = value;
	if (uidAutoGenerate) {
	    userUid = generateUserUid();
	}
    }

    /** Getter for uid explict set radio button */
    public boolean isUidSet() {
	return(uidSet);
    }

    /** Setter for uid explict set radio button */
    public void setUidSet(boolean value) {
	uidSet = value;
	if (uidSet) {
	    userUid = "";
	}
    }

    /** Getter for uid field disabled state */
    public boolean isUidDisabled() {
	// UID field is disabled when auto generate is selected.
	return (uidAutoGenerate);
    }

    /** Setter for uid field disabled state */
    public void setUidDisabled(boolean value) {
	// Not explicitly set; derived from radio button settings.
    }

    /** Getter for user password */
    public String getUserPassword() {
	return (userPswd);
    }

    /** Setter for user password */
    public void setUserPassword(String pswd) {
	userPswd = pswd;
    }

    /** Getter for user password confirmation */
    public String getUserPasswordConfirm() {
	return (userPswdConfirm);
    }

    /** Setter for user password confirm */
    public void setUserPasswordConfirm(String pswd) {
	userPswdConfirm = pswd;
    }

    /** Getter for password locked radio button */
    public boolean isPswdLocked() {
	return(pswdLocked);
    }

    /** Setter for password locked radio button */
    public void setPswdLocked(boolean value) {
	pswdLocked = value;
    }

    /** Getter for password on first login radio button */
    public boolean isPswdFirstLogin() {
	return(pswdFirstLogin);
    }

    /** Setter for password on first login radio button */
    public void setPswdFirstLogin(boolean value) {
	pswdFirstLogin = value;
    }

    /** Getter for password now radio button */
    public boolean isPswdNow() {
	return(pswdNow);
    }

    /** Setter for password now radio button */
    public void setPswdNow(boolean value) {
	pswdNow = value;
	// If set now is not selected, clear password fields.
	if (! pswdNow) {
	    userPswd = "";
	    userPswdConfirm = "";
	}
    }

    /** Getter for password field disabled state */
    public boolean isPasswordDisabled() {
	// Password is disabled if Set Now is not enabled.
	return (! pswdNow);
    }

    /** Setter for password field disabled state */
    public void setPasswordDisabled(boolean value) {
	// Not explicitly set; derived from radio button settings.
    }

    /** Getter for password confirmation field disabled state */
    public boolean isPasswordConfirmDisabled() {
	// Confirmation is disabled if Set Now is not enabled.
	return (! pswdNow);
    }

    /** Setter for password confirmation field disabled state */
    public void setPasswordConfirmDisabled(boolean value) {
	// Not explicitly set; derived from radio button settings.
    }

    /** Getter for primary group name */
    public String getPrimaryGroupName() {
	return (primaryGroupName);
    }

    /** Setter for primary group name */
    public void setPrimaryGroupName(String groupName) {
	primaryGroupName = groupName;
    }

    /** Getter for home directory server */
    public String getHomeServer() {
	return (homeServer);
    }

    /** Setter for home directory server */
    public void setHomeServer(String serverName) {
	homeServer = serverName;
    }

    /** Getter for home directory path */
    public String getHomePath() {
	String val = homePath;
	if ((val == null) || (val.length() == 0)) {
	    val = DEFAULT_HOME_PATH;
	    if (userName != null) {
		val = val + userName;
	    }
	}
	return (val);
    }

    /** Setter for home directory path */
    public void setHomePath(String pathName) {
	homePath = pathName;
    }

    /** Getter for home directory server and path */
    public String getHomeDirectory() {
	return (homeServer + ":" + homePath);
    }

    /** Getter for primary group drop down list */
    public Option[] getPrimaryGroupList() {
	return (primaryGroupList);
    }

    /** Getter for selected secondary group names */
    public String[] getSecondaryGroupNames() {
	if (secondaryGroupNames == null) {
	    return (new String[0]);
	} else {
	    return (secondaryGroupNames);
	}
    }

    /** Setter for selected secondary group names */
    public void setSecondaryGroupNames(String[] groupNames) {
	secondaryGroupNames = groupNames;
    }

    /** Getter for secondary group available list */
    public Option[] getSecondaryGroupList() {
	return (secondaryGroupList);
    }

    /** Getter for password setting option */
    public String getPasswordSetting() {
	String val = "";
	if (pswdLocked) {
	    val = PASSWORD_SETTING_LOCKED;
	} else if (pswdFirstLogin) {
	    val = PASSWORD_SETTING_FIRSTLOGIN;
	} else if (pswdNow) {
	    val = PASSWORD_SETTING_CREATE;
	}
	return (val);
    }

    /** Setter for password setting option */
    public void setPasswordSetting() {
	// Not set, but derived from radio settings.
    }

    /** Getter for results of operation */
    public String getResultMessage() {
	return (resultMessage);
    }
	    
    /** Setter for results of operation */
    public void setResultMessage(String msg) {
	resultMessage = msg;
    }

    /** Getter for wizard event handler */
    public WizardEventListener getWizardEventListener() {
	return (new SimpleWizardEventListener(this));
    }

    /** Getter for wizard step event handler */
    public WizardEventListener getWizardStepEventListener() {
	return (new SimpleWizardEventListener(this));
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Validator methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Validator for user name.  Name must be a string of ASCII
     * characters from 6 to 32 bytes long comprised of lower case letters
     * and numbers, and beginning with a letter.
     *
     * @throws ValidatorException
     */
    public void validateUserName(FacesContext context,
	UIComponent component, Object value) throws ValidatorException {

        if (value != null) {
	    boolean bValid = false;
	    String user = value.toString();
	    char[] cs = user.toCharArray();
	    String msgKey = "wiz_invalid_username_1";
	    if (Character.isLowerCase(cs[0])) {
		// Check for a valid user name length.
		msgKey = "wiz_invalid_username_2";
		if ((cs.length > 5) && (cs.length < 33)) {
		    // Check for valid characters in the name.
		    bValid = true;
		    for (int i = 0; i < cs.length; i++) {
			if ((Character.isLowerCase(cs[i])) ||
			    (Character.isDigit(cs[i]))) {
			    continue;
			}
			msgKey = "wiz_invalid_username_3";
			bValid = false;
			break;
		    }				// End of for
		    if (bValid) {
			// Check first character is valid.
			if (! Character.isLowerCase(cs[0])) {
			    msgKey = "wiz_invalid_username_4";
			    bValid = false;
			}
		    }
		}
	    }
            if (! bValid) {
                String msgString =
		    MessageUtil.getMessage(msgKey);
                FacesMessage msg = new FacesMessage(msgString);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }

    } // validateUserName

    /**
     * Validator for user uid.  Name must be an integer number
     * from 100 to 65535.  We should check if the UID is already
     * in use, but this is not part of this example.
     *
     * @throws ValidatorException
     */
    public void validateUserUid(FacesContext context,
            UIComponent component, Object value)
            throws ValidatorException {

	int uid = -1;
	boolean bValid = false;
        if (value != null) {
	    String msgKey = "";
	    try {
		uid = (new Integer ((String) value)).intValue();
		msgKey = "wiz_invalid_userid_2";
		if ((uid >= 100) & (uid < 65536)) {
		    bValid = true;
		}
	    } catch (Exception ex) {
		// Number format error
		msgKey = "wiz_invalid_userid_1";
	    }
            if (! bValid) {
                String msgString =
		    MessageUtil.getMessage(msgKey);
                FacesMessage msg = new FacesMessage(msgString);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
	}

    } // validateUserUid

    /**
     * Validator for user password.  Must be an ASCII string
     * at least 8 bytes long consisting of lower case and
     * upper case alphabetic characters, numbers, and some
     * specific punctuation marks.
     *
     * @throws ValidatorException
     */
    public void validateUserPassword(FacesContext context,
            UIComponent component, Object value)
            throws ValidatorException {

	// We only validate the password if the option to
	// set it now was selected.  Note that a value must
	// be specified for the password field in this case.
	if (! pswdNow) {
	    return;
	}
	checkPswd=null;
	String pswd = null;
	boolean bValid = false;
	String msgKey = "wiz_missing_password";
        if (value != null) {
	    pswd = value.toString();
	    char[] cs = pswd.toCharArray();
	    // Check for proper password length.
	    msgKey = "wiz_invalid_password_1";
	    if ((cs.length > 7) && (cs.length < 33)) {
		boolean bUpper = false;
		boolean bLower = false;
		boolean bDigit = false;
		boolean bSpecial = false;
		// Check for an invalid character.
		bValid = true;
		for (int i = 0; i < cs.length; i++) {
		    int iUpper = 0;
		    int iLower = 0;
		    int iDigit = 0;
		    int iSpecial = 0;
		    if (Character.isLowerCase(cs[i])) {
			bLower = true;
			iLower = 1;
		    }
		    if (Character.isUpperCase(cs[i])) {
			bUpper = true;
			iUpper = 1;
		    }
		    if (Character.isDigit(cs[i])) {
			bDigit = true;
			iDigit = 1;
		    }
		    if (SPECIAL_PSWD_CHARS.indexOf(cs[i]) >= 0) {
			bSpecial = true;
			iSpecial = 1;
		    }
		    if ((iLower + iUpper + iDigit + iSpecial) == 0) {
			msgKey = "wiz_invalid_password_2";
			bValid = false;
			break;
		    }
		}				// End of for
		// Check that we have at least one character from
		// each category.
		if (bValid) {
		    if (! bLower) {
			msgKey = "wiz_invalid_password_3";
			bValid = false;
		    } else if (! bUpper) {
			msgKey = "wiz_invalid_password_4";
			bValid = false;
		    } else if (! bDigit) {
			msgKey = "wiz_invalid_password_5";
			bValid = false;
		    } else if (! bSpecial) {
			msgKey = "wiz_invalid_password_6";
			bValid = false;
		    }
		}
	    }
	}
        if (! bValid) {
            String msgString = MessageUtil.getMessage(msgKey,
			SPECIAL_PSWD_CHARS);
            FacesMessage msg = new FacesMessage(msgString);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
	checkPswd = pswd;

    } // validateUserPassword

    /**
     * Validator for confirming user password.  Assumes it is
     * called after the first password validator above.  Checks
     * that the confirmation value equals the original password
     * value.
     *
     * @throws ValidatorException
     */
    public void confirmUserPassword(FacesContext context,
            UIComponent component, Object value)
            throws ValidatorException {

	// We only confirm the password if the option to set a
	// password now was selected.  If checking, the password
	// field validator left us the password value.
	if (! pswdNow) {
	    return;
	}
	boolean bValid = true;
	String msgKey = "";
        if (value != null) {
	    String pswd = value.toString();
	    if (checkPswd != null) {
		if (! checkPswd.equals(pswd)) {
		    msgKey = "wiz_invalid_confirm";
		    bValid = false;
		}
	    }
	} else {
	    msgKey = "wiz_missing_confirm";
	    bValid = false;
	}
	checkPswd = null;
        if (! bValid) {
            String msgString = MessageUtil.getMessage(msgKey);
            FacesMessage msg = new FacesMessage(msgString);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

    } // confirmUserPassword

    /**
     * Validator for home directory server.  Must be a host
     * accessible from this server.
     *
     * @throws ValidatorException
     */
    public void validateHomeServer(FacesContext context,
            UIComponent component, Object value)
            throws ValidatorException {

	String host = null;
	boolean bValid = false;
        if (value != null) {
	    try {
		host = (String) value;
		InetAddress.getByName(host);
		bValid = true;
	    } catch (Exception ex) {
		// Unknown host error
	    }
            if (! bValid) {
                String msgString =
		    MessageUtil.getMessage("wiz_invalid_servername");
                FacesMessage msg = new FacesMessage(msgString);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }

    } // validateHomeServer

    /**
     * Validator for home directory path.  Must be a fully
     * qualified path name.  We should check that the directory
     * exists as a shared file system path on the home directory
     * server, but that is not part of this example.
     *
     * @throws ValidatorException
     */
    public void validateHomePath(FacesContext context,
            UIComponent component, Object value)
            throws ValidatorException {

	String path = null;
	boolean bValid = false;
        if (value != null) {
	    try {
		path = (String) value;
		if ((path.length() > 0) &&
		    (! path.equals(DEFAULT_HOME_PATH))) {
		    File fPath = new File(path);
		    if (fPath.isAbsolute()) {
			bValid = true;
		    }
		}
	    } catch (Exception ex) {
		// Class cast error
	    }
            if (! bValid) {
                String msgString =
		    MessageUtil.getMessage("wiz_invalid_pathname");
                FacesMessage msg = new FacesMessage(msgString);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }

    } // validateHomePath

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Initialize methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Set auto generated user identifier.
    // Probably OS specific, but we just fake it for our example.
    private String generateUserUid() {

	long t = System.currentTimeMillis();
	int uid = (int) (t - ((t/1000)*1000)) + 100;
	return (Integer.toString(uid));

    } // generateUserUid

    // Generate the list of primary group names.
    // Typically accessed from underlying OS.
    private Option[] initPrimaryGroupList() {

	Option[] list = new Option[6];
	list[0] = new Option("staff", "staff");
	list[1] = new Option("engineering", "engineering");
	list[2] = new Option("marketing", "marketing");
        list[3] = new Option("sysadmin", "sysadmin");
        list[4] = new Option("other", "other");
        list[5] = new Option("nobody", "nobody");
	return (list);

    } // initPrimaryGroupList

    // Generate the list of secondary group names.
    // Typically accessed from underlying OS.
    private Option[] initSecondaryGroupList() {

	Option[] list = new Option[11];
	list[0] = new Option("east", "east");
	list[1] = new Option("bur_eng", "bur_eng");
	list[2] = new Option("bur_mktg", "bur_mktg");
        list[3] = new Option("central", "central");
        list[4] = new Option("brm_eng", "brm_eng");
        list[5] = new Option("brm_mktg", "brm_mktg");
        list[6] = new Option("west", "west");
        list[7] = new Option("mpk_eng", "mpk_eng");
        list[8] = new Option("mpk_mktg", "mpk_mktg");
        list[9] = new Option("sca_eng", "sca_eng");
        list[10] = new Option("sca_mktg", "sca_mktg");
	return (list);

    } // initSecondaryGroupList

    // Reset state.  Called by wizard event handler when
    // wizard is complete.  Method is package protected.
    void clearState() {

	userName = "";
	userDesc = "";
	userUid = "";
	uidAutoGenerate = false;
	uidSet = true;
	userPswd = "";
	userPswdConfirm = "";
	pswdNow = true;
	pswdLocked = false;
	pswdFirstLogin = false;
	primaryGroupName = "other";
	secondaryGroupNames = new String[0];
	homeServer = "";
	homePath = "";
	resultMessage = "Invalid results!";

    } // clearState

} // SimpleWizardBackingBean

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// Event handling class
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
  * Handle wizard events based on wizard NavigationEvent state.
  * We handle the FINISH event to indicate that we must perform
  * the actual operation of adding the new user account and then
  * set the results message based on the outcome of that operation.
  * We handle the COMPLETE event to indicate we should reset all
  * wizard properties so the next execution will start with
  * initial values.  All other events are ignored.
  */
class SimpleWizardEventListener implements WizardEventListener {

    // Reference to our backing bean instance.
    private SimpleWizardBackingBean bean;

    /** Constructor accepts backing bean reference */
    public SimpleWizardEventListener(SimpleWizardBackingBean bean) {

	this.bean = bean;

    } // Constructor

    /** Wizard event handling method */
    public boolean handleEvent(WizardEvent event) {

	Wizard wiz = event.getWizard();
	// WizardStep step = event.getStep();
	// String stepTitle = step.getTitle();
	// System.out.println("Wizard event step: " + stepTitle);
	switch (event.getNavigationEvent()) {

	    // FINISH event indicates we can add our new user.
	    // In this example, we simply set the success message.
	    case WizardEvent.FINISH:
		String messageString =
		    MessageUtil.getMessage("wiz_simple_result",
		    bean.getUserName());
		bean.setResultMessage(messageString);
		break;

	    // COMPLETE event indicates we are all done.
	    // Reset the wizard be removing all its children,
	    // which forces next execution to reinitialize them,
	    // and clearing the bean state.
	    case WizardEvent.COMPLETE:
		wiz.getChildren().clear();
		bean.clearState();
		break;

	    // All other events are ignored.
	    default:

		break;
	}					// End of switch
	return (true);

    } // handleEvent

    // Methods for JSF state saving must be implemented.
    // Since we are not participating in state saving, we
    // return true for isTransient.
    public void setTransient(boolean transientFlag) { }
    public boolean isTransient() { return true; }
    public Object saveState(FacesContext context) { return null; }
    public void restoreState(FacesContext context, Object state) { }

} // SimpleWizardEventListener
