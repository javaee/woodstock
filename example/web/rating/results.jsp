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
      
      Copyright 2008 Sun Microsystems, Inc. All rights reserved.
    -->
    <webuijsf:page >
      <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
      <webuijsf:html id="html">
        <webuijsf:head id="head" title="#{msgs.rating_resultsTitle}" >
 	  <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
        </webuijsf:head>
        <webuijsf:body id="body">
          <webuijsf:form id="form">

            <!-- Masthead -->
            <webuijsf:masthead id="Masthead" 
              productImageURL="/images/example_primary_masthead.png"
              productImageHeight="40"
              productImageWidth="188"
              productImageDescription="#{msgs.mastheadAltText}" 
              userInfo="test_user"
              serverInfo="test_server" />     
                         
            <!-- Bread Crumb Component -->
            <webuijsf:breadcrumbs id="breadcrumbs">
              <webuijsf:hyperlink id="indexPageLink"
                text="#{msgs.index_title}" 
                toolTip="#{msgs.index_title}" 
                actionExpression="#{RatingBean.showExampleIndex}" 
                onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
              <webuijsf:hyperlink id="ratingLink"
                text="#{msgs.rating_title}"
                actionExpression="#{RatingBean.showRating}" 
                onMouseOver="javascript:window.status='#{msgs.rating_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
              <webuijsf:hyperlink text="#{msgs.rating_resultsTitle}"/>
            </webuijsf:breadcrumbs>

            <!-- Content Page Title -->
            <webuijsf:contentPageTitle id="contentPageTitle"
              helpText="#{msgs.rating_resultsHelpText}"
              title="#{msgs.rating_resultsTitle}" >
  
              <f:facet name="pageButtonsTop">
                <!-- Back button -->
                <webuijsf:button id="backButton" primary="false"
                  text="#{msgs.rating_backButton}" 
                  actionExpression="#{RatingBean.showRating}"/>
               </f:facet>
            </webuijsf:contentPageTitle>

            <br />
                       
            <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">

              <table border="1" cellpadding="5" cellspacing="5">
                <tr>
                  <th><webuijsf:staticText text="#{msgs.rating_resultsExampleHeader}"/></th>
                  <th><webuijsf:staticText text="#{msgs.rating_resultsAvgGradeHeader}"/></th>
                  <th><webuijsf:staticText text="#{msgs.rating_resultsNumGradesHeader}"/></th>
                  <th><webuijsf:staticText text="#{msgs.rating_resultsNotInterestedHeader}"/></th>
                </tr>
           
                <tr>
                  <td>(1)</td>
                  <td><webuijsf:staticText id="avgtext1" text="#{RatingBean.averageGrade1}" /></td>
                  <td><webuijsf:staticText id="numgrades1" text="#{RatingBean.numGrades1}" /></td>
                  <td><webuijsf:staticText id="notinttext1" text="#{RatingBean.numNotInterested1}" /></td>
                </tr>
           
                <tr>
                  <td>(2)</td>
                  <td><webuijsf:staticText id="avgtext2" text="#{RatingBean.averageGrade2}" /></td>
                  <td><webuijsf:staticText id="numgrades2" text="#{RatingBean.numGrades2}" /></td>
                  <td><webuijsf:staticText id="notinttext2" text="#{RatingBean.numNotInterested2}" /></td>
                </tr>
           
                <tr>
                  <td>(3)</td>
                  <td><webuijsf:staticText id="avgtext3" text="#{RatingBean.averageGrade3}" /></td>
                  <td><webuijsf:staticText id="numgrades3" text="#{RatingBean.numGrades3}" /></td>
                  <td><webuijsf:staticText id="notinttext3" text="#{RatingBean.numNotInterested3}" /></td>
                </tr>
              </table>

            </webuijsf:markup>
          </webuijsf:form>
        </webuijsf:body> 
      </webuijsf:html>  
    </webuijsf:page>
  </f:view>
</jsp:root>                       
