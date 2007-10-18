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
        <webuijsf:head title="#{msgs.cbrb_clientsideCbRbTitle}">
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
                <webuijsf:hyperlink text="#{msgs.cbrb_clientsideCbRbTitle}"/>
              </webuijsf:breadcrumbs>
                       
              <!-- Page Title -->
              <webuijsf:contentPageTitle title="#{msgs.cbrb_clientsideCbRbTitle}" />

              <!-- Workaround for issue #854.
                The default "auto submit" HTML behavior for the return key
                is to run the onClick handler for the 1st submit input element.
                For this page, that means that entering the return key
                for any element which does not have an onclick handler will
                will cause the Disable button's onClick handler to be called,
                which results in the 1st group of checkboxes being disabled.
                So to workaround the problem, we create an invisible button 
                with a handler that stops the event chaining.
              -->
              <webuijsf:button primary="false" id="dummy" text="" 
                visible="false" onClick="return false;"/>
              
                           
              <webuijsf:script>                  
                  
                     function updateGroup(props, id) {
                            var domNode = document.getElementById(id);
                            domNode.setProps(props);             
                            return false;
                     }                                
                     
                     function changeLevel(levelid, id) {
                            var domNode = document.getElementById(id);                                       
                            if (levelid == '1') {
                                domNode.setProps({
                                        label: {level: 1}
                                });
                            } else {
                                domNode.setProps({
                                        label: {level: 3}
                                });                
                            }
                            return false;                                
                     }      
                    
                     function refreshGroup(id1, id2) {                                
                            var domNode = document.getElementById(id1); // Get the group element to refresh.
                            domNode.refresh(id2); // Update text field value and refresh.                                    
                     }
                  
              </webuijsf:script>          
              
              <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">                              
                    <br /><br />   
               <h2>              
                <webuijsf:staticText id="checkboxText" text="#{msgs.cb_title}" />                
              </h2>
                    <webuijsf:checkboxGroup id="cbGrp1" items="#{rbcbGroupBean.array}" 
                            selected="#{rbcbGroupBean.cb1selected}"
                            toolTip="CheckboxGroup" />                                                                 
                    <br /><br />
                    <webuijsf:button primary="false" id="disableBtn" text="#{msgs.cbrb_disable}" onClick="return updateGroup({disabled: true}, 'form1:cbGrp1');"/>                                    
                    <webuijsf:button primary="false" id="enableBtn" text="#{msgs.cbrb_enable}" onClick="return updateGroup({disabled: false}, 'form1:cbGrp1');"/>                                                        
                    <br /><br />                 
                    
                    <webuijsf:checkboxGroup id="cbGrp2" items="#{rbcbGroupBean.array}" 
                           selected="#{rbcbGroupBean.cb1selected}"
                           label="#{rbcbGroupBean.label}" 
                           toolTip="#{rbcbGroupBean.label}" />                                                                                                            
                    <br /><br />    
                    <webuijsf:textField id="dynamicLabel1" label="#{msgs.cbrb_labelTitle}" onKeyUp="refreshGroup('form1:cbGrp2', 'form1:dynamicLabel1')" text="#{rbcbGroupBean.label}"/>       
                    <br /><br />   
                    
               <h2>              
                <webuijsf:staticText id="radioButtonText" text="#{msgs.rb_title}" />                
              </h2>
                    <webuijsf:radioButtonGroup id="rbGrp1" items="#{rbcbGroupBean.array}" 
                           selected="#{rbcbGroupBean.rb1selected}"                                            
                           toolTip="#{msgs.cbrb_radiobuttonGroupLabel}">  
                        <f:facet name="label">
                                 <webuijsf:label text="#{msgs.cbrb_radiobuttonGroupLabel}" for="rbGrp1"/> 
                        </f:facet>
                    </webuijsf:radioButtonGroup>
                    <br /><br />    
                    <webuijsf:button primary="false" id="levelBtn1" text="#{msgs.cbrb_level1}" onClick="return changeLevel('1', 'form1:rbGrp1');"/>
                    <webuijsf:button primary="false" id="levelBtn3" text="#{msgs.cbrb_level3}" onClick="return changeLevel('3', 'form1:rbGrp1');"/>                     
                    <br /><br />    
                    
                    <webuijsf:radioButtonGroup id="rbGrp2" items="#{rbcbGroupBean.array}" 
                           selected="#{rbcbGroupBean.rb1selected}"
                           label="#{rbcbGroupBean.label}" 
                           toolTip="#{rbcbGroupBean.label}" />                                                                                                            
                    <br /><br />   
                    <webuijsf:textField id="dynamicLabel2" label="#{msgs.cbrb_labelTitle}" onKeyUp="refreshGroup('form1:rbGrp2', 'form1:dynamicLabel2')" text="#{rbcbGroupBean.label}"/>       
                    <br /><br />
                                           
              </webuijsf:markup>
            </webuijsf:form>
          </webuijsf:body> 
      </webuijsf:html>  
    </webuijsf:page>
  </f:view>
</jsp:root>                       
