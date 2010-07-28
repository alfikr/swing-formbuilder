package org.formbuilder.main;

import java.lang.reflect.Method;

/**
 * @author aeremenok
 *         Date: 28.07.2010
 *         Time: 12:31:28
 */
public class MapperNotFoundException
        extends Exception
{
    private final Method readMethod;

    public MapperNotFoundException( final Method readMethod )
    {
        this.readMethod = readMethod;
    }

    public Method getReadMethod()
    {
        return readMethod;
    }
}
