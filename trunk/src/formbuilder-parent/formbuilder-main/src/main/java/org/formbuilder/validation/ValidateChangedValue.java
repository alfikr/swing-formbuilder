/*
 * Copyright (C) 2010 Andrey Yeremenok (eav1986__at__gmail__com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.formbuilder.validation;

import org.formbuilder.mapping.PropertyEditor;
import org.formbuilder.mapping.change.ValueChangeListener;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.beans.PropertyDescriptor;
import java.util.Set;

/** @author aeremenok Date: 30.07.2010 Time: 17:26:09 */
public class ValidateChangedValue<B, C extends JComponent, V>
        implements ValueChangeListener
{
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final PropertyEditor<C, V> propertyEditor;

    public ValidateChangedValue( @Nonnull final PropertyEditor<C, V> propertyEditor )
    {
        this.propertyEditor = propertyEditor;
    }

    @SuppressWarnings( {"unchecked"} )
    @Override
    public void onChange()
    {
        final V newValue = propertyEditor.getValue();

        final Set<ConstraintViolation<B>> violations = doValidation( validator, newValue );

        final ValidationContext validationContext = new ValidationContext( propertyEditor, violations, newValue );
        propertyEditor.getMapper().getValidationMarker().markViolations( validationContext );
    }

    @Nonnull
    @SuppressWarnings( {"unchecked"} )
    protected Set<ConstraintViolation<B>> doValidation( @Nonnull final Validator validator,
                                                        @Nullable final V newValue )
    {
        final PropertyDescriptor descriptor = propertyEditor.getDescriptor();
        final Class<B> beanType = (Class<B>) descriptor.getReadMethod().getDeclaringClass();
        final String propertyName = descriptor.getName();
        return validator.validateValue( beanType, propertyName, newValue );
    }
}
