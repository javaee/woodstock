/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
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

package rating;

public class Rating {
    private static int NOT_INTERESTED_GRADE = -1;
    private static int CLEAR_GRADE = 0;

    private double averageGrade = 0.0;
    private int grade = 0;
    private int gradeSum = 0;
    private int numGrades = 0;
    private int numNotInterested = 0;

    public Rating() {
    }

    /** Getter for property averageGrade */
    public double getAverageGrade() {
	if (numGrades == 0)
	    return 0.0;

	// Compute average grade, rounded to neaest .001
        averageGrade = (double)gradeSum / numGrades;
	averageGrade = Math.rint(averageGrade * 1000) / 1000;
        return averageGrade;
    }
    
    /** Setter for property grade.  */
    public void setGrade(int grade) {
        this.grade = grade;

	// Not interested grade does not factor into average grade
	if (this.grade == NOT_INTERESTED_GRADE) {
	    this.numNotInterested++;
	    return;
	}

	// Clear grade does not factor into average grade
	if (this.grade == CLEAR_GRADE) {
	    return;
	}

        gradeSum += grade;
        numGrades++;
    }
}
