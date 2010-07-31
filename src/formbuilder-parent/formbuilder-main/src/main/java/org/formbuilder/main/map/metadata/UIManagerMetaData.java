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
    public Boolean isHidden( final PropertyDescriptor descriptor )
    {
        return toBoolean( UIManager.getString( getQName( descriptor ) + ".hidden" ) );
    }

    @Override
    public Boolean isReadOnly( final PropertyDescriptor descriptor )
    {
        return toBoolean( UIManager.getString( getQName( descriptor ) + ".readonly" ) );
    }

    private Boolean toBoolean( final String s )
    {
        if ( "true".equalsIgnoreCase( s ) )
        {
            return true;
        }
        if ( "false".equalsIgnoreCase( s ) )
        {
            return false;
        }
        return null;
    }

    protected String getQName( PropertyDescriptor descriptor )
    {
        String className = descriptor.getReadMethod().getDeclaringClass().getSimpleName();
        String propertyName = descriptor.getName();
        return className + "." + propertyName;
    }
}
