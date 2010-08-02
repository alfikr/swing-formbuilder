/**
 *
 */
package org.formbuilder.util;

import net.sf.cglib.proxy.Enhancer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author aeremenok 2010
 */
public class Reflection
{
    private static final Map<Class, Object> emptyValues = new HashMap<Class, Object>();
    private static final Map<Class, Class> primitiveToBox = new HashMap<Class, Class>();

    static
    {
        emptyValues.put( Integer.TYPE, Integer.MIN_VALUE );
        emptyValues.put( Long.TYPE, Long.MIN_VALUE );
        emptyValues.put( Short.TYPE, Short.MIN_VALUE );
        emptyValues.put( Character.TYPE, Character.MIN_VALUE );
        emptyValues.put( Double.TYPE, Double.MIN_VALUE );
        emptyValues.put( Float.TYPE, Float.MIN_VALUE );
    }

    static
    {
        primitiveToBox.put( Integer.TYPE, Integer.class );
        primitiveToBox.put( Long.TYPE, Long.class );

        primitiveToBox.put( Double.TYPE, Double.class );
        primitiveToBox.put( Float.TYPE, Float.class );

        primitiveToBox.put( Boolean.TYPE, Boolean.class );

        primitiveToBox.put( Character.TYPE, Character.class );
        primitiveToBox.put( Short.TYPE, Short.class );

        // todo will ever be used?
        primitiveToBox.put( Void.TYPE, Void.class );
    }

    @SuppressWarnings( "unchecked" )
    @Nonnull
    public static <T> T createProxy( @Nonnull final Class<T> beanClass,
                                     @Nonnull final InvocationHandler handler )
    {
        final Enhancer e = new Enhancer();
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

    @Nullable
    public static Object emptyValue( @Nonnull final Method method )
    {
        final Class<?> returnType = method.getReturnType();
        if ( !returnType.isPrimitive() )
        {
            return null;
        }
        return checkNotNull( emptyValues.get( returnType ), "No default return value for method " + method );
    }

    @Nonnull
    public static <T> T newInstance( @Nonnull Class<T> aClass )
    {
        try
        {
            return aClass.newInstance();
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }
    }

    @Nonnull
    public static Class<?> box( @Nonnull final Class<?> mayBePrimitive )
    {
        if ( !mayBePrimitive.isPrimitive() )
        {
            return mayBePrimitive;
        }

        final Class boxed = primitiveToBox.get( mayBePrimitive );
        return checkNotNull( boxed, "Cannot box primitive type " + mayBePrimitive );
    }

    @Nonnull
    public static BeanInfo getBeanInfo( @Nonnull final Class<?> beanClass )
    {
        try
        {
            return Introspector.getBeanInfo( beanClass );
        }
        catch ( final IntrospectionException e )
        {
            throw new RuntimeException( e );
        }
    }

    @Nonnull
    public static PropertyDescriptor getDescriptor( @Nonnull final Method readMethod )
    {
        BeanInfo info = getBeanInfo( readMethod.getDeclaringClass() );
        for ( PropertyDescriptor descriptor : info.getPropertyDescriptors() )
        {
            if ( readMethod.equals( descriptor.getReadMethod() ) )
            {
                return descriptor;
            }
        }
        throw new RuntimeException( readMethod + " is not a getter method for a bean" );
    }

    public static void setValue( @Nonnull final Object bean,
                                 @Nullable final Object propertyValue,
                                 @Nonnull final PropertyDescriptor propertyDescriptor )
    {
        try
        {
            propertyDescriptor.getWriteMethod().invoke( bean, propertyValue );
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }
    }

    @Nullable
    public static Object getValue( @Nonnull final PropertyDescriptor descriptor,
                                   @Nullable final Object bean )
    {
        if ( bean == null )
        {
            return emptyValue( descriptor.getReadMethod() );
        }

        try
        {
            return descriptor.getReadMethod().invoke( bean );
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }
    }
}