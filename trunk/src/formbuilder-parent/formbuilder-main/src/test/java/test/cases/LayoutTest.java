package test.cases;

import domain.Person;
import org.fest.swing.fixture.JPanelFixture;
import org.formbuilder.main.Builder;
import org.formbuilder.main.Form;
import org.formbuilder.main.map.bean.SampleBeanMapper;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.*;

import static org.testng.Assert.fail;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 16:22:16
 */
public class LayoutTest
        extends FormTest
{
    @Test
    public void customizedLayout()
    {
        assert !SwingUtilities.isEventDispatchThread();
        final Form<Person> form = env.buildFormInEDT( Builder.map( Person.class ).with( new SampleBeanMapper<Person>()
        {
            @Override
            public JComponent mapBean( final Person beanTemplate )
            {
                final JPanel panel = new JPanel( new BorderLayout() );
                panel.add( component( beanTemplate.getName() ) );
                return panel;
            }
        } ) );

        final JComponent component = form.asComponent();
        env.verifyLayout( component, JPanel.class, BorderLayout.class );
        env.addToWindow( form );

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
}
