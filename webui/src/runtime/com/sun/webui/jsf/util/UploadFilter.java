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
 * $Id: UploadFilter.java,v 1.4 2007-08-14 23:00:40 mattbohm Exp $
 */

package com.sun.webui.jsf.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper; 

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;

import com.sun.webui.jsf.component.Upload;
import com.sun.webui.jsf.util.MessageUtil;
import com.sun.webui.jsf.util.LogUtil;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 * Use the UploadFilter if your application contains an Upload component 
 * (&lt;ui:upload&gt; tag).
 * </p> 
 * <p>
 * Configure the filter by declaring the filter element in the web application's
 * deployment descriptor.
 * </p> 
 *
 * <pre> 
 *  &lt;filter&gt;
 *    &lt;filter-name&gt;UploadFilter&lt;/filter-name&gt;
 *    &lt;filter-class&gt;com.sun.web.ui.util.UploadFilter&lt;/filter-class&gt;
 *  &lt;/filter&gt;
 * </pre>
 * <p>
 * Map the filter to the FacesServlet, for example</p>
 * <pre>
 *  &lt;filter-mapping&gt;
 *   &lt;filter-name&gt;UploadFilter&lt;/filter-name&gt;
 *   &lt;servlet-name&gt;FacesServlet&lt;/servlet-name&gt;
 * &lt;/filter-mapping&gt;
 * </pre> 
 * <p>
 * The UploadFilter uses the Apache commons fileupload package. You can 
 * configure the parameters of the <code>UploadFilterDiskFileUpload</code>
 * class (a subclass of DiskFileUpload) by specifying init 
 * parameters to the Filter. The following parameters are available: 
 * <ul> 
 * <li>
 * <code>maxSize</code> The maximum allowed upload size in bytes. 
 * If negative, there is no maximum. The default value is 1,000,000.
 * <em>Consider carefully if allowing any size files to be uploaded.
 * This can have serious consequences and consume all of a server's 
 * resources. Also make sure it is large enough for the content of the JSF 
 * view state field when the JSF application is configured for
 * client side state saving mode.</em>
 * </li> 
 * <li>
 * <code>sizeThreshold</code>The implementation of the uploading 
 * functionality uses temporary storage of the file contents before the 
 * Upload component stores them per its configuration. In the temporary 
 * storage, smaller files are stored in memory while larger files are 
 * written directly to disk . Use this parameter 
 * to specify an integer value of the cut-off where files should be 
 * written to disk. The default value is 4096 bytes.
 * </li> 
 * <li>
 * <code>tmpDir</code> Use this directory to specify the directory to 
 * be used for temporary storage of files. The default behaviour is to use
 * the directory specified in the system property "java.io.tmpdir".
 * </li> 
 * </ul> 
 * <p>
 * If an unexpected exception occurs due to a malformed request, for 
 * example, <code>UploadFilter.UPLOAD_ERROR_KEY</code> is added as a request
 * attribute with the exception as the value, and is available to the
 * rest of the request chain. If there were no unexpected exceptions 
 * but no form data was obtained, <code>Upload.UPLOAD_NO_DATA_KEY</code>
 * is added as a request attribute, with 
 * <code>Upload.UPLOAD_NO_DATA_KEY</code>
 * as the value. If <code>Upload.UPLOAD_ERROR_KEY</code> is set
 * <code>Upload.UPLOAD_NO_DATA_KEY</code> is also set but adds no
 * additional information and can be ignored, it is always set when there is an 
 * unexpected parsing exception.
 * </p>
 * <p>
 * <code>UploadFilter</code> places all <code>FileItem</code>'s returned
 * by <code>DiskFileUpload.parseRequest()</code> that are not field
 * forms in the request map mapped to their <code>getFieldName</code>
 * value. This includes <code>Upload</code> component fields as well as
 * any standard <code>input</code> fields of type "file".
 * </p>
 * <p>
 * In order to integrate with the a console application, the
 * <code>UploadFilter</code> recognizes the following
 * <code>ServletContext</code> attribute
 * <code>com.sun.webui.v4.uploadfilter.maxfilesize</code>. If the value
 * of this attribute is an instance of a <code>Long</code> its value
 * takes precedence over the <code>UploadFilter</code>'s default values and the
 * value of a filter's <code>maxSize</code> init-param value.
 * If the value is <code>-1</code> then there is no restriction of upload file
 * size.
 * </p>
 */
