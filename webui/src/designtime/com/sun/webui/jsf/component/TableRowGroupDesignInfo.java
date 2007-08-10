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

package com.sun.webui.jsf.component;

import com.sun.data.provider.TableDataProvider;
import com.sun.data.provider.impl.CachedRowSetDataProvider;
import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignContext;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.DisplayAction;
import com.sun.rave.designtime.Result;
import com.sun.rave.designtime.event.DesignBeanListener;
import com.sun.rave.designtime.faces.FacesDesignContext;
import com.sun.webui.jsf.component.customizers.TableBindToDataAction;
import com.sun.webui.jsf.component.customizers.TableCustomizerAction;
import com.sun.webui.jsf.component.table.TableDesignHelper;
import com.sun.webui.jsf.design.AbstractDesignInfo;
import com.sun.webui.jsf.component.table.TableRowGroupDesignState;
import com.sun.webui.jsf.component.TableRowGroup;
import javax.faces.el.ValueBinding;
import javax.faces.context.FacesContext;
import com.sun.data.provider.DataAdapter;
import com.sun.data.provider.DataListener;
import com.sun.data.provider.DataProvider;

/**
 * DesignInfo for the <code>TableRowGroup</code> component. The following behavior is
 * implemented:
 * <ul>
 * <li>Upon component creation, pre-populate with one table coulum.</li>
 * </ul>
 *
 * @author Winston Prakash
 */
public class TableRowGroupDesignInfo extends AbstractDesignInfo {
    
    private static final String SOURCE_DATA_PROPERTY = "sourceData";
    public static final String SELECTION_COLUMN_SUFFIX = "SelectionColumn";   //NOI18N
    public static final String SELECTION_CHILD_SUFFIX = "SelectionChild";   //NOI18N
    
    public TableRowGroupDesignInfo() {
        super(TableRowGroup.class);
    }
    
    /** {@inheritDoc} */
    public DisplayAction[] getContextItems(DesignBean bean) {
        return new DisplayAction[] {
            new TableCustomizerAction(bean),
            new TableBindToDataAction(bean)
        };
    }
    
    /**
     * Create Table Row Group Design State with the design bean and save its state
     * which in turn would create default no of table columns with few row of data.
     * {@inheritDoc}
     */
    public Result beanCreatedSetup(DesignBean tableRowGroupBean) {
        DesignProperty rowCountProperty = tableRowGroupBean.getProperty("rows"); //NOI18N
        rowCountProperty.setValue(new Integer(5));
        // Now table automatically created the column header. So may not be required.
        /*if (tableRowGroupBean.getBeanParent().getChildBeanCount() > 0){
            DesignProperty headerProperty = tableRowGroupBean.getProperty("headerText"); //NOI18N
            headerProperty.setValue(DesignMessageUtil.getMessage(TableRowGroupDesignInfo.class,"tableRowGroupHeader.headerText"));
        }*/
        FacesDesignContext fcontext = (FacesDesignContext) tableRowGroupBean.getDesignContext();
        TableRowGroupDesignState tblRowGroupDesignState = new TableRowGroupDesignState(tableRowGroupBean);
        tblRowGroupDesignState.setDataProviderBean(TableDesignHelper.createDefaultDataProvider(tableRowGroupBean.getBeanParent()));
        tblRowGroupDesignState.saveState();
        return Result.SUCCESS;
    }
    
    
    /**
     * {@inheritDoc}
     * Accept only TableColumn as Child
     */
    public boolean acceptChild(DesignBean parentBean, DesignBean childBean, Class childClass) {
        return childClass.isAssignableFrom(TableColumn.class);
    }
    
    /**
     * {@inheritDoc}
     * Accept only Table as Parent
     */
    public boolean acceptParent(DesignBean parentBean, DesignBean childBean, Class parentClass) {
        return parentBean.getInstance().getClass().isAssignableFrom(Table.class);
    }
    
