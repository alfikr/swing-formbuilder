package test.cases;

import domain.Person;
import org.fest.swing.fixture.JPanelFixture;
import org.formbuilder.Form;
import org.formbuilder.FormBuilder;
import org.formbuilder.mapping.bean.PropertyNameBeanMapper;
import org.formbuilder.mapping.bean.SampleBeanMapper;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.*;

import static org.testng.Assert.fail;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 16:22:16
 */
public class CustomMapperTest
        extends FormTest
{
    @Test
    public void customizedBySample()
    {
        assert !SwingUtilities.isEventDispatchThread();
        final Form<Person> form = env.buildFormInEDT( FormBuilder.map( Person.class ).with( new SampleBeanMapper<Person>()
        {
            @Override
            public JComponent mapBean( final Person beanTemplate )
            {
                final JPanel panel = new JPanel( new BorderLayout() );
                panel.add( label( beanTemplate.getName() ), BorderLayout.NORTH );
                panel.add( editor( beanTemplate.getName() ), BorderLayout.CENTER );
                return panel;
            }
        } ) );

        final JComponent component = form.asComponent();
        env.verifyLayout( component, JPanel.class, BorderLayout.class );
        env.addToWindow( form );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        wrapperPanel.textBox( "name" );
        wrapperPanel.label( "name" );

        try
        {
            wrapperPanel.spinner( "age" );
            fail();
        }
        catch ( final Exception ignored )
        {}
    }

    @Test( dependsOnMethods = "customizedBySample" )
    public void customizedByPropertyName()
    {
        assert !SwingUtilities.isEventDispatchThread();
        final Form<Person> form = env.buildFormInEDT( FormBuilder.map( Person.class ).with( new PropertyNameBeanMapper<Person>()
        {
            @Override
            public JComponent mapBean()
            {
                final JPanel panel = new JPanel( new BorderLayout() );
                panel.add( label( "name" ), BorderLayout.NORTH );
                panel.add( editor( "name" ), BorderLayout.CENTER );
                return panel;
            }
        } ) );

        final JComponent component = form.asComponent();
        env.verifyLayout( component, JPanel.class, BorderLayout.class );
        env.addToWindow( form );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        wrapperPanel.textBox( "name" );
        wrapperPanel.label( "name" );

        try
        {
            wrapperPanel.spinner( "age" );
            fail();
        }
        catch ( final Exception ignored )
        {}
    }
}
