package org.formbuilder.main.map;

import java.beans.PropertyDescriptor;

/**
 * @author aeremenok
 *         Date: 28.07.2010
 *         Time: 12:31:28
 */
public class UnmappedTypeException
        extends MappingException
{
    public UnmappedTypeException( final PropertyDescriptor descriptor )
    {
        super( descriptor );
    }
}
