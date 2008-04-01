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

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.Button;
import com.sun.webui.jsf.component.FileChooser;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.component.DropDown;
import com.sun.webui.jsf.component.Label;
import com.sun.webui.jsf.component.StaticText;
import com.sun.webui.jsf.component.TextField;
import com.sun.webui.jsf.component.HelpInline;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ClientSniffer;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.theme.Theme;

import java.io.IOException;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.convert.ConverterException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>Renderer for a {@link com.sun.webui.jsf.component.FileChooser} component.</p>
 * <p>
 * The FileChooser renders the following logical elements to effect
 * the FileChooser behavoior.
 * </p>
 * <ul>
 * <li>An input field to accept and display the current open folder path.</li>
 * <li>An input field to accept and display a string used to filter the
 * contents of the current open folder.</li>
 * <li>An input field to accept and display the files to select.</li>
 * <li>A list box to display the contents of the current open folder.</li>
 * <li>A drop down menu of sort options to control the order of the 
 * open folder's contents.</li>
 * <li>An open folder button to open and display a selected folder in the
 * list box.</li>
 * <li>A move up button to navigate and display the currently open folder's
 * parent folder.</li>
 * </ul>
 * <p>
 * The expected submitted value is an array of Strings. If there are
 * selections a request parameter called "<clientId>:<id>_selections"
 * where "clientId" is the client id of the FileChooser component
 * and "id" is the component id of the FileChooser component.
 * </p>
 */

@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.FileChooser"))
public class FileChooserRenderer extends AbstractRenderer {
    
    private final static boolean DEBUG = false;
    
    // the "-" string constant
    public static final String HYFEN = "-";     //NOI18N
    
    /** Creates a new instance of FileChooserRenderer */
    public FileChooserRenderer() {
    }
    
    /**
     * <p>Decode any new state of the specified <code>UIComponent</code>
     * from the request contained in the specified <code>FacesContext</code>,
     * and store that state on the <code>UIComponent</code>.</p>
     *
     * <p>The default implementation calls <code>setSubmittedValue()</code>
     * on components that implement EditableValueHolder (i.e. input fields)</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be decoded
     *
     */
    public void decode(FacesContext context, UIComponent component) {
        if(DEBUG) log("decode(context, component)"); //NOI18N
        
        if(isDisabled(component) || isReadOnly(component)) {
            if(DEBUG) log("component is readonly..."); //NOI18N
            return;
        }
        
        if (!(component instanceof FileChooser)) {
	    throw new FacesException("FileChooserRenderer can only " + //NOI18N
		"render FileChooser components."); //NOI18N
        }
	FileChooser chooser = (FileChooser)component;
        
	// If the submitted value is not null then the
	// selectFieldValidator has decodeed the selections and
	// set the file chooser's submitted value.
	// All future processing should occur as if the value was
	// decoded here.
	//
	if (chooser.getSubmittedValue() == null) {
	    decodeSubmittedValue(context, chooser);
	}
    }

    /**
     * The hidden field is encoded with an operation and the
     * selections separated by ","
     *
     * This must be reworked to not depend on an operation
     * and a delimiter. Use a select element so that named
     * array can returned in the request.
     */
    private void decodeSubmittedValue(FacesContext context, FileChooser chooser) {
	// Look for the submitted dynamically created select element.
	// This will be set for the
	// 
	// dblclicked - dblclick a file selection in file chooser mode
	// selectedFileEntered - selectFileField enter pressed
	//     in file mode and folder mode
	//
	// These are the selection actions
	// If not set no selection has been made and effectively
	// no change, as far as file choosers value.
	//
	// Probable want to always create the hidded select element
	// to reduce dependence on javascript.
	//
        String selectionsId = chooser.getClientId(context) + ":" +
            chooser.getId() + "_selections";

        Map requestParameters =
            context.getExternalContext().getRequestParameterValuesMap();

	/*
	java.util.Set keyset = requestParameters.keySet();
	Object[] keys = keyset.toArray();
	*/

        String[] selections = (String[])requestParameters.get(selectionsId);
	if (selections != null) {
	    chooser.setSubmittedValue(selections);
	} else {
	    chooser.setSubmittedValue(null);
	}
    }
    
