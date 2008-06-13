<%@ taglib prefix="a" uri="http://jmaki/v1.0/jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--
 The contents of this file are subject to the terms
 of the Common Development and Distribution License
 (the License). You may not use this file except in
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
 
 Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 -->
<html>
  <head>
    <title>Button Example</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">       
    <script type="text/javascript">
      var woodstockConfig = {
        ajax: { url: "/example-jmaki/ButtonServlet" },
        namespace: "woodstock"
      };
    </script>
    <script type="text/javascript" src="/example-jmaki/button/button.js"></script>
  </head>
  <body>
    <form id="form1" method="post" action="/example-jmaki/ButtonServlet">

      <!-- Masthead -->
      <div class="MstDiv_sun4">
        <div style="padding:6px 10px 4px;"></div>
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="MstTblBot_sun4" title="">
          <tr>
            <td class="MstTdTtl_sun4" width="99%">
              <div class="MstDivUsr_sun4">
                <a:widget name="woodstock.text" args="{value: 'User:', class: 'MstLbl_sun4'}" />
                <a:widget name="woodstock.text" args="{value: 'test_user', class: 'MstTxt_sun4'}" />
                <a:widget name="woodstock.image" value="{src: '/example-jmaki/images/masthead-separator.gif', height: 14, width: 14}" />
                <a:widget name="woodstock.text" args="{value: 'Server:', class: 'MstLbl_sun4'}" />
                <a:widget name="woodstock.text" args="{value: 'test_server', class: 'MstTxt_sun4'}" />
              </div>
              <div class="MstDivTtl_sun4" style="height:35px;margin:5px 0px 0px 0px;">
                <a:widget name="woodstock.image" value="{src: '/example-jmaki/images/example_primary_masthead.png'}" />
              </div>
            </td>
          </tr>
          <tr><td><div class="hrule_sun4"></div></td></tr>
        </table>
      </div>

      <!-- Content Margin -->
      <div class="ConMgn_sun4">

        <!-- breadcrumb -->
        <br/>
        <a:widget name="woodstock.anchor" value="{
            href: '../index.jsp', 
            contents: [ 'Example Application Index > ' ]}" />
        <a:widget name="woodstock.text" args="{value: 'Button'}" />

        <!-- Content Page Title -->
        <br/><br/>
        <a:widget name="woodstock.text" args="{value: 'Button Examples', style: 'font-size:17px'}" />


        <br/><br/>
        <table>
          <tr>
            <td>
              <a:widget name="woodstock.label" value="{value: 'Image Button:'}" />
            </td>
            <td>
              <a:widget id="imageBtn" name="woodstock.button" args="{
                src: '/example-jmaki/images/check_all.gif',
                value: 'Image Button'}" />
            </td>
            <td></td>
          </tr>
          <tr>
            <td>
              <a:widget name="woodstock.label" value="{value: 'Primary Button:'}" />
            </td>
            <td>
              <a:widget id="primaryBtn" name="woodstock.button" args="{value: 'Primary'}" />
            </td>
            <td>
              <a:widget name="woodstock.checkbox" value="{
                checked: true,
                label: { value: 'Enable' },
                onClick: 'toggleDisabled(\"primaryBtn\")'}" />
            </td>
          </tr>
          <tr>
            <td>
              <a:widget name="woodstock.label" value="{value: 'Secondary Button:'}" />
            </td>
            <td>
              <a:widget id="secondaryBtn" name="woodstock.button" args="{
                primary: false,
                value: 'Secondary'}" />
            </td>
            <td>
              <a:widget name="woodstock.checkbox" value="{
                checked: true,
                label: { value: 'Enable' },
                onClick: 'toggleDisabled(\"secondaryBtn\")'}" />
            </td>
          </tr>
          <tr>
            <td>
              <a:widget name="woodstock.label" value="{value: 'Enter Dynamic Label:'}" />
            </td>
            <td>
              <a:widget id="textField" name="woodstock.textField" args="{
                value: 'Not implemented...',
                onKeyPress: 'setTimeout(\"updateLabel();\", 0);'}"/>
            </td>
            <td></td>
          </tr>
        </table>

      </div>
    </form>
  </body>
</html>
