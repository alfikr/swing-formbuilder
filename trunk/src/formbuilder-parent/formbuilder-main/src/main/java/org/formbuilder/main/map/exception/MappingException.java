package org.formbuilder.main.map.exception;

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

    public MappingException( final PropertyDescriptor descriptor ) {this.descriptor = descriptor;}

    public PropertyDescriptor getDescriptor()
    {
        return descriptor;
    }
}
