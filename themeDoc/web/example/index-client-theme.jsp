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

<jsp:root version="2.0" xmlns:f="http://java.sun.com/jsf/core" 
    xmlns:h="http://java.sun.com/jsf/html" 
    xmlns:jsp="http://java.sun.com/JSP/Page" 
    xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=ISO-8859-1" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page>
            <webuijsf:html>
                <webuijsf:head title="Client Theme Properties"/>
                <webuijsf:body>
                <webuijsf:form id="form1">


                <webuijsf:masthead id="Masthead" 
                    productImageDescription="Java Web Console" 
		    userInfo="test_user"
                    serverInfo="test_server" />

                <webuijsf:breadcrumbs id="breadcrumbs1">
                    <webuijsf:hyperlink url="index.jsp" text="themeTestapp"/>
		    <webuijsf:hyperlink text="Client Theme Properties"/>
                    
                </webuijsf:breadcrumbs>

<webuijsf:script type="text/javascript">
<![CDATA[

var theme = {

    getMessage: function(property, params) {
	return webui.suntheme.theme.common.getMessage(property, params);
    },

    getPrefix : function() {
	return webui.suntheme.theme.common.getPrefix();
    },

    getProperty: function(category, key) {
	return webui.suntheme.theme.common.getProperty(category, key);
    },

    getProperties: function(category) {
	return webui.suntheme.theme.common.getProperties(category);
    },

    getImage: function(property) {
	return webui.suntheme.theme.common.getImage(property);
    },

    getClassName : function(property) {
	return webui.suntheme.theme.common.getClassName(property);
    },

    getTemplatePath: function(key) {
        return webui.suntheme.theme.common.getTemplatePath(key);
    },

    getTemplate: function(key) {
        return webui.suntheme.theme.common.getTemplate(key);
    }
};

    function dumpImage(obj) {
	document.write("<table>");

	var imageProps = ["url", "width", "height", "alt", "title"];
	
	for (var i = 0; i < imageProps.length; ++i) {
	    document.write(
		"<tr>" +
		"<td>" + imageProps[i] + "=" + "</td>" +
		"<td>" + obj[imageProps[i]] + "</td>" +
		"</tr>"
	    );
	}

	document.write("</table>");

    }
    function dumpThemeCategory(cat, valueCB) {

	document.write("<h2>Theme " + cat + "</h2>");
	document.write(
	    "<table border='1'>" +
		"<thead style='font-size: 12pt'>" +
		"<tr>" +
		    "<td><b>Property</b></td>" +
		    "<td><b>Value</b></td>" +
		"</tr>" +
	    "</thead>"
	);
	var properties = theme.getProperties(cat);

	properties = sortProperties(properties);

	//for (var prop in properties) {
	for (var i = 0; i < properties.length; ++i) {
	    var prop = properties[i];
	    var value = theme.getProperty(cat, prop);
	    if (valueCB != null) {
		value = valueCB(prop, value);
	    }
	    document.write(
	    "<tr>" +
		"<td>" + prop + "</td>" +
		"<td>"
	    );
	    if (value != null && typeof value == "object") {
		dumpImage(value);
	    } else {
		document.write(value);
	    }
	    document.write(
		"</td>" +
	    "</tr>"
	    );
	}
	document.write("</table>");
    }

    var params = [ "ARG1", "ARG2", "ARG3", "ARG4", "ARG5", "ARG6", "ARG7" ];

    dumpThemeCategory("messages", function(prop, value) {
	    return webui.suntheme.theme.common.getMessage(prop, params);
	});

    dumpThemeCategory("images", function(prop, value) {

	    var propsyms = prop.split('_');
	    var proptype = propsyms[propsyms.length - 1];
	    if (propsyms.length == 1 || 
		(proptype != "ALT" && proptype != "TITLE" &&
		proptype != "WIDTH" && proptype != "HEIGHT")) {

		return theme.getImage(prop);
	    }

	    if (proptype == "ALT" || proptype == "TITLE") {
		var msgValue = theme.getMessage(value);
		if (msgValue != null) {
		    value = value + " = " + msgValue;
		}
	    }
	    return value;
	});

    dumpThemeCategory("styles", function(prop, value) {
	    return theme.getClassName(prop);
	});
    dumpThemeCategory("templates", function(prop, value) {
	    return theme.getTemplate(prop);
	});

    function sortProperties(props) {
	var sortedProps = new Array();
	var count = 0;
	for (var p in props) {
	    sortedProps[count++] = p;
	}
	for (var i = 0; i < count; ++i) {
	    for (var j = 0; j < count - i; j++) {
		if (sortedProps[j+1] < sortedProps[j]) {
		    var tmp = sortedProps[j];
		    sortedProps[j] = sortedProps[j+1];
		    sortedProps[j+1] = tmp;
		}
	    }
	}
	return sortedProps;
    }

]]>
</webuijsf:script>
                    </webuijsf:form>                    
                </webuijsf:body> 
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
