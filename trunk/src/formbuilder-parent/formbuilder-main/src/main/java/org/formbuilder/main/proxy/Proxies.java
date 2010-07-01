/**
 * 
 */
package org.formbuilder.main.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

/**
 * @author aeremenok 2010
 */
public class Proxies
{
    @SuppressWarnings( "unchecked" )
    public static <T> T createProxy( final Class<T> beanClass, final InvocationHandler handler )
    {
        final Enhancer e = new Enhancer();
        e.setSuperclass( beanClass );
        e.setUseFactory( false );
        e.setCallback( handler );
        return (T) e.create();
    }
}
