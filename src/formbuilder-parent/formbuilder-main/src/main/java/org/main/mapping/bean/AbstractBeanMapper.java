/**
 *
 */
package org.main.mapping.bean;

import org.apache.log4j.Logger;
import org.main.mapping.BeanMapping;
import org.main.mapping.MappingRules;
import org.main.mapping.ValueChangeListener;
import org.main.mapping.exception.MappingException;
import org.main.mapping.metadata.CombinedMetaData;
import org.main.mapping.metadata.MetaData;
import org.main.mapping.type.TypeMapper;
import org.main.validation.ValidateChangedValue;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.beans.PropertyDescriptor;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public abstract class AbstractBeanMapper<B>
        implements BeanMapper<B>
{
    private static final Logger log = Logger.getLogger( AbstractBeanMapper.class );
    protected final MetaData metaData = createMetaData();

    @Nonnull
    protected JLabel createLabel( @Nonnull final BeanMapping beanMapping,
                                  @Nonnull final PropertyDescriptor descriptor )
    {
        final JLabel label = new JLabel( metaData.getTitle( descriptor ) );
        label.setName( descriptor.getName() );
        beanMapping.addLabel( descriptor, label );
        return label;
    }

    @SuppressWarnings( {"unchecked"} )
    @Nonnull
    protected JComponent createEditor( @Nonnull final PropertyDescriptor descriptor,
                                       @Nonnull final MappingRules mappingRules,
                                       @Nonnull final BeanMapping beanMapping )
            throws
            MappingException
    {
        final TypeMapper mapper = mappingRules.getMapper( descriptor );
        final JComponent editor = mapper.createEditorComponent();
        editor.setEnabled( isEditable( descriptor ) );
        editor.setName( descriptor.getName() );

        mapper.bindChangeListener( editor, createValueChangeListener( descriptor, mapper, editor ) );

        beanMapping.addEditor( descriptor, editor, mapper );

        return editor;
    }

    @Nonnull
    @SuppressWarnings( {"unchecked"} )
    protected <V> ValueChangeListener<V> createValueChangeListener( @Nonnull final PropertyDescriptor descriptor,
                                                                    @Nonnull final TypeMapper<?, ? extends V> mapper,
                                                                    @Nonnull final JComponent editor )
    {
        return new ValidateChangedValue( mapper, editor, descriptor );
    }

    protected void handleMappingException( @Nonnull final MappingException e )
    {
        // todo implement different strategies
        log.warn( "Cannot find mapper for method " + e.getDescriptor() );
    }

    @Nonnull
    protected MetaData createMetaData()
    {
        return new CombinedMetaData();
    }

    protected boolean isEditable( @Nonnull final PropertyDescriptor descriptor )
    {
        final boolean hasWriteMethod = descriptor.getWriteMethod() != null;
        return hasWriteMethod && !metaData.isReadOnly( descriptor );
    }
}
