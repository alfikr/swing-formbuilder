/**
 * 
 */
package org.formbuilder.main;

import javax.swing.JComponent;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public interface Form<B>
{
    B getValue();

    void setValue( B bean );

    JComponent asComponent();
}
