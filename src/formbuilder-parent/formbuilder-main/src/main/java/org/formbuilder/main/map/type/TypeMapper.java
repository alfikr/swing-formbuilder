/**
 *
 */
package org.formbuilder.main.map.type;

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
}
