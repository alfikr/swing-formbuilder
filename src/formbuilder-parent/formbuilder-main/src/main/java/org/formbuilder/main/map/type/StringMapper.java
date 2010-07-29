package org.formbuilder.main.map.type;

import javax.swing.*;

/**
 * @author aeremenok
 *         Date: 28.07.2010
 *         Time: 11:22:32
 */
public enum StringMapper
        implements TypeMapper<JTextField, String>
{
    INSTANCE;

    @Override
    public Class<String> valueClass()
    {
        return String.class;
    }

    @Override
    public String getValue( JTextField component )
    {
        return component.getText();
    }

    @Override
    public void setValue( JTextField component,
                          String value )
    {
        component.setText( value );
    }

    @Override
    public JTextField createComponent()
    {
        return new JTextField();
    }
}
