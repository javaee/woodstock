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

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.model.UploadedFile;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.ObjectStreamException; 
import java.io.Serializable; 

import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.FacesException;

import org.apache.commons.fileupload.FileItem;

/**
 * The Upload component is used to create an input tag with its <code>type</code> 
 * field set to "file".
 */
@Component(type="com.sun.webui.jsf.Upload", family="com.sun.webui.jsf.Upload", displayName="File Upload",
    instanceName="fileUpload", tagName="upload", isContainer=false,
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_file_upload",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_upload_props")
public class Upload extends Field implements Serializable {
    /**
     * A string concatenated with the component ID to form the ID and
     * name of the HTML input element. 
     */
    public static final String INPUT_ID = "_com.sun.webui.jsf.upload"; //NOI18N
    public static final String INPUT_PARAM_ID = "_com.sun.webui.jsf.uploadParam"; //NOI18N
    public static final String SCRIPT_ID="_script"; 
    public static final String SCRIPT_FACET="script"; 
    public static final String TEXT_ID="_text"; 
    public static final String LENGTH_EXCEEDED="length_exceeded";
    public static final String UPLOAD_ERROR_KEY="upload_error_key";
    public static final String FILE_SIZE_KEY="file_size_key";
            
    private static final boolean DEBUG = false;

    /**
     * Default constructor.
     */
    public Upload() {
        super();
        setRendererType("com.sun.webui.jsf.Upload");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Upload";
    }

    /**
     * Log an error - only used during development time.
     */
    protected void log(String s) {
        System.out.println(this.getClass().getName() + "::" + s); //NOI18N
    }
    
    /**
     * <p>Converts the submitted value. Returns an object of type 
     * UploadedFile.</p>
     * @param context The FacesContext
     * @param value An object representing the submitted value
     * @return An Object representation of the value (a
     * java.lang.String or a java.io.File, depending on how the
     * component is configured 
     */
    public Object getConvertedValue(FacesContext context, Object value) { 

        if(DEBUG) log("getConvertedValue"); 
        UploadedFileImpl uf = new UploadedFileImpl(value, context);   
        if(DEBUG) { 
            log("\tSize is " + String.valueOf(uf.getSize())); 
            log("\tName is " + uf.getOriginalName()); 
            log("\tValue is required " + String.valueOf(isRequired())); 
        }
        if(isRequired() && uf.getSize() == 0) { 
            String name = uf.getOriginalName(); 
            if(name == null || name.trim().length() == 0) { 
                if(DEBUG) log("No file specified");
                setValue("");
                if(DEBUG) log("Set value to empty string");
                return "";
                //FacesMessage msg = new FacesMessage("Enter a file to upload");
                //throw new ConverterException(msg);
            }          
        }      
        return uf;   
    } 

    /**
     * <p>Return the value to be rendered when the component is 
     * rendered as a String. For the FileUpload, we never
     * render the file name in the textfield, so we return null.</p>
     * @param context FacesContext for the current request
     * @return A String value of the component
     */
    public String getValueAsString(FacesContext context) { 
        return null;
    } 
    
    /**
     * <p>Return the value to be rendered as a string when the
     * component is readOnly. This method overrides the default 
     * behaviour by returning a String "No file uploaded" if 
     * getValueAsString() returns null.</p>
     * @param context FacesContext for the current request
     * @return A String value of the component
     */
    public String getReadOnlyValueString(FacesContext context) {
        
        String valueString = null;
        Object value = getValue(); 
        if(value != null & value instanceof UploadedFile) {
            try { 
                valueString = ((UploadedFile)value).getOriginalName();
            } 
            catch(Exception ex) { 
                // We have somehow lost the underlying file representation.
                // We do nothing in this case.
            } 
        }   
        if(valueString == null) {
            valueString = ThemeUtilities.getTheme(context)
            .getMessage("FileUpload.noFile");
        }
        return valueString;
    }
    
    /**
     * Overrides getType in the FileInput class, to always return
     * "file" 
     * @return "file"
     */
    public String getType() {
        return "file";
    }

    /**
     * This method overrides getText() in Field. It always returns null. 
     */
    private Object _getText() {
        return null;
    }
    
    /**
     * This method overrides setText() in Field. It is a noop.
     */
    public void setText(Object text) {
        // do nothing
    }
    
    public int getColumns() {
        int columns = _getColumns();
        if(columns < 1) { 
            columns = 40; 
            setColumns(40); 
        }
        return columns;
    }
    
    // Overrides the method in Field.java as a workaround for an 
    // apparent compiler problem (?). The renderer (for Upload as well 
    // as TextField etc) casts the component to Field. It invokes 
    // Field.getPrimaryElementID, and even though this.getClass()
    // returns Upload, this.INPUT_ID returns Field.INPUT_ID and 
    // not Upload.INPUT_ID. 
    /** 
     * Retrieves the DOM ID for the HTML input element. To be used by 
     * Label component as a value for the "for" attribute. 
     * @deprecated
     * @see #getLabeledElementId
     */
    public String getPrimaryElementID(FacesContext context) {
	String clntId = this.getClientId(context);
	return clntId.concat(INPUT_ID);
    }
     
