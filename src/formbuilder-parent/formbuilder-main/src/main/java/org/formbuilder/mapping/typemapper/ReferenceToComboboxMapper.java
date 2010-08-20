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
package org.formbuilder.mapping.typemapper;

import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.change.ChangeHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collection;
import java.util.Vector;

/** @author aeremenok Date: 30.07.2010 Time: 13:41:29 */
@NotThreadSafe
public abstract class ReferenceToComboboxMapper<R>
        implements TypeMapper<JComboBox, R>
{
    @Override
    public void handleChanges( @Nonnull final JComboBox editorComponent,
                               @Nonnull final ChangeHandler<R> changeHandler )
    {
        editorComponent.addItemListener( new ItemListener()
        {
            @Override
            public void itemStateChanged( final ItemEvent e )
            {
                if ( e.getStateChange() == ItemEvent.SELECTED )
                {
                    changeHandler.onChange();
                }
            }
        } );
    }

    @Nonnull
    @Override
    public JComboBox createEditorComponent()
    {
        return new JComboBox( new Vector<R>( getSuitableData() ) );
    }

    @Nullable
    @SuppressWarnings( {"unchecked"} )
    @Override
    public R getValue( @Nonnull final JComboBox editorComponent )
    {
        return (R) editorComponent.getSelectedItem();
    }

    @Override
    public void setValue( @Nonnull final JComboBox editorComponent,
                          @Nullable final R value )
    {
        editorComponent.setSelectedItem( value );
    }

    @Nonnull
    protected abstract Collection<R> getSuitableData();
}
