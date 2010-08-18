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
package org.formbuilder.mapping.beanmapper;

import org.formbuilder.BeanMapper;
import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.mapping.BeanMappingContext;
import org.formbuilder.util.MethodRecorder;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import java.awt.*;

/** @author aeremenok 2010 */
@NotThreadSafe
public abstract class SampleBeanMapper<B>
        implements BeanMapper<B>
{
// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface BeanMapper ---------------------

    @Nonnull
    @Override
    public BeanMapping map( @Nonnull final BeanMappingContext<B> context )
    {
        final JPanel wrapper = new JPanel( new BorderLayout() );
        final BeanMapping beanMapping = new BeanMapping( wrapper );

        final MethodRecorder methodRecorder = new MethodRecorder();
        final SampleContext<B> sampleContext = new SampleContext<B>( context, beanMapping, methodRecorder );
        final B beanSample = Reflection.createProxy( context.getBeanClass(), methodRecorder );

        wrapper.add( mapBean( beanSample, sampleContext ) );

        return beanMapping;
    }

// -------------------------- OTHER METHODS --------------------------

    @Nonnull
    protected abstract JComponent mapBean( @Nonnull final B sample,
                                           @Nonnull final SampleContext<B> context );
}
