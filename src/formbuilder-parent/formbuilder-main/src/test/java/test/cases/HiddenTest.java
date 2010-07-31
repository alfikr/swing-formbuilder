package test.cases;

import domain.Person;
import org.fest.swing.fixture.JPanelFixture;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

/**
 * @author eav
 *         Date: 31.07.2010
 *         Time: 20:48:24
 */
public class HiddenTest
        extends FormTest
{
    @Test
    public void testHidden()
    {
        env.addDefaultForm( Person.class );
        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        try
        {
            wrapperPanel.spinner( "id" );
            fail();
        }
        catch ( Exception ignored )
        { }
    }
}
