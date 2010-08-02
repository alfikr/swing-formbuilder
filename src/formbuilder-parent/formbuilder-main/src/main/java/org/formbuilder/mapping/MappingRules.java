package org.formbuilder.mapping;

import org.formbuilder.mapping.exception.InvalidPropertyMappingException;
import org.formbuilder.mapping.exception.MappingException;
import org.formbuilder.mapping.exception.UnmappedTypeException;
import org.formbuilder.mapping.type.BooleanToCheckboxMapper;
import org.formbuilder.mapping.type.DateToSpinnerMapper;
import org.formbuilder.mapping.type.NumberToSpinnerMapper;
import org.formbuilder.mapping.type.StringToTextFieldMapper;
import org.formbuilder.mapping.type.TypeMapper;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
    protected final Map<Class, TypeMapper> typeToMapper = new HashMap<Class, TypeMapper>();
    protected final Map<String, TypeMapper> propertyToMapper = new HashMap<String, TypeMapper>();

    public MappingRules()
    {
        super();
        initDefaults();
    }

    protected void initDefaults()
    {
        addMapper( new StringToTextFieldMapper() );
        addMapper( new NumberToSpinnerMapper() );
        addMapper( new BooleanToCheckboxMapper() );
        addMapper( new DateToSpinnerMapper() );
    }

    public void addMapper( @Nonnull TypeMapper mapper )
    {
        typeToMapper.put( mapper.getValueClass(), mapper );
    }

    public void addMapper( @Nonnull String propertyName,
                           @Nonnull TypeMapper mapper )
    {
        propertyToMapper.put( propertyName, mapper );
    }

    @Nonnull
    public TypeMapper getMapper( @Nonnull PropertyDescriptor descriptor )
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

    @Nonnull
    private TypeMapper checkType( @Nonnull final TypeMapper mapper,
                                  @Nonnull final PropertyDescriptor descriptor )
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

    @Nullable
    private TypeMapper findMapperOfSuperType( @Nonnull final Class<?> subtype )
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
