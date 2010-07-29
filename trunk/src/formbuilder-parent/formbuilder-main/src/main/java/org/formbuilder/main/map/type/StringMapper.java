package org.formbuilder.main.map.type;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author aeremenok
 *         Date: 28.07.2010
 *         Time: 11:22:32
 */
public enum StringMapper
        implements TypeMapper<JTextField, String>
{
    INSTANCE;

    @Override
    public Class<String> valueClass()
    {
        return String.class;
    }

    @Override
    public String getValue( JTextField component )
    {
        return component.getText();
    }

    @Override
    public void setValue( JTextField component,
                          String value )
    {
        component.setText( value );
    }

    @Override
    public JTextField createComponent()
    {
        return new JTextField();
    }

    @Override
    public void bindChangeListener( final JTextField component,
                                    final ValueChangeListener<String> stringChangeListener )
    {
        component.getDocument().addDocumentListener( new DocumentListener()
        {
            @Override
            public void insertUpdate( final DocumentEvent e )
            {
                stringChangeListener.onChange();
            }

            @Override
            public void removeUpdate( final DocumentEvent e )
            {
                stringChangeListener.onChange();
            }

            @Override
            public void changedUpdate( final DocumentEvent e )
            {
                stringChangeListener.onChange();
            }
        } );
    }

    @Override
    public ValidationHighlighter getValidationHighlighter()
    {
        return BackgroundHighlighter.INSTANCE;
    }
}
