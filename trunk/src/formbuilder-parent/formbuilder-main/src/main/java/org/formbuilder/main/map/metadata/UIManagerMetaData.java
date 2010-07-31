package org.formbuilder.main.map.metadata;

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
    public String getTitle( final PropertyDescriptor descriptor )
    {
        return UIManager.getString( getQName( descriptor ) + ".title" );
    }

    @Override
    public Integer getOrder( final PropertyDescriptor descriptor )
    {
        String o = UIManager.getString( getQName( descriptor ) + ".order" );
        if ( o == null )
        {
            return null;
        }

        try
        {
            return Integer.valueOf( o );
        }
        catch ( NumberFormatException e )
        {
            return null;
        }
    }

    @Override
    public boolean isHidden( final PropertyDescriptor descriptor )
    {
        return UIManager.getBoolean( getQName( descriptor ) + ".hidden" );
    }

    @Override
    public boolean isReadOnly( final PropertyDescriptor descriptor )
    {
        return UIManager.getBoolean( getQName( descriptor ) + ".readonly" );
    }

    protected String getQName( PropertyDescriptor descriptor )
    {
        String className = descriptor.getReadMethod().getDeclaringClass().getSimpleName();
        String propertyName = descriptor.getName();
        return className + "." + propertyName;
    }
}
