package org.main.mapping;

import org.main.mapping.type.TypeMapper;
import org.main.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 17:54:35
 */
@NotThreadSafe
public abstract class GetterMapper<B>
        implements InvocationHandler
{
    private MappingRules currentMappingRules;
    private Method lastCalledMethod;

    protected abstract void mapGetters( @Nonnull B beanSample );

    protected <T> void mapGetter( T whatProxyGetterReturned,
                                  @Nonnull TypeMapper<?, ? extends T> mapper )
    {
        final String propertyName = Reflection.getDescriptor( lastCalledMethod ).getName();
        currentMappingRules.addMapper( propertyName, mapper );
    }

    public void mapGettersToRules( @Nonnull Class<B> beanClass,
                                   @Nonnull MappingRules mappingRules )
    {
        // todo not running in EDT - restrict threads?
        currentMappingRules = mappingRules;
        mapGetters( Reflection.createProxy( beanClass, this ) );
    }

    @Override
    public Object invoke( final Object proxy,
                          final Method method,
                          final Object[] args )
            throws
            Throwable
    {
        lastCalledMethod = method;
        return Reflection.emptyValue( method );
    }
}
