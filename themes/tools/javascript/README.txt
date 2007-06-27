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

  java -jar tools.jar -compressJS -verbose \
       -sourcePath <path> -rhinoJar custom_rhino.jar

References:

http://dojotoolkit.org/docs/shrinksafe
http://dojotoolkit.org/book/dojo-book-0-4/part-6-customizing-dojo-builds-better-performance/dojo-build-system

Embedding HTML templates
------------------------

With this change, we no longer generate requests for the HTML
templates. Currently, we generate two requests for each widget type
used in the page; one for the JS file and another for an HTML
template.

    http://smpt.east/~danl/webrev
    http://dan.east.sun.com:8081/testapp/faces/index.jsp

I originally planned to embed template strings in each JavaScript
file, but decided to use the theme name space. This was a little
easier to deal with during build-time. And these properties really
should be part of the theme, anyway.

I ran into a little trouble with the button because it uses three
templates; button.html, imageButton.html, and resetButton.html. Dojo
actually caches the templateString based on the widgetType
property. That means, the first button type rendered in the page wins
(e.g., I cannot create a reset button after rendering an image
button). I could attempt to reset a private Dojo variable, but I
thought that approach was fragile. Further, Dojo would not be able to
cache button templates because I would have to reset it for each new
instance.

In order for Dojo to continue caching templates, I created a new
resetButton and imageButton widget. These widgets simply inherit from
button, but allow me to define a default template string for each. As
a general rule, each template we create really should be mapped to a
widget. Hyperlink, for example, can still use the anchor
template. However, caching becomes an issue if more than one template
is supported per widget type.

Note that I was also able to override Dojo's buildRendering()
function, so we can ensure that the templatePath property takes
precedence over templateString. I'm doing this in widgetBase, so all
widgets inherit this functionality. This will allow web app developers
to specify their own custom templates.

That said, I basically ended up adding a simple property to each
widget file. For example, the staticText widget looks like so:

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.staticText, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.staticText.fillInTemplate,
    setProps: webui.@THEME@.widget.staticText.setProps,
    refresh: webui.@THEME@.widget.staticText.refresh.processEvent,
    getProps: webui.@THEME@.widget.staticText.getProps,

    // Set defaults.
    escape: true,
    templateString: webui.@THEME@.theme.getTemplateString("staticText"),
    widgetType: "staticText"
});

And after the build, the theme.js file looks like this:

//
// Note: The contents below are generated at build-time -- DO NOT EDIT!
//
webui.suntheme.theme.templateStrings =
{
    "alarm": "<span dojoAttachPoint=\"domNode\">...
    "checkboxGroup": "<div dojoAttachPoint=\"domNode\">...
    "button": "<input dojoAttachPoint=\"domNode\">...
    "table2": "<div dojoAttachPoint=\"domNode\">...
    ...
}

You'll also notice that the keys of template.properties have been
commented out. This allows the default template strings to be used in
the widgets. If you create a custom theme or just want to debug,
uncomment the key(s) you're interested in. The renderers should still
generate a templatePath property to override the default.

Usage:

  java -jar tools.jar -templateJS -verbose \
       -sourcePath <path> -destPath <path> -nameSpace <jsPath>
