Please see:

    http://sites.google.com/site/glassfishconsole/updatingwoodstock

* Remember to change the version #'s in master/build.properties **AND**
  mvndeploy **AND** mvninstall

* Remember to update the doc/release-notes.html file

* Remember to tag the release!!!

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
