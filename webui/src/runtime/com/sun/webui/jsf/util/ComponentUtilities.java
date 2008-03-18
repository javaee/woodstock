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

/*
 * $Id: ComponentUtilities.java,v 1.9 2008-03-18 21:24:19 rratta Exp $
 */

package com.sun.webui.jsf.util;

import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Methods for general component manipulation.
 */
public class ComponentUtilities {
    
    private final static String USCORE = "_"; //NOI18N
    
    /** Creates a new instance of ComponentUtilities. */
    public ComponentUtilities() {
    }
    
    /**
     * Store an internally created component utilizing the
     * internal facet naming convention by mapping the facet
     * to the name returned by <code>createPrivateFacetName()</code>.
     * Add the component to the parent's facets map.
     *
     * @param parent the component that created the facet
     * @param facetName the public facet name
     * @param facet the private facet component instance
     */
    public static void putPrivateFacet(UIComponent parent,
            String facetName, UIComponent facet) {
        
        if (parent == null || facet == null || facetName == null) {
            return;
        }
        parent.getFacets().put(createPrivateFacetName(facetName), facet);
    }
    
    /**
     * Remove an internally created component utilizing the
     * internal facet naming convention by mapping the facet
     * to the name returned by <code>createPrivateFacetName()</code>.
     * Remove the component from the parent's facets map.
     *
     * @param parent the component that created the facet
     * @param facetName the public facet name
     */
    public static void removePrivateFacet(UIComponent parent,
            String facetName) {
        
        if (parent == null || facetName == null) {
            return;
        }
        parent.getFacets().remove(createPrivateFacetName(facetName));
    }
    /**
     * Return a private facet from the the parent component's facet map.
     * Look for a private facet name by calling
     * <code>createPrivateFacetName()</code> on the facetName parameter.
     * <p>
     * If the matchId parameter is true, verify that the facet that is found
     * has an id that matches the value of
     * <code>getPrivateFacetId(parent.getId(), facetName)</code>.
     * If the id's do not match return null and remove the existing facet.</br>
     * If matchId is false, return the facet if found or null.
     *
     * @param parent the component that contains the facet
     * @param facetName the public facet name
     * @parem matchId verify a the id of the facet
     * @return a UIComponent if the facet is found else null.
     */
    public static UIComponent getPrivateFacet(UIComponent parent,
            String facetName, boolean matchId) {
        
        if (parent == null || facetName == null) {
            return null;
        }
        
        String pfacetName = createPrivateFacetName(facetName);
        UIComponent facet = (UIComponent)parent.getFacets().get(pfacetName);
        if (facet == null) {
            return null;
        }
        
        if (matchId == false) {
            return facet;
        }
        
        // Will never be null as long as facetName is not null.
        //
        String id = createPrivateFacetId(parent, facetName);
        if (!id.equals(facet.getId())) {
            parent.getFacets().remove(pfacetName);
            return null;
        }
        return facet;
    }
    
    /**
     * Prefix the facetName parameter with an "_".
     *
     * @param facetName the public facet name
     * @return a private facet name
     */
    public static String createPrivateFacetName(String facetName) {
        return USCORE.concat(facetName);
    }
    
    /**
     * Return an id using the convention</br>
     * <code>parent.getId() + "_" + facetName</code>
     * If <code>parent.getId()</code> is null, <code>"_" + facetName </code>
     * is returned.
     *
     * @param parent the component that contains the facet
     * @param facetName the public facet name
     * @return an id for a private facet.
     */
    public static String createPrivateFacetId(UIComponent parent,
            String facetName) {
        
        String pfacetName = createPrivateFacetName(facetName);
        String id = parent.getId();
        if (id != null) {
            pfacetName = id.concat(pfacetName);
        }
        return pfacetName;
    }
    
    /**
     * <p>Return <code>true</code> if the specified component is disabled.</p>
     *
     * @param component <code>UIComponent</code> to be checked
     */
    public static boolean isDisabled(UIComponent component) {
        Object disabled = component.getAttributes().get("disabled");
        if (disabled == null) {
            return (false);
        }
        if (disabled instanceof String) {
            return (Boolean.valueOf((String) disabled).booleanValue());
        } else {
            return (disabled.equals(Boolean.TRUE));
        }
    }
    
    /**
     * <p>Return <code>true</code> if the specified component is read only.</p>
     *
     * @param component <code>UIComponent</code> to be checked
     */
    public static boolean isReadOnly(UIComponent component) {
        Object readonly = component.getAttributes().get("readonly");
        if (readonly == null) {
            return (false);
        }
        if (readonly instanceof String) {
            return (Boolean.valueOf((String) readonly).booleanValue());
        } else {
            return (readonly.equals(Boolean.TRUE));
        }
    }
    
