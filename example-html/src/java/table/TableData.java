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

package table;

/**
 * Model to hold table data.
 */
public class TableData {
    // An array of Name objects.
    protected static final Name[] names = {
        new Name("William", "Dupont"),
        new Name("Anna", "Keeney"),
        new Name("Mariko", "Randor"),
        new Name("John", "Wilson"),
        new Name("Lynn", "Seckinger"),
        new Name("Richard", "Tattersall"),
        new Name("Gabriella", "Sarintia"),
        new Name("Lisa", "Hartwig"),
        new Name("Shirley", "Jones"),
        new Name("Bill", "Sprague"),
        new Name("Greg", "Doench"),
        new Name("Solange", "Nadeau"),
        new Name("Heather", "McGann"),
        new Name("Roy", "Martin"),
        new Name("Claude", "Loubier"),
        new Name("Dan", "Woodard"),
        new Name("Ron", "Dunlap"),
        new Name("Keith", "Frankart"),
        new Name("Andre", "Nadeau"),
        new Name("Horace", "Celestin")
    };
    
    /**
     * Get table data.
     * 
     * @return An array of Name objects.
     */
    public Name[] getNames() {
        return names;
    }
}
