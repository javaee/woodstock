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

package com.sun.webui.theme;

/**
 * <p>Exception describing a problem configuring a specified {@link Theme}.
 * This class has JDK 1.4 compatible constructor and method signatures,
 * but will also work in a 1.3 environment.</p>
 */
public class ThemeConfigurationException extends RuntimeException {
    

    /**
     * <p>Construct a ThemeConfigurationException with a message.</p>
     * @param message Message describing the configuration problem
     */
    public ThemeConfigurationException(String message) {
        super(message);
    }
}
