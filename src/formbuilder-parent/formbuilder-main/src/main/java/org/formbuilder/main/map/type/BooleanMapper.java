package org.formbuilder.main.map.type;

import org.formbuilder.main.map.ValueChangeListener;
import org.formbuilder.main.validation.BackgroundMarker;
import org.formbuilder.main.validation.ValidationMarker;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 13:38:16
 */
public enum BooleanMapper
        implements TypeMapper<JCheckBox, Boolean>
{
    INSTANCE;

    @Override
    public Class<Boolean> getValueClass()
    {
        return Boolean.class;
    }

    @Override
    public Boolean getValue( final JCheckBox editorComponent )
    {
        return editorComponent.isSelected();
    }

    @Override
    public void setValue( final JCheckBox editorComponent,
                          final Boolean value )
    {
        editorComponent.setSelected( Boolean.TRUE.equals( value ) );
    }

    @Override
    public JCheckBox createEditorComponent()
    {
        return new JCheckBox();
    }

    @Override
    public void bindChangeListener( final JCheckBox editorComponent,
                                    final ValueChangeListener<Boolean> booleanValueChangeListener )
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

    @Override
    public ValidationMarker getValidationMarker()
    {
        return BackgroundMarker.INSTANCE;
    }
}
