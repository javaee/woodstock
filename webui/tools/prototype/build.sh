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
PROTOTYPE_JAR=$SCRIPT_DIR/prototype-1.5.0.jar
PROTOTYPE_DIR=$CLASSES_DIR/META-INF/prototype
COMPRESSED_FILE=prototype.js
UNCOMPRESSED_FILE=prototype.js.uncompressed.js

#
# Copy file to compress.
#
mkdir -p $PROTOTYPE_DIR
cp $SRC_DIR/$COMPRESSED_FILE $PROTOTYPE_DIR/$COMPRESSED_FILE
cp $SRC_DIR/$COMPRESSED_FILE $PROTOTYPE_DIR/$UNCOMPRESSED_FILE

#
# Compress jar on given JavaScript directory or file.
#
java -jar $TOOLS_JAR -compressJS -verbose \
     -sourcePath $PROTOTYPE_DIR/$COMPRESSED_FILE -rhinoJar $RHINO_JAR

#
# Add copyright
#
head -7 $SRC_DIR/$COMPRESSED_FILE > $PROTOTYPE_DIR/$COMPRESSED_FILE.tmp
cat $PROTOTYPE_DIR/$COMPRESSED_FILE >> $PROTOTYPE_DIR/$COMPRESSED_FILE.tmp
mv $PROTOTYPE_DIR/$COMPRESSED_FILE.tmp $PROTOTYPE_DIR/$COMPRESSED_FILE

#
# Create manifest.
#
cat > $MANIFEST_FILE <<- EEOOFF 
Created-By: Sun Microsystems Inc.
Specification-Title: Prototype
Specification-Version: 1.5.0_rc1
Specification-Vendor: prototypejs.org
EEOOFF

#
# Create jar.
#
JUNK=`rm -f $PROTOTYPE_JAR`
cd $CLASSES_DIR
jar cvfm $PROTOTYPE_JAR $MANIFEST_FILE *
rm $MANIFEST_FILE

#
# Clean
#
cd $SCRIPT_DIR
JUNK=`rm -rf $CLASSES_DIR`

