package org.formbuilder.mapping.type;

import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.change.ValueChangeListener;
import org.formbuilder.validation.BackgroundMarker;
import org.formbuilder.validation.ValidationMarker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Date;

/**
 * @author aeremenok
 *         Date: 28.07.2010
 *         Time: 11:57:47
 */
@NotThreadSafe
public class DateToSpinnerMapper
        implements TypeMapper<JSpinner, Date>
{
    @Override
    public void bindChangeListener( @Nonnull final JSpinner editorComponent,
                                    @Nonnull final ValueChangeListener<Date> dateValueChangeListener )
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

    @Nonnull
    @Override
    public JSpinner createEditorComponent()
    {
        return new JSpinner( new SpinnerDateModel() );
    }

    @Nonnull
    @Override
    public ValidationMarker getValidationMarker()
    {
        return BackgroundMarker.INSTANCE;
    }

    @Nullable
    @Override
    public Date getValue( @Nonnull final JSpinner editorComponent )
    {
        return (Date) editorComponent.getValue();
    }

    @Nonnull
    @Override
    public Class<Date> getValueClass()
    {
        return Date.class;
    }

    @Override
    public void setValue( @Nonnull final JSpinner editorComponent,
                          @Nullable Date value )
    {
        if ( value == null )
        {
            value = new Date( 0 );
        }
        editorComponent.setValue( value );
    }
}
