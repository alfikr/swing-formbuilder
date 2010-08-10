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

package test.cases;

import static org.testng.Assert.fail;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.fest.swing.fixture.JPanelFixture;
import org.formbuilder.Form;
import org.formbuilder.FormBuilder;
import org.formbuilder.mapping.bean.PropertyNameBeanMapper;
import org.formbuilder.mapping.bean.SampleBeanMapper;
import org.testng.annotations.Test;

import domain.Person;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 16:22:16
 */
public class CustomMapperTest
        extends FormTest
{
    @Test( dependsOnMethods = "customizedBySample" )
    public void customizedByPropertyName()
    {
        assert !SwingUtilities.isEventDispatchThread();
        final Form<Person> form = env.buildFormInEDT( FormBuilder.map( Person.class ).with( new PropertyNameBeanMapper<Person>()
        {
            @Override
            public JComponent mapBean()
            {
                final JPanel panel = new JPanel( new BorderLayout() );
                panel.add( label( "name" ), BorderLayout.NORTH );
                panel.add( editor( "name" ), BorderLayout.CENTER );
                return panel;
            }
        } ) );

        final JComponent component = form.asComponent();
        env.verifyLayout( component, JPanel.class, BorderLayout.class );
        env.addToWindow( form );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        wrapperPanel.textBox( "name" );
        wrapperPanel.label( "name" );

        try
        {
            wrapperPanel.spinner( "age" );
            fail();
        }
        catch ( final Exception ignored )
        {}
    }

    @Test
    public void customizedBySample()
    {
        assert !SwingUtilities.isEventDispatchThread();
        final Form<Person> form = env.buildFormInEDT( FormBuilder.map( Person.class ).with( new SampleBeanMapper<Person>()
        {
            @Override
            public JComponent mapBean( final Person beanTemplate )
            {
                final JPanel panel = new JPanel( new BorderLayout() );
                panel.add( label( beanTemplate.getName() ), BorderLayout.NORTH );
                panel.add( editor( beanTemplate.getName() ), BorderLayout.CENTER );
                return panel;
            }
        } ) );

        final JComponent component = form.asComponent();
        env.verifyLayout( component, JPanel.class, BorderLayout.class );
        env.addToWindow( form );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        wrapperPanel.textBox( "name" );
        wrapperPanel.label( "name" );

        try
        {
            wrapperPanel.spinner( "age" );
            fail();
        }
        catch ( final Exception ignored )
        {}
    }
}
