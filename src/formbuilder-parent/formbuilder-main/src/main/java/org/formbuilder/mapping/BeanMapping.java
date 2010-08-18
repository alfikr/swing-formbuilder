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
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Maps beanmapper properties to their editors.
 *
 * @author aeremenok Date: 28.07.2010 Time: 13:47:07
 */
public class BeanMapping
{
// ------------------------------ FIELDS ------------------------------

    private final Map<PropertyDescriptor, PropertyMapping> propertyMappings = new HashMap<PropertyDescriptor, PropertyMapping>();
    private final Map<PropertyDescriptor, JLabel> labels = new HashMap<PropertyDescriptor, JLabel>();
    @Nonnull
    private final JComponent panel;

// --------------------------- CONSTRUCTORS ---------------------------

    public BeanMapping( @Nonnull final JComponent panel )
    {
        this.panel = checkNotNull( panel );
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    @Nonnull
    public JComponent getPanel()
    {
        return panel;
    }

// -------------------------- OTHER METHODS --------------------------

    public void addEditor( @Nonnull final PropertyDescriptor descriptor,
                           @Nonnull final JComponent editor,
                           @Nonnull final TypeMapper mapper )
    {
        propertyMappings.put( descriptor, new PropertyMapping( editor, mapper ) );
    }

    @SuppressWarnings( "unused" )
    public void addLabel( @Nonnull final PropertyDescriptor descriptor,
                          @Nonnull final JLabel label )
    {
        labels.put( descriptor, label );
    }

    @Nullable
    public JComponent getEditor( @Nonnull final PropertyDescriptor descriptor )
    {
        final PropertyMapping mapping = propertyMappings.get( descriptor );
        if ( mapping == null )
        {
            return null;
        }
        return mapping.getEditor();
    }

    @Nullable
    public JLabel getLabel( @Nonnull final PropertyDescriptor descriptor )
    {
        return labels.get( descriptor );
    }

    /**
     * Propagate changes from editor components to a beanmapper
     *
     * @param bean a value destination
     */
    public void setBeanValues( @Nonnull final Object bean )
    {
        for ( final Map.Entry<PropertyDescriptor, PropertyMapping> entry : propertyMappings.entrySet() )
        {
            final PropertyMapping propertyMapping = entry.getValue();
            final PropertyDescriptor propertyDescriptor = entry.getKey();

            final Object propertyValue = propertyMapping.getValue();
            Reflection.setValue( bean, propertyValue, propertyDescriptor );
        }
    }

    /**
     * Propagate changes from a beanmapper to editor components.
     *
     * @param bean a value source
     */
    public void setComponentValues( @Nullable final Object bean )
    {
        for ( final Map.Entry<PropertyDescriptor, PropertyMapping> entry : propertyMappings.entrySet() )
        {
            final PropertyMapping propertyMapping = entry.getValue();
            final PropertyDescriptor propertyDescriptor = entry.getKey();

            final Object propertyValue = Reflection.getValue( propertyDescriptor, bean );
            propertyMapping.setValue( propertyValue );
        }
    }
}
