<jsp:root version="2.0" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
  <jsp:directive.page contentType="text/html" />
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
    <webuijsf:page>
      <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
      <webuijsf:html>
        <webuijsf:head title="#{msgs.accordion_navTitle}"/>
        <webuijsf:body>
          <webuijsf:form id="accordionExample">
        
            <webuijsf:accordion id="acc1" style="width:100%">
        
              <webuijsf:accordionTab id="tab1" title="#{msgs.accordion_tabNews}" >
                <webuijsf:hyperlink id="hyper2" text="#{msgs.accordion_CNN}" 
                  url="http://www.cnn.com"
                  target="rightFrame"/>

                <br/>

                <webuijsf:hyperlink id="hyper3" text="#{msgs.accordion_Slate}" 
                  url="http://www.slate.com"
                  target="rightFrame"/>

                <br/>

                <webuijsf:hyperlink id="hyper5" text="#{msgs.accordion_WashPost}" 
                  url="http://www.washingtonpost.com"
                  target="rightFrame"/>
              </webuijsf:accordionTab>
                
              <webuijsf:accordionTab id="tab2" title="#{msgs.accordion_tabStock}" >
                <webuijsf:hyperlink id="hyper1" text="#{msgs.accordion_Sun}" 
                  url="http://finance.yahoo.com/q?s=java"
                  target="rightFrame"/>

                <br/>

                <webuijsf:hyperlink id="hyper2" text="#{msgs.accordion_Cisco}" 
                  url="http://finance.yahoo.com/q?s=csco"
                  target="rightFrame"/>

                <br/>

                <webuijsf:hyperlink id="hyper3" text="#{msgs.accordion_EMC}" 
                  url="http://finance.yahoo.com/q?s=emc"
                  target="rightFrame"/>

                <br/>

                <webuijsf:hyperlink id="hyper4" text="#{msgs.accordion_IBM}" 
                  url="http://finance.yahoo.com/q?s=ibm"
                  target="rightFrame"/>

                <br/>

                <webuijsf:hyperlink id="hyper5" text="#{msgs.accordion_Google}" 
                  url="http://finance.yahoo.com/q?s=goog"
                  target="rightFrame"/>

              </webuijsf:accordionTab>
                
              <webuijsf:accordionTab id="tab3" title="#{msgs.accordion_tabBlogs}" >
                <webuijsf:hyperlink id="hyper1" text="#{msgs.accordion_blogsGlassfish}" 
                  url="http://blogs.sun.com/theaquarium/"
                  target="rightFrame"/>

                <br/>

                <webuijsf:hyperlink id="hyper2" text="#{msgs.accordion_blogsSun}" 
                  url="http://blogs.sun.com"
                  target="rightFrame"/>

                <br/>

                <webuijsf:hyperlink id="hyper3" text="#{msgs.accordion_blogsJS}" 
                  url="http://blogs.sun.com/jonathan"
                  target="rightFrame"/>

                <br/>

                <webuijsf:hyperlink id="hyper4" text="#{msgs.accordion_blogsDojo}" 
                  url="http://dojotoolkit.org/blog"
                  target="rightFrame"/>

              </webuijsf:accordionTab>
                
              <webuijsf:accordionTab id="tab4" title="#{msgs.accordion_tabWeather}" >
                <webuijsf:hyperlink id="hyper1" text="#{msgs.accordion_Burlington}" 
                  url="http://weather.yahoo.com/forecast/USMA0062.html"
                  target="rightFrame"/>

                <br/>

                <webuijsf:hyperlink id="hyper2" text="#{msgs.accordion_Cambridge}" 
                  url="http://weather.yahoo.com/forecast/USMA0066.html"
                  target="rightFrame"/>

                <br/>

                <webuijsf:hyperlink id="hyper3" text="#{msgs.accordion_Bangalore}" 
                  url="http://weather.yahoo.com/forecast/INXX0012.html"
                  target="rightFrame"/>

              </webuijsf:accordionTab>
            </webuijsf:accordion>
      
          </webuijsf:form>
        </webuijsf:body>
      </webuijsf:html>
    </webuijsf:page>
  </f:view>
</jsp:root>
