<?xml version="1.0" encoding="UTF-8"?>
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

<jsp:root version="1.2"
    xmlns:f="http://java.sun.com/jsf/core"
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bc="http://org/BoxComponent"
    xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=ISO-8859-1"
	pageEncoding="UTF-8"/>

<f:view>
    <webuijsf:page>
    <webuijsf:html>
    <webuijsf:head title="index page" />
    <webuijsf:body styleClass="DefBdy">
    <webuijsf:form id="form1">
	<bc:box id="box1" />
	<![CDATA[
	    <br/>
	]]>
	<webuijsf:staticText id="discuss"
	    text="This is suntheme static text. The following button is a suntheme primary button."/>
	    
	<![CDATA[
	    <br/>
	]]>
	<webuijsf:button id="abutton" primary="true"
	    text="Suntheme primary button"/>
<![CDATA[
<p>
The application is overriding theme properties defined
in <code>webui-jsf-suntheme.jar</code>. The application defines
an init-param <code>com.sun.webui.theme.THEME_RESOURCES</code> in web.xml
with the value &quot;apptheme&quot;. This value is
interpreted as a ResourceBundle basename, that represents a ResourceBundle
properties file called <a href="apptheme.properties">apptheme.properties</a>.
This basename is used to identify a Javascript file
<a href="apptheme.js">apptheme.js</a>.
</p>
<p>
The application defined javascript file overrides the 
<code>BUTTON1</code> theme property and defines the CSS selector value
in the application's stylesheet <a href="app.css">app.css</a> file.
In order to load the stylesheet on every application page, the
<code>Theme.stylesheets</code> theme property is defined in the 
apptheme.properties file. Redefining the <code>BUTTON1</code> theme
property affects the button widget's look. In this case it is 
combining the application's look with the theme's default <code>BUTTON1</code>
value.
</p>
<p>
The Woodstock Theme and Components are trsnsitioning from HTML
renderers to Javascript "widget" renderers.
Some Woodstock Components are rendered utilizing 
"HTML" renderers and some are rendered via "widget" renderers.
Some theme properties are therefore referenced on the server and
some are referenced on the client. Theme properties defined in 
ResourceBundle properties files are referenced on the server, which
includes properties like <code>Theme.stylesheets</code> that affect
applications at the "page" level and components that are rendered
using HTML renderers. The Javascript file defines properties for
components that are renderered via Javascript "widget" renderers.
</p>
<p>
See <a href="../index.html">
The Theme and Woodstock Component Web Applications"</a> For details 
on the how to customize and create Themes for the Woodstock Components.
</p>
<h2>
<a href="index-client-theme.jsp">Client Theme Contents</a>
</h2>
<p>
This application also demonstrates how components can be created
and delivered as jar files. The large "box" at the top of the page
is such a component. It is called a "box" component.
Theme resources specific to the
component are also defined in the jar and take advantage of the 
Woodstock Theme infrastructure. An application can then override
those component theme properties as well, in the same manner as
Woodstock Components.
</p>
<p>
Localization is also supported by the Theme infrastructure and can
be seen by changing the browser language to "fr". The "box" component
renders a French flag in this case. Application defined theme properties
can also be localized by providing locale variants of the bundles.
See <a href="../redefining-theme-properties.html#3.3">Theme Localization</a>.
</p>
]]>

    </webuijsf:form>
    </webuijsf:body>
    </webuijsf:html>
    </webuijsf:page>
</f:view>

</jsp:root>
