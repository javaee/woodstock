//<!--
// The contents of this file are subject to the terms
// of the Common Development and Distribution License
// (the License).  You may not use this file except in
// compliance with the License.
// 
// You can obtain a copy of the license at
// https://woodstock.dev.java.net/public/CDDLv1.0.html.
// See the License for the specific language governing
// permissions and limitations under the License.
// 
// When distributing Covered Code, include this CDDL
// Header Notice in each file and include the License file
// at https://woodstock.dev.java.net/public/CDDLv1.0.html.
// If applicable, add the following below the CDDL Header,
// with the fields enclosed by brackets [] replaced by
// you own identifying information:
// "Portions Copyrighted [year] [name of copyright owner]"
// 
// Copyright 2008 Sun Microsystems, Inc. All rights reserved.
//

// Note: Do not use multiline comments below for TLD examples as renderer XML
// files shall be used to generate Javadoc. Embedding a "*/" in a Javadoc 
// comment causes compile errors because it terminates the outer comment.
//

function RatingListener(n) {
    this.ratingID = "form:rating" + n;
    this.avgtextID = "form:avgtext" + n;
}

// Callback for after state change
function OnSubmit(props) {
    if (props.id != this.ratingID)
        return;

    // Get rating node
    var ratingNode = document.getElementById(this.ratingID);
    if (ratingNode == null)
        return;

    // Get properties for the node
    props = ratingNode.getProps();

    // If autoSubmit disabled, return.
    if (!props.autoSubmit)
        return;

    // Get text node for actual average grade and refresh
    var textNode = document.getElementById(this.avgtextID);
    if (textNode != null)
        textNode.refresh();

    // Get rating node for average grade and refresh
    var ratingAvgNode = document.getElementById(this.ratingID + "Avg");
    if (ratingAvgNode != null)
        ratingAvgNode.refresh();
}
RatingListener.prototype.onSubmit = OnSubmit;

function rating_init() {
    for (var i=1; i<=3; i++) {
        var domNode = document.getElementById("form:rating" + i);
        if (domNode == null || domNode.event == null) {
            return setTimeout('rating_init();', 10);
        }
        var listener = new RatingListener(i);
        var props = domNode.getProps();

        if (props.autoSubmit) {
            // autoSubmit is enabled, so subscribe to post-submit event
            domNode.subscribe(domNode.event.submit.endTopic, listener, listener.onSubmit);
        }
    }
}
