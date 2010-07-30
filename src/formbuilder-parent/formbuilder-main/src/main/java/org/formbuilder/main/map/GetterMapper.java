package org.formbuilder.main.map;

import org.formbuilder.main.map.type.TypeMapper;
import org.formbuilder.main.util.Reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 17:54:35
 */
public abstract class GetterMapper<B>
        implements InvocationHandler
{
    private MappingRules currentMappingRules;
    private Method lastCalledMethod;

    protected abstract void mapGetters( B beanSample );

    protected <T> void mapGetter( T whatProxyGetterReturned,
                                  TypeMapper<?, ? extends T> mapper )
    {
        final String propertyName = Reflection.getDescriptor( lastCalledMethod ).getName();
        currentMappingRules.addMapper( propertyName, mapper );
    }

    public void mapGettersToRules( Class<B> beanClass,
                                   MappingRules mappingRules )
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
