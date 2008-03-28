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
        <webuijsf:head id="head" title="#{msgs.rating_title}" >
 	  <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
           <webuijsf:script type="text/javascript" url="rating.js" />
             <style type="text/css">
               .description {
                   width: 60%;
                   float: left;
                   margin-right: 5em;
               }
               .config {
                   float:left;
               }
               .divider {
                   clear: both;
               }
               .avgLabel {
                   margin-right: .5em;
               }
               .avgText {
               }
             </style>
        </webuijsf:head>
        <webuijsf:body id="body" onLoad="rating_init();">
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
              <webuijsf:hyperlink 
                text="#{msgs.index_title}" 
                toolTip="#{msgs.index_title}" 
                actionExpression="#{RatingBean.showExampleIndex}" 
                onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
              <webuijsf:hyperlink text="#{msgs.rating_title}"/>
            </webuijsf:breadcrumbs>

            <!-- Content Page Title -->
            <webuijsf:contentPageTitle id="contentPageTitle"
                helpText="#{msgs.rating_help}"
                title="#{msgs.rating_title}" >
  
                <!-- Submit and Reset buttons -->
                <f:facet name="pageButtonsTop">
                  <webuijsf:panelGroup id="pageButtonsGroupTop">
  
                    <webuijsf:button id="submitButton" primary="true"
                      text="#{msgs.rating_submitButton}"
                      toolTip="#{msgs.rating_submitButtonTooltip}"
                      actionExpression="showRatingResults" />
  
                    <webuijsf:button id="resetButton" primary="false"
                      text="#{msgs.rating_resetButton}" 
                      toolTip="#{msgs.rating_resetButtonTooltip}"
                      actionExpression="#{RatingBean.reset}"/>
                   </webuijsf:panelGroup>
                 </f:facet>
            </webuijsf:contentPageTitle>

            <br /><br />
                       
            <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">

              <!-- Example 1 -->

              <div class="description">
                <webuijsf:staticText id="rating1Desc" text="#{msgs.rating1_description}" />
              </div>

              <div class="config">
                <webuijsf:rating id="rating1"
                  autoSubmit="true"
                  includeModeToggle="true"
                  notInterestedHoverText="#{RatingBean.notInterestedHoverText}"
                  notInterestedAcknowledgedText="#{RatingBean.notInterestedAcknowledgedText}"
                  gradeAcknowledgedText="#{RatingBean.gradeAcknowledgedText}"
                  gradeHoverTexts="#{RatingBean.gradeHoverTexts}"
                  clearHoverText="#{RatingBean.clearHoverText}"
                  clearAcknowledgedText="#{RatingBean.clearAcknowledgedText}"
                  modeToggleHoverTexts="#{RatingBean.modeToggleHoverTexts}"
                  modeToggleAcknowledgedTexts="#{RatingBean.modeToggleAcknowledgedTexts}"
                  grade="#{RatingBean.grade1}"
                  averageGrade="#{RatingBean.averageGrade1}"
                  maxGrade="#{RatingBean.maxGrade}">
                </webuijsf:rating>

                <br />

                <webuijsf:staticText id="avglabel1" styleClass="avgLabel" 
                  text="#{msgs.rating_avgRating}" />
                <webuijsf:staticText id="avgtext1" styleClass="avgText" 
                  text="#{RatingBean.averageGrade1}" />
              </div>

              <div class="divider"></div> <br /><br /><br />

              <!-- Example 2 -->

              <div class="description">
                <webuijsf:staticText id="rating2Desc" text="#{msgs.rating2_description}" />
              </div>

              <div class="config">
                <webuijsf:rating id="rating2"
                  autoSubmit="true"
                  includeModeToggle="false"
                  notInterestedHoverText="#{RatingBean.notInterestedHoverText}"
                  notInterestedAcknowledgedText="#{RatingBean.notInterestedAcknowledgedText}"
                  gradeAcknowledgedText="#{RatingBean.gradeAcknowledgedText}"
                  gradeHoverTexts="#{RatingBean.gradeHoverTexts}"
                  clearHoverText="#{RatingBean.clearHoverText}"
                  clearAcknowledgedText="#{RatingBean.clearAcknowledgedText}"
                  grade="#{RatingBean.grade2}"
                  averageGrade="#{RatingBean.averageGrade2}"
                  maxGrade="#{RatingBean.maxGrade}">
                </webuijsf:rating>
  
                <br />
  
                <webuijsf:rating id="rating2Avg"
                  autoSubmit="false"
                  includeText="false"
                  includeNotInterested="false"
                  includeClear="false"
                  includeModeToggle="false"
                  inAverageMode="true"
                  modeReadOnly="true"
                  gradeReadOnly="true"
                  grade="#{RatingBean.grade2}"
                  averageGrade="#{RatingBean.averageGrade2}"
                  maxGrade="#{RatingBean.maxGrade}">
                </webuijsf:rating>

                <br />

                <webuijsf:staticText id="avglabel2" styleClass="avgLabel" 
                  text="#{msgs.rating_avgRating}" />
                <webuijsf:staticText id="avgtext2" styleClass="avgText" 
                  text="#{RatingBean.averageGrade2}" />
              </div>

              <div class="divider"></div> <br /><br /><br />

              <!-- Example 3 -->

              <div class="description">
                <webuijsf:staticText id="rating3Desc" text="#{msgs.rating3_description}" />
              </div>

              <div class="config">
                <webuijsf:rating id="rating3"
                  autoSubmit="false"
                  includeModeToggle="true"
                  notInterestedHoverText="#{RatingBean.notInterestedHoverText}"
                  notInterestedAcknowledgedText="#{RatingBean.notInterestedAcknowledgedText}"
                  gradeAcknowledgedText="#{RatingBean.gradeAcknowledgedText}"
                  gradeHoverTexts="#{RatingBean.gradeHoverTexts}"
                  clearHoverText="#{RatingBean.clearHoverText}"
                  clearAcknowledgedText="#{RatingBean.clearAcknowledgedText}"
                  modeToggleHoverTexts="#{RatingBean.modeToggleHoverTexts}"
                  modeToggleAcknowledgedTexts="#{RatingBean.modeToggleAcknowledgedTexts}"
                  grade="#{RatingBean.grade3}"
                  averageGrade="#{RatingBean.averageGrade3}"
                  maxGrade="#{RatingBean.maxGrade}">
                </webuijsf:rating>

                <br />

                <webuijsf:staticText id="avglabel3" styleClass="avgLabel" 
                  text="#{msgs.rating_avgRating}" />
                <webuijsf:staticText id="avgtext3" styleClass="avgText" 
                  text="#{RatingBean.averageGrade3}" />
              </div>

            </webuijsf:markup>
          </webuijsf:form>
        </webuijsf:body> 
      </webuijsf:html>  
    </webuijsf:page>
  </f:view>
</jsp:root>                       