public class UploadFilter implements Filter {
    
    /**
     * The name of the filter init parameter used to specify the maximum 
     * allowable file upload size.
     */
    public static final String MAX_SIZE = "maxSize";
    /**
     * The name of the filter init parameter used to specify the byte 
     * size above which temporary storage of files is on disk.
     */
    public static final String SIZE_THRESHOLD = "sizeThreshold";
    /**
     * The name of the filter init parameter used to specify
     * the directory to be used for temporary storage of 
     * uploaded files.
     */
    public static final String TMP_DIR = "tmpDir";

    /**
     * The default maximum file sise 1MB.
     */
    private long maxSize = 1000000;
    /**
     * The default in memory threshold 4K.
     */
    private int sizeThreshold = 4096; 
    /**
     * The default directory for temporary files "java.io.tmpdir".
     */
    private String tmpDir = System.getProperty("java.io.tmpdir");
    /**
     * Localized UploadFilter messages.
     */
    private String messages = "com.sun.web.ui.resources.LogMessages";
    
    // This is the ServletContext attribute that will be set by the
    // console in order to maintain backward compatibility.
    // Recent changes to UploadFilter will actually respect a
    // web.xml maxSize value and not allow any size uploads by default.
    // However Console braveheart apps that share webui.jar will
    // now not have unlimited upload sizes. 
    //
    // The console will set this attribute in the ServletContext
    // to "-1" so that the upload will continue to behave as it 
    // did originally and sys admin's can change that value to 
    // something reasonable if concerned about the security risk..
    //
    // See src/bundled/app/webmgt/lib/services/com/sun/management/services/
    // common/WebConstants.java
    // and
    // session/CoreSessionManagerFilter.java
    // in the Lockhart console sources.
    //
    /**
     * <code>ServletContext</code> attribute that defines the maximum
     * upload file size.
     */
    private final static String FILEUPLOAD_V4_MAXSIZE_PROP =
	"com.sun.webui.v4.uploadfilter.maxfilesize";

