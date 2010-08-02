/**
 *
 */
package org.main.mapping.bean;

import org.main.mapping.BeanMapping;
import org.main.mapping.MappingRules;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * @author aeremenok 2010
 * @param <B>
 */
@NotThreadSafe
public interface BeanMapper<B>
{
    @Nonnull
    BeanMapping map( @Nonnull final Class<B> beanClass,
                     @Nonnull final MappingRules mappingRules );
}
