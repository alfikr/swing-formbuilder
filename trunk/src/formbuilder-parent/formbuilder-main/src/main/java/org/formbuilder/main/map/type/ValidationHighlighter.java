package org.formbuilder.main.map.type;

import javax.swing.*;
import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * @author aeremenok
 *         Date: 29.07.2010
 *         Time: 17:40:48
 */
public interface ValidationHighlighter
{
    void highlightViolations( final JComponent editor,
                              Set<ConstraintViolation> violations );
}
