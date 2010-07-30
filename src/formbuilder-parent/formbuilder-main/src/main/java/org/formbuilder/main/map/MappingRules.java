package org.formbuilder.main.map;

import org.formbuilder.main.map.exception.InvalidPropertyMappingException;
import org.formbuilder.main.map.exception.MappingException;
import org.formbuilder.main.map.exception.UnmappedTypeException;
import org.formbuilder.main.map.type.BooleanMapper;
import org.formbuilder.main.map.type.DateMapper;
import org.formbuilder.main.map.type.NumberMapper;
import org.formbuilder.main.map.type.StringMapper;
import org.formbuilder.main.map.type.TypeMapper;
import org.formbuilder.main.util.Reflection;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author aeremenok
 *         Date: 28.07.2010
 *         Time: 11:39:46
 */
public class MappingRules
{
    private final Map<Class, TypeMapper> typeToMapper = new HashMap<Class, TypeMapper>();
    private final Map<String, TypeMapper> propertyToMapper = new HashMap<String, TypeMapper>();

    public MappingRules()
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
        typeToMapper.put( mapper.getValueClass(), mapper );
    }

    public void addMapper( String propertyName,
                           TypeMapper mapper )
    {
        propertyToMapper.put( propertyName, mapper );
    }

    public TypeMapper getMapper( PropertyDescriptor descriptor )
            throws
            MappingException
    {
        TypeMapper mapper = propertyToMapper.get( descriptor.getName() );
        if ( mapper != null )
        {
            return checkType( mapper, descriptor );
        }

        final Class<?> boxed = Reflection.box( descriptor.getPropertyType() );

        mapper = typeToMapper.get( boxed );
        if ( mapper != null )
        {
            return mapper;
        }

        final TypeMapper mapperOfSuperType = findMapperOfSuperType( boxed );
        if ( mapperOfSuperType != null )
        {
            return mapperOfSuperType;
        }

        throw new UnmappedTypeException( descriptor );
    }

    private TypeMapper checkType( final TypeMapper mapper,
                                  final PropertyDescriptor descriptor )
            throws
            InvalidPropertyMappingException
    {
        final Class<?> boxed = Reflection.box( descriptor.getPropertyType() );
        if ( !boxed.isAssignableFrom( mapper.getValueClass() ) )
        {
            throw new InvalidPropertyMappingException( descriptor, boxed, mapper.getValueClass() );
        }
        return mapper;
    }

    private TypeMapper findMapperOfSuperType( final Class<?> subtype )
    {
        for ( Map.Entry<Class, TypeMapper> entry : typeToMapper.entrySet() )
        {
            if ( entry.getKey().isAssignableFrom( subtype ) )
            {
                return entry.getValue();
            }
        }
        return null;
    }
}
