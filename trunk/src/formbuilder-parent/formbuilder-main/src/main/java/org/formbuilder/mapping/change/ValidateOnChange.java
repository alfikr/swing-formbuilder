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
package org.formbuilder.mapping.change;

import org.formbuilder.mapping.PropertyEditor;
import org.formbuilder.validation.ValidationEvent;
import org.formbuilder.validation.ValidationMarker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.beans.PropertyDescriptor;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;
import static javax.swing.SwingUtilities.isEventDispatchThread;

/** @author aeremenok Date: 30.07.2010 Time: 17:26:09 */
public class ValidateOnChange<B, C extends JComponent, V>
        implements ChangeHandler
{
// ------------------------------ FIELDS ------------------------------
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final PropertyEditor<C, V> propertyEditor;

// --------------------------- CONSTRUCTORS ---------------------------

    public ValidateOnChange( @Nonnull final PropertyEditor<C, V> propertyEditor )
    {
        this.propertyEditor = propertyEditor;
    }

// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface ChangeHandler ---------------------

    @Override
    public void onChange( final ValidationMarker... validationMarkers )
    {
        if ( validationMarkers.length > 0 )
        {
            final V newValue = propertyEditor.getValue();
            final Set<ConstraintViolation<B>> violations = doValidation( validator, newValue );

            final ValidationEvent<B, C, V> validationEvent = new ValidationEvent<B, C, V>( propertyEditor,
                    violations,
                    newValue );

            checkState( isEventDispatchThread() );
            for ( final ValidationMarker validationMarker : validationMarkers )
            {
                if ( validationMarker != null )
                {
                    validationMarker.markViolations( validationEvent );
                }
            }
        }
    }

// -------------------------- OTHER METHODS --------------------------

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
