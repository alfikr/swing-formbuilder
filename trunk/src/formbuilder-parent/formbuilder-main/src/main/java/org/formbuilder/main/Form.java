/**
 *
 */
package org.formbuilder.main;

import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.JComponent;

/**
 * @author aeremenok 2010
 * @param <B>
 */
@NotThreadSafe
public interface Form<B>
{
    B getValue();

    void setValue( B bean );

    JComponent asComponent();
}
