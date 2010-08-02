package test.cases;

import domain.Person;
import org.fest.swing.fixture.JPanelFixture;
import org.main.Builder;
import org.main.Form;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.*;

/**
 * @author eav
 *         Date: 31.07.2010
 *         Time: 12:03:33
 */
public class ReadOnlyTest
        extends FormTest
{
    @Test
    public void testReadOnly()
    {
        UIManager.getDefaults().put( "Person.name.readonly", true );

        final Form<Person> form = env.buildFormInEDT( Builder.map( Person.class ) );

        final JComponent component = form.asComponent();
        env.verifyLayout( component, JPanel.class, GridBagLayout.class );
        env.addToWindow( form );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        wrapperPanel.spinner( "birthDate" ).requireDisabled();
        wrapperPanel.textBox( "name" ).requireDisabled();

        UIManager.getDefaults().remove( "Person.name.readonly" );
    }
}
