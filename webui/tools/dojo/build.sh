#!/bin/sh

PATH="/usr/bin:$PATH"
SCRIPT=`basename $0`
SCRIPT_DIR=`dirname $0`
SCRIPT_DIR=`cd $SCRIPT_DIR; pwd`

#
# Resources loaded via ThemeServlet are cached using an "Expires" time
# stamp. In order for browsers to load new resources, without expiring
# or clearing the cache manually, a new package path is used. The
# following must be updated for new releases.
#
VERSION=1.0.1
VERSION_DIR=1_0_1

#
# Defaults
#
CLASSES_DIR=$SCRIPT_DIR/classes
SRC_DIR=$SCRIPT_DIR/src
MANIFEST_FILE=$SCRIPT_DIR/MANIFEST.tmp
DOJO_ZIP=$SRC_DIR/dojo-release-$VERSION.tar.gz
DOJO_JAR=$SCRIPT_DIR/dojo-$VERSION.jar
DOJO_DIR=$CLASSES_DIR/META-INF/dojo$VERSION_DIR

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
Specification-Version: $VERSION
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
