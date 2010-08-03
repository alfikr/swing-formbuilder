package org.formbuilder.mapping.metadata.sort;

import com.google.common.collect.Ordering;
import org.formbuilder.mapping.metadata.MetaData;
import org.formbuilder.mapping.metadata.MetaDataUser;
import org.formbuilder.mapping.metadata.functions.AddOrder;
import org.formbuilder.mapping.metadata.functions.IsSupported;
import org.formbuilder.mapping.metadata.functions.IsVisible;

import javax.annotation.Nonnull;
import java.beans.PropertyDescriptor;
import java.util.Comparator;
import java.util.List;

import static com.google.common.base.Predicates.and;
import static com.google.common.collect.ImmutableList.of;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.transform;
import static org.formbuilder.util.Reflection.getBeanInfo;

/**
 * @author aeremenok
 *         Date: 02.08.2010
 *         Time: 17:07:41
 */
public class PropertySorter
        extends MetaDataUser
        implements Comparator<OrderedPropertyDescriptor>
{
    public PropertySorter( @Nonnull final MetaData metaData )
    {
        super( metaData );
    }

    @Nonnull
    public List<OrderedPropertyDescriptor> activeSortedDescriptors( @Nonnull final Class beanClass )
    {
        final Iterable<PropertyDescriptor> descriptors = of( getBeanInfo( beanClass ).getPropertyDescriptors() );
        final Iterable<PropertyDescriptor> supportedAndVisible = filter( descriptors,
                and( IsSupported.INSTANCE, new IsVisible( metaData ) ) );
        final Iterable<OrderedPropertyDescriptor> withOrder = transform( supportedAndVisible,
                new AddOrder( metaData ) );
        return Ordering.from( this ).sortedCopy( withOrder );
    }

    @Override
    public int compare( @Nonnull final OrderedPropertyDescriptor o1,
                        @Nonnull final OrderedPropertyDescriptor o2 )
    {
        final int compared = o1.getOrder() - o2.getOrder();
        if ( compared == 0 )
        {
            return o1.getDescriptor().getName().compareTo( o2.getDescriptor().getName() );
        }
        return compared;
    }
}
