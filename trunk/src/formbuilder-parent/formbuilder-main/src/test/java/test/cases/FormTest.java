package test.cases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import test.env.FormEnvironment;

/**
 * @author eav
 *         Date: 31.07.2010
 *         Time: 20:53:42
 */
public abstract class FormTest
{
    protected FormEnvironment env;

    @BeforeClass
    public void setUp()
            throws
            Exception
    {
        env = new FormEnvironment();
        env.setUp( this );
    }

    @AfterClass
    public void tearDown()
            throws
            Exception
    {
        env.tearDown( this );
    }
}
