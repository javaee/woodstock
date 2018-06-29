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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A map for storing internationalized properties during file generation.
 *
 * @author gjmurphy
 */
public class PropertyBundleMap implements Map {
    
    Map<Object,Object> map = new HashMap<Object,Object>();
    List<Object> keyList = new ArrayList<Object>();
    
    PropertyBundleMap(String qualifiedName) {
        this.setQualifiedName(qualifiedName);
    }

    private String qualifiedName;

    public String getQualifiedName() {
        return this.qualifiedName;
    }
    
    void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public Object remove(Object key) {
        this.keyList.remove(key);
        return this.map.remove(key);
    }

    public Object get(Object key) {
        return this.map.get(key);
    }

    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    public Collection<Object> values() {
        return this.map.values();
    }

    public int size() {
        return this.map.size();
    }

    public Object put(Object key, Object value) {
        if (this.map.put(key, value) == null) {
            this.keyList.add(key);
            return null;
        }
        return key;
    }
    
    public void putAll(Map map) {
        this.map.putAll(map);
        this.keyList.addAll(map.keySet());
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public void clear() {
        this.map.clear();
        this.keyList.clear();
    }

    public Set<Map.Entry<Object, Object>> entrySet() {
        return this.map.entrySet();
    }

    public Set<Object> keySet() {
        return this.map.keySet();
    }
    
    public List<Object> keyList() {
        return this.keyList;
    }
    
}
