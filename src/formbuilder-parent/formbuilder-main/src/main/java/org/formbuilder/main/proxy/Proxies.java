/**
 *
 */
package org.formbuilder.main.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author aeremenok 2010
 */
public class Proxies
{
    private static Map<Class, Object> emptyValues = new HashMap<Class, Object>();

    static
    {
        emptyValues.put( Integer.TYPE, Integer.MIN_VALUE );
        emptyValues.put( Long.TYPE, Long.MIN_VALUE );
        emptyValues.put( Short.TYPE, Short.MIN_VALUE );
        emptyValues.put( Character.TYPE, Character.MIN_VALUE );
        emptyValues.put( Double.TYPE, Double.MIN_VALUE );
        emptyValues.put( Float.TYPE, Float.MIN_VALUE );
    }

    @SuppressWarnings( "unchecked" )
    public static <T> T createProxy( final Class<T> beanClass,
                                     final InvocationHandler handler )
    {
        final Enhancer e = new Enhancer();
        e.setSuperclass( beanClass );
        e.setUseFactory( false );
        e.setCallback( handler );
        return (T) e.create();
    }

    public static Object emptyValue( final Method method )
    {
        final Class<?> returnType = method.getReturnType();
        if ( !returnType.isPrimitive() )
        {
            return null;
        }
        return checkNotNull( emptyValues.get( returnType ), "No default return value for method " + method );
    }
}