    /**
     * Local storage for the <code>FilterConfig</code> instance.
     */
    private FilterConfig filterConfig;

    
    /**
     * If the incoming request has multipart content, create a
     * <code>UploadFilterDiskFileUpload</code> to parse the request, else
     * pass the request unchanged to the next filter in the chain.
     * <p>
     * <code>UploadFilterDiskFileUpload</code> reimplements some of the
     * <code>commons-fileupload</code> support classes in order to ensure
     * that the form-data is extracted in the case of unexpected failures
     * due to problems with input/output of the file content. It also
     * ensures that the <code>maxSize</code> specification is enforced
     * on the file size and not the request size. It does this by passing
     * <code>-1</code> to the underlying <code>FileUploadBase</code> instance
     * and in the upload filter reimplemented classes enforces the size
     * constraint. If the size constraint is exceeded processing continues
     * but resources are not consumed, in order to ensure that any form-data
     * is processed so that it can be passed to the rest of the request
     * chain.it does, the filter processes the request for form
     * components.
     * <p>
     * Exceptions that occur can be obtained from the specialized
     * <code>UpateFilterFileItem</code> instances stored in the request.
     * These instances can be obtained in the decode method in order to
     * continue in the JSF lifecycle as appropriate.
     * </p>
     * <p>
     * The form data that might exist in the multipart request is collected
     * into a <code>Hashtable</code> and passed onto the rest of the chain
     * in a wrapped request, as the request parameters.
     * </p>
     * <p>
     * <em>Note that if an unexpected exception occurs while parsing the
     * multipart data, all request form data is lost and not passed onto
     * the rest of the chain. This means that the JSF view state is also
     * lost and the FacesServlet will see this request as a first request
     * for this page. The unexpected exception will be seen by the Upload
     * renderer and a FacesMessage queued and logged, but for this error
     * to be seen by the application, a component must be rendered after
     * the Upload component to react to the queued message.</em>
     * </p> 
     *
     * @param response The servlet response
     * @param request The servlet request we are processing
     * @param chain The filter chain we are processing
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)request;
        
	if (!FileUpload.isMultipartContent(req)) {
	    chain.doFilter(request, response);
	    return;
	}

	// files with names in other languages (like Japanese) are not
	// being uploaded with the proper names. Proper encoding has to 
	// be set for this to happen.
	String encoding = request.getCharacterEncoding();
	if (encoding == null) {
	    encoding = "UTF-8";
	}

	// Check the ServletContext for the Console's maxSize value.
	//
	if (filterConfig != null) {
	    ServletContext sc = filterConfig.getServletContext();
	    if (sc != null) {
		Object consoleMaxSize = 
			sc.getAttribute(FILEUPLOAD_V4_MAXSIZE_PROP);
		if (consoleMaxSize != null && consoleMaxSize instanceof Long) {
		    if (((Long)consoleMaxSize).longValue() < (long)0) {
			maxSize = -1;
		    } else {
			maxSize = ((Long)consoleMaxSize).longValue();
		    }
		}
	    }
	}

	// We have an upload request
	//

	// Originally the size threshold was set to -1 for
	// unlimited file sizes, in order to better catch 
	// failures. But this allowed excessively large files
	// to be uploaded, which is not safe.
	// Also the maxsize was related to the total request content
	// size and not the size of individual files.
	//
	// However doing so means that fileItems can be returned
	// as null in case of IO exceptions. In this case no parameters
	// are passed to chain.doFilter, which means that the upload 
	// component does not get decoded, in fact nothing gets decoded
	// since the JSF VIEW_STATE is lost.
	//
	// This means that any errors must be caught in the 
	// upload renderer, but this is too late most of the time
	// since message and alerts normally preceed the upload 
	// component, which means they will never see a FacesMessage
	// that is queued by the upload renderer.
	//
	// UploadFilterDiskFileUpload implements constraining
	// each file upload to the maxSize value and always
	// processes and returns the form data if files exceed maxSize.
	// It also catches exceptions and logs them to the "errorLog"
	// of UpdateFilterFileItem.
	//
	UploadFilterDiskFileUpload fu = new UploadFilterDiskFileUpload(
	    sizeThreshold, maxSize, tmpDir, encoding);

	// The only exceptions we should see here are from
	// the reading of the multipart data not being formed
	// correctly. All other exceptions should be caught
	// and available in the errorLog of the UpdateFilterFileItem.
	//
	// If this happens the rest of the form-data will be lost.
	// including the JSF view state which means that this request
	// will appear like the first visit to this page and nothing
	// will be decoded. This error will be seen in the upload
	// renderer but unless there is amessage component rendered
	// after the upload component, looking for the faces messages
	// from the upload component, it will only be logged.
	//
	List fileItems = null;
	try {
	    fileItems = fu.parseRequest(req);
	} catch(Exception fue) {
	    request.setAttribute(Upload.UPLOAD_ERROR_KEY, fue); 
	}

	// process the FileItems List to get the form-data
	// for the wrapped request.
	//
	// There's not much to do if a severe error happens
	// and fileItems comes back null. The request parameters
	// are more or less lost unless we implement parsing
	// a multipart/form-data request ourselves.
	//
	Hashtable parameters = null;
	if (fileItems != null && !fileItems.isEmpty()) {
	    parameters = getFormDataAsRequestParameters(fileItems, request);
	} else {
	    // Tell the renderer that we got no data.
	    // This should be ignored if Upload.UPLOAD_ERROR_KEY
	    // was also seen.
	    //
	    request.setAttribute(Upload.UPLOAD_NO_DATA_KEY, 
		Upload.UPLOAD_NO_DATA_KEY); 
	    parameters = new Hashtable();
	}

	UploadRequest wrappedRequest = new UploadRequest(req, parameters);

	chain.doFilter(wrappedRequest, response);

	Enumeration e = request.getAttributeNames(); 
	while (e.hasMoreElements()) { 
	    String name = (String)e.nextElement();
	    Object o = request.getAttribute(name);
	    if (o instanceof FileItem) { 
		((FileItem)o).delete();
	    }
	    request.removeAttribute(name);
	}
    }

    /**
     * Initializes the Upload filter by reading any init parameters.
     * If an init parameter is invalid, or an exception occurs due to
     * an invalid init parameter the default value for that parameter 
     * will be used and messages and exceptions will be logged 
     * as <code>java.util.logging.Level.WARNING</code>.
     *
     * @param filterConfig the filter configuration
     */
    public void init(FilterConfig filterConfig) {

	// save off the filterConfig instance for later use
	// in order to get the ServletContext and check for the
	// Console's maxsize attribute when an upload occurs.
	//
	this.filterConfig = filterConfig;

        String param = filterConfig.getInitParameter(MAX_SIZE); 
        if (param != null) { 
            try { 
                maxSize = Long.parseLong(param); 
            } catch(NumberFormatException nfe) { 
                Object[] params = { MAX_SIZE, param, Long.valueOf(maxSize) }; 
		if (LogUtil.warningEnabled()) {
		    LogUtil.warning(UploadFilter.class, 
			"Upload.filter.invalidLong", params);
		}
            }
        } 
        param = filterConfig.getInitParameter(SIZE_THRESHOLD);
        if (param != null) {       
            try { 
                 sizeThreshold = Integer.parseInt(param); 
            } catch(NumberFormatException nfe) { 
                Object[] params = { SIZE_THRESHOLD, param,
		    Integer.valueOf(sizeThreshold)}; 
		if (LogUtil.warningEnabled()) {
		    LogUtil.warning(UploadFilter.class, 
			"Upload.filter.invalidInt", params);
		}
            }
        }
        param = filterConfig.getInitParameter(TMP_DIR);
        if (param != null) { 
	    try {
		String msgKey = null;
		File dir = new File(param); 
		if (!dir.exists() || !dir.isDirectory()) {
		    msgKey = "Upload.filter.doesNotExist";
		} else 
		if (!dir.canWrite() || !dir.canRead()) {
		    msgKey = "Upload.filter.cantReadWrite";
		} else {
		    tmpDir = param;
		}
		if (msgKey != null) {
		    Object[] params = { TMP_DIR, param, tmpDir }; 
		    if (LogUtil.warningEnabled()) {
			LogUtil.warning(UploadFilter.class, msgKey, params);
		    }
		}
	    } catch(Exception e) {
		Object[] params = { TMP_DIR, param, 
			e.getMessage() != null ? e.getMessage() : "",
			tmpDir }; 
		String msg = MessageUtil.getMessage(messages, 
		    "Upload.filter.invalidDir", params);
		if (LogUtil.warningEnabled()) {
		    LogUtil.warning(UploadFilter.class, msg, e);
		}
            }
        } 
    }
    
