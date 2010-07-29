/**
 *
 */
package org.formbuilder.main.map.bean;

import org.apache.log4j.Logger;
import org.formbuilder.main.annotations.UIField;
import org.formbuilder.main.map.MapperNotFoundException;
import org.formbuilder.main.map.Mapping;
import org.formbuilder.main.map.TypeMappers;
import org.formbuilder.main.map.type.ValueChangeListener;
import org.formbuilder.main.map.type.TypeMapper;

import javax.swing.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Set;

import static org.formbuilder.main.util.TextUtil.capitalize;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public abstract class AbstractBeanMapper<B>
        implements BeanMapper<B>
{
    private static final Logger log = Logger.getLogger( AbstractBeanMapper.class );
    private static final Validator validator;

    static
    {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

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

    @SuppressWarnings( {"unchecked"} )
    protected JComponent createEditor( final TypeMappers typeMappers,
                                       final PropertyDescriptor descriptor,
                                       final Mapping mapping )
            throws
            MapperNotFoundException
    {
        final TypeMapper mapper = typeMappers.getMapper( descriptor.getReadMethod() );
        final JComponent editor = mapper.createComponent();
        editor.setName( descriptor.getName() );
        mapper.bindChangeListener( editor, new ValueChangeListener()
        {
            @Override
            public void onChange()
            {
                final Object newValue = mapper.getValue( editor );
                final Set<ConstraintViolation> violations = doValidation( descriptor, newValue );
                mapper.getValidationHighlighter().highlightViolations( editor, violations );
            }
        } );
        mapping.addEditor( descriptor, editor, mapper );
        return editor;
    }

    @SuppressWarnings( {"unchecked"} )
    protected Set<ConstraintViolation> doValidation( final PropertyDescriptor descriptor,
                                                        final Object newValue )
    {
        final Class beanType = descriptor.getReadMethod().getDeclaringClass();
        final String propertyName = descriptor.getName();
        return validator.validateValue( beanType, propertyName, newValue );
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
