package org.formbuilder.main;

import javax.swing.*;

/**
 * @author aeremenok
 *         Date: 28.07.2010
 *         Time: 11:55:54
 */
public enum NumberMapper
        implements TypeMapper<JSpinner, Number>
{
    INSTANCE;

    @Override
    public Class<Number> valueClass()
    {
        return Number.class;
    }

    @Override
    public Number getValue( final JSpinner component )
    {
        return (Number) component.getValue();
    }

    @Override
    public void setValue( final JSpinner component,
                          final Number value )
    {
        component.setValue( value );
    }

    @Override
    public JSpinner createComponent()
    {
        return new JSpinner( new SpinnerNumberModel() );
    }
}