    /**
     * Return a String representation of the UploadFilter
     * @return A String representation of the UploadFilter
     */
    public String toString() {
        
        return (this.getClass().getName());      
    }

    /** Invoked when the Filter is destroyed */ 
    public void destroy() {
        // do nothing
    }
    
    /**
     * This request wrapper class extends the support class
     * HttpServletRequestWrapper,which implements all the methods
     * in the HttpServletRequest interface, as delegations to the
     * wrapped request.
     * You only need to override the methods that you need to change.
     * You can get access to the wrapped request using the method getRequest()
     */
    class UploadRequest extends HttpServletRequestWrapper {
        
	/**
	 * Set to true when the original request objects parameters
	 * (query params) are merged with the multipart/form-data 
	 * parameters
	 */
	private boolean merged = false;
	/**
	 * This hashtable will have all the parameters once
	 * if "merged" is true;
	 */
        private Hashtable parameters;
	/**
	 * A "Collections.unmodifiableMap", created once
	 * "getParametersMap" is called.
	 */
	private Map unmodifiableParametersMap;
        
        public UploadRequest(HttpServletRequest request, Hashtable parameters) {
            super(request);
            this.parameters = parameters;
        }
        
        public String getParameter(String name) {

            Object param = parameters.get(name);

	    // Delegate to the wrapped original request.
	    // The only parameters in that request map
	    // should be the query parameters.
	    //
	    if (param == null) {
		return merged ? null : super.getParameter(name);
	    }
            
            if (param instanceof String) {
                return (String)param;
            }
            if (param instanceof String[]) {
                String[] params = (String[])param;
		// Just in case there is an empty array.
		//
                return params.length == 0 ? null : params[0];
            }
	    // Just in case a non string got into the map.
	    // but shouldn't happen.
	    //
            return param.toString();
        }
        
