/**
 *
 */
package org.formbuilder.mapping;

import org.formbuilder.Form;
import org.formbuilder.mapping.bean.BeanMapper;
import org.formbuilder.util.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;

import static com.google.common.base.Preconditions.checkState;
import static javax.swing.SwingUtilities.isEventDispatchThread;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public class FormImpl<B>
        implements Form<B>
{
    private final BeanMapping beanMapping;
    private final Class<B> beanClass;

    public FormImpl( @Nonnull final Class<B> beanClass,
                     @Nonnull final BeanMapper<B> beanMapper,
                     @Nonnull final MappingRules mappingRules )
    {
        this.beanClass = beanClass;
        this.beanMapping = beanMapper.map( beanClass, mappingRules );
    }

    @Nonnull
    @Override
    public JComponent asComponent()
    {
        return beanMapping.getPanel();
    }

    @Nonnull
    @Override
    public B getValue()
    {
        final B newBean = Reflection.newInstance( beanClass );
        beanMapping.setBeanValues( newBean );
        return newBean;
    }

    @Override
    public void setValue( @Nullable final B bean )
    {
        checkState( isEventDispatchThread() );
        beanMapping.setComponentValues( bean );
    }
}
