<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %> 
<%@taglib uri="http://www.sun.com/webui/webuijsf" prefix="webuijsf" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %> 
<style>
    
    .shaded {background-color: rgb(240,240,240);}
    a.highlighted {  background-color:#FFCC66; color:#000000;  }
    a.highlighted:hover {  background-color:#FF3300; cursor:pointer; text-decoration: underline; }
    .sampleBox {background-color:#eeeeee; width: 500px;}
    .sampleBoxFloating {position: absolute; right: 40px; background-color:#eeeee; max-width: 500px;}
    
</style>
<f:view>
    <webuijsf:page >
        
        <webuijsf:head title="All TextField Tests"  debug="true">
            <f:loadBundle basename="com.sun.webui.jsf.example.textfield.Resources" var="custMsgs"/>
            <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
            
        </webuijsf:head>
        <webuijsf:body>
            
            <webuijsf:form id="form1">
                
                
                <!-- Masthead -->
                <webuijsf:masthead id="masthead"
                                   productImageURL="/images/example_primary_masthead.png"
                                   productImageHeight="40"
                                   productImageWidth="188"
                                   userInfo="test_user" 
                                   serverInfo="test_server"
                                   productImageDescription="#{msgs.mastheadAltText}" />
                
                <!-- Breadcrumbs -->   
                <webuijsf:breadcrumbs id="breadcrumbs">
                    <webuijsf:hyperlink id="indexLink"
                                        text="#{msgs.index_title}"
                                        toolTip="#{msgs.index_title}"
                                        actionExpression="#{TextInputBean.showExampleIndex}" 
                                        onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                                        onMouseOut="javascript: window.status=''; return true" />
                    <webuijsf:hyperlink id="exampleLink" text="#{msgs.field_title}"/>
                </webuijsf:breadcrumbs>
                <webuijsf:contentPageTitle title="Field Component Tests" /><br/>      
                
                <!-- webuijsf:alert id="alert" summary="#  { custMsgs.info }" type="information" /><br/> -->
                
                <!-- ------------------------------------------------------- -->
                <!--  start of client side testing ------------------------- -->
                <!-- ------------------------------------------------------- -->
                <div class = "sampleBox">
                    <!-- this text field will be used for testing client side functions -->
                    <h3>Auto Validation</h3>
                    This text field is enabled for auto-validation. It triggers autovalidation on onBlur event.
                    If invalid, the label will be prepended with the error message. If valid, the message 
                    will change to a '[validated OK].<br>
                    <b><span id='messageId'>[not validated]</span>&nbsp;</b><br>
                    <webuijsf:textField 
                        id="textFieldA"   
                        text="4111 1111 1111 1111" 
                        label="Enter Credit Card Number"    
                        columns="20"
                        required="true"  
                        autoValidate="true"
                        validatorExpression="#{Payment.cardValidator}"
                    />
                    <script type="text/javascript">
                var processEvents = {
                    summary: '<h:outputText value="#{custMsgs.summary}"/>',

                    update: function(props) {
                        var domNode = document.getElementById("form1:alert");
                        
                        //make sure we have a client rendered alarm component version
                        //that supports setProps
                        if (domNode && domNode.setProps) {
                            domNode.setProps({
                                type: (props.valid == true) ? "information" : "error",
                                summary: (props.valid == true) ? processEvents.summary : props.summary,
                                detail: (props.valid == true) ? " " : props.detail
                            });
                        };
                         
                        //the default autovalidation will ONLYY invalidate ( make it red) the label
                        //on the text field.
                        //In addition, as an example, we will change the prefix before the label on the text field
                        //to display detail message of the error, or successful validation upon success
                        var date = (new Date()).toLocaleString();
                        document.getElementById('messageId').innerHTML  = 
                             ((props.valid == true)? '[validated OK]': '['+props.detail+']' ) +
                              ' on ' + date;                            
//                         if (props.valid == true)
//                            updateField({   'label': {  'value': '[validated OK]'} } );
//                        else
//                            updateField({   'label': {  'value': props.detail} } );
                         
                        
                    }
                }
                // Subscribe to validation event.
                dojo.event.topic.subscribe(
                    webui.suntheme.widget.textField.validation.endEventTopic, processEvents, "update");
                    </script>
                    
                    <script>
                    /** this function finds our specific text field and pushes the 
                     * supplied properties onto it.
                     * Usage: 
                     updateField({
                        prop1:value1
                        [, prop2:value2]
                        ...
                        });
                     */
                    function updateField(props) {
                        // Update text field.
                        var domNode = document.getElementById("form1:textFieldA");
                        domNode.setProps(props);
                    }
                    
                    
                    </script>
                    
                    <p>
                    <h3>Client Side Control Tests</h3>
                    The links below change the above rendered individual widget properties on the client side only.
                    When component is reloaded though, it will be re-rendered according to the state saved on the server.
                    <br>
                    <a class="highlighted" onclick="updateField({   required:false });">required=false</a>
                    <a class="highlighted" onclick="updateField({   required:true });">required=true</a>
                    
                    
                    <a class="highlighted" onclick="updateField({   disabled:false });">disabled=false</a>
                    <a class="highlighted" onclick="updateField({   disabled:true });">disabled=true</a>
                    
                    <a class="highlighted" onclick="updateField({   valid: false  });">valid=false</a>
                    <a class="highlighted" onclick="updateField({   valid: true });">valid=true</a>
                    <a class="highlighted" onclick="updateField({   size: 50});">columns=50</a>
                    <a class="highlighted" onclick="updateField({   size: 20});">columns=20</a>
                    <a class="highlighted" onclick="updateField({   visible: false } );">text field visible='false'</a>
                    <a class="highlighted" onclick="updateField({   visible: true  } );">text field visible='true'</a>
                    
                    <a class="highlighted" onclick="updateField({   'label': {  'value': 'label 1'} } );">label='label 1'</a>
                    <a class="highlighted" onclick="updateField({   'label': {  'value': 'label 2'} } );">label='label 2'</a>
                    <!--
                <a class="highlighted" onclick="updateField({   'label': {  'visible': false} } );">visible='false'</a>
                <a class="highlighted" onclick="updateField({   'label': {  'visible': true} } );">visible='true'</a>
                -->        
                    
                </div>
                <!-- ------------------------------------------------------- -->
                <!-- end of client side testing -->
                <!-- ------------------------------------------------------- -->
                

                
                <!-- ------------------------------------------------------- -->
                <!--  start of server side attributes ---------------------- -->
                <!-- ------------------------------------------------------- -->
                <h3>Rendering Tests</h3><p>
                
                <table>
                    <th>Test</th> <th>Status</th><th>Presentation</th>
                    <tr>
                        <td class="shaded">No-label field</td>
                        <td class="shaded">ok</td>
                        <td>            
                            <webuijsf:textField 
                                id="testNoLabel"   
                                text="4111 1111 1111 1111" 
                                ajaxify="false"
                            />            
                        </td>
                    </tr>
                    <tr>
                        <td class="shaded">Regular field</td>
                        <td class="shaded">ok</td>
                        <td>            
                            <webuijsf:textField 
                                id="testRegular"   
                                text="4111 1111 1111 1111" 
                                label="Label"  
                                ajaxify="false"
                            />            
                        </td>
                    </tr>
                    
                    <tr>
                        <td class="shaded">Required=true</td>
                        <td class="shaded">ok</td>
                        <td>      
                            <webuijsf:textField 
                                id="testRequired"   
                                text="4111 1111 1111 1111" 
                                label="Label"    
                                required="true"        
                                ajaxify="false"
                            />
                        </td>
                    </tr>
                    
                    
                    <tr>
                        <td class="shaded">Ajaxify=false</td>
                        <td class="shaded">ok</td>
                        <td>      
                            <webuijsf:textField 
                                id="testAjaxify"   
                                text="4111 1111 1111 1111" 
                                label="Label"    
                                ajaxify="false"
                            />
                        </td>
                    </tr>
                    <tr>
                        <td class="shaded">columns=50</td>
                        <td class="shaded">ok</td>
                        <td>      
                            <webuijsf:textField 
                                id="testsize"   
                                columns = "50"
                                text="4111 1111 1111 1111" 
                                label="Label"    
                                ajaxify="false"
                            />
                        </td>
                    </tr>
                    
                    
                    <tr>
                        <td class="shaded">Rendered=false</td>
                        <td class="shaded">ok</td>
                        <td>      
                            <webuijsf:textField 
                                id="testRenderedFalse"   
                                text="4111 1111 1111 1111" 
                                label="Label"    
                                rendered="false"
                                ajaxify="false"
                            />
                        </td>
                    </tr>
                    
                    <tr>
                        <td class="shaded">onMouseUp->alert()</td>
                        <td class="shaded">ok</td>
                        <td>      
                            <webuijsf:textField 
                                id="testOnMouseUp"   
                                text="4111 1111 1111 1111" 
                                label="Label"    
                                onMouseUp="javascript:alert('clicked');"
                                ajaxify="false"
                            />
                        </td>
                    </tr>
                    
                    <tr>
                        <td class="shaded">disabled=true</td>
                        <td class="shaded">ok</td>
                        <td>      
                            <webuijsf:textField 
                                id="testDisabled"   
                                text="4111 1111 1111 1111" 
                                label="Label"    
                                disabled="true"
                                ajaxify="false"
                            />
                        </td>
                    </tr>
                    
                    <tr>
                        <td class="shaded">EL expression</td>
                        <td class="shaded">ok</td>
                        <td>      
                            <webuijsf:textField 
                                id="testExpression"   
                                label="#{custMsgs.creditCard}"
                                text="#{TextInputBean.textFieldValue}"
                                ajaxify="false"
                            />
                        </td>
                    </tr>                    
                    
                    <tr>
                        <td class="shaded">w/ Validator <br>( incorrect credit card <br>number would invalidate <br>field label)</td>
                        <td class="shaded">ok</td>
                        <td>      
                            <webuijsf:textField 
                                id="testValidator"   
                                text="4111 1111 1111 1111" 
                                label="Label"                                  
                                autoValidate="true"
                                validatorExpression="#{Payment.cardValidator}"
                            />
                        </td>
                    </tr>                    
                    
                    <tr>
                        <td class="shaded">w/ Converter</td>
                        <td class="shaded">ok</td>
                        <td>      
                            <webuijsf:textField 
                                id="testConverter"   
                                text="4111 1111 1111 1111" 
                                label="Label"  
                                ajaxify="false"
                            />
                        </td>
                    </tr>                    
                    
                    
                    
                    <tr>
                        <td class="shaded">Facet "label"</td>
                        <td class="shaded">ok</td>
                        <td>      
                            <webuijsf:textField 
                                id="facetTestId"   
                                text="4111 1111 1111 1111" 
                                label="Enter Credit Card Number"    
                                required="true"  
                            >
                                <f:facet name = "label">
                                    <webuijsf:image id="image2" icon="ALARM_CRITICAL_SMALL" />
                                </f:facet>
                            </webuijsf:textField>
                        </td>
                    </tr>                    
                    
                    <tr>
                        <td class="shaded">Password mode <br>( will be emptied <br>on every submit)</td>
                        <td class="shaded">ok</td>
                        <td>      
                            <webuijsf:textField 
                                id="pwdTestId"   
                                text="secret password" 
                                label="Enter Password"    
                                required="true"  
                                passwordMode = "true"
                            >
                            </webuijsf:textField>
                        </td>
                    </tr>                    
                    
                    
                </table> 
                
                <br><br>
                <webuijsf:button primary="true" id="submitButton" text="Submit"  actionExpression="submitResults" />
                
            </webuijsf:form>
        </webuijsf:body>
    </webuijsf:page>
</f:view>

