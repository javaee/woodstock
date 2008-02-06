<jsp:root version="2.0" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html"
xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
 <jsp:directive.page contentType="text/html"/>
 <f:view>    
  <webuijsf:page>
    <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
    <webuijsf:html id="html">  
      <webuijsf:head id="head" title="#{msgs.login_example4}">                        
        <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
          <webuijsf:script>
            function init() {
                var domNode = document.getElementById("form1:secondary");
                if (domNode == null || domNode.event == null) { 
                    return setTimeout('init();', 10);
                }
                domNode.subscribe(domNode.event.result.successTopic, this, handleSuccessEvent);
                domNode.subscribe(domNode.event.result.failureTopic, this, handleFailureEvent);
            }
            this.handleSuccessEvent = function(id) {
                // show the common task page after successful authentication
                var cts = document.getElementById("form1:ctp1");
                cts.style.display = "block";
            }
            this.handleFailureEvent = function(id) {
                // show the retry button
                var loginDiv = document.getElementById("form1:secondary");
                loginDiv.style.display = "none";
                var btn = document.getElementById("form1:retry");
                btn.style.display = "block";
            }
        </webuijsf:script>
      </webuijsf:head>
      <webuijsf:body id="body" onLoad="init();" styleClass="#{themeStyles.CTS_BACKGROUND}">
      <webuijsf:form id="form1">  
         <webuijsf:login id="secondary" value="#{LoginBean.value}" serviceName="AppLogin1" />
         <webuijsf:button id="retry" text="Authentication Failed, Retry" toolTip="Try Authenticating Again"
              style="display:none"
              onClick="var login = document.getElementById('form1:login1'); login.style.display='block'; login.authenticate();" />
                             
       <webuijsf:commonTasksSection style="display:none" id="ctp1">
         <webuijsf:commonTasksGroup id="taskGroup1" title="#{msgs.commontask_group1}">
            <webuijsf:commonTask id="task1" text="#{msgs.commontask_task1}"  
                toolTip="#{msgs.commontask_task1}" actionExpression="#{commonTask.taskAction}"
                target="_blank" icon="CTS_OVERVIEW">
            </webuijsf:commonTask>
            <webuijsf:commonTask id="task2" text="#{msgs.commontask_task2}" toolTip="#{msgs.commontask_task2}"
                onClick="popup();return false;" infoTitle="#{msgs.commontask_task2infotitle}"
                infoText="#{msgs.commontask_task2infotext}" />
        </webuijsf:commonTasksGroup>

        <webuijsf:commonTasksGroup id="taskGroup2"  title="#{msgs.commontask_group2}">
            <webuijsf:commonTask id="task3" text="#{msgs.commontask_task3}"  toolTip="#{msgs.commontask_task3}"
                 onClick="popup();return false; "  infoLinkUrl="/faces/commontask/sample.jsp" 
                  infoLinkText="#{msgs.commontask_infolinktext}" infoTitle="#{msgs.commontask_task3infotitle}" 
                  infoText="#{msgs.commontask_task3infotext}"/>
             <webuijsf:commonTask id="task4" text="#{msgs.commontask_task4}" onClick="popup();return false;"
                 toolTip="#{msgs.commontask_task4}"/>
              <webuijsf:commonTask id="task5" text="#{msgs.commontask_task5}" 
                  onClick="popup();return false;" infoTitle="#{msgs.commontask_task5infotitle}"
                  toolTip="#{msgs.commontask_task5}" infoText="#{msgs.commontask_task5infotext}"/>
        </webuijsf:commonTasksGroup>
        
              <webuijsf:commonTasksGroup id="taskGroup3" title="#{msgs.commontask_group3}">

                  <webuijsf:commonTask id="task6" text="#{msgs.commontask_task6}"  
                      toolTip="#{msgs.commontask_task6}" onClick="popup();return false;" />
                  <webuijsf:commonTask id="task7" text="#{msgs.commontask_task7}" 
                      actionExpression="#{commonTask.taskAction}" target="_blank" infoLinkUrl="/faces/commontask/sample.jsp" 
                      infoLinkText="#{msgs.commontask_infolinktext}" infoTitle="#{msgs.commontask_task7infotitle}" 
                      toolTip="#{msgs.commontask_task7}" infoText="#{msgs.commontask_task7infotext}"/>
              </webuijsf:commonTasksGroup>

              <webuijsf:commonTasksGroup id="taskGroup4" title="#{msgs.commontask_group4}">
                  <webuijsf:commonTask id="task8" text="#{msgs.commontask_task8}" toolTip="#{msgs.commontask_task8}"
                      actionExpression="#{commonTask.taskAction}" target="_blank"/>
                  <webuijsf:commonTask id="task9" text="#{msgs.commontask_task9}" toolTip="#{msgs.commontask_task9}"
                      onClick="popup();return false;" infoLinkUrl="/faces/commontask/sample.jsp" 
                      infoLinkText="#{msgs.commontask_infolinktext}" infoTitle="#{msgs.commontask_task9infotitle}"
                      infoText="#{msgs.commontask_task9infotext}"/>
                   <webuijsf:commonTask id="task10" text="#{msgs.commontask_task10}" onClick="popup();return false;"
                      toolTip="#{msgs.commontask_task10}" infoLinkUrl="/faces/commontask/sample.jsp" infoLinkText="#{msgs.commontask_infolinktext}" 
                      infoTitle="#{msgs.commontask_task10infotitle}" infoText="#{msgs.commontask_task10infotext}"/>  
                   </webuijsf:commonTasksGroup>

              <webuijsf:commonTasksGroup id="taskGroup5" title="#{msgs.commontask_group5}">
                   <webuijsf:commonTask id="index" text="#{msgs.commontask_index}" actionExpression="#{commonTask.showExampleIndex}"
                      infoTitle="#{msgs.commontask_indexinfotitle}" toolTip="#{msgs.commontask_index}"
                      infoText="#{msgs.commontask_indexinfotext}"/>
              </webuijsf:commonTasksGroup>
         </webuijsf:commonTasksSection>
       </webuijsf:form>
      </webuijsf:body>
          <webuijsf:script type="text/javascript">
        function popup() {
            var popupWin = window.open('/example/faces/commontask/sample.jsp','Test Page','scrollbars,resizable,width=650,height=500,top='+((screen.height - (screen.height/1.618)) - (500/2))+',left='+((screen.width-650)/2) ); 
            popupWin.focus();
        }
    </webuijsf:script>
    </webuijsf:html>  
  </webuijsf:page>
 </f:view>
</jsp:root>
