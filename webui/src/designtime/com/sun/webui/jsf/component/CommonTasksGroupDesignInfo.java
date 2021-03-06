/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.webui.jsf.component;


import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import javax.faces.component.html.HtmlPanelGrid;
import com.sun.rave.designtime.markup.*;
import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignContext;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.Result;
import com.sun.rave.designtime.faces.FacesDesignContext;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.component.util.DesignUtil;
import com.sun.webui.jsf.design.AbstractDesignInfo;
import com.sun.webui.jsf.component.CommonTask;

/**
 *DesignInfo class for components that extend the {@link
 * com.sun.webui.jsf.component.CommonTasksGroup} component.
 */

public class CommonTasksGroupDesignInfo extends AbstractDesignInfo {
    
    /** Creates a new instance of CommonTasksGroupDesignInfo */
    public CommonTasksGroupDesignInfo() {
        super (CommonTasksGroup.class);
    }
    
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {        
        return true;
    }
    
        public Result beanCreatedSetup(DesignBean bean) {
        DesignContext context = bean.getDesignContext();
        if (context.canCreateBean(CommonTask.class.getName(), bean, null)) {
            DesignBean commonTaskBean = context.createBean(CommonTask.class.getName(), bean, null);
            commonTaskBean.getDesignInfo().beanCreatedSetup(commonTaskBean);
        }
        return Result.SUCCESS;
    }
        
    public boolean acceptParent(DesignBean parentBean, DesignBean childBean, Class childClass) {
        Class parentClass = parentBean.getInstance().getClass();
        if(CommonTasksGroup.class.equals(parentClass)) {
            return false;
        } else {
            return true;
        }
    }
}
