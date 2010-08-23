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
package test;

import com.toedter.calendar.JDateChooser;
import domain.Person;
import org.formbuilder.Form;
import org.formbuilder.FormBuilder;
import org.formbuilder.TypeMapper;
import org.formbuilder.mapping.beanmapper.SampleBeanMapper;
import org.formbuilder.mapping.beanmapper.SampleContext;
import org.formbuilder.mapping.change.ChangeHandler;
import org.formbuilder.mapping.typemapper.GetterConfig;
import org.formbuilder.mapping.typemapper.GetterMapper;
import org.formbuilder.validation.BackgroundMarker;
import test.cases.PropertyMappingTest.StringToTextAreaMapper;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

/** @author aeremenok 2010 */
@SuppressWarnings( "unused" )
public class Example
{
    public void ex1()
    {
        final JFrame myFrame = new JFrame();

        final Form<Person> form = FormBuilder.map( Person.class ).buildForm();
        myFrame.add( form.asComponent() );

        final Person person = new Person();
        person.setName( "person" );
        form.setValue( person );

        final Person changed = form.getValue();
        assert person != changed;
    }

    public void ex2()
    {
        Form<Person> form = FormBuilder.map( Person.class ).with( new SampleBeanMapper<Person>()
        {
            @Override
            protected JComponent mapBean( Person sample,
                                          SampleContext<Person> ctx )
            {
                final Box box = Box.createHorizontalBox();
                box.add( ctx.label( sample.getName() ) );
                box.add( ctx.editor( sample.getName() ) );
                return box;
            }
        } ).buildForm();
    }

    public void ex3()
    {
        final Form<Person> form = FormBuilder.map( Person.class ).use( new StringToTextAreaMapper() ).buildForm();
    }

    public void ex4()
    {
        final Form<Person> form = FormBuilder.map( Person.class )
                .useForProperty( "description", new StringToTextAreaMapper() )
                .buildForm();
    }

    public void ex5()
    {
        Form<Person> form = FormBuilder.map( Person.class ).useForGetters( new GetterMapper<Person>()
        {
            @Override
            public void mapGetters( Person beanSample,
                                    GetterConfig config )
            {
                config.use( beanSample.getDescription(), new StringToTextAreaMapper() );
            }
        } ).buildForm();
    }

    public void ex6()
    {
        final Form<Person> form = FormBuilder.map( Person.class ).doValidation( false ).buildForm();
    }

    class DateToDateChooserMapper
            implements TypeMapper<JDateChooser, Date>
    {
        @Override
        public void handleChanges( final JDateChooser editorComponent,
                                   final ChangeHandler changeHandler )
        {
            editorComponent.getDateEditor().addPropertyChangeListener( "date", new PropertyChangeListener()
            {
                @Override
                public void propertyChange( final PropertyChangeEvent evt )
                {
                    changeHandler.onChange( BackgroundMarker.INSTANCE );
                }
            } );
        }

        @Override
        public JDateChooser createEditorComponent()
        {
            return new JDateChooser();
        }

        @Override
        public Date getValue( final JDateChooser editorComponent )
        {
            return editorComponent.getDate();
        }

        @Override
        public Class<Date> getValueClass()
        {
            return Date.class;
        }

        @Override
        public void setValue( final JDateChooser editorComponent,
                              final Date value )
        {
            editorComponent.setDate( value );
        }
    }
}