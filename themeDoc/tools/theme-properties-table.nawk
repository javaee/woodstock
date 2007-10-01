
# The contents of this file are subject to the terms
# of the Common Development and Distribution License
# (the License).  You may not use this file except in
# compliance with the License.
#
# You can obtain a copy of the license at
# https://woodstock.dev.java.net/public/CDDLv1.0.html.
# See the License for the specific language governing
# permissions and limitations under the License.
#
# When distributing Covered Code, include this CDDL
# Header Notice in each file and include the License file
# at https://woodstock.dev.java.net/public/CDDLv1.0.html.
# If applicable, add the following below the CDDL Header,
# with the fields enclosed by brackets [] replaced by
# you own identifying information:
# "Portions Copyrighted [year] [name of copyright owner]"
#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
#
BEGIN { incomponent = 0;
	inmsg = 0;
	inimg = 0;
	incss = 0;
}

$1 == "component" { component = $2; components[component] = component; }
$1 == "widget" { component = $2; widgets[component] = component; }
$1 == "msg" { inmsg = 1; }
$1 == "css" { incss = 1; }
$1 == "img" { inimg = 1; }
$1 == "key" { key = $2; }

$1 == "value" { 
    value = substr($0, 6);
    if (inmsg) {
	messages[component,key] = value;
    } else 
    if (inimg) {
	images[component,key] = value;
    } else 
    if (incss) {
	css[component,key] = value;
    }
}

