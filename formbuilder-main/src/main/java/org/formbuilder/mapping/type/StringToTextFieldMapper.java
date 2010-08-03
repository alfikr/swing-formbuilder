package org.formbuilder.mapping.type;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;

/**
 * @author aeremenok
 *         Date: 28.07.2010
 *         Time: 11:22:32
 */
@NotThreadSafe
public class StringToTextFieldMapper
        extends StringMapper<JTextField>
{
    @Nonnull
    @Override
    public JTextField createEditorComponent()
    {
        return new JTextField();
    }
}
