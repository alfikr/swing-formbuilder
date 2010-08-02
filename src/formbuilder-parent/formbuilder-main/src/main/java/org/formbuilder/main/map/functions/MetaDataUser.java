package org.formbuilder.main.map.functions;

import org.formbuilder.main.map.metadata.MetaData;

import javax.annotation.Nonnull;

/**
 * @author aeremenok
 *         Date: 02.08.2010
 *         Time: 16:53:02
 */
public abstract class MetaDataUser
{
    protected final MetaData metaData;

    public MetaDataUser( @Nonnull final MetaData metaData )
    {
        this.metaData = metaData;
    }
}
