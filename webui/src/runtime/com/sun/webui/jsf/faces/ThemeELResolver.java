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
package com.sun.webui.jsf.faces;

import com.sun.faces.annotation.Resolver;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeJavascript;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme;
import com.sun.webui.theme.ThemeImage;

import java.beans.FeatureDescriptor;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import javax.el.ELResolver;
import javax.el.ELContext;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.faces.context.FacesContext;

/**
 * <p><code>ThemeELResolver</code> is a <code>PropertyResolver</code> 
 * implementation that passes calls to <code>getValue()</code>, 
 * <code>getType()</code>, <code>isReadOnly()</code>, and 
 * <code>setValue()</code> to the underlying {@link Theme} instance.</p>
 *
 * <p>Examples of supported expressions:</p><p>
 *
 * 1. <code>#{themeStyles.CONTENT_MARGIN}</code></p><p>
 * This expression binds to a value in the {@link Theme} corresponding to the 
 * CONTENT_MARGIN constant in ThemeStyles.
 * </p><p>
 *
 * 2. <code>#{themeImages.ALERT_ERROR_LARGE}</code></p><p>
 * This expression binds to a value in the {@link Theme} corresponding to the 
 * ALERT_ERROR_LARGE constant in ThemeImages.
 * </p><p>
 *
 * 3. <code>#{themeJavascript.DOJO}</code></p><p>
 * This expression binds to the value of the {@link Theme} value corresponding 
 * to the DOJO constant in ThemeJavascript.
 * </p><p>
 *
 * 4. <code>#{themeMessages['EditableList.invalidRemove']}</code></p><p>
 * This expression binds to a value in the {@link Theme} corresponding to the 
 * EditableList.invalidRemove key in messages.properties.
 * </p><p>
 *
 * 5. <code>#{themeTemplates.PROGRESSBAR}</code></p><p>
 * This expression binds to a value in the {@link Theme} corresponding to the 
 * PROGRESSBAR constant in ThemeTemplates.
 * </p>
 */
@Resolver
public class ThemeELResolver extends ELResolver {
    private final String THEME_IMAGES = "themeImages";
    private final String THEME_JAVASCRIPT = "themeJavascript";
    private final String THEME_MESSAGES = "themeMessages";
    private final String THEME_STYLES = "themeStyles";
    private final String THEME_TEMPLATES = "themeTemplates";

    /**
     * Default constructor.
     */
    public ThemeELResolver() {
    }

