/**
 *
 */
package org.formbuilder.main.map.bean;

import org.formbuilder.main.map.Mapping;
import org.formbuilder.main.map.MappingRules;
import org.formbuilder.main.map.exception.MappingException;
import org.formbuilder.main.util.GridBagPanel;
import org.formbuilder.main.util.Reflection;

import javax.swing.*;
import java.beans.PropertyDescriptor;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public class GridBagMapper<B>
        extends AbstractBeanMapper<B>
{
    @Override
    public Mapping map( final Class<B> beanClass,
                        final MappingRules mappingRules )
    {
        assert SwingUtilities.isEventDispatchThread();

        final Mapping mapping = new Mapping();

        final GridBagPanel gridBagPanel = new GridBagPanel();
        mapping.setPanel( gridBagPanel );

        final PropertyDescriptor[] propertyDescriptors = Reflection.getBeanInfo( beanClass ).getPropertyDescriptors();
        for ( int i = 0; i < propertyDescriptors.length; i++ )
        {
            final PropertyDescriptor descriptor = propertyDescriptors[i];
            if ( isSupported( descriptor ) && !metaData.isHidden( descriptor ) )
            {
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
        }

        return mapping;
    }
}
