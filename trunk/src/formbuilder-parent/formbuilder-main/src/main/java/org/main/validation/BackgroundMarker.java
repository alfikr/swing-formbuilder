package org.main.validation;

import org.main.util.TextUtil;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import javax.validation.ConstraintViolation;
import java.awt.*;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;
import static javax.swing.SwingUtilities.isEventDispatchThread;

/**
 * @author aeremenok
 *         Date: 29.07.2010
 *         Time: 17:42:02
 */
@NotThreadSafe
public enum BackgroundMarker
        implements ValidationMarker
{
    INSTANCE;

    @Override
    public void markViolations( @Nonnull final JComponent editor,
                                @Nonnull final Set<ConstraintViolation> violations )
    {
        checkState( isEventDispatchThread() );

        if ( violations.isEmpty() )
        {
            editor.setBackground( Color.WHITE );
            editor.setToolTipText( null );
        }
        else
        {
            editor.setBackground( Color.PINK );
            editor.setToolTipText( TextUtil.digest( ";\n", violations ) );
        }
    }
}
