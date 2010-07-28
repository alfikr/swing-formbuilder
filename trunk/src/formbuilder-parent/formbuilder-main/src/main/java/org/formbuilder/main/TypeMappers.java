package org.formbuilder.main;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author aeremenok
 *         Date: 28.07.2010
 *         Time: 11:39:46
 */
public class TypeMappers
{
    private static Map<Class, Class> primitiveToBox = new HashMap<Class, Class>();

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
//        primitiveToBox.put( Void.TYPE, Void.class );
    }

    private Map<Class, TypeMapper> mappers = new HashMap<Class, TypeMapper>();

    public TypeMappers()
    {
        super();
        initDefaults();
    }

    protected void initDefaults()
    {
        addMapper( StringMapper.INSTANCE );
        addMapper( NumberMapper.INSTANCE );
        addMapper( DateMapper.INSTANCE );
    }

    public void addMapper( TypeMapper mapper )
    {
        mappers.put( mapper.valueClass(), mapper );
    }

    public TypeMapper getMapper( Method readMethod )
            throws
            MapperNotFoundException
    {
        final Class<?> boxed = box( readMethod.getReturnType() );

        final TypeMapper mapper = mappers.get( boxed );
        if ( mapper != null )
        {
            return mapper;
        }

        final TypeMapper mapperOfSuperType = findMapperOfSuperType( boxed );
        if ( mapperOfSuperType != null )
        {
            return mapperOfSuperType;
        }

        throw new MapperNotFoundException( readMethod );
    }

    private TypeMapper findMapperOfSuperType( final Class<?> subtype )
    {
        for ( Map.Entry<Class, TypeMapper> entry : mappers.entrySet() )
        {
            if ( entry.getKey().isAssignableFrom( subtype ) )
            {
                return entry.getValue();
            }
        }
        return null;
    }

    private Class<?> box( final Class<?> mayBePrimitive )
    {
        if ( mayBePrimitive.isPrimitive() )
        {
            final Class boxed = primitiveToBox.get( mayBePrimitive );
            return checkNotNull( boxed, "Cannot box primitive type " + mayBePrimitive );
        }
        return mayBePrimitive;
    }
}
