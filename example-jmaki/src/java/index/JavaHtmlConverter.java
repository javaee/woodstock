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

package index;

import java.io.*;
import java.util.*;

/**
 * JavaHtmlConverter converts the contents of a Java source code file to HTML.
 * The HTML generated display line numbers and color codes Java keywords (or 
 * at least those in the private keyword array field).
 * 
 * @author  Sun Microsystems, Inc.
 */
public class JavaHtmlConverter extends Object {
    /**
     * Accepts a Reader object containing the Java source code to markup and
     * a Writer object that will be used to output the HTML to display the 
     * source.
     *
     * @param in Contains the Java source code
     * @param out Used to output the HTML displaying the source
     */
    public static void convert(Reader in, Writer out)
	    throws IOException {
	convert(in, out, false, true, -1, -1);
    }	

    /**
     * Accepts a Reader object containing the Java source code to markup and
     * a Writer object that will be used to output the HTML to display the 
     * source. Also accepts a boolean indicating if the HTML generated will
     * be embedded withing an enclosing HTML page, or will be standalone. If
     * embeddable is false, the HTML header and footer will be written to out.
     * Otherwise, no header or footer are written.
     *
     * @param in Contains the Java source code
     * @param out Used to output the HTML displaying the source
     * @param embeddable Indicates if the HTML output will be embedded inside
     *        an HTML page. If false, the HTML header & footer will be written
     *        to the Writer object.
     */
    public static void convert(Reader in, Writer out, boolean embeddable)
	    throws IOException {
	convert(in, out, embeddable, true, -1, -1);
    }	
 
    /**
     * Accepts a Reader object containing the Java source code to markup and
     * a Writer object that will be used to output the HTML to display the 
     * source. Also accepts a boolean indicating if the HTML generated will
     * be embedded withing an enclosing HTML page, or will be standalone. If
     * embeddable is false, the HTML header and footer will be written to out.
     * Otherwise, no header or footer are written. The highlightKeywords param
     * should be set to true if color coding of Java keywords is desired.
     *
     * @param in Contains the Java source code
     * @param out Used to output the HTML displaying the source
     * @param embeddable Indicates if the HTML output will be embedded inside
     *        an HTML page. If false, the HTML header & footer will be written
     *        to the Writer object.
     * @param highlightKeywords Indicates if Java keyword color coding / 
     *        highlighting is desired
     */
    public static void convert(Reader in, Writer out, boolean embeddable, 
			       boolean highlightKeywords)
	throws IOException {

	convert(in, out, embeddable, highlightKeywords, -1, -1);
    }	
    
