#!/bin/sh

SCRIPT=`basename $0`
SCRIPT_DIR=`dirname $0`
SCRIPT_DIR=`cd $SCRIPT_DIR; pwd`

#
# Defaults
#
CLASSES_DIR=$SCRIPT_DIR/classes
MANIFEST_FILE=$SCRIPT_DIR/MANIFEST.tmp
TOOLS_JAR=$SCRIPT_DIR/tools.jar
RHINO_JAR=$SCRIPT_DIR/custom_rhino.jar

#
# Compile Java classes.
#
JUNK=`rm -rf $CLASSES_DIR`
mkdir $CLASSES_DIR
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
# Test jar on given JavaScript directory or file.
#
if [ "$?" -eq 0 ]; then
    if [ "$1" = "-compressJS" ]; then
        java -jar $TOOLS_JAR -compressJS -rhinoJar $RHINO_JAR -sourcePath $2 -verbose
    elif [ "$1" = "-embedTemplates" ]; then
        java -jar $TOOLS_JAR -embedTemplates -destPath $2 -sourcePath $3 -verbose
    fi
fi
