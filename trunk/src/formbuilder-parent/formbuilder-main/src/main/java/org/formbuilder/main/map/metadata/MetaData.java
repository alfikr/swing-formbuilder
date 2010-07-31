package org.formbuilder.main.map.metadata;

import java.beans.PropertyDescriptor;

/**
 * @author eav
 *         Date: 31.07.2010
 *         Time: 12:17:38
 */
public interface MetaData
{
    String getTitle( PropertyDescriptor descriptor );

    Integer getOrder( PropertyDescriptor descriptor );

    Boolean isHidden( PropertyDescriptor descriptor );

    Boolean isReadOnly( PropertyDescriptor descriptor );
}
