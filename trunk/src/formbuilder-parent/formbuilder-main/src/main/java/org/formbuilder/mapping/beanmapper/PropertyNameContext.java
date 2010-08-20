package org.formbuilder.mapping.beanmapper;

import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.mapping.BeanMappingContext;
import org.formbuilder.mapping.exception.MappingException;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.beans.PropertyDescriptor;

/** @author aeremenok Date: Aug 18, 2010 Time: 4:32:57 PM */
public class PropertyNameContext<B>
{
// ------------------------------ FIELDS ------------------------------
    private final BeanMappingContext<B> beanMappingContext;

// --------------------------- CONSTRUCTORS ---------------------------

    public PropertyNameContext( @Nonnull final BeanMappingContext<B> beanMappingContext )
    {
        this.beanMappingContext = beanMappingContext;
    }

// -------------------------- OTHER METHODS --------------------------

    @Nonnull
    public JComponent editor( @Nonnull final String propertyName )
            throws
            MappingException
    {
        return beanMappingContext.getEditor( getDescriptor( propertyName ) );
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
        return beanMappingContext.getLabel( getDescriptor( propertyName ) );
    }
}
