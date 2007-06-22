#!/bin/sh

SCRIPT=`basename $0`
SCRIPT_DIR=`dirname $0`
SCRIPT_DIR=`cd $SCRIPT_DIR; pwd`

#
# Defaults
#
CLASSES_DIR=$SCRIPT_DIR/classes
MANIFEST_FILE=$SCRIPT_DIR/MANIFEST.tmp
COMPRESS_JAR=$SCRIPT_DIR/compress.jar
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
Main-Class: com.sun.webui.theme.javascript.Compress
EEOOFF

#
# Create jar.
#
JUNK=`rm $COMPRESS_JAR`
cd $CLASSES_DIR
jar cvfm $COMPRESS_JAR $MANIFEST_FILE *
rm $MANIFEST_FILE

#
# Test jar on given JavaScript directory or file.
#
if [ "$?" -eq 0 -a -n "$1" ]; then
    java -jar $COMPRESS_JAR -rhinoJar $RHINO_JAR -pathName $1 -verbose
fi
