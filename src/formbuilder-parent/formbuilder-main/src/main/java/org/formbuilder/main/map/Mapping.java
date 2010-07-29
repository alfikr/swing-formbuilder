package org.formbuilder.main.map;

import org.formbuilder.main.map.type.TypeMapper;

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
            setValue( bean, propertyValue, propertyDescriptor );
        }
    }

    private void setValue( final Object bean,
                           final Object propertyValue,
                           final PropertyDescriptor propertyDescriptor )
    {
        try
        {
            propertyDescriptor.getWriteMethod().invoke( bean, propertyValue );
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }
    }

    private class PropertyMapping
    {
        private JComponent component;
        private TypeMapper mapper;

        private PropertyMapping( final JComponent component,
                                 final TypeMapper mapper )
        {
            this.component = component;
            this.mapper = mapper;
        }

        @SuppressWarnings( {"unchecked"} )
        public void setValue( Object value )
        { mapper.setValue( component, value ); }

        @SuppressWarnings( {"unchecked"} )
        public Object getValue()
        {return mapper.getValue( component );}
    }

    public Mapping( final JComponent panel )
    {
        this.panel = panel;
    }

    public Mapping()
    {
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

            final Object propertyValue = getValue( propertyDescriptor, bean );
            propertyMapping.setValue( propertyValue );
        }
    }

    private Object getValue( final PropertyDescriptor descriptor,
                             final Object bean )
    {
        try
        {
            return descriptor.getReadMethod().invoke( bean );
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }
    }
}
