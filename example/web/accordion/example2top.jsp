<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %> 
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@taglib uri="http://www.sun.com/webui/webuijsf" prefix="webuijsf" %>
<f:view>
<webuijsf:page>
    <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
    <webuijsf:html>
    <webuijsf:head title="Accordion Example 2"/>
    <webuijsf:body>
      <webuijsf:form id="accordionExample">
         <webuijsf:masthead id="masthead"
             productImageURL="/images/example_primary_masthead.png"
             productImageHeight="40"
             productImageWidth="188"
             userInfo="test_user" 
             serverInfo="test_server"
             productImageDescription="#{msgs.mastheadAltText}" />
                  <webuijsf:breadcrumbs id="breadcrumbs">
        <webuijsf:hyperlink url="../index.jsp" text="Example Index" target="_top"/>
        <webuijsf:hyperlink url="../accordion/index.jsp" text="Accordion Examples" target="_top"/>
        <webuijsf:hyperlink url="../accordion/example2top.jsp" text="Example 2"/>
               
       </webuijsf:breadcrumbs>
      
      </webuijsf:form>
    </webuijsf:body>
    </webuijsf:html>
  </webuijsf:page>
</f:view>