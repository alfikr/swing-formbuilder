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

package org.formbuilder.mapping.change;

import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.PropertyEditor;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Asks a type mapper to handle the changes of editor component using an appropriate change handler
 *
 * @author aeremenok Date: 19.08.2010 Time: 17:57:30
 */
public class ChangeObservation
{
// ------------------------------ FIELDS ------------------------------
    private final boolean doValidation;

// --------------------------- CONSTRUCTORS ---------------------------

    public ChangeObservation( final boolean doValidation )
    {
        this.doValidation = doValidation;
    }

// -------------------------- OTHER METHODS --------------------------

    @SuppressWarnings( {"unchecked"} )
    public void observe( final PropertyEditor propertyEditor )
    {
        final JComponent editorComponent = propertyEditor.getEditorComponent();
        final TypeMapper mapper = propertyEditor.getMapper();
        mapper.handleChanges( editorComponent, createValueChangeListener( propertyEditor ) );
    }

    @Nonnull
    @SuppressWarnings( {"unchecked"} )
    protected ChangeHandler createValueChangeListener( final PropertyEditor propertyEditor )
    {
        return doValidation ? new ValidateOnChange( propertyEditor ) : DoNothingOnChange.INSTANCE;
    }
}
