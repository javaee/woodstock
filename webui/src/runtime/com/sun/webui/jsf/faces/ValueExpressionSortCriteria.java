/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.
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
 * @author Joe Nuxoll, John Yeary
 */
@Component(isTag = false)
public class ValueExpressionSortCriteria extends SortCriteria {

    private static final long serialVersionUID = -1400195724479592404L;

    /**
     * Constructs a ValueExpressionSortCriteria with no value expression
     */
    public ValueExpressionSortCriteria() {
    }

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
    @Override
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
                facesContext.getELContext(), valueExpression, Object.class);

        if (valueBinding == null) {
            return null;
        }

        Map requestMap = facesContext.getExternalContext().getRequestMap();
        Object value = null;

        //FIXME synchronization on a non-final field
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
    @Property(displayName = "Value Expression")
    private String valueExpression;
    @Property(displayName = "Request Map Key")
    private String requestMapKey = "currentRow"; // NOI18N
    private transient TableRowDataProvider rowProvider;
    private String rowProviderLock = "rowProviderLock"; // this is a monitor lock for rowProvider
}
