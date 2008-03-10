<jsp:root version="2.0" 
          xmlns:f="http://java.sun.com/jsf/core" 
          xmlns:jsp="http://java.sun.com/JSP/Page" 
          xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>

<f:view>
   <webuijsf:page >
       <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
       <webuijsf:head title="#{msgs.login_examples}"/>
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
        <webuijsf:hyperlink url="../index.jsp" text="#{msgs.index_title}"/>
        <webuijsf:hyperlink url="index.jsp" text="#{msgs.login_indexTitle}"/>
      </webuijsf:breadcrumbs>
      <webuijsf:contentPageTitle title="#{msgs.login_examples}" />
      <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
          
       <p>
<webuijsf:hyperlink url="login1.jsp" 
	      text="#{msgs.login_example1}"/>
</p>

<p>
<webuijsf:hyperlink url="login2.jsp" 
	      text="#{msgs.login_example2}"/>


</p>

      <p>
<webuijsf:hyperlink url="login3.jsp" 
	      text="#{msgs.login_example3}"/>


</p>

<p>
<webuijsf:hyperlink url="secondary.jsp" 
	      text="#{msgs.login_example4}"/>


</p>
  
            <br/><br/>
            <p>
            <webuijsf:staticText id="st2" text="#{msgs.login_msg1}"/>
            </p> 
            <p>
            <webuijsf:staticText id="st3" text="#{msgs.login_msg2}"/>
            </p>
            <pre>
            &lt;glassfish_install_dir&gt;/domains/your_domain/config/login.conf
            </pre>
            <br/>
            <pre>
            AppLogin1 {
                com.sun.webui.jsf.example.login.TestLoginModule optional;
            };
            
            AppLogin2 {
                com.sun.webui.jsf.example.login.TestLoginModule2 requisite;
                com.sun.webui.jsf.example.login.TestLoginModule3 requisite;
            };
            </pre>
            <p>
                <webuijsf:staticText id="st4" text="#{msgs.login_msg3}"/>
            </p>
            <pre>-Djava.security.auth.login.config=/path/to/file/login.conf</pre>
            
      </webuijsf:markup>

        </webuijsf:form>
        </webuijsf:body> 
    </webuijsf:page>
</f:view>
</jsp:root>
