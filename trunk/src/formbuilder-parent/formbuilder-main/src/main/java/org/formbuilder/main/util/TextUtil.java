package org.formbuilder.main.util;

/**
 * @author aeremenok
 *         Date: 28.07.2010
 *         Time: 13:29:57
 */
public class TextUtil
{
    public static String capitalize( String s )
    {
        return s.substring( 0, 1 ).toUpperCase() + s.substring( 1 );
    }
}
