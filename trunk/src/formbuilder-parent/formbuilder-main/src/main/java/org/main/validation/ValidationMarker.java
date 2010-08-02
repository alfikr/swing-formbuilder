package org.main.validation;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * @author aeremenok
 *         Date: 29.07.2010
 *         Time: 17:40:48
 */
@NotThreadSafe
public interface ValidationMarker
{
    void markViolations( @Nonnull final JComponent editor,
                         @Nonnull final Set<ConstraintViolation> violations );
}
