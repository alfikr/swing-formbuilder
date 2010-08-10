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

package org.formbuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import org.formbuilder.mapping.MappingRules;
import org.formbuilder.util.Reflection;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 17:54:35
 * @param <B>
 */
@NotThreadSafe
public abstract class GetterMapper<B>
{
    private MappingRules currentMappingRules;
    private Method lastCalledMethod;
    private final InvocationHandler invocationHandler = new InvocationHandler()
    {
        @Override
        public Object invoke( final Object proxy,
                              final Method method,
                              final Object[] args )
        {
            lastCalledMethod = method;
            return Reflection.emptyValue( method );
        }
    };

    protected <T> void mapGetter( @SuppressWarnings( "unused" ) @Nullable final T whatProxyGetterReturned,
                                  @Nonnull final TypeMapper<?, ? extends T> mapper )
    {
        final String propertyName = Reflection.getDescriptor( lastCalledMethod ).getName();
        currentMappingRules.addMapper( propertyName, mapper );
    }

    protected abstract void mapGetters( @Nonnull B beanSample );

    protected void mapGettersToRules( @Nonnull final Class<B> beanClass,
                                      @Nonnull final MappingRules mappingRules )
    {
        currentMappingRules = mappingRules;
        mapGetters( Reflection.createProxy( beanClass, invocationHandler ) );
    }
}
