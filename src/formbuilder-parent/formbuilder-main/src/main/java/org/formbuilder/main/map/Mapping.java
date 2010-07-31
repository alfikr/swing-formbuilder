package org.formbuilder.main.map;

import org.formbuilder.main.map.type.TypeMapper;
import org.formbuilder.main.util.Reflection;

import javax.swing.*;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author aeremenok
 *         Date: 28.07.2010
 *         Time: 13:47:07
 */
public class Mapping
{
    private final Map<PropertyDescriptor, PropertyMapping> propertyMappings = new HashMap<PropertyDescriptor, PropertyMapping>();
    private JComponent panel;

    public void setBeanValues( final Object bean )
    {
        for ( Map.Entry<PropertyDescriptor, PropertyMapping> entry : propertyMappings.entrySet() )
        {
            final PropertyMapping propertyMapping = entry.getValue();
            final PropertyDescriptor propertyDescriptor = entry.getKey();

            final Object propertyValue = propertyMapping.getValue();
            Reflection.setValue( bean, propertyValue, propertyDescriptor );
        }
    }

    public void setPanel( final JComponent panel )
    {
        this.panel = panel;
    }

    public JComponent getPanel()
    {
        return panel;
    }

    public void addEditor( PropertyDescriptor descriptor,
                           JComponent editor,
                           TypeMapper mapper )
    {
        propertyMappings.put( descriptor, new PropertyMapping( editor, mapper ) );
    }

    public void addLabel( PropertyDescriptor descriptor,
                          JLabel label )
    {
        // todo
//        labels.put( descriptor, label );
    }

    public void setComponentValues( final Object bean )
    {
        assert SwingUtilities.isEventDispatchThread();
        for ( Map.Entry<PropertyDescriptor, PropertyMapping> entry : propertyMappings.entrySet() )
        {
            final PropertyMapping propertyMapping = entry.getValue();
            final PropertyDescriptor propertyDescriptor = entry.getKey();

            final Object propertyValue = Reflection.getValue( propertyDescriptor, bean );
            propertyMapping.setValue( propertyValue );
        }
    }
}
