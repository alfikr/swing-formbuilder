package org.formbuilder.main.map;

import org.formbuilder.main.map.type.TypeMapper;

import javax.swing.*;

/**
 * @author eav
 *         Date: 31.07.2010
 *         Time: 17:19:34
 */
public class PropertyMapping
{
    private JComponent component;
    private TypeMapper mapper;

    public PropertyMapping( final JComponent component,
                            final TypeMapper mapper )
    {
        this.component = component;
        this.mapper = mapper;
    }

    @SuppressWarnings( {"unchecked"} )
    public void setValue( Object value )
    {
        mapper.setValue( component, value );
    }

    @SuppressWarnings( {"unchecked"} )
    public Object getValue()
    {
        return mapper.getValue( component );
    }
}
