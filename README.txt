#### :warning:This project is now part of the EE4J initiative. This repository has been archived as all activities are now happening in the [corresponding Eclipse repository](http://link_to_repo). See [here](https://www.eclipse.org/ee4j/status.php) for the overall EE4J transition status.
 
---
 
Please see:

    http://aseng-wiki.us.oracle.com/asengwiki/display/GlassFish/WoodstockUpdate 
    for detail instruction on how to update and publish woodstock jars.

* To build:  
	SET the ENV property ANT_OPTS with HTTP Proxy and HTTP Port

	export ANT_OPTS="-Dhttp.proxyHost=<HTTP_PROXY_VALUE> -Dhttp.proxyPort=<HTTP_PORT_VALUE>"

	cd master; ant clean; ant;  The jars will be available at master/build/ship/lib directory.

* BEFORE Running build-bundles to create the webui-jsf-bundle.jar and webui-jsf-suntheme-bundle.jar for pushing using Nexus UI, do the following:

     * Remember to change the version #'s in master/build.properties **AND**  build-bundles

     * Remember to update the doc/release-notes.html file

* Remember to tag the release!!!
(eg. "svn copy https://svn.java.net/svn/woodstock~svn/branches/Woodstock_402_Branch https://svn.java.net/svn/woodstock~svn/tags/4.0.2.8")

* Remember to document any process changes!!!

* You MUST use Maven 2.x.  Maven 3 will not work.

Troubleshooting:

* mvn may not work...
  1) Try making sure your mvn version (esp. on Ubuntu) is current.
  2) Try (re)moving your ~/.subversion directory
  3) Make sure you have a valid ~/.java.net file with your username / password:
    vim ~/.java.net
    userName=yourJavaNetLogin
    password=yourPassword
    //save the file and apply permission 600 to restrict the access:
    chmod 600 ~/.java.net
  4) If all else fails, ask for help!
