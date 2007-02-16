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
 * $Id: FileChooserLookInValidator.java,v 1.1 2007-02-16 01:51:58 bob_yennaco Exp $
 */
package com.sun.webui.jsf.validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.sun.webui.jsf.component.FileChooser;

// This is difficult to get right. We assume the
// Object is a String. But it is the converted value of
// a possible developer defined facet. If we require the object
// to be a native type of the FileChooserModel we should be ok.
//
/**
 * There has to be validator on the LookIn component because it
 * may be a developer defined facet. This is the only place where
 * the FileChooser policy can be enforced in order to not
 * have the local value set to an invalid value.
 *
 * Since all validators are given a chance to validate even if
 * one fails, other validators should not attempt to validate
 * if the component is invalid at the time a validator is called.
 *
 * This validator, if it determines the value invalid, will clear
 * the submitted value in order to ensure the last known
 * valid value is rendered.
 */
// Note that typing this by referencing FileChooser
// prevents using this in a general Chooser paradigm.
//
public class FileChooserLookInValidator implements Validator {

    public void validate(FacesContext context, UIComponent  component,
	    Object value) throws ValidatorException {

	FileChooser chooser = (FileChooser)component.getParent();
	chooser.validateLookInComponent(context, component, value);
    }
}
