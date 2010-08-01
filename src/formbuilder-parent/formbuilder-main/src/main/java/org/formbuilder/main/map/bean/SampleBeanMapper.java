/**
 *
 */
package org.formbuilder.main.map.bean;

import org.formbuilder.main.map.Mapping;
import org.formbuilder.main.map.MappingRules;
import org.formbuilder.main.map.exception.MappingException;
import org.formbuilder.main.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.lang.reflect.InvocationHandler;
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

    @Nonnull
    @Override
    public Object invoke( @Nonnull final Object proxy,
                          @Nonnull final Method method,
                          @Nullable final Object[] args )
            throws
            InvocationTargetException,
            IllegalAccessException
    {
        assert SwingUtilities.isEventDispatchThread();

        lastCalledMethod = method;
        return Reflection.emptyValue( method );
    }

    @Nonnull
    @Override
    public Mapping map( @Nonnull final Class<B> beanClass,
                        @Nonnull final MappingRules mappingRules )
    {
        this.currentMappingRules = mappingRules;
        this.currentMapping = new Mapping();
        this.currentMapping.setPanel( mapBean( Reflection.createProxy( beanClass, this ) ) );
        return this.currentMapping;
    }

    @Nonnull
    protected JComponent component( @SuppressWarnings( "unused" ) @Nullable final Object whatProxyGetterReturned )
            throws
            MappingException
    {
        // todo error messages
        final MappingRules mappers = checkNotNull( currentMappingRules );
        final Method readMethod = checkNotNull( lastCalledMethod );
        final Mapping mapping = checkNotNull( currentMapping );
        return createEditor( Reflection.getDescriptor( readMethod ), mappers, mapping );
    }

    @Nonnull
    protected abstract JComponent mapBean( @Nonnull B beanSample );
}