    /**
     * Accepts a Reader object containing the Java source code to markup and
     * a Writer object that will be used to output the HTML to display the 
     * source. Also accepts a boolean indicating if the HTML generated will
     * be embedded withing an enclosing HTML page, or will be standalone. If
     * embeddable is false, the HTML header and footer will be written to out.
     * Otherwise, no header or footer are written. The highlightKeywords param
     * should be set to true if color coding of Java keywords is desired. This
     * constructor can also be used to highlight a certain range of lines in the
     * markup generated for the Java source. The desired lines to highlight are
     * specified via the startLineHighlight and endLineHighlight int params.
     *
     * @param in Contains the Java source code
     * @param out Used to output the HTML displaying the source
     * @param embeddable Indicates if the HTML output will be embedded inside
     *        an HTML page. If false, the HTML header & footer will be written
     *        to the Writer object.
     * @param highlightKeywords Indicates if Java keyword color coding / 
     *        highlighting is desired
     * @param startLineHighlight The first line of the Java source that should
     *        be highlighted from the rest
     * @param endLineHighlight The last line of the Java source that should be 
     *        highlighted from the rest
     */
    public static void convert(Reader in, Writer out, 
	boolean embeddable, boolean highlightKeywords, 
	int startLineHighlight, int endLineHighlight) 
	    throws IOException {

	int lineNumber = 1;
	
	StringBuffer buf = new StringBuffer(2048);
	int c = 0;
	int kwl = 0;
	int bufl = 0;
	char ch = 0;
	char lastChar;
	
	final int STATE_NORMAL       = 0;
	final int STATE_STRING       = 1;
	final int STATE_CHAR         = 2;
	final int STATE_COMMENT_LINE = 3;
	final int STATE_COMMENT      = 4;
	
	// Keep a state flag
	int state = STATE_NORMAL;
	
	// Write the header
	if (!embeddable) {
	    out.write("<html>\r\n<head>\r\n<title>");
	    out.write("</title>\r\n</head>\r\n<body ");
	    out.write("bgcolor=\"" + backgroundColor +"\" ");
	    out.write("text=\"" + textColor +"\">\r\n");
	}
	
	out.write("<font size=\"3\"><pre>\r\n");
	out.write(getLineNumberReference(
	    lineNumber++, startLineHighlight, endLineHighlight));
	
	while (c != -1) {
	    c = in.read();
	    lastChar = ch;
	    ch = c >= 0 ? (char) c : 0;

	    if (state == STATE_NORMAL) {
		if (kwl == 0 && Character.isJavaIdentifierStart(ch) 
		    && !Character.isJavaIdentifierPart(lastChar)
		    || kwl > 0 && Character.isJavaIdentifierPart(ch)) {

		    buf.append(ch);
		    bufl++;
		    kwl++;
		    continue;
		} else if (kwl > 0) {
		    String kw = buf.toString().
			substring(buf.length() - kwl);
		    if (keywordList.contains(kw) && 
			highlightKeywords) {
			buf.insert(buf.length() - kwl, 
			    "<font color=\"" + keywordColor + "\">");
			buf.append("</font>");
		    }
		    kwl = 0;
		}
	    }

	    switch (ch) {
		case '&':
		    buf.append("&amp;");
		    bufl++;
		    break;
			
		case '\"':
		    buf.append("&quot;");
		    bufl++;
		    if (state == STATE_NORMAL) {
			buf.insert(buf.length() - 6, 
			    "<font color=\"" + stringColor + "\">");
			state = STATE_STRING;
		    } else if (state == STATE_STRING && lastChar != '\\') {
			buf.append("</font>");
			state = STATE_NORMAL;
		    }
			
		    break;
			
		case '\'':
		    buf.append("\'");
		    bufl++;
		    if (state == STATE_NORMAL)
			state = STATE_CHAR;
		    else if (state == STATE_CHAR && lastChar != '\\')
			state = STATE_NORMAL;

		    break;
			
		case '\\':
		    buf.append("\\");
		    bufl++;
		    if (lastChar == '\\' && 
		       (state == STATE_STRING || state == STATE_CHAR))
			lastChar = 0;

		    break;
			
		case '/':
		    buf.append("/");
		    bufl++;
		    if (state == STATE_COMMENT && lastChar == '*') {
			buf.append("</font>");
			state = STATE_NORMAL;
		    }
		    if (state == STATE_NORMAL && lastChar == '/') {
			buf.insert(buf.length() - 2, 
			    "<font color=\"" + commentColor + "\">");
			state = STATE_COMMENT_LINE;
		    }

		    break;
			
		case '*':
		    buf.append("*");
		    bufl++;
		    if (state == STATE_NORMAL && lastChar == '/') {
			buf.insert(buf.length() - 2, 
			    "<font color=\"" + commentColor + "\">");
			state = STATE_COMMENT;
		    }

		    break;
			
		case '<':
		    buf.append("&lt;");
		    bufl++;
		    break;
			
		case '>':
		    buf.append("&gt;");
		    bufl++;
		    break;
			
		case '\t':
		    int n = bufl / tabsize * tabsize + tabsize;
		    while (bufl < n) {
			buf.append(' ');
			bufl++;
		    }

		    break;
			
		case '\r':
		    // Ignore; we will deal with these
		    // as part of the '\n' processing
		    break;
			
		case '\n':
		    if (state == STATE_COMMENT_LINE) {
			buf.append("</font>");
			state = STATE_NORMAL;
		    }
			
		    buf.append('\r');
		    buf.append(ch);
		    buf.append(getLineNumberReference(
			lineNumber++, startLineHighlight, endLineHighlight));
			
		    if (buf.length() >= 1024) {
			out.write(buf.toString());
			buf.setLength(0);
		    }

		    bufl = 0;

		    if (kwl != 0)
			kwl = 0; // This should never execute
		    if (state != STATE_NORMAL && state != STATE_COMMENT)
			state = STATE_NORMAL; // Syntax Error

		    break;
			
		case 0:
		    if (c < 0) {
			if (state == STATE_COMMENT_LINE) {
			    buf.append("</font>");
			    state = STATE_NORMAL;
			}
			out.write(buf.toString());
			buf.setLength(0);
			bufl = 0;
			if (state == STATE_COMMENT) {
			    // Syntax Error
			    buf.append("</font>");
			    state = STATE_NORMAL;
			}

			break;
		    }
			
		default:
		    bufl++;
		    buf.append(ch);
	    }
	}

	out.write("</pre></font>");
	if (!embeddable)
	    out.write("\r\n</body>\r\n</html>");
	
	out.flush();
    }

