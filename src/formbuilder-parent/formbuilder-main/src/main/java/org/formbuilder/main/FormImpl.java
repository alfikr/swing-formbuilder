/**
 *
 */
package org.formbuilder.main;

import org.formbuilder.main.map.BeanMapping;
import org.formbuilder.main.map.MappingRules;
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
    private BeanMapping beanMapping;
    private Class<B> beanClass;

    public FormImpl( final Class<B> beanClass,
                     final BeanMapper<B> beanMapper,
                     final MappingRules mappingRules )
    {
        this.beanClass = beanClass;
        this.beanMapping = beanMapper.map( beanClass, mappingRules );
    }

    @Override
    public JComponent asComponent()
    {
        return beanMapping.getPanel();
    }

    @Override
    public B getValue()
    {
        B newBean = Reflection.newInstance( beanClass );
        beanMapping.setBeanValues( newBean );
        return newBean;
    }

    @Override
    public void setValue( final B bean )
    {
        assert SwingUtilities.isEventDispatchThread();
        beanMapping.setComponentValues( bean );
    }
}
