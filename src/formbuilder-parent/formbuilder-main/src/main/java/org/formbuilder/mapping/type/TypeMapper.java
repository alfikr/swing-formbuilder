/**
 *
 */
package org.formbuilder.mapping.type;

import org.formbuilder.mapping.change.ValueChangeListener;
import org.formbuilder.validation.ValidationMarker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;

/**
 * @author aeremenok 2010
 */
@NotThreadSafe
public interface TypeMapper<C extends JComponent, V>
{
    @Nonnull
    Class<V> getValueClass();

    @Nullable
    V getValue( @Nonnull C editorComponent );

    void setValue( @Nonnull C editorComponent,
                   @Nullable V value );

    @Nonnull
    C createEditorComponent();

    void bindChangeListener( @Nonnull C editorComponent,
                             @Nonnull ValueChangeListener<V> changeListener );

    @Nonnull
    ValidationMarker getValidationMarker();
}
