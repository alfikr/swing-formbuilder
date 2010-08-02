package org.formbuilder.util;

import com.google.common.base.Function;
import com.google.common.base.Joiner;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.ConstraintViolation;
import java.util.Set;

import static com.google.common.collect.Iterables.transform;

/**
 * @author aeremenok
 *         Date: 28.07.2010
 *         Time: 13:29:57
 */
public class TextUtil
{
    public static final Function<ConstraintViolation, String> TO_MESSAGE = new Function<ConstraintViolation, String>()
    {
        @Override
        public String apply( @Nullable final ConstraintViolation violation )
        {
            return violation == null ? "" : violation.getMessage();
        }
    };

    public static String capitalize( String s )
    {
        return s.substring( 0, 1 ).toUpperCase() + s.substring( 1 );
    }

    @Nonnull
    public static String digest( @Nonnull String delimiter,
                                 @Nonnull final Set<ConstraintViolation> violations )
    {
        return Joiner.on( delimiter ).join( transform( violations, TO_MESSAGE ) );
    }
}
