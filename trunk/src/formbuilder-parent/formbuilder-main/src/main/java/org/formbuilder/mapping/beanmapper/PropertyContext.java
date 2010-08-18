package org.formbuilder.mapping.beanmapper;

import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.mapping.BeanMappingContext;
import org.formbuilder.mapping.exception.MappingException;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.beans.PropertyDescriptor;

/** @author aeremenok Date: Aug 18, 2010 Time: 4:32:57 PM */
public class PropertyContext<B>
{
// ------------------------------ FIELDS ------------------------------
    private final BeanMappingContext<B> beanMappingContext;
    private final BeanMapping beanMapping;

// --------------------------- CONSTRUCTORS ---------------------------

    public PropertyContext( @Nonnull final BeanMappingContext<B> beanMappingContext,
                            @Nonnull final BeanMapping beanMapping )
    {
        this.beanMappingContext = beanMappingContext;
        this.beanMapping = beanMapping;
    }

// -------------------------- OTHER METHODS --------------------------

    @Nonnull
    public JComponent editor( @Nonnull final String propertyName )
            throws
            MappingException
    {
        final PropertyDescriptor descriptor = getDescriptor( propertyName );
        return beanMappingContext.getEditor( descriptor, beanMapping );
    }

    protected PropertyDescriptor getDescriptor( final String propertyName )
    {
        final Class<B> beanClass = beanMappingContext.getBeanClass();
        return Reflection.getDescriptor( beanClass, propertyName );
    }

    @Nonnull
    public JLabel label( @Nonnull final String propertyName )
            throws
            MappingException
    {
        final PropertyDescriptor descriptor = getDescriptor( propertyName );
        return beanMappingContext.getLabel( descriptor, beanMapping );
    }
}
