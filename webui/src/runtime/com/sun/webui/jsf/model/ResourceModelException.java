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
 * $Id: ResourceModelException.java,v 1.1 2007-02-16 01:32:12 bob_yennaco Exp $
 */

package com.sun.webui.jsf.model;

import javax.faces.validator.ValidatorException;
import javax.faces.application.FacesMessage;

/**
 *
 * Need to make this an interface as well.
 * And in our implementation log the exception as well.
 */
public class ResourceModelException extends ValidatorException {

    public ResourceModelException(FacesMessage fmsg) {
	super(fmsg);
    }

    public ResourceModelException(FacesMessage fmsg, Throwable cause) {
	super(fmsg, cause);
    }
}
