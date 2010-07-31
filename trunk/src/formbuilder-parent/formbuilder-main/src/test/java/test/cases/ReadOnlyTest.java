package test.cases;

import domain.Person;
import org.fest.swing.fixture.JPanelFixture;
import org.formbuilder.main.Builder;
import org.formbuilder.main.Form;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.env.FormEnvironment;

import javax.swing.*;
import java.awt.*;

/**
 * @author eav
 *         Date: 31.07.2010
 *         Time: 12:03:33
 */
public class ReadOnlyTest
{
    private FormEnvironment env;

    @BeforeClass
    public void setUp()
            throws
            Exception
    {
        this.env = new FormEnvironment();
        this.env.setUp( this );

        UIManager.getDefaults().put( "Person.name.readonly", true );
    }

    @AfterClass
    public void tearDown()
            throws
            Exception
    {
        this.env.tearDown( this );
    }

    @Test
    public void testReadOnly()
    {
        final Form<Person> form = env.buildFormInEDT( Builder.map( Person.class ) );

        final JComponent component = form.asComponent();
        env.verifyLayout( component, JPanel.class, GridBagLayout.class );
        env.addToWindow( form );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        wrapperPanel.spinner( "birthDate" ).requireDisabled();
        wrapperPanel.textBox( "name" ).requireDisabled();
    }
}
