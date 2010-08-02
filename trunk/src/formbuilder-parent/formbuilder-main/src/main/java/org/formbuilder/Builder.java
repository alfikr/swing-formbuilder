/**
 *
 */
package org.formbuilder;

import org.formbuilder.mapping.GetterMapper;
import org.formbuilder.mapping.MappingRules;
import org.formbuilder.mapping.bean.BeanMapper;
import org.formbuilder.mapping.bean.GridBagMapper;
import org.formbuilder.mapping.type.TypeMapper;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author aeremenok 2010
 * @param <B>
 */
@NotThreadSafe
public class Builder<B>
{
    private final Class<B> beanClass;
    private final MappingRules mappingRules = new MappingRules();
    @Nonnull
    private BeanMapper<B> beanMapper = new GridBagMapper<B>();

    private Builder( final Class<B> beanClass )
    {
        this.beanClass = beanClass;
    }

    @Nonnull
    public static <T> Builder<T> map( @Nonnull final Class<T> beanClass )
    {
        return new Builder<T>( checkNotNull( beanClass ) );
    }

    @Nonnull
    public Form<B> buildForm()
    {
        return new FormImpl<B>( beanClass, beanMapper, mappingRules );
    }

    @Nonnull
    public Builder<B> with( @Nonnull final BeanMapper<B> beanMapper )
    {
        this.beanMapper = checkNotNull( beanMapper );
        return this;
    }

    @Nonnull
    public Builder<B> use( @Nonnull final TypeMapper typeMapper )
    {
        this.mappingRules.addMapper( checkNotNull( typeMapper ) );
        return this;
    }

    @Nonnull
    public Builder<B> useForProperty( @Nonnull final String propertyName,
                                      @Nonnull final TypeMapper propertyMapper )
    {
        this.mappingRules.addMapper( checkNotNull( propertyName ), checkNotNull( propertyMapper ) );
        return this;
    }

    @Nonnull
    public Builder<B> useForGetters( @Nonnull final GetterMapper<B> getterMapper )
    {
        getterMapper.mapGettersToRules( beanClass, mappingRules );
        return this;
    }
}
