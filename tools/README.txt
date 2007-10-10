Building the tools.jar
----------------------

To build the tools.jar, run "ant" in this directory.

JavaScript compression
----------------------

I've Added the Rhino compress tool, used by Dojo, to our own build. It
slows the theme build a bit, but it speeds up downloads by decreasing
the size of our JavaScript library more than half. Currently, the entire library
ways in at approximately 700k and the tool can reduce that to about 250k. 
Combined with the lazy jsfx downloads and removal of duplicate code, performance
is noticeably getting better.

The Rhino tool compacts the source code by removing white space,
comments, replacement of identifiers with short identifiers, and
removal of extra semicolons or other semantically equivalent
changes. The resulting code is unreadable, but we cannot view
JavaScript files without using the debug mode anyway. When debug=true,
a different module path is used (i.e.,
.../com/sun/webui/jsf/suntheme/javascript_uncompressed). This will
allow us to debug readable files just as we normally do.

Regarding the build.xml file of the theme module, I wasn't able to use
ANT to iterate over file lists. However, I created a small Java
program to iterate over files myself. The Rhino compress tool runs out
of memory when attempting to compress multiple files (~8 or more) --
likely due to parsing long variable names. Therefore, I had to exec
the program in a new VM for each compressed file. Not very efficient,
but it gets the job done.

Note: Web app developers will not notice any changes in the
JavaScript and debug mode is nothing new. Therefore, the web app
should run as normal. Hopefully, just a little quicker. ;) 

Usage:

  java -jar tools.jar -compress -verbose \
       -sourcePath <path> -rhinoJar custom_rhino.jar

Options:

-rhinoJar       Jar file containing the Rhino compression tool.
-sourcePath     JavaScript directory or file to compress.
-verbose        Enable verbose output.

References:

http://alex.dojotoolkit.org/shrinksafe
http://dojotoolkit.org/docs/shrinksafe
http://dojotoolkit.org/book/dojo-book-0-4/part-6-customizing-dojo-builds-better-performance/dojo-build-system

Combining JavaScript files
--------------------------

This feature combines a JavaScript directory or file.

Usage:

  java -jar tools.jar -combineJavaScript -verbose \
       -sourcePath <path> -modulePath <path> -modulePrefix webui.suntheme -combinedFile <path>

Options:

-combinedFile   File path for combined output.
-modulePath     The path to locate module sources.
-modulePrefix   The JavaScript prefix for module sources.
-sourcePath     JavaScript directory or file to combine.
-verbose        Enable verbose output.

Combining CSS files
-------------------

This feature combines a CSS directory or file.

Usage:

  java -jar tools.jar -combineCSS -verbose \
       -sourcePath <path> -modulePath <path> -combinedFile <path>

Options:

-combinedFile   File path for combined output.
-modulePath     The path to locate module sources.
-sourcePath     JavaScript directory or file to combine.
-verbose        Enable verbose output.

Converting natively encoded properties files
--------------------------------------------

Use the "-native2ascii" option to convert a file containing
natively encoded strings to unicode escapes. This is just a wrapper
for the JDK's "native2ascii" executable for use by the themes build.xml
file.

  java -jar tools.jar -native2ascii -verbose \
       -sourceDir <directory containing the files of fileList> \
       -destDir <converted files are place here> \
       -fileList <comma separated list of files relative to sourceDir to convert> \
	[-encoding <the native encoding (UTF-8 is the default)>] \
	[-reverse]

Options for -native2ascii include:
-sourceDir <sourceDir>  Directory containin files in fileList.
-destDir <destDir>      Directory where converted files are written.
-fileList <f0,...,fn>   A comma separated list of files to convert.
[-encoding <encoding>]  The encoding to convert from or to.UTF-8 is the default
encoding
[-reverse]      Convert fileList to encoding

