package test.cases;

import domain.Person;
import org.fest.swing.fixture.JPanelFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.formbuilder.main.Builder;
import org.formbuilder.main.Form;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.env.FormEnvironment;

import java.awt.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertNull;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 16:21:04
 */
public class ValidationTest
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
    public void testValidation()
            throws
            InterruptedException
    {
        final Form<Person> form = env.buildFormInEDT( Builder.map( Person.class ) );
        env.addToWindow( form.asComponent() );

        final Person oldValue = env.createPerson();
        env.setValueInEDT( form, oldValue );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        JTextComponentFixture nameTextBox = wrapperPanel.textBox( "name" );

        assertNotSame( nameTextBox.target.getBackground(), Color.PINK );
        assertNull( nameTextBox.target.getToolTipText() );
        nameTextBox.setText( "ee" );
        assertEquals( nameTextBox.target.getBackground(), Color.PINK );
        nameTextBox.requireToolTip( "size must be between 3 and 2147483647" );
    }
}
