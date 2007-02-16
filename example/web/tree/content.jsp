<jsp:root version="2.0" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
<jsp:directive.page contentType="text/html"/>
<f:view>    
    <!--
      The contents of this file are subject to the terms
      of the Common Development and Distribution License
      (the License).  You may not use this file except in
      compliance with the License.
      
      You can obtain a copy of the license at
      https://woodstock.dev.java.net/public/CDDLv1.0.html.
      See the License for the specific language governing
      permissions and limitations under the License.
      
      When distributing Covered Code, include this CDDL
      Header Notice in each file and include the License file
      at https://woodstock.dev.java.net/public/CDDLv1.0.html.
      If applicable, add the following below the CDDL Header,
      with the fields enclosed by brackets [] replaced by
      you own identifying information:
      "Portions Copyrighted [year] [name of copyright owner]"
      
      Copyright 2007 Sun Microsystems, Inc. All rights reserved.
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
