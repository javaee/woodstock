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
TOOLS_DIR=$SCRIPT_DIR/../../../themes/tools/javascript
TOOLS_JAR=$TOOLS_DIR/tools.jar
RHINO_JAR=$TOOLS_DIR/custom_rhino.jar
JSON_ZIP=$SRC_DIR/json.zip
JSON_JAR=$SCRIPT_DIR/json-2.jar
JSON_DIR=$CLASSES_DIR/META-INF/json
COMPRESSED_FILE=json.js
UNCOMPRESSED_FILE=json.js.uncompressed.js

# 
# Extract JSON sources. 
# 
cd $SRC_DIR 
unzip $JSON_ZIP
chmod -R 755 org
chmod 644 `find . -type f`

# 
# Compile Java classes. 
# 
mkdir $CLASSES_DIR 
cd $SCRIPT_DIR 
javac -d $CLASSES_DIR `find src -name \*.java`

#
# Copy file to compress.
#
mkdir -p $JSON_DIR
cp $SRC_DIR/$COMPRESSED_FILE $JSON_DIR/$COMPRESSED_FILE
cp $SRC_DIR/$COMPRESSED_FILE $JSON_DIR/$UNCOMPRESSED_FILE

#
# Compress jar on given JavaScript directory or file.
#
java -jar $TOOLS_JAR -compress -verbose -sourcePath $JSON_DIR/$COMPRESSED_FILE -rhinoJar $RHINO_JAR

#
# Add copyright
#
head -20 $SRC_DIR/$COMPRESSED_FILE > $JSON_DIR/$COMPRESSED_FILE.tmp
cat $JSON_DIR/$COMPRESSED_FILE >> $JSON_DIR/$COMPRESSED_FILE.tmp
mv $JSON_DIR/$COMPRESSED_FILE.tmp $JSON_DIR/$COMPRESSED_FILE

#
# Create jar.
#
cd $CLASSES_DIR
JUNK=`rm $JSON_JAR`
jar cvf $JSON_JAR *

#
# Clean
#
cd $SCRIPT_DIR
JUNK=`rm -rf $CLASSES_DIR`
JUNK=`rm -rf $SRC_DIR/org`