$2 == "end" { 
    if (inmsg) {
	inmsg = 0;
    } else 
    if (inimg) {
	inimg = 0;
    } else 
    if (incss) {
	incss = 0;
    } else
    if (incomponent) {
	incomponent = 0;
    }
}

    # return 0 if s1 == s2
    # return 1 if s1 > s2
    # return -1 if s2 < s2
    #
    function strcmp(s1, s2) {
	l1 = length(s1);
	l2 = length(s2);

	if (l1 < l2) {
	    slen = l1;
	} else {
	    slen = l2;
	}

	for (i = 1; i < slen; ++i) {
	    c1 = substr(s1, i, 1);
	    c2 = substr(s2, i, 1);
	    if (c1 == c2) {
		continue;
	    }
	    if (c1 < c2) {
		return -1;
	    } else {
		return 1;
	    }
	}

	# slen chars are equal
	#
	if (l1 == l2) {
	    return 0;
	} else if (l1 < l2) {
	    return -1;
	} else {
	    return 1;
	}
    }
    function bubbleSort(a, n) {
	for (k = 0; k < n; ++k) {
	    for (l = 0; l < n - k; l++) {
		if (strcmp(a[l+1], a[l]) <  0) {
		    tmp = a[l];
		    a[l] = a[l+1];
		    a[l+1] = tmp;
		}
	    }
	}
	return a;
    }

    function getRow(k, v, r, n) {
	cell = "\t<td style=\"vertical-align: top;\">%s</td>";
	row = "    <tr>\n" cell "\n" cell "\n    </tr>\n";
	r[n] = sprintf(row, k, v);
    }

    function getResourceRows(resource, component, rows) {
	keycount = 0;
	for (indice in resource) {
	    split(indice, indiceArray, SUBSEP);
	    if (component != indiceArray[1]) {
		continue
	    }
	    # if components match count and save the keys
	    #
	    sortedKeys[keycount + 1] = indiceArray[2];
	    debugOut("indiceArray[2] = " indiceArray[2]);
	    keycount++;
	}
	if (keycount == 0) {
	    return null;
	}
	bubbleSort(sortedKeys, keycount);
	for (i = 1; i <= keycount; i++) {
	    key = sortedKeys[i];
	    value = resource[component, key];
	    getRow(key, value, rows, i);
	}
	return keycount;
    }

    function printCopyright() {

print "<!--"
print " The contents of this file are subject to the terms"
print " of the Common Development and Distribution License"
print " (the License).  You may not use this file except in"
print " compliance with the License."
print ""
print " You can obtain a copy of the license at"
print " https://woodstock.dev.java.net/public/CDDLv1.0.html."
print " See the License for the specific language governing"
print " permissions and limitations under the License."
print ""
print " When distributing Covered Code, include this CDDL"
print " Header Notice in each file and include the License file"
print " at https://woodstock.dev.java.net/public/CDDLv1.0.html."
print " If applicable, add the following below the CDDL Header,"
print " with the fields enclosed by brackets [] replaced by"
print " you own identifying information:"
print " \"Portions Copyrighted [year] [name of copyright owner]\""
print ""
print "Copyright 2007 Sun Microsystems, Inc. All rights reserved."
print "-->"

    }

    function printSectionHeader(section) {

print "    <tr>";
print "      <th style=\"vertical-align: top;\"><big>" section "</big>";
print "      </th>";
print "      <th style=\"vertical-align: top;\"><big>Key</big>";
print "      </th>";
print "      <th style=\"vertical-align: top;\"><big>Value</big>";
print "      </th>";
print "    </tr>";

    }

    function printDocHeader() {

print "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">";
print "<html>";
print "<head>";
print "  <meta content=\"text/html; charset=ISO-8859-1\"";
print " http-equiv=\"content-type\">";
print "  <title>theme-properties</title>";
print "</head>";
print "<body>";
print "<table style=\"width: 100%; text-align: left;\" border=\"1\" cellpadding=\"2\"";
print " cellspacing=\"2\">";
print "  <tbody>";

    }

    function printComponentRows(comps, howmany) {
	# For each resource get the keys associated 
	# with the component
	#
	for (n = 1; n <= howmany; n++) {

	    msgCount = 0;
	    imgCount = 0;
	    cssCount = 0;

	    component = comps[n];

	    debugOut("component = " component);

	    msgRows[1] = "";
	    msgCount = getResourceRows(messages, component, msgRows);

	    debugOut("msgCount = " msgCount);

	    imgRows[1] = "";
	    imgCount = getResourceRows(images, component, imgRows);
	    
	    debugOut("imgCount = " imgCount);

	    cssRows[1] = "";
	    cssCount = getResourceRows(css, component, cssRows);

	    debugOut("cssCount = " cssCount);

	    totalRows = msgCount + imgCount + cssCount;
	    if (totalRows == 0) {
		continue;
	    }

	    haveMsg = msgCount != 0 ? 1 : 0;
	    haveImg = imgCount != 0 ? 1 : 0;
	    haveCss = cssCount != 0 ? 1 : 0;

	    if (haveMsg) {
		header = "Messages";
	    } else 
	    if (haveImg) {
		header = "Images";
	    } else {
		header = "Styles";
	    }
	    totalRows += (haveMsg + haveImg + haveCss);

	    printHeader(header, component, totalRows);
	    if (haveMsg) {
		printRows(null, msgRows, msgCount);
	    }
	    if (haveImg) {
		header = haveMsg ? "Images" : null;
		printRows(header, imgRows, imgCount);
	    }
	    if (haveCss) {
		header = haveMsg || haveImg ? "Styles" : null;
		printRows(header, cssRows, cssCount);
	    }
	}
    }
    function printDocFooter() {
	print "  </tbody>";
	print "</table>";
	print "</body>";
	print "</html>";
    }

    function printResourceRow(r) {
    print "    <tr>";
    print "      <th colspan=\"2\" style=\"vertical-align: top;\">" r "</th>";
    print "    </tr>";
    }

    function printRows(s, rows, count) {
	if (s != null) {
	    printResourceRow(s);
	}
	for (i = 1; i <= count; i++) {
	    print rows[i];
	}
    }

    function printHeader(s, comp, nrows) {
        fmt = "    <tr>\n"\
	  "      <td style=\"vertical-align: top;\" rowspan=\"%d\">%s</td>\n"\
	  "      <th colspan=\"2\" style=\"vertical-align: top;\">%s</th>\n"\
	  "    </tr>\n";
	print sprintf(fmt, nrows, comp, s);
    }

    function debugOut(s) {
	print s >> "/tmp/debugOut"
    }

END { 

    printCopyright();
    printDocHeader();

    # count the components
    #
    count = 0;
    for (component in components) {
	sortedComponents[count + 1] = component;
	count++;
    }
    # sort components
    #
    bubbleSort(sortedComponents, count);

    printSectionHeader("Component/Renderer");

    printComponentRows(sortedComponents, count);

    # count the widgets
    #
    widgetcount = 0;
    for (widget in widgets) {
	sortedWidgets[widgetcount + 1] = widget;
	widgetcount++;
    }
    # sort widgets
    #
    bubbleSort(sortedWidgets, widgetcount);

    printSectionHeader("Javascript Widget");

    printComponentRows(sortedWidgets, widgetcount);

    printDocFooter();
}
