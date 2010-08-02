package org.main.validation;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import javax.swing.*;
import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 14:04:23
 */
@ThreadSafe
public enum DoNothingMarker
        implements ValidationMarker
{
    INSTANCE;

    @Override
    public void markViolations( @Nonnull final JComponent editor,
                                @Nonnull final Set<ConstraintViolation> violations )
    {
    }
}
