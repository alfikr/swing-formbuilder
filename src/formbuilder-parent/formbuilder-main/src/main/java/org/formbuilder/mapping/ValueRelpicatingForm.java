/**
 *
 */
package org.formbuilder.mapping;

import org.formbuilder.Form;
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
public class ValueRelpicatingForm<B>
        implements Form<B>
{
    private final BeanMapping beanMapping;
    private final Class<B> beanClass;

    public ValueRelpicatingForm( @Nonnull final BeanMapping beanMapping,
                                 @Nonnull final Class<B> beanClass )
    {
        this.beanMapping = beanMapping;
        this.beanClass = beanClass;
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
