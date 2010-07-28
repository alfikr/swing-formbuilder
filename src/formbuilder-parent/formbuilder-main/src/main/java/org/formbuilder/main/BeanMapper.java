/**
 *
 */
package org.formbuilder.main;

import javax.swing.JComponent;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public interface BeanMapper<B>
{
    JComponent map( Class<B> beanClass,
                    final TypeMappers typeMappers );
}
