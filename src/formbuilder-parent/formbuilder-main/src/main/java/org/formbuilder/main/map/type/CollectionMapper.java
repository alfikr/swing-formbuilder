package org.formbuilder.main.map.type;

import org.formbuilder.main.map.ValueChangeListener;
import org.formbuilder.main.validation.DoNothingMarker;
import org.formbuilder.main.validation.ValidationMarker;

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
 */
public abstract class CollectionMapper<R, CT extends Collection>
        implements TypeMapper<JList, CT>
{
    @SuppressWarnings( {"unchecked"} )
    @Override
    public CT getValue( final JList editorComponent )
    {
        Object[] vs = editorComponent.getSelectedValues();
        List<R> selectedValueList = new ArrayList<R>( vs.length );
        for ( Object v : vs )
        {
            selectedValueList.add( (R) v );
        }
        return refine( selectedValueList );
    }

    @SuppressWarnings( {"unchecked"} )
    @Override
    public void setValue( final JList editorComponent,
                          final CT value )
    {
        if ( value == null || value.isEmpty() )
        {
            editorComponent.clearSelection();
            return;
        }

        ImmutableListModel<R> model = (ImmutableListModel<R>) editorComponent.getModel();

        int[] ixs = new int[value.size()];
        int i = 0;
        for ( Object r : value )
        {
            ixs[i] = model.indexOf( (R) r );
            i++;
        }

        editorComponent.setSelectedIndices( ixs );
    }

    @Override
    public JList createEditorComponent()
    {
        return new JList( new ImmutableListModel<R>( getSuitableData() ) );
    }

    protected abstract Collection<R> getSuitableData();

    protected abstract CT refine( List<R> selectedValues );

    @Override
    public void bindChangeListener( final JList editorComponent,
                                    final ValueChangeListener<CT> iValueChangeListener )
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

    @Override
    public ValidationMarker getValidationMarker()
    {
        return DoNothingMarker.INSTANCE;
    }

    public static class ImmutableListModel<R>
            extends AbstractListModel
    {
        private final List<R> data;

        public ImmutableListModel( Collection<R> data )
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
        public int getSize()
        {
            return data == null ? 0 : data.size();
        }

        @Override
        public R getElementAt( final int index )
        {
            return data.get( index );
        }

        public int indexOf( final R o ) {return data.indexOf( o );}
    }
}
