package test.cases;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.fest.swing.fixture.JPanelFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.formbuilder.Form;
import org.formbuilder.FormBuilder;
import org.formbuilder.GetterMapper;
import org.formbuilder.mapping.type.StringMapper;
import org.testng.annotations.Test;

import domain.Person;

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
                        mapGetter( beanSample.getDescription(), new StringToTextAreaMapper() );
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
                .useForProperty( "description", new StringToTextAreaMapper() );
        final Form<Person> form = env.buildFormInEDT( formBuilder );
        env.addToWindow( form.asComponent() );

        env.setValueInEDT( form, env.createPerson() );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();

        final JTextComponentFixture nameComponent = wrapperPanel.textBox( "name" );
        assert nameComponent.target instanceof JTextField;

        final JTextComponentFixture descComponent = wrapperPanel.textBox( "description" );
        assert descComponent.target instanceof JTextArea;
    }

    public static class StringToTextAreaMapper
        extends StringMapper<JTextArea>
    {
        @Override
        public JTextArea createEditorComponent()
        {
            return new JTextArea();
        }
    }
}
