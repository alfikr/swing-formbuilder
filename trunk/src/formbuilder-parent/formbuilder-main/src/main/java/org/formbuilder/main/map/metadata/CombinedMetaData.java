package org.formbuilder.main.map.metadata;

import java.beans.PropertyDescriptor;

import static org.formbuilder.main.util.TextUtil.capitalize;

/**
 * @author eav
 *         Date: 31.07.2010
 *         Time: 17:04:02
 */
public class CombinedMetaData
        implements MetaData
{
    private AnnotationMetaData annotationMetaData = new AnnotationMetaData();
    private UIManagerMetaData uiManagerMetaData = new UIManagerMetaData();

    @Override
    public String getTitle( final PropertyDescriptor descriptor )
    {
        return nvl( annotationMetaData.getTitle( descriptor ),
                uiManagerMetaData.getTitle( descriptor ),
                capitalize( descriptor.getDisplayName() ) );
    }

    @Override
    public Integer getOrder( final PropertyDescriptor descriptor )
    {
        return nvl( annotationMetaData.getOrder( descriptor ), uiManagerMetaData.getOrder( descriptor ) );
    }

    @Override
    public Boolean isHidden( final PropertyDescriptor descriptor )
    {
        return nvl( annotationMetaData.isHidden( descriptor ), uiManagerMetaData.isHidden( descriptor ) );
    }

    @Override
    public Boolean isReadOnly( final PropertyDescriptor descriptor )
    {
        return nvl( annotationMetaData.isReadOnly( descriptor ), uiManagerMetaData.isHidden( descriptor ) );
    }

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
