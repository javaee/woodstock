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
        <webuijsf:page >
            <webuijsf:html>
                <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
                <webuijsf:head title="#{msgs.hyperlink_title}">
		  <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
                  <webuijsf:script type="text/javascript">
                   function toggleVisible() {
                      var domNode = document.getElementById("hyperlinkForm:clientSideLink");
                      var props;
                      if (domNode != null) {
                          props = domNode.getProps();
                          if (props != null) {
                              domNode.setProps({visible:!props.visible});
                          }
                      }
                   }
                     
                   
                   function toggleDisable() {
                      var domNode = document.getElementById("hyperlinkForm:clientSideLink");
                      var props;
                      if (domNode != null) {
                          props = domNode.getProps();
                          if (props != null) {
                              domNode.setProps({disabled:!props.disabled});
                          }
                      }
                   }                   
                   
                   function toggleImageDisable() {
                       var domNode = document.getElementById("hyperlinkForm:clientSideImageLink");
                       var props;
                       if (domNode != null) {
                          props = domNode.getProps();
                          if (props != null) {
                                domNode.setProps({disabled:!props.disabled});
                          }
                      }
                   }
                   
                   function toggleAnchorDisable() {
                       var domNode = document.getElementById("anchor3");
                       var props;
                       if (domNode != null) {
                           props = domNode.getProps();
                           if (props != null) {
                               domNode.setProps({disabled:!props.disabled});
                           }
                       }                   
                   }
                  </webuijsf:script>
                </webuijsf:head>
                <webuijsf:body>
                    <webuijsf:form id="hyperlinkForm">

                       <!-- Masthead -->
                       <webuijsf:masthead id="Masthead" productImageURL="/images/example_primary_masthead.png"
                         productImageDescription="#{msgs.mastheadAltText}" 
                         userInfo="test_user"
                         serverInfo="test_server" />     
                       
                       <!-- Bread Crumb Component -->
                       <webuijsf:breadcrumbs id="breadcrumbs">
                            <webuijsf:hyperlink id="exampleLink"
                                          text="#{msgs.index_title}"
                                          toolTip="#{msgs.index_title}"
                                          actionExpression="#{HyperlinkBean.showExampleIndex}" 
                                          onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                                          onMouseOut="javascript: window.status=''; return true" />
                            <webuijsf:hyperlink id="hyperlinkExample" text="#{msgs.hyperlink_title}"/>
                       </webuijsf:breadcrumbs>
                       
                       <!-- Alert for validator exception. -->
                       <webuijsf:alert id="pageAlert" rendered="#{HyperlinkBean.errorsOnPage}" detail="#{HyperlinkBean.detailMsg}" type="error" 
                                 summary="#{HyperlinkBean.summaryMsg}"> 
                       </webuijsf:alert>
                       
                       <!-- Page Title -->
                       <webuijsf:contentPageTitle title="#{msgs.hyperlink_title}" id="linkContentPage1"/>
                          <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
                          <webuijsf:legend id="legend" text="#{msgs.label_requiredLabel}" />
                          <br/>

                          <!-- Anchor test-->
                          <webuijsf:label labelLevel="1" text="#{msgs.hyperlink_anchorTextHeading}" id="anchorPagelabel"/>          
                            <webuijsf:helpInline id="anchorPageHelp" text="#{msgs.hyperlink_anchorTestHelp}" />                          
                          <webuijsf:anchor id="anchor1" text="#{msgs.hyperlink_anchorText}"/>
                          <br/>  <br/>                          
                          <webuijsf:label labelLevel="1" text="#{msgs.hyperlink_anchorUrlHeading}" id="anchorUrlLabel"/>          
                            <webuijsf:helpInline id="anchorUrlHelp" text="#{msgs.hyperlink_anchorUrlHelp}" />                          
                          <webuijsf:anchor id="anchor2" text="#{msgs.hyperlink_anchorUrlText}" url="http://www.sun.com"/>
                          <br/>                                                    
                          <br/>  <br/>                          
                          <webuijsf:label labelLevel="1" text="#{msgs.hyperlink_clientAnchorHeading}" id="clientAnchorLabel"/>          
                            <webuijsf:helpInline id="clientAnchorHelp" text="#{msgs.hyperlink_clientAnchorHelp}" />                          
                          <webuijsf:anchor id="anchor3" text="#{msgs.hyperlink_clientAnchorText}" url="http://www.sun.com"/>  
                          <webuijsf:button id="buttonClientAnchor" text="#{msgs.hyperlink_clientAnchorDisable}" onClick ="toggleAnchorDisable(); return false;"/>
                          <br/>                                                                              
                          <!-- Hyperlink List -->
                            
                          <!-- using image and text as hyperlink -->
                            <br/><br/>
                            <webuijsf:label labelLevel="1" text="#{msgs.hyperlink_imageTextHeading}" id="bodylabel"/>
                            <webuijsf:helpInline id="imageHelp" text="#{msgs.hyperlink_textBody}" />
                            <br/>                       
                            <webuijsf:hyperlink id="hyperlinkimage" url="http://www.sun.com" 
                                          toolTip="#{msgs.hyperlink_imageTooltip}" 
                                          disabled="#{HyperlinkBean.linkOnoff}">
                               <webuijsf:image 
				url="/images/version_product_name.png"
				id="image1" 
				 height="40" width="180"
				 alt="#{msgs.hyperlink_imageTooltip}" 
				 border="1" hspace="0" 
				 vspace="5" align="middle" 
				 toolTip="#{msgs.hyperlink_imageTooltip}"/>
                            <f:verbatim><![CDATA[&nbsp;]]></f:verbatim>             
                               <webuijsf:staticText text="#{msgs.hyperlink_textlinkBody}" />
                            </webuijsf:hyperlink>
                                                      
                          <!-- ImageHyperlink -->
                            <br/><br/><br/>
                            <webuijsf:label labelLevel="1" text="#{msgs.hyperlink_imageHeading}" id="imagelink"/>
                            <webuijsf:helpInline id="imageHyperHelp" text="#{msgs.hyperlink_imagelinktext}" />
                            <br/>                        
                            <webuijsf:imageHyperlink id="imagehyperlink" 
			    imageURL="/images/version_product_name.png"
			    url="http://www.sun.com" 
			    toolTip="#{msgs.masthead_imagehyperlinkToolTip}"
                            hspace="5" vspace="5"
			    height="40" width="180"
                            disabled="#{HyperlinkBean.linkOnoff}" />

                            <br/><br/><br/>                          
                            <webuijsf:label labelLevel="1" text="#{msgs.hyperlink_pageHeading}" id="pagelabel"/>
                            <webuijsf:helpInline id="pageHelp" text="#{msgs.hyperlink_textPage}" />
                            <br/>                       
                            <webuijsf:hyperlink id="hyperlinkPage" text="#{msgs.hyperlink_textlinkPage}" 
                                          url="http://www.mozilla.org/" disabled="#{HyperlinkBean.linkOnoff}"
                                          toolTip="#{msgs.hyperlink_linkPagetoolTip}" />

                           <!--  submit hyperlink -->                   
                            <br/><br/><br/>                                  
                            <webuijsf:label labelLevel="1" text="#{msgs.hyperlink_submitHeading}" id="submitlabel"/>
                            <webuijsf:helpInline id="submitHelp" text="#{msgs.hyperlink_textSubmit}" />
                            <br/>
                            <webuijsf:textField id="nameField" 
                              label="#{msgs.hyperlink_textlabel}" 
                              text="#{HyperlinkBean.userName}" 
                              toolTip="#{msgs.hyperlink_tooltip}"
                              required="true" />
                            <br/><br/>                            
                            <webuijsf:hyperlink id="hyperlinkSubmit" text="#{msgs.hyperlink_textlinkSubmit}" 
                                          actionExpression="#{HyperlinkBean.nextPage}" 
                                          toolTip="#{msgs.hyperlink_linkSubmittoolTip}"
                                          disabled="#{HyperlinkBean.linkOnoff}"  />
                            
                            <!-- hyperlink with immediate attribute set to true. -->
                            <br/><br/><br/>
                            <webuijsf:label labelLevel="1" text="#{msgs.hyperlink_immediateHyperlink}" id="requestlabel"/>
                            <webuijsf:helpInline id="immediateHelp" text="#{msgs.hyperlink_textImmediate}" />
                            <br/>                        
                            <webuijsf:hyperlink  id="hyperlinkImmediate" immediate="true" 
					   actionListenerExpression="#{HyperlinkBean.immediateAction}"
                                           actionExpression="#{HyperlinkBean.nextPage}" 
                                           text="#{msgs.hyperlink_textlinkImmediate}" 
                                           disabled="#{HyperlinkBean.linkOnoff}"
                                           toolTip="#{msgs.hyperlink_linkImmediatetoolTip}" >
                            </webuijsf:hyperlink>
                          
                          <!--Client side hyperlink update -->
                          <br/><br/><br/>
                           <webuijsf:label labelLevel="1" text="#{msgs.hyperlink_clientSideLabel}" id="clientSideLabel"/>
                            <webuijsf:helpInline id="clientSideHelp" text="#{msgs.hyperlink_clientSideHelp}" />
                             <br/>
                            <webuijsf:hyperlink id="clientSideLink" text="#{msgs.hyperlink_clientLinkText}"                                           
                                          toolTip="#{msgs.hyperlink_paramlinktooltip}"
                                          disabled="#{HyperlinkBean.linkOnoff}"
                                          actionExpression="#{HyperlinkBean.nextPage}" />             
                            <webuijsf:button id="buttonClientSide1" text="#{msgs.hyperlink_clientLinkVisible}" onClick="toggleVisible();return false;"/>
                            <webuijsf:button id="buttonClientSide2" text="#{msgs.hyperlink_clientLinkDisable}" onClick ="toggleDisable(); return false;"/>
                            <br/>
                            
                          <!--Client side image hyperlink update -->
                          <br/><br/><br/>
                           <webuijsf:label labelLevel="1" text="#{msgs.hyperlink_imgHyperlinkclientSideLabel}" id="clientSideLabel2"/>
                            <webuijsf:helpInline id="clientSideHelp2" text="#{msgs.hyperlink_imgHyperlinkClientSideHelp}" />
                             <br/>
                             <webuijsf:imageHyperlink id="clientSideImageLink" icon="ALARM_MAJOR_MEDIUM"                                           
                                                      toolTip="#{msgs.hyperlink_paramlinktooltip}"
                                                      disabled="#{HyperlinkBean.linkOnoff}"
                                                      actionExpression="#{HyperlinkBean.nextPage}">  
                                                          <f:facet name="disabledImage">
                                                              <webuijsf:image id="disabledImage" icon="ALARM_MASTHEAD_MAJOR_DIMMED"/>
                                                          </f:facet>                  
                             </webuijsf:imageHyperlink>
                            <webuijsf:button id="buttonClientSideImag4" text="#{msgs.hyperlink_imgHyperlinkClientLinkDisable}" onClick ="toggleImageDisable(); return false;"/>
                            <br/>                            
                          <!-- hyperlink using f:param -->
                            <br/><br/><br/>
                            <webuijsf:label labelLevel="1" text="#{msgs.hyperlink_paramHeading}" id="fparam"/>
                            <webuijsf:helpInline id="paramHelp" text="#{msgs.hyperlink_paramtext}" />
                            <br/>
                            <webuijsf:hyperlink id="paramlink" disabled="#{HyperlinkBean.linkOnoff}"  
                                          text="#{msgs.hyperlink_paramlinktext}" 
                                          toolTip="#{msgs.hyperlink_paramlinktooltip}"
                                          actionExpression="#{HyperlinkBean.nextPage}" >
                                <f:param name="dateParam" value="#{HyperlinkBean.startDate}"/>          
                            </webuijsf:hyperlink> 
                                          
                            <!-- Anchor tag -->
                            <br/><br/><br/>
                            <webuijsf:label labelLevel="1" text="#{msgs.hyperlink_anchorHeading}" id="anchorlabel"/>
                            <webuijsf:helpInline id="anchorHelp" text="#{msgs.hyperlink_anchorPageText}" />
                            <br/>
                            <webuijsf:anchor id="anchorlink" disabled="#{HyperlinkBean.linkOnoff}"  
                                          text="#{msgs.hyperlink_anchorlinktext}" url="#anchor1" 
                                          toolTip="#{msgs.hyperlink_anchortoolTip}" />
                                          
                          <!-- Disabling enabling hyperlinks -->
                            <br/><br/><br/>
                            <webuijsf:panelGroup id="pageButtonsGroupBottom">
                                
                                <webuijsf:button id="disable" primary="true" immediate="true" 
                                           actionListenerExpression="#{HyperlinkBean.onOffbuttonAction}"
                                           text="#{msgs.hyperlink_disableButton}" disabled="#{HyperlinkBean.disableHyperlink}" />
                                <webuijsf:button id="enable" primary="true" immediate="true" 
                                           actionListenerExpression="#{HyperlinkBean.onOffbuttonAction}"
                                           text="#{msgs.hyperlink_enableButton}" disabled="#{HyperlinkBean.enableHyperlink}" />             
                            </webuijsf:panelGroup>                       
                          </webuijsf:markup>
                                                    
                                    
                    </webuijsf:form>
                </webuijsf:body> 
            </webuijsf:html>  
        </webuijsf:page>
    </f:view>
</jsp:root>
