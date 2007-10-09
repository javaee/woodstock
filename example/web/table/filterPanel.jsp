<jsp:root version="2.0" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
  <jsp:directive.page contentType="text/html" />
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

<!-- Filter Panel -->
<webuijsf:textField id="customFilter"
    columns="50"
    label="#{msgs.table_customFilterLabel}"
    labelLevel="2"
    onKeyPress="var evt = (event) ? event : window.event; if (evt.keyCode==13) {var e=document.getElementById('form1:table1:filterPanel:submit'); if (e != null) e.click(); return false}"
    text="#{TableBean.groupA.filter.customFilter}"/>
<webuijsf:markup tag="div" styleClass="#{themeStyles.TABLE_PANEL_BUTTON_DIV}">
  <webuijsf:button id="submit"
      actionExpression="#{TableBean.groupA.filter.applyCustomFilter}"
      mini="true"
      primary="true"
      text="#{msgs.table_ok}"/>
  <webuijsf:button id="cancel"
      mini="true"
      onClick="toggleFilterPanel(); return false"
      text="#{msgs.table_cancel}"/>
</webuijsf:markup>

<!-- Note: If the user presses the enter key while the text field has focus,
     the page will be submitted incorrectly, unless we capture the onKeyPress
     event and invoke the click method of the submit button. -->

</jsp:root>
