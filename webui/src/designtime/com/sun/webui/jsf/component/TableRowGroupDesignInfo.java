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
import com.sun.data.provider.RowKey;
import com.sun.data.provider.FieldKey;
import com.sun.data.provider.impl.CachedRowSetDataProvider;
import com.sun.data.provider.impl.TableRowDataProvider;
import com.sun.rave.designtime.DesignBean;
import com.sun.rave.designtime.DesignContext;
import com.sun.rave.designtime.DesignProperty;
import com.sun.rave.designtime.DisplayAction;
import com.sun.rave.designtime.Result;
import com.sun.rave.designtime.faces.FacesDesignContext;
import com.sun.webui.jsf.component.customizers.TableBindToDataAction;
import com.sun.webui.jsf.component.customizers.TableCustomizerAction;
import com.sun.webui.jsf.component.table.TableDesignHelper;
import com.sun.webui.jsf.design.AbstractDesignInfo;
import com.sun.webui.jsf.component.table.TableRowGroupDesignState;
import com.sun.webui.jsf.component.TableRowGroup;
import javax.faces.component.UIComponent;
import javax.faces.el.ValueBinding;
import javax.el.ValueExpression;
import javax.el.ELContext;
import javax.el.ELException;
import javax.faces.context.FacesContext;
import com.sun.data.provider.DataAdapter;
import com.sun.data.provider.DataListener;
import com.sun.data.provider.DataProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    
    private static final String SOURCE_DATA_PROPERTY = "sourceData"; //NOI18N
    private static final String SOURCE_VAR_PROPERTY = "sourceVar"; //NOI18N
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
            
            TableDataProvider tdp = (TableDataProvider) sourceBean.getInstance();
            FieldKey[] columns = tdp.getFieldKeys();
            if((columns == null) || (columns.length == 0)){
                return Result.FAILURE;
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
    
    private boolean propertyChangedReentrance = false;
    private boolean columnsAlreadyReconstructed = false;
    
    public boolean isColumnsAlreadyReconstructed() {
        return columnsAlreadyReconstructed;
    }
    
    public void setColumnsAlreadyReconstructed(boolean b) {
        columnsAlreadyReconstructed = b;
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
            //this will happen if no columns are selected in table layout
            //because the designstate will unset the sourceData
            if((oldValue != null) && (!property.isModified())) {
                propertyChangedReentrance = true;
                TableRowGroupDesignState tblRowGroupDesignState = new TableRowGroupDesignState(tableRowGroupBean);
                DesignBean dataProviderBean = TableDesignHelper.createDefaultDataProvider(tableRowGroupBean.getBeanParent());
                tblRowGroupDesignState.setDataProviderBean(dataProviderBean,true);
                tblRowGroupDesignState.saveState();
                propertyChangedReentrance = false;
            } else {
                Object propertyValue = property.getValue();
                if (propertyValue instanceof TableDataProvider) {
                    TableDataProvider tdpInstance = (TableDataProvider)propertyValue;
                    FieldKey[] tdpInstanceFks = tdpInstance.getFieldKeys();
                    if (tdpInstanceFks != null && tdpInstanceFks.length > 0) {
                        if (columnsAlreadyReconstructed) {
                            //we know not to reconstruct the columns here,
                            //but need to set the flag false for next time
                            columnsAlreadyReconstructed = false;
                        }
                        else {
                            DesignBean dataProviderBean = dcontext.getBeanForInstance(tdpInstance);
                            if (dataProviderBean != null) {
                                propertyChangedReentrance = true;
                                TableRowGroupDesignState tblRowGroupDesignState = new TableRowGroupDesignState(tableRowGroupBean);
                                tblRowGroupDesignState.setDataProviderBean(dataProviderBean,true);
                                tblRowGroupDesignState.saveState();
                                propertyChangedReentrance = false;
                            }
                        }
                    }
                }
            }
            
            if (propertyChangedReentrance) {
                return;
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
     * The specified DesignBean's DesignContext has been "activated" in the project
     *
     * @param designBean the DesignBean who's DesignContext that has been activated
     */
    public void beanContextActivated(DesignBean designBean) {
        DesignProperty sourceDataProperty = designBean.getProperty(SOURCE_DATA_PROPERTY);
        Object propertyValue = sourceDataProperty.getValue();
        if (propertyValue instanceof TableDataProvider) {
            TableDataProvider tdpInstance = (TableDataProvider)propertyValue;
            int providerListenerCount = getProviderListenerCount(tdpInstance, designBean);
            if (providerListenerCount < 1) {
                ProviderListener providerListener = new ProviderListener(designBean);
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
    
    private int getProviderListenerCount(TableDataProvider tdp, DesignBean tableRowGroupBeanOfListenersToCount) {
        DataListener[] dls = tdp.getDataListeners();
        int count = 0;
        if (dls != null && dls.length > 0) {
            for (int i = 0; i < dls.length; i++) {
                if (dls[i] instanceof ProviderListener) {
                    ProviderListener pl = (ProviderListener)dls[i];
                    DesignBean tableRowGroupBean = pl.getTableRowGroupBean();
                    //only count listeners whose tableRowGroupBean is the one specified
                    if (tableRowGroupBean == tableRowGroupBeanOfListenersToCount) {
                        count++;
                    }
                }
            }
        }
        return count;
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
            FacesDesignContext fdcontext = (FacesDesignContext)tableRowGroupBean.getDesignContext();
            FacesContext fcontext = fdcontext.getFacesContext();
            DesignBean[] children = tableRowGroupBean.getChildBeans();
            List childrenToRemove = new ArrayList();
            if (children != null && children.length > 0) {
                //populate the "currentRow" request attribute
                TableDataProvider tdp = (TableDataProvider)provider;
                RowKey[] rowKeys = tdp.getRowKeys(1, null);
                TableRowDataProvider rowDataProvider;
                if (rowKeys.length > 0) {
                    rowDataProvider = new TableRowDataProvider(tdp, rowKeys[0]);
                }
                else {
                    rowDataProvider = new TableRowDataProvider(tdp);
                }
                Map requestMap = fcontext.getExternalContext().getRequestMap();
                DesignProperty sourceVarProperty = tableRowGroupBean.getProperty(SOURCE_VAR_PROPERTY);
                Object sourceVarObject = sourceVarProperty.getValue();
                String sourceVar = "currentRow"; //NOI18N
                if (sourceVarObject instanceof String) {
                    sourceVar = (String)sourceVarObject;
                }
                Object oldRequestMapValue = requestMap.get(sourceVar);
                requestMap.put(sourceVar, rowDataProvider);
                //go through the columns of the row group
                for (int c = 0; c < children.length; c++) {
                    DesignBean child = children[c];
                    if (child.getInstance() instanceof TableColumn) {
                        //this is a TableColumn. loop through its children (grandchildren of the row group)
                        DesignBean[] grandchildren = child.getChildBeans();
                        if (grandchildren != null && grandchildren.length > 0) {
                            for (int g = 0; g < grandchildren.length; g++) {
                                DesignBean grandchild = grandchildren[g];
                                //try to evaluate the binding for the "value" property of the grandchild
                                //if evaluating it throws an ELException, then add child to childrenToRemove
                                Object instanceObject = grandchild.getInstance();
                                if (instanceObject instanceof UIComponent) {
                                    UIComponent grandchildInstance = (UIComponent)instanceObject;
                                    ValueExpression ve = grandchildInstance.getValueExpression("value");    //NOI18N
                                    if (ve != null) {
                                        ELContext elcontext = fcontext.getELContext();
                                        try {
                                            ve.getValue(elcontext);
                                        }
                                        catch (ELException e) {
                                            childrenToRemove.add(child);
                                            break;  //go to the next child
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //clean up: restore the oldRequestMapValue
                requestMap.put(sourceVar, oldRequestMapValue);
            }
            int childrenToRemoveSize = childrenToRemove.size();
            if (childrenToRemoveSize > 0) {
                if (childrenToRemoveSize == children.length) {
                    //all the table columns would have thrown ELException, so we have to reconstruct
                    //if there are no field keys, no-op (see comments below)
                    //if there are field keys, reconstruct the table keeping the current data provider
                    FieldKey[] fieldKeys = provider.getFieldKeys();
                    if (fieldKeys == null || fieldKeys.length < 1) {
                        //table columns would throw ELException, and there are no field keys.
                        //one possibility would be to rebind the row group to the default data provider.
                        //that's appropriate for the table layout dialog, since the user chooses the table columns there.
                        //here, however, rebinding might be invasive: even though the field keys are unknown at designtime,
                        //they may be present at runtime. since there are no field keys at designtime,
                        //ObjectListDataProvider or ObjectArrayDataProvider will contain zero rows, 
                        //and therefore not throw any ELExceptions.
                        //So here we can assume the field keys will be present at runtime, and simply no-op.
                    }
                    else {
                        DesignBean dataProviderBean = fdcontext.getBeanForInstance(provider);
                        if (dataProviderBean != null) {
                            TableRowGroupDesignState tblRowGroupDesignState = new TableRowGroupDesignState(tableRowGroupBean);
                            tblRowGroupDesignState.setDataProviderBean(dataProviderBean,true);
                            tblRowGroupDesignState.saveState();
                        }
                    }
                }
                else {
                    //just remove the columns that would have thrown ELException
                    for (Iterator iter = childrenToRemove.iterator(); iter.hasNext(); ) {
                        DesignBean child = (DesignBean)iter.next();
                        fdcontext.deleteBean(child);
                    }
                }
            }
        }
    }
}
