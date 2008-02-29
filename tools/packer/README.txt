The Dean Edwards Packer tool is used to compress JavaScript with some
impressive compression ratios. In fact, it out compresses Dojo's
ShrinkSafe approach by more than half. The reason behind this is due
to the packer tool applying a base62 encoding. Unfortunately, this
means that the packer tool must also embed some of its own JavaScript
to decode files client-side. Even with the browser cache enabled,
resources are always decoded when JavaScript is evaluated.

For small files, like addRemove.js, this seems to be okay. (I have
observed that it only takes 39 milliseconds to unpack this particular
file.) However, for large JavaScript files, like boostrap.js and
webui.js, the performance is not acceptable. (These special files
combine the content of several other files in order to reduce the
number of JavaScript requests.) Using the packer tool requires an
additional 888 milliseconds (for both files) just to unpack the
JavaScript client-side. Therefore, an example which previously
took approximately .9 seconds to load, will now take 1.9 seconds.

That said, all is not lost. There is a flag we can use to turn the
base62 encoding off. Even with this feature turned off, the packer
tool still does a slightly better job than ShrinkSafe. For example,
ShrinkSafe reduces the webui.js file to 106kb, while the packer tool
reduces the same file to 91kb. Therefore, we can still use base62
encoding for small files, but turn it off for the larger, "combined"
files.

In the case of bootstrap.js and webui.js, gzip will be required to
reduce the overall size. (In the case of gzip resources, base62 will
not be used.) This approach is recommended for large files,
anyway. From Dean Edwards'  blog:

"The most efficient way to serve compressed JavaScript is to first run
your code through a JavaScript compressor that shrinks variable and
argument names, and then serve the resulting code using gzip
compression."

"Remember that base62 compression is only necessary if you can't use gzip."

Running Packer
--------------

To run Packer, use the following command.

java -jar custom_rhino.jar compressJs.js

where options include:

-base62 Pack files using base62 encoding.
-copyrightFile <copyrightFile> Path for copyright file.
-destDir <destDir> Directory where compressed files are written.
-fileList <f0,...,fn> A comma separated list of relative file paths.
-packerDir <packerDir> Directory where packer resources are located.
-shrink Shorten long variable names.
-sourceDir <sourceDir> Directory containing the files in fileList.
-verbose Enable verbose output.

Resources
---------

See "Notes on JavaScript Compression" at:

http://dean.edwards.name/weblog
