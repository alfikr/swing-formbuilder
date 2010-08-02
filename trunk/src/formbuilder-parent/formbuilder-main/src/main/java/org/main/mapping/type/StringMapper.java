package org.main.mapping.type;

import org.main.mapping.ValueChangeListener;
import org.main.validation.BackgroundMarker;
import org.main.validation.ValidationMarker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

/**
 * @author eav
 *         Date: Aug 2, 2010
 *         Time: 11:54:19 PM
 */
public abstract class StringMapper<C extends JTextComponent>
        implements TypeMapper<C, String>
{
    @Override
    public Class<String> getValueClass()
    {
        return String.class;
    }

    @Override
    public String getValue( @Nonnull final C editorComponent )
    {
        return editorComponent.getText();
    }

    @Override
    public void setValue( @Nonnull final C editorComponent,
                          @Nullable final String value )
    {
        editorComponent.setText( value );
    }

    @Override
    public void bindChangeListener( @Nonnull final C editorComponent,
                                    @Nonnull final ValueChangeListener<String> stringValueChangeListener )
    {
        editorComponent.getDocument().addDocumentListener( new DocumentListener()
        {
            @Override
            public void insertUpdate( final DocumentEvent e )
            {
                stringValueChangeListener.onChange();
            }

            @Override
            public void removeUpdate( final DocumentEvent e )
            {
                stringValueChangeListener.onChange();
            }

            @Override
            public void changedUpdate( final DocumentEvent e )
            {
                stringValueChangeListener.onChange();
            }
        } );
    }

    @Override
    public ValidationMarker getValidationMarker()
    {
        return BackgroundMarker.INSTANCE;
    }
}
