<jsp:root version="2.0" 
          xmlns:f="http://java.sun.com/jsf/core" 
          xmlns:h="http://java.sun.com/jsf/html" 
          xmlns:jsp="http://java.sun.com/JSP/Page" 
          xmlns:webuijsf="http://www.sun.com/webui/webuijsf">

<jsp:directive.page contentType="text/html" /> 
                   
<!-- Static Text Example -->
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
  <webuijsf:page id="page1" >
    <webuijsf:html id="html1" >
      <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
        <webuijsf:head id="head1" title="#{msgs.statictext_title}">
          <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
        </webuijsf:head>
        <webuijsf:body id="body1" >
            <webuijsf:form id="form1">
            
              <!-- Masthead -->
              <webuijsf:masthead id="masthead" serverInfo="test_server" userInfo="test_user" 
                           productImageURL="/images/example_primary_masthead.png" 
                           productImageDescription="#{msgs.mastheadAltText}"/>
                           
              <!-- Breadcrumbs -->             
              <webuijsf:breadcrumbs id="breadcrumbs">
                <webuijsf:hyperlink id="hyp1" actionExpression="#{IndexBean.showIndex}" text="#{msgs.exampleTitle}"
                              toolTip="#{msgs.index_title}" immediate="true"
                              onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true"
                              onMouseOut="javascript:window.status=''; return true"/>
                <webuijsf:hyperlink id="hyp2" text="#{msgs.statictext_title}"/>
              </webuijsf:breadcrumbs>
              
              <!-- Page Title -->
              <webuijsf:contentPageTitle id="pagetitle" title="#{msgs.statictext_title}" helpText="#{msgs.statictext_helpText}" />
              <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
              
              <!-- Static Texts with Default Converter -->
              <br/><br/>
              <webuijsf:label id="label" text="#{msgs.statictext_label}" labelLevel="1" />
              <webuijsf:helpInline id="pageHelp1" text="#{msgs.statictext_helpText1}" />
              
              <br/><table border="0"><tr><td> 
              <webuijsf:label id="label1" for="text1" text="#{msgs.statictext_label1}" style="padding-left:10px;" />
              
              </td><td> 
              <webuijsf:staticText id="text1" text="#{StatictextBean.date}" >
                <f:convertDateTime pattern="yyyy.MM.dd G HH:mm:ss z" />
              </webuijsf:staticText>                            
              
              </td></tr><tr><td>              
              <webuijsf:label id="label2" for="text2" text="#{msgs.statictext_label2}" style="padding-left:10px;" />  
  
              </td><td>              
              <webuijsf:staticText id="text2" text="#{StatictextBean.date}" >
                <f:convertDateTime pattern="MM/dd/yyyy HH:mm:ss z" />
              </webuijsf:staticText>
              
              </td></tr><tr><td> 
              <webuijsf:label id="label3" for="text3" text="#{msgs.statictext_label3}" style="padding-left:10px;" />  
              
              </td><td> 
              <webuijsf:staticText id="text3" text="#{StatictextBean.date}" >
                <f:convertDateTime pattern="MM/dd/yy" />
              </webuijsf:staticText>
              </td></tr></table> 
             
              <!-- Static Text with Custom Converter -->
              <br/><br/>              
              <webuijsf:label id="label4" for="text4" text="#{msgs.statictext_label4}" labelLevel="1" />
              <webuijsf:helpInline id="pageHelp2" text="#{msgs.statictext_helpText2}" />
              
              <br/> <f:verbatim><![CDATA[&nbsp;&nbsp;&nbsp;&nbsp;]]></f:verbatim>             
              <webuijsf:staticText id="text4"  text="#{StatictextBean.emp}" converter="#{StatictextBean.empConverter}" /> 
              
              <!-- Static Text with Dynamic Message -->
              <br/><br/><br/>
              <webuijsf:label id="label5" for="text5" text="#{msgs.statictext_label5}" labelLevel="1" />    
              <webuijsf:helpInline id="pageHelp3" text="#{msgs.statictext_helpText3}" />
                                  
              <br/> <f:verbatim><![CDATA[ &nbsp;&nbsp;&nbsp;&nbsp;]]></f:verbatim>
              <webuijsf:staticText id="text5" text="#{msgs.statictext_text}" 
                             onMouseOver="(document.getElementById('form1:text5')).innerHTML='#{msgs.statictext_imageMouseOver}'; return false;"
                             onMouseOut="(document.getElementById('form1:text5')).innerHTML='#{msgs.statictext_imageMouseOut}'; return false;"
                             onClick="(document.getElementById('form1:text5')).innerHTML='#{msgs.statictext_imageOnClick}'; return false;" />
                               
              </webuijsf:markup>
            </webuijsf:form>
          </webuijsf:body>
    </webuijsf:html>
  </webuijsf:page> 
</f:view>
</jsp:root>
