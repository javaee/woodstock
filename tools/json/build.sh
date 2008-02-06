#!/bin/sh

PATH=".:/usr/bin:$PATH"
SCRIPT=`basename $0`
SCRIPT_DIR=`dirname $0`
SCRIPT_DIR=`cd $SCRIPT_DIR; pwd`

#
# Resources loaded via ThemeServlet are cached using an "Expires" time
# stamp. In order for browsers to load new resources, without expiring
# or clearing the cache manually, a new package path is used. The
# following must be updated for new releases.
#
VERSION=2
VERSION_DIR=2_0

#
# Defaults
#
BUILD_DIR=$SCRIPT_DIR/build
SRC_DIR=$SCRIPT_DIR/src
LIB_DIR=$SCRIPT_DIR/lib
MANIFEST_FILE=$SCRIPT_DIR/MANIFEST.tmp
TOOLS_DIR=$SCRIPT_DIR/..
TOOLS_JAR=$TOOLS_DIR/woodstock/lib/webui-tools.jar
RHINO_JAR=$TOOLS_DIR/dojo/lib/custom_rhino.jar
JSON_ZIP=$SRC_DIR/json.zip
JSON_JAR=$LIB_DIR/json-$VERSION.jar
JSON_DIR=$BUILD_DIR/META-INF/json$VERSION_DIR
COMPRESSED_FILE=json.js
UNCOMPRESSED_FILE=json.js.uncompressed.js

#
# Clean
#
cd $SCRIPT_DIR
JUNK=`rm -rf $BUILD_DIR $SRC_DIR/org $JSON_JAR`

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
mkdir $BUILD_DIR 
cd $SCRIPT_DIR 
javac -d $BUILD_DIR `find src -name \*.java`

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
Specification-Version: $VERSION
Specification-Vendor: json.org
EEOOFF

#
# Create jar.
#
JUNK=`rm -f $JSON_JAR`
cd $BUILD_DIR
mkdir -p $LIB_DIR
jar cvfm $JSON_JAR $MANIFEST_FILE *
rm $MANIFEST_FILE
