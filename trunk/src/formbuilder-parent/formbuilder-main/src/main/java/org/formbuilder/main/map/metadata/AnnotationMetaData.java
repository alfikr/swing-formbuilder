package org.formbuilder.main.map.metadata;

import org.formbuilder.main.annotations.UIHidden;
import org.formbuilder.main.annotations.UIOrder;
import org.formbuilder.main.annotations.UIReadOnly;
import org.formbuilder.main.annotations.UITitle;

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
    public String getTitle( final PropertyDescriptor descriptor )
    {
        final UITitle uiTitle = getAnnotation( descriptor, UITitle.class );
        if ( uiTitle != null )
        {
            return uiTitle.value();
        }
        return null;
    }

    @Override
    public Integer getOrder( final PropertyDescriptor descriptor )
    {
        final UIOrder uiOrder = getAnnotation( descriptor, UIOrder.class );
        if ( uiOrder != null )
        {
            return uiOrder.value();
        }
        return null;
    }

    @Override
    public boolean isHidden( final PropertyDescriptor descriptor )
    {
        return getAnnotation( descriptor, UIHidden.class ) != null;
    }

    @Override
    public boolean isReadOnly( final PropertyDescriptor descriptor )
    {
        return getAnnotation( descriptor, UIReadOnly.class ) != null;
    }

    protected <T extends Annotation> T getAnnotation( final PropertyDescriptor descriptor,
                                                      Class<T> annotationClass )
    {
        return descriptor.getReadMethod().getAnnotation( annotationClass );
    }
}
