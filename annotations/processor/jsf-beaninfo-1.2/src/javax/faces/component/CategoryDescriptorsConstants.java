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
package javax.faces.component;

import com.sun.rave.designtime.CategoryDescriptor;
import java.util.Locale;
import java.util.ResourceBundle;

public class CategoryDescriptorsConstants {
    
    protected static ResourceBundle bundle =
            ResourceBundle.getBundle("javax.faces.component.bundle", Locale.getDefault());

    public static final CategoryDescriptor ADVANCED =
            new CategoryDescriptor(bundle.getString("advanced_category"));

    public static final CategoryDescriptor APPEARANCE =
            new CategoryDescriptor(bundle.getString("appearance_category"));

    public static final CategoryDescriptor DATA = 
            new CategoryDescriptor(bundle.getString("data_category"));
    
    public static final CategoryDescriptor GENERAL =
            new CategoryDescriptor(bundle.getString("general_category"));

}
