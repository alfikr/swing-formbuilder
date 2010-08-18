package org.formbuilder.mapping.bean;

import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.mapping.MappingRules;
import org.formbuilder.mapping.metadata.CombinedMetaData;
import org.formbuilder.mapping.metadata.MetaData;
import org.formbuilder.mapping.metadata.sort.OrderedPropertyDescriptor;
import org.formbuilder.mapping.metadata.sort.PropertySorter;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.beans.PropertyDescriptor;
import java.util.List;

/** @author aeremenok Date: Aug 18, 2010 Time: 1:57:48 PM */
public class BeanMappingContext<B>
{
// ------------------------------ FIELDS ------------------------------
    private final Class<B> beanClass;
    private final MappingRules mappingRules;
    private final ComponentFactory componentFactory;
    private final boolean doValidation;
    private final PropertySorter sorter;

// --------------------------- CONSTRUCTORS ---------------------------

    public BeanMappingContext( final Class<B> beanClass,
                               final MappingRules mappingRules,
                               final boolean doValidation )
    { // todo inject metadata
        this( beanClass, mappingRules, doValidation, new CombinedMetaData() );
    }

    public BeanMappingContext( final Class<B> beanClass,
                               final MappingRules mappingRules,
                               final boolean doValidation,
                               final MetaData metaData )
    {
        this.beanClass = beanClass;
        this.mappingRules = mappingRules;
        this.doValidation = doValidation;

        this.componentFactory = new ComponentFactory( metaData );
        this.sorter = new PropertySorter( metaData );
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    @Nonnull
    public Class<B> getBeanClass()
    {
        return beanClass;
    }

// -------------------------- OTHER METHODS --------------------------

    @Nonnull
    public List<OrderedPropertyDescriptor> getActiveSortedDescriptors()
    {
        return sorter.activeSortedDescriptors( beanClass );
    }

    @Nonnull
    public JComponent getEditor( @Nonnull final PropertyDescriptor descriptor,
                                 @Nonnull final BeanMapping beanMapping )
    {
        JComponent editor = beanMapping.getEditor( descriptor );
        if ( editor == null )
        {
            final TypeMapper mapper = mappingRules.getMapper( descriptor );
            editor = componentFactory.createEditor( descriptor, mapper, doValidation );
            beanMapping.addEditor( descriptor, editor, mapper );
        }
        return editor;
    }

    @Nonnull
    public JLabel getLabel( @Nonnull final PropertyDescriptor descriptor,
                            @Nonnull final BeanMapping beanMapping )
    {
        JLabel label = beanMapping.getLabel( descriptor );
        if ( label == null )
        {
            final TypeMapper mapper = mappingRules.getMapper( descriptor );
            label = componentFactory.createLabel( descriptor, mapper, doValidation );
            beanMapping.addLabel( descriptor, label );
        }
        return label;
    }
}
