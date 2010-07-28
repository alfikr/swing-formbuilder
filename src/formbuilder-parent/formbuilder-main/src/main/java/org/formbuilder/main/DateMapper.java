package org.formbuilder.main;

import javax.swing.*;
import java.util.Date;

/**
 * @author aeremenok
 *         Date: 28.07.2010
 *         Time: 11:57:47
 */
public enum DateMapper
        implements TypeMapper<JSpinner, Date>
{
    INSTANCE;

    @Override
    public Class<Date> valueClass()
    {
        return Date.class;
    }

    @Override
    public Date getValue( final JSpinner component )
    {
        return (Date) component.getValue();
    }

    @Override
    public void setValue( final JSpinner component,
                          final Date value )
    {
        component.setValue( value );
    }

    @Override
    public JSpinner createComponent()
    {
        return new JSpinner( new SpinnerDateModel() );
    }
}
