package test.env;

import domain.Person;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.formbuilder.Form;
import org.formbuilder.FormBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 16:08:40
 */
public class FormEnvironment
        extends ComponentEnvironment<JPanel>
{
    public FormEnvironment()
    {
        super( new Callable<JPanel>()
        {
            @Override
            public JPanel call()
            {
                return new JPanel( new BorderLayout() );
            }
        }, false );
    }

    public <B> Form<B> addDefaultForm( final Class<B> beanClass )
    {
        final Form<B> form = buildFormInEDT( FormBuilder.map( beanClass ) );

        final JComponent component = form.asComponent();
        verifyLayout( component, JPanel.class, GridBagLayout.class );
        addToWindow( form );
        return form;
    }

    public void addToWindow( final Form form )
    {
        addToWindow( form.asComponent() );
    }

    public void addToWindow( final JComponent component )
    {
        final JPanel panel = getComponent();
        panel.removeAll();
        panel.add( component );
        getFrameFixture().component().pack();
    }

    public <B> Form<B> buildFormInEDT( final FormBuilder<B> formBuilder )
    {
        return GuiActionRunner.execute( new GuiQuery<Form<B>>()
        {
            @Override
            protected Form<B> executeInEDT()
            {
                return formBuilder.buildForm();
            }
        } );
    }

    public Person createPerson()
    {
        final Person oldValue = new Person();
        oldValue.setName( "jonh smith" );
        oldValue.setAge( 24 );
        oldValue.setBirthDate( new Date( 1 ) );
        return oldValue;
    }

    public <B> void setValueInEDT( final Form<B> form,
                                   final B value )
    {
        GuiActionRunner.execute( new GuiTask()
        {
            @Override
            protected void executeInEDT()
            {
                form.setValue( value );
            }
        } );
    }

    public void verifyLayout( final JComponent component,
                              final Class<? extends JComponent> superClass,
                              final Class<? extends LayoutManager> layoutClass )
    {
        assert superClass.isAssignableFrom( component.getClass() ) : component;
        assert layoutClass.isAssignableFrom( component.getLayout().getClass() ) : component.getLayout();
    }
}
