/**
 *
 */
package org.formbuilder.main;

import org.apache.log4j.Logger;
import org.formbuilder.main.annotations.UIField;

import javax.swing.*;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import static org.formbuilder.main.util.TextUtil.capitalize;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public abstract class AbstractBeanMapper<B>
        implements BeanMapper<B>
{
    private static final Logger log = Logger.getLogger( AbstractBeanMapper.class );

    protected BeanInfo getBeanInfo( final Class<?> beanClass )
    {
        try
        {
            return Introspector.getBeanInfo( beanClass );
        }
        catch ( final IntrospectionException e )
        {
            throw new RuntimeException( e );
        }
    }

    protected JLabel createLabel( final Mapping mapping,
                                  final PropertyDescriptor descriptor )
    {
        final JLabel label = new JLabel( getTitle( descriptor ) );
        label.setName( descriptor.getName() );
        mapping.addLabel( descriptor, label );
        return label;
    }

    protected String getTitle( final PropertyDescriptor descriptor )
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

    protected JComponent createEditor( final TypeMappers typeMappers,
                                       final PropertyDescriptor descriptor,
                                       final Mapping mapping )
            throws
            MapperNotFoundException
    {
        final TypeMapper mapper = typeMappers.getMapper( descriptor.getReadMethod() );
        final JComponent editor = mapper.createComponent();
        editor.setName( descriptor.getName() );
        mapping.addEditor( descriptor, editor, mapper );
        return editor;
    }

    protected void handleNotFoundMapper( final MapperNotFoundException e )
    {
        // todo implement different strategies
        log.warn( "Cannot find mapper for method " + e.getReadMethod() );
    }

    protected boolean isSupported( final PropertyDescriptor descriptor )
    {
        return descriptor.getReadMethod() != null && !"class".equals( descriptor.getName() );
    }
}
