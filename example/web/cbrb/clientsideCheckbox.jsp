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
        <webuijsf:head title="#{msgs.cbrb_clientsideCbTitle}">
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
                <webuijsf:hyperlink text="#{msgs.cbrb_clientsideCbTitle}"/>
              </webuijsf:breadcrumbs>
                       
              <!-- Page Title -->
              <webuijsf:contentPageTitle title="#{msgs.cbrb_clientsideCbTitle}" />
              <webuijsf:script>
                  
                    // Toggle enable/disable state of checkbox client-side.
                    function toggleCheckboxState() {
                        var domNode = document.getElementById("form1:cbGrp1");
                        domNode.setProps({disabled: !domNode.getProps().disabled});
                        return false;
                    }
                  
              </webuijsf:script>          

              <!-- Workaround for issue #854.
                The default "auto submit" HTML behavior for the return key
                is to run the onClick handler for the 1st submit input element.
                For this page, that means that entering the return key
                for any element which does not have an onclick handler will
                will cause the toggle button's onClick handler to be called,
                which results in the group of checkboxes being disabled.
                So to workaround the problem, we create an invisible button 
                with a handler that stops the event chaining.
              -->
              <webuijsf:button primary="false" id="dummy" text="" 
                visible="false" onClick="return false;"/>
              
              <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
                       
                <!-- Use HTML table for layout.  Note that if we had included this
                     content within the body of contentPageTitle, then we would need
                     to wrap the HTML markup in the f:verbatim tag.  webuijsf:markup could
                     also be used but that is more heavyweight (slower). -->
                <table>
                  <tr valign="top">
                    <td style="padding-top:3px">
                      <!-- Checkbox -->
                       <webuijsf:checkboxGroup id="cbGrp1" items="#{rbcbGroupBean.array}" 
                            selected="#{rbcbGroupBean.cb1selected}"
                            toolTip="CheckboxGroup" />                                                                 
                    <br /><br />                             
                    </td>
                  </tr>               
                </table>
                <br/>

                <!-- Toggle State Button -->
                <webuijsf:button id="ToggleStateButton" text="#{msgs.cbrb_testCase_toggleCheckboxState}" onClick="return toggleCheckboxState();" />
                                           
              </webuijsf:markup>
            </webuijsf:form>
          </webuijsf:body> 
      </webuijsf:html>  
    </webuijsf:page>
  </f:view>
</jsp:root>                       
