package org.formbuilder.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** @author eav Date: 22.08.2010 Time: 22:39:32 */
public class ImmutableListModel<R>
        extends AbstractListModel
{
// ------------------------------ FIELDS ------------------------------
    private final List<R> data;

// --------------------------- CONSTRUCTORS ---------------------------

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

// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface ListModel ---------------------

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

// -------------------------- OTHER METHODS --------------------------

    public int indexOf( @Nullable final R o )
    {
        return data.indexOf( o );
    }
}
