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

package org.example;

import com.sun.faces.annotation.Attribute;
import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

/**
 * A non-default component.
 */
@Component()
public class NonDefaultPropertyComponent {
    
    /**
     * A property that is not an attribute.
     */
    @Property(isAttribute=false)
    private String nonAttribute;

    public String getNonAttribute() {
        return this.nonAttribute;
    }

    public void setNonAttribute(String nonAttribute) {
        this.nonAttribute = nonAttribute;
    }
    
    /**
     * A property with a special name.
     */
    @Property(attribute=@Attribute(name="specialNamedAttribute"))
    private String namedAttribute;

    public String getNamedAttribute() {
        return this.namedAttribute;
    }

    public void setNamedAttribute(String namedAttribute) {
        this.namedAttribute = namedAttribute;
    }

    /**
     * A required property.
     */
    @Property(attribute=@Attribute(isRequired=true))
    private String requiredAttribute;

    public String getRequiredAttribute() {
        return this.requiredAttribute;
    }

    public void setRequiredAttribute(String requiredAttribute) {
        this.requiredAttribute = requiredAttribute;
    }

    /**
     * A read-only property.
     */
    @Property(isAttribute=false)
    private String readOnlyProperty;

    public String getReadOnlyProperty() {
        return this.readOnlyProperty;
    }

    /**
     * A write-only property.
     */
    @Property()
    private String writeOnlyProperty;

    public void setWriteOnlyProperty(String writeOnlyProperty) {
        this.writeOnlyProperty = writeOnlyProperty;
    }

    /**
     * A categorized property.
     */
    @Property(category="category")
    private String categorizedProperty;

    public String getCategorizedProperty() {
        return this.categorizedProperty;
    }

    public void setCategorizedProperty(String categorizedProperty) {
        this.categorizedProperty = categorizedProperty;
    }

    /**
     * A hidden property.
     */
    @Property(isHidden=true)
    private String hiddenProperty;

    public String getHiddenProperty() {
        return this.hiddenProperty;
    }

    public void setHiddenProperty(String hiddenProperty) {
        this.hiddenProperty = hiddenProperty;
    }

    /**
     * A property that serves as this component's default property.
     */
    @Property(isDefault=true)
    private String defaultProperty;

    public String getDefaultProperty() {
        return this.defaultProperty;
    }

    public void setDefaultProperty(String defaultProperty) {
        this.defaultProperty = defaultProperty;
    }

    @Property(name="namedProperty")
    private String namedPropertyField;

    public String getNamedProperty() {
        return this.namedPropertyField;
    }

    public void setNamedProperty(String namedProperty) {
        this.namedPropertyField = namedProperty;
    }

    @Property(name="readNamedProperty",readMethodName="getDifferentReadNamedProperty")
    private String readNamedPropertyField;

    public String getDifferentReadNamedProperty() {
        return this.readNamedPropertyField;
    }

    public void setReadNamedProperty(String readNamedProperty) {
        this.readNamedPropertyField = readNamedProperty;
    }

    @Property(name="writeNamedProperty",writeMethodName="setDifferentWriteNamedProperty")
    private String writeNamedPropertyField;

    public String getWriteNamedProperty() {
        return this.differentNamedPropertyField;
    }

    public void setDifferentWriteNamedProperty(String writeNamedProperty) {
        this.differentNamedPropertyField = writeNamedProperty;
    }

    @Property(
            name="bothNamedProperty",
            writeMethodName="setDifferentBothNamedProperty",
            readMethodName="getDifferentBothNamedProperty")
    private String differentNamedPropertyField;

    public String getDifferentBothNamedProperty() {
        return this.differentNamedPropertyField;
    }

    public void setDifferentBothNamedProperty(String value) {
        this.differentNamedPropertyField = value;
    }
    
}
