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
package org.formbuilder.mapping.typemapper.impl;

import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.change.ChangeHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** @author aeremenok Date: 30.07.2010 Time: 14:08:34 */
@NotThreadSafe
public abstract class CollectionToJListMapper<R, CT extends Collection>
        implements TypeMapper<JList, CT>
{
    @Override
    public void handleChanges( @Nonnull final JList editorComponent,
                               @Nonnull final ChangeHandler changeHandler )
    {
        editorComponent.getSelectionModel().addListSelectionListener( new ListSelectionListener()
        {
            @Override
            public void valueChanged( final ListSelectionEvent e )
            {
                if ( !e.getValueIsAdjusting() )
                {
                    changeHandler.onChange();
                }
            }
        } );
    }

    @Nonnull
    @Override
    public JList createEditorComponent()
    {
        return new JList( new ImmutableListModel<R>( getSuitableData() ) );
    }

    @Nullable
    @SuppressWarnings( {"unchecked"} )
    @Override
    public CT getValue( @Nonnull final JList editorComponent )
    {
        final Object[] vs = editorComponent.getSelectedValues();
        final List<R> selectedValueList = new ArrayList<R>( vs.length );
        for ( final Object v : vs )
        {
            selectedValueList.add( (R) v );
        }
        return refine( selectedValueList );
    }

    @SuppressWarnings( {"unchecked"} )
    @Override
    public void setValue( @Nonnull final JList editorComponent,
                          @Nullable final CT value )
    {
        if ( value == null || value.isEmpty() )
        {
            editorComponent.clearSelection();
            return;
        }

        final ImmutableListModel<R> model = (ImmutableListModel<R>) editorComponent.getModel();

        final int[] ixs = new int[value.size()];
        int i = 0;
        for ( final Object r : value )
        {
            ixs[i] = model.indexOf( (R) r );
            i++;
        }

        editorComponent.setSelectedIndices( ixs );
    }

    @Nonnull
    protected abstract Collection<R> getSuitableData();

    @Nullable
    protected abstract CT refine( @Nonnull List<R> selectedValues );

    public static class ImmutableListModel<R>
            extends AbstractListModel
    {
        private final List<R> data;

        public ImmutableListModel( @Nonnull final Collection<R> data )
        {
            if ( data instanceof List )
            {
                this.data = (List<R>) data;
            }
            else
            {
                this.data = new ArrayList<R>( data );
            }
        }

        @Override
        public R getElementAt( final int index )
        {
            return data.get( index );
        }

        @Override
        public int getSize()
        {
            return data == null ? 0 : data.size();
        }

        public int indexOf( @Nullable final R o ) {return data.indexOf( o );}
    }
}
