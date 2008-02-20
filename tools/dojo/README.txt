Building Dojo
-------------

To build dojo, run build.sh in this directory.

Customizing Dojo
----------------

The src/custom/util/buildscripts/profiles directory contains files used to 
create the custom Dojo build. In particular, the webui.profile.js file defines 
the resources for the resulting dojo.js and dnd.js files.

After the Dojo build completes, references to dojo, dijit, and djConfig are 
renamed for the webui.suntheme* name space. In order to perform this task 
properly, some JavaScript syntax must be overridden. Therefore, the src/custom 
directory hierarchy mimics the resources of the dojo-*-src.tar.gz file. When the
file is extracted to the build directory, any customized files are copied over
that. Custom modifications are kept to a minimum and marked with "Woodstock:" 
comments.

The resulting files are ultimately added to the themes module. During the theme 
build, the javascript/dojo/dojo.js file is used when combining JavaScript for 
the javascript/bootstrap.js file. The javascript/dojo/dnd.js file is used when 
combining JavaScript for the javascript/dnd.js file.

Resources
---------

See the following URL for instructions regarding custom Dojo builds.

http://www.dojotoolkit.org/book/dojo-book-0-9/part-4-meta-dojo/package-system-and-custom-builds

A source build of Dojo can be obtained below. The source builds are
suffixed with "-src".

http://download.dojotoolkit.org/release-1.0.2.

You can also download the latest code from the Subversion code
repository, see the Use Subversion page below. 

http://dojotoolkit.org/book/dojo-book-0-9/part-4-meta-dojo/get-code-subversion
