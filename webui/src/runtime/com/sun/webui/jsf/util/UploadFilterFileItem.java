/*
 * $Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/woodstock/webui/src/runtime/com/sun/webui/jsf/util/UploadFilterFileItem.java,v 1.1 2007-06-07 21:53:44 rratta Exp $
 * $Revision: 1.1 $
 * $Date: 2007-06-07 21:53:44 $
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001-2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package com.sun.webui.jsf.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

/**
 * This class is based on the the default implementation of the
 * {@link org.apache.commons.fileupload.FileItem FileItem} interface,
 * {@link org.apache.commons.fileupload.DefaultFileItem DefaultFileItem}.
 * <p>
 * The difference is that this implementation uses the maximum file size
 * for each file uploaded, vs. comparing the maximum file size the size
 * of the request. These can differ significantly, especially when more
 * than one file is uploaded in a given request.
 * <p>
 * Another difference is that if the maximum size is exceeded only that
 * file is not uploaded. If all files exceed the maximum file size then
 * only the form data is collected and passed on to the application.
 * </p>
 * <p>
 * <code>UploadFilterFileItem</code> collects exceptions that occur in 
 * its <code>errorLog</code> member it passes it to the
 * <code>UploadFilterDeferredFileOutputStream</code> instance it creates.
 * </p>
 * <p>
 * <em>Note that like the DefaultFileItem implemenentation, getOutputStream
 * must be called before many if not all other methods, otherwise
 * NPE will result.</em>
 * </p>
 */
public class UploadFilterFileItem implements FileItem {

    // ----------------------------------------------------------- Data members

    /**
     * The default maximum file size 1MB.
     */
    private static final long DEFAULT_MAX_FILE_SIZE = 1000000;

    /**
     * Counter used in unique identifier generation.
     */
    private static int counter = 0;

    /**
     * The name of the form field as provided by the browser.
     */
    private String fieldName;

    /**
     * The content type passed by the browser, or <code>null</code> if
     * not defined.
     */
    private String contentType;

    /**
     * Whether or not this item is a simple form field.
     */
    private boolean isFormField;

    /**
     * The is the value submitted in the request for the input element
     * of type "file". This is not necessarily the full path name. Some
     * browsers only submit the file name and not the directory portion
     * of the path. @see #fullPathName
     */
    private String fileName;

    /**
     * The threshold above which uploads will be stored on disk.
     */
    private int sizeThreshold;

    /**
     * The directory in which uploaded files will be stored, if stored on disk.
     */
    private File repository;

    /**
     * Cached contents of the file.
     */
    private byte[] cachedContent;

    /**
     * Output stream for this item.
     */
    private UploadFilterDeferredFileOutputStream dfos;

    /**
     * The maximum allowable file size.
     */
    private long maxFileSize = DEFAULT_MAX_FILE_SIZE;

    /**
     * Hold exceptions that occur during upload.
     */
    private List errorLog = new ArrayList();

    /**
     * The value of the input element of type "file" that was entered
     * by the user. This may be different from the value returned by
     * <code>getName</code>.
     */
    private String clientFilePath;

    // ----------------------------------------------------------- Constructors

    /**
     * Constructs a new <code>DefaultFileItem</code> instance.
     *
     * @param fieldName     The name of the form field.
     * @param contentType   The content type passed by the browser or
     *                      <code>null</code> if not specified.
     * @param isFormField   Whether or not this item is a plain form field, as
     *                      opposed to a file upload.
     * @param fileName      The original filename in the user's filesystem, or
     *                      <code>null</code> if not specified.
     * @param sizeThreshold The threshold, in bytes, below which items will be
     *                      retained in memory and above which they will be
     *                      stored as a file.
     * @param repository    The data repository, which is the directory in
     *                      which files will be created, should the item size
     *                      exceed the threshold.
     */
    UploadFilterFileItem(String fieldName, String contentType, 
	    boolean isFormField, String fileName, int sizeThreshold, 
	    File repository) {

        this.fieldName = fieldName;
        this.contentType = contentType;
        this.isFormField = isFormField;
        this.fileName = fileName;
        this.sizeThreshold = sizeThreshold;
        this.repository = repository;
    }

    /**
     * Constructs a new <code>DefaultFileItem</code> instance.
     *
     * @param fieldName     The name of the form field.
     * @param contentType   The content type passed by the browser or
     *                      <code>null</code> if not specified.
     * @param isFormField   Whether or not this item is a plain form field, as
     *                      opposed to a file upload.
     * @param fileName      The original filename in the user's filesystem, or
     *                      <code>null</code> if not specified.
     * @param sizeThreshold The threshold, in bytes, below which items will be
     *                      retained in memory and above which they will be
     *                      stored as a file.
     * @param repository    The data repository, which is the directory in
     *                      which files will be created, should the item size
     *                      exceed the threshold.
     */
    UploadFilterFileItem(String fieldName, String contentType, 
	    boolean isFormField, String fileName, int sizeThreshold, 
	    File repository, long maxFileSize) {

	this(fieldName, contentType, isFormField, fileName, sizeThreshold,
	    repository);
        this.maxFileSize = maxFileSize;
    }

