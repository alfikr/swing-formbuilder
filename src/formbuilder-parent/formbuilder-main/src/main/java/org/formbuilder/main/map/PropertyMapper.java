/**
 *
 */
package org.formbuilder.main.map;

import javax.swing.*;
import java.beans.PropertyDescriptor;

/**
 * @author aeremenok 2010
 */
public interface PropertyMapper
{
    JComponent map( PropertyDescriptor propertyDescriptor );
}
