/*
 * Copyright (C) 2010 Andrey Yeremenok (eav1986__at__gmail__com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

/**
 *
 */
package org.formbuilder;

import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.mapping.ValueRelpicatingForm;
import org.formbuilder.mapping.MappingRules;
import org.formbuilder.mapping.bean.GridBagMapper;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author aeremenok 2010
 * @param <B>
 */
@NotThreadSafe
public class FormBuilder<B>
{
    private final Class<B> beanClass;
    private final MappingRules mappingRules = new MappingRules();
    @Nonnull
    private BeanMapper<B> beanMapper = new GridBagMapper<B>();
    private boolean doValidation = true;

    private FormBuilder( final Class<B> beanClass )
    {
        this.beanClass = beanClass;
    }

    @Nonnull
    public static <T> FormBuilder<T> map( @Nonnull final Class<T> beanClass )
    {
        return new FormBuilder<T>( checkNotNull( beanClass ) );
    }

    @Nonnull
    public Form<B> buildForm()
    {
        final BeanMapping mapping = beanMapper.map( beanClass, mappingRules, doValidation );
        return new ValueRelpicatingForm<B>( mapping, beanClass );
    }

    @Nonnull
    public FormBuilder<B> use( @Nonnull final TypeMapper... typeMappers )
    {
        for ( TypeMapper typeMapper : typeMappers )
        {
            this.mappingRules.addMapper( checkNotNull( typeMapper ) );
        }
        return this;
    }

    @Nonnull
    public FormBuilder<B> useForGetters( @Nonnull final GetterMapper<B> getterMapper )
    {
        getterMapper.mapGettersToRules( beanClass, mappingRules );
        return this;
    }

    @Nonnull
    public FormBuilder<B> useForProperty( @Nonnull final String propertyName,
                                          @Nonnull final TypeMapper propertyMapper )
    {
        this.mappingRules.addMapper( checkNotNull( propertyName ), checkNotNull( propertyMapper ) );
        return this;
    }

    @Nonnull
    public FormBuilder<B> with( @Nonnull final BeanMapper<B> beanMapper )
    {
        this.beanMapper = checkNotNull( beanMapper );
        return this;
    }

    @Nonnull
    public FormBuilder<B> doValidation( final boolean doValidation )
    {
        this.doValidation = doValidation;
        return this;
    }
}
