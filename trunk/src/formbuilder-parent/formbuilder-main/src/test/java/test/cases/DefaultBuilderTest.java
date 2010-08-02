/**
 *
 */
package test.cases;

import domain.Person;
import org.fest.swing.fixture.JPanelFixture;
import org.main.Builder;
import org.main.Form;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.*;

import static org.testng.Assert.assertEquals;

/**
 * @author aeremenok 2010
 */
public class DefaultBuilderTest
        extends FormTest
{
    @Test
    public void setAndGetValue()
    {
        final Form<Person> form = env.buildFormInEDT( Builder.map( Person.class ) );

        final JComponent component = form.asComponent();
        env.verifyLayout( component, JPanel.class, GridBagLayout.class );
        env.addToWindow( form );

        env.setValueInEDT( form, null );
        assert form.getValue() != null;

        final Person person = env.createPerson();
        env.setValueInEDT( form, person );
        requireNewBeanCreated( form, person );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();

        wrapperPanel.label( "name" ).requireText( "Person's first name" );
        wrapperPanel.label( "age" ).requireText( "Age" );
        wrapperPanel.label( "birthDate" ).requireText( "Date of birth" );

        wrapperPanel.textBox( "name" ).requireText( person.getName() );
        wrapperPanel.spinner( "age" ).requireValue( person.getAge() );
        wrapperPanel.spinner( "birthDate" ).requireValue( person.getBirthDate() );
    }

    private void requireNewBeanCreated( final Form<Person> form,
                                        final Person oldValue )
    {
        final Person newValue = form.getValue();
        assertEquals( newValue, oldValue );
        assert form.getValue() != oldValue;
    }
}
