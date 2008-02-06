<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %> 
<%@taglib uri="http://www.sun.com/webui/webuijsf" prefix="webuijsf" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %> 
<f:view>
  <webuijsf:page>
    <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
    <webuijsf:head title="#{msgs.login_example3}">
        <webuijsf:script>
            function init() {
                var domNode = document.getElementById("form2:login2");
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
                var div1 = document.getElementById("div1");
                div1.style.visibility = "visible";        
            }
        </webuijsf:script>
    </webuijsf:head>
    <webuijsf:body onLoad="init();">
    <webuijsf:form id="form2">
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
                <webuijsf:hyperlink url="login3.jsp" text="#{msgs.login_example3}"/>
            </webuijsf:breadcrumbs>
       <br/><br/>
       
        <webuijsf:login id="login2" value="#{LoginBean.value}" serviceName="AppLogin2" />
        <br/><br/>
        <div id="div1" style="visibility:hidden">
            
            <webuijsf:staticText id="st1" text="#{msgs.login_page_reload}"/>
            <p>
            <webuijsf:staticText id="st2" text="#{msgs.login_msg1}"/>
            </p> 
            <p>
            <webuijsf:staticText id="st3" text="#{msgs.login_msg2}"/>
            </p>
            <pre>
            &lt;glassfish_install_dir&gt;/domains/your_domain/config/login.conf
            </pre>
            <br/>
            <pre>
            AppLogin1 {
                com.sun.webui.jsf.example.login.TestLoginModule optional;
            };
            
            AppLogin2 {
                com.sun.webui.jsf.example.login.TestLoginModule2 requisite;
                com.sun.webui.jsf.example.login.TestLoginModule3 requisite;
            };
            </pre>
            <p>
                <webuijsf:staticText id="st4" text="#{msgs.login_msg3}"/>
            </p>
            <pre>-Djava.security.auth.login.config=/path/to/file/login.conf</pre>
            
        </div>      
        </webuijsf:form>
    </webuijsf:body>
  </webuijsf:page>
</f:view>
