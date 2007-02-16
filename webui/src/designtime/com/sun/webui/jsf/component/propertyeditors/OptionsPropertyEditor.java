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
package com.sun.webui.jsf.component.propertyeditors;

import com.sun.rave.propertyeditors.TabularPropertyEditor;
import com.sun.rave.propertyeditors.TabularPropertyModel;
import com.sun.rave.propertyeditors.util.JavaInitializer;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.model.Option;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.event.TableModelListener;

/**
 * An editor for properties that take lists of options, such as the
 * <code>items</code> property on all list-like components.
 *
 * @author gjmurphy
 */
public class OptionsPropertyEditor extends TabularPropertyEditor {
    
    public OptionsPropertyEditor() {
    }
    
    private OptionsTableModel tableModel;
    
    protected TabularPropertyModel getTabularPropertyModel() {
        if (tableModel == null)
            tableModel = new OptionsTableModel();
        return tableModel;
    }
    
    public String getJavaInitializationString() {
        Object value = this.getValue();
        if (!(value instanceof Option[]))
            return null;
        Option[] options = (Option[]) value;
        StringBuffer buffer = new StringBuffer();
        buffer.append("new " + Option.class.getName() + "[] {");
        for (int i = 0; i < options.length; i++) {
            if (i > 0)
                buffer.append(", ");
            buffer.append("new " + Option.class.getName() + "(");
            buffer.append(JavaInitializer.toJavaInitializationString(options[i].getValue()));
            buffer.append(", ");
            buffer.append(JavaInitializer.toJavaInitializationString(options[i].getLabel()));
            buffer.append(")");
        }
        buffer.append("}");
        return buffer.toString();
    }
    
    
    static final String[] columnNames = new String[] {
        DesignMessageUtil.getMessage(OptionsPropertyEditor.class, "Options.label"), //NOI18N
                DesignMessageUtil.getMessage(OptionsPropertyEditor.class, "Options.value") //NOI18N
    };
    
    static class OptionsTableModel implements TabularPropertyModel {
        
        final int labelIndex = 0;
        final int valueIndex = 1;
        
        ArrayList options;
        
        public void setValue(Object value) {
            if (value instanceof Option[]) {
                options = new ArrayList();
                Option[] values = (Option[]) value;
                for (int i = 0; i < values.length; i++)
                    options.add(new Option(values[i].getValue(), values[i].getLabel()));
            }
        }
        
        public Object getValue() {
            if (options == null)
                return null;
            return options.toArray(new Option[options.size()]);
        }
        
        public void addTableModelListener(TableModelListener listener) {
        }
        
        public void removeTableModelListener(TableModelListener listener) {
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (columnIndex < 0 || columnIndex >= columnNames.length)
                return false;
            if (rowIndex < 0 || rowIndex >= options.size())
                return false;
            return true;
        }
        
        public void setValueAt(Object newValue, int rowIndex, int columnIndex) {
            if (isCellEditable(rowIndex, columnIndex)) {
                if (columnIndex == labelIndex)
                    ((Option) options.get(rowIndex)).setLabel(newValue.toString());
                else
                    ((Option) options.get(rowIndex)).setValue(newValue);
            }
        }
        
        public Class getColumnClass(int columnIndex) {
            if (columnIndex == labelIndex)
                return String.class;
            else if (options.size() > 0 && options.get(0) != null)
                return ((Option) options.get(0)).getValue().getClass();
            else
                return Object.class;
        }
        
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (rowIndex >= 0 && rowIndex < options.size()) {
                if (columnIndex == labelIndex)
                    return ((Option) options.get(rowIndex)).getLabel();
                else
                    return ((Option) options.get(rowIndex)).getValue();
            }
            return null;
        }
        
        public int getRowCount() {
            return options.size();
        }
        
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }
        
        public int getColumnCount() {
            return columnNames.length;
        }
        
        public boolean canAddRow() {
            return true;
        }
        
        public boolean addRow() {
            options.add(new Option(columnNames[valueIndex], columnNames[labelIndex]));
            return true;
        }
        
        public boolean canMoveRow(int indexFrom, int indexTo) {
            if (indexFrom < 0 || indexTo < 0 || indexFrom == indexTo )
                return false;
            if (indexFrom >= options.size() || indexTo >= options.size())
                return false;
            return true;
        }
        
        public boolean moveRow(int indexFrom, int indexTo) {
            if (!canMoveRow(indexFrom, indexTo))
                return false;
            options.add(indexTo, options.remove(indexFrom));
            return true;
        }
        
        public boolean canRemoveRow(int index) {
            if (index < 0 || index >= options.size())
                return false;
            return true;
        }
        
        public boolean removeRow(int index) {
            if (!canRemoveRow(index))
                return false;
            options.remove(index);
            return true;
        }
        
        public boolean removeAllRows() {
            if( options.isEmpty() )
                return true;
            
            int numRows = options.size();
            options.clear();
            
            return true;
        }
    }
    
}
