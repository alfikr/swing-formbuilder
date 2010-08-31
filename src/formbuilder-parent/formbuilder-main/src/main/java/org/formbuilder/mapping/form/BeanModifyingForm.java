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
package org.formbuilder.mapping.form;

import org.formbuilder.Form;
import org.formbuilder.mapping.BeanMapping;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;

/** @author aeremenok Date: Aug 31, 2010 Time: 12:49:55 PM */
public class BeanModifyingForm<B>
        implements Form<B>
{
// ------------------------------ FIELDS ------------------------------
    @Nullable
    private B bean;
    private final JComponent panel;
    private final BeanMapping beanMapping;

// --------------------------- CONSTRUCTORS ---------------------------

    public BeanModifyingForm( @Nonnull final JComponent panel,
                              @Nonnull final BeanMapping beanMapping )
    {
        this.panel = panel;
        this.beanMapping = beanMapping;
    }

// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface Form ---------------------

    public JComponent asComponent()
    {
        return panel;
    }

    public B getValue()
    {
        beanMapping.setBeanValues( bean );
        return bean;
    }

    public void setValue( @Nullable final B bean )
    {
        this.bean = bean;
    }
}
