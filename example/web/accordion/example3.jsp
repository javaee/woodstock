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
    <webuijsf:page>
      <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
      <webuijsf:html>
        <webuijsf:head title="#{msgs.accordion_refreshTitle}"/>
        <webuijsf:body>
          <webuijsf:form id="accordionExample">
  
             <webuijsf:masthead id="masthead"
               productImageURL="/images/example_primary_masthead.png"
               productImageHeight="40"
               productImageWidth="188"
               userInfo="test_user" 
               serverInfo="test_server"
               productImageDescription="#{msgs.mastheadAltText}" />
  
             <webuijsf:breadcrumbs id="breadcrumbs">
               <webuijsf:hyperlink url="../index.jsp" text="#{msgs.index_title}"/>
               <webuijsf:hyperlink url="../accordion/index.jsp" text="#{msgs.accordion_indexTitle}"/>
               <webuijsf:hyperlink url="../accordion/example3.jsp" text="#{msgs.accordion_refreshTitle}"/>
             </webuijsf:breadcrumbs>
  
             <br/><br/>
        
             <table><tr valign="top">
  
               <!-- Accordion example1 -->
               <td style="width:200px">
                 <div style="width:100%">
                   <webuijsf:staticText text="#{msgs.accordion_refreshAccd1Description1}"/>
                 </div>
  
                 <br/>
  
                 <webuijsf:accordion id="acc1" toggleControls="true" refreshIcon="true">
         
                   <webuijsf:accordionTab id="tab1" title="#{msgs.accordion_tab1}" 
                     contentHeight="50px" >
                     <webuijsf:staticText binding="#{AccordionBean.text1}"/>
                   </webuijsf:accordionTab>
                
                   <webuijsf:accordionTab id="tab2" title="#{msgs.accordion_tab2}" 
                     contentHeight="50px">
                     <webuijsf:staticText binding="#{AccordionBean.text2}"/>
                   </webuijsf:accordionTab>
                
                   <webuijsf:accordionTab id="tab3" title="#{msgs.accordion_tab3}" 
                     contentHeight="50px">
                     <webuijsf:staticText binding="#{AccordionBean.text3}"/>
                   </webuijsf:accordionTab>
                
                   <webuijsf:accordionTab id="tab4" title="#{msgs.accordion_tab4}" 
                     contentHeight="50px">
                     <webuijsf:staticText binding="#{AccordionBean.text4}"/>
                   </webuijsf:accordionTab>
                
                 </webuijsf:accordion>
  
                 <!-- Primary Button -->
                 <br/>
                 <webuijsf:button primary="true"  id="returnButton1" 
                   text="#{msgs.accordion_refreshButton}" onClick="return refreshAccordion1()"/>
  
                 <webuijsf:markup tag="p">
                   <webuijsf:staticText text="#{msgs.accordion_refreshAccd1Description2}"/>
                 </webuijsf:markup>
                      
               </td>
  
               <!-- space between each example -->
               <td><div style="width:10px"/></td>
  
               <!-- Accordion example2 -->
               <td style="width:200px">
                 <div style="width:100%">
                   <webuijsf:staticText text="#{msgs.accordion_refreshAccd2Description1}"/>
                 </div>
  
                 <br/>
  
                 <webuijsf:accordion id="acc2" toggleControls="true" refreshIcon="true"
                   multipleSelect="true">
         
                   <webuijsf:accordionTab id="tab1" title="#{msgs.accordion_tab1}" 
                     contentHeight="50px" >
                     <webuijsf:staticText binding="#{AccordionBean.text5}"/>
                   </webuijsf:accordionTab>
                
                   <webuijsf:accordionTab id="tab2" title="#{msgs.accordion_tab2}" 
                     contentHeight="50px">
                     <webuijsf:staticText binding="#{AccordionBean.text6}"/>
                   </webuijsf:accordionTab>
                
                   <webuijsf:accordionTab id="tab3" title="#{msgs.accordion_tab3}" 
                     contentHeight="50px">
                     <webuijsf:staticText binding="#{AccordionBean.text7}"/>
                   </webuijsf:accordionTab>
                
                   <webuijsf:accordionTab id="tab4" title="#{msgs.accordion_tab4}" 
                     contentHeight="50px">
                     <webuijsf:staticText binding="#{AccordionBean.text8}"/>
                   </webuijsf:accordionTab>
                
                 </webuijsf:accordion>
  
                 <!-- Primary Button -->
                 <br/>
                 <webuijsf:button primary="true"  id="returnButton2" 
                   text="Refresh" onClick="return refreshAccordion2()"/>
  
                 <webuijsf:markup tag="p">
                   <webuijsf:staticText text="#{msgs.accordion_refreshAccd2Description2}"/>
                 </webuijsf:markup>
                      
               </td>
             </tr></table>
  
             <webuijsf:script>
               function refreshAccordion1() {
                 var acc = document.getElementById("accordionExample:acc1:tab1");
                 acc.refresh("accordionExample:acc1:tab1");
                 return false;
               }
  
               function refreshAccordion2() {
                 var acc = document.getElementById("accordionExample:acc2:tab1");
                 acc.refresh("accordionExample:acc2:tab1");
                 return false;
               }
             </webuijsf:script>
  
          </webuijsf:form>
        </webuijsf:body>
      </webuijsf:html>
    </webuijsf:page>
  </f:view>
</jsp:root>
