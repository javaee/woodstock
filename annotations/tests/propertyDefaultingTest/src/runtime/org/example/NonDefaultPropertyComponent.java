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
