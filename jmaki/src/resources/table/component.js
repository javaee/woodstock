/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the license at
 * https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */

jmaki.namespace("@JMAKI_NS@.table");

/*
 * jMaki wrapper for Woodstock table widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Follows the table data model.
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the widget.
 * publish:   Topic to publish jMaki events to; if not specified, the
 *            default topic is "/woodstock/table".
 * subscribe: Topic to subscribe to for events; if not specified, the
 *            default topic is "/woodstock/table".
 * id:        User specified widget identifier; if not specified, the
 *            jMaki auto-generated identifier is used.
 * 
 * This widget subscribes to the following jMaki events:
 *
 * event type 	 argument (object literal)
 * addRow 	{ value: <row>}
 * addRows 	{ value: [<row>]}
 * clear 	{} 
 * removeRow 	{targetId : < rowId>}
 * select 	{targetId : < rowId>}
 * updateRow 	{ targetId :<rowId>, value: <row> } 
 * 
 * This widget publishes the following jMaki events:
 * 
 * none 
 */
@JMAKI_NS@.table.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/woodstock/table"];
    this._publish = "/woodstock/table";
    this._subscriptions = [];
    this._wid = wargs.uuid;
    if (wargs.id) {
        this._wid = wargs.id;
    } 
    if (wargs.publish) {
	// User supplied a specific topic to publish to.
	this._publish = wargs.publish;
    }
    if (wargs.subscribe) {
	// User supplied one or more specific topics to subscribe to.
        if (typeof wargs.subscribe == "string") {
            this._subscribe = [];
            this._subscribe.push(wargs.subscribe);
        } else {
            this._subscribe = wargs.subscribe;
        }
    }

    // Subscribe to jMaki events
    for (var i = 0; i < this._subscribe.length; i++) {
        var s1 = jmaki.subscribe(this._subscribe[i] + "/select", 
        woodstock4_3.widget.common._hitch(this, "_selectCallback"));
        this._subscriptions.push(s1);

        var s2 = jmaki.subscribe(this._subscribe[i] + "/clear", 
        woodstock4_3.widget.common._hitch(this, "_clearCallback"));
        this._subscriptions.push(s2);

        var s3 = jmaki.subscribe(this._subscribe[i] + "/removeRow", 
        woodstock4_3.widget.common._hitch(this, "_removeRowCallback"));
        this._subscriptions.push(s3);

        var s4 = jmaki.subscribe(this._subscribe[i] + "/updateRows", 
        woodstock4_3.widget.common._hitch(this, "_updateRowCallback"));
        this._subscriptions.push(s4);

        var s5 = jmaki.subscribe(this._subscribe[i] + "/addRow", 
        woodstock4_3.widget.common._hitch(this, "_addRowCallback"));
        this._subscriptions.push(s5);

        var s6 = jmaki.subscribe(this._subscribe[i] + "/addRows", 
        woodstock4_3.widget.common._hitch(this, "_addRowCallback"));
        this._subscriptions.push(s6);

    }

    // Create Woodstock widget.
    this._create(wargs);
};

// Create Woodstock widget.
@JMAKI_NS@.table.Widget.prototype._create = function(wargs) {

    // Get the jMaki wrapper properties for a Woodstock table.
    var props = {};
    if (wargs.args != null) {
        woodstock4_3._base.proto._extend(props, wargs.args);
    }
    if (wargs.value != null) {
        woodstock4_3._base.proto._extend(props, wargs.value);
    }
    this._convertModel(props);

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "table";

    // Create the Woodstock table widget.
    var span_id = wargs.uuid + "_span";
    woodstock4_3.widget.common.createWidget(span_id, props);

    // connect events
    //  =currently disabled . pending issue 1264 fix
    //woodstock4_3.widget.common.subscribe(woodstock4_3.widget.table.event.submit.beginTopic, this, "_selectCallback");

};

// Unsubscribe from jMaki events and destroy the Woodstock widget.
@JMAKI_NS@.table.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
    woodstock4_3.widget.common.destroyWidget(this._wid);
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.table.Widget.prototype.postLoad = function() {
    // Do nothing...
} 


