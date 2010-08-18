package org.formbuilder.mapping.beanmapper;

import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.mapping.BeanMappingContext;
import org.formbuilder.mapping.exception.MappingException;
import org.formbuilder.util.MethodRecorder;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import static com.google.common.base.Preconditions.checkNotNull;

/** @author aeremenok Date: Aug 18, 2010 Time: 4:24:58 PM */
public class SampleContext<B>
{
// ------------------------------ FIELDS ------------------------------
    private final BeanMappingContext<B> beanMappingContext;
    private final BeanMapping beanMapping;
    private final MethodRecorder methodRecorder;

// --------------------------- CONSTRUCTORS ---------------------------

    public SampleContext( @Nonnull final BeanMappingContext<B> beanMappingContext,
                          @Nonnull final BeanMapping beanMapping,
                          @Nonnull final MethodRecorder methodRecorder )
    {
        this.beanMappingContext = beanMappingContext;
        this.beanMapping = beanMapping;
        this.methodRecorder = methodRecorder;
    }

// -------------------------- OTHER METHODS --------------------------

    @Nonnull
    public JComponent editor( @SuppressWarnings( "unused" ) @Nullable final Object whatProxyGetterReturned )
            throws
            MappingException
    {

        final PropertyDescriptor descriptor = getDescriptor();
        return beanMappingContext.getEditor( descriptor, beanMapping );
    }

    @Nonnull
    public JLabel label( @SuppressWarnings( "unused" ) @Nullable final Object whatProxyGetterReturned )
            throws
            MappingException
    {
        final PropertyDescriptor descriptor = getDescriptor();
        return beanMappingContext.getLabel( descriptor, beanMapping );
    }

    @Nonnull
    protected PropertyDescriptor getDescriptor()
    {
        // todo error messages
        final Method readMethod = checkNotNull( methodRecorder.getLastCalledMethod() );
        return Reflection.getDescriptor( readMethod );
    }
}
