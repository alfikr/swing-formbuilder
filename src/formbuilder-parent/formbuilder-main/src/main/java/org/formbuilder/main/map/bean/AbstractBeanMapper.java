/**
 *
 */
package org.formbuilder.main.map.bean;

import org.apache.log4j.Logger;
import org.formbuilder.main.annotations.UITitle;
import org.formbuilder.main.map.Mapping;
import org.formbuilder.main.map.MappingException;
import org.formbuilder.main.map.MappingRules;
import org.formbuilder.main.map.UnmappedTypeException;
import org.formbuilder.main.map.ValueChangeListener;
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

    @SuppressWarnings( {"unchecked"} )
    protected Set<ConstraintViolation> doValidation( final PropertyDescriptor descriptor,
                                                     final Object newValue )
    {
        final Class beanType = descriptor.getReadMethod().getDeclaringClass();
        final String propertyName = descriptor.getName();
        return validator.validateValue( beanType, propertyName, newValue );
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

    private class ValidateChangedValue
            implements ValueChangeListener
    {
        private final TypeMapper mapper;
        private final JComponent editor;
        private final PropertyDescriptor descriptor;

        public ValidateChangedValue( final TypeMapper mapper,
                                     final JComponent editor,
                                     final PropertyDescriptor descriptor )
        {
            this.mapper = mapper;
            this.editor = editor;
            this.descriptor = descriptor;
        }

        @SuppressWarnings( {"unchecked"} )
        @Override
        public void onChange()
        {
            final Object newValue = mapper.getValue( editor );
            final Set<ConstraintViolation> violations = doValidation( descriptor, newValue );
            mapper.getValidationHighlighter().highlightViolations( editor, violations );
        }
    }
}
