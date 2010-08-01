package test.cases;

import domain.Person;
import org.fest.swing.fixture.JPanelFixture;
import org.fest.swing.fixture.JSpinnerFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.testng.annotations.Test;

import javax.swing.*;

/**
 * @author eav
 *         Date: 31.07.2010
 *         Time: 21:05:55
 */
public class OrderTest
        extends FormTest
{
    @Test
    public void naturalOrder()
    {
        env.addDefaultForm( Person.class );
        JPanelFixture wrapper = env.getWrapperPanelFixture();

        JSpinnerFixture ageSpinner = wrapper.spinner( "age" );
        JSpinnerFixture birthDateSpinner = wrapper.spinner( "birthDate" );
        JTextComponentFixture descTextBox = wrapper.textBox( "description" );
        JTextComponentFixture nameTextBox = wrapper.textBox( "name" );

        assert getY( ageSpinner ) < getY( birthDateSpinner );
        assert getY( birthDateSpinner ) < getY( descTextBox );
        assert getY( descTextBox ) < getY( nameTextBox );
    }

    @Test( dependsOnMethods = "naturalOrder" )
    public void customOrder()
    {
        // unspecified order = maxint, so this should be handled normally 
//        UIManager.getDefaults().put( "Person.age.order", 3 );
        UIManager.getDefaults().put( "Person.birthDate.order", 2 );
        UIManager.getDefaults().put( "Person.description.order", 1 );
        UIManager.getDefaults().put( "Person.name.order", 0 );

        env.addDefaultForm( Person.class );
        JPanelFixture wrapper = env.getWrapperPanelFixture();

        JSpinnerFixture ageSpinner = wrapper.spinner( "age" );
        JSpinnerFixture birthDateSpinner = wrapper.spinner( "birthDate" );
        JTextComponentFixture descTextBox = wrapper.textBox( "description" );
        JTextComponentFixture nameTextBox = wrapper.textBox( "name" );

        assert getY( ageSpinner ) > getY( birthDateSpinner );
        assert getY( birthDateSpinner ) > getY( descTextBox );
        assert getY( descTextBox ) > getY( nameTextBox );

        UIManager.getDefaults().remove( "Person.age.order" );
        UIManager.getDefaults().remove( "Person.birthDate.order" );
        UIManager.getDefaults().remove( "Person.description.order" );
        UIManager.getDefaults().remove( "Person.name.order" );
    }

    private int getY( JSpinnerFixture fixture )
    {
        return fixture.target.getLocationOnScreen().y;
    }

    private int getY( JTextComponentFixture fixture )
    {
        return fixture.target.getLocationOnScreen().y;
    }
}
