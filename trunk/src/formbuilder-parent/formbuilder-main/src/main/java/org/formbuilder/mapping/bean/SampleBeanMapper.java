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

import org.formbuilder.BeanMapper;
import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.mapping.exception.MappingException;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static javax.swing.SwingUtilities.isEventDispatchThread;

/** @author aeremenok 2010 */
@NotThreadSafe
public abstract class SampleBeanMapper<B>
        implements BeanMapper<B>
{
// ------------------------------ FIELDS ------------------------------
    private Method lastCalledMethod;
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
    private BeanMappingContext<B> currentMappingContext;

// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface BeanMapper ---------------------

    @Nonnull
    @Override
    public BeanMapping map( @Nonnull final BeanMappingContext<B> context )
    {
        this.currentMappingContext = context;

        JPanel wrapper = new JPanel( new BorderLayout() );
        this.currentBeanMapping = new BeanMapping( wrapper );

        final B beanSample = Reflection.createProxy( context.getBeanClass(), invocationHandler );
        wrapper.add( mapBean( beanSample ) );

        return this.currentBeanMapping;
    }

// -------------------------- OTHER METHODS --------------------------

    @Nonnull
    protected JComponent editor( @SuppressWarnings( "unused" ) @Nullable final Object whatProxyGetterReturned )
            throws
            MappingException
    {
        // todo error messages
        final BeanMappingContext<B> context = checkNotNull( currentMappingContext );
        final BeanMapping beanMapping = checkNotNull( currentBeanMapping );
        final Method readMethod = checkNotNull( lastCalledMethod );

        final PropertyDescriptor descriptor = Reflection.getDescriptor( readMethod );
        return context.getEditor( descriptor, beanMapping );
    }

    @Nonnull
    protected JLabel label( @SuppressWarnings( "unused" ) @Nullable final Object whatProxyGetterReturned )
            throws
            MappingException
    {
        final BeanMappingContext<B> context = checkNotNull( currentMappingContext );
        final Method readMethod = checkNotNull( lastCalledMethod );
        final BeanMapping beanMapping = checkNotNull( currentBeanMapping );
        return context.getLabel( Reflection.getDescriptor( readMethod ), beanMapping );
    }

    @Nonnull
    protected abstract JComponent mapBean( @Nonnull B beanSample );
}
