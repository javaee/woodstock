#!/bin/sh

PATH="/usr/bin:$PATH"
SCRIPT=`basename $0`
SCRIPT_DIR=`dirname $0`
SCRIPT_DIR=`cd $SCRIPT_DIR; pwd`

#
# Defaults
#
CLASSES_DIR=$SCRIPT_DIR/classes
SRC_DIR=$SCRIPT_DIR/src
MANIFEST_FILE=$SCRIPT_DIR/MANIFEST.tmp
TOOLS_JAR=$SCRIPT_DIR/tools.jar
RHINO_JAR=$SCRIPT_DIR/custom_rhino.jar
JSON_JAR=$SCRIPT_DIR/../../../webui/tools/json/json.zip

#
# Extract JSON sources.
#
cd $SRC_DIR
unzip $JSON_JAR
chmod -R 755 org
chmod 644 `find . -type f`

#
# Compile Java classes.
#
mkdir $CLASSES_DIR
cd $SCRIPT_DIR
javac -d $CLASSES_DIR `find src -name \*.java`

#
# Create manifest.
#
cat > $MANIFEST_FILE <<- EEOOFF 
Main-Class: com.sun.webui.tools.Main
EEOOFF

#
# Create jar.
#
JUNK=`rm $TOOLS_JAR`
cd $CLASSES_DIR
jar cvfm $TOOLS_JAR $MANIFEST_FILE *
rm $MANIFEST_FILE

#
# Clean
#
cd $SCRIPT_DIR
JUNK=`rm -rf $CLASSES_DIR`
JUNK=`rm -rf $SRC_DIR/org`

#
# Test jar on given JavaScript directory or file.
#
if [ "$?" -eq 0 ]; then
    if [ "$1" = "-compressJS" ]; then
        java -jar $TOOLS_JAR -compressJS -verbose -sourcePath $2 -rhinoJar $RHINO_JAR
    elif [ "$1" = "-templateJS" ]; then
        java -jar $TOOLS_JAR -templateJS -verbose -sourcePath $2 -destPath $3 -nameSpace $4
    fi
fi
