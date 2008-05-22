<?xml version="1.0" encoding="UTF-8"?>

<jsp:root version="2.0"
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:f="http://java.sun.com/jsf/core"
    xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
  <jsp:directive.page contentType="text/html" />

  <f:view>
    <webuijsf:page id="thepage">
        <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
      <webuijsf:html>
	<webuijsf:head id="head" title="#{msgs.tree2_example2}" debug="true" webuiAll="true" />

	<webuijsf:body>
	  <webuijsf:form id="myForm">
	    <webuijsf:masthead id="masthead"
              productImageURL="/images/example_primary_masthead.png"
              productImageHeight="40"
              productImageWidth="188"
              userInfo="test_user" 
              serverInfo="test_server"
              productImageDescription="#{msgs.mastheadAltText}" />
                 
            <webuijsf:breadcrumbs id="breadcrumbs">
              <webuijsf:hyperlink url="../index.jsp" text="#{msgs.index_title}"/>
              <webuijsf:hyperlink url="../tree2/index.jsp" text="#{msgs.tree2_index}"/>
              <webuijsf:hyperlink url="../tree2/example1.jsp" text="#{msgs.tree2_example2}"/>
            </webuijsf:breadcrumbs>
	    
	    <!-- PageTitle... -->
	    <webuijsf:contentPageTitle id="pageTitle" title="#{msgs.index_title}" 
                helpText="#{msgs.tree2_example2_help}" />

            <table width="100%">
            <tr valign="top"><td>
	    <!-- Tree Example 1... -->
	    <webuijsf:tree2 style="width:30em;" id="TreeExample2"
                label="Dynamic Tree" url="#">
		<f:facet name="image">
		    <webuijsf:image id="image" icon="TREE_FOLDER_ALARM_CRITICAL" />
		</f:facet>
		<webuijsf:treeNode2 id="node0" expanded="true" label="Building 0">
		    <f:facet name="image">
			<webuijsf:image id="image" icon="TREE_FOLDER_ALARM_CRITICAL" />
		    </f:facet>
		    <webuijsf:treeNode2 id="node1" expanded="true" label="Building 1">
			<f:facet name="image">
			    <webuijsf:image id="image" icon="TREE_FOLDER_ALARM_CRITICAL" />
			</f:facet>
			<webuijsf:treeNode2 id="node1_1" label="Kenga">
			    <f:facet name="image">
				<webuijsf:image id="image" icon="TREE_SERVER" />
			    </f:facet>
			</webuijsf:treeNode2>
			<webuijsf:treeNode2 id="node1_2" label="Node with a URL" url="../index.jsp">
			    <f:facet name="image">
				<webuijsf:image id="image" icon="TREE_SERVER_CRITICAL" />
			    </f:facet>
			</webuijsf:treeNode2>
		    </webuijsf:treeNode2>
		    <webuijsf:treeNode2 id="node2" imageURL="/images/google.jpg"
                     label="Building 10" url="#">
                         <webuijsf:treeNode2 id="node1_2" label="Skippy" url="#">
			    <f:facet name="image">
				<webuijsf:image id="image" icon="TREE_SERVER" />
			    </f:facet>
			</webuijsf:treeNode2>
		    </webuijsf:treeNode2>
		</webuijsf:treeNode2>
		<webuijsf:treeNode2 id="node3" expanded="true" label="Building 2" url="#">
		    <f:facet name="image">
			<webuijsf:image id="image" icon="TREE_FOLDER" />
		    </f:facet>
		    <webuijsf:treeNode2 id="node3_1" label="Arizona" url="#">
			<f:facet name="image">
			    <webuijsf:image id="image" icon="TREE_SERVER" />
			</f:facet>
		    </webuijsf:treeNode2>
		</webuijsf:treeNode2>
		<webuijsf:treeNode2 id="node4" label="Building 3" url="#">
		    <f:facet name="image">
			<webuijsf:image id="image" icon="TREE_FOLDER" />
		    </f:facet>
		    <webuijsf:treeNode2 id="node4_1" label="Tundra" url="#">
			<f:facet name="image">
			    <webuijsf:image id="image" icon="TREE_SERVER" />
			</f:facet>
		    </webuijsf:treeNode2>
		</webuijsf:treeNode2>
		<webuijsf:treeNode2 id="node5" expanded="true" label="Building 4" url="#">
		    <f:facet name="image">
			<webuijsf:image id="image" icon="TREE_FOLDER_ALARM_MAJOR" />
		    </f:facet>
		    <webuijsf:treeNode2 id="node5_1" label="Neptune" url="#">
			<f:facet name="image">
			    <webuijsf:image id="image" icon="TREE_STORAGE_MAJOR" />
			</f:facet>
		    </webuijsf:treeNode2>
		    <webuijsf:treeNode2 id="node5_2" label="Zeus" url="#">
			<f:facet name="image">
			    <webuijsf:image id="image" icon="TREE_STORAGE" />
			</f:facet>
		    </webuijsf:treeNode2>
		</webuijsf:treeNode2>
	    </webuijsf:tree2>
	    <!-- End Tree Example 1 -->
            </td>
        </tr>
        </table>

	  </webuijsf:form>
	</webuijsf:body>
      </webuijsf:html>
    </webuijsf:page>
  </f:view>
</jsp:root>