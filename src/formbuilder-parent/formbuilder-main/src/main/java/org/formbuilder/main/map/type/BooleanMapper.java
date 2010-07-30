package org.formbuilder.main.map.type;

import org.formbuilder.main.map.ValueChangeListener;
import org.formbuilder.main.validation.BackgroundHighlighter;
import org.formbuilder.main.validation.ValidationHighlighter;

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
    public Class<Boolean> valueClass()
    {
        return Boolean.class;
    }

    @Override
    public Boolean getValue( final JCheckBox component )
    {
        return component.isSelected();
    }

    @Override
    public void setValue( final JCheckBox component,
                          final Boolean value )
    {
        component.setSelected( Boolean.TRUE.equals( value ) );
    }

    @Override
    public JCheckBox createComponent()
    {
        return new JCheckBox();
    }

    @Override
    public void bindChangeListener( final JCheckBox component,
                                    final ValueChangeListener<Boolean> booleanValueChangeListener )
    {
        component.addChangeListener( new ChangeListener()
        {
            @Override
            public void stateChanged( final ChangeEvent e )
            {
                booleanValueChangeListener.onChange();
            }
        } );
    }

    @Override
    public ValidationHighlighter getValidationHighlighter()
    {
        return BackgroundHighlighter.INSTANCE;
    }
}
