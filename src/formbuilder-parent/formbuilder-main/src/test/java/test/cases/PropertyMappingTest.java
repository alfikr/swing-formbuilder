package test.cases;

import domain.Person;
import org.fest.swing.fixture.JPanelFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.formbuilder.main.Builder;
import org.formbuilder.main.Form;
import org.formbuilder.main.map.GetterMapper;
import org.formbuilder.main.map.ValueChangeListener;
import org.formbuilder.main.map.type.TypeMapper;
import org.formbuilder.main.validation.BackgroundMarker;
import org.formbuilder.main.validation.ValidationMarker;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.env.FormEnvironment;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 16:36:33
 */
public class PropertyMappingTest
{
    private FormEnvironment env;

    @BeforeClass
    public void setUp()
            throws
            Exception
    {
        this.env = new FormEnvironment();
        this.env.setUp( this );
    }

    @AfterClass
    public void tearDown()
            throws
            Exception
    {
        this.env.tearDown( this );
    }

    @Test
    public void mapByGetter()
    {
        final Builder<Person> builder = Builder.map( Person.class ).useForGetters( new GetterMapper<Person>()
        {
            @Override
            public void mapGetters( final Person beanSample )
            {
                mapGetter( beanSample.getDescription(), TextAreaMapper.INSTANCE );
            }
        } );
        final Form<Person> form = env.buildFormInEDT( builder );
        env.addToWindow( form.asComponent() );

        env.setValueInEDT( form, env.createPerson() );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();

        JTextComponentFixture nameComponent = wrapperPanel.textBox( "name" );
        assert nameComponent.target instanceof JTextField;

        JTextComponentFixture descComponent = wrapperPanel.textBox( "description" );
        assert descComponent.target instanceof JTextArea;
    }

    @Test( dependsOnMethods = "mapByGetter" )
    public void mapByPropertyName()
    {
        final Builder<Person> builder = Builder.map( Person.class )
                .useForProperty( "description", TextAreaMapper.INSTANCE );
        final Form<Person> form = env.buildFormInEDT( builder );
        env.addToWindow( form.asComponent() );

        env.setValueInEDT( form, env.createPerson() );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();

        JTextComponentFixture nameComponent = wrapperPanel.textBox( "name" );
        assert nameComponent.target instanceof JTextField;

        JTextComponentFixture descComponent = wrapperPanel.textBox( "description" );
        assert descComponent.target instanceof JTextArea;
    }

    private static enum TextAreaMapper
            implements TypeMapper<JTextArea, String>
    {
        INSTANCE;

        @Override
        public Class<String> getValueClass()
        {
            return String.class;
        }

        @Override
        public String getValue( final JTextArea editorComponent )
        {
            return editorComponent.getText();
        }

        @Override
        public void setValue( final JTextArea editorComponent,
                              final String value )
        {
            editorComponent.setText( value );
        }

        @Override
        public JTextArea createEditorComponent()
        {
            return new JTextArea();
        }

        @Override
        public void bindChangeListener( final JTextArea editorComponent,
                                        final ValueChangeListener<String> stringValueChangeListener )
        {
            editorComponent.getDocument().addDocumentListener( new DocumentListener()
            {
                @Override
                public void insertUpdate( final DocumentEvent e )
                {
                    stringValueChangeListener.onChange();
                }

                @Override
                public void removeUpdate( final DocumentEvent e )
                {
                    stringValueChangeListener.onChange();
                }

                @Override
                public void changedUpdate( final DocumentEvent e )
                {
                    stringValueChangeListener.onChange();
                }
            } );
        }

        @Override
        public ValidationMarker getValidationMarker()
        {
            return BackgroundMarker.INSTANCE;
        }
    }
}
