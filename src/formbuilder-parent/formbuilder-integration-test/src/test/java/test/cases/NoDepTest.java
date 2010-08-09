package test.cases;

import domain.Address;
import org.fest.swing.fixture.JPanelFixture;
import org.formbuilder.Form;
import org.formbuilder.FormBuilder;
import org.formbuilder.mapping.bean.SampleBeanMapper;
import org.testng.annotations.Test;

import javax.annotation.Nonnull;
import javax.swing.*;

import static org.testng.Assert.fail;

/**
 * @author aeremenok
 *         Date: 09.08.2010
 *         Time: 17:32:06
 */
public class NoDepTest
        extends FormTest
{
    @Test
    public void testMinimalConfig()
    {
        FormBuilder<Address> b = FormBuilder.map( Address.class ).doValidation( false );
        Form<Address> form = env.buildFormInEDT( b );
        env.addToWindow( form );

        JPanelFixture mainPanel = env.getWrapperPanelFixture();
        mainPanel.textBox( "country" ).enterText( "Russia" );
        mainPanel.textBox( "city" ).enterText( "Saint-Petersburg" );
    }

    @Test( dependsOnMethods = "testMinimalConfig" )
    public void tryValidation()
    {
        FormBuilder<Address> b = FormBuilder.map( Address.class ).doValidation( true );
        try
        {
            Form<Address> form = env.buildFormInEDT( b );
            fail();
        }
        catch ( Throwable e )
        {
            log.error( e.getMessage(), e );
        }
    }

    @Test( dependsOnMethods = "tryValidation" )
    public void tryCgLib()
    {
        FormBuilder<Address> b = FormBuilder.map( Address.class ).with( new SampleBeanMapper<Address>()
        {
            @Override
            protected JComponent mapBean( @Nonnull final Address beanSample )
            {
                final Box box = Box.createHorizontalBox();
                box.add( editor( beanSample.getCity() ) );
                return box;
            }
        } );

        try
        {
            Form<Address> form = env.buildFormInEDT( b );
            fail();
        }
        catch ( Throwable e )
        {
            log.error( e.getMessage(), e );
        }
    }
}
