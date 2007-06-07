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

import java.io.File;
import java.util.List;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.DefaultFileItemFactory;

/**
 * This class extends {@link org.apache.commons.fileupload.DiskFileUpload}.
 * <p>
 * It is different in that it enforces the maximum file size for
 * each uploaded file, vs. the total size of the request.
 * </p>
 */
public class UploadFilterDiskFileUpload extends DiskFileUpload {

    // ----------------------------------------------------------- Data members

    // ----------------------------------------------------------- Constructors


    /**
     * Constructs an instance of this class which uses the default factory to
     * create <code>FileItem</code> instances.
     *
     * @see #DiskFileUpload(DefaultFileItemFactory fileItemFactory)
     */
    public UploadFilterDiskFileUpload() {
        super((DefaultFileItemFactory)new UploadFilterFileItemFactory());
    }


    /**
     * Constructs an instance of this class which uses the supplied factory to
     * create <code>FileItem</code> instances.
     *
     * @see #DiskFileUpload()
     */
    public UploadFilterDiskFileUpload(UploadFilterFileItemFactory
		fileItemFactory) {
        super((DefaultFileItemFactory)fileItemFactory);
    }

    /**
     * Constructs an instance with the configuration defined by the 
     * arguments.
     */
    public UploadFilterDiskFileUpload(int sizeThreshold, long maxFileSize, 
	    String repository, String encoding) {

	this(new UploadFilterFileItemFactory(sizeThreshold, repository,
	    maxFileSize));

	// This tells the underlying DiskFileUpload to not check
	// file size, which is done at lower levels.
	//
	super.setSizeMax(-1);
	super.setHeaderEncoding(encoding);
    }

    // ----------------------------------------------------- Property accessors

    // --------------------------------------------------------- Public methods
}
