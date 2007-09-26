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
 * TableDesignUtils.java
 * Created on August 24, 2005, 10:10 AM
 * Version 1.0
 */

package com.sun.webui.jsf.component.table;

import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.faces.FacesDesignContext;
import com.sun.webui.jsf.component.Table;
import com.sun.webui.jsf.component.TableRowGroup;
import com.sun.webui.jsf.model.DefaultTableDataProvider;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Logger;

/**
 * Helper class for Table designtime
 * @author Winston Prakash
 */
public final class TableDesignHelper {
    
    private static final String SOURCE_DATA_PROPERTY = "sourceData";
    
    public static final String DEFAULT_TABLE_DATA_PROVIDER =  "defaultTableDataProvider"; //NOI18N
    
    private static final int INITIAL_TABLE_COLUMN_WIDTH = 150;
    private static final String WIDTH_PROPRTY = "width";
    
    // Should not be instantiated
    private TableDesignHelper() {}
    
    /**
     * Create the default table model bean
     */
    public static DesignBean createDefaultDataProvider(DesignBean tableBean){
        FacesDesignContext fcontext = (FacesDesignContext) tableBean.getDesignContext();
        DesignBean[] defTableDataProviderBeans = fcontext.getBeansOfType(DefaultTableDataProvider.class);
        if ((defTableDataProviderBeans == null) || (defTableDataProviderBeans.length < 1)){
            DesignBean defaultDataProvider = fcontext.createBean(DefaultTableDataProvider.class.getName(), null, null);
            defaultDataProvider.setInstanceName(DEFAULT_TABLE_DATA_PROVIDER);
            return defaultDataProvider;
        }else{
            return defTableDataProviderBeans[0];
        }
    }
    
