// Function to obtain request parameters.
function getParameter(name) {
  name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
  var regexS = "[\\?&]" + name + "=([^&#]*)";
  var regex = new RegExp(regexS);
  var results = regex.exec(window.location.href);
  return (results != null) ? unescape(results[1]) : null;
}

// Function to initialize widgets.
function init() {
    // Get alert widget.
    var widget = woodstock.widget.common.getWidget("ww_id10");
    if (widget == null) {
        return setTimeout("init();", 10);
    }
    // Set summary.
    var value = getParameter("detail");
    if (value != null) {        
        widget.setProps({
            detail: value
        });
    }
}

// Function to toggle disabled widget state.
function toggleDisabled(id) {
    var widget = woodstock.widget.common.getWidget(id);
    widget.setProps({
        disabled: !widget.getProps().disabled
    });
}
