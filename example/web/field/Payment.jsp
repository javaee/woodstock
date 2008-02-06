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
      <webuijsf:head id="head" title="#{msgs.field_autoValidateTitle}">
	<webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
<webuijsf:script type="text/javascript">

    /**
     * Listener class for textfields.  Note the ID naming policy we
     * use in this example:
     *   name for the textfield
     *   nameLabel for the textfield's label
     *   nameError2 for the static text component to hold the error
     *                msg when the auto-validation fails.  When the message
     *                component is integrated into the widget event system,
     *                the static text error fields can be deleted and the
     *                '2' can be dropped.
     * This naming policy is strictly for convenience, since we manage
     * events the same for both textfields.
     *
     * @param srcID  the ID of the element whose events we listen for.
     *               This is NOT the fully-qualified ID, but simply the
     *               basename.  Note again that this is simply due to
     *               application policy in that the textfields have the
     *               same parent container.
     */
    function TextfieldListener(srcID) {
	this.srcID = 'form:contentPageTitle:' + srcID;
	this.labelID = this.srcID + 'Label';
	this.errorID = this.srcID + 'Error2';
    }

    /**
     * Interface for a TextfieldListener class to receive events.
     * We make the function an instance method by assigning it
     * to the prototype object of the constructor.
     *
     * @param props properties associated with the textfield widget
     */
    function TextfieldNotify(props) {
	// Ensure we have the correct event.
	if (props.id != this.srcID) { return; }

	// Update label.
	var label = document.getElementById(this.labelID);
	label.setProps({
	    valid: props.valid
	});
 
	// Update error message.
	var message = document.getElementById(this.errorID);
	if (props.valid == true) {
	    message.setProps({
		value: "",
		visible: !props.valid
	    });
	} else {
	    message.setProps({
		value: props.detail,
		visible: !props.valid
	    });
        }
    }
    TextfieldListener.prototype.notify = TextfieldNotify;

    function init() {               
        var domNode = document.getElementById("form:contentPageTitle:creditCard");
        if (domNode == null || domNode.event == null) { 
            return setTimeout('init();', 10);
        }

        // Subscribe to validation event for credit card number.
        var listener = new TextfieldListener("creditCard");
        domNode.subscribe(domNode.event.validation.endTopic, listener, listener.notify);

        // Subscribe to validation event for amount.
        var listener = new TextfieldListener("amount");
        domNode.subscribe(domNode.event.validation.endTopic, listener, listener.notify);
    }
</webuijsf:script>
        </webuijsf:head>
      <webuijsf:body id="body" onLoad="init();">
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
          <webuijsf:breadcrumbs id="breadcrumbs">
            <webuijsf:hyperlink id="indexPageLink"
                text="#{msgs.index_title}"
                toolTip="#{msgs.index_title}"
                actionExpression="#{PaymentBean.showExampleIndex}"
                onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
            <webuijsf:hyperlink id="tabsetIndexLink"
                text="#{msgs.field_indexTitle}"
                toolTip="#{msgs.field_indexTitle}"
                actionExpression="#{PaymentBean.showTextInputIndex}"
                onMouseOver="javascript:window.status='#{msgs.field_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
            <webuijsf:hyperlink id="tabsetLink" text="#{msgs.field_autoValidateTitle}" />
          </webuijsf:breadcrumbs>

          <!-- Content Page Title -->
          <webuijsf:contentPageTitle id="contentPageTitle"
              title="#{msgs.field_autoValidateTitle}" >

              <!-- Submit and Reset buttons -->
              <f:facet name="pageButtonsTop">
                <webuijsf:panelGroup id="pageButtonsGroupTop">

                  <webuijsf:button id="submitButton" primary="true"
                    text="#{msgs.field_autoValidateSubmitButton}"
                    toolTip="#{msgs.field_autoValidateSubmitButtonTooltip}"
                    actionExpression="showTextInputAutoValidateResults" />

                  <webuijsf:button id="resetButton" primary="false"
                    text="#{msgs.field_autoValidateResetButton}" 
                    toolTip="#{msgs.field_autoValidateResetButtonTooltip}" 
                    immediate="true"
                    actionListenerExpression="#{PaymentBean.resetListener}" />
                 </webuijsf:panelGroup>
               </f:facet>

            <br/><br/>
            <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">

              <webuijsf:legend id="legend" text="#{msgs.field_requiredLabel}" />

              <br/>
              <table>
                <!-- Credit Card number -->
                <tr>
                  <td></td>
                  <td>
                    <!-- Because the message component is not yet integrated
                         into the widget event system, we use a staticText
                         component to render errors when auto-validating
                         using Ajax.  If the page is submitted, errors are
                         rendered via the message component.
                    -->
                    <webuijsf:message id="creditCardError" for="creditCard" />
                    <webuijsf:staticText id="creditCardError2"
                      text=""
                      visible="false"
                      style="color:red"/>
                  </td>
                </tr>
                <tr>
                  <td>
                    <webuijsf:label id="creditCardLabel" for="creditCard"
                      toolTip="#{msgs.field_creditCardTooltip}"
                      text="#{msgs.field_creditCardLabel}" />
                  </td>
                  <td>
                    <webuijsf:textField id="creditCard"
                      text="#{PaymentBean.cardNumber}"
                      toolTip="#{msgs.field_creditCardTooltip}"
                      required="true"
                      autoValidate="true"
                      maxLength="19"
                      validatorExpression="#{PaymentBean.cardNumberValidator}">
                    </webuijsf:textField>
                  </td>
                </tr>

                <tr><td><div style="height:10px"></div></td></tr>

                <!-- Amount -->
                <tr>
                  <td></td>
                  <td>
                    <!-- Because the message component is not yet integrated
                         into the widget event system, we use a staticText
                         component to render errors when auto-validating
                         using Ajax.  If the page is submitted, errors are
                         rendered via the message component.
                    -->
                    <webuijsf:message id="amountError" for="amount" />
                    <webuijsf:staticText id="amountError2"
                      text=""
                      visible="false"
                      style="color:red"/>
                  </td>
                </tr>
                <tr>
                  <td>
                    <webuijsf:label id="amountLabel" for="amount"
                      toolTip="#{msgs.field_amountTooltip}"
                      text="#{msgs.field_amountLabel}" />
                  </td>
                  <td>
                    <webuijsf:textField id="amount"
                      text="#{PaymentBean.amount}"
                      toolTip="#{msgs.field_amountTooltip}"
                      required="true"
                      autoValidate="true"
                      validatorExpression="#{PaymentBean.amountValidator}">
                    </webuijsf:textField>
                  </td>
                </tr>
              </table>
            </webuijsf:markup>
          </webuijsf:contentPageTitle>
        </webuijsf:form>
      </webuijsf:body>
    </webuijsf:html>
  </webuijsf:page>
</f:view>

</jsp:root>
