package test.cases;

import domain.Person;
import org.fest.swing.fixture.JPanelFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.formbuilder.Form;
import org.formbuilder.FormBuilder;
import org.formbuilder.GetterMapper;
import org.formbuilder.mapping.type.StringMapper;
import org.testng.annotations.Test;

import javax.swing.*;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 16:36:33
 */
public class PropertyMappingTest
        extends FormTest
{
    @Test
    public void mapByGetter()
    {
        final FormBuilder<Person> formBuilder = FormBuilder.map( Person.class )
                .useForGetters( new GetterMapper<Person>()
                {
                    @Override
                    public void mapGetters( final Person beanSample )
                    {
                        mapGetter( beanSample.getDescription(), new TextAreaMapper() );
                    }
                } );
        final Form<Person> form = env.buildFormInEDT( formBuilder );
        env.addToWindow( form.asComponent() );

        env.setValueInEDT( form, env.createPerson() );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();

        final JTextComponentFixture nameComponent = wrapperPanel.textBox( "name" );
        assert nameComponent.target instanceof JTextField;

        final JTextComponentFixture descComponent = wrapperPanel.textBox( "description" );
        assert descComponent.target instanceof JTextArea;
    }

    @Test( dependsOnMethods = "mapByGetter" )
    public void mapByPropertyName()
    {
        final FormBuilder<Person> formBuilder = FormBuilder.map( Person.class )
                .useForProperty( "description", new TextAreaMapper() );
        final Form<Person> form = env.buildFormInEDT( formBuilder );
        env.addToWindow( form.asComponent() );

        env.setValueInEDT( form, env.createPerson() );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();

        final JTextComponentFixture nameComponent = wrapperPanel.textBox( "name" );
        assert nameComponent.target instanceof JTextField;

        final JTextComponentFixture descComponent = wrapperPanel.textBox( "description" );
        assert descComponent.target instanceof JTextArea;
    }

    private static class TextAreaMapper
            extends StringMapper<JTextArea>
    {
        @Override
        public JTextArea createEditorComponent()
        {
            return new JTextArea();
        }
    }
}
