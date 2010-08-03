package org.formbuilder.mapping.exception;

import javax.annotation.Nonnull;

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

    public PropertyNotFoundException( @Nonnull final Class beanClass,
                                      @Nonnull final String propertyName )
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
