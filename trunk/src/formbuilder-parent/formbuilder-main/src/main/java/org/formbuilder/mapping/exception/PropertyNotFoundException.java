package org.formbuilder.mapping.exception;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.beans.PropertyDescriptor;

/**
 * @author aeremenok
 *         Date: Aug 3, 2010
 *         Time: 1:28:52 PM
 */
public class PropertyNotFoundException
        extends MappingException
{
    private final Class beanClass;
    private final String propertyName;

    public PropertyNotFoundException( @Nonnull Class beanClass,
                                      @Nonnull String propertyName )
    {
        super( "Cannot find property " + propertyName + " of class " + beanClass, null );
        this.beanClass = beanClass;
        this.propertyName = propertyName;
    }

    @Nonnull
    public Class getBeanClass()
    {
        return beanClass;
    }

    @Nonnull
    public String getPropertyName()
    {
        return propertyName;
    }
}
