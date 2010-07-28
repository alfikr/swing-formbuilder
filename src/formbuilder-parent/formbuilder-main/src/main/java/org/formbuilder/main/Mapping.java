package org.formbuilder.main;

import org.apache.log4j.Logger;

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
    private static final Logger log = Logger.getLogger( Mapping.class );
    private JComponent panel;
    private Map<PropertyDescriptor, JLabel> labels = new HashMap<PropertyDescriptor, JLabel>();
    private Map<PropertyDescriptor, PropertyMapping> propertyMappings = new HashMap<PropertyDescriptor, PropertyMapping>();

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

        public void setValue( Object value )
        {
            mapper.setValue( component, value );
        }
    }

    public Mapping( final JComponent panel )
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
        labels.put( descriptor, label );
    }

    public void setComponentValues( final Object bean )
    {
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
            log.error( e, e );
            throw new RuntimeException( e );
        }
    }
}
