package org.formbuilder.validation;

import org.formbuilder.mapping.PropertyEditor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.Set;

/**
 * Contains validation results.
 *
 * @author aeremenok Date: 19.08.2010 Time: 18:10:47
 */
public class ValidationEvent<B, C extends JComponent, V>
{
// ------------------------------ FIELDS ------------------------------
    private final PropertyEditor<C, V> propertyEditor;
    private final Set<ConstraintViolation<B>> violations;
    private final V newValue;

// --------------------------- CONSTRUCTORS ---------------------------

    public ValidationEvent( @Nonnull final PropertyEditor<C, V> propertyEditor,
                            @Nonnull final Set<ConstraintViolation<B>> violations,
                            @Nullable final V newValue )
    {
        this.propertyEditor = propertyEditor;
        this.violations = violations;
        this.newValue = newValue;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    /** @return changed property value */
    @Nullable
    public V getNewValue()
    {
        return newValue;
    }

    /**
     * @return constraint violations of a changed property value
     *
     * @see Validator#validateValue(Class, String, Object, Class[])
     */
    @Nonnull
    public Set<ConstraintViolation<B>> getViolations()
    {
        return violations;
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * @return a descriptor of a changed property
     *
     * @see BeanInfo#getPropertyDescriptors()
     */
    @Nonnull
    public PropertyDescriptor getDescriptor()
    {
        return propertyEditor.getDescriptor();
    }

    /** @return an editor component of a changed property */
    @Nonnull
    public C getEditorComponent()
    {
        return propertyEditor.getEditorComponent();
    }

    /** @return a label of a changed property */
    @Nonnull
    public JLabel getLabel()
    {
        return propertyEditor.getLabel();
    }
}
