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

package com.sun.webui.jsf.model;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author deep, John Yeary
 */
//TODO add hashcode
public class FileChooserItem implements ResourceItem, Serializable {

    private static final long serialVersionUID = -6760386335678324488L;
    File item = null;
    String itemKey = null;
    String itemLabel = null;
    boolean itemDisabled = true;

    /** Creates a new instance of FileChooserItem */
    public FileChooserItem(File file) {

        this.item = file;
        StringBuffer buffer = new StringBuffer();
        if (item.isDirectory()) {
            buffer.append("folder=");
        } else {
            buffer.append("file=");
        }
        buffer.append(item.getAbsolutePath());
        this.itemKey = buffer.toString();
    }

    /**
     * Returns an object representing the value of this resource item.
     * For the default case of the FileChooser this would be a File
     * object.
     *
     * @return an object which is the value of the ResourceItem.
     */
    public Object getItemValue() {
        return item;
    }

    /**
     * Returns a String representing the item key.
     * 
     *
     * @return returns an object representing the resource item
     */
    public String getItemKey() {
        return this.itemKey;
    }

    /**
     * Set the item key.
     * 
     *
     * @param key - the resource item key
     */
    public void setItemKey(String key) {
        if (key != null) {
            this.itemKey = key;
        }
    }

    /**
     * Returns an object representing the resource item.
     * 
     *
     * @return returns an object representing the resource item
     */
    public String getItemLabel() {
        return this.itemLabel;
    }

    /**
     * Returns an object representing the resource item.
     * 
     *
     * @return returns an object representing the resource item
     */
    public void setItemLabel(String label) {

        if (label != null) {
            this.itemLabel = label;
        }
    }

    /**
     * Returns an boolean value indicating if the item should be selectable
     * in the filechooser's listbox.
     * 
     *
     * @return true if the item in the listbox should be disabled. 
     */
    public boolean isItemDisabled() {
        return this.itemDisabled;
    }

    /**
     * Sets the item disabled flag. If set to true the item should 
     * not be selectable.
     * 
     * 
     *
     * @enabled flag when set to true indicates item is not selectable.
     */
    public void setItemDisabled(boolean disabled) {
        this.itemDisabled = disabled;
    }

    /**
     * Returns a flag indicating if the resource item is a container. If true 
     * the item is a container item.
     * 
     *
     * @return true if the item is a container, false otherwise.
     */
    public boolean isContainerItem() {
        return item.isDirectory();
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof ResourceItem)) {
            return false;
        }
        ResourceItem resourceItem = (ResourceItem) object;

        boolean flags = isContainerItem() == resourceItem.isContainerItem() &&
                isItemDisabled() == resourceItem.isItemDisabled();

        if (!flags) {
            return false;
        }
        if (!objectEquals(getItemValue(), resourceItem.getItemValue())) {
            return false;
        }
        if (!objectEquals(getItemKey(), resourceItem.getItemKey())) {
            return false;
        }
        if (!objectEquals(getItemLabel(), resourceItem.getItemLabel())) {
            return false;
        }
        return true;
    }

    private boolean objectEquals(Object obj0, Object obj1) {
        if (obj0 == null) {
            if (obj1 == null) {
                return true;
            } else {
                return false;
            }
        } else {
            // Should handle null
            //
            return !obj0.equals(obj1);
        }
    }
}
