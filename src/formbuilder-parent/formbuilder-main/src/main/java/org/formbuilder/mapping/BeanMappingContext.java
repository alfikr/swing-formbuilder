package org.formbuilder.mapping;

import org.formbuilder.TypeMapper;
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
    protected final Class<B> beanClass;
    protected final MappingRules mappingRules;
    protected final ComponentFactory componentFactory;
    protected final PropertySorter sorter;
    protected final ChangeObservation changeObservation;

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
        this.changeObservation = new ChangeObservation( doValidation );

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
        JComponent editorComponent = beanMapping.getEditorComponent( descriptor );
        if ( editorComponent == null )
        {
            final TypeMapper mapper = mappingRules.getMapper( descriptor );
            editorComponent = componentFactory.createEditor( descriptor, mapper );
            changeObservation.observe( beanMapping.addEditor( descriptor, editorComponent, mapper ) );
        }
        return editorComponent;
    }

    @Nonnull
    public JLabel getLabel( @Nonnull final PropertyDescriptor descriptor,
                            @Nonnull final BeanMapping beanMapping )
    {
        JLabel label = beanMapping.getLabel( descriptor );
        if ( label == null )
        {
            label = componentFactory.createLabel( descriptor );
            beanMapping.addLabel( descriptor, label );
        }
        return label;
    }
}
