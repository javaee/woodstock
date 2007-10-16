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
      
      Copyright 2007 Sun Microsystems, Inc. All rights reserved.
    -->
    <webuijsf:page >
      <webuijsf:html>
        <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
        <webuijsf:head title="#{msgs.button_title}">
	  <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />

          <f:verbatim><![CDATA[
            <script type="text/javascript">
              // The ID of the form used for this page.
              var theForm = "form1";
                      
              // Utility for setting a button's enable/disable state.
              // buttonID: ID of the button whose state will be set.
              // hiddenFieldID: ID of the hidden field that maintains the button's state
              // disable: true to disable the button, false to enable it
              //
              // Both buttonID and hiddenFieldID are the base ID of the component, and
              // not the fully-qualified ID.
              //
              function disableButton(buttonID, hiddenFieldID, disable) {
                  (document.getElementById(theForm + ":" + buttonID)).setProps({disabled: disable});
                  var hidden = document.getElementById(theForm + ":" + hiddenFieldID);
                  if (disable)
                      hidden.setProps({value: 'true'});
                  else
                      hidden.setProps({value: 'false'});
              }
                      
              // Utility for setting enable/disable state for all buttons.
              // disable: true to disable the buttons, false to enable them
              //
              function disableAll(disable) {
                  disableButton('PrimaryButton','primaryButtonDisabled', disable);
                  disableButton('SecondaryButton','secondaryButtonDisabled', disable);
                  var checkbox;
                  checkbox = document.getElementById(theForm + ":PrimaryCheckbox");
                  checkbox.setProps({checked: !disable});
                  checkbox = document.getElementById(theForm + ":SecondaryCheckbox");
                  checkbox.setProps({checked: !disable});
              }
            </script>
          ]]></f:verbatim>
        </webuijsf:head>
        <webuijsf:body>
          <webuijsf:form id="form1">
                       
            <!-- Create hidden fields to maintain the enable/disable state of each button. -->
            <webuijsf:hiddenField id="primaryButtonDisabled" text="#{ButtonBean.primaryDisabled}"/>
            <webuijsf:hiddenField id="secondaryButtonDisabled" text="#{ButtonBean.secondaryDisabled}"/>

            <!-- Masthead -->
            <webuijsf:masthead id="Masthead" productImageURL="/images/example_primary_masthead.png"
              productImageDescription="#{msgs.mastheadAltText}" 
              userInfo="test_user"
              serverInfo="test_server" />     
                         
            <!-- Bread Crumb Component -->
            <webuijsf:breadcrumbs id="breadcrumbs">
              <webuijsf:hyperlink actionExpression="#{ButtonBean.showExampleIndex}" text="#{msgs.exampleTitle}"
                onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
              <webuijsf:hyperlink text="#{msgs.button_title}"/>
            </webuijsf:breadcrumbs>

            <!-- Alert -->
            <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
              <br/>
              <webuijsf:alert id="Alert" rendered="#{ButtonBean.alertRendered}"
                type="information"
                summary="#{ButtonBean.alertSummary}" detail="#{ButtonBean.alertDetail}" />
            </webuijsf:markup>
                       
            <!-- Page Title -->
            <webuijsf:contentPageTitle title="#{msgs.button_title}" />

            <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
                       
                  <!-- Use HTML table for layout.  Note that if we had included this
                       content within the body of contentPageTitle, then we would need
                       to wrap the HTML markup in the f:verbatim tag.  webuijsf:markup could
                       also be used but that is more heavyweight (slower). -->
                  <table>
                    <!-- Icon Button -->
                    <tr>
                      <td>
                        <webuijsf:label id="IconButtonLabel" text="#{msgs.button_iconButtonLabel}" />
                      </td>
                      <td colspan="2">
                        <webuijsf:button id="IconButton" imageURL="/images/check_all.gif"
                          actionListenerExpression="#{ButtonBean.iconActionListener}"
                          actionExpression="#{ButtonBean.actionHandler}"
                          toolTip="#{msgs.button_iconButtonTooltip}"
                          alt="#{msgs.button_iconButtonAlt}" />
                      </td>
                      <td>
                      </td>
                    </tr>

                    <!-- Primary Button -->
                    <tr>
                      <td>
                        <webuijsf:label id="PrimaryButtonLabel" text="#{msgs.button_primaryButtonLabel}" />
                      </td>
                      <td>
                        <webuijsf:button id="PrimaryButton" text="#{msgs.button_primaryButtonText}" primary="true"
                          disabled="#{ButtonBean.primaryDisabled}"
                          actionListenerExpression="#{ButtonBean.primaryActionListener}"
                          actionExpression="#{ButtonBean.actionHandler}"
                          toolTip="#{msgs.button_primaryButtonTooltip}" />
                      </td>
                      <td>
                        <webuijsf:checkbox id="PrimaryCheckbox" label="#{msgs.button_enable}"
                          selected="#{ButtonBean.primaryCBSelected}"
                          onClick="javascript: 
                            var domNode = document.getElementById('form1:PrimaryCheckbox');
                            disableButton('PrimaryButton', 'primaryButtonDisabled', !domNode.getProps().checked); 
                            return true;"/>
                      </td>
                    </tr>

                    <!-- Secondary Button -->
                    <tr>
                      <td>
                        <webuijsf:label id="SecondaryButtonLabel" text="#{msgs.button_secondaryButtonLabel}" />
                      </td>
                      <td>
                        <webuijsf:button id="SecondaryButton" text="#{msgs.button_secondaryButtonText}"
                          disabled="#{ButtonBean.secondaryDisabled}"
                          actionListenerExpression="#{ButtonBean.secondaryActionListener}"
                          actionExpression="#{ButtonBean.actionHandler}"
                          toolTip="#{msgs.button_secondaryButtonTooltip}"
                          primary="false" />
                      </td>
                      <td>
                        <webuijsf:checkbox id="SecondaryCheckbox" label="#{msgs.button_enable}"
                          selected="#{ButtonBean.secondaryCBSelected}"
                          onClick="javascript: 
                            var domNode = document.getElementById('form1:SecondaryCheckbox');
                            disableButton('SecondaryButton', 'secondaryButtonDisabled', !domNode.getProps().checked); 
                            return true;"/>
                      </td>
                    </tr>
                  </table>

                  <br/><br/>
                    
                  <!-- Submit button -->
                  <webuijsf:button id="submitButton" text="#{msgs.button_testCase_submit}" 
                    actionExpression="showButtonResults" />

                  <!-- Test Case Menu -->
                  <!--
                       Because we are setting button enable/disable states on the client,
                       we have to set those states in the onChange event.  This means we can
                       NOT use a valueChangeListener in the backing bean because that listener
                       will be called with the new value early in the event life-cycle (after
                       the Apply Request Values phase), but then the hidden values will be passed
                       as request parameters later on in the Update Model Values phase.
                          
                       Note also that if this example had input data that required validation,
                       we would not include actions to disable/enable buttons in a dropdown
                       like this because that would cause the input data to go thru the
                       JSF lifecycle, resulting in the data model being updated when all that
                       was required was to change the button states.  This is generally not
                       good practice.  Instead, it would be better to provide disable/enable
                       interfaces via components with "immediate=true" so that the Update
                       Model phase is bypassed.  However, since this example is merely to 
                       demonstrate button style features, including the ability to dynamically 
                       enable and disable buttons in Javascript, we don't show this distinction.
                  -->
                  <webuijsf:dropDown id="TestCaseMenu" items="#{ButtonBean.testCaseOptions}"
                    actionExpression="#{ButtonBean.testCaseActionHandler}"
                    onChange="var selected=document.getElementById(theForm + ':TestCaseMenu').getSelectedValue();
                        if (selected == 'button_testCase_disableAll') {
                            disableAll(true);
                        }
                        if (selected == 'button_testCase_enableAll') {
                            disableAll(false);
                        }"
                    submitForm="true"
                    forgetValue="true"/>
            </webuijsf:markup>
          </webuijsf:form>
        </webuijsf:body> 
      </webuijsf:html>  
    </webuijsf:page>
  </f:view>
</jsp:root>
