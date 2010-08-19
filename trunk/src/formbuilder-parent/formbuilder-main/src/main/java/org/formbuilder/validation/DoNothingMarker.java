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

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import javax.swing.*;
import javax.validation.Validator;

/**
 * Ignores the constraint violations
 *
 * @author aeremenok Date: 30.07.2010 Time: 14:04:23
 * @see Validator
 */
@ThreadSafe
public class DoNothingMarker
        implements ValidationMarker
{
    public static final DoNothingMarker INSTANCE = new DoNothingMarker();

    @Override
    public <B, C extends JComponent, V> void markViolations( @Nonnull final ValidationContext<B, C, V> validationContext )
    {
    }
}
