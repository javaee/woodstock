<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %> 
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@taglib uri="http://www.sun.com/webui/webuijsf" prefix="webuijsf" %>
<f:view>
  <webuijsf:page>
    <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
    <webuijsf:html>
    <webuijsf:head title="Accordion tab Example 1"/>
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
        <webuijsf:hyperlink url="../index.jsp" text="Tests Index"/>
        <webuijsf:hyperlink url="../accordion/index.jsp" text="Accordion Examples"/>
        <webuijsf:hyperlink url="../accordion/test1.jsp" text="Accordion Example 1"/>
       </webuijsf:breadcrumbs>
      </br></br></br>
      
      
   <table><tr valign="top">
     <!-- Accordion example #1 -->
     <td width="200px">
     <div style="width:100%">Accordion occupying 100% of parent width with variable tab
     content height. The first 4 are 50 pixels, the last one 200 pixels. </div><br/>

         <webuijsf:accordion id="acc1" multipleSelect="true" >
        
                <webuijsf:accordionTab id="tab1" title="One" contentHeight="50px" >
                    <webuijsf:label id="label1" text="Label1"/>
                    <webuijsf:staticText text="Contents on bucket 1"/>
                </webuijsf:accordionTab>
                
                <webuijsf:accordionTab id="tab2" title="Two" contentHeight="50px">
                  <webuijsf:markup tag="p">
                    <webuijsf:staticText text="Contents on bucket 2"/>
                  </webuijsf:markup>
                </webuijsf:accordionTab>
                
                <webuijsf:accordionTab id="tab3" title="Three" contentHeight="50px">
                  <webuijsf:markup tag="p">
                    <webuijsf:staticText text="Contents on bucket 3"/>
                  </webuijsf:markup>
                </webuijsf:accordionTab>
                
                <webuijsf:accordionTab id="tab4" title="Four" contentHeight="50px">
                  <webuijsf:markup tag="p">
                    <webuijsf:staticText text="Contents on bucket 4"/>
                  </webuijsf:markup>
                  <webuijsf:textField id="tf1" label="Search:" labelLevel="2"/>
                </webuijsf:accordionTab>
                
                <webuijsf:accordionTab id="tab5" title="News" contentHeight="200px">
                    <webuijsf:tree id="MyTree" style="width:35em;">
                        <webuijsf:treeNode id="Node1" expanded="true" text="External URLs">
                            <f:facet name="image">
                                <webuijsf:image id="image" icon="TREE_SERVER" />
                            </f:facet>
                            <webuijsf:treeNode id="Node1_1" text="Sun Microsystems, Inc." url="http://www.sun.com" target="external">
                                <f:facet name="image">
                                    <webuijsf:image id="image" icon="TREE_STORAGE_MAJOR" />
                                </f:facet>
                            </webuijsf:treeNode>
                            <webuijsf:treeNode id="Node1_2" expanded="true" text="Search Engines">
                                <webuijsf:treeNode id="Node1_2_1" text="Google" url="http://www.google.com" target="external">
                                    <f:facet name="image">
                                        <webuijsf:image id="image" url="/images/google.jpg" height="16" width="16" />
                                    </f:facet>
                                </webuijsf:treeNode>
                                <webuijsf:treeNode id="Node1_2_2" text="Yahoo!" url="http://www.yahoo.com" imageURL="/images/yahoo.jpg" target="external">
                                </webuijsf:treeNode>
                                <webuijsf:treeNode id="Node1_2_3" text="Lycos" url="http://www.lycos.com" target="external">
                                    <f:facet name="image">
                                        <webuijsf:image id="image" url="http://ly.lygo.com/ly/srch/hp/dog_web_34x35.gif" height="16" width="16" />
                                    </f:facet>
                                </webuijsf:treeNode>
                            </webuijsf:treeNode>
                        </webuijsf:treeNode>
                    </webuijsf:tree>
                </webuijsf:accordionTab> 
                
         </webuijsf:accordion>
       </div>
    </td>


    <!-- space between each example -->
    <td><div>&nbsp&nbsp&nbsp</div></td>


    <!-- Accordion example #2 -->
    <td width="500px">

    <div style="width:30%">Accordion occupying 30% of parent width with standard tab content height = 100px and a 
    saved view selector set at a fixed height = 100%. Multiple select is set to true.</div><br />

      <!-- width override's default in CSS -->
    <webuijsf:accordion id="acc2" style="width:30%" multipleSelect="true"  toggleControls="true" refreshButton="true">
        
                <webuijsf:accordionTab id="tab1" title="One" >
                    <webuijsf:label id="label1" text="Label1"/>
                    <webuijsf:staticText text="Contents on bucket 1"/>
                </webuijsf:accordionTab>
                
                <webuijsf:accordionTab id="tab2" title="Two" >
                  <webuijsf:markup tag="p">
                    <webuijsf:staticText text="Contents on bucket 2"/>
                  </webuijsf:markup>
                </webuijsf:accordionTab>
                
                <webuijsf:accordionTab id="tab3" title="Three" >
                  <webuijsf:markup tag="p">
                    <webuijsf:staticText text="Contents on bucket 3"/>
                  </webuijsf:markup>
                </webuijsf:accordionTab>
                
                <webuijsf:accordionTab id="tab4" title="Four" >
                  <webuijsf:markup tag="p">
                    <webuijsf:staticText text="Contents on bucket 4"/>
                  </webuijsf:markup>
                </webuijsf:accordionTab>
          </webuijsf:accordion>
       </div>
    </td>
    
     <!-- space between each example -->
    <td><div>&nbsp&nbsp&nbsp</div></td>


    <!-- Accordion example #3 -->
    <td width="200px">

      <div style="width:100%">Accordion occupying 100% of parent width with standard tab content height = 100px 
      and a saved view selector that fits all items. Multiple select set to false.</div><br />

      <!-- width override's default in CSS -->
    <webuijsf:accordion id="acc3" style="width:100%" toggleControls="true" refreshButton="true">
        
                <webuijsf:accordionTab id="tab1" title="One" >
                    <webuijsf:label id="label1" text="Label1"/>
                    <webuijsf:staticText text="Contents on bucket 1"/>
                </webuijsf:accordionTab>
                
                <webuijsf:accordionTab id="tab2" title="Two" >
                  
                    <webuijsf:staticText text="Contents on bucket 2"/>
                  
                </webuijsf:accordionTab>
                
                <webuijsf:accordionTab id="tab3" title="Three" >
                  
                    <webuijsf:staticText text="Contents on bucket 3"/>
                  
                </webuijsf:accordionTab>
                
                <webuijsf:accordionTab id="tab4" title="Four" >
                   <webuijsf:staticText text="Contents on bucket 4"/>
                </webuijsf:accordionTab>
       </webuijsf:accordion>
      </div>
    </td>
   </tr>
   <tr><td>
    <webuijsf:script>
        function submitForm() {
          var form = document.getElementById("accordionExample");
          form.submit();
        }
    </webuijsf:script>
    <!-- Primary Button -->
    </br>
    <webuijsf:button primary="true"  id="returnButton" 
        text="Submit" onClick="return submitForm()"/>
    </td>
   </tr>
   <tr>
       <td> <p> 
           Along with exhibiting basic accordion behavior this test case 
           also illustrates the fact that the state of the accordion tabs
           is maintained when the form is submitted. Open or close arbitrary
           accordion tabs and click the "Submit" button. The form should
           reload with tabs in the same open/closed state they were in prior
           to submission.
       </p>
   </tr>
   
</table>

      </webuijsf:form>
    </webuijsf:body>
    </webuijsf:html>
  </webuijsf:page>
</f:view>