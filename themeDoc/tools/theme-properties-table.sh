#!/bin/sh

# The contents of this file are subject to the terms
# of the Common Development and Distribution License
# (the License).  You may not use this file except in
# compliance with the License.
#
# You can obtain a copy of the license at
# https://woodstock.dev.java.net/public/CDDLv1.0.html.
# See the License for the specific language governing
# permissions and limitations under the License.
#
# When distributing Covered Code, include this CDDL
# Header Notice in each file and include the License file
# at https://woodstock.dev.java.net/public/CDDLv1.0.html.
# If applicable, add the following below the CDDL Header,
# with the fields enclosed by brackets [] replaced by
# you own identifying information:
# "Portions Copyrighted [year] [name of copyright owner]"
#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
#

doctools=`pwd`
wdstk_edit=`pwd`/../../..

if [ ! -d ../../../woodstock ]; then
    echo "Can't find ../../../woodstock."
    exit -1
fi

case $# in
1) outfile=$1 ;;
*) echo "Usage: $0 <outfile>" ; exit 1 ;;
esac

msgkeys=$doctools/msgkeys
msgkeys_noclass=$doctools/msgkeys_noclass
imgkeys=$doctools/imgkeys
imgkeys_noclass=$doctools/imgkeys_noclass
csskeys=$doctools/csskeys
csskeys_noclass=$doctools/csskeys_noclass

theme_properties_table=$outfile

alljavafiles=$doctools/alljavafiles
component_properties=$doctools/component_properties

alljsfiles=$doctools/alljsfiles

msgs=$wdstk_edit/woodstock/themes/src/suntheme/messages/messages.properties
images=$wdstk_edit/woodstock/themes/src/suntheme/properties/images.properties
css=$wdstk_edit/woodstock/themes/src/suntheme/properties/styles.properties

clean() {
    rm -f $msgkeys $imgkeys $csskeys $alljavafiles $alljsfiles
    rm -f $component_properties
}

collectData() {

    find woodstock/webui/src/runtime -name \*.java -print > $alljavafiles
find woodstock/themes/src/suntheme/javascript -name \*.js -print > $alljsfiles

    nawk -f $doctools/properties-tool.nawk -v cmd=msgkeys $msgs > $msgkeys
    nawk -f $doctools/properties-tool.nawk -v cmd=imagekeys $images > $imgkeys
    nawk -f $doctools/properties-tool.nawk -v cmd=stylekeys $css > $csskeys

    sed -e 's/ThemeStyles\.//' $csskeys > $csskeys_noclass
    sed -e 's/ThemeImages\.//' $imgkeys > $imgkeys_noclass

}

clean

cd $wdstk_edit

collectData

putProperty() {

    componentfile=$1
    props=$2
    keys=$3
    tag=$4
    properties=$5

    # If there are no matches in componentfile then
    # just return
    #
    patternfile=$keys
    case $componentfile in
    *.js) partternfile=${keys}_noclass ;;
    esac
    /usr/xpg4/bin/grep -q -F -f $patternfile $componentfile
    if [ $? -ne 0 ]; then
	return 1
    fi

    for k in `cat $keys` ; do

	# Strip the class reference for js files
	# and for component_properties file
	#
	key=`echo $k | sed -e 's/ThemeStyles\.//' -e 's/ThemeImages\.//'`
	case $componentfile in
	*.js) k=$key ;;
	esac

	/usr/xpg4/bin/grep -q -F $k $componentfile
	if [ $? -ne 0 ]; then
	    continue
	fi
	echo "$tag" >> $properties

	echo "key $key" >> $properties
	value=`nawk -F'=' '$1 == "'$key'" { print $2 }' $props`
	echo "value $value" | sed -e 's/@THEME_CSS@//' \
		-e 's/@THEME@//' \
		-e 's?@THEME_PATH@/images/??' >> $properties

    done
    echo "$tag end" >> $properties
}

getProperties() {

    files=$1
    properties=$2
    type=$3

    for f in `cat $files` ; do

	## We probably don't need this any more since we are checking
	## for "ThemeStyles" and "ThemeImages" class references in java files.
	##
	#    for n in "jsf/renderkit/ajax" "HTMLAttributes" "HTMLElements" \
	#	"ThemeStyles" "ThemeImages" "JarTheme" \
	#	"ThemeContext" "ThemeJavascript" "ThemeELResolver" \
	#	"ThemeTemplates" "ConversionUtilities" \
	#	"DataProviderELResolver" "FacesMessageUtils" \
	#	"FormRenderer" "HelpUtils" "ListSelector" \
	#	"ResourceBundleTheme" "/Selector.java" \
	#	"TablePaginationActionListener" "TableSortActionListener" \
	#	"TypeConverter" "ValueType" "ValueTypeEvaluator" \
	#	"WidgetUtilities" "WizardStepListBase"  ; do
	#	echo $f | grep "$n" > /dev/null
	#	if [ $? -eq 0 ]; then
	#	    continue 2
	#	fi
	#    done

	comp=`basename $f`

	## Don't strip Renderer or BackingBean. This will imply
	## these "classes" reference the keys.
	## We will try and make the association to "components" later.
	#comp=`echo $comp | sed -e 's/Renderer\.java//' \
	#	-e 's/\.js$//' -e 's/\.java$//' \
	#	-e 's/BackingBean//'`
	#
	comp=`echo $comp | sed -e 's/\.js$//' -e 's/\.java$//'`

	echo "$type $comp" >> $properties

	putProperty $f $msgs $msgkeys "msg" $properties
	putProperty $f $images $imgkeys "img" $properties
	putProperty $f $css $csskeys "css" $properties

	echo "$type end" >> $properties

    done
}

getProperties $alljavafiles $component_properties "component"
getProperties $alljsfiles $component_properties "widget"

nawk -f $doctools/theme-properties-table.nawk \
	$component_properties > $theme_properties_table
