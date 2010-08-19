package org.formbuilder.mapping;

import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.exception.MappingException;
import org.formbuilder.mapping.metadata.MetaData;

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
                                    @Nonnull final TypeMapper mapper )
            throws
            MappingException
    {
        final JComponent editor = checkNotNull( mapper.createEditorComponent() );
        editor.setEnabled( isEditable( descriptor ) );
        editor.setName( descriptor.getName() );
        return editor;
    }

    protected boolean isEditable( @Nonnull final PropertyDescriptor descriptor )
    {
        final boolean hasWriteMethod = descriptor.getWriteMethod() != null;
        return hasWriteMethod && !metaData.isReadOnly( descriptor );
    }

    @Nonnull
    public JLabel createLabel( @Nonnull final PropertyDescriptor descriptor )
    {
        final JLabel label = new JLabel( metaData.getTitle( descriptor ) );
        label.setName( descriptor.getName() );
        return label;
    }
}
