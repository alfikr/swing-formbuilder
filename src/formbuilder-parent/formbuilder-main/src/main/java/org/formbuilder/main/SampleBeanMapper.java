/**
 *
 */
package org.formbuilder.main;

import net.sf.cglib.proxy.InvocationHandler;
import org.formbuilder.main.proxy.Proxies;

import javax.swing.*;
import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public abstract class SampleBeanMapper<B>
        extends AbstractBeanMapper<B>
        implements InvocationHandler
{
    private Method lastCalledMethod;
    private TypeMappers currentTypeMappers;
    private Mapping currentMapping;

    @Override
    public Object invoke( final Object proxy,
                          final Method method,
                          final Object[] args )
            throws
            InvocationTargetException,
            IllegalAccessException
    {
        assert SwingUtilities.isEventDispatchThread();

        lastCalledMethod = method;
        return Proxies.emptyValue( method );
    }

    @Override
    public Mapping map( final Class<B> beanClass,
                        final TypeMappers typeMappers )
    {
        this.currentTypeMappers = typeMappers;
        this.currentMapping = new Mapping();
        this.currentMapping.setPanel( map( Proxies.createProxy( beanClass, this ) ) );
        return this.currentMapping;
    }

    protected JComponent component( @SuppressWarnings( "unused" ) final Object propertyValue )
            throws
            MapperNotFoundException
    {
        // todo error messages
        final TypeMappers mappers = checkNotNull( currentTypeMappers );
        final Method readMethod = checkNotNull( lastCalledMethod );
        final Mapping mapping = checkNotNull( currentMapping );
        return createEditor( mappers, getDescriptor( readMethod ), mapping );
    }

    protected PropertyDescriptor getDescriptor( final Method readMethod )
    {
        BeanInfo info = getBeanInfo( readMethod.getDeclaringClass() );
        for ( PropertyDescriptor descriptor : info.getPropertyDescriptors() )
        {
            if ( readMethod.equals( descriptor.getReadMethod() ) )
            {
                return descriptor;
            }
        }
        throw new RuntimeException( readMethod + " is not a getter method for a bean" );
    }

    protected abstract JComponent map( B beanSample );
}
