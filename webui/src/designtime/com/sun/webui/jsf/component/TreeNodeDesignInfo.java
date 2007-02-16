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
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.Result;
import com.sun.rave.designtime.faces.FacesDesignBean;
import com.sun.rave.designtime.faces.FacesDesignContext;
import com.sun.rave.designtime.markup.BasicMarkupMouseRegion;
import com.sun.rave.designtime.markup.MarkupDesignBean;
import com.sun.rave.designtime.markup.MarkupDesignInfo;
import com.sun.rave.designtime.markup.MarkupMouseRegion;
import com.sun.rave.designtime.markup.MarkupPosition;
import com.sun.rave.designtime.markup.MarkupRenderContext;
import com.sun.webui.jsf.component.util.DesignUtil;
import com.sun.webui.jsf.design.AbstractDesignInfo;

import javax.faces.component.UICommand;
import javax.faces.component.UIGraphic;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import org.w3c.dom.Attr;

import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;


/**
 * Design time behavior for a <code>TreeNode</code> component.
 *
 * @author gjmurphy
 * @author Edwin Goei
 */

public class TreeNodeDesignInfo extends AbstractDesignInfo implements MarkupDesignInfo {
    
    /** Name of the image facet */
    private static final String IMAGE_FACET = "image"; //NOI18N
    
    /** Name of the content facet */
    private static final String CONTENT_FACET = "content"; //NOI18N
    
    /** Name of theme icon for external tree nodes */
    private static final String TREE_DOCUMENT_ICON = "TREE_DOCUMENT"; //NOI18N
    
    /** Name of theme icon for internal tree nodes */
    private static final String TREE_FOLDER_ICON = "TREE_FOLDER"; //NOI18N
    
    public TreeNodeDesignInfo() {
        super(TreeNode.class);
    }
    
    protected DesignProperty getDefaultBindingProperty(DesignBean targetBean) {
        return targetBean.getProperty("text"); //NOI18N
    }
    
    public boolean acceptParent(DesignBean parentBean, DesignBean childBean,
            Class childClass) {
        Class parentClass = parentBean.getInstance().getClass();
        if (TreeNode.class.isAssignableFrom(parentClass))
            return true;
        return false;
    }
    
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean,
            Class childClass) {
        if (childClass.equals(TreeNode.class) || UIGraphic.class.isAssignableFrom(childClass)
        || UICommand.class.isAssignableFrom(childClass)
        || UIOutput.class.isAssignableFrom(childClass))
            return true;
        return false;
    }
    
    public Result beanCreatedSetup(DesignBean bean) {
        DesignProperty prop;
        
        // Set a default display name for the text node
        String suffix = DesignUtil.getNumericalSuffix(bean.getInstanceName());
        String displayName = bean.getBeanInfo().getBeanDescriptor().getDisplayName();
        bean.getProperty("text").setValue(displayName + " " + suffix); //NOI18N
        
        // Add an image component to the image facet, set by default to display
        // the theme icon for tree nodes
        FacesDesignContext fdc = (FacesDesignContext) bean.getDesignContext();
        String imageComponentName = ImageComponent.class.getName();
        if (fdc.canCreateFacet(IMAGE_FACET, imageComponentName, bean)) {
            DesignBean imageFacet = fdc.createFacet(IMAGE_FACET,
                    imageComponentName, bean);
            imageFacet.getProperty("icon").setValue(TREE_DOCUMENT_ICON); //NOI18N
        } else {
            return Result.FAILURE;
        }
        
        // If parent componet is a tree node, make it expanded. Also, if it has
        // an image facet, and the facet's component's icon is set to the document 
        // icon, change it to folder.
        DesignBean parent = bean.getBeanParent();
        if (parent.getInstance().getClass().equals(TreeNode.class)) {
            parent.getProperty("expanded").setValue(Boolean.TRUE); //NOI18N
            DesignBean imageFacet = ((FacesDesignBean)parent).getFacet(IMAGE_FACET);
            if (imageFacet != null && imageFacet.getInstance().getClass().equals(ImageComponent.class)) {
                DesignProperty iconProperty = imageFacet.getProperty("icon"); //NOI18N
                if (iconProperty.getValue() != null && iconProperty.getValue().equals(TREE_DOCUMENT_ICON))
                    iconProperty.setValue(TREE_FOLDER_ICON);
            }
        }
        
        return Result.SUCCESS;
    }
    
    public void customizeRender(final MarkupDesignBean bean, MarkupRenderContext renderContext) {
        
        DocumentFragment documentFragment = renderContext.getDocumentFragment();
        MarkupPosition begin = renderContext.getBeginPosition();
        MarkupPosition end = renderContext.getEndPosition();
        if (begin == end)
            return;
        
        // Look for div element that wraps the image and hyperlink which implement
        // the tree node toggle widget (./div[@class='TreeRow']/div[@class='float'])
	Theme theme =
	    ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
	String treeRow = theme.getStyleClass(ThemeStyles.TREE_ROW);
	String flt = theme.getStyleClass(ThemeStyles.FLOAT);

        Node thisNode = begin.getBeforeSibling();
        Node lastNode = end.getBeforeSibling();
        Element div = null;

        while (thisNode != lastNode && div == null) {
            if (thisNode instanceof Element) {
                Element e = (Element)thisNode;
                if (e.getLocalName().equals("div") && e.getAttribute("class") != null
                        && e.getAttribute("class").indexOf(treeRow) != -1)
                    div = e;
            }
            thisNode = thisNode.getNextSibling();
        }
        if (div == null)
            return;
        NodeList nodeList = div.getElementsByTagName("div");
        div = null;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element e = (Element) nodeList.item(i);
            if (e.getAttribute("class") != null && e.getAttribute("class").indexOf(flt) != -1)
                div = e;
        }
        if (div == null)
            return;
        
        // Associate a markup mouse region with the tree node toggle markup, which
        // expands or contracts the node when clicked
        MarkupMouseRegion region = new BasicMarkupMouseRegion() {
            
            public boolean isClickable() {
                return true;
            }
            
            public Result regionClicked(int clickCount) {
                DesignProperty property = bean.getProperty("expanded"); // NOI18N
                property.setValue(property.getValue().equals(Boolean.TRUE) ? Boolean.FALSE : Boolean.TRUE);
                return Result.SUCCESS;
            }
        };
        
        renderContext.associateMouseRegion(div, region);
    }
    
    public Result beanDeletedCleanup(DesignBean bean) {
        // If parent componet is a tree node, it has an image facet, and it's
        // image facet's icon is set to the folder icon, and this is its only
        // child, change icon to document icon
        DesignBean parent = bean.getBeanParent();
        if (parent.getInstance().getClass().equals(TreeNode.class) && parent.getChildBeanCount() == 2) {
            DesignBean imageFacet = ((FacesDesignBean)parent).getFacet(IMAGE_FACET);
            if (imageFacet != null && imageFacet.getInstance().getClass().equals(ImageComponent.class)) {
                DesignProperty iconProperty = imageFacet.getProperty("icon"); //NOI18N
                if (iconProperty.getValue() != null && iconProperty.getValue().equals(TREE_FOLDER_ICON))
                    iconProperty.setValue(TREE_DOCUMENT_ICON);
            }
        }
        return Result.SUCCESS;
    }
    
}
