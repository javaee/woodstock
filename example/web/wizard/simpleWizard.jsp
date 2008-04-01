<jsp:root version="2.0"
    xmlns:f="http://java.sun.com/jsf/core"
    xmlns:h="http://java.sun.com/jsf/html"
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
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
  <webuijsf:page id="SimpleWizardPage">
    <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources"
      var="msgs" />
    <webuijsf:html id="html">  
      <webuijsf:head id="head" title="#{msgs.wiz_simple_title}">
      <webuijsf:script>
        function init() {
            var domNode = document.getElementById('form1:wizard1');
            if (domNode == null || typeof domNode.wizOnLoad != "function") {
                return setTimeout('init();', 10);
            }
            domNode.wizOnLoad();
        }
        function setDisabled(elementId, disabled, value) {
            var domNode = document.getElementById(elementId);
            domNode.setProps({
                disabled: disabled,
                value: (value) ? value : null
            });
        }
      </webuijsf:script>
      </webuijsf:head>
      <webuijsf:body id="body" onLoad="init();">
          <webuijsf:form id="form1">                             
            
          <!-- Simple Wizard:

		This wizard collects information for adding a new user
		account.  A finish panel then displays the gathered
		information and a results panel shows the user account
		was created.  A Wizard event listener handler is used
		to perform the add operation and to clean up the wizard
		when its completed.  We use simple table layout to align
                columns in the step pages.

          -->
          <webuijsf:wizard id="wizard1"
            title="#{msgs.wiz_user_title}"
            eventListener="#{SimpleWizardBean.wizardEventListener}"
            onPopupDismiss="document.getElementById('form1:wizard1').closeAndForward('form1', '../wizard/index.jsp', true);">

            <!-- ====================================== -->
            <!-- Step 1: Get user identity information. -->
            <webuijsf:wizardStep id="step1"
              summary="#{msgs.wiz_simple_step1_summary}"
              title="#{msgs.wiz_simple_step1_title}"
              detail="#{msgs.wiz_simple_step1_detail}"
              help="#{msgs.wiz_simple_step1_help}">

                <webuijsf:legend id="legend" style="margin-bottom:10px"
                  text="#{msgs.wiz_requiredLabel}" />

		<table border="0">
                <!-- User name field is required and validated.
		     Guidelines require inline alert messages be used.
		     Guidelines suggest inline help be used.
                     Set textField label empty to avoid id's in JSF messages.
                     Set textField onKeyPress to prevent browser auto submit
                       when enter key pressed.
                -->
		<tr><td>
		</td><td>
		<webuijsf:message showDetail="true" for="name1"/>
		</td></tr>
		<tr><td>
                <webuijsf:label text="#{msgs.wiz_user_username}" for="name1"
                  toolTip="#{msgs.wiz_user_username_tooltip}"/>
		</td><td>
                <webuijsf:textField id="name1"
                  label=""
                  required="true"
                  validatorExpression="#{SimpleWizardBean.validateUserName}"
                  text="#{SimpleWizardBean.userName}"
                  toolTip="#{msgs.wiz_user_username_tooltip}"
                  onKeyPress="var evt = (event) ? event : window.event; if (evt.keyCode==13) return false;"/>
		</td></tr>
		<tr><td></td><td>
		<webuijsf:helpInline id="inhelp1" type="field"
                  text="#{msgs.wiz_inhelp_username}"/>
		</td></tr>

		<!-- Guidelines require empty line between form fields. -->
		<tr><td><![CDATA[&nbsp;]]></td></tr>

                <!-- User description field is optional. -->
		<tr><td>
                <webuijsf:label text="#{msgs.wiz_user_userdesc}"/>
		</td><td>
                <webuijsf:textField
                  text="#{SimpleWizardBean.userDescription}"/>
		</td></tr>

		<!-- Guidelines require empty line between form fields. -->
		<tr><td><![CDATA[&nbsp;]]></td></tr>

                <!-- Specify how UID is obtained. Toggle UID text field. -->
		<tr><td>
                <webuijsf:label text="#{msgs.wiz_user_uidChoiceText}"/>
		</td><td>
                <webuijsf:radioButton id="radio1" name="radiouid"
                  label="#{msgs.wiz_user_uidAutoGenerate}"
                  onClick="setDisabled('form1:wizard1:step1:uid1', true, '');"
                  selected="#{SimpleWizardBean.uidAutoGenerate}"/>
		</td></tr>
		<tr><td>
		</td><td>
                <webuijsf:radioButton id="radio2" name="radiouid"
                  label="#{msgs.wiz_user_uidSet}"
                  onClick="setDisabled('form1:wizard1:step1:uid1', false);"
                  selected="#{SimpleWizardBean.uidSet}"/>
		</td></tr>

		<!-- Guidelines require empty line between form fields. -->
		<tr><td><![CDATA[&nbsp;]]></td></tr>

                <!-- Explicit UID can be set based on radio buttons -->
		<!-- Guidelines require inline alert messages be used. -->
		<tr><td>
		</td><td>
		<webuijsf:message showDetail="true" for="uid1"/>
		</td></tr>
		<tr><td>
                <webuijsf:label text="#{msgs.wiz_user_uid}" for="uid1"
                  toolTip="#{msgs.wiz_user_uid_tooltip}"/>
		</td><td>
                <webuijsf:textField id="uid1"
                  text="#{SimpleWizardBean.userUid}"
                  label=""
                  required="true"
                  disabled="#{SimpleWizardBean.uidDisabled}"
                  toolTip="#{msgs.wiz_user_uid_tooltip}"
                  validatorExpression="#{SimpleWizardBean.validateUserUid}"
                  onKeyPress="var evt = (event) ? event : window.event; if (evt.keyCode==13) return false;"/>
		</td></tr>
		<tr><td></td><td>
		<webuijsf:helpInline id="inhelp2" type="field"
                  text="#{msgs.wiz_inhelp_useruid}"/>
		</td></tr>
		</table>

            </webuijsf:wizardStep>

            <!-- ====================================== -->
            <!-- Step 2: Get user password information. -->
            <webuijsf:wizardStep id="step2"
              summary="#{msgs.wiz_simple_step2_summary}"
              title="#{msgs.wiz_simple_step2_title}"
              detail="#{msgs.wiz_simple_step2_detail}"
              help="#{msgs.wiz_simple_step2_help}">

                <webuijsf:legend id="legend" style="margin-bottom:10px"
                  text="#{msgs.wiz_requiredLabel}" />

		<table border="0">

                <!-- Set how password is obtained. Toggle password field. -->
		<tr><td>
                <webuijsf:label text="#{msgs.wiz_user_pswdChoiceText}"/>
		</td><td>
                <webuijsf:radioButton id="radio3" name="radiopswd"
                  label="#{msgs.wiz_user_pswdLocked}"
                  onClick="setDisabled('form1:wizard1:step2:pswd1', true, '');
                    setDisabled('form1:wizard1:step2:pswd2', true, '');"
                  selected="#{SimpleWizardBean.pswdLocked}"/>
		</td></tr>
		<tr><td></td><td>
                <webuijsf:radioButton id="radio4" name="radiopswd"
                  label="#{msgs.wiz_user_pswdFirstLogin}"
                  onClick="setDisabled('form1:wizard1:step2:pswd1', true, '');
                    setDisabled('form1:wizard1:step2:pswd2', true, '');"
                  selected="#{SimpleWizardBean.pswdFirstLogin}"/>
		</td></tr>
		<tr><td></td><td>
                <webuijsf:radioButton id="radio5" name="radiopswd"
                  label="#{msgs.wiz_user_pswdNow}"
                  onClick="setDisabled('form1:wizard1:step2:pswd1', false);
                    setDisabled('form1:wizard1:step2:pswd2', false);"
                  selected="#{SimpleWizardBean.pswdNow}"/>
		</td></tr>

		<!-- Guidelines require empty line between form fields. -->
		<tr><td><![CDATA[&nbsp;]]></td></tr>

                <!-- Set password now with confirmation -->
		<!-- Guidelines require inline alert messages be used. -->
		<tr><td>
		</td><td>
		<webuijsf:message showDetail="true" for="pswd1"/>
		</td></tr>
		<tr><td>
                <webuijsf:label text="#{msgs.wiz_user_pswdEnter1}"
                  for="pswd1"
                  toolTip="#{msgs.wiz_user_pswdEnter1_tooltip}"/>
		</td><td>
                <webuijsf:passwordField id="pswd1"
                  password="#{SimpleWizardBean.userPassword}"
                  disabled="#{SimpleWizardBean.passwordDisabled}"
                  toolTip="#{msgs.wiz_user_pswdEnter1_tooltip}"
                  required="true"
                  label=""
                  validatorExpression="#{SimpleWizardBean.validateUserPassword}"
                  onKeyPress="var evt = (event) ? event : window.event; if (evt.keyCode==13) return false;"/>
		</td></tr>
		<tr><td></td><td>
		<webuijsf:helpInline id="inhelp3" type="field"
                  text="#{msgs.wiz_inhelp_password}"/>
		</td></tr>

		<!-- Guidelines require empty line between form fields. -->
		<tr><td><![CDATA[&nbsp;]]></td></tr>

		<tr><td>
		</td><td>
		<webuijsf:message showDetail="true" for="pswd2"/>
		</td></tr>
                <tr><td>
                <webuijsf:label text="#{msgs.wiz_user_pswdEnter2}"
                  for="pswd2"
                  toolTip="#{msgs.wiz_user_pswdEnter2_tooltip}"/>
		</td><td>
                <webuijsf:passwordField id="pswd2"
                  password="#{SimpleWizardBean.userPasswordConfirm}"
                  disabled="#{SimpleWizardBean.passwordConfirmDisabled}"
                  toolTip="#{msgs.wiz_user_pswdEnter2_tooltip}"
                  required="true"
                  label=""
                  validatorExpression="#{SimpleWizardBean.confirmUserPassword}"
                  onKeyPress="var evt = (event) ? event : window.event; if (evt.keyCode==13) return false;"/>
		</td></tr>
		<tr><td></td><td>
		<webuijsf:helpInline id="inhelp4" type="field"
                  text="#{msgs.wiz_inhelp_confirm}"/>
		</td></tr>
		</table>

            </webuijsf:wizardStep>

            <!-- =================================== -->
            <!-- Step 3: Set user group information. -->
            <webuijsf:wizardStep id="step3"
              summary="#{msgs.wiz_simple_step3_summary}"
              title="#{msgs.wiz_simple_step3_title}"
              detail="#{msgs.wiz_simple_step3_detail}"
              help="#{msgs.wiz_simple_step3_help}">

                <!-- Choose primary group name from drop down list -->
                <webuijsf:dropDown id="prigroup1"
                  selected="#{SimpleWizardBean.primaryGroupName}"
                  items="#{SimpleWizardBean.primaryGroupList}"
                  label="#{msgs.wiz_user_primaryGroup}"/>

                <!-- Add and remove secondary groups from list -->
                <br/><br/>
                <webuijsf:addRemove id="secgroup1"
                  selected="#{SimpleWizardBean.secondaryGroupNames}"
                  items="#{SimpleWizardBean.secondaryGroupList}"
                  label="#{msgs.wiz_user_secondaryGroup}"
                  availableItemsLabel="#{msgs.wiz_user_secGroupAvailable}"
                  selectedItemsLabel="#{msgs.wiz_user_secGroupSelected}"
                  rows="8"
                  labelOnTop="true"
                  sorted="true"/>

            </webuijsf:wizardStep>

            <!-- ===========================-->
            <!-- Step 4: Set home directory -->
            <webuijsf:wizardStep id="step4"
              summary="#{msgs.wiz_simple_step4_summary}"
              title="#{msgs.wiz_simple_step4_title}"
              detail="#{msgs.wiz_simple_step4_detail}"
              help="#{msgs.wiz_simple_step4_help}">

                <webuijsf:legend id="legend" style="margin-bottom:10px"
                  text="#{msgs.wiz_requiredLabel}" />

                <table border="0">
                <!-- Home dir server name field is required and validated. -->
		<!-- Guidelines require inline alert messages be used. -->
		<tr><td>
		</td><td>
		<webuijsf:message showDetail="true" for="servername1"/>
		</td></tr>
		<tr><td>
                <webuijsf:label text="#{msgs.wiz_user_homeserver}"
                  for="servername1"
                  toolTip="#{msgs.wiz_user_homeserver_tooltip}"/>
		</td><td>
                <webuijsf:textField id="servername1"
                  required="true"
                  label=""
                  validatorExpression="#{SimpleWizardBean.validateHomeServer}"
                  toolTip="#{msgs.wiz_user_homeserver_tooltip}"
                  text="#{SimpleWizardBean.homeServer}"
                  onKeyPress="var evt = (event) ? event : window.event; if (evt.keyCode==13) return false;"/>
		</td></tr>
		<tr><td></td><td>
		<webuijsf:helpInline id="inhelp5" type="field"
                  text="#{msgs.wiz_inhelp_servername}"/>
		</td></tr>

		<!-- Guidelines require empty line between form fields. -->
		<tr><td><![CDATA[&nbsp;]]></td></tr>

                <!-- Home dir path field is required -->
		<tr><td>
		</td><td>
		<webuijsf:message showDetail="true" for="homepath1"/>
		</td></tr>
		<tr><td>
                <webuijsf:label text="#{msgs.wiz_user_homedir}"
                  for="homepath1"
                  toolTip="#{msgs.wiz_user_homedir_tooltip}"/>
		</td><td>
                <webuijsf:textField id="homepath1"
                  required="true"
                  label=""
                  validatorExpression="#{SimpleWizardBean.validateHomePath}"
                  text="#{SimpleWizardBean.homePath}"
                  toolTip="#{msgs.wiz_user_homedir_tooltip}"
                  onKeyPress="var evt = (event) ? event : window.event; if (evt.keyCode==13) return false;"/>
		</td></tr>
		</table>

            </webuijsf:wizardStep>

            <!-- ==========================-->
            <!-- Step 5: Confirmation step -->
            <webuijsf:wizardStep id="step5"
              summary="#{msgs.wiz_simple_step5_summary}"
              title="#{msgs.wiz_simple_step5_title}"
              detail="#{msgs.wiz_simple_step5_detail}"
              help="#{msgs.wiz_simple_step5_help}"
              finish="true">

                <table border="0">
		<tr><td>
                <webuijsf:label text="#{msgs.wiz_user_username}"/>
		</td><td>
                <webuijsf:staticText id="res_username"
                  text="#{SimpleWizardBean.userName}"/>

		</td></tr><tr><td>
                <webuijsf:label text="#{msgs.wiz_user_userdesc}"/>
		</td><td>
                <webuijsf:staticText id="res_userdesc"
                  text="#{SimpleWizardBean.userDescription}"/>

		</td></tr><tr><td>
                <webuijsf:label text="#{msgs.wiz_user_uid}"/>
		</td><td>
                <webuijsf:staticText id="res_useruid"
                  text="#{SimpleWizardBean.userUid}"/>

		</td></tr><tr><td>
                <webuijsf:label text="#{msgs.wiz_user_passwordSetting}"/>
		</td><td>
                <webuijsf:staticText id="res_password"
                  text="#{SimpleWizardBean.passwordSetting}"/>

		</td></tr><tr><td>
                <webuijsf:label text="#{msgs.wiz_user_primaryGroup}"/>
		</td><td>
                <webuijsf:staticText id="res_prigroup"
                  text="#{SimpleWizardBean.primaryGroupName}"/>

		</td></tr><tr><td>
                <webuijsf:label text="#{msgs.wiz_user_homepath}"/>
		</td><td>
                <webuijsf:staticText id="res_homedir"
                  text="#{SimpleWizardBean.homeDirectory}"/>
		</td></tr>
		</table>

            </webuijsf:wizardStep>

            <!-- ==========================-->
            <!-- Step 6: Results step -->
            <webuijsf:wizardStep id="step6"
              summary="#{msgs.wiz_simple_step6_summary}"
              title="#{msgs.wiz_simple_step6_title}"
              detail="#{msgs.wiz_simple_step6_detail}"
              help="#{msgs.wiz_simple_step6_help}"
              results="true">

		<!-- Show the results of the operation -->
                <webuijsf:staticText id="res_msg"
                  text="#{SimpleWizardBean.resultMessage}"/>

            </webuijsf:wizardStep>

          </webuijsf:wizard>
        </webuijsf:form>
      </webuijsf:body>
    </webuijsf:html>  
  </webuijsf:page>
</f:view>

</jsp:root>
