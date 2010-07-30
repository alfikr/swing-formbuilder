package org.formbuilder.main.map.type;

import org.formbuilder.main.map.ValueChangeListener;
import org.formbuilder.main.validation.BackgroundMarker;
import org.formbuilder.main.validation.ValidationMarker;

import javax.swing.*;
import javax.swing.event.*;

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
    public Class<Number> getValueClass()
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

    @Override
    public void bindChangeListener( final JSpinner component,
                                    final ValueChangeListener<Number> numberChangeListener )
    {
        component.addChangeListener( new ChangeListener()
        {
            @Override
            public void stateChanged( final ChangeEvent e )
            {
                numberChangeListener.onChange();
            }
        } );
    }

    @Override
    public ValidationMarker getValidationHighlighter()
    {
        return BackgroundMarker.INSTANCE;
    }
}
