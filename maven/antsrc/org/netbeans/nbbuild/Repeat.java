/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2018 Oracle and/or its affiliates. All rights reserved.
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

package org.netbeans.nbbuild;

import java.io.File;
import java.util.*;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Ant;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.CallTarget;
import org.apache.tools.ant.taskdefs.Property;

/**
 * For each of specified property values calls target task of a specified name
 * with property set to one of these values
 *
 * @author  Radim Kubacki
 */
public class Repeat extends Task {
    
    private List   values; // List<String>
    private String target;
    private String startdir;
    private String name;
    
    //
    // init
    //
    
    public Repeat() {
        values = new Vector();
        target    = null;
    }

    //
    // itself
    //

    /** Name of property that will be set for each call. */
    public void setName (String s) {
        log ("SET name = " + s, Project.MSG_DEBUG);

        name = s;
    }
    
    /** Comma separated list of values. */
    public void setValues (String s) {
        log ("SET values = " + s, Project.MSG_DEBUG);

        StringTokenizer tok = new StringTokenizer (s, ", ");
        values = new Vector ();
        while ( tok.hasMoreTokens() ) {
            values.add (tok.nextToken().trim());
        }
    }
    
    /** Name of target which will be used with ant task. If not specified,
     * owning target name is used.
     */
    public void setTarget (String s) {
        log ("SET target = " + s, Project.MSG_DEBUG);

        target = s;
    }

    /** Execute this task. */
    public void execute () throws BuildException {        
        if ( values.isEmpty() ) {
            throw new BuildException("You must set at least one value!", getLocation());
        }

        if ( target == null ) {
            throw new BuildException("Target must be set!", getLocation());
        }

        Iterator it = values.iterator();
        while ( it.hasNext() ) {
            String val = (String) it.next();

            log ("Process '" + val + "' location with '" + target + "' target ...", Project.MSG_VERBOSE);
            
            CallTarget antCall = (CallTarget) getProject().createTask("antcall");
            antCall.init();
            antCall.setLocation(getLocation());
            
            // ant.setDir (dir);
            antCall.setTarget (target);
            Property prop = antCall.createParam();
            prop.setName(name);
            prop.setValue(val);
            
            antCall.execute();
        }
    }
    
}
