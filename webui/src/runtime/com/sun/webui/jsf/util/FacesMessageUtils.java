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

package com.sun.webui.jsf.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/*
 * Utilities for retrieving messages from FacesMessages
 * TODO: Move to a superclass for Message and MessageGroup only
 */

public class FacesMessageUtils {

    /**
     * Return a iterator that can be used to retrieve messages from
     * FacesContext.
     *
     * @param context The FacesContext of the request
     * @param forComponentId The component associated with the message(s)
     * @param msgComponent The Message, MessageGroup component
     *
     * @return an Iterator over FacesMessages that are queued.
     */
    public static Iterator getMessageIterator(FacesContext context,
    	    String forComponentId, UIComponent msgComponent) {
	
        Iterator messageIterator = null;

	// Return messages for the specified component
	if (forComponentId != null) {
	    if (forComponentId.length() == 0) {
		// Return global messages - not associated with any component.
		messageIterator = context.getMessages(null);
	    } else {
		// Get messages for the specified component only.
		UIComponent forComponent = getForComponent(context, 
							   forComponentId,
							   msgComponent);
		if (forComponent != null) {
		    String clientId = forComponent.getClientId(context);
		    messageIterator = context.getMessages(clientId);
		} else {
		    messageIterator = Collections.EMPTY_LIST.iterator();
		}
	    }
	} else {
	    // No component specified return all messages.
	    messageIterator = context.getMessages();
	}

	return messageIterator;
    }


    /**
     * Walk the component tree looking for the specified component.
     *
     * @param context The FacesContext of the request
     * @param forComponentId The component to look for
     * @param msgComponent The Message, MessageGroup component to start 
     * the search.
     *
     * @return the matching component, or null if no match is found.
     */
    private static UIComponent getForComponent(FacesContext context,
    	    String forComponentId, UIComponent msgComponent) {

        if (forComponentId == null || forComponentId.length() == 0) {
            return null;
        }

        UIComponent forComponent = null;
        UIComponent currentParent = msgComponent;
        try {
            // Check the naming container of the current 
            // component for the forComponent
            while (currentParent != null) {
                // If the current component is a NamingContainer,
                // see if it contains what we're looking for.
                forComponent = currentParent.findComponent(forComponentId);
                if (forComponent != null)
                    break;
                // if not, start checking further up in the view
                currentParent = currentParent.getParent();
            }                   
	    // Note that this following clause will never happen
	    // because JSF throws IllegalArgumentException
	    // and therefore if the component isn't found goes
	    // right to the "catch". So what was the point ?

            // no hit from above, scan for a NamingContainer
            // that contains the component we're looking for from the root.    
            if (forComponent == null) {
                forComponent =
                    findUIComponentBelow(context.getViewRoot(), forComponentId);
            }
        } catch (Throwable t) {
	    // Keep this looking like jsf
	    // ignore and log message below

        }

        if (forComponent == null) {
	    // Log a message.
	    if (LogUtil.warningEnabled(FacesMessageUtils.class)) {
		LogUtil.warning(FacesMessageUtils.class, 
			"FacesMesageUtils.componentNotFound",
			forComponentId);
	    }
        }
        return forComponent;
    }


    /**
     * Recursively searches for NamingContainers from the top of the tree
     * looking for the specified component
     *
     * @param context The FacesContext of the request
     * @param forComponentId the component to search for
     *
     * @return the matching component, or null if no match is found.
     * 
     */
    private static UIComponent findUIComponentBelow(UIComponent startComponent,
	String forComponentId) {

        UIComponent forComponent = null;
        List children = startComponent.getChildren();

        for (int i = 0, size = children.size(); i < size; i++) {
            UIComponent comp = (UIComponent) children.get(i);

            if (comp instanceof NamingContainer) {
                forComponent = comp.findComponent(forComponentId);
            }

            if (forComponent == null) {
                if (comp.getChildCount() > 0) {
                    forComponent = findUIComponentBelow(comp, forComponentId);
                }
            }

            if (forComponent != null)
                break;
        }
        return forComponent;
    }

    /**
     * Return the detail messages for <code>id</code>.
     * If <code>all</code> is <code>true</code> return all messages
     * for <code>id</code> in a single <code>String<code> separated
     * by <code>separator</code>, else just the first message.
     * If there are no messages, <code>null</code> is returned.
     * If all is <code>true</code> and separator is <code>null</code>
     * <code>" "</code> is used as the separator. If <code>id</code>
     * is <code>null</code> return <code>null</code>.
     */
    public static String getDetailMessages(FacesContext context, 
	    String id, boolean all, String separator) {

	if (id == null) {
	    return null;
	}

	Iterator messages = context.getMessages(id);
	FacesMessage fm = null;
	StringBuilder msgBuffer = new StringBuilder(200);

	// Optimization if all is false.
	// Get the first message.
	//
	if (messages.hasNext()) {
	    fm = (FacesMessage)(messages.next());
	    msgBuffer.append(fm.getDetail());
	}
	if (!all) {
	    return msgBuffer.length() != 0 ? msgBuffer.toString() : null;
	}
	if (separator == null) {
	    separator = " ";
	}
	while(messages.hasNext()) { 
	    fm = (FacesMessage)(messages.next());
	    msgBuffer.append(separator);
	    msgBuffer.append(fm.getDetail());
	}              
	return msgBuffer.length() != 0 ? msgBuffer.toString() : null;
    }
}
