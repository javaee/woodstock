#!/bin/sh
#
# DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
#
# Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.
#
# The contents of this file are subject to the terms of either the GNU
# General Public License Version 2 only ("GPL") or the Common Development
# and Distribution License("CDDL") (collectively, the "License").  You
# may not use this file except in compliance with the License.  You can
# obtain a copy of the License at
# https://oss.oracle.com/licenses/CDDL+GPL-1.1
# or LICENSE.txt.  See the License for the specific
# language governing permissions and limitations under the License.
#
# When distributing the software, include this License Header Notice in each
# file and include the License file at LICENSE.txt.
#
# GPL Classpath Exception:
# Oracle designates this particular file as subject to the "Classpath"
# exception as provided by Oracle in the GPL Version 2 section of the License
# file that accompanied this code.
#
# Modifications:
# If applicable, add the following below the License Header, with the fields
# enclosed by brackets [] replaced by your own identifying information:
# "Portions Copyright [year] [name of copyright owner]"
#
# Contributor(s):
# If you wish your version of this file to be governed by only the CDDL or
# only the GPL Version 2, indicate your decision by adding "[Contributor]
# elects to include this software in this distribution under the [CDDL or GPL
# Version 2] license."  If you don't indicate a single choice of license, a
# recipient has the option to distribute your version of this file under
# either the CDDL, the GPL Version 2 or to extend the choice of license to
# its licensees as provided above.  However, if you add GPL Version 2 code
# and therefore, elected the GPL Version 2 license, then the option applies
# only if the new code is made subject to such option by the copyright
# holder.
#

PKGPREFIX="com.sun.webui.jsf"
PKGPATHPREFIX="com/sun/webui/jsf"
WEBUISRC="webui/runtime/src"

PATH="/usr/bin:${PATH}"
SCRIPT=`basename $0`
SCRIPT_DIR=`dirname $0`
SCRIPT_DIR=`cd $SCRIPT_DIR; pwd`

[ -z "$JAVA_HOME" ] && JAVA_HOME=/usr/java

BASE_DIR=$SCRIPT_DIR/..
DOCLET_PATH=$BASE_DIR/src
WS_DIR=$SCRIPT_DIR/../../../..

##
## Build Javadoc doclet
##
echo "\nBuilding tags package...\n"
cd $BASE_DIR/src/$PKGPATHPREFIX/doclets/standard/tags
javac -classpath $JAVA_HOME/lib/tools.jar:$DOCLET_PATH *.java

[ "$?" -ne 0 ] && exit
echo "\nBuilding standard package...\n"
cd $BASE_DIR/src/$PKGPATHPREFIX/doclets/standard
javac -classpath $JAVA_HOME/lib/tools.jar:$DOCLET_PATH *.java

[ "$?" -ne 0 ] && exit
echo "\nBuilding doclet package...\n"
cd $BASE_DIR/src/$PKGPATHPREFIX/doclets
javac -classpath $JAVA_HOME/lib/tools.jar:$DOCLET_PATH *.java

[ "$?" -ne 0 ] && exit
echo "\nBuilding doclets jar file\n"
cd $BASE_DIR/src
rm -rf doclets.jar
jar cvf $BASE_DIR/lib/doclets.jar `find . -name \*class -o -name \*properties`

##
## Clean up.
##
rm -rf `find . -name \*class`

exit

##
## Test Javadoc
##
CLASS_PATH="$WS_DIR/webui/classes/runtime/$PKGPATHPREFIX/component"

SOURCE_PATH="\
    $WS_DIR/$WEBUISRC/$PKGPATHPREFIX/component \
    $WS_DIR/webui/gen/component/$PKGPATHPREFIX/component"

FILES="\
    $WS_DIR/$WEBUISRC/$PKGPATHPREFIX/component/DropDown.java
    $WS_DIR/$WEBUISRC/$PKGPATHPREFIX/component/ListSelector.java
    $WS_DIR/$WEBUISRC/$PKGPATHPREFIX/component/Selector.java
    $WS_DIR/$WEBUISRC/$PKGPATHPREFIX/component/Table.java
    $WS_DIR/$WEBUISRC/$PKGPATHPREFIX/event/TableSelectPhaseListener.java
    $WS_DIR/webui/gen/component/$PKGPATHPREFIX/component/DropDownBase.java
    $WS_DIR/webui/gen/component/$PKGPATHPREFIX/component/ListSelectorBase.java
    $WS_DIR/webui/gen/component/$PKGPATHPREFIX/component/SelectorBase.java
    $WS_DIR/webui/gen/component/$PKGPATHPREFIX/component/TableBase.java"

echo "\nTesting Javadoc: see $BASE_DIR/test\n"
rm -rf $BASE_DIR/test
javadoc -classpath "$CLASS_PATH" -sourcepath "$SOURCE_PATH" $FILES \
    -docletpath $BASE_DIR/lib/doclets.jar -doclet $PKGPATH.doclets.standard.Standard \
    -d $BASE_DIR/test
