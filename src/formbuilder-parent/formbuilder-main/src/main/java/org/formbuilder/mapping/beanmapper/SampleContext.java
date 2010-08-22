package org.formbuilder.mapping.beanmapper;

import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.BeanMappingContext;
import org.formbuilder.mapping.exception.GetterNotFoundException;
import org.formbuilder.mapping.exception.InvalidTypeMappingException;
import org.formbuilder.mapping.exception.NoGetterProvidedException;
import org.formbuilder.mapping.exception.UnmappedTypeException;
import org.formbuilder.mapping.metadata.MetaData;
import org.formbuilder.util.MethodRecorder;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.beans.PropertyDescriptor;

/**
 * Provides editor components and labels for called getters of bean sample
 *
 * @author aeremenok Date: Aug 18, 2010 Time: 4:24:58 PM
 */
public class SampleContext<B>
{
// ------------------------------ FIELDS ------------------------------
    private final BeanMappingContext<B> beanMappingContext;
    private final MethodRecorder methodRecorder;

// --------------------------- CONSTRUCTORS ---------------------------

    public SampleContext( @Nonnull final BeanMappingContext<B> beanMappingContext,
                          @Nonnull final MethodRecorder methodRecorder )
    {
        this.beanMappingContext = beanMappingContext;
        this.methodRecorder = methodRecorder;
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * @param whatProxyGetterReturned the result of calling the proxy getter, which is actually ignored. Instead the
     *                                getter call is recorded by a cglib proxy to determine the property.
     * @return an editor component for a property, one instance per property
     * @throws GetterNotFoundException     the method, which was called on a sample bean is not a read method for any
     *                                     property
     * @throws NoGetterProvidedException   no method was called on a sample bean before calling this injection method
     * @throws UnmappedTypeException       cannot find a type mapper for a given property name
     * @throws InvalidTypeMappingException found a type mapper that has a wrong type
     * @see BeanMappingContext#getEditor(PropertyDescriptor)
     * @see TypeMapper#createEditorComponent()
     * @see TypeMapper#getValueClass()
     */
    @Nonnull
    public JComponent editor( @SuppressWarnings( "unused" ) @Nullable final Object whatProxyGetterReturned )
            throws
            GetterNotFoundException,
            NoGetterProvidedException,
            InvalidTypeMappingException,
            UnmappedTypeException
    {
        return beanMappingContext.getEditor( getDescriptor() );
    }

    @Nonnull
    private PropertyDescriptor getDescriptor()
            throws
            GetterNotFoundException,
            NoGetterProvidedException
    {
        if ( methodRecorder.getLastCalledMethod() == null )
        {
            throw new NoGetterProvidedException();
        }

        final PropertyDescriptor descriptor = Reflection.getDescriptor( methodRecorder.getLastCalledMethod() );
        methodRecorder.reset();
        return descriptor;
    }

    /**
     * @param whatProxyGetterReturned the result of calling the proxy getter, which is actually ignored
     * @return a label for a property, one instance per property
     * @throws GetterNotFoundException   the method, which was called on a sample bean is not a read method for any
     *                                   property
     * @throws NoGetterProvidedException no method was called on a sample bean before calling this injection method
     * @see BeanMappingContext#getLabel(PropertyDescriptor)
     * @see MetaData#getTitle(PropertyDescriptor)
     */
    @Nonnull
    public JLabel label( @SuppressWarnings( "unused" ) @Nullable final Object whatProxyGetterReturned )
            throws
            GetterNotFoundException,
            NoGetterProvidedException
    {
        return beanMappingContext.getLabel( getDescriptor() );
    }
}
