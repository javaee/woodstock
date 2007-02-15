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

package org.example;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import org.example.base.SuperBean01;

@Component()
public class Component10 implements Interface04 {
    
    @Property(displayName="The First")
    private String one;

    public String getOne() {
        return this.one;
    }

    public void setOne(String one) {
        this.one = one;
    }
    
    @Property(isAttribute=true)
    private String two;

    public String getTwo() {
        return this.two;
    }

    public void setTwo(String two) {
        this.two = two;
    }
    
    @Property(displayName="The Third")
    private String three;

    public String getThree() {
        return this.three;
    }

    public void setThree(String three) {
        this.three = three;
    }
}
