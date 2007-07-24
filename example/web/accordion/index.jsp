<jsp:root version="2.0" 
          xmlns:f="http://java.sun.com/jsf/core" 
          xmlns:jsp="http://java.sun.com/JSP/Page" 
          xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=ISO-8859-1" 
                        pageEncoding="UTF-8"/>

<f:view>
   <webuijsf:page >
       <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
       <webuijsf:head title="Accordion Tests Links Page" />
       <webuijsf:body>
       <webuijsf:form id="form1">
       <webuijsf:masthead id="masthead"
             productImageURL="/images/example_primary_masthead.png"
             productImageHeight="40"
             productImageWidth="188"
             userInfo="test_user" 
             serverInfo="test_server"
             productImageDescription="#{msgs.mastheadAltText}" />
             
       <webuijsf:breadcrumbs id="breadcrumbs">
        <webuijsf:hyperlink url="../index.jsp" text="Example Index"/>
        <webuijsf:hyperlink url="index.jsp" text="Accordion"/>
      </webuijsf:breadcrumbs>
      <webuijsf:contentPageTitle title="Accordion Examples" />
      <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
       <p>
<webuijsf:hyperlink url="example1.jsp" 
	      text="Test 1 "/>
<webuijsf:staticText text="Basic Accordion Example"/>
</p>

<p>
<webuijsf:hyperlink url="example2.jsp" 
	      text="Test 2 "/>
<webuijsf:staticText text="Accordion as a navigational component."/>


</p>

      <p>
<webuijsf:hyperlink url="example3.jsp" 
	      text="Test 3 "/>
<webuijsf:staticText text="Accordion showing refresh behavior."/>


</p>

  

      </webuijsf:markup>

        </webuijsf:form>
        </webuijsf:body> 
    </webuijsf:page>
</f:view>
</jsp:root>
