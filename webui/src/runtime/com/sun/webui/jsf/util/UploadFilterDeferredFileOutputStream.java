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
 * $Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/woodstock/webui/src/runtime/com/sun/webui/jsf/util/UploadFilterDeferredFileOutputStream.java,v 1.2 2007-10-26 20:22:53 rratta Exp $
 * $Revision: 1.2 $
 * $Date: 2007-10-26 20:22:53 $
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.ThresholdingOutputStream;

/**
 * This class is based on the 
 {@link org.apache.commons.fileupload.DeferredFileOutputStream DeferredFileOutputStream}.
 * <p>
 * The difference is that the implmentation of <code>thresholdReached</code>
 * also checks for maximum file size. If the maximum file size is exceeded
 * reading and writing stops, and any written data up to that point is 
 * removed including, deleting an file that might have been created.
 * However, the reading continues until it is exhausted but the data
 * read is ignored. This implementation permits files to be read based
 * on the maximum file size of the file and not the size of the request.
 * </p>
 * <p>
 * In order to catch exceptions, the <code>ThresholdingOutputStream</code>
 * is implemented within this class directly.
 * </p>
 * <p>
 * Any exceptions that occur result in substituting the output stream
 * with <code>DevNullOutputStream</code> which just consumes the input.
 * The exceptions are added to the <code>List</code> <code>errorLog</code>
 * and eventually made available to the application.
 * </p>
 */
public class UploadFilterDeferredFileOutputStream extends OutputStream {

    /** 
     * The default maximum file size, 1MB.
     */
    private final static long DEFAULT_MAX_FILE_SIZE = 1000000;

    /** 
     * The default threshold 10k.
     */
    private final static int DEFAULT_THRESHOLD = 10240;
    // ----------------------------------------------------------- Data members

    /**
     * The output stream to which data will be written prior to the theshold
     * being reached.
     */
    private ByteArrayOutputStream memoryOutputStream;

    /**
     * The output stream to which data will be written after the theshold is
     * reached.
     */
    private FileOutputStream diskOutputStream;

    /**
     * The output stream to which data will be written at any given time. This
     * will always be one of <code>memoryOutputStream</code> or
     * <code>diskOutputStream</code>.
     */
    private OutputStream currentOutputStream;

    /**
     * The file to which output will be directed if the threshold is exceeded.
     */
    private File outputFile;

    /**
     * The maximum allowable file size.
     */
    private long maxFileSize = DEFAULT_MAX_FILE_SIZE;

    /**
     * Hold exceptions that occur.
     */
    private List errorLog;


    /**
     * The threshold at which the event will be triggered.
     */
    private int threshold = DEFAULT_THRESHOLD;

    /**
     * The number of bytes written to the output stream.
     */
    private long written;


    /**
     * Whether or not the configured threshold has been exceeded.
     */
    private boolean thresholdExceeded;


    // ----------------------------------------------------------- Constructors

    /**
     * Constructs an instance of this class which will trigger an event at the
     * specified threshold, and save data to a file beyond that point.
     *
     * @param threshold  The number of bytes at which to trigger an event.
     * @param outputFile The file to which data is saved beyond the threshold.
     * @param maxFileSize The maximum allowable file size.
     * @param errorLog Any Exceptions that occur are added to this list.
     */
    public UploadFilterDeferredFileOutputStream(int threshold, 
	    File outputFile, long maxFileSize, List errorLog) {

        this.threshold = threshold;
        this.outputFile = outputFile;
	this.maxFileSize = maxFileSize;
	this.errorLog = errorLog;
        memoryOutputStream = new ByteArrayOutputStream(threshold);
        currentOutputStream = memoryOutputStream;
    }

    /**
     * This null arg constructor creates a <code>DevNullOutputStream</code>.
     */
    public UploadFilterDeferredFileOutputStream() {
	currentOutputStream = new DevNullOutputStream();
    }

    // --------------------------------------------------------- Public methods

    /**
     * Determines whether or not the data for this output stream has been
     * retained in memory.
     *
     * @return <code>true</code> if the data is available in memory;
     *         <code>false</code> otherwise.
     */
    public boolean isInMemory() {
        return (!isThresholdExceeded());
    }

    /**
     * Returns the data for this output stream as an array of bytes, assuming
     * that the data has been retained in memory. If the data was written to
     * disk, this method returns <code>null</code>.
     *
     * @return The data for this output stream, or <code>null</code> if no such
     *         data is available.
     */
    public byte[] getData() {
        if (memoryOutputStream != null) {
            return memoryOutputStream.toByteArray();
        }
        return null;
    }

    /**
     * Returns the data for this output stream as a <code>File</code>, assuming
     * that the data was written to disk. If the data was retained in memory,
     * this method returns <code>null</code>.
     *
     * @return The file for this output stream, or <code>null</code> if no such
     *         file exists.
     */
    public File getFile() {
        return outputFile;
    }


