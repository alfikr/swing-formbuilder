package org.formbuilder.main.validation;

import javax.swing.*;
import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 14:04:23
 */
public enum DoNothingMarker
        implements ValidationMarker
{
    INSTANCE;

    @Override
    public void markViolations( final JComponent editor,
                                final Set<ConstraintViolation> violations )
    {
    }
}
