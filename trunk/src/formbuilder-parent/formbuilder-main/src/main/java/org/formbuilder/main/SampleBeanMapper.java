/**
 * 
 */
package org.formbuilder.main;

import java.lang.reflect.Method;

import javax.swing.JComponent;

import net.sf.cglib.proxy.InvocationHandler;

import org.formbuilder.main.proxy.Proxies;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public abstract class SampleBeanMapper<B>
    extends AbstractBeanMapper<B>
    implements
    InvocationHandler
{
    private final ThreadLocal<Method> lastCalledMethod = new ThreadLocal<Method>();

    @Override
    public Object invoke( final Object proxy, final Method method, final Object[] args )
    {
        lastCalledMethod.set( method );
        return null;
    }

    @Override
    public JComponent map( final Class<B> beanClass )
    {
        return map( Proxies.createProxy( beanClass, this ) );
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
