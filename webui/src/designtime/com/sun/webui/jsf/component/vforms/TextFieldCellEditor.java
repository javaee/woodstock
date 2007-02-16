/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
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
 * Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 */
/*
 * TextFieldCellEditor.java
 *
 * Created on October 19, 2005, 2:57 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.sun.webui.jsf.component.vforms;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * A JTextField cell editor by default all the text is selected
 */
public class TextFieldCellEditor extends DefaultCellEditor  {
    
    private JTable table;
    
    public TextFieldCellEditor( JTable table, JTextField component ) {
        super( component );
        this.table = table;
    }
    
    // This method is called when a cell value is edited by the user.
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
        
        // Configure the component with the specified value
        ((JTextField)super.getComponent()).setText((String)value);
        
        if (isSelected) {
            ((JTextField)super.getComponent()).selectAll();
            table.repaint();
        }
        
        // Return the configured component
        return super.getComponent();
    }
}
