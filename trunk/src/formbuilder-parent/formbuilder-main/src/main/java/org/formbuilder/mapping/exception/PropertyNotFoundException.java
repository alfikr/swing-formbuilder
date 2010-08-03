package org.formbuilder.mapping.exception;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

/**
 * @author aeremenok
 *         Date: Aug 3, 2010
 *         Time: 12:06:57 PM
 */
public class PropertyNotFoundException
        extends MappingException
{
    private Method readMethod;

    public PropertyNotFoundException( @Nonnull Method readMethod )
    {
        super( readMethod + " is not a getter method of a bean", null );
        this.readMethod = readMethod;
    }

    @Nonnull
    public Method getReadMethod()
    {
        return readMethod;
    }
}
