package org.formbuilder.main.map.type;

import org.formbuilder.main.map.ValueChangeListener;
import org.formbuilder.main.validation.NothingHighlighter;
import org.formbuilder.main.validation.ValidationHighlighter;

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
    public R getValue( final JComboBox component )
    {
        return (R) component.getSelectedItem();
    }

    @Override
    public void setValue( final JComboBox component,
                          final R value )
    {
        component.setSelectedItem( value );
    }

    @Override
    public JComboBox createComponent()
    {
        return new JComboBox( new Vector<R>( getSuitableData() ) );
    }

    protected abstract Collection<R> getSuitableData();

    @Override
    public void bindChangeListener( final JComboBox component,
                                    final ValueChangeListener<R> rValueChangeListener )
    {
        component.addItemListener( new ItemListener()
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
    public ValidationHighlighter getValidationHighlighter()
    {
        return NothingHighlighter.INSTANCE;
    }
}
