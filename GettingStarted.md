Consider a simple java bean:
```
public class Person
{
    private String name;
    private String description;
    private int age;
    private Date birthDate;
    private boolean gender;
// ... getters and setters
}
```
You can create custom editors for its instances, and it takes few lines of code.

# Default Configuration #
This simple code will add a `JPanel` with `GridBagLayout` to a frame:
```
JFrame myFrame = new JFrame();
// ... further initialization
Form<Person> form = FormBuilder.map( Person.class )
                               // should be called in EDT (see "Threading")
                               .buildForm();
myFrame.add( form.asComponent() );
```

Each row represents a property and contains a JLabel and a specific editor component:

![http://swing-formbuilder.googlecode.com/svn/trunk/doc/default.png](http://swing-formbuilder.googlecode.com/svn/trunk/doc/default.png)

To display a given instance, simply write:
```
Person person = new Person();
person.setName( "john smith" );
// ... further initialization
form.setValue( person );
```
By default, that `person` instance will not be modified as you enter new values in a form. Instead, a copy will be returned. So
```
Person changed = form.getValue();
assert person != changed;
```
But you can specify a custom strategy of instance modification
```
Form<Person> form = FormBuilder.map( Person.class ).formsOf( FormFactories.MODIFYING ).buildForm();
// this will result in:
// ...
assert person == changed;
```
# Mapping by sample #
What if you need a custom layout of the editor components? Pass an instance of a `BeanMapper` into builder.

For example, `SampleBeanMapper` guides a panel creation using a dynamic proxy of a bean. Since a panel is created _before_ the value is set, an empty bean stub is being passed into `mapBean` method. Note that `beanSample` is a dynamic proxy, created by CGLIB.
```
Form<Person> form = FormBuilder.map( Person.class ).with( new SampleBeanMapper<Person>()
        {
            @Override
            protected JComponent mapBean( Person sample,
                                          SampleContext<Person> ctx )
            {
                Box box = Box.createHorizontalBox();
                box.add( ctx.label( sample.getName() ) );
                box.add( ctx.editor( sample.getName() ) );
                return box;
            }
        } ).buildForm();
```
If you don't want to include CGLIB dependency, use `PropertyNameBeanMapper` instead. It founds suitable components by property names. This is less magical, but more error-prone, since property names can be changed or misspelled.
```
Form<Person> form = FormBuilder.map( Person.class ).with( new PropertyNameBeanMapper<Person>()
        {
            @Override
            public JComponent mapBean( PropertyNameContext<Person> ctx )
            {
                JPanel panel = new JPanel( new BorderLayout() );
                panel.add( ctx.label( "name" ), BorderLayout.NORTH );
                panel.add( ctx.editor( "name" ), BorderLayout.CENTER );
                return panel;
            }
        } ).buildForm();
```

# Custom type mapping #
Most common property types are mapped by default to specific components using `TypeMapper`. If you need to change the default type mapping, [implement your own mapper](CustomMapper.md) and pass it into a builder.

For instance, you want to represent each `String` with `JTextArea` instead of `JTextField`:
```
Form<Person> form = FormBuilder.map( Person.class ).use( new StringToTextAreaMapper() ).buildForm();
```

# Custom property mapping #
To use custom mapping only for specific properties, write the following:
```
Form<Person> form = FormBuilder.map( Person.class )
                .useForProperty( "description", new StringToTextAreaMapper() ).buildForm();
```
or in a more sophisticated, but type-safe way (CGLIB proxy-magic again):
```
Form<Person> form = FormBuilder.map( Person.class ).useForGetters( new GetterMapper<Person>()
        {
            @Override
            public void mapGetters( Person beanSample,
                                    GetterConfig config )
            {
                config.use( beanSample.getDescription(), new StringToTextAreaMapper() );
            }
        } ).buildForm();
```

# Validation #
By default, when you enter new value to a form, it is validated using _[Java validation API (JSR303)](http://jcp.org/en/jsr/detail?id=303)_ and its default implementation - _[Hibernate validator](http://www.hibernate.org/subprojects/validator.html)_. When a constraint violation occurs - a component is marked with red and receives a tooltip with the error message. The [hibenate validator manual](http://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/) describes, how to customize your beans for validation.

If the validation isn't needed, and you don't want to include the hibernate validator dependency (not the validation-api, however), simply turn off the validation:
```
Form<Person> form = FormBuilder.map( Person.class ).doValidation( false ).buildForm();
```
# Custom appearance #
The following parameters are also customizable for each property:

| **Name** | **By annotation** | **By `UIManager` key** | **Description** |
|:---------|:------------------|:-----------------------|:----------------|
|Title     |`@UITitle`         |`"Person.name.title"`   |Displayed by a JLabel|
|Order     |`@UIOrder`         |`"Person.name.order"`   |By default, rows are sorted using this parameter. If not specified it is `Integer.MAX_VALUE`|
|Is Read Only|`@UIReadOnly`      |`"Person.name.readonly"`|Locks an editor component|
|Is Hidden |`@UIHidden`        |`"Person.name.hidden"`  |Such property is skipped during a form creation|

For example
```
UIManager.getDefaults().put( "Person.birthDate.title", "Date of birth" );
UIManager.getDefaults().put( "Person.birthDate.readonly", true );
```
is equal to
```
    @UIReadOnly
    @UITitle( "Date of birth" )
    public Date getBirthDate()
    {
        return birthDate;
    }
```
or
```
    @UIReadOnly
    @UITitle( "Date of birth" )
    private Date birthDate;
```

# Threading #

Note, that the following methods:

  * `FormBuilder#buildForm()`,
  * `Form#setValue()`,
  * `ChangeHandler#onChange()`,
  * `ValidationMarker#markViolations()`,
  * `BeanMapper#map()`

must be called in [Swing Event Dispatch Thread](http://java.sun.com/products/jfc/tsc/articles/threads/threads1.html). They cause creation and modification of UI components, so checking is being performed inside them in order to ensure thread correctness. If the thread, that called these methods is not Swing EDT, they will fail with `java.lang.IllegalStateException`.