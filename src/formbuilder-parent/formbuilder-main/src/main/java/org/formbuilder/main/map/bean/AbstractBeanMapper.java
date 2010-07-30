/**
 *
 */
package org.formbuilder.main.map.bean;

import org.apache.log4j.Logger;
import org.formbuilder.main.annotations.UITitle;
import org.formbuilder.main.map.Mapping;
import org.formbuilder.main.map.exception.MappingException;
import org.formbuilder.main.map.MappingRules;
import org.formbuilder.main.map.type.TypeMapper;

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

        UITitle uiTitle = readMethod.getAnnotation( UITitle.class );
        if ( uiTitle != null )
        {
            return uiTitle.value();
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

    @SuppressWarnings( {"unchecked"} )
    protected JComponent createEditor( final PropertyDescriptor descriptor,
                                       final MappingRules mappingRules,
                                       final Mapping mapping )
            throws
            MappingException
    {
        final TypeMapper mapper = mappingRules.getMapper( descriptor );
        final JComponent editor = mapper.createComponent();
        editor.setEnabled( isEditable( descriptor ) );
        editor.setName( descriptor.getName() );

        mapper.bindChangeListener( editor, new ValidateChangedValue( mapper, editor, descriptor ) );

        mapping.addEditor( descriptor, editor, mapper );

        return editor;
    }

    protected void handleMappingException( final MappingException e )
    {
        // todo implement different strategies
        log.warn( "Cannot find mapper for method " + e.getDescriptor() );
    }

    protected boolean isSupported( final PropertyDescriptor descriptor )
    {
        return descriptor.getReadMethod() != null && !"class".equals( descriptor.getName() );
    }

    protected boolean isEditable( final PropertyDescriptor descriptor )
    {
        return descriptor.getWriteMethod() != null;
    }
}
