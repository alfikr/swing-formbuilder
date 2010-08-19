package org.formbuilder.validation;

import org.formbuilder.mapping.PropertyEditor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import javax.validation.ConstraintViolation;
import java.beans.PropertyDescriptor;
import java.util.Set;

/** @author aeremenok Date: 19.08.2010 Time: 18:10:47 */
public class ValidationContext<B, C extends JComponent, V>
{
    private final PropertyEditor<C, V> propertyEditor;
    private final Set<ConstraintViolation<B>> violations;
    private final V newValue;

    public ValidationContext( @Nonnull final PropertyEditor<C, V> propertyEditor,
                              @Nonnull final Set<ConstraintViolation<B>> violations,
                              @Nullable final V newValue )
    {
        this.propertyEditor = propertyEditor;
        this.violations = violations;
        this.newValue = newValue;
    }

    @Nonnull
    public Set<ConstraintViolation<B>> getViolations()
    {
        return violations;
    }

    @Nullable
    public V getNewValue()
    {
        return newValue;
    }

    @Nonnull
    public C getEditorComponent()
    {
        return propertyEditor.getEditorComponent();
    }

    @Nonnull
    public JLabel getLabel()
    {
        return propertyEditor.getLabel();
    }

    @Nonnull
    public PropertyDescriptor getDescriptor()
    {
        return propertyEditor.getDescriptor();
    }
}
