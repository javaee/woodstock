  <a:widget name='woodstock.accordion' 
    value = "{items : 
           [
           {id: 'books', label : 'Books', content : 'Book content'},
           {id: 'music', label : 'Music', content : 'Music content', selected : true},
           {id : 'magazines', label : 'Magazines', include : 'test.jsp', lazyLoad : true},
           {label : 'Newspaper', content : 'Newspaper content', selected : false}
           ]}"    
    />

    
 <!-- BEGIN OF accordion TEST
            
         accordion is displayed in response to the event only.
         The snippet below provides an opportunity to test it,
         and can be safely removed.-->

    <a onClick="jmaki.publish('/woodstock/accordion/select', {targetId: 'magazines'});"
       style = "background: #CCCCCC">** select MAGAZINES tab **</a>                

    <a onClick="jmaki.publish('/woodstock/accordion/setContent', {targetId: 'books', value: 'new content'});"
       style = "background: #CCCCCC">** change content in BOOKS tab **</a>                

    <a onClick="jmaki.publish('/woodstock/accordion/setInclude', {targetId: 'music', value: 'http://google.com'});"
       style = "background: #CCCCCC">** set include in MUSIC **</a>                

<!-- END OF accordion TEST -->
    


