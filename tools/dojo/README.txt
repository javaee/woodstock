Building Dojo
-------------

To build dojo, run build.sh in this directory.

Customizing Dojo
----------------

The src/custom/util/buildscripts/profiles directory contains two files
used to create the custom Dojo build. The webui.profile.js file is
used to create the dojo.js and dojo-dnd.js files. And the
webui-all.profile.js file is used to create dojo-all.js. Since the
Dojo build only runs one profile at a time, the build is run twice.
Once using the webui.profile.js file and again with the
webui-all.profile.js file. 

After the Dojo build completes, references to dojo, dijit, and
djConfig are renamed for the webui.suntheme* name space. In order to
perform this task properly, some JavaScript syntax must be
overridden. Therefore, the src/custom directory hierarchy mimics the
resources of the dojo zip file. When the zip file is extracted to the
build directory, a select few customize files are copied over
that. Custom modifications should be kept to a minimum and marked with
"// Woodstock: " comments.

The resulting files are ultimately added to the appropriate themes
directory. During the theme build, the dojo.js file is used as base
code when combining JavaScript for the webui.js and webui-jsfx.js
files. Next, the dojo-all.js file is used as base code when combining
JavaScript for the webui-all.js and webui-jsfx-all.js files. Finally,
the dojo-dnd.js file is used as base code for the dnd.js file. All
this helps reduce the number of requests for each page.

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
