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
 * $Id: ScriptMarkup.java,v 1.1 2007-02-16 01:32:26 bob_yennaco Exp $
 */

package com.sun.webui.jsf.model;


/**
 * <p>Specialized version of {@link Markup} that automatically surrounds
 * any accumulated markup in this element with the required prolog and
 * epilogue strings for an embedded script element.</p>
 */

public class ScriptMarkup extends Markup {


    // ----------------------------------------------------- Instance Variables


    /**
     * <p>The CDATA wrapping flag for this markup.</p>
     */
    private boolean cdata = false;


    // ------------------------------------------------------------- Properties


    /**
     * <p>Return the current state of CDATA wrapping for this markup.</p>
     */
    public boolean isCdata() {

        return this.cdata;

    }


    /**
     * <p>Set the new state of CDATA wrapping for this markup.</p>
     *
     * @param cdata New wrapping flag
     */
    public void setCdata(boolean cdata) {

        this.cdata = cdata;

    }



    /**
     * <p>Return the accumulated markup for this element, surrounded by the
     * required prolog and epilog strings for an embedded script element.</p>
     */
    public String getMarkup() {

        StringBuffer sb = new StringBuffer
            ("<script type=\"text/javascript\">"); //NOI18N
        if (isCdata()) {
            sb.append("<![CDATA["); //NOI18N
        }
        sb.append("\n"); //NOI18N
        sb.append(super.getMarkup());
        sb.append("\n"); //NOI18N
        if (isCdata()) {
            sb.append("]]>"); //NOI18N
        }
        sb.append("</script>\n"); //NOI18N
        return sb.toString();

    }


}
