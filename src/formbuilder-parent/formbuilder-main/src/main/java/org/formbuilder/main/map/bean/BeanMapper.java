/**
 *
 */
package org.formbuilder.main.map.bean;

import org.formbuilder.main.map.Mapping;
import org.formbuilder.main.map.MappingRules;

import javax.annotation.Nonnull;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public interface BeanMapper<B>
{
    @Nonnull
    Mapping map( @Nonnull final Class<B> beanClass,
                 @Nonnull final MappingRules mappingRules );
}
