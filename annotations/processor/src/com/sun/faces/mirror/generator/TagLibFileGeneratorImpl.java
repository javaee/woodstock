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
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * An implementation of TagLibFileGenerator that creates the taglib file by
 * merging component info with a template file.
 * 
 * @author gjmurphy
 */
class TagLibFileGeneratorImpl extends TagLibFileGenerator {
    
    static final String TEMPLATE = "com/sun/faces/mirror/generator/TagLib.template";
    
    VelocityEngine velocityEngine;
    
    
    TagLibFileGeneratorImpl(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
    
    @Override
    public void generate() throws GeneratorException {
        try {
            Set<DeclaredComponentInfo> componentInfoSet = new TreeSet(new Comparator() {
                public int compare(Object obj1, Object obj2) {
                    String tag1 = ((DeclaredComponentInfo) obj1).getTagName();
                    String tag2 = ((DeclaredComponentInfo) obj2).getTagName();
                    if (tag1 == null) {
                        if (tag2 == null)
                            return 0;
                        return -1;
                    } else {
                        return tag1.compareTo(tag2);
                    }
                }
            });
            componentInfoSet.addAll(this.getDeclaredComponentInfoSet());
            String namespace = this.getNamespace();
            String namespacePrefix = this.getNamespacePrefix();
            PrintWriter printWriter = this.getPrintWriter();
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("date", DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date()));
            velocityContext.put("componentInfoSet", componentInfoSet);
            velocityContext.put("namespace", namespace == null ? "" : namespace);
            velocityContext.put("namespacePrefix", namespacePrefix == null ? "" : namespacePrefix);
            Template template = this.velocityEngine.getTemplate(TEMPLATE);
            template.merge(velocityContext, printWriter);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneratorException(e);
        }
    }
    
    @Override
    public String getFileName() {
        return "taglib.xml";
    }
    
    @Override
    public String getDirectoryName() {
        return "META-INF";
    }
    
}
