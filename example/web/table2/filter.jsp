<?xml version="1.0" encoding="UTF-8"?>
<jsp:root version="2.0" xmlns:f="http://java.sun.com/jsf/core"
    xmlns:h="http://java.sun.com/jsf/html"
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>

<!-- Filter Panel -->
<webuijsf:textField id="customFilter"
    columns="50"
    label="#{msgs.table2_customFilterLabel}"
    labelLevel="2"
    valueChangeListenerExpression="#{Table2Bean.groupA.filter.filterType}"    
    text="#{Table2Bean.groupA.filter.customFilter}"
    onKeyPress="var evt = (event) ? event : window.event; if (evt.keyCode==13) {var e=document.getElementById('form1:table1:filterPanel:search'); if (e != null) e.click(); return false}"/>
<webuijsf:markup tag="div" styleClass="#{themeStyles.TABLE_PANEL_BUTTON_DIV}"> 
  <webuijsf:button id="search"
      mini="true" primary="true"
      onClick="updateFilter(); return false;"
      text="#{msgs.table2_search}"/>
  <webuijsf:button id="cancel"
                   mini="true" 
      onClick="closeFilterPanel(); return false;"
      text="#{msgs.table_cancel}"/>
</webuijsf:markup>
<!-- Note: If the user presses the enter key while the text field has focus,
     the page will be submitted incorrectly, unless we capture the onKeyPress
     event and invoke the click method of the submit button. -->
</jsp:root>
