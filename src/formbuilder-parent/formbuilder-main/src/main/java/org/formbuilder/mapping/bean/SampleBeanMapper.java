/**
 *
 */
package org.formbuilder.mapping.bean;

import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.mapping.MappingRules;
import org.formbuilder.mapping.exception.MappingException;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static javax.swing.SwingUtilities.isEventDispatchThread;

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
    private BeanMapping currentBeanMapping;

    @Nonnull
    @Override
    public Object invoke( @Nonnull final Object proxy,
                          @Nonnull final Method method,
                          @Nullable final Object[] args )
            throws
            InvocationTargetException,
            IllegalAccessException
    {
        checkState( isEventDispatchThread() );

        lastCalledMethod = method;
        return Reflection.emptyValue( method );
    }

    @Nonnull
    @Override
    public BeanMapping map( @Nonnull final Class<B> beanClass,
                            @Nonnull final MappingRules mappingRules )
    {
        this.currentMappingRules = mappingRules;
        this.currentBeanMapping = new BeanMapping();
        this.currentBeanMapping.setPanel( mapBean( Reflection.createProxy( beanClass, this ) ) );
        return this.currentBeanMapping;
    }

    @Nonnull
    protected JComponent component( @SuppressWarnings( "unused" ) @Nullable final Object whatProxyGetterReturned )
            throws
            MappingException
    {
        // todo error messages
        final MappingRules mappers = checkNotNull( currentMappingRules );
        final Method readMethod = checkNotNull( lastCalledMethod );
        final BeanMapping beanMapping = checkNotNull( currentBeanMapping );
        return createEditor( Reflection.getDescriptor( readMethod ), mappers, beanMapping );
    }

    @Nonnull
    protected abstract JComponent mapBean( @Nonnull B beanSample );
}
