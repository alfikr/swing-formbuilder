package org.formbuilder.mapping.exception;

/** @author eav Date: 22.08.2010 Time: 12:34:45 */
public class NoGetterProvidedException
        extends MappingException
{
    public NoGetterProvidedException()
    {
        super( "A getter must be called for component injection", null );
    }
}
