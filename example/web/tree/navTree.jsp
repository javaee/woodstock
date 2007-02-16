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
  <webuijsf:page frame="true">
    <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
    <webuijsf:html id="html">  
      <webuijsf:head id="head" title="#{msgs.tree_navTreeTitle}">
	<webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
      </webuijsf:head>

      <!-- Outer frameset containing all components of the page -->
      <webuijsf:frameSet id="outerFrameSet"
        rows="#{NavTreeBean.outerFramesetRows}"
        frameBorder="true" border="2" frameSpacing="2">
            
        <!-- Masthead frame-->
        <webuijsf:frame id="mastheadFrame" name="mastheadFrame"
          scrolling="no" noResize="true"
          marginWidth="0" marginHeight="0"
          url="header.jsp" />
          
        <!-- Inner frameset containing everything BUT the masthead -->
        <webuijsf:frameSet id="innerFrameSet" cols="20%,*"
          frameBorder="true"
          border="2"
          frameSpacing="2">

          <!-- Navigation tree frame -->
          <webuijsf:frame id="navtreeFrame" name="navtreeFrame"
            marginWidth="5" marginHeight="5"
            scrolling="auto" 
            frameBorder="true"
            url="treeFrame.jsp" />

          <!-- Content frame -->
          <webuijsf:frame id="contentsFrame" name="contentsFrame"
            scrolling="auto"
            frameBorder="true"
            url="content.jsp" />

        </webuijsf:frameSet>
      </webuijsf:frameSet>

      <!-- Displayed when frames are not supported -->
      <noframes>
        <body>
          <webuijsf:staticText id="indexText" text="#{msgs.tree_framesRequired}" />
        </body>
      </noframes>
              
    </webuijsf:html>  
  </webuijsf:page>
</f:view>

</jsp:root>
