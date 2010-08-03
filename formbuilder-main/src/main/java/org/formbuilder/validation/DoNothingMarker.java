package org.formbuilder.validation;

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
public class DoNothingMarker
        implements ValidationMarker
{
    public static final DoNothingMarker INSTANCE = new DoNothingMarker();

    @Override
    public <B> void markViolations( @Nonnull final JComponent editor,
                                    @Nonnull final Set<ConstraintViolation<B>> violations )
    {
    }
}
