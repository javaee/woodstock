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
  <webuijsf:page>
    <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
    <webuijsf:html id="html">  
      <webuijsf:head id="head" title="#{msgs.tabset_statesTitle}">
	<webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
      </webuijsf:head>
      <webuijsf:body id="body">
        <webuijsf:form id="form">                             
            
          <!-- Masthead -->
          <webuijsf:masthead id="masthead"
             productImageURL="/images/example_primary_masthead.png"
             productImageHeight="40"
             productImageWidth="188"
             userInfo="test_user" 
             serverInfo="test_server"
             productImageDescription="#{msgs.mastheadAltText}" />
          
          <!-- Breadcrumbs -->   
          <webuijsf:breadcrumbs id="breadcrumbs"
            styleClass="#{themeStyles.BREADCRUMB_GRAY_DIV}" >

            <webuijsf:hyperlink id="indexPageLink"
                text="#{msgs.index_title}"
                toolTip="#{msgs.index_title}"
                actionExpression="#{StateBean.showExampleIndex}"
                onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
            <webuijsf:hyperlink id="tabsetIndexLink"
                text="#{msgs.tabset_title}"
                toolTip="#{msgs.tabset_title}"
                actionExpression="#{StateBean.showTabsetIndex}"
                onMouseOver="javascript:window.status='#{msgs.tabset_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
            <webuijsf:hyperlink id="tabsetLink" text="#{msgs.tabset_statesTitle}" />
          </webuijsf:breadcrumbs>

          <!-- Content Page Title -->
          <webuijsf:contentPageTitle id="contentPageTitle"
              styleClass="#{themeStyles.BREADCRUMB_GRAY_BACKCOLOR}"
              title="#{msgs.tabset_statesTitle}" >

              <!-- Submit and Reset buttons -->
              <f:facet name="pageButtonsTop">
                <webuijsf:panelGroup id="pageButtonsGroupTop">

                  <webuijsf:button id="submitButton" primary="true"
                    text="#{msgs.tabset_submitButton}"
                    actionExpression="showTabsetStateResults" />

                  <webuijsf:button id="cancelButton" primary="false"
                    text="#{msgs.tabset_resetButton}" 
                    actionExpression="#{StateBean.reset}"/>
                 </webuijsf:panelGroup>
               </f:facet>
          </webuijsf:contentPageTitle>


	  <!-- Tabset -->
          <webuijsf:tabSet id="tabset" selected="#{StateBean.selectedTab}"
            actionListenerExpression="#{StateBean.tabClicked}">

            <!-- Note that FileChooser explicitly applies themeStyles.CONTENT_MARGIN
                 so its content has a left margin that makes it align
                 with the left edge of the leftmost tab.  This is not the
                 case with other components, specifically those used in
                 the other tabs.  Thus, we apply this style to these other
                 tabs, below, so they too align with the leftmost tab.
            -->

            <!-- Textfield Tab -->
            <webuijsf:tab id="tabTextfield" text="#{msgs.tabset_tabTextField}">
              <br />
              <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
                <webuijsf:textField id="userId"
                  text="#{StateBean.userId}"
                  label="#{msgs.tabset_userIdLabel}"
                  disabled="false"
                  toolTip="#{msgs.tabset_userIdTooltip}"
                  required="false"/>
                <br />
                <br />
                <webuijsf:passwordField id="password"
                  password="#{StateBean.password}"
                  label="#{msgs.tabset_passwordLabel}"
                  disabled="false"
                  toolTip="#{msgs.tabset_passwordTooltip}"
                  required="false"/>
              </webuijsf:markup>
            </webuijsf:tab>

            <!-- FileChooser Tab -->
            <webuijsf:tab id="tabChooser" text="#{msgs.tabset_tabChooser}">
              <br />
              <!-- File Chooser -->
              <!-- We create a binding so that our backing bean has more
                   more control to manipulate the chooser's properties
                   and it's child components.  For example, we want
                   the "lookin" property to persist thru page submits
                   to show that state can be maintained.  Chooser behavior
                   makes this problematic if you simply bind the attributes
                   to bean methods.
              -->
              <webuijsf:fileChooser id="fileChooser"
                binding="#{StateBean.chooser}">
                <f:facet name="fileChooserLabel">
                  <webuijsf:staticText id="filelabel"
                    styleClass="#{themeStyles.CONTENT_FIELDSET_LEGEND_DIV}"
                    text="#{msgs.tabset_fileChooserLabel}" />
                </f:facet>
              </webuijsf:fileChooser>

            </webuijsf:tab>

            <!-- Orderable List Tab -->
            <webuijsf:tab id="tabOrderableList"
              text="#{msgs.tabset_tabOrderableList}">
              <br />
              <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
                <webuijsf:orderableList id="OrderableList"
                  label="#{msgs.tabset_orderableHeading}"
                  list="#{StateBean.listItems}"
                  multiple="true"
                  labelOnTop="true"
                  moveTopBottom="true" />
              </webuijsf:markup>

            </webuijsf:tab>

            <!-- AddRemove Tab -->
            <webuijsf:tab id="tabAddremove" text="#{msgs.tabset_tabAddRemove}">
              <br />
              <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
                <webuijsf:addRemove id="addRemove"
                  label="#{msgs.tabset_addRemoveSelectAuthors}"
                  moveButtons="true"
                  selected="#{StateBean.selectedOptions}"
                  items="#{StateBean.availableOptions}"
                  availableItemsLabel="#{msgs.tabset_addRemoveAvailableLabel}"
                  selectedItemsLabel="#{msgs.tabset_addRemoveSelectedLabel}"
                  required="false"
                  selectAll="true"
                  labelOnTop="false" />
              </webuijsf:markup>

            </webuijsf:tab>

            <!-- Calendar -->
            <webuijsf:tab id="tabCalendar" text="#{msgs.tabset_tabCalendar}" >
              <br />
              <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
                <webuijsf:calendar label="#{msgs.tabset_tabCalendarLabel}"
                  selectedDate="#{StateBean.date}" />
              </webuijsf:markup>
            </webuijsf:tab>

          </webuijsf:tabSet>

        <script type="text/javascript">
          // In order to submit values for the filechooser, an association must
          // be defined between the chooser and an element that submits the
          // page.  We use the Submit button in the contentPageTitle as that
          // element, but only if the chooser actually exists - it will only
          // when the chooser tab is the selected tab when the page renders.
          var domNode = document.getElementById("form:contentPageTitle:tabset:tabChooser:fileChooser");
          if (domNode != null) {
              domNode.setChooseButton("form:contentPageTitle:pageButtonsGroupTop:submitButton");
          }
        </script>
        </webuijsf:form>
      </webuijsf:body>
    </webuijsf:html>
  </webuijsf:page>
</f:view>

</jsp:root>
