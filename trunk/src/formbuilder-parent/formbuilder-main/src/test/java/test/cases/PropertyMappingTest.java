package test.cases;

import domain.Person;
import org.fest.swing.fixture.JPanelFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.formbuilder.main.Builder;
import org.formbuilder.main.Form;
import org.formbuilder.main.map.ValueChangeListener;
import org.formbuilder.main.map.type.TypeMapper;
import org.formbuilder.main.validation.BackgroundHighlighter;
import org.formbuilder.main.validation.ValidationHighlighter;
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
    public void testProperty()
            throws
            InterruptedException
    {
        final Builder<Person> builder = Builder.from( Person.class )
                .useForProperty( "description", TextAreaMapper.INSTANCE );
        final Form<Person> form = env.buildFormInEDT( builder );
        env.addToWindow( form.asComponent() );

        final Person oldValue = env.createPerson();
        env.setValueInEDT( form, oldValue );

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
        public Class<String> valueClass()
        {
            return String.class;
        }

        @Override
        public String getValue( final JTextArea component )
        {
            return component.getText();
        }

        @Override
        public void setValue( final JTextArea component,
                              final String value )
        {
            component.setText( value );
        }

        @Override
        public JTextArea createComponent()
        {
            return new JTextArea();
        }

        @Override
        public void bindChangeListener( final JTextArea component,
                                        final ValueChangeListener<String> stringValueChangeListener )
        {
            component.getDocument().addDocumentListener( new DocumentListener()
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
        public ValidationHighlighter getValidationHighlighter()
        {
            return BackgroundHighlighter.INSTANCE;
        }
    }
}
