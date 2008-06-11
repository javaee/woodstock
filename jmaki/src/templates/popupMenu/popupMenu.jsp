<a:widget name='@JS_NAME@.popupMenu' 
    value = '{
        "menu": [
            {
                "label" : "action",
                "action" : {
                    "topic" : "/foo",
                    "message" : {
                        "value" : "test.jsp" 
                    } 
                } 
            },        
            {
                "label": "Yahoo!",
                "href" : "http://www.yahoo.com" 
            },
            {
                "label": "Sun Microsystems",
                "href": "http://www.sun.com",
                "style": {
                    "checked": true 
                } 
            },
            {
                "label": "Oracle",
                "href": "http://www.oracle.com" 
            } 
        ]
    }' />

    
 <!-- BEGIN OF popupMenu TEST
            
         popupMenu is displayed in response to the event only.
         The snippet below provides an opportunity to test it,
         and can be safely removed.-->

    <a onMouseOver="jmaki.publish('/woodstock/popupMenu/*/open', {event: event});"
       style = "background: grey">** Mouse Over to test the popupMenu **</a>                

<!-- END OF popupMenu TEST -->
    



