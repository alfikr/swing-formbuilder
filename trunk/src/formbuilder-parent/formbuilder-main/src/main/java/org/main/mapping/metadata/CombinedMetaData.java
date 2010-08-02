package org.main.mapping.metadata;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.beans.PropertyDescriptor;

import static org.main.util.TextUtil.capitalize;

/**
 * @author eav
 *         Date: 31.07.2010
 *         Time: 17:04:02
 */
public class CombinedMetaData
        implements MetaData
{
    private final AnnotationMetaData annotationMetaData = new AnnotationMetaData();
    private final UIManagerMetaData uiManagerMetaData = new UIManagerMetaData();

    @Nonnull
    @Override
    public String getTitle( @Nonnull final PropertyDescriptor descriptor )
    {
        return nvl( annotationMetaData.getTitle( descriptor ),
                uiManagerMetaData.getTitle( descriptor ),
                capitalize( descriptor.getDisplayName() ) );
    }

    @Nullable
    @Override
    public Integer getOrder( @Nonnull final PropertyDescriptor descriptor )
    {
        return nvl( annotationMetaData.getOrder( descriptor ), uiManagerMetaData.getOrder( descriptor ) );
    }

    @Override
    public boolean isHidden( @Nonnull final PropertyDescriptor descriptor )
    {
        return annotationMetaData.isHidden( descriptor ) || uiManagerMetaData.isHidden( descriptor );
    }

    @Override
    public boolean isReadOnly( @Nonnull final PropertyDescriptor descriptor )
    {
        return annotationMetaData.isReadOnly( descriptor ) || uiManagerMetaData.isReadOnly( descriptor );
    }

    @Nullable
    private <T> T nvl( T... t )
    { // todo defer calls
        for ( T t0 : t )
        {
            if ( t0 != null )
            {
                return t0;
            }
        }
        return null;
    }
}
