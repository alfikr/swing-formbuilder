/**
 *
 */
package org.formbuilder.main.map.bean;

import org.apache.log4j.Logger;
import org.formbuilder.main.map.BeanMapping;
import org.formbuilder.main.map.MappingRules;
import org.formbuilder.main.map.exception.MappingException;
import org.formbuilder.main.map.metadata.CombinedMetaData;
import org.formbuilder.main.map.metadata.MetaData;
import org.formbuilder.main.map.type.TypeMapper;
import org.formbuilder.main.validation.ValidateChangedValue;

import javax.annotation.Nonnull;
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

    @Nonnull
    protected JLabel createLabel( @Nonnull final BeanMapping beanMapping,
                                  @Nonnull final PropertyDescriptor descriptor )
    {
        final JLabel label = new JLabel( metaData.getTitle( descriptor ) );
        label.setName( descriptor.getName() );
        beanMapping.addLabel( descriptor, label );
        return label;
    }

    @SuppressWarnings( {"unchecked"} )
    @Nonnull
    protected JComponent createEditor( @Nonnull final PropertyDescriptor descriptor,
                                       @Nonnull final MappingRules mappingRules,
                                       @Nonnull final BeanMapping beanMapping )
            throws
            MappingException
    {
        final TypeMapper mapper = mappingRules.getMapper( descriptor );
        final JComponent editor = mapper.createEditorComponent();
        editor.setEnabled( isEditable( descriptor ) );
        editor.setName( descriptor.getName() );

        mapper.bindChangeListener( editor, new ValidateChangedValue( mapper, editor, descriptor ) );

        beanMapping.addEditor( descriptor, editor, mapper );

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

    protected boolean isEditable( @Nonnull final PropertyDescriptor descriptor )
    {
        final boolean hasWriteMethod = descriptor.getWriteMethod() != null;
        return hasWriteMethod && !metaData.isReadOnly( descriptor );
    }
}
