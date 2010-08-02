package org.main.mapping.type;

import org.main.mapping.ValueChangeListener;
import org.main.validation.BackgroundMarker;
import org.main.validation.ValidationMarker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 13:38:16
 */
@NotThreadSafe
public enum BooleanMapper
        implements TypeMapper<JCheckBox, Boolean>
{
    INSTANCE;

    @Nonnull
    @Override
    public Class<Boolean> getValueClass()
    {
        return Boolean.class;
    }

    @Override
    public Boolean getValue( @Nonnull final JCheckBox editorComponent )
    {
        return editorComponent.isSelected();
    }

    @Override
    public void setValue( @Nonnull final JCheckBox editorComponent,
                          @Nullable final Boolean value )
    {
        editorComponent.setSelected( Boolean.TRUE.equals( value ) );
    }

    @Nonnull
    @Override
    public JCheckBox createEditorComponent()
    {
        return new JCheckBox();
    }

    @Override
    public void bindChangeListener( @Nonnull final JCheckBox editorComponent,
                                    @Nonnull final ValueChangeListener<Boolean> booleanValueChangeListener )
    {
        editorComponent.addChangeListener( new ChangeListener()
        {
            @Override
            public void stateChanged( final ChangeEvent e )
            {
                booleanValueChangeListener.onChange();
            }
        } );
    }

    @Nonnull
    @Override
    public ValidationMarker getValidationMarker()
    {
        return BackgroundMarker.INSTANCE;
    }
}
