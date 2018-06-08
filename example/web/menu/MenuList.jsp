<%--

    DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

    Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.

    The contents of this file are subject to the terms of either the GNU
    General Public License Version 2 only ("GPL") or the Common Development
    and Distribution License("CDDL") (collectively, the "License").  You
    may not use this file except in compliance with the License.  You can
    obtain a copy of the License at
    https://oss.oracle.com/licenses/CDDL+GPL-1.1
    or LICENSE.txt.  See the License for the specific
    language governing permissions and limitations under the License.

    When distributing the software, include this License Header Notice in each
    file and include the License file at LICENSE.txt.

    GPL Classpath Exception:
    Oracle designates this particular file as subject to the "Classpath"
    exception as provided by Oracle in the GPL Version 2 section of the License
    file that accompanied this code.

    Modifications:
    If applicable, add the following below the License Header, with the fields
    enclosed by brackets [] replaced by your own identifying information:
    "Portions Copyright [year] [name of copyright owner]"

    Contributor(s):
    If you wish your version of this file to be governed by only the CDDL or
    only the GPL Version 2, indicate your decision by adding "[Contributor]
    elects to include this software in this distribution under the [CDDL or GPL
    Version 2] license."  If you don't indicate a single choice of license, a
    recipient has the option to distribute your version of this file under
    either the CDDL, the GPL Version 2 or to extend the choice of license to
    its licensees as provided above.  However, if you add GPL Version 2 code
    and therefore, elected the GPL Version 2 license, then the option applies
    only if the new code is made subject to such option by the copyright
    holder.

--%>

<jsp:root version="2.0" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
 <jsp:directive.page contentType="text/html"/>
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
    <webuijsf:html id="html">  
      <webuijsf:head id="head" title="#{msgs.menu_title}" >
        <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
      </webuijsf:head>
      <webuijsf:body id="body">
        <webuijsf:form id="form">                             
                
          <!-- Masthead -->
          <webuijsf:masthead id="masthead"
              productImageURL="/images/example_primary_masthead.png"
              productImageHeight="40"
              productImageWidth="188"
              userInfo="test_user" 
              serverInfo="test_server"
              productImageDescription="#{msgs.mastheadAltText}" />
          
          <!-- Breadcrumbs -->   
          <webuijsf:breadcrumbs id="breadcrumbs">
            <webuijsf:hyperlink id="indexLink"
                text="#{msgs.index_title}"
                toolTip="#{msgs.index_title}"
                actionExpression="#{MenuListBean.showExampleIndex}" 
                onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
            <webuijsf:hyperlink id="exampleLink" text="#{msgs.menu_title}"/>
          </webuijsf:breadcrumbs>
          
          <!-- Alert -->
          <webuijsf:alert id="alert"
              summary="#{msgs.menu_alertElement}"
              type="info"
              rendered="#{MenuListBean.alertRendered}"
              detail="#{MenuListBean.alertDetail}" />
            
          <!-- Content Page Title -->
          <webuijsf:contentPageTitle id="contentPageTitle" title="#{msgs.menu_title}"/>           
          
          <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
            <br />
            <br />
            <table border="0">
              <tr>                
                <td valign="top"><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td>
                <td>                
                  <!-- Action Jump Menu -->                     
                  <webuijsf:dropDown id="jumpMenu"
                      toolTip="#{msgs.menu_jumpMenuTitle}"                 
                      submitForm="true"
                      immediate="true"
                      forgetValue="true"
                      disabled="#{MenuListBean.jumpMenuDisabled}"
                      selected="#{MenuListBean.jumpMenuSelectedOption}"
                      items="#{MenuListBean.jumpMenuOptions}"                      
                      actionListenerExpression="#{MenuListBean.processJumpMenuSelection}" />                     
                </td>
              </tr>
              <tr>
                <td><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td>
                <td>                   
                  <webuijsf:helpInline id="jumpMenuHelp" type="field" text="#{msgs.menu_jumpMenuHelp}" />                                  
                </td>
              </tr>
              <tr>
                <td colspan="2"><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td>
              </tr>
              <tr>
                <td colspan="2"><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td>
              </tr>
              <tr>
                <td valign="top">                
                  <!-- Standard Menu Label --> 
                  <webuijsf:label id="standardMenuLabel"
                      for="standardMenu"
                      style="padding-right:10px;"
                      text="#{msgs.menu_standardMenuLabel}" />                
                </td>
                <td>                
                  <!-- Standard Menu -->                     
                  <webuijsf:dropDown id="standardMenu"
                      toolTip="#{msgs.menu_standardMenuTitle}"
                      disabled="#{MenuListBean.standardMenuDisabled}"
                      selected="#{MenuListBean.standardMenuSelectedOption}"
                      items="#{MenuListBean.standardMenuOptions}"
                      actionListenerExpression="#{MenuListBean.processStandardMenuSelection}" />                     
                </td>
              </tr>
              <tr>
                <td><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td>
		<td>    
                  <webuijsf:helpInline id="standardMenuHelp" type="field" text="#{msgs.menu_standardMenuHelp}" />                
                </td>
              </tr>
              <tr>
                <td colspan="2"><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td>
              </tr>
              <tr>
                <td colspan="2"><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td>
              </tr>
              <tr>
                <td valign="top">                
                  <!-- Scrolling List Label -->  
                  <webuijsf:label id="scrollListLabel"
                      for="scrollList"
                      style="padding-right:10px;"
                      text="#{msgs.menu_selectableListLabel}" />                
                </td>
                <td>
                  <!-- Scrolling List -->                     
                  <webuijsf:listbox id="scrollList"                    
                      toolTip="#{menu_selectableListTitle}"
                      selected="#{MenuListBean.listboxSelectedOption}"
                      disabled="#{MenuListBean.listboxDisabled}"   
                      items="#{MenuListBean.listboxOptions}"
                      onChange="javascript:
                                var value = webui.suntheme.listbox.getSelectedValue('form:scrollList');                                
                                if (value == 'option_0_value') {
                                    (document.getElementById('form:text')).innerHTML='#{msgs.menu_noOption}'; 
                                } else {
                                    (document.getElementById('form:text')).innerHTML='#{msgs.menu_staticText} ' + value;
                                } 
                                return true;" />                                      
                </td>
              </tr>
              <tr>
                <td colspan="2"><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td>
              </tr>
              <tr>
                <td><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td>                
                <td align="left">                                                       
                  <!-- Static Text for showing the value of the selected option. -->                    
                  <webuijsf:staticText id="text" text="" />                                        
                </td>
              </tr>                
              <tr>
                <td colspan="2"><f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim></td>
              </tr>              
            </table>
               
            <webuijsf:panelGroup id="pageActionsGroup" block="true" style="padding:25px 0px 0px 0px;">     
              <!-- Submit Button -->            
              <webuijsf:button id="SubmitButton"                       
                  text="#{msgs.menu_submitButton}"
                  actionExpression="#{MenuListBean.showMenuListResults}" />
                  
              <!-- Test Case Menu -->                     
              <webuijsf:dropDown id="testCaseMenu"     
                  immediate="true"
                  submitForm="true"
                  forgetValue="true"                     
                  items="#{MenuListBean.testCaseOptions}"
                  actionListenerExpression="#{MenuListBean.processTestCaseMenuSelection}" /> 
            </webuijsf:panelGroup>     
                            
          </webuijsf:markup>          
        </webuijsf:form>
      </webuijsf:body>
    </webuijsf:html>  
  </webuijsf:page>
 </f:view>
</jsp:root>
