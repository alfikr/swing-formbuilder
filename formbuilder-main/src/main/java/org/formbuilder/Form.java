/**
 *
 */
package org.formbuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;

/**
 * @author aeremenok 2010
 * @param <B>
 */
@NotThreadSafe
public interface Form<B>
{
    @Nonnull
    JComponent asComponent();

    @Nonnull
    B getValue();

    void setValue( @Nullable B bean );
}
