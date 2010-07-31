package org.formbuilder.main.map.type;

import org.formbuilder.main.map.ValueChangeListener;
import org.formbuilder.main.validation.BackgroundMarker;
import org.formbuilder.main.validation.ValidationMarker;

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
    public Class<String> getValueClass()
    {
        return String.class;
    }

    @Override
    public String getValue( JTextField editorComponent )
    {
        return editorComponent.getText();
    }

    @Override
    public void setValue( JTextField editorComponent,
                          String value )
    {
        editorComponent.setText( value );
    }

    @Override
    public JTextField createEditorComponent()
    {
        return new JTextField();
    }

    @Override
    public void bindChangeListener( final JTextField editorComponent,
                                    final ValueChangeListener<String> stringChangeListener )
    {
        editorComponent.getDocument().addDocumentListener( new DocumentListener()
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
    public ValidationMarker getValidationMarker()
    {
        return BackgroundMarker.INSTANCE;
    }
}
