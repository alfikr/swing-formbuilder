/**
 *
 */
package org.formbuilder.main.impl;

import org.apache.log4j.Logger;
import org.formbuilder.main.AbstractBeanMapper;
import org.formbuilder.main.MapperNotFoundException;
import org.formbuilder.main.TypeMapper;
import org.formbuilder.main.TypeMappers;
import org.formbuilder.main.annotations.UIField;
import org.formbuilder.main.util.GridBagPanel;

import javax.swing.*;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import static org.formbuilder.main.util.TextUtil.capitalize;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public class GridBagMapper<B>
        extends AbstractBeanMapper<B>
{
    private static final Logger log = Logger.getLogger( GridBagMapper.class );

    @Override
    public JComponent map( final Class<B> beanClass,
                           final TypeMappers typeMappers )
    {
        assert SwingUtilities.isEventDispatchThread();

        final GridBagPanel gridBagPanel = new GridBagPanel();

        final PropertyDescriptor[] propertyDescriptors = getBeanInfo( beanClass ).getPropertyDescriptors();
        for ( int i = 0; i < propertyDescriptors.length; i++ )
        {
            final PropertyDescriptor descriptor = propertyDescriptors[i];
            if ( isSupported( descriptor ) )
            {
                gridBagPanel.add( createLabel( descriptor ), i, 0 );

                try
                {
                    gridBagPanel.add( createEditor( typeMappers, descriptor ), i, 1 );
                }
                catch ( MapperNotFoundException e )
                {
                    handleNotFoundMapper( e );
                }
            }
        }

        return gridBagPanel;
    }

    private JLabel createLabel( final PropertyDescriptor descriptor )
    {
        final JLabel label = new JLabel( getTitle( descriptor ) );
        label.setName( descriptor.getName() );
        return label;
    }

    private String getTitle( final PropertyDescriptor descriptor )
    {
        final Method readMethod = descriptor.getReadMethod();

        UIField uiField = readMethod.getAnnotation( UIField.class );
        if ( uiField != null )
        {
            return uiField.title();
        }

        String className = readMethod.getDeclaringClass().getSimpleName();
        String propertyName = descriptor.getName();
        String uiManagerTitle = UIManager.getString( className + "." + propertyName );
        if ( uiManagerTitle != null )
        {
            return uiManagerTitle;
        }

        String displayName = descriptor.getDisplayName();
        if ( displayName != null )
        {
            return capitalize( displayName );
        }

        return capitalize( descriptor.getName() );
    }

    private JComponent createEditor( final TypeMappers typeMappers,
                                     final PropertyDescriptor descriptor )
            throws
            MapperNotFoundException
    {
        final TypeMapper mapper = typeMappers.getMapper( descriptor.getReadMethod() );
        final JComponent component = mapper.createComponent();
        component.setName( descriptor.getName() );
        return component;
    }

    protected void handleNotFoundMapper( final MapperNotFoundException e )
    {
        // todo implement different strategies
        log.warn( "Cannot find mapper for method " + e.getReadMethod() );
    }

    private boolean isSupported( final PropertyDescriptor descriptor )
    {
        return descriptor.getReadMethod() != null && !"class".equals( descriptor.getName() );
    }
}
