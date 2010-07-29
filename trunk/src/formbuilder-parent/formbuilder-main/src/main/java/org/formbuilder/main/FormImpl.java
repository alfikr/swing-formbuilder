/**
 *
 */
package org.formbuilder.main;

import org.formbuilder.main.map.Mapping;
import org.formbuilder.main.map.TypeMappers;
import org.formbuilder.main.map.bean.BeanMapper;
import org.formbuilder.main.util.Reflection;

import javax.swing.*;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public class FormImpl<B>
        implements Form<B>
{
    private Mapping mapping;
    private Class<B> beanClass;

    public FormImpl( final Class<B> beanClass,
                     final BeanMapper<B> beanMapper,
                     final TypeMappers typeMappers )
    {
        this.beanClass = beanClass;
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
        B newBean = Reflection.newInstance( beanClass );
        mapping.setBeanValues(newBean);
        return newBean;
    }

    @Override
    public void setValue( final B bean )
    {
        mapping.setComponentValues( bean );
    }
}
