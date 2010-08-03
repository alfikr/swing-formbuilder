package org.formbuilder;

import org.formbuilder.mapping.MappingRules;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 17:54:35
 * @param <B>
 */
@NotThreadSafe
public abstract class GetterMapper<B>
{
    private MappingRules currentMappingRules;
    private Method lastCalledMethod;
    private final InvocationHandler invocationHandler = new InvocationHandler()
    {
        @Override
        public Object invoke( final Object proxy,
                              final Method method,
                              final Object[] args )
        {
            lastCalledMethod = method;
            return Reflection.emptyValue( method );
        }
    };

    protected void mapGettersToRules( @Nonnull final Class<B> beanClass,
                                      @Nonnull final MappingRules mappingRules )
    {
        currentMappingRules = mappingRules;
        mapGetters( Reflection.createProxy( beanClass, invocationHandler ) );
    }

    protected <T> void mapGetter( @SuppressWarnings( "unused" ) @Nullable final T whatProxyGetterReturned,
                                  @Nonnull final TypeMapper<?, ? extends T> mapper )
    {
        final String propertyName = Reflection.getDescriptor( lastCalledMethod ).getName();
        currentMappingRules.addMapper( propertyName, mapper );
    }

    protected abstract void mapGetters( @Nonnull B beanSample );
}
