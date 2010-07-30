package org.formbuilder.main.validation;

import com.google.common.base.Function;
import com.google.common.base.Joiner;

import javax.swing.*;
import javax.validation.ConstraintViolation;
import java.awt.*;
import java.util.Set;

import static com.google.common.collect.Iterables.transform;

/**
 * @author aeremenok
 *         Date: 29.07.2010
 *         Time: 17:42:02
 */
public enum BackgroundMarker
        implements ValidationMarker
{
    INSTANCE;

    @Override
    public void markViolations( final JComponent editor,
                                final Set<ConstraintViolation> violations )
    {
        assert SwingUtilities.isEventDispatchThread();

        if ( violations.isEmpty() )
        {
            editor.setBackground( Color.WHITE );
            editor.setToolTipText( null );
            return;
        }

        editor.setBackground( Color.PINK );
        editor.setToolTipText( digest( violations ) );
    }

    protected String digest( final Set<ConstraintViolation> violations )
    {
        return Joiner.on( ";\n" ).join( transform( violations, TO_MESSAGE ) );
    }

    Function<ConstraintViolation, String> TO_MESSAGE = new Function<ConstraintViolation, String>()
    {
        @Override
        public String apply( final ConstraintViolation from )
        {
            return from.getMessage();
        }
    };
}
