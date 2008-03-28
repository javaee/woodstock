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

package com.sun.webui.jsf.example.rating;

import java.io.Serializable;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.faces.event.ActionEvent;

import com.sun.webui.jsf.example.index.IndexBackingBean;
import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.util.ExampleUtilities;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.component.Rating;

/**
 * Backing bean for the Rating examples.  This bean is shared amongst the multiple
 * rating instances used in this example.  And so while most of the config properties
 * (hover text, etc...) are the same, the important properties related to the
 * "value" (grade, averageGrade, etc.) must be unique per instance.  Thus the use
 * of arrays to manage these properties.
 */
public class RatingBackingBean implements Serializable {

    public static final String SHOW_RATING = "showRating";
    private static final int NUM_RATINGS = 3;

    private double[] averageGrade = new double[NUM_RATINGS];
    private String clearAcknowledgedText = null;
    private String clearHoverText = null;
    private int[] grade = new int[NUM_RATINGS];
    private int maxGrade = 5;
    private String gradeAcknowledgedText = null;
    private String[] gradeHoverTexts = null;
    private String[] modeToggleAcknowledgedTexts = null;
    private String[] modeToggleHoverTexts = null;
    private String notInterestedAcknowledgedText = null;
    private String notInterestedHoverText = null;
    private int[] gradeSum = new int[NUM_RATINGS];
    private int[] numGrades = new int[NUM_RATINGS];
    private int[] numNotInterested = new int[NUM_RATINGS];

    /**
     * Constructor
     */
    public RatingBackingBean() {
	_reset();
    } // constructor
    
    /** Getter for property clearAcknowledgedText.  */
    public String getClearAcknowledgedText() {
        return this.clearAcknowledgedText;
    }

    /** Setter for property clearAcknowledgedText.  */
    public void setClearAcknowledgedText(String clearAcknowledgedText) {
        this.clearAcknowledgedText = clearAcknowledgedText;
    }
    
    /** Getter for property clearHoverText.  */
    public String getClearHoverText() {
        return this.clearHoverText;
    }

    /** Setter for property clearHoverText.  */
    public void setClearHoverText(String clearHoverText) {
        this.clearHoverText = clearHoverText;
    }

    /** Getter for rating1 not interested count */
    public int getNumNotInterested1() {
	return this.numNotInterested[0];
    }

    /** Setter for rating1 not interested count */
    public void setNumNotInterested1(int numNotInterested) {
	this.numNotInterested[0] = numNotInterested;
    }

    /** Getter for rating2 not interested count */
    public int getNumNotInterested2() {
	return this.numNotInterested[1];
    }

    /** Setter for rating2 not interested count */
    public void setNumNotInterested2(int numNotInterested) {
	this.numNotInterested[1] = numNotInterested;
    }

    /** Getter for rating3 not interested count */
    public int getNumNotInterested3() {
	return this.numNotInterested[2];
    }

    /** Setter for rating3 not interested count */
    public void setNumNotInterested3(int numNotInterested) {
	this.numNotInterested[2] = numNotInterested;
    }

    /** Getter for rating1 property grade.  */
    public int getGrade1() {
        return grade[0];
    }
    
    /** Setter for rating1 property grade.  */
    public void setGrade1(int grade) {
	setGrade(0, grade);
    }
    
    /** Getter for rating2 property grade.  */
    public int getGrade2() {
        return grade[1];
    }
    
    /** Setter for rating2 property grade.  */
    public void setGrade2(int grade) {
	setGrade(1, grade);
    }
    
    /** Getter for rating3 property grade.  */
    public int getGrade3() {
        return grade[2];
    }
    
    /** Setter for rating3 property grade.  */
    public void setGrade3(int grade) {
	setGrade(2, grade);
    }
    
    /** Getter for maximum grade.  */
    public int getMaxGrade() {
        return maxGrade;
    }
    
    /** Setter for maximum grade.  */
    public void setMaxGrade(int maxGrade) {
        this.maxGrade = maxGrade;
    }

    /** Getter for rating1 property averageGrade.  */
    public double getAverageGrade1() {
	return getAverageGrade(0);
    }
    
    /** Setter for rating1 property averageGrade.  */
    public void setAverageGrade1(double averageGrade) {
        this.averageGrade[0] = averageGrade;
    }

    /** Getter for rating2 property averageGrade.  */
    public double getAverageGrade2() {
	return getAverageGrade(1);
    }
    
    /** Setter for rating2 property averageGrade.  */
    public void setAverageGrade2(double averageGrade) {
        this.averageGrade[1] = averageGrade;
    }

    /** Getter for rating3 property averageGrade.  */
    public double getAverageGrade3() {
	return getAverageGrade(2);
    }
    
    /** Setter for rating3 property averageGrade.  */
    public void setAverageGrade3(double averageGrade) {
        this.averageGrade[2] = averageGrade;
    }

    /** Getter for property gradeAcknowledgedText.  */
    public String getGradeAcknowledgedText() {
        return this.gradeAcknowledgedText;
    }

    /** Setter for property gradeAcknowledgedText.  */
    public void setGradeAcknowledgedText(String gradeAcknowledgedText) {
        this.gradeAcknowledgedText = gradeAcknowledgedText;
    }
        
