package org.formbuilder.mapping.metadata;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.beans.PropertyDescriptor;

/**
 * @author eav
 *         Date: 31.07.2010
 *         Time: 12:17:38
 */
public interface MetaData
{
    @Nullable
    Integer getOrder( @Nonnull PropertyDescriptor descriptor );

    @Nullable
    String getTitle( @Nonnull PropertyDescriptor descriptor );

    boolean isHidden( @Nonnull PropertyDescriptor descriptor );

    boolean isReadOnly( @Nonnull PropertyDescriptor descriptor );
}
