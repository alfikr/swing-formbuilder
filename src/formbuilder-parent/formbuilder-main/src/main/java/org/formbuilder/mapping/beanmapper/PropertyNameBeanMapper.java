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
package org.formbuilder.mapping.beanmapper;

import org.formbuilder.BeanMapper;
import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.mapping.BeanMappingContext;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import java.awt.*;

/** @author aeremenok Date: Aug 3, 2010 Time: 1:09:26 PM */
@NotThreadSafe
public abstract class PropertyNameBeanMapper<B>
        implements BeanMapper<B>
{
// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface BeanMapper ---------------------

    @Override
    public BeanMapping map( @Nonnull final BeanMappingContext<B> context )
    {
        final JPanel wrapper = new JPanel( new BorderLayout() );

        final BeanMapping beanMapping = new BeanMapping( wrapper );
        final PropertyNameContext<B> propertyNameContext = new PropertyNameContext<B>( context, beanMapping );

        wrapper.add( mapBean( propertyNameContext ) );

        return beanMapping;
    }

// -------------------------- OTHER METHODS --------------------------

    protected abstract JComponent mapBean( final PropertyNameContext<B> context );
}
