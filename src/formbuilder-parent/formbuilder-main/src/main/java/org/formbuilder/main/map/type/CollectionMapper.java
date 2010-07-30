package org.formbuilder.main.map.type;

import org.formbuilder.main.map.ValueChangeListener;
import org.formbuilder.main.validation.NothingHighlighter;
import org.formbuilder.main.validation.ValidationHighlighter;

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
    public CT getValue( final JList component )
    {
        Object[] vs = component.getSelectedValues();
        List<R> selectedValueList = new ArrayList<R>( vs.length );
        for ( Object v : vs )
        {
            selectedValueList.add( (R) v );
        }
        return refine( selectedValueList );
    }

    @SuppressWarnings( {"unchecked"} )
    @Override
    public void setValue( final JList component,
                          final CT value )
    {
        if ( value == null || value.isEmpty() )
        {
            component.clearSelection();
            return;
        }

        ImmutableListModel<R> model = (ImmutableListModel<R>) component.getModel();

        int[] ixs = new int[value.size()];
        int i = 0;
        for ( Object r : value )
        {
            ixs[i] = model.indexOf( (R) r );
            i++;
        }

        component.setSelectedIndices( ixs );
    }

    @Override
    public JList createComponent()
    {
        return new JList( new ImmutableListModel<R>( getSuitableData() ) );
    }

    protected abstract Collection<R> getSuitableData();

    protected abstract CT refine( List<R> selectedValues );

    @Override
    public void bindChangeListener( final JList component,
                                    final ValueChangeListener<CT> iValueChangeListener )
    {
        component.getSelectionModel().addListSelectionListener( new ListSelectionListener()
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
    public ValidationHighlighter getValidationHighlighter()
    {
        return NothingHighlighter.INSTANCE;
    }

    public static class ImmutableListModel<R>
            extends AbstractListModel
    {
        private final List<R> data;

        public ImmutableListModel( Collection<R> data )
        {
            this.data = new ArrayList<R>( data );
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
