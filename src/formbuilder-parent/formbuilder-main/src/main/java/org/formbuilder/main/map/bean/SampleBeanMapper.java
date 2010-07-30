/**
 *
 */
package org.formbuilder.main.map.bean;

import net.sf.cglib.proxy.InvocationHandler;
import org.formbuilder.main.map.Mapping;
import org.formbuilder.main.map.MappingException;
import org.formbuilder.main.map.MappingRules;
import org.formbuilder.main.util.Reflection;

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
    private MappingRules currentMappingRules;
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
        return Reflection.emptyValue( method );
    }

    @Override
    public Mapping map( final Class<B> beanClass,
                        final MappingRules mappingRules )
    {
        this.currentMappingRules = mappingRules;
        this.currentMapping = new Mapping();
        this.currentMapping.setPanel( map( Reflection.createProxy( beanClass, this ) ) );
        return this.currentMapping;
    }

    protected JComponent component( @SuppressWarnings( "unused" ) final Object propertyValue )
            throws
            MappingException
    {
        // todo error messages
        final MappingRules mappers = checkNotNull( currentMappingRules );
        final Method readMethod = checkNotNull( lastCalledMethod );
        final Mapping mapping = checkNotNull( currentMapping );
        return createEditor( getDescriptor( readMethod ), mappers, mapping );
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
