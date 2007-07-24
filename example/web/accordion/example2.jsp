<?xml version="1.0" encoding="UTF-8"?>
<jsp:root version="2.0" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=ISO-8859-1" pageEncoding="UTF-8"/><f:view>
        <webuijsf:page frame="true">
            <webuijsf:html>
                <webuijsf:head title="Accordion Example" />
                <webuijsf:frameSet rows="20%,*" style="color:blue" styleClass="blue" toolTip="blah">
                    <webuijsf:frame name="leftFrame" toolTip="LeftFrame" url="../accordion/example2top.jsp" frameBorder="false" noResize="false"/>
                    <webuijsf:frameSet cols="20%,*" style="color:blue" styleClass="blue" toolTip="blah">
                        <webuijsf:frame name="leftFrame" toolTip="LeftFrame" url="../accordion/example2left.jsp" frameBorder="false" noResize="false"/>
                        <webuijsf:frame  name="rightFrame" toolTip="RightFrame" url="../accordion/example2right.jsp" frameBorder="false" noResize="false"/>
                    </webuijsf:frameSet>
                </webuijsf:frameSet>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>