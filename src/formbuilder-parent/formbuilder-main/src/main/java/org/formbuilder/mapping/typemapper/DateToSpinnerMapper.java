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

import java.util.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.change.ValueChangeListener;
import org.formbuilder.validation.BackgroundMarker;
import org.formbuilder.validation.ValidationMarker;

/**
 * @author aeremenok
 *         Date: 28.07.2010
 *         Time: 11:57:47
 */
@NotThreadSafe
public class DateToSpinnerMapper
        implements TypeMapper<JSpinner, Date>
{
    @Override
    public void bindChangeListener( @Nonnull final JSpinner editorComponent,
                                    @Nonnull final ValueChangeListener<Date> dateValueChangeListener )
    {
        editorComponent.addChangeListener( new ChangeListener()
        {
            @Override
            public void stateChanged( final ChangeEvent e )
            {
                dateValueChangeListener.onChange();
            }
        } );
    }

    @Nonnull
    @Override
    public JSpinner createEditorComponent()
    {
        return new JSpinner( new SpinnerDateModel() );
    }

    @Nonnull
    @Override
    public ValidationMarker getValidationMarker()
    {
        return BackgroundMarker.INSTANCE;
    }

    @Nullable
    @Override
    public Date getValue( @Nonnull final JSpinner editorComponent )
    {
        return (Date) editorComponent.getValue();
    }

    @Nonnull
    @Override
    public Class<Date> getValueClass()
    {
        return Date.class;
    }

    @Override
    public void setValue( @Nonnull final JSpinner editorComponent,
                          @Nullable Date value )
    {
        if ( value == null )
        {
            value = new Date( 0 );
        }
        editorComponent.setValue( value );
    }
}
