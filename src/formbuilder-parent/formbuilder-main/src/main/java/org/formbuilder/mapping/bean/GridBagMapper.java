/**
 *
 */
package org.formbuilder.mapping.bean;

import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.mapping.MappingRules;
import org.formbuilder.mapping.exception.MappingException;
import org.formbuilder.mapping.metadata.OrderedPropertyDescriptor;
import org.formbuilder.mapping.metadata.PropertySorter;
import org.formbuilder.util.GridBagPanel;

import javax.annotation.Nonnull;
import java.beans.PropertyDescriptor;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static javax.swing.SwingUtilities.isEventDispatchThread;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public class GridBagMapper<B>
        extends AbstractBeanMapper<B>
{
    private final PropertySorter sorter = new PropertySorter( metaData );

    @Nonnull
    @Override
    public BeanMapping map( @Nonnull final Class<B> beanClass,
                            @Nonnull final MappingRules mappingRules )
    {
        checkState( isEventDispatchThread() );

        final GridBagPanel gridBagPanel = new GridBagPanel();

        final BeanMapping beanMapping = new BeanMapping();
        beanMapping.setPanel( gridBagPanel );

        final List<OrderedPropertyDescriptor> descriptors = sorter.activeSortedDescriptors( beanClass );
        for ( int i = 0; i < descriptors.size(); i++ )
        {
            final PropertyDescriptor descriptor = descriptors.get( i ).getDescriptor();
            try
            {
                gridBagPanel.add( createEditor( descriptor, mappingRules, beanMapping ), i, 1 );
                gridBagPanel.add( createLabel( beanMapping, descriptor ), i, 0 );
            }
            catch ( MappingException e )
            {
                handleMappingException( e );
            }
        }

        return beanMapping;
    }
}
