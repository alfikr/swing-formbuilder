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

    V getValue( C component );

    void setValue( C component,
                   V value );

    C createComponent();

    void bindChangeListener( C component,
                             ValueChangeListener<V> changeListener );

    ValidationMarker getValidationHighlighter();
}
