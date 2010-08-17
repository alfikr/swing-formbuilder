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
package org.formbuilder.mapping;

import org.formbuilder.TypeMapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;

/**
 * Holds an editor and a type mapper, that provides access to it.
 *
 * @author eav Date: 31.07.2010 Time: 17:19:34
 */
public class PropertyMapping
{
    private final JComponent component;
    private final TypeMapper mapper;

    public PropertyMapping( @Nonnull final JComponent component,
                            @Nonnull final TypeMapper mapper )
    {
        this.component = component;
        this.mapper = mapper;
    }

    @SuppressWarnings( {"unchecked"} )
    @Nullable
    public Object getValue()
    {
        return mapper.getValue( component );
    }

    @SuppressWarnings( {"unchecked"} )
    public void setValue( @Nullable final Object value )
    {
        mapper.setValue( component, value );
    }
}
