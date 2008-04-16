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
VERSION=1.0.2
VERSION_DIR=1_0_2

#
# Defaults
#
BUILD_DIR=$SCRIPT_DIR/build
SRC_DIR=$SCRIPT_DIR/src
THEMES_DIR=$SCRIPT_DIR/../../themes/src/suntheme
DOJO_ZIP=$SRC_DIR/dojo-release-$VERSION-src.tar.gz
DOJO_DIR=$BUILD_DIR/dojo-release-$VERSION-src
DOJO_BUILD_DIR=$DOJO_DIR/util/buildscripts
DOJO_RELEASE_DIR=$DOJO_DIR/release

#
# Clean.
#
cd $SCRIPT_DIR
JUNK=`rm -rf $BUILD_DIR`

# 
# Extract DOJO sources. 
#
mkdir -p $BUILD_DIR
cp $DOJO_ZIP $BUILD_DIR
cd $BUILD_DIR
gunzip *.gz
tar xvf *.tar
rm *.tar

#
# Copy custom sources.
#
cd $SRC_DIR/custom
find . -name CVS -prune -o -print | cpio -dumpv $DOJO_DIR

#
# Create the dojo.js and dnd.js files. The dojo.js file shall be used as a base file
# for webui.js. The dnd.js file shall be used as a base file for Woodstock's dnd.js file.
#
cd $DOJO_BUILD_DIR
build.sh profile=webui action=release version=$VERSION \
copyTests=false internStrings=false
#layerOptimize=packer

#
# Copy custom resources.
#
mkdir -p $BUILD_DIR/javascript/_dojo
cp $DOJO_RELEASE_DIR/dojo/dojo/dojo.js.uncompressed.js $BUILD_DIR/javascript/_dojo/dojo.js
cp $DOJO_RELEASE_DIR/dojo/dojo/dnd.js.uncompressed.js $BUILD_DIR/javascript/_dojo/dnd.js

#
# Copy firebug resources.
#
cd $DOJO_RELEASE_DIR/dojo/dojo
find _firebug -name \*.png -prune -o -print | cpio -dumpv $BUILD_DIR/javascript/_dojo

#
# Clean.
#
cd $SCRIPT_DIR
JUNK=`rm -rf $DOJO_DIR`

#
# Modify the djConfig variable.
#
OLD=djConfig
NEW=@JS_NS@\\._base\\.config\\._djConfig
for F in `find $BUILD_DIR/javascript -name \*.js`
do
    sed -e "s|$OLD\.|$NEW\.|g" \
        -e "s|$OLD\[|$NEW\[|g" \
        -e "s|$OLD =|$NEW =|g" \
        -e "s|$OLD !=|$NEW !=|g" $F > $F.tmp

    mv $F.tmp $F
done

#
# Modify the dojo name space.
#
OLD=dojo
NEW=@JS_NS@\\._dojo
for F in `find $BUILD_DIR/javascript -name \*.js`
do
    sed -e "s|$OLD\.|$NEW\.|g" \
        -e "s|$OLD\[|$NEW\[|g" \
        -e "s|$OLD =|$NEW =|g" \
        -e "s|$OLD\"|$NEW\"|g" \
        -e "s|$OLD)|$NEW)|g" \
        -e "s|$OLD;|$NEW;|g" \
        -e "s|$OLD,|$NEW,|g" $F > $F.tmp

    mv $F.tmp $F
done

#
# Modify the dijit name space.
#
OLD=dijit
NEW=@JS_NS@\\._dijit
for F in `find $BUILD_DIR/javascript -name \*.js`
do
    sed -e "s|$OLD\.|$NEW\.|g" \
        -e "s|$OLD,|$NEW,|g" $F > $F.tmp

    mv $F.tmp $F
done

#
# Update themes module.
#
cd $BUILD_DIR
find . | cpio -dumpv $THEMES_DIR
