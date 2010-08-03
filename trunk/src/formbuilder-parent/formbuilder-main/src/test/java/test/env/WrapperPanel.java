package test.env;

import javax.swing.*;
import java.awt.*;

/**
 * @author eav 2009
 */
public class WrapperPanel
        extends JPanel
{
    protected final JComponent component;

    public WrapperPanel( final JComponent component )
    {
        this( component, false );
    }

    public WrapperPanel( final JComponent component,
                         final boolean scroll )
    {
        super( new BorderLayout() );
        this.component = component;
        if ( scroll )
        {
            add( new JScrollPane( component ), BorderLayout.CENTER );
        }
        else
        {
            add( component, BorderLayout.CENTER );
        }
    }

    public JComponent unwrap()
    {
        return component;
    }
}
