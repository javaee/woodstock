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
            dojo.subscribe(webui.suntheme.widget.login.event.result.failureTopic, 
                this, handleFailureEvent);
        }
        
        this.handleEvent = function(id) {

            var loc = document.location;
            var newURL = loc.protocol + "//" + loc.host;
            newURL = newURL + "/example/faces/login/result.jsp";
            document.location.href = newURL;
        }
        
        this.handleFailureEvent = function(id) {

            var loc = document.location;
            var newURL = loc.protocol + "//" + loc.host;
            newURL = newURL + "/example/faces/login/failed.jsp";
            document.location.href = newURL;
        }

    </webuijsf:script>
    
    <webuijsf:body>
    <webuijsf:form id="form1">
      <webuijsf:masthead id="Masthead" productImageURL="/images/webconsole.png"
            productImageDescription="Java Web Console" userInfo="test_user"
            serverInfo="test_server" />
      
       <br/><br/>
       
        <webuijsf:login id="login3" value="#{LoginBean.value}" serviceName="AppLogin1"/>
            
        </webuijsf:form>
    </webuijsf:body>
  </webuijsf:page>
</f:view>