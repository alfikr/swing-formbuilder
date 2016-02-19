# Writing a custom mapper #

Suppose, you want to map `java.util.Date` to [JDateChooser](http://www.toedter.com/en/jcalendar/index.html) instead of `JSpinner`. Implement the following:
```
class DateToDateChooserMapper
        implements
        TypeMapper<JDateChooser, Date>
    {
```
Specify, which property type you map
```
        @Override
        public Class<Date> getValueClass()
        { 
            return Date.class;
        }
```
When a form is created, this `TypeMapper` is asked to create a new instance of editor component
```
        @Override
        public JDateChooser createEditorComponent()
        { 
            return new JDateChooser();
        }
```
Transfer the value between component and bean property
```
        @Override
        public Date getValue( final JDateChooser editorComponent )
        { 
            return editorComponent.getDate();
        }

        @Override
        public void setValue( final JDateChooser editorComponent, final Date value )
        {
            editorComponent.setDate( value );
        }
```
Propagate value change events from component to mapping. If you don't need validation, you may leave this method empty
```
        @Override
        public void handleChanges( final JDateChooser editorComponent,
                                   final ChangeHandler changeHandler )
        {
            editorComponent.getDateEditor().addPropertyChangeListener( "date", new PropertyChangeListener()
            {
                @Override
                public void propertyChange( final PropertyChangeEvent evt )
                {
                    changeHandler.onChange( BackgroundMarker.INSTANCE );
                }
            } );
        }
```
`BackgroundMarker.INSTANCE` implements the `ValidationMarker` interface. It marks the components, when constraint violations occur. You may implement your custom marker, or do not pass anything to `onChange()`, if the validation isn't needed.