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
package org.formbuilder.mapping.bean;

import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.mapping.MappingRules;
import org.formbuilder.mapping.exception.MappingException;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static javax.swing.SwingUtilities.isEventDispatchThread;

/**
 * @author aeremenok 2010
 * @param <B>
 */
@NotThreadSafe
public abstract class SampleBeanMapper<B>
        extends AbstractBeanMapper<B>
{
    private Method lastCalledMethod;
    private MappingRules currentMappingRules;
    private boolean doValidation;
    private BeanMapping currentBeanMapping;
    private final InvocationHandler invocationHandler = new InvocationHandler()
    {
        @Override
        public Object invoke( final Object proxy,
                              final Method method,
                              final Object[] args )
        {
            checkState( isEventDispatchThread() );

            lastCalledMethod = method;
            return Reflection.emptyValue( method );
        }
    };

    @Nonnull
    @Override
    public BeanMapping map( @Nonnull final Class<B> beanClass,
                            @Nonnull final MappingRules mappingRules,
                            final boolean doValidation )
    {
        this.currentMappingRules = mappingRules;
        this.doValidation = doValidation;
        this.currentBeanMapping = new BeanMapping();

        final B beanSample = Reflection.createProxy( beanClass, invocationHandler );
        this.currentBeanMapping.setPanel( mapBean( beanSample ) );
        return this.currentBeanMapping;
    }

    @Nonnull
    protected JComponent editor( @SuppressWarnings( "unused" ) @Nullable final Object whatProxyGetterReturned )
            throws
            MappingException
    {
        // todo error messages
        final MappingRules mappingRules = checkNotNull( currentMappingRules );
        final Method readMethod = checkNotNull( lastCalledMethod );
        final BeanMapping beanMapping = checkNotNull( currentBeanMapping );

        return createEditor( Reflection.getDescriptor( readMethod ), mappingRules, beanMapping, doValidation );
    }

    @Nonnull
    protected JLabel label( @SuppressWarnings( "unused" ) @Nullable final Object whatProxyGetterReturned )
            throws
            MappingException
    {
        final Method readMethod = checkNotNull( lastCalledMethod );
        final BeanMapping beanMapping = checkNotNull( currentBeanMapping );
        return createLabel( beanMapping, Reflection.getDescriptor( readMethod ) );
    }

    @Nonnull
    protected abstract JComponent mapBean( @Nonnull B beanSample );
}
