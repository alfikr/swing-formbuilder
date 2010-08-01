/**
 *
 */
package org.formbuilder.main.map.bean;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.apache.log4j.Logger;
import org.formbuilder.main.map.Mapping;
import org.formbuilder.main.map.MappingRules;
import org.formbuilder.main.map.exception.MappingException;
import org.formbuilder.main.map.metadata.CombinedMetaData;
import org.formbuilder.main.map.metadata.MetaData;
import org.formbuilder.main.map.type.TypeMapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.beans.PropertyDescriptor;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public abstract class AbstractBeanMapper<B>
        implements BeanMapper<B>
{
    private static final Logger log = Logger.getLogger( AbstractBeanMapper.class );
    protected final MetaData metaData = createMetaData();
    protected final Predicate<PropertyDescriptor> isSupported = new Predicate<PropertyDescriptor>()
    {
        @Override
        public boolean apply( @Nullable final PropertyDescriptor descriptor )
        {
            return isSupported( descriptor );
        }
    };
    protected final Predicate<PropertyDescriptor> isVisible = new Predicate<PropertyDescriptor>()
    {
        @Override
        public boolean apply( @Nullable final PropertyDescriptor descriptor )
        {
            return !metaData.isHidden( descriptor );
        }
    };
    protected final Function<PropertyDescriptor, OrderedPropertyDescriptor> addOrder = new Function<PropertyDescriptor, OrderedPropertyDescriptor>()
    {
        protected int nonNull( @Nullable Integer i )
        {
            if ( i == null )
            {
                return Integer.MAX_VALUE;
            }
            return i;
        }

        @Override
        public OrderedPropertyDescriptor apply( @Nullable final PropertyDescriptor from )
        {
            return new OrderedPropertyDescriptor( from, nonNull( metaData.getOrder( from ) ) );
        }
    };

    protected JLabel createLabel( final Mapping mapping,
                                  final PropertyDescriptor descriptor )
    {
        final JLabel label = new JLabel( metaData.getTitle( descriptor ) );
        label.setName( descriptor.getName() );
        mapping.addLabel( descriptor, label );
        return label;
    }

    @SuppressWarnings( {"unchecked"} )
    protected JComponent createEditor( final PropertyDescriptor descriptor,
                                       final MappingRules mappingRules,
                                       final Mapping mapping )
            throws
            MappingException
    {
        final TypeMapper mapper = mappingRules.getMapper( descriptor );
        final JComponent editor = mapper.createEditorComponent();
        editor.setEnabled( isEditable( descriptor ) );
        editor.setName( descriptor.getName() );

        mapper.bindChangeListener( editor, new ValidateChangedValue( mapper, editor, descriptor ) );

        mapping.addEditor( descriptor, editor, mapper );

        return editor;
    }

    protected void handleMappingException( @Nonnull final MappingException e )
    {
        // todo implement different strategies
        log.warn( "Cannot find mapper for method " + e.getDescriptor() );
    }

    @Nonnull
    protected MetaData createMetaData()
    {
        return new CombinedMetaData();
    }

    protected boolean isSupported( @Nonnull final PropertyDescriptor descriptor )
    {
        return descriptor.getReadMethod() != null && !"class".equals( descriptor.getName() );
    }

    protected boolean isEditable( @Nonnull final PropertyDescriptor descriptor )
    {
        final boolean hasWriteMethod = descriptor.getWriteMethod() != null;
        return hasWriteMethod && !metaData.isReadOnly( descriptor );
    }
}
