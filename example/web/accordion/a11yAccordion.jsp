<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %> 
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@taglib uri="http://www.sun.com/webui/webuijsf" prefix="webuijsf" %>
<f:view>
  <webuijsf:page>
    <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
    <webuijsf:html>
      <webuijsf:head title="Accessible Accordion Example"/>
      <webuijsf:body>
        <webuijsf:form id="a11yExample">
  
          <webuijsf:masthead id="masthead"
            productImageURL="/images/example_primary_masthead.png"
            productImageHeight="40"
            productImageWidth="188"
            userInfo="test_user" 
            serverInfo="test_server"
            productImageDescription="#{msgs.mastheadAltText}" />
               
          <webuijsf:breadcrumbs id="breadcrumbs">
            <webuijsf:hyperlink url="../index.jsp" text="Examples Index"/>
            <webuijsf:hyperlink url="../accordion/index.jsp" text="Accordion Examples"/>
            <webuijsf:hyperlink url="../accordion/a11yAccordion.jsp" text="Accessible Accordion Example"/>
          </webuijsf:breadcrumbs>
    
          <br/><br/>
          
          <table><tr valign="top">
       
            <!-- Accessible Accordion example -->
            <td style="width:300px">
              <div style="width:100%">
                Accordion occupying 100% of parent width with variable tab
                content height. The first 4 are 50 pixels, the last one 200 pixels.
              </div>
       
              <br/>
       
              <webuijsf:accordion id="acc1" tabIndex="1" multipleSelect="true" refreshIcon="true" toggleControls="true">
               
                <webuijsf:accordionTab id="tab1" title="One" contentHeight="50px"
                 focusId="a11yExample:acc1:tab1:tf1_field">
                  <webuijsf:textField id="tf1" tabIndex="1" label="Focus1:" labelLevel="2"/>
                  <br/>
                  <webuijsf:textField id="tf2" tabIndex="1" label="Focus2:" labelLevel="2"/>
                  <br/>
                  <webuijsf:textField id="tf3" tabIndex="1" label="Focus3:" labelLevel="2"/>
                  <br/>
                  <webuijsf:label id="label1" text="Current Time: "/>
                  <webuijsf:staticText binding="#{AccordionBean.text5}"/>
                </webuijsf:accordionTab>
                       
                <webuijsf:accordionTab id="tab2" title="Two" contentHeight="50px">
                    <webuijsf:label id="label2" text="Current Time: "/>
                    <webuijsf:staticText binding="#{AccordionBean.text6}"/>
                </webuijsf:accordionTab>
                       
                <webuijsf:accordionTab id="tab3" title="Three" contentHeight="50px">
                    <webuijsf:label id="label3" text="Current Time: "/>
                    <webuijsf:staticText binding="#{AccordionBean.text7}"/>
                </webuijsf:accordionTab>
                       
                <webuijsf:accordionTab id="tab4" title="Four" contentHeight="50px"
                    focusId="a11yExample:acc1:tab4:tf4_field">
                  <webuijsf:markup tag="p">
                    <webuijsf:staticText text="Contents on bucket 4"/>
                  </webuijsf:markup>
                  <webuijsf:textField id="tf4" tabIndex="1" label="Search:" labelLevel="2"/>
                </webuijsf:accordionTab>
                       
                <webuijsf:accordionTab id="tab5" title="News" contentHeight="200px">
                  <webuijsf:tree id="MyTree" style="width:35em;">
                    <webuijsf:treeNode id="Node1" expanded="true" text="External URLs">
                      <f:facet name="image">
                        <webuijsf:image id="image" icon="TREE_SERVER" />
                      </f:facet>
                      <webuijsf:treeNode id="Node1_1" text="Sun Microsystems, Inc." 
                        url="http://www.sun.com" target="external">
                        <f:facet name="image">
                          <webuijsf:image id="image" icon="TREE_STORAGE_MAJOR" />
                        </f:facet>
                      </webuijsf:treeNode>
       
                      <webuijsf:treeNode id="Node1_2" expanded="true" 
                        text="Search Engines">
       
                        <webuijsf:treeNode id="Node1_2_1" text="Google" 
                          url="http://www.google.com" target="external">
                          <f:facet name="image">
                            <webuijsf:image id="image" url="/images/google.jpg" 
                              height="16" width="16" />
                          </f:facet>
                        </webuijsf:treeNode>
       
                        <webuijsf:treeNode id="Node1_2_2" text="Yahoo!" 
                          url="http://www.yahoo.com" 
                          imageURL="/images/yahoo.jpg" target="external">
                        </webuijsf:treeNode>
       
                        <webuijsf:treeNode id="Node1_2_3" text="Lycos" 
                          url="http://www.lycos.com" target="external">
                          <f:facet name="image">
                            <webuijsf:image id="image" 
                              url="http://ly.lygo.com/ly/srch/hp/dog_web_34x35.gif"
                              height="16" width="16" />
                          </f:facet>
                        </webuijsf:treeNode>
                      </webuijsf:treeNode>
                    </webuijsf:treeNode>
                  </webuijsf:tree>
                </webuijsf:accordionTab> 
                       
              </webuijsf:accordion>
       
              <!-- Primary Button -->
              <br/>
              <webuijsf:button primary="true"  id="returnButton" 
                text="Submit" tabIndex="2" onClick="return submitForm()"/>

              <p> 
                Along with exhibiting basic accordion behavior this test case 
                also illustrates how to traverse the accordion using keystrokes.
              </p>

            </td>

          </tr></table>

          <webuijsf:script>
            function submitForm() {
              var form = document.getElementById("a11yExample");
              form.submit();
            }
          </webuijsf:script>
  
        </webuijsf:form>
      </webuijsf:body>
    </webuijsf:html>
  </webuijsf:page>
</f:view>