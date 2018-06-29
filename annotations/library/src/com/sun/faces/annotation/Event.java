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
 * Annotation that identifies a component event. An event is a logical entity
 * that consists typically of:
 * <ul>
 * <li>an event class or interface
 * <li>an event listener class or interface, with methods that are invoked when
 * an event occurs, and passed an instance of the event class or interface
 * <li>a component "add" method for adding event listeners
 * <li>a component "remove" method for removing event listeners
 * <li>optionally, a component "get" method for obtaining an array of all
 * registered event listeners.
 * </ul>
 * An event annotation is placed on either of the required listener add or remove
 * methods. It is preferable to annotate the "add" method.
 *
 * <p>The annotation for a given event should occur only once per component.
 * An event is uniquely identified by its {@link #name}.
 *
 * <p>An event may be annotated in a component class, a non-component class, or
 * an interface. It is generally a good idea to define common events once, in
 * an abstract base class or interface. Inherited event metadata may be overridden,
 * by providing an annotation on the overriding add or remove method. Annotation
 * element values from the super class will be inherited unless overridden by
 * annotation element values in the child class.
 * 
 * <p>If names for the listener add and remove methods are not supplied, they may
 * be generated following the standard naming conventions. For example, for an
 * event "blam", if the annotated method has a single parameter, and the method name
 * begins with the prefix "add", and the remaining portion of the name equals the
 * unqualified class name of the singleton parameter, then this method is assumed
 * to be an "add" method for an event listener, and the singleton parameter type
 * is assumed to be the event listener class. The same logic is used to imply the
 * name of the "remove" method.
 *
 * <p>The name of an event may also be defaulted, if, in addition to the naming
 * conventions for listener add and remove methods, the event listener class name
 * ends with the suffix "Listener". Hence for an event listener class named
 * {@code BlamListener}, the default event name would be "blam".
 *
 * <p>If all standard naming conventions are followed, a minimal event annotation for
 * the "blam" event would look like:
 *
 * <pre>
 *    &#64;Event
 *    public void addBlamListener(BlamListener blamListener) {...}
 *    public void removeBlamListener(BlamListener blamListener) {...}
 * </pre>
 * 
 * <p>If the event is to be mapped to a property on the component, then the listener 
 * class must define extactly one handler method, e.g.:
 *
 * <pre>
 *    public interface BlamListener {
 *        public void blamHappened(BlamEvent blamEvent);
 *    }
 * </pre>
 *
 * <p>Note that since add and remove listener methods have the same signature, if
 * default naming conventions are not followed, it is necessary to provide both
 * method names explicitly, e.g.
 *
 * <pre>
 *    &#64;Event(
 *        name="blam",
 *        addListenerMethodName="insertBlamListener",
 *        removeListenerMethodName="deleteBlamListener"
 *    )
 *    public void insertBlamListener(KerBlamListener blamListener) {...}
 *    public void deleteBlamListener(KerBlamListener blamListener) {...}
 * </pre>
 *
 * @author gjmurphy
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Event {
    
    /**
     * This event's unique name. The name must be unique within the scope of
     * the event's class.
     */
    public String name() default "";
    
    /**
     * The display name, typically used at design-time. If no value is provided, the
     * {@link #name} will be used.
     */
    @Localizable
    public String displayName() default "";

    /**
     * An optional short description of this event, typically used as a tool
     * tip at design-time.
     */
    @Localizable
    public String shortDescription() default "";

    /**
     * The name of the "add" method for this event. A value need be specified
     * only if the annotation is placed elsewhere than the "add" method.
     */
    public String addListenerMethodName() default "";

    /**
     * The name of the "remove" method for this event. A value need be specified
     * only if the annotation is placed elsewhere than the "remove" method, or if
     * the name of the method does not follow standard naming conventions.
     */
    public String removeListenerMethodName() default "";

    /**
     * The name of the "get" method for this event. A value need be specified
     * only if the annotation is placed elsewhere than the "remove" method, or if
     * the name of the method does not follow standard naming conventions.
     */
    public String getListenersMethodName() default "";
    
    /**
     * Indicates whether this event is the default event for its component.
     * Only one event may be declared to be the default event for a component.
     * If a component does not declare a default event and does not inherit a
     * default event, and implements {@link javax.faces.component.ActionSource},
     * the {@code action} event will be made the default, if it exists; if it
     * implements {@link javax.faces.component.EditableValueHolder}, the 
     * {@code valueChange} event will be made the default, if it exists.
     */
    public boolean isDefault() default false;
                    
}