	// From the servlet spec
	// getParameterValues(String) public java.lang.String[]
	//	getParameterValues(java.lang.String name)
	// Returns an array of String objects containing all of the values
	// the given request parameter has, or null if the parameter does
	// not exist. If the parameter has a single value, the array
	// has a length of 1.
	/**
	 * Returns an array of String objects containing all of the
	 * values the given request parameter has, 
	 * or null if the parameter does not exist. If the
	 * parameter has a single value, the array has a length of 1.
	 */
        public String[] getParameterValues(String name) {            

	    Object value = parameters.get(name);

	    // Delegate to the wrapped original request.
	    // The only parameters in that request map
	    // should be the query parameters.
	    //
	    if (value == null) {
		return merged ? null : super.getParameterValues(name);
	    }

	    // All values are stored as String[]
	    // to satisfy getParameterMap interface.
	    //
	    return (String[])value;
        }
        
	/**
	 * Return all the parameter names. This will include all the
	 * names in the original request object's parameter map.
	 * Those parameters should only be from
	 * the query string.
	 */
        public Enumeration getParameterNames() {
	    // Get all the names from the original request as well.
	    //
	    mergeParameters();
	    return parameters.keys();
        }
        
	/**
	 * Return a map containing all parameters including those
	 * in the original request object's parameter map.
	 * Those parameters should only be from
	 * the query string.
	 */
        public Map getParameterMap() {
	    mergeParameters();
	    return unmodifiableParametersMap;
        }

	/**
	 * Return a Hashtable including any parameters in the
	 * original request.
	 */
	private void mergeParameters() {
	    // We only need to do this once.
	    //
	    if (merged) {
		return;
	    }
	    // Merge the original request's parameters 
	    // Note that if a form-data parameter matches a
	    // query string parameter, the form data parameter will
	    // take precedence. It's not clear which should actually
	    // take precedence, or if query parameters should be respected
	    // in a "POST".
	    //
	    Hashtable mergedmap = new Hashtable(super.getParameterMap());
	    mergedmap.putAll(parameters);
	    parameters = mergedmap;

	    // Now create the unmodifiable map.
	    //
	    unmodifiableParametersMap =
		Collections.unmodifiableMap(parameters);

	    merged = true;
	}
    }

