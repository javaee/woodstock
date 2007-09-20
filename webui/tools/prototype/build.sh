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
java -jar $TOOLS_JAR -compress -verbose -sourcePath $PROTOTYPE_DIR/$COMPRESSED_FILE -rhinoJar $RHINO_JAR

#
# Add copyright
#
head -7 $SRC_DIR/$COMPRESSED_FILE > $PROTOTYPE_DIR/$COMPRESSED_FILE.tmp
cat $PROTOTYPE_DIR/$COMPRESSED_FILE >> $PROTOTYPE_DIR/$COMPRESSED_FILE.tmp
mv $PROTOTYPE_DIR/$COMPRESSED_FILE.tmp $PROTOTYPE_DIR/$COMPRESSED_FILE

#
# Create jar.
#
cd $CLASSES_DIR
JUNK=`rm $PROTOTYPE_JAR`
jar cvf $PROTOTYPE_JAR *

#
# Clean
#
cd $SCRIPT_DIR
JUNK=`rm -rf $CLASSES_DIR`
