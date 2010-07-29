/**
 *
 */
package org.formbuilder.main;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.fixture.JPanelFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.formbuilder.main.map.bean.SampleBeanMapper;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.env.ComponentEnvironment;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.concurrent.Callable;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.fail;

/**
 * @author aeremenok 2010
 */
public class BuilderTest
{
    private ComponentEnvironment<JPanel> env;

    @Test( dependsOnMethods = "setAndGetValue" )
    public void customizedLayout()
    {
        assert !SwingUtilities.isEventDispatchThread();
        final Form<Person> form = buildFormInEDT( Builder.from( Person.class ).mapBeanWith( new SampleBeanMapper<Person>()
        {
            @Override
            public JComponent map( final Person beanTemplate )
            {
                final JPanel panel = new JPanel( new BorderLayout() );
                panel.add( component( beanTemplate.getName() ) );
                return panel;
            }
        } ) );

        final JComponent component = form.asComponent();
        verifyLayout( component, JPanel.class, BorderLayout.class );
        addToWindow( component );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        wrapperPanel.textBox( "name" );

        try
        {
            assert wrapperPanel.spinner( "age" ) == null;
            fail();
        }
        catch ( final Exception ignored )
        {}
    }

    @Test
    public void setAndGetValue()
    {
        final Form<Person> form = buildFormInEDT( Builder.from( Person.class ) );

        final JComponent component = form.asComponent();
        verifyLayout( component, JPanel.class, GridBagLayout.class );
        addToWindow( component );

        final Person oldValue = createPerson();
        setValueInEDT( form, oldValue );
        requireNewBeanCreated( form, oldValue );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();

        wrapperPanel.label( "name" ).requireText( "Person's first name" );
        wrapperPanel.label( "age" ).requireText( "Age" );
        wrapperPanel.label( "birthDate" ).requireText( "Date of birth" );

        wrapperPanel.textBox( "name" ).requireText( oldValue.getName() );
        wrapperPanel.spinner( "age" ).requireValue( oldValue.getAge() );
        wrapperPanel.spinner( "birthDate" ).requireValue( oldValue.getBirthDate() );
    }

    @Test( dependsOnMethods = "customizedLayout" )
    public void testValidation()
            throws
            InterruptedException
    {
        final Form<Person> form = buildFormInEDT( Builder.from( Person.class ) );
        addToWindow( form.asComponent() );

        final Person oldValue = createPerson();
        setValueInEDT( form, oldValue );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        JTextComponentFixture nameTextBox = wrapperPanel.textBox( "name" );

        assertNotSame( nameTextBox.target.getBackground(), Color.PINK );
        nameTextBox.setText( "ee" );
        assertEquals( nameTextBox.target.getBackground(), Color.PINK );
    }

    private void requireNewBeanCreated( final Form<Person> form,
                                        final Person oldValue )
    {
        final Person newValue = form.getValue();
        assertEquals( newValue, oldValue );
        assert form.getValue() != oldValue;
    }

    private Person createPerson()
    {
        final Person oldValue = new Person();
        oldValue.setName( "oldValue" );
        oldValue.setAge( 24 );
        oldValue.setBirthDate( new Date( 1 ) );
        return oldValue;
    }

    private <B> Form<B> buildFormInEDT( final Builder<B> builder )
    {
        return GuiActionRunner.execute( new GuiQuery<Form<B>>()
        {
            @Override
            protected Form<B> executeInEDT()
            {
                return builder.buildForm();
            }
        } );
    }

    private <B> void setValueInEDT( final Form<B> form,
                                    final B value )
    {
        GuiActionRunner.execute( new GuiTask()
        {
            @Override
            protected void executeInEDT()
                    throws
                    Throwable
            {
                form.setValue( value );
            }
        } );
    }

    @BeforeClass
    public void setUp()
            throws
            Exception
    {
        this.env = ComponentEnvironment.fromQuery( new Callable<JPanel>()
        {
            @Override
            public JPanel call()
            {
                return new JPanel( new BorderLayout() );
            }
        } );
        this.env.setUp( this );
    }

    @AfterClass
    public void tearDown()
            throws
            Exception
    {
        this.env.tearDown( this );
    }

    private void addToWindow( final JComponent component )
    {
        final JPanel panel = env.getComponent();
        panel.removeAll();
        panel.add( component );
        env.getFrameFixture().component().pack();
    }

    private void verifyLayout( final JComponent component,
                               final Class<? extends JComponent> superClass,
                               final Class<? extends LayoutManager> layoutClass )
    {
        assert superClass.isAssignableFrom( component.getClass() ) : component;
        assert layoutClass.isAssignableFrom( component.getLayout().getClass() ) : component.getLayout();
    }
}
