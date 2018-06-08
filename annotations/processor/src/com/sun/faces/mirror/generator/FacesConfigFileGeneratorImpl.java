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
import com.sun.mirror.apt.Messager;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.Set;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * An implementation of FacesConfigFileGenerator that creates the config file by
 * merging component and renderer info with a template file.
 * 
 * 
 * @author gjmurphy
 */
class FacesConfigFileGeneratorImpl extends FacesConfigFileGenerator {
    
    static final String TEMPLATE = "com/sun/faces/mirror/generator/FacesConfig.template";
    
    VelocityEngine velocityEngine;
    
    FacesConfigFileGeneratorImpl(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
    
    @Override
    public void generate() throws GeneratorException {
        Set<DeclaredComponentInfo> componentInfoSet = this.getDeclaredComponentInfoSet();        
        Set<DeclaredRendererInfo> rendererInfoSet = this.getDeclaredRendererInfoSet();
        Set<String> propertyResolverNameSet = this.getDeclaredPropertyResolverNameSet();
        Set<String> variableResolverNameSet = this.getDeclaredVariableResolverNameSet();
        Set<String> javaeeResolverNameSet = this.getDeclaredJavaEEResolverNameSet();
        PrintWriter printWriter = this.getPrintWriter();
        if (componentInfoSet.size() == 0 && rendererInfoSet.size() == 0)
            return;
        try {
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("date", DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date()));
            velocityContext.put("componentInfoSet", componentInfoSet);
            velocityContext.put("rendererInfoSet", rendererInfoSet);
            velocityContext.put("propertyResolverNameSet", propertyResolverNameSet);
            velocityContext.put("variableResolverNameSet", variableResolverNameSet);
            velocityContext.put("javaeeResolverNameSet", javaeeResolverNameSet);
            Template template = velocityEngine.getTemplate(TEMPLATE);
            template.merge(velocityContext, printWriter);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneratorException(e);
        }
    }
    
    @Override
    public String getFileName() {
        return "faces-config.xml";
    }
    
    @Override
    public String getDirectoryName() {
        return "META-INF";
    }
    
}
