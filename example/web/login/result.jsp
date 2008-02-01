<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %> 
<%@taglib uri="http://www.sun.com/webui/webuijsf" prefix="webuijsf" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %> 
<f:view>
  <webuijsf:page>
    <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
    <webuijsf:head title="#{msgs.login_success_page}" />
    <webuijsf:body>
    <webuijsf:form id="form1">
      <webuijsf:masthead id="masthead"
              productImageURL="/images/example_primary_masthead.png"
              productImageHeight="40"
              productImageWidth="188"
              userInfo="test_user" 
              serverInfo="test_server"
              productImageDescription="#{msgs.mastheadAltText}" />
      
       <br/><br/>
       
       <webuijsf:breadcrumbs id="breadcrumbs">
                <webuijsf:hyperlink url="../index.jsp" text="#{msgs.index_title}"/>
                <webuijsf:hyperlink url="index.jsp" text="#{msgs.login_indexTitle}"/>
                <webuijsf:hyperlink url="result.jsp" text="#{msgs.login_success_page}"/>
            </webuijsf:breadcrumbs>
        
        <br/><br/>
        <webuijsf:staticText id="st1" text="#{msgs.login_success_summary}" />
        
        </webuijsf:form>
    </webuijsf:body>
  </webuijsf:page>
</f:view>