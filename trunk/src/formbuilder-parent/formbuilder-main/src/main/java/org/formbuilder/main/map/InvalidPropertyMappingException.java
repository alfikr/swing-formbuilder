package org.formbuilder.main.map;

import java.beans.PropertyDescriptor;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 17:12:15
 */
public class InvalidPropertyMappingException
        extends MappingException
{
    private Class expectedType;
    private Class actualType;

    public InvalidPropertyMappingException( final PropertyDescriptor descriptor,
                                            Class expectedType,
                                            Class actualType )
    {
        super( descriptor );
        this.expectedType = expectedType;
        this.actualType = actualType;
    }

    public Class getExpectedType()
    {
        return expectedType;
    }

    public Class getActualType()
    {
        return actualType;
    }
}
