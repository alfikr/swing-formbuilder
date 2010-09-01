package test.cases;

import domain.Address;
import domain.AddressImpl;
import org.fest.swing.fixture.ContainerFixture;
import org.formbuilder.Form;
import org.formbuilder.FormBuilder;
import org.testng.annotations.Test;

import static org.formbuilder.mapping.form.FormFactories.MODIFYING;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/** @author eav Date: 01.09.2010 Time: 23:35:15 */
public class InterfaceTest
        extends FormTest
{
    @Test
    public void mapReplicating()
    {
        final Form<Address> form = env.buildFormInEDT( FormBuilder.map( Address.class ) );
        env.addToWindow( form );

        final Address oldValue = new AddressImpl();
        env.setValueInEDT( form, oldValue );
        assertNull( oldValue.getStreet() );

        final ContainerFixture wrapperPanel = env.getWrapperPanelFixture();
        wrapperPanel.textBox( "street" ).requireEmpty();

        final Address newValue = form.getValue();

        assertNull( newValue.getStreet(), newValue.getStreet() );

        assertEquals( oldValue, newValue );
        assert oldValue != newValue;
    }

    @Test( dependsOnMethods = "mapReplicating" )
    public void mapModifying()
    {
        final Form<Address> form = env.buildFormInEDT( FormBuilder.map( Address.class ).formsOf( MODIFYING ) );
        env.addToWindow( form );

        final Address oldValue = new AddressImpl();
        env.setValueInEDT( form, oldValue );

        final Address newValue = form.getValue();

        assertEquals( oldValue, newValue );
        assert oldValue == newValue;
    }

    @Test( dependsOnMethods = "mapModifying" )
    public void mapNullModifying()
    {
        final Form<Address> form = env.buildFormInEDT( FormBuilder.map( Address.class ).formsOf( MODIFYING ) );
        env.addToWindow( form );

        env.setValueInEDT( form, null );

        final Address newValue = form.getValue();
        assert newValue != null;
    }
}