    /**
     * Create a value for the fileChooser component based on the 
     * submitted value, which are the user selections.
     * The selections may be absolute or relative paths.
     * The result is an array of objects.
     *
     * @return - an object that reflects the value of the fileChooser
     *       component.
     * 
     */
    public java.lang.Object getConvertedValue(FacesContext context,
        UIComponent component, Object submittedValue)
            throws ConverterException {

	// This implementation of getConvertedValue calls back into
	// the component's getConvertedValue. We do this because
	// we are also writing the renderer and it is more convenient
	// and useful to place the real implementation in the
	// component. However if someone else writes a renderer
	// they may want to implement it. Therefore the component
	// will call the renderer's getConveredValue first if
	// a renderer is configured, which in effect will be a call
	// back to an overloaded FileChooser.getConvertedValue().
	//
	// public Object getConvertedValue(FacesContext, FileChooser, Object);
	//
        if (!(component instanceof FileChooser)) {
	    String msg = "Can only convert values for FileChooser component."; //NOI18N
	    throw new ConverterException(msg);
	}
	return ((FileChooser)component).getConvertedValue(context,
		(FileChooser)component, submittedValue);
    }
    
    /**
     * <p>Render the children of the specified <code>UIComponent</code>
     * to the output stream or writer associated with the response we are
     * creating.</p>
     *
     * <p>The default implementation iterates through the children of
     * this component and renders them.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be decoded
     *
     * @exception NullPointerException if <code>context</code> or
     *  <code>component</code> is <code>null</code>
     *
     * @exception IOException if an input/output error occurs
     */
    // We shouldn't bother with a default implementation - this is exactly
    // what happens when you rendersChildren = false. Why duplicate the
    // code here?
    
    public void encodeChildren(FacesContext context, UIComponent component)
        throws IOException {
    }
   
    /**
     *  <p> The <code>FileChooser</code> component is responsible for rendering
     *      its own children, they should not be rendered automatically.</p>
     *
     *  @return <code>true</code> because this component will render its own
     *          children explicitly.
     */
    public boolean getRendersChildren() {
        return true;
    }
    
