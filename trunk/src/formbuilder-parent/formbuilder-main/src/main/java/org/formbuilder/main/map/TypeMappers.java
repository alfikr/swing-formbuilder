package org.formbuilder.main.map;

import org.formbuilder.main.map.type.BooleanMapper;
import org.formbuilder.main.map.type.DateMapper;
import org.formbuilder.main.map.type.NumberMapper;
import org.formbuilder.main.map.type.StringMapper;
import org.formbuilder.main.map.type.TypeMapper;
import org.formbuilder.main.util.Reflection;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author aeremenok
 *         Date: 28.07.2010
 *         Time: 11:39:46
 */
public class TypeMappers
{
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
        addMapper( BooleanMapper.INSTANCE );
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
        final Class<?> boxed = Reflection.box( readMethod.getReturnType() );

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
}
