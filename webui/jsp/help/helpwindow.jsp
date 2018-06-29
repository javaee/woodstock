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

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %> 
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@taglib uri="http://www.sun.com/webui/webuijsf" prefix="webuijsf" %>

<%
  // set certain browser dependent frame style attributes
  String outerFramesetRows = "100";
  String middleFramesetSpacing = "";
  String frameBorderTrue = "yes";
  String frameBorderFalse = "no";
  String middleFramesetBorderColor = "";
  String navFrameBorder = "yes";
  String navFrameScrolling = "auto";
  String innerFramesetSpacing = "";
  String innerFramesetBorderColor = "";
  String buttonFrameBorder = "yes";
  
  if (request.getHeader("USER-AGENT").indexOf("MSIE") >= 0) {
      // set the style attrs for IE
      outerFramesetRows = "104";
      middleFramesetSpacing = "\n framespacing=\"2\"";
      innerFramesetSpacing = "\n framespacing=\"1\"";
      frameBorderTrue = "1";
      frameBorderFalse = "0";
      middleFramesetBorderColor = "\n bordercolor=\"#CCCCCC\"";
      navFrameBorder = "1";
      navFrameScrolling = "auto";
      innerFramesetSpacing = "\nf ramespacing=\"1\"";
      innerFramesetBorderColor = "\n bordercolor=\"#939CA3\"";
      buttonFrameBorder = "1";
  }
  
  // get the query params from the helpwindow link
  String windowTitle = request.getParameter("windowTitle") != null ?
      request.getParameter("windowTitle") : "";
  String helpFile = request.getParameter("helpFile") != null ?
      request.getParameter("helpFile") : "";
  %>

<f:view>
  <HTML>
    <HEAD><TITLE><%=windowTitle%></TITLE></HEAD>
 
<!-- Frameset for Nav, ButtonNav, and Content frames -->
<frameset cols="33%,67%"
 frameborder="<%=frameBorderTrue%>"
 border="1">

<!-- Nav Frame -->
<frame src="<h:outputText value="#{JavaHelpBean.navigatorUrl}"/>"
 name="navFrame"
 frameBorder="<%=navFrameBorder%>"
 scrolling="<%=navFrameScrolling%>"
 id="navFrame"
 title="<h:outputText value="#{JavaHelpBean.navFrameTitle}" />" />

<!-- Frameset for ButtonNav and Content Frames -->
<frameset rows="32,*"
 frameborder="<%=frameBorderTrue%>"
 border="0">

<!-- ButtonNav Frame -->
<frame src="<h:outputText value="#{JavaHelpBean.buttonFrameUrl}"/>"
 name="buttonNavFrame"
 frameBorder="<%=buttonFrameBorder%>"
 scrolling="no" border="0"
 id="buttonNavFrame"
 title="<h:outputText value="#{JavaHelpBean.buttonFrameTitle}" />" />

<!-- Content Frame -->
<frame src="<h:outputText value="#{JavaHelpBean.localizedHelpPath}" /><%=helpFile %>"
 name="contentFrame" border="1"
 frameBorder="<%=frameBorderTrue%>"
 scrolling="auto"
 id="contentFrame"
 title="<h:outputText value="#{JavaHelpBean.contentFrameTitle}" />" />

</frameset>
</frameset>

<noframes>
<body>
<webuijsf:staticText id="noFramesText" text="#{JavaHelpBean.noFrames}" />
</body>
</noframes>

  </HTML>
</f:view>
