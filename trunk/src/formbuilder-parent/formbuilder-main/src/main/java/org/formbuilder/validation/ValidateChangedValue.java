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

import java.beans.PropertyDescriptor;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JComponent;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.change.ValueChangeListener;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 17:26:09
 * @param <B>
 */
public class ValidateChangedValue<B>
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
        final Set<ConstraintViolation<B>> violations = doValidation( validator, descriptor, newValue );
        mapper.getValidationMarker().markViolations( editorComponent, violations );
    }

    @Nonnull
    @SuppressWarnings( {"unchecked"} )
    protected Set<ConstraintViolation<B>> doValidation( @Nonnull final Validator validator,
                                                        @Nonnull final PropertyDescriptor descriptor,
                                                        @Nullable final Object newValue )
    {
        final Class<B> beanType = (Class<B>) descriptor.getReadMethod().getDeclaringClass();
        final String propertyName = descriptor.getName();
        return validator.validateValue( beanType, propertyName, newValue );
    }
}
