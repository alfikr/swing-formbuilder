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

/**
 *
 */
package org.formbuilder.mapping.bean;

import org.formbuilder.BeanMapper;
import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.mapping.exception.MappingException;
import org.formbuilder.mapping.metadata.sort.OrderedPropertyDescriptor;
import org.formbuilder.util.GridBagPanel;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.*;
import java.beans.PropertyDescriptor;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static javax.swing.SwingUtilities.isEventDispatchThread;

/** @author aeremenok 2010 */
@NotThreadSafe
public class GridBagMapper<B>
        implements BeanMapper<B>
{
// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface BeanMapper ---------------------

    @Nonnull
    @Override
    public BeanMapping map( @Nonnull final BeanMappingContext<B> context )
    {
        checkState( isEventDispatchThread() );

        final GridBagPanel gridBagPanel = new GridBagPanel();

        final BeanMapping beanMapping = new BeanMapping( gridBagPanel );

        int row = 0;
        final List<OrderedPropertyDescriptor> sorted = context.getActiveSortedDescriptors();
        for ( final OrderedPropertyDescriptor orderedPropertyDescriptor : sorted )
        {
            final PropertyDescriptor descriptor = orderedPropertyDescriptor.getDescriptor();
            try
            {
                final JComponent editor = context.getEditor( descriptor, beanMapping );
                final JLabel label = context.getLabel( descriptor, beanMapping );

                gridBagPanel.add( editor, row, 1 );
                gridBagPanel.add( label, row, 0 );

                row++;
            }
            catch ( final MappingException e )
            {
                handleMappingException( e );
            }
        }

        return beanMapping;
    }

// -------------------------- OTHER METHODS --------------------------

    protected void handleMappingException( @Nonnull final MappingException e )
    {
        // skip wrong property
    }
}
