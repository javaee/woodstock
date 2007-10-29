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
        <webuijsf:head title="#{msgs.accordion_basicTitle}"/>
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
              <webuijsf:hyperlink url="../accordion/example1.jsp" text="#{msgs.accordion_basicTitle}"/>
            </webuijsf:breadcrumbs>
      
            <br/><br/>
            
            <table><tr valign="top">
         
              <!-- Accordion example #1 -->
              <td style="width:200px">
                <div style="width:100%">
                  <webuijsf:staticText text="#{msgs.accordion_basicAccd1Description}"/>
                </div>
         
                <br/>
         
                <webuijsf:accordion id="acc1" multipleSelect="true" >
                 
                  <webuijsf:accordionTab id="tab1" title="#{msgs.accordion_tab1}"
                    contentHeight="50px" >
                    <webuijsf:label id="label1" text="#{msgs.accordion_content1Label}"/>
                    <webuijsf:staticText text="#{msgs.accordion_content1}"/>
                  </webuijsf:accordionTab>
                         
                  <webuijsf:accordionTab id="tab2" title="#{msgs.accordion_tab2}"
                    contentHeight="50px">
                    <webuijsf:markup tag="p">
                      <webuijsf:staticText text="#{msgs.accordion_content2}"/>
                    </webuijsf:markup>
                  </webuijsf:accordionTab>
                         
                  <webuijsf:accordionTab id="tab3" title="#{msgs.accordion_tab3}"
                    contentHeight="50px">
                    <webuijsf:markup tag="p">
                      <webuijsf:staticText text="#{msgs.accordion_content3}"/>
                    </webuijsf:markup>
                  </webuijsf:accordionTab>
                         
                  <webuijsf:accordionTab id="tab4" title="#{msgs.accordion_tab4}"
                    contentHeight="50px">
                    <webuijsf:markup tag="p">
                      <webuijsf:staticText text="#{msgs.accordion_content4}"/>
                    </webuijsf:markup>
                    <webuijsf:textField id="tf1" 
                      label="#{msgs.accordion_searchLabel}" labelLevel="2"/>
                  </webuijsf:accordionTab>
                         
                  <webuijsf:accordionTab id="tab5" title="#{msgs.accordion_tabNews}"
                    contentHeight="200px">
  
                    <webuijsf:tree id="MyTree" style="width:35em;">
                      <webuijsf:treeNode id="Node1" expanded="true"
                        text="#{msgs.accordion_newsRoot}">
                        <f:facet name="image">
                          <webuijsf:image id="image" icon="TREE_SERVER" />
                        </f:facet>
                        <webuijsf:treeNode id="Node1_1"
                          text="#{msgs.accordion_Sun}" 
                          url="http://www.sun.com" target="external">
                          <f:facet name="image">
                            <webuijsf:image id="image" icon="TREE_STORAGE_MAJOR" />
                          </f:facet>
                        </webuijsf:treeNode>
         
                        <webuijsf:treeNode id="Node1_2" expanded="true" 
                          text="#{msgs.accordion_newsSearch}">
         
                          <webuijsf:treeNode id="Node1_2_1"
                            text="#{msgs.accordion_Google}" 
                            url="http://www.google.com" target="external">
                          </webuijsf:treeNode>
         
                          <webuijsf:treeNode id="Node1_2_2"
                            text="#{msgs.accordion_Yahoo}" 
                            url="http://www.yahoo.com"> 
                          </webuijsf:treeNode>
         
                          <webuijsf:treeNode id="Node1_2_3"
                            text="#{msgs.accordion_Lycos}" 
                            url="http://www.lycos.com" target="external">
                          </webuijsf:treeNode>
                        </webuijsf:treeNode>
                      </webuijsf:treeNode>
                    </webuijsf:tree>
                  </webuijsf:accordionTab> 
                         
                </webuijsf:accordion>
         
                <!-- Primary Button -->
                <br/>
                <webuijsf:button primary="true"  id="returnButton" 
                  text="#{msgs.accordion_submitButton}" onClick="return submitForm()"/>
  
                <webuijsf:markup tag="p">
                  <webuijsf:staticText text="#{msgs.accordion_basicDescription}"/>
                </webuijsf:markup>
  
              </td>
  
              <!-- space between each example -->
              <td><div style="width:10px"/></td>
          
              <!-- Accordion example #2 -->
              <td style="width:500px">
          
                <div style="width:30%">
                  <webuijsf:staticText text="#{msgs.accordion_basicAccd2Description}"/>
                </div>
          
                <br />
          
                <!-- width override's default in CSS -->
                <webuijsf:accordion id="acc2" style="width:30%" multipleSelect="true"
                  toggleControls="true" refreshIcon="true">
                  
                  <webuijsf:accordionTab id="tab1" title="#{msgs.accordion_tab1}" >
                    <webuijsf:label id="label1" text="#{msgs.accordion_content1Label}"/>
                    <webuijsf:staticText text="#{msgs.accordion_content1}"/>
                  </webuijsf:accordionTab>
                          
                  <webuijsf:accordionTab id="tab2" title="#{msgs.accordion_tab2}" >
                    <webuijsf:markup tag="p">
                      <webuijsf:staticText text="#{msgs.accordion_content2}"/>
                    </webuijsf:markup>
                  </webuijsf:accordionTab>
                          
                  <webuijsf:accordionTab id="tab3" title="#{msgs.accordion_tab3}" >
                    <webuijsf:markup tag="p">
                      <webuijsf:staticText text="#{msgs.accordion_content3}"/>
                    </webuijsf:markup>
                  </webuijsf:accordionTab>
                          
                  <webuijsf:accordionTab id="tab4" title="#{msgs.accordion_tab4}" >
                    <webuijsf:markup tag="p">
                      <webuijsf:staticText text="#{msgs.accordion_content4}"/>
                    </webuijsf:markup>
                  </webuijsf:accordionTab>
                </webuijsf:accordion>
              </td>
              
              <!-- space between each example -->
              <td><div style="width:10px"/></td>
          
              <!-- Accordion example #3 -->
              <td style="width:200px">
          
                <div style="width:100%">
                  <webuijsf:staticText text="#{msgs.accordion_basicAccd3Description}"/>
                </div>
          
                <br />
          
                <!-- width override's default in CSS -->
                <webuijsf:accordion id="acc3" style="width:100%" toggleControls="true" 
                  refreshIcon="true">
                  
                  <webuijsf:accordionTab id="tab1" title="#{msgs.accordion_tab1}" >
                    <webuijsf:label id="label1" text="#{msgs.accordion_content1Label}"/>
                    <webuijsf:staticText text="#{msgs.accordion_content1}"/>
                  </webuijsf:accordionTab>
                          
                  <webuijsf:accordionTab id="tab2" title="#{msgs.accordion_tab2}" >
                    <webuijsf:staticText text="#{msgs.accordion_content2}"/>
                  </webuijsf:accordionTab>
                          
                  <webuijsf:accordionTab id="tab3" title="#{msgs.accordion_tab3}" >
                    <webuijsf:staticText text="#{msgs.accordion_content3}"/>
                  </webuijsf:accordionTab>
                          
                  <webuijsf:accordionTab id="tab4" title="#{msgs.accordion_tab4}" >
                    <webuijsf:staticText text="#{msgs.accordion_content4}"/>
                  </webuijsf:accordionTab>
                </webuijsf:accordion>
              </td>
            </tr></table>
  
            <webuijsf:script>
              function submitForm() {
                var form = document.getElementById("accordionExample");
                form.submit();
              }
            </webuijsf:script>
    
          </webuijsf:form>
        </webuijsf:body>
      </webuijsf:html>
    </webuijsf:page>
  </f:view>
</jsp:root>