    // Overrides the method in Field.java as a workaround for an 
    // apparent compiler problem (?). The renderer (for Upload as well 
    // as TextField etc) casts the component to Field. It invokes 
    // Field.getPrimaryElementID, and even though this.getClass()
    // returns Upload, this.INPUT_ID returns Field.INPUT_ID and 
    // not Upload.INPUT_ID. 
    /**
     * Returns the ID of an HTML element suitable to use as the value
     * of an HTML LABEL element's <code>for</code> attribute.
     *
     * @param context The FacesContext used for the request
     * @return The id of the HTML element
     */      
    public String getLabeledElementId(FacesContext context) {

	// If this component has a label either as a facet or
	// an attribute, return the id of the input element
	// that will have the "INPUT_ID" suffix. IF there is no
	// label, then the input element id will be the component's
	// client id.
	//
	// If it is read only then return null
	//
	if (isReadOnly()) {
	    return null;
	}

	// To ensure we get the right answer call getLabelComponent.
	// This checks for a developer facet or the private label facet.
	// It also checks the label attribute. This is better than
	// relying on "getLabeledComponent" having been called
	// like this method used to do.
	//
	String clntId = this.getClientId(context);
	return clntId.concat(INPUT_ID);
    }
    
    /**
     * Returns the id of an HTML element suitable to
     * receive the focus.
     *
     * @param context The FacesContext used for the request
     */
    public String getFocusElementId(FacesContext context) {
	return getLabeledElementId(context);
    }

    // Obtain the FileItem in the constructor, based on the
    // arguments. The original code attemtped to get the 
    // object repeatedly from the request parameters. But the
    // instance may live longer that this current request thereby
    // potentially returning a different FileItem. Also there was
    // originally a transient attribute member. If the instance
    // was serialized and restored the attribute member would be
    // null causing the exception to be thrown.
    // Originally the assumption was that the attribute member would
    // never be null, otherwise getConvertedValue
    // would not have been called and an instance of this class would
    // not have been created.
    //
    // In addition, creator made changes that allows the FileItem
    // to not exist in the request map. This means that the instance
    // returned does not represent a valid FileItem. Originally this
    // would have thrown an exception. But with the creator changes
    // an exception is only thrown if the object found in the request
    // map is not a FileItem object, and null is acceptable.
    // 
    // This behavior may be different since the application will
    // receive an instance of this class when there is no file
    // when previously an exception would have been thrown.
    //
    class UploadedFileImpl  implements UploadedFile {
        
        transient FileItem fileItemObject = null; 
        
        /** Creates a new instance of UploadedFileImpl */
        UploadedFileImpl(Object attribute, FacesContext context) {         
	    // Allow null
	    //
	    try {
		this.fileItemObject = (FileItem)
		    context.getExternalContext().getRequestMap().get(attribute);
	    } catch (Exception e) {
                String message = 
		    "File not uploaded. Is the upload filter installed ?";
                throw new FacesException(message, e);
            }
        }
        
        /**
         * Write the contents of the uploaded file to a file on the
         * server host. Note that writing files outside of the web
         * server's tmp directory must be explicitly permitted through
         * configuration of the server's security policy.
         *
         * This method is not guaranteed to succeed if called more
         * than once for the same item.
         * @param file The <code>File</code> where the contents should
         * be written 
         *
         * @exception Exception the
         */
        public void write(java.io.File file) throws Exception {
            
	    if (fileItemObject != null) {
		fileItemObject.write(file);
	    }
        }
        
        /**
         * The size of the file in bytes.
	 * If there is no fileItemObject, return 0.
         *
         * @return The size of the file in bytes.
         */
        public long getSize() {
	    if (fileItemObject != null) {
		return fileItemObject.getSize();
	    } else {
		return 0;
	    }
        }
        
        /**
         * Use this method to retrieve the name that the file has on the web
         * application user's local system.
	 * If the fileItemObject is null, return null;
         *
         * @return the name of the file on the web app user's system
         */
        public String getOriginalName() {
	    if (fileItemObject != null) {
		return fileItemObject.getName();
	    } else {
		return null;
	    }
        }
        
        /**
         * Returns a {@link java.io.InputStream InputStream} for
         * reading the file.
	 * Returns null if fileItemObject is null.
         *
         * @return An {@link java.io.InputStream InputStream} for
         * reading the file. 
         *
         * @exception IOException if there is a problem while reading
         * the file 
         */
        public java.io.InputStream getInputStream() throws java.io.IOException {
	    if (fileItemObject != null) {
		return fileItemObject.getInputStream(); 
	    } else {
		return null;
	    }
        }
        
        /**
         * Get the content-type that the browser communicated with the
         * request that included the uploaded file. If the browser did
         * not specify a content-type, this method returns null. 
	 * Returns null if fileItemObject is null.
         *
         * @return  the content-type that the browser communicated
         * with the request that included the uploaded file
         */
        public String getContentType() {
	    if (fileItemObject != null) {
		return fileItemObject.getContentType();
	    } else {
		return null;
	    }
        }
        
