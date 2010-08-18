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
package org.formbuilder.mapping.bean;

import org.formbuilder.BeanMapper;
import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.mapping.exception.MappingException;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyDescriptor;

import static com.google.common.base.Preconditions.checkNotNull;

/** @author aeremenok Date: Aug 3, 2010 Time: 1:09:26 PM */
@NotThreadSafe
public abstract class PropertyNameBeanMapper<B>
        implements BeanMapper<B>
{
// ------------------------------ FIELDS ------------------------------
    private BeanMapping currentBeanMapping;
    private BeanMappingContext<B> currentMappingContext;

// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface BeanMapper ---------------------

    @Override
    public BeanMapping map( @Nonnull final BeanMappingContext<B> context )
    {
        this.currentMappingContext = context;

        JPanel wrapper = new JPanel( new BorderLayout() );
        this.currentBeanMapping = new BeanMapping( wrapper );
        wrapper.add( mapBean() );

        return this.currentBeanMapping;
    }

// -------------------------- OTHER METHODS --------------------------

    @Nonnull
    protected JComponent editor( @Nonnull final String propertyName )
            throws
            MappingException
    {
        // todo error messages
        final BeanMapping beanMapping = checkNotNull( currentBeanMapping );
        final BeanMappingContext<B> context = checkNotNull( currentMappingContext );

        final PropertyDescriptor descriptor = Reflection.getDescriptor( context.getBeanClass(), propertyName );

        return context.getEditor( descriptor, beanMapping );
    }

    @Nonnull
    protected JLabel label( @Nonnull final String propertyName )
            throws
            MappingException
    {
        final BeanMapping beanMapping = checkNotNull( currentBeanMapping );
        final BeanMappingContext<B> context = checkNotNull( currentMappingContext );

        final PropertyDescriptor descriptor = Reflection.getDescriptor( context.getBeanClass(), propertyName );
        return context.getLabel( descriptor, beanMapping );
    }

    protected abstract JComponent mapBean();
}
