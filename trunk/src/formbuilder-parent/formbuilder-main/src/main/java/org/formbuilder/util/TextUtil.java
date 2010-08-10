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

package org.formbuilder.util;

import static com.google.common.collect.Iterables.transform;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.ConstraintViolation;

import com.google.common.base.Function;
import com.google.common.base.Joiner;

/**
 * @author aeremenok
 *         Date: 28.07.2010
 *         Time: 13:29:57
 */
public class TextUtil
{
    public static final Function<ConstraintViolation, String> TO_MESSAGE = new Function<ConstraintViolation, String>()
    {
        @Override
        public String apply( @Nullable final ConstraintViolation violation )
        {
            return violation == null ? "" : violation.getMessage();
        }
    };

    public static String capitalize( final String s )
    {
        return s.substring( 0, 1 ).toUpperCase() + s.substring( 1 );
    }

    @Nonnull
    public static <B> String digest( @Nonnull final String delimiter,
                                     @Nonnull final Set<ConstraintViolation<B>> violations )
    {
        return Joiner.on( delimiter ).join( transform( violations, TO_MESSAGE ) );
    }
}
