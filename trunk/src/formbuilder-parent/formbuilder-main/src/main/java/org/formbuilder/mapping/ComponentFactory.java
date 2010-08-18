package org.formbuilder.mapping;

import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.change.EmptyChangeListener;
import org.formbuilder.mapping.change.ValueChangeListener;
import org.formbuilder.mapping.exception.MappingException;
import org.formbuilder.mapping.metadata.MetaData;
import org.formbuilder.validation.ValidateChangedValue;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.beans.PropertyDescriptor;

import static com.google.common.base.Preconditions.checkNotNull;

/** @author aeremenok Date: Aug 18, 2010 Time: 1:52:39 PM */
public class ComponentFactory
{
// ------------------------------ FIELDS ------------------------------

    protected final MetaData metaData;

// --------------------------- CONSTRUCTORS ---------------------------

    public ComponentFactory( @Nonnull final MetaData metaData )
    {
        this.metaData = metaData;
    }

// -------------------------- OTHER METHODS --------------------------

    @SuppressWarnings( {"unchecked"} )
    @Nonnull
    public JComponent createEditor( @Nonnull final PropertyDescriptor descriptor,
                                    @Nonnull final TypeMapper mapper,
                                    final boolean doValidation )
            throws
            MappingException
    {
        final JComponent editor = checkNotNull( mapper.createEditorComponent() );
        editor.setEnabled( isEditable( descriptor ) );
        editor.setName( descriptor.getName() );

        mapper.bindChangeListener( editor, createValueChangeListener( descriptor, mapper, editor, doValidation ) );

        return editor;
    }

    protected boolean isEditable( @Nonnull final PropertyDescriptor descriptor )
    {
        final boolean hasWriteMethod = descriptor.getWriteMethod() != null;
        return hasWriteMethod && !metaData.isReadOnly( descriptor );
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

    @Nonnull
    public JLabel createLabel( @Nonnull final PropertyDescriptor descriptor,
                               @Nonnull final TypeMapper mapper,
                               @Nonnull final boolean doValidation )
    {
        final JLabel label = new JLabel( metaData.getTitle( descriptor ) );
        label.setName( descriptor.getName() );
        return label;
    }
}
