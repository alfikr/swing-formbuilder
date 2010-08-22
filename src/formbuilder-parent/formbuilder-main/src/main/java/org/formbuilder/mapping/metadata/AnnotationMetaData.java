/*
 * Copyright (C) 2010 Andrey Yeremenok (eav1986__at__gmail__com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.formbuilder.mapping.metadata;

import org.formbuilder.annotations.UIHidden;
import org.formbuilder.annotations.UIOrder;
import org.formbuilder.annotations.UIReadOnly;
import org.formbuilder.annotations.UITitle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;

/** @author eav Date: 31.07.2010 Time: 12:18:03 */
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
