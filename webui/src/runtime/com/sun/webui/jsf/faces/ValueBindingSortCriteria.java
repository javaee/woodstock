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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.el.ValueExpression;
import javax.el.ELContext;
import com.sun.data.provider.RowKey;
import com.sun.data.provider.SortCriteria;
import com.sun.data.provider.TableDataProvider;
import com.sun.data.provider.impl.TableRowDataProvider;
import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

/**
 * <p>The ValueBindingSortCriteria class is an implementation of SortCriteria
 * that simply retrieves the sort value from the {@link ValueBinding}.</p>
 *
 * @author Joe Nuxoll
 */

// TODO: rename the class not to have ValueBinding???

public class ValueBindingSortCriteria extends SortCriteria {

    /**
     * Constructs a ValueBindingSortCriteria with no associated {@link ValueExpression}.
     */
    public ValueBindingSortCriteria() {}

    /**
     * Constructs a ValueBindingSortCriteria with the specified {@link ValueExpression}.
     *
     * @param valueExpression The desired ValueExpression
     */
    public ValueBindingSortCriteria(ValueExpression valueExpression) {
        this.valueExpression = valueExpression;
    }

    /**
     * Constructs a ValueBindingSortCriteria with the specified {@link ValueExpression} and
     * ascending state.
     *
     * @param valueExpression The desired ValueExpression
     * @param ascending The desired boolean state for the ascending property
     */
    public ValueBindingSortCriteria(ValueExpression valueExpression, boolean ascending) {
        this.valueExpression = valueExpression;
        this.setAscending(ascending);
    }

    /**
     * Returns the ValueExpression to use for this sort criteria.
     *
     * @return The currently set ValueExpression for this sort criteria
     */
    public ValueExpression getValueExpression() {
        return valueExpression;
    }

    /**
     * Sets the ValueExpression for this sort criteria.
     *
     * @param valueExpression The desired ValueExpression for this sort criteria
     */
    public void setValueExpression(ValueExpression valueExpression) {
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
     * <p>If no display name is set, this returns the {@link ValueExpression}'s
     * display name.</p>
     *
     * {@inheritDoc}
     */
    public String getDisplayName() {
        String name = super.getDisplayName();
        if ((name == null || "".equals(name)) && valueExpression != null) {
            return valueExpression.getExpressionString();
        }
        return name;
    }

    /**
     * Returns the ValueExpression's value expresssion string.
     *
     * {@inheritDoc}
     */
    public String getCriteriaKey() {
        return valueExpression != null ? valueExpression.getExpressionString() : ""; // NOI18N
    }

    /**
     * <p>Returns the value from the {@link ValueExpression} ignoring the arguments.</p>
     *
     * {@inheritDoc}
     */
    public Object getSortValue(TableDataProvider provider, RowKey row) {

        if (valueExpression == null) {
            return null;
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();
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

            value = valueExpression.getValue(facesContext.getELContext());

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

    private transient ValueExpression valueExpression;
    
    private String requestMapKey = "currentRow";
    
    private transient TableRowDataProvider rowProvider;
    private String rowProviderLock = "rowProviderLock"; // this is a monitor lock for rowProvider

    private void writeObject(ObjectOutputStream out) throws IOException {

	// Serialize simple objects first
	out.writeObject(requestMapKey);
	out.writeObject(rowProviderLock);

	// Serialize valueExpression specially
        if (valueExpression != null) {
            out.writeObject(valueExpression.getExpressionString());
	} else {
            out.writeObject((String) null);
        }

	// NOTE - rowProvider is reconstituted on demand,
	// so we don't need to serialize it

    }

    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException {

	// Deserialize simple objects first
	requestMapKey = (String) in.readObject();
	rowProviderLock = (String) in.readObject();

        // Deserialize valueExpression specially
        String s = (String) in.readObject();
        if (s != null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            valueExpression = 
                    facesContext.getApplication().getExpressionFactory().createValueExpression(elContext,s,Object.class); 
        } else {
            valueExpression = null;
        }
    }

}
