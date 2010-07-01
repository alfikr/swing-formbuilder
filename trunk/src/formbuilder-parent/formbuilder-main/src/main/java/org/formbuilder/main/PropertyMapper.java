/**
 * 
 */
package org.formbuilder.main;

import java.beans.PropertyDescriptor;

import javax.swing.JComponent;

/**
 * @author aeremenok 2010
 */
public interface PropertyMapper
{
    JComponent map( PropertyDescriptor propertyDescriptor );
}
