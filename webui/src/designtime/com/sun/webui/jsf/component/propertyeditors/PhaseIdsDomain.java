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
package com.sun.webui.jsf.component.propertyeditors;

import com.sun.rave.propertyeditors.domains.Domain;
import com.sun.rave.propertyeditors.domains.Element;
import javax.faces.event.PhaseId;
import com.sun.webui.jsf.component.util.DesignMessageUtil;

public class PhaseIdsDomain  extends Domain {
    private static Element[] elements = new Element[] {
        new Element(PhaseId.ANY_PHASE,
                DesignMessageUtil.getMessage(PhaseIdsDomain.class, "PhaseId.ANY_PHASE")), //NOI18N
        new Element(PhaseId.RESTORE_VIEW, 
                DesignMessageUtil.getMessage(PhaseIdsDomain.class, "PhaseId.RESTORE_VIEW")), //NOI18N
        new Element(PhaseId.APPLY_REQUEST_VALUES, 
                DesignMessageUtil.getMessage(PhaseIdsDomain.class, "PhaseId.APPLY_REQUEST_VALUES")), //NOI18N
        new Element(PhaseId.PROCESS_VALIDATIONS, 
                DesignMessageUtil.getMessage(PhaseIdsDomain.class, "PhaseId.PROCESS_VALIDATIONS")), //NOI18N                
        new Element(PhaseId.UPDATE_MODEL_VALUES, 
                DesignMessageUtil.getMessage(PhaseIdsDomain.class, "PhaseId.UPDATE_MODEL_VALUES")), //NOI18N                
        new Element(PhaseId.INVOKE_APPLICATION, 
                DesignMessageUtil.getMessage(PhaseIdsDomain.class, "PhaseId.INVOKE_APPLICATION")), //NOI18N                
        new Element(PhaseId.RENDER_RESPONSE, 
                DesignMessageUtil.getMessage(PhaseIdsDomain.class, "PhaseId.RENDER_RESPONSE")) //NOI18N                
                
    };
    
    public Element[] getElements() {
        return PhaseIdsDomain.elements;
    }    

}
