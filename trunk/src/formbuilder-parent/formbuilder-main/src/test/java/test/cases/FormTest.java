package test.cases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import test.env.FormEnvironment;
import test.env.ScreenshotSaver;

/**
 * @author eav
 *         Date: 31.07.2010
 *         Time: 20:53:42
 */
public abstract class FormTest
{
    protected final Logger log = LoggerFactory.getLogger( getClass() );
    protected final ScreenshotSaver screenshotSaver = new ScreenshotSaver();
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
