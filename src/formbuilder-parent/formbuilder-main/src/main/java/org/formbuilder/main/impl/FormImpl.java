/**
 *
 */
package org.formbuilder.main.impl;

import org.formbuilder.main.BeanMapper;
import org.formbuilder.main.Form;

import javax.swing.*;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public class FormImpl<B>
        implements Form<B>
{
    private JComponent panel;
    private B value;
    private final Class<B> beanClass;
    private final BeanMapper<B> beanMapper;

    public FormImpl( final Class<B> beanClass,
                     final BeanMapper<B> beanMapper )
    {
        this.beanClass = beanClass;
        this.beanMapper = beanMapper;
    }

    @Override
    public JComponent asComponent()
    {
        if ( panel == null )
        {
            panel = beanMapper.map( beanClass );
        }
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
