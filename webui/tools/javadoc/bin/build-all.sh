#!/bin/sh
# 
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
