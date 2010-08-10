package org.formbuilder.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;

/**
 * @author aeremenok
 *         Date: 09.08.2010
 *         Time: 17:38:47
 */
public class CGLibUtil
{
    @SuppressWarnings( {"unchecked"} )
    @Nonnull
    static <T> T createCGLibProxy( final Class<T> beanClass,
                                           final InvocationHandler handler )
    {
        final net.sf.cglib.proxy.Enhancer e = new net.sf.cglib.proxy.Enhancer();
        e.setSuperclass( beanClass );
        e.setUseFactory( false );
        e.setCallback( new net.sf.cglib.proxy.InvocationHandler()
        {
            @Override
            public Object invoke( final Object proxy,
                                  final Method method,
                                  final Object[] args )
                    throws
                    Throwable
            {
                return handler.invoke( proxy, method, args );
            }
        } );
        return (T) e.create();
    }
}
