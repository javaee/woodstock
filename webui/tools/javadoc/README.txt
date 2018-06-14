
DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.

The contents of this file are subject to the terms of either the GNU
General Public License Version 2 only ("GPL") or the Common Development
and Distribution License("CDDL") (collectively, the "License").  You
may not use this file except in compliance with the License.  You can
obtain a copy of the License at
https://oss.oracle.com/licenses/CDDL+GPL-1.1
or LICENSE.txt.  See the License for the specific
language governing permissions and limitations under the License.

When distributing the software, include this License Header Notice in each
file and include the License file at LICENSE.txt.

GPL Classpath Exception:
Oracle designates this particular file as subject to the "Classpath"
exception as provided by Oracle in the GPL Version 2 section of the License
file that accompanied this code.

Modifications:
If applicable, add the following below the License Header, with the fields
enclosed by brackets [] replaced by your own identifying information:
"Portions Copyright [year] [name of copyright owner]"

Contributor(s):
If you wish your version of this file to be governed by only the CDDL or
only the GPL Version 2, indicate your decision by adding "[Contributor]
elects to include this software in this distribution under the [CDDL or GPL
Version 2] license."  If you don't indicate a single choice of license, a
recipient has the option to distribute your version of this file under
either the CDDL, the GPL Version 2 or to extend the choice of license to
its licensees as provided above.  However, if you add GPL Version 2 code
and therefore, elected the GPL Version 2 license, then the option applies
only if the new code is made subject to such option by the copyright
holder.

6330200 - JAVADOC: Omit generated base classes from Javadoc

During a recent architecture meeting, the team agreed that the
generated base classes should be omitted from the Javadoc, if
possible. The theory is that we do not want to support developers
extending the base classes because extending from those classes will
not work with the associated renderer -- there are no interfaces here.

The problem with omitting the generated base classes from the Javadoc
is that they contain public component APIs. If the generated base
classes are simply omitted, developers will not be able to find
required documentation for the component. Likewise, all the TLD
documentation appears in the generated base classes as well. Thus,
that documentation would only be available via the TLD docs.

The proposed solution was to combine the Javadoc for the generated
base classes with the Javadoc for the component classes. For example,
the team would like to see all of the generated base classes methods
appear in the Javadoc for the component. Likewise, the TLD
documentation should appear in the component Javadoc. Further, there
should be no links to the Javadoc for the generated base
classes. Other than the class hierarchy, it should appear as if the
base classes do not exist at all. Although it's not easy, this can be
done via overriding the standard Java doclet used to generate the
Javadoc.

Basically, a doclet is a class or set of classes used to produce the
Javadoc. If you want to modify how the Javadoc is output, you must
provide your own doclet. For example, I created a doclet package,
named com.sun.webui.jsf.doclets, that overrides the following Java 1.4.2
classes. Typically, you only need to override Standard.java, but many
of the methods I needed to modify were package protected or
private. Thus, I ended up creating my own doclets package and
modifying source directly.

com.sun.tools.doclets.standard.AbstractSubWriter.java
com.sun.tools.doclets.standard.AllClassesFrameWriter.java
com.sun.tools.doclets.standard.ClassWriter.java
com.sun.tools.doclets.standard.HtmlStandardWriter.java
com.sun.tools.doclets.standard.PackageFrameWriter.java
com.sun.tools.doclets.standard.PackageWriter.java
com.sun.tools.doclets.VisibleMemberMap.java

Another problem with overriding the standard doclet is that these
classes are not supported. Sun provides the source, but only the
com.sun.javadoc package is actually supported. In fact, if you compare
the packages provided in the tools.jar file of 1.4.2, the classes were
completely removed from Java 1.5. The standard doclet appears to have
been completely rewritten.

That said, this is not a problem for us because these classes are not
deliverable. If we check in the doclets.jar file used to create the
doclets package, the build should compile no matter what version of
Java is used. I'll just modify the ant build to use doclets.jar as the
doclet path and bypass the standard doclet.

To verify, build the Javadoc and confirm that the APIs from the
generated base classes appear for the associated component. For
example, all the APIs for TableBase should appear in the Javadoc for
the com.sun.webui.jsf.component.Table class. Likewise, the component
description should be replaced with the TLD documentation.
