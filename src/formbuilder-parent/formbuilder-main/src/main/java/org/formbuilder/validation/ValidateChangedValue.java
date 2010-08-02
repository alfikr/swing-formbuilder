package org.formbuilder.validation;

import org.formbuilder.mapping.ValueChangeListener;
import org.formbuilder.mapping.type.TypeMapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.beans.PropertyDescriptor;
import java.util.Set;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 17:26:09
 */
public class ValidateChangedValue
        implements ValueChangeListener
{
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final TypeMapper mapper;
    private final JComponent editorComponent;
    private final PropertyDescriptor descriptor;

    public ValidateChangedValue( @Nonnull final TypeMapper mapper,
                                 @Nonnull final JComponent editorComponent,
                                 @Nonnull final PropertyDescriptor descriptor )
    {
        this.mapper = mapper;
        this.editorComponent = editorComponent;
        this.descriptor = descriptor;
    }

    @SuppressWarnings( {"unchecked"} )
    @Override
    public void onChange()
    {
        final Object newValue = mapper.getValue( editorComponent );
        final Set<ConstraintViolation> violations = doValidation( validator, descriptor, newValue );
        mapper.getValidationMarker().markViolations( editorComponent, violations );
    }

    @Nonnull
    @SuppressWarnings( {"unchecked"} )
    protected Set<ConstraintViolation> doValidation( @Nonnull final Validator validator,
                                                     @Nonnull final PropertyDescriptor descriptor,
                                                     @Nullable final Object newValue )
    {
        final Class beanType = descriptor.getReadMethod().getDeclaringClass();
        final String propertyName = descriptor.getName();
        return validator.validateValue( beanType, propertyName, newValue );
    }
}
