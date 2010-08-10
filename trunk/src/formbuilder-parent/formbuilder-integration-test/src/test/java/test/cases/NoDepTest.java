package test.cases;

import static org.testng.Assert.fail;

import javax.annotation.Nonnull;
import javax.swing.Box;
import javax.swing.JComponent;

import org.fest.swing.fixture.JPanelFixture;
import org.formbuilder.Form;
import org.formbuilder.FormBuilder;
import org.formbuilder.mapping.bean.SampleBeanMapper;
import org.testng.annotations.Test;

import domain.Address;

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
        final FormBuilder<Address> b = FormBuilder.map( Address.class ).doValidation( false );
        final Form<Address> form = env.buildFormInEDT( b );
        env.addToWindow( form );

        final JPanelFixture mainPanel = env.getWrapperPanelFixture();
        mainPanel.textBox( "country" ).enterText( "Russia" );
        mainPanel.textBox( "city" ).enterText( "Saint-Petersburg" );
    }

    @Test( dependsOnMethods = "tryValidation" )
    public void tryCgLib()
    {
        final FormBuilder<Address> b = FormBuilder.map( Address.class ).with( new SampleBeanMapper<Address>()
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
            env.buildFormInEDT( b );
            fail();
        }
        catch ( final Throwable e )
        {
            log.error( e.getMessage(), e );
        }
    }

    @Test( dependsOnMethods = "testMinimalConfig" )
    public void tryValidation()
    {
        final FormBuilder<Address> b = FormBuilder.map( Address.class ).doValidation( true );
        try
        {
            env.buildFormInEDT( b );
            fail();
        }
        catch ( final Throwable e )
        {
            log.error( e.getMessage(), e );
        }
    }
}