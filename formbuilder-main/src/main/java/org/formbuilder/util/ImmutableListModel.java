package org.formbuilder.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author eav Date: 22.08.2010 Time: 22:39:32
 * @param <T> item type
 */
public class ImmutableListModel<T>
        extends AbstractListModel
{
// ------------------------------ FIELDS ------------------------------
    private final List<T> data;

// --------------------------- CONSTRUCTORS ---------------------------

    public ImmutableListModel( @Nonnull final Collection<T> data )
    {
        if ( data instanceof List )
        {
            this.data = (List<T>) data;
        }
        else
        {
            this.data = new ArrayList<T>( data );
        }
    }

// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface ListModel ---------------------

    @Override
    public int getSize()
    {
        return data == null ? 0 : data.size();
    }

    @Override
    public T getElementAt( final int index )
    {
        return data.get( index );
    }

// -------------------------- OTHER METHODS --------------------------

    public int indexOf( @Nullable final T o )
    {
        return data.indexOf( o );
    }
}