    /** Getter for property gradeHoverTexts.  */
    public String[] getGradeHoverTexts() {
        return this.gradeHoverTexts;
    }

    /** Setter for property gradeHoverTexts.  */
    public void setGradeHoverTexts(String[] gradeHoverTexts) {
        this.gradeHoverTexts = gradeHoverTexts;
    }
        
    /** Getter for property modeToggleHoverTexts.  */
    public String[] getModeToggleHoverTexts() {
        return this.modeToggleHoverTexts;
    }

    /** Setter for property modeToggleHoverTexts.  */
    public void setModeToggleHoverTexts(String[] modeToggleHoverTexts) {
        this.modeToggleHoverTexts = modeToggleHoverTexts;
    }
        
    /** Getter for property modeAcknowledgedHoverTexts.  */
    public String[] getModeToggleAcknowledgedTexts() {
        return this.modeToggleAcknowledgedTexts;
    }

    /** Setter for property modeToggleAcknowledgedTexts.  */
    public void setModeToggleAcknowledgedTexts(String[] modeToggleAcknowledgedTexts) {
        this.modeToggleAcknowledgedTexts = modeToggleAcknowledgedTexts;
    }
        
    /** Getter for property notInterestedAcknowledgedText.  */
    public String getNotInterestedAcknowledgedText() {
        return this.notInterestedAcknowledgedText;
    }

    /** Setter for property notInterestedAcknowledgedText.  */
    public void setNotInterestedAcknowledgedText(String notInterestedAcknowledgedText) {
        this.notInterestedAcknowledgedText = notInterestedAcknowledgedText;
    }
        
    /** Getter for property notInterestedHoverText.  */
    public String getNotInterestedHoverText() {
        return this.notInterestedHoverText;
    }

    /** Setter for property notInterestedHoverText.  */
    public void setNotInterestedHoverText(String notInterestedHoverText) {
        this.notInterestedHoverText = notInterestedHoverText;
    }
    
    /** Getter for rating1 number of grades.  */
    public int getNumGrades1() {
        return numGrades[0];
    }
    
    /** Getter for rating2 number of grades.  */
    public int getNumGrades2() {
        return numGrades[1];
    }
    
    /** Getter for rating3 number of grades.  */
    public int getNumGrades3() {
        return numGrades[2];
    }

    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Action Handlers
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** Action handler when navigating to the main example index. */
    public String showExampleIndex() {
	_reset();
        return IndexBackingBean.INDEX_ACTION;
    }

    /** Action handler for the Reset button */
    public String reset() {
	_reset();

	return SHOW_RATING;
    }

    /** Action handler when navigating to the rating examples. */
    public String showRating() {
	return SHOW_RATING;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /** Setter for property grade.  */
    private void setGrade(int n, int grade) {
	// Only if grade is changing.
	if (this.grade[n] == grade)
	    return;

        this.grade[n] = grade;

	// Not interested grade does not factor into average grade
	if (this.grade[n] == Rating.NOT_INTERESTED_GRADE) {
	    this.numNotInterested[n]++;
	    return;
	}

	// Clear grade does not factor into average grade
	if (this.grade[n] == Rating.CLEAR_GRADE) {
	    return;
	}

        gradeSum[n] += grade;
        numGrades[n]++;
    }
    
    /** Getter for property averageGrade.  */
    private double getAverageGrade(int n) {
	if (numGrades[n] == 0)
	    return 0.0;

	// Compute average grade, rounded to neaest .001
        averageGrade[n] = (double)gradeSum[n] / numGrades[n];
	averageGrade[n] = Math.rint(averageGrade[n] * 1000) / 1000;
        return averageGrade[n];
    }

    /** Initial all bean values to their defaults */
    private void _reset() {
	gradeHoverTexts = new String[this.maxGrade];
	for (int i=1; i <= this.maxGrade; i++)
	    gradeHoverTexts[i-1] =  MessageUtil.getMessage("rating_gradeHoverText" + i);

	gradeAcknowledgedText =  MessageUtil.getMessage("rating_gradeAcknowledgedText");
	notInterestedHoverText =  MessageUtil.getMessage("rating_notInterestedHoverText");
	notInterestedAcknowledgedText =  MessageUtil.getMessage("rating_notInterestedAcknowledgedText");
	clearHoverText =  MessageUtil.getMessage("rating_clearHoverText");
	clearAcknowledgedText =  MessageUtil.getMessage("rating_clearAcknowledgedText");

	modeToggleHoverTexts = new String[2];
	modeToggleAcknowledgedTexts = new String[2];
	for (int i=1; i <= 2; i++) {
	    modeToggleHoverTexts[i-1] =  MessageUtil.getMessage("rating_modeToggleHoverText" + i);
	    modeToggleAcknowledgedTexts[i-1] =  MessageUtil.getMessage("rating_modeToggleAcknowledgedText" + i);
	}

	for (int i=0; i < NUM_RATINGS; i++) {
	    grade[i] = Rating.CLEAR_GRADE;
	    averageGrade[i] = 0.0;
	    gradeSum[i] = 0;
	    numGrades[i] = 0;
	    numNotInterested[i] = 0;
	}
    }
}
