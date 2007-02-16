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
package com.sun.webui.jsf.renderkit.html;

import com.sun.webui.jsf.component.TreeNode;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.renderkit.html.TreeNodeRenderer;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;


/**
 * A delegating renderer for {@link com.sun.webui.jsf.component.TreeNode}.
 *
 * @author gjmurphy
 */
public class TreeNodeDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    protected static String STYLE_CLASS_PROP = "styleClass"; //NOI18N
    
    boolean isTextSet;
    boolean isStyleSet;
    
    public TreeNodeDesignTimeRenderer() {
        super(new TreeNodeRenderer());
    }
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (TreeNode.class.isAssignableFrom(component.getClass())) {
            ValueBinding valueBinding = component.getValueBinding("text"); //NOI18N
            TreeNode treeNode = (TreeNode) component;
            String text = treeNode.getText();
            if (valueBinding != null && (text == null || text.toString().length() == 0)) {
                Object dummyText = getDummyData(context, valueBinding);
                if (dummyText == null) {
                    treeNode.setText("");
                } else {
                    treeNode.setText(dummyText.toString());
                }
                isTextSet = true;
            } else if (treeNode.getText() == null) {
                treeNode.setText(DesignMessageUtil.getMessage(TreeNodeDesignTimeRenderer.class, "treeNode.label"));
                String styleClass = (String) component.getAttributes().get(STYLE_CLASS_PROP);
                component.getAttributes().put(STYLE_CLASS_PROP, addStyleClass(styleClass, UNINITITIALIZED_STYLE_CLASS));
                isTextSet = true;
                isStyleSet = true;
            }
        }
        super.encodeBegin(context, component);
    }
    
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        super.encodeEnd(context, component);
        if (isTextSet) {
            TreeNode treeNode = (TreeNode) component;
            treeNode.setText(null);
            isTextSet = false;
        }
        if (isStyleSet) {
            String styleClass = (String) component.getAttributes().get(STYLE_CLASS_PROP);
            component.getAttributes().put(STYLE_CLASS_PROP, removeStyleClass(styleClass, UNINITITIALIZED_STYLE_CLASS));
            isStyleSet = false;
        }
    }
}
