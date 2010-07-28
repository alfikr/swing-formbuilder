/**
 *
 */
package org.formbuilder.main;

import net.sf.cglib.proxy.InvocationHandler;
import org.formbuilder.main.proxy.Proxies;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public abstract class SampleBeanMapper<B>
        extends AbstractBeanMapper<B>
        implements InvocationHandler
{
    private final ThreadLocal<Method> lastCalledMethod = new ThreadLocal<Method>();

    @Override
    public Object invoke( final Object proxy,
                          final Method method,
                          final Object[] args )
            throws
            InvocationTargetException,
            IllegalAccessException
    {
        lastCalledMethod.set( method );
        return Proxies.emptyValue( method );
    }

    @Override
    public Mapping map( final Class<B> beanClass,
                        final TypeMappers typeMappers )
    {
        return new Mapping( map( Proxies.createProxy( beanClass, this ) ) );
    }

    private String getPropertyName( final Method method )
    {
        return "";
    }

    protected JComponent component( @SuppressWarnings( "unused" ) final Object propertyValue )
    {
        final String propertyName = getPropertyName( lastCalledMethod.get() );
        return getComponent( propertyName );
    }

    protected abstract JComponent map( B beanSample );
}
