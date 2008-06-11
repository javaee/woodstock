<html>
<%@page language="java" %>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@taglib uri="http://www.sun.com/webui/webuijsf" prefix="webuijsf" %>
<f:view>
<webuijsf:page>
    <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
    <webuijsf:head  title="#{msgs.tree2_example1}" debug="true" webuiAll="true"
    jsfx="true">
    <webuijsf:script>       
            var count = 42;
            addChild = function() {
                var nodeProps = 
                    {   "parent": "form1:tree1:node4",
                        "image": {
                            "widgetType": "image",
                            "width": 19,
                            "height": 19,
                            "title": "[Alarm:Down] Node " + count,
                            "icon": "TREE_FOLDER_ALARM_MINOR",
                            "visible": true,
                            "border": 0,
                            "id": "form:tree1:DynamicTree:Image:" + count
                        },
                        "label": "Node " + count,
                        "id": "form1:tree1:node4:node" + count
                    };
                // invoke setProps on the tree
                count++;
                var widget = woodstock4_3.widget.common.getWidget("form1:tree1");
                widget.addNodes(nodeProps);
                return false;
            }
            
            deleteSelectedNodes = function() {
                var widget = woodstock4_3.widget.common.getWidget("form1:tree1");
                var selectedNodes = widget.getSelectedNodes();
                if (selectedNodes) {
                    for (var i=0; i<selectedNodes.length; i++) {
                        widget.deleteNode(selectedNodes[i])
                    }
                }
                return false;
            }
            
            listSelectedNodes = function() {
                var widget = woodstock4_3.widget.common.getWidget("form1:tree1");
                var selectedNodes = widget.getSelectedNodes();
                if (selectedNodes) {
                    // var stxtWidget = woodstock4_3.widget.common.getWidget("form1:stxt1");
                    var nodes = "Selected Nodes: ";
                    for (var i=0; i<selectedNodes.length; i++) {
                        nodes = nodes + selectedNodes[i] + ", ";
                    }
                    var divNode = document.getElementById("stxt1");
                    divNode.innerHTML = nodes;
                    // stxtWidget.setProps({id: "form1:stxt1", text: nodes});
                }
                return false;
            }
            
        </webuijsf:script>
    </webuijsf:head>
<webuijsf:body>
    
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
              <webuijsf:hyperlink url="../tree2/example1.jsp" text="#{msgs.tree2_example1}"/>
            </webuijsf:breadcrumbs>

	    <webuijsf:contentPageTitle id="pageTitle" title="#{msgs.index_title}" 
                helpText="#{msgs.tree2_example1_help}" />
<table width="100%">
    <tr valign="top"><td>
          <span id="tree1_holder">
          <script type="text/javascript">

            toggleClicked = function () {
                alert("toggle node clicked");
            }
                
            woodstock4_3.widget.common.createWidget('tree1_holder', {
                "widgetType": "tree",
                "label": "Root Node",
                "parent": "null",
                "image": {
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
                        "image": {
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
                        "expanded": "true",
                        "id": "form1:tree1:node1"
                    },
                    {   "parent": "form1:tree1",
                        "image": {
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
                        "expanded": "true",
                        "id": "form1:tree1:node2"
                    },
                    {   "parent": "form1:tree1",
                        "image": {
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
                            "image": {
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
                            "expanded": "true",
                            "id": "form1:tree1:node1:node31"
                            },
                            {
                            "parent": "form1:tree1:node3",
                            "image": {
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
                            "expanded": "true",
                            "id": "form1:tree1:node1:node32"
                            }
                        ],
                        "label": "Node 3",
                        "expanded": "true",
                        "id": "form1:tree1:node3"
                    },
                    {   "parent": "form1:tree1",
                        "image": {
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
                            "image": {
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
                            "id": "form1:tree1:node4:node41"
                            }
                        ],
                        "label": "Node 4",
                        "expanded": "true",
                        "id": "form1:tree1:node4"
                    }
                ],
                "label": "Node 0",
                "toolTip": "Node 0 Tooltip",
                "selected": "false",
                "id": "form1:tree1"
                }
            );
            
          </script>
        </span>
</td>
<td>
    <webuijsf:hyperlink id="link1"
        onClick="addChild(); return false;" text="#{msgs.tree2_example1_addNode}"/>
    <webuijsf:staticText text="#{msgs.tree2_example1_addNode_msg}" />
    <br/>
    <webuijsf:hyperlink id="link2" 
        onClick="deleteSelectedNodes(); return false;" text="#{msgs.tree2_example1_deleteNode}" />
    <webuijsf:staticText text="#{msgs.tree2_example1_deleteNode_msg}" />
     <br/>
    <webuijsf:hyperlink id="link3" 
                        onClick="listSelectedNodes(); return false;" text="#{msgs.tree2_example1_selectNode}" />
    <webuijsf:staticText text="#{msgs.tree2_example1_selectNode_msg}" />
   
    <br/>
    <div id="stxt1"></div>
    
</td>
    </tr>
</table>
</webuijsf:form>
</webuijsf:body> 
</webuijsf:page>
</f:view>
</html> 