    /**
     * Return <code>true</code> if the Ajax request is for the given component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    public static boolean isAjaxRequest(FacesContext context,
            UIComponent component) {
        return isAjaxRequest(context, component, null);
    }
    
    /**
     * Return <code>true</code> if the Ajax request is for the given component
     * and matches the given event name.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * @param event The event name for this Ajax request (may be null).
     * @return  
     *      <code>true</code> if the Ajax request is for the given component
     *  and matches the given event name, or 
     *      <code>false</code> otherwise
     */
    public static boolean isAjaxRequest(FacesContext context,
            UIComponent component, String event) {
        if (context == null || component == null) {
            return false;
        }
        boolean isAjaxRequest = false;
        
        // Ensure this request is not for an AjaxZone.
        if (AsyncResponse.isAjaxRequest()) {
            try {
                Map map = context.getExternalContext().getRequestHeaderMap();
                JSONObject xjson = new JSONObject((String)
                    map.get(AsyncResponse.XJSON_HEADER));
                
                String id = (String) xjson.get("id");
                if (component.getClientId(context).equals(id)) {
                    if (event != null) {
                        String evt = (String) xjson.get("event");
                        if (event.equals(evt)) {
                            isAjaxRequest = true;
                        }
                    } else {
                        isAjaxRequest = true;
                    }
                }
            } catch(JSONException e) {
            } catch(NullPointerException e) {} // JSON property may be null.
        }
        return isAjaxRequest;
    }
    
    /**
     * Return <code>true</code> if the Ajax request is for the given component
     * and component's id is present in the list of components to execute.
     * This method assumes that request contains a list of ids to be executed under
     * AsyncResponse.EXECUTE_HEADER key. If such list is not found, method returns
     * false.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * @return  
     *      <code>true</code> if the Ajax request is for the given component
     * and component's id is present in the list of components to execute.
     *      <code>false</code> otherwise
     */
    public static boolean isAjaxExecuteRequest(FacesContext context,
            UIComponent component) {
        if (context == null || component == null) {
            return false;
        }
        
        // Ensure this request is not for an AjaxZone.
        if (AsyncResponse.isAjaxRequest()) {
            Map<String,String> map = context.getExternalContext().getRequestHeaderMap();
            String listToExecute = map.get(AsyncResponse.EXECUTE_HEADER);
            if (listToExecute == null || listToExecute.equals("none")) {
                return false;
            }
            
            String compId = component.getClientId(context);
            //fireAjaxTransaction syntax calls for comma-separted list of ids
            //thereby we tokenize by " " and ","
            StringTokenizer st = new StringTokenizer(listToExecute, ", ");
            while (st.hasMoreTokens()) {
                if (st.nextToken().equals(compId)) {
                    return true;
                }
            }            
        }
        return false;
    }  

    /**
     * Return a date pattern that ensures it contains "yyyy",
     * "MM", and "dd".
     * <p>
     * <code>pattern</code> is assumed to be
     * obtained from a call to <code>toPattern</code> on an instance
     * of <code>DateFormat</code> and not <code>toLocalizedPattern</code>.
     * </p>
     * <p>
     * If pattern is null, <code>SimpleDateFormat</code> using the
     * <code>DateFormat.SHORT</code> format for the current locale
     * to return the pattern.
     * </p>
     */
    public static String getDefaultDatePattern(String pattern, Locale locale) {

	if (locale == null) {
	    locale = 
		FacesContext.getCurrentInstance().getViewRoot().getLocale();
	}

	String resultPattern = null;
	if (pattern != null) {
	    resultPattern = new String(pattern);
	} else {
	    SimpleDateFormat dateFormat = (SimpleDateFormat)
		SimpleDateFormat.getDateInstance(DateFormat.SHORT, locale);
	    resultPattern = dateFormat.toPattern();
	}
	    
	// We don't want pattern to derived from "toLocalizedPattern"
	// here since the characters may not be "MM" or "dd" or "yyyy"
	//
	if (resultPattern.indexOf("yyyy") == -1) {
	    resultPattern = resultPattern.replaceFirst("yy", "yyyy");
	}
	if (resultPattern.indexOf("MM") == -1) {
	    resultPattern = resultPattern.replaceFirst("M", "MM");
	}
	if (resultPattern.indexOf("dd") == -1) {
	    resultPattern = resultPattern.replaceFirst("d", "dd");
	}
	return resultPattern;
    }
}
