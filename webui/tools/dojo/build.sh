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
DOJO_ZIP=$SRC_DIR/dojo-release-0.9.0.tar.gz
DOJO_JAR=$SCRIPT_DIR/dojo-0.9.0.jar
DOJO_DIR=$CLASSES_DIR/META-INF/dojo

# 
# Extract DOJO sources. 
#
mkdir -p `dirname $DOJO_DIR`
cp $DOJO_ZIP $CLASSES_DIR
cd $CLASSES_DIR
gunzip *.gz
tar xvf *.tar
rm *.tar
mv dojo* $DOJO_DIR

#
# Remove unnecessary files.
#
rm -rf $DOJO_DIR/util
rm -rf `find $DOJO_DIR -name tests\* -o -name demos\* -o -name bench\*`

#
# Create manifest.
#
cat > $MANIFEST_FILE <<- EEOOFF 
Created-By: Sun Microsystems Inc.
Specification-Title: Dojo
Specification-Version: 0.9.0
Specification-Vendor: dojotoolkit.org
EEOOFF

#
# Create jar.
#
JUNK=`rm $DOJO_JAR`
cd $CLASSES_DIR
jar cvfm $DOJO_JAR $MANIFEST_FILE *
rm $MANIFEST_FILE

#
# Clean
#
cd $SCRIPT_DIR
JUNK=`rm -rf $CLASSES_DIR`
