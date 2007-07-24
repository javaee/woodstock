<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %> 
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@taglib uri="http://www.sun.com/webui/webuijsf" prefix="webuijsf" %>
<f:view>
  <webuijsf:page>
    <webuijsf:html>
    <webuijsf:head title="Accordion as a navigational element"/>
    <webuijsf:body>
      <webuijsf:form id="accordionExample">
        
         <webuijsf:accordion id="acc1" style="width:100%">
        
                <webuijsf:accordionTab id="tab1" title="News" >
                    
                    <webuijsf:hyperlink id="hyper2" text="CNN" 
                    url="http://www.cnn.com"
                    target="rightFrame"/>
                    <br/>
                    <webuijsf:hyperlink id="hyper3" text="Slate" 
                    url="http://www.slate.com"
                    target="rightFrame"/>
                    <br/>
                    <webuijsf:hyperlink id="hyper5" text="Washington Post" 
                    url="http://www.washingtonpost.com"
                    target="rightFrame"/>
                </webuijsf:accordionTab>
                
                <webuijsf:accordionTab id="tab2" title="Stock Quotes" >
                    <webuijsf:hyperlink id="hyper1" text="Sun Microsystems" 
                        url="http://finance.yahoo.com/q?s=sunw"
                    target="rightFrame"/>
                    <br/>
                    <webuijsf:hyperlink id="hyper2" text="Cisco" 
                    url="http://finance.yahoo.com/q?s=csco"
                    target="rightFrame"/>
                    <br/>
                    <webuijsf:hyperlink id="hyper3" text="EMC Corp" 
                        url="http://finance.yahoo.com/q?s=emc"
                        target="rightFrame"/>
                    </br>
                    <webuijsf:hyperlink id="hyper4" text="IBM" 
                        url="http://finance.yahoo.com/q?s=ibm"
                    target="rightFrame"/>
                    <br/>
                    <webuijsf:hyperlink id="hyper5" text="Google Inc." 
                        url="http://finance.yahoo.com/q?s=goog"
                    target="rightFrame"/>
                </webuijsf:accordionTab>
                
                <webuijsf:accordionTab id="tab3" title="Blogs" >
                    <webuijsf:hyperlink id="hyper1" text="Glassfish - The Aquarium" 
                        url="http://blogs.sun.com/theaquarium/"
                    target="rightFrame"/>
                    <br/>
                    <webuijsf:hyperlink id="hyper2" text="Sun Blogs" 
                        url="http://blogs.sun.com"
                    target="rightFrame"/>
                    <br/>
                    <webuijsf:hyperlink id="hyper3" text="Jonathan's Blog" 
                        url="http://blogs.sun.com/jonathan"
                    target="rightFrame"/>
                    <br/>
                    <webuijsf:hyperlink id="hyper4" text="Dojo Toolkit Blog" 
                        url="http://dojotoolkit.org/blog"
                    target="rightFrame"/>
                </webuijsf:accordionTab>
                
                <webuijsf:accordionTab id="tab4" title="Weather" >
                    <webuijsf:hyperlink id="hyper1" text="Burlington" 
                        url="http://weather.yahoo.com/forecast/USMA0062.html"
                    target="rightFrame"/>
                    <br/>
                    <webuijsf:hyperlink id="hyper2" text="Cambridge" 
                        url="http://weather.yahoo.com/forecast/USMA0066.html"
                    target="rightFrame"/>
                    <br/>
                    <webuijsf:hyperlink id="hyper3" text="Bangalore" 
                        url="http://weather.yahoo.com/forecast/INXX0012.html"
                    target="rightFrame"/>
                </webuijsf:accordionTab>
          </webuijsf:accordion>
      
      </webuijsf:form>
    </webuijsf:body>
    </webuijsf:html>
  </webuijsf:page>
</f:view>