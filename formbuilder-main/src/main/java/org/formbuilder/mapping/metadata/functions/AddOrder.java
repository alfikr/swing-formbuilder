package org.formbuilder.mapping.metadata.functions;

import com.google.common.base.Function;
import org.formbuilder.mapping.metadata.MetaData;
import org.formbuilder.mapping.metadata.MetaDataUser;
import org.formbuilder.mapping.metadata.sort.OrderedPropertyDescriptor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.beans.PropertyDescriptor;

/**
 * @author aeremenok
 *         Date: 02.08.2010
 *         Time: 16:58:40
 */
public class AddOrder
        extends MetaDataUser
        implements Function<PropertyDescriptor, OrderedPropertyDescriptor>
{
    public AddOrder( @Nonnull final MetaData metaData )
    {
        super( metaData );
    }

    @Override
    public OrderedPropertyDescriptor apply( @Nonnull final PropertyDescriptor from )
    {
        final int order = toInt( metaData.getOrder( from ) );
        return new OrderedPropertyDescriptor( from, order );
    }

    protected int toInt( @Nullable final Integer i )
    {
        if ( i == null )
        {
            return Integer.MAX_VALUE;
        }
        return i;
    }
}
