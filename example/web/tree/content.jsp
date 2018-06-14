<jsp:root version="2.0" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
<jsp:directive.page contentType="text/html"/>
<f:view>    
    <!--
      DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

	  Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.

	  The contents of this file are subject to the terms of either the GNU
	  General Public License Version 2 only ("GPL") or the Common Development
	  and Distribution License("CDDL") (collectively, the "License").  You
	  may not use this file except in compliance with the License.  You can
	  obtain a copy of the License at
	  https://oss.oracle.com/licenses/CDDL+GPL-1.1
	  or LICENSE.txt.  See the License for the specific
	  language governing permissions and limitations under the License.

	  When distributing the software, include this License Header Notice in each
	  file and include the License file at LICENSE.txt.

	  GPL Classpath Exception:
	  Oracle designates this particular file as subject to the "Classpath"
	  exception as provided by Oracle in the GPL Version 2 section of the License
	  file that accompanied this code.

	  Modifications:
	  If applicable, add the following below the License Header, with the fields
	  enclosed by brackets [] replaced by your own identifying information:
	  "Portions Copyright [year] [name of copyright owner]"

	  Contributor(s):
	  If you wish your version of this file to be governed by only the CDDL or
	  only the GPL Version 2, indicate your decision by adding "[Contributor]
	  elects to include this software in this distribution under the [CDDL or GPL
	  Version 2] license."  If you don't indicate a single choice of license, a
	  recipient has the option to distribute your version of this file under
	  either the CDDL, the GPL Version 2 or to extend the choice of license to
	  its licensees as provided above.  However, if you add GPL Version 2 code
	  and therefore, elected the GPL Version 2 license, then the option applies
	  only if the new code is made subject to such option by the copyright
	  holder.
    -->
  <webuijsf:page>
    <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
    <webuijsf:html id="html">  
      <webuijsf:head id="head" title="#{msgs.tree_navTreeTitle}">
	<webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
        <script type="text/javascript">
          // Yoke to the treenode referenced by the specified client ID
          function YokeToNode(nodeID) {
            var tree = window.parent.navtreeFrame.document.getElementById('form:navTree');
            tree.selectTreeNode(nodeID);

/*
/// Due to a bug, programmatically expanding/collapsing nodes does not work.
            // Ensure all ancestry nodes are expanded
            node = window.parent.navtreeFrame.document.getElementById(nodeID);
	    var parentNode = tree.getParentTreeNode(node);
	    while (parentNode != null) {
                if (! tree.treeNodeIsExpanded(parentNode))
                    tree.expandCollapse(parentNode, null);
	        parentNode = tree.getParentTreeNode(parentNode);
            }
*/
          }

          // Unhighlight the tree's selected node
          function ClearHighlight() {
            var tree = window.parent.navtreeFrame.document.getElementById('form:navTree');
	    node = tree.getSelectedTreeNode('form:navTree');
	    tree.clearHighlight(node);
          }
        </script>
      </webuijsf:head>
      <webuijsf:body id="body">
        <webuijsf:form id="form">                             

          <webuijsf:breadcrumbs id="breadcrumbs"
            rendered="#{NavTreeBean.breadcrumbsRendered}"
            pages="#{NavTreeBean.pageList}" />

          <webuijsf:contentPageTitle id="contentPageTitle"
            title="#{msgs.tree_contentsTitle}" >

            <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">

              <br /><br /><br /><br />
              <p align="center"><strong>
                <webuijsf:staticText id="nodeSelected" 
                   text="#{NavTreeBean.nodeClicked}" />
                <webuijsf:staticText id="nodeClicked" 
                   text="#{msgs.tree_nodeClicked}" />
              </strong></p>

              <p align="center">
                <webuijsf:hyperlink id="treeIndexLink"
                    target="_top"
                    text="#{msgs.tree_breadcrumbMouseOver}"
                    toolTip="#{msgs.tree_breadcrumbMouseOver}"
                    actionExpression="#{DynamicTreeBean.showTreeIndex}"
                    onMouseOver="javascript:window.status=
                      '#{msgs.tree_breadcrumbMouseOver}'; return true;"
                    onMouseOut="javascript: window.status=''; return true" />
              </p>
            
            </webuijsf:markup>
          </webuijsf:contentPageTitle>
        </webuijsf:form>
      </webuijsf:body>
    </webuijsf:html>  
  </webuijsf:page>
</f:view>

</jsp:root>
