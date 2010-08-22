package org.formbuilder.mapping.typemapper;

import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.MappingRules;
import org.formbuilder.mapping.exception.GetterNotFoundException;
import org.formbuilder.mapping.exception.NoGetterProvidedException;
import org.formbuilder.util.MethodRecorder;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.beans.PropertyDescriptor;

/** @author eav Date: 22.08.2010 Time: 21:35:24 */
public class GetterMapperContext
{
    private MappingRules mappingRules;
    private MethodRecorder methodRecorder;

    public GetterMapperContext( @Nonnull final MappingRules mappingRules,
                                @Nonnull final MethodRecorder methodRecorder )
    {
        this.mappingRules = mappingRules;
        this.methodRecorder = methodRecorder;
    }

    public <T> GetterMapperContext mapGetter( @Nullable final T whatProxyGetterReturned,
                                              @Nonnull final TypeMapper<?, ? extends T> mapper )
            throws
            NoGetterProvidedException,
            GetterNotFoundException
    {
        if ( methodRecorder.getLastCalledMethod() == null )
        {
            throw new NoGetterProvidedException();
        }

        final PropertyDescriptor descriptor = Reflection.getDescriptor( methodRecorder.getLastCalledMethod() );
        mappingRules.addMapper( descriptor.getName(), mapper );
        methodRecorder.reset();

        return this;
    }
}
