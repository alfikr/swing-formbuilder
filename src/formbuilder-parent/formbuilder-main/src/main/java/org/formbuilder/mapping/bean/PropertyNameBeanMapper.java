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

import static com.google.common.base.Preconditions.checkNotNull;

import java.beans.PropertyDescriptor;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.JComponent;
import javax.swing.JLabel;

import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.mapping.MappingRules;
import org.formbuilder.mapping.exception.MappingException;
import org.formbuilder.util.Reflection;

/**
 * @author aeremenok
 *         Date: Aug 3, 2010
 *         Time: 1:09:26 PM
 * @param <B>
 */
@NotThreadSafe
public abstract class PropertyNameBeanMapper<B>
        extends AbstractBeanMapper<B>
{
    private Class<B> beanClass;
    private MappingRules currentMappingRules;
    private boolean doValidation;
    private BeanMapping currentBeanMapping;

    @Override
    public BeanMapping map( @Nonnull final Class<B> beanClass,
                            @Nonnull final MappingRules mappingRules,
                            final boolean doValidation )
    {
        this.beanClass = beanClass;
        this.currentMappingRules = mappingRules;
        this.doValidation = doValidation;
        this.currentBeanMapping = new BeanMapping();

        this.currentBeanMapping.setPanel( mapBean() );
        return this.currentBeanMapping;
    }

    @Nonnull
    protected JComponent editor( @Nonnull final String propertyName )
            throws
            MappingException
    {
        // todo error messages
        final MappingRules mappingRules = checkNotNull( currentMappingRules );
        final BeanMapping beanMapping = checkNotNull( currentBeanMapping );

        final PropertyDescriptor descriptor = Reflection.getDescriptor( beanClass, propertyName );

        return createEditor( descriptor, mappingRules, beanMapping, doValidation );
    }

    @Nonnull
    protected JLabel label( @Nonnull final String propertyName )
            throws
            MappingException
    {
        final BeanMapping beanMapping = checkNotNull( currentBeanMapping );
        final PropertyDescriptor descriptor = Reflection.getDescriptor( beanClass, propertyName );
        return createLabel( beanMapping, descriptor );
    }

    protected abstract JComponent mapBean();
}
