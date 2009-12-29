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
 * UploadedFile.java
 *
 * Created on April 1, 2005, 9:39 AM
 */
package com.sun.webui.jsf.model;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author avk
 */
//FIXME Should this be implements Serializable?
public interface UploadedFile extends Serializable {

    /**
     * Returns a {@link java.io.InputStream InputStream} for reading the file. 
     *
     * @return An {@link java.io.InputStream InputStream} for reading the file. 
     *
     * @exception IOException if there is a problem while reading the file
     */
    public InputStream getInputStream() throws IOException;

    /**
     * Get the content-type that the browser communicated with the request
     * that included the uploaded file. If the browser did not specify a 
     * content-type, this method returns null. 
     *
     * @return  the content-type that the browser communicated with the request
     * that included the uploaded file
     */
    public String getContentType();

    /**
     * Use this method to retrieve the name that the file has on the web 
     * application user's local system. 
     *
     * @return the name of the file on the web app user's system
     */
    public String getOriginalName();


    // ------------------------------------------------------- FileItem methods
    /**
     * The size of the file in bytes
     *
     * @return The size of the file in bytes.
     */
    public long getSize();

    /**
     * Use this method to retrieve the contents of the file as an array of bytes. 
     * @return The contents of the file as a byte array
     */
    public byte[] getBytes();

    /**
     * Use this method to retrieve the contents of the file as a String
     *
     * @return the contents of the file as a String
     */
    public String getAsString();

    /**
     * Write the contents of the uploaded file to a file on the server host. 
     * Note that writing files outside of the web server's tmp directory
     * must be explicitly permitted through configuration of the server's 
     * security policy. 
     * 
     * This method is not guaranteed to succeed if called more than once for
     * the same item. 
     * @param file The <code>File</code> where the contents should be written
     *
     * @exception Exception the 
     */
    public void write(File file) throws Exception;

    /**
     * Dispose of the resources associated with the file upload (this will
     * happen automatically when the resource is garbage collected). 
     */
    public void dispose();
}
