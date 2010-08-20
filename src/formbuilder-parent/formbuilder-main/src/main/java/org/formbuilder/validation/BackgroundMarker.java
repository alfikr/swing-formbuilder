/*
 * Copyright (C) 2010 Andrey Yeremenok (eav1986__at__gmail__com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.formbuilder.validation;

import org.formbuilder.util.TextUtil;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import javax.validation.ConstraintViolation;
import java.awt.*;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;
import static javax.swing.SwingUtilities.isEventDispatchThread;

/**
 * Changes the background of an editor to {@link Color#PINK} if there are some violations, or to {@link Color#WHITE} if
 * no violations occured. Also decorates an editor with semicolon-separated violation messages if the occure.
 *
 * @author aeremenok Date: 29.07.2010 Time: 17:42:02
 * @see ConstraintViolation#getMessage()
 */
@NotThreadSafe
public class BackgroundMarker
        implements ValidationMarker
{
    public static final BackgroundMarker INSTANCE = new BackgroundMarker();

    @Override
    public <B, C extends JComponent, V> void markViolations( @Nonnull final ValidationContext<B, C, V> validationContext )
    {
        checkState( isEventDispatchThread() );

        final Set<ConstraintViolation<B>> violations = validationContext.getViolations();
        final C editor = validationContext.getEditorComponent();

        if ( violations.isEmpty() )
        {
            editor.setBackground( Color.WHITE );
            editor.setToolTipText( null );
        }
        else
        {
            editor.setBackground( Color.PINK );
            editor.setToolTipText( TextUtil.digest( ";\n", violations ) );
        }
    }
}