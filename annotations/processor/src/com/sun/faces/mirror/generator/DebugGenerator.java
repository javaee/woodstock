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

import com.sun.faces.mirror.ClassInfo;
import com.sun.faces.mirror.DeclaredComponentInfo;
import com.sun.faces.mirror.DeclaredRendererInfo;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 *
 * @author gjmurphy
 */
public class DebugGenerator extends Generator {
    
    final static String TEMPLATE = "com/sun/faces/mirror/generator/Debug.template";
    
    VelocityEngine velocityEngine;
    
    public DebugGenerator(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
    
    @Override
    public void generate() throws GeneratorException {
        try {
            VelocityContext velocityContext = new VelocityContext();
            String namespace = this.getNamespace();
            String namespacePrefix = this.getNamespacePrefix();
            velocityContext.put("packageNameSet", this.getPackageNameSet());
            velocityContext.put("componentInfoSet", this.getDeclaredComponentInfoSet());
            velocityContext.put("rendererInfoSet", this.getDeclaredRendererInfoSet());
            velocityContext.put("namespace", namespace == null ? "" : namespace);
            velocityContext.put("namespacePrefix", namespacePrefix == null ? "" : namespacePrefix);
            Template template = velocityEngine.getTemplate(TEMPLATE);
            PrintWriter printWriter = this.getPrintWriter();
            template.merge(velocityContext, printWriter);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneratorException(e);
        }
    }

    private String namespace;

    /**
     * Protected getter for property namespace.
     */
    protected String getNamespace() {
        return this.namespace;
    }

    /**
     * Setter for property namespace.
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    private String namespacePrefix;

    /**
     * Protected getter for property namespacePrefix.
     * @return Value of property namespacePrefix.
     */
    protected String getNamespacePrefix() {
        return this.namespacePrefix;
    }

    /**
     * Setter for property namespacePrefix.
     */
    public void setNamespacePrefix(String namespacePrefix) {
        this.namespacePrefix = namespacePrefix;
    }
    
    
    private Set<DeclaredComponentInfo> declaredComponentInfoSet;
    
    /**
     * Protected getter for property declaredComponentInfoSet.
     */
    protected Set<DeclaredComponentInfo> getDeclaredComponentInfoSet() {
        TreeSet<DeclaredComponentInfo> sortedSet = new TreeSet<DeclaredComponentInfo>(new ClassInfoComparator());
        sortedSet.addAll(this.declaredComponentInfoSet);
        return sortedSet;
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
        TreeSet<DeclaredRendererInfo> sortedSet = new TreeSet<DeclaredRendererInfo>(new ClassInfoComparator());
        sortedSet.addAll(this.declaredRendererInfoSet);
        return sortedSet;
    }
    
    /**
     * Setter for property declaredRendererInfoSet.
     */
    public void setDeclaredRendererInfoSet(Set<DeclaredRendererInfo> declaredRendererInfoSet) {
        this.declaredRendererInfoSet = declaredRendererInfoSet;
    }

    private Set<String> packageNameSet;

    /**
     * Getter for property packageNameSet.
     */
    protected Set<String> getPackageNameSet() {
        TreeSet<String> sortedSet = new TreeSet<String>();
        sortedSet.addAll(this.packageNameSet);
        return sortedSet;
    }

    /**
     * Setter for property packageNameSet.
     */
    public void setPackageNameSet(Set<String> packageNameSet) {
        this.packageNameSet = packageNameSet;
    }
    
    
    static class ClassInfoComparator implements Comparator {
        
        public int compare(Object obj1, Object obj2) {
            return ((ClassInfo)obj1).getQualifiedName().compareTo(((ClassInfo)obj2).getClassName());
        }
        
    }
    
}
