/**
 *
 */
package org.formbuilder;

import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.mapping.MappingRules;

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
                     @Nonnull final MappingRules mappingRules,
                     final boolean doValidation );
}
