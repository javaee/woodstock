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
# 
# want to create
#
# <key> : <messages>
#
BEGIN { FS = "=" }

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

    # remove extra single quotes
    #
    function unescape(s) {

	newstr = "";
	oldstr = s;
	do {

	    qptr = index(oldstr, "'");
	    if (qptr == 0) {
		newstr = newstr oldstr;
		return newstr;
	    }

	    # get the string excluding to the first quote
	    #
	    newstr = newstr substr(oldstr, 1, qptr - 1);

	    # Move the pointer past the quote
	    # and see if we are escaping a quote character.
	    ++qptr;
	    oldstr = substr(oldstr, qptr);  
	    q2 = substr(oldstr, 1, 1);
	    if (q2 == "'") {
		oldstr = substr(oldstr, 2);
		newstr = newstr "'";
	    }

	} while (1);

    }

$0 ~ /^[ 	]*$/ { next; }
$0 ~ /^[ 	]*#+.*[ 	]*$/ { next; }
$2 ~ /^[ 	]*$/ { next; }
{
    #messages[key] = message

    # If we want to extract a component prefix
    #
    #n = split($1, keysplit, ".");

    # get rid of trailing spaces
    #
    split($1, buf, " ");
    key = buf[1];
    messages[key] = unescape($2);
    keys[key] = key;
}

    function getPrefix(s) {
	n = split(s, segments, ".");
	return segments[1];
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

END {

    count = 0;
    for (key in keys) {
	sortedKeys[count + 1] = keys[key];
	count++;
    }
    #
    # -v cmd=prefix
    #
    if (cmd == "prefix") {
	for (k = 1; k <= count; k++) {
	    component = getPrefix(sortedKeys[k]);
	    components[component] = component;
	}
	ccount = 0;
	for (component in components) {
	    sortedComponents[ccount + 1] = components[component];
	    ccount++;
	}
	bubbleSort(sortedComponents, ccount);
	for (k = 1; k <= ccount; k++) {
	    print sortedComponents[k];
	}
	exit(0);
    }

    # Sort the keys
    #
    bubbleSort(sortedKeys, count);

    #print "\n";
    #print "webui.@THEME@.theme.messages = {" ;


    #
    # -v cmd=keys
    #
    if (cmd == "stylekeys" || cmd == "msgkeys" || cmd == "imagekeys") {
	for (k = 1; k <= count; k++) {
	    if (cmd == "imagekeys") {
		prefix = "ThemeImages.";
		n = split(sortedKeys[k], segs, "_");
		if (n > 1 && (segs[n] == "ALT" || segs[n] == "WIDTH" || 
			segs[n] == "HEIGHT" || segs[n] == "TITLE")) {
		    continue;
		}
	    } else 
	    if (cmd == "stylekeys") {
		prefix = "ThemeStyles.";
	    } else {
		prefix = "";
	    }
	    print prefix  sortedKeys[k];
	}
	exit(0);
    }

    for (i = 1; i <= count; ++i) {
	key = sortedKeys[i];
	if (messages[key] == "") {
	    ++i;
	    continue;
	}
	statement = "    " key ": \"" messages[key] "\"" ;

	if (i < count) {
	    statement = statement ",";
	}

	print statement;
    }
    print "};";
}