    /**
     * Returns an anchor tag containg the necessary markup to highlight the
     * given lineNumber. The given lineNumber must be between 
     * startLineHighlight and endLineHighlight or else its color will not 
     * be changed (it will not be highlighted). Note that this ONLY
     * highlights the line number, not the entire line.
     *
     * @param lineNumber The line number to be highlighted
     * @param startLineHighlight Minimum value for lineNumber
     * @param endLineHighlight Maximum value for lineNumber
     * @return String Contains the markup to display the highlighted line
     *         number
     */
    private static String getLineNumberReference(int lineNumber,
	    int startLineHighlight, int endLineHighlight) {
	String result = "<a name=\"line" + lineNumber + "\"><font color=\"";
	
	if (startLineHighlight != -1 && endLineHighlight != -1 && 
	    lineNumber >= startLineHighlight && lineNumber < endLineHighlight)
	    result += highlightLineNumberColor;
	else
	    result += lineNumberColor;
	
	result += "\">";
	
	if (lineNumber < 10)
	    result += "&nbsp;&nbsp;&nbsp;" + lineNumber;
	else
	    if (lineNumber < 100)
		result += "&nbsp;&nbsp;" + lineNumber;
	    else
		if (lineNumber < 1000)
		    result += "&nbsp;" + lineNumber;
		else
		    if (lineNumber < 10000)
			result += lineNumber;
	
	result += "</font></a>&nbsp;";
	return result;
    }
    
    // Class variables
    
    /**
     * The words that will color coded in the HTML as Java keywords
     */
    static final String[] keywords = {
	"abstract", "default", "if",         "private",   "throw",
	"boolean",  "do",      "implements", "protected", "throws",
	"break",    "double",  "import",     "public",    "transient",
	"byte",     "else",    "instanceof", "return",    "try",
	"case",     "extends", "int",        "short",     "void",
	"catch",    "final",   "interface",  "static",    "volatile",
	"char",     "finally", "long",       "super",     "while",
	"class",    "float",   "native",     "switch",
	"const",    "for",     "new",        "synchronized",
	"continue", "goto",    "package",    "this"
    };
    
    /**
     * Helper List to process the Java keywords array
     */
    private static List keywordList = new ArrayList(keywords.length);
    /**
     * Size to use for syntax indenting in the HTML produced
     */
    private static int tabsize = 8;
    
    // Note, without the hashmarks, JEditorPane doesn't understand these colors
    /**
     * Background color used for the HTML produced
     */
    private static String backgroundColor = "#FFFFFF";
    /**
     * Color used for normal text in the HTML produced
     */
    private static String textColor = "#000000";
    /**
     * Color used for keyword text in the HTML produced
     */
    private static String keywordColor = "#0000F0";
    /**
     * Color used for Java comment text in the HTML produced
     */
    private static String commentColor = "#1E801E";
    /**
     * Color used for Java String literals in the HTML produced
     */
    private static String stringColor = "#007F7F";
    /**
     * Color used for line number text in the HTML produced
     */
    private static String lineNumberColor = "#C0C0C0";
    /**
     * Color used to highlight line number text in the HTML produced
     */
    private static String highlightLineNumberColor = "#FF0000";
    
    // Static initialization
    
    static {
	for (int i = 0; i < keywords.length; i++)
	    keywordList.add(keywords[i]);
    }
}