    public static boolean isDefaultDataProvider(DesignBean tableBean, DesignBean dataProviderBean){
        FacesDesignContext fcontext = (FacesDesignContext) tableBean.getDesignContext();
        DesignBean[] defTableDataProviderBeans = fcontext.getBeansOfType(DefaultTableDataProvider.class);
        if ((defTableDataProviderBeans != null) && (defTableDataProviderBeans.length > 0)){
            // Always assume there can be only one Default Table Data Provider per page
            if(dataProviderBean == defTableDataProviderBeans[0]){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Delete the default model if another data model data is used to fill the table row group
     */
    public static void deleteDefaultDataProvider(DesignBean designBean){
        DesignBean tableBean;
        FacesDesignContext fcontext = (FacesDesignContext) designBean.getDesignContext();
        DesignBean[] defTableDataProviderBeans = fcontext.getBeansOfType(DefaultTableDataProvider.class);
        // Always assume there can be only one Default Table Data Provider per page context
        if ((defTableDataProviderBeans != null) && (defTableDataProviderBeans.length > 0)){
            // Check if the bean passed is a TableRowGroup, if so see if the
            // containing table has anyother row group that references the
            // Default Table Provider
            if (designBean.getInstance() instanceof TableRowGroup){
                tableBean = designBean.getBeanParent();
                int childCount = tableBean.getChildBeanCount();
                for(int j=0; j< childCount; j++){
                    DesignBean childBean = tableBean.getChildBean(j);
                    // Don't check the passed TableRowGroup bean it is being deleted anyway
                    if ((childBean.getInstance() instanceof TableRowGroup) && (childBean != designBean)){
                        String sourceDataStr = null;
                        DesignProperty designProperty = childBean.getProperty(SOURCE_DATA_PROPERTY);
                        if(designProperty != null){
                            sourceDataStr = designProperty.getValueSource();
                            String modelBindingExpr = fcontext.getBindingExpr(defTableDataProviderBeans[0]);
                            if(sourceDataStr.startsWith(modelBindingExpr)){
                                // OK there is a reference so return with out deleting
                                return;
                            }
                        }
                    }
                }
            }else{
                tableBean = designBean;
            }
            DesignBean[] tableBeans = fcontext.getBeansOfType(Table.class);
            if (tableBeans != null){
                for(int i=0; i<tableBeans.length; i++){
                    // We don't have to check the passed bean. It is being deleted anyway
                    // or already checked if passed bean is a TableRowGroup
                    if(tableBean != tableBeans[i]){
                        int childCount = tableBeans[i].getChildBeanCount();
                        for(int j=0; j< childCount; j++){
                            DesignBean childBean = tableBeans[i].getChildBean(j);
                            if (childBean.getInstance() instanceof TableRowGroup){
                                TableRowGroup tableRowGroup = (TableRowGroup) childBean.getInstance();
                                String sourceDataStr = null;
                                DesignProperty designProperty = childBean.getProperty(SOURCE_DATA_PROPERTY);
                                if(designProperty != null){
                                    sourceDataStr = designProperty.getValueSource();
                                    String modelBindingExpr = fcontext.getBindingExpr(defTableDataProviderBeans[0]);
                                    if(modelBindingExpr.startsWith(sourceDataStr)){
                                        // OK there is a reference so return with out deleting
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for(int i=0; i<defTableDataProviderBeans.length; i++){
                fcontext.deleteBean(defTableDataProviderBeans[i]);
            }
        }
    }
    
    /**
     * Find the first TableRowGroup bean. Should never return null!
     */
    public static DesignBean getTableRowGroupBean(DesignBean tableBean){
        DesignBean tableRowGroupBean = null;
        int childCount = tableBean.getChildBeanCount();
        for(int i=0; i< childCount; i++){
            tableRowGroupBean = tableBean.getChildBean(i);
            if (tableRowGroupBean.getInstance() instanceof TableRowGroup){
                break;
            }
        }
        return tableRowGroupBean;
    }
    
    /**
     * Find the TableColumnBean corresponding to the column no.
     * Should never return null!
     */
    public static DesignBean getTableColumnBean(DesignBean tableRowGroupBean, int colNo){
        DesignBean tableColumnBean = null;
        int childCount = tableRowGroupBean.getChildBeanCount();
        for(int i=0; i< childCount; i++){
            tableColumnBean = tableRowGroupBean.getChildBean(i);
            //TableColumn tableColumn = (TableColumn) tableColumnBean.getInstance();
            if( i == colNo){
                break;
            }
        }
        return tableColumnBean;
    }
    
    public static void adjustTableWidth(DesignBean tableBean, int oldColumnWidth, int newColumnWidth) {
        DesignBean tableRowGroupBean = getTableRowGroupBean(tableBean);
        if (tableRowGroupBean == null) {
            return;
        }
        // Adjust the width of the table in its style property
        int tableWidth = -1;

        DesignProperty widthProperty = tableBean.getProperty(WIDTH_PROPRTY); //NOI18N
        String widthValue = (String) widthProperty.getValue();

        if (widthProperty.getValue() != null) {
            try {
                tableWidth = Integer.parseInt(widthValue);
            } catch (Exception exc) {
                tableWidth = -1;
            }
        }

        int childCount = tableRowGroupBean.getChildBeanCount();

        if (tableWidth == -1) {
            //System.out.println("Table width null! ");
            for (int i = 0; i < childCount; i++) {
                DesignBean tableColumnBean = tableRowGroupBean.getChildBean(i);
                DesignProperty columnWidthProperty = tableColumnBean.getProperty(WIDTH_PROPRTY); //NOI18N
                if ((columnWidthProperty != null) && (columnWidthProperty.getValue() != null)) {
                    try {
                        int colWidth = Integer.parseInt((String) columnWidthProperty.getValue());
                        //System.out.println("Width of column - " + i + " is " + colWidth);
                        tableWidth += colWidth;
                    } catch (Exception exc) {
                        Logger.getLogger(TableDesignHelper.class.getName()).info(exc.getLocalizedMessage());
                        tableWidth += INITIAL_TABLE_COLUMN_WIDTH;
                    }
                } else {
                    tableWidth += INITIAL_TABLE_COLUMN_WIDTH;
                }
            }
        }

        //System.out.println("Table old width - " + tableWidth);
        if (oldColumnWidth == -1) {
            int noWidthCols = 0;
            int setColumnsWidth = 0;
            for (int i = 0; i < childCount; i++) {
                DesignBean tableColumnBean = tableRowGroupBean.getChildBean(i);
                DesignProperty tcWidthProperty = tableColumnBean.getProperty(WIDTH_PROPRTY);
                if ((tcWidthProperty != null) && (tcWidthProperty.getValue() != null)) {
                    try {
                        int colWidth = Integer.parseInt((String) tcWidthProperty.getValue());
                        //System.out.println("Width of column - " + i + " is " + colWidth);
                        setColumnsWidth += colWidth;
                    } catch (Exception exc) {
                        Logger.getLogger(TableDesignHelper.class.getName()).info(exc.getLocalizedMessage());
                        noWidthCols++;
                    }
                } else {
                    noWidthCols++;
                }
            }
            //System.out.println("No of columns without width  - " + noWidthCols);
            //System.out.println("Total  width of columns set  - " + setColumnsWidth);
            if (noWidthCols != 0) {
                oldColumnWidth = (tableWidth - setColumnsWidth) / noWidthCols;
                //System.out.println(" Old Width - " + oldColumnWidth + " New Column Width " + newColumnWidth);
                tableWidth = tableWidth + (newColumnWidth - oldColumnWidth);
                //System.out.println("Table new width - " + tableWidth);
            }
        }

        widthValue = String.valueOf(tableWidth);
        widthProperty.setValue(widthValue);
    }

    public static void adjustTableWidth(DesignBean tableRowGroupBean) {
        // Adjust the width of the table in its style property
        //System.out.println("Adjusting Table Width .. ");
        DesignBean tableBean = tableRowGroupBean.getBeanParent();
        DesignProperty tableWidthProperty = tableBean.getProperty(WIDTH_PROPRTY); //NOI18N
        int oldTableWidth = 0;
        int childCount = tableRowGroupBean.getChildBeanCount();

        if ((tableWidthProperty != null) && (tableWidthProperty.getValue() != null)) {
            try {
                oldTableWidth = Integer.parseInt((String) tableWidthProperty.getValue());
            } catch (Exception exc) {
                Logger.getLogger(TableDesignHelper.class.getName()).info(exc.getLocalizedMessage());
            }
        } else {
            // When the table is dropped set its initial width, else the default width will be 100%
            for (int i = 0; i < childCount; i++) {
                oldTableWidth += INITIAL_TABLE_COLUMN_WIDTH;
            }
            tableWidthProperty.setValue(String.valueOf(oldTableWidth));
        }

        int newTableWidth = 0;

        for (int i = 0; i < childCount; i++) {
            DesignBean tableColumnBean = tableRowGroupBean.getChildBean(i);
            DesignProperty columnWidthProperty = tableColumnBean.getProperty(WIDTH_PROPRTY); //NOI18N
            if ((columnWidthProperty != null) && (columnWidthProperty.getValue() != null)) {
                try {
                    int colWidth = Integer.parseInt((String) columnWidthProperty.getValue());
                    newTableWidth += colWidth;
                    //System.out.println("Width of column - " + i + " is " + colWidth);
                } catch (Exception exc) {
                    Logger.getLogger(TableDesignHelper.class.getName()).info(exc.getLocalizedMessage());
                }
            }
        }

        if (newTableWidth > oldTableWidth) {
            tableWidthProperty.setValue(String.valueOf(newTableWidth));
        }
    }
    
    public static DesignBean findChildBeanByName(DesignBean parent, String instanceName) {
        DesignBean child = null;
        DesignBean[] childBeans = parent.getChildBeans();
        if (childBeans != null && childBeans.length > 0) {
            for (int i = 0; i < childBeans.length; i++) {
                if (instanceName.equals(childBeans[i].getInstanceName())) {
                    child = childBeans[i];
                    break;
                }
            }
        }
        return child;
    }
}
