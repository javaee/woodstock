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

package com.sun.webui.jsf.component;

import com.sun.rave.designtime.DesignBean;
import com.sun.webui.jsf.design.AbstractDesignInfo;

/**
 * DesignInfo for the {@link com.sun.webui.jsf.component.Scheduler} component.
 */
public class SchedulerDesignInfo extends AbstractDesignInfo {
    
    /**
     * Default constructor.
     */
    public SchedulerDesignInfo() {
        super(Scheduler.class);
    }    
    
     /**
     * <p>This method is called any time a new component is being created or
     * dragged around in the visual designer. Returns false indicating this child
     * component can not be added as a child to the scheduler component.</p>
     *   
     * @param parentBean The DesignBean representing the potential parent
     * component to receive the child
     * @param childBean The DesignBean representing the potential child
     * component that is being created or reparented.
     * @param childClass The Class object representing the potential child
     * component that is being created or reparented.
     * @return true if this child bean is suitable for this parent bean,
     * or false if not
     */
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        return false;
    }  
    
    /**
     * Scheduler doesn't need to support the "autosubmit" behavior,
     * so disabling the default behavior.
     *
     * @param bean <code>DesignBean</code> for the newly created instance 
     */
    public boolean supportsAutoSubmit(DesignBean bean) {
           return false;
    }
}