    /**
     * We need to collect the request parameters from the multipart/form-data
     * request that are not files defined by the "input" elements with
     * type "file".
     * <p>
     * There may be non file fields and  as well as "input" elements
     * of type "file" that were not rendered by an <code>Upload</code>
     * component. <code>UploadFilter</code> places all
     * <code>FileItem</code>'s returned
     * by <code>DiskFileUpload.parseRequest()</code> that are not form-data
     * fields in the request map mapped to their <code>getFieldName</code>
     * value. This includes <code>Upload</code> component fields as well as
     * any standard <code>input</code> fields of type "file".
     * Upload input fields are passed to the application as
     * request attributes whose values are <code>UploadFilterFileItem</code>
     * instances.
     * </p>
     * <p>
     * Non file or form-data fields are collected and transferred into
     * a parameters map and passed on to a "wrapped" request
     * and then onto the filter chain. Special care must be taken for
     * form-data fields that have multiple values. Each value is
     * represented by a form-data field with the same "fieldName"
     * but a different value. All these values are collected and
     * provided to the "wrapped" request parameters map.
     * </p>
     * <p>
     * Return <code>true</code> if any form-data element has multiple values.
     * </p>
     */
    private boolean getFormData(FileItem fileItem, 
	    Hashtable formDataMap, ServletRequest request) throws UnsupportedEncodingException {
        
	boolean haveMultipleValues = false;
	String fieldName = fileItem.getFieldName();

	// If it is not an upload input field, collect the
	// form-data fields and values in formDataMap.
	// 
	// We need to place all !fileItem.isFormField() items into the
	// the request, otherwise we might lose some that
	// are not braveheart components. The UploadRender just needs
	// to look for either the component id, (no label) or the
	// id.concat(INPUT_ID) when it is labeled in decode.
	// No need for a hidden field.
	//
	if (!fileItem.isFormField()) {
	    request.setAttribute(fieldName, fileItem);
	} else {
	    // We may Need to store the value as an ArrayList because some
	    // fieldName's have multiple values, for example, the selected
	    // values for the add remove component (see CR6504432)
	    //
	    // See if we have created a value for this fieldName
	    // already. If not just save the value as is.
	    // If we already saved a value for this fieldName
	    // then create an ArrayList and add the existing value and
	    // the new value. This keeps us from creating ArrayLists
	    // unnecessarily.
	    //
            String encoding = request.getCharacterEncoding();
            if (encoding == null) {
                encoding = "UTF-8";
            }
	    String svalue = fileItem.getString(encoding);
	    Object value = formDataMap.get(fieldName);
	    if (value == null) {
		// Since getParameterMap must return a Map containing
		// String arrays, even if there is only one element
		// in the array save the value as String[]
		//
		formDataMap.put(fieldName, new String[]{svalue});
	    } else 
	    if (value instanceof ArrayList) {
		((ArrayList)value).add(svalue);
	    } else {
		// We have multiple value form data
		//
		haveMultipleValues = true;

		// This is a multi valued field, create an array list
		// and add the two values, overwrite the original single
		// value field reference in formDataMap.
		//
		ArrayList valueList = new ArrayList();
		// Value should be a String[] 
		//
		valueList.add(((String[])value)[0]);
		valueList.add(svalue);
		formDataMap.put(fieldName, valueList);
	    }
	}
	return haveMultipleValues;
    }


    /**
     * Assemble the form data into a Hashtable.
     */
    Hashtable getFormDataAsRequestParameters(List fileItems,
	    ServletRequest request) throws UnsupportedEncodingException {

	// A flag set in getFormData if we encounter fields with multiple
	// values. This is an optimization. If there are no multiple values
	// the form data set does not have to iterated over again and the
	// Hashtable constructed by <code>getFormData</code> can be 
	// passed directly onto the chain.
	//
	boolean haveMultipleValues = false;

	Hashtable formDataMap = new Hashtable();

	Iterator iterator = fileItems.iterator();
	while (iterator.hasNext()) {
	    if (getFormData((FileItem)iterator.next(), formDataMap, request)) {
		haveMultipleValues = true;
	    }
	}

	// If we don't have multiple values, there is no
	// need to iterate over the list again.
	//
	if (!haveMultipleValues) {
	    return formDataMap;
	}
          
	Set keys = formDataMap.entrySet();
	Hashtable requestParameters = new Hashtable();

	iterator = keys.iterator();

        // Create a hashtable to use for the parameters, and add the remaining
        // fileItems
	//
        while (iterator.hasNext()) {
	    Map.Entry me = (Map.Entry)iterator.next();
	    String key = (String)me.getKey();
	    Object value = me.getValue();
            
	    // If its an ArrayList it should have more than one value
	    // All other values should be String[]
	    // to satisfy "getParameterMap" interface.
	    //
	    if (value instanceof ArrayList) {
                requestParameters.put(key, ((ArrayList)value).toArray(
			new String[((ArrayList)value).size()]));
	    } else {
                requestParameters.put(key, value);
	    }
        }
	return requestParameters;
    }
}
