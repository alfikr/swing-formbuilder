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
    private BeanMapper<B> beanMapper = new GridBagMapper<B>();
    private TypeMappers typeMappers = new TypeMappers();

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
        return new FormImpl<B>( beanClass, beanMapper , typeMappers);
    }

    public Builder<B> mapBeanWith( final BeanMapper<B> beanMapper )
    {
        this.beanMapper = beanMapper;
        return this;
    }

    public Builder<B> use( final TypeMapper typeMapper )
    {
        typeMappers.addMapper( typeMapper );
        return this;
    }

    public Builder<B> mapPropertyWith( final String propertyName,
                                       final PropertyMapper propertyMapper )
    {
        // todo
        return this;
    }
}
