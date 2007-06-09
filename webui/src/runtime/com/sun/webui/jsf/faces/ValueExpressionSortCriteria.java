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

package com.sun.webui.jsf.faces;

import java.util.Map;
import javax.faces.context.FacesContext;
import javax.el.ValueExpression;
import com.sun.data.provider.RowKey;
import com.sun.data.provider.SortCriteria;
import com.sun.data.provider.TableDataProvider;
import com.sun.data.provider.impl.TableRowDataProvider;
import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

/**
 * <p>The ValueExpressionSortCriteria class is an implementation of SortCriteria
 * that simply retrieves the sort value from a {@link ValueExpression} which is
 * created using the specified value expression.</p>
 *
 * @author Joe Nuxoll
 */
public class ValueExpressionSortCriteria extends SortCriteria {

    /**
     * Constructs a ValueExpressionSortCriteria with no value expression
     */
    public ValueExpressionSortCriteria() {}

    /**
     * Constructs a ValueExpressionSortCriteria with the specified value
     * expression.
     *
     * @param valueExpression The desired value expression
     */
    public ValueExpressionSortCriteria(String valueExpression) {
        this.valueExpression = valueExpression;
    }

    /**
     * Constructs a ValueExpressionSortCriteria with the specified value
     * expression and ascending state.
     *
     * @param valueExpression The desired value expression
     * @param ascending The desired boolean state for the ascending property
     */
    public ValueExpressionSortCriteria(String valueExpression, boolean ascending) {
        this.valueExpression = valueExpression;
        this.setAscending(ascending);
    }

    /**
     * Returns the value expression to use for this sort criteria.
     *
     * @return The currently set value expression for this sort criteria
     */
    public String getValueExpression() {
        return valueExpression;
    }

    /**
     * Sets the value expression for this sort criteria.
     *
     * @param valueExpression The desired value expression for this sort criteria
     */
    public void setValueExpression(String valueExpression) {
        this.valueExpression = valueExpression;
    }

    /**
     * Returns the request map variable key that will be used to store the
     * {@link TableRowDataProvider} for the current row being sorted.  This
     * allows value expressions to refer to the "current" row during the sort
     * operation.
     *
     * @return String key to use for the {@link TableRowDataProvider}
     */
    public String getRequestMapKey() {
        return requestMapKey;
    }

    /**
     * Sets the request map variable key that will be used to store the
     * {@link TableRowDataProvider} for the current row being sorted.  This
     * allows value expressions to refer to the "current" row during the sort
     * operation.
     *
     * @param requestMapKey String key to use for the {@link TableRowDataProvider}
     */
    public void setRequestMapKey(String requestMapKey) {
        this.requestMapKey = requestMapKey;
    }

    /**
     * <p>If no display name is set, this returns the value expression.</p>
     *
     * {@inheritDoc}
     */
    public String getDisplayName() {
        String name = super.getDisplayName();
        if ((name == null || "".equals(name)) && valueExpression != null && !"".equals(valueExpression)) {
            return valueExpression;
        }
        return name;
    }

    /**
     * Returns the value expression.
     *
     * {@inheritDoc}
     */
    public String getCriteriaKey() {
        return valueExpression != null ? valueExpression : ""; // NOI18N
    }

    /**
     * <p>Returns the value from a {@link ValueExpression} created using the value
     * expression.  The passed arguments are ignored.</p>
     *
     * {@inheritDoc}
     */
    public Object getSortValue(TableDataProvider provider, RowKey row) {
        if (valueExpression == null || "".equals(valueExpression)) {
            return null;
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ValueExpression valueBinding = facesContext.getApplication().getExpressionFactory().createValueExpression(
                facesContext.getELContext(),valueExpression,Object.class);

        if (valueBinding == null) {
            return null;
        }

        Map requestMap = facesContext.getExternalContext().getRequestMap();
        Object value = null;

        synchronized (rowProviderLock) {

            Object storedRequestMapValue = null;
            if (requestMapKey != null && !"".equals(requestMapKey)) {
                storedRequestMapValue = requestMap.get(requestMapKey);
                if (rowProvider == null) {
                    rowProvider = new TableRowDataProvider();
                }
                rowProvider.setTableDataProvider(provider);
                rowProvider.setTableRow(row);
                requestMap.put(requestMapKey, rowProvider);
            }

            value = valueBinding.getValue(facesContext.getELContext());

            if (requestMapKey != null && !"".equals(requestMapKey)) {
                if (rowProvider != null) {
                    rowProvider.setTableDataProvider(null);
                    rowProvider.setTableRow(null);
                }
                requestMap.put(requestMapKey, storedRequestMapValue);
            }
        }

        return value;
    }

    private String valueExpression;
    
    private String requestMapKey = "currentRow"; // NOI18N
    
    private transient TableRowDataProvider rowProvider;
    private String rowProviderLock = "rowProviderLock"; // this is a monitor lock for rowProvider
}
