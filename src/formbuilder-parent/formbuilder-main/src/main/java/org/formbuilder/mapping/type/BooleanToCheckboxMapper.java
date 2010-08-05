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

package org.formbuilder.mapping.type;

import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.change.ValueChangeListener;
import org.formbuilder.validation.BackgroundMarker;
import org.formbuilder.validation.ValidationMarker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 13:38:16
 */
@NotThreadSafe
public class BooleanToCheckboxMapper
        implements TypeMapper<JCheckBox, Boolean>
{
    @Override
    public void bindChangeListener( @Nonnull final JCheckBox editorComponent,
                                    @Nonnull final ValueChangeListener<Boolean> booleanValueChangeListener )
    {
        editorComponent.addChangeListener( new ChangeListener()
        {
            @Override
            public void stateChanged( final ChangeEvent e )
            {
                booleanValueChangeListener.onChange();
            }
        } );
    }

    @Nonnull
    @Override
    public JCheckBox createEditorComponent()
    {
        return new JCheckBox();
    }

    @Nonnull
    @Override
    public ValidationMarker getValidationMarker()
    {
        return BackgroundMarker.INSTANCE;
    }

    @Override
    public Boolean getValue( @Nonnull final JCheckBox editorComponent )
    {
        return editorComponent.isSelected();
    }

    @Nonnull
    @Override
    public Class<Boolean> getValueClass()
    {
        return Boolean.class;
    }

    @Override
    public void setValue( @Nonnull final JCheckBox editorComponent,
                          @Nullable final Boolean value )
    {
        editorComponent.setSelected( Boolean.TRUE.equals( value ) );
    }
}
