/**
 *
 */
package org.formbuilder.mapping.bean;

import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.mapping.MappingRules;
import org.formbuilder.mapping.exception.MappingException;
import org.formbuilder.mapping.metadata.sort.OrderedPropertyDescriptor;
import org.formbuilder.mapping.metadata.sort.PropertySorter;
import org.formbuilder.util.GridBagPanel;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import java.beans.PropertyDescriptor;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static javax.swing.SwingUtilities.isEventDispatchThread;

/**
 * @author aeremenok 2010
 * @param <B>
 */
@NotThreadSafe
public class GridBagMapper<B>
        extends AbstractBeanMapper<B>
{
    private final PropertySorter sorter = new PropertySorter( metaData );

    @Nonnull
    @Override
    public BeanMapping map( @Nonnull final Class<B> beanClass,
                            @Nonnull final MappingRules mappingRules,
                            final boolean doValidation )
    {
        checkState( isEventDispatchThread() );

        final GridBagPanel gridBagPanel = new GridBagPanel();

        final BeanMapping beanMapping = new BeanMapping();
        beanMapping.setPanel( gridBagPanel );

        int row = 0;
        final List<OrderedPropertyDescriptor> sorted = sorter.activeSortedDescriptors( beanClass );
        for ( final OrderedPropertyDescriptor orderedPropertyDescriptor : sorted )
        {
            final PropertyDescriptor descriptor = orderedPropertyDescriptor.getDescriptor();
            try
            {
                gridBagPanel.add( createEditor( descriptor, mappingRules, beanMapping, doValidation ), row, 1 );
                gridBagPanel.add( createLabel( beanMapping, descriptor ), row, 0 );
            }
            catch ( final MappingException e )
            {
                handleMappingException( e );
            }
            row++;
        }

        return beanMapping;
    }
}
