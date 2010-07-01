/**
 * 
 */
package org.formbuilder.main.impl;

import java.beans.PropertyDescriptor;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.formbuilder.main.AbstractBeanMapper;
import org.formbuilder.main.util.GridBagPanel;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public class GridBagMapper<B>
    extends AbstractBeanMapper<B>
{
    @Override
    public JComponent map( final Class<B> beanClass )
    {
        assert SwingUtilities.isEventDispatchThread();

        final GridBagPanel gridBagPanel = new GridBagPanel();

        final PropertyDescriptor[] propertyDescriptors = getBeanInfo( beanClass ).getPropertyDescriptors();
        for( int i = 0; i < propertyDescriptors.length; i++ )
        {
            final PropertyDescriptor descriptor = propertyDescriptors[i];
            if( isSupported( descriptor ) )
            {
                gridBagPanel.add( new JLabel( descriptor.getName() ), i, 0 );
                gridBagPanel.add( getComponent( descriptor.getName() ), i, 1 );
            }
        }

        return gridBagPanel;
    }

    private boolean isSupported( final PropertyDescriptor descriptor )
    {
        return descriptor.getReadMethod() != null && !"class".equals( descriptor.getName() );
    }

}
