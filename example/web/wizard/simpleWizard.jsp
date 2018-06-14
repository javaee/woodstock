<jsp:root version="2.0"
    xmlns:f="http://java.sun.com/jsf/core"
    xmlns:h="http://java.sun.com/jsf/html"
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
<jsp:directive.page contentType="text/html"/>
<f:view>    
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
  <webuijsf:page id="SimpleWizardPage">
    <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources"
      var="msgs" />
    <webuijsf:html id="html">  
      <webuijsf:head id="head" title="#{msgs.wiz_simple_title}">
      </webuijsf:head>
      <webuijsf:body id="body"
        onLoad="document.getElementById('form1:wizard1').wizOnLoad()">
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
                <webuijsf:label text="#{msgs.wiz_user_username}" for="name1"/>
		</td><td>
                <webuijsf:textField id="name1"
                  label=""
                  required="true"
                  validatorExpression="#{SimpleWizardBean.validateUserName}"
                  text="#{SimpleWizardBean.userName}"
                  onKeyPress="if (event.keyCode==13) return false;"/>
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
                  onClick="javascript:
                    webui.suntheme.field.setDisabled('form1:wizard1:step1:uid1',true);
                    webui.suntheme.field.setValue('form1:wizard1:step1:uid1','');"
                  selected="#{SimpleWizardBean.uidAutoGenerate}"/>
		</td></tr>
		<tr><td>
		</td><td>
                <webuijsf:radioButton id="radio2" name="radiouid"
                  label="#{msgs.wiz_user_uidSet}"
                  onClick="javascript:webui.suntheme.field.setDisabled('form1:wizard1:step1:uid1',false);"
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
                <webuijsf:label text="#{msgs.wiz_user_uid}" for="uid1"/>
		</td><td>
                <webuijsf:textField id="uid1"
                  text="#{SimpleWizardBean.userUid}"
                  label=""
                  required="true"
                  disabled="#{SimpleWizardBean.uidDisabled}"
                  validatorExpression="#{SimpleWizardBean.validateUserUid}"
                  onKeyPress="if (event.keyCode==13) return false;"/>
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
		<table border="0">

                <!-- Set how password is obtained. Toggle password field. -->
		<tr><td>
                <webuijsf:label text="#{msgs.wiz_user_pswdChoiceText}"/>
		</td><td>
                <webuijsf:radioButton id="radio3" name="radiopswd"
                  label="#{msgs.wiz_user_pswdLocked}"
                  onClick="javascript:
                    webui.suntheme.field.setDisabled('form1:wizard1:step2:pswd1',true);
                    webui.suntheme.field.setValue('form1:wizard1:step2:pswd1','');
                    webui.suntheme.field.setDisabled('form1:wizard1:step2:pswd2',true);
                    webui.suntheme.field.setValue('form1:wizard1:step2:pswd2','');"
                  selected="#{SimpleWizardBean.pswdLocked}"/>
		</td></tr>
		<tr><td></td><td>
                <webuijsf:radioButton id="radio4" name="radiopswd"
                  label="#{msgs.wiz_user_pswdFirstLogin}"
                  onClick="javascript:
                    webui.suntheme.field.setDisabled('form1:wizard1:step2:pswd1',true);
                    webui.suntheme.field.setValue('form1:wizard1:step2:pswd1','');
                    webui.suntheme.field.setDisabled('form1:wizard1:step2:pswd2',true);
                    webui.suntheme.field.setValue('form1:wizard1:step2:pswd2','');"
                  selected="#{SimpleWizardBean.pswdFirstLogin}"/>
		</td></tr>
		<tr><td></td><td>
                <webuijsf:radioButton id="radio5" name="radiopswd"
                  label="#{msgs.wiz_user_pswdNow}"
                  onClick="javascript:
                    webui.suntheme.field.setDisabled('form1:wizard1:step2:pswd1',false);
                    webui.suntheme.field.setDisabled('form1:wizard1:step2:pswd2',false);"
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
                  for="pswd1"/>
		</td><td>
                <webuijsf:passwordField id="pswd1"
                  password="#{SimpleWizardBean.userPassword}"
                  disabled="#{SimpleWizardBean.passwordDisabled}"
                  required="true"
                  label=""
                  validatorExpression="#{SimpleWizardBean.validateUserPassword}"
                  onKeyPress="if (event.keyCode==13) return false;"/>
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
                  for="pswd2"/>
		</td><td>
                <webuijsf:passwordField id="pswd2"
                  password="#{SimpleWizardBean.userPasswordConfirm}"
                  disabled="#{SimpleWizardBean.passwordConfirmDisabled}"
                  required="true"
                  label=""
                  validatorExpression="#{SimpleWizardBean.confirmUserPassword}"
                  onKeyPress="if (event.keyCode==13) return false;"/>
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

                <table border="0">
                <!-- Home dir server name field is required and validated. -->
		<!-- Guidelines require inline alert messages be used. -->
		<tr><td>
		</td><td>
		<webuijsf:message showDetail="true" for="servername1"/>
		</td></tr>
		<tr><td>
                <webuijsf:label text="#{msgs.wiz_user_homeserver}"
                  for="servername1"/>
		</td><td>
                <webuijsf:textField id="servername1"
                  required="true"
                  label=""
                  validatorExpression="#{SimpleWizardBean.validateHomeServer}"
                  text="#{SimpleWizardBean.homeServer}"
                  onKeyPress="if (event.keyCode==13) return false;"/>
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
                  for="homepath1"/>
		</td><td>
                <webuijsf:textField id="homepath1"
                  required="true"
                  label=""
                  validatorExpression="#{SimpleWizardBean.validateHomePath}"
                  text="#{SimpleWizardBean.homePath}"
                  onKeyPress="if (event.keyCode==13) return false;"/>
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
