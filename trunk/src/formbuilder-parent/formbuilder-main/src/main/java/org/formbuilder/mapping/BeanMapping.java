package org.formbuilder.mapping;

import org.formbuilder.mapping.type.TypeMapper;
import org.formbuilder.util.Reflection;

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
        // todo
//        labels.put( descriptor, label );
    }

    @Nonnull
    public JComponent getPanel()
    {
        return panel;
    }

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

    public void setPanel( @Nonnull final JComponent panel )
    {
        this.panel = panel;
    }
}
