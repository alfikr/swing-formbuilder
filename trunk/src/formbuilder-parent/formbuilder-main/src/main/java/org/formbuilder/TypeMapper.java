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

import org.formbuilder.mapping.change.ValueChangeListener;
import org.formbuilder.validation.BackgroundMarker;
import org.formbuilder.validation.DoNothingMarker;
import org.formbuilder.validation.ValidationMarker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;

/**
 * Maps the values of a given type to the given editors.
 *
 * @author aeremenok 2010
 * @param <C> editor component type
 * @param <V> property value type
 */
@NotThreadSafe
public interface TypeMapper<C extends JComponent, V>
{
    /**
     * Propagates the changes of the editor component's state to the form. Particularly, this is needed to validate the
     * changes.
     *
     * @param editorComponent an editor, which state should be observed
     * @param changeListener  you should call {@link ValueChangeListener#onChange()} when editorComponent changes its
     *                        state
     */
    void bindChangeListener( @Nonnull C editorComponent,
                             @Nonnull ValueChangeListener<V> changeListener );

    /**
     * A factory method for editors.
     *
     * @return new instance of editor component
     */
    @Nonnull
    C createEditorComponent();

    /**
     * @return a strategy of marking editors according to validation results. If the validation isn't needed, you can
     *         return a {@link DoNothingMarker}
     * @see ValidationMarker
     * @see BackgroundMarker
     * @see DoNothingMarker
     */
    @Nonnull
    ValidationMarker getValidationMarker();

    /**
     * @param editorComponent an editor to extract its value
     * @return editor's value
     */
    @Nullable
    V getValue( @Nonnull C editorComponent );

    /**
     * @return value class, which should be mapped using this mapper
     */
    @Nonnull
    Class<V> getValueClass();

    /**
     * @param editorComponent an editor to display new value
     * @param value           a new property value
     */
    void setValue( @Nonnull C editorComponent,
                   @Nullable V value );
}
