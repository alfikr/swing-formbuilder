package org.formbuilder.mapping.exception;

import org.formbuilder.TypeMapper;

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
    private final TypeMapper mapper;

    public InvalidPropertyMappingException( @Nonnull final PropertyDescriptor descriptor,
                                            @Nonnull final TypeMapper mapper )
    {
        super( message( descriptor, mapper ), descriptor );
        this.expectedType = descriptor.getPropertyType();
        this.mapper = mapper;
    }

    private static String message( final PropertyDescriptor descriptor,
                                   final TypeMapper mapper )
    {
        return "Property " + descriptor.getName() + " of type " + descriptor.getPropertyType() + " cannot be mapped using " + mapper + " since it maps " + mapper
                .getValueClass();
    }

    @Nonnull
    public Class getActualType()
    {
        return mapper.getValueClass();
    }

    @Nonnull
    public Class getExpectedType()
    {
        return expectedType;
    }
}
