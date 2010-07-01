/**
 * 
 */
package org.formbuilder.main;

import javax.swing.JComponent;

/**
 * @author aeremenok 2010
 * @param <C>
 * @param <B>
 */
public interface PropertyBinder<C extends JComponent, B>
{
    void propertyToComponent( C c, B bean );

    void componentToProperty( C c, B bean );
}