    /**
     * Accept only Reult Set (may be not required in future) or  TableDataProvider as links
     *
     * {@inheritDoc}
     */
    public boolean acceptLink(DesignBean targetBean, DesignBean sourceBean, Class sourceClass) {
        if (TableDataProvider.class.isAssignableFrom(sourceClass)){
            return true;
        }
        return false;
    }
    
    /**
     * If child bean is a TableDataProvider create TableRowGroupDesignState with the target bean
     * and set TableDataProvider as source bean and then save its state which in turn would create
     * corresponding tables and columns with the data in the TableDataProvider.
     *
     * TBD - if the child bean is TableColumn
     *
     * {@inheritDoc}
     */
    public Result linkBeans(DesignBean targetBean, DesignBean sourceBean) {
        if (sourceBean.getInstance() instanceof TableDataProvider) {
            // Bug 6333281 - After cancelling rowset previous table binding lost
            // After dropping a CachedRowsetDataProvider, a dialog pops up if
            // already a CachedRowset exists in the session bean. If the user
            // Cancels this dialog, then the rowset is not set and the data provider
            // is deleted. 
            if (sourceBean.getInstance() instanceof CachedRowSetDataProvider){
                CachedRowSetDataProvider cachedRowSetDataProvider = (CachedRowSetDataProvider)sourceBean.getInstance();
                if(cachedRowSetDataProvider.getCachedRowSet() == null){
                    return Result.FAILURE;
                }
            }
            TableRowGroupDesignState ts = new TableRowGroupDesignState(targetBean);
            ts.setDataProviderBean(sourceBean);
            ts.saveState();
            TableDesignHelper.deleteDefaultDataProvider(targetBean);
        }
        return Result.SUCCESS;
    }
    
    /** {@inheritDoc} */
    public Result beanDeletedCleanup(DesignBean bean) {
        TableDesignHelper.deleteDefaultDataProvider(bean);
        
        //remove provider listeners from attached tdp if appropriate
        DesignProperty sourceDataProperty = bean.getProperty(SOURCE_DATA_PROPERTY);
        Object propertyValue = sourceDataProperty.getValue();
        if (propertyValue instanceof TableDataProvider) {
            TableDataProvider tdpInstance = (TableDataProvider)propertyValue;
            //remove any instances of ProviderListener from tdpInstance
            removeProviderListeners(tdpInstance, bean);
        }
        
        return Result.SUCCESS;
    }
    
    
    /**
     * Reset the table row group to use default table if the source data is set to null
     * This could happen if the user deleted the data provider. 
     * @param property The <code>DesignProperty</code> that has changed.
     * @param oldValue Optional oldValue, or <code>null</code> if the
     *  previous value is not known
     */
    public void propertyChanged(DesignProperty property, Object oldValue) {

        String propertyName = property.getPropertyDescriptor().getName();
        if(propertyName.equals(SOURCE_DATA_PROPERTY)){
            
            DesignBean tableRowGroupBean = property.getDesignBean();
            DesignContext dcontext = tableRowGroupBean.getDesignContext();
            
            //see if we need to hook up a new default data provider
            if((oldValue != null) && (!property.isModified())) {
                TableRowGroupDesignState tblRowGroupDesignState = new TableRowGroupDesignState(tableRowGroupBean);
                DesignBean dataProviderBean = TableDesignHelper.createDefaultDataProvider(tableRowGroupBean.getBeanParent());
                tblRowGroupDesignState.setDataProviderBean(dataProviderBean,true);
                tblRowGroupDesignState.saveState();
            }
            
            //stop listening to old tdp
            TableDataProvider oldTdpInstance = null;
            if (oldValue instanceof ValueBinding && dcontext instanceof FacesDesignContext) {
                FacesContext fcontext = ((FacesDesignContext)dcontext).getFacesContext();
                Object oldValueObject = ((ValueBinding)oldValue).getValue(fcontext);
                if (oldValueObject instanceof TableDataProvider) {
                    oldTdpInstance = (TableDataProvider)oldValueObject;
                }
            }
            else if (oldValue instanceof TableDataProvider) {
                oldTdpInstance = (TableDataProvider)oldValue;
            }
            if (oldTdpInstance != null) {
                //remove appropriate instances of ProviderListener from oldTdpInstance
                removeProviderListeners(oldTdpInstance, tableRowGroupBean);
            }
            
            //start listening to new tdp
            Object propertyValue = property.getValue();
            if (propertyValue instanceof TableDataProvider) {
                TableDataProvider tdpInstance = (TableDataProvider)propertyValue;
                //remove appropriate instances of ProviderListener from tdpInstance (just defensive)
                removeProviderListeners(tdpInstance, tableRowGroupBean);
                //add a new ProviderListener to tdpInstance
                ProviderListener providerListener = new ProviderListener(tableRowGroupBean);
                tdpInstance.addDataListener(providerListener);
            }
        }
    }
    
