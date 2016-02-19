You can include a following maven dependency:
```
<repository>
    <id>formbuilder.repo</id>
    <name>Swing Form Builder Repository</name>
    <url>http://swing-formbuilder.googlecode.com/svn/maven-repository/releases/</url>
    <releases>
        <enabled>true</enabled>
        <updatePolicy>never</updatePolicy>
    </releases>
    <snapshots>
        <enabled>false</enabled>
    </snapshots>
</repository>
...
<dependency>
    <groupId>org.formbuilder</groupId>
    <artifactId>formbuilder-main</artifactId>
    <version>1.1</version>
</dependency>
```

Or directly download

  * ## [binaries](http://swing-formbuilder.googlecode.com/svn/maven-repository/releases/org/formbuilder/formbuilder-main/1.1/formbuilder-main-1.1.jar) ##
  * ## [sources](http://swing-formbuilder.googlecode.com/svn/maven-repository/releases/org/formbuilder/formbuilder-main/1.1/formbuilder-main-1.1-sources.jar) ##
  * ## [javadoc](http://swing-formbuilder.googlecode.com/svn/maven-repository/releases/org/formbuilder/formbuilder-main/1.1/formbuilder-main-1.1-javadoc.jar) ##

In such case, you will also need to add libraries, the formbuilder relies on:
  * ## [jsr305](http://code.google.com/p/findbugs/) ##
  * ## [hibernate validator](http://www.hibernate.org/subprojects/validator.html) ##
  * ## [google-collections](http://code.google.com/p/google-collections/) ##
  * ## [cglib](http://cglib.sourceforge.net/) ##