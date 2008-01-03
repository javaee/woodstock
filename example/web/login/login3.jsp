<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %> 
<%@taglib uri="http://www.sun.com/webui/webuijsf" prefix="webuijsf" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %> 
<f:view>
  <webuijsf:page>
    <webuijsf:head title="Test Login Page" debug="true"/>
    
    <webuijsf:script>
        window.onload=init;
        
        function init() {
            dojo.subscribe(webui.suntheme.widget.login.event.result.successTopic, 
                this, handleEvent);
        }
        
        this.handleEvent = function(id) {

            var loc = document.location;
            var newURL = loc.protocol + "//" + loc.host;
            newURL = newURL + "/example/faces/login/result.jsp";
            // dojo.debug("forwarding to...: ", newURL);
            document.location.href = newURL;
        }


    </webuijsf:script>
    
    <webuijsf:body>
    <webuijsf:form id="form2">
      <webuijsf:masthead id="Masthead" productImageURL="/images/webconsole.png"
            productImageDescription="Java Web Console" userInfo="test_user"
            serverInfo="test_server" />
      
       <br/><br/>
       
        <webuijsf:login id="login2" value="#{LoginBean.value}" serviceName="AppLogin2" />
            
        </webuijsf:form>
    </webuijsf:body>
  </webuijsf:page>
</f:view>