/**
 *
 */
package org.formbuilder;

import org.formbuilder.mapping.change.ValueChangeListener;
import org.formbuilder.validation.ValidationMarker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;

/**
 * @author aeremenok 2010
 * @param <C>
 * @param <V>
 */
@NotThreadSafe
public interface TypeMapper<C extends JComponent, V>
{
    void bindChangeListener( @Nonnull C editorComponent,
                             @Nonnull ValueChangeListener<V> changeListener );

    @Nonnull
    C createEditorComponent();

    @Nonnull
    ValidationMarker getValidationMarker();

    @Nullable
    V getValue( @Nonnull C editorComponent );

    @Nonnull
    Class<V> getValueClass();

    void setValue( @Nonnull C editorComponent,
                   @Nullable V value );
}
