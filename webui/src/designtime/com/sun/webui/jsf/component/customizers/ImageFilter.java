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
 * ImageFilter.java
 *
 * Created on March 22, 2005, 1:37 PM
 */

package com.sun.webui.jsf.component.customizers;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/* ImageFilter.java is a 1.4 example used by FileChooserDemo2.java. */
public class ImageFilter extends FileFilter {
    
    //Accept all directories and all gif, jpg, or png files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        
        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(Utils.gif) ||                
                    extension.equals(Utils.jpg) ||
                    extension.equals(Utils.jpe) ||                    
                    extension.equals(Utils.png) ||
                    extension.equals(Utils.jpeg)) {
                return true;
            } else {
                return false;
            }
        }
        
        return false;
    }
    
    //The description of this filter
    public String getDescription() {
        //"All Image Files (.gif, .jpg, .png, .jpe, .jpeg)";
        return java.util.ResourceBundle.getBundle("com/sun/webui/jsf/component/customizers/Bundle-DT").getString("imageFilterLabel"); // NOI18N
                
    }
}

