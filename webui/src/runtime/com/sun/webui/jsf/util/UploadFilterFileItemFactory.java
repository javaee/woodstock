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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletRequest;

import org.apache.commons.fileupload.DefaultFileItemFactory;
import org.apache.commons.fileupload.FileItem;

/**
 * This class extends 
 * {@link org.apache.commons.fileupload.DefaultFileItemFactory} but
 * creates {@link org.apache.commons.fileupload.FileItem} instances
 * of <code>UploadFilterFileItem</code>. This factory supports this
 * implementation of FileItem by enforcing the application's maximum
 * file size for each file uploaded, vs. the size the of the total
 * size of the request. If a given uploaded file exceeds the maximum
 * any data read and/or written up until that time is discarded
 * and all data read from that point is discarded. Any files created
 * are deleted. In addition instead of aborting, all form-data is
 * read and passed onto the application.
 */
public class UploadFilterFileItemFactory extends DefaultFileItemFactory {

    /**
     * The maximum allowable file size, 1MB.
     */
    private static long DEFAULT_MAXIMUM_FILE_SIZE = 1000000; // In bytes

    private long maxFileSize = DEFAULT_MAXIMUM_FILE_SIZE;

    public UploadFilterFileItemFactory() {
	super();
    }

    public UploadFilterFileItemFactory(int sizeThreshold, File repository) {
	super(sizeThreshold, repository);
    }

    public UploadFilterFileItemFactory(int sizeThreshold, String repositoryPath,
	    long maxFileSize) {
	super(sizeThreshold, new File(repositoryPath));
	this.maxFileSize = maxFileSize;
    }

    // --------------------------------------------------------- Public Methods

    /**
     * Create a new {@link com.sun.web.ui.util.UploadFilterFileItem}
     * instance from the supplied parameters and the local factory
     * configuration.
     *
     * @param fieldName   The name of the form field.
     * @param contentType The content type of the form field.
     * @param isFormField <code>true</code> if this is a plain form field;
     *                    <code>false</code> otherwise.
     * @param fileName    The name of the uploaded file, if any, as supplied
     *                    by the browser or other client.
     *
     * @return The newly created file item.
     */
    public FileItem createItem(
            String fieldName,
            String contentType,
            boolean isFormField,
            String fileName
            ) {

	FileItem fi = (FileItem)new UploadFilterFileItem(fieldName, 
	    contentType, isFormField, fileName, getSizeThreshold(),
	    getRepository(), maxFileSize);

	return fi;
    }

}
