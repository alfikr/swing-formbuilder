/**
 * 
 */
package org.formbuilder.main.impl;

import javax.swing.JComponent;

import org.formbuilder.main.BeanMapper;
import org.formbuilder.main.Form;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public class FormImpl<B>
    implements
    Form<B>
{
    private final JComponent panel;
    private B                value;

    public FormImpl( final Class<B> beanClass, final BeanMapper<B> beanMapper )
    {
        panel = beanMapper.map( beanClass );
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
