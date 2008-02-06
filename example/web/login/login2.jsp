<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %> 
<%@taglib uri="http://www.sun.com/webui/webuijsf" prefix="webuijsf" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %> 
<f:view>
  <webuijsf:page>
    <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs"/>
    <webuijsf:head title="#{msgs.login_example2}">
        <webuijsf:script>       
            function init() {
                var domNode = document.getElementById("form1:login3");
                if (domNode == null || domNode.event == null) { 
                    return setTimeout('init();', 10);
                }
                domNode.subscribe(domNode.event.result.successTopic, this, handleEvent);
                domNode.subscribe(domNode.event.result.failureTopic, this, handleFailureEvent);
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
    </webuijsf:head>
    <webuijsf:body onLoad="init();">
    <webuijsf:form id="form1">
      <webuijsf:masthead id="masthead"
              productImageURL="/images/example_primary_masthead.png"
              productImageHeight="40"
              productImageWidth="188"
              userInfo="test_user" 
              serverInfo="test_server"
              productImageDescription="#{msgs.mastheadAltText}" />
      <webuijsf:breadcrumbs id="breadcrumbs">
                <webuijsf:hyperlink url="../index.jsp" text="#{msgs.index_title}"/>
                <webuijsf:hyperlink url="index.jsp" text="#{msgs.login_indexTitle}"/>
                <webuijsf:hyperlink url="login2.jsp" text="#{msgs.login_example2}"/>
            </webuijsf:breadcrumbs>
       <br/><br/>
       
        <webuijsf:login id="login3" value="#{LoginBean.value}" serviceName="AppLogin1"/>
            
        </webuijsf:form>
    </webuijsf:body>
  </webuijsf:page>
</f:view>
