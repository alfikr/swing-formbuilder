/**
 *
 */
package org.formbuilder.main;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public interface BeanMapper<B>
{
    Mapping map( Class<B> beanClass,
                    final TypeMappers typeMappers );
}
