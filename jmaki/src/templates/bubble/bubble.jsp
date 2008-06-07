<a:widget name="@JS_NAME@.bubble" 
        value = "{
                'autoClose': false,
                'visible':false,
                'closeButton':true,
                'title':'Bubble Help Title',
                'contents':['Bubble contents...']
                }" />
                
            <!-- BEGIN OF BUBBLE TEST
            
                 Bubble is displayed in response to the event only.
                 The snippet below provides an opportunity to test it,
                 and can be safely removed.-->

            <a onMouseOver="jmaki.publish('/woodstock/bubble/*/open', {event: event});"
               onMouseOut ="jmaki.publish('/woodstock/bubble/*/close', {event: event});"
               style = "background: grey">** Mouse Over to test the Bubble **</a>                

             <!-- END OF BUBBLE TEST -->
