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

package com.sun.webui.jsf.model;


/** This interface defines methods to be implemented by the backing bean
 *  that supports AutoComplete functionality.
 * 
 */
public interface AutoComplete {
    /** 
     * Filter options according to the specified filter and return updated options.
     * It is reasonable to expect that number of options returned will be trimmed for
     * optimization purposes.
     * 
     * @param filter filter as typed in by user, may be null
     * @return Options[]
     */
    public Option[] getOptions(String filter);

}
