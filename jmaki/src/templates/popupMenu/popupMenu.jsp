<a:widget name='@JS_NAME@.popupMenu' 
    value = '{
        "menu": [
            {
                "label": "Visit Sun Microsystems",
                "href": "http://www.sun.com",
                "style": {
                    "checked": true 
                } 
            },
            {
                "label" : "Send Action",
                "action" : {
                    "topic" : "/foo",
                    "message" : {
                        "value" : "test.jsp" 
                    } 
                } 
            },        
          
            {
                "label": "Publish Event",
                "topic": "/topic" 
            } 
        ]
    }' />

    
 <!-- BEGIN OF popupMenu TEST
            
         popupMenu is displayed in response to the event only.
         The snippet below provides an opportunity to test it,
         and can be safely removed.-->

    <a onMouseOver="jmaki.publish('/woodstock/popupMenu/open', {event: event});"
       style = "background: grey">** Mouse Over to test the popupMenu **</a>                

<!-- END OF popupMenu TEST -->
    



