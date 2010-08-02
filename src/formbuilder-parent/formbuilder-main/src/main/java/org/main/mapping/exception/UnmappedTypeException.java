package org.main.mapping.exception;

import javax.annotation.Nonnull;
import java.beans.PropertyDescriptor;

/**
 * @author aeremenok
 *         Date: 28.07.2010
 *         Time: 12:31:28
 */
public class UnmappedTypeException
        extends MappingException
{
    public UnmappedTypeException( @Nonnull final PropertyDescriptor descriptor )
    {
        super( descriptor );
    }
}
