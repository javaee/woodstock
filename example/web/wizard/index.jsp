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

<jsp:root version="2.0" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
<jsp:directive.page contentType="text/html"/>
<f:view>
  <webuijsf:page id="Page">
    <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
    <webuijsf:html id="html">  
      <webuijsf:head id="head" title="#{msgs.wiz_index_title}">
	<webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
      </webuijsf:head>
      <webuijsf:body id="body">
        <webuijsf:form id="form1">                             
            
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
            <webuijsf:hyperlink id="indexPageLink" 
                text="#{msgs.index_title}"
                toolTip="#{msgs.index_title}"
                actionExpression="#{IndexBean.showIndex}" 
                onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
            <webuijsf:hyperlink id="wizardIndexLink" text="#{msgs.wiz_index_title}"/>
          </webuijsf:breadcrumbs>
          
          <!-- Java script functions to launch wizard pop-up windows -->
          <script type="text/javascript">
 	    function wizard_popup(url, name, height, width) {
 		var top=((screen.height-(screen.height/1.618))-(height/2));
 		var left=((screen.width-width)/2);
 		var newurl = url + "?&amp;WIZARD_NAME=" + name;
 		var args= "height=" + height + ",width=" + width +
 			    ",top=" + top + ",left=" + left;
 		window.open(newurl, name, args);
 		return false;
 	    }
 	    function wizard_launch(url) {
 		wizard_popup("../faces/wizard/" + url,
 			"Wizard", 450, 600);
 	    }
          </script>

          <!-- Content Page Title -->
          <webuijsf:contentPageTitle id="contentPageTitle" title="#{msgs.wiz_index_title}">
              
            <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">              
              <webuijsf:markup tag="br" singleton="true" />
                
              <webuijsf:markup tag="div" style="padding-bottom: 8px">              
                <!-- Simple Wizard Launch -->
                <webuijsf:markup tag="p" singleton="true" />
                <webuijsf:staticText id="wiz_popup1_desc"
                    text="#{msgs.wiz_index_popup1_desc}"/>
                <webuijsf:markup tag="p" singleton="true" />
                <webuijsf:button id="wiz_popup1_button" 
                    primary="true"                    
                    text="#{msgs.wiz_index_button1}"                   
                    toolTip="#{msgs.wiz_index_button1_tip}"		    
                    onClick="wizard_launch('simpleWizard.jsp'); return false;"/>

              </webuijsf:markup>               

            </webuijsf:markup>          
          </webuijsf:contentPageTitle>           
        </webuijsf:form>
      </webuijsf:body>
    </webuijsf:html>  
  </webuijsf:page>
</f:view>

</jsp:root>
