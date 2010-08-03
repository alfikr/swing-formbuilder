package org.formbuilder.mapping.metadata.sort;

import javax.annotation.Nonnull;
import java.beans.PropertyDescriptor;

/**
 * @author eav
 *         Date: 01.08.2010
 *         Time: 22:53:40
 */
public class OrderedPropertyDescriptor
{
    private final int order;
    private final PropertyDescriptor descriptor;

    public OrderedPropertyDescriptor( @Nonnull final PropertyDescriptor descriptor,
                                      final int order )
    {
        this.order = order;
        this.descriptor = descriptor;
    }

    @Nonnull
    public PropertyDescriptor getDescriptor()
    {
        return descriptor;
    }

    public int getOrder()
    {
        return order;
    }
}
