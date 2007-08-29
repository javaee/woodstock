/**
 * Invoked when a selection is made in the menu
 */
function changeEvent() {
    document.getElementById("form1:popup1").submit();
    var domNode = document.getElementById("form1:verifier");
    domNode.refresh();
}

/**
 * Display the first popup menu
 */
function displayMenuOne(event) {
    document.getElementById("form1:popup1").open(event);
}

/**
 * Display the second popup menu
 */
function displayMenuTwo(event) {
    document.getElementById("form1:popup2").open(event);
}
