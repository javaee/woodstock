/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
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
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */
package com.sun.webui.tools;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;

public class ExecProcess implements Runnable {
    private String command;
    private final boolean debug = false;
    private Process currentProcess;
    private int returnValue = -1;
    private boolean finished;

    public ExecProcess(String command) {
	this.command = command;
    }
    
    public void run() {
	// wait for the current process to finish
	if(currentProcess != null) {
	    try {
		returnValue = currentProcess.waitFor();
	    }
	    catch(Exception e) {
		if(debug) {
		    System.out.println(
			"Exception caught while waiting for currentProcess");
		    e.printStackTrace();
		}
		System.out.println("Exception in currentProcess.waitFor();");
	    }
	}
	else {
	    System.err.println("ExecProcess.run() - currentProcess is null!");
	}
	if(debug) {
	    System.out.println("Thread finished : returnValue == " +
	    returnValue);
	}
	finished = true;
    }

    /**
     * Exec's <command> piping err and input stream output to the screen
     */
    public synchronized int exec() {
	try {	    
	    if(debug) {
		System.out.println(
		    "ExecProcess.exec() - command is: " + command);
	    }
	    Runtime           runtime = Runtime.getRuntime();
	    Process           process = runtime.exec(command);
	    // stdout
	    InputStream       is      = process.getInputStream();
	    // stderr
	    InputStream       es      = process.getErrorStream();
	    // stdin
	    OutputStream      os      = process.getOutputStream();

	    InputStreamReader ir      = new InputStreamReader(is);
	    InputStreamReader er      = new InputStreamReader(es);
	    OutputStreamWriter or      = new OutputStreamWriter(os);
	    BufferedReader    ibr     = new BufferedReader(ir);
	    BufferedReader    ebr     = new BufferedReader(er);

	    finished       = false;
	    currentProcess = process;
	    Thread t       = new Thread(this);
	    
	    if(debug) {
		System.out.println("Starting thread...");
	    }
	    t.start();
    
	    if(debug) {
		System.out.println("Looping for stdin... ");
	    }
	    
	    boolean continueFlag = true;
	    int     count        = 0;
	    do {
		if(debug) {
		    System.out.println("loop.");
		}
		// See if any input has come along either input stream
		try {
		    // stdout
		    if(ibr.ready()) {
			while(ibr.ready()) {
			    //char ch = (char)ibr.read();
			    //System.out.print(String.valueOf(ch));
			    String line = ibr.readLine();
			    System.out.println(line);
			    System.out.flush();
			}
		    }
		    // stderr
		    if(ebr.ready()) {
			while(ebr.ready()) {
			    String line = ebr.readLine();
			    System.err.println(line);
			    System.err.flush();
			}   
		    }
		    Thread.currentThread().sleep(100);
		    //throw new Exception("Force the exception");
		}
		catch(Exception e) {
		    if(debug) {
			System.out.println(
			    "Exception encountered while outputting streams");
			e.printStackTrace();		    
		    }
		    currentProcess.destroy();
		    System.out.println("Called destroy");
		}
		if(finished == true) {
		    // wait 4 cycles after command has finished 
		    // (just to be sure to get all stdout)
		    if(count >= 4) {
			continueFlag = false;
		    }
		    else {
			count++;
		    }
		}

	    }
	    while (continueFlag);
	}
	catch(Exception e) {
	    System.out.println(
		"An error occured while trying to exec cmd: " + command);
	    if(debug) {
		e.printStackTrace();
	    }
	}
	return returnValue;
    }
}
