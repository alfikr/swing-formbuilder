/**
 *
 */
package org.formbuilder.main.impl;

import org.formbuilder.main.BeanMapper;
import org.formbuilder.main.Form;
import org.formbuilder.main.TypeMappers;

import javax.swing.*;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public class FormImpl<B>
        implements Form<B>
{
    private B value;
    private final JComponent panel;

    public FormImpl( final Class<B> beanClass,
                     final BeanMapper<B> beanMapper,
                     final TypeMappers typeMappers )
    {
        this.panel = beanMapper.map( beanClass, typeMappers );
    }

    @Override
    public JComponent asComponent()
    {
        return panel;
    }

    @Override
    public B getValue()
    {
        return value;
    }

    @Override
    public void setValue( final B bean )
    {
        this.value = bean;
    }
}
