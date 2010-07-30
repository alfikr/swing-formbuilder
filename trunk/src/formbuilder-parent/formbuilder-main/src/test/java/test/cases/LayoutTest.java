package test.cases;

import domain.Person;
import org.fest.swing.fixture.JPanelFixture;
import org.formbuilder.main.Builder;
import org.formbuilder.main.Form;
import org.formbuilder.main.map.bean.SampleBeanMapper;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.env.FormEnvironment;

import javax.swing.*;
import java.awt.*;

import static org.testng.Assert.fail;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 16:22:16
 */
public class LayoutTest
{
    private FormEnvironment env;

    @BeforeClass
    public void setUp()
            throws
            Exception
    {
        this.env = new FormEnvironment();
        this.env.setUp( this );
    }

    @AfterClass
    public void tearDown()
            throws
            Exception
    {
        this.env.tearDown( this );
    }

    @Test
    public void customizedLayout()
    {
        assert !SwingUtilities.isEventDispatchThread();
        final Form<Person> form = env.buildFormInEDT( Builder.from( Person.class ).mapBeanWith( new SampleBeanMapper<Person>()
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
        env.verifyLayout( component, JPanel.class, BorderLayout.class );
        env.addToWindow( form );

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
}
