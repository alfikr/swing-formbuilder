/**
 *
 */
package org.formbuilder.main.map.type;

import org.formbuilder.main.map.ValueChangeListener;
import org.formbuilder.main.validation.ValidationHighlighter;

import javax.swing.*;

/**
 * @author aeremenok 2010
 */
public interface TypeMapper<C extends JComponent, V>
{
    Class<V> valueClass();

    V getValue( C component );

    void setValue( C component,
                   V value );

    C createComponent();

    void bindChangeListener( C component,
                             ValueChangeListener<V> changeListener );





    ValidationHighlighter getValidationHighlighter();
}
