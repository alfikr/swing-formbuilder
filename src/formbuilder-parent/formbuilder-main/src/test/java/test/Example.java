/**
 *
 */
package test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;

import org.formbuilder.Form;
import org.formbuilder.FormBuilder;
import org.formbuilder.GetterMapper;
import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.bean.SampleBeanMapper;
import org.formbuilder.mapping.change.ValueChangeListener;
import org.formbuilder.validation.BackgroundMarker;
import org.formbuilder.validation.ValidationMarker;

import test.cases.PropertyMappingTest.StringToTextAreaMapper;

import com.toedter.calendar.JDateChooser;

import domain.Person;

/**
 * @author aeremenok 2010
 */
public class Example
{
    public void ex1()
    {
        final JFrame myFrame = new JFrame();

        final Form<Person> form = FormBuilder.map( Person.class ).buildForm();
        myFrame.add( form.asComponent() );
    }

    public void ex2()
    {
        final Form<Person> form = FormBuilder.map( Person.class ).with( new SampleBeanMapper<Person>()
        {
            @Override
            protected JComponent mapBean( final Person beanSample )
            {
                final Box box = Box.createHorizontalBox();
                box.add( label( beanSample.getName() ) );
                box.add( editor( beanSample.getName() ) );
                return box;
            }
        } ).buildForm();
    }

    public void ex3()
    {
        final Form<Person> form = FormBuilder.map( Person.class ).use( new StringToTextAreaMapper() ).buildForm();
    }

    public void ex4()
    {
        final Form<Person> form = FormBuilder.map( Person.class )
                .useForProperty( "description", new StringToTextAreaMapper() ).buildForm();
    }

    public void ex5()
    {
        final Form<Person> form = FormBuilder.map( Person.class ).useForGetters( new GetterMapper<Person>()
        {
            @Override
            protected void mapGetters( final Person beanSample )
            {
                mapGetter( beanSample.getDescription(), new StringToTextAreaMapper() );
            }
        } ).buildForm();
    }

    public void ex6()
    {
        final Form<Person> form = FormBuilder.map( Person.class ).doValidation( false ).buildForm();
    }

    class DateToDateChooserMapper
        implements
        TypeMapper<JDateChooser, Date>
    {
        @Override
        public Class<Date> getValueClass()
        {
            return Date.class;
        }

        @Override
        public JDateChooser createEditorComponent()
        {
            return new JDateChooser();
        }

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

        @Override
        public void bindChangeListener( final JDateChooser editorComponent,
            final ValueChangeListener<Date> changeListener )
        {
            editorComponent.getDateEditor().addPropertyChangeListener( "date", new PropertyChangeListener()
            {
                @Override
                public void propertyChange( final PropertyChangeEvent evt )
                {
                    changeListener.onChange();
                }
            } );
        }

        @Override
        public ValidationMarker getValidationMarker()
        {
            return BackgroundMarker.INSTANCE;
        }
    }

}
