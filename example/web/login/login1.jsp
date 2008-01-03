<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %> 
<%@taglib uri="http://www.sun.com/webui/webuijsf" prefix="webuijsf" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %> 
<f:view>
  <webuijsf:page>
    <webuijsf:head title="Login" debug="true"/>
    <webuijsf:body>
    <webuijsf:form id="form1">
      <webuijsf:masthead id="Masthead" productImageURL="/images/webconsole.png"
            productImageDescription="Java Web Console" userInfo="test_user"
            serverInfo="test_server" />
            
            <p><b> If authentication fails this page needs to be reloaded.</b> </p>
       <br/><br/>
       <div align="center">
        <webuijsf:login id="login1"  redirectURL="/example/faces/login/result.jsp" 
            serviceName="AppLogin1" value="#{LoginBean.value}" />
            
        <br/><br/>
        
          
            
        </webuijsf:form>
    </webuijsf:body>
  </webuijsf:page>
</f:view>