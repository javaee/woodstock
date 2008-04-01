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
  * $Id: UploadRenderer.java,v 1.8 2008-04-01 17:08:27 danl Exp $
  */
package com.sun.webui.jsf.renderkit.html;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;

import com.sun.faces.annotation.Renderer;

import com.sun.webui.jsf.component.ComplexComponent;
import com.sun.webui.jsf.component.Upload;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.MessageUtil;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.UploadFilter;
import com.sun.webui.jsf.util.UploadFilterFileItem;

import com.sun.webui.theme.Theme;


/**
 * Renderer for a {@link com.sun.webui.jsf.component.Upload} component.
 * <p>
 * The <code>UploadRenderer</code> works closely with the
 * {@link com.sun.webui.jsf.util.UploadFilter} and other
 * upload filter classes.
 * </p>
 * <p>
 * There is a problem processing errors. If there
 * are serious errors where the form-data cannot be parsed from
 * the multipart request, the JSF view state is also lost
 * and only the RENDER phase executes. This leaves only
 * the encode methods to catch and report any unexpected
 * errors.
 * </p>
 * The <code>UploadFilter</code> places attributes in the requst map
 * to indicate multipart/form-data request parsing failures.
 * The following request attributes will exist if there are fatal 
 * parsing errors.
 * </p>
 * <ul>
 * <li><code>Upload.UPLOAD_ERROR_KEY</code>. When there is a request 
 * attribute with this name, the value is an <code>Exception</code> that
 * was thrown while parsing the multipart request. It typically results in the
 * form-data as well as the file data being lost. This means that the
 * JSF view state field is also lost and the request will be interpreted as
 * a first time visit to the page, i.e. only the RENDER phase will execute.
 * </li>
 * <li><code>Upload.UPLOAD_NO_DATA_KEY</code>. When there is a request
 * attribute with this name, the value is the same as the name. It indicates
 * that no form or file data was parsed from the multipart document. Typically
 * this attribute will exist only when <code>Upload.UPLOAD_ERROR_KEY</code>
 * also exists, but may, in extremely unusual circumstances
 * like a malformed request, exist by itself.
 * </li>
 * </ul>
 * </p>
 * <p>
 * If any of these errors are seen FacesMessages are queued and any
 * information available is logged.
 * </p>
 * <p>
 * If an exception occurred while processing the file uploaded in the
 * request, like the maximum file size was exceeded or space for the
 * temporary file could not be obtained, the <code>UploadFilterFileItem</code>
 * instance mapped to the component id in the request map, will have
 * the exceptions in its <code>errorLog</code>. Any exceptions 
 * found will be logged and a <code>FacesMessage</code> queued.
 * </p>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Upload"))
public class UploadRenderer extends FieldRenderer {
    
    /**
     * Retrieve the <code>UploadFilterFileItem</code> from the
     * request map that was placed there by the <code>UploadFilter</code>.
     * <p>
     * Check for fatal errors first by calling <code>checkForFatalErrors</code>
     * and set submittedValue to null if fatal errors occurred.
     * </p>
     * <p>
     * Check for file related errors in the <code>errorLog</code> of the
     * <code>UploadFilterFileItem</code> instance.
     * Create a FacesMessage for the component and log the exceptions.
     * </p>
     * <p>
     * If no errors occurred, set the submitterValue to the id
     * of the component.
     * </p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>Upload</code> component being processed
     */
    public void decode(FacesContext context, UIComponent component) {

        Upload upload = (Upload)component; 
	// We need the request map for the file items
	//
	Map fileItemMap = context.getExternalContext().getRequestMap();

	// Shouldn't this be in validate ?
	//
	if (checkForFatalErrors(context, component, fileItemMap)) {
	    upload.setSubmittedValue(null);
	    return;
	}

	// Note that we always render a span and not just when
	// a built in label is specified therefore the id we want
	// is the "labeledElementId" or the client id withe the 
	// INPUT_ID suffix.
	//
	// The value of a parameter that matches the id will be
	// an instance of UploadFilterFileItem and calling "getErrorLog"
	// returns a List of exceptions that were thrown while
	// uploading the file.
	// 
        String id = component.getClientId(context);
        if (component instanceof ComplexComponent) {
            id = ((ComplexComponent)component).getLabeledElementId(context);
        }
	UploadFilterFileItem fi = (UploadFilterFileItem)fileItemMap.get(id);

	// If there is a fileItem for this upload component
	// see if there is "preservePath" field in the request.
	//
	if (fi != null) {
	    // Get the parmeters map for the hidden preservePath field
	    // 
	    Map paramMap = 
		context.getExternalContext().getRequestParameterMap();

	    String pid = id.concat(Upload.PRESERVE_PATH_ID);
	    String preservePath = (String)paramMap.get(pid);
	    if (preservePath != null) {
		fi.setClientFilePath(preservePath);
	    }
	}
	// Why are we actually checking for errors here.
	// This should be part of validate.
	// I think the submitted value should be the 
	// UploadFilterFileItem and Upload should implement the 
	// validate methods of UIInput.
	//
	upload.setSubmittedValue(null);
	if (fi != null && !checkForFileErrors(context, component, fi)) {
	    upload.setSubmittedValue(id);
	}
        return;
    }

    /**
     * Render an <code>input</code> element of type <code>file</code>.
     * The component's client id will have been
     * set on a <code>span</code> element that contains both an 
     * optionally rendered label and the input field. The input element will
     * have the component's client id appended with
     * <code>Upload.INPUT_ID</code>.
     */
    public void encodeEnd(FacesContext context, UIComponent component)
	    throws IOException {
          
        if(!(component instanceof Upload)) { 
            Object[] params = { component.toString(), 
                                this.getClass().getName(), 
                                Upload.class.getName() };
            String message = MessageUtil.getMessage
                ("com.sun.webui.jsf.resources.LogMessages",
                 "Upload.renderer", params);
            throw new FacesException(message);  
        }
	Upload upload = (Upload)component;

        Map map = context.getExternalContext().getRequestMap(); 
	// Check for fatal errors in case all fields were lost
	// this is the only place to catch those errors.
	// If the errors were caught earlier, then they will
	// be removed from the request map.
	//
	checkForFatalErrors(context, upload, map);
        super.renderField(context, upload, "file", getStyles(context));
        
	// Note that all these javascript functions work based on the
	// clientid, not the labeled element id. This means that the
	// suffix is known in the javascript. This is not good since it
	// is implementation and component version dependent.
	//
        String id = upload.getClientId(context);

	// First init the javascript with the id suffix.
	// This is a little bogus doing this here since is
	// assumes what the ComplexComponent getLabeledElementId does.
	// But we need this, or else the information needs to be 
	// hardcoded in the javascript. This is a "better" alternative.
	//
	StringBuilder jsString = new StringBuilder(128);
        jsString.append(JavaScriptUtilities.getModule("_html.upload"))
            .append("\n")
            .append(JavaScriptUtilities.getModuleName("_html.upload._setEncodingType"))
	    .append("('")
	    .append(id)
	    .append("');\n");

	// If preserving the path, call the javscript function to
	// set up the client to preserve the file path.
	// It will create a hidden field and register a listener for 
	// changes to the file input field.
	//
	if (upload.isPreservePath()) {
	    jsString.append(JavaScriptUtilities.getModuleName("_html.upload._preservePath"))
		.append("('")
		.append(id)
		.append("','")
		.append(id.concat(Upload.INPUT_ID).concat(Upload.PRESERVE_PATH_ID))
		.append("');\n");
	}
        JavaScriptUtilities.renderJavaScript(upload, 
	    context.getResponseWriter(), jsString.toString(), 
            JavaScriptUtilities.isParseOnLoad());
    }

    /**
     * Return true if either Upload.UPLOAD_ERROR_KEY or
     * Upload.UPLOAD_NO_DATA_KEY were found in <code>map</code> else false
     * if not found. Queue a FacesMessages and log the message
     * and exception from Upload.UPLOAD_ERROR_KEY or just the message.
     * Remove the attributes from the map, so that they are not
     * reported twice during encoding, if found during decode.
     */
    private boolean checkForFatalErrors(FacesContext context, 
	    UIComponent component, Map map) {

	// First see if unexpected exceptions were thrown.
	// Its value should be an Exception.
	//
	String message = null;
	Throwable cause = null;
        Throwable error =  (Throwable)map.get(Upload.UPLOAD_ERROR_KEY);
        if (error != null) {
	    cause = error.getCause();
	    if (cause != null) {
		message = cause.getMessage();
	    } else {
		// For logging purposes in case cause is null
		//
		cause = error;
	    }
	    if (message == null) {
		message = error.getMessage();
	    }
	    if (message == null) {
		message = ThemeUtilities.getTheme(context).getMessage(
		    "Upload.fatalError.parseException");
	    }
	} else {
	    // The value of this is key is the key and is just used
	    // to indicate there has been an error, there was no
	    // more information to convey about the error.
	    // FileItems list was null but didn't throw exception.
	    //
	    String errorKey =  (String)map.get(Upload.UPLOAD_NO_DATA_KEY);
	    if (errorKey != null) {
		message = ThemeUtilities.getTheme(context).getMessage(
			"Upload.fatalError.noData");
	    } else {
		// No errors;
		return false;
	    }
	}

	FacesMessage fmsg = new FacesMessage(message);
	context.addMessage(component.getClientId(context), fmsg);
		    
	if (LogUtil.warningEnabled(Upload.class)) {
	    if (cause != null) {
		// log the cause, if it isn't null, otherwise
		// t has been set to the original exception
		//
		LogUtil.warning(Upload.class, message, cause);
	    } else {
		LogUtil.warning(Upload.class, message);
	    }
        }

	// Remove the errors so that encode will not see them
	// if we're in an earlier phase.
	//
	map.remove(Upload.UPLOAD_ERROR_KEY);
	map.remove(Upload.UPLOAD_NO_DATA_KEY);

	return true;
    }

    /**
     * Return <code>true</code> if the list returned by 
     * <code>ufi.getErrorLog</code> was not empty else <code>false</code>.
     * If errors were found, queue a <code>FacesMessage</code> and log
     * the exceptions.
     */
    private boolean checkForFileErrors(FacesContext context,
		UIComponent component, UploadFilterFileItem ufi) {

	List errorLog = ufi.getErrorLog();
	if (errorLog == null || errorLog.isEmpty()) {
	    return false;
	}

	String msg = null;

	// There should be only one.
	// But log and queue a faces message for each one.
	//
	Iterator iterator = errorLog.iterator();
	while (iterator.hasNext()) {
	    // Check for well know exceptions
	    //
	    Exception e = (Exception)iterator.next();
	    if (e instanceof SizeLimitExceededException) {
		msg = ThemeUtilities.getTheme(context).getMessage(
		    "Upload.error", 
		    new Object[] {Long.valueOf(e.getMessage())});
	    } else {
		// Some other IOException. 
		//
		msg = e.getMessage();
	    }
	    if (msg == null) {
		msg = ThemeUtilities.getTheme(context).getMessage(
			"Upload.fileError.IOException");
	    }
	    FacesMessage fmsg = new FacesMessage(msg);
	    context.addMessage(component.getClientId(context), fmsg);

	    if (LogUtil.fineEnabled(Upload.class)) {
		LogUtil.fine(Upload.class, 
		    msg == null ? e.getClass().getName() : msg, e);
	    }
	}

	return true;
    }
}