    class DevNullOutputStream extends OutputStream {
	public void write(int b) throws IOException {}
	public void write(byte b[]) throws IOException {}
	public void write(byte b[], int off, int len) 
		throws IOException {}
	public void flush() throws IOException {}
	public void close() throws IOException {}
    }

    // The implementation of ThresholdingOutputStream
    //

    // --------------------------------------------------- OutputStream methods


    /**
     * Writes the specified byte to this output stream.
     *
     * @param b The byte to be written.
     *
     * @exception IOException if an error occurs.
     */
    public void write(int b) throws IOException {
        checkThreshold(1);
	try {
	    currentOutputStream.write(b);
	} catch (Exception e) {
	    logException(e);
	}
        written++;
    }


    /**
     * Writes <code>b.length</code> bytes from the specified byte array to this
     * output stream.
     *
     * @param b The array of bytes to be written.
     *
     * @exception IOException if an error occurs.
     */
    public void write(byte b[]) throws IOException {
        checkThreshold(b.length);
	try {
	    currentOutputStream.write(b);
	} catch (Exception e) {
	    logException(e);
	}
        written += b.length;
    }


    /**
     * Writes <code>len</code> bytes from the specified byte array starting at
     * offset <code>off</code> to this output stream.
     *
     * @param b   The byte array from which the data will be written.
     * @param off The start offset in the byte array.
     * @param len The number of bytes to write.
     *
     * @exception IOException if an error occurs.
     */
    public void write(byte b[], int off, int len) throws IOException {
        checkThreshold(len);
	try {
	    currentOutputStream.write(b, off, len);
	} catch (Exception e) {
	    logException(e);
	}
        written += len;
    }


    /**
     * Flushes this output stream and forces any buffered output bytes to be
     * written out.
     *
     * @exception IOException if an error occurs.
     */
    public void flush() throws IOException {
	try {
	    currentOutputStream.flush();
	} catch (Exception e) {
	    logException(e);
	}
    }


    /**
     * Closes this output stream and releases any system resources associated
     * with this stream.
     *
     * @exception IOException if an error occurs.
     */
    public void close() throws IOException {
        try {
	    currentOutputStream.flush();
	    currentOutputStream.close();
        }
        catch (IOException ignored) {
            // ignore
        }
    }


    // --------------------------------------------------------- Public methods


    /**
     * Returns the threshold, in bytes, at which an event will be triggered.
     *
     * @return The threshold point, in bytes.
     */
    public int getThreshold() {
        return threshold;
    }


    /**
     * Returns the number of bytes that have been written to this output stream.
     *
     * @return The number of bytes written.
     */
    public long getByteCount() {
        return written;
    }


    /**
     * Determines whether or not the configured threshold has been exceeded for
     * this output stream.
     *
     * @return <code>true</code> if the threshold has been reached;
     *         <code>false</code> otherwise.
     */
    public boolean isThresholdExceeded() {
        return (written > threshold);
    }


    /**
     * Implement <code>thresholdReached directly in this method.
     * If the pending write exceeds the maxFileSize replace the
     * output stream with a DevNullOutputStream. If the threshold
     * is exceeded replace the memory stream with a file output stream.
     * If that fails, set it to a DevNullOutputStream and log the
     * exceptions.
     * Stops calling thresholdReached once the threshold is exceeded.
     */
    private void checkThreshold(int count) {

	// If this is true then we don't do anything.
	// It'll be weird if maxFileSize is less than the threshold.
	//
	if (currentOutputStream instanceof DevNullOutputStream) {
	    return;
	}
	long total = written + count;
	if (maxFileSize > -1 && total > maxFileSize) {
	    logException(new SizeLimitExceededException(
		Long.toString(maxFileSize)));
	} else 
	if (!thresholdExceeded && (total > threshold)) {
		thresholdExceeded = true;

	    // This was thresholdReached
	    //
	    FileOutputStream fos = null;
	    try {
		byte[] data = memoryOutputStream.toByteArray();
		fos = new FileOutputStream(outputFile);
		fos.write(data);
		diskOutputStream = fos;
		currentOutputStream = fos;
	    } catch(Exception e) {
		try {
		    if (fos != null) {
			fos.close();
		    }
		} catch(Exception e0) {
		    // ignore
		}
		diskOutputStream = null;
		logException(e);
	    }
	    memoryOutputStream = null;
	}
    }

    /**
     * Handle a random exception.
     */
    private void logException(Exception e) {
	try {
	    // Close the output stream.
	    // 
	    close();
	    // Delete the file.
	    //
	    outputFile.delete();
	} catch (Exception ignore) {
	    // Can't do much here.
	}
	// Create a dev/null outputstream
	//
	currentOutputStream = new DevNullOutputStream();
	if (errorLog != null) {
	    errorLog.add(e);
	}
	return;
    }
}