    // ------------------------------- Methods from javax.activation.DataSource

    /**
     * Returns an {@link java.io.InputStream InputStream} that can be
     * used to retrieve the contents of the file.
     *
     * @return An {@link java.io.InputStream InputStream} that can be
     *         used to retrieve the contents of the file.
     *
     * @exception IOException if an error occurs.
     */
    public InputStream getInputStream() throws IOException {
	if (!dfos.isInMemory()) {
	    return new FileInputStream(dfos.getFile());
	}

	if (cachedContent == null) {
	    cachedContent = dfos.getData();
	}
	return new ByteArrayInputStream(cachedContent);
    }

    /**
     * Returns the content type passed by the browser or <code>null</code> if
     * not defined.
     *
     * @return The content type passed by the browser or <code>null</code> if
     *         not defined.
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * This is the value of the form-data field in the request representing
     * the input element with "type" file. Some browsers only submit the
     * file name portion and not the full path as entered by the user.
     * @see #clientFilePath
     *
     * @return The original filename in the client's filesystem.
     */
    public String getName() {
        return fileName;
    }

    /**
     * The file path as entered by the user on the client. This may not
     * always be the same as the value of <code>getName</code>. This value
     * may also be null if the <code>preservePath</code> property on the
     * <code>Upload</code> component was false.
     */
    public String getClientFilePath() {
	return clientFilePath;
    }

    /**
     * The file path as entered by the user on the client. This may not
     * always be the same as the value of <code>getName</code>. This value
     * may also be null if the <code>preservePath</code> property on the
     * <code>Upload</code> component was false. This method is called
     * by the <code>UploadFilter</code> it encounters the a form-data
     * field that represents this value. 
     * @see com.sun.webui.jsf.component.Upload#setPreservePath
     */
    public String setClientFilePath(String clientFilePath) {
	return this.clientFilePath = clientFilePath;
    }

    // ------------------------------------------------------- FileItem methods

    /**
     * Provides a hint as to whether or not the file contents will be read
     * from memory.
     *
     * @return <code>true</code> if the file contents will be read
     *         from memory; <code>false</code> otherwise.
     */
    public boolean isInMemory() {
        return (dfos.isInMemory());
    }


    /**
     * Returns the size of the file.
     *
     * @return The size of the file, in bytes.
     */
    public long getSize() {
        if (cachedContent != null) {
            return cachedContent.length;
        } else if (dfos.isInMemory()) {
            return dfos.getData().length;
        } else {
            return dfos.getFile().length();
        }
    }

    /**
     * Returns the contents of the file as an array of bytes.  If the
     * contents of the file were not yet cached in memory, they will be
     * loaded from the disk storage and cached.
     *
     * @return The contents of the file as an array of bytes.
     */
    public byte[] get() {
        if (dfos.isInMemory()) {
            if (cachedContent == null) {
                cachedContent = dfos.getData();
            }
            return cachedContent;
        }

        byte[] fileData = new byte[(int) getSize()];
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(dfos.getFile());
            fis.read(fileData);
        } catch (IOException e) {
            fileData = null;
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return fileData;
    }


    /**
     * Returns the contents of the file as a String, using the specified
     * encoding.  This method uses {@link #get()} to retrieve the
     * contents of the file.
     *
     * @param encoding The character encoding to use.
     *
     * @return The contents of the file, as a string.
     *
     * @exception UnsupportedEncodingException if the requested character
     *                                         encoding is not available.
     */
    public String getString(String encoding)
        throws UnsupportedEncodingException {
        return new String(get(), encoding);
    }

    /**
     * Returns the contents of the file as a String, using the default
     * character encoding.  This method uses {@link #get()} to retrieve the
     * contents of the file.
     *
     * @return The contents of the file, as a string.
     */
    public String getString() {
        return new String(get());
    }

