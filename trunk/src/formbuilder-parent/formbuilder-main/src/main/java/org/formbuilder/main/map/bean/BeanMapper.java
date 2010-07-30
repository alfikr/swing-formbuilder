/**
 *
 */
package org.formbuilder.main.map.bean;

import org.formbuilder.main.map.Mapping;
import org.formbuilder.main.map.MappingRules;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public interface BeanMapper<B>
{
    Mapping map( Class<B> beanClass,
                    final MappingRules mappingRules );
}
