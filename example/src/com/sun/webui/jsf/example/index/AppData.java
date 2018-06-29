/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.webui.jsf.example.index;

import com.sun.webui.jsf.example.index.AppAction;

/**
 * A convenient class to wrap data about a given example.
 */
public class AppData {    
    
    /** The example app name */
    private String name;

    /** The concepts illustrated by this example */
    private String concepts;
    
    /** The example app action */
    private String appAction;

    /** 
     * Array of file names that will have source code
     * links for this example
     */
    private String[] files;               
    
    /**
     * Accepts info necessary to describe the given
     * example.
     *
     * @param name The name of the example
     * @param concepts The concepts illustrated by this example
     * @param appAction The example app action
     * @param files Array of file names for this example
     */
    public AppData(String name, String concepts, String appAction, String[] files) {
        this.name = name;
	this.concepts = concepts;
        this.appAction = appAction;
	this.files = files;
    }    
    
    /**
     * Get the name of the example
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get the concepts illustrated by this example
     */
    public String getConcepts() {
        return concepts;
    }         
    
    /**
     * Get AppAction.
     */
    public AppAction getAppAction() {               
        return new AppAction(appAction);                
    }
    
    /**
     * Get array of files for this example
     */
    public String[] getFiles() {
        return files;
    }      
}
