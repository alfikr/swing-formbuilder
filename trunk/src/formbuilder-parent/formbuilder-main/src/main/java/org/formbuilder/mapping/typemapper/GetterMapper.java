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
package org.formbuilder.mapping.typemapper;

import org.formbuilder.mapping.MappingRules;
import org.formbuilder.util.MethodRecorder;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

/** @author aeremenok Date: 30.07.2010 Time: 17:54:35 */
@NotThreadSafe
public abstract class GetterMapper<B>
{
    protected abstract void mapGetters( @Nonnull B beanSample,
                                        @Nonnull GetterMapperContext context );

    public final void mapGettersToRules( @Nonnull final Class<B> beanClass,
                                         @Nonnull final MappingRules mappingRules )
    {
        final MethodRecorder methodRecorder = new MethodRecorder();
        final GetterMapperContext context = new GetterMapperContext( mappingRules, methodRecorder );
        mapGetters( Reflection.createProxy( beanClass, methodRecorder ), context );
    }
}
