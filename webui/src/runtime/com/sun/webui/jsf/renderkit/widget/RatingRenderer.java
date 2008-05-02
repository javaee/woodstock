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
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */

package com.sun.webui.jsf.renderkit.widget;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.Rating;
import com.sun.webui.jsf.util.JSONUtilities;
import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders Rating component.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.Rating",
        componentFamily="com.sun.webui.jsf.Rating"))
public class RatingRenderer extends RendererBase {
    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String stringAttributes[] = {        
        "style"                
    };
    
    /**
     * The set of pass-through int attributes to be rendered.
     */
    private static final String intAttributes[] = {        
	"tabIndex"
    };


    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * This method looks for a particular name value pair in the request 
     * parameter map. The name has to be the client id of the component appended
     * with the suffix "_submitValue" and the value is the selected grade
     * of the rating. If such a name value pair exists, then the component's
     * submitted value is set to the value present in the name-value pair.
     */
    public void decode(FacesContext context, UIComponent component) {
        if (context == null) {
            throw new NullPointerException();
        }
    
	if (!(component instanceof Rating)) {
	    throw new IllegalArgumentException(
              "RatingRenderer can only render Rating components.");
        } 
        
        Rating rating = ((Rating)component);
	String hiddenFieldId = rating.getClientId(context) + "_submitValue";

	Map map = context.getExternalContext().getRequestParameterMap();
	String value = (String) map.get(hiddenFieldId);
	if (value == null) {
	    return;
	}
	try {
	    Integer v = Integer.valueOf(value);
	    rating.setSubmittedValue(v);
	} catch (Exception e) {
	}
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to obtain component properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs
     * @exception JSONException if a key/value error occurs
     */
    protected JSONObject getProperties(FacesContext context,
            UIComponent component) throws IOException, JSONException {
	if (!(component instanceof Rating)) {
	    throw new IllegalArgumentException(
                "RatingRenderer can only render Rating components.");
        }
        Rating rating = (Rating) component;
                        
        JSONObject json = new JSONObject();
        json.put("autoSubmit", rating.isAutoSubmit())
	    .put("averageGrade", rating.getAverageGrade())
            .put("clearAcknowledgedText", rating.getClearAcknowledgedText())
            .put("clearHoverText", rating.getClearHoverText())
            .put("grade", rating.getGrade())
            .put("gradeAcknowledgedText", rating.getGradeAcknowledgedText())
            .put("gradeReadOnly", rating.isGradeReadOnly())
            .put("inAverageMode", rating.isInAverageMode())
            .put("includeClear", rating.isIncludeClear())
            .put("includeModeToggle", rating.isIncludeModeToggle())
            .put("includeNotInterested", rating.isIncludeNotInterested())
            .put("includeText", rating.isIncludeText())
            .put("maxGrade", rating.getMaxGrade())
            .put("modeReadOnly", rating.isModeReadOnly())
            .put("notInterestedAcknowledgedText", rating.getNotInterestedAcknowledgedText())
            .put("notInterestedHoverText", rating.getNotInterestedHoverText());
        
        JSONArray jarray;
        String[] texts;
        
        jarray = new JSONArray();
        json.put("gradeHoverTexts", jarray);
        texts = rating.getGradeHoverTexts();
        for (int i = 0; (texts != null) && (i < texts.length); i++)
            jarray.put(texts[i]);
        
        jarray = new JSONArray();
        json.put("modeToggleHoverTexts", jarray);
        texts = rating.getModeToggleHoverTexts();
        for (int i = 0; (texts != null) && (i < texts.length); i++)
            jarray.put(texts[i]);
        
        jarray = new JSONArray();
        json.put("modeToggleAcknowledgedTexts", jarray);
        texts = rating.getModeToggleAcknowledgedTexts();
        for (int i = 0; (texts != null) && (i < texts.length); i++)
            jarray.put(texts[i]);
        
       // Add attributes.
        JSONUtilities.addStringProperties(stringAttributes, component, json);
        JSONUtilities.addIntegerProperties(intAttributes, component, json);
        return json;
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "rating";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
