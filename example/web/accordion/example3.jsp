<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %> 
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@taglib uri="http://www.sun.com/webui/webuijsf" prefix="webuijsf" %>
<f:view>
  <webuijsf:page>
    <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
    <webuijsf:html>
    <webuijsf:head title="Accordion tab Example 3"/>
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
        <webuijsf:hyperlink url="../index.jsp" text="Example Index"/>
        <webuijsf:hyperlink url="../accordion/index.jsp" text="Accordion Examples"/>
        <webuijsf:hyperlink url="../accordion/example3.jsp" text="Accordion Example 3"/>
       </webuijsf:breadcrumbs>
      </br></br></br>
      
      
   <table><tr valign="top">
     <!-- Accordion example1 -->
     <td style="width:200px">
     <div style="width:100%">Accordion occupying 100% of parent width. </div><br/>

     <webuijsf:accordion id="acc1" toggleControls="true" refreshIcon="true">
         
         <webuijsf:accordionTab id="tab1" title="One" contentHeight="50px" >
              <webuijsf:staticText binding="#{AccordionBean.text1}"/>
         </webuijsf:accordionTab>
                
         <webuijsf:accordionTab id="tab2" title="Two" contentHeight="50px">
                <webuijsf:staticText binding="#{AccordionBean.text2}"/>
         </webuijsf:accordionTab>
                
         <webuijsf:accordionTab id="tab3" title="Three" contentHeight="50px">
            <webuijsf:staticText binding="#{AccordionBean.text3}"/>
         </webuijsf:accordionTab>
                
         <webuijsf:accordionTab id="tab4" title="Four" contentHeight="50px">
           <webuijsf:staticText binding="#{AccordionBean.text4}"/>
         </webuijsf:accordionTab>
                
     </webuijsf:accordion>
                      
    </td>
    <td> </td>
    <!-- Accordion example2 -->
     <td style="width:200px">
     <div style="width:100%">Accordion occupying 100% of parent width. </div><br/>

     <webuijsf:accordion id="acc2" toggleControls="true" refreshIcon="true"
        multipleSelect="true">
         
         <webuijsf:accordionTab id="tab1" title="One" contentHeight="50px" >
              <webuijsf:staticText binding="#{AccordionBean.text5}"/>
         </webuijsf:accordionTab>
                
         <webuijsf:accordionTab id="tab2" title="Two" contentHeight="50px">
                <webuijsf:staticText binding="#{AccordionBean.text6}"/>
         </webuijsf:accordionTab>
                
         <webuijsf:accordionTab id="tab3" title="Three" contentHeight="50px">
            <webuijsf:staticText binding="#{AccordionBean.text7}"/>
         </webuijsf:accordionTab>
                
         <webuijsf:accordionTab id="tab4" title="Four" contentHeight="50px">
           <webuijsf:staticText binding="#{AccordionBean.text8}"/>
         </webuijsf:accordionTab>
                
     </webuijsf:accordion>
                      
    </td>
   </tr>
    <tr><td>
    <webuijsf:script>
        function refreshAccordion1() {
          var acc = document.getElementById("accordionExample:acc1:tab1");
          acc.refresh("accordionExample:acc1:tab1");
          return false;
        }
    </webuijsf:script>
    <!-- Primary Button -->
    </br>
    <webuijsf:button primary="true"  id="returnButton1" 
        text="Refresh" onClick="return refreshAccordion1()"/>
    </td>
    <td> </td>
    <td>
    <webuijsf:script>
        function refreshAccordion2() {
          var acc = document.getElementById("accordionExample:acc2:tab1");
          acc.refresh("accordionExample:acc2:tab1");
          return false;
        }
    </webuijsf:script>
    <!-- Primary Button -->
    </br>
    <webuijsf:button primary="true"  id="returnButton2" 
        text="Refresh" onClick="return refreshAccordion2()"/>
    </td>
   </tr>
   <tr>
       <td style="width:200px"> <p> 
               This test case illustrates the refresh behavior of a single select accordion. Clicking
               the refresh icon of the accordion refreshes each accordionTab with the latest
               timestamp. Clicking on the refresh button above only refreshes the
               first accordionTab. 
       </p></td>
       <td> </td>
       <td style="width:200px"> <p> 
               This test case illustrates the refresh behavior of a multiple select accordion. Clicking
               the refresh icon of the accordion refreshes each accordionTab with the latest
               timestamp. Clicking on the refresh button above only refreshes the
               first accordionTab. 
       </p></td>
   </tr>
</table>

      </webuijsf:form>
    </webuijsf:body>
    </webuijsf:html>
  </webuijsf:page>
</f:view>
