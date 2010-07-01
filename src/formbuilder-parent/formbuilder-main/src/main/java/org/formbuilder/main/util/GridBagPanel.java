package org.formbuilder.main.util;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * @author eav 2009
 */
public class GridBagPanel
    extends JPanel
{
    protected final GridBagConstraints constraints = new GridBagConstraints();

    public GridBagPanel()
    {
        super( new GridBagLayout() );

        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.BASELINE;
        constraints.ipadx = 10;
        constraints.ipady = 10;
    }

    public GridBagConstraints add( final JComponent component, final int row, final int col )
    {
        return add( component, row, col, 1, 1 );
    }

    public GridBagConstraints add( final JComponent component, final int row, final int col, final int weightx )
    {
        return add( component, row, col, 1, 1, weightx );
    }

    public GridBagConstraints add( final JComponent component, final int row, final int col, final int rowspan,
        final int colspan )
    {
        return add( component, row, col, rowspan, colspan, 1 );
    }

    public GridBagConstraints add( final JComponent component, final int row, final int col, final int rowspan,
        final int colspan, final int weightx )
    {
        constraints.gridx = col;
        constraints.gridy = row;
        constraints.gridwidth = colspan;
        constraints.gridheight = rowspan;
        constraints.weightx = weightx;
        constraints.weighty = 0;

        add( component, constraints );
        return constraints;
    }

    public GridBagConstraints getConstraints()
    {
        return constraints;
    }

    public void setPadding( final int ipadx, final int ipady )
    {
        constraints.ipadx = ipadx;
        constraints.ipady = ipady;
    }

    public void setInsets( final int top, final int bottom, final int left, final int right )
    {
        constraints.insets = new Insets( top, left, bottom, right );
    }
}
