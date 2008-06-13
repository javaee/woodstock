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


