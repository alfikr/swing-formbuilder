## UPDATE: The source code repository has been moved to [GitHub](http://github.com/aeremenok/swing-formbuilder) ##

Allows to create swing forms in the following manner:
```
Form<Person> form = FormBuilder.map( Person.class ).buildForm();
```
or
```
Form<Person> form = FormBuilder.map( Person.class ).with( new SampleBeanMapper<Person>() {
            @Override
            protected JComponent mapBean( Person sample,
                                          SampleContext<Person> ctx )
            {
                final Box box = Box.createHorizontalBox();
                box.add( ctx.label( sample.getName() ) );
                box.add( ctx.editor( sample.getName() ) );
                return box;
            }
} ).buildForm();
```

## Usage: ##
  * ### [Include a dependency](Dependencies.md) ###
  * ### [Write Code](GettingStarted.md) ###

## News: ##
### Version 1.1 has been released ###
  * #### [Field annotations](https://code.google.com/p/swing-formbuilder/issues/detail?id=5&can=7) ####
  * #### [Custom strategies of bean instance modification](https://code.google.com/p/swing-formbuilder/issues/detail?id=4&can=7) ####
  * #### [Bean interfaces allowed](https://code.google.com/p/swing-formbuilder/issues/detail?id=6&can=7) ####
  * #### [JDK-1.5 compatibility](https://code.google.com/p/swing-formbuilder/issues/detail?id=2&can=7) ####