<?xml version="1.0" encoding="UTF-8"?>
<!--
      DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

	  Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.

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
-->

<jsp:root version="2.0"
          xmlns:f="http://java.sun.com/jsf/core" 
          xmlns:h="http://java.sun.com/jsf/html" 
          xmlns:jsp="http://java.sun.com/JSP/Page" 
          xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
          
<jsp:directive.page contentType="text/html" /> 
                    
<!-- Page with Images and ImageHyperlink -->
<f:view>
  <webuijsf:page id="page6" >
    <webuijsf:html id="html6" >
      <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
        <webuijsf:head id="head6" title="#{msgs.masthead_title}"/>
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
              </ul>
            
            </webuijsf:form>
          </webuijsf:body>
    </webuijsf:html>
  </webuijsf:page>
</f:view>
</jsp:root>
