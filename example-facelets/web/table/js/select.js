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
// comment cuases compile errors because it terminates the outer comment.

// Use this function to initialize all rows displayed in the table when the
// state of selected components change (i.e., checkboxes or radiobuttons used to
// de/select rows of the table). This functionality requires the selectId 
// property of the tableColumn component to be set.
// 
// Note: Use setTimeout when invoking this function. This will ensure that 
// checkboxes and radiobutton are selected immediately, instead of waiting for 
// the onClick event to complete. For example: 
//
// onClick="setTimeout('initAllRows(); disableActions()', 0)"
function initAllRows() {
    // Disable table actions by default.
    var table = document.getElementById("form1:table1");
    table.initAllRows();
}

//-->
