<html>
<%@page language="java" %>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@taglib uri="http://www.sun.com/webui/webuijsf" prefix="webuijsf" %>
<f:view>
<webuijsf:page>
    <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
    <webuijsf:head  title="#{msgs.tree2_example3}" debug="true" webuiAll="true"
    jsfx="true">
    <webuijsf:script>       
            function init() {
                var domNode = document.getElementById("form1:tree1");
                if (domNode == null || domNode.event == null) { 
                    return setTimeout('init();', 10);
                }
                domNode.subscribe(domNode.event.load.beginTopic, this, handleLoadChildren);
            }
            
            this.handleLoadChildren = function(props) {
                
                var nodeId = props.nodeId;
                var rootNode = props.id;
                var newId = nodeId + ":child1";
                var nodeProps = 
                    {   "parent": nodeId,
                        "nodeImage": {
                            "widgetType": "image",
                            "width": 19,
                            "height": 19,
                            "title": "[Alarm:Down] Node 1",
                            "icon": "TREE_FOLDER_ALARM_MINOR",
                            "visible": true,
                            "border": 0,
                            "id": "form:tree1:DynamicTree:Node1Image"
                        },
                        "label": "Node 42",
                        "id": newId
                    };
                // invoke setProps on the tree
                var widget = woodstock4_3.widget.common.getWidget(rootNode);
                widget.addNodes(nodeProps);
                woodstock4_3.widget.common.publish(woodstock4_3.widget.tree.event.load.endTopic, [{
                    id: nodeId}]);
            }
            
        </webuijsf:script>
    </webuijsf:head>
<webuijsf:body onLoad="init();">
    
<webuijsf:form id="form1">

            <webuijsf:masthead id="masthead"
              productImageURL="/images/example_primary_masthead.png"
              productImageHeight="40"
              productImageWidth="188"
              userInfo="test_user" 
              serverInfo="test_server"
              productImageDescription="#{msgs.mastheadAltText}" />
                 
            <webuijsf:breadcrumbs id="breadcrumbs">
              <webuijsf:hyperlink url="../index.jsp" text="#{msgs.index_title}"/>
              <webuijsf:hyperlink url="../tree2/index.jsp" text="#{msgs.tree2_index}"/>
              <webuijsf:hyperlink url="../tree2/example3.jsp" text="#{msgs.tree2_example3}"/>
            </webuijsf:breadcrumbs>
	    
	    <!-- PageTitle... -->
	    <webuijsf:contentPageTitle id="pageTitle" title="#{msgs.index_title}" 
                helpText="#{msgs.tree2_example3_help}" />

          <span id="tree1_holder">
          <script type="text/javascript">

            toggleClicked = function () {
                alert("toggle node clicked");
            }
                
            woodstock4_3.widget.common.createWidget('tree1_holder', {
                "widgetType": "tree",
                "label": "Root Node",
                "parent": "null",
                "nodeImage": {
                    "widgetType": "image",
                    "width": 19,
                    "height": 19,
                    "title": "[Alarm:Down] Node 0",
                    "icon": "TREE_FOLDER_ALARM_DOWN",
                    "visible": true,
                    "border": 0,
                    "id": "form:tree1:DynamicTree:Node0Image"
                },
                "childNodes" : [ 
                    {   "parent": "form1:tree1",
                        "nodeImage": {
                            "widgetType": "image",
                            "width": 19,
                            "height": 19,
                            "title": "[Alarm:Down] Node 1",
                            "icon": "TREE_FOLDER_ALARM_MINOR",
                            "visible": true,
                            "border": 0,
                            "id": "form:tree1:DynamicTree:Node1Image"
                        },
                        "label": "Node 1",
                        "id": "form1:tree1:node1"
                    },
                    {   "parent": "form1:tree1",
                        "nodeImage": {
                            "widgetType": "image",
                            "width": 19,
                            "height": 19,
                            "title": "[Alarm:Warning] Node 2",
                            "icon": "TREE_FOLDER_ALARM_MINOR",
                            "visible": true,
                            "border": 0,
                            "id": "form:tree1:DynamicTree:Node2Image"
                        },
                        "label": "Node 2",
                        "id": "form1:tree1:node2"
                    },
                    {   "parent": "form1:tree1",
                        "nodeImage": {
                            "widgetType": "image",
                            "width": 19,
                            "height": 19,
                            "title": "[Alarm:Down] Node 3",
                            "icon": "TREE_FOLDER_ALARM_CRITICAL",
                            "visible": true,
                            "border": 0,
                            "id": "form:tree1:DynamicTree:Node3Image"
                        },
                        "childNodes" : [ 
                          {
                            "parent": "form1:tree1:node3",
                            "nodeImage": {
                                "widgetType": "image",
                                "width": 19,
                                "height": 19,
                                "title": "[Alarm:Warning] Node 31",
                                "icon": "TREE_FOLDER_ALARM_MAJOR",
                                "visible": true,
                                "border": 0,
                                "id": "form:tree1:DynamicTree:Node31Image"
                                },
                            "label": "Node 31",
                            "id": "form1:tree1:node1:node31"
                            },
                            {
                            "parent": "form1:tree1:node3",
                            "nodeImage": {
                                "widgetType": "image",
                                "width": 19,
                                "height": 19,
                                "title": "[Alarm:Warning] Node 32",
                                "icon": "TREE_FOLDER_ALARM_MAJOR",
                                "visible": true,
                                "border": 0,
                                "id": "form:tree1:DynamicTree:Node32Image"
                                },
                            "label": "Node 32",
                            "id": "form1:tree1:node1:node32"
                            }
                        ],
                        "label": "Node 3",
                        "id": "form1:tree1:node3"
                    },
                    {   "parent": "form1:tree1",
                        "nodeImage": {
                            "widgetType": "image",
                            "width": 19,
                            "height": 19,
                            "title": "[Alarm:Down] Node 4",
                            "icon": "TREE_FOLDER_ALARM_CRITICAL",
                            "visible": true,
                            "border": 0,
                            "id": "form:tree1:DynamicTree:Node4Image"
                        },
                        "childNodes" : [ 
                          {
                            "parent": "form1:tree1:node4",
                            "nodeImage": {
                                "widgetType": "image",
                                "width": 19,
                                "height": 19,
                                "title": "[Alarm:Warning] Node 41",
                                "icon": "TREE_FOLDER_ALARM_MAJOR",
                                "visible": true,
                                "border": 0,
                                "id": "form:tree1:DynamicTree:Node41Image"
                            },
                            "label": "Node 41",
                            "id": "form1:tree1:node1:node41"
                            }
                        ],
                        "label": "Node 4",
                        "onToggleNodeClick": "toggleClicked();",
                        "id": "form1:tree1:node4"
                    }
                ],
                "label": "Node 0",
                "toolTip": "Node 0 Tooltip",
                "selected": "false",
                "loadOnExpand": "true",
                "onToggleNodeClick": "toggleClicked",
                "id": "form1:tree1"
                }
            );
            
          </script>
        </span>

</div>
</webuijsf:form>
</webuijsf:body> 
</webuijsf:page>
</f:view>
</html> 
