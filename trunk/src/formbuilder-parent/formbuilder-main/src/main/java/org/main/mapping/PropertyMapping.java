package org.main.mapping;

import org.main.mapping.type.TypeMapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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

    public PropertyMapping( @Nonnull final JComponent component,
                            @Nonnull final TypeMapper mapper )
    {
        this.component = component;
        this.mapper = mapper;
    }

    @SuppressWarnings( {"unchecked"} )
    public void setValue( @Nullable Object value )
    {
        mapper.setValue( component, value );
    }

    @SuppressWarnings( {"unchecked"} )
    @Nullable
    public Object getValue()
    {
        return mapper.getValue( component );
    }
}
