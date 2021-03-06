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

package com.sun.faces.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that identifies a class as being a JSF component class. The annotated
 * type must be a public, non-abstract class. A minimal component annotation need
 * contain no element values, e.g.
 * <pre>
 *    &#64;Component
 *    public class MyComponent extends UIComponent {
 *        // ....
 *    }
 * </pre>
 * It is generally a good idea to provide, at a minimum, a suitable display name
 * and component family, e.g.
 * <pre>
 *    &#64;Component(
 *        displayName="My Component",
 *        family="org.example.Mine")
 *    public class MyComponent extends UIComponent {
 *        // ....
 *    }
 * </pre>
 * <p>The javadoc-formatted comment found with the annotated class declaration is
 * also considered part of this property's metadata. Typically, the first sentence
 * of the comment will be used as the component's default short description.
 *
 * <p>A component's properties should be annotated with the {@link Property},
 * annotation, and its events should be annotated with the {@link Event} annotation.
 *
 * @author gjmurphy
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Component {
    
    /**
     * Constraints that determine how a component may be resized in the IDE.
     */
    public enum ResizeConstraints {
        NONE,
        ANY,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        HORIZONTAL,
        VERTICAL,
        MAINTAIN_ASPECT_RATIO
    }
    
    /**
     * This component's type, which acts as a unique identifier. The value of type
     * must be unique within the scope of the current compilation unit. If no
     * value is supplied, the component class's fully qualifed name will be used.
     */
    public String type() default "";
    
    /**
     * This component's family. The value should be the same as is returned by a
     * call to <code>UIComponent.getFamily()</code>. If no value is supplied, 
     * defaults to the value of {@link #type}.
     */
    public String family() default "";
    
    /**
     * This component's display name, typically used at design-time. If no value
     * is supplied, defaults to the component's simple class name.
     */
    @Localizable
    public String displayName() default "";
    
    /**
     * The basis of variable names generated for this component, typically at
     * design-time. If no value is provided, defaults to the component's simple
     * class name, with the first letter having been translated from upper to
     * lower case.
     */
    public String instanceName() default "";
    
    /**
     * If true, this component is intended to be used only in Java code, and no
     * JSP tag should be generated for it.
     *
     * @see Property#isAttribute
     */
    public boolean isTag() default true;
    
    /**
     * The name of the JSP tag that corresponds to this component. If no value is 
     * provided, the {@link #instanceName} will be used.
     * 
     * <p><b>Note:</b> If this component does not correspond to a tag ({@link #isTag}
     * is set to false), then providing a value for this element has no effect.
     */
    public String tagName() default "";
    
    /**
     * The renderer type for this component's tag. The value specified must
     * correspond to a renderer type specified for a renderer class in the current
     * compilation unit (see {@link Renderer.Renders#rendererType}). If not specified,
     * and exactly one renderer type has been associated with this compononents
     * {@link #family}, this value will default to that renderer type. If not
     * specified, and more than one renderer type has been associated with this
     * component's {@link #family}, the default value is undefined.
     * 
     * <p><b>Note:</b> If this component does not correspond to a tag ({@link #isTag}
     * is set to false), then providing a value for this element has no effect.
     */
    public String tagRendererType() default "";
    
    /**
     * An optional short description of this component, typically used as a tool
     * tip at design-time. If no value is provided, the first sentence of the
     * javadoc-formatted comment for this component's class definition will be 
     * used.
     */
    @Localizable
    public String shortDescription() default "";
    
    /**
     * Indicates whether this component may act as a container for other components.
     */
    public boolean isContainer() default true;
    
    /**
     * Controls the manner in which a component may be resized at design-time.
     */
    public ResizeConstraints[] resizeConstraints() default {ResizeConstraints.ANY};
    
    /**
     * The name of a unique key used to associate help with this component at
     * design-time. If no key is provided, a suitable default will be generated.
     */
    public String helpKey() default "";
    
    /**
     * The name of a unique key used to associate help with this component at
     * design-time. If no key is provided, a suitable default will be generated.
     */
    public String propertiesHelpKey() default "";
    
}
