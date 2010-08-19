package org.formbuilder.mapping;

import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.change.EmptyChangeListener;
import org.formbuilder.mapping.change.ValueChangeListener;
import org.formbuilder.validation.ValidateChangedValue;

import javax.annotation.Nonnull;
import javax.swing.*;

/** @author aeremenok Date: 19.08.2010 Time: 17:57:30 */
public class ChangeObservation
{
    private final boolean doValidation;

    public ChangeObservation( final boolean doValidation )
    {
        this.doValidation = doValidation;
    }

    @SuppressWarnings( {"unchecked"} )
    public void observe( final PropertyEditor propertyEditor )
    {
        final JComponent editorComponent = propertyEditor.getEditorComponent();
        final TypeMapper mapper = propertyEditor.getMapper();
        mapper.bindChangeListener( editorComponent, createValueChangeListener( propertyEditor ) );
    }

    @Nonnull
    @SuppressWarnings( {"unchecked"} )
    protected <V> ValueChangeListener<V> createValueChangeListener( final PropertyEditor propertyEditor )
    {
        return doValidation ? new ValidateChangedValue( propertyEditor ) : EmptyChangeListener.INSTANCE;
    }
}
