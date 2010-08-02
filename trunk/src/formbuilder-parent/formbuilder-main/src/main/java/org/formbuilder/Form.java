/**
 *
 */
package org.formbuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.JComponent;

/**
 * @author aeremenok 2010
 * @param <B>
 */
@NotThreadSafe
public interface Form<B>
{
    @Nonnull
    B getValue();

    void setValue( @Nullable B bean );

    @Nonnull
    JComponent asComponent();
}
