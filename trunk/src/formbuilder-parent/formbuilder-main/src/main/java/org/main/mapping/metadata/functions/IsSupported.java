package org.main.mapping.metadata.functions;

import com.google.common.base.Predicate;

import javax.annotation.Nonnull;
import java.beans.PropertyDescriptor;

/**
 * @author aeremenok
 *         Date: 02.08.2010
 *         Time: 16:41:44
 */
public enum IsSupported
        implements Predicate<PropertyDescriptor>
{
    INSTANCE;

    @Override
    public boolean apply( @Nonnull final PropertyDescriptor descriptor )
    {
        return descriptor.getReadMethod() != null && !"class".equals( descriptor.getName() );
    }
}
