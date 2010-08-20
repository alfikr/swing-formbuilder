package org.formbuilder.mapping.change;

import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.PropertyEditor;
import org.formbuilder.validation.ValidateOnChange;

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
        mapper.handleChanges( editorComponent, createValueChangeListener( propertyEditor ) );
    }

    @Nonnull
    @SuppressWarnings( {"unchecked"} )
    protected <V> ChangeHandler<V> createValueChangeListener( final PropertyEditor propertyEditor )
    {
        return doValidation ? new ValidateOnChange( propertyEditor ) : EmptyChangeHandler.INSTANCE;
    }
}