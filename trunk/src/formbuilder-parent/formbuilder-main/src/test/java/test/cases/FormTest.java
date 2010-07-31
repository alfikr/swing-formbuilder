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
}
