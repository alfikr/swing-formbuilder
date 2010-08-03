/**
 *
 */
package org.formbuilder.mapping.bean;

import org.formbuilder.BeanMapper;
import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.mapping.MappingRules;
import org.formbuilder.mapping.change.EmptyChangeListener;
import org.formbuilder.mapping.change.ValueChangeListener;
import org.formbuilder.mapping.exception.MappingException;
import org.formbuilder.mapping.metadata.CombinedMetaData;
import org.formbuilder.mapping.metadata.MetaData;
import org.formbuilder.validation.ValidateChangedValue;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import java.beans.PropertyDescriptor;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author aeremenok 2010
 * @param <B>
 */
@NotThreadSafe
public abstract class AbstractBeanMapper<B>
        implements BeanMapper<B>
{
    protected final MetaData metaData = createMetaData();

    @SuppressWarnings( {"unchecked"} )
    @Nonnull
    protected JComponent createEditor( @Nonnull final PropertyDescriptor descriptor,
                                       @Nonnull final MappingRules mappingRules,
                                       @Nonnull final BeanMapping beanMapping,
                                       final boolean doValidation )
            throws
            MappingException
    {
        final TypeMapper mapper = mappingRules.getMapper( descriptor );

        final JComponent editor = checkNotNull( mapper.createEditorComponent() );
        editor.setEnabled( isEditable( descriptor ) );
        editor.setName( descriptor.getName() );

        mapper.bindChangeListener( editor, createValueChangeListener( descriptor, mapper, editor, doValidation ) );

        beanMapping.addEditor( descriptor, editor, mapper );

        return editor;
    }

    @Nonnull
    protected JLabel createLabel( @Nonnull final BeanMapping beanMapping,
                                  @Nonnull final PropertyDescriptor descriptor )
    {
        final JLabel label = new JLabel( metaData.getTitle( descriptor ) );
        label.setName( descriptor.getName() );
        beanMapping.addLabel( descriptor, label );
        return label;
    }

    @Nonnull
    protected MetaData createMetaData()
    {
        return new CombinedMetaData();
    }

    @Nonnull
    @SuppressWarnings( {"unchecked"} )
    protected <V> ValueChangeListener<V> createValueChangeListener( @Nonnull final PropertyDescriptor descriptor,
                                                                    @Nonnull final TypeMapper<?, ? extends V> mapper,
                                                                    @Nonnull final JComponent editor,
                                                                    final boolean doValidation )
    {
        return doValidation ? new ValidateChangedValue( mapper, editor, descriptor ) : EmptyChangeListener.INSTANCE;
    }

    protected void handleMappingException( @Nonnull final MappingException e )
    {
        // skip
    }

    protected boolean isEditable( @Nonnull final PropertyDescriptor descriptor )
    {
        final boolean hasWriteMethod = descriptor.getWriteMethod() != null;
        return hasWriteMethod && !metaData.isReadOnly( descriptor );
    }
}
