package org.formbuilder.mapping.metadata;

import org.formbuilder.annotations.UIHidden;
import org.formbuilder.annotations.UIOrder;
import org.formbuilder.annotations.UIReadOnly;
import org.formbuilder.annotations.UITitle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;

/**
 * @author eav
 *         Date: 31.07.2010
 *         Time: 12:18:03
 */
public class AnnotationMetaData
        implements MetaData
{
    @Override
    public Integer getOrder( @Nonnull final PropertyDescriptor descriptor )
    {
        final UIOrder uiOrder = getAnnotation( descriptor, UIOrder.class );
        if ( uiOrder != null )
        {
            return uiOrder.value();
        }
        return null;
    }

    @Override
    @Nullable
    public String getTitle( @Nonnull final PropertyDescriptor descriptor )
    {
        final UITitle uiTitle = getAnnotation( descriptor, UITitle.class );
        if ( uiTitle != null )
        {
            return uiTitle.value();
        }
        return null;
    }

    @Override
    public boolean isHidden( @Nonnull final PropertyDescriptor descriptor )
    {
        return getAnnotation( descriptor, UIHidden.class ) != null;
    }

    @Override
    public boolean isReadOnly( @Nonnull final PropertyDescriptor descriptor )
    {
        return getAnnotation( descriptor, UIReadOnly.class ) != null;
    }

    @Nullable
    protected <T extends Annotation> T getAnnotation( @Nonnull final PropertyDescriptor descriptor,
                                                      @Nonnull final Class<T> annotationClass )
    {
        return descriptor.getReadMethod().getAnnotation( annotationClass );
    }
}
