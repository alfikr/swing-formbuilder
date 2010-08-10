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
package org.formbuilder.mapping;

import static com.google.common.base.Preconditions.checkState;
import static javax.swing.SwingUtilities.isEventDispatchThread;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JComponent;

import org.formbuilder.Form;
import org.formbuilder.util.Reflection;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public class ValueRelpicatingForm<B>
        implements Form<B>
{
    private final BeanMapping beanMapping;
    private final Class<B> beanClass;

    public ValueRelpicatingForm( @Nonnull final BeanMapping beanMapping,
                                 @Nonnull final Class<B> beanClass )
    {
        this.beanMapping = beanMapping;
        this.beanClass = beanClass;
    }

    @Nonnull
    @Override
    public JComponent asComponent()
    {
        return beanMapping.getPanel();
    }

    @Nonnull
    @Override
    public B getValue()
    {
        final B newBean = Reflection.newInstance( beanClass );
        beanMapping.setBeanValues( newBean );
        return newBean;
    }

    @Override
    public void setValue( @Nullable final B bean )
    {
        checkState( isEventDispatchThread() );
        beanMapping.setComponentValues( bean );
    }
}