// selects a row
// event payload:
// select 	{targetId : < rowId>}
// WOODSTOCK does not support this function
@JMAKI_NS@.table.Widget.prototype._selectCallback = function(payload) {
    return false;
};

// clear selection 
@JMAKI_NS@.table.Widget.prototype._clearCallback = function(payload) {
    var widget = woodstock4_3.widget.common.getWidget(this._wid);
    if (widget) {
      var props = {};
      props.rowGroups = [];
      widget.setProps(props);
    }
};

// remove row
// event payload:
// removeRow 	{targetId : < rowId>}
@JMAKI_NS@.table.Widget.prototype._removeRowCallback = function(payload) {
    if (!payload || !payload.targetId) {
        return false;
    }
    var widget = woodstock4_3.widget.common.getWidget(this._wid);
    if (widget) {
	// XXXX - Not yet implemented.        
    }

};

// update row
// event payload:
// updateRow 	{ targetId :<rowId>, value: <row> } 
@JMAKI_NS@.table.Widget.prototype._updateRowCallback = function(payload) {
    if (!payload || !payload.targetId) {
        return false;
    }
    var widget = woodstock4_3.widget.common.getWidget(this._wid);
    if (widget) {
	// XXXX - Not yet implemented.        
    }

};

// add row(s)
// event payload:
// addRow 	{ value: <row>}
// addRows 	{ value: [<row>]}
@JMAKI_NS@.table.Widget.prototype._addRowCallback = function(payload) {
    if (!payload || !payload.targetId) {
        return false;
    }
    var widget = woodstock4_3.widget.common.getWidget(this._wid);
    if (widget) {
        // XXXX - Not yet implemented.
    }

};


// row is selected
// publishes onSelect event
// onSelect 	 {widgetId : uuid , topic : , type : 'onSelect' , targetId : selected_row} 	 
@JMAKI_NS@.table.Widget.prototype._onRowSelected = function(props) {
    if (!props || !props.id) {
        return false;
    }

    var payload = {};
    payload.targetId = props.id;
    jmaki.publish(this._publish, payload );
};


// Converts jmaki model to props digestable by woodstock table widget
@JMAKI_NS@.table.Widget.prototype._convertModel = function(props){
    
    if (!props.columns )  {
        return false;
    }

    //establish table level properties
    if (!props.caption ) {
        props.caption = "Table Caption";
    }
    // widgetType and id are expected to be here
    //model supports only one rowGroup
    if (props.rowGroups != null) {
        return true; //model is assumed to be there
    }
   
    //create rowGroup
    props.rowGroups = [];
    var gr = {};
    props.rowGroups.push(gr);
   
    gr.first = 0; //default
    gr.paginationControls = false; //default
    gr.id = props.id +"gr0";
    gr.totalRows = (props.rows)? props.rows.length : 0;
    gr.maxRows = gr.totalRows; //default
    gr.widgetType = "tableRowGroup";
   
   
    //column definition
    gr.columns = [];
    var total_columns = 0;
    var colNamesMap = {};
    for (var i = 0; i < props.columns.length; i++) {
        var colDef = props.columns[i];
        var col = {};
        gr.columns.push(col);
        col.headerText = (colDef.label)? colDef.label: "Column " + total_columns;
        col.id = (colDef.id)? colDef.id: props.id +"_col" + total_columns;
        col.noWrap = false;
        col.sort = false;
        colNamesMap[colDef.label.toLowerCase()] = i;
        total_columns++;
    }

    gr.rows = [];
    for (var i = 0; i < props.rows.length; i++) {
        var row = [];
        gr.rows.push(row);
            
        //create iniially empty cells
        for (var j = 0; j < total_columns; j++) {
            var cell = {};
            row.push(cell);
            
            cell.widgetType = "text";
            cell.value = "---"; //no data
            cell.id  = props.id +"col" + j + "row" + i;
                
        }
            
        for (var item in props.rows[i]) {
            var lItem  = item.toLowerCase();
            if (colNamesMap[item] != null) { //number 0+
                var position = colNamesMap[lItem];
                if (position < row.length){
                    row[position].value = props.rows[i][lItem];
                }
                
            }
        }
    }    

};