        /**
         * Use this method to retrieve the contents of the file as an
         * array of bytes. 
	 * Returns null if fileItemObject is null.
	 *
	 * @return The contents of the file as a byte array
         */
        public byte[] getBytes() {
	    if (fileItemObject != null) {
		return fileItemObject.get();
	    } else {
		return null;
	    }
        }
        
        /**
         * Use this method to retrieve the contents of the file as a
         * String 
	 * Returns null if fileItemObject is null.
         *
         * @return the contents of the file as a String
         */
        public String getAsString() {
	    if (fileItemObject != null) {
		return fileItemObject.getString();
	    } else {
		return null;
	    }
        }
        
        /**
         * Dispose of the resources associated with the file upload
         * (this will happen automatically when the resource is
         * garbage collected). 
         */
        public void dispose() {
	    if (fileItemObject != null) {
		fileItemObject.delete(); 
	    }
        }       
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Flag indicating that an input value for this field is mandatory, and 
     * failure to provide one will trigger a validation error.
     */
    @Property(name="required") 
    public void setRequired(boolean required) {
        super.setRequired(required);
    }

    /**
     * <p>Return the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property aliases.</p>
     *
     * @param name Name of value binding expression to retrieve
     */
    public ValueExpression getValueExpression(String name) {
        if (name.equals("uploadedFile")) {
            return super.getValueExpression("value");
        }
        return super.getValueExpression(name);
    }

    /**
     * <p>Set the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property
     * aliases.</p>
     *
     * @param name    Name of value binding to set
     * @param binding ValueExpression to set, or null to remove
     */
    public void setValueExpression(String name,ValueExpression binding) {
        if (name.equals("uploadedFile")) {
            super.setValueExpression("value", binding);
            return;
        }
        super.setValueExpression(name, binding);
    }

    /**
     * The converter attribute is used to specify a method to translate native
     * property values to String and back for this component. The converter
     * attribute value must be one of the following: 
     * <ul>
     * <li>a JavaServer Faces EL expression that resolves to a
     * backing bean or bean property that implements the
     * <code>javax.faces.converter.Converter</code> interface; or</li> 
     * <li>the ID of a registered converter (a String).</li> 
     */
    @Property(name="converter", isHidden=true, isAttribute=true)
    public Converter getConverter() {
        return super.getConverter();
    }
    
    /**
     * The maximum number of characters that can be entered for this field.
     */
    @Property(name="maxLength", isHidden=true, isAttribute=true)
    public int getMaxLength() {
        return super.getMaxLength();
    }

    // Hide trim
    @Property(name="trim", isHidden=true, isAttribute=false)
    public boolean isTrim() {
        return super.isTrim();
    }
    
    // Hide text
    @Property(name="text", isHidden=true, isAttribute=false)
    public Object getText() {
        return _getText();
    }
    
    /**
     * <p>Number of character character columns used to render this
     * field. The default is 40.</p>
     */
    @Property(name="columns", displayName="Columns", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int columns = Integer.MIN_VALUE;
    private boolean columns_set = false;

    /**
     * <p>Number of character character columns used to render this
     * field. The default is 40.</p>
     */
    private int _getColumns() {
        if (this.columns_set) {
            return this.columns;
        }
        ValueExpression _vb = getValueExpression("columns");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return 40;
    }

    /**
     * <p>Number of character character columns used to render this
     * field. The default is 40.</p>
     * @see #getColumns()
     */
    public void setColumns(int columns) {
        this.columns = columns;
        this.columns_set = true;
    }

    /**
     * <p>The value of this attribute must be a JSF EL expression, and
     * it must resolve to an object of type
     * <code>com.sun.webui.jsf.model.UploadedFile</code>. See the JavaDoc for
     * this class for details.</p>
     */
    @Property(name="uploadedFile", displayName="Uploaded File", category="Data", editorClassName="com.sun.rave.propertyeditors.binding.ValueBindingPropertyEditor")

    /**
     * <p>The value of this attribute must be a JSF EL expression, and
     * it must resolve to an object of type
     * <code>com.sun.webui.jsf.model.UploadedFile</code>. See the JavaDoc for
     * this class for details.</p>
     */
    public com.sun.webui.jsf.model.UploadedFile getUploadedFile() {
        return (com.sun.webui.jsf.model.UploadedFile) getValue();
    }

    /**
     * <p>The value of this attribute must be a JSF EL expression, and
     * it must resolve to an object of type
     * <code>com.sun.webui.jsf.model.UploadedFile</code>. See the JavaDoc for
     * this class for details.</p>
     * @see #getUploadedFile()
     */
    public void setUploadedFile(com.sun.webui.jsf.model.UploadedFile uploadedFile) {
        setValue((Object) uploadedFile);
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.columns = ((Integer) _values[1]).intValue();
        this.columns_set = ((Boolean) _values[2]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[3];
        _values[0] = super.saveState(_context);
        _values[1] = new Integer(this.columns);
        _values[2] = this.columns_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
