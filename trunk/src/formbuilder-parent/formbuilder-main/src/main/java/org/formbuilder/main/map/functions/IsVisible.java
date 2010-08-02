package org.formbuilder.main.map.functions;

import com.google.common.base.Predicate;
import org.formbuilder.main.map.metadata.MetaData;

import javax.annotation.Nonnull;
import java.beans.PropertyDescriptor;

/**
 * @author aeremenok
 *         Date: 02.08.2010
 *         Time: 16:56:56
 */
public class IsVisible
        extends MetaDataUser
        implements Predicate<PropertyDescriptor>
{
    public IsVisible( @Nonnull final MetaData metaData )
    {
        super( metaData );
    }

    @Override
    public boolean apply( @Nonnull final PropertyDescriptor input )
    {
        return !metaData.isHidden( input );
    }
}
