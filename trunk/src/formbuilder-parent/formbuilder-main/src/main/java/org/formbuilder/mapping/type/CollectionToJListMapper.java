package org.formbuilder.mapping.type;

import org.formbuilder.mapping.change.ValueChangeListener;
import org.formbuilder.validation.DoNothingMarker;
import org.formbuilder.validation.ValidationMarker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 14:08:34
 * @param <R>
 * @param <CT>
 */
@NotThreadSafe
public abstract class CollectionToJListMapper<R, CT extends Collection>
        implements TypeMapper<JList, CT>
{
    @Override
    public void bindChangeListener( @Nonnull final JList editorComponent,
                                    @Nonnull final ValueChangeListener<CT> iValueChangeListener )
    {
        editorComponent.getSelectionModel().addListSelectionListener( new ListSelectionListener()
        {
            @Override
            public void valueChanged( final ListSelectionEvent e )
            {
                if ( !e.getValueIsAdjusting() )
                {
                    iValueChangeListener.onChange();
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

    @Nonnull
    @Override
    public ValidationMarker getValidationMarker()
    {
        return DoNothingMarker.INSTANCE;
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