    /* Render the start of the filechooser
     *
     * @param context The current FacesContext
     * @param component The FileChooser object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurss
     */
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        try {
            FileChooser chooser = null;
            if (component instanceof FileChooser) {
                chooser = (FileChooser) component;
            } else {
                String message = "Component " + component.toString() + //NOI18N
		    " has been associated with a FileChooser. " +  //NOI18N
		    " This renderer can only be used by components " + //NOI18N
		" that extend com.sun.webui.jsf.component.FileChooser."; //NOI18N
                throw new FacesException(message);
            }
            
            if (!chooser.isRendered()) {
                return;
            }
            
            Theme theme = ThemeUtilities.getTheme(context);
            
            // start the div the entire filechooser is wrapped in
            writer.startElement("div", component); //NOI18N
            writer.writeAttribute("id", component.getClientId(context), null);
            
            String style = chooser.getStyle();
            if (style != null) {
                writer.writeAttribute("style", style, null);  //NOI18N
            } 
            
            RenderingUtilities.renderStyleClass(context, writer, component, null);
            writer.writeText("\n", null); //NOI18N
            
            // render title in a table
            renderChooserTitle(context, chooser, writer, theme); //NOI18N
            
            // render file chooser in a div
            writer.startElement("div", component); //NOI18N                       
            writer.writeAttribute("class",         //NOI18N
                    theme.getStyleClass(ThemeStyles.FILECHOOSER_CONMGN), null);                                                    
            writer.writeText("\n", null); //NOI18N
            writer.startElement("table", chooser); //NOI18N
            writer.writeAttribute("border", "0", "border"); //NOI18N
            writer.writeAttribute("cellpadding", "0", "cellpadding"); //NOI18N
            writer.writeAttribute("cellspacing", "0", "cellspacing"); //NOI18N
            writer.writeAttribute("title", " ", "title"); //NOI18N
            writer.writeText("\n", null);
            writer.startElement("tr", chooser); // NOI18N
            writer.startElement("td", chooser); // NOI18N
            
            // check if facets are available and if not add a default one.
            
            // renderEmptyLine(context, chooser, writer, null, 1, 20, theme); //NOI18N
            renderServerName(context, chooser, writer, theme); //NOI18N
            renderClearDiv(context, chooser, writer, theme);
            renderLookinTextField(context, chooser, writer, theme); 
            renderClearDiv(context, chooser, writer, theme);
            renderFilterField(context, chooser, writer, theme);
            renderClearDiv(context, chooser, writer, theme);
            
            String helpMessage = theme.getMessage("filechooser.enterKeyHelp"); //NOI18N
            renderInlineHelp(context, chooser, writer, helpMessage, theme); //NOI18N
            renderClearDiv(context, chooser, writer, theme);
            writer.endElement("td");
            writer.endElement("tr");
            writer.writeText("\n", null);
            
            writer.startElement("tr", chooser);
            writer.startElement("td", chooser);
            writer.startElement("hr", chooser);
            writer.writeAttribute("color", "#98a0a5", null);
            writer.writeAttribute("size", "1", null);
            writer.endElement("hr");
            writer.endElement("td");
            writer.endElement("tr");
            writer.writeText("\n", null);
            
            writer.startElement("tr", chooser);
            writer.startElement("td", chooser);
            renderButtons(context, chooser, writer, theme);  //NOI18N
            renderSortFields(context, chooser, writer, theme);  //NOI18N
            writer.endElement("td");
            writer.endElement("tr");
            writer.writeText("\n", null);
            
            writer.startElement("tr", chooser);
            writer.startElement("td", chooser);
            writer.startElement("div", chooser);
            writer.writeAttribute("class", 
                theme.getStyleClass(ThemeStyles.FILECHOOSER_LST_HDR), null);
            writer.startElement("div", chooser);
            writer.writeAttribute("class", 
                theme.getStyleClass(ThemeStyles.FILECHOOSER_NAME_HDR), null);
            writer.write(theme.getMessage("filechooser.name_column_header"));
            writer.endElement("div");
            writer.writeText("\n", null);
            writer.startElement("div", chooser);
            writer.writeAttribute("class", 
                theme.getStyleClass(ThemeStyles.FILECHOOSER_SIZE_HDR), null);
            writer.write(theme.getMessage("filechooser.size_column_header"));
            writer.endElement("div");
            writer.writeText("\n", null);
            writer.startElement("div", chooser);
            writer.writeAttribute("class", 
                theme.getStyleClass(ThemeStyles.FILECHOOSER_DATE_TIME_HDR), null);
            writer.write(theme.getMessage("filechooser.date_time_column_header"));
            writer.endElement("div");
            writer.writeText("\n", null);
            writer.endElement("div");
            writer.endElement("td");
            writer.endElement("tr");
            writer.writeText("\n", null);
            
            writer.startElement("tr", chooser); //NOI18N
            writer.startElement("td", chooser); //NOI18N
            renderFileList(context, chooser, writer, theme);  
            writer.endElement("td"); //NOI18N
            writer.endElement("tr"); //NOI18N
            
            renderMultiSelectHelp(context, chooser, writer, theme);
            
            writer.startElement("tr", chooser); //NOI18N
            writer.startElement("td", chooser); //NOI18N
            writer.writeText("\n", null);
            renderSelectText(context, chooser, writer, theme); 
            writer.endElement("td"); //NOI18N
            writer.endElement("tr"); //NOI18N
            
            writer.writeText("\n", null);
            writer.startElement("tr", chooser); //NOI18N
            writer.startElement("td", chooser); //NOI18N
            writer.startElement("hr", chooser); //NOI18N
            writer.writeAttribute("color", "#98a0a5", null);
            writer.writeAttribute("size", "1", null);
            writer.endElement("hr"); //NOI18N
            writer.endElement("td"); //NOI18N
            writer.endElement("tr"); //NOI18N
            writer.writeText("\n", null);
            writer.endElement("table"); //NOI18N
            
            writer.endElement("div"); //NOI18N
            writer.endElement("div"); //NOI18N
            
            // end of filechooser layout, now add the two hidden fields.
            String hiddenID = chooser.getClientId(context) + ":" +
                chooser.getId() + FileChooser.FILECHOOSER_HIDDENFIELD_ID;
            writer.startElement("input", chooser);
            writer.writeAttribute("id", hiddenID, null); //NOI18N
            writer.writeAttribute("name", hiddenID, null); //NOI18N
            writer.writeAttribute("type", "hidden", null); //NOI18N
            writer.writeAttribute("value", "NOACTION", null); //NOI18N
            writer.endElement("input");
            
            Button hiddenButton = (Button) chooser.getHiddenFCButton();
            RenderingUtilities.renderComponent(hiddenButton, context);

	    // Render a hidden select to hold the currently selected
	    // values.
	    // Not sure if this really needs to done.
	    // No change should be indicated by no submitted value
	    // or if the selected file field contains exactly what it
	    // did when it was rendered.
	    //
	    //renderSelectedSelections(context, chooser, writer, theme);

	    // This may be useful at some point.
	    // I was going to use this to detect entries that
	    // where typed into the selected file field.
	    // It would be a way to determine if an entry
	    // was a full path. The problem is that you can't
	    // detect when the file chooser is actually submitted
	    // since the submit may have occured from an element
	    // outside the file chooser. Then only way to do this
	    // then would be onchange events and key press events
	    // on the selected file field.
	    //
	    //renderRoots(context, chooser, writer, theme);

            renderJavaScript(chooser, context, writer, theme);            
        } catch (Exception e) {
            throw new FacesException("Filechooser throws exception while" +
                    "rendering: " + e.getMessage());
        }
    }

    private void renderSelectedSelections(FacesContext context,
        FileChooser chooser, ResponseWriter writer, Theme theme) 
            throws Exception {    
        writer.startElement("select", chooser);              //NOI18N
	String id = chooser.getClientId(context) + ":" + //NOI18N
            chooser.getId() + "_selections"; //NOI18N
	writer.writeAttribute("id", id, null); //NOI18N
	writer.writeAttribute("name", id, null); //NOI18N
	writer.writeAttribute("style", "{display:none}", null); //NOI18N
	writer.writeAttribute("multiple", "multiple", null); //NOI18N
        
	// If this select is going to hold the current selections
	// need to get conversion hooked up to convert from the
	// app's type
        writer.endElement("select");              //NOI18N
    }
    
    // Render the possible roots.
    //
    private void renderRoots(FacesContext context, FileChooser chooser,
            ResponseWriter writer, Theme theme) throws Exception {
	String[] roots = chooser.getRoots();
        writer.startElement("select", chooser);              //NOI18N
	String id = chooser.getClientId(context) + ":" + //NOI18N
            chooser.getId() + "_roots"; //NOI18N
	writer.writeAttribute("id", id, null); //NOI18N
	writer.writeAttribute("name", id, null); //NOI18N
	writer.writeAttribute("style", "{display:none}", null); //NOI18N
	writer.writeAttribute("multiple", "multiple", null); //NOI18N

	for (int i = 0; i < roots.length; ++i) {
	    writer.startElement("option", chooser); //NOI18N
	    writer.writeAttribute("value", roots[i], null);      //NOI18N
	    writer.endElement("option"); //NOI18N
	}
        
	// If this select is going to hold the current selections
	// need to get conversion hooked up to convert from the
	// app's type
        writer.endElement("select");              //NOI18N
    }
    
    /**
     * This method appends the servername from where the files are being
     * listed to the filechooser layout.
     */
    protected void renderServerName(FacesContext context, FileChooser chooser,
            ResponseWriter writer, Theme theme) throws Exception {
        try {          

            UIOutput uio = (UIOutput) chooser.getServerNameText();
            Label label = (Label) chooser.getServerNameLabel();

	    // If either not rendered, render neither.
            if (!uio.isRendered() || !label.isRendered()) {
		return;
	    }

            writer.startElement("div", chooser);    //NOI18N
            writer.writeAttribute("class", 
                theme.getStyleClass(ThemeStyles.FILECHOOSER_SRV_DIV), null);

	    // Render server name label
	    writer.startElement("div", chooser);    //NOI18N
	    writer.writeAttribute("class", 
		theme.getStyleClass(ThemeStyles.FILECHOOSER_LEV2_DIV), null);
	    RenderingUtilities.renderComponent(label, context);
	    writer.endElement("div");    //NOI18N
	    writer.writeText("\n", null);    //NOI18N

	    // Render server name text
	    RenderingUtilities.renderComponent(uio, context);

	    writer.endElement("div");    //NOI18N
	    writer.writeText("\n", null);    //NOI18N

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * This method renders the fileChooser label.
     */
    protected void renderChooserTitle(FacesContext context, FileChooser chooser,
            ResponseWriter writer, Theme theme) throws Exception {
        // Append alert icon html.
        try {
            StaticText title  = (StaticText)chooser.getFileChooserTitle();
            if (!title.isRendered()) {
                return;
            }
            writer.startElement("table", chooser);  //NOI18N
            writer.writeAttribute("width", "100%", "width");  //NOI18N
            writer.writeAttribute("border", "0", "border");  //NOI18N
            writer.writeAttribute("cellpadding", "0", "cellpadding"); //NOI18N
            writer.writeAttribute("cellspacing", "0", "cellspacing"); //NOI18N
            writer.writeAttribute("title", "", "title");  //NOI18N
            writer.startElement("tr", chooser);    //NOI18N
            writer.writeAttribute("valign", "bottom", "valign");  //NOI18N
            writer.startElement("td", chooser);    //NOI18N
            writer.writeAttribute("valign", "bottom", "valign");  //NOI18N
            writer.startElement("div", chooser);
            writer.writeAttribute("class", 
                theme.getStyleClass(ThemeStyles.TITLE_TEXT_DIV), "class");
            writer.startElement("h1", chooser);
            writer.writeAttribute("class",
                theme.getStyleClass(ThemeStyles.TITLE_TEXT), "class");
            
            RenderingUtilities.renderComponent(title, context);
            writer.endElement("h1");    //NOI18N
            writer.endElement("div");    //NOI18N
            writer.endElement("td");    //NOI18N
            writer.endElement("tr");    //NOI18N
            writer.endElement("table");
            writer.writeText("\n", null);    //NOI18N
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * This method renders help message in small font to
     * the filechooser layout.
     */
    protected void renderInlineHelp(FacesContext context, FileChooser chooser,
        ResponseWriter writer, String messageKey, Theme theme)
            throws Exception {
        try {
            UIComponent help = chooser.getEnterInlineHelp();
            
            
            writer.startElement("div", chooser);   //NOI18N
            writer.writeAttribute("class", 
                theme.getStyleClass(ThemeStyles.FILECHOOSER_FLT_HLP_DIV), null);
            
            if (help.isRendered()) {
                RenderingUtilities.renderComponent(help, context);
            }
            writer.writeText("\n", null);  //NOI18N
            writer.endElement("div");  //NOI18N
            writer.writeText("\n", null);  //NOI18N
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * This method appends a help message in small font to
     * the filechooser layout.
     */
    protected void renderLookinTextField(FacesContext context,
        FileChooser chooser, ResponseWriter writer, Theme theme) 
            throws Exception {
        try {
            TextField lookinField = (TextField) chooser.getLookInTextField();
            if (!lookinField.isRendered()) {
                return;
            }
            
            writer.startElement("div", chooser);  //NOI18N
            writer.writeAttribute("class", 
                theme.getStyleClass(ThemeStyles.FILECHOOSER_LOOK_IN_DIV), null);
            writer.writeText("\n", null);  //NOI18N
            
            writer.startElement("div", chooser);  //NOI18N
            writer.writeAttribute("class", 
                theme.getStyleClass(ThemeStyles.FILECHOOSER_LEV2_DIV), null);
            writer.writeText("\n", null);  //NOI18N
                        
            // render the lookin textfield label
            RenderingUtilities.renderComponent(chooser.getLookInLabel(), context);
            writer.endElement("div");  //NOI18N
            writer.writeText("\n", null);  //NOI18N
            
            // render the lookin textfield
            setEnterKeyPressHandler(context, chooser, lookinField);

            RenderingUtilities.renderComponent(lookinField, context);
            writer.endElement("div");  //NOI18N
            writer.writeText("\n", null);  //NOI18N
        } catch (Exception e) {
            throw e;
        }
    }
    /**
     * This method appends a help message in small font to
     * the filechooser layout.
     */
    protected void renderFilterField(FacesContext context, FileChooser chooser,
            ResponseWriter writer, Theme theme) throws Exception {
        try {
            TextField filterOnField = (TextField) chooser.getFilterTextField();
            if (!filterOnField.isRendered()) {
                return;
            }
            writer.startElement("div", chooser);  //NOI18N
            writer.writeAttribute("class", 
                theme.getStyleClass(ThemeStyles.FILECHOOSER_FLT_DIV), null);
            writer.writeText("\n", null);  //NOI18N
            
            writer.startElement("div", chooser);  //NOI18N
            writer.writeAttribute("class", 
                theme.getStyleClass(ThemeStyles.FILECHOOSER_LEV2_DIV), null);
            RenderingUtilities.renderComponent(chooser.getFilterLabel(), context);
            writer.endElement("div");  //NOI18N
            writer.writeText("\n", null);  //NOI18N
            
            // render the lookin textfield
            setEnterKeyPressHandler(context, chooser, filterOnField);
	    RenderingUtilities.renderComponent(filterOnField, context);
            writer.endElement("div");  //NOI18N
            writer.writeText("\n", null);  //NOI18N
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * This method renders the sort field drop down menu
     * for the filechooser component.
     */
    protected void renderSortFields(FacesContext context, FileChooser chooser,
            ResponseWriter writer, Theme theme) throws Exception {
        try {
            writer.startElement("div", chooser);  //NOI18N
            writer.writeAttribute("class", 
                theme.getStyleClass(ThemeStyles.FILECHOOSER_SORT_BY_DIV), null);
            
            writer.writeText("\n", null); //NOI18N
            Label sortLabel = (Label) chooser.getSortComponentLabel();
            DropDown jdd = (DropDown) chooser.getSortComponent();
            if (!jdd.isRendered()) {
                return;
            }
            if (sortLabel.isRendered()) {
                RenderingUtilities.renderComponent(sortLabel, context);
            }
            RenderingUtilities.renderComponent(jdd, context);
            writer.writeText("\n", null); //NOI18N
            writer.endElement("div");  //NOI18N
            writer.writeText("\n", null);  //NOI18N
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * This method renders the list of files/folders in
     * a listbox.
     */
    protected void renderFileList(FacesContext context, FileChooser chooser,
            ResponseWriter writer, Theme theme) throws Exception {
        try {
            writer.startElement("div", chooser);  //NOI18N
            writer.writeAttribute("class", 
                theme.getStyleClass(ThemeStyles.FILECHOOSER_LST_DIV), null);
            
            UIComponent fileList = chooser.getListComponent();
	    createJavaScriptForFileList(chooser, context, fileList);
            setEnterKeyPressHandler(context, chooser, fileList);
            RenderingUtilities.renderComponent(fileList, context);

            writer.endElement("div");  //NOI18N
            writer.writeText("\n", null);  //NOI18N
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * This method renders the multiselect help if supplied
     */
    protected void renderMultiSelectHelp(FacesContext context, FileChooser chooser,
            ResponseWriter writer, Theme theme) throws Exception {
        try {
            HelpInline help = (HelpInline)chooser.getMultiSelectHelp();
            if (help != null && help.isRendered()) {
                writer.startElement("tr", chooser);  //NOI18N
                writer.startElement("td", chooser);  //NOI18N
                writer.startElement("div", chooser);  //NOI18N
                writer.writeAttribute("class", 
                theme.getStyleClass(ThemeStyles.FILECHOOSER_MULT_HLP_DIV), null);    
                RenderingUtilities.renderComponent(help, context);
                writer.endElement("div");
                renderClearDiv(context, chooser, writer, theme);
                writer.endElement("td");
                writer.endElement("tr");
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * This method renders the buttons used to traverse the file/folder
     * list.
     */
    protected void renderButtons(FacesContext context, FileChooser chooser,
            ResponseWriter writer, Theme theme) throws Exception {
        try {
            writer.startElement("div", chooser);  //NOI18N
            writer.writeAttribute("class", 
                theme.getStyleClass(ThemeStyles.FILECHOOSER_BTN_GRP_DIV), null);
            
            /*           
            HelpInline help = (HelpInline)chooser.getMultiSelectHelp();
            if (help == null) {
                writer.write("&nbsp;");  //NOI18N
            } else{
                if (help.isRendered()) {
                    RenderingUtilities.renderComponent(help, context);    
                }
            }
            */
            
            renderUpLevelButton(context, chooser, writer, theme);  //NOI18N
            renderOpenFolderButton(context, chooser, writer, theme);   //NOI18N

            writer.endElement("div");   //NOI18N
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * This method renders the button to traverse the resource list
     * up one level.
     */
    protected void renderUpLevelButton(FacesContext context, 
        FileChooser chooser, ResponseWriter writer, Theme theme)
            throws Exception {
        try {
            writer.writeText("\n", null);  //NOI18N
            Button child = (Button)chooser.getUpLevelButton(false);
            if (!child.isRendered()) {
                return;
            }
            StringBuffer jsBuffer = new StringBuffer(256);
            jsBuffer.append(JavaScriptUtilities.getDomNode(context, chooser));
            jsBuffer.append(".moveUpButtonClicked();");
            child.setOnClick(jsBuffer.toString());

            RenderingUtilities.renderComponent(child, context);
            writer.writeText("\n", null);   //NOI18N
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * This method renders the button to open a folder.
     */
    protected void renderOpenFolderButton(FacesContext context,
        FileChooser chooser, ResponseWriter writer, Theme theme)
            throws Exception {
        try {
            writer.writeText("\n", null);   //NOI18N
            Button child = (Button)chooser.getOpenFolderButton();
            if (!child.isRendered()) {
                return;
            }
            StringBuffer jsBuffer = new StringBuffer(256);
            jsBuffer.append(JavaScriptUtilities.getDomNode(context, chooser));
            jsBuffer.append(".openFolderClicked();");
            child.setOnClick(jsBuffer.toString());
            RenderingUtilities.renderComponent(child, context);
            writer.writeText("\n", null); //NOI18N
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * This method renders the test field which lists the selected
     * file(s) or folder(s).
     * It must satisfy the following UI properties:
     * If a file is selected in the list, display the name of the file
     * in the Selected File text field. If multiple files are selected,
     * display all of the files names separated by commas. Do not display
     * folder names in the Selected File text field. Replace existing text
     * in the Selected File text field only when the user selects a new
     * file name from the list or types a new file name. No other action
     * should replace existing text in the Selected File text field.
     *
     * Allow the user to specify a new path in the Selected File text
     * field. After the user enters the new path and hits enter, update
     * the list and the Look In text field to reflect the new path. If a
     * user types a path that begins with '/' or '\', take that as the
     * full path name. Otherwise, append what the user types to the path
     * in the Look In text field.
     *
     * Allow the user to specify a file name in the Selected File text
     * field by typing a full path, or without a path if the file is
     * contained in the current Look In folder. Once the user types in a
     * valid file name and presses the Enter key, the window should react
     * appropriately: pop-up windows should close and return the file name
     * to the application while inline file choosers should return the file
     * name, clear the Selected file text field, and either close or await
     * additional user input at the application designer's discretion.
     * If the file chooser is in a pop-up window, set the keyboard focus
     * inside the Selected File text field when the pop-up window
     * initially comes up.
     *
     */
    protected void renderSelectText(FacesContext context, FileChooser chooser, 
            ResponseWriter writer, Theme theme) throws Exception {
        try {
            TextField selectedTextField =
		(TextField) chooser.getSelectedTextField();
            if (!selectedTextField.isRendered()) {
                return;
            }          
            writer.startElement("div", chooser);  //NOI18N
            writer.writeAttribute("class", 
                theme.getStyleClass(ThemeStyles.FILECHOOSER_SEL_FILE_DIV), null);
            writer.writeText("\n", null);  //NOI18N
            writer.startElement("div", chooser);  //NOI18N
            writer.writeAttribute("class", 
                theme.getStyleClass(ThemeStyles.FILECHOOSER_SEL_FILE_LEV2_DIV), null);
            
            RenderingUtilities
                    .renderComponent(chooser.getSelectLabel(), context);
            writer.endElement("div");  //NOI18N
            setEnterKeyPressHandler(context, chooser, selectedTextField);
            RenderingUtilities.renderComponent(selectedTextField, context);
            writer.endElement("div");  //NOI18N
                        
            renderClearDiv(context, chooser, writer, theme); //NOI18N 
        } catch (Exception e) {
            throw e;
        }
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * This method renders JavaScript to initialize dom node.
     */
    private void renderJavaScript(FileChooser chooser, FacesContext context,
            ResponseWriter writer, Theme theme) throws IOException {
        // boolean folderChooser = chooser.isFolderChooser();
        String chooserType = null;
        if (chooser.isFolderChooser()) {
            chooserType = "folderChooser";
        } else if (chooser.isFileAndFolderChooser()) {
                chooserType = "fileAndFolderChooser";
        } else {
            chooserType = "fileChooser";
        }
        
	// Arguments are
	// The client id
	// The chooser mode, folder chooser "true", file chooser "false"
	// The move up dir or the parent directory of the displayed 
	// directory. This may be the same as the current directory.
	// The escape char, the string to use to escape the delimiter when
	// it appears in a file name.
	// The delimiter, the string to use to separate entries in 
	// the selected file field.

	// Need to escape the escapeChar
	// There are other possible issue characters like "'"
	// and '"' but let's not deal with that now.
	//
	String esc = chooser.getEscapeChar();
	if (esc.equals("\\")) { //NOI18N
	    esc = "\\\\"; //NOI18N
	}

	// getParentFolder can return null.
	// If null is returned, use getCurrentFolder().
	// FileChooserModel.getCurrentDir no longer returns null.
	// and will return the the root directory.
	//
	String parentDir = chooser.getParentFolder();
        String currentFolder = chooser.getCurrentFolder();
	if (parentDir == null) {
	    parentDir = currentFolder;
	}
	// If parentDir is still null, set it to empty string.
	// probably not FileChooserModel.
	//
	String sep = chooser.getSeparatorString();
	if (parentDir == null) {
	    parentDir = "";
	} else {
	    // Need  to escape separator String
	    //
	    if (sep.equals("\\")) { //NOI18N
		sep = sep + sep;
		parentDir = parentDir.replaceAll(sep, sep + sep);
                
            }
            
	}
        sep = chooser.getSeparatorString();
        if (currentFolder == null) {
	    currentFolder = "";
	} else {
	    // Need  to escape separator String
	    //
	    if (sep.equals("\\")) { //NOI18N
		sep = sep + sep;
		currentFolder = currentFolder.replaceAll(sep, sep + sep);
            }
            
	}
        
        try {
            // Append properties.
            String id = chooser.getClientId(context);
            StringBuffer buff = new StringBuffer(256);
            JSONObject json = new JSONObject();
            json.put("id", id)
                .put("chooserType", chooserType)
                .put("parentFolder", parentDir)
                .put("separatorChar", sep)
                .put("escapeChar", esc)
                .put("delimiter", chooser.getDelimiterChar())
                .put("currentFolder", currentFolder);

            // Append JavaScript.
            buff.append(JavaScriptUtilities.getModule("_html.fileChooser"))
                .append("\n") // NOI18N
                .append(JavaScriptUtilities.getModuleName("_html.fileChooser._init")) // NOI18N
                .append("(") //NOI18N
                .append(JSONUtilities.getString(json))
                .append(");"); //NOI18N
            
            // Render JavaScript.
            JavaScriptUtilities.renderJavaScript(chooser, writer,
                buff.toString(), JavaScriptUtilities.isParseOnLoad());
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }    
       
    /**
     * Create the javascript for file list to handle onchange
     */
    private void createJavaScriptForFileList(FileChooser chooser,
            FacesContext context, UIComponent fileList ) {
        String jsObject = JavaScriptUtilities.getDomNode(context, chooser);

        // generate the JavaScript that will disable DBL clicks
        // on files in a folderchooser.
        StringBuffer dblClickBuffer = new StringBuffer(256);
        dblClickBuffer.append(jsObject);
        dblClickBuffer.append(".handleDblClick();"); //NOI18N
        fileList.getAttributes().put("onDblClick", dblClickBuffer.toString()); //NOI18N
        
        // The user gets to choose the child name, label, tooltip for
        // the control button that is placed at the bottom of the
        // filechooser tag. The following few lines of code disables
        // this button based on certain conditions. For example, the
        // button would be disabled if the user selects a file but
        // the choose button is meant to choose a folder.
        
        // The following code disabled the open folder button
        // based on the following situations:
        // a) user selects more than one from the list of files/folders
        // b) user selects a file
        // c) user does not select anything
        
        // This javascript also performs the following additional
        // function. Add the file/folder to the selected file/folder
        // textfield if its of the appropriate type and has been selected.

        StringBuffer jsBuffer = new StringBuffer(256);
        jsBuffer.append(jsObject);
        jsBuffer.append(".handleOnChange();"); //NOI18N
        fileList.getAttributes().put("onChange", jsBuffer.toString()); //NOI18N
    }
    
    /**
     * This method returns the string of size maxLen by padding the
     * appropriate amount of spaces next to str.
     */
    private void setEnterKeyPressHandler(FacesContext context,
            FileChooser chooser, UIComponent child) {
        StringBuffer scriptBuffer = new StringBuffer();
	scriptBuffer.append("return "); //NOI18N
        scriptBuffer.append(JavaScriptUtilities.getDomNode(context, chooser));
        scriptBuffer.append(".enterKeyPressed(this);"); //NOI18N

        String js = getReturnKeyJavascriptWrapper(scriptBuffer.toString());
	scriptBuffer.setLength(0);
	scriptBuffer.append(js);
        child.getAttributes().put("onKeyPress", scriptBuffer.toString());
    }
    
    /**
     * Return the HTML equivalent of a single table row of empty space.
     */
    private void renderEmptyLine(FacesContext context, FileChooser chooser,
            ResponseWriter writer, String colSpan, int wd, int ht, Theme theme) {
        try {
            writer.startElement("tr", chooser);    //NOI18N
            renderDotImage(writer, chooser, context, colSpan, wd, ht, theme);
            writer.endElement("tr");    //NOI18N
            writer.writeText("\n", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    /**
     * Return the HTML equivalent of a single table row of empty space.
     */
    private void renderClearDiv(FacesContext context, FileChooser chooser,
            ResponseWriter writer, Theme theme) {
        try {
            writer.startElement("div", chooser);    //NOI18N
            writer.writeAttribute("class", 
                theme.getStyleClass(ThemeStyles.CLEAR), null);
            writer.endElement("div");    //NOI18N
            writer.writeText("\n", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Return the HTML equivalent of a single table data of empty space.
     */
    private void renderDotImage(ResponseWriter writer, FileChooser chooser,
            FacesContext context, String colSpan, int wd, int ht, Theme theme) {
        try {
            writer.startElement("td", chooser);    //NOI18N
            if (colSpan != null) {
                writer.writeAttribute("colspan", colSpan, null);    //NOI18N
            }
            
            Icon dot = ThemeUtilities.getIcon(theme, ThemeImages.DOT);
            dot.setWidth(wd);
            dot.setHeight(ht);
            dot.setBorder(0);
            dot.setAlt("");
            RenderingUtilities.renderComponent(dot, context);
            
            // close the td tag
            writer.endElement("td");    //NOI18N
            writer.writeText("\n", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Log an error - only used during development time.
     */
    void log(String s) {
        if (LogUtil.fineEnabled(FileChooserRenderer.class)) {
            LogUtil.fine(FileChooserRenderer.class, s);
        }
    }

    /**
     * Helper method to get Javascript to submit the "go" button when the user
     * clicks enter in the page field.
     *
     * @return The Javascript used to submit the "go" button.
     */
    private String getReturnKeyJavascriptWrapper(String body) {
        ClientSniffer cs = ClientSniffer.getInstance(getFacesContext());

        // Get key code.
        String keyCode = cs.isNav() ? "event.which" : "event.keyCode"; //NOI18N

        // Append JS to capture the event.
        StringBuffer buff = new StringBuffer(128)
            .append("if (event && ") //NOI18N
            .append(keyCode)
            .append("==13) {"); //NOI18N

        // To prevent an auto-submit, Netscape 6.x and netscape 7.0 require 
        // setting the cancelBubble property. However, Netscape 7.1, 
        // Mozilla 1.x, IE 5.x for SunOS/Windows do not use this property.
        if (cs.isNav6() || cs.isNav70()) {
            buff.append("event.cancelBubble = true;"); //NOI18N
        }
        
        buff.append(body).append("} else { return true; }"); //NOI18N
        return buff.toString();
    }
}
