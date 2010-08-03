package org.formbuilder.mapping.metadata;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.beans.PropertyDescriptor;

/**
 * @author eav
 *         Date: 31.07.2010
 *         Time: 12:18:28
 */
public class UIManagerMetaData
        implements MetaData
{
    @Override
    @Nullable
    public Integer getOrder( @Nonnull final PropertyDescriptor descriptor )
    {
        final Object o = UIManager.get( getQName( descriptor ) + ".order" );
        if ( o == null )
        {
            return null;
        }
        if ( o instanceof Number )
        {
            final Number n = (Number) o;
            return n.intValue();
        }

        try
        {
            return Integer.valueOf( o.toString() );
        }
        catch ( final NumberFormatException e )
        {
            return null;
        }
    }

    @Override
    @Nullable
    public String getTitle( @Nonnull final PropertyDescriptor descriptor )
    {
        return UIManager.getString( getQName( descriptor ) + ".title" );
    }

    @Override
    public boolean isHidden( @Nonnull final PropertyDescriptor descriptor )
    {
        return UIManager.getBoolean( getQName( descriptor ) + ".hidden" );
    }

    @Override
    public boolean isReadOnly( @Nonnull final PropertyDescriptor descriptor )
    {
        return UIManager.getBoolean( getQName( descriptor ) + ".readonly" );
    }

    @Nonnull
    protected String getQName( final PropertyDescriptor descriptor )
    {
        final String className = descriptor.getReadMethod().getDeclaringClass().getSimpleName();
        final String propertyName = descriptor.getName();
        return className + "." + propertyName;
    }
}
