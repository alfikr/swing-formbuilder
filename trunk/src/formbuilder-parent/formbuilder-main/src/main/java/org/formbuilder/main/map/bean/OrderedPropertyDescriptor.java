package org.formbuilder.main.map.bean;

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

    public OrderedPropertyDescriptor( final PropertyDescriptor descriptor,
                                      final int order )
    {
        this.order = order;
        this.descriptor = descriptor;
    }

    public int getOrder()
    {
        return order;
    }

    public PropertyDescriptor getDescriptor()
    {
        return descriptor;
    }
}
