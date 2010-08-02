package org.main.mapping;

import org.main.mapping.type.TypeMapper;
import org.main.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author aeremenok
 *         Date: 28.07.2010
 *         Time: 13:47:07
 */
public class BeanMapping
{
    private final Map<PropertyDescriptor, PropertyMapping> propertyMappings = new HashMap<PropertyDescriptor, PropertyMapping>();
    @Nonnull
    private JComponent panel;

    public void setBeanValues( @Nonnull final Object bean )
    {
        for ( Map.Entry<PropertyDescriptor, PropertyMapping> entry : propertyMappings.entrySet() )
        {
            final PropertyMapping propertyMapping = entry.getValue();
            final PropertyDescriptor propertyDescriptor = entry.getKey();

            final Object propertyValue = propertyMapping.getValue();
            Reflection.setValue( bean, propertyValue, propertyDescriptor );
        }
    }

    public void setPanel( @Nonnull final JComponent panel )
    {
        this.panel = panel;
    }

    @Nonnull
    public JComponent getPanel()
    {
        return panel;
    }

    public void addEditor( @Nonnull PropertyDescriptor descriptor,
                           @Nonnull JComponent editor,
                           @Nonnull TypeMapper mapper )
    {
        propertyMappings.put( descriptor, new PropertyMapping( editor, mapper ) );
    }

    public void addLabel( @Nonnull PropertyDescriptor descriptor,
                          @Nonnull JLabel label )
    {
        // todo
//        labels.put( descriptor, label );
    }

    public void setComponentValues( @Nullable final Object bean )
    {
        for ( Map.Entry<PropertyDescriptor, PropertyMapping> entry : propertyMappings.entrySet() )
        {
            final PropertyMapping propertyMapping = entry.getValue();
            final PropertyDescriptor propertyDescriptor = entry.getKey();

            final Object propertyValue = Reflection.getValue( propertyDescriptor, bean );
            propertyMapping.setValue( propertyValue );
        }
    }
}
