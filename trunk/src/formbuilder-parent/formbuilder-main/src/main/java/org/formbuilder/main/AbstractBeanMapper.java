/**
 * 
 */
package org.formbuilder.main;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;

import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * @author aeremenok 2010
 * @param <B>
 */
public abstract class AbstractBeanMapper<B>
    implements
    BeanMapper<B>
{
    protected JComponent getComponent( final String propertyName )
    {
        final JTextField jTextField = new JTextField();
        jTextField.setName( propertyName );
        return jTextField;
    }

    protected BeanInfo getBeanInfo( final Class<B> beanClass )
    {
        try
        {
            return Introspector.getBeanInfo( beanClass );
        }
        catch( final IntrospectionException e )
        {
            throw new RuntimeException( e );
        }
    }
}
