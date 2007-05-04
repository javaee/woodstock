<jsp:root version="2.0" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
  <jsp:directive.page contentType="text/html" />
  <f:view>
    <!--
      The contents of this file are subject to the terms
      of the Common Development and Distribution License
      (the License).  You may not use this file except in
      compliance with the License.
      
      You can obtain a copy of the license at
      https://woodstock.dev.java.net/public/CDDLv1.0.html.
      See the License for the specific language governing
      permissions and limitations under the License.
      
      When distributing Covered Code, include this CDDL
      Header Notice in each file and include the License file
      at https://woodstock.dev.java.net/public/CDDLv1.0.html.
      If applicable, add the following below the CDDL Header,
      with the fields enclosed by brackets [] replaced by
      you own identifying information:
      "Portions Copyrighted [year] [name of copyright owner]"
      
      Copyright 2007 Sun Microsystems, Inc. All rights reserved.
    -->
    <webuijsf:page >
      <webuijsf:html>
        <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
        <webuijsf:head title="#{msgs.cbrb_clientsideRbTitle}">
	  <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
        </webuijsf:head>
        <webuijsf:body>
            <webuijsf:form id="form1">

              <!-- Masthead -->
              <webuijsf:masthead id="Masthead" productImageURL="/images/example_primary_masthead.png"
                productImageDescription="#{msgs.mastheadAltText}" 
                userInfo="test_user"
                serverInfo="test_server" />     
                         
              <!-- Bread Crumb Component -->
              <webuijsf:breadcrumbs id="breadcrumbs">
                <webuijsf:hyperlink actionExpression="#{CheckboxRadiobuttonBean.showExampleIndex}" text="#{msgs.exampleTitle}"
                  onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
                <webuijsf:hyperlink actionExpression="#{CheckboxRadiobuttonBean.showCbRbIndex}" text="#{msgs.cbrb_indexTitle}"
                  onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
                <webuijsf:hyperlink text="#{msgs.cbrb_clientsideRbTitle}"/>
              </webuijsf:breadcrumbs>
                       
              <!-- Page Title -->
              <webuijsf:contentPageTitle title="#{msgs.cbrb_clientsideRbTitle}" />
              
                           
              <webuijsf:script>
                  
                  
                    // Toggle enable/disable state of radiobutton client-side.
                    function toggleRadiobuttonState() {
                        var domNode = document.getElementById("form1:rb1");
                        domNode.setProps({disabled: !domNode.getProps().disabled});
                        return false;
                    }
                    
                    // Toggle Check/Uncheck state of radiobutton client-side
                    function toggleRadiobuttonCheckState() {
                        var domNode = document.getElementById("form1:rb2");
                        if (domNode.getProps().checked == true) {
                               domNode.setProps({checked: false});
                           } else {
                               domNode.setProps({checked: true});
                           }                            
                           return false;
                    }
                    
                    // Toggle label change
                    function toggleRadiobuttonLabelChange(label) {
                        var domNode = document.getElementById("form1:rb3");                    
                        domNode.setProps({                                                                
                               label: {value: label}
                           });
                           return false;                    
                    }
                    
                    // Toggle Enable/Disable state of Radio Button Group 
                    function toggleRadioButtonGroupState() {
                        rb4 = document.getElementById("form1:rbServer");                    
                        rb5 = document.getElementById("form1:rbVolume");
                        rb6 = document.getElementById("form1:rbPool");
                        
                        rb4.setProps({disabled: !rb4.getProps().disabled});
                        rb5.setProps({disabled: !rb5.getProps().disabled});
                        rb6.setProps({disabled: !rb6.getProps().disabled});
                        return false;                        
                    }                              
                  
              </webuijsf:script>          
              
              <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">             
                  
                    <br /><br />                        
                    <webuijsf:radioButton id="rb1" imageURL="/images/tree_server.gif" label="#{msgs.cbrb_radioButton1}" toolTip="#{msgs.cbrb_radioButton1}" />                            
                    <br /><br />                        
                    
                    <webuijsf:button id="btn1" text="#{msgs.cbrb_testCase_toggleRadiobuttonState}" onClick="return toggleRadiobuttonState();" />
                    <br /><br />                            
                    
                    <webuijsf:radioButton id="rb2" imageURL="/images/volumegroup_tree.gif" label="#{msgs.cbrb_radioButton2}" toolTip="#{msgs.cbrb_radioButton2}" />                             
                    <br /><br />                                                         
                    <webuijsf:button id="btn2" text="#{msgs.cbrb_testCase_toggleRadiobuttonCheckState}" onClick="return toggleRadiobuttonCheckState();" /> 
                    <br /><br />
                    
                    <webuijsf:radioButton id="rb3" imageURL="/images/pool_tree.gif" label="#{msgs.cbrb_radioButton3}" toolTip="#{msgs.cbrb_radioButton3}" />   
                    <br /><br/>
                    <webuijsf:button id="btn3" text="#{msgs.cbrb_testCase_RadiobuttonLabelChange}" onClick="return toggleRadiobuttonLabelChange('#{msgs.cbrb_radioButton2}');" /> 
                    <webuijsf:button id="btn4" text="#{msgs.cbrb_testCase_RadiobuttonLabelUnChange}" onClick="return toggleRadiobuttonLabelChange('#{msgs.cbrb_radioButton3}');" /> 
                    <br /><br />
                   
                    <!-- Radio Button Group -->
                    <webuijsf:label id="RadioButtonLabel" text="#{msgs.cbrb_radiobuttonGroupLabel}"/>
                    
                            <div><webuijsf:radioButton id="rbServer" name="rbgrp"                                                                         
                                        imageURL="/images/tree_server.gif"
                                        label="#{msgs.cbrb_radioButton1}"/>
                            </div>                        
                            <div> <webuijsf:radioButton id="rbVolume" name="rbgrp" 
                                        selected="true"
                                        imageURL="/images/volumegroup_tree.gif"
                                        label="#{msgs.cbrb_radioButton2}"/> 
                            </div>
                            <div> <webuijsf:radioButton id="rbPool" name="rbgrp"                                         
                                        imageURL="/images/pool_tree.gif"
                                        label="#{msgs.cbrb_radioButton3}"/>
                            </div>
                
                    <br/><br/>                
                    <webuijsf:button id="btn5" text="#{msgs.cbrb_testCase_toggleRadioButtonGroupState}" onClick="return toggleRadioButtonGroupState();" /> 
                    <br /><br />
                   
                                           
              </webuijsf:markup>
            </webuijsf:form>
          </webuijsf:body> 
      </webuijsf:html>  
    </webuijsf:page>
  </f:view>
</jsp:root>                       