    /**
     * {@inheritDoc}
     */
    public Object getValue(ELContext context, Object base, Object property) {
        if (property == null) {
            throw new PropertyNotFoundException("Property cannot be null."); 
        }
        if(context == null) {
            throw new NullPointerException();
        }

        Object result = null;
        if (base != null) {
            // Resolve the given property associated with base object.
            if (base instanceof Images) {
                result = ((Images) base).getValue(property.toString());
                context.setPropertyResolved(true);
            } else if (base instanceof Javascript) {
                result = ((Javascript) base).getValue(property.toString());
                context.setPropertyResolved(true);
            } else if (base instanceof Messages) {
                result = ((Messages) base).getValue(property.toString());
                context.setPropertyResolved(true);
            } else if (base instanceof Styles) {
                result = ((Styles) base).getValue(property.toString());
                context.setPropertyResolved(true);
            } else if (base instanceof Templates) {
                result = ((Templates) base).getValue(property.toString());
                context.setPropertyResolved(true);
            }
        } else {
            // Variable resolution is a special case of property resolution
            // where the base is null.
            if (THEME_IMAGES.equals(property)) {
                result = new Images();
                context.setPropertyResolved(true);
            } else if (THEME_JAVASCRIPT.equals(property)) {
                result = new Javascript();
                context.setPropertyResolved(true);
            } else if (THEME_MESSAGES.equals(property)) {
                result = new Messages();
                context.setPropertyResolved(true);
            } else if (THEME_STYLES.equals(property)) {
                result = new Styles();
                context.setPropertyResolved(true);
            } else if (THEME_TEMPLATES.equals(property)) {
                result = new Templates();
                context.setPropertyResolved(true);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public void setValue(ELContext context, Object base, Object property, Object value) {
        // Variable resolution is a special case of property resolution
        // where the base is null.
        if (base != null) {
            return;
        }
        if (property == null) {
            throw new PropertyNotFoundException("Property cannot be null."); 
        }
        if (base != null) {
            // Regardless of the base object, all properties are read only.
            if (base instanceof Images
                    || base instanceof Javascript
                    || base instanceof Messages
                    || base instanceof Styles
                    || base instanceof Templates) {
                throw new PropertyNotWritableException(property.toString());
            }
        } else {
            // Variable resolution is a special case of property resolution
            // where the base is null. Thus, We need to provide an object to 
            // resolve the next property.
            if (THEME_IMAGES.equals(property)
                    || THEME_JAVASCRIPT.equals(property)
                    || THEME_MESSAGES.equals(property)
                    || THEME_STYLES.equals(property)
                    || THEME_TEMPLATES.equals(property)) {
                throw new PropertyNotWritableException(property.toString());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        if (property == null) {
            throw new PropertyNotFoundException("Property cannot be null."); 
        }
        if(context == null) {
            throw new NullPointerException();
        }
        boolean result = false;
        if (base != null) {
            // Regardless of the base object, all properties are read only.
            if (base instanceof Images
                    || base instanceof Javascript
                    || base instanceof Messages
                    || base instanceof Styles
                    || base instanceof Templates) {
                result = true;
                context.setPropertyResolved(true);
            }
        } else {
            // Variable resolution is a special case of property resolution
            // where the base is null. Thus, We need to provide an object to 
            // resolve the next property.
            if (THEME_IMAGES.equals(property)
                    || THEME_JAVASCRIPT.equals(property)
                    || THEME_MESSAGES.equals(property)
                    || THEME_STYLES.equals(property)
                    || THEME_TEMPLATES.equals(property)) {
                result = true;
                context.setPropertyResolved(true);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public Class getType(ELContext context, Object base, Object property) {
        if (property == null) {
	    return null;
	    //  What the heck?  This is not reasonable behavior.  This
	    //  resolver will be called for every EL expression, mostly those
	    //  that do not belong to Woodstock.  Further, many EL expressions
	    //  will not be JavaBean-based (i.e. Maps), and therefor may not
	    //  have "Properties" associated with their value.  Just because
	    //  this code doesn't know what to do, doesn't mean it should break
	    //  everyone else.
            //throw new PropertyNotFoundException("Property cannot be null."); 
        }
        if(context == null) {
            throw new NullPointerException();
        }
        Class result = null;
        if (base != null) {
            // Regardless of the base object, all properties are Strings.
            if (base instanceof Images
                    || base instanceof Javascript
                    || base instanceof Messages
                    || base instanceof Styles
                    || base instanceof Templates) {
                result = String.class;
                context.setPropertyResolved(true);
            }
        } else {
            // Variable resolution is a special case of property resolution
            // where the base is null. Thus, We need to provide an object to 
            // resolve the next property.
            if (THEME_IMAGES.equals(property)
                    || THEME_JAVASCRIPT.equals(property)
                    || THEME_MESSAGES.equals(property)
                    || THEME_STYLES.equals(property)
                    || THEME_TEMPLATES.equals(property)) {
                result = String.class;
                context.setPropertyResolved(true);
            }
        }
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
        // Variable resolution is a special case of property resolution
        // where the base is null. Thus, if the object is not null, we're not 
        // meant to resolve it.
        if (base != null) {
            return null;
        }
        if(context == null) {
            throw new NullPointerException();
        }
        List<FeatureDescriptor> result = new ArrayList<FeatureDescriptor>();
        result.add(getFeatureDescriptor(THEME_IMAGES, String.class));
        result.add(getFeatureDescriptor(THEME_JAVASCRIPT, String.class));
        result.add(getFeatureDescriptor(THEME_MESSAGES, String.class));
        result.add(getFeatureDescriptor(THEME_STYLES, String.class));
        result.add(getFeatureDescriptor(THEME_TEMPLATES, String.class));
        return result.iterator();
    }
    
    /**
     * {@inheritDoc}
     */
    public Class getCommonPropertyType(ELContext context, Object base) {       
        // Variable resolution is a special case of property resolution
        // where the base is null. Thus, if the object is not null, we're not 
        // meant to resolve it.
        if (base != null) {
            return null;
        }
        return String.class;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    private FeatureDescriptor getFeatureDescriptor(String name, Class clazz) {
        FeatureDescriptor desc = new FeatureDescriptor();
        desc.setName(name);
        desc.setDisplayName(name);
        desc.setValue(ELResolver.TYPE, clazz);
        desc.setValue(ELResolver.RESOLVABLE_AT_DESIGN_TIME, true);
        return desc;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private classes
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Private class to resolve ThemeImage constants.
    private class Images {
        private static final String HEIGHT_SUFFIX = "_HEIGHT";
        private static final String WIDTH_SUFFIX = "_WIDTH";
        private static final String ALT_SUFFIX = "_ALT";

        /**
         * Reslove ThemeImages constants.
         *
         * @param property The ThemeImages constant.
         */
        public Object getValue(String property) {
            if (property == null) {
                throw new PropertyNotFoundException("Property cannot be null."); 
            }

            Object result = null;
            Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());

            try {
                Field field = ThemeImages.class.getField(property);
                String value = field.get(null).toString();

                // Since there are a few diffierent types of properties, we need
                // to resolve each a bit differently.
                if (property.endsWith(ALT_SUFFIX)) {
                    // Resolve image alt strings.
                    result = theme.getMessage(theme.getImageString(value));
                } else if (property.endsWith(HEIGHT_SUFFIX)) {
                    // Resolve image height properties.
                    result = theme.getImageString(value);
                } else if (property.endsWith(WIDTH_SUFFIX)) {
                    // Resolve image width properties.
                    result = theme.getImageString(value);
                } else {
                    // Resolve image paths.
                    result = theme.getImage(value).getPath();
                }
            } catch(Exception e) {
                // Try to resolve as resource key, bypassing ThemeImages.
                result = theme.getImageString(property);
            }
            return result;
        }
    }

    // Private class to resolve ThemeJavascript constants.
    private class Javascript {
        // Special constant which does not prefix the theme path.
        private static final String JS_PREFIX = "JS_PREFIX"; // Deprecated.
        private static final String MODULE = "MODULE";
        private static final String MODULE_PATH = "MODULE_PATH";
        private static final String MODULE_PREFIX = "MODULE_PREFIX"; // Deprecated.

        /**
         * Reslove ThemeJavascript constants.
         *
         * @param property The ThemeJavascript constant.
         */
        public Object getValue(String property) {
            if (property == null) {
                throw new PropertyNotFoundException("Property cannot be null."); 
            }

            Object result = null;
            Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());

            try {
                Field field = ThemeJavascript.class.getField(property);
                String value = field.get(null).toString();

                // This is a special case where the theme path is not prefixed.
                if (JS_PREFIX.equals(property)
                        || MODULE.equals(property)
                        || MODULE_PATH.equals(property)
                        || MODULE_PREFIX.equals(property)) {
                    result = theme.getJSString(value);
                } else {
                    // Resolve Javascript file path.
                    result = theme.getPathToJSFile(value);
                }
            } catch(Exception e) {
                // Try to resolve as resource key, bypassing ThemeJavascript.
                result = theme.getJSString(property);
            }
            return result;
        }
    }

    // Private class to resolve messages.properties keys.
    private class Messages {
        /**
         * Reslove messages.properties keys.
         *
         * @param property The messages.properties key.
         */
        public Object getValue(String property) {
            if (property == null) {
                throw new PropertyNotFoundException("Property cannot be null."); 
            }

            Object result = null;
            Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());

            // Resolve resource bundle string.
            result = theme.getMessage(property);
            return result;
        }
    }

    // Private class to resolve ThemeStyles constants.
    private class Styles {
        /**
         * Reslove ThemeStyles constants.
         *
         * @param property The ThemeStyles constant.
         */
        public Object getValue(String property) {
            if (property == null) {
                throw new PropertyNotFoundException("Property cannot be null."); 
            }

            Object result = null;
            Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());

            try {
                // Resolve the style selector.
                Field field = ThemeStyles.class.getField(property);
                result = theme.getStyleClass(field.get(null).toString());
            } catch(Exception e) {
                // Try to resolve as resource key, bypassing ThemeStyles.
                result = theme.getStyleClass(property);
            }
            return result;
        }
    }

    // Private class to resolve ThemeTemplates constants.
    private class Templates {
        /**
         * Reslove ThemeTemplates constants.
         *
         * @param property The ThemeTemplates constant.
         */
        public Object getValue(String property) {
            if (property == null) {
                throw new PropertyNotFoundException("Property cannot be null."); 
            }

            Object result = null;
            Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());

            try {
                // Resolve the HTML template path.
                Field field = ThemeTemplates.class.getField(property);
                result = theme.getPathToTemplate(field.get(null).toString());
            } catch(Exception e) {
                // Try to resolve as resource key, bypassing ThemeTemplates.
                result = theme.getPathToTemplate(property);
            }
            return result;
        }
    }
}
