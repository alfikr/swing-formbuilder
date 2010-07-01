/**
 * 
 */
package org.formbuilder.main;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.util.concurrent.Callable;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.fest.swing.fixture.JPanelFixture;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test.env.ComponentEnvironment;

/**
 * @author aeremenok 2010
 */
public class BuilderTest
{
    private ComponentEnvironment<JPanel> env;

    @Test( dependsOnMethods = "setAndGetValue" )
    public void customizedLayout()
    {
        final Form<Person> form = Builder.from( Person.class ).mapBeanWith( new SampleBeanMapper<Person>()
        {
            @Override
            public JComponent map( final Person beanTemplate )
            {
                //                final JPanel panel = new JPanel( new BorderLayout() );
                final Box panel = Box.createHorizontalBox();
                panel.add( component( beanTemplate.getName() ) );
                panel.add( component( beanTemplate.getAge() ) );
                return panel;
            }
        } ).buildForm();

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
        catch( final Exception e )
        {}
    }

    @Test
    public void setAndGetValue()
    {
        final Form<Person> form = Builder.from( Person.class ).buildForm();

        final JComponent component = form.asComponent();
        verifyLayout( component, JPanel.class, GridBagLayout.class );
        addToWindow( component );

        final Person eav = new Person();
        form.setValue( eav );
        assertEquals( form.getValue(), eav );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();

        wrapperPanel.label( "name" ).requireText( "Person's first name" );
        wrapperPanel.label( "age" ).requireText( "Age" );
        wrapperPanel.label( "birthDate" ).requireText( "Date of birth" );

        wrapperPanel.textBox( "name" ).requireText( eav.getName() );
        wrapperPanel.spinner( "age" ).requireValue( 24 );
    }

    @BeforeClass
    public void setUp()
        throws Exception
    {
        this.env = ComponentEnvironment.fromQuery( new Callable<JPanel>()
        {
            @Override
            public JPanel call()
            {
                final JPanel containerPanel = new JPanel( new BorderLayout() );
                return containerPanel;
            }
        } );
        this.env.setUp( this );
    }

    @AfterClass
    public void tearDown()
        throws Exception
    {
        this.env.tearDown( this );
    }

    private void addToWindow( final JComponent component )
    {
        env.getComponent().add( component );
        env.getFrameFixture().component().pack();
    }

    private void verifyLayout( final JComponent component, final Class<? extends JComponent> superClass,
        final Class<? extends LayoutManager> layoutClass )
    {
        assert superClass.isAssignableFrom( component.getClass() ) : component;
        assert layoutClass.isAssignableFrom( component.getLayout().getClass() ) : component.getLayout();
    }
}
