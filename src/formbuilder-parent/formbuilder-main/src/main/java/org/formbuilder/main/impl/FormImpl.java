/**
 *
 */
package org.formbuilder.main.impl;

import org.formbuilder.main.BeanMapper;
import org.formbuilder.main.Form;
import org.formbuilder.main.Mapping;
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
    private Mapping mapping;

    public FormImpl( final Class<B> beanClass,
                     final BeanMapper<B> beanMapper,
                     final TypeMappers typeMappers )
    {
        this.mapping = beanMapper.map( beanClass, typeMappers );
    }

    @Override
    public JComponent asComponent()
    {
        return mapping.getPanel();
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
        mapping.setComponentValues( bean );
    }
}
