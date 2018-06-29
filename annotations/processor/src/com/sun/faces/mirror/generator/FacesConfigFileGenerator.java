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

package com.sun.faces.mirror.generator;

import com.sun.faces.mirror.DeclaredComponentInfo;
import com.sun.faces.mirror.DeclaredRendererInfo;
import java.util.Set;

/**
 * Base generator for the faces configuration file.
 *
 * @author gjmurphy
 */
abstract public class FacesConfigFileGenerator extends FileGenerator {

    private Set<DeclaredComponentInfo> declaredComponentInfoSet;

    /**
     * Protected getter for property declaredComponentInfoSet.
     */
    protected Set<DeclaredComponentInfo> getDeclaredComponentInfoSet() {
        return this.declaredComponentInfoSet;
    }

    /**
     * Setter for property declaredComponentInfoSet.
     */
    public void setDeclaredComponentInfoSet(Set<DeclaredComponentInfo> declaredComponentInfoSet) {
        this.declaredComponentInfoSet = declaredComponentInfoSet;
    }

    private Set<DeclaredRendererInfo> declaredRendererInfoSet;

    /**
     * Protected getter for property declaredRendererInfoSet.
     */
    protected Set<DeclaredRendererInfo> getDeclaredRendererInfoSet() {
        return this.declaredRendererInfoSet;
    }

    /**
     * Setter for property declaredRendererInfoSet.
     */
    public void setDeclaredRendererInfoSet(Set<DeclaredRendererInfo> declaredRendererInfoSet) {
        this.declaredRendererInfoSet = declaredRendererInfoSet;
    }

    private Set<String> declaredPropertyResolverNameSet;

    /**
     * Protected getter for property declaredPropertyResolverNameSet.
     * @return Value of property declaredPropertyResolverNameSet.
     */
    protected Set<String> getDeclaredPropertyResolverNameSet() {
        return this.declaredPropertyResolverNameSet;
    }

    /**
     * Setter for property declaredPropertyResolverNameSet.
     * @param declaredPropertyResolverNameSet New value of property declaredPropertyResolverNameSet.
     */
    public void setDeclaredPropertyResolverNameSet(Set<String> declaredPropertyResolverNameSet) {
        this.declaredPropertyResolverNameSet = declaredPropertyResolverNameSet;
    }
    

    private Set<String> declaredVariableResolverNameSet;

    /**
     * Protected getter for Variable declaredVariableResolverNameSet.
     * @return Value of Variable declaredVariableResolverNameSet.
     */
    protected Set<String> getDeclaredVariableResolverNameSet() {
        return this.declaredVariableResolverNameSet;
    }

    /**
     * Setter for Variable declaredVariableResolverNameSet.
     * @param declaredVariableResolverNameSet New value of Variable declaredVariableResolverNameSet.
     */
    public void setDeclaredVariableResolverNameSet(Set<String> declaredVariableResolverNameSet) {
        this.declaredVariableResolverNameSet = declaredVariableResolverNameSet;
    }

    
    private Set<String> declaredJavaEEResolverNameSet;

    /**
     * Protected getter for JavaEE declaredJavaEEResolverNameSet.
     * @return Value of JavaEE declaredJavaEEResolverNameSet.
     */
    protected Set<String> getDeclaredJavaEEResolverNameSet() {
        return this.declaredJavaEEResolverNameSet;
    }

    /**
     * Setter for JavaEE declaredJavaEEResolverNameSet.
     * @param declaredJavaEEResolverNameSet New value of JavaEE declaredJavaEEResolverNameSet.
     */
    public void setDeclaredJavaEEResolverNameSet(Set<String> declaredJavaEEResolverNameSet) {
        this.declaredJavaEEResolverNameSet = declaredJavaEEResolverNameSet;
    }
    
}
