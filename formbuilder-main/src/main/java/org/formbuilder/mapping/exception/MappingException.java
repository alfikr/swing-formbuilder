package org.formbuilder.mapping.exception;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.beans.PropertyDescriptor;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 17:04:09
 */
public class MappingException
        extends RuntimeException
{
    private final PropertyDescriptor descriptor;

    public MappingException( @Nonnull String message,
                             @Nullable final PropertyDescriptor descriptor )
    {
        super( message );
        this.descriptor = descriptor;
    }

    @Nullable
    public PropertyDescriptor getDescriptor()
    {
        return descriptor;
    }
}
