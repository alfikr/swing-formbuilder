package org.formbuilder.mapping.change;

/**
 * @author eav
 *         Date: Aug 3, 2010
 *         Time: 12:05:47 AM
 * @param <V>
 */
public class EmptyChangeListener<V>
        implements ValueChangeListener<V>
{
    public static final EmptyChangeListener INSTANCE = new EmptyChangeListener();

    @Override
    public void onChange()
    {
        // do nothing
    }
}
