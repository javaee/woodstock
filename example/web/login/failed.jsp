<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %> 
<%@taglib uri="http://www.sun.com/webui/webuijsf" prefix="webuijsf" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %> 
<f:view>
  <webuijsf:page>
    <webuijsf:head title="Failed Authentication Page." />
    <webuijsf:body>
    <webuijsf:form id="form1">
      <webuijsf:masthead id="Masthead" productImageURL="/images/webconsole.png"
            productImageDescription="Java Web Console" userInfo="test_user"
            serverInfo="test_server" />
      
       <br/><br/>
       
        Authentication Failed! You do not have access to the application.
        
        </webuijsf:form>
    </webuijsf:body>
  </webuijsf:page>
</f:view>