package org.formbuilder.mapping.exception;

import javax.annotation.Nonnull;
import java.beans.PropertyDescriptor;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 17:12:15
 */
public class InvalidPropertyMappingException
        extends MappingException
{
    private final Class expectedType;
    private final Class actualType;

    public InvalidPropertyMappingException( @Nonnull final PropertyDescriptor descriptor,
                                            @Nonnull final Class expectedType,
                                            @Nonnull final Class actualType )
    {
        super( descriptor );
        this.expectedType = expectedType;
        this.actualType = actualType;
    }

    @Nonnull
    public Class getActualType()
    {
        return actualType;
    }

    @Nonnull
    public Class getExpectedType()
    {
        return expectedType;
    }
}
