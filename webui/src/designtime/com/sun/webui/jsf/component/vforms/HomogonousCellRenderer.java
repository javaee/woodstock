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
 * HomogonousCellRenderer.java
 *
 * Created on October 19, 2005, 2:54 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.sun.webui.jsf.component.vforms;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * A delegating renderer class that consistently sets the background color
 * of cells to reflect "selected" and "unselected" states.
 */
public class HomogonousCellRenderer extends DefaultTableCellRenderer {
    
    private static Color SELECTION_BACKGROUND =
            UIManager.getDefaults().getColor("TextField.selectionBackground");
    
    private static Color SELECTION_FOREGROUND =
            UIManager.getDefaults().getColor("TextField.selectionForeground");
    
    private static Color BACKGROUND =
            UIManager.getDefaults().getColor("TextField.background");
    
    private static Color FOREGROUND =
            UIManager.getDefaults().getColor("TextField.foreground");
    
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table,  value, isSelected, hasFocus, row, column);
        
        // To make the selection more visual
        
        //if( hasFocus )
        //    setBorder( new LineBorder(Color.WHITE) );
        
        if (isSelected) {
            c.setBackground(SELECTION_BACKGROUND);
            c.setForeground(SELECTION_FOREGROUND);
        }
        else {
            c.setBackground(BACKGROUND);
            c.setForeground(FOREGROUND);
        }
        
        return c;
    }
}
