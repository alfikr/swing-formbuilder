package org.formbuilder.main.map.type;

import org.formbuilder.main.map.ValueChangeListener;
import org.formbuilder.main.validation.BackgroundMarker;
import org.formbuilder.main.validation.ValidationMarker;

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
    public Class<Date> getValueClass()
    {
        return Date.class;
    }

    @Override
    public Date getValue( final JSpinner editorComponent )
    {
        return (Date) editorComponent.getValue();
    }

    @Override
    public void setValue( final JSpinner editorComponent,
                          Date value )
    {
        if ( value == null )
        {
            value = new Date( 0 );
        }
        editorComponent.setValue( value );
    }

    @Override
    public JSpinner createEditorComponent()
    {
        return new JSpinner( new SpinnerDateModel() );
    }

    @Override
    public void bindChangeListener( final JSpinner editorComponent,
                                    final ValueChangeListener<Date> dateValueChangeListener )
    {
        editorComponent.addChangeListener( new ChangeListener()
        {
            @Override
            public void stateChanged( final ChangeEvent e )
            {
                dateValueChangeListener.onChange();
            }
        } );
    }

    @Override
    public ValidationMarker getValidationMarker()
    {
        return BackgroundMarker.INSTANCE;
    }
}
