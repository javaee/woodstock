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

package com.sun.faces.mirror;

import com.sun.mirror.declaration.ClassDeclaration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a JSF renderer class declared in the current compilation unit.
 *
 * @author gjmurphy
 */
public class DeclaredRendererInfo extends DeclaredClassInfo {
    
    static String VALUE = "value";
    
    Map<String,Object> annotationValueMap;
    List<RendersInfo> renderings;
    
    DeclaredRendererInfo(Map<String,Object> annotationValueMap, ClassDeclaration decl) {
        super(decl);
        this.annotationValueMap = annotationValueMap;
        renderings = new ArrayList<RendersInfo>();
        if (this.annotationValueMap.containsKey(VALUE)) {
            for (Object value : (List) this.annotationValueMap.get(VALUE)) {
                Map nestedAnnotationValueMap = (Map) value;
                renderings.add(new RendersInfo(nestedAnnotationValueMap));
            }
        }
    }
    
    public List<RendersInfo> getRenderings() {
        return this.renderings;
    }
    
    /**
     * Represents a single rendering declared within a renderer annotation.
     */
    static public class RendersInfo {
        
        static String RENDERER_TYPE = "rendererType";
        static String COMPONENT_FAMILY = "componentFamily";
        
        Map annotationValueMap;
        
        RendersInfo(Map annotationValueMap) {
            this.annotationValueMap = annotationValueMap;
        }
        
        /**
         * The renderer type.
         */
        public String getRendererType() {
            if (this.annotationValueMap.containsKey(RENDERER_TYPE))
                return (String) this.annotationValueMap.get(RENDERER_TYPE);
            String[] componentFamilies = this.getComponentFamilies();
            if (componentFamilies.length > 0)
                return componentFamilies[0];
            return null;
        }
        
        /**
         * One or more component families to which this render type applies.
         */
        public String[] getComponentFamilies() {
            if (this.annotationValueMap.containsKey(COMPONENT_FAMILY)) {
                List componentFamilies = (List) this.annotationValueMap.get(COMPONENT_FAMILY);
                if (componentFamilies != null)
                    return (String[]) componentFamilies.toArray(new String[componentFamilies.size()]);
            }
            return new String[0];
        }
    }
    
}
