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

package com.sun.webui.jsf.renderkit.html;


import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.sun.webui.jsf.component.Rating;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.renderkit.widget.RatingRenderer;


/**
 *
 * Rating Component DesignTime Renderer
 */
public class RatingDesignTimeRenderer extends AbstractDesignTimeRenderer {

    private Theme theme;
    
    /** Creates a new instance of RatingDesignTimeRenderer */
    public RatingDesignTimeRenderer() {
        super(new RatingRenderer());
    }
    
    public void encodeBegin(FacesContext context, UIComponent component)
    throws IOException {
        
	ResponseWriter writer = context.getResponseWriter();
	theme = ThemeUtilities.getTheme(context);
	String clientId = component.getClientId(context);
	Rating rating = (Rating) component;
        
	// Get classnames used
	String outterDivClassName = theme.getStyleClass(ThemeStyles.RATING);
	String textContainerClassName = theme.getStyleClass(ThemeStyles.RATING_TEXT_CONTAINER);
	String controlContainerClassName = theme.getStyleClass(ThemeStyles.RATING_CONTROL_CONTAINER);
	String spacerClassName = theme.getStyleClass(ThemeStyles.RATING_SPACER_NODE);
	String gradeEmptyClassName = theme.getStyleClass(ThemeStyles.RATING_GRADE_EMPTY_IMAGE);
	String gradeFullClassName = theme.getStyleClass(ThemeStyles.RATING_GRADE_FULL_IMAGE);
	String gradeAvgFullClassName = theme.getStyleClass(ThemeStyles.RATING_GRADE_AVG_FULL_IMAGE);
	String gradeAvgHalfClassName = theme.getStyleClass(ThemeStyles.RATING_GRADE_AVG_HALF_IMAGE);
	String notInterestedOnClassName = theme.getStyleClass(ThemeStyles.RATING_NOT_INTERESTED_ON_IMAGE);
	String notInterestedOffClassName = theme.getStyleClass(ThemeStyles.RATING_NOT_INTERESTED_OFF_IMAGE);
	String clearOnClassName = theme.getStyleClass(ThemeStyles.RATING_CLEAR_ON_IMAGE);
	String clearOffClassName = theme.getStyleClass(ThemeStyles.RATING_CLEAR_OFF_IMAGE);
	String normalModeClassName = theme.getStyleClass(ThemeStyles.RATING_MODE_NORMAL_IMAGE);
	String averageModeClassName = theme.getStyleClass(ThemeStyles.RATING_MODE_AVERAGE_IMAGE);
	String hiddenStyleClass = theme.getStyleClass(ThemeStyles.HIDDEN);

	// Get image properties
	//
	int notInterestedOnWidth = getImageIntProp(ThemeImages.RATING_NOT_INTERESTED_ON_WIDTH);
	int notInterestedOffWidth = getImageIntProp(ThemeImages.RATING_NOT_INTERESTED_OFF_WIDTH);
	int notInterestedOnHeight = getImageIntProp(ThemeImages.RATING_NOT_INTERESTED_ON_HEIGHT);
	int notInterestedOffHeight = getImageIntProp(ThemeImages.RATING_NOT_INTERESTED_OFF_HEIGHT);
	int ratingGradeFullWidth = getImageIntProp(ThemeImages.RATING_GRADE_FULL_WIDTH);
	int ratingGradeFullHeight = getImageIntProp(ThemeImages.RATING_GRADE_FULL_HEIGHT);
	int ratingGradeEmptyWidth = getImageIntProp(ThemeImages.RATING_GRADE_EMPTY_WIDTH);
	int ratingGradeEmptyHeight = getImageIntProp(ThemeImages.RATING_GRADE_EMPTY_HEIGHT);
	int ratingAvgGradeHalfWidth = getImageIntProp(ThemeImages.RATING_AVG_GRADE_HALF_WIDTH);
	int ratingAvgGradeHalfHeight = getImageIntProp(ThemeImages.RATING_AVG_GRADE_HALF_HEIGHT);
	int ratingAvgGradeFullWidth = getImageIntProp(ThemeImages.RATING_AVG_GRADE_FULL_WIDTH);
	int ratingAvgGradeFullHeight = getImageIntProp(ThemeImages.RATING_AVG_GRADE_FULL_HEIGHT);
	int clearOnWidth = getImageIntProp(ThemeImages.RATING_CLEAR_ON_WIDTH);
	int clearOffWidth = getImageIntProp(ThemeImages.RATING_CLEAR_OFF_WIDTH);
	int clearOnHeight = getImageIntProp(ThemeImages.RATING_CLEAR_ON_HEIGHT);
	int clearOffHeight = getImageIntProp(ThemeImages.RATING_CLEAR_OFF_HEIGHT);
	int modeAverageWidth = getImageIntProp(ThemeImages.RATING_MODE_AVG_WIDTH);
	int modeAverageHeight = getImageIntProp(ThemeImages.RATING_MODE_AVG_HEIGHT);
	int modeNormalWidth = getImageIntProp(ThemeImages.RATING_MODE_NORMAL_WIDTH);
	int modeNormalHeight = getImageIntProp(ThemeImages.RATING_MODE_NORMAL_HEIGHT);

	// Get message properties
	//
	int spacerWidth = getMessageIntProp("rating.spacerWidth");
	int gradeMarginRight = getMessageIntProp("rating.gradeMarginRight");

	// As we "walk-thru" the configuration and determine what is being displayed,
	// we need to maintain the minimum width of the container for the grade controls.
	int controlContainerWidth = 0;

	// Determine width of notInterested control
	if (rating.isIncludeNotInterested()) {
	    if (rating.getGrade() == Rating.NOT_INTERESTED_GRADE)
		controlContainerWidth += notInterestedOnWidth;
	    else
		controlContainerWidth += notInterestedOffWidth;
	    controlContainerWidth += gradeMarginRight;
	}

	// Determine the width and css class to be used for each grade.
	//
	int[] gradeWidths = null;
	String[] gradeClasses = null;
	if (rating.getMaxGrade() > 0) {
	    gradeWidths = new int[rating.getMaxGrade()];
	    gradeClasses = new String[rating.getMaxGrade()];
	}
	for (int i = 1; i <= rating.getMaxGrade(); i++) {
	    if (rating.isInAverageMode()) {
		// Compute the difference between the average grade and the rank
                // associated with this image.
		double diff = rating.getAverageGrade() - i;

		// Show correct image based on diff
		if (diff < -0.5) {
		    // Difference is more than half-grade below zero.
		    // Show empty grade
		    gradeWidths[i-1] = ratingGradeEmptyWidth;
		    gradeClasses[i-1] = gradeEmptyClassName;
		} else if (diff < 0) {
		    // Difference is less than a half-grade below 0.
		    // Show average half-full grade
		    gradeWidths[i-1] = ratingAvgGradeHalfWidth;
		    gradeClasses[i-1] = gradeAvgHalfClassName;
		} else {
		    // Difference is 0 or higher.
		    // Show average full grade.
		    gradeWidths[i-1] = ratingAvgGradeFullWidth;
		    gradeClasses[i-1] = gradeAvgFullClassName;
		}
	    } else {
		int grade = (rating.getGrade() < 0) ? 0 : rating.getGrade();
		if (i <= grade) {
		    // Show full user's grade
		    gradeWidths[i-1] = ratingGradeFullWidth;
		    gradeClasses[i-1] = gradeFullClassName;
                } else {
		    // Show empty grade
		    gradeWidths[i-1] = ratingGradeEmptyWidth;
		    gradeClasses[i-1] = gradeEmptyClassName;
                }
	    }
	    controlContainerWidth += (gradeWidths[i-1] + gradeMarginRight);
	}

	// Determine width of spacer between clear and modeToggle controls
	if (rating.isIncludeClear() || rating.isIncludeModeToggle()) {
	    controlContainerWidth += spacerWidth;
	}

	// Determine width of clear control
	if (rating.isIncludeClear()) {
	    if (rating.getGrade() == Rating.CLEAR_GRADE)
		controlContainerWidth += clearOnWidth;
	    else
		controlContainerWidth += clearOffWidth;
	    controlContainerWidth += gradeMarginRight;
	}

	// Determine width of modeToggle control
	if (rating.isIncludeModeToggle()) {
	    if (rating.isInAverageMode())
		controlContainerWidth += modeAverageWidth;
	    else
		controlContainerWidth += modeNormalWidth;
	}
        
	StringBuffer sb = new StringBuffer();
        
	// Component's top-level div
	//
	writer.startElement("div", component);  //NOI18N
	writer.writeAttribute("id", clientId, null);  //NOI18N

	sb.append(outterDivClassName);
	if (!rating.isVisible()) {
	    sb.append(" ");
	    sb.append(hiddenStyleClass);
	}
	writer.writeAttribute("class", sb.toString(), "styleClass");  //NOI18N
	writer.write("\n");
	sb.setLength(0);
        
	String style = (String) component.getAttributes().get("style"); //NOI18N
	if ((style != null) && (style.length() > 0))
	    sb.append(style);

	// If height not specified, compute it.
	if ((style == null) || !style.contains("height")) {
	    // height for images is max of all images used
	    int height = 0;
	    if (rating.isIncludeNotInterested()) {
		height = Math.max(height, notInterestedOnHeight);
		height = Math.max(height, notInterestedOffHeight);
	    }
	    height = Math.max(height, ratingGradeFullHeight);
	    height = Math.max(height, ratingGradeEmptyHeight);
	    height = Math.max(height, ratingAvgGradeHalfHeight);
	    height = Math.max(height, ratingAvgGradeFullHeight);
	    if (rating.isIncludeClear()) {
		height = Math.max(height, clearOnHeight);
		height = Math.max(height, clearOffHeight);
	    }
	    if (rating.isIncludeModeToggle()) {
		height = Math.max(height, modeAverageHeight);
		height = Math.max(height, modeNormalHeight);
	    }

	    // If including text and no font sizing specified, add px approximation for font size.
	    if (rating.isIncludeText() && (style != null) && !style.contains("font-size"))
		height += 15;
	    if (sb.length() > 0)
		sb.append(";");
	    sb.append(" height:" + height + "px");
	}

	// If width not specified, set to at least width of images control container.
	if ((style == null) || !style.contains("width")) {
	    if (sb.length() > 0)
		sb.append(";");
	    sb.append(" width:" + controlContainerWidth + "px;");
	}
	if (sb.length() > 0)
	    writer.writeAttribute("style", sb.toString(), null); //NOI18N
	sb.setLength(0);

	// Text container
	if (rating.isIncludeText()) {
	    writer.startElement("div", component);  // NO18N
	    writer.writeAttribute("id", clientId + "_text", null);  //NOI18N
	    writer.writeAttribute("class", textContainerClassName, null);  //NOI18N
	    writer.writeAttribute("style", "width:" + controlContainerWidth + "px", "style");
	    writer.write("&nbsp;");
            writer.endElement("div");
	    writer.write("\n");
	}

	// Control container
	writer.startElement("div", component);  // NO18N
	writer.writeAttribute("id", clientId + "_control", null);  //NOI18N
	writer.writeAttribute("class", controlContainerClassName, null);  //NOI18N
        writer.writeAttribute("style", "width:" + controlContainerWidth + "px", "style");
        
        // IE requires that we wrap the notInterested, clear, and modeToggleimage divs 
        // in another div, else the float:left property on the image's div does not 
        // take effect, resulting in a bad layout.

	// Not interested image
	if (rating.isIncludeNotInterested()) {
	    writer.startElement("div", component);  // NO18N
	    writer.startElement("div", component);  // NO18N
	    writer.writeAttribute("id", clientId + "_notInterested", null);  //NOI18N
	    if (rating.getGrade() == Rating.NOT_INTERESTED_GRADE)
		writer.writeAttribute("class", notInterestedOnClassName, null); //NO18N
	    else
		writer.writeAttribute("class", notInterestedOffClassName, null); //NO18N
            writer.endElement("div");
            writer.endElement("div");
	    writer.write("\n");
	}

	// Grade images
	writer.startElement("div", component);  // NO18N
	writer.writeAttribute("id", clientId + "_gradeContainer", null);  //NOI18N
	for (int i = 1; i <= rating.getMaxGrade(); i++) {
	    writer.startElement("div", component);  // NO18N
	    writer.writeAttribute("id", clientId + "_grade" + i, null);  //NOI18N
	    writer.writeAttribute("class", gradeClasses[i-1], null); //NO18N
            writer.endElement("div");
	    writer.write("\n");
	}
        writer.endElement("div");
	writer.write("\n");
	
	// Spacer between Clear and Not Interested images
	if (rating.isIncludeClear() || rating.isIncludeModeToggle()) {
	    writer.startElement("div", component);  // NO18N
	    writer.writeAttribute("class", spacerClassName, null); //NO18N
            writer.endElement("div");
	    writer.write("\n");
	}

	// Clear image
	if (rating.isIncludeClear()) {
	    writer.startElement("div", component);  // NO18N
	    writer.startElement("div", component);  // NO18N
	    writer.writeAttribute("id", clientId + "_clear", null);  //NOI18N
	    if (rating.getGrade() == Rating.CLEAR_GRADE)
		writer.writeAttribute("class", clearOnClassName, null); //NO18N
	    else
		writer.writeAttribute("class", clearOffClassName, null); //NO18N
            writer.endElement("div");
            writer.endElement("div");
	    writer.write("\n");
	}

	// Mode toggle image
	if (rating.isIncludeModeToggle()) {
	    writer.startElement("div", component);  // NO18N
	    writer.startElement("div", component);  // NO18N
	    writer.writeAttribute("id", clientId + "_modeToggle", null);  //NOI18N
	    if (rating.isInAverageMode())
		writer.writeAttribute("class", averageModeClassName, null); //NO18N
	    else
		writer.writeAttribute("class", normalModeClassName, null); //NO18N
            writer.endElement("div");
            writer.endElement("div");
	    writer.write("\n");
	}

	// Control container end div
        writer.endElement("div");
	writer.write("\n");

	// Component's end div
        writer.endElement("div");
	writer.write("\n");

    }
    
    public void encodeChildren(FacesContext context, UIComponent component) 
        throws IOException {
        // don't do anything
    }
    
    public void encodeEnd(FacesContext context, UIComponent component)
        throws IOException {
        //don't do anything.
    }

    private int getImageIntProp(String key) {
	try {
	    return Integer.parseInt(theme.getImageString(key));
        } catch (Exception e) {
	    return 0;
        }
    }

    private int getMessageIntProp(String key) {
	try {
	    return Integer.parseInt(theme.getMessage(key));
        } catch (Exception e) {
	    return 0;
        }
    }
}
