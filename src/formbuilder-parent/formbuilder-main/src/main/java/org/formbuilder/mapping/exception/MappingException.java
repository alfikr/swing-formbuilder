package org.formbuilder.mapping.exception;

import javax.annotation.Nonnull;
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

    public MappingException( @Nonnull final PropertyDescriptor descriptor ) {this.descriptor = descriptor;}

    @Nonnull
    public PropertyDescriptor getDescriptor()
    {
        return descriptor;
    }
}
