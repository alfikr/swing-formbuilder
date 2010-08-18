package org.formbuilder.util;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/** @author aeremenok Date: Aug 18, 2010 Time: 4:22:16 PM */
public class MethodRecorder
        implements InvocationHandler
{
// ------------------------------ FIELDS ------------------------------
    private Method lastCalledMethod;

// --------------------- GETTER / SETTER METHODS ---------------------

    @Nullable
    public Method getLastCalledMethod()
    {
        return lastCalledMethod;
    }

// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface InvocationHandler ---------------------

    @Override
    public Object invoke( final Object proxy,
                          final Method method,
                          final Object[] args )
    {
        lastCalledMethod = method;
        return Reflection.emptyValue( method );
    }
}
