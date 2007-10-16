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
TOOLS_DIR=$SCRIPT_DIR/../../../tools
TOOLS_JAR=$TOOLS_DIR/lib/woodstock-tools.jar
RHINO_JAR=$TOOLS_DIR/lib/custom_rhino.jar
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
cp $SRC_DIR/$COMPRESSED_FILE $JSON_DIR/$UNCOMPRESSED_FILE

#
# Compress jar on given JavaScript directory or file.
#
java -jar $TOOLS_JAR -compressJS \
     -verbose \
     -sourceDir $SRC_DIR \
     -fileList $COMPRESSED_FILE \
     -destDir $JSON_DIR \
     -rhinoJar $RHINO_JAR \

#
# Add copyright
#
head -20 $JSON_DIR/$UNCOMPRESSED_FILE > $JSON_DIR/$COMPRESSED_FILE.tmp
cat $JSON_DIR/$COMPRESSED_FILE >> $JSON_DIR/$COMPRESSED_FILE.tmp
mv $JSON_DIR/$COMPRESSED_FILE.tmp $JSON_DIR/$COMPRESSED_FILE

#
# Create manifest.
#
cat > $MANIFEST_FILE <<- EEOOFF 
Created-By: Sun Microsystems Inc.
Specification-Title: JSON
Specification-Version: 2
Specification-Vendor: json.org
EEOOFF

#
# Create jar.
#
JUNK=`rm -f $JSON_JAR`
cd $CLASSES_DIR
jar cvfm $JSON_JAR $MANIFEST_FILE *
rm $MANIFEST_FILE

#
# Clean
#
cd $SCRIPT_DIR
JUNK=`rm -rf $CLASSES_DIR`
JUNK=`rm -rf $SRC_DIR/org`
