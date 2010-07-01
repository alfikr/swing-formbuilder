/**
 * 
 */
package org.formbuilder.main;

import org.formbuilder.main.impl.FormImpl;
import org.formbuilder.main.impl.GridBagMapper;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public class Builder<B>
{
    private final Class<B> beanClass;
    private BeanMapper<B>  beanMapper = new GridBagMapper<B>();

    private Builder( final Class<B> beanClass )
    {
        this.beanClass = beanClass;
    }

    public static <T> Builder<T> from( final Class<T> beanClass )
    {
        return new Builder<T>( beanClass );
    }

    public Form<B> buildForm()
    {
        return new FormImpl<B>( beanClass, beanMapper );
    }

    public Builder<B> mapBeanWith( final BeanMapper<B> beanMapper )
    {
        this.beanMapper = beanMapper;
        return this;
    }

    public Builder<B> mapTypeWith( final Class<?> type, final TypeMapper typeMapper )
    {
        // todo
        return this;
    }

    public Builder<B> mapPropertyWith( final String propertyName, final PropertyMapper propertyMapper )
    {
        // todo
        return this;
    }

}
