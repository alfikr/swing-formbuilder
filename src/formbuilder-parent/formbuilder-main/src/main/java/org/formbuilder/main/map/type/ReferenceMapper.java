package org.formbuilder.main.map.type;

import org.formbuilder.main.map.ValueChangeListener;
import org.formbuilder.main.validation.DoNothingMarker;
import org.formbuilder.main.validation.ValidationMarker;

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
public abstract class ReferenceMapper<R>
        implements TypeMapper<JComboBox, R>
{
    @SuppressWarnings( {"unchecked"} )
    @Override
    public R getValue( final JComboBox editorComponent )
    {
        return (R) editorComponent.getSelectedItem();
    }

    @Override
    public void setValue( final JComboBox editorComponent,
                          final R value )
    {
        editorComponent.setSelectedItem( value );
    }

    @Override
    public JComboBox createEditorComponent()
    {
        return new JComboBox( new Vector<R>( getSuitableData() ) );
    }

    protected abstract Collection<R> getSuitableData();

    @Override
    public void bindChangeListener( final JComboBox editorComponent,
                                    final ValueChangeListener<R> rValueChangeListener )
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

    @Override
    public ValidationMarker getValidationMarker()
    {
        return DoNothingMarker.INSTANCE;
    }
}
