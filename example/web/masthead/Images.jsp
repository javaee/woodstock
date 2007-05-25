<jsp:root version="2.0" 
          xmlns:f="http://java.sun.com/jsf/core" 
          xmlns:h="http://java.sun.com/jsf/html" 
          xmlns:jsp="http://java.sun.com/JSP/Page" 
          xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
          
<jsp:directive.page contentType="text/html" /> 
                    
<!-- Page with Images and ImageHyperlink -->
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
  <webuijsf:page id="page6" >
    <webuijsf:html id="html6" >
      <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
          <webuijsf:head id="head6" title="#{msgs.masthead_title}">
            <webuijsf:script type="text/javascript">
              function toggleVisible() {
                 var domNode = document.getElementById("form6:image5");
                      if (domNode != null) {
                          var props = domNode.getProps();
                          if (props != null) {
                              domNode.setProps({visible:!props.visible});
                          }
                      }
                  }
              
              function refreshImage() {
                // Get image to refresh.
                  var domNode = document.getElementById("form6:image6");
                  domNode.refresh("form6:textField1"); // Update text field value and refresh.
              }              
              
              function toggleBorder() {
                  var domNode = document.getElementById("form6:image5");
                  if (domNode != null) {
                      var props = domNode.getProps();
                      if (props != null) {
                          if (props.border == 1) {
                              domNode.setProps({border:0});
                          } else {
                              domNode.setProps({border:1});                                                                                            
                          }    
                      }
                  }
              }
              
              function toggleImageHyperlink() {
                  var props = document.getElementById("form6:iconhyperlink").getProps();
                  document.getElementById("form6:iconhyperlink").setProps({disabled:!props.disabled});              
              }
            </webuijsf:script>
          </webuijsf:head>
          <webuijsf:body id="body6" >
            <webuijsf:form id="form6">
            
              <!-- Masthead -->
              <webuijsf:masthead id="masthead" serverInfo="#{MastheadBean.server}" userInfo="#{MastheadBean.user}" 
                           productImageURL="/images/example_primary_masthead.png" productImageDescription="#{msgs.mastheadAltText}"/>
                           
              <!-- Breadcrumbs -->             
              <webuijsf:breadcrumbs id="breadcrumbs">
                <webuijsf:hyperlink id="hyp1" actionExpression="#{IndexBean.showIndex}" text="#{msgs.exampleTitle}"
                              toolTip="#{msgs.index_title}" immediate="true"
                              onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true"
                              onMouseOut="javascript:window.status=''; return true"/>
                <webuijsf:hyperlink id="hyp2" actionExpression="#{MastheadBean.goToMastheadIndex}"
                              text="#{msgs.masthead_title}" toolTip="#{msgs.masthead_titleToolTip}" immediate="true"
                              onMouseOver="javascript:window.status='#{msgs.masthead_breadcrumbMouseOver}'; return true"
                              onMouseOut="javascript:window.status=''; return true"/>
                <webuijsf:hyperlink id="hyp3" text="#{msgs.masthead_masthead3Title}"/>
              </webuijsf:breadcrumbs>
              
              <!-- Page Title -->
              <webuijsf:contentPageTitle id="pagetitle1" title="#{msgs.masthead_pageTitle3}" helpText="#{msgs.masthead_helpText3}" />
                                          
              <ul><li/>
              <webuijsf:label id="label1" for="image1" text="#{msgs.masthead_label1}" />
              <br/>
              
              <!-- Image -->
              <webuijsf:image id="image1" 
		  url="/images/version_product_name.png" 
		  alt="#{msgs.masthead_imageAltText}" 
                        height="140" width="140" border="2" hspace="5" vspace="5" align="middle" 
                        toolTip="#{msgs.masthead_imageToolTip}"
                        onMouseOver="(document.getElementById('form6:text')).innerHTML='#{msgs.masthead_imageMouseOver}'; return false;"
                        onMouseOut="(document.getElementById('form6:text')).innerHTML='#{msgs.masthead_imageMouseOut}'; return false;"
                        onClick="(document.getElementById('form6:text')).innerHTML='#{msgs.masthead_imageOnClick}'; return false;"/>  
               
              <!-- Static Text -->                    
              <webuijsf:staticText id="text" text=""/>
              
              <li/>
              <webuijsf:label id="label2" text="#{msgs.masthead_label2}" />
              <br/>
              
              <!-- Theme Specific Images -->
              <webuijsf:image id="image2" icon="ALARM_CRITICAL_SMALL" hspace="5" longDesc="#{msgs.masthead_imageDesc}"/> 
              <webuijsf:image id="image3" icon="ALARM_MASTHEAD_MAJOR_MEDIUM" hspace="5" longDesc="#{msgs.masthead_imageDesc}"/>
              <webuijsf:image id="image4" icon="ALARM_MASTHEAD_MINOR_DIMMED" hspace="5" longDesc="#{msgs.masthead_imageDesc}"/>
              
              <li/>
              <webuijsf:label id="label3" for="imagehyperlink" text="#{msgs.masthead_label3}" />
              <br/>
              
              <!-- ImageHyperlink -->
              <webuijsf:imageHyperlink id="imagehyperlink" 
	      imageURL="/images/version_product_name.png"
                                 url="http://www.sun.com" toolTip="#{msgs.masthead_imagehyperlinkToolTip}"
                                 hspace="5" vspace="5" immediate="true" />
              <!-- Client side image examples -->                                               
              <li/>
              <webuijsf:label id="label4" for="image2" text="#{msgs.masthead_label4}"/>
              <br/>              
              <webuijsf:image id="image5" url="/images/version_product_name.png" alt="#{msgs.masthead_imageAltText}" 
                        toolTip="#{msgs.masthead_imageToolTip}"/>
              <br/>                        
              <webuijsf:button id="buttonClientSide1" text="#{msgs.masthead_clientImageVisible}" onClick="toggleVisible();return false;"/>
              <webuijsf:button id="buttonClientSide2" text="#{msgs.masthead_clientImageBorder}" onClick="toggleBorder();return false;"/>
              <br/><br/>
              <li/>
              <webuijsf:label id="label6" text="#{msgs.masthead_label6}"/>
               <br/>
              <webuijsf:imageHyperlink id="iconhyperlink" 
                                       url="http://www.sun.com"
                                       alt="#{msgs.masthead_imageAltText}" 
                                       icon="ALARM_MAJOR_MEDIUM" 
                                       text="#{msgs.masthead_imageHyperlinkText}"
                                       textPosition="left">
                   <f:facet name="disabledImage">
                        <webuijsf:image id="disabledImage" icon="ALARM_MASTHEAD_MAJOR_DIMMED"/>
                   </f:facet>
              </webuijsf:imageHyperlink>
              <webuijsf:button id="buttonClientSide3" text="#{msgs.masthead_toggleImageHyperlink}" onClick="toggleImageHyperlink();return false"/>                            
              <br/><br/>  
              <li/>
              <webuijsf:label labelLevel="2" id="label5" for="form6:textField1" text="#{msgs.masthead_label5}"/>               
              <br/>                
              <webuijsf:image id="image6" url="#{ImageBean.imageUrl}" alt="#{msgs.masthead_imageAltText}" 
                        toolTip="#{msgs.masthead_imageToolTip}"/>              
              <br/>                           
              <webuijsf:textField id="textField1" onKeyPress="setTimeout('refreshImage();', 0);" text="#{ImageBean.imageUrl}"
                label="#{msgs.masthead_textField1}"/><br/>
              </ul>            
            </webuijsf:form>
          </webuijsf:body>
    </webuijsf:html>
  </webuijsf:page>
</f:view>
</jsp:root>
