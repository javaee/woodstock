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
        <webuijsf:head title="#{msgs.bubble_title}" >
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
              <webuijsf:hyperlink actionExpression="#{LabelBean.showExampleIndex}" 
                text="#{msgs.exampleTitle}" 
                onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
              <webuijsf:hyperlink text="#{msgs.bubble_text}"/>
            </webuijsf:breadcrumbs>
                       
            <!-- Page Title -->
            <webuijsf:contentPageTitle title="#{msgs.bubble_title}">
              <webuijsf:staticText style="margin-left:10px;" id="static" 
                    text="#{msgs.bubble_helpText}" />                 
                   <webuijsf:hyperlink id="pageHelpLink2" 
                     text="#{msgs.bubble_imageLinkText}" 
                     onMouseOver="document.getElementById('form1:bubble1').open(event);"
                     onMouseOut="document.getElementById('form1:bubble1').close();"
                     onKeyDown="document.getElementById('form1:bubble1').open(event);"
                     onClick="return false;"/>             
            </webuijsf:contentPageTitle>                       
            <br/>
                       
            <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
                            
              <table border="0">                
                  <tr>
                    <td>
                      <webuijsf:hyperlink id="basicBubble" 
                        onMouseOver="document.getElementById('form1:bubble2').open(event);" 
                        onMouseOut="document.getElementById('form1:bubble2').close();"
                        onKeyDown="document.getElementById('form1:bubble2').open(event);"
                        onClick="return false;"
                        text="#{msgs.bubble_basicBubble}"/>
                    </td>
                    
                  </tr>  
                  <!-- blank rows.   -->
                <tr><td colspan="2"><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td></tr>
                <tr><td colspan="2"><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td></tr>
                <tr><td colspan="2"><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td></tr>
                <tr><td colspan="2"><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td></tr>
                 
                  <tr>
                    <td>
                      <webuijsf:hyperlink id="linkBubble" 
                        onMouseOver="document.getElementById('form1:bubble3').open(event);" 
                        onMouseOut="document.getElementById('form1:bubble3').close();"
                        onKeyDown="document.getElementById('form1:bubble3').open(event);"
                        onClick="return false;"
                        text="#{msgs.bubble_hyperlinkBubble}"/>
                    </td>                    
                  </tr>  
                                
                <!-- blank rows.   -->
                <tr><td colspan="2"><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td></tr>
                <tr><td colspan="2"><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td></tr>
                <tr><td colspan="2"><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td></tr>
                <tr><td colspan="2"><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td></tr>
                
                <tr>
                  <td>
                    <webuijsf:hyperlink id="imageBubble" 
                        onMouseOver="document.getElementById('form1:bubble4').open(event);" 
                        onMouseOut="document.getElementById('form1:bubble4').close();"
                        onKeyDown="document.getElementById('form1:bubble4').open(event);"
                        onClick="return false;"
                        text="#{msgs.bubble_imageBubble}"/>
                  </td>                 
                </tr>
                
                <!-- blank rows.   -->
                <tr><td colspan="2"><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td></tr>
                <tr><td colspan="2"><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td></tr>
                <tr><td colspan="2"><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td></tr>
                <tr><td colspan="2"><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td></tr>
                
                <tr>
                  <td>
                    <webuijsf:label id="closeBubble" 
                        onMouseOver="document.getElementById('form1:bubble5').open(event);" 
                        onMouseOut="document.getElementById('form1:bubble5').close();"
                        
                        text="#{msgs.bubble_closeBubble}"/>
                  </td>                 
                </tr>
              </table>

              <!-- Bubble for image hyperlink. -->        
              <webuijsf:bubble id="bubble1" title="#{msgs.bubble_titleText1}" >
                  <webuijsf:staticText text="#{msgs.bubble_bodyTextLarge}" />        
              </webuijsf:bubble>
              
              <!-- Bubble with simple text -->
              <webuijsf:bubble id="bubble2" title="#{msgs.bubble_titleText2}">
                  <webuijsf:staticText text="#{msgs.bubble_bodyTextBasic}" />        
              </webuijsf:bubble>
              
              <!-- Bubble with hyperlink -->
              <webuijsf:bubble id="bubble3"  focusId="anchor2" title="#{msgs.bubble_titleText3}" >
                  <webuijsf:panelLayout id="panel3">
                      <webuijsf:label labelLevel="1" text="#{msgs.bubble_anchorHeading}" 
                         id="anchorUrlLabel"/>          
                        <webuijsf:helpInline id="anchorUrlHelp" 
                          text="#{msgs.hyperlink_anchorUrlHelp}" />
                      <webuijsf:anchor id="anchor2"  
                          text="#{msgs.hyperlink_anchorUrlText}" url="http://www.sun.com"/>                                               
                  </webuijsf:panelLayout>
              </webuijsf:bubble>
              
              <!-- Bubble with images -->            
              <webuijsf:bubble id="bubble4" title="#{msgs.bubble_titleText4}" >
                   <webuijsf:panelLayout id="panel4">
                      <webuijsf:label text="#{msgs.bubble_imageLabel}" labelLevel="1" />
                      <f:verbatim><![CDATA[ <br> ]]></f:verbatim>
                      <webuijsf:label text="#{msgs.bubble_errorIndicator}"/>
                      <webuijsf:image url="/images/error_medium.gif"/>
                      <f:verbatim><![CDATA[ <br> ]]></f:verbatim>
                      <webuijsf:label text="#{msgs.bubble_infoIndicator}"/>
                      <webuijsf:image url="/images/info_medium.gif"/>
                      <f:verbatim><![CDATA[ <br> ]]></f:verbatim>
                      <webuijsf:label text="#{msgs.bubble_successIndicator}"/>
                      <webuijsf:image url="/images/success_medium.gif"/>
                      <f:verbatim><![CDATA[ <br> ]]></f:verbatim>
                      <webuijsf:label text="#{msgs.bubble_warnIndicator}"/>
                      <webuijsf:image url="/images/warning_medium.gif"/>
                   </webuijsf:panelLayout>  
              </webuijsf:bubble>
              
              <!-- Bubble without close button -->
              <webuijsf:bubble id="bubble5" title="#{msgs.bubble_titleText5}" closeButton="false">
                  <webuijsf:staticText text="#{msgs.bubble_bodyTextClose}" />        
              </webuijsf:bubble>
              
            </webuijsf:markup>
          </webuijsf:form>
        </webuijsf:body> 
      </webuijsf:html>  
    </webuijsf:page>
  </f:view>
</jsp:root>                       
