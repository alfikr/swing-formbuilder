package org.formbuilder.mapping.type;

import org.formbuilder.mapping.ValueChangeListener;
import org.formbuilder.validation.DoNothingMarker;
import org.formbuilder.validation.ValidationMarker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collection;
import java.util.Vector;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 13:41:29
 */
@NotThreadSafe
public abstract class ReferenceMapper<R>
        implements TypeMapper<JComboBox, R>
{
    @Nullable
    @SuppressWarnings( {"unchecked"} )
    @Override
    public R getValue( @Nonnull final JComboBox editorComponent )
    {
        return (R) editorComponent.getSelectedItem();
    }

    @Override
    public void setValue( @Nonnull final JComboBox editorComponent,
                          @Nullable final R value )
    {
        editorComponent.setSelectedItem( value );
    }

    @Nonnull
    @Override
    public JComboBox createEditorComponent()
    {
        return new JComboBox( new Vector<R>( getSuitableData() ) );
    }

    @Nonnull
    protected abstract Collection<R> getSuitableData();

    @Override
    public void bindChangeListener( @Nonnull final JComboBox editorComponent,
                                    @Nonnull final ValueChangeListener<R> rValueChangeListener )
    {
        editorComponent.addItemListener( new ItemListener()
        {
            @Override
            public void itemStateChanged( final ItemEvent e )
            {
                if ( e.getStateChange() == ItemEvent.SELECTED )
                {
                    rValueChangeListener.onChange();
                }
            }
        } );
    }

    @Nonnull
    @Override
    public ValidationMarker getValidationMarker()
    {
        return DoNothingMarker.INSTANCE;
    }
}
