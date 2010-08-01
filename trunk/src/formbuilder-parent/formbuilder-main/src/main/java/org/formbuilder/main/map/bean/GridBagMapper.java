/**
 *
 */
package org.formbuilder.main.map.bean;

import com.google.common.collect.Ordering;
import org.formbuilder.main.map.Mapping;
import org.formbuilder.main.map.MappingRules;
import org.formbuilder.main.map.exception.MappingException;
import org.formbuilder.main.util.GridBagPanel;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.beans.PropertyDescriptor;
import java.util.Comparator;
import java.util.List;

import static com.google.common.base.Predicates.and;
import static com.google.common.collect.ImmutableList.of;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.transform;
import static org.formbuilder.main.util.Reflection.getBeanInfo;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public class GridBagMapper<B>
        extends AbstractBeanMapper<B>
        implements Comparator<OrderedPropertyDescriptor>
{
    @Nonnull
    @Override
    public Mapping map( @Nonnull final Class<B> beanClass,
                        @Nonnull final MappingRules mappingRules )
    {
        assert SwingUtilities.isEventDispatchThread();

        final Mapping mapping = new Mapping();

        final GridBagPanel gridBagPanel = new GridBagPanel();
        mapping.setPanel( gridBagPanel );

        Iterable<PropertyDescriptor> descriptorIterator = of( getBeanInfo( beanClass ).getPropertyDescriptors() );
        Iterable<PropertyDescriptor> supportedAndVisible = filter( descriptorIterator, and( isSupported, isVisible ) );
        Iterable<OrderedPropertyDescriptor> withOrder = transform( supportedAndVisible, addOrder );
        List<OrderedPropertyDescriptor> descriptors = Ordering.from( this ).sortedCopy( withOrder );
        for ( int i = 0; i < descriptors.size(); i++ )
        {
            final PropertyDescriptor descriptor = descriptors.get( i ).getDescriptor();
            try
            {
                gridBagPanel.add( createEditor( descriptor, mappingRules, mapping ), i, 1 );
                gridBagPanel.add( createLabel( mapping, descriptor ), i, 0 );
            }
            catch ( MappingException e )
            {
                handleMappingException( e );
            }
        }

        return mapping;
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
