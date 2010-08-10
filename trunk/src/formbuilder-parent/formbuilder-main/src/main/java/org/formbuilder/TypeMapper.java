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

import org.formbuilder.mapping.change.ValueChangeListener;
import org.formbuilder.validation.ValidationMarker;

/**
 * @author aeremenok 2010
 * @param <C>
 * @param <V>
 */
@NotThreadSafe
public interface TypeMapper<C extends JComponent, V>
{
    void bindChangeListener( @Nonnull C editorComponent,
                             @Nonnull ValueChangeListener<V> changeListener );

    @Nonnull
    C createEditorComponent();

    @Nonnull
    ValidationMarker getValidationMarker();

    @Nullable
    V getValue( @Nonnull C editorComponent );

    @Nonnull
    Class<V> getValueClass();

    void setValue( @Nonnull C editorComponent,
                   @Nullable V value );
}