    /**
     * A convenience method to write an uploaded item to disk. The client code
     * is not concerned with whether or not the item is stored in memory, or on
     * disk in a temporary location. They just want to write the uploaded item
     * to a file.
     * <p>
     * This implementation first attempts to rename the uploaded item to the
     * specified destination file, if the item was originally written to disk.
     * Otherwise, the data will be copied to the specified file.
     * <p>
     * This method is only guaranteed to work <em>once</em>, the first time it
     * is invoked for a particular item. This is because, in the event that the
     * method renames a temporary file, that file will no longer be available
     * to copy or rename again at a later time.
     *
     * @param file The <code>File</code> into which the uploaded item should
     *             be stored.
     *
     * @exception Exception if an error occurs.
     */
    public void write(File file) throws Exception {
        if (isInMemory()) {
            FileOutputStream fout = null;
            try {
                fout = new FileOutputStream(file);
                fout.write(get());
            } finally {
                if (fout != null) {
                    fout.close();
                }
            }
        } else {
            File outputFile = getStoreLocation();
            if (outputFile != null) {
                /*
                 * The uploaded file is being stored on disk
                 * in a temporary location so move it to the
                 * desired file.
                 */
                if (!outputFile.renameTo(file)) {
                    BufferedInputStream in = null;
                    BufferedOutputStream out = null;
                    try {
                        in = new BufferedInputStream(
                            new FileInputStream(outputFile));
                        out = new BufferedOutputStream(
                                new FileOutputStream(file));
                        byte[] bytes = new byte[2048];
                        int s = 0;
                        while ((s = in.read(bytes)) != -1) {
                            out.write(bytes, 0, s);
                        }
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            // ignore
                        }
                        try {
                            out.close();
                        } catch (IOException e) {
                            // ignore
                        }
                    }
                }
            } else {
                /*
                 * For whatever reason we cannot write the
                 * file to disk.
                 */
                throw new FileUploadException(
                    "Cannot write uploaded file to disk.");
            }
        }
    }

    /**
     * Deletes the underlying storage for a file item, including deleting any
     * associated temporary disk file. Although this storage will be deleted
     * automatically when the <code>FileItem</code> instance is garbage
     * collected, this method can be used to ensure that this is done at an
     * earlier time, thus preserving system resources.
     */
    public void delete() {
        cachedContent = null;
        File outputFile = getStoreLocation();
        if (outputFile != null && outputFile.exists()) {
            outputFile.delete();
        }
    }

    /**
     * Returns the name of the field in the multipart form corresponding to
     * this file item.
     *
     * @return The name of the form field.
     *
     * @see #setFieldName(java.lang.String)
     *
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the field name used to reference this file item.
     *
     * @param fieldName The name of the form field.
     *
     * @see #getFieldName()
     *
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Determines whether or not a <code>FileItem</code> instance represents
     * a simple form field.
     *
     * @return <code>true</code> if the instance represents a simple form
     *         field; <code>false</code> if it represents an uploaded file.
     *
     * @see #setFormField(boolean)
     *
     */
    public boolean isFormField() {
        return isFormField;
    }

    /**
     * Specifies whether or not a <code>FileItem</code> instance represents
     * a simple form field.
     *
     * @param state <code>true</code> if the instance represents a simple form
     *              field; <code>false</code> if it represents an uploaded file.
     *
     * @see #isFormField()
     *
     */
    public void setFormField(boolean state) {
        isFormField = state;
    }

    /**
     * Returns an {@link java.io.OutputStream OutputStream} that can
     * be used for storing the contents of the file.
     *
     * @return An {@link java.io.OutputStream OutputStream} that can be used
     *         for storing the contensts of the file.
     *
     * @exception IOException if an error occurs.
     */
    public OutputStream getOutputStream()
        throws IOException {
        if (dfos == null) {
	    try {
		File outputFile = getTempFile();
		dfos = new UploadFilterDeferredFileOutputStream(sizeThreshold, 
			outputFile, maxFileSize, errorLog);
	    } catch(Exception e) {
		errorLog.add(e);
		return (dfos = new UploadFilterDeferredFileOutputStream());
	    }
        }
        return dfos;
    }

    // --------------------------------------------------------- Public methods

    /**
     * Returns the {@link java.io.File} object for the <code>FileItem</code>'s
     * data's temporary location on the disk. Note that for
     * <code>FileItem</code>s that have their data stored in memory,
     * this method will return <code>null</code>. When handling large
     * files, you can use {@link java.io.File#renameTo(java.io.File)} to
     * move the file to new location without copying the data, if the
     * source and destination locations reside within the same logical
     * volume.
     *
     * @return The data file, or <code>null</code> if the data is stored in
     *         memory.
     */
    public File getStoreLocation() {
        return dfos.getFile();
    }

    /**
     * Return any collected errors during the upload.
     */
    public List getErrorLog() {
	return errorLog;
    }

    // ------------------------------------------------------ Protected methods

    /**
     * Removes the file contents from the temporary storage.
     */
    protected void finalize() {
        File outputFile = dfos.getFile();

        if (outputFile != null && outputFile.exists()) {
            outputFile.delete();
        }
    }

    /**
     * Creates and returns a {@link java.io.File File} representing a uniquely
     * named temporary file in the configured repository path.
     *
     * @return The {@link java.io.File File} to be used for temporary storage.
     */
    protected File getTempFile() {
        File tempDir = repository;
        if (tempDir == null) {
            tempDir = new File(System.getProperty("java.io.tmpdir"));
        }

        String fileName = "upload_" + getUniqueId() + ".tmp";

        File f = new File(tempDir, fileName);
        f.deleteOnExit();
        return f;
    }

    // -------------------------------------------------------- Private methods

    /**
     * Returns an identifier that is unique within the class loader used to
     * load this class, but does not have random-like apearance.
     *
     * @return A String with the non-random looking instance identifier.
     */
    private static String getUniqueId() {
        int current;
        synchronized (UploadFilterFileItem.class) {
            current = counter++;
        }
        String id = Integer.toString(current);

        // If you manage to get more than 100 million of ids, you'll
        // start getting ids longer than 8 characters.
        if (current < 100000000) {
            id = ("00000000" + id).substring(id.length());
        }
        return id;
    }
}
