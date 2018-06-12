/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */


// Note: Do not use multiline comments below for TLD examples as renderer XML
// files shall be used to generate Javadoc. Embedding a "*/" in a Javadoc 
// comment cuases compile errors because it terminates the outer comment.

// Set disabled state of table actions. If a selection has been made, actions
// are enabled. If no selection has been made, actions are disabled.
// 
// Note: Use setTimeout when invoking this function. This will ensure that 
// checkboxes and radiobutton are selected immediately, instead of waiting for 
// the onClick event to complete. For example: 
//
// onClick="setTimeout('initAllRows(); disableActions()', 0)"
function disableActions() {
    // Disable table actions by default.
    var table = document.getElementById("form1:table1");
    var selections = table.getAllSelectedRowsCount(); // Hidden & visible selections.
    var disabled = (selections > 0) ? false : true;

    // Set disabled state for top actions.
    document.getElementById("form1:table1:actionsTop:action1").setDisabled(disabled);
    document.getElementById("form1:table1:actionsTop:action2").setDisabled(disabled);
    document.getElementById("form1:table1:actionsTop:action3").setDisabled(disabled);
    document.getElementById("form1:table1:actionsTop:action4").setDisabled(disabled);
    webui.suntheme.dropDown.setDisabled("form1:table1:actionsTop:moreActions", disabled);

    // Set disabled state for bottom actions.
    document.getElementById("form1:table1:actionsBottom:action1").setDisabled(disabled);
    document.getElementById("form1:table1:actionsBottom:action2").setDisabled(disabled);
    document.getElementById("form1:table1:actionsBottom:action3").setDisabled(disabled);
    document.getElementById("form1:table1:actionsBottom:action4").setDisabled(disabled);
    webui.suntheme.dropDown.setDisabled("form1:table1:actionsBottom:moreActions", disabled);
}

//
// Use this function to confirm the number of selected components (i.e., 
// checkboxes or radiobuttons used to de/select rows of the table), affected by
// a delete action. This functionality requires the selectId property of the
// tableColumn component and hiddenSelectedRows property of the tableRowGroup
// component to be set.
// 
// If selections are hidden from view, the confirmation message indicates the
// number of selections not displayed in addition to the total number of
// selections. If selections are not hidden, the confirmation message indicates
// only the total selections.
function confirmDeleteSelectedRows() {
    var table = document.getElementById("form1:table1");
    return table.confirmDeleteSelectedRows();
}

// Use this function to confirm the number of selected components (i.e., 
// checkboxes or radiobuttons used to de/select rows of the table), affected by
// an action such as edit, archive, etc. This functionality requires the 
// selectId property of the tableColumn component and hiddenSelectedRows
// property of the tableRowGroup component to be set.
// 
// If selections are hidden from view, the confirmation message indicates the
// number of selections not displayed in addition to the total number of
// selections. If selections are not hidden, the confirmation message indicates
// only the total selections.
function confirmSelectedRows() {
    var table = document.getElementById("form1:table1");
    return table.confirmSelectedRows("\n\nArchive all selections?");
}

//-->
