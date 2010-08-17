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
package org.formbuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.JComponent;

/**
 * Wraps the editor panel and holds the ediable bean instance
 *
 * @author aeremenok 2010
 * @param <B> bean type
 */
@NotThreadSafe
public interface Form<B>
{
    /** @return the form component */
    @Nonnull
    JComponent asComponent();

    /**
     * Converts values from the editor components to bean properties
     *
     * @return changed bean instance
     */
    @Nullable
    B getValue();

    /**
     * Converts values from bean properties to the editor components
     *
     * @param bean a value source
     */
    void setValue( @Nullable B bean );
}
