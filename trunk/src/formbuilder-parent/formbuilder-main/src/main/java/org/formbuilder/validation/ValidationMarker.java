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
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * @author aeremenok
 *         Date: 29.07.2010
 *         Time: 17:40:48
 */
@NotThreadSafe
public interface ValidationMarker
{
    <B> void markViolations( @Nonnull final JComponent editor,
                             @Nonnull final Set<ConstraintViolation<B>> violations );
}
