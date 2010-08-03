/**
 *
 */
package test.env;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.LoggerFactory;
import org.testng.ITest;

import java.lang.Thread.UncaughtExceptionHandler;
import java.net.URL;

/**
 * @author eav 2010
 */
public class Environment
        implements UncaughtExceptionHandler
{
    protected org.slf4j.Logger log = LoggerFactory.getLogger( getClass() );

    @SuppressWarnings( "unused" )
    public void setUp( final Object test )
            throws
            Exception
    {
        URL url = Environment.class.getClassLoader().getResource( "log4j.properties" );
        assert url != null;
        PropertyConfigurator.configure( url );
        Thread.setDefaultUncaughtExceptionHandler( this );
        log.debug( testName( test ) + "> env set up" );
    }

    @SuppressWarnings( "unused" )
    public void tearDown( final Object test )
            throws
            Exception
    {
        log.debug( testName( test ) + "> env tear down" );
    }

    @Override
    public void uncaughtException( final Thread t,
                                   final Throwable e )
    {
        log.debug( t + ": " + e, e );
    }

    protected String testName( final Object test )
    {
        if ( test instanceof ITest )
        {
            return ( (ITest) test ).getTestName();
        }
        return String.valueOf( test );
    }
}