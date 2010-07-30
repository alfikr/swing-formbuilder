package org.formbuilder.main.map.type;

import org.formbuilder.main.map.ValueChangeListener;
import org.formbuilder.main.validation.BackgroundHighlighter;
import org.formbuilder.main.validation.ValidationHighlighter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
                          Date value )
    {
        if ( value == null )
        {
            value = new Date( 0 );
        }
        component.setValue( value );
    }

    @Override
    public JSpinner createComponent()
    {
        return new JSpinner( new SpinnerDateModel() );
    }

    @Override
    public void bindChangeListener( final JSpinner component,
                                    final ValueChangeListener<Date> dateValueChangeListener )
    {
        component.addChangeListener( new ChangeListener()
        {
            @Override
            public void stateChanged( final ChangeEvent e )
            {
                dateValueChangeListener.onChange();
            }
        } );
    }

    @Override
    public ValidationHighlighter getValidationHighlighter()
    {
        return BackgroundHighlighter.INSTANCE;
    }
}
