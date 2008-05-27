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
	<webuijsf:head id="head" title="#{msgs.tree2_example4}"
                debug="true" webuiAll="true" webuiJsfx="true"/>

    <webuijsf:script>
            function init() {
                var domNode = document.getElementById("myForm:tree");
                if (domNode == null || domNode.event == null) { 
                    return setTimeout('init();', 10);
                }
                domNode.subscribe(woodstock4_3.widget.tree.event.nodeSelection.beginTopic, handleSelectedEvent); 
            }
            this.handleSelectedEvent = function(id) {
                var stxt2 = woodstock4_3.widget.common.getWidget("myForm:text1");
                stxt2.refresh();
            }
           
    </webuijsf:script>
    
	<webuijsf:body onLoad="init();">
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
              <webuijsf:hyperlink url="../tree2/example3.jsp" text="#{msgs.tree2_example4}"/>
            </webuijsf:breadcrumbs>
	    
	    <!-- PageTitle... -->
	    <webuijsf:contentPageTitle id="pageTitle" title="#{msgs.index_title}" 
                helpText="#{msgs.tree2_example4_help}" />
	    
            <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
            
            
            <webuijsf:tree2 id="tree" binding="#{TestTreeBean.newTree}">
            </webuijsf:tree2>	
            
	    <br/>
            <webuijsf:label text="#{msgs.tree2_example_selected_nodes}" labelLevel="2"/>
	    <webuijsf:staticText id="text1" binding="#{TestTreeBean.statictext}" />
            </webuijsf:markup>

	  </webuijsf:form>
	</webuijsf:body>
      </webuijsf:html>
    </webuijsf:page>
  </f:view>
</jsp:root>
