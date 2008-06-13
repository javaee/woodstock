#!/bin/sh

# Script to scan a widget.js file, find the config properties,
# and format a JSON snippet for inclusion into a widget.json
# file.  Finds the widget's "name" by scanning for "_widgetType".
# Formats the following JSON layout:
#
# {name: "<_widgetType>",
#  type: "@JS_NAME@",
#  version: "@JS_VERSION@",
#  jmakeVersion: ${JM_VERSION},
#  description: "Woodstock <name> widget.",
#  args: [
#        "<name from @config>: {type: "<type from @config",
#                               description: "<desc from @config>",
#                               defaultValue: "<TBD?>"
#                              },
#  ... <repeated for each config parameter>
#
# Output is to stdout and can be redirected into a widget.json file.
#
# Command syntax:
#
# wsargs  <filename>
 
# Define constants affected by versions
JS_VERSION="4_3_p1"
JM_VERSION="1.8"

# Define contants for page configuration
DEBUG=true
NAMESPACE=woodstock
THEME_LOCALE=en
WEBUIAJAX=false
WEBUIALL=true

# Define constants for bootstrap script
UNCOMPRESS=""
if [ "${DEBUG}" = "true" ]; then
    UNCOMPRESS="_uncompressed"
fi
BOOTSTRAP="../resources/libs/woodstock${JS_VERSION}/suntheme/javascript${UNCOMPRESS}/bootstrap.js"

filename=$1
if [ "${filename}" = "" ]; then
    echo "Must specify a widget javascript file."
    exit 1
fi
if [ ! -f $filename ]; then
    echo "File ${filename} does not exist."
    exit 1
fi

# Get the widget type.
temp=`grep "_widgetType:" ${filename}`
if [ $? -ne 0 ]; then
    echo "Cannot find widget type..."
    temp='_widgetType: "WIDGET_TYPE"'
fi
temp=`echo ${temp} | cut -f2 -d" "`
widget=`echo ${temp} | sed -e "s@\"@@g"`

# Echo widget definition lines...
echo "{"
echo "    \"name\": \"${widget}\","
echo "    \"type\": \"@JS_NAME@\","
echo "    \"version\": \"@JS_VERSION@\","
echo "    \"jmakiVersion\": \"${JM_VERSION}\","
echo "    \"description\": \"Woodstock ${widget} widget.\","

# Echo args header line
echo "    \"args\": ["

# Read the JS file and get the @config lines
if [ -f /tmp/grep.tmp ]; then
    rm /tmp/grep.tmp > /dev/null &2>1
fi
grep "@config" ${filename} > /tmp/grep.tmp
numlines=`wc -l /tmp/grep.tmp`

# Process each config line to get name, type, and description
# Echo formatted output
count=0
cat /tmp/grep.tmp | while read line; do

    # Add a comma delimiter unless last line
    comma=","
    count=`expr $count + 1`
    if [ $count -eq $numlines ]; then
	comma=""
    fi

    # Got a config line.  Parse its content:
    temp=`echo "${line}" | cut -f3 -d" "`
    name=`echo "${line}" | cut -f4 -d" "`
    desc=`echo "${line}" | cut -f5- -d" "`

    type="UNKNOWN"
    dflt="\"\""
    if [ "${temp}" = "{String}" ]; then
	type="STRING"
	dflt="\"\""
    fi
    if [ "${temp}" = "{boolean}" ]; then
	type="BOOLEAN"
	dflt="false"
    fi
    if [ "${temp}" = "{int}" ]; then
	type="INT"
	dflt="1"
    fi
    if [ "${temp}" = "{Object}" ]; then
	type="OBJECT"
	dflt="{}"
    fi
    if [ "${temp}" = "{Array}" ]; then
	type="ARRAY"
	dflt="[]"
    fi

    echo "      {\"${name}\": {"
    echo "         \"type\": \"${type}\","
    echo "         \"description\": \"${desc}\","
    echo "         \"defaultValue\": ${dflt}}}${comma}"

done

# Echo remaining lines...
echo "    ],"
echo "    \"config\": {"
echo "      \"type\": {"
echo "        \"id\": \"@JS_NAME@\","
echo "        \"libs\": ["
echo "          \"@JS_PATH@/bootstrap.js\""
echo "        ],"
echo "        \"resources\": [ \"../resources/\" ]"
echo "        }"
echo "    }"
echo "}"

exit 0
