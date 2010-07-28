/**
 *
 */
package org.formbuilder.main;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.JPanelFixture;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.env.ComponentEnvironment;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.concurrent.Callable;

import static org.testng.Assert.assertEquals;
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
                assert SwingUtilities.isEventDispatchThread();
                final JPanel panel = new JPanel( new BorderLayout() );
                panel.add( component( beanTemplate.getName() ) );
                panel.add( component( beanTemplate.getAge() ) );
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
            wrapperPanel.spinner( "age" );
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

        final Person eav = new Person();
        eav.setName( "eav" );
        eav.setAge( 24 );
        eav.setBirthDate( new Date( 1 ) );

        form.setValue( eav );
        assertEquals( form.getValue(), eav );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();

        wrapperPanel.label( "name" ).requireText( "Person's first name" );
        wrapperPanel.label( "age" ).requireText( "Age" );
        wrapperPanel.label( "birthDate" ).requireText( "Date of birth" );

        wrapperPanel.textBox( "name" ).requireText( eav.getName() );
        wrapperPanel.spinner( "age" ).requireValue( eav.getAge() );
        wrapperPanel.spinner( "birthDate" ).requireValue( eav.getBirthDate() );
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
        env.getComponent().add( component );
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
