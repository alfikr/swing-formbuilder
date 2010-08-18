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
package org.formbuilder.mapping.form;

import org.formbuilder.Form;
import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;

import static com.google.common.base.Preconditions.checkState;
import static javax.swing.SwingUtilities.isEventDispatchThread;

/**
 * A {@link Form} that allocates a new instance of beanmapper each time it is requested for a changed value.
 *
 * @author aeremenok 2010
 */
public class BeanRelpicatingForm<B>
        implements Form<B>
{
    private final BeanMapping beanMapping;
    private final Class<B> beanClass;

    public BeanRelpicatingForm( @Nonnull final BeanMapping beanMapping,
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

    /** @return a new beanmapper instance with changed values */
    @Nonnull
    @Override
    public B getValue()
    {
        final B newBean = Reflection.newInstance( beanClass );
        beanMapping.setBeanValues( newBean );
        return newBean;
    }

    /**
     * Propagates changes to mapping, but doesn't remember a given beanmapper instance.
     *
     * @param bean a value source
     */
    @Override
    public void setValue( @Nullable final B bean )
    {
        checkState( isEventDispatchThread() );
        beanMapping.setComponentValues( bean );
    }
}
