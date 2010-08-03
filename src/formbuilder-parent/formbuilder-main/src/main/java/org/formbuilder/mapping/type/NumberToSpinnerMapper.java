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

/**
 * @author aeremenok
 *         Date: 28.07.2010
 *         Time: 11:55:54
 */
@NotThreadSafe
public class NumberToSpinnerMapper
        implements TypeMapper<JSpinner, Number>
{
    @Override
    public void bindChangeListener( @Nonnull final JSpinner editorComponent,
                                    @Nullable final ValueChangeListener<Number> numberChangeListener )
    {
        editorComponent.addChangeListener( new ChangeListener()
        {
            @Override
            public void stateChanged( final ChangeEvent e )
            {
                numberChangeListener.onChange();
            }
        } );
    }

    @Nonnull
    @Override
    public JSpinner createEditorComponent()
    {
        return new JSpinner( new SpinnerNumberModel() );
    }

    @Nonnull
    @Override
    public ValidationMarker getValidationMarker()
    {
        return BackgroundMarker.INSTANCE;
    }

    @Nullable
    @Override
    public Number getValue( @Nonnull final JSpinner editorComponent )
    {
        return (Number) editorComponent.getValue();
    }

    @Nonnull
    @Override
    public Class<Number> getValueClass()
    {
        return Number.class;
    }

    @Override
    public void setValue( @Nonnull final JSpinner editorComponent,
                          @Nullable Number value )
    {
        if ( value == null )
        {
            value = 0;
        }
        editorComponent.setValue( value );
    }
}