    /**
     * The specified DesignBean's instance name was changed.  This is the source-code instance name
     * of the bean component.
     *
     * @param designBean The DesignBean that has a new instance name
     * @param oldInstanceName The old instance name
     */
    public void instanceNameChanged(DesignBean designBean, String oldInstanceName) {
        //look for a selection column and selection child, and, if found, change their instance names
        String selectionColumnName = oldInstanceName + TableRowGroupDesignInfo.SELECTION_COLUMN_SUFFIX;
        DesignBean selectionColumnBean = TableDesignHelper.findChildBeanByName(designBean, selectionColumnName);
        if (selectionColumnBean != null) {
            String selectionChildName = oldInstanceName + TableRowGroupDesignInfo.SELECTION_CHILD_SUFFIX;
            DesignBean selectionChildBean = TableDesignHelper.findChildBeanByName(selectionColumnBean, selectionChildName);
            if (selectionChildBean != null) {
                String tableRowGroupBeanName = designBean.getInstanceName();
                String selectionColumnNameRevised = tableRowGroupBeanName + TableRowGroupDesignInfo.SELECTION_COLUMN_SUFFIX;
                String selectionChildNameRevised = tableRowGroupBeanName + TableRowGroupDesignInfo.SELECTION_CHILD_SUFFIX;
                selectionColumnBean.setInstanceName(selectionColumnNameRevised);
                selectionChildBean.setInstanceName(selectionChildNameRevised);
                //change the selectId property of the selectionColumnBean
                DesignProperty selectIdProperty = selectionColumnBean.getProperty("selectId");  //NOI18N
                Object oldValue = selectIdProperty.getValue();
                if (!selectionChildNameRevised.equals(oldValue)) {
                    selectIdProperty.setValue(selectionChildNameRevised);
                }
            }
        }
    }
    
    private void removeProviderListeners(TableDataProvider tdp, DesignBean tableRowGroupBeanOfListenersToRemove) {
        DataListener[] dls = tdp.getDataListeners();
        if (dls != null && dls.length > 0) {
            for (int i = 0; i < dls.length; i++) {
                if (dls[i] instanceof ProviderListener) {
                    ProviderListener pl = (ProviderListener)dls[i];
                    DesignBean tableRowGroupBean = pl.getTableRowGroupBean();
                    //only remove listeners whose tableRowGroupBean is the one specified
                    if (tableRowGroupBean == tableRowGroupBeanOfListenersToRemove) {
                        tdp.removeDataListener(pl);
                    }
                }
            }
        }
    }
    
    private static class ProviderListener extends DataAdapter {
        private DesignBean tableRowGroupBean;
        public ProviderListener(DesignBean tableRowGroupBean) {
            this.tableRowGroupBean = tableRowGroupBean;
        }
        public DesignBean getTableRowGroupBean() {
            return tableRowGroupBean;
        }
        public void providerChanged(DataProvider provider) {
            DesignContext dcontext = tableRowGroupBean.getDesignContext();
            DesignBean dataProviderBean = dcontext.getBeanForInstance(provider);
            if (dataProviderBean != null) {
                TableRowGroupDesignState tblRowGroupDesignState = new TableRowGroupDesignState(tableRowGroupBean);
                tblRowGroupDesignState.setDataProviderBean(dataProviderBean,true);
                tblRowGroupDesignState.saveState();
            }
        }
    }
}
