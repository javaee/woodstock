Building the tools.jar
----------------------

To build the tools.jar, run the build.sh script in this directory.

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

References:

http://alex.dojotoolkit.org/shrinksafe
http://dojotoolkit.org/docs/shrinksafe
http://dojotoolkit.org/book/dojo-book-0-4/part-6-customizing-dojo-builds-better-performance/dojo-build-system
