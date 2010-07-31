/**
 *
 */
package org.formbuilder.main.map.type;

import org.formbuilder.main.map.ValueChangeListener;
import org.formbuilder.main.validation.ValidationMarker;

import javax.swing.*;

/**
 * @author aeremenok 2010
 */
public interface TypeMapper<C extends JComponent, V>
{
    Class<V> getValueClass();

    V getValue( C editorComponent );

    void setValue( C editorComponent,
                   V value );

    C createEditorComponent();

    void bindChangeListener( C editorComponent,
                             ValueChangeListener<V> changeListener );

    ValidationMarker getValidationMarker();
}
