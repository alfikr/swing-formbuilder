package org.formbuilder.mapping.bean;

import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.mapping.MappingRules;
import org.formbuilder.mapping.exception.MappingException;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import java.beans.PropertyDescriptor;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author aeremenok
 *         Date: Aug 3, 2010
 *         Time: 1:09:26 PM
 */
@NotThreadSafe
public abstract class PropertyNameBeanMapper<B>
        extends AbstractBeanMapper<B>
{
    private Class<B> beanClass;
    private MappingRules currentMappingRules;
    private boolean doValidation;
    private BeanMapping currentBeanMapping;

    @Override
    public BeanMapping map( @Nonnull final Class<B> beanClass,
                            @Nonnull final MappingRules mappingRules,
                            final boolean doValidation )
    {
        this.beanClass = beanClass;
        this.currentMappingRules = mappingRules;
        this.doValidation = doValidation;
        this.currentBeanMapping = new BeanMapping();

        this.currentBeanMapping.setPanel( mapBean() );
        return this.currentBeanMapping;
    }

    @Nonnull
    protected JComponent editor( @Nonnull String propertyName )
            throws
            MappingException
    {
        // todo error messages
        final MappingRules mappingRules = checkNotNull( currentMappingRules );
        final BeanMapping beanMapping = checkNotNull( currentBeanMapping );

        final PropertyDescriptor descriptor = Reflection.getDescriptor( beanClass, propertyName );

        return createEditor( descriptor, mappingRules, beanMapping, doValidation );
    }

    @Nonnull
    protected JLabel label( @Nonnull String propertyName )
            throws
            MappingException
    {
        final BeanMapping beanMapping = checkNotNull( currentBeanMapping );
        final PropertyDescriptor descriptor = Reflection.getDescriptor( beanClass, propertyName );
        return createLabel( beanMapping, descriptor );
    }

    protected abstract JComponent mapBean();
}
